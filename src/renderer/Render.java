package renderer;

import elements.Camera;
import geometries.Intersectable;
import scene.Scene;
import primitives.*;

import java.util.List;

/**
 * A class representing a renderer
 * Can render images based on a scene
 */
public class Render {
 private ImageWriter _imageWriter;
 private Scene _scene;

    /**
     * Only constructor
     * @param imageWriter (ImageWriter)
     * @param scene (Scene) Contains geometries and lighting info
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }
    public void renderImage(){
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY=_imageWriter. getNy();
        double width=_imageWriter.getWidth();
        double height=_imageWriter.getHeight();
        double distance =_scene.getDistance();
        for (int h=0;h<nX;h++)
        {
            for (int w=0;w<nY;w++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, h, w, distance, width, height);
                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints.isEmpty())
                    _imageWriter.writePixel(w, h, background);
                else
                    {
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(w, h, calcColor(closestPoint).getColor());
                }
            }
        }
    }
    /**
     * A function returning the color at a point
     */
    private Color calcColor(Point3D point) {
        return _scene.ambientLight.getIntensity();
    }

    /**
     * Returns the closest point to the camera from a list
     */
    private Point3D getClosestPoint(List<Point3D> intersectionPoints) {
        double distance = Double.MIN_VALUE;
        Point3D P0= _scene.getCamera().getP0();
        Point3D minDistancePoint=null;

        for (Point3D point : intersectionPoints){
            if(P0.distance(point)<distance)
                minDistancePoint=new Point3D(point);
            distance =P0.distance(point);
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

    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}




