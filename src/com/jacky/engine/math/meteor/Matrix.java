//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jacky.engine.math.meteor;


import java.io.Serializable;

public class Matrix implements Serializable {
    private static final long serialVersionUID = 2L;
    private static final float pi = 3.1415927F;
    private static final float mpi = -3.1415927F;
    private static final float pih = 1.5707964F;
    private static final float mpih = -1.5707964F;
    private static final float spi = (float)Math.sin(3.141592653589793D);
    private static final float mspi = (float)Math.sin(-3.141592653589793D);
    private static final float cpi = (float)Math.cos(3.141592653589793D);
    private static final float mcpi = (float)Math.cos(-3.141592653589793D);
    private static final float spih = (float)Math.sin(1.5707963267948966D);
    private static final float mspih = (float)Math.sin(-1.5707963267948966D);
    private static final float cpih = (float)Math.cos(1.5707963267948966D);
    private static final float mcpih = (float)Math.cos(-1.5707963267948966D);
    public float[][] mat = new float[4][4];
    float[] mat0;
    float[] mat1;
    float[] mat2;
    float[] mat3;

    public Matrix() {
        this.mat0 = this.mat[0];
        this.mat1 = this.mat[1];
        this.mat2 = this.mat[2];
        this.mat3 = this.mat[3];
        this.mat0[0] = 1.0F;
        this.mat1[1] = 1.0F;
        this.mat2[2] = 1.0F;
        this.mat3[3] = 1.0F;
    }

    public Matrix(Matrix var1) {
        this.mat0 = this.mat[0];
        this.mat1 = this.mat[1];
        this.mat2 = this.mat[2];
        this.mat3 = this.mat[3];
        this.setTo(var1);
    }

    public final boolean isIdentity() {
        return this.mat[0][0] == 1.0F && this.mat[1][1] == 1.0F && this.mat[2][2] == 1.0F && this.mat[3][3] == 1.0F && this.mat[0][1] == 0.0F && this.mat[0][2] == 0.0F && this.mat[0][3] == 0.0F && this.mat[1][0] == 0.0F && this.mat[1][2] == 0.0F && this.mat[1][3] == 0.0F && this.mat[2][1] == 0.0F && this.mat[2][0] == 0.0F && this.mat[2][3] == 0.0F && this.mat[3][1] == 0.0F && this.mat[3][2] == 0.0F && this.mat[3][0] == 0.0F;
    }

    public final void setIdentity() {
        this.mat[0][0] = 1.0F;
        this.mat[1][1] = 1.0F;
        this.mat[2][2] = 1.0F;
        this.mat[3][3] = 1.0F;
        this.mat[0][1] = 0.0F;
        this.mat[0][2] = 0.0F;
        this.mat[0][3] = 0.0F;
        this.mat[1][0] = 0.0F;
        this.mat[1][2] = 0.0F;
        this.mat[1][3] = 0.0F;
        this.mat[2][1] = 0.0F;
        this.mat[2][0] = 0.0F;
        this.mat[2][3] = 0.0F;
        this.mat[3][1] = 0.0F;
        this.mat[3][2] = 0.0F;
        this.mat[3][0] = 0.0F;
    }

    public final void scalarMul(float var1) {
        this.mat[0][0] *= var1;
        this.mat[0][1] *= var1;
        this.mat[0][2] *= var1;
        this.mat[1][0] *= var1;
        this.mat[1][1] *= var1;
        this.mat[1][2] *= var1;
        this.mat[2][0] *= var1;
        this.mat[2][1] *= var1;
        this.mat[2][2] *= var1;
    }

