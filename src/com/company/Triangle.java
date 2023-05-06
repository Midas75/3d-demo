package com.company;


public class Triangle {
    public final com.company.Vertex[] vertex;
    private Vector normalVector;
    public boolean vectorIsNew;
    public com.company.Texture texture;
    public final double material;
    public Triangle(Vector v0, Vector v1, Vector v2) {
        vertex = new Vertex[]{new Vertex(v0), new Vertex(v1), new Vertex(v2)};
        vectorIsNew = false;
        material =128;
        normalVector =new Vector(0,0,0);
    }

    public Vector getNormalVector() {
        if (!vectorIsNew) {
            Vector v01= Vector.minus(vertex[0].vector,vertex[1].vector);
            Vector v02=Vector.minus(vertex[1].vector,vertex[2].vector);
            normalVector=Vector.crossProduct(v01,v02);
            normalVector.normalization();
        }
        vectorIsNew = true;
        return normalVector;
    }
    public void setVertex(int i, Double x,Double y,Double z,Integer tox,Integer toy,Integer color) {
        if (x != null) {
            vertex[i].vector.x = x;
            vectorIsNew = false;
        }
        if (y != null) {
            vertex[i].vector.y = y;
            vectorIsNew = false;
        }
        if (z != null) {
            vertex[i].vector.z = z;
            vectorIsNew = false;
        }
        if (tox != null) vertex[i].textureOffsetX = tox;
        if (toy != null) vertex[i].textureOffsetY = toy;
        if (color != null) vertex[i].color = color;
    }
}
