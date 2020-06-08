package renderer;

import elements.*;
import geometries.Geometry;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.Vector;
import scene.Scene;
import primitives.*;

import java.security.KeyStore;
import java.util.*;

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
    private static final double EPS = 0.1;
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
                 List<GeoPoint> intersectionPoints  = geometries.findIntersections(ray);
                if (intersectionPoints.isEmpty())
                    _imageWriter.writePixel(column, row, background);
                else
                    {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    java.awt.Color colorClosestPoint = calcColor(closestPoint,ray).getColor();
                    _imageWriter.writePixel(column, row, colorClosestPoint);
                }
            }
        }
    }


    /**
     *
     * @param  @param intersection (Point3D) the point of which we need the color
     * @return the color
     */
    private Color calcColor(GeoPoint intersection, Ray inRay) {
        return calcColor(intersection, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(_scene._ambientLight.getIntensity());
    }
    private Color calcColor(GeoPoint intersection,Ray inRay,int level,double k) {
        if (level == 0) return Color.BLACK;
        //Color color = _scene._ambientLight.getIntensity();
        //color = color.add(intersection.geometry.getEmission());
        Color color = intersection.geometry.getEmission();
       // Vector a= inRay.getDirection();
        Vector v = intersection.point.subtract(_scene.getCamera().getP0()).normalize();
        Vector n = intersection.geometry.getNormal(intersection.point);
        Material material =intersection.geometry.getMaterial();
        double nShininess = material.getShininess();
        double kd = material.getKd();
        double ks = material.getKs();
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(intersection.point);
            if (sign(n.dotProduct(l)) == sign(n.dotProduct(v))) {
                double ktr=transparency(l,n,intersection);
                if (ktr*k> MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        double kr=intersection.geometry._material.getKr();
        double kkr=k*kr;
        if (kkr>MIN_CALC_COLOR_K){
            Ray reflectedRay = constructReflectedRay(n, intersection.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersections(reflectedRay);
            if (reflectedRay!=null && reflectedPoint!=null) {
                color.add(calcColor(reflectedPoint, reflectedRay,level-1,kkr).scale(kr));
            }

        }

        double kt = intersection.geometry._material.getKt();
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K){
            Ray refractedRay = constructRefractedRay(intersection, inRay) ;
            GeoPoint refractedPoint = findClosestIntersections(refractedRay);
            if (refractedRay !=null && refractedPoint!=null) {
                color = color.add(calcColor(refractedPoint, refractedRay , level-1, kkt).scale(kt));
            }
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
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, double nShininess, Color lightIntensity) {
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
     *  To check if the light isn't locked by an object if so we don't color the point
     * @param lightSource
     * @param l
     * @param n
     * @param geoPoint
     * @return
     */
    private boolean unshaded(LightSource lightSource,Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? EPS : -EPS);
        Point3D point = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections.isEmpty())
            return true;
        double distance = lightSource.getDistance(geoPoint.point);
        for (GeoPoint gpt : intersections) {
            if (alignZero(gpt.point.distance(geoPoint.point) - distance) <= 0)
                return false;
        }
        return true;

    }
    /**
     * it sends back the reflected ray from the geometric pictures
     * @param ray
     * @param normal
     * @param pt
     * @return
     */
    public Ray  constructReflectedRay(Vector normal,Point3D pt,Ray ray){
        Vector newVector;
        double d=ray.getDirection().dotProduct(normal);
        newVector=ray.getDirection().subtract(normal.scale(2*d));
        return new Ray(pt,newVector);
    }
    /**
     * send the refractedRay
     * @param ray
     * @return
     */
    public Ray constructRefractedRay(GeoPoint geoPoint,Ray ray){
        return new Ray(geoPoint.point,ray.getDirection());
    }


    private GeoPoint findClosestIntersections(Ray ray){
        GeoPoint closestPoint;
        List<GeoPoint>intersections=_scene.getGeometries().findIntersections(ray);

        if (intersections.isEmpty())
            return null;

        closestPoint=intersections.get(0);
        double minDistance=ray.getPt().distance(closestPoint.point);
        for (GeoPoint geoPoint:intersections){
            double newDistance=ray.getPt().distance(geoPoint.point);
            if (newDistance< minDistance)
            {
                minDistance=newDistance;
                closestPoint=geoPoint;
            }
        }
        return closestPoint;
    }

    private double transparency(Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? EPS : -EPS);
        Point3D point = geopoint.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        double ktr = 1;
        for (GeoPoint gp : intersections)
            ktr *= gp.geometry._material.getKt();
        return ktr;
    }



    /**
     * Function writeToImage produces unoptimized jpeg file of
     * the image according to pixel color matrix in the directory
     * of the project
     */

    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}




