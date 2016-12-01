package com.jacky.engine.resource.meteorFile;


import java.util.HashMap;
import java.util.Map;

public class GMBObj {

	public String ObjName;
	int vertexCount; // 0x04000000
	int indicesCount; // 0x02000000
	
	public float [] Positon;
	float [] Normal;
	int[] Color;
	public float [] UV;
	
	public int[] unknow;
	
	
	public int []Triangle;
	
	float FaceNormal[]; // ?

	//记录旧3角形顶点对应的新顶点值  《旧3角形顶点,新三角形顶点》
	//public Map<Integer,Integer> newtriangle = new HashMap<Integer,Integer>();

}
