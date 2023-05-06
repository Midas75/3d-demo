package com.company;

public class Camera {
    public static final int SHOW_LINE=0b1;
    public static final int SHOW_TRIANGLE=0b10;
    public static final int SHOW_LIGHT=0b100;
    public static final int SHOW_TEXTURE=0b1000;
    public Vector position;
    public Vector lookDirection;
    public Vector xDirection, yDirection;
    public int obMode;
    public final double fov=90.0;
    public double aspect;
    public double near;
    public double far;
    public final double rate=2.5;
    public enum Type{
        CLASSIC
    }
    public Camera(Type type){
        if(type==Type.CLASSIC){
            position =new Vector(0,0,0);
            lookDirection =new Vector(0,0,0);
            xDirection =new Vector(0,0,0);
            yDirection =new Vector(0,0,0);
            position.set(500,500,0);
            lookDirection.set(1,1,0);
            lookDirection.normalization();
            aspect=(double)1024/(double)768;
            near=768/2.0*Math.tan(Math.toRadians(fov/2));
            directionReset();
            //obMode =SHOW_TRIANGLE|SHOW_LIGHT|SHOW_TEXTURE;
            obMode =SHOW_TRIANGLE|SHOW_LIGHT|SHOW_TEXTURE;
        }
    }
    public void goForward(int direction){
        double rate=5.0*direction;
        position.x-= lookDirection.x*rate;
        position.y-= lookDirection.y*rate;
        position.z-= lookDirection.z*rate;
    }
    public void turnLeft(int direction){
        double rate=0.2*direction;
        Matrix mt=Matrix.getTurnMatrix(lookDirection,new Vector(0,0,1),new Vector(0,0,0),rate);
        double[][] temp = {{lookDirection.x}, {lookDirection.y}, {lookDirection.z}, {1}};
        Matrix tempmatrix = new Matrix(temp);
        tempmatrix=Matrix.matrixMulti(mt, tempmatrix);
        lookDirection.x = tempmatrix.value[0][0];
        lookDirection.y = tempmatrix.value[1][0];
        lookDirection.z = tempmatrix.value[2][0];
        directionReset();
    }

    public void turnUp(int direction){
        double rate=0.2*direction;
        Matrix mt=Matrix.getTurnMatrix(lookDirection,new Vector(-1,1,0),new Vector(0,0,0),rate);
        double[][] temp = {{lookDirection.x}, {lookDirection.y}, {lookDirection.z}, {1}};
        Matrix tempmatrix = new Matrix(temp);
        tempmatrix=Matrix.matrixMulti(mt, tempmatrix);
        lookDirection.x = tempmatrix.value[0][0];
        lookDirection.y = tempmatrix.value[1][0];
        lookDirection.z = tempmatrix.value[2][0];
        directionReset();
    }

    public void directionReset(){
        Vector temp=new Vector(0,0,1);
        xDirection=Vector.crossProduct(temp, lookDirection);
        yDirection=Vector.crossProduct(xDirection, lookDirection);
        xDirection.normalization();
        yDirection.normalization();
    }
}

