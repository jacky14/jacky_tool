package dev.mouse.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
/**
 * ¶ÁÈ¡Êý¾Ý
 * @author DEVILIVED
 *
 */
public class DevRandomAccessFile {
	
	private RandomAccessFile randomAccessFile;

	public DevRandomAccessFile(File file, String mode)
			throws FileNotFoundException {
		randomAccessFile = new RandomAccessFile(file, mode);
	}
	public DevRandomAccessFile(String name, String mode)
			throws FileNotFoundException {
		randomAccessFile = new RandomAccessFile(name, mode);
	}

	public int readInt() throws IOException {
		byte[] buff = new byte[4];
		randomAccessFile.read(buff);
		return (buff[3] << 24) 
			+ ((buff[2] << 24) >>> 8) 
			+ ((buff[1] << 24) >>> 16) 
			+ ((buff[0] << 24) >>> 24);
	}
	
	public short readUnsignedShort() 
		throws IOException {
		byte[] buff = new byte[2];
		randomAccessFile.read(buff);
		return (short)(((buff[1] << 24) >>> 16)+ ((buff[0] << 24) >>> 24));
	}
	
	public short readShort() 
		throws IOException {
		byte[] buff = new byte[2];
		randomAccessFile.read(buff);
		return (short)((buff[1] << 8) 
				+ ((buff[0] << 24) >>> 24));
	}

	public char readUnsignedChar()
		throws IOException {
		return (char) ((this.randomAccessFile.readByte() << 24) >>> 24);
	}
	
	public final float readFloat() throws IOException {
		return Float.intBitsToFloat(this.readInt());
	}

	public int read(byte[] buff) 
		throws IOException {
		return this.randomAccessFile.read(buff);
	}

	public String readString(int length) 
		throws IOException {
		byte[] buff = new byte[length];
		this.randomAccessFile.read(buff);
		return this.makeSafeString(buff);
	}
	
	
	public void seek(long pos) throws IOException {
		this.randomAccessFile.seek(pos);
	}
	
	public void close() throws IOException {
		this.randomAccessFile.close();
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
