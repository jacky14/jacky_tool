package com.jacky14.jbx;

import com.jacky.engine.resource.BinaryFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/1/19.
 */
public class Jxbdata {
    public  float[] ver;
    public  float[] uv;
    public  int indexs[];

    public static String path = "C:\\Users\\Administrator\\Desktop\\tmp\\";
    public void covlist(List<float[]> vex ,List<float[]> uvtmp,List<Integer>idxtmp){
        ver = new float[vex.size()*3];
        for(int i=0;i<vex.size();i++){
            ver[i*3] = vex.get(i)[0];
            ver[i*3+1] = vex.get(i)[1];
            ver[i*3+2] = vex.get(i)[2];
        }

        uv = new float [uvtmp.size()*2];
        for(int i=0;i<uvtmp.size();i++){
            uv[i*2] = uvtmp.get(i)[0];
            uv[i*2+1] = uvtmp.get(i)[1];
        }
        indexs = new int [idxtmp.size()];
        for(int i=0;i<idxtmp.size();i++){
            indexs[i] = idxtmp.get(i);
        }
    }

    public void savedata(String name) throws IOException {
        File outfile = new File(path+name);
        outfile.createNewFile();
        OutputStream is = new FileOutputStream(outfile);


        is.write(BinaryFile.getByte(ver.length));
        for(int i=0;i<ver.length;i++){
            is.write(BinaryFile.getByte(Float.floatToIntBits(ver[i])));
        }

        is.write(BinaryFile.getByte(uv.length));
        for(int i=0;i<uv.length;i++){
            is.write(BinaryFile.getByte(Float.floatToIntBits(uv[i])));
        }
        is.write(BinaryFile.getByte(indexs.length));
        for(int i=0;i<indexs.length;i++){
            is.write(BinaryFile.getByte(indexs[i]));
        }
        is.close();

    }

}
