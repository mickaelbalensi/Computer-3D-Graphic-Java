package image;

import geometries.Geometries;
import geometries.Triangle;
import primitives.*;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class Cat {
    Cat1 cat1;
    Cat2 cat2;
    Cat3 cat3;
    Cat4 cat4;
    Cat5 cat5;
    Cat6 cat6;

/*    protected double Xmin; // the X-value minimum of the Geometry
    protected double Ymin; // the Y-value minimum of the Geometry
    protected double Zmin; // the Z-value minimum of the Geometry
    protected double Xmax; // the X-value maximum of the Geometry
    protected double Ymax; // the Y-value maximum of the Geometry
    protected double Zmax; // the Z-value maximum of the Geometry
    */
    protected ArrayList<Triangle> list;

    public Cat() {
        cat1 = new Cat1(new Color(java.awt.Color.darkGray), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0));
        cat2 = new Cat2(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0));
        cat3 = new Cat3(new Color(java.awt.Color.white), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0));
        cat4 = new Cat4(new Color(java.awt.Color.darkGray), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0));
        cat5 = new Cat5(new Color(java.awt.Color.gray), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0));
        cat6 = new Cat6(new Color(java.awt.Color.darkGray), new Material(0.5, 0.5, 30), .5, new Point3D(0, 0, 0));

        list =new ArrayList<>();
        list.addAll(cat1.getList());
        list.addAll(cat2.getList());
        list.addAll(cat3.getList());
        list.addAll(cat4.getList());
        list.addAll(cat5.getList());
        list.addAll(cat6.getList());

/*
        Xmin=Util.min(cat1.getXmin(),cat2.getXmin(),cat3.getXmin(),cat4.getXmin(),cat5.getXmin(),cat6.getXmin());
        Ymin=Util.min(cat1.getYmin(),cat2.getYmin(),cat3.getYmin(),cat4.getYmin(),cat5.getYmin(),cat6.getYmin());
        Zmin=Util.min(cat1.getZmin(),cat2.getZmin(),cat3.getZmin(),cat4.getZmin(),cat5.getZmin(),cat6.getZmin());

        Xmax=Util.max(cat1.getXmax(),cat2.getXmax(),cat3.getXmax(),cat4.getXmax(),cat5.getXmax(),cat6.getXmax());
        Ymax=Util.max(cat1.getYmax(),cat2.getYmax(),cat3.getYmax(),cat4.getYmax(),cat5.getYmax(),cat6.getYmax());
        Zmax=Util.max(cat1.getZmax(),cat2.getZmax(),cat3.getZmax(),cat4.getZmax(),cat5.getZmax(),cat6.getZmax());
*/
    }
/*    @Override
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
    }*/
/*    @Override
    public ArrayList<Triangle> getList() {
           return list;
    }*/

    public Geometries getGeometries() {
        Geometries geo =new Geometries();
        geo.addList(list);
        return geo;
    }
}
