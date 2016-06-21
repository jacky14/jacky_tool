package com.jacky14.util;

import com.jacky.engine.resource.BinaryFile;
import com.jacky.engine.resource.meteorFile.GMBObj;
import com.jacky.engine.resource.meteorFile.GmbFileFormat;
import com.jacky14.ObjFileUtile;
import com.jacky14.jbx.Jxbdata;
import com.jacky14.pl.xml.PlistData;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/3/2.
 */
public class MapUtile {

    public static void main(String [] args) throws Exception {
        BinaryFile bf =new BinaryFile(new FileInputStream("C:\\Users\\Administrator\\Desktop\\mapb\\sn13.gmb"));
        pt.load(new FileInputStream("../jacky-toool/assets/sn13map"));
        GmbFileFormat gf =new GmbFileFormat();

        gf.readDataFromFilename(bf);

        chick2(gf);


        //rename("C:\\Users\\Administrator\\Desktop\\tmp");

    }

    /**
     * 将指定目录下jpg文件后缀修改为大写JPG后缀
     * @param path
     */
    public static void rename(String path){
        File file = new File(path);
        File [] files = file.listFiles();
        String newPath = "C:\\Users\\Administrator\\Desktop\\jpgtmp\\";

        for(int i=0;i<files.length;i++){
            File tmp = files[i];
            String Name = tmp.getName();

            if(Name.substring(Name.length() - 3) .equals("jpg") ||Name.substring(Name.length() - 3) .equals("JPG") ){
                System.out.print("\""+ Name.substring(0,Name.length() - 4) + "\",");
                Name = Name.substring(0,Name.length() - 3) + "JPG";
                tmp.renameTo(new File(newPath+Name));
            }
        }
    }

    //四方阵屏蔽列表
    public static Set<String> set06 = new HashSet<>();

    //炽雪城屏蔽列表
    public static Set<String> set04 = new HashSet<>();

    //金华城屏蔽列表
    public static Set<String> set13 = new HashSet<>();
    public static Set<String> set13_jxb = new HashSet<>();

    //洛阳城屏蔽列表
    public static Set<String> set19 = new HashSet<>();
    public static Set<String> set19_jxb = new HashSet<>();


    //一线天obj屏蔽列表
    public static Set<String> set03 = new HashSet<>();
    public static Set<String> set03_jxb = new HashSet<>();


