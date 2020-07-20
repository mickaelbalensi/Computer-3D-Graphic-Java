package renderer;

import elements.*;
import geometries.Box;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.Vector;
import scene.Node;
import scene.Scene;
import primitives.*;

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
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private int _threads = 1;
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean _print = false; // printing progress percentage
    private static int count = 0;
    private static int counter = 0;
    private static boolean ACTIVATE = true;
    private static boolean DEACTIVATE = false;


    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) synchronized (System.out) {
                System.out.printf("\r %02d%%", _percents);
            }
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_print && _counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_print && _counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (_print && percents > 0)
                synchronized (System.out) {
                    System.out.printf("\r %02d%%", percents);
                }
            if (percents >= 0)
                return true;
            if (_print) synchronized (System.out) {
                System.out.printf("\r %02d%%", 100);
            }
            return false;
        }
    }

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

    private volatile int max = 0;

    public void renderImage() {
        renderImage(DEACTIVATE);
    }

    /**
     * This function create an image of shapes according to the ambient light
     * @param improvementCheckIntersection check if there is an intersection
     */
    public void renderImage(boolean improvementCheckIntersection) {
        final Camera camera = _scene.getCamera();
        final Intersectable geometries = _scene.getGeometries();
        final java.awt.Color background = _scene.getBackground().getColor();

        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final double width = _imageWriter.getWidth();
        final double height = _imageWriter.getHeight();
        final double distance = _scene.getDistance();
        // Multi-threading
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                max = 0;
                while (thePixel.nextPixel(pixel)) {
                    if (count == 33) {
                        count = 33;
                    }
                    Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, distance, width, height);
                    GeoPoint closestPoint = findClosestIntersection(ray, improvementCheckIntersection);
                    count++;


                    _imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background :
                            calcColor(closestPoint, ray, ACTIVATE, improvementCheckIntersection).getColor());
                    System.out.println(count);
                }
               /* while (thePixel.nextPixel(pixel)) {
                    count ++;
                    Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, distance, width, height);
                    GeoPoint closestPoint = findClosestIntersection(ray);
                    int rest= count %1000;
                    if (count<=180000 || rest<=240)
                        _imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background :
                                calcColor(closestPoint, ray,DEACTIVATE).getColor());
                    else
                        _imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background :
                                calcColor(closestPoint, ray,ACTIVATE).getColor());
                    if(count%200==0)
                        System.out.println(count);
                }*/
            });
        }
        for (Thread thread : threads) thread.start();
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        if (_print) synchronized (System.out) {
            System.out.printf("\r100%%\n");
        }
    }

