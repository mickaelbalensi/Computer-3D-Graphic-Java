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

        double projection = _direction.dotProduct(getL(p));

        if (Util.isZero(projection)) {
            return Color.BLACK;
        }
        double factor = Math.max(0, projection);
        Color pointlightIntensity = super.getIntensity(p);


        return (pointlightIntensity.scale(factor));
    }
}
