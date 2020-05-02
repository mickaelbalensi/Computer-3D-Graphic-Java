package elementTest;

import elements.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {
    Camera cam = new Camera(Point3D.ZERO,new Vector(0,0,1), new Vector(0,-1,0));
    Camera cam2 = new Camera(new Point3D(0,0,-0.5),new Vector(0,0,1), new Vector(0,-1,0));

    int Nx = 3;
    int Ny = 3;
    @Test
    void constructRayThroughPixelWithSphere(){

        //region TC01 : The Sphere is behind the screen

        Sphere sphere1 = new Sphere(new Point3D(0,0,3),1 );

        int count1 = 0;

        for(int i=0;i<Nx;i++)
            for(int j=0;j<Ny;j++){
                Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,9,9);
                List<Point3D> result= sphere1.findIntersections(ray);
                if(result != null)
                    count1+= result.size();
            }
        assertEquals(2, count1,"TC01 : Sphere behind the screen");
        //endregion

        //region TC02 : The Sphere is in front of the screen and fill it

        Sphere sphere2 = new Sphere(new Point3D(0,0,2.5),2.5 );

        int count2 = 0;

        for(int i=0;i<Nx;i++)
            for(int j=0;j<Ny;j++){
                Ray ray= cam2.constructRayThroughPixel(Nx,Ny,j,i,1,3,3);
                List<Point3D> result= sphere2.findIntersections(ray);
                if(result != null)
                    count2+= result.size();
            }

        assertEquals(18, count2,"TC02 : The Sphere is in front of the screen and fill it");
        //endregion

        //region TC03 : The Sphere is in front of the screen but don't fill it

        Sphere sphere3 = new Sphere(new Point3D(0,0,2.5),2 );

        int count3 = 0;

        for(int i=0;i<Nx;i++)
            for(int j=0;j<Ny;j++){
                Ray ray= cam2.constructRayThroughPixel(Nx,Ny,j,i,1,3,3);
                List<Point3D> result= sphere3.findIntersections(ray);
                if(result != null)
                    count3+= result.size();
            }

        assertEquals(10, count3,"TC03 : The Sphere is in front of the screen but don't fill it");
        //endregion

        //region TC04 : The Camera is in the Sphere
        Sphere sphere4 = new Sphere(new Point3D(0,0,1),5 );

        int count4 = 0;

        for(int i=0;i<Nx;i++)
            for(int j=0;j<Ny;j++){
                Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,3,3);
                List<Point3D> result= sphere4.findIntersections(ray);
                if(result != null)
                    count4+= result.size();
            }

        assertEquals(9, count4,"TC04 : The Camera is in the Sphere");
        //endregion

        //region TC05 : The Sphere is behind the camera

        Sphere sphere5 = new Sphere(new Point3D(0,0,-1),0.5 );

        int count5 = 0;

        for(int i=0;i<Nx;i++)
            for(int j=0;j<Ny;j++){
                Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,3,3);
                List<Point3D> result= sphere5.findIntersections(ray);
                if(result != null)
                    count5+= result.size();
            }

        assertEquals(0, count5,"TC05 : The Sphere is behind the camera");
        //endregion
    }

    @Test
    void constructRayThroughPixelWithPlane(){

        //region TC01 : The Plane is behind the screen
        Plane plane1 = new Plane(new Vector(0,0,1), new Point3D(0,0,5));

        int count1 = 0;

        for(int i=0;i<Nx;i++)
            for(int j=0;j<Ny;j++){
                Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,9,9);
                List<Point3D> result= plane1.findIntersections(ray);
                if(result != null)
                    count1+= result.size();
            }

        assertEquals(9, count1,"TC01 : The Plane is behind the screen");
        //endregion

        //region TC02 : The Plane is parallel to the screen
        Plane plane2 = new Plane(new Point3D(1.5,-1.5,1),new Point3D(-1.5,-1.5,1),new Point3D(0,2,2));

        int count2 = 0;

        for(int i=0;i<Nx;i++)
            for(int j=0;j<Ny;j++){
                Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,3,3);
                List<Point3D> result= plane2.findIntersections(ray);
                if(result != null)
                    count2+= result.size();
            }

        assertEquals(9, count2,"TC02 : The Plane is parallel to the screen");
        //endregion

        //region TC03 : The Plane is oblique
        Plane plane3 = new Plane(new Vector(0,4.5,1), new Point3D(0,0,5));

        int count3 = 0;

        for(int i=0;i<Nx;i++)
            for(int j=0;j<Ny;j++){
                Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,9,9);
                List<Point3D> result= plane3.findIntersections(ray);
                if(result != null)
                    count3+= result.size();
            }

        assertEquals(6, count3,"TC03 : The Plane is oblique");
        //endregion
    }

    @Test
    void constructRayThroughPixelWithTriangle(){
        //region TC01 : The little triangle is behind the screen
        Triangle triangle1 = new Triangle(new Point3D(0,-1,2),new Point3D(1,1,2),new Point3D(-1,1,2));

        int count1 = 0;

        for(int i=0;i<Nx;i++)
            for(int j=0;j<Ny;j++){
                Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,9,9);
                List<Point3D> result= triangle1.findIntersections(ray);
                if(result != null)
                    count1+= result.size();
            }

        assertEquals(1, count1,"TC01 : The little triangle is behind the screen");
        //endregion

        //region TC02 : The big triangle is behind the screen
        Triangle triangle2 = new Triangle(new Point3D(0,-20,2),new Point3D(1,1,2),new Point3D(-1,1,2));

        int count2 = 0;

        for(int i=0;i<Nx;i++)
            for(int j=0;j<Ny;j++){
                Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,3,3);
                List<Point3D> result= triangle2.findIntersections(ray);
                if(result != null)
                    count2+= result.size();
            }

        assertEquals(2, count2,"TC02 : The big triangle is behind the screen");
        //endregion
    }
}
