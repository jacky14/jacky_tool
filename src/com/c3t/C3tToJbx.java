package com.c3t;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.jacky.engine.math.Matrix4f;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yueyue on 2016/11/25.
 */
public class C3tToJbx {


    public static String c3tfile = "F:\\huangbc\\cocos2d-3.13.1\\cocos2d-x-3.13.1\\tests\\cpp-tests\\Resources\\Sprite3DTest\\orc.c3t";
    public static void main(String [] arg) throws Exception {

        FileInputStream file = new FileInputStream(new File(c3tfile)) ;
        int size = file.available();
        byte[] dataBuffer = new byte[size];
        file.read(dataBuffer);
        file.close();
        String strtmp = new String(dataBuffer);
        JSONObject jobj  =  JSON.parseObject(strtmp);


        String verstr = jobj.getString("version");
        System.out.println("c3t file version is " + verstr);

        JSONArray meshes = jobj.getJSONArray("meshes");
        if(meshes.size()>1){
            System.out.println("current not supper mul mesh ，mesh count is " + meshes.size() );
            return;
        }else if(meshes.size()<1){
            System.out.println("mesh mush be than num 0 ，mesh count is " + meshes.size() );
            return;
        }

        // parse vertex
        JSONObject mesh = (JSONObject)meshes.get(0);
        JSONArray vers = mesh.getJSONArray("vertices");
        if(vers.size()<1){
            System.out.println("vertex num mush than 0 ,current ver size is " + vers.size());
            return;
        }
        int versize = vers.size() / 16;
        List<Vertex> listver = new ArrayList<>();
        for(int i=0;i<versize;i++){
            int baseidx = i*16;
            Vertex tmpver = new Vertex();
            tmpver.pos = new float[]{vers.getFloat(baseidx),vers.getFloat(baseidx + 1 ),vers.getFloat(baseidx + 2 )  };
            tmpver.nor = new float[]{vers.getFloat(baseidx + 3),vers.getFloat(baseidx + 4),vers.getFloat(baseidx + 5)};
            tmpver.uv = new float []{vers.getFloat(baseidx + 6),vers.getFloat(baseidx + 7)};
            tmpver.wei = new float[]{vers.getFloat(baseidx + 8),vers.getFloat(baseidx + 9),vers.getFloat(baseidx + 10),vers.getFloat(baseidx + 11)};
            tmpver.boneidx = new int[]{f2i(vers.getFloat(baseidx + 12)),f2i(vers.getFloat(baseidx + 13)),f2i(vers.getFloat(baseidx + 14)),f2i(vers.getFloat(baseidx + 15))};
            listver.add(tmpver);
        }

        JSONArray idx = mesh.getJSONArray("parts").getJSONObject(0).getJSONArray("indices");
        versize =  idx.size()/3;


        List<TrgIdx> lss = new ArrayList<>();
        for(int i=0;i<versize;i++){
            TrgIdx ti = new TrgIdx();
            int bidx = i*3;
            ti.x = idx.getInteger(bidx);
            ti.y = idx.getInteger(bidx + 1);
            ti.z = idx.getInteger(bidx + 2);
            lss.add(ti);
        }

        List<Bone> bones = new ArrayList<>();
        JSONArray jaay =  jobj.getJSONArray("nodes").getJSONObject(0).getJSONArray("parts").getJSONObject(0).getJSONArray("bones");
        for(int i=0;i<jaay.size();i++){
            JSONObject obj =   jaay.getJSONObject(i);

            JSONArray max =  obj.getJSONArray("transform");
            Bone bone = new Bone();
            bone.name = obj.getString("node");

            for(int j=0;j<max.size();j++){
                bone.m4f_jdjz.matrix[j] = max.getFloat(j);
            }
            bones.add(bone);
        }


        System.out.println("asdf");
    }
    private static int f2i(float f){
        return (int)f;
    }
}
