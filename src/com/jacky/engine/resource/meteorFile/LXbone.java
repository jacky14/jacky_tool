package com.jacky.engine.resource.meteorFile;

import com.jacky.engine.math.meteor.Matrix;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Administrator on 2016/3/2.
 */
public class LXbone {
    //读取骨骼数据模式 0 生成人物动画模式  1 生成武器动画模式
    public static int mode = 1;
    public String boneName;
    public String parentName;
    public int parentIndex;
    public byte type;//--1代表bone
    public float[] vpos;
    public float[] vrot;
    public int child;

    public Matrix JueDuiJuZhen;
    public Matrix XiangDuiJuZhen;


    //某一帧的变换参数
    public float[] fvpos;
    public float[] fvrot;
    public Matrix BianHuaJuZhen;

    //public Matrix 绝对矩阵的逆;
    private  static void parentIndex(List<LXbone> bones){
        Map<String, Integer> ni=new HashMap<String, Integer>();
        //添加所有骨骼信息到集合
        for(int i=0;i<bones.size();i++){
            ni.put(bones.get(i).boneName, i);
        }
        //重集合中查找父节点


        for(int i=0;i<bones.size();i++){
            LXbone tmpb =  bones.get(i);
            //1 bone 2 Dummey  精确记录父节点包含傀儡骨骼，用于计算武器变化矩阵
            if(tmpb.type == 1){
                if(ni.get(tmpb.parentName)!=null){
                    tmpb.parentIndex=ni.get(tmpb.parentName);
                }else{
                    tmpb.parentIndex=-1;
                }
            }else if(tmpb.type == 2){
                if("NULL".equals(tmpb.parentName)){
                    tmpb.parentIndex=-1;
                }else{
                    String ptype = tmpb.parentName.substring(0,1);
                    int pidx = Integer.valueOf(tmpb.parentName.substring(1)) ;
                    if("b".equals(ptype)){
                        tmpb.parentIndex=pidx;
                    }else if("d".equals(ptype)){
                        tmpb.parentIndex=pidx+30;
                    }
                    tmpb.parentName = bones.get(tmpb.parentIndex).boneName;

                }

            }


        }


    }


    public static void main(String [] args) throws Exception {
        List<LXbone> afsd = get_bone(new FileInputStream("C:\\Users\\Administrator\\Desktop\\P0.bnc"));
        System.out.print("");
    }
    @SuppressWarnings("resource")
    public static List<LXbone> get_bone(InputStream fileName) throws FileNotFoundException {
        Scanner sc=new Scanner(fileName);
        List<LXbone>  bones=new ArrayList<LXbone>();
        Map<String, Integer> ni=new HashMap<String, Integer>();
        LXbone  bone=null;
        while(sc.hasNext()){
            String line=sc.nextLine().trim();//-- 读入一行
            String[] subline=line.split("\\s+");//-- 用空格分解为文本数组  数组下标由0开始与上面脚本区别

            if("bone".equals(subline[0])){
                bone=new LXbone();
                bone.boneName =subline[1];	bone.type = 1;
                if(mode == 0){
                    ni.put(bone.boneName,bones.size());
                }

            }else if("Dummey".equals(subline[0])){
                bone=new LXbone();
                bone.boneName =subline[1];	bone.type = 2;

            }else if("{".equals(subline[0])){


            }else if("parent".equals(subline[0])){
                bone.parentName = subline[1];
                if(mode == 0){
                    Integer tmppp = ni.get(bone.parentName);
                    if(tmppp==null){
                        bone.parentIndex = -1;
                    }else {
                        bone.parentIndex = tmppp;
                    }
                }

            }else if("pivot".equals(subline[0])){
                bone.vpos=new float[]{Float.parseFloat(subline[1]),Float.parseFloat(subline[2]),Float.parseFloat(subline[3])};

            }else if("quaternion".equals(subline[0])){
                bone.vrot=new float[]{Float.parseFloat(subline[1]),Float.parseFloat(subline[2]),Float.parseFloat(subline[3]),Float.parseFloat(subline[4])};

            }else if("children".equals(subline[0])){
                bone.child =Integer.parseInt(subline[1]);

            }else if("}".equals(subline[0])){
                bones.add(bone);

            }
            /*switch (subline[0]) {

                case "bone":
                    bone=new LXbone();
                    bone.boneName =subline[1];	bone.type = 1;
                    break;
                case "Dummey":
                    bone=new LXbone();
                    bone.boneName =subline[1];	bone.type = 2;
                    break;
                case "{":
                    //continue;
                    break;
                case "parent":
                    bone.parentName = subline[1];
                    break;
                case "pivot":
                    bone.vpos=new float[]{Float.parseFloat(subline[1]),Float.parseFloat(subline[2]),Float.parseFloat(subline[3])};
                    break;
                case "quaternion":
                    bone.vrot=new float[]{Float.parseFloat(subline[1]),Float.parseFloat(subline[2]),Float.parseFloat(subline[3]),Float.parseFloat(subline[4])};

                    break;
                case "children":
                    bone.child =Integer.parseInt(subline[1]);
                    break;
                case "}":
                    bones.add(bone);
            }*/





        }
        if(mode == 1){
            parentIndex(bones);
        }

        return bones;
    }




}
