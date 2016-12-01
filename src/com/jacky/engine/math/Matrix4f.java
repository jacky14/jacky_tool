package com.jacky.engine.math;

/**
 * row matrix class
 * @author huangbc
 * @date 	2015年2月10日 下午2:44:59
 */
public class Matrix4f {

	/**
	 *  0  1  2  3
	 *  4  5  6  7
	 *  8  9 10 11
	 * 12 13 14 15
	 */
	public float[] matrix;
	
	public Matrix4f() {
		init();
	}
	public void init(){
		matrix=new float[16];
		matrix[ 0]=1;
		matrix[ 5]=1;
		matrix[10]=1;
		matrix[15]=1;
	}
	public void reinit(){


		for(int i=0;i<matrix.length;i++){
			if(i==0){
				matrix[i] = 1;
			}else if(i==5){
				matrix[i] = 1;
			}else if(i==10){
				matrix[i] = 1;
			}else if(i==15){
				matrix[i] = 1;
			}else{
				matrix[i] = 0;
			}
		}

	}

	/**
	 * move around axis(x,y,z) (if this matrix covers rotate infomation,that the axis be rotate )
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translate(float x,float y,float z){
		for (int i=0 ; i<4 ; i++) {
           matrix[12 + i] += matrix[i] * x + matrix[4 + i] * y + matrix[8 + i] * z;
        }
	}
	/**
	 * Scales matrix m in place by sx, sy, and sz.
	 *
	 * @param x scale factor x
	 * @param y scale factor y
	 * @param z scale factor z
	 */
	public  void scaleM(float x, float y, float z) {
		for (int i=0 ; i<4 ; i++) {
			int mi = i;
			matrix[     mi] *= x;
			matrix[ 4 + mi] *= y;
			matrix[ 8 + mi] *= z;
		}
	}
	/**
	 * rotates matrix by angle a around the axis(x ,y ,z)
	 * @param a
	 * @param x
	 * @param y
	 * @param z
	 */
	public void rotate(float a, float x, float y, float z) {
		 	a *= (float) (Math.PI / 180.0f);
	        float s = (float) Math.sin(a);
	        float c = (float) Math.cos(a);
	        if (1.0f == x && 0.0f == y && 0.0f == z) {
	            matrix[5] = c;   matrix[10]= c;
	            matrix[6] = s;   matrix[9] = -s;
	            matrix[1] = 0;   matrix[2] = 0;
	            matrix[4] = 0;   matrix[8] = 0;
	            matrix[0] = 1;
	        } else if (0.0f == x && 1.0f == y && 0.0f == z) {
	            matrix[0] = c;   matrix[10]= c;
	            matrix[8] = s;   matrix[2] = -s;
	            matrix[1] = 0;   matrix[4] = 0;
	            matrix[6] = 0;   matrix[9] = 0;
	            matrix[5] = 1;
	        } else if (0.0f == x && 0.0f == y && 1.0f == z) {
	            matrix[0] = c;   matrix[5] = c;
	            matrix[1] = s;   matrix[4] = -s;
	            matrix[2] = 0;   matrix[6] = 0;
	            matrix[8] = 0;   matrix[9] = 0;
	            matrix[10]= 1;
	        } else {
	            float len = length(x, y, z);
	            if (1.0f != len) {
	                float recipLen = 1.0f / len;
	                x *= recipLen;
	                y *= recipLen;
	                z *= recipLen;
	            }
	            float nc = 1.0f - c;
	            float xy = x * y;
	            float yz = y * z;
	            float zx = z * x;
	            float xs = x * s;
	            float ys = y * s;
	            float zs = z * s;
	            matrix[ 0] = x*x*nc +  c;
	            matrix[ 4] =  xy*nc - zs;
	            matrix[ 8] =  zx*nc + ys;
	            matrix[ 1] =  xy*nc + zs;
	            matrix[ 5] = y*y*nc +  c;
	            matrix[ 9] =  yz*nc - xs;
	            matrix[ 2] =  zx*nc - ys;
	            matrix[ 6] =  yz*nc + xs;
	            matrix[10] = z*z*nc +  c;
	        }
	}
	/**
	 * computes the length of a vector
	 * @param x x coordinate of a vector
	 * @param y y coordinate of a vector
	 * @param z z coordinate of a vector
	 * @return the length of vector
	 */
	private float length(float x,float y,float z) {
		 return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 *//*
	private void length_fast(float x,float y,float z) {
		int temp;
		int x1,y1,z1;
		x1=Math.abs(x)*1024;
		y1=Math.abs(y);
		z1=Math.abs(z);
	}*/
	
	/**
	 * 
	 * @param eyeX the camera position
	 * @param eyeY
	 * @param eyeZ
	 * @param centerX look to the position
	 * @param centerY
	 * @param centerZ
	 * @param upX camera upward direction vector
	 * @param upY
	 * @param upZ
	 */
	public void setLookAt(
			float eyeX, float eyeY, float eyeZ,
            float centerX, float centerY, float centerZ, 
            float upX, float upY,float upZ) 
	{
		// See the OpenGL GLUT documentation for gluLookAt for a description
        // of the algorithm. We implement it in a straightforward way:

        float fx = centerX - eyeX;
        float fy = centerY - eyeY;
        float fz = centerZ - eyeZ;

        // Normalize f
        float rlf = 1.0f / length(fx, fy, fz);
        fx *= rlf;
        fy *= rlf;
        fz *= rlf;

        // compute s = f x up (x means "cross product")
        float sx = fy * upZ - fz * upY;
        float sy = fz * upX - fx * upZ;
        float sz = fx * upY - fy * upX;

        // and normalize s
        float rls = 1.0f / length(sx, sy, sz);
        sx *= rls;
        sy *= rls;
        sz *= rls;

        // compute u = s x f
        float ux = sy * fz - sz * fy;
        float uy = sz * fx - sx * fz;
        float uz = sx * fy - sy * fx;

        matrix[0] = sx;
        matrix[1] = ux;
        matrix[2] = -fx;
        matrix[3] = 0.0f;
       
        matrix[4] = sy;
        matrix[5] = uy;
        matrix[6] = -fy;
        matrix[7] = 0.0f;
       
        matrix[8] = sz;
        matrix[9] = uz;
        matrix[10] = -fz;
        matrix[11] = 0.0f;
       
        matrix[12] = 0.0f;
        matrix[13] = 0.0f;
        matrix[14] = 0.0f;
        matrix[15] = 1.0f;

        translate( -eyeX, -eyeY, -eyeZ);
	}
	/**
	 * Computes an orthographic projection matrix.
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param near
	 * @param far
	 */
	public  void orthoM(float left, float right, float bottom, float top,
							  float near, float far) {
		if (left == right) {
			throw new IllegalArgumentException("left == right");
		}
		if (bottom == top) {
			throw new IllegalArgumentException("bottom == top");
		}
		if (near == far) {
			throw new IllegalArgumentException("near == far");
		}

		final float r_width  = 1.0f / (right - left);
		final float r_height = 1.0f / (top - bottom);
		final float r_depth  = 1.0f / (far - near);
		final float x =  2.0f * (r_width);
		final float y =  2.0f * (r_height);
		final float z = -2.0f * (r_depth);
		final float tx = -(right + left) * r_width;
		final float ty = -(top + bottom) * r_height;
		final float tz = -(far + near) * r_depth;
		matrix[ 0] = x;
		matrix[ 5] = y;
		matrix[10] = z;
		matrix[12] = tx;
		matrix[13] = ty;
		matrix[14] = tz;
		matrix[15] = 1.0f;
		matrix[ 1] = 0.0f;
		matrix[ 2] = 0.0f;
		matrix[ 3] = 0.0f;
		matrix[ 4] = 0.0f;
		matrix[ 6] = 0.0f;
		matrix[ 7] = 0.0f;
		matrix[ 8] = 0.0f;
		matrix[ 9] = 0.0f;
		matrix[ 11] = 0.0f;
	}
	/**
	 * 
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param near
	 * @param far
	 */
	public void frustum(
			float left, float right, 
			float bottom, float top, 
			float near, float far) 
	{
		
		if (left == right) {
            throw new IllegalArgumentException("left == right");
        }
        if (top == bottom) {
            throw new IllegalArgumentException("top == bottom");
        }
        if (near == far) {
            throw new IllegalArgumentException("near == far");
        }
        if (near <= 0.0f) {
            throw new IllegalArgumentException("near <= 0.0f");
        }
        if (far <= 0.0f) {
            throw new IllegalArgumentException("far <= 0.0f");
        }
        final float r_width  = 1.0f / (right - left);
        final float r_height = 1.0f / (top - bottom);
        final float r_depth  = 1.0f / (near - far);
        final float x = 2.0f * (near * r_width);
        final float y = 2.0f * (near * r_height);
        final float A = 2.0f * ((right + left) * r_width);
        final float B = (top + bottom) * r_height;
        final float C = (far + near) * r_depth;
        final float D = 2.0f * (far * near * r_depth);
    	matrix[ 0] = x;
        matrix[ 5] = y;
        matrix[ 8] = A;
        matrix[ 9] = B;
        matrix[10] = C;
        matrix[14] = D;
        matrix[11] = -1.0f;
        matrix[ 1] = 0.0f;
        matrix[ 2] = 0.0f;
        matrix[ 3] = 0.0f;
        matrix[ 4] = 0.0f;
        matrix[ 6] = 0.0f;
        matrix[ 7] = 0.0f;
        matrix[12] = 0.0f;
        matrix[13] = 0.0f;
        matrix[15] = 0.0f;
	}

	float[] r=new float[16];
	/**
	 * matrix multiply( row matrix: this.matrix * m.matrix   "*" means is (matrix multiply) )
	 * @param m is right matrix
	 */
	public void multiplyMM(Matrix4f m) {
		for (int i=0 ; i<4 ; i++) {
             float rhs_i0 = this.matrix[ 4*i ];
             float ri0 = m.matrix[ 0 ] * rhs_i0;
             float ri1 = m.matrix[ 1 ] * rhs_i0;
             float ri2 = m.matrix[ 2 ] * rhs_i0;
             float ri3 = m.matrix[ 3 ] * rhs_i0;
             
             int _i=4*i;
             for (int j=1 ; j<4 ; j++) {
            	int _j=4*j;
                float rhs_ij = this.matrix[ _i+j ];
                ri0 += m.matrix[ _j   ] * rhs_ij;
                ri1 += m.matrix[ _j+1 ] * rhs_ij;
                ri2 += m.matrix[ _j+2 ] * rhs_ij;
                ri3 += m.matrix[ _j+3 ] * rhs_ij;
             }
             r[ _i   ] = ri0;
             r[ _i+1 ] = ri1;
             r[ _i+2 ] = ri2;
             r[ _i+3 ] = ri3;
        }
		this.matrix=r;
	}
	public static Matrix4f clone = new Matrix4f();
	public Matrix4f clone() {
		System.arraycopy(this.matrix, 0, clone.matrix, 0, 16);
		return clone;
	}
	/**
	 * martix multiply vector
	 * @param v4f
	 */
	public void multiplyMV_w(Vector4f v4f){
		float [] xyz=mmv(v4f);
		
		float reciprocal_w=1/(matrix[3] * v4f.x + matrix[7] * v4f.y + matrix[11] * v4f.z + matrix[15]);
		
		v4f.changed_x=xyz[0]*reciprocal_w;
		v4f.changed_y=xyz[1]*reciprocal_w;
		v4f.changed_z=xyz[2]*reciprocal_w;
		
	}
	float []xyz = new float[3];
	public void multiplyMV(Vector4f v4f){
		mmv(v4f);
		v4f.changed_x=xyz[0];
		v4f.changed_y=xyz[1];
		v4f.changed_z=xyz[2];
	}
	private final float[] mmv(Vector4f v4f) {
		xyz[0] = matrix[0] * v4f.x + matrix[4] * v4f.y + matrix[ 8] * v4f.z + matrix[12] ;
		xyz[1] = matrix[1] * v4f.x + matrix[5] * v4f.y + matrix[ 9] * v4f.z + matrix[13] ;
		xyz[2] = matrix[2] * v4f.x + matrix[6] * v4f.y + matrix[10] * v4f.z + matrix[14] ;
		return xyz;
	}
	
}