    public final void matMul(Matrix var1) {
        float var2 = this.mat0[0];
        float var3 = this.mat0[1];
        float var4 = this.mat0[2];
        float var5 = this.mat0[3];
        float var6 = this.mat1[0];
        float var7 = this.mat1[1];
        float var8 = this.mat1[2];
        float var9 = this.mat1[3];
        float var10 = this.mat2[0];
        float var11 = this.mat2[1];
        float var12 = this.mat2[2];
        float var13 = this.mat2[3];
        float var14 = this.mat3[0];
        float var15 = this.mat3[1];
        float var16 = this.mat3[2];
        float var17 = this.mat3[3];
        float var18 = var1.mat0[0];
        float var19 = var1.mat0[1];
        float var20 = var1.mat0[2];
        float var21 = var1.mat0[3];
        float var22 = var1.mat1[0];
        float var23 = var1.mat1[1];
        float var24 = var1.mat1[2];
        float var25 = var1.mat1[3];
        float var26 = var1.mat2[0];
        float var27 = var1.mat2[1];
        float var28 = var1.mat2[2];
        float var29 = var1.mat2[3];
        float var30 = var1.mat3[0];
        float var31 = var1.mat3[1];
        float var32 = var1.mat3[2];
        float var33 = var1.mat3[3];
        this.mat0[0] = var2 * var18 + var3 * var22 + var4 * var26 + var5 * var30;
        this.mat0[1] = var2 * var19 + var3 * var23 + var4 * var27 + var5 * var31;
        this.mat0[2] = var2 * var20 + var3 * var24 + var4 * var28 + var5 * var32;
        this.mat0[3] = var2 * var21 + var3 * var25 + var4 * var29 + var5 * var33;
        this.mat1[0] = var6 * var18 + var7 * var22 + var8 * var26 + var9 * var30;
        this.mat1[1] = var6 * var19 + var7 * var23 + var8 * var27 + var9 * var31;
        this.mat1[2] = var6 * var20 + var7 * var24 + var8 * var28 + var9 * var32;
        this.mat1[3] = var6 * var21 + var7 * var25 + var8 * var29 + var9 * var33;
        this.mat2[0] = var10 * var18 + var11 * var22 + var12 * var26 + var13 * var30;
        this.mat2[1] = var10 * var19 + var11 * var23 + var12 * var27 + var13 * var31;
        this.mat2[2] = var10 * var20 + var11 * var24 + var12 * var28 + var13 * var32;
        this.mat2[3] = var10 * var21 + var11 * var25 + var12 * var29 + var13 * var33;
        this.mat3[0] = var14 * var18 + var15 * var22 + var16 * var26 + var17 * var30;
        this.mat3[1] = var14 * var19 + var15 * var23 + var16 * var27 + var17 * var31;
        this.mat3[2] = var14 * var20 + var15 * var24 + var16 * var28 + var17 * var32;
        this.mat3[3] = var14 * var21 + var15 * var25 + var16 * var29 + var17 * var33;
    }
    public void setF(float[] setm ,int index){
        setm[index]     = mat[0][0];setm[index + 1] = mat[1][0];setm[index + 2]  = mat[2][0];setm[index + 3]  = mat[3][0];
        setm[index + 4] = mat[0][1];setm[index + 5] = mat[1][1];setm[index + 6]  = mat[2][1];setm[index + 7]  = mat[3][1];
        setm[index + 8] = mat[0][2];setm[index + 9] = mat[1][2];setm[index + 10] = mat[2][2];setm[index + 11] = mat[3][2];
    }
 /*   public void matMul(Matrix var1) {
        float[][] var2 = var1.mat;
        float var3 = this.x * var2[0][0] + this.y * var2[1][0] + this.z * var2[2][0] + var2[3][0];
        float var4 = this.x * var2[0][1] + this.y * var2[1][1] + this.z * var2[2][1] + var2[3][1];
        float var5 = this.x * var2[0][2] + this.y * var2[1][2] + this.z * var2[2][2] + var2[3][2];
        this.x = var3;
        this.y = var4;
        this.z = var5;
    }*/
    public final void rotateX(float var1) {
        float var2;
        float var3;
        if(var1 == 3.1415927F) {
            var2 = cpi;
            var3 = spi;
        } else if(var1 == -3.1415927F) {
            var2 = mcpi;
            var3 = mspi;
        } else if(var1 == 1.5707964F) {
            var2 = cpih;
            var3 = spih;
        } else if(var1 == -1.5707964F) {
            var2 = mcpih;
            var3 = mspih;
        } else {
            var2 = (float)Math.cos((double)var1);
            var3 = (float)Math.sin((double)var1);
        }

        float var4 = this.mat0[1];
        float var5 = this.mat0[2];
        float var6 = this.mat1[1];
        float var7 = this.mat1[2];
        float var8 = this.mat2[1];
        float var9 = this.mat2[2];
        float var10 = this.mat3[1];
        float var11 = this.mat3[2];
        this.mat0[1] = var4 * var2 + var5 * var3;
        this.mat0[2] = var4 * -var3 + var5 * var2;
        this.mat1[1] = var6 * var2 + var7 * var3;
        this.mat1[2] = var6 * -var3 + var7 * var2;
        this.mat2[1] = var8 * var2 + var9 * var3;
        this.mat2[2] = var8 * -var3 + var9 * var2;
        this.mat3[1] = var10 * var2 + var11 * var3;
        this.mat3[2] = var10 * -var3 + var11 * var2;
    }