/*
    public void renderImageOriginal() {
        final Camera camera = _scene.getCamera();
        final Intersectable geometries = _scene.getGeometries();
        final java.awt.Color background = _scene.getBackground().getColor();

        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final double width = _imageWriter.getWidth();
        final double height = _imageWriter.getHeight();
        final double distance = _scene.getDistance();
        // Multi-threading
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                max = 0;

                while (thePixel.nextPixel(pixel)) {
                    count++;
                    Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, distance, width, height);
                    GeoPoint closestPoint = findClosestIntersection(ray);
                    int rest = count % 1000;
                    if (count <= 180000 || rest <= 240)
                        _imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background :
                                calcColor(closestPoint, ray, DEACTIVATE).getColor());
                    else
                        _imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background :
                                calcColor(closestPoint, ray, ACTIVATE).getColor());
                    if (count % 200 == 0)
                        System.out.println(count);
                }
            });
        }
        for (Thread thread : threads) thread.start();
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        if (_print) synchronized (System.out) {
            System.out.printf("\r100%%\n");
        }
    }
*/

    private Color calcColor(GeoPoint geopoint, Ray inRay, boolean softShadow, boolean improvementCheckIntersection) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0, softShadow, improvementCheckIntersection).add(
                _scene.ambientLight.getIntensity());
    }

    /**
     * @param @param intersection (Point3D) the point of which we need the color
     * @return the color
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay, int level, double k, boolean softShadow, boolean improvementCheckIntersection) {
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
                double ktr = transparency(lightSource, l, n, geopoint, softShadow, improvementCheckIntersection);
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
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay, improvementCheckIntersection);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr, softShadow, improvementCheckIntersection).scale(kr));
        }

        double kt = material.getKt(), kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, geopoint, inRay);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay, improvementCheckIntersection);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt, softShadow, improvementCheckIntersection).scale(kt));
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

        return lightIntensity.scale(ks * Math.pow(-vr, nShininess));//
    }

    private Color calcDiffusive(double kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd * abs(nl));
    }

    private GeoPoint findClosestIntersection(Ray ray, boolean improvementCheckIntersection) {
        return findClosestIntersection(ray, Double.POSITIVE_INFINITY, improvementCheckIntersection);
    }

    /**
     * This function calculate intersections' points from ray receiving in parameter.
     * <p>
     * In the case improvement is activated
     *
     * @param ray
     * @param max
     * @param improvementCheckIntersection
     * @return
     */
    private List<GeoPoint> improvementIntersection(Ray ray, double max, boolean improvementCheckIntersection) {
        if (improvementCheckIntersection == DEACTIVATE) {//check for all geometries if ray crows one of them
            return _scene.getGeometries().findGeoIntersections(ray, max);
        }
        Scene.Node<Geometries> root = _scene.getGeometriesTree();
//        List<Intersectable> box =root.getData().getGeometries();
        Geometries box2 = root.getData();
        List<GeoPoint> intersectionPointsWithOuterBox = box2.findGeoIntersections(ray, max);
        if (intersectionPointsWithOuterBox == null)
            return null;

        List<GeoPoint> interPointsWithInnerBox = null;
        List<GeoPoint> intersectionPointsWithGeometries = null;
        List<Scene.Node<Geometries>> children = _scene.getGeometriesTree().getChildren();
        for (Scene.Node<Geometries> g : children) {
            interPointsWithInnerBox = g.getData().findGeoIntersections(ray, max);
            if (interPointsWithInnerBox != null) {
                List<GeoPoint> interPointsGeometry = g.getChildren().get(0).getData().findGeoIntersections(ray, max);
                if (interPointsGeometry != null) {
                    //initialization of the list for the first intersection's point with the geometry
                    if (intersectionPointsWithGeometries == null)//
                        intersectionPointsWithGeometries = new ArrayList();
                    for (GeoPoint gp : interPointsGeometry)
                        intersectionPointsWithGeometries.add(gp);
                }
            }
        }
        return intersectionPointsWithGeometries;
    }

    private GeoPoint findClosestIntersection(Ray ray, double max, boolean improvementCheckIntersection) {
        Point3D pt = ray.getPt();
        List<GeoPoint> intersectionPoints = improvementIntersection(ray, max, improvementCheckIntersection);
        List<GeoPoint> interPlane = _scene.getGeometries().findGeoIntersections(ray, max);


        if (intersectionPoints == null && interPlane == null) return null;


        GeoPoint minDistancePoint = null;
        double distanceMinPoint = Double.POSITIVE_INFINITY;

        if (intersectionPoints != null)
            for (GeoPoint gp : intersectionPoints) {
                double distancePoint = gp.point.distance(pt);
                if (distancePoint < distanceMinPoint) {
                    minDistancePoint = gp;
                    distanceMinPoint = distancePoint;
                }
            }

        double distanceToLastPoint = interPlane.get(0).point.distance(pt);
        if (distanceToLastPoint < distanceMinPoint) {
            minDistancePoint = interPlane.get(0);
        }
        return minDistancePoint;

    }

    private GeoPoint findClosestIntersectionOriginal(Ray ray, double max) {
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

        return reflectedRay;
    }

    public Ray constructRefractedRay(Vector n, GeoPoint geoPoint, Ray ray) {
        return new Ray(geoPoint.point, ray.getDirection(), n);
    }

    /**
     * this function checks if the
     *
     * @param lightSource
     * @param l
     * @param n
     * @param geoPoint
     * @param softShadow
     * @return
     */
    private double transparency(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint, boolean softShadow, boolean improvementCheckIntersection) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);// from point to light source

        if (lightSource instanceof DirectionalLight || softShadow == DEACTIVATE) {

            double lightDistance = lightSource.getDistance(geoPoint.point);
            /**
             * * * * * * * * * * * * * * * * * * * * Remplace line* * * * * * * * * * * * * * * * * * * * * * * * * * *
             */
            List<GeoPoint> intersections = improvementIntersection(lightRay, lightDistance, improvementCheckIntersection);
            //List<GeoPoint> intersections = _scene.getGeometries().findGeoIntersections(lightRay, lightDistance);
            if (intersections == null)
                return 1.0;

            double ktr = 1.0;
            for (GeoPoint gpt : intersections) {
                ktr *= gpt.geometry.getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K)
                    return 0.0;
            }
            return ktr;

        } else {
            // get list of several rays
            ArrayList<Ray> listRay = lightRay.getListRays(lightSource.getBulb().getCenter(), (int) lightSource.getBulb().getRadius());

            double lightDistance = lightSource.getDistance(geoPoint.point);

            double ktr = 1.0;
            double sumKtr = 0;//sum ktr of all intersection points for all rays
            boolean flagIntersection = false;

            for (Ray r : listRay) {
                /**
                 * * * * * * * * * * * * * * * * * * * * Remplace line* * * * * * * * * * * * * * * * * * * * * * * * * * *
                 */
                //List<GeoPoint> intersecOneRay = _scene.getGeometries().findGeoIntersections(r, lightDistance);
                List<GeoPoint> intersecOneRay = improvementIntersection(r, lightDistance, improvementCheckIntersection);
                // if the ray 'r' don't crosses any geometries, it's like it crosses geometries transparent
                if (intersecOneRay == null) ktr = 1.0;
                else {
                    flagIntersection = true;//there is at least one intersection point
                    // calculate an accumulation of ktr for all geometries crossed by the ray 'r'
                    for (GeoPoint gpt : intersecOneRay) {
                        ktr *= gpt.geometry.getMaterial().getKt();
                    }
                }

                sumKtr += ktr;
                ktr = 1.0;
            }

            if (flagIntersection == false)//if there aren't any intersection
                return 1.0;

            int numRay = listRay.size();
            double ktrAverage = sumKtr / numRay;

            return ktrAverage;
        }
    }

