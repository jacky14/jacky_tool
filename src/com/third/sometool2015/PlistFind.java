package com.third.sometool2015;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;


public class PlistFind {

	public static String plistfind = "F:\\TEMP\\assets\\res";
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		FileFindUtil.houzui = "plist";
		
		List<File> list = FileFindUtil.select(plistfind);
		
		
		for(int i =0 ;i<list.size();i++){
			size(list.get(i));
		}
		
		
	}
	public static void size(File f) throws Exception{
		Scanner sc = new Scanner(f);
		String scdd = "";
		
		
		while(sc.hasNext()){
			String str = sc.nextLine();
			if(str.indexOf("smartupdate")!=-1){
				
				int start= scdd.indexOf("{");
				int mid = scdd.indexOf(",");
				int end = scdd.indexOf("}");
				
				int wid = Integer.valueOf(scdd.substring(start+1, mid));
				int hig = Integer.valueOf(scdd.substring(mid+1, end));
				
				if(wid ==512&&hig==1024){
					System.out.println(f.getPath() + "---"+f.getName());
				}
				
				break;
			}
			scdd = str;
		}
		
		
		
	}
	

	
	
	
}
