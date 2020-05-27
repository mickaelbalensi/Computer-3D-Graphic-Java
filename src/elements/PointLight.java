package elements;

import primitives.*;

public class PointLight extends Light implements LightSource {
    protected Point3D _position;
    double _kC,_kL,_kQ;

    /**
     *
     * @param intensity
     * @param position
     * @param kC
     * @param kL
     * @param kQ
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ){
        super(intensity);
        _position=position;
        _kC=kC;
        _kL=kL;
        _kQ=kQ;
    }

    public PointLight(Color colorIntensity, Point3D position) {
        this(colorIntensity, position, 1d, 0d, 0d);
    }

    @Override
    public Color getIntensity(Point3D p) {
        double dSquared=p.distanceSquared(_position);
        double d=p.distance(_position);

        return _intensity.reduce(_kC+_kL*d+_kQ*dSquared);
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalize();
    }
}
