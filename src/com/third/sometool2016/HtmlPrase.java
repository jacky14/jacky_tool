package com.third.sometool2016;

import com.third.sometool2015.FileFindUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 * Created by yueyue on 2016/11/9.
 */
public class HtmlPrase {
    public static String tag = "../assets/";
    public static String sourcepath = "C:\\Users\\yueyue\\Desktop\\assets";
    public static String outpath = "C:\\Users\\yueyue\\Desktop\\out";
    public static String jsppath = "C:\\Users\\yueyue\\Desktop\\xitong";


    public static void main(String[] args) throws DocumentException {
        FileFindUtil.houzui = ".jsp";
        List<File> lf = FileFindUtil.select(jsppath);
        for(int i=0;i<lf.size();i++){
            pasfile(lf.get(i));
        }

        Iterator<String> it = chtmp.keySet().iterator();
        int hahha = 0;
        while(it.hasNext()){hahha++;
            String src = it.next();
            String out = chtmp.get(src);
            //CopyFileUtil.copyFile(src,out);
            System.out.println("idx:"+hahha +"  " +src+">>>>>>>>>"+out);
        }
    }

    public static Map<String,String> chtmp = new HashMap<String,String>();
    public static void pasfile(File f){
        String txt = TexGBK2UTF.getTextFromText(f);
        int idx = txt.indexOf(tag);
        while(idx>0){
            int end = 0;
            for(int i=idx;i<txt.length();i++){
                char c =  txt.charAt(i);
                if(c=='"'){
                    end = i;
                    break;
                }
            }
            String src = sourcepath + txt.substring(idx+9,end);
            String out = outpath + txt.substring(idx+9,end);
            chtmp.put(src,out);

            txt = txt.replaceFirst(tag,"");
            idx = txt.indexOf(tag);
        }

    }
}
