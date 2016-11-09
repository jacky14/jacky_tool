package com.com.third.sometool2016;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * Created by yueyue on 2016/11/9.
 */
public class HtmlPrase {

    public static void main(String[] args) throws DocumentException {
        String txt = TexGBK2UTF.getTextFromText(new File("C:\\Users\\yueyue\\Desktop\\shouquan.jsp"));
        String tag = "../assets/";



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
            System.out.println(txt.substring(idx,end));
            txt = txt.replaceFirst(tag,"x");
            idx = txt.indexOf(tag);
        }
    }
}
