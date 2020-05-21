package geometries;

import primitives.*;
public abstract class RadialGeometry extends Geometry { // inherits from Geometry
    protected double _radius;

    //region CTORs

    /**
     * the geometries.RadialGeometry constructor receiving the radius of the geometry, it's color and the material
     * @param _radius of type double
     * @param color (Color)
     * @param material (Material)
     */
    public RadialGeometry(double _radius,Color color,Material material){
        super(color,material);
        this._radius=_radius;
    }

    /**
     * the geometries.RadialGeometry constructor receiving the radius of the geometry and it's color
     * @param radius of type double
     * @param color of type Color
     */
    public RadialGeometry(double radius,Color color){
        super(color);
        setRadius(radius);
    }

    /**
     * the geometries.RadialGeometry constructor receiving the radius of the geometry
     * @param radius (double)
     */
    public RadialGeometry(double radius){
        super();
        setRadius(radius);
    }

    /**
     * the geometries.RadialGeometry copy-constructor receiving the radius of the geometry
     * @param geo by type RadialGeometry
     */
    public RadialGeometry(RadialGeometry geo){
        super(geo._emission,geo._material);
        setRadius(geo._radius);
    }
    //endregion

    /**
     * the getter of the radius of the geometry
     * @return radius
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * setter of radius
     * @param _radius
     */
    public void setRadius(double _radius) {
        this._radius = _radius;
    }
}
