package com.company;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Buffer {
    public static class TransformedTri {
        public Triangle triReference;
        public Vector[] transformedVector;
        public double[] w;
    }
    public static class BUnit {
        public double zValue;
        public TransformedTri transformedTri;
        final public Vector color;
        final public Vector worldPosition;
        public double a, b, c;
        public BUnit() {
            zValue = Double.MAX_VALUE;
            color = new Vector(0, 0, 0);
            worldPosition = new Vector(0, 0, 0);
            a = 0;
            b = 0;
            c = 0;
        }
    }

    final public TransformedTri[] transformedTriSet;
    public final BUnit[][] bUnitSet;
    final public int width, height;
    public int minX, maxX, minY, maxY;

    public Buffer(int width, int height, Triangle[] triangles) {
        this.width = width;
        this.height = height;
        bUnitSet = new BUnit[this.width][this.height];
        transformedTriSet = new TransformedTri[triangles.length];
        for (int i = 0; i < triangles.length; i++) {
            transformedTriSet[i]= new TransformedTri();
            transformedTriSet[i].triReference = triangles[i];
            transformedTriSet[i].w=new double[3];
            transformedTriSet[i].transformedVector = new Vector[]{
                    new Vector(triangles[i].vertex[0].vector),
                    new Vector(triangles[i].vertex[1].vector),
                    new Vector(triangles[i].vertex[2].vector),};
        }
        minX = this.width;
        maxX = 0;
        minY = this.height;
        maxY = 0;
    }

    public double[] interpolationZ(double px, double py, TransformedTri t) {
        double[] res = {0, 0, 0, 0};
        double a, b, c;
        Vector[] tv=new Vector[]{
                t.transformedVector[0],
                t.transformedVector[1],
                t.transformedVector[2]
        };

        a = -(px - tv[1].x) * (tv[2].y - tv[1].y) + (py - tv[1].y) * (tv[2].x - tv[1].x);
        a /= -(tv[0].x - tv[1].x) * (tv[2].y - tv[1].y) + (tv[0].y - tv[1].y) * (tv[2].x - tv[1].x);

        b = -(px - tv[2].x) * (tv[0].y - tv[2].y) + (py - tv[2].y) * (tv[0].x - tv[2].x);

        b /= -(tv[1].x - tv[2].x) * (tv[0].y - tv[2].y) + (tv[1].y - tv[2].y) * (tv[0].x - tv[2].x);

        c = 1 - a - b;
        if(c<0) c=0;

        res[3] = a*tv[0].z+b*tv[1].z+c*tv[2].z;
        double z=a/t.w[0]+b/t.w[1]+c/t.w[2];
        a/=z*t.w[0];
        b/=z*t.w[1];
        c/=z*t.w[2];

        res[0] = a;
        res[1] = b;
        res[2] = c;


        //res[3]=a*t.w[0]+b*t.w[1]+c*t.w[2];

        return res;
    }
    public void interpolationBuffer(Camera camera){
        for(int i=minX;i<maxX;i++){
            for(int j=minY;j<maxY;j++){
                BUnit bunit= bUnitSet[i][j];
                if(bunit==null||bunit.transformedTri==null) continue;
                Vertex[] tv=bunit.transformedTri.triReference.vertex;
                bunit.worldPosition.x =bunit.a * tv[0].vector.x + bunit.b * tv[1].vector.x + bunit.c * tv[2].vector.x;
                bunit.worldPosition.y =bunit.a * tv[0].vector.y + bunit.b * tv[1].vector.y + bunit.c * tv[2].vector.y;
                bunit.worldPosition.z =bunit.a * tv[0].vector.z + bunit.b * tv[1].vector.z + bunit.c * tv[2].vector.z;
                if((camera.obMode&Camera.SHOW_TEXTURE)!=0){
                    int tox, toy;
                    tox = (int) (bunit.a * tv[0].textureOffsetX + bunit.b * tv[1].textureOffsetX + bunit.c * tv[2].textureOffsetX);
                    toy = (int) (bunit.a * tv[0].textureOffsetY + bunit.b * tv[1].textureOffsetY + bunit.c * tv[2].textureOffsetY);

                    int tColor = bunit.transformedTri.triReference.texture.getColor(tox, toy);
                    int[] color = new int[]{(tColor >> 16)&0xFF, (tColor>>8)&0xFF, tColor & 0xFF};

                    bunit.color.x = color[0];
                    bunit.color.y = color[1];
                    bunit.color.z = color[2];
                }else{
                    int[] r=new int[]{(tv[0].color>>16)&0xFF,(tv[1].color>>16)&0xFF,(tv[2].color>>16)&0xFF};
                    int[] g=new int[]{(tv[0].color>>8)&0xFF,(tv[1].color>>8)&0xFF,(tv[2].color>>8)&0xFF};
                    int[] b=new int[]{(tv[0].color)&0xFF,(tv[1].color)&0xFF,(tv[2].color)&0xFF};

                    bunit.color.x = bunit.a*r[0]+bunit.b*r[1]+bunit.c*r[2];
                    bunit.color.y = bunit.a*g[0]+bunit.b*g[1]+bunit.c*g[2];
                    bunit.color.z = bunit.a*b[0]+bunit.b*b[1]+bunit.c*b[2];
                }

            }
        }
    }
    public static int judge(Vector[] p, int px, int py) {
        int[] vx = new int[3], vy = new int[3];
        for (int i = 0; i < 3; i++) {
            vx[i] = px - (int)p[i].x;
            vy[i] = py - (int)p[i].y;
        }
        int p_sign = 0, n_sign = 0;
        int[] order = new int[]{0, 1, 1, 2, 2, 0};
        for (int i = 0; i < 3; i++) {
            int temp = vx[order[i * 2]] * vy[order[i * 2 + 1]] - vx[order[i * 2 + 1]] * vy[order[i * 2]];
            if (temp > 0) p_sign += 1;
            if (temp < 0) n_sign += 1;
        }
        if(p_sign==3||n_sign==3) return 1;
        else if (p_sign == 0 || n_sign == 0) return 0;
        return -1;
    }
    public void DDALine(int x0, int y0,int r0,int g0,int b0, int x1, int y1,int r1,int g1,int b1) {
        double dx, dy,dr,dg,db,e, x, y,r,g,b;
        dx = x1 - x0;
        dy = y1 - y0;
        dr=r1-r0;
        dg=g1-g0;
        db=b1-b0;
        e = max(abs(dx), abs(dy));
        if(e==0) return;
        dx /= e;dy /= e;dr/=e;dg/=e;db/=e;
        x = x0;y = y0;r=r0;g=g0;b=b0;
        for (int i = 0; i < e; i++) {
            x += dx;y += dy;
            r+=dr;g+=dg;b+=db;
            if(x>=maxX||y>=maxY||x<0||y<0) continue;
            int j=(int)x,k=(int)y;
            if(bUnitSet[j][k]==null){
                bUnitSet[j][k]=new BUnit();
                bUnitSet[j][k].color.x=r;
                bUnitSet[j][k].color.y=g;
                bUnitSet[j][k].color.z=b;
            }
        }
    }

    public void DDALine(int x0, int y0,int c0, int x1, int y1,int c1) {
        DDALine(x0,y0,(c0>>16)&0xff,(c0>>8)&0xff,(c0)&0xff,x1,y1,(c1>>16)&0xff,(c1>>8)&0xff,(c1)&0xff);
    }
    public void setBuffer(Camera camera) {
        Matrix.doWorldProjectTransform(this, camera);
        if ((camera.obMode & Camera.SHOW_LINE) != 0) {
            for (TransformedTri transformedTri : transformedTriSet) {
                double
                        minx = transformedTri.transformedVector[0].x,
                        maxx = transformedTri.transformedVector[0].x,
                        miny = transformedTri.transformedVector[0].y,
                        maxy = transformedTri.transformedVector[0].y;
                for (int j = 1; j < 3; j++) {
                    minx = Math.min(minx, transformedTri.transformedVector[j].x);
                    maxx = Math.max(maxx, transformedTri.transformedVector[j].x);
                    miny = Math.min(miny, transformedTri.transformedVector[j].y);
                    maxy = Math.max(maxy, transformedTri.transformedVector[j].y);
                }
                minX = Math.min(minX, (int) minx);
                minX = Math.max(minX, 0);
                maxX = Math.max(maxX, (int) maxx);
                maxX = Math.min(maxX, width);
                minY = Math.min(minY, (int) miny);
                minY = Math.max(minY, 0);
                maxY = Math.max(maxY, (int) maxy);
                maxY = Math.min(maxY, height);

                DDALine((int) transformedTri.transformedVector[0].x, (int) transformedTri.transformedVector[0].y, transformedTri.triReference.vertex[0].color,
                        (int) transformedTri.transformedVector[1].x, (int) transformedTri.transformedVector[1].y, transformedTri.triReference.vertex[1].color);
                DDALine((int) transformedTri.transformedVector[0].x, (int) transformedTri.transformedVector[0].y, transformedTri.triReference.vertex[0].color,
                        (int) transformedTri.transformedVector[2].x, (int) transformedTri.transformedVector[2].y, transformedTri.triReference.vertex[2].color);
                DDALine((int) transformedTri.transformedVector[1].x, (int) transformedTri.transformedVector[1].y, transformedTri.triReference.vertex[1].color,
                        (int) transformedTri.transformedVector[2].x, (int) transformedTri.transformedVector[2].y, transformedTri.triReference.vertex[2].color);
            }
        }
        if ((camera.obMode & Camera.SHOW_TRIANGLE) != 0) {
            for (TransformedTri transformedTri : transformedTriSet) {
                if (Vector.dotProduct(transformedTri.triReference.getNormalVector(), camera.lookDirection) < 0)
                    continue;
                double
                        minx = transformedTri.transformedVector[0].x,
                        maxx = transformedTri.transformedVector[0].x,
                        miny = transformedTri.transformedVector[0].y,
                        maxy = transformedTri.transformedVector[0].y;
                for (int j = 1; j < 3; j++) {
                    minx = Math.min(minx, transformedTri.transformedVector[j].x);
                    maxx = Math.max(maxx, transformedTri.transformedVector[j].x);
                    miny = Math.min(miny, transformedTri.transformedVector[j].y);
                    maxy = Math.max(maxy, transformedTri.transformedVector[j].y);
                }
                minX = Math.min(minX, (int) minx);
                minX = Math.max(minX, 0);
                maxX = Math.max(maxX, (int) maxx);
                maxX = Math.min(maxX, width);
                minY = Math.min(minY, (int) miny);
                minY = Math.max(minY, 0);
                maxY = Math.max(maxY, (int) maxy);
                maxY = Math.min(maxY, height);


                for (int j = Math.max((int) minx, 0); j < Math.min((int) maxx, width); j++) {
                    for (int k = Math.max((int) miny, 0); k < Math.min((int) maxy, height); k++) {
                        int v = judge(transformedTri.transformedVector, j, k);
                        if (v == 1 || v == 0) {
                            double[] zRes = interpolationZ(j, k, transformedTri);
                            if (zRes[3] < 0) continue;
                            if (bUnitSet[j][k] == null) {
                                bUnitSet[j][k] = new BUnit();
                            }
                            if (bUnitSet[j][k].zValue >= zRes[3]) {
                                bUnitSet[j][k].zValue = zRes[3];
                                bUnitSet[j][k].a = zRes[0];
                                bUnitSet[j][k].b = zRes[1];
                                bUnitSet[j][k].c = zRes[2];
                                bUnitSet[j][k].transformedTri = transformedTri;
                            }
                        }
                    }
                }
            }
        }
    }
}
