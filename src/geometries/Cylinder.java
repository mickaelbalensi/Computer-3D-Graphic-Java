package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Ray;

/**
 *The cylinder class represents the two-dimensional cylinder in the
 * 3D Cartesian coordinate system.it uses primitive classes and objects.
 */
public class Cylinder extends Tube {
    protected double height;


    //region CTORs

    /**
     * Constructor who takes tube doubles, Color and material
     * @param ray cylinder's ray
     * @param radius cylinder's radius
     * @param height cylinder's height
     * @param color cylinder's color
     * @param material cylinder's material
     */
    public Cylinder(Ray ray, double radius, double height, Color color, Material material) {
        super(ray, radius, color, material);
        this.height = height;
    }

    /**
     * Constructor who takes tube doubles and Color
     *
     * @param ray    (Ray)
     * @param radius (double)
     * @param height (double)
     * @param color  (Color)
     */
    public Cylinder(Ray ray, double radius, double height, Color color) {
        this(ray, radius, height, color, Material.DEFAULT);
    }

    /**
     * CTOR without color
     * @param ray r
     * @param height height
     * @param  radius cylinder's radius
     */
    public Cylinder(Ray ray, double radius, double height) {
        this(ray, radius, height, Color.BLACK);
    }

    //endregion

    /**
     * the getter of the height
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }


    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", _axisRay=" + axisRay +
                ", _radius=" + radius +
                '}';
    }
}