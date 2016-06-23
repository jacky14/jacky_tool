package dev.mouse.ms3d;

import java.io.IOException;

import dev.mouse.io.LittleEndianInputStream;
/**
 * MS3D�ļ�ͷ��Ϣ
 * @author DEVILIVED
 *
 */
public class MS3DHeader {

	private String id;
	
	private int version;
	
	private MS3DHeader(String id, int version) {
		this.id = id;
		this.version = version;
	}
	/**
	 * ��ȡ�汾��Ϣ
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public final static MS3DHeader load(LittleEndianInputStream is) throws IOException {
		return new MS3DHeader(is.readString(10), is.readInt());
	}

	public final String getId() {
		return id;
	}

	public final int getVersion() {
		return version;
	}
}
