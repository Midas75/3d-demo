package com.company;

import static java.lang.Math.pow;

public class Light {
    public static int AMBIENT_LIGHT = 0, DIFFUSE_LIGHT = 1, HIGH_LIGHT = 2;
    public double strength;
    public Vector rgb;
    public Vector position;
    public int type;

    public Light(double strength, double r, double g, double b, double px, double py, double pz, int type) {
        this.strength = strength;
        rgb = new Vector(r, g, b);
        position = new Vector(px, py, pz);
        this.type = type;
    }

    public static void placeLightToBuffer(Buffer.BUnit bUnit, Light[] lights, Camera camera) {
        Vector res = new Vector(0, 0, 0);
        double k = Vector.dotProduct(bUnit.color, bUnit.color);
        k = (1 - k / 195075) * 0.5 + 1;
        for (Light light : lights) {
            Vector ls = new Vector(light.rgb);
            ls.multK(light.strength);
            if (light.type == AMBIENT_LIGHT) {
                Vector tv = new Vector(bUnit.color.x, bUnit.color.y, bUnit.color.z);
                tv.x *= ls.x;
                tv.y *= ls.y;
                tv.z *= ls.z;

                res.add(tv);
            } else if (light.type == DIFFUSE_LIGHT) {
                Vector tv = Vector.minus(light.position, bUnit.worldPosition);
                double dis = tv.x * tv.x + tv.y * tv.y + tv.z * tv.z;
                tv.normalization();
                double cosv = Vector.dotProduct(tv, bUnit.transformedTri.triReference.getNormalVector());
                if (cosv < 0) continue;
                cosv /= dis + 1;

                tv.set(bUnit.color.x, bUnit.color.y, bUnit.color.z);

                tv.x *= cosv * ls.x;
                tv.y *= cosv * ls.y;
                tv.z *= cosv * ls.z;

                res.add(tv);
            } else if (light.type == HIGH_LIGHT) {
                Vector vn = Vector.minus(camera.position, bUnit.worldPosition);

                Vector vl = Vector.minus(light.position, bUnit.worldPosition);

                double dis = Vector.dotProduct(vl, vl);
                vn.normalization();
                vl.normalization();
                Vector tv = Vector.add(vn, vl);
                tv.normalization();

                double cosv = Vector.dotProduct(tv, bUnit.transformedTri.triReference.getNormalVector());
                if (cosv < 0) continue;
                cosv = pow(cosv, bUnit.transformedTri.triReference.material);

                cosv /= dis + 1;

                tv.set(bUnit.color.x, bUnit.color.y, bUnit.color.z);

                tv.x *= cosv * ls.x;
                tv.y *= cosv * ls.y;
                tv.z *= cosv * ls.z;

                res.add(tv);
            }
            res.multK(k);
            bUnit.color.x = Math.min(res.x, 255);
            bUnit.color.y = Math.min(res.y, 255);
            bUnit.color.z = Math.min(res.z, 255);
        }
    }

    public static void placeLightToBuffer(Buffer buffer, int x, int y, Light[] lights, Camera camera) {
        Vector res = new Vector(0, 0, 0);
        double k = Vector.dotProduct(buffer.bUnitSet[x][y].color, buffer.bUnitSet[x][y].color);
        k = (1 - k / 195075) * 0.5 + 1;

        for (int i = 0; i < lights.length; i++) {
            Vector ls = new Vector(lights[i].rgb);
            ls.multK(lights[i].strength);
            if (lights[i].type == AMBIENT_LIGHT) {
                Vector tv = new Vector(buffer.bUnitSet[x][y].color.x, buffer.bUnitSet[x][y].color.y, buffer.bUnitSet[x][y].color.z);
                tv.x *= ls.x;
                tv.y *= ls.y;
                tv.z *= ls.z;

                res = Vector.add(tv, res);
            } else if (lights[i].type == DIFFUSE_LIGHT) {
                Vector tv = Vector.minus(lights[i].position, buffer.bUnitSet[x][y].worldPosition);
                double dis = tv.x * tv.x + tv.y * tv.y + tv.z * tv.z;
                tv.normalization();
                double cosv = Vector.dotProduct(tv, buffer.bUnitSet[x][y].transformedTri.triReference.getNormalVector());

                //cosv=1;
                if (cosv < 0) continue;

                //cosv=pow(cosv,0.5);
                //cosv=MyMath.invSqrt(MyMath.invSqrt(cosv));


                cosv /= dis + 1;

                tv.set(buffer.bUnitSet[x][y].color.x, buffer.bUnitSet[x][y].color.y, buffer.bUnitSet[x][y].color.z);

                tv.x *= cosv * ls.x;
                tv.y *= cosv * ls.y;
                tv.z *= cosv * ls.z;

                res = Vector.add(tv, res);

            } else if (lights[i].type == HIGH_LIGHT) {
                Vector vn = Vector.minus(camera.position, buffer.bUnitSet[x][y].worldPosition);

                Vector vl = Vector.minus(lights[i].position, buffer.bUnitSet[x][y].worldPosition);

                double dis = Vector.dotProduct(vl, vl);
                vn.normalization();
                vl.normalization();
                Vector tv = Vector.add(vn, vl);
                tv.normalization();

                double cosv = Vector.dotProduct(tv, buffer.bUnitSet[x][y].transformedTri.triReference.getNormalVector());
                if (cosv < 0) continue;
                cosv = pow(cosv, buffer.bUnitSet[x][y].transformedTri.triReference.material);

                cosv /= dis + 1;

                tv.set(buffer.bUnitSet[x][y].color.x, buffer.bUnitSet[x][y].color.y, buffer.bUnitSet[x][y].color.z);

                tv.x *= cosv * ls.x;
                tv.y *= cosv * ls.y;
                tv.z *= cosv * ls.z;

                res = Vector.add(tv, res);
            }
        }
        res.multK(k);
        buffer.bUnitSet[x][y].color.x = Math.min(res.x, 255);
        buffer.bUnitSet[x][y].color.y = Math.min(res.y, 255);
        buffer.bUnitSet[x][y].color.z = Math.min(res.z, 255);
    }
}
