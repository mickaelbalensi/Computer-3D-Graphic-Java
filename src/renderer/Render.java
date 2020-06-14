/*package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

*//**
 *
 *//*
public class Render {
    private Scene scene;
    private ImageWriter imageWriter;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static  final int MAX_RAYS = 6;
    private final ImageWriter _imageWriter;
    private final Scene _scene;

    private double _supersamplingRate;

    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
        this._supersamplingRate = 0d;
    }

    public double getSupersamplingRate() {
        return _supersamplingRate;
    }

    public void setSupersamplingRate(double supersamplingRate) {
        _supersamplingRate = supersamplingRate;
    }

    public void printGrid(int interval, java.awt.Color color) {
        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    public void renderImage() {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        AmbientLight ambientLight = _scene.getAmbientLight();
        double distance = _scene.getDistance();

        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        if (_supersamplingRate == 0d) {
            for (int row = 0; row < Ny; row++) {
                for (int collumn = 0; collumn < Nx; collumn++) {
                    Ray ray = camera.constructRayThroughPixel(Nx, Ny, collumn, row, distance, width, height);
                    GeoPoint closestPoint = findClosestIntersection(ray);
                    if (closestPoint == null) {
                        _imageWriter.writePixel(collumn, row, background);
                    } else {
                        _imageWriter.writePixel(collumn, row, calcColor(closestPoint, ray).getColor());
                    }
                }
            }
        } else {    //supersampling
            for (int row = 0; row < Ny; row++) {
                for (int collumn = 0; collumn < Nx; collumn++) {
                    //    List<Ray> rays = camera.constructRayBeamThroughPixel(Nx, Ny, collumn, row, distance, width, height, _supersamplingRate);
                    Color averageColor = Color.BLACK;
                    //makes a list of the multiple rays that goes through different parts of the pixel
                    List<Ray> rays = getRaysThroughPixel(row, collumn);

                    //will store the colors in that pixel in a list later
                    //  List<Color> raysColors = new ArrayList<>();

                    Color Bckg = new Color(background);
                    for (Ray ray : rays) {
                        GeoPoint closestPoint = findClosestIntersection(ray);
                        if (closestPoint == null) {
                            averageColor = averageColor.add(Bckg);
                        } else {
                            averageColor = averageColor.add(calcColor(closestPoint, ray));
                        }
                        averageColor.scale(1d / rays.size());
                    }
                    _imageWriter.writePixel(collumn, row, averageColor.getColor());
                }
            }
        }
    }

    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {

        if (intersectionPoints == null) {
            return null;
        }

        GeoPoint result = null;

        Point3D p0 = _scene.getCamera().getP0();
        double minDist = Double.MAX_VALUE;
        double currentDistance;

        for (GeoPoint geoPoint : intersectionPoints) {
            currentDistance = p0.distance(geoPoint.getPoint());
            if (currentDistance < minDist) {
                minDist = currentDistance;
                result = geoPoint;
            }
        }
        return result;
    }

    *//**
     * Find intersections of a ray with the scene geometries and get the
     * intersection point that is closest to the ray head. If there are no
     * intersections, null will be returned.
     *
     * @param ray intersecting the scene
     * @return the closest point
     *//*
    private GeoPoint findClosestIntersection(Ray ray) {

        if (ray == null) {
            return null;
        }

        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.getPt();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.getPoint());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = geoPoint;
            }
        }
        return closestPoint;
    }

    private Color calcColor(GeoPoint geoPoint, Ray inRay) {
        Color color = calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);
        color = color.add(_scene.getAmbientLight().getIntensity());
        return color;
    }

    private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
        if (level == 1) {
            return Color.BLACK;
        }

        Point3D pointGeo = geoPoint.getPoint();
        Geometry geometryGeo = geoPoint.geometry;
        Color color = geometryGeo.getEmission();

        Material material = geometryGeo.getMaterial();
        int nShininess = material.getShininess();
        double kd = material.getKd();
        double ks = material.getKs();

        Vector v = pointGeo.subtract(_scene.getCamera().getP0()).normalize();
        Vector n = geometryGeo.getNormal(pointGeo);

//        double nv = alignZero(n.dotProduct(v));
//        if (nv == 0) {
//            //ray parallel to geometry surface ??
//            //and orthogonal to normal
//            return Color.BLACK;
//        }

        color = getColorLightSources(geoPoint, k, color, v, n, nShininess, kd, ks);

        double kr = geometryGeo.getMaterial().getKr();
        double kkr = k * kr;

        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(pointGeo, inRay, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null) {
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }

        double kt = geometryGeo.getMaterial().getKt();
        double kkt = k * kt;

        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(pointGeo, inRay, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);

            if (refractedPoint != null) {
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }
        return color;
    }

    private Color getColorLightSources(GeoPoint geoPoint, double k, Color color, Vector v, Vector n, int nShininess, double kd, double ks) {
        Point3D pointGeo = geoPoint.getPoint();
        if (_scene.getLights() != null) {
            for (LightSource lightSource : _scene.getLights()) {
                Vector l = lightSource.getL(pointGeo);
                double nl = n.dotProduct(l);
                double nv = n.dotProduct(v);
                double ktr;
                if (nl * nv > 0) {
//                if (unshaded(lightSource, l, n, geoPoint)) {
//                    ktr = 1d;
//                } else {
//                    ktr = 0d;
//                }
                    ktr = transparency(lightSource, l, n, geoPoint);
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(pointGeo).scale(ktr);
                        color = color.add(
                                calcDiffusive(kd, nl, lightIntensity),
                                calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                    }
                }
            }
        }
        return color;
    }


    private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n) {
        return new Ray(pointGeo, inRay.getDirection(), n);
    }

    private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n) {
        //𝒓=𝒗 −𝟐∙(𝒗∙𝒏)∙𝒏
        Vector v = inRay.getDirection();
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
    }

    *//**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param nl         dot-product n*l
     * @param V          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * @author Dan Zilberstein (slightly modified by me)
     * <p>
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     * </p>
     *//*
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector V, int nShininess, Color ip) {
        double p = nShininess;
        if (isZero(nl)) {
            throw new IllegalArgumentException("nl cannot be Zero for scaling the normal vector");
        }
        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double VR = alignZero(V.dotProduct(R));
        if (VR >= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        // [rs,gs,bs]ks(-V.R)^p
        return ip.scale(ks * Math.pow(-1d * VR, p));
    }

    *//**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd diffusive component coef
     * @param nl dot-product n*l
     * @param ip light intensity at the point
     * @return diffusive component of light reflection
     * @author Dan Zilberstein
     * <p>
     * The diffuse component is that dot product n•L. It approximates light, originally from light source L,
     * reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossysurface is paper.
     * In general, you'll also want this to have a non-gray color value,
     * so this term would in general be a color defined as: [rd,gd,bd](n•L)
     * </p>
     *//*
    private Color calcDiffusive(double kd, double nl, Color ip) {
        return ip.scale(Math.abs(nl) * kd);
    }

    private boolean sign(double val) {
        return (val > 0d);
    }

    private boolean unshaded_0(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
        Point3D pointGeo = geopoint.getPoint();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) {
            return true;
        }
        // Flat geometry cannot self intersect
        if (geopoint.geometry instanceof Geometry) {
            intersections.remove(geopoint);
        }

        double lightDistance = light.getDistance(pointGeo);
        for (GeoPoint gp : intersections) {
            double temp = gp.getPoint().distance(pointGeo) - lightDistance;
            if (alignZero(temp) <= 0)
                return false;
        }
        return true;
    }

    private boolean occluded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Point3D geometryPoint = geopoint.getPoint();
        Vector lightDirection = light.getL(geometryPoint);
        lightDirection.scale(-1);

        Vector epsVector = geopoint.geometry.getNormal(geometryPoint);
        epsVector.scale(epsVector.dotProduct(lightDirection) > 0 ? 2 : -2);
        geometryPoint.add(epsVector);
        Ray lightRay = new Ray(geometryPoint, lightDirection);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);

        // Flat geometry cannot self intersect
        if (geopoint.geometry instanceof Geometry) {
            intersections.remove(geopoint);
        }

        for (GeoPoint entry : intersections)
            if (entry.geometry.getMaterial().getKt() == 0)
                return true;
        return false;
    }

    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
        Point3D pointGeo = geopoint.getPoint();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) {
            return 1d;
        }
        double lightDistance = light.getDistance(pointGeo);
        double ktr = 1d;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.getPoint().distance(pointGeo) - lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K) {
                    return 0.0;
                }
            }
        }
        return ktr;
    }

    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
        Point3D pointGeo = geopoint.getPoint();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) {
            return true;
        }
        double lightDistance = light.getDistance(pointGeo);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.getPoint().distance(pointGeo) - lightDistance) <= 0
                    && gp.geometry.getMaterial().getKt() == 0) {
                return false;
            }
        }
        return true;
    }
    //this function improves ray tracing by making multiple rays through different parts of the pixel
    private List<Ray> getRaysThroughPixel(int x, int y){

        //the ratio of the screen dimensions to the number of pixels
        double Rx = imageWriter.getWidth() /imageWriter.getNx();
        double Ry = imageWriter.getHeight() / imageWriter.getNy();

        //store the rays in a list
        List<Ray> raysThroughPixel = new ArrayList<>();

        //i will go from -1/2 * the max to 1/2 max
        for (int i = (-1 * MAX_RAYS) / 2; i <= MAX_RAYS / 2; i++) {
            for (int j = (-1 * MAX_RAYS) / 2; j <= MAX_RAYS / 2; j++) {

                //calculating the coordinate of the offset rays
                double iOffSet = (i * Rx) / MAX_RAYS;
                double jOffSet = (j * Ry) / MAX_RAYS;
                //making a ray to that position and placing it in the list
                Ray ray = scene.getCamera().constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), x + iOffSet, y + jOffSet, scene.getDistance(), imageWriter.getWidth(), imageWriter.getHeight());
                raysThroughPixel.add(ray);
            }
        }

        return raysThroughPixel;
    }

}*/

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
                double ktr= transparency(lightSource,l,n,geopoint);//rajout
                if (ktr*k>MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geopoint.point).scale(ktr);
                    Color calcDiff=calcDiffusive(kd, l, n, lightIntensity);
                    Color calcSpec=calcSpecular(ks, l, n, v, nShininess, lightIntensity);
                    color = color.add(calcDiff,calcSpec);
                }