    //皇天城obj屏蔽列表
    public static Set<String> set05 = new HashSet<>();
    public static Set<String> set05_jxb = new HashSet<>();
    static{
        String[] sss05 = new String []{
            "Sphere01","nder03","CBox","Wall","nder47","nder46","der48","inder52","Object07","Object86","nder02","Object18","Object15","Object22","","Box276","f4FD","nder04","Box86","Object19","ngle19","Plane08","f17","Object29","Object19","Object38","Box54","ngle22","f4FW","Object34","f3FY","f2FY","Loft01","Box87","Box233","Object25","Box124","Object89","Object88","Object05","Object27","Object59","Box232","nder27","Object16","Box285","Box284","f2FL02","Object79","Box147","Plane17","Plane11","Box293","Box289","Box291","lt03","Object06","Object21","f12","Box290","Box292","lt06","f32","lt01","lt02","Plane12","Object30","Plane10","Object81","Box173","Box213","Object82","Box05","Box286","Box281","Object20","Object85","Object32","lt07","","Plane16","Object98","Object97","Object65","Object66","Object87","Object99","Box03","","Object23","Object83","Object100","Object24","Object80","ngle09","Box84","Box85","Object03","Object17",

        };
        for(int i=0;i<sss05.length;i++){
            set05.add(sss05[i]);
        }
        String[] s05_jxb = new String[]{
                "kmtree01_jpg","sn05s3_jpg", "303w27_jpg", "sn05z_jpg"
        };
        for(int i=0;i<s05_jxb.length;i++){
            set05_jxb.add(s05_jxb[i]);
        }


        String[] s03_jxb = new String[]{
                "sn308_JPG","sn3tr_JPG","sn322_JPG","sn312_JPG","kms03t01_JPG"
        };
        for(int i=0;i<s03_jxb.length;i++){
            set03_jxb.add(s03_jxb[i]);
        }

        String[] sss03 = new String[]{"Sphere01","mou","wu","Box04",
                "Line16" ,"Shape01" ,"mou01", "Line17" ,"Object39",
                "Object11","Line06","R24","Btf16","safeBoxB","R25","Shape03","glass305","glass332","Object01","Line12","Plane01","Line11","gree18","Object59","Line18","gree112","nder38","inder42","inder35","gree10","nder26","inder34","Object77","R27","inder39","nder37","inder40","inder33","Object58","Object60","Object28","Object32","Object71","nder36","Object57","inder41","Line07","Object04","Object42","tizi03","Object41","tizi","Object43","tizi05","Object44","tizi06","Object76","Object03","DBox03","DBox01","DDBox01","Object07","tizi08","Object45","Object46","tizi07","DBox07","DBox05","safeBoxB","Box158"

        };
        for(int i=0;i<sss03.length;i++){
            set03.add(sss03[i]);
        }



        //被屏蔽对象的列表 sn06 四方阵
        String[] sss06 = new String[]{"wu","wu02","wu03","Plane02","Plane30",
                "Plane29","Object01","Line03","Box18","wu01","Box31","Box34",
                "Box28","Box25","Box22","Box07","Box06","Box05","Box04","Box03","Box12",
                "Box14","Box15","Box61","Box58","Box55","Box53","Box50","Box49","Box47",
                "Box44","Box40","Box38","Box35","Plane01","HBox",
                "wu04"
        };//"Cylinder01",
        for(int i=0;i<sss06.length;i++){
            set06.add(sss06[i]);
        }


        String[] sss04 = new String[]{"45","asn0437.JPG","-1","sn0442.JPG","0"};
        for(int i=0;i<sss04.length;i++){
            set04.add(sss04[i]);
        }


        //金华城
        String[] sss13 = new String[]{
                "Sphere01","Sphere03","Sphere02","Box02","Plane08","Object44","Object08","Box103","Plane03","Plane03","Object66","Box157","Object65","Box158","Box01","Object15","Box162","Object70","Object64","Box159","Object68","Box160","Object71","Box105","Object13","Box108","Box107","Object12","Object69","Box161","Box101","Object06","Box100","Object05","Box109","Object14","Object93","Object129",
                "Object131"
                ,"ian1562"
                ,"Box180"
                ,"ian1561"
                ,"Object130"
                ,"ian1563"
                ,"Object132"
                ,"Plane11"
                ,"ian1551"
                ,"Object127"
                ,"ian1636"
                ,"ian1550"
                ,"Plane12"
                ,"Box181"
                ,"Object129"
                ,"ian1539"
                ,"ian1549"
                ,"Plane21"
                ,"Object128"
                ,"ian1635"
                ,"Object122"
                ,"Object124"
                ,"Box172"
                ,"ian1559"
                ,"Object123"
                ,"Box05"
                ,"Plane22"
                ,"ian1547"
                ,"Object89"
                ,"ian1544"
                ,"ian1638"
                ,"Object118"
                ,"Object91"
                ,"Plane15"
                ,"ian1542"
                ,"ian1535"
                ,"ian1560"
                ,"Plane13"
                ,"Box175"
                ,"Box174"
                ,"Plane14"
                ,"Object92"
                ,"Object90"
                ,"Plane23"
                ,"Plane17"
                ,"Plane18"
                ,"ian1567"
                ,"Box170"
                ,"ian1528"
                ,"Object117"
                ,"ian1564"
                ,"Object116"
                ,"Object115"
                ,"ian1565"
                ,"ian1493"
                ,"Object133"
                ,"ian1566"
                ,"Object114"
                ,"ian1568"
                ,"Object119"
                ,"ian1552"
                ,"Plane24"
                ,"Object113"
                ,"ian1637"
                ,"ian1556"
                ,"ian1541"
                ,"Object155"
                ,"Object156"
                ,"Object157"
                ,"Object158"
                ,"Object159"
                ,"Object160"
                ,"Plane10"
                ,"Object163"
                ,"Object162"
                ,"Object161"
                ,"Object164"
                ,"Object165"
                ,"ian1526"
                ,"Object141"
                ,"ian1569"
                ,"Object139"
                ,"Box177"
                ,"ian1570"
                ,"Object140"
                ,"Box179"
                ,"Plane25"
                ,"ian1557"
                ,"ian1475"
                ,"Object142"
                ,"Object120"
                ,"Object121"
                ,"Object88"
                ,"Object125"
                ,"Object126"
                ,"Object101"
                ,"Object93"
                ,"Object134"
                ,"Plane09"
                ,"Plane16"
                ,"Plane20"
                ,"Box169"
                ,"Box171"
                ,"Box173"
                ,"Box176"
                ,"Object151"
                ,"Box183"
                ,"Box182"
                ,"ian1634"
                ,"Object87"
                ,"Object99"
                ,"Object98"
                ,"Object97"
                ,"Object95"
                ,"Object94"
                ,"Box178"
                ,"Object85"
                ,"Object182"
                ,"Object84"
                ,"Object136"
                ,"ian1546"
                ,"Object96"
                ,"Object183"
                ,"Object154"
                ,"Object153"
                ,"Object152"
                ,"Object100"
                ,"Box184"
                ,"Object135"
                ,"Object86"
                ,"Object137"
                ,"ian1335"
                ,"Object42"
                ,"Object40"
                ,"Object38"
                ,"ian1589"
                ,"Object37"
                ,"ian1626"
                ,"Plane26"
                ,"ian1639"
                ,"Object51"
                ,"ian1624"
                ,"Object180"
                ,"Object181"
                ,"ian1591"
                ,"Object32"
                ,"Object33"
                ,"Object35"
                ,"Object36"
                ,"ian1590"
                ,"Object31"
                ,"ian1601"
                ,"ian1594"
                ,"Object30"
                ,"ian1600"
                ,"Object29"
                ,"Object28"
                ,"ian1642"
                ,"Plane32"
                ,"Object21"
                ,"ian1623"
                ,"Object27"
                ,"ian1640"
                ,"Object55"
                ,"ian1622"
                ,"Object175"
                ,"Object176"
                ,"Object166"
                ,"Object167"
                ,"Object168"
                ,"Object169"
                ,"Object170"
                ,"Object171"
                ,"Object20"
                ,"ian1631"
                ,"Object22"
                ,"ian1641"
                ,"Plane33"
                ,"ian1645"
                ,"Object177"
                ,"Object178"
                ,"Object179"
                ,"ian1597"
                ,"ian1599"
                ,"Object39"
                ,"Object43"
                ,"Box216"
                ,"Box203"
                ,"Plane27"
                ,"Box205"
                ,"Box214"
                ,"Box206"
                ,"Box207"
                ,"Box208"
                ,"Box215"
                ,"Object52"
                ,"Plane29"
                ,"Object53"
                ,"Plane28"
                ,"Object189"
                ,"Object16"
                ,"Box209"
                ,"Plane31"
                ,"Plane30"
                ,"Plane35"
                ,"Plane34"
                ,"Box210"
                ,"Object19"
                ,"Object03"
                ,"Box212"
                ,"Object07"
                ,"Object11"
                ,"ian1628"
                ,"ian1643"
                ,"Object09"
                ,"Object04"
                ,"Object186"
                ,"ian1629"
                ,"Object23"
                ,"ian1644"
                ,"Object173"
                ,"Object172"
                ,"Object174"
                ,"Object24"
                ,"ian1632"
                ,"ian1633"
                ,"Object138"
                ,"Object25"
                ,"ian1598"
                ,"Object18"
                ,"ian1595"
                ,"Object17"
                ,"Box211"
                ,"Box217"
                ,"Object187"
                ,"Object188"
                ,"Box213"
                ,"Object184"
                ,"Object185"
                ,"Box204"
                ,"Object54"
                ,"Object148"
                ,"Object147"
                ,"Object150"
                ,"Object149"
                ,"Object146"
                ,"Object145"
                ,"Object144"
                ,"Object143"
                ,"Object83"
                ,"ian1620"
                ,"Box185"
                ,"ian1619"
                ,"Object82"
                ,"Box186"
                ,"Object26"
        };
        for(int  i =0;i<sss13.length;i++){
            set13.add(sss13[i]);
        }
        String[] sss13_jxb = new String[]{};
        for(int i=0;i<sss13_jxb.length;i++){
            set13_jxb.add(sss13_jxb[i]);
        }




        String[] sss19 = new String []{
                "xu","Object73","fog","CBox","RoomG09","Plane18","Box03","","Loft13","Object46","Object71","Object72","Box646","cheng","ty1","RoomG07","ta03","Box502","ying","Box496","Box475","Loft12","Box643","qiang2","Box545","Box486","Box524","Box530","zuoying1","shi05","Box549","lan03","Object21","Plane20","Object95","Box02","pai1","Object19","Box84","roomtop","Loft03","Box151","Object01","Object22","Object18","Box286","Roomg0","Box285","rrr02","Box302","rrr","Box251","Box280","Box278","Object14","Box288","Box304","Box305","Object16","Box281","Box266","rrrr01","Box540","Roomg5","Box306","Object17","Object13","Object15","Box277","Box296","Box297","Box258","Box295","Box298","Object78","Box264","Object03","Box229","Object89","Object33","Box76","Object31","Object29","Object20","Object88","Object12","Box167","Object30","Box60","Object25","Object23","Loft04","Box241","Object87","roomg12","Object93","Object05","Box88","Object27","Object26","Box654","Object86","Box58","Object47","Box265","Object76","Object66","Object24","Object34","Object64","Object63","Object60","Object61","Object44","Object62","Box651","Object92","Box48","Box170","Box56","Box23","Box46","Box61","Box80","Box69","Box129","gggg","Box27","Plane10","Plane17","Box25","Box647","Object04","Plane11","Plane16","Box652","Object65","Box294","Box548","pop14","Box550","Box01","Box544","Box546","Box485","Box478","Box480","Box476","Box487","Box479","Box482","Box484","Box541","Box533","Box535","Box538","Box543","Box534","Box537","Box474","Box514","pop17","ui02","Box473","pop13","pop15","po02",
        };

        for(int  i =0;i<sss19.length;i++){
            set19.add(sss19[i]);
        }
        String[] sss19_jxb = new String []{};
        for(int i=0;i<sss19_jxb.length;i++){
            set19_jxb.add(sss19_jxb[i]);
        }


    }
    public static Properties pt = new Properties();

