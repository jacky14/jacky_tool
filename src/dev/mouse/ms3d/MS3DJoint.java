package dev.mouse.ms3d;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import dev.mouse.io.LittleEndianInputStream;
import dev.mouse.lib.Matrix4f;
import dev.mouse.lib.Vector3f;
import dev.mouse.lib.Vector4f;


/**
 * �ؽ���Ϣ
 * @author DEVILIVED
 *
 */
public class MS3DJoint {
	
	private byte flag;	//����
	
	private String name;	//�ؽ�����
	
	private String parentName;	//���ؽ�����
	
	private MS3DJoint parent;		//���ؽ�
	
	private Vector3f rotate;	//��ʼ��תֵ
	
	private Vector3f position;	//��ʼλ��
	
	private MS3DKeyFrameRotate[] rotates;	//�ؼ�֡��תֵ
	
	private MS3DKeyFramePosition[] positions;	//�ؼ�֡λ������
	
	
	
	public MS3DJoint() {}

	public void setFlag(byte flag) {
		this.flag = flag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public void setParent(MS3DJoint parent) {
		this.parent = parent;
	}

	public void setRotate(Vector3f rotate) {
		this.rotate = rotate;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setRotates(MS3DKeyFrameRotate[] rotates) {
		this.rotates = rotates;
	}

	public void setPositions(MS3DKeyFramePosition[] positions) {
		this.positions = positions;
	}

	public void setRelative(Matrix4f relative) {
		this.relative = relative;
	}

	public void setAbsolute(Matrix4f absolute) {
		this.absolute = absolute;
	}

	public void setMatrix(Matrix4f matrix) {
		this.matrix = matrix;
	}
	private Matrix4f relative;			//��Ծ���
	
	private Matrix4f absolute;			//���Ծ���
	
	public Matrix4f matrix;			//�任����
	
	
	public Matrix4f am;//�ܱ�þ���
	
	/**
	 * ���عؽڲ���
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public final static MS3DJoint[] load(LittleEndianInputStream is) throws IOException {
		int count = is.readUnsignedShort();
		int numKeyFramesRot, numKeyFramesPos;
		MS3DJoint[] joints = new MS3DJoint[count];
		MS3DJoint joint = null;
		Map<String, MS3DJoint> map = new LinkedHashMap<String, MS3DJoint>();
		for(int i=0; i<count; i++) {
			
			joint = new MS3DJoint();
			joint.flag = is.readByte();
			joint.name = is.readString(32);
			joint.parentName = is.readString(32);
			joint.rotate = new Vector3f(
					is.readFloat(),
					is.readFloat(),
					is.readFloat());
			joint.position = new Vector3f(
					is.readFloat(),
					is.readFloat(),
					is.readFloat());
			numKeyFramesRot = is.readUnsignedShort();
			
			numKeyFramesPos = is.readUnsignedShort();
			
			if(numKeyFramesRot > 0)
				joint.rotates = MS3DKeyFrameRotate.load(is, numKeyFramesRot);
			if(numKeyFramesPos > 0)
				joint.positions = MS3DKeyFramePosition.load(is, numKeyFramesPos);
			joints[i] = joint;
			//���Ҹ��ؽ�
			map.put(joint.name, joint);
			//��ø��ؽ�
			joint.parent = map.get(joint.parentName); 
			
			//������Ծ���
			joint.relative = new Matrix4f();
			joint.relative.loadIdentity();
			//������ת
			joint.relative.setRotationRadians(joint.rotate);
			//����λ��
			joint.relative.setTranslation(joint.position);
			//���þ��Ծ���
			joint.absolute = new Matrix4f();
			joint.absolute.loadIdentity();
			//�Ƿ��и��ؽ�
			if(joint.parent != null) {
				//�и��ؽ�
				joint.absolute.mul(joint.parent.absolute, joint.relative);
			} else {
				//�޸��ؽ�
				joint.absolute.set(joint.relative);
			}
			
		}
		map.clear();
		map = null;
		return joints;
	}

	
	public final void update(float time,Boolean isman) {
		//û����ת��Ϣ��û��λ��ƫ����Ϣ
		if(this.rotates == null && this.positions == null) {
			if(this.matrix==null)
				this.matrix=new Matrix4f();
			this.matrix.set(this.absolute);
			return;
		}
		//����ת
		Matrix4f matrix = this.rotate(time);
		//λ��
		
		Vector3f v3=this.position(time,isman);
		
		matrix.setTranslation(v3);
		//��������Ծ������
		matrix.mul(this.relative, matrix);
		//�Ƿ��и��ؽ�
		if(this.parent != null) {
			//�и��ؽ�
			this.matrix = matrix.mul(this.parent.matrix, matrix);
		} else {
			//�޸��ؽ�
			this.matrix = matrix;
		}
		//am=new Matrix4f();
		
		//am.
		
		
	}
	/**
	 * ��ֵ������ת
	 * @param time
	 * @return
	 */
	private Matrix4f rotate(float time) {
		Matrix4f m = new Matrix4f();
		int index = 0;
		int size = this.rotates.length;
		while(index < size && this.rotates[index].getTime() < time)
			index ++;
		if(index == 0) {
			m.set(this.rotates[0].getRotate());
		} else if(index == size) {
			m.set(this.rotates[size - 1].getRotate());
		} else {
			MS3DKeyFrameRotate left = this.rotates[index - 1];
			MS3DKeyFrameRotate right = this.rotates[index];
			Vector4f v = new Vector4f();
			v.interpolate(
					left.getRotate(), 
					right.getRotate(), 
					(time - left.getTime()) / (right.getTime() - left.getTime()));
			m.set(v);
		}
		
		return m;
	}
	
	/**
	 * ��ֵ����λ��
	 * @param time
	 * @return
	 */
	private Vector3f position(float time,Boolean isman) {
		int index = 0;
		int size = this.positions.length;
		while(index < size && this.positions[index].getTime() < time)
			index ++;
		
		
		if(index == 0) {
			return this.positions[0].getPosition();
		} else if(index == size) {
			return this.positions[size - 1].getPosition();
		} else {
			MS3DKeyFramePosition left = this.positions[index - 1];
			MS3DKeyFramePosition right = this.positions[index];
			Vector3f v = new Vector3f();
			v.interpolate(
					left.getPosition(), 
					right.getPosition(), 
					(time - left.getTime()) / (right.getTime() - left.getTime()));
			if(isman!=null&&isman&&index>=9&&index<=45){
				v.setX(-2);
			}
			return v;
		}
	}
	
	public final byte getFlag() {
		return flag;
	}

	public final String getName() {
		return name;
	}

	public final String getParentName() {
		return parentName;
	}

	public final Vector3f getRotate() {
		return rotate;
	}

	public final Vector3f getPosition() {
		return position;
	}

	public final MS3DKeyFrameRotate[] getRotates() {
		return rotates;
	}

	public final MS3DKeyFramePosition[] getPositions() {
		return positions;
	}

	public final MS3DJoint getParent() {
		return parent;
	}
/**
 * //�任����
 * @return
 */
	public final Matrix4f getMatrix() {
		return matrix;
	}

	public final Matrix4f getRelative() {
		return relative;
	}

	/**
	 * //���Ծ���
	 * @return
	 */
	public final Matrix4f getAbsolute() {
		return absolute;
	}

}
