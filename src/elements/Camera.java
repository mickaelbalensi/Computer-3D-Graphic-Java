
/*package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    Point3D _p0;
    Vector _vTo;
    Vector _vUp;
    Vector _vRight;

    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {

        //if the the vectors are not orthogonal, throw exception.
        if (_vUp.dotProduct(_vTo) != 0)
            throw new IllegalArgumentException("the vectors must be orthogonal");

        this._p0 =  new Point3D(_p0);
        this._vTo = _vTo.normalized();
        this._vUp = _vUp.normalized();

        _vRight = this._vTo.crossProduct(this._vUp).normalize();

    }

    public Ray constructRayThroughPixel(int nX, int nY,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight)
    {
        if (isZero(screenDistance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        Point3D Pc = _p0.add(_vTo.scale(screenDistance));

        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;

        double yi =  ((i - nY/2d)*Ry + Ry/2d);
        double xj=   ((j - nX/2d)*Rx + Rx/2d);

        Point3D Pij = Pc;

        if (! isZero(xj))
        {
            Pij = Pij.add(_vRight.scale(xj));
        }
        if (! isZero(yi))
        {
            Pij = Pij.subtract(_vUp.scale(yi)); // Pij.add(_vUp.scale(-yi))
        }

        Vector Vij = Pij.subtract(_p0);

        return new Ray(_p0,Vij);

    }


    public Point3D getP0() {
        return new Point3D(_p0);
    }

    public Vector get_vTo() {
        return new Vector(_vTo);
    }

    public Vector get_vUp() {
        return new Vector(_vUp);
    }

    public Vector get_vRight() {
        return new Vector(_vRight);
    }
}*/

package elements;

import primitives.*;

import java.util.ArrayList;

/**
 * Class elements.Camera is the  class representing a camera to be a central cartesian landmark
 * The class is based on primitives.Vector and primitives.Point3D .
 *
 * @author mickael balensi
 */

public class Camera {
    private Point3D p0; // the central cartesian landmark
    private Vector Vto; // the axe Z
    private Vector Vup; // the axe -Y
    private Vector Vright; //the axe X

    //region CTOR
    /**
     * its constructor that create a new camera with the position chosed
     *
     * @param p0  is the center Point3D of the Camera
     * @param vto is a Vector, represents the axe Z of the landmark
     * @param vup is a Vector, represents the axe Y of the landmark
     */
    public Camera(Point3D p0, Vector vto, Vector vup) {
        this.p0 = new Point3D(p0);
        try {
            if (vto.dotProduct(vup) != 0)
                throw new IllegalArgumentException("Vectors vto and vup have to be orthogonal");
            this.Vto = vto.normalized();
            this.Vup = vup.normalized();
            this.Vright = this.Vto.crossProduct(this.Vup).normalize();
        } catch (IllegalArgumentException ex) {

        }
    }
    //endregion

    //region getter
    public Point3D getP0() {
        return p0;
    }
    //endregion

    /**
     * Cette fonction permet de creer une droite partant du centre de la camera vers un pixel donne en parametre
     * Le but etant par la suite de colorer chaque pixel de l'image selon les formes geomeriques presentes sur la scene
     * de la photo. Pour cela il est important de determiner pour chaque droite partant de la camera traversant un pixel
     * si la droite atteint un objet de la scene. Si oui, le pixel en question prendra la couleur exacte du point
     * d'intersection de la droite avec l'objet. Et cepour chaque pixel de l'image
     *
     * @param Nx             numbers of pixel int the width of the screen
     * @param Ny             numbers of pixel int the height of the screen
     * @param j              index of column
     * @param i              index of line
     * @param screenDistance distance between the view plane and the camera
     * @param screenWidth    the width of the view plane
     * @param screenHeight   the height of the camera
     * @return rays crosses the view plane
     */
    public Ray constructRayThroughPixel(int Nx, int Ny,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight) {

        //Point3D Pc= new Point3D(0,0,screenDistance);

        Point3D Pc = p0.add(Vto.scale(screenDistance));

        double Rx = screenWidth / Nx;
        double Ry = screenHeight / Ny;

        double factor1 = (j - (Nx - 1) / 2d) * Rx;
        double factor2 = (i - (Ny - 1) / 2d) * Ry;
        Point3D Pij = Pc;
        Vector v1 = null;
        Vector v2 = null;
        if (factor1 != 0) {//if v1 is not the vector zero
            v1 = Vright.scale(factor1);
            Pij = Pij.add(v1);
        }

        if (factor2 != 0) {//if v2 is not the vector zero
            v2 = Vup.scale(factor2);
            Pij = Pij.subtract(v2);
        }

        Vector vec = Pij.subtract(p0);
        Ray res= new Ray(p0, vec);
        return res;

    }
    public ArrayList<Ray> constructRayThroughPixel2(int Nx, int Ny,
                                                   int j, int i, double screenDistance,
                                                   double screenWidth, double screenHeight) {

        //Point3D Pc= new Point3D(0,0,screenDistance);

        Point3D Pc = p0.add(Vto.scale(screenDistance));

        double Rx = screenWidth / Nx;
        double Ry = screenHeight / Ny;

        double factor1 = (j - (Nx - 1) / 2d) * Rx;
        double factor2 = (i - (Ny - 1) / 2d) * Ry;
        Point3D Pij = Pc;
        Vector v1 = null;
        Vector v2 = null;
        if (factor1 != 0) {//if v1 is not the vector zero
            v1 = Vright.scale(factor1);
            Pij = Pij.add(v1);
        }

        if (factor2 != 0) {//if v2 is not the vector zero
            v2 = Vup.scale(factor2);
            Pij = Pij.subtract(v2);
        }

        Vector vec = Pij.subtract(p0);
        Ray res= new Ray(p0, vec);


        int radius= Nx<Ny ? Nx/2 : Ny/2;

        return res.getListRays(Pij,radius);
    }

}
