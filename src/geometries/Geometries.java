package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;

public class Geometries implements Intersectable {
    private List<Intersectable> shapes;

    public Geometries() {
        this.shapes = new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable ... geometries){
        this.shapes = new ArrayList<>();
        for (int i=0;i< geometries.length;i++){
            this.shapes.add(geometries[i]);
        }
    }

    public Geometries(Geometries geo){
        this.shapes = new ArrayList<Intersectable>(geo.shapes);
    }

    public void add(Intersectable ... geometries){
        for (int i=0;i< geometries.length;i++){
            this.shapes.add(geometries[i]);
        }
    }

    /***
     *
     * @param ray ray pointing toward a Geometry
     * @return all the intersections points between our rays and our shapes
     */
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
