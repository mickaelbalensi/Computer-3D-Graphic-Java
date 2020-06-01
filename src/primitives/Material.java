package primitives;

public class Material {
    double _kD;
    double _kS;
    int _nShininess;
    double _kR;
    double _kT;


    public Material(double kD, double kS, int nShininess) {
        this._kD = kD;
        this._kS = kS;
        this._nShininess = nShininess;
    }

    public Material(Material material) {
        this._kD = material._kD;
        this._kS = material._kS;
        this._nShininess = material._nShininess;
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
