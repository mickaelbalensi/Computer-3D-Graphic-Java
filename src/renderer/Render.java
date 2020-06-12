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
     * @param imageWriter (ImageWriter)
     * @param scene (Scene) Contains geometries and lighting info
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }

    /**
     * This function create an image of shapes according to the ambient light
     */
/*
    public void renderImage(){
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();

        int nX = _imageWriter.getNx();
        int nY=_imageWriter. getNy();
        double width=_imageWriter.getWidth();
        double height=_imageWriter.getHeight();
        double distance =_scene.getDistance();

        for (int row=0;row<nY;row++)
        {
            for (int column=0;column<nX;column++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, column, row, distance, width, height);


                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints.isEmpty())
                    _imageWriter.writePixel(column, row, background);
                else
                {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    java.awt.Color colorClosestPoint = calcColor(closestPoint).getColor();
                    _imageWriter.writePixel(column, row, colorClosestPoint);
                }
            }
        }
    }
*/
    public void renderImage(){
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();

        int nX = _imageWriter.getNx();
        int nY=_imageWriter. getNy();
        double width=_imageWriter.getWidth();
        double height=_imageWriter.getHeight();
        double distance =_scene.getDistance();

        for (int row=0;row<nY;row++)
        {
            for (int column=0;column<nX;column++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, column, row, distance, width, height);

                GeoPoint closestPoint = findClosestIntersection(ray);
                _imageWriter.writePixel(column,row, closestPoint == null ?
                        _scene.getBackground().getColor():
                        calcColor(closestPoint, ray).getColor());
            }
        }
    }

    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene._ambientLight.getIntensity());
    }

    /**
     *
     * @param  @param intersection (Point3D) the point of which we need the color
     * @return the color
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay, int level, double k)  {
        //Color color = _scene._ambientLight.getIntensity();
        Color color = geopoint.geometry.getEmission();

        Vector v = geopoint.point.subtract(_scene.getCamera().getP0()).normalize();
        Vector n = geopoint.geometry.getNormal(geopoint.point);
        Material material =geopoint.geometry.getMaterial();
        int nShininess = material.getShininess();
        double kd = material.getKd();
        double ks = material.getKs();
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(geopoint.point);
            if (sign(n.dotProduct(l)) == sign(n.dotProduct(v))) {
                if (unshaded(lightSource,l,n,geopoint)) {
                    Color lightIntensity = lightSource.getIntensity(geopoint.point);
                    Color calcDiff=calcDiffusive(kd, l, n, lightIntensity);
                    Color calcSpec=calcSpecular(ks, l, n, v, nShininess, lightIntensity);
                    color = color.add(calcDiff,calcSpec);
                }
            }
        }

        if (level == 1) return Color.BLACK;

        double kr = geopoint.geometry.getMaterial().getKr(), kkr = k * kr;

        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, geopoint.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay,
                        level-1, kkr).scale(kr));
        }
        double kt = geopoint.geometry.getMaterial().getKt(), kkt = k * kt;

        if (kkt > MIN_CALC_COLOR_K) {
                Ray refractedRay = constructRefractedRay(geopoint, inRay) ;
                GeoPoint refractedPoint = findClosestIntersection(refractedRay);
                if (refractedPoint != null)
                    color = color.add(calcColor(refractedPoint, refractedRay,
                            level-1, kkt).scale(kt));
        }
        return color;
    }

    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * Calculate Specular component of light reflection
     * @param ks                the coefficient of the specular component
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        double p = nShininess;
        double nl=(n.dotProduct(l));

        if (isZero(nl)) {
            throw new IllegalArgumentException("nl cannot be Zero for scaling the normal vector");
        }
        Vector r= l.add(n.scale(-2*nl));
        double VR = (v.dotProduct(r));
        if (VR >= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }

        return lightIntensity.scale(ks * Math.pow(-1d * VR, p));
    }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd*abs(n.dotProduct(l)));
    }

    /**
     * Returns the closest point to the camera from a list
     */
    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {
        if (intersectionPoints.isEmpty())
            return null;

        GeoPoint minDistancePoint=intersectionPoints.get(0);

        for (int i=1;i<intersectionPoints.size();i++){
            double distancePoint1=intersectionPoints.get(i).point.distance(_scene.getCamera().getP0());
            double distanceMinPoint=minDistancePoint.point.distance(_scene.getCamera().getP0());
            if( distancePoint1< distanceMinPoint)
                minDistancePoint=intersectionPoints.get(i);
        }
        return minDistancePoint;
    }

    private GeoPoint findClosestIntersection(Ray ray){
        List<GeoPoint> pointsIntersections=_scene.getGeometries().findIntersections(ray);

        if (pointsIntersections.isEmpty())
            return null;

        return getClosestPoint(pointsIntersections);
    }

    public Ray constructReflectedRay(Vector n,Point3D pt,Ray ray){
        Vector v=ray.getDirection();
        Vector epsVector = n.scale(n.dotProduct(v) > 0 ? DELTA : -DELTA);
        Point3D pointDelta = pt.add(epsVector);

        double _2vn=2*v.dotProduct(n);

        Vector r=v.subtract(n.scale(_2vn));

        return new Ray(pointDelta,r);
    }

    public Ray constructRefractedRay(GeoPoint geoPoint,Ray ray){
        /*
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        */
        return new Ray(geoPoint.point,ray.getDirection());
    }


    /**
     * Displays a grid with fixed squares size
     */
    public void printGrid(int interval, java.awt.Color color){
        for (int h=0;h<_imageWriter.getNy();h++)
        {
            for (int w=0;w<_imageWriter.getNx();w++) {
                if (h % 50 != 0 && w % 50 != 0);
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
    private boolean unshaded(LightSource lightSource,Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);

        double lightDistance = lightSource.getDistance(geoPoint.point);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay,lightDistance);
        if (intersections.isEmpty())
            return true;

        for (GeoPoint gpt : intersections) {
            if (alignZero(gpt.point.distance(geoPoint.point) - lightDistance) <= 0)
                return false;
        }
        return true;

    }


    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}
