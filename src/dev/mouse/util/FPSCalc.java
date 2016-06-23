package dev.mouse.util;
/**
 * fps����
 * @author DEVILIVED
 *
 */
public class FPSCalc {

	private long currentTime;	//��ǰʱ��
	
	private long interval;		//���ʱ��
	
	private float fps;			//֡
	
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
