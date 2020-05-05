package renderer;

import scene.Scene;
import primitives.*;

import java.util.List;

public class Render {
 private ImageWriter image;
 private Scene _scene;

    public Render(ImageWriter imageWriter, Scene scene) {
    }


    public void  renderImage(){ }
    private Color calcColor(Point3D point) {
        return _scene.ambientLight.getIntensity();
    }

    public void getClosestPoint(List<Point3D> points){}
    public void printGrid(int interval, java.awt.Color color){}
    public void writeToImage() {
    }
}




