package dev.mouse.ms3d;

import java.io.IOException;

import dev.mouse.io.LittleEndianInputStream;
/**
 * MS3D文件头信息
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
	 * 获取版本信息
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
