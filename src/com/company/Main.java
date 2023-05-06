package com.company;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main {

    public static void main(String[] args) {
        VisualWindow visualwindow = new VisualWindow();
        Light[] lightSet = new Light[3];
        lightSet[0] = new Light(0.4, 1, 1, 1, 0, 100, 100, Light.AMBIENT_LIGHT);
        lightSet[1] = new Light(6000.0, 1, 1, 1, 80, 80, 0, Light.DIFFUSE_LIGHT);
        lightSet[2] = new Light(12000.0, 1, 1, 1, 80, 80, 0, Light.HIGH_LIGHT);

        Geometry geometry = new Geometry(Geometry.Shape.CUBE);

        Camera camera = new Camera(Camera.Type.CLASSIC);

        visualwindow.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                visualwindow.canvas.requestFocus();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        visualwindow.canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'd') {
                    for (int i = 0; i < geometry.vectorSet.length; i++) {
                        Matrix.turnRound(geometry.vectorSet[i], new Vector(0, 0, 1), new Vector(0, 0, 0), 1.2);
                    }
                } else if (e.getKeyChar() == 'a') {
                    for (int i = 0; i < geometry.vectorSet.length; i++) {
                        Matrix.turnRound(geometry.vectorSet[i], new Vector(0, 0, 1), new Vector(0, 0, 0), -1.2);
                    }
                } else if (e.getKeyChar() == 'w') {
                    for (int i = 0; i < geometry.vectorSet.length; i++) {
                        Matrix.turnRound(geometry.vectorSet[i], new Vector(-1, 1, 0), new Vector(0, 0, 0), -1.2);
                    }
                } else if (e.getKeyChar() == 's') {
                    for (int i = 0; i < geometry.vectorSet.length; i++) {
                        Matrix.turnRound(geometry.vectorSet[i], new Vector(-1, 1, 0), new Vector(0, 0, 0), 1.2);
                    }
                } else if (e.getKeyChar() == 't') {
                    camera.obMode^=Camera.SHOW_TRIANGLE;
                    camera.obMode^=Camera.SHOW_LINE;
                }else if (e.getKeyChar() == 'r') {
                    camera.obMode^=Camera.SHOW_TEXTURE;
                }else if (e.getKeyChar() == 'l') {
                    camera.obMode^=Camera.SHOW_LIGHT;
                }else if(e.getKeyCode()==37){
                    camera.turnLeft(1);
                }else if(e.getKeyCode()==38){
                    camera.turnUp(1);
                }else if(e.getKeyCode()==39){
                    camera.turnLeft(-1);
                }else if(e.getKeyCode()==40){
                    camera.turnUp(-1);
                }else if(e.getKeyChar()=='q'){
                    camera.goForward(2);
                }else if(e.getKeyChar()=='e'){
                    camera.goForward(-2);
                }else if(e.getKeyChar()=='b'){
                    lightSet[1].position=new Vector(80,80,0);
                    lightSet[2].position=new Vector(80,80,0);
                }else if(e.getKeyChar()=='v'){
                    lightSet[1].position=new Vector(0,80,80);
                    lightSet[2].position=new Vector(0,80,80);
                }
                for (int i = 0; i < geometry.triangleSet.length; i++) {
                    geometry.triangleSet[i].vectorIsNew = false;
                }
                Draw.drawAll(camera, geometry, lightSet, visualwindow, true);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        Draw.drawAll(camera, geometry, lightSet, visualwindow, true);

        if (true)
            while (true) {
                for (int i = 0; i < geometry.vectorSet.length; i++) {
                    Matrix.turnRound(geometry.vectorSet[i], new Vector(0, 0, 1), new Vector(0, 0, 0), 0.8);
                    Matrix.turnRound(geometry.vectorSet[i], new Vector(0, 1, 0), new Vector(0, 0, 0), 1.0);
                }
                for (int i = 0; i < geometry.triangleSet.length; i++) {
                    geometry.triangleSet[i].vectorIsNew = false;
                }
                Draw.drawAll(camera, geometry, lightSet, visualwindow, true);
            }
    }
}
