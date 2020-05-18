package elements;

import primitives.*;

public class spotLight extends pointLight {
    private Vector _direction;

    public spotLight(Color intensity, Point3D position, double kC, double kL, double kQ, Vector _direction) {
        super(intensity, position, kC, kL, kQ);
        this._direction = _direction.normalize();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double d=p.distance(_position);
        Vector l=p.subtract(_position).normalize();
        return new Color(_intensity).scale(Math.max(0,_direction.dotProduct(l))/_kC+_kL*d+_kQ*d*d);
    }
}
