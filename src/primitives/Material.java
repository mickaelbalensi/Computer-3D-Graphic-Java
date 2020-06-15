package primitives;

public class Material {
    private double _kD = 0d;
    private double _kS = 0d;
    private int _nShininess = 1;
    private double _kT = 0d;
    private double _kR = 0d;
    public static final Material DEFAULT = new Material();

    public Material() {}

    public Material(double kD, double kS, int nShininess, double kT, double kR) {
        this._kT = kT;
        this._kR = kR;
        this._kD = kD;
        this._kS = kS;
        this._nShininess = nShininess;
    }

    public Material(double kD, double kS, int nShininess) {
        this(kD, kS, nShininess, 0, 0);
    }

    /**
     * getter of kd
     *
     * @return kd
     */
    public double getKd() {
        return _kD;
    }

    /**
     * getter of ks
     *
     * @return ks
     */
    public double getKs() {
        return _kS;
    }

    /**
     * getter of shininess
     *
     * @return the shininess
     */
    public int getShininess() {
        return _nShininess;
    }

    public double getKt() {
        return _kT;
    }

    public double getKr() {
        return _kR;
    }
}
