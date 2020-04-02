package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void testSubtract() {
        //==================Equivalences Tests==================
        Vector v2= new Vector(1,2,3);
        Vector v3= new Vector(0,0,3);
        Vector result=v2.subtract(v3) ;

        Vector expResult = new Vector(1,2,0);
        assertEquals(result,expResult);

        //==================Boundary Tests======================
        //subtract the vector with self
        try{
            Vector v1= new Vector(4,5,6);
            v1.subtract(v1);
        }catch (Exception e){}

    }

    @Test
    void testAdd() {
        //==================Equivalences Tests==================
        Vector v1= new Vector(1,2,3);
        Vector v2= new Vector(3,4,5);

        Vector expResult = new Vector(4,6,8);
        Vector result = v1.add(v2);
        assertTrue(result.equals(expResult), "v1+v2=result");

        Vector v3 =new Vector(-3,-4,5);
        Vector expResult3 = new Vector(-2,-2,8);
        Vector result3 = v1.add(v3);
        assertTrue(result3.equals(expResult3), "v1+v3=result3");

        Vector v4 =new Vector(0,5,-5);
        Vector expResult4 = new Vector(1,7,-2);
        Vector result4 = v1.add(v4);
        assertTrue(result4.equals(expResult4), "v1+v4=result4");

        //==================Boundary Tests==================
        //testing sum of orthogonal vector
        Vector v5=new Vector(0,0,1);
        Vector v6=new Vector(0,1,0);
        Vector expResult5 = new Vector(0,1,1);
        Vector result5 = v5.add(v6);
        assertTrue(result5.equals(expResult5), "v5+v6=result5");

        try{
            //testing sum of inverse vectors
            Vector v7=new Vector(0,0,-1);
            Vector expResult7 = new Vector(0,1,1);
            Vector result7 = v5.add(v7);
            assertTrue( Util.isZero(result7.pt.getX().get())
                    && Util.isZero(result7.pt.getY().get())
                    && Util.isZero(result7.pt.getZ().get()), "v5+v7=vectorZERO");
        }
        catch (Exception e){}
    }

    @Test
    void testScale() {
        //==================Equivalences Tests==================
        Vector v1=new Vector(1,2,3);
        double scalar=2;
        Vector result=v1.scale(scalar);

        //test if the length of scale of a vector with a scalar is equal
        // to the multiplication of the length of the same vector by this scalar
        assertEquals(v1.length()*scalar,result.length(),"scale() wrong result length");

        //==================Boundary Tests==================

        double scalar2=0;
        try{
            Vector result2=v1.scale(scalar2);
            //test if the length of scale of a vector by 0 is equal to 0
            assertTrue(Util.isZero(result2.length()),"scale() wrong result length");
        }catch(Exception e){}

    }

    @Test
    void testDotProduct() {
        //==================Equivalences Tests==================
        Vector v1= new Vector(1,2,3);
        Vector v2= new Vector(3,4,5);
        double expResult=26;
        double result = v1.dotProduct(v2);
        assertEquals(expResult,result,"scalarProduct of v1 and v2=26");

        Vector v3= new Vector(-3,-4,5);
        double expResult3=4;
        double result3 = v1.dotProduct(v3);
        assertEquals(expResult3,result3,"scalarProduct of v1 and v3=4");

        //==================Boundary Tests==================
        //ortogonal vector v4 and v5
        Vector v4= new Vector(1,2,3);
        Vector v5= new Vector(-2,1,0);

        double expResult4 = 0;
        double result4 = v4.dotProduct(v5);
        assertEquals(expResult4,result4,"scalarProduct of orthogonal vectors v4 and v5 = 0");

        //parallel vector v6 and v7
        Vector v6= new Vector(1,2,3);
        Vector v7= new Vector(2,4,6);

        double expResult6 = 28;
        double result6 = v6.dotProduct(v7);
        assertEquals(expResult6,result6,"scalarProduct of orthogonal vectors v6 and v7 = 28");

    }

    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals( v1.length() * v3.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(Util.isZero(vr.dotProduct(v1)),"crossProduct() result is not orthogonal to 1st operand" );
        assertTrue(Util.isZero(vr.dotProduct(v3)),"crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}
    }

    @Test
    void testLengthSquared() {
        Vector v1 = new Vector(1, 2, 3);
        double result= v1.lengthSquared();
        double expResult= 14;
        assertEquals(expResult,result);
    }

    @Test
    void testLength() {
        Vector v1 = new Vector(1, 2, 3);
        double result= v1.length();
        double expResult= Math.sqrt(v1.lengthSquared());
        assertEquals(expResult,result);
    }

    @Test
    void testNormalize() {
        Vector v1 = new Vector(1, 2, 3);
        v1.normalize();
        assertTrue(v1.length()==1);
    }

    @Test
    void testNormalized() {
        Vector v1 = new Vector(1, 2, 3);
        double length=v1.length();
        Vector v2 = v1.normalized();
        assertTrue(v2.length()==1);
        assertTrue(v1.length()==length);
    }

    @Test
    void testEquals() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 3);
        boolean result= v1.equals(v2);
        assertTrue(result);

    }

}