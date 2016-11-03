package dev.mouse.ms3d;

import java.io.IOException;

import dev.mouse.io.LittleEndianInputStream;
import dev.mouse.lib.Vector3f;


/**
 * 顶点信息
 * @author DEVILIVED
 *
 */
public class MS3DVertex {
	
	private byte flag;		//无用
	
	private Vector3f vertex;	//顶点信息 不会改变
	
	private Vector3f buffer;	//顶点缓冲
	
	private byte bone;			//骨骼ID
	
	private byte none;			//保留无用
	
	private Vector3f verbuff;//骨骼本地坐标值，通常第一次加载时计算完成，以后无需计算
	
	private Vector3f newverbuff;//武器模型骨骼本地坐标值，通常第一次加载时计算完成，以后无需计算
	
	
	public Vector3f getNewverbuff() {
		return newverbuff;
	}

	public void setNewverbuff(Vector3f newverbuff) {
		this.newverbuff = newverbuff;
	}

	public Vector3f getVerbuff() {
		return verbuff;
	}

	public void setVerbuff(Vector3f verbuff) {
		this.verbuff = verbuff;
	}

	public void setBone(byte bone) {
		this.bone = bone;
	}

	private MS3DVertex() {}

	/**
	 * 读取顶点信息
	 * @param is
	 * @return
	 * @throws IOException 
	 */
	public final static MS3DVertex[] load(LittleEndianInputStream is,float[] mMMatrix) throws IOException {
		int count = is.readUnsignedShort();
		MS3DVertex[] vertexs = new MS3DVertex[count];
		MS3DVertex vertex = null;
		for(int i=0; i<count; i++) {
			vertex = new MS3DVertex();
			vertex.flag = is.readByte();
			
			if(mMMatrix!=null){
				vertex.vertex = new Vector3f(
						(is.readFloat()+mMMatrix[0]),
						(is.readFloat()+mMMatrix[1]),
						(is.readFloat()+mMMatrix[2]));
			}else{
				vertex.vertex = new Vector3f(
						(is.readFloat()),
						(is.readFloat()),
						(is.readFloat()));
			}
			
			
			
			
			
			vertex.bone = is.readByte();
			vertex.none = is.readByte();
			vertexs[i] = vertex;
		}
		return vertexs;
	}

	public final byte getFlag() {
		return flag;
	}

	public final Vector3f getVertex() {
		return vertex;
	}

	public final byte getBone() {
		return bone;
	}

	public final byte getNone() {
		return none;
	}

	public final Vector3f getBuffer() {
		return buffer;
	}

	public final void setBuffer(Vector3f buffer) {
		this.buffer = buffer;
	}

	
	
	
}
