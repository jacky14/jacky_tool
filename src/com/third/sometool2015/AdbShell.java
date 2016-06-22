package com.third.sometool2015;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;


public class AdbShell {
	public static String id ="";
	//air.com.aschord.goldflower 炸金花高手
	//air.com.igs.SICBO 甜心股宝
	//com.android.fileexplorer:remote   赛马_etrnalsimulato
	public static String packname="air.com.igs.SICBO";
	
	//https://play.google.com/store/apps/details?id=air.com.igs.SICBO
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		boolean test= true;
		
		long time = System.currentTimeMillis();
		
		while(test){
			long cu = System.currentTimeMillis();
			long sel = cu - time;
			if(sel<300){
				Thread.sleep(300 - sel);
				time = cu;
			}
			chick();
			if(id.trim().length()>0){
				test = false;
				//Thread.sleep(500);
				hack() ;
			}
		}
	}
	
	public static void hack() throws Exception{
		Process p  = Runtime.getRuntime().exec(" adb shell \r");
		OutputStream out =  p.getOutputStream();
		out.write("su \r".getBytes());
		out.write(("./data/local/tmp/hijack -d -p "+id+" -l /data/local/tmp/libexample.so \r").getBytes());
		out.write("exit \r".getBytes());
		out.write("exit \r".getBytes());
		out.flush();
		out.close();
		Scanner sc = new Scanner(p.getInputStream());
		while(sc.hasNext()){
			System.out.println(sc.nextLine());
		}
		sc.close();
	}
	
	public static void chick() throws Exception{
		Process p  = Runtime.getRuntime().exec(" adb shell \r");
		OutputStream out =  p.getOutputStream();
		out.write("ps \r".getBytes());
		out.write("exit \r".getBytes());
		out.flush();
		out.close();
		Scanner sc = new Scanner(p.getInputStream());
		while(sc.hasNext()){
			String temp = sc.nextLine();
			//System.out.println(temp);
			String[] str = temp.split("[ ]+");
			if(packname.equals(str[str.length-1])){
				id = str[1];
				System.out.println(str[str.length-1]  +  " ============  " + id);
			}
		}
		
		sc.close();
	}

	
	public static void mainzxc(String[] args) throws Exception {
		chick();
	}
}


/*com.pokercity.bydrqp.qihu ============  32748
shell@android:/ $ su 


shell@android:/ # ./data/local/tmp/hijack -d -p 32748 -l /data/local/tmp/libex
32748 -l /data/local/tmp/libex                                                <ample.so 


mprotect: 0x401e2adc

dlopen: 0x40177c2d

pc=401e3a50 lr=403965b5 sp=be82a590 fp=be82a74c

r0=fffffffc r1=be82a5d8

r2=10 r3=11b0

stack: 0xbe80a000-0xbe82b000 leng = 135168

executing injection code at 0xbe82a540

calling mprotect

library injection completed!

shell@android:/ # exit */

