package renderer;

import scene.Scene;
import primitives.*;

import java.util.List;

/**
 * A class representing a renderer
 * Can render images based on a scene
 */
public class Render {
 private ImageWriter image;
 private Scene _scene;

    /**
     * Only constructor
     * @param imageWriter (ImageWriter)
     * @param scene (Scene) Contains geometries and lighting info
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this.image = imageWriter;
        this._scene = scene;
    }
    public void  renderImage(){ }
    /**
     * A function returning the color at a point
     */
    private Color calcColor(Point3D point) {
        return _scene.ambientLight.getIntensity();
    }

    /**
     * Returns the closest point to the camera from a list
     */
    public void getClosestPoint(List<Point3D> points){}

    /**
     * Displays a grid with fixed squares size
     */
    public void printGrid(int interval, java.awt.Color color){}

    public void writeToImage() {
    }
}




