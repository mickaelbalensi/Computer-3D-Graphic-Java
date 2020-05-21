package geometries;


import primitives.*;

/**
 * this class geometries.Geometry represents all of geometric shapes
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission;

    /**
     * geometries.Geometry Default Constructor put color Black for emission
     */
    public Geometry(){
        this._emission=Color.BLACK;
    }

    /**
     * geometries.Geometry Constructor receiving the color
     * @param emission color value
     */
    public Geometry(Color emission){
        this._emission=emission;
    }

    /**
     * @return the Color emission of the Geometry
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * the geometries.Geometry getMormal function calculate the Normal Vector in
     * specific Point on the Geometry
     * @param pt by type Point
     * @return the normal vector
     */
    public abstract Vector getNormal(Point3D pt);


}
