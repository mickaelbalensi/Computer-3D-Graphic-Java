package scene;


import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import elements.*;
import geometries.*;
import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The class scene represents all of tools to take a picture
 * by a camera, a view Plane, light source, background of the screen
 * and many shapes for the image
 */
public class Scene {

    private String name;
    private Color background;
    public AmbientLight ambientLight;
    private Geometries geometries;
    private Camera camera;
    private double distance;
    private List<LightSource> lights;
    private List<Box> boxes;
    private Node<Geometries> geometriesTree;
    /**
     * @author w w w. j a v a g i s t s . c o m
     */
    public class Node<T> extends Geometries{

        private T data = null;
        private List<Node<T>> children = new ArrayList<>();
        private Node<T> parent = null;

        public Node(T data) {
            this.data = data;
        }

        public Node<T> addChild(Node<T> child) {
            child.setParent(this);
            this.children.add(child);
            return child;
        }

        public void addChildren(List<Node<T>> children) {
            children.forEach(each -> each.setParent(this));
            this.children.addAll(children);
        }

        public List<Node<T>> getChildren() {
            return children;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        private void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public Node<T> getParent() {
            return parent;
        }

    }




    /**
     * Scene.Constructor receiving name of the scene
     *
     * @param name
     */
    public Scene(String name) {
        this.name = name;
        this.geometries = new Geometries();
        this.lights = new LinkedList<LightSource>();
        geometriesTree = new Node<Geometries>(new Box());
    }

    //region getters/setters

    /**
     * get the name of the scene
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * get the color of the background
     *
     * @return the color of the background
     */
    public Color getBackground() {
        return background;
    }

    /**
     * get the ambientLight
     *
     * @return the ambientLight
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     * get all of shapes of the image
     *
     * @return the list of geometries in the picture
     */
    public Geometries getGeometries() {
        return geometries;
    }


    /**
     * get the camera
     *
     * @return the camera
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * get the distance between the camera and the screen
     *
     * @return the distance between the camera and the screen
     */
    public double getDistance() {
        return distance;
    }

    /**
     * get all light of the scene
     *
     * @return list of lights
     */
    public List<LightSource> getLights() {
        return lights;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public Node<Geometries> getGeometriesTree(){
        return geometriesTree;
    }



    /**
     * set the name of the scene
     *
     * @param _name type string
     */
    public void setName(String _name) {
        this.name = _name;
    }

    /**
     * set the color of the background of the screen
     *
     * @param _background type Color
     */
    public void setBackground(Color _background) {
        this.background = _background;
    }

    /**
     * set the light of the scene
     *
     * @param _ambientLight type AmbientLight
     */
    public void setAmbientLight(AmbientLight _ambientLight) {
        this.ambientLight = _ambientLight;
    }

    /**
     * set the List of shapes to represent the picture
     *
     * @param _geometries type Geometries
     */
    public void setGeometries(Geometries _geometries) {
        this.geometries = _geometries;
    }

    /**
     * set the camera of the scene
     *
     * @param _camera type Camera
     */
    public void setCamera(Camera _camera) {
        this.camera = _camera;
    }

    /**
     * set the distance between the camera and he screen of the picture
     *
     * @param _distance type double
     */
    public void setDistance(double _distance) {
        this.distance = _distance;
    }


    //endregion

    /**
     * add many geometries in the the group of geometries of the picture
     *
     * @param geometries type Intersectable
     */
    public void addGeometries(Intersectable... geometries) {
        this.geometries.add(geometries);
/*        for(int i=0;i<geometries.length;i++)
            this.geometries.add(geometries[i]);*/
    }

    /**
     * add an many ArrayList of many geometries in the the group of geometries of the picture
     *
     * @param arrayGeo type ArrayList<Intersectable>...
     */
    public void addGeometries(ArrayList<Intersectable>... arrayGeo) {
        for (int i = 0; i < arrayGeo.length; i++)
            for (Intersectable geo : arrayGeo[i])
                this.geometries.add(geo);
    }

   /* public void addMultiGeometries(SeveralGeometries severalGeometries){
        this.geometries.addList(severalGeometries.getList());
    }*/

    public void addBox(Box... box) {
        for (int i = 0; i < box.length; i++)
            this.boxes.add(box[i]);
    }

    public void addGroupGeometries(Geometries... geometriesParam) {
        Box rootBox=new Box();//box containing all geometries in parameters
        rootBox.addGeometries(geometriesParam);//calculate the size of the box

        Node<Geometries> root = new Node<>(rootBox);

        for(Geometries g : geometriesParam){
            root.addChild(new Node<Geometries>(new Box(g)));
        }

        int index=0;
        for (Node<Geometries> n : root.getChildren()){
            n.addChild(new Node(geometriesParam[index]));
            index++;
        }

        geometriesTree= root;

    }


    /**
     * add many lights in the group of lights of the scene
     *
     * @param lights
     */
    public void addLights(LightSource... lights) {
        for (int i = 0; i < lights.length; i++)
            this.lights.add(lights[i]);
    }

}
