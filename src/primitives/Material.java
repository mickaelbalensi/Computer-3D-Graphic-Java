package primitives;

/**
 * This Class represents the material of shapes.
 * This Class indicates if the shape is transparent/opaque or if the lights source are reflected by the shape
 * This Class includes several indexes :
 * index of diffusive, index of specular, index of transparency, index of reflection, and index of shininess of the material
 *
 * @author mickael balensi
 */
public class Material {
    private double kD = 0d; // index of diffusive
    private double kS = 0d; // index of specular
    private int nShininess = 1; // index of shininess
    private double kT = 0d; //index of transparency
    private double kR = 0d; //index of reflection
    public static final Material DEFAULT = new Material(); // the default material of the Shape

    //region CTORs
    public Material() {}

    public Material(double kD, double kS, int nShininess, double kT, double kR) {
        this.kT = kT;
        this.kR = kR;
        this.kD = kD;
        this.kS = kS;
        this.nShininess = nShininess;
    }

    public Material(double kD, double kS, int nShininess) {
        this(kD, kS, nShininess, 0, 0);
    }
    //endregion

    //region getters
    public double getKd() {
        return kD;
    }

    public double getKs() {
        return kS;
    }

    public int getShininess() {
        return nShininess;
    }

    public double getKt() {
        return kT;
    }

    public double getKr() {
        return kR;
    }
    //endregion
}
