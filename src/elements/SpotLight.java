package elements;

import primitives.*;

public class SpotLight extends PointLight {
    private Vector _direction;

    public SpotLight(Color intensity, Point3D position, Vector _direction, double kC, double kL, double kQ) {
        this(intensity, position, kC, kL, kQ, _direction);
    }

    public SpotLight(Color intensity, Point3D position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        this._direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double projection = _direction.dotProduct(getL(p));
        if (Util.alignZero(projection) <= 0) return Color.BLACK;

        return (super.getIntensity(p).scale(projection));
    }
}
