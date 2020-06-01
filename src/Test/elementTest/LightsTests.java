package elementTest;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test rendering abasic image
 * 
 * @author Dan
 */
public class LightsTests {

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(10000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Point3D(0, 0, 50),50,new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)));

        scene.addLights(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)));

        ImageWriter imageWriter = new ImageWriter("sphereDirectional", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(  new Point3D(0, 0, 50),50 ,new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)));

        scene.addLights(new PointLight(new Color(500, 300, 0), new Point3D(-50, 50, -50), 1, 0.00001, 0.000001));

        ImageWriter imageWriter = new ImageWriter("spherePoint1", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere( new Point3D(0, 0, 50),50,new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)));

        scene.addLights(new SpotLight(new Color(500, 300, 0), new Point3D(-50, 50, -50),
                 1, 0.00001, 0.00000001,new Vector(1, -1, 2)));

        ImageWriter imageWriter = new ImageWriter("sphereSpot2", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture with some spheres lighted by many light source : 2 spot and 1 Point Light
     */
    @Test
    public void sphereMultipleSource(){
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(10000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere( new Point3D(-22, -22, 0),13,new Color(java.awt.Color.red), new Material(0.5, 0.5, 100)),
                new Sphere( new Point3D(-5, -5, 0),11,new Color(java.awt.Color.green), new Material(0.5, 0.5, 100)),
                new Sphere( new Point3D(10, 10, 0),9,new Color(java.awt.Color.CYAN), new Material(0.5, 0.5, 100)),
                new Sphere( new Point3D(22, 22, 0),7,new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)),
                new Sphere( new Point3D(30, 30, 0),4,new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 100))
        );

        scene.addLights(
                new SpotLight(new Color(300, 300, 300), new Point3D(-50, 50, -50),
                1, 0.00001, 0.00000001,new Vector(1, -1, 2)),
                new SpotLight(new Color(300, 300, 300), new Point3D(150, 50, -50),
                        1, 0.00001, 0.00000001,new Vector(-1, -1, 1)),
                new PointLight(new Color(100, 200, 300), new Point3D(150, 0, -50),
                        1, 0.00001, 0.00000001/*,new Vector(0, 1, 1)*/)

        );

        ImageWriter imageWriter = new ImageWriter("sphereTwoSpot", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();

    }
    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle(new Point3D(-150, 150, 150), new Point3D(150, 150, 150),
                        new Point3D(75, -75, 150),Color.BLACK, new Material(0.8, 0.2, 300)),
                new Triangle(new Point3D(-150, 150, 150), new Point3D(-70, -70, 50),
                        new Point3D(75, -75, 150),Color.BLACK, new Material(0.8, 0.2, 300)));

        scene.addLights(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, 1)));

        ImageWriter imageWriter = new ImageWriter("trianglesDirectional", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -100000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(100000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle( new Point3D(-150, 150, 150), new Point3D(150, 150, 150),
                        new Point3D(75, -75, 150),Color.BLACK, new Material(0.5, 0.5, 300)),
                new Triangle(new Point3D(-150, 150, 150), new Point3D(-70, -70, 50),
                        new Point3D(75, -75, 150),Color.BLACK, new Material(0.5, 0.5, 300)));

        scene.addLights(new PointLight(new Color(500, 250, 250),
                new Point3D(10, 10, 130), 1, 0.0005, 0.0005));

        ImageWriter imageWriter = new ImageWriter("trianglesPoint", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries(
                new Triangle( new Point3D(-150, 150, 150), new Point3D(150, 150, 150), new Point3D(75, -75, 150),
                        Color.BLACK, new Material(0.5, 0.5, 300)),
                new Triangle( new Point3D(-150, 150, 150), new Point3D(-70, -70, 50), new Point3D(75, -75, 150),
                        Color.BLACK, new Material(0.5, 0.5, 300)));

        scene.addLights(new SpotLight(new Color(500, 250, 250), new Point3D(10, 10, 130),
                1, 0.0001, 0.000005, new Vector(-2, 2, 1)));

        ImageWriter imageWriter = new ImageWriter("trianglesSpot", 200, 200, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }


    public static class IntegrationTest {
        Camera cam = new Camera(Point3D.ZERO,new Vector(0,0,1), new Vector(0,-1,0));
        Camera cam2 = new Camera(new Point3D(0,0,-0.5),new Vector(0,0,1), new Vector(0,-1,0));

        int Nx = 3;
        int Ny = 3;
        @org.junit.jupiter.api.Test
        void constructRayThroughPixelWithSphere(){

            //region TC01 : The Sphere is behind the screen

            Sphere sphere1 = new Sphere(new Point3D(0,0,3),1 );

            int count1 = 0;

            for(int i=0;i<Nx;i++)
                for(int j=0;j<Ny;j++){
                    Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,3,3);
                    List<Intersectable.GeoPoint> result= sphere1.findIntersections(ray);
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
                    List<Intersectable.GeoPoint> result= sphere2.findIntersections(ray);
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
                    List<Intersectable.GeoPoint> result= sphere3.findIntersections(ray);
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
                    List<Intersectable.GeoPoint> result= sphere4.findIntersections(ray);
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
                    List<Intersectable.GeoPoint> result= sphere5.findIntersections(ray);
                    if(result != null)
                        count5+= result.size();
                }

            assertEquals(0, count5,"TC05 : The Sphere is behind the camera");
            //endregion
        }

        @org.junit.jupiter.api.Test
        void constructRayThroughPixelWithPlane(){

            //region TC01 : The Plane is behind the screen
            Plane plane1 = new Plane(new Vector(0,0,1), new Point3D(0,0,5));

            int count1 = 0;

            for(int i=0;i<Nx;i++)
                for(int j=0;j<Ny;j++){
                    Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,9,9);
                    List<Intersectable.GeoPoint> result= plane1.findIntersections(ray);
                    if(result != null)
                        count1+= result.size();
                }

            assertEquals(9, count1,"TC01 : The Plane is behind the screen");
            //endregion

            //region TC02 : The Plane is parallel to the screen
            Plane plane2 = new Plane(new Point3D(1.5,-1.5,1),new Point3D(-1.5,-1.5,1),new Point3D(0,2,2),new Color(256,0,0));

            int count2 = 0;

            for(int i=0;i<Nx;i++)
                for(int j=0;j<Ny;j++){
                    Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,3,3);
                    List<Intersectable.GeoPoint> result= plane2.findIntersections(ray);
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
                    List<Intersectable.GeoPoint> result= plane3.findIntersections(ray);
                    if(result != null)
                        count3+= result.size();
                }

            assertEquals(6, count3,"TC03 : The Plane is oblique");
            //endregion
        }

        @org.junit.jupiter.api.Test
        void constructRayThroughPixelWithTriangle(){
            //region TC01 : The little triangle is behind the screen
            Triangle triangle1 = new Triangle(new Point3D(0,-1,2),new Point3D(1,1,2),new Point3D(-1,1,2),new Color(256,0,0));

            int count1 = 0;

            for(int i=0;i<Nx;i++)
                for(int j=0;j<Ny;j++){
                    Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,9,9);
                    List<Intersectable.GeoPoint> result= triangle1.findIntersections(ray);
                    if(result != null)
                        count1+= result.size();
                }

            assertEquals(1, count1,"TC01 : The little triangle is behind the screen");
            //endregion

            //region TC02 : The big triangle is behind the screen
            Triangle triangle2 = new Triangle(new Point3D(0,-20,2),new Point3D(1,1,2),new Point3D(-1,1,2),new Color(256,0,0));

            int count2 = 0;

            for(int i=0;i<Nx;i++)
                for(int j=0;j<Ny;j++){
                    Ray ray= cam.constructRayThroughPixel(Nx,Ny,j,i,1,3,3);
                    List<Intersectable.GeoPoint> result= triangle2.findIntersections(ray);
                    if(result != null)
                        count2+= result.size();
                }

            assertEquals(2, count2,"TC02 : The big triangle is behind the screen");
            //endregion
        }
    }
}
