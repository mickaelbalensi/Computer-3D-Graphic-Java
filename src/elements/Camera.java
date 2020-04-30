package elements;

import primitives.*;

/**
 * Class elements.Camera is the  class representing a camera to be a central cartesian landmark
 * The class is based on primitives.Vector and primitives.Point3D .
 */
public class Camera {
    Point3D p0;
    Vector vto;
    Vector vup;
    Vector vright;

    /**
     *
     * @param p0 is the center Point3D of the Camera
     * @param vto is a Vector, represents the axe Z of the landmark
     * @param vup is a Vector, represents the axe Y of the landmark
     */
    public Camera (Point3D p0,Vector vto,Vector vup){
        this.p0=new Point3D(p0);
        try{
            if (vto.dotProduct(vup)!=0)
                throw new IllegalArgumentException("Vectors vto and vup have to be orthogonal");
            this.vto=vto.normalized();
            this.vup=vup.normalized();
            this.vright=this.vto.crossProduct(this.vup);
        }catch(IllegalArgumentException ex){

        }
    }

    /**
     * This function returns all of rays crosses the view plane
     * @param nX numbers of pixel int the width of the screen
     * @param nY numbers of pixel int the height of the screen
     * @param j index of line
     * @param i index of column
     * @param screenDistance distance between the view plane and the camera
     * @param screenWidth the width of the view plane
     * @param screenHeight the height of the camera
     * @return rays crosses the view plane
     */
    public Ray constructRayThroughPixel (int nX, int nY,
                                         int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight){
        return null;
    }

}
