package elements;

import primitives.*;

/**
 * Class elements.Camera is the  class representing a camera to be a central cartesian landmark
 * The class is based on primitives.Vector and primitives.Point3D .
 */
public class Camera {
    Point3D _p0;
    Vector _Vto;
    Vector _Vup;
    Vector _Vright;

    /**
     * @param p0 is the center Point3D of the Camera
     * @param vto is a Vector, represents the axe Z of the landmark
     * @param vup is a Vector, represents the axe Y of the landmark
     */
    public Camera (Point3D p0, Vector vto, Vector vup){
        this._p0 =new Point3D(p0);
        try{
            if (vto.dotProduct(vup)!=0)
                throw new IllegalArgumentException("Vectors vto and vup have to be orthogonal");
            this._Vto =vto.normalized();
            this._Vup =vup.normalized();
            this._Vright =this._Vto.crossProduct(this._Vup);
        }catch(IllegalArgumentException ex){

        }
    }

    /**
     * This function returns all of rays crosses the view plane
     * @param Nx numbers of pixel int the width of the screen
     * @param Ny numbers of pixel int the height of the screen
     * @param j index of column
     * @param i index of line
     * @param screenDistance distance between the view plane and the camera
     * @param screenWidth the width of the view plane
     * @param screenHeight the height of the camera
     * @return rays crosses the view plane
     */
    public Ray constructRayThroughPixel (int Nx, int Ny,
                                         int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight){

        Point3D Pc= new Point3D(0,0,screenDistance);

        double Rx=screenWidth/Nx;
        double Ry=screenHeight/Ny;

        double factor1=(j-(Nx-1)/2d)*Rx;
        double factor2=(i-(Ny-1)/2d)*Ry;
        Point3D Pij=Pc;
        Vector v1 = null;
        Vector v2=null;
        if(factor1!=0) {//if v1 is not the vector zero
            v1 = _Vright.scale(factor1);
            Pij = Pij.add(v1);
        }

        if (factor2!=0){//if v2 is not the vector zero
            v2= _Vup.scale(factor2);
            Pij=Pij.subtract(v2);
        }

        Vector vec= Pij.subtract(_p0);
        return new Ray(_p0, vec);

    }

    public Point3D getP0() {
        return _p0;
    }
}
