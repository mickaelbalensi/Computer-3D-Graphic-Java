package elements;

import primitives.*;

public interface LightSource {
    Color getIntensity(Point3D p);
    Vector getL(Point3D p);
    public double getDistance(Point3D point);
}
