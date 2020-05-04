package geometries;

import primitives.Ray;

public class Cylinder extends Tube {
    protected double height;



  public double getHeight() {
        return height;
    }

    /**
     *
     * @param tube
     * @param height
     */
    public Cylinder(Tube tube, double height){
        super(tube);
        this.height=height;
    }

    /**
     *
     * @param ray
     * @param radius
     * @param height
     */
    public Cylinder(Ray ray, double radius, double height){
        super(ray,radius);
        this.height=height;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", _axisRay=" + _axisRay +
                ", _radius=" + _radius +
                '}';
    }
}