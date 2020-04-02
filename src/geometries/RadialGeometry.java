package geometries;

import primitives.*;
public abstract class RadialGeometry implements Geometry { // inherits from Geometry
    protected double _radius;

    //region CTORs
    public RadialGeometry(double _radius){
        this._radius=_radius;
    }

    public RadialGeometry(RadialGeometry geo){
        this._radius=geo._radius;
    }
    //endregion

    public double get_radius() {
        return _radius;
    }


}
