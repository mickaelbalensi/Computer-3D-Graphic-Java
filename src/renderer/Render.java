package renderer;

import elements.*;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import scene.Scene;
import primitives.*;

import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.*;


/**
 * A class representing a renderer
 * Can render images based on a scene
 */

public class Render {
    private ImageWriter _imageWriter;
    private Scene _scene;
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * Only constructor
     *
     * @param imageWriter (ImageWriter)
     * @param scene       (Scene) Contains geometries and lighting info
     */

    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }


    /**
     * This function create an image of shapes according to the ambient light
     */
    public void renderImage() {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();
        double distance = _scene.getDistance();

        for (int row = 0; row < nY; row++) {
            for (int column = 0; column < nX; column++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, column, row, distance, width, height);
                GeoPoint closestPoint = findClosestIntersection(ray);
                _imageWriter.writePixel(column, row, closestPoint == null ? background :
                        calcColor(closestPoint, ray).getColor());
            }
        }
    }

    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene._ambientLight.getIntensity());
    }


    /**
     * @param @param intersection (Point3D) the point of which we need the color
     * @return the color
     */

    private Color calcColor(GeoPoint geopoint, Ray inRay, int level, double k) {
        Color color = geopoint.geometry.getEmission();

        Vector v = inRay.getDirection();
        Vector n = geopoint.geometry.getNormal(geopoint.point);
        double nv = alignZero(n.dotProduct(v));
        Material material = geopoint.geometry.getMaterial();
        int nShininess = material.getShininess();
        double kd = material.getKd();
        double ks = material.getKs();
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(geopoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nv * nl > 0) {
                double ktr = transparency(lightSource, l, n, geopoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geopoint.point).scale(ktr);
                    Color calcDiff = calcDiffusive(kd, nl, lightIntensity);
                    Color calcSpec = calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity);
                    color = color.add(calcDiff, calcSpec);
                }
            }
        }

        if (level == 1) return Color.BLACK; // don't do recursive part if it will be stopped anyway

        double kr = material.getKr(), kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, geopoint.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay,
                        level - 1, kkr).scale(kr));
        }

        double kt = material.getKt(), kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, geopoint, inRay);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay,
                        level - 1, kkt).scale(kt));
        }
        return color;
    }

    /**
     * Calculate Specular component of light reflection
     *
     * @param ks             the coefficient of the specular component
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return
     */

    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.add(n.scale(-2 * nl));

        double vr = alignZero(v.dotProduct(r));
        if (vr >= 0) return Color.BLACK; // view from direction opposite to r vector

        return lightIntensity.scale(ks * Math.pow(-vr, nShininess));
    }

    private Color calcDiffusive(double kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd * abs(nl));
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        return findClosestIntersection(ray, Double.POSITIVE_INFINITY);
    }

    private GeoPoint findClosestIntersection(Ray ray, double max) {
        Point3D pt = ray.getPt();
        List<GeoPoint> intersectionPoints = _scene.getGeometries().findGeoIntersections(ray, max);
        if (intersectionPoints == null) return null;

        GeoPoint minDistancePoint = null;
        double distanceMinPoint = Double.POSITIVE_INFINITY;
        for (GeoPoint gp : intersectionPoints) {
            double distancePoint = gp.point.distance(pt);
            if (distancePoint < distanceMinPoint) {
                minDistancePoint = gp;
                distanceMinPoint = distancePoint;
            }
        }
        return minDistancePoint;
    }

    public Ray constructReflectedRay(Vector n, Point3D pt, Ray ray) {
        Vector v = ray.getDirection();
        double _2vn = 2 * v.dotProduct(n);
        Vector reflectedDirection = v.subtract(n.scale(_2vn));

        Ray reflectedRay = new Ray(pt, reflectedDirection, n);

        /*Vector epsVector = n.scale(n.dotProduct(reflectedDirection) > 0 ? DELTA : -DELTA);
        Point3D pointDelta = pt.add(epsVector);*/

        return reflectedRay;
    }

    public Ray constructRefractedRay(Vector n, GeoPoint geoPoint, Ray ray) {

/*
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        */

        return new Ray(geoPoint.point, ray.getDirection(), n);
    }


    /**
     * Displays a grid with fixed squares size
     */

    public void printGrid(int interval, java.awt.Color color) {
        for (int h = 0; h < _imageWriter.getNy(); h++) {
            for (int w = 0; w < _imageWriter.getNx(); w++) {
                if (h % 50 != 0 && w % 50 != 0) ;
                    //_imageWriter.writePixel(w, h, java.awt.Color.BLACK);
                else
                    _imageWriter.writePixel(w, h, color);
            }
        }
    }


    /**
     * Function writeToImage produces unoptimized jpeg file of
     * the image according to pixel color matrix in the directory
     * of the project
     */

    private boolean unshaded(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

        double lightDistance = lightSource.getDistance(geoPoint.point);
        List<GeoPoint> intersections = _scene.getGeometries().findGeoIntersections(lightRay, lightDistance);
        if (intersections == null) return true;

        for (GeoPoint gpt : intersections) {
            if (gpt.geometry.getMaterial().getKt() == 0)
                return false;
        }
        return true;
    }

    private double transparency(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

        double lightDistance = lightSource.getDistance(geoPoint.point);
        List<GeoPoint> intersections = _scene.getGeometries().findGeoIntersections(lightRay, lightDistance);
        if (intersections == null)
            return 1.0;

        double ktr = 1.0;
        for (GeoPoint gpt : intersections) {
            if (alignZero(gpt.point.distance(geoPoint.point) - lightDistance) <= 0)
                ktr *= gpt.geometry.getMaterial().getKt();
            if (ktr < MIN_CALC_COLOR_K) return 0.0;
        }
        return ktr;
    }

    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}

