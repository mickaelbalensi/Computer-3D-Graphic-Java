package primitives;

public class Material {
    double _kD;
    double _kS;
    int _nShininess;
    double _kT;
    double _kR;

    public Material(double kT, double kR,double kD, double kS, int nShininess) {
        this._kT=kT;
        this._kR=kR;
        this._kD = kD;
        this._kS = kS;
        this._nShininess = nShininess;
    }

    public Material(double kD, double kS, int nShininess) {
        this(0,0,kD,kS,nShininess);
    }

    public Material(Material material) {
        this(0,0,material._kD,material._kS,material._nShininess);
    }


    /**
     * getter of kd
     * @return kd
     */
    public double getKd() {
        return _kD;
    }

    /**
     * getter of ks
     * @return ks
     */
    public double getKs() {
        return _kS;
    }

    /**
     * getter of shininess
     * @return the shininess
     */
    public int getShininess() {
        return _nShininess;
    }
}
