package com.jacky14.metor;


public class Vertex {

	
	/**
	 * 顶点
	 */
	public float[] verts;
	/**
	 * 纹理坐标
	 */
	public float[] uvs;
	
	/**
	 * 顶点绑定到的骨骼索引
	 */
	public short[] bone_index; 
	/**
	 * 各个骨骼对顶点影响权重  与上面数组长度一致
	 */
	public float[] bone_weight;

	/**
	 * 每个顶点对应的材质索引
	 */
	public int matid = -1;
}
