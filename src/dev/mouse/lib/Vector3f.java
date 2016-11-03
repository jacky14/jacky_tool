package dev.mouse.lib;


/**
 * 向量
 * @author DEVILIVED
 *
 */
public class Vector3f {

	private float[] coord = new float[3];	//坐标
	
	
	public Vector3f() {
		
	}
	public Vector3f(float x, float y, float z) {
		this.coord[0] = x;
		this.coord[1] = y;
		this.coord[2] = z;
	}
	/**
	 * 摘自别人的代码
	 * @param v1
	 * @param v2
	 * @param alpha
	 */
	public final void interpolate(Vector3f v1, Vector3f v2, float alpha) {
		this.setX((1 - alpha) * v1.getX() + alpha * v2.getX());
		this.setY((1 - alpha) * v1.getY() + alpha * v2.getY());
		this.setZ((1 - alpha) * v1.getZ() + alpha * v2.getZ());
	}
	
	/**
	 * v1 点乘 v2后赋值给当前对象
	 * @param v1
	 * @param v2
	 * @return this
	 */
	public final Vector3f multiByPoint(Vector3f v1, Vector3f v2) {
		this.setX(v1.getX() * v2.getX());
		this.setY(v1.getY() * v2.getY());
		this.setZ(v1.getZ() * v2.getZ());
		return this;
	}
	/**
	 *当前对象点乘 v
	 * @param v
	 * @return this
	 */
	public final Vector3f multiByPoint(Vector3f v) {
		this.setX(v.getX() * this.getX());
		this.setY(v.getY() * this.getY());
		this.setZ(v.getZ() * this.getZ());
		return this;
	}
	
	/**
	 * v1 点乘 v2
	 * @param v1
	 * @param v2
	 * @return 新向量对象
	 */
	public final Vector3f multiByPointForNewInstance(Vector3f v1, Vector3f v2) {
		return v1.multiByPointForNewInstance(v2);
	}
	
	/**
	 *当前对象点乘 v 不改变当前对象值
	 * @param v
	 * @return 新的向量对象
	 */
	public final Vector3f multiByPointForNewInstance(Vector3f v) {
		Vector3f t_temp = new Vector3f();
		t_temp.setX(v.getX() * this.getX());
		t_temp.setY(v.getY() * this.getY());
		t_temp.setZ(v.getZ() * this.getZ());
		return t_temp;
	}
	
	/**
	 * 当前对象叉乘v
	 * @param v
	 * @return this
	 */
	public final Vector3f multiByFork(Vector3f v) {
		this.setX(this.getY() * v.getZ() - v.getY() * this.getZ());
		this.setY(this.getZ() * v.getX() - v.getZ() * this.getX());
		this.setZ(this.getX() * v.getY() - v.getX() * this.getY());
		return this;
	}
	
	/**
	 * v1叉乘v2赋值给当前对象
	 * @param v1
	 * @param v2
	 * @return this
	 */
	public final Vector3f multiByFork(Vector3f v1, Vector3f v2) {
		this.setX(v1.getY() * v2.getZ() - v2.getY() * v1.getZ());
		this.setY(v1.getZ() * v2.getX() - v2.getZ() * v1.getX());
		this.setZ(v1.getX() * v2.getY() - v2.getX() * v1.getY());
		return this;
	}
	
	/**
	 * 当前对象叉乘v 不改变当前向量值
	 * @param v
	 * @return 新的向量对象
	 */
	public final Vector3f multiByForkForNewInstance(Vector3f v) {
		Vector3f v_temp = new Vector3f();
		v_temp.setX(this.getY() * v.getZ() - v.getY() * this.getZ());
		v_temp.setY(this.getZ() * v.getX() - v.getZ() * this.getX());
		v_temp.setZ(this.getX() * v.getY() - v.getX() * this.getY());
		return v_temp;
	}
	
	/**
	 * v1叉乘v2
	 * @param v1
	 * @param v2
	 * @return 新的向量对象
	 */
	public final Vector3f multiByForkForNewInstance(Vector3f v1, Vector3f v2) {
		return v1.multiByForkForNewInstance(v2);
	}
	
	/**
	 * 与另一向量相加，并赋值给本身
	 * @param v
	 * @return this
	 */
	public final Vector3f sum(Vector3f v) {
		this.setX(this.getX() + v.getX());
		this.setY(this.getY() + v.getY());
		this.setZ(this.getZ() + v.getZ());
		return this;
	}
	
	/**
	 * 设置位置
	 * @param v
	 */
	public final void setPosition(Vector3f v) {
		this.setPosition(v.getX(), v.getY(), v.getZ());
	}
	
	/**
	 * 设置位置
	 * @param x
	 * @param y
	 * @param z
	 */
	public final void setPosition(float x, float y, float z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}
	
	public final float[] getVector3fArray() {
		return this.coord;
	}
	java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.0000"); 
	//df.format(你要格式化的数字);
	public final float getX() {
		return Float.valueOf(df.format(this.coord[0])) ;
	}
	
	public final float getY() {
		return Float.valueOf(df.format(this.coord[1])) ;
	}
	
	public final float getZ() {
		return Float.valueOf(df.format(this.coord[2])) ;
	}
	
	public final void setX(float x) {
		this.coord[0] = x;
	}
	
	public final void setY(float y) {
		this.coord[1] = y;
	}
	
	public final void setZ(float z) {
		this.coord[2] = z;
	}
	
}
