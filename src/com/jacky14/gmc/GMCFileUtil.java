package com.jacky14.gmc;

import com.jacky.engine.math.meteor.Matrix;
import com.jacky.engine.math.meteor.Quaternion;
import com.jacky.engine.math.meteor.SimpleVector;
import com.jacky.engine.resource.BinaryFile;
import com.jacky.engine.resource.meteorFile.LXbone;
import com.jacky.engine.resource.meteorFile.amb.AmbAnsys;
import com.jacky.engine.resource.meteorFile.amb.AmbBone;
import com.jacky.engine.resource.meteorFile.amb.Frame;
import com.jacky14.ObjFileUtile;
import com.jacky14.jbx.Jxbdata;
import com.jacky14.metor.LXobj;
import com.jacky14.util.AmbBon2Jxbb;
import com.jacky14.util.Skc2Jbx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/1/7.
 */
public class GMCFileUtil {


    public static float[] ver;
    public static int veri;
    public static float[] uv;
    public static int uvi;
    public static int indexs[];
    public static int indexsi;

    public static List<String> texNams = new ArrayList<String>();
    public static void main(String[] args) throws Exception
    {
        load("C:\\Users\\Administrator\\Desktop\\w5_0.GMC");
       /* Jxbdata j =new Jxbdata();
        j.ver = ver;
        j.uv = uv;
        j.indexs = indexs;*/
        ObjFileUtile  ofu = new ObjFileUtile(ver,uv,indexs);
        ofu.save("C:\\Users\\Administrator\\Desktop\\tmp\\");

        // j.savedata("gongheguo.jxb");
    }

    public static void main5(String[] args) throws Exception {
       // int a = '文';
        //System.out.print(a);
        //加载一个武器文件
        //load("F:\\mypro\\jacky-X\\jacky-toool\\assets\\w4_2.GMC");
        //LXobj l=new LXobj();
        //l.loadObj("F:\\mypro\\jacky-X\\jacky-toool\\assets\\p0.skc");


        //AmbBone ab=  AmbAnsys.ansysAmb("F:\\mypro\\jacky-X\\jacky-toool\\assets\\p0.amb");
        AmbBone comm=  AmbAnsys.ansysAmb(Skc2Jbx.thisModuleAssetsPath + "character"+".amb");

        List<Frame> framesnewall = new ArrayList<>();
        //添加人物本身动作
        //AmbBon2Jxbb.addframe(AmbBon2Jxbb.myani,ab.frames,framesnewall);
        //添加公用动作
        //ex dao bishou jian
        //AmbBon2Jxbb.addframe(AmbBon2Jxbb.ex,comm.frames,framesnewall);


        //AmbBon2Jxbb.addframe(AmbBon2Jxbb.dao,comm.frames,framesnewall);
        //AmbBon2Jxbb.addframe(AmbBon2Jxbb.bishou,comm.frames,framesnewall);
        AmbBon2Jxbb.addframe(AmbBon2Jxbb.jian,comm.frames,framesnewall);


        //计算当前节点的绝对矩阵
        LXbone.mode = 1;
        bones1= LXbone.get_bone(new FileInputStream("F:\\mypro\\jacky-X\\jacky-desktop\\assets\\p0.bnc"));
        for(int i=0;i<bones1.size();i++){
            coPJD(bones1.get(i));
        }
        float temp[][][] = new float [framesnewall.size()][][];


        for(int i=0;i<framesnewall.size();i++){//循环所有的帧
            Frame fe =framesnewall.get(i);
            fe.m = new Matrix[fe.quaternion.length + fe.dummys.length];
            for(int j=0;j<fe.quaternion.length;j++){//其中某一帧的动画
                LXbone bone=bones1.get(j);
                bone.fvpos = bone.vpos;
                bone.fvrot = fe.quaternion[j];
            }
            for(int j= 0;j< fe.dummys.length; j++){
                LXbone bone=bones1.get(j + fe.quaternion.length);
                bone.fvpos = bone.vpos;
                bone.fvrot = fe.dummys[j].quaternion ;
            }
            //计算某一帧中骨骼对应的变化矩阵
            for(int j=0;j<bones1.size();j++){
                coPFM(bones1.get(j));
            }
            //bones1.get(33).BianHuaJuZhen

            temp[i] = bones1.get(35).BianHuaJuZhen.mat;



          /* if(i==66){//第几帧时特殊处理数据
                //生成人物模型文件
                for(int j=0;j<l.vertexs.length;j++){
                    SimpleVector vertex =new SimpleVector(l.vertexs[j].verts[0],l.vertexs[j].verts[1],l.vertexs[j].verts[2]);

                    vertex.matMul(bones1.get(l.vertexs[j].bone_index[0]).JueDuiJuZhen.invert());
                    vertex.matMul(bones1.get(l.vertexs[j].bone_index[0]).BianHuaJuZhen);
                    l.vertexs[j].verts[0]= vertex.x;
                    l.vertexs[j].verts[1] =  vertex.y;
                    l.vertexs[j].verts[2] = vertex.z;

                }
                //生成武器模型文件
                for(int j=0;j<ver.length/3 ;j++){
                    SimpleVector vertex =new SimpleVector(ver[j*3],ver[j*3 + 1],ver[j*3 + 2]);
                    vertex.matMul(bones1.get(33).BianHuaJuZhen);
                    ver[j*3] = vertex.x;
                    ver[j*3 + 1] = vertex.y;
                    ver[j*3 + 2] = vertex.z;
                }
                break;
            }*/
        }
       File outfile = new File("C:\\Users\\Administrator\\Desktop\\tmp\\p0_jian.dum");
        outfile.createNewFile();
        OutputStream is = new FileOutputStream(outfile);
        //float temp[][][] = new float [ab.frames.length][][];
        is.write(BinaryFile.getByte(temp.length));
        for(int i=0;i<temp.length;i++){
            for(int j=0;j<4;j++){
                for(int k = 0;k<4;k++){
                    is.write(BinaryFile.getByte(Float.floatToIntBits(temp[i][j][k])));
                }
            }
        }
        is.close();




        //l.genObj("C:\\Users\\Administrator\\Desktop\\tmp\\");
        //ObjFileUtile obj = new ObjFileUtile(ver,uv,indexs);
        //obj.save("C:\\Users\\Administrator\\Desktop\\tmp\\");


        //加载一个骨骼数据文件
       /*
        //武器数据文件乘以骨骼文件绝对矩阵的逆回到本地空间
        int length = ver.length /3;
        for(int i=0;i<length;i++){
            SimpleVector vertex =new SimpleVector(ver[i*3],ver[i*3+1],ver[i*3+2]);
            vertex.matMul(bones1.get(32).JueDuiJuZhen.invert());
            ver[i*3] = vertex.x;
            ver[i*3+1] = vertex.y;
            ver[i*3+2] = vertex.z;
        }
        System.out.print("");



       */


    }




