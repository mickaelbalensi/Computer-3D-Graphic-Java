package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Color;

import java.awt.*;

public abstract class Geometry implements Intersectable {
    protected primitives.Color _emission;
    public abstract  Vector getNormal(Point3D pt);
    Geometry(Color emission){
        this._emission=emission;
    }
    Geometry(){
        this._emission=Color.BLACK;
    }
    public Color getEmission(){
        return _emission;
    }

}
