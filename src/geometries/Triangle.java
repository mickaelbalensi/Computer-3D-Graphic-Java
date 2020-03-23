package geometries;
import primitives.Point3D;

import java.util.List;

public class Triangle extends Polygon {

    public Triangle(Point3D pt1,Point3D pt2,Point3D pt3) {
        super(pt1,pt2,pt3);
    }
    Plane plane;
    List<Point3D> _vertices;
}

