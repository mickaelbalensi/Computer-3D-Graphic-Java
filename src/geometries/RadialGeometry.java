package geometries;

import primitives.*;
/**
 * DRY to create radius both fors sphere and tube
 */
public abstract class RadialGeometry implements Geometry { // inherits from Geometry
    protected double _radius;

    //region CTORs

    /**
     * ctor radialgeometry
     * @param _radius radius
     */
    public RadialGeometry(double _radius){
        this._radius=_radius;
    }

    public RadialGeometry(RadialGeometry geo){
        this._radius=geo._radius;
    }
    //endregion

    /**
     * getter od radius
     * @return radius
     */
    public double get_radius() {
        return _radius;
    }


}