    /**
     * 将当前模型转化为一个以贴图为分组的模型对象
     * @param gf
     */
    public static void chick3(GmbFileFormat gf){

        int type =0;//0导出为jxb格式   1 导出obj格式


        //记录纹理名称对应一个模型缓存
        Map<String,MapMode> modemap = new HashMap<>();

        //<模型名称,<旧索引id,新索引id>>
        //Map<String,Map<Integer,Integer>> tex_idx_map = new HashMap<>();
        for(int i=0;i<gf.ObjectList.size();i++){
            GMBObj gmb = gf.ObjectList.get(i);
            if(set13.contains(gmb.ObjName)){
                continue;
            }
            Map<Integer,Integer> idx_map =   new HashMap<>();

            for(int j=0;j<gmb.unknow.length;j++){
                String key =pt.getProperty(String.valueOf(gmb.unknow[j])) ;

                MapMode mm = modemap.get(key);
                if(mm==null){
                    mm =new MapMode();
                    modemap.put(key,mm);
                }

                int firse  = gmb.Triangle[j*3];
                Integer newftid = idx_map.get(firse);
                if(newftid==null){
                    newftid = mm.vex.size();
                    float [] oldvex = new float[]{gmb.Positon[firse*3],gmb.Positon[firse*3+1],gmb.Positon[firse*3+2]   };
                    float [] olduv =null;
                    if(type==0){
                        olduv = new float[]{gmb.UV[firse*2],1 -  gmb.UV[firse*2+1]};
                    }else if(type==1){
                        olduv = new float[]{gmb.UV[firse*2],gmb.UV[firse*2+1]};
                    }


                    mm.vex.add(oldvex);
                    mm.uv.add(olduv);
                    idx_map.put(firse,newftid);
                }
                mm.idx.add(newftid);



                int second =  gmb.Triangle[j*3+1];
                Integer newstid =  idx_map.get(second);
                if(newstid==null){
                    newstid = mm.vex.size();
                    float[] oldvex = new float[]{gmb.Positon[second*3],gmb.Positon[second*3+1],gmb.Positon[second*3+2]  };
                    float[] olduv = null;
                    if(type==1){
                        olduv =  new float[]{gmb.UV[second*2],gmb.UV[second*2+1]};
                    }else if(type==0){
                        olduv =  new float[]{gmb.UV[second*2],1-gmb.UV[second*2+1]};
                    }

                    mm.vex.add(oldvex);
                    mm.uv.add(olduv);
                    idx_map.put(second,newstid);
                }
                mm.idx.add(newstid);

                int thread =  gmb.Triangle[j*3+2];
                Integer newttid = idx_map.get(thread);
                if(newttid==null){
                    newttid = mm.vex.size();


                    float[] oldvex = new float[]{gmb.Positon[thread*3],gmb.Positon[thread*3+1],gmb.Positon[thread*3+2] };
                    float[] olduv = null;

                    switch (type){
                        case 0:
                            olduv =  new float[]{gmb.UV[thread*2],1-gmb.UV[thread*2+1]};
                            break;
                        case 1:
                            olduv =  new float[]{gmb.UV[thread*2],gmb.UV[thread*2+1]};
                            break;
                    }


                    mm.vex.add(oldvex);
                    mm.uv.add(olduv);
                    idx_map.put(thread,newttid);
                }
                mm.idx.add(newttid);
            }
        }
        //将计算好的模型保存为jxb文件

        if(type==0){
            Iterator<String> strkey =   modemap.keySet().iterator();
            while(strkey.hasNext()){
                String sk = strkey.next();
               if(!set13_jxb.contains(sk)){
                   MapMode mm = modemap.get(sk);
                   Jxbdata jd = new Jxbdata();
                   jd.covlist(mm.vex,mm.uv,mm.idx);
                   try {
                       String fname ="";
                       if(sk.length()>=5){
                           fname = sk.substring(0,sk.length()-3)+"jxb";
                           System.out.print(" ,\"" +sk.substring(0,sk.length()-4) +"\""  );
                       }else{
                           fname = sk+".jxb";
                           System.out.print("模型可能不存在对应贴图，请检查");
                       }

                       jd.savedata(fname);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                }


            }
        }else if(type == 1){
            //将计算好的模型文件导出为obj，
            String filename = "ObjFileUtile" + System.currentTimeMillis()+".obj";
            String allfile = "C:\\Users\\Administrator\\Desktop\\map\\" + filename ;
            File tmp = new File(allfile);

            PrintWriter pw=null;
            try {
                tmp.createNewFile();
                pw = new PrintWriter(tmp);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int idx = 0;
            //modemap
            Iterator<String> strkey =   modemap.keySet().iterator();
            while(strkey.hasNext()){
                String sk = strkey.next();
                MapMode  mm = modemap.get(sk);
                for(int i=0;i<mm.vex.size();i++){
                    float [] v = mm.vex.get(i);
                    String str = "v " +v[0] + " " +  v[1]  + " " + v[2];
                    pw.println(str);
                }

                for(int i=0;i<mm.uv.size();i++){
                    float []u = mm.uv.get(i);
                    String str = "vt " + u[0] + " " +  u[1];
                    pw.println(str);
                }
                pw.println("g " + sk);
                for(int i=0;i<mm.idx.size()/3;i++){
                    int f = mm.idx.get(i*3);
                    int s = mm.idx.get(i*3+1);
                    int t = mm.idx.get(i*3+2);
                    String str = "f " + (idx + f + 1 )+"/"+ (idx +f + 1 ) + " " +

                            (idx +s + 1 ) +"/"+(idx +s + 1 )+   " " +

                            (idx +t + 1 ) +"/" + (idx +t + 1 )  ;
                    pw.println(str);

                }

                idx+=mm.vex.size();
            }
            pw.close();
        }

    }




    /**
     * 导出为一个统一的obj文件
     * @param gf
     */
    public static void chick2(GmbFileFormat gf) throws Exception {
        DecimalFormat df=new DecimalFormat("0.#########");


        String filename = "ObjFileUtile" + System.currentTimeMillis()+".obj";
        String allfile = "C:\\Users\\Administrator\\Desktop\\map\\" + filename ;

        //pt.load(new FileInputStream("../jacky-toool/assets/sn05map"));
        //PlistData pd = new PlistData("C:\\Users\\Administrator\\Desktop\\Plist.plist");

        File tmp = new File(allfile);
        tmp.createNewFile();
        PrintWriter pw = new PrintWriter(tmp);

        //处理单个gmb—obj文件中的纹理数据
        /*for(int i=0;i<gf.ObjectList.size();i++){
            int[] tidx = gf.ObjectList.get(i).Triangle;
            int[] unkonw = gf.ObjectList.get(i).unknow;

            float[] tmpuv = new float [gf.ObjectList.get(i).UV.length];

            for(int j=0;j<tidx.length/3;j++){
                String key = String.valueOf(unkonw[j]);
                key = "sn06/"+pt.getProperty(key);//plist中纹理key
                int first = tidx[j*3];
                float adsf[] =   pd.transition(key,new float[]{gf.ObjectList.get(i).UV[first*2],gf.ObjectList.get(i).UV[first*2+1]});
                tmpuv[first*2] = adsf[0];
                tmpuv[first*2+1] = adsf[1];

                int secton = tidx[j*3+1];
                adsf =   pd.transition(key,new float[]{gf.ObjectList.get(i).UV[secton*2],gf.ObjectList.get(i).UV[secton*2+1]});
                tmpuv[secton*2] = adsf[0];
                tmpuv[secton*2+1]= adsf[1];

                int thred = tidx[j*3+2];
                adsf =   pd.transition(key,new float[]{gf.ObjectList.get(i).UV[thred*2],gf.ObjectList.get(i).UV[thred*2+1]});
                tmpuv[thred*2] = adsf[0];
                tmpuv[thred*2+1]= adsf[1];

            }
            gf.ObjectList.get(i).UV = tmpuv;
            if("Box02".equals( gf.ObjectList.get(i).ObjName)){
                ObjFileUtile ofu = new ObjFileUtile(gf.ObjectList.get(i).Positon,gf.ObjectList.get(i).UV,gf.ObjectList.get(i).Triangle);
                ofu.name = gf.ObjectList.get(i).ObjName;
                ofu.save("C:\\Users\\Administrator\\Desktop\\map\\");
            }else{
                break;
            }


        }*/



        //导出整体gmb文件中数据到obj文件
        int idx = 0;
        for(int i=0;i<gf.ObjectList.size();i++){
            float[] v  = gf.ObjectList.get(i).Positon;
            float[] u  = gf.ObjectList.get(i).UV;
            int[] tidx = gf.ObjectList.get(i).Triangle;


            for(int j=0;j<v.length /3 ;j++){
                String str = "v " +v[j*3] + " " +  v[j*3+1]  + " " + v[j*3+2];
                pw.println(str);
            }



            for(int j=0;j<u.length/2;j++){
                String str = "vt " + df.format(u[ j*2 ]) + " " +  df.format(u[ j*2 +1 ]);
                pw.println(str);
            }
            pw.println("g " +  gf.ObjectList.get(i).ObjName);
            for(int j=0;j<tidx.length/3;j++){

                String str = "f " + (idx +tidx[j*3] + 1 )+"/"+ (idx +tidx[j*3] + 1 ) + " " +

                        (idx +tidx[j*3+1] + 1 ) +"/"+(idx +tidx[j*3+1] + 1 )+   " " +

                        (idx +tidx[j*3+2] + 1 ) +"/" + (idx +tidx[j*3+2] + 1 )  ;
                pw.println(str);
            }

            idx+=v.length/3;
        }

        pw.close();
    }



    /**
     * 检测地图文件使用到的纹理资源，（删除无用重复的地图纹理）
     * @param gf
     */
    public static void chick1(GmbFileFormat gf){
        //纹理索引的计数
        Map<Integer,Integer> texcount = new HashMap<>();

        for(int i=0;i<gf.ObjectList.size();i++){
            GMBObj gmb = gf.ObjectList.get(i);
            for(int j=0;j<gmb.unknow.length;j++){
                Integer count = texcount.get(gmb.unknow[j]);
                if(count==null){
                    count = 0;
                }
                count++;
                texcount.put(gmb.unknow[j],count);

            }
        }
        for(int i=0;i<gf.textureList.size();i++){
            System.out.println( (i+1) +"  " + gf.textureList.get(i));
        }
        Iterator<Integer> it =  texcount.keySet().iterator();
        while(it.hasNext()){
            Integer key = it.next();

            int indx = key - 1;
            if(indx<0||indx>=gf.textureList.size()){
                System.out.println( key+" = "+key);
            }else{
                System.out.println( key+" = "+gf.textureList.get( key - 1 ));
            }
        }

    }

    /**
     * 保存为单个obj文件
     * @param gf
     */
    public static void save2Obj(GmbFileFormat gf){
        for(int i=0;i<gf.ObjectList.size();i++){
            GMBObj gmb = gf.ObjectList.get(i);


            ObjFileUtile ofu = new ObjFileUtile(gmb.Positon,gmb.UV,gmb.Triangle);
            ofu.name = gmb.ObjName;
            try {
                ofu.save("C:\\Users\\Administrator\\Desktop\\map\\");
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }





}
