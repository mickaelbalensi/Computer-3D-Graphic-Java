package primitives;

import geometries.Intersectable;

public class Point3D {
    //region fields
    protected Coordinate x;
    protected Coordinate y;
    protected Coordinate z;
    public static final Point3D ZERO=new Point3D(0,0,0);
    //endregion

    //region CTORs

    /**
     *
     * @param x coordinate x of the point
     * @param y coordinate y of the point
     * @param z coordinate of the point
     */
    public Point3D(Coordinate x,Coordinate y,Coordinate z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    /**
     * point's coordinate
     * @param x x
     * @param y y
     * @param z z
     */
    public Point3D(double x, double y,double z){
        this.x=new Coordinate(x);
        this.y=new Coordinate(y);
        this.z=new Coordinate(z);
    }

    /**
     * @param pt now the coordinates of our points are x,y  and z
     */

    public Point3D(Point3D pt){
        this.x=pt.x;
        this.y=pt.y;
        this.z=pt.z;
    }
    //endregion

    //region functions

    //region getters

    /**
     * @return coordinate x
     */
    public Coordinate getX() { return x; }

    /**
     *
     * @return coordinate y
     */
    public Coordinate getY() {
        return y;
    }

    /**
     *
     * @return coordinate z
     */
    public Coordinate getZ() {    return z; }
    //endregion

    /*    //region setters
    public void setX(Coordinate x) {
        this.x = x;
    }
    public void setY(Coordinate y) {
        this.y = y;
    }
    public void setZ(Coordinate z) {
        this.z = z;
    }
    //endregion
*/

    /**
     *
     * @param pt the point with which we are going to subract our vector
     * @return Vector
     *  we subtract the point by the vector
     */
    public Vector subtract(Point3D pt)  {
       Vector vec = new Vector(
               this.getX().get()-pt.getX().get(),
               this.getY().get()-pt.getY().get(),
               this.getZ().get()-pt.getZ().get());
       return vec; // to subtract two vectors we need to subtract every coordinates of each vectors by his correspondents
    }

    public Point3D subtract(Vector v){
        return subtract(v.getPt()).getPt();
    }

    /**
     *
     * @param vec the  vector with which  we are going to add to our point
     * @return Vector
     * we add a point to the vector
     */
    public Point3D add(Vector vec){
        Point3D dest=new Point3D(this.x.get()+vec.pt.x.get(),this.y.get()+vec.pt.y.get(),this.z.get()+vec.pt.z.get());
        return dest;//to add two vectors we do the same than the subtract but we add
    }

    /**
     *
     * @param pt the point pointed by our vector from (0,0,0)
     * @return double
     * we calculate the lenght square of the vector
     */
    public double distanceSquared(Point3D pt){
        return (pt.x.get()-this.x.get())*(pt.x.get()-this.x.get()) +
                (pt.y.get()-this.y.get())*(pt.y.get()-this.y.get())+
                (pt.z.get()-this.z.get())*(pt.z.get()-this.z.get()); //we do the sum of all points that we squared
    }

    /**
     *
     * @param pt pt
     * @return double
     * the lenght of the vector
     */
    public double distance(Point3D pt){
        return Math.sqrt(distanceSquared(pt));
    }// we return the square root of the distance squared

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) && y.equals(point3D.y) && z.equals(point3D.z);
    }




    //endregion


}
