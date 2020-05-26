package scene;

import elements.*;
import geometries.*;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * The class scene represents all of tools to take a picture
 * by a camera, a view Plane, light source, background of the screen
 * and many shapes for the image
 */
public class Scene {
    String _name;
    Color _background;
    public AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double _distance;
    List<LightSource> _lights;

    /**
     * Scene.Constructor receiving name of the scene
     * @param _name
     */
    public Scene(String _name){
        this._name = _name;
        this._geometries =new Geometries();
        this._lights= new LinkedList<LightSource>();
    }

    //region getters/setters

    /**
     * get the name of the scene
     * @return the name
     */
    public String getName(){
        return _name;
    }

    /**
     * get the color of the background
     * @return the color of the background
     */
    public Color getBackground(){
        return _background;
    }

    /**
     * get the ambientLight
     * @return the ambientLight
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * get all of shapes of the image
     * @return the list of geometries in the picture
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * get the camera
     * @return the camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * get the distance between the camera and the screen
     * @return the distance between the camera and the screen
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * get all light of the scene
     * @return list of lights
     */
    public List<LightSource> getLights() {
        return _lights;
    }
    /**
     * set the name of the scene
     * @param _name type string
     */
    public void setName(String _name) {
        this._name = _name;
    }

    /**
     * set the color of the background of the screen
     * @param _background type Color
     */
    public void setBackground(Color _background) {
        this._background = _background;
    }

    /**
     * set the light of the scene
     * @param _ambientLight type AmbientLight
     */
    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * set the List of shapes to represent the picture
     * @param _geometries type Geometries
     */
    public void setGeometries(Geometries _geometries) {
        this._geometries = _geometries;
    }

    /**
     * set the camera of the scene
     * @param _camera type Camera
     */
    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * set the distance between the camera and he screen of the picture
     * @param _distance type double
     */
    public void setDistance(double _distance) {
        this._distance = _distance;
    }





    //endregion

    /**
     * add many geometries in the the group of geometries of the picture
     * @param geometries type Intersectable
     */
    public void addGeometries(Intersectable... geometries){
        for(int i=0;i<geometries.length;i++)
            this._geometries.add(geometries[i]);
    }

    /**
     * add many lights in the group of lights of the scene
     * @param lights
     */
    public void addLights(LightSource... lights) {
        for(int i=0;i<lights.length;i++)
            this._lights.add(lights[i]);
    }

}
