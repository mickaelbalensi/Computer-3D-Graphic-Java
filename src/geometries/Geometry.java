package geometries;


import primitives.*;

/**
 * this class geometries.Geometry represents all of geometric shapes
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission;
    protected Material _material;

    //region CTORs

    /**
     * geometries.Geometry Constructor receiving the color and material
     *
     * @param emission color value
     * @param material
     */
    public Geometry(Color emission, Material material) {
        this._emission = emission;
        this._material = material;
    }

    /**
     * geometries.Geometry Constructor receiving the color
     *
     * @param emission color value
     */
    public Geometry(Color emission) {
        this(emission, new Material(0, 0, 0));
    }

    /**
     * the Default Constructor
     */
    public Geometry() {
        this(Color.BLACK);
    }
    //endregion

    /**
     * @return the Color emission of the Geometry
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * the geometries.Geometry getMormal function calculate the Normal Vector in
     * specific Point on the Geometry
     *
     * @param pt by type Point
     * @return the normal vector
     */
    public abstract Vector getNormal(Point3D pt);

    /**
     * @return the Material of the Geometry
     */
    public Material getMaterial() {
        return _material;
    }
}
