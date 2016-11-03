package com.jacky.cs;

import java.io.FileInputStream;

import dev.mouse.lib.Vector3f;
import dev.mouse.ms3d.MS3DModel;
import dev.mouse.util.TextureManager;

public class Ms3dRead {

	
	public static void main(String[] args) throws Exception {
		TextureManager manager=new TextureManager();
		
		MS3DModel ms3d = MS3DModel.load(new FileInputStream("F:\\mypro\\CounterStrike\\assets\\ak47.ms3d"), null, manager, null);
		
		
		
		
		Ms3dOutTxt mo = new Ms3dOutTxt(ms3d);
		
		mo.save();
		
		
		//第三人称模式下武器变换到本地坐标空间
		/*MS3DModel akp = MS3DModel.load(new FileInputStream("F:\\mypro\\CounterStrike\\assets\\p_ak47.ms3d"), null, manager, null);
		for(int i=0;i<akp.getVertexs().length;i++){
			Vector3f v3 = ms3d.joints[26].getAbsolute().invTransformAndRotate(akp.getVertexs()[i].getVertex());
			akp.getVertexs()[i].setVerbuff(v3);
		}
		akp.saveObj("C:\\Users\\Administrator\\Desktop\\ak.obj");*/
		System.out.println(ms3d);
	}
	
	
}
