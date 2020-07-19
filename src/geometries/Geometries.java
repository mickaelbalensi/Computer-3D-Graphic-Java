package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.*;

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

    public Geometries() {
        this.shapes = new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable... geometries) {
        this();
        add(geometries);
    }

/*
    public void add(Intersectable... geometries) {

        for (int i = 0; i < geometries.length; i++) {
            this.shapes.add(geometries[i]);
        }

        double XminGeo = geometries[0].getXmin();
        double YminGeo = geometries[0].getYmin();
        double ZminGeo = geometries[0].getZmin();
        double XmaxGeo = geometries[0].getXmax();
        double YmaxGeo = geometries[0].getYmax();
        double ZmaxGeo = geometries[0].getZmax();

        for (Intersectable i : geometries) {

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
*/

    public void add(Intersectable... geometries) {
        List listGeometries=new ArrayList();

        for (int i = 0; i < geometries.length; i++) {
            listGeometries.add(geometries[i]);
        }
        addList(listGeometries);
    }

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
    public double getXmin() {
        return Xmin;
    }

    public double getYmin() {
        return Ymin;
    }

    public double getZmin() {
        return Zmin;
    }

    public double getXmax() {
        return Xmax;
    }

    public double getYmax() {
        return Ymax;
    }

    public double getZmax() {
        return Zmax;
    }
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
