package geometries;

import java.util.ArrayList;

public abstract class SeveralGeometries {

    protected double Xmin; // the X-value minimum of the Geometry
    protected double Ymin; // the Y-value minimum of the Geometry
    protected double Zmin; // the Z-value minimum of the Geometry
    protected double Xmax; // the X-value maximum of the Geometry
    protected double Ymax; // the Y-value maximum of the Geometry
    protected double Zmax; // the Z-value maximum of the Geometry
    protected ArrayList<Triangle> list;
    static double MAX = 100000;
    static double MIN = -100000;


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
    //public ArrayList<Triangle> getList() {
    //    return list;
    //}


}
