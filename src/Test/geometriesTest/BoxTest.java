package geometriesTest;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoxTest {

    @Test
    public void boxTest() {
        Box box= new Box(new Sphere(new Point3D(5,3,7),1));
        Geometries g = box;
        List<Intersectable> list = g.getGeometries();

        System.out.println(g);

        for(Intersectable i : list){
            System.out.println(i);
        }



    }

}
