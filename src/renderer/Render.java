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
    private static final double EPS = 0.1;



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
                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints.isEmpty())
                    _imageWriter.writePixel(column, row, background);
                else
                    {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(column, row, calcColor(closestPoint).getColor());
                }
            }
        }
    }

    /**
     *
     * @param  @param intersection (Point3D) the point of which we need the color
     * @return the color
     */
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
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
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
            if(intersectionPoints.get(i).point.distance(_scene.getCamera().getP0()) < minDistancePoint.point.distance(_scene.getCamera().getP0()))
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
     * Function writeToImage produces unoptimized jpeg file of
     * the image according to pixel color matrix in the directory
     * of the project
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}




