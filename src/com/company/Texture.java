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
    public int getColor(int offsetx, int offsety){
        if(offsetx<0||offsety<0) return 0;
        offsetx%= width;
        offsety%= height;
        return rgbValue[offsetx][offsety];
    }
    public int getColor(double offsetx, double offsety){
        return getColor((int)offsetx,(int)offsety);
    }
}
