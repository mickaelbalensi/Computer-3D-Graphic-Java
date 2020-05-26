package elements;

import primitives.*;

public class SpotLight extends PointLight {
    private Vector _direction;

    public SpotLight(Color intensity, Point3D position, double kC, double kL, double kQ, Vector _direction) {
        super(intensity, position, kC, kL, kQ);
        this._direction = _direction.normalize();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double d=p.distance(_position);
        Vector l=getL(p);

        return super.getIntensity(p).scale(Math.max(0,_direction.dotProduct(l)));
    }
}