/*    private double transparency3(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint, boolean flag) {
        if (lightSource instanceof DirectionalLight || flag == DEACTIVATE)
            return transparency(lightSource, l, n, geoPoint);
        else return transparency2(lightSource, l, n, geoPoint);
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
            ktr *= gpt.geometry.getMaterial().getKt();
            if (ktr < MIN_CALC_COLOR_K)
                return 0.0;
        }
        return ktr;
    }

    private double transparency2(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        // get list of several rays
        ArrayList<Ray> listRay = lightRay.getListRays(lightSource.getBulb().getCenter(), (int) lightSource.getBulb().getRadius());

        double lightDistance = lightSource.getDistance(geoPoint.point);

        double ktr = 1.0;
        double sumKtr = 0;//sum ktr of all intersection points for all rays
        boolean flagIntersection = false;

        for (Ray r : listRay) {
            List<GeoPoint> intersecOneRay = _scene.getGeometries().findGeoIntersections(r, lightDistance);
            // if the ray 'r' don't crosses any geometries, it's like it crosses geometries transparent
            if (intersecOneRay == null) ktr = 1.0;
            else {
                flagIntersection = true;//there is at least one intersection point
                // calculate an accumulation of ktr for all geometries crossed by the ray 'r'
                for (GeoPoint gpt : intersecOneRay) {
                    ktr *= gpt.geometry.getMaterial().getKt();
                }
            }

            sumKtr += ktr;
            ktr = 1.0;
        }

        if (flagIntersection == false)//if there aren't any intersection
            return 1.0;

        int numRay = listRay.size();
        double ktrAverage = sumKtr / numRay;

        return ktrAverage;
    }*/

    /**
     * Displays a grid with fixed squares size
     * @param interval interval
     * @param color color
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


    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            _threads = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }
}

