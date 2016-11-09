package com.third.sometool2016;

import com.third.sometool2015.FileFindUtil;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class Ser2Obj {
    public static String srcpath = "D:\\Ñ¸À×ÏÂÔØ\\com.xiaoao.riskSnipe\\assets\\Scene\\Chuan";
    public static String outpath = "C:\\Users\\Administrator\\Desktop\\map";

    public static void main(String [] args) throws Exception {
        FileFindUtil.houzui = ".ser";
        List<File> ls = FileFindUtil.select(srcpath);
        for(int i=0;i<ls.size();i++){
            bin(ls.get(i));
        }
        //bin(new File("D:\\Ñ¸À×ÏÂÔØ\\com.xiaoao.riskSnipe\\assets\\Scene\\Chuan\\Chuan1.ser"));
    }


    public static void bin(File f) throws Exception {
        Object3D[] obj = Loader.loadSerializedObjectArray(new FileInputStream(f));

        String fn = f.getPath().substring(srcpath.length());
        fn = outpath+fn.substring(0,fn.length()-4)+".obj";
        File out = new File(fn);
        File fp = out.getParentFile();
        if(!fp.exists()){
            fp.mkdirs();
        }
        out.createNewFile();
        for(int i=0;i<obj.length;i++){
            obj[i].save(out);
        }
    }
}