    public final void rotateY(float var1) {
        float var2;
        float var3;
        if(var1 == 3.1415927F) {
            var2 = cpi;
            var3 = spi;
        } else if(var1 == -3.1415927F) {
            var2 = mcpi;
            var3 = mspi;
        } else if(var1 == 1.5707964F) {
            var2 = cpih;
            var3 = spih;
        } else if(var1 == -1.5707964F) {
            var2 = mcpih;
            var3 = mspih;
        } else {
            var2 = (float)Math.cos((double)var1);
            var3 = (float)Math.sin((double)var1);
        }

        float var4 = this.mat0[0];
        float var5 = this.mat0[2];
        float var6 = this.mat1[0];
        float var7 = this.mat1[2];
        float var8 = this.mat2[0];
        float var9 = this.mat2[2];
        float var10 = this.mat3[0];
        float var11 = this.mat3[2];
        this.mat0[0] = var4 * var2 + var5 * var3;
        this.mat0[2] = var4 * -var3 + var5 * var2;
        this.mat1[0] = var6 * var2 + var7 * var3;
        this.mat1[2] = var6 * -var3 + var7 * var2;
        this.mat2[0] = var8 * var2 + var9 * var3;
        this.mat2[2] = var8 * -var3 + var9 * var2;
        this.mat3[0] = var10 * var2 + var11 * var3;
        this.mat3[2] = var10 * -var3 + var11 * var2;
    }

    public final void rotateZ(float var1) {
        float var2;
        float var3;
        if(var1 == 3.1415927F) {
            var2 = cpi;
            var3 = spi;
        } else if(var1 == -3.1415927F) {
            var2 = mcpi;
            var3 = mspi;
        } else if(var1 == 1.5707964F) {
            var2 = cpih;
            var3 = spih;
        } else if(var1 == -1.5707964F) {
            var2 = mcpih;
            var3 = mspih;
        } else {
            var2 = (float)Math.cos((double)var1);
            var3 = (float)Math.sin((double)var1);
        }

        float var4 = this.mat0[0];
        float var5 = this.mat0[1];
        float var6 = this.mat1[0];
        float var7 = this.mat1[1];
        float var8 = this.mat2[0];
        float var9 = this.mat2[1];
        float var10 = this.mat3[0];
        float var11 = this.mat3[1];
        this.mat0[0] = var4 * var2 + var5 * var3;
        this.mat0[1] = var4 * -var3 + var5 * var2;
        this.mat1[0] = var6 * var2 + var7 * var3;
        this.mat1[1] = var6 * -var3 + var7 * var2;
        this.mat2[0] = var8 * var2 + var9 * var3;
        this.mat2[1] = var8 * -var3 + var9 * var2;
        this.mat3[0] = var10 * var2 + var11 * var3;
        this.mat3[1] = var10 * -var3 + var11 * var2;
    }

