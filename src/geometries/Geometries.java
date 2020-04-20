package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable {
    private List<Intersectable> forms;

    public Geometries() {
        this.forms = new ArrayList<>();
    }

    public Geometries(Intersectable ... geometries){
        this.forms = new ArrayList<>();
        for (int i=0;i< geometries.length;i++){
            this.forms.add(geometries[i]);
        }
    }

    public void add(Intersectable ... geometries){
        for (int i=0;i< geometries.length;i++){
            this.forms.add(geometries[i]);
        }
    }
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
