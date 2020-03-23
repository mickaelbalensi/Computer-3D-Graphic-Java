package primitives;

import java.util.Objects;

public class Vector {
    //region fields
    protected Point3D pt;//our vector will be the distance between the point(0,0,0) and the point pt
    //endregion

    //region CTORs

    public Vector(Coordinate x,Coordinate y,Coordinate z){
        Point3D temp=new Point3D(x, y, z); // create a new potential vector
        if(temp.equals(Point3D.ZERO)) // it can't be zero otherwise it's like we haven't created anything
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
        return this.pt.x.get()*vec.pt.x.get()+//x1*x2+
                this.pt.y.get()*vec.pt.y.get()+//y1*y2+
                this.pt.z.get()*vec.pt.z.get();// z1*z2
    }

    public  Vector crossProduct(Vector vec) {
        return new Vector
                (this.pt.y.get()*vec.pt.z.get()-vec.pt.y.get()*this.pt.z.get(),//y1*z2-y2*z1
                 (this.pt.x.get()*vec.pt.z.get()-vec.pt.x.get()*this.pt.z.get())*-1,//-(x1*z2-x2*z1
                 this.pt.x.get()*vec.pt.y.get()-vec.pt.x.get()*this.pt.y.get());//x1*y2-x2*y1
    }

    public double lengthSquared(){
        return (this.pt.x.get())*(this.pt.x.get())+//x^2+
                (this.pt.y.get())*(this.pt.y.get())+//y^2+
                (this.pt.z.get())*(this.pt.z.get());//z^2
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }//sqrt(x^2+y^2+z^2)

    public Vector normalize(){
        double length=this.length();
        this.pt.x=new Coordinate(this.pt.x.get()/length);//x=x/sqrt(x^2+y^2+z^2)
        this.pt.y=new Coordinate(this.pt.y.get()/length);//y=y/sqrt(x^2+y^2+z^2)
        this.pt.z=new Coordinate(this.pt.z.get()/length);//z=z/sqrt(x^2+y^2+z^2)
        return this;
    }
    public Vector normalized(){
        Vector temp=new Vector(this);
        return temp.normalize();//we just return a copy
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
