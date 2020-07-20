package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;
/**
 *this class gathers all shapes of finale picture it's a frame of our shapes in order to minimise running time
 */
public class Geometries implements Intersectable {
    private List<Intersectable> shapes;

    /**
     * create a list who will contain shapes
     */
    public Geometries() {
        this.shapes = new ArrayList<Intersectable>();
    }

    /**
     * ctor
     * @param geometries geometries
     */
    public Geometries(Intersectable ... geometries){
        this.shapes = new ArrayList<>();
        for (int i=0;i< geometries.length;i++){
            this.shapes.add(geometries[i]);
        }
    }

    /**
     * Copy Ctor
     * @param geo
     */
    public Geometries(Geometries geo){
        this.shapes = new ArrayList<Intersectable>(geo.shapes);
    }

    /**
     * add shapes to our list
     * @param geometries shapes
     */
    public void add(Intersectable ... geometries){
        for (int i=0;i< geometries.length;i++){
            this.shapes.add(geometries[i]);
        }
    }


    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> intersectionPointsList= new ArrayList<>();
        for(int i=0;i<shapes.size();i++){
            List<Point3D> list=shapes.get(i).findIntersections(ray);
            if(list!=null)
                for(int j=0;j<list.size();j++){
                intersectionPointsList.add(list.get(j));
                }
        }

        return intersectionPointsList;
    }
}
