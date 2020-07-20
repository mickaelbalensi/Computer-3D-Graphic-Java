package geometries;

import primitives.*;

/**
 * DRY to create radius both fors sphere and tube
 */
public abstract class RadialGeometry extends Geometry { // inherits from Geometry
    protected double radius;
    protected double radiusSqrd;

    //region CTORs

    /**
     * the geometries.RadialGeometry constructor receiving the radius of the geometry, it's color and the material
     * @param radius of type double
     * @param color (Color)
     * @param material (Material)
     */
    public RadialGeometry(double radius, Color color, Material material){
        super(color,material);
        this.radius = radius;
        this.radiusSqrd = radius * radius;
    }

    /**
     * the geometries.RadialGeometry constructor receiving the radius of the geometry and it's color
     * @param radius of type double
     * @param color of type Color
     */
    public RadialGeometry(double radius,Color color){
        this(radius, color, Material.DEFAULT);
    }

    /**
     * the geometries.RadialGeometry constructor receiving the radius of the geometry
     * @param radius (double)
     */
    public RadialGeometry(double radius){
        this(radius, Color.BLACK);
    }
    //endregion

    /**
     * the getter of the radius of the geometry
     * @return radius
     */
    public double getRadius() {
        return radius;
    }
}
