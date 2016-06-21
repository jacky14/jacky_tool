package com.jacky14.mode3d;

import com.jacky.engine.resource.BinaryFile;
import com.jacky.engine.resource.objutile.ObjFileLoder;

import java.io.*;

/**
 * Created by Administrator on 2015/12/10.
 */
public class ObjFileTool {

  public static String[] argpath= new String[]{
          "C:\\Users\\Administrator\\Desktop\\map\\",
          "C:\\Users\\Administrator\\Desktop\\tmp\\"
  };
  
  public static void main(String[] args) throws FileNotFoundException {
	  ObjFileLoder ol = new ObjFileLoder();
	  ol.loadStrforfname("F:\\mypro\\CounterStrike\\assets\\");
	  
	  
	  
  }
  
  public static void main2(String [] asdf)throws Exception{
	  ObjFileLoder ol = new ObjFileLoder();
      ol.loadStrforInput(new FileInputStream("C:\\Users\\Administrator\\Desktop\\zxcxzc.obj")) ;
      
      String str = "";
      for(int i=0;i<ol.vertexlist.size();i++){
    	  str+= "("+ ol.vertexlist.get(i) +"),";
      }
      System.out.println(str);
      String str1 = "";
      for(int i=0;i<ol.Indexlist.size();i++){
    	  str1+="("+ ol.Indexlist.get(i) +"),";
      }
      System.out.println(str1);
      
  }
  
  public static void main1(String [] args) throws Exception {

      File sourceford = new File(argpath[0]);
      File[] sfs = sourceford.listFiles();

      for(int j=0;j<sfs.length;j++){
          File tmp = sfs[j];
          String name = tmp.getName();
          if(".obj".equals(name.substring(name.length()-4))){

             System.out.println("OBJ文件:'"+name+"'开始解析..........");
             long start  = System.currentTimeMillis();
             ObjFileLoder ol = new ObjFileLoder();
             ol.loadStrforInput(new FileInputStream(tmp)) ;
              File outfile = new File(argpath[1]+name.substring(0,name.length()-4)+".jxb");
              outfile.createNewFile();
              OutputStream is = new FileOutputStream(outfile);
              is.write(BinaryFile.getByte(ol.ivertexlist.size()));
              for(int i=0;i<ol.ivertexlist.size();i++){
                  is.write(BinaryFile.getByte(Float.floatToIntBits(ol.ivertexlist.get(i))));
              }
              is.write(BinaryFile.getByte(ol.itexturelist.size()));
              for(int i=0;i<ol.itexturelist.size();i++){
                  is.write(BinaryFile.getByte(Float.floatToIntBits(ol.itexturelist.get(i))));
              }
              is.write(BinaryFile.getByte(ol.Indexlist.size()));
              for(int i=0;i<ol.Indexlist.size();i++){
                  is.write(BinaryFile.getByte(ol.Indexlist.get(i)));
              }

              is.close();
              System.out.println("OBJ文件:'"+name+"'解析完成。耗时："+(System.currentTimeMillis()-start)+"毫秒.");
          }
      }

  }


}
