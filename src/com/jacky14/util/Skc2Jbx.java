package com.jacky14.util;

import com.jacky.engine.math.meteor.Matrix;
import com.jacky.engine.math.meteor.Quaternion;
import com.jacky.engine.math.meteor.SimpleVector;
import com.jacky.engine.resource.meteorFile.LXbone;
import com.jacky14.jbx.JbxModeData;
import com.jacky14.metor.LXobj;
import com.jacky14.metor.Triangle;
import com.jacky14.metor.Vertex;
import com.jacky14.pl.xml.PlistData;
import com.jacky14.timehelp.MyClock;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2015/12/20.
 */
public class Skc2Jbx {

    public static final String thisModuleAssetsPath = "../jacky-toool/assets/";
    public static Properties pt = new Properties();


    public static void main (String [] args) throws Exception {
        MyClock my =new MyClock();
        LXbone.mode = 0;
        pt.load(new FileInputStream(thisModuleAssetsPath + "property"));
        genJxb("p8");

        my.showtime();
    }

    public static void genJxb(String fname) throws Exception {
        LXobj l=new LXobj();
        l.loadObj(thisModuleAssetsPath + fname +".skc");

        PlistData pd = new PlistData(thisModuleAssetsPath+fname +".plist");

        JbxModeData  jmd = new JbxModeData();
        jmd.verts = new float[l.vertexs.length * 3];
        jmd.uvs = new float[l.vertexs.length * 2];
        jmd.v_index = new int[l.triangles.length * 3];
        jmd.bones = new float [l.vertexs.length];

        for(int i=0 ;i<l.vertexs.length;i++){
            Vertex v = l.vertexs[i];
            String key = pt.getProperty(l.lxmats[v.matid].texture);

            jmd.verts[i*3 ] = v.verts[0];
            jmd.verts[i*3 + 1] = v.verts[1];
            jmd.verts[i*3 + 2] = v.verts[2];

            v.uvs =   pd.transition(key,v.uvs);
            jmd.uvs[i*2] = v.uvs[0];
            jmd.uvs[i*2 + 1] = v.uvs[1];
        }

        for(int i=0;i<l.triangles.length ; i++){
            Triangle t = l.triangles[i];
            jmd.v_index[i*3] = t.v_index[0];
            jmd.v_index[i*3 + 1] = t.v_index[1];
            jmd.v_index[i*3 + 2] = t.v_index[2];
        }
        for(int i=0;i<l.vertexs.length;i++){
            jmd.bones[i]=l.vertexs[i].bone_index[0];
        }

        
        List<LXbone> bones= LXbone.get_bone(new FileInputStream(thisModuleAssetsPath + fname+".bnc"));
        for(int i=0;i<30;i++){//处理骨骼绝对矩阵变化
            LXbone bone=bones.get(i);
            Matrix matrix=new Quaternion(bone.vrot[1], bone.vrot[2], bone.vrot[3],bone.vrot[0]).getRotationMatrix();
            matrix.translate(bone.vpos[0], bone.vpos[1],bone.vpos[2]);
            bone.XiangDuiJuZhen =matrix.cloneMatrix();
            if(bone.parentIndex!=-1){
                matrix.matMul(bones.get(bone.parentIndex).JueDuiJuZhen);
            }
            bone.JueDuiJuZhen =matrix;
        }
        for(int i=0;i< jmd.bones.length;i++){///静态处理成本地坐标空间
            int veri = i*3;
            SimpleVector vertex =new SimpleVector(jmd.verts[veri],jmd.verts[veri +1],jmd.verts[veri+2]);
            vertex.matMul(bones.get((int)jmd.bones[i]).JueDuiJuZhen.invert());
            jmd.verts[ veri  ]=vertex.x;
            jmd.verts[ veri + 1 ] = vertex.y;
            jmd.verts[ veri + 2 ] = vertex.z;
        }
        


        jmd.genFile(fname);

    }

}
