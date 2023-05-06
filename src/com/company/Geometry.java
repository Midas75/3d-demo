package com.company;

public class Geometry {
    public Vector[] vectorSet;
    public Triangle[] triangleSet;
    public Line[] LineSet;
    public static enum Shape{
        CUBE,CUBE_PURE
    }
    public Geometry(Shape shape) {
        if (shape == Shape.CUBE) {
            vectorSet = new Vector[]{
                    new Vector(-50, -50, -50),
                    new Vector(50, -50, -50),
                    new Vector(50, 50, -50),
                    new Vector(-50, 50, -50),

                    new Vector(-50, 50, 50),
                    new Vector(-50, -50, 50),
                    new Vector(50, -50, 50),
                    new Vector(50, 50, 50)
            };
            int[] color=new int[]{0x000000,0x0000ff,0x00ff00,0x00fffff,0xff0000,0xff00ff,0xffff00,0xffffff};
            Line[] lineSet=new Line[]{
                    new Line(new Vertex(vectorSet[0],color[0]),new Vertex(vectorSet[1],color[1])),
                    new Line(new Vertex(vectorSet[1],color[1]),new Vertex(vectorSet[2],color[2])),
                    new Line(new Vertex(vectorSet[2],color[2]),new Vertex(vectorSet[3],color[3])),
                    new Line(new Vertex(vectorSet[3],color[3]),new Vertex(vectorSet[0],color[0])),

                    new Line(new Vertex(vectorSet[4],color[4]),new Vertex(vectorSet[5],color[5])),
                    new Line(new Vertex(vectorSet[5],color[5]),new Vertex(vectorSet[6],color[6])),
                    new Line(new Vertex(vectorSet[6],color[6]),new Vertex(vectorSet[7],color[7])),
                    new Line(new Vertex(vectorSet[7],color[7]),new Vertex(vectorSet[4],color[4])),

                    new Line(new Vertex(vectorSet[0],color[0]),new Vertex(vectorSet[5],color[5])),
                    new Line(new Vertex(vectorSet[1],color[1]),new Vertex(vectorSet[6],color[6])),
                    new Line(new Vertex(vectorSet[2],color[2]),new Vertex(vectorSet[7],color[7])),
                    new Line(new Vertex(vectorSet[3],color[3]),new Vertex(vectorSet[4],color[4])),
            };
            Texture texture1=new Texture("src/com/company/brick.png");
            Texture texture2=new Texture("src/com/company/coarse_dirt.png");
            triangleSet = new Triangle[]{
                    new Triangle(vectorSet[0], vectorSet[3], vectorSet[2]),
                    new Triangle(vectorSet[0], vectorSet[2], vectorSet[1]),

                    new Triangle(vectorSet[2], vectorSet[3], vectorSet[4]),
                    new Triangle(vectorSet[2], vectorSet[4], vectorSet[7]),

                    new Triangle(vectorSet[4], vectorSet[3], vectorSet[0]),
                    new Triangle(vectorSet[4], vectorSet[0], vectorSet[5]),

                    new Triangle(vectorSet[6], vectorSet[1], vectorSet[2]),
                    new Triangle(vectorSet[6], vectorSet[2], vectorSet[7]),

                    new Triangle(vectorSet[0], vectorSet[1], vectorSet[6]),
                    new Triangle(vectorSet[0], vectorSet[6], vectorSet[5]),

                    new Triangle(vectorSet[6], vectorSet[7], vectorSet[4]),
                    new Triangle(vectorSet[6], vectorSet[4], vectorSet[5])
            };
            for (int i = 0; i < 12; i += 2) {
                triangleSet[i].setVertex(0, null, null, null, 0, 0, 0xFF7F4F);
                triangleSet[i].setVertex(1, null, null, null, 128, 0, 0xFF7F4F);
                triangleSet[i].setVertex(2, null, null, null, 128, 128, 0xFF7F4F);

                triangleSet[i+1].setVertex(0, null, null, null, 0, 0, 0xFF7F4F);
                triangleSet[i+1].setVertex(1, null, null, null, 128, 128, 0xFF7F4F);
                triangleSet[i+1].setVertex(2, null, null, null, 0, 128, 0xFF7F4F);
            }
            for(int i=0;i<6;i++)triangleSet[i].texture=texture1;
            for(int i=6;i<12;i++)triangleSet[i].texture=texture2;
        }if (shape == Shape.CUBE_PURE) {
            vectorSet = new Vector[]{
                    new Vector(-50, -50, -50),
                    new Vector(50, -50, -50),
                    new Vector(50, 50, -50),
                    new Vector(-50, 50, -50),

                    new Vector(-50, 50, 50),
                    new Vector(-50, -50, 50),
                    new Vector(50, -50, 50),
                    new Vector(50, 50, 50)
            };
            int[] color=new int[]{0x000000,0x0000ff,0x00ff00,0x00fffff,0xff0000,0xff00ff,0xffff00,0xffffff};
            Line[] lineSet=new Line[]{
                    new Line(new Vertex(vectorSet[0],color[0]),new Vertex(vectorSet[1],color[1])),
                    new Line(new Vertex(vectorSet[1],color[1]),new Vertex(vectorSet[2],color[2])),
                    new Line(new Vertex(vectorSet[2],color[2]),new Vertex(vectorSet[3],color[3])),
                    new Line(new Vertex(vectorSet[3],color[3]),new Vertex(vectorSet[0],color[0])),

                    new Line(new Vertex(vectorSet[4],color[4]),new Vertex(vectorSet[5],color[5])),
                    new Line(new Vertex(vectorSet[5],color[5]),new Vertex(vectorSet[6],color[6])),
                    new Line(new Vertex(vectorSet[6],color[6]),new Vertex(vectorSet[7],color[7])),
                    new Line(new Vertex(vectorSet[7],color[7]),new Vertex(vectorSet[4],color[4])),

                    new Line(new Vertex(vectorSet[0],color[0]),new Vertex(vectorSet[5],color[5])),
                    new Line(new Vertex(vectorSet[1],color[1]),new Vertex(vectorSet[6],color[6])),
                    new Line(new Vertex(vectorSet[2],color[2]),new Vertex(vectorSet[7],color[7])),
                    new Line(new Vertex(vectorSet[3],color[3]),new Vertex(vectorSet[4],color[4])),
            };
            Texture texture1=new Texture("src/com/company/stone_andesite_smooth.png");
            Texture texture2=new Texture("src/com/company/stone_diorite.png");
            triangleSet = new Triangle[]{
                    new Triangle(vectorSet[0], vectorSet[3], vectorSet[2]),
                    new Triangle(vectorSet[0], vectorSet[2], vectorSet[1]),

                    new Triangle(vectorSet[2], vectorSet[3], vectorSet[4]),
                    new Triangle(vectorSet[2], vectorSet[4], vectorSet[7]),

                    new Triangle(vectorSet[4], vectorSet[3], vectorSet[0]),
                    new Triangle(vectorSet[4], vectorSet[0], vectorSet[5]),

                    new Triangle(vectorSet[6], vectorSet[1], vectorSet[2]),
                    new Triangle(vectorSet[6], vectorSet[2], vectorSet[7]),

                    new Triangle(vectorSet[0], vectorSet[1], vectorSet[6]),
                    new Triangle(vectorSet[0], vectorSet[6], vectorSet[5]),

                    new Triangle(vectorSet[6], vectorSet[7], vectorSet[4]),
                    new Triangle(vectorSet[6], vectorSet[4], vectorSet[5])
            };
            for (int i = 0; i < 12; i += 2) {
                triangleSet[i].setVertex(0, null, null, null, 0, 0, 0x0000ff);
                triangleSet[i].setVertex(1, null, null, null, 128, 0, 0x00ff00);
                triangleSet[i].setVertex(2, null, null, null, 128, 128, 0xff0000);

                triangleSet[i+1].setVertex(0, null, null, null, 0, 0, 0x0000ff);
                triangleSet[i+1].setVertex(1, null, null, null, 128, 128, 0xff0000);
                triangleSet[i+1].setVertex(2, null, null, null, 0, 128, 0x00ff00);
            }
            for(int i=0;i<6;i++)triangleSet[i].texture=texture1;
            for(int i=6;i<12;i++)triangleSet[i].texture=texture2;
        }
    }
}
