//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jacky.engine.math.meteor;


import java.io.Serializable;

public class SimpleVector implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final SimpleVector ORIGIN = new SimpleVector(0.0F, 0.0F, 0.0F);
    public float x;
    public float y;
    public float z;
    private static final SimpleVector DOWN = new SimpleVector(0.0F, 1.0F, 0.0F);

    public SimpleVector(float var1, float var2, float var3) {
        this.x = var1;
        this.y = var2;
        this.z = var3;
    }

    public SimpleVector(double var1, double var3, double var5) {
        this.x = (float)var1;
        this.y = (float)var3;
        this.z = (float)var5;
    }

    public SimpleVector(SimpleVector var1) {
        this.x = var1.x;
        this.y = var1.y;
        this.z = var1.z;
    }

    public SimpleVector() {
        this.x = 0.0F;
        this.y = 0.0F;
        this.z = 0.0F;
    }

    public SimpleVector(float[] var1) {
        if(var1.length == 3) {
            this.x = var1[0];
            this.y = var1[1];
            this.z = var1[2];
        } else {
            this.x = 0.0F;
            this.y = 0.0F;
            this.z = 0.0F;
            //Logger.log("Source-array needs to have a length of 3", 0);
        }

    }

    public static SimpleVector create() {
        return new SimpleVector();
    }

    public static SimpleVector create(float var0, float var1, float var2) {
        return new SimpleVector(var0, var1, var2);
    }

    public static SimpleVector create(SimpleVector var0) {
        return new SimpleVector(var0);
    }

    public void set(float var1, float var2, float var3) {
        this.x = var1;
        this.y = var2;
        this.z = var3;
    }

    public void set(SimpleVector var1) {
        this.x = var1.x;
        this.y = var1.y;
        this.z = var1.z;
    }

    public float[] toArray() {
        return new float[]{this.x, this.y, this.z};
    }

    public String toString() {
        return "(" + this.x + "," + this.y + "," + this.z + ")";
    }

    public boolean equals(Object var1) {
        if(!(var1 instanceof SimpleVector)) {
            return false;
        } else {
            SimpleVector var2 = (SimpleVector)var1;
            return var2.x == this.x && var2.y == this.y && var2.z == this.z;
        }
    }

    public int hashCode() {
        return (int)(this.x * 100.0F + this.y * 10.0F + this.z);
    }

    public SimpleVector rotate(SimpleVector var1) {
        float var2 = this.x;
        float var3 = this.y;
        float var4 = this.z;
        float var5 = (float)Math.sin((double)var1.x);
        float var6 = (float)Math.cos((double)var1.x);
        float var7 = (float)Math.sin((double)var1.y);
        float var8 = (float)Math.cos((double)var1.y);
        float var9 = (float)Math.sin((double)var1.z);
        float var10 = (float)Math.cos((double)var1.z);
        float var11 = var2 * var10 - var3 * var9;
        float var12 = var2 * var9 + var3 * var10;
        var2 = var11;
        var3 = var12;
        var11 = var11 * var8 - var4 * var7;
        float var13 = var2 * var7 + var4 * var8;
        var12 = var12 * var6 - var13 * var5;
        var13 = var3 * var5 + var13 * var6;
        return new SimpleVector(var11, var12, var13);
    }

    public void rotateX(float var1) {
        float var2 = this.y;
        float var3 = this.z;
        float var4 = (float)Math.sin((double)var1);
        float var5 = (float)Math.cos((double)var1);
        this.y = var2 * var5 - var3 * var4;
        this.z = var2 * var4 + var3 * var5;
    }

    public void rotateY(float var1) {
        float var2 = this.x;
        float var3 = this.z;
        float var4 = (float)Math.sin((double)var1);
        float var5 = (float)Math.cos((double)var1);
        this.x = var2 * var5 - var3 * var4;
        this.z = var2 * var4 + var3 * var5;
    }

    public void rotateZ(float var1) {
        float var2 = this.y;
        float var3 = this.x;
        float var4 = (float)Math.sin((double)var1);
        float var5 = (float)Math.cos((double)var1);
        this.x = var3 * var5 - var2 * var4;
        this.y = var3 * var4 + var2 * var5;
    }

    public SimpleVector normalize() {
        double var1 = (double)this.x;
        double var3 = (double)this.y;
        double var5 = (double)this.z;
        double var7 = Math.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
        return var7 != 0.0D?new SimpleVector((float)(var1 / var7), (float)(var3 / var7), (float)(var5 / var7)):new SimpleVector(0.0F, 0.0F, 0.0F);
    }

    public SimpleVector normalize(SimpleVector var1) {
        if(var1 == null) {
            var1 = create();
        }

        float var2 = (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
        if(var2 != 0.0F) {
            float var3 = 1.0F / var2;
            var1.set(this.x * var3, this.y * var3, this.z * var3);
        } else {
            var1.set(0.0F, 0.0F, 0.0F);
        }

        return var1;
    }

    public void createNormal(SimpleVector var1, SimpleVector var2, SimpleVector var3) {
        float var4 = var2.x - var1.x;
        float var5 = var2.y - var1.y;
        float var6 = var2.z - var1.z;
        float var7 = var3.x - var1.x;
        float var8 = var3.y - var1.y;
        float var9 = var3.z - var1.z;
        this.x = var5 * var9 - var6 * var8;
        this.y = var6 * var7 - var4 * var9;
        this.z = var4 * var8 - var5 * var7;
        double var10 = Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
        if(var10 != 0.0D) {
            this.x = (float)((double)this.x / var10);
            this.y = (float)((double)this.y / var10);
            this.z = (float)((double)this.z / var10);
        }

    }

    public float length() {
        return (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
    }

    public float distance(SimpleVector var1) {
        float var2 = -this.x + var1.x;
        float var3 = -this.y + var1.y;
        float var4 = -this.z + var1.z;
        return (float)Math.sqrt((double)(var2 * var2 + var3 * var3 + var4 * var4));
    }

    public SimpleVector calcCross(SimpleVector var1) {
        double var2 = (double)this.x;
        double var4 = (double)this.y;
        double var6 = (double)this.z;
        double var8 = (double)var1.x;
        double var10 = (double)var1.y;
        double var12 = (double)var1.z;
        double var14 = var4 * var12 - var6 * var10;
        double var16 = var6 * var8 - var2 * var12;
        double var18 = var2 * var10 - var4 * var8;
        return new SimpleVector((float)var14, (float)var16, (float)var18);
    }

    public float calcDot(SimpleVector var1) {
        return this.x * var1.x + this.y * var1.y + this.z * var1.z;
    }

    public SimpleVector calcSub(SimpleVector var1) {
        return new SimpleVector(this.x - var1.x, this.y - var1.y, this.z - var1.z);
    }

    public SimpleVector calcAdd(SimpleVector var1) {
        return new SimpleVector(this.x + var1.x, this.y + var1.y, this.z + var1.z);
    }

    public float calcAngleFast(SimpleVector var1) {
        return this.acosFast(this._calcAngle(var1));
    }

    public float calcAngle(SimpleVector var1) {
        return (float)Math.acos((double)this._calcAngle(var1));
    }

    private float _calcAngle(SimpleVector var1) {
        float var2 = this.x * var1.x + this.y * var1.y + this.z * var1.z;
        float var3 = (float)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
        float var4 = (float)Math.sqrt((double)(var1.x * var1.x + var1.y * var1.y + var1.z * var1.z));
        var2 /= var3 * var4;
        if(var2 < -1.0F) {
            var2 = -1.0F;
        }

        if(var2 > 1.0F) {
            var2 = 1.0F;
        }

        return var2;
    }

    private float acosFast(float var1) {
        return (-0.6981317F * var1 * var1 - 0.87266463F) * var1 + 1.5707964F;
    }

    public void scalarMul(float var1) {
        this.x *= var1;
        this.y *= var1;
        this.z *= var1;
    }

    public void matMul(Matrix var1) {
        float[][] var2 = var1.mat;
        float var3 = this.x * var2[0][0] + this.y * var2[1][0] + this.z * var2[2][0] + var2[3][0];
        float var4 = this.x * var2[0][1] + this.y * var2[1][1] + this.z * var2[2][1] + var2[3][1];
        float var5 = this.x * var2[0][2] + this.y * var2[1][2] + this.z * var2[2][2] + var2[3][2];
        this.x = var3;
        this.y = var4;
        this.z = var5;
    }


    public void rotate(Matrix var1) {
        float[][] var2 = var1.mat;
        float var3 = this.x * var2[0][0] + this.y * var2[1][0] + this.z * var2[2][0];
        float var4 = this.x * var2[0][1] + this.y * var2[1][1] + this.z * var2[2][1];
        float var5 = this.x * var2[0][2] + this.y * var2[1][2] + this.z * var2[2][2];
        this.x = var3;
        this.y = var4;
        this.z = var5;
    }

    public void rotateAxis(SimpleVector var1, float var2) {
        Matrix var3 = new Matrix();
        var3.rotateAxis(var1, var2);
        this.matMul(var3);
    }

    public SimpleVector reflect(SimpleVector var1) {
        SimpleVector var2 = this.calcCross(var1);
        var2 = var1.calcCross(var2);
        var2.scalarMul(2.0F);
        var2 = var2.calcSub(this);
        return var2;
    }

    public void add(SimpleVector var1) {
        this.x += var1.x;
        this.y += var1.y;
        this.z += var1.z;
    }

    public void sub(SimpleVector var1) {
        this.x -= var1.x;
        this.y -= var1.y;
        this.z -= var1.z;
    }

    public void makeEqualLength(SimpleVector var1) {
        float var2 = var1.length();
        float var3 = this.length();
        if(var3 > var2) {
            SimpleVector var4 = this.normalize();
            var4.scalarMul(var2);
            this.x = var4.x;
            this.y = var4.y;
            this.z = var4.z;
        }

    }

    public Matrix getRotationMatrix() {
        return this.getRotationMatrix(new Matrix(), DOWN);
    }

    public Matrix getRotationMatrix(SimpleVector var1) {
        return this.getRotationMatrix(new Matrix(), var1);
    }

    public Matrix getRotationMatrix(Matrix var1) {
        return this.getRotationMatrix(var1, DOWN);
    }

    public Matrix getRotationMatrix(Matrix var1, SimpleVector var2) {
        double var3 = (double)this.x;
        double var5 = (double)this.y;
        double var7 = (double)this.z;
        if(var3 == 0.0D && var7 == 0.0D) {
            var3 += 1.0E-128D;
        }

        double var11 = Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
        if(var11 != 0.0D) {
            var3 /= var11;
            var5 /= var11;
            var7 /= var11;
        }

        float[][] var13 = var1.mat;
        var13[1][0] = 0.0F;
        var13[1][1] = 1.0F;
        var13[1][2] = 0.0F;
        var13[2][0] = (float)var3;
        var13[2][1] = (float)var5;
        var13[2][2] = (float)var7;
        double var14 = (double)var2.x;
        double var16 = (double)var2.y;
        double var18 = (double)var2.z;
        double var26 = var16 * var7 - var18 * var5;
        double var28 = var18 * var3 - var14 * var7;
        double var30 = var14 * var5 - var16 * var3;
        double var32 = 0.0D;
        double var34 = 0.0D;
        double var36 = 0.0D;
        var11 = Math.sqrt(var26 * var26 + var28 * var28 + var30 * var30);
        if(var11 != 0.0D) {
            var26 /= var11;
            var28 /= var11;
            var30 /= var11;
        }

        var32 = var5 * var30 - var7 * var28;
        var34 = var7 * var26 - var3 * var30;
        var36 = var3 * var28 - var5 * var26;
        var11 = Math.sqrt(var32 * var32 + var34 * var34 + var36 * var36);
        if(var11 != 0.0D) {
            var32 /= var11;
            var34 /= var11;
            var36 /= var11;
        }

        var13[0][0] = (float)var26;
        var13[0][1] = (float)var28;
        var13[0][2] = (float)var30;
        var13[1][0] = (float)var32;
        var13[1][1] = (float)var34;
        var13[1][2] = (float)var36;
        var1.orthonormalizeDouble();
        return var1;
    }
}
