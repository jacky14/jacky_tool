package dev.mouse.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class BufferBuilder {

	public final static IntBuffer buildIntBuffer(int length) {
		IntBuffer i_buffer = null;
		ByteBuffer b_buffer = ByteBuffer.allocateDirect(length * 4);
		b_buffer.order(ByteOrder.nativeOrder());
        i_buffer = b_buffer.asIntBuffer();
        return i_buffer;
	}
	
	public final static FloatBuffer buildFloatBuffer(int length) {
		FloatBuffer f_buffer = null;
		ByteBuffer b_buffer = ByteBuffer.allocateDirect(length * 4);
		b_buffer.order(ByteOrder.nativeOrder());
		f_buffer = b_buffer.asFloatBuffer();
		return f_buffer;
	}
	
	public final static ShortBuffer buildShortBuffer(int length) {
		ShortBuffer s_buffer = null;
		ByteBuffer b_buffer = ByteBuffer.allocateDirect(length * 2);
		b_buffer.order(ByteOrder.nativeOrder());
		s_buffer = b_buffer.asShortBuffer();
		return s_buffer;
	}
	
	
	public final static IntBuffer buildIntBuffer(int[] buffer) {
		IntBuffer i_buffer = null;
		if(buffer != null) {
			ByteBuffer b_buffer = ByteBuffer.allocateDirect(buffer.length * 4);
			b_buffer.order(ByteOrder.nativeOrder());
	        i_buffer = b_buffer.asIntBuffer();
	        i_buffer.put(buffer);
	        i_buffer.position(0);
		}
		return i_buffer;
	}
	
	public final static FloatBuffer buildFloatBuffer(float[] buffer) {
		FloatBuffer f_buffer = null;
		if(buffer != null) {
			ByteBuffer b_buffer = ByteBuffer.allocateDirect(buffer.length * 4);
			b_buffer.order(ByteOrder.nativeOrder());
			f_buffer = b_buffer.asFloatBuffer();
			f_buffer.put(buffer);
			f_buffer.position(0);
		}
		return f_buffer;
	}
	
	public final static ShortBuffer buildShortBuffer(short[] buffer) {
		ShortBuffer s_buffer = null;
		if(buffer != null) {
			ByteBuffer b_buffer = ByteBuffer.allocateDirect(buffer.length * 2);
			b_buffer.order(ByteOrder.nativeOrder());
			s_buffer = b_buffer.asShortBuffer();
			s_buffer.put(buffer);
			s_buffer.position(0);
		}
		return s_buffer;
	}
	
}
