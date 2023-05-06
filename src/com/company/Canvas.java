package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {
    private static final long serialVersionUID=1L;
    public BufferedImage buffer;
    public Canvas(){
    }
    public void paint(Graphics g){
        if(buffer !=null){
            g.drawImage(buffer,0,0,null);
        }
    }
    public void flush(BufferedImage buffer){
        this.buffer =buffer;
        paint(getGraphics());
    }
}
