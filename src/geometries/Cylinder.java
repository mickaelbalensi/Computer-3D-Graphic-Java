package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Ray;

public class Cylinder extends Tube {
    protected double height;


    //region CTORs
    /**
     * Constructor who takes tube doubles, Color and material
     * @param ray
     * @param radius
     * @param height
     * @param color
     * @param material
     */
    public Cylinder(Ray ray, double radius, double height,Color color,Material material){
        super(ray,radius,color,material);
        this.height=height;
    }

    /**
     * Constructor who takes tube doubles and Color
     * @param ray (Ray)
     * @param radius (double)
     * @param height (double)
     * @param color (Color)
     */
    public Cylinder(Ray ray, double radius, double height,Color color){
        super(ray,radius,color);
        this.height=height;
    }

    /**
     * CTOR without color
     * @param ray
     * @param radius
     * @param height
     */
    public Cylinder(Ray ray, double radius, double height){
        super(ray,radius);
        this.height=height;
    }

    /**
     * Constructor who takes tube double and Color and it's Material
     * @param tube (Tube)
     * @param height (double)
     */
    public Cylinder(Tube tube, double height){//, Color color, Material material){
        super(tube);
        this.height=height;
    }

    //endregion

    /**
     * the getter of the height
     * @return height
     */
    public double getHeight() {
        return height;
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