package com.jacky14.jbx;

import com.jacky.engine.resource.BinaryFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2015/12/20.
 */
public class JbxModeData {
    public static String path = "C:\\Users\\Administrator\\Desktop\\tmp\\";
    public static String suffix = ".jxb";
    /**
     * 顶点
     */
    public float[] verts;
    /**
     * 纹理坐标
     */
    public float[] uvs;
    /**
     * 顶点索引
     */
    public int[] v_index;

    /**
     * 顶点对应的骨骼信息
     */
    public float [] bones;

    /**
     * 生成jbx模型文件的文件夹
     * @param fileName
     */
    public void genFile(String fileName) throws IOException {
        File outfile = new File(path+fileName+suffix);
        outfile.createNewFile();
        OutputStream is = new FileOutputStream(outfile);

        is.write(BinaryFile.getByte(verts.length));
        for(int i=0;i<verts.length;i++){
            is.write(BinaryFile.getByte(Float.floatToIntBits(verts[i])));
        }

        is.write(BinaryFile.getByte(uvs.length));
        for(int i=0;i<uvs.length;i++){
            is.write(BinaryFile.getByte(Float.floatToIntBits(uvs[i])));
        }

        is.write(BinaryFile.getByte(v_index.length));
        for(int i=0;i<v_index.length;i++){
            is.write(BinaryFile.getByte(v_index[i]));
        }
        is.write(BinaryFile.getByte(bones.length));
        for(int i=0;i<bones.length;i++){
            is.write(BinaryFile.getByte(Float.floatToIntBits(bones[i])));
        }
        is.close();

    }
}
