package scene;

import elements.*;
import geometries.*;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The class scene represents all of tools to take a picture
 * by a camera, a view Plane, light source, background of the screen
 * and many shapes for the image
 */
public class Scene {
    String name;
    Color background;
    AmbientLight ambientLight;
    Geometries geometries;
    Camera camera;
    double distance;

    public Scene(String name){
        this.name=name;
        this.geometries=new Geometries();
    }

    //region getters/setters

    /**
     * get the name of the scene
     * @return the name
     */
    public String getName(){
        return name;
    }

    /**
     * get the color of the background
     * @return the color of the background
     */
    public Color getBackground(){
        return background;
    }

    /**
     * get the ambientLight
     * @return the ambientLight
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     * get all of shapes of the image
     * @return the list of geometries in the picture
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     * get the camera
     * @return the camera
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * get the distance between the camera and the screen
     * @return the distance between the camera and the screen
     */
    public double getDistance() {
        return distance;
    }

    /**
     * set the name of the scene
     * @param name type string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set the color of the background of the screen
     * @param background type Color
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    /**
     * set the light of the scene
     * @param ambientLight type AmbientLight
     */
    public void setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }

    /**
     * set the List of shapes to represent the picture
     * @param geometries type Geometries
     */
    public void setGeometries(Geometries geometries) {
        this.geometries = geometries;
    }

    /**
     * set the camera of the scene
     * @param camera type Camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * set the distance between the camera and he screen of the picture
     * @param distance type double
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    //endregion

    /**
     * add many geometries in the the gourp of geometries of the picture
     * @param geometries type Intersectable
     */
    void addGeometries(Intersectable ... geometries){
        for(int i=0;i<geometries.length;i++)
            this.geometries.add(geometries[i]);
    }

}
