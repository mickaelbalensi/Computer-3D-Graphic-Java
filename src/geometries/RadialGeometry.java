package geometries;

import primitives.*;
public abstract class RadialGeometry extends Geometry { // inherits from Geometry
    protected double _radius;

    //region CTORs

    /**
     * Constructor who takes double and Color
     * @param _radius (double)
     * @param color (Color)
     */
    public RadialGeometry(double _radius,Color color){
        super(color);
        this._radius=_radius;
    }
    public RadialGeometry(double _radius){
        this._radius=_radius;
    }
    /**
     *
     * @param geo
     */
    public RadialGeometry(RadialGeometry geo){
        this._radius=geo._radius;
    }
    //endregion

    /**
     *
     * @return radius
     */
    public double get_radius() {
        return _radius;
    }


}
