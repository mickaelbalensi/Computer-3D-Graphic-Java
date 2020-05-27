package elements;

import primitives.*;

/**
 * Class elements.Camera is the  class representing a camera to be a central cartesian landmark
 * The class is based on primitives.Vector and primitives.Point3D .
 */
public class Camera {
    Point3D p0;
    Vector Vto;
    Vector Vup;
    Vector Vright;

    /**
     * @param p0 is the center Point3D of the Camera
     * @param vto is a Vector, represents the axe Z of the landmark
     * @param vup is a Vector, represents the axe Y of the landmark
     */
    public Camera (Point3D p0,Vector vto,Vector vup){
        this.p0=new Point3D(p0);
        try{
            if (vto.dotProduct(vup)!=0)
                throw new IllegalArgumentException("Vectors vto and vup have to be orthogonal");
            this.Vto =vto.normalized();
            this.Vup =vup.normalized();
            this.Vright =this.Vto.crossProduct(this.Vup);
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
        Vector v1 = null;
        Point3D Pij;

        if(factor1!=0) {//if v1 is not the vector zero
            v1 = this.Vright.scale(factor1);
            Pij = Pc.add(v1);
        }else
            Pij=Pc;

        Vector v2=null;

        if (factor2!=0){//if v2 is not the vector zero
            v2=this.Vup.scale(factor2);
            Pij=Pij.subtract(v2.getPt()).getPt();
        }

        Vector vec= Pij.subtract(this.p0);
        return new Ray(this.p0, vec);

    }

    public Point3D getP0() {
        return p0;
    }
}
