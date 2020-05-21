package geometries;

import primitives.Color;
import primitives.Ray;

public class Cylinder extends Tube {
    protected double height;



  public double getHeight() {
        return height;
    }

    /**
     * Constructor who takes tube double and Color
     * @param tube (Tube)
     * @param height (double)
     * @param color (Color)
     */
    public Cylinder(Tube tube, double height, Color color){
        super(tube,color);
        this.height=height;
    }

    /**
     * Constructor who takes tube doubles and Color
     * @param ray
     * @param radius
     * @param height
     * @param color
     */
    public Cylinder(Ray ray, double radius, double height,Color color){
        super(ray,radius,color);
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