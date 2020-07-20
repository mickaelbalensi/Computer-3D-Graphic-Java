package geometries;

import primitives.Ray;
/**
 *The cylinder class represents the two-dimensional cylinder in the
 * 3D Cartesian coordinate system.it uses primitive classes and objects.
 */
public class Cylinder extends Tube {
    protected double height;



  public double getHeight() {
        return height;
    }

    /**
     * Constructor who takes tube doubles, Color and material
     * @param ray ray
     * @param radius radisu
     * @param height height
     */
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