    public final void rotateAxis(SimpleVector var1, float var2) {
        double var3 = Math.cos((double)var2);
        double var5 = Math.sin((double)var2);
        double var7 = 1.0D - var3;
        var1 = var1.normalize();
        double var9 = (double)var1.x;
        double var11 = (double)var1.y;
        double var13 = (double)var1.z;
        Matrix var15 = new Matrix();
        double var16 = var5 * var11;
        double var18 = var5 * var9;
        double var20 = var5 * var13;
        double var22 = var7 * var9 * var11;
        double var24 = var7 * var9 * var13;
        double var26 = var7 * var11 * var13;
        var15.mat0[0] = (float)(var7 * var9 * var9 + var3);
        var15.mat1[0] = (float)(var22 + var20);
        var15.mat2[0] = (float)(var24 - var16);
        var15.mat0[1] = (float)(var22 - var20);
        var15.mat1[1] = (float)(var7 * var11 * var11 + var3);
        var15.mat2[1] = (float)(var26 + var18);
        var15.mat0[2] = (float)(var24 + var16);
        var15.mat1[2] = (float)(var26 - var18);
        var15.mat2[2] = (float)(var7 * var13 * var13 + var3);
        var15.orthonormalize();
        this.matMul(var15);
    }

    public final void interpolate(Matrix var1, Matrix var2, float var3) {
        if(var3 > 1.0F) {
            var3 = 1.0F;
        } else if(var3 < 0.0F) {
            var3 = 0.0F;
        }

        float var4 = 1.0F - var3;

        for(int var5 = 0; var5 < 4; ++var5) {
            this.mat[var5][0] = var1.mat[var5][0] * var4 + var2.mat[var5][0] * var3;
            this.mat[var5][1] = var1.mat[var5][1] * var4 + var2.mat[var5][1] * var3;
            this.mat[var5][2] = var1.mat[var5][2] * var4 + var2.mat[var5][2] * var3;
            this.mat[var5][3] = var1.mat[var5][3] * var4 + var2.mat[var5][3] * var3;
        }

        this.orthonormalize();
    }

    public final SimpleVector getTranslation() {
        return new SimpleVector(this.mat3[0], this.mat3[1], this.mat3[2]);
    }

    public final SimpleVector getTranslation(SimpleVector var1) {
        var1.set(this.mat3[0], this.mat3[1], this.mat3[2]);
        return var1;
    }

    public final SimpleVector getXAxis() {
        return new SimpleVector(this.mat0[0], this.mat0[1], this.mat0[2]);
    }

    public final SimpleVector getYAxis() {
        return new SimpleVector(this.mat1[0], this.mat1[1], this.mat1[2]);
    }

    public final SimpleVector getZAxis() {
        return new SimpleVector(this.mat2[0], this.mat2[1], this.mat2[2]);
    }

    public final SimpleVector getXAxis(SimpleVector var1) {
        var1.set(this.mat0[0], this.mat0[1], this.mat0[2]);
        return var1;
    }

    public final SimpleVector getYAxis(SimpleVector var1) {
        var1.set(this.mat1[0], this.mat1[1], this.mat1[2]);
        return var1;
    }

    public final SimpleVector getZAxis(SimpleVector var1) {
        var1.set(this.mat2[0], this.mat2[1], this.mat2[2]);
        return var1;
    }

    public final void setOrientation(SimpleVector var1, SimpleVector var2) {
        this.setOrientation(var1, var2, true);
    }

