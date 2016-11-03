package dev.mouse.ms3d;

import java.io.IOException;

import dev.mouse.io.LittleEndianInputStream;
import dev.mouse.lib.Vector3f;

/**
 * 关节关键帧位置信息
 * @author DEVILIVED
 *
 */
public class MS3DKeyFramePosition {
	
	public float time;				//时间
	
	private Vector3f position;		//位置坐标

	private MS3DKeyFramePosition() {}
	
	public final static MS3DKeyFramePosition[] load(LittleEndianInputStream is, int num) throws IOException {
		MS3DKeyFramePosition[] positions = new MS3DKeyFramePosition[num];
		MS3DKeyFramePosition position = null;
		for(int i=0; i<num; i++) {
			position = new MS3DKeyFramePosition();
			position.time = is.readFloat();
			position.position = new Vector3f(
					is.readFloat(), 
					is.readFloat(), 
					is.readFloat());
			positions[i] = position;
		}
		return positions;
	}

	public final float getTime() {
		return time;
	}

	public final Vector3f getPosition() {
		return position;
	}
}
