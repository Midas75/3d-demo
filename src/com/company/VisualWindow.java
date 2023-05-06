package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import static java.lang.Math.max;
import static java.lang.Math.round;

public class VisualWindow extends JFrame {
    int width, height;
    com.company.Canvas canvas;
    BufferedImage buffer;

    public VisualWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        width = 1024;
        height = 768;
        canvas = new Canvas();
        setSize(width, height);
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setVisible(true);
        add(canvas);
        setLocationRelativeTo(null);
        canvas.requestFocus();
    }

    public void flush() {
        canvas.flush(buffer);
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, int color) {
        if (x < 0 || y < 0 || x >= width || y >= height) return;
        buffer.setRGB(x, y, color);
    }

    public void putPixel(double x, double y, int color) {
        if (x < 0 || y < 0 || x >= width || y >= height) return;
        int ix = (int) x, iy = (int) y;
        buffer.setRGB(ix, iy, color);
    }

    public void putPixel(int x, int y, int r, int g, int b) {
        this.putPixel(x, y, getColor(r,g,b));
    }

    public void putPixel(int x, int y, double r, double g, double b) {
        this.putPixel(x, y, getColor(r, g, b));
    }

    public static int getColor(int r, int g, int b) {
        return  ((0 & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0);
    }

    public static int getColor(double r, double g, double b) {
        return getColor(round(r * 255), round(g * 255), round(b * 255));
    }
}
