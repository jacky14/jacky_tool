package com.jacky14.jbx;

import com.jacky.engine.resource.BinaryFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/1/3.
 */
public class JbxBoneData {
    public static String path = "C:\\Users\\Administrator\\Desktop\\tmp\\";
    public static String suffix = ".jxbb";
    //帧数index 整体骨骼数据
    public  float [][] data;


    public void genFile(String fileName) throws IOException {
        File outfile = new File(path+fileName+suffix);
        outfile.createNewFile();
        OutputStream is = new FileOutputStream(outfile);

        is.write(BinaryFile.getByte(data.length));
        for(int i=0;i<data.length;i++){
            float[] tmp = data[i];
            is.write(BinaryFile.getByte(tmp.length));
            for(int j=0;j<tmp.length;j++){
                is.write(BinaryFile.getByte(Float.floatToIntBits(tmp[j])));
            }
        }
        is.close();
    }
}
