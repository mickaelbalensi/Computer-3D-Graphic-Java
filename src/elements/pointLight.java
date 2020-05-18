package elements;

import primitives.*;

public class pointLight extends Light implements LightSource {
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
    public pointLight(Color intensity, Point3D position, double kC, double kL, double kQ){
        super(intensity);
        _position=position;
        _kC=kC;
        _kL=kL;
        _kQ=kQ;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double d=p.distance(_position);
        return new Color(_intensity).scale(1/_kC+_kL*d+_kQ*d*d);
    }

    @Override
    public Vector getL(Point3D p) {
        return null;
    }
}
