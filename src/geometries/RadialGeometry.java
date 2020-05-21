package geometries;

import primitives.*;
public abstract class RadialGeometry extends Geometry { // inherits from Geometry
    protected double _radius;

    //region CTORs

    /**
     * the geometries.RadialGeometry constructor receiving the radius of the geometry
     * @param _radius of type double
     */
    public RadialGeometry(double _radius,Color color){
        super(color);
        this._radius=_radius;
    }
    public RadialGeometry(double _radius){
        this._radius=_radius;
    }
    /**
     * the geometries.RadialGeometry copy-constructor receiving the radius of the geometry
     * @param geo by type RadialGeometry
     */
    public RadialGeometry(RadialGeometry geo){
        this._radius=geo._radius;
    }
    //endregion

    /**
     * the getter of the radius of the geometry
     * @return radius
     */
    public double get_radius() {
        return _radius;
    }


}
