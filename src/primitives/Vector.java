package primitives;

public class Vector {
    //region fields
    protected Point3D pt;
    //endregion

    //region CTORs

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public Vector(Coordinate x,Coordinate y,Coordinate z){
        Point3D temp=new Point3D(x, y, z);
       // if(temp.equals(Point3D.ZERO))
        //    throw new IllegalArgumentException("Can't be a vector zero !");
        this.pt=temp;
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */

    public  Vector(double x, double y,double z){
        Point3D temp=new Point3D(x, y, z);
        //if(temp.equals(new Point3D(0.0d,0.0d,0.0d)))
          //  throw new IllegalArgumentException("Can't be a vector zero !");

        this.pt=temp;
    }

    /**
     *
     * @param pt
     */
    public Vector(Point3D pt){
        //if(pt.equals(new Point3D(0.0,0.0d,0.0d)))
            //   throw new IllegalArgumentException("Can't be a vector zero !");
        this.pt = pt;
    }

    /**
     *
     * @param vec
     */
    public Vector(Vector vec){
        this.pt=new Point3D(vec.pt);
    }

    //endregion

    //region functions

    /**
     *
     * @param pt
     */
    public void setPt(Point3D pt) {
        this.pt = pt;
    }

    /**
     *
     * @param vec
     * @return vector
     */
    public Vector subtract(Vector vec)  {
            return this.pt.subtract(vec.pt);
    }

    /**
     *
     * @param vec
     * @return Vector
     */
    public Vector add(Vector vec)  {
            return  new Vector(this.pt.add(vec));
    }

    /**
     *
     * @param scalar
     * @return Vector
     */
    public Vector scale(double scalar)  {
        return new Vector(this.pt.x.get()*scalar,this.pt.y.get()*scalar,this.pt.z.get()*scalar);
    }

    /**
     *
     * @param vec
     * @return double
     */
    public double dotProduct(Vector vec){
        return this.pt.x.get()*vec.pt.x.get()+
                this.pt.y.get()*vec.pt.y.get()+
                this.pt.z.get()*vec.pt.z.get();
    }

    /**
     *
     * @param vec
     * @return vector
     */
    public  Vector crossProduct(Vector vec) {
        return new Vector
                (this.pt.y.get()*vec.pt.z.get()-vec.pt.y.get()*this.pt.z.get(),
                 (this.pt.x.get()*vec.pt.z.get()-vec.pt.x.get()*this.pt.z.get())*-1,
                 this.pt.x.get()*vec.pt.y.get()-vec.pt.x.get()*this.pt.y.get());
    }

    /**
     *
     * @return double
     */
    public double lengthSquared(){
        return (this.pt.x.get())*(this.pt.x.get())+
                (this.pt.y.get())*(this.pt.y.get())+
                (this.pt.z.get())*(this.pt.z.get());
    }

    /**
     *
     * @return double
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     *
     * @return Vector
     */
    public Vector normalize(){
        double length=this.length();
        
        this.pt.x=new Coordinate(this.pt.x.get()/length);
        this.pt.y=new Coordinate(this.pt.y.get()/length);
        this.pt.z=new Coordinate(this.pt.z.get()/length);
        return this;
    }

    /**
     *
     * @return Vector
     */
    public Vector normalized(){
        Vector temp=new Vector(this);
        return temp.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return vector.pt.equals(this.pt);
    }


    @Override
    public String toString() {
        return "Vector{" +
                "pt=" + pt +
                '}';
    }

    public Point3D getPt() {
        return pt;
    }

    //endregion
}
