package com.company;

public class Vector {
    public double x,y,z;
    public Vector(double x0,double y0,double z0){
        x=x0;
        y=y0;
        z=z0;
    }
    public Vector(Vector v){
        x=v.x;
        y=v.y;
        z=v.z;
    }
    public void set(double x0,double y0,double z0){
        x=x0;
        y=y0;
        z=z0;
    }
    public static Vector crossProduct(Vector v1,Vector v2){
        Vector res=new Vector(0,0,0);
        res.x=v1.y*v2.z-v2.y*v1.z;
        res.y=v1.z*v2.x-v2.z*v1.x;
        res.z=v1.x*v2.y-v2.x*v1.y;
        return res;
    }
    public static Vector add(Vector v1,Vector v2){
        return new Vector(v1.x+v2.x,v1.y+v2.y,v1.z+v2.z);
    }
    public Vector add(Vector v1){
        this.x+=v1.x;
        this.y+=v1.y;
        this.z+=v1.z;
        return this;
    }
    public static double dotProduct(Vector v1,Vector v2){
        return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
    }
    public static Vector minus(Vector v1,Vector v2){
        return new Vector(v1.x-v2.x,v1.y-v2.y,v1.z-v2.z);
    }
    public void multiK(double k){
        x*=k;
        y*=k;
        z*=k;
    }
    public void normalization(){
        double r=x*x+y*y+z*z;
        r=MyMath.invSqrt(r);
        multiK(r);
    }
}
