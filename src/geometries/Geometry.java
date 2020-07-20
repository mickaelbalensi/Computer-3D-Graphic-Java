package geometries;


import primitives.*;

/**
 * this class geometries.Geometry represents all of geometric shapes
 * this class is abstract,
 * Geometry implements the Intersectable's interface
 */
public abstract class Geometry implements Intersectable {
    protected Color emission; // the emission color of the Geometry
    protected Material material; // the material of the Geometry
    protected double Xmin; // the X-value minimum of the Geometry
    protected double Ymin; // the Y-value minimum of the Geometry
    protected double Zmin; // the Z-value minimum of the Geometry
    protected double Xmax; // the X-value maximum of the Geometry
    protected double Ymax; // the Y-value maximum of the Geometry
    protected double Zmax; // the Z-value maximum of the Geometry
    protected static double MAX = 100000;
    protected static double MIN = -100000;


    //region CTORs

    /**
     * geometries.Geometry Constructor receiving the color and material
     *
     * @param emission color value
     * @param material geometry's material
     */
    public Geometry(Color emission, Material material) {
        this.emission = emission;
        this.material = material;
    }

    /**
     * geometries.Geometry Constructor receiving the color
     *
     * @param emission color value
     */
    public Geometry(Color emission) {
        this(emission, Material.DEFAULT);
    }

    /**
     * the Default Constructor
     */
    public Geometry() {
        this(Color.BLACK);
    }
    //endregion

    //region getters
    public Color getEmission() {
        return emission;
    }
    public Material getMaterial() {
        return material;
    }
    @Override
    public double getXmin() {
        return Xmin;
    }
    @Override
    public double getYmin() {
        return Ymin;
    }
    @Override
    public double getZmin() {
        return Zmin;
    }
    @Override
    public double getXmax() {
        return Xmax;
    }
    @Override
    public double getYmax() {
        return Ymax;
    }
    @Override
    public double getZmax() {
        return Zmax;
    }
    //endregion

    /**
     * the geometries.Geometry getNormal function is the principal function of this class
     * it calculate the Normal Vector in specific Point (received in parameter) on the Geometry
     *
     * @param pt Point on Geometry
     * @return the normal vector
     */
    public abstract Vector getNormal(Point3D pt);



}
