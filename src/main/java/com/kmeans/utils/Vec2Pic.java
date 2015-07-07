package com.kmeans.utils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class Vec2Pic {

	/**
	 * @param matrix
	 *            ����
	 * @param filedir
	 *            �ļ�·������,d:\\test.jpg
	 * @throws IOException
	 */
	public static void createMatrixImage(int[][] matrix, String filedir)
			throws IOException {
		int cy = matrix.length;
		int cx = matrix[0].length;
		// �����θ߿�
		int cz = 10;
		// ���ͼ�Ŀ��
		int width = cx * cz;
		// ���ͼ�ĸ߶�
		int height = cy * cz;

		OutputStream output = new FileOutputStream(new File(filedir));
		BufferedImage bufImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gs = bufImg.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.clearRect(0, 0, width, height);

		gs.setColor(Color.BLACK);
		for (int i = 0; i < cx; i++) {
			for (int j = 0; j < cy; j++) {
				// 1�������ھ���
				if (matrix[j][i] == 1) {
					gs.drawRect(i * cz, j * cz, cz, cz);
					gs.fillRect(i * cz, j * cz, cz, cz);
				}
			}
		}
		gs.dispose();
		bufImg.flush();
		// ����ļ�
		ImageIO.write(bufImg, "jpeg", output);

	}
/*
	public static int[][] read2vec(String filepath){
    	File file = new File(filepath);
    	int result[][] = null;
    	if(!file.exists()){
    		return result;
    	}
    	BufferedReader br = null;

    	try {
    		//����BufferedReader����
    		result = new int[240][360];
    		br = new BufferedReader(new FileReader(file));

    		String line = null;
    		int i = 1;
    		int j = 0,k=0;
    		while ((line = br.readLine()) != null) {

    			if(i%240 != 0){
    				result[j][k] = Integer.parseInt(line);
    				j++;
    			}
    			else{
    				result[j][k] = Integer.parseInt(line);
    				k++;
    				j=0;
    			}
    			i++;
    		}
    	}
    	catch (IOException e) {
    		e.printStackTrace();
    	}
    	finally {

    	//�ر�BufferedReader
    		if (br != null) {
    			try {
    				br.close();
    			}
    			catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	return result;
    }
	*/
	public static int[][] read2vec(int[] a){
		int[][] result = new int[240][360];
		int j = 0,k=0;
    	for(int i = 0; i < a.length; i++){
    		
    		if((i+1)%240 != 0){
				result[j][k] = a[i];
				j++;
			}
			else{
				result[j][k] = a[i];
				k++;
				j=0;
			}
    	}
    	return result;
    }

	public static void main(String[] args) throws Exception {
		// ����
		Random rand =new Random(2);
		int a[] = new int[240*360];
		for(int i = 0; i < 240*360; i++){
			a[i] = rand.nextInt(2)+1;
		}
		int[][] matrix = read2vec(a);

		Vec2Pic.createMatrixImage(matrix, "test.jpg");
	}

}