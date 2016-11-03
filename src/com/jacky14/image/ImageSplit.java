package com.jacky14.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageSplit {
	
	//需要切割的图片文件的路径
	public static String path = "C:\\Users\\Administrator\\Desktop\\srcimg\\";
	
	//切割后生成小图的文件的路径
	public static String outpath = "C:\\Users\\Administrator\\Desktop\\outimg\\";
	//切割的小图的大小
	public static int f_h = 134;
	public static int f_w = 120;
	
	public static String []fhz = new String[]{"jpg","png"};
	public static void main(String[] args) throws Exception {
		File file = new File(path);
		String [] fs = file.list();
		//获得需要处理的文件列表
		List<String> listfs = new ArrayList<String>();
		for(int i = 0 ;i<fs.length;i++){
			boolean isTag = false;
			String hz = fs[i].substring(fs[i].length()-3);
			for(int j=0;j<fhz.length;j++){
				if(fhz[j].equalsIgnoreCase(hz)){
					isTag = true;
					break;
				}
			}
			if(!isTag){
				continue;
			}
			listfs.add(fs[i]);
		}
		System.out.println(listfs);
		
		for(int i=0;i<listfs.size();i++){
			File tmp  = new File(path + listfs.get(i) ); 
			split(tmp);
		}
		
		
		
		/*File ffff = new File(path+"aa.png");
		if(!ffff.exists()){
			ffff.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(ffff);
		
		
		BufferedImage test = new BufferedImage(45, 45, BufferedImage.TYPE_4BYTE_ABGR);
		test.setRGB(4, 7, Color.red.getRGB());
		test.setRGB(9, 9, Color.yellow.getRGB());
		
		
		ImageIO.write(test, "png", ffff);*/
	}
	
	
	
	public static void split(File file) throws IOException{
		BufferedImage bi =  ImageIO.read(file);
		int w = bi.getWidth();
		int h = bi.getHeight();
		
		int h_chushu = h/f_h;
		int h_yushu = h%f_h;
		if(h_yushu!=0){
			h_chushu++;
		}
		
		int w_chushu = w/f_w;
		int w_yushu =  w%f_w;
		if(w_yushu!=0){
			w_chushu++;
		}
		
		
		
		
		BufferedImage [][] outbufs = new BufferedImage[h_chushu][w_chushu];
		
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				int hanghao = i/f_h;//当前处理像素单元的行号
				int liehao = j/f_w;
				
				int local_h = i%f_h;
				int local_w = j%f_w;
				if(outbufs[hanghao][liehao] == null){
					outbufs[hanghao][liehao] = new BufferedImage(f_w, f_h, BufferedImage.TYPE_3BYTE_BGR);
				}
				outbufs[hanghao][liehao].setRGB(local_w,local_h, bi.getRGB(j, i));
			}
		}
		
		
		for(int i=0;i<outbufs.length;i++){
			for(int j=0;j<outbufs[i].length;j++){
				File ffff = new File(outpath+file.getName().substring(0, file.getName().length()-4)+i+"_"+j+".jpg");
				if(!ffff.exists()){
					ffff.createNewFile();
				}
				ImageIO.write(outbufs[i][j], "jpg", ffff);
			}
		}
		
	}
	
}
