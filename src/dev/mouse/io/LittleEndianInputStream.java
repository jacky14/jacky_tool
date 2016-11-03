package dev.mouse.io;

import java.io.IOException;
import java.io.InputStream;
/**
 * 读取
 * @author DEVILIVED
 *
 */
public class LittleEndianInputStream {

	private InputStream is;
	
	private long position;
	
	public long getPosition() {
		return position;
	}
	public int available() throws IOException {
		return is.available();
	}
	public LittleEndianInputStream(InputStream is) {
		this.is = is;
	}
	public void skip(long length) throws IOException {
		this.position += this.is.skip(length);
	}
	public int readInt() throws IOException {
		byte[] buff = new byte[4];
		is.read(buff);
		this.position += 4;
		return (buff[3] << 24) 
			+ ((buff[2] << 24) >>> 8) 
			+ ((buff[1] << 24) >>> 16) 
			+ ((buff[0] << 24) >>> 24);
	}
	
	public int readUnsignedShort() 
		throws IOException {
		byte[] buff = new byte[2];
		is.read(buff);
		this.position += 2;
		return ((buff[1] << 24) >>> 16) 
				+ ((buff[0] << 24) >>> 24);
	}
	
	public short readShort() 
		throws IOException {
		byte[] buff = new byte[2];
		is.read(buff);
		this.position += 2;
		return (short)((buff[1] << 8) 
				+ ((buff[0] << 24) >>> 24));
	}

	public byte readByte() throws IOException {
		this.position += 1;
		return (byte) is.read();
	}
	
	public char readUnsignedChar()
		throws IOException {
		return (char) ((this.readByte() << 24) >>> 24);
	}
	
	public final float readFloat() throws IOException {
		int temp=this.readInt();
		return Float.intBitsToFloat(temp);
	}

	public int read(byte[] buff) 
		throws IOException {
		int count = this.is.read(buff);
		this.position += count;
		return count;
	}

	public String readString(int length) 
		throws IOException {
		byte[] buff = new byte[length];
		this.position += this.is.read(buff);
		return this.makeSafeString(buff);
	}
	
	public String readString() throws IOException {
		StringBuffer s = new StringBuffer();
		char c = 0;
		while((c = this.readUnsignedChar()) != 0)
			s.append(c);
		return s.toString();
	}
	
	public void close() throws IOException {
		if(is != null)
			is.close();
	}
	
	private final String makeSafeString(final byte buffer[]) {
        final int len = buffer.length;
        for (int i = 0; i < len; i++) {
            if (buffer[i] == (byte) 0) {
                return new String(buffer, 0, i);
            }
        }
        return new String(buffer).trim();
    }
}