    final void setOrientation(SimpleVector var1, SimpleVector var2, boolean var3) {
        var2 = var2.normalize();
        var1 = var1.normalize();
        SimpleVector var4 = var2.calcCross(var1).normalize();
        if(!var3) {
            this.mat[0][0] = var4.x;
            this.mat[1][0] = var4.y;
            this.mat[2][0] = var4.z;
            this.mat[3][0] = 0.0F;
            this.mat[0][1] = var2.x;
            this.mat[1][1] = var2.y;
            this.mat[2][1] = var2.z;
            this.mat[3][1] = 0.0F;
            this.mat[0][2] = var1.x;
            this.mat[1][2] = var1.y;
            this.mat[2][2] = var1.z;
            this.mat[3][2] = 0.0F;
            this.mat[0][3] = 0.0F;
            this.mat[1][3] = 0.0F;
            this.mat[2][3] = 0.0F;
            this.mat[3][3] = 1.0F;
        } else {
            this.mat[0][0] = var4.x;
            this.mat[0][1] = var4.y;
            this.mat[0][2] = var4.z;
            this.mat[0][3] = 0.0F;
            this.mat[1][0] = var2.x;
            this.mat[1][1] = var2.y;
            this.mat[1][2] = var2.z;
            this.mat[1][3] = 0.0F;
            this.mat[2][0] = var1.x;
            this.mat[2][1] = var1.y;
            this.mat[2][2] = var1.z;
            this.mat[2][3] = 0.0F;
            this.mat[3][0] = 0.0F;
            this.mat[3][1] = 0.0F;
            this.mat[3][2] = 0.0F;
            this.mat[3][3] = 1.0F;
        }

    }

    public final void translate(SimpleVector var1) {
        this.mat3[0] += var1.x;
        this.mat3[1] += var1.y;
        this.mat3[2] += var1.z;
    }

    public final void translate(float var1, float var2, float var3) {
        this.mat3[0] += var1;
        this.mat3[1] += var2;
        this.mat3[2] += var3;
    }

    public final Matrix cloneMatrix() {
        Matrix var1 = new Matrix();
        var1.setTo(this);
        return var1;
    }

    public final Matrix invert() {
        return this.invert(new Matrix());
    }

