package com.jacky14.timehelp;

/**
 * Created by Administrator on 2015/12/20.
 */
public class MyClock {
    long start;

    long last;
    public  MyClock(){
        start = System.currentTimeMillis();
    }

    public void showtime(){
        System.out.println("经历的毫秒数："+ (System.currentTimeMillis() - start));
    }
}
