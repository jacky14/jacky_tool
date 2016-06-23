package dev.mouse.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class FloatBufferBuilder {

	private FloatBuffer f_buffer;
	
	public final void begin(int buffer_length) {
		ByteBuffer b_buffer = ByteBuffer.allocateDirect(buffer_length * 4);
		b_buffer.order(ByteOrder.nativeOrder());
		f_buffer = b_buffer.asFloatBuffer();
	}
	
	public final void put(float buff) {
		f_buffer.put(buff);
	}
	
	public final void put(float[] buff) {
		f_buffer.put(buff);
	}
	
	public final void put(FloatBuffer buff) {
		f_buffer.put(buff);
	}
	
	public final void put(int index, float buff) {
		f_buffer.put(index, buff);
	}
	
	public final void put(float[] buff, int offset, int len) {
		f_buffer.put(buff, offset, len);
	}
	
	public final void finish() {
		f_buffer.position(0);
	}
	
	public final FloatBuffer getFloatBuffer() {
		return this.f_buffer;
	}
	
}
