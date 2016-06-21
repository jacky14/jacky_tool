package tmp.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/18.
 */
public class Rect {
    public static float [][] test = new float [][]{
            {-420.5007f, -441.3484f},
            {479.4993f , -441.3484f},
            { -420.5007f, 458.5343f},
            {479.4993f ,  458.5343f},
    };
    public static void main (String [] args){
        double jd = 90;
        double hd = jd/180*Math.PI;



        double sinhd = Math.sin(hd);
        double coshd = Math.cos(hd);

        //œÚ¡ø
        double x = 0;
        double y = 1.414213562373095;

        double tmpx = x*coshd+y*sinhd;
        double tmpy = x*-sinhd+y*coshd;


        System.out.println(tmpx + "     " + tmpy);


    }

    public static void orderPoint(float[][] tmpf){
        float [][] lists = new float[4][];
        for(int i=0;i<tmpf.length;i++){
        }
    }

}