    public final Matrix invert(Matrix var1) {
        float var2 = this.mat[0][0];
        float var3 = this.mat[0][1];
        float var4 = this.mat[0][2];
        float var5 = this.mat[0][3];
        float var6 = this.mat[1][0];
        float var7 = this.mat[1][1];
        float var8 = this.mat[1][2];
        float var9 = this.mat[1][3];
        float var10 = this.mat[2][0];
        float var11 = this.mat[2][1];
        float var12 = this.mat[2][2];
        float var13 = this.mat[2][3];
        float var14 = this.mat[3][0];
        float var15 = this.mat[3][1];
        float var16 = this.mat[3][2];
        float var17 = this.mat[3][3];
        float var18 = var12 * var17;
        float var19 = var16 * var13;
        float var20 = var8 * var17;
        float var21 = var16 * var9;
        float var22 = var8 * var13;
        float var23 = var12 * var9;
        float var24 = var4 * var17;
        float var25 = var16 * var5;
        float var26 = var4 * var13;
        float var27 = var12 * var5;
        float var28 = var4 * var9;
        float var29 = var8 * var5;
        float var30 = var18 * var7 + var21 * var11 + var22 * var15 - (var19 * var7 + var20 * var11 + var23 * var15);
        float var31 = var19 * var3 + var24 * var11 + var27 * var15 - (var18 * var3 + var25 * var11 + var26 * var15);
        float var32 = var20 * var3 + var25 * var7 + var28 * var15 - (var21 * var3 + var24 * var7 + var29 * var15);
        float var33 = var23 * var3 + var26 * var7 + var29 * var11 - (var22 * var3 + var27 * var7 + var28 * var11);
        float var34 = var19 * var6 + var20 * var10 + var23 * var14 - (var18 * var6 + var21 * var10 + var22 * var14);
        float var35 = var18 * var2 + var25 * var10 + var26 * var14 - (var19 * var2 + var24 * var10 + var27 * var14);
        float var36 = var21 * var2 + var24 * var6 + var29 * var14 - (var20 * var2 + var25 * var6 + var28 * var14);
        float var37 = var22 * var2 + var27 * var6 + var28 * var10 - (var23 * var2 + var26 * var6 + var29 * var10);
        var18 = var10 * var15;
        var19 = var14 * var11;
        var20 = var6 * var15;
        var21 = var14 * var7;
        var22 = var6 * var11;
        var23 = var10 * var7;
        var24 = var2 * var15;
        var25 = var14 * var3;
        var26 = var2 * var11;
        var27 = var10 * var3;
        var28 = var2 * var7;
        var29 = var6 * var3;
        float var38 = var18 * var9 + var21 * var13 + var22 * var17 - (var19 * var9 + var20 * var13 + var23 * var17);
        float var39 = var19 * var5 + var24 * var13 + var27 * var17 - (var18 * var5 + var25 * var13 + var26 * var17);
        float var40 = var20 * var5 + var25 * var9 + var28 * var17 - (var21 * var5 + var24 * var9 + var29 * var17);
        float var41 = var23 * var5 + var26 * var9 + var29 * var13 - (var22 * var5 + var27 * var9 + var28 * var13);
        float var42 = var20 * var12 + var23 * var16 + var19 * var8 - (var22 * var16 + var18 * var8 + var21 * var12);
        float var43 = var26 * var16 + var18 * var4 + var25 * var12 - (var24 * var12 + var27 * var16 + var19 * var4);
        float var44 = var24 * var8 + var29 * var16 + var21 * var4 - (var28 * var16 + var20 * var4 + var25 * var8);
        float var45 = var28 * var12 + var22 * var4 + var27 * var8 - (var26 * var8 + var29 * var12 + var23 * var4);
        float var46 = 1.0F / (var2 * var30 + var6 * var31 + var10 * var32 + var14 * var33);
        var1.mat[0][0] = var30 * var46;
        var1.mat[0][1] = var31 * var46;
        var1.mat[0][2] = var32 * var46;
        var1.mat[0][3] = var33 * var46;
        var1.mat[1][0] = var34 * var46;
        var1.mat[1][1] = var35 * var46;
        var1.mat[1][2] = var36 * var46;
        var1.mat[1][3] = var37 * var46;
        var1.mat[2][0] = var38 * var46;
        var1.mat[2][1] = var39 * var46;
        var1.mat[2][2] = var40 * var46;
        var1.mat[2][3] = var41 * var46;
        var1.mat[3][0] = var42 * var46;
        var1.mat[3][1] = var43 * var46;
        var1.mat[3][2] = var44 * var46;
        var1.mat[3][3] = var45 * var46;
        return var1;
    }

    public final Matrix invert3x3() {
        return this.invert3x3(new Matrix());
    }

    public final Matrix invert3x3(Matrix var1) {
        if(var1 == this) {
            //Logger.log("The matrix to be filled can\'t be the same instance as the matrix to invert!", 0);
        }

        var1.mat[0][1] = this.mat[1][0];
        var1.mat[0][2] = this.mat[2][0];
        var1.mat[1][0] = this.mat[0][1];
        var1.mat[1][2] = this.mat[2][1];
        var1.mat[2][0] = this.mat[0][2];
        var1.mat[2][1] = this.mat[1][2];
        var1.mat[0][0] = this.mat[0][0];
        var1.mat[1][1] = this.mat[1][1];
        var1.mat[2][2] = this.mat[2][2];
        return var1;
    }

    public final Matrix transpose() {
        Matrix var1 = new Matrix();
        var1.mat[0][1] = this.mat[1][0];
        var1.mat[0][2] = this.mat[2][0];
        var1.mat[0][3] = this.mat[3][0];
        var1.mat[1][0] = this.mat[0][1];
        var1.mat[1][2] = this.mat[2][1];
        var1.mat[1][3] = this.mat[3][1];
        var1.mat[2][0] = this.mat[0][2];
        var1.mat[2][1] = this.mat[1][2];
        var1.mat[2][3] = this.mat[3][2];
        var1.mat[3][0] = this.mat[0][3];
        var1.mat[3][1] = this.mat[1][3];
        var1.mat[3][2] = this.mat[2][3];
        var1.mat[0][0] = this.mat[0][0];
        var1.mat[1][1] = this.mat[1][1];
        var1.mat[2][2] = this.mat[2][2];
        var1.mat[3][3] = this.mat[3][3];
        return var1;
    }

