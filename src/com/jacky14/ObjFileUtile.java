package com.jacky14;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/3/1.
 */
public class ObjFileUtile {

    /**
     * 顶点数据
     */
    public float[] vex;
    public String strvex = "v ";
    /**
     * 纹理数据
     */
    public float[] uv;
    public String struv = "vt ";

    /**
     * 索引数据
     */
    public int[] indexs;
    public String strindex = "f ";


    public String name;
    public int type =0;
    public ObjFileUtile(float[]v , float[] u,int[] i){
        this.vex = v;
        this.uv = u;
        this.indexs = i;
        type = 0;
    }
    private void save0(PrintWriter pw){
        for(int i = 0 ;i< vex.length /3 ;i++){
            String str = strvex + vex[i*3] + " " + vex[i*3+1] + " " +vex[i*3+2] ;
            pw.println(str);
        }

        for(int i = 0 ; i < uv.length / 2; i++){
            String str = struv + df.format(uv[i*2]) + " " + df.format(uv[i*2+1]);
            pw.println(str);
        }
        for(int i=0;i<indexs.length /3;i++){
            String str = strindex + (indexs[i*3]+1)+"/"+ (indexs[i*3]+1) +  " " +
                    (indexs[i*3+1]+1)+"/"+(indexs[i*3+1]+1) +" " +
                    (indexs[i*3+2]+1)+"/" + (indexs[i*3+2]+1);
            pw.println(str);
        }
    }

    DecimalFormat df=new DecimalFormat("0.#########");
    private List<float[]> lvex;
    private List<float[]> luv;
    private List<int[]> li;
    public ObjFileUtile(List<float[]> v,List<float[]> u,List<int[]> i){
        this.lvex = v;this.luv = u;this.li = i;
        type = 1;
    }
    private void save1(PrintWriter pw){
        for(int i=0;i<lvex.size();i++){
            String str = strvex +lvex.get(i)[0] + " " +  lvex.get(i)[1] + " " + lvex.get(i)[2] ;
            pw.println(str);
        }
        for(int i=0;i<luv.size();i++){
        		String str = struv+    df.format(luv.get(i)[0]) +" "+ df.format(luv.get(i)[1]);
        		pw.println(str);
        }
        for(int i=0;i<li.size();i++){
        		String str = strindex + (li.get(i)[1]+1) +"/"+(li.get(i)[1]+1) +" " +
                        (li.get(i)[1]+1) +"/"+(li.get(i)[1]+1)   +" "+

                        (li.get(i)[2]+1)+"/"+(li.get(i)[2]+1) ;
        		pw.println(str);
        }
    }

    public void save(String path) throws IOException{
        String filename = "ObjFileUtile" + System.currentTimeMillis()+name+".obj";
        String allfile = path + filename ;

        File tmp = new File(allfile);

        tmp.createNewFile();
        PrintWriter pw = new PrintWriter(tmp);

        if(type==0){
            save0(pw);
        }else if(type==1){
            save1(pw);
        }


        pw.close();
    }
    public void saveDefual(){



    }


    public static void main(String [] arg) throws IOException {
        //Load ld  = new Load(new FileInputStream("F:\\mypro\\jacky-X\\jacky-desktop\\assets\\cbz.jxb"));
        //ObjFileUtile u = new ObjFileUtile(ld.vertex,ld.texture,ld.index);
        //u.save("C:\\Users\\Administrator\\Desktop\\tmp\\");


     }


}
