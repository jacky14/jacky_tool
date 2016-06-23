package dev.mouse.ms3d;

import java.io.IOException;

import dev.mouse.io.LittleEndianInputStream;
import dev.mouse.lib.Vector3f;
import dev.mouse.lib.Vector4f;
/**
 * 关节关键帧旋转信息
 * @author DEVILIVED
 *
 */
public class MS3DKeyFrameRotate {

	private float time;			//时间
	
	private Vector4f rotate;	//旋转向量

	public  Vector3f olj;
	
	private MS3DKeyFrameRotate() {}
	
	public final static MS3DKeyFrameRotate[] load(LittleEndianInputStream is, int num) throws IOException {
		MS3DKeyFrameRotate[] rotates = new MS3DKeyFrameRotate[num];
		MS3DKeyFrameRotate rotate = null;
		for(int i=0; i<num; i++) {
			rotate = new MS3DKeyFrameRotate();
			rotate.time = is.readFloat();
			rotate.rotate = new Vector4f();
			rotate.olj = new Vector3f(is.readFloat(), 
					is.readFloat(), 
					is.readFloat());
			rotate.rotate.set(
					rotate.olj.getX(), 
					rotate.olj.getY(), 
					rotate.olj.getZ());
			rotates[i] = rotate;
		}
		return rotates;
	}

	public final float getTime() {
		return time;
	}

	public final Vector4f getRotate() {
		return rotate;
	}
}
