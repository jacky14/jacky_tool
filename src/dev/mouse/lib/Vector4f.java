package dev.mouse.lib;


/**
 * 齐次坐标
 * @author DEVILIVED
 *
 */
public class Vector4f {

	private float[] coording = new float[4];
	
	/**
	 * 设置
	 * @param v 3f坐标
	 */
	public final void set(Vector3f v) {
		this.set(v.getVector3fArray());
	}
	
	/**
	 * 设置
	 * @param coording3f
	 */
	public final void set(float[] coording3f) {
		this.set(coording3f[0], coording3f[1], coording3f[2]);
	}
	
	/**
	 * 算法照抄别人的，具体不知道为何如此计算
	 * 设置
	 * @param coording3f 3f坐标数组
	 */
	public final void set(float x, float y, float z) {
		float angle = 0.0f;
        float sr, sp, sy, cr, cp, cy;
        //rescale the inputs to 1/2 angle
        angle = z * 0.5f;
        sy = (float) Math.sin(angle);
        cy = (float) Math.cos(angle);
        angle = y * 0.5f;
        sp = (float) Math.sin(angle);
        cp = (float) Math.cos(angle);
        angle = x * 0.5f;
        sr = (float) Math.sin(angle);
        cr = (float) Math.cos(angle);

        this.setX(sr * cp * cy - cr * sp * sy); // X
        this.setY(cr * sp * cy + sr * cp * sy); // Y
        this.setZ(cr * cp * sy - sr * sp * cy); // Z
        this.setW(cr * cp * cy + sr * sp * sy); // W
	}
	
	/**
	 * 照抄别人算法
	 * @param q1
	 * @param q2
	 * @param alpha
	 */
	public final void interpolate(Vector4f v1, Vector4f v2, float alpha) {
        // From "Advanced Animation and Rendering Techniques"
        // by Watt and Watt pg. 364, function as implemented appeared to be
        // incorrect.  Fails to choose the same quaternion for the double
        // covering. Resulting in change of direction for rotations.
        // Fixed function to negate the first quaternion in the case that the
        // dot product of q1 and this is negative. Second case was not needed.

        double dot = 0, s1, s2, om, sinom;
        for(int i=0; i<4; i++)
        	dot +=  v2.coording[i] * v1.coording[i];
        Vector4f v0 = null;
        if (dot < 0) {
        	v0 = new Vector4f();
            // negate quaternion
        	for(int i=0; i<4; i++)
        		v0.coording[i] = - v1.coording[i];
            dot = -dot;
        } else
        	v0 = v1;
        if ((1.0 - dot) > 0.000001f) {
            om = Math.acos(dot);
            sinom = Math.sin(om);
            s1 = Math.sin((1.0 - alpha) * om) / sinom;
            s2 = Math.sin(alpha * om) / sinom;
        } else {
            s1 = 1.0 - alpha;
            s2 = alpha;
        }
        this.setW((float) (s1 * v0.getW() + s2 * v2.getW()));
        this.setX((float) (s1 * v0.getX() + s2 * v2.getX()));
        this.setY((float) (s1 * v0.getY() + s2 * v2.getY()));
        this.setZ((float) (s1 * v0.getZ() + s2 * v2.getZ()));
    }
	
	/**
	 * 设置
	 * @param v 4f坐标
	 */
	public final void set(Vector4f v) {
		for(int i=0; i<4; i++)
			this.coording[i] = v.coording[i];
	}
	
	/**
	 * 获得坐标数组
	 * @return
	 */
	public final float[] getVector4fArray() {
		return coording;
	}
	
	public final void setX(float x) {
		this.coording[0] = x;
	}
	
	public final void setY(float y) {
		this.coording[1] = y;
	}
	
	public final void setZ(float z) {
		this.coording[2] = z;
	}
	
	public final void setW(float w) {
		this.coording[3] = w;
	}
	
	public final float getX() {
		return this.coording[0];
	}
	
	public final float getY() {
		return this.coording[1];
	}
	
	public final float getZ() {
		return this.coording[2];
	}
	
	public final float getW() {
		return this.coording[3];
	}
	
}
