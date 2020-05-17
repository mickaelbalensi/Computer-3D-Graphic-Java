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
    public void  renderImage(){
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();
        int nX = _imageWriter.getNx();
        int nY=_imageWriter. getNy();
        double width=_imageWriter.getWidth();
        double height=_imageWriter.getHeight();
        double distance =_scene.getDistance();
        for (int h=0;h<height*100;h++)
        {
            for (int w=0;w<width*100;w++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, w, h, distance, width, height);
                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null)
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
    public Point3D getClosestPoint(List<Point3D> points)
    {
        return null;
    }

    /**
     * Displays a grid with fixed squares size
     */
    public void printGrid(int interval, java.awt.Color color){}

    public void writeToImage() {
    }
}




