package dev.mouse.lib;
/**
 * зјБъ
 * @author DEVILIVED
 *
 */
public class Vector2f {

	private float[] coording = new float[2];
	
	public Vector2f() {}
	
	public Vector2f(float x, float y) {
		this.setX(x);
		this.setY(y);
	}
	
	public final void setX(float x) {
		this.coording[0] = x;
	}
	
	public final void setY(float y) {
		this.coording[1] = y;
	}
	
	public final float[] getVectorArray() {
		return this.coording;
	}
	
	public final float getX() {
		return this.coording[0];
	}
	
	public final float getY() {
		return this.coording[1];
	}
}
