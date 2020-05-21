package renderer;

import elements.Camera;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
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
        for (int h=0;h<nX;h++)
        {
            for (int w=0;w<nY;w++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, h, w, distance, width, height);
                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints.isEmpty())
                    _imageWriter.writePixel(w, h, background);
                else
                    {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(w, h, calcColor(closestPoint).getColor());
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
        Color color = _scene.ambientLight.getIntensity();
        color = color.add(intersection.geometry.getEmission());
        return color;
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

    /**
     * Function writeToImage produces unoptimized jpeg file of
     * the image according to pixel color matrix in the directory
     * of the project
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}




