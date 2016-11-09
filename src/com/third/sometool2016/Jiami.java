package com.third.sometool2016;

import com.jacky.engine.resource.BinaryFile;
import com.third.sometool2015.FileFindUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public class Jiami {

    public static String source = "C:\\Users\\yueyue\\Desktop\\decode";
    public static String outpath = "C:\\Users\\yueyue\\Desktop\\hehehe";

    public static byte mima = 117;//密码
    public static int len = 68;
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        FileFindUtil.houzui = "";
        List<File> asdf = FileFindUtil.select(source);
        for(int i=0;i<asdf.size();i++){
            BinaryFile bf = new BinaryFile(new FileInputStream(asdf.get(i)));
            byte[] src = bf.dataBuffer;

            String apath =  asdf.get(i).getPath().substring(source.length());
            File ofile =  new File(outpath+apath);
            File fp = ofile.getParentFile();
            if(!fp.exists()){
                fp.mkdirs();
            }
            ofile.createNewFile();
            FileOutputStream fo = new FileOutputStream(ofile);
            for(int j=0;j<src.length;j++){
                if(j>len){
                    break;
                }
                src[j] = (byte) (src[j]^mima);
            }
            fo.write(src);
            fo.close();
        }


    }
}
