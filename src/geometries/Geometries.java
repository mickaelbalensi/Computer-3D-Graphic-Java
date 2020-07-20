package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;

/**
 *this class gathers all shapes of finale picture it's a frame of our shapes in order to minimise running time
 */
public class Geometries implements Intersectable {
    protected List<Intersectable> shapes;
    protected double Xmin; // the X-value minimum of the Geometry
    protected double Ymin; // the Y-value minimum of the Geometry
    protected double Zmin; // the Z-value minimum of the Geometry
    protected double Xmax; // the X-value maximum of the Geometry
    protected double Ymax; // the Y-value maximum of the Geometry
    protected double Zmax; // the Z-value maximum of the Geometry
    protected static double MAX = 100000;
    protected static double MIN = -100000;

    /**
     * create a list who will contain shapes
     */
    public Geometries() {
        this.shapes = new ArrayList<Intersectable>();
    }

    /**
     * ctor
     * @param geometries geometries
     */
    public Geometries(Intersectable... geometries) {
        this();
        add(geometries);
    }


    /**
     * add shapes to our list
     * @param geometries shapes
     */
    public void add(Intersectable... geometries) {
        List listGeometries=new ArrayList();

        for (int i = 0; i < geometries.length; i++) {
            listGeometries.add(geometries[i]);
        }
        addList(listGeometries);
    }

    /**
     * framing shapes
     * @param listGeometries list of shapes
     */
    public void addList(List listGeometries) {
        this.shapes.addAll(listGeometries);

        //calculate the min and max of coordinates X,Y,Z of Geometries
        double XminGeo = shapes.get(0).getXmin();
        double YminGeo = shapes.get(0).getYmin();
        double ZminGeo = shapes.get(0).getZmin();
        double XmaxGeo = shapes.get(0).getXmax();
        double YmaxGeo = shapes.get(0).getYmax();
        double ZmaxGeo = shapes.get(0).getZmax();

        for (Intersectable i : shapes) {

            if (XminGeo > i.getXmin()) XminGeo = i.getXmin();
            if (YminGeo > i.getYmin()) YminGeo = i.getYmin();
            if (ZminGeo > i.getZmin()) ZminGeo = i.getZmin();

            if (XmaxGeo < i.getXmax()) XmaxGeo = i.getXmax();
            if (YmaxGeo < i.getYmax()) YmaxGeo = i.getYmax();
            if (ZmaxGeo < i.getZmax()) ZmaxGeo = i.getZmax();
        }
        this.Xmin = XminGeo;
        this.Xmax = XmaxGeo;
        this.Ymin = YminGeo;
        this.Ymax = YmaxGeo;
        this.Zmin = ZminGeo;
        this.Zmax = ZmaxGeo;

    }

    //region getters

    /**
     *getter of x min
     * @return Xmin
     */
    public double getXmin() {
        return Xmin;
    }

    /**
     * getter of y min
     * @return Ymin
     */
    public double getYmin() {
        return Ymin;
    }

    /**
     * getter of z min
     * @return z
     */
    public double getZmin() {
        return Zmin;
    }

    /**
     * getter of x max
     * @return x max
     */
    public double getXmax() {
        return Xmax;
    }

    /**
     * getter of y max
     * @return ymax
     */
    public double getYmax() {
        return Ymax;
    }

    /**
     * getter of z max
     * @return Zmax
     */
    public double getZmax() {
        return Zmax;
    }

    /**
     * list of shapes
     * @return list of shapes
     */
    public List<Intersectable> getGeometries(){
        return shapes;
    }
    //endregion

    /***
     *
     * @param ray ray pointing toward a Geometry
     * @return all the intersections points between our rays and our shapes
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
        List<GeoPoint> intersectionPointsList = null;
        for (Intersectable geo : shapes) {
            List<GeoPoint> list = geo.findGeoIntersections(ray, max);
            if (list != null) {
                if (intersectionPointsList == null) intersectionPointsList = new LinkedList<>();
                intersectionPointsList.addAll(list);
            }
        }
        return intersectionPointsList;
    }

    @Override
    public String toString() {
        return "Geometries{" +
                "shapes=" + shapes +
                ", Xmin=" + Xmin +
                ", Ymin=" + Ymin +
                ", Zmin=" + Zmin +
                ", Xmax=" + Xmax +
                ", Ymax=" + Ymax +
                ", Zmax=" + Zmax +
                '}';
    }
}
