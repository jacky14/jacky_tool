package com.jacky14.util;

import com.jacky.engine.math.meteor.Matrix;
import com.jacky.engine.math.meteor.Quaternion;
import com.jacky.engine.resource.meteorFile.LXbone;
import com.jacky.engine.resource.meteorFile.amb.AmbAnsys;
import com.jacky.engine.resource.meteorFile.amb.AmbBone;
import com.jacky.engine.resource.meteorFile.amb.Frame;
import com.jacky14.jbx.JbxBoneData;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/3.
 */
public class AmbBon2Jxbb {

    public static void  main(String [] arg) throws IOException {
        genFile("p0");
    }

    public static int[] [] myani = new int [][]{
            {0	,60},
            {322,340}
    };

    public static int [][] ex = new int [][]{
            {1662,1689},
            {1778,1800},
            {1693,1709},
            {2449,2492}
    };

    public static int [][] dao  = new int [][]{
            {5746,5812},
            {5858,5950},
            {6176,6285},
            {6991,7239}
    };

    public static int [][] bishou = new int [][]{
            {8949	,9000},
             {9141,	9191},
             {9413	,9528},
             {9529	,9582},
    };
    public static int [][] jian = new int [][] {
         {9833	,9866},
          {10412	,10484},
        {10484	,10569},
        {10571	,10670}

    };

    public static void addframe(int [][] asd,Frame[] src , List<Frame> tag){
        for(int i=0;i<asd.length;i++){
            System.out.print("start = " + tag.size());
            for(int j=asd[i][0];j<=asd[i][1];j++){
                tag.add(src[j]);
            }
            System.out.println("end = " + tag.size());
        }
    }
    public static void genFile(String fname) throws IOException {

        //公用武器动作动画
       AmbBone comm=  AmbAnsys.ansysAmb(Skc2Jbx.thisModuleAssetsPath + "character"+".amb");
       //AmbBone ab=  AmbAnsys.ansysAmb(Skc2Jbx.thisModuleAssetsPath + fname+".amb");

        List<Frame> framesnewall = new ArrayList<>();

       //添加人物本身动作
        //addframe(myani,ab.frames,framesnewall);
        //添加公用动作
        //ex dao bishou jian
        //addframe(ex,comm.frames,framesnewall);


        //addframe(dao,comm.frames,framesnewall);
        //addframe(bishou,comm.frames,framesnewall);
        addframe(jian,comm.frames,framesnewall);




        float tmp [][] = new float[framesnewall.size()][];
        for(int i=0;i<framesnewall.size();i++){
            tmp[i] =new float[framesnewall.get(i).quaternion.length*4];
            for(int j=0;j<framesnewall.get(i).quaternion.length;j++){
                for(int k=0;k<4;k++){
                    tmp[i][j*4 + k] = framesnewall.get(i).quaternion[j][k];
                }
            }
        }
        float [][] matmp =new float [framesnewall.size()][];
        //处理变换顶点信息
        LXbone.mode = 0;
        List<LXbone> bones= LXbone.get_bone(new FileInputStream(Skc2Jbx.thisModuleAssetsPath + fname+".bnc"));
        for(int i=0;i<tmp.length;i++){
            float [] q = tmp[i];
            int len = q.length/4;
            matmp[i] = new float[len * 12];
            for(int j=0;j<len;j++){
                int indextmp = j*4;
                LXbone bone=bones.get(j);
                Matrix XuanZhuan =new Quaternion(q[indextmp + 1],q[indextmp + 2], q[indextmp + 3], q[indextmp]).getRotationMatrix();//当前帧旋转
                XuanZhuan.translate(bone.vpos[0], bone.vpos[1],bone.vpos[2]);
                if(bone.parentIndex!=-1){
                    XuanZhuan.matMul(bones.get(bone.parentIndex).BianHuaJuZhen);
                }
                bone.BianHuaJuZhen = XuanZhuan;
                XuanZhuan.setF(matmp[i],j*12);
            }
        }

        JbxBoneData jbd = new JbxBoneData();
        jbd.data = matmp;
        jbd.genFile(fname);

    }






}
