package dev.mouse.util;
/**
 * fps计算
 * @author DEVILIVED
 *
 */
public class FPSCalc {

	private long currentTime;	//当前时间
	
	private long interval;		//间隔时间
	
	private float fps;			//帧
	
	public final void start() {
		this.currentTime = System.currentTimeMillis();
	}
	
	public final float getFPS() {
		return this.fps;
	}
	
	public final void finish() {
		interval = System.currentTimeMillis() - this.currentTime;
		if(interval > 0)
			fps = 1000 / interval;
		else 
			fps = 1000;
	}

	public final long getInterval() {
		return interval;
	}
	
}
