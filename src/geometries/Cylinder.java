package geometries;

import primitives.Ray;

public class Cylinder extends Tube {
    protected double height;



  public double getHeight() {
        return height;
    }

    public Cylinder(Tube tube, double height){
        super(tube);
        this.height=height;
    }

    public Cylinder(Ray ray, double radius, double height){
        super(ray,radius);
        this.height=height;
    }


    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", _axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }
}