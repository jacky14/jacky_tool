package com.jacky.engine.math;

/**
 * vector class four float 
 * @author huangbc
 * @date 	2015年2月10日 下午2:40:38
 */
public class Vector4f {

	public float x;
	public float y;
	public float z;
	
	public float changed_x;
	public float changed_y;
	public float changed_z;
	
	public int pixel_x;
	public int pixel_y;
	
	
	/**
	 * this vertex is on back_face 
	 */
	public boolean back_vertexofface=true;
	
	public Vector4f() {
		
	}

	public Vector4f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "Vector4f [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	
	public Vector4f subtract(Vector4f vec){
		Vector4f v4f = new Vector4f();
		v4f.changed_x = this.changed_x - vec.changed_x ;
		v4f.changed_y = this.changed_y - vec.changed_y ;
		v4f.changed_z = this.changed_z - vec.changed_z ;
		return v4f;
	}
	public float dot(Vector4f vec){
		return this.changed_x * vec.changed_x + this.changed_y * vec.changed_y + this.changed_z * vec.changed_z ;
	}
	public Vector4f cross(Vector4f vec){
		Vector4f v4f = new Vector4f();
		v4f.changed_x = this.changed_y * vec.changed_z - this.changed_z * vec.changed_y ;
		v4f.changed_y = this.changed_z * vec.changed_x - this.changed_x * vec.changed_z ;
		v4f.changed_z = this.changed_x * vec.changed_y - this.changed_y * vec.changed_x ;
		return v4f;
	}
	

	
	
}
