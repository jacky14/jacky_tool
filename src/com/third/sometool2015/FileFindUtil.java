package com.third.sometool2015;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileFindUtil {

	public static List<File> list = new ArrayList<File>();
	public static String houzui = "";
	public static int fl = 0;
	
	
	public static  List<File>  select(String rootPath){
		File file = new File(rootPath);
		fl = houzui.length();
		find(file);
		return list;
	}
	private static void find(File file){
		File [] files =  file.listFiles();
		for(int i=0;i<files.length;i++){
			boolean isdir = files[i].isDirectory();
			if(isdir){
				find(files[i]);
			}else{
				String fnm =files[i].getName(); 
				if(houzui.equals(fnm.substring(fnm.length()-fl))){
					list.add(files[i]);
				}
			}
		}
	}

}
