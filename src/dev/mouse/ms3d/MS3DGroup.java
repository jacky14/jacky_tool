package dev.mouse.ms3d;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import dev.mouse.io.LittleEndianInputStream;


/**
 * ����Ϣ
 * @author DEVILIVED
 *
 */
public class MS3DGroup {

	private byte flag;			//����
	
	private String name;		//����
	
	private int[] indicies;		//����
	
	
	public Map<Byte,List<Integer>> bone_indicies;//��������������Ϣ�ٷֳɲ�ͬ������
	
	public Map<Byte,List<Float>> bone_tex;//��������������Ϣ�ٷֳɲ�ͬ������
	private byte materialIndex;	//�������
	
	
	
	
	
	private MS3DGroup() {}
	
	public final static MS3DGroup[] load(LittleEndianInputStream is) throws IOException {
		int count = is.readUnsignedShort();
		MS3DGroup[] groups = new MS3DGroup[count];
		int indexCount;
		for(int i=0; i<count; i++) {
			MS3DGroup group = new MS3DGroup();
			group.flag = is.readByte();
			group.name = is.readString(32);
			indexCount = is.readUnsignedShort();
			group.indicies = new int[indexCount];
			for(int j=0; j<indexCount; j++) {
				group.indicies[j] = is.readUnsignedShort();
			}
			group.materialIndex = is.readByte();
			groups[i] = group;
		}
		return groups;
	}

	public final byte getFlag() {
		return flag;
	}

	public final String getName() {
		return name;
	}

	public final int[] getIndicies() {
		return indicies;
	}

	public final byte getMaterialIndex() {
		return materialIndex;
	}
	
	
}
