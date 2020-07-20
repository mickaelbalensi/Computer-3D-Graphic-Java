package geometries;

import primitives.Ray;
/**
 *The cylinder class represents the two-dimensional cylinder in the
 * 3D Cartesian coordinate system.it uses primitive classes and objects.
 */
public class Cylinder extends Tube {
    protected double height;

    /**
     * getter of height
     * @return height
     */
  public double getHeight() {
        return height;
    }

    /**
     * Ctor from
     * @param tube tube
     * @param height double
     */
    public Cylinder(Tube tube, double height){
        super(tube);
        this.height=height;
    }

    /**
     * Second Ctor
     * @param ray ray
     * @param radius double
     * @param height double
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