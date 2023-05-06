package com.company;


import static java.lang.Math.*;

public class Matrix {
    public double[][] value;
    public Matrix(int r,int c){
        value=new double[r][c];
    }
    public Matrix(double[][] v){
        value=v;
    }
    public int getRow(){
        return value.length;
    }
    public int getColumn(){
        return value[0].length;
    }
    public static Matrix matrixMult(Matrix m1,Matrix m2){
        int r1=m1.getRow(),r2=m2.getRow(),c1=m1.getColumn(),c2=m2.getColumn();
        if(c1!=r2) return null;
        Matrix res=new Matrix(r1,c2);
        for(int i=0;i<r1;i++){
            for(int j=0;j<c2;j++){
                for(int k=0;k<c1;k++){
                    res.value[i][j]+=m1.value[i][k]*m2.value[k][j];
                }
            }
        }
        return res;
    }
    public static Matrix getTurnMatrix(Vector p,Vector d,Vector fp,double alpha){
        Matrix res=new Matrix(4,4);
        d.normalization();
        double ar = alpha * 3.1415926 / 180.0;
        double car = cos(ar), sar = sin(ar);
        double k = 1 - car;
        double m = Vector.dotProduct(d, fp);
        res.value[0][0] = d.x * d.x * k + car;
        res.value[0][1] = d.x * d.y * k - d.z * sar;
        res.value[0][2] = d.x * d.z * k + d.y * sar;
        res.value[0][3] = (fp.x - d.x * m) * k + (d.z * fp.y - d.y * fp.z) * sar;

        res.value[1][0] = d.x * d.y * k + d.z * sar;
        res.value[1][1] = d.y * d.y * k + car;
        res.value[1][2] = d.y * d.z * k - d.x * sar;
        res.value[1][3] = (fp.y - d.y * m) * k + (d.x * fp.z - d.z * fp.x) * sar;

        res.value[2][0] = d.x * d.z * k - d.y * sar;
        res.value[2][1] = d.y * d.z * k + d.x * sar;
        res.value[2][2] = d.z * d.z * k + car;
        res.value[2][3] = (fp.z - d.z * m) * k + (d.y * fp.x - d.x * fp.y) * sar;

        res.value[3][0] = 0;
        res.value[3][1] = 0;
        res.value[3][2] = 0;
        res.value[3][3] = 1;
        return res;
    }

    public static void turnRound(Vector p, Vector d, Vector fp, double alpha) {
        d.normalization();
        double ar = alpha * 3.1415926 / 180.0;
        double car = cos(ar), sar = sin(ar);
        double k = 1 - car;
        double m = Vector.dotProduct(d, fp);
        Matrix turnmatrix = getTurnMatrix(p,d,fp,alpha);

        double[][] temp = {{p.x}, {p.y}, {p.z}, {1}};
        Matrix tempmatrix = new Matrix(temp);
        tempmatrix=Matrix.matrixMult(turnmatrix, tempmatrix);
        p.x = tempmatrix.value[0][0];
        p.y = tempmatrix.value[1][0];
        p.z = tempmatrix.value[2][0];
    }

    public static Matrix getViewMatrix(Camera camera){
        Matrix m1=new Matrix(4,4),m2=new Matrix(4,4);
        m1.value[0][0]=1;
        m1.value[1][1]=1;
        m1.value[2][2]=1;
        m1.value[3][3]=1;

        m1.value[0][3]=-camera.position.x;
        m1.value[1][3]=-camera.position.y;
        m1.value[2][3]=-camera.position.z;

        m2.value[0][0]=camera.xDirection.x;
        m2.value[1][0]=camera.yDirection.x;
        m2.value[2][0]=camera.lookDirection.x;

        m2.value[0][1]=camera.xDirection.y;
        m2.value[1][1]=camera.yDirection.y;
        m2.value[2][1]=camera.lookDirection.y;

        m2.value[0][2]=camera.xDirection.z;
        m2.value[1][2]=camera.yDirection.z;
        m2.value[2][2]=camera.lookDirection.z;

        m2.value[3][3]=1;

        return matrixMult(m2,m1);
    }

    public static Matrix getProjectMatrix(Camera camera){
       Matrix res=new Matrix(4,4);
       double n=camera.near,f=n*2,t=n*Math.tan(Math.toRadians(camera.fov/2)),r=t*camera.aspect;
       res.value[0][0]=n/r;
       res.value[1][1]=n/t;
       res.value[2][2]=-(f+n)/(f-n);
       res.value[2][3]=-2*f*n/(f-n);
       res.value[3][2]=-1;
       return res;
    }
    public static void doWorldProjectTransform(Buffer buffer,Camera camera){
        Matrix mc=getViewMatrix(camera),mp=getProjectMatrix(camera);
        for(int i=0;i<buffer.transformedTriSet.length;i++){
            Vector[] p=buffer.transformedTriSet[i].transformedVector;
            for(int j=0;j<p.length;j++){
                Matrix txyz=new Matrix(4,1);
                txyz.value[0][0]=p[j].x;
                txyz.value[1][0]=p[j].y;
                txyz.value[2][0]=p[j].z;
                txyz.value[3][0]=1;

                txyz=matrixMult(mc,txyz);
                txyz=matrixMult(mp,txyz);

                txyz.value[0][0]*=camera.rate/txyz.value[3][0]*buffer.width;
                txyz.value[1][0]*=camera.rate/txyz.value[3][0]*buffer.height;
                txyz.value[2][0]*=camera.rate/txyz.value[3][0];
                p[j].x=txyz.value[0][0]+buffer.width/2;
                p[j].y=txyz.value[1][0]+buffer.height/2;
                p[j].z=txyz.value[2][0];
                buffer.transformedTriSet[i].w[j]=txyz.value[3][0];
            }
        }
    }
}

