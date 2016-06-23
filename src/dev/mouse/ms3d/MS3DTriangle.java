package dev.mouse.ms3d;

import java.io.IOException;

import dev.mouse.io.LittleEndianInputStream;
import dev.mouse.lib.Vector3f;


/**
 * 三角形
 * @author DEVILIVED
 *
 */
public class MS3DTriangle {
	
	private int flag;			//无用
	
	private int[] indexs;		//索引序号 
	
	private Vector3f[] normals;	//3个顶点法线向量
	
	private Vector3f s;			//应该是UV坐标的x
	
	private Vector3f t;			//应该是UV坐标的y
	
	private byte smoothingGroup; //这个不知道神马用处
	
	private byte groupIndex;	//就是不知道了(⊙o⊙)
	
	private MS3DTriangle() {}

	public final static MS3DTriangle[] load(LittleEndianInputStream is) throws IOException {
		int count = is.readUnsignedShort();
		MS3DTriangle[] triangles = new MS3DTriangle[count];
		MS3DTriangle triangle = null;
		for(int i=0; i<count; i++) {
			triangle = new MS3DTriangle();
			triangle.flag = is.readUnsignedShort();
			triangle.indexs = new int[]{
				is.readUnsignedShort(),
				is.readUnsignedShort(),
				is.readUnsignedShort()
			};
			
			triangle.normals = new Vector3f[3];
			for(int j=0; j<3; j++) {
				triangle.normals[j] = new Vector3f(
						is.readFloat(),
						is.readFloat(),
						is.readFloat());
			}
			triangle.s = new Vector3f(
					is.readFloat(),
					is.readFloat(),
					is.readFloat()); 
			
			triangle.t = new Vector3f(
					is.readFloat(),
					is.readFloat(),
					is.readFloat());
			
			
			triangle.smoothingGroup = is.readByte();
			
			triangle.groupIndex = is.readByte();
			
			triangles[i] = triangle;
		}
		return triangles;
	}

	public final int getFlag() {
		return flag;
	}

	public final int[] getIndexs() {
		return indexs;
	}

	public final Vector3f[] getNormals() {
		return normals;
	}

	public final Vector3f getS() {
		return s;
	}

	public final Vector3f getT() {
		return t;
	}

	public final byte getSmoothingGroup() {
		return smoothingGroup;
	}

	public final byte getGroupIndex() {
		return groupIndex;
	}
	
	
	
}
