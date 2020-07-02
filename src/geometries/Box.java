package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Box extends Geometries{
    private List<Polygon> polygons;
    //private Geometries geometries;
    private double Xmin; // the X-value minimum of the Geometry
    private double Ymin; // the Y-value minimum of the Geometry
    private double Zmin; // the Z-value minimum of the Geometry
    private double Xmax; // the X-value maximum of the Geometry
    private double Ymax; // the Y-value maximum of the Geometry
    private double Zmax; // the Z-value maximum of the Geometry


    /*    public Box(double Xmin, double Xmax, double Ymin, double Ymax, double Zmin, double Zmax) {
        geometries=new Geometries();
        polygons.add(new Polygon(new Point3D(Xmin, Ymin, Zmin), new Point3D(Xmax, Ymin, Zmin), new Point3D(Xmin, Ymax, Zmin), new Point3D(Xmax, Ymax, Zmin)));
        polygons.add(new Polygon(new Point3D(Xmin, Ymin, Zmax), new Point3D(Xmax, Ymin, Zmax), new Point3D(Xmin, Ymax, Zmax), new Point3D(Xmax, Ymax, Zmax)));
        polygons.add(new Polygon(new Point3D(Xmax, Ymin, Zmin), new Point3D(Xmax, Ymin, Zmax), new Point3D(Xmax, Ymax, Zmin), new Point3D(Xmax, Ymax, Zmax)));
        polygons.add(new Polygon(new Point3D(Xmin, Ymin, Zmin), new Point3D(Xmin, Ymin, Zmax), new Point3D(Xmin, Ymax, Zmin), new Point3D(Xmin, Ymax, Zmax)));
        polygons.add(new Polygon(new Point3D(Xmin, Ymin, Zmin), new Point3D(Xmax, Ymin, Zmin), new Point3D(Xmin, Ymin, Zmax), new Point3D(Xmax, Ymin, Zmax)));
        polygons.add(new Polygon(new Point3D(Xmin, Ymax, Zmin), new Point3D(Xmax, Ymax, Zmin), new Point3D(Xmin, Ymax, Zmax), new Point3D(Xmax, Ymax, Zmax)));
}*/
    public Box() {
        Xmin = 0;
        Xmax = 0;
        Ymin = 0;
        Ymax = 0;
        Zmin = 0;
        Zmax = 0;
        polygons = new ArrayList();
    }

    public Box(Intersectable inter){
        setBox(inter.getXmin(), inter.getXmax(), inter.getYmin(), inter.getYmax(), inter.getZmin(), inter.getZmax());
    }

    /*public Box(Geometries geometries) {
        setBox(geometries.getXmin(), geometries.getXmax(), geometries.getYmin(), geometries.getYmax(), geometries.getZmin(), geometries.getZmax());

    }
    public Box(Geometry geometry) {
        setBox(geometry.getXmin(), geometry.getXmax(), geometry.getYmin(), geometry.getYmax(), geometry.getZmin(), geometry.getZmax());
    }*/

    public void setBox(double Xmin, double Xmax, double Ymin, double Ymax, double Zmin, double Zmax) {
        this.Xmin = Xmin;
        this.Xmax = Xmax;
        this.Ymin = Ymin;
        this.Ymax = Ymax;
        this.Zmin = Zmin;
        this.Zmax = Zmax;

        if(polygons==null)
            polygons=new ArrayList();
        else
            polygons.clear();
        polygons.add(new Polygon(new Point3D(Xmin, Ymin, Zmin), new Point3D(Xmax, Ymin, Zmin), new Point3D(Xmin, Ymax, Zmin), new Point3D(Xmax, Ymax, Zmin)));
        polygons.add(new Polygon(new Point3D(Xmin, Ymin, Zmax), new Point3D(Xmax, Ymin, Zmax), new Point3D(Xmin, Ymax, Zmax), new Point3D(Xmax, Ymax, Zmax)));
        polygons.add(new Polygon(new Point3D(Xmax, Ymin, Zmin), new Point3D(Xmax, Ymin, Zmax), new Point3D(Xmax, Ymax, Zmin), new Point3D(Xmax, Ymax, Zmax)));
        polygons.add(new Polygon(new Point3D(Xmin, Ymin, Zmin), new Point3D(Xmin, Ymin, Zmax), new Point3D(Xmin, Ymax, Zmin), new Point3D(Xmin, Ymax, Zmax)));
        polygons.add(new Polygon(new Point3D(Xmin, Ymin, Zmin), new Point3D(Xmax, Ymin, Zmin), new Point3D(Xmin, Ymin, Zmax), new Point3D(Xmax, Ymin, Zmax)));
        polygons.add(new Polygon(new Point3D(Xmin, Ymax, Zmin), new Point3D(Xmax, Ymax, Zmin), new Point3D(Xmin, Ymax, Zmax), new Point3D(Xmax, Ymax, Zmax)));
    }

    public void addGeometries(Intersectable ... geometries) {
        double Xmingeometries = Xmin;
        double Ymingeometries = Ymin;
        double Zmingeometries = Zmin;
        double Xmaxgeometries = Xmax;
        double Ymaxgeometries = Ymax;
        double Zmaxgeometries = Zmax;

        for (Intersectable i : geometries) {
            if (Xmingeometries > i.getXmin()) Xmingeometries = i.getXmin();
            if (Ymingeometries > i.getYmin()) Ymingeometries = i.getYmin();
            if (Zmingeometries > i.getZmin()) Zmingeometries = i.getZmin();

            if (Xmaxgeometries < i.getXmax()) Xmaxgeometries = i.getXmax();
            if (Ymaxgeometries < i.getYmax()) Ymaxgeometries = i.getYmax();
            if (Zmaxgeometries < i.getZmax()) Zmaxgeometries = i.getZmax();
        }

        setBox(Xmingeometries, Xmaxgeometries, Ymingeometries, Ymaxgeometries, Zmingeometries, Zmaxgeometries);
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
        List<GeoPoint> intersectionPoint = null;
        for (Polygon p : polygons)
            if (intersectionPoint == null)
                intersectionPoint = p.findGeoIntersections(ray, max);
            else
                intersectionPoint.addAll(p.findGeoIntersections(ray, max));
        return intersectionPoint;
    }

    //public Geometries getGeometries() {
    //    return geometries;
    //}


}
