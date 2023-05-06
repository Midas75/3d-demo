package com.company;

public class Vertex {
    public int textureOffsetX, textureOffsetY, color;
    public final Vector vector;
    public Vertex(Vector v){
        vector=v;
        textureOffsetX=0;
        textureOffsetY=0;
        color=VisualWindow.getColor(0xff,0xff,0xff);
    }
    public Vertex(Vector v,int r,int g,int b){
        vector=v;
        textureOffsetX=0;
        textureOffsetY=0;
        color=VisualWindow.getColor(r,g,b);
    }
    public Vertex(Vector v,int c){
        vector=v;
        textureOffsetX=0;
        textureOffsetY=0;
        color=c;
    }
}
