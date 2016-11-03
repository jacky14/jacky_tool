package dev.mouse.lib;


/**
 * 4 × 4矩阵运算
 * @author DEVILIVED
 *
 */
public class Matrix4f {
	
	private float[][] matrix = new float[4][4];	//4*4矩阵
	
	/**
	 * 获得矩阵数组
	 * @return
	 */
	public final float[][] getMatrixArray() {
		return this.matrix;
	}
	
	/**
	 * 重置矩阵
	 */
	public final void loadIdentity() {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(i == j)
					this.matrix[i][j] = 1.0f;
				else
					this.matrix[i][j] = 0.0f;
			}
		}
	}
	
	/**
	 * 设置位置
	 * @param v
	 */
	public final void setTranslation(Vector3f v) {
		this.setTranslation(v.getX(), v.getY(), v.getZ());
	}
	
	public final void setTranslation(float[] v) {
		this.setTranslation(v[0], v[1], v[2]);
	}
	
	public final void setTranslation(float x, float y, float z) {
		this.matrix[0][3] = x;
		this.matrix[1][3] = y;
		this.matrix[2][3] = z;
	}
	
	/**
	 * 旋转
	 * @param angles
	 */
	public final void setRotationRadians(float[] angles) {
		this.setRotationRadians(angles[0], angles[1], angles[2]);
	}
	
	/**
	 * 旋转
	 * @param angles
	 */
	public final void setRotationRadians(final Vector3f angles) {
		this.setRotationRadians(angles.getX(), angles.getY(), angles.getZ());
	}
	
	/**
	 * 设置旋转   由欧拉角转换到旋转矩阵
	 */
	public final void setRotationRadians(float x, float y, float z) {
		final double cr = Math.cos(x);
        final double sr = Math.sin(x);
        final double cp = Math.cos(y);
        final double sp = Math.sin(y);
        final double cy = Math.cos(z);
        final double sy = Math.sin(z);
        final double srsp = sr * sp;
        final double crsp = cr * sp;

        this.matrix[0][0] = (float) (cp * cy);
        this.matrix[1][0] = (float) (cp * sy);
        this.matrix[2][0] = (float) (-sp);

        this.matrix[0][1] = (float) (srsp * cy - cr * sy);
        this.matrix[1][1] = (float) (srsp * sy + cr * cy);
        this.matrix[2][1] = (float) (sr * cp);

        this.matrix[0][2] = (float) (crsp * cy + sr * sy);
        this.matrix[1][2] = (float) (crsp * sy - sr * cy);
        this.matrix[2][2] = (float) (cr * cp);
	}
	
	/**
	 * 当前矩阵与m相乘 并赋值给当前对象
	 * @param m
	 * @return this
	 */
	public final Matrix4f mul(Matrix4f m) {
		this.matrix = this.mulForNewInstance(m).matrix;
		return this;
    }
	
	/**
	 * 获得m1矩阵 与 m2矩阵 相乘后的矩阵 并赋值给当前矩阵对象
	 * @param m1
	 * @param m2
	 * @return this
	 */
	public final Matrix4f mul(Matrix4f m1, Matrix4f m2) {
		this.matrix = m1.mulForNewInstance(m2).matrix;
		return this;
    }
	
	
	
	/**
	 * m1与m2相乘 并返回新的矩阵对象
	 * @param m1
	 * @param m2
	 * @return 新的矩阵对象
	 */
	public final Matrix4f mulForNewInstance(Matrix4f m1, Matrix4f m2) {
		return m1.mulForNewInstance(m2);
    }
	
	/**
	 * 当前矩阵与m相乘 并返回新的矩阵对象
	 * @param m
	 * @return 新的矩阵对象
	 */
	public final Matrix4f mulForNewInstance(Matrix4f m) {
		Matrix4f matrix4f = new Matrix4f();
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				for(int k=0; k<4; k++) {
					matrix4f.matrix[i][j] += this.matrix[i][k] * m.matrix[k][j]; 
				}
			}
		}
		return matrix4f;
    }
	
	/**
	 * 设置
	 * @param m
	 */
	public final void set(Matrix4f m) {
        for(int i=0; i<4; i++) {
        	for(int j=0; j<4; j++) {
        		this.matrix[i][j] = m.matrix[i][j];
        	}
        }
    }
	
	
	public final void set(float[] v) {
		this.set(v[0], v[1], v[2], v[3]);
	}
	
	public final void set(Vector4f v) {
		this.set(v.getX(), v.getY(), v.getZ(), v.getW());
	}
	
	/**
	 * 照抄的  由四元数转换到旋转矩阵
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public final void set(float x, float y, float z, float w) {
        this.matrix[0][0] = (1.0f - 2.0f * y * y - 2.0f * z * z);
        this.matrix[1][0] = (2.0f * (x * y + w * z));
        this.matrix[2][0] = (2.0f * (x * z - w * y));

        this.matrix[0][1] = (2.0f * (x * y - w * z));
        this.matrix[1][1] = (1.0f - 2.0f * x * x - 2.0f * z * z);
        this.matrix[2][1] = (2.0f * (y * z + w * x));

        this.matrix[0][2] = (2.0f * (x * z + w * y));
        this.matrix[1][2] = (2.0f * (y * z - w * x));
        this.matrix[2][2] = (1.0f - 2.0f * x * x - 2.0f * y * y);

        this.matrix[0][3] = (float) 0.0;
        this.matrix[1][3] = (float) 0.0;
        this.matrix[2][3] = (float) 0.0;

        this.matrix[3][0] = (float) 0.0;
        this.matrix[3][1] = (float) 0.0;
        this.matrix[3][2] = (float) 0.0;
        this.matrix[3][3] = (float) 1.0;
    }
	
	/**
	 * 复制本身创建一个新的矩阵
	 * @return
	 */
	public Matrix4f copy(){
		Matrix4f m=new Matrix4f();
		for(int i=0; i<4; i++) {
	        	for(int j=0; j<4; j++) {
	        		m.matrix[i][j]= this.matrix[i][j];
	        	}
	    }
		return m;
	}
	
	/**
	 * 照抄的 顶点的位移、旋转   进行旋转变化时应先进行反向移动矩阵中的位移信息，经过旋转操作后再加上位移信息
	 * 这里的v旋转后并没有加上位移信息，（我也疑惑中？？？？？？？？？？？？？）
	 * 
	 * 该矩阵为将顶点变换到骨骼本地坐标中去，这里是乘以绝对矩阵的逆矩阵
	 * @param point
	 * @return
	 */
	public final Vector3f invTransformAndRotate(Vector3f point) {
        Vector3f v = new Vector3f();
        //位移
        float x = point.getX() - this.matrix[0][3];
        float y = point.getY() - this.matrix[1][3];
        float z = point.getZ() - this.matrix[2][3];
        //旋转
        v.setX(this.matrix[0][0] * x + this.matrix[1][0] * y + this.matrix[2][0] * z);
        v.setY(this.matrix[0][1] * x + this.matrix[1][1] * y + this.matrix[2][1] * z);
        v.setZ(this.matrix[0][2] * x + this.matrix[1][2] * y + this.matrix[2][2] * z);
        return v;
    }
	
	
	/**
	 * 2014/10/07 性能优化：在顶点变换到屏幕坐标时，计算一个最终的矩阵
	 */
	public final float[] getBZ(){
		float[] f=new float[16];
		int index=0;
		
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				f[index++]=matrix[j][i];
			}
		}
		return f;
	}
	
	/**
	 * 照抄的    向量与矩阵乘法
	 * @param point
	 * @return
	 */
	public final Vector3f transform(Vector3f point) {
		Vector3f v = new Vector3f();
        v.setX(
        		this.matrix[0][0] * point.getX() 
        		+ this.matrix[0][1] * point.getY() 
        		+ this.matrix[0][2] * point.getZ() 
        		+ this.matrix[0][3]);
        v.setY(
        		this.matrix[1][0] * point.getX() 
        		+ this.matrix[1][1] * point.getY() 
        		+ this.matrix[1][2] * point.getZ() 
        		+ this.matrix[1][3]);
        v.setZ(
        		this.matrix[2][0] * point.getX() 
        		+ this.matrix[2][1] * point.getY() 
        		+ this.matrix[2][2] * point.getZ() 
        		+ this.matrix[2][3]);
        return v;
    }
}
