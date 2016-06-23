package dev.mouse.ms3d;

import java.io.IOException;

import dev.mouse.io.LittleEndianInputStream;
import dev.mouse.lib.Vector3f;


/**
 * ������Ϣ
 * @author DEVILIVED
 *
 */
public class MS3DVertex {
	
	private byte flag;		//����
	
	private Vector3f vertex;	//������Ϣ ����ı�
	
	private Vector3f buffer;	//���㻺��
	
	private byte bone;			//����ID
	
	private byte none;			//��������
	
	private Vector3f verbuff;//������������ֵ��ͨ����һ�μ���ʱ������ɣ��Ժ��������
	
	private Vector3f newverbuff;//����ģ�͹�����������ֵ��ͨ����һ�μ���ʱ������ɣ��Ժ��������
	
	
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
	 * ��ȡ������Ϣ
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
