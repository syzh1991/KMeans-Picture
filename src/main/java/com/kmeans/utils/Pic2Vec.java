package com.kmeans.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Pic2Vec {
	public static Vector getImageGRB(String filePath) {
        File file  = new File(filePath);
        Vector result = null;
        if (!file.exists()) {
             return result;
        }
        try {
             BufferedImage bufImg = ImageIO.read(file);
             int height = bufImg.getHeight();
             int width = bufImg.getWidth();
             result = new Vector(height * width);
             for (int i = 0; i < width; i++) {
                  for (int j = 0; j < height; j++) {
                        int r = (bufImg.getRGB(i, j) & 0xFF0000) >> 16;
                        int g = (bufImg.getRGB(i, j) & 0xFF00) >> 8;
                        int b = (bufImg.getRGB(i, j) & 0xFF);
                        
                        result.add(r + "," + g + "," + b);
                  }
             }  
        } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
        }
        return result;
  }

	public static void main(String[] args){
		Pic2Vec p = new Pic2Vec();
		Vector a = p.getImageGRB("im.jpg");
		for(int i = 0; i < a.size(); i++){
			System.out.println(a.get(i));
		}
		
	}
}
