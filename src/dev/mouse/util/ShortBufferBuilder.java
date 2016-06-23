package dev.mouse.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public class ShortBufferBuilder {

	private ShortBuffer f_buffer;
	
	
	public final void begin(int buffer_length) {
		ByteBuffer b_buffer = ByteBuffer.allocateDirect(buffer_length * 2);
		b_buffer.order(ByteOrder.nativeOrder());
		f_buffer = b_buffer.asShortBuffer();
	}
	
	public final void put(short buff) {
		f_buffer.put(buff);
	}
	
	public final void put(short[] buff) {
		f_buffer.put(buff);
	}
	
	public final void put(ShortBuffer buff) {
		f_buffer.put(buff);
	}
	
	public final void put(int index, short buff) {
		f_buffer.put(index, buff);
	}
	
	public final void put(short[] buff, int offset, int len) {
		f_buffer.put(buff, offset, len);
	}
	
	public final void finish() {
		f_buffer.position(0);
	}
	
	public final ShortBuffer getShortBuffer() {
		return this.f_buffer;
	}
}