/*
package renderer;

import elements.*;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import scene.Scene;
import primitives.*;
import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.*;

*/
/**
 * A class representing a renderer
 * Can render images based on a scene
 *//*

public class Render {
    private ImageWriter _imageWriter;
    private Scene _scene;
    private static final double DELTA = 0.1;

    */
/**
     * Only constructor
     * @param imageWriter (ImageWriter)
     * @param scene (Scene) Contains geometries and lighting info
     *//*

    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }

    */
/**
     * This function create an image of shapes according to the ambient light
     *//*

    public void renderImage(){
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();

        int nX = _imageWriter.getNx();
        int nY=_imageWriter. getNy();
        double width=_imageWriter.getWidth();
        double height=_imageWriter.getHeight();
        double distance =_scene.getDistance();

        for (int row=0;row<nY;row++)
        {
            for (int column=0;column<nX;column++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, column, row, distance, width, height);
                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints.isEmpty())
                    _imageWriter.writePixel(column, row, background);
                else
                    {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    java.awt.Color colorClosestPoint = calcColor(closestPoint).getColor();
                    _imageWriter.writePixel(column, row, colorClosestPoint);
                }
            }
        }
    }

    */
/**
     *
     * @param  @param intersection (Point3D) the point of which we need the color
     * @return the color
     *//*

    private Color calcColor(GeoPoint intersection) {
        Color color = _scene._ambientLight.getIntensity();
        color = color.add(intersection.geometry.getEmission());

        Vector v = intersection.point.subtract(_scene.getCamera().getP0()).normalize();
        Vector n = intersection.geometry.getNormal(intersection.point);
        Material material =intersection.geometry.getMaterial();
        int nShininess = material.getShininess();
        double kd = material.getKd();
        double ks = material.getKs();
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(intersection.point);
            if (sign(n.dotProduct(l)) == sign(n.dotProduct(v))) {
                if (unshaded(lightSource,l,n,intersection)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    Color calcDiff=calcDiffusive(kd, l, n, lightIntensity);
                    Color calcSpec=calcSpecular(ks, l, n, v, nShininess, lightIntensity);
                    color = color.add(calcDiff,calcSpec);
                }
            }
        }
        return color;
    }

    private boolean sign(double val) {
        return (val > 0d);
    }

    */
/**
     * Calculate Specular component of light reflection
     * @param ks                the coefficient of the specular component
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return
     *//*

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        double p = nShininess;
        double nl=(n.dotProduct(l));

        if (isZero(nl)) {
            throw new IllegalArgumentException("nl cannot be Zero for scaling the normal vector");
        }
        Vector r= l.add(n.scale(-2*nl));
        double VR = (v.dotProduct(r));
        if (VR >= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }

        return lightIntensity.scale(ks * Math.pow(-1d * VR, p));
    }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd*abs(n.dotProduct(l)));
    }

    */
/**
     * Returns the closest point to the camera from a list
     *//*

    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {
        if (intersectionPoints.isEmpty())
            return null;

        GeoPoint minDistancePoint=intersectionPoints.get(0);

        for (int i=1;i<intersectionPoints.size();i++){
            double distancePoint1=intersectionPoints.get(i).point.distance(_scene.getCamera().getP0());
            double distanceMinPoint=minDistancePoint.point.distance(_scene.getCamera().getP0());
            if( distancePoint1< distanceMinPoint)
                minDistancePoint=intersectionPoints.get(i);
        }
        return minDistancePoint;
    }

    */
/**
     * Displays a grid with fixed squares size
     *//*

    public void printGrid(int interval, java.awt.Color color){
        for (int h=0;h<_imageWriter.getNy();h++)
        {
            for (int w=0;w<_imageWriter.getNx();w++) {
                if (h % 50 != 0 && w % 50 != 0);
                    //_imageWriter.writePixel(w, h, java.awt.Color.BLACK);
                else
                    _imageWriter.writePixel(w, h, color);
            }
        }
    }

    */
/**
     * Function writeToImage produces unoptimized jpeg file of
     * the image according to pixel color matrix in the directory
     * of the project
     *//*

    private boolean unshaded(LightSource lightSource,Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        double lightDistance = lightSource.getDistance(geoPoint.point);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay,lightDistance);
        if (intersections.isEmpty())
            return true;

        for (GeoPoint gpt : intersections) {
            if (alignZero(gpt.point.distance(geoPoint.point) - lightDistance) <= 0)
                return false;
        }
        return true;

    }


    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}




*/
