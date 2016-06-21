package com.jacky14.util;

import com.jacky.engine.viewnode.Node2D;
import com.jacky14.pl.xml.PlistData;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Created by Administrator on 2016/4/11.
 */
public class Plist2muv {
    public static void main(String  [] args ) throws Exception {
        PlistData pd = new PlistData("C:\\Users\\Administrator\\Desktop\\Plist1.plist");

        File fl = new File("C:\\Users\\Administrator\\Desktop\\prs.muv");
        fl.createNewFile();

        PrintWriter pw =new PrintWriter(fl);
        Iterator<String> keys =  pd.frames.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            StringBuffer tmpstr = new StringBuffer();
            tmpstr.append(key + " ");
            for(int i=0;i<Node2D.texarr.length/2;i++){
                float[] olduv = new float [] {Node2D.texarr[i*2],Node2D.texarr[i*2+1]};
                float[] tmpuv = pd.transition(key,olduv);
                if(i == (Node2D.texarr.length/2 - 1)){
                    tmpstr.append(tmpuv[0] + " " + tmpuv[1] );
                }else{
                    tmpstr.append(tmpuv[0] + " " + tmpuv[1] + " ");
                }
            }
            pw.print(tmpstr.toString() + "#");
        }
        pw.close();


    }

}