/*if (unshaded(lightSource,l,n,geopoint)) {
                    Color lightIntensity = lightSource.getIntensity(geopoint.point);
                    Color calcDiff=calcDiffusive(kd, l, n, lightIntensity);
                    Color calcSpec=calcSpecular(ks, l, n, v, nShininess, lightIntensity);
                    color = color.add(calcDiff,calcSpec);
                }*/


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
                Ray refractedRay = constructRefractedRay(n, geopoint, inRay) ;
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
        double _2vn=2*v.dotProduct(n);
        Vector reflectedDirection=v.subtract(n.scale(_2vn));

        Ray reflectedRay= new Ray(pt,reflectedDirection,n);

        /*Vector epsVector = n.scale(n.dotProduct(reflectedDirection) > 0 ? DELTA : -DELTA);
        Point3D pointDelta = pt.add(epsVector);*/

        return reflectedRay;
    }

    public Ray constructRefractedRay(Vector n,GeoPoint geoPoint,Ray ray){

/*
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        */

        return new Ray(geoPoint.point,ray.getDirection(),n);
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
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

/*Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);*/


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

    private double transparency(LightSource lightSource,Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay/*,lightDistance*/);
        if (intersections.isEmpty())
            return 1.0;

        double lightDistance = lightSource.getDistance(geoPoint.point);
        double ktr =1.0;
        for (GeoPoint gpt : intersections) {
            if (alignZero(gpt.point.distance(geoPoint.point) - lightDistance) <= 0)
                ktr*=gpt.geometry.getMaterial().getKt();
            if(ktr<MIN_CALC_COLOR_K) return 0.0;
        }
        return ktr;
    }
    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}

