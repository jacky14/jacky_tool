package com.third.sometool2015;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class PngTest {

	static String out_root_path = "E:\\pngtmp\\";
	static String src_root_path = "E:\\cocos2d\\cocos2d-x-2.2.6\\projects\\BuyuDarenNormal\\Resources\\"; 
	static boolean isjiami = true;
	//static String src_root_path = "E:\\cocos2d\\cocos2d-x-2.2.6\\projects\\BuyuDarenNormal\\proj.android\\assets\\"; 
	static String[] src_paths = new String[]{
		"",
		"fonts\\",
		"ios\\",
		"newUI\\",
		"newUI\\bottom\\",
		"newUI\\common\\",
		"newUI\\gamemain\\",
		"newUI\\gift\\",
		"newUI\\meiri\\",
		"newUI\\role\\",
		"newUI\\shop\\",
		"newUI\\startgame\\",
		"newUI\\vip\\",
		"bonefish\\",
		"ios\\texiao\\"
		
	};
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		long s = System.currentTimeMillis();
		for(String tmpstr:src_paths){
			String sourcedir;
			String outdir;
			sourcedir = src_root_path + tmpstr;
			outdir = out_root_path+ tmpstr;
			dir(sourcedir, outdir);
		}
		System.out.println("ʱ�䣺 " + (System.currentTimeMillis() - s));
	}
	public static void dir(String sourcedir,String outdir) throws Exception{
		File file =new File(sourcedir);
		File[] files =  file.listFiles();
		for(File tmp : files){
			String fn = tmp.getName();
			if("png".equals(fn.substring(fn.length()-3))){
				File outdirexits =new File(outdir);
				outdirexits.mkdirs();
				genFile(tmp, outdir + fn);
			}
			
		}
	}
	
	public static void genFile(File source,String newfs) throws Exception{
		FileInputStream fi = new FileInputStream(source);
		byte[] be = new byte[fi.available()];
		fi.read(be);
		fi.close();
		if(isjiami){
			be[1] = 31;
			be[2] = 32;
			be[3] = 33;
		}else{
			be[1] = 80;
			be[2] = 78;
			be[3] = 71;
		}
		
		
		File outfile = new File(newfs);
		outfile.createNewFile() ;
		OutputStream out = new FileOutputStream(outfile);
		out.write(be);
		out.close();
	}
	public static void main2(String[] t) throws Exception{
		FileInputStream fi = new FileInputStream("C:\\Users\\Administrator\\Desktop\\queding.png");
		byte[] be = new byte[fi.available()];
		fi.read(be);//80, 78, 71
		fi.close();
		be[1] = 80;
		be[2] = 78;
		be[3] = 71;
		File f= new File("e:\\a");
		f.createNewFile();
		FileOutputStream fff = new FileOutputStream(f);
		
		fff.write(be);
		fff.close();
		
		
		
	}
	
}
