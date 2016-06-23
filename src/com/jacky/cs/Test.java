package com.jacky.cs;

import dev.mouse.lib.Matrix4f;
import dev.mouse.lib.Vector3f;

public class Test {
	public static void main(String[] args) {
		Matrix4f juzhen = new Matrix4f();
		juzhen.loadIdentity();
		
		//设置旋转
		juzhen.setRotationRadians(new  Vector3f(-1.5707964f, -1.570795f, 0.0f));//[-1.5707964, -1.570795, 0.0]
		//设置位移
		juzhen.setTranslation(new  Vector3f(0.233849f, 38.19215f, 2.25168f));//[0.233849, 38.19215, 2.25168]
		
		double x = Math.atan2(juzhen.getMatrixArray()[2][1],juzhen.getMatrixArray()[2][2]);
		
		
		double z =  Math.atan2(juzhen.getMatrixArray()[0][1],juzhen.getMatrixArray()[0][0]);
		
		
		System.out.println(x);
		System.out.println(z);
	}
}
