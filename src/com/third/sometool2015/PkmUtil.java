package com.third.sometool2015;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PkmUtil {

	
	public static String rootpath = "C:/Users/Administrator/Desktop/test/assets/";
	public static String outpath = "D:/ddata/";
	public static String spath = "D:/sdata/";
	
	
	public static List<File> list = new ArrayList<File>();
	
	//etcpack C:\Users\Administrator\Desktop\lzy_65.pkm d:\ -ext PNG
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		File fe = new File(rootpath);
		find(fe);
		gendpkm();
		
		//etcpack D:\ddata\zxb005.pkm d:\sdata -ext JPG
		//etcpack D:\ddata\1000.pkm d:\sdata -ext PNG
		/*Process p  = Runtime.getRuntime().exec("etcpack.exe D:\\ddata\\1000.pkm D:\\sdata -ext PNG ");
		Scanner sc = new Scanner(p.getInputStream());
		while(sc.hasNext()){
			System.out.println(sc.nextLine());
		}*/
		/*File file = new File(outpath);
		File[] fi = file.listFiles();
		for(int i=0;i<fi.length;i++){
			System.out.println("etcpack D:\\ddata\\"+fi[i].getName()+" d:\\sdata -ext JPG");
			
		}*/
		
		
	}
	
	public static void execdpkm() throws Exception{
		File fe = new File(outpath);
		File[] fs = fe.listFiles();
		
		Process p  = Runtime.getRuntime().exec(" cmd \r");
		OutputStream out =  p.getOutputStream();
		out.write("cd D:\\Program Files\\pkmtool\\bin \r".getBytes());
		//out.write(("etcpack "+outpath++" "+spath+" -ext PNG \r").getBytes());
	}
	
	
	public static void gendpkm() throws Exception{
		for(int i=0;i<list.size();i++){
			File file = list.get(i);
			FileInputStream fi =new FileInputStream(file);
			byte[] bdata = new byte[fi.available()];
			fi.read(bdata);
			fi.close();
			
			byte []tmp = new byte[bdata.length - 12];
			for(int j=0;j<tmp.length;j++){
				tmp[j] = bdata[j+12];
			}
			tmp[0] = -119;
			File fdd = new File(outpath +file.getName() );
			if(fdd.exists()){
				System.out.println("出现重名文件："+file.getName());
				fdd = new File(outpath +file.getName()+"_1" );
			}
			fdd.createNewFile();
			FileOutputStream fo = new FileOutputStream(fdd);
			fo.write(tmp);
			fo.close();
		}
	}
	public static void find(File file){
		File [] files =  file.listFiles();
		for(int i=0;i<files.length;i++){
			boolean isdir = files[i].isDirectory();
			if(isdir){
				find(files[i]);
			}else{
				String fnm =files[i].getName(); 
				if(".png".equals(fnm.substring(fnm.length()-4))){
					list.add(files[i]);
				}
			}
		}
	}
	
	public static void mainsdf(String[] adf) throws Exception{
		// TODO Auto-generated method stub
				File file = new File("C:\\Users\\Administrator\\Desktop\\fishres_fish7.png");
				FileInputStream fi =new FileInputStream(file);
				
				
				byte[] bdata = new byte[fi.available()];
				fi.read(bdata);
				fi.close();
				byte []tmp = new byte[bdata.length - 12];
				for(int i=0;i<tmp.length;i++){
					tmp[i] = bdata[i+12];
				}
				tmp[0] = -119;
				File fdd = new File("d:/1");
				fdd.createNewFile();
				FileOutputStream fo = new FileOutputStream(fdd);
				fo.write(tmp);
				fo.close();
				
				System.out.println(tmp);
		
	}
}
