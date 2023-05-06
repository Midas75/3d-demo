package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {
    public int width, height;
    public int[][] rgbValue;
    public Texture(String path){
        BufferedImage resource;
        try {
            resource =ImageIO.read(new File(path));
            width = resource.getWidth();
            height = resource.getHeight();
            rgbValue =new int[width][height];
            for(int i=0;i<width;i++){
                for(int j=0;j<height;j++){
                    rgbValue[i][j]=resource.getRGB(i,j);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getColor(int offsetX, int offsetY){
        if(offsetX<0||offsetY<0) return 0;
        offsetX%= width;
        offsetY%= height;
        return rgbValue[offsetX][offsetY];
    }
    public int getColor(double offsetX, double offsetY){
        return getColor((int)offsetX,(int)offsetY);
    }
}