    public final void orthonormalizeDouble() {
        double[][] var1 = new double[3][3];

        for(int var2 = 0; var2 < 3; ++var2) {
            var1[var2][0] = (double)this.mat[var2][0];
            var1[var2][1] = (double)this.mat[var2][1];
            var1[var2][2] = (double)this.mat[var2][2];
        }

        int var8;
        for(var8 = 0; var8 < 3; ++var8) {
            double var4;
            double var6;
            double var18;
            for(int var9 = 0; var9 < var8; ++var9) {
                var4 = var1[0][var8];
                var18 = var1[1][var8];
                var6 = var1[2][var8];
                double var10 = var1[0][var9];
                double var12 = var1[1][var9];
                double var14 = var1[2][var9];
                double var16 = var4 * var10 + var18 * var12 + var6 * var14;
                var1[0][var9] -= var4 * var16;
                var1[1][var9] -= var18 * var16;
                var1[2][var9] -= var6 * var16;
            }

            var4 = var1[0][var8];
            var18 = var1[1][var8];
            var6 = var1[2][var8];
            double var19 = 1.0D / Math.sqrt(var4 * var4 + var18 * var18 + var6 * var6);
            var1[0][var8] *= var19;
            var1[1][var8] *= var19;
            var1[2][var8] *= var19;
        }

        for(var8 = 0; var8 < 3; ++var8) {
            this.mat[var8][0] = (float)var1[var8][0];
            this.mat[var8][1] = (float)var1[var8][1];
            this.mat[var8][2] = (float)var1[var8][2];
        }

    }

    public final void orthonormalize() {
        float var1 = 0.0F;
        float var2 = 0.0F;
        float var3 = 0.0F;
        float var4 = 0.0F;
        float var5 = 0.0F;
        float var6 = 0.0F;

        for(int var7 = 0; var7 < 3; ++var7) {
            for(int var8 = 0; var8 < var7; ++var8) {
                var1 = this.mat[0][var7];
                var2 = this.mat[1][var7];
                var3 = this.mat[2][var7];
                var4 = this.mat[0][var8];
                var5 = this.mat[1][var8];
                var6 = this.mat[2][var8];
                float var9 = var1 * var4 + var2 * var5 + var3 * var6;
                this.mat[0][var8] -= var1 * var9;
                this.mat[1][var8] -= var2 * var9;
                this.mat[2][var8] -= var3 * var9;
            }

            var1 = this.mat[0][var7];
            var2 = this.mat[1][var7];
            var3 = this.mat[2][var7];
            double var10 = 1.0D / Math.sqrt((double)(var1 * var1 + var2 * var2 + var3 * var3));
            this.mat[0][var7] = (float)((double)this.mat[0][var7] * var10);
            this.mat[1][var7] = (float)((double)this.mat[1][var7] * var10);
            this.mat[2][var7] = (float)((double)this.mat[2][var7] * var10);
        }

    }

    public final float[] getDump() {
        return this.fillDump((float[])null);
    }

    public final float[] fillDump(float[] var1) {
        if(var1 == null) {
            var1 = new float[16];
        } else if(var1.length != 16) {
            //Logger.log("Dump array has to have a length of 16!", 0);
        }

        byte var2 = 0;
        int var3 = var2 + 1;
        var1[var2] = this.mat0[0];
        var1[var3++] = this.mat0[1];
        var1[var3++] = this.mat0[2];
        var1[var3++] = this.mat0[3];
        var1[var3++] = this.mat1[0];
        var1[var3++] = this.mat1[1];
        var1[var3++] = this.mat1[2];
        var1[var3++] = this.mat1[3];
        var1[var3++] = this.mat2[0];
        var1[var3++] = this.mat2[1];
        var1[var3++] = this.mat2[2];
        var1[var3++] = this.mat2[3];
        var1[var3++] = this.mat3[0];
        var1[var3++] = this.mat3[1];
        var1[var3++] = this.mat3[2];
        var1[var3] = this.mat3[3];
        return var1;
    }

