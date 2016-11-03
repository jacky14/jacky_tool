package com.com.third.sometool2016;

import com.third.sometool2015.FileFindUtil;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/11/3.
 */
public class TexGBK2UTF {

    public static String source = "G:\\jacky_tool.git\\trunk\\src";
    public static String outpath = "C:\\Users\\Administrator\\Desktop\\src";
    public static void main(String [] args) throws IOException {
        FileFindUtil.houzui = "";
        List<File> files = FileFindUtil.select(source);
        for(int i=0;i<files.size();i++){

            if("Ms3dRead.java".equals(files.get(i).getName())){
                System.out.print("asdf");

            }
            StringBuffer sb = new StringBuffer();
            Scanner sc = new Scanner(files.get(i));

            while(sc.hasNext()){
                sb.append(sc.nextLine() + "\r\n");
            }
            sc.close();
            String aaaa=getTextFromText(files.get(i));


            String apath =  files.get(i).getPath().substring(source.length());
            File ofile =  new File(outpath+apath);
            File fp = ofile.getParentFile();
            if(!fp.exists()){
                fp.mkdirs();
            }
            ofile.createNewFile();
            PrintWriter pw = new PrintWriter(ofile,"UTF-8");
            pw.write(aaaa);
            pw.flush();
            pw.close();
        }

    }


    public static String getTextFromText(File file){

        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file),getCharset(file));
            BufferedReader br = new BufferedReader(isr);

            StringBuffer sb = new StringBuffer();
            String temp = null;
            while((temp = br.readLine()) != null){
                sb.append(temp+"\r\n");
            }
            br.close();
            return sb.toString();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    private static String getCharset(File file) throws IOException{

        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();

        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;
    }
}
