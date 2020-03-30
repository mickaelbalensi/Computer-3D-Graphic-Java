package primitives;

import java.util.Objects;

public class Vector {
    //region fields
    protected Point3D pt;
    //endregion

    //region CTORs

    public Vector(Coordinate x,Coordinate y,Coordinate z){
        Point3D temp=new Point3D(x, y, z);
        if(temp.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Can't be a vector zero !");
        this.pt=temp;
    }
    public  Vector(double x, double y,double z){
        Point3D temp=new Point3D(x, y, z);
        if(temp.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Can't be a vector zero !");

        this.pt=temp;
    }
    public Vector(Point3D pt){
        if(pt.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Can't be a vector zero !");
        this.pt = pt;
    }
    public Vector(Vector vec){
        this.pt=vec.pt;
    }
    //endregion

    //region functions


    public void setPt(Point3D pt) {
        this.pt = pt;
    }

    public Vector subtract(Vector vec)  {
            return this.pt.subtract(vec.pt);
    }

    public Vector add(Vector vec)  {
            return  new Vector(this.pt.add(vec));
    }

    public Vector scale(double scalar)  {
        return new Vector(this.pt.x.get()*scalar,this.pt.y.get()*scalar,this.pt.z.get()*scalar);
    }

    public double dotProduct(Vector vec){
        return this.pt.x.get()*vec.pt.x.get()+
                this.pt.y.get()*vec.pt.y.get()+
                this.pt.z.get()*vec.pt.z.get();
    }

    public  Vector crossProduct(Vector vec) {
        return new Vector
                (this.pt.y.get()*vec.pt.z.get()-vec.pt.y.get()*this.pt.z.get(),
                 (this.pt.x.get()*vec.pt.z.get()-vec.pt.x.get()*this.pt.z.get())*-1,
                 this.pt.x.get()*vec.pt.y.get()-vec.pt.x.get()*this.pt.y.get());
    }

    public double lengthSquared(){
        return (this.pt.x.get())*(this.pt.x.get())+
                (this.pt.y.get())*(this.pt.y.get())+
                (this.pt.z.get())*(this.pt.z.get());
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize(){
        double length=this.length();

        this.pt.x=new Coordinate(this.pt.x.get()/length);
        this.pt.y=new Coordinate(this.pt.y.get()/length);
        this.pt.z=new Coordinate(this.pt.z.get()/length);
        return this;
    }
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
