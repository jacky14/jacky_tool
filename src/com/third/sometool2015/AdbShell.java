package com.third.sometool2015;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * 辅助adbi 钩子注入程序，无需手动输入注入命令 只需填写包名后 精准快捷的自动执行钩子命令
 * @author Administrator
 *
 */
public class AdbShell {
	public static String id ="";
	public static String packname="com.jackylua.ts3d";
	//https://play.google.com/store/apps/details?id=air.com.igs.SICBO
	//adb pull /data/local/tmp/adbi_example.log  d:/
	
	/*ndk-build;
	adb push libs/armeabi/libexample.so /data/local/tmp/*/
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		boolean test= true;
		
		long time = System.currentTimeMillis();
		
		while(test){                                 //my_jackytest
			long cu = System.currentTimeMillis();
			long sel = cu - time;
			if(sel<300){
				Thread.sleep(300 - sel);
				time = cu;
			}
			chick();
			if(id.trim().length()>0){
				test = false;
				Thread.sleep(300);
				hack() ;
			}
		}
	}
	//./hijack -d -p 4442 -l /data/local/tmp/libexample.so
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

