package com.jacky14.metor;


public class Vertex {

	
	/**
	 * ����
	 */
	public float[] verts;
	/**
	 * ��������
	 */
	public float[] uvs;
	
	/**
	 * ����󶨵��Ĺ�������
	 */
	public short[] bone_index; 
	/**
	 * ���������Զ���Ӱ��Ȩ��  ���������鳤��һ��
	 */
	public float[] bone_weight;

	/**
	 * ÿ�������Ӧ�Ĳ�������
	 */
	public int matid = -1;
}
