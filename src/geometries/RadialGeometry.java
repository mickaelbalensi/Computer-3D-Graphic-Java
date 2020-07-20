package geometries;

import primitives.*;

/**
 * Class for DRY for radius used in Sphere and Tube
 */
public abstract class RadialGeometry implements Geometry { // inherits from Geometry
    protected double _radius;

    //region CTORs

    /**
     * the geometries.RadialGeometry constructor receiving the radius of the geometry
     * @param _radius (double)
     */
    public RadialGeometry(double _radius){
        this._radius=_radius;
    }

    /**
     * Copy Ctor recieving an other radial Geometry
     * @param geo
     */
    public RadialGeometry(RadialGeometry geo){
        this._radius=geo._radius;
    }
    //endregion

    /**
     * getter of the radius
     * @return radius
     */
    public double get_radius() {
        return _radius;
    }


}