    //当前骨骼全部骨骼数据
    public static List<LXbone> bones1;


    /**
     * 计算某一帧中骨骼对应的变化矩阵
     * @param bone
     */
    public static void coPFM(LXbone bone){
        Matrix matrix=new Quaternion(bone.fvrot[1], bone.fvrot[2], bone.fvrot[3],bone.fvrot[0]).getRotationMatrix();
        matrix.translate(bone.fvpos[0], bone.fvpos[1],bone.fvpos[2]);
        if(bone.parentIndex!=-1){
            LXbone pbone = bones1.get(bone.parentIndex);
            if(pbone.BianHuaJuZhen ==null){
                coPFM(pbone);
            }
            matrix.matMul(pbone.BianHuaJuZhen);
        }
        bone.BianHuaJuZhen = matrix;
    }

    /**
     * 计算当前节点的绝对矩阵
     * @param bone
     */
    public static void coPJD(LXbone bone){
        //1.计算当前节点的相对矩阵
        Matrix matrix=new Quaternion(bone.vrot[1], bone.vrot[2], bone.vrot[3],bone.vrot[0]).getRotationMatrix();
        matrix.translate(bone.vpos[0], bone.vpos[1],bone.vpos[2]);
        bone.XiangDuiJuZhen =matrix.cloneMatrix();
        if(bone.parentIndex!=-1){
            LXbone pbone = bones1.get(bone.parentIndex);
            //如果父节点绝对矩阵为空，则递归计算父节点绝对矩阵。
            if(pbone.JueDuiJuZhen ==null){
                coPJD(pbone);
            }
            matrix.matMul(pbone.JueDuiJuZhen);
        }
        bone.JueDuiJuZhen =matrix;
    }

    public static void load(String filename) throws Exception {
        Scanner scanner = new Scanner(new FileInputStream(filename));
        boolean ischickend = false;
        while (scanner.hasNext()){
            String str = scanner.nextLine().trim();
            String[] strtmp =  str.split("\\s+");
            if("png".equalsIgnoreCase(GE(strtmp[0])) ||"jpg".equalsIgnoreCase(GE(strtmp[0])) ||"bmp".equalsIgnoreCase(GE(strtmp[0]))||"tga".equalsIgnoreCase(GE(strtmp[0]))  ){
                texNams.add(strtmp[0]);
            }
            if("Object".equals(strtmp[0])){
                ischickend = true;
            }
            if("Vertices".equals(strtmp[0])){
                ver = new float [Integer.valueOf(strtmp[1]) * 3 ];
                uv = new float [Integer.valueOf(strtmp[1]) * 2];
                indexs =new int [Integer.valueOf(strtmp[3]) * 3 ];
            }
            if("v".equals(strtmp[0])){
                ver[veri++] = Float.valueOf(strtmp[1]);
                ver[veri++] = Float.valueOf(strtmp[2]);
                ver[veri++] = Float.valueOf(strtmp[3]);

                if(strtmp.length<=14){
                    System.out.println();
                }
                uv[uvi++] = Float.valueOf(strtmp[14]);
                uv[uvi++] = 1 - Float.valueOf(strtmp[15]);
            }

            if("f".equals(strtmp[0])){
                indexs[indexsi++] = Integer.valueOf(strtmp[2]);
                indexs[indexsi++] = Integer.valueOf(strtmp[3]);
                indexs[indexsi++] = Integer.valueOf(strtmp[4]);
            }


            if(ischickend){
                if("}".equals(strtmp[0])){
                    break;
                }
            }

        }

    }
    public static final String GE(String s){
        if(s.length()>3){
            return s.substring(s.length()-3);
        }else{
            return s;
        }

    }

}