    public final void setDump(float[] var1) {
        if(var1.length == 16) {
            int var2 = 0;

            for(int var3 = 0; var3 < 4; ++var3) {
                this.mat[var3][0] = var1[var2];
                this.mat[var3][1] = var1[var2 + 1];
                this.mat[var3][2] = var1[var2 + 2];
                this.mat[var3][3] = var1[var2 + 3];
                var2 += 4;
            }
        } else {
            //Logger.log("Not a valid matrix dump!", 0);
        }

    }

    public final void setTo(Matrix var1) {
        float[] var2 = this.mat[0];
        float[] var3 = var1.mat[0];
        var2[0] = var3[0];
        var2[1] = var3[1];
        var2[2] = var3[2];
        var2[3] = var3[3];
        var2 = this.mat[1];
        var3 = var1.mat[1];
        var2[0] = var3[0];
        var2[1] = var3[1];
        var2[2] = var3[2];
        var2[3] = var3[3];
        var2 = this.mat[2];
        var3 = var1.mat[2];
        var2[0] = var3[0];
        var2[1] = var3[1];
        var2[2] = var3[2];
        var2[3] = var3[3];
        var2 = this.mat[3];
        var3 = var1.mat[3];
        var2[0] = var3[0];
        var2[1] = var3[1];
        var2[2] = var3[2];
        var2[3] = var3[3];
    }

    public final void set(int var1, int var2, float var3) {
        if(var1 >= 0 && var1 <= 4 && var2 >= 0 && var2 <= 4) {
            this.mat[var1][var2] = var3;
        }

    }

    public final void setRow(int var1, float var2, float var3, float var4, float var5) {
        if(var1 >= 0 && var1 <= 4) {
            this.mat[var1][0] = var2;
            this.mat[var1][1] = var3;
            this.mat[var1][2] = var4;
            this.mat[var1][3] = var5;
        }

    }

    public final void setColumn(int var1, float var2, float var3, float var4, float var5) {
        if(var1 >= 0 && var1 <= 4) {
            this.mat[0][var1] = var2;
            this.mat[1][var1] = var3;
            this.mat[2][var1] = var4;
            this.mat[3][var1] = var5;
        }

    }

    public final float get(int var1, int var2) {
        return var1 >= 0 && var1 <= 4 && var2 >= 0 && var2 <= 4?this.mat[var1][var2]:0.0F;
    }

    public String toString() {
        String var1 = "(\n";

        for(int var2 = 0; var2 < 4; ++var2) {
            var1 = var1 + "\t" + this.mat[var2][0];
            var1 = var1 + "\t" + this.mat[var2][1];
            var1 = var1 + "\t" + this.mat[var2][2];
            var1 = var1 + "\t" + this.mat[var2][3] + "\n";
        }

        var1 = var1 + ")\n";
        return var1;
    }

    public boolean equals(Object var1) {
        if(var1 instanceof Matrix) {
            Matrix var2 = (Matrix)var1;

            for(int var3 = 0; var3 < 4; ++var3) {
                if(var2.mat[var3][0] != this.mat[var3][0]) {
                    return false;
                }

                if(var2.mat[var3][1] != this.mat[var3][1]) {
                    return false;
                }

                if(var2.mat[var3][2] != this.mat[var3][2]) {
                    return false;
                }

                if(var2.mat[var3][3] != this.mat[var3][3]) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public void transformToGL() {
        this.mat0[1] *= -1.0F;
        this.mat0[2] *= -1.0F;
        this.mat1[1] *= -1.0F;
        this.mat1[2] *= -1.0F;
        this.mat2[1] *= -1.0F;
        this.mat2[2] *= -1.0F;
        this.mat3[1] *= -1.0F;
        this.mat3[2] *= -1.0F;
    }
}
