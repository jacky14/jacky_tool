package com.third.sometool2015;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Pngd {

	
	public static String source = "F:\\TEMP\\png\\";
	public static String out = "F:\\TEMP\\pngd\\";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File filp = new File(source);
		
		File[] lsf =  filp.listFiles();
		for(int i=0;i<lsf.length;i++){
			dpng(lsf[i]);
		}
		
		
		
		//dpng(new File("C:\\Users\\Administrator\\Desktop\\png\\128_72_72"));
		
		
		
	}

	public static void dpng(File pf ) throws Exception{
		
		
	
		FileInputStream fi = new FileInputStream(pf);
	
		String []ttmp = pf.getName().split("_");
		byte[] be = new byte[fi.available()];
		fi.read(be);
		fi.close();
		
		int width = Integer.parseInt(ttmp[1]);
		int height = Integer.parseInt(ttmp[2]);
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);
		
		int rgbas[] = new int [width*height];
		for(int i=0;i<be.length/4;i++){
			int b= (be[i*4 + 0] << 24) ;
			int g= (be[i*4 + 1] << 24) ;
			int r= (be[i*4 + 2] << 24) ;
			int a= (be[i*4 + 3] << 24) ;
			rgbas[i] = a  + (b>>>8) +( g>>>16 )+ (r>>>24);
		}
		image.setRGB(0, 0, width, height, rgbas, 0, width);
		File file2 =new File(out+pf.getName()+".png");  
		file2.createNewFile();
		ImageIO.write(image, "png", file2); 
	}
	
	
}
