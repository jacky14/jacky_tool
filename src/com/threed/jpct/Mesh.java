package com.threed.jpct;

import com.threed.jpct.Config;
import com.threed.jpct.GenericContainer;
import com.threed.jpct.IVertexController;
import com.threed.jpct.IntegerC;
import com.threed.jpct.Logger;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Vectors;
import com.threed.jpct.VertexAttributes;
import com.threed.jpct.World;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
//com.threed.jpct.Mesh
public  class Mesh implements Serializable {
   private static final long serialVersionUID = 2L;
   public static final boolean COMPRESS = true;
   public static final boolean DONT_COMPRESS = false;
   public static final int SERIALIZE_ALL = 0;
   public static final int SERIALIZE_VERTICES_ONLY = 1;
   public static final int SERIALIZE_LOW_PRECISION = 2;
   private int serializeMethod = 0;
   private IVertexController myController = null;
   boolean normalsCalculated;
   boolean tangentsCalculated = false;
   public int anzVectors;
   int maxVectors;
   int anzCoords;
   int anzTri;
   int[][] points;
   float[] xOrg;
   float[] yOrg;
   float[] zOrg;
   float[] nxOrg;
   float[] nyOrg;
   float[] nzOrg;
   public int[] coords;
   int obbStart;
   int obbEnd;
   short[] sxOrg = null;
   short[] syOrg = null;
   short[] szOrg = null;
   short[] snxOrg = null;
   short[] snyOrg = null;
   short[] snzOrg = null;
   float[][] tangentVectors;
   Vector attrList = null;
   boolean locked = false;
   private VertexAttributes[] attrsArrays = null;

   
   public void out(PrintWriter pw){
	  for(int i=0;i<anzCoords;i++){
		 pw.println("v "+ xOrg[i] + " " + yOrg[i] + " " + zOrg[i]);
	  }
	  
	  /*int size = anzVectors / 3;
	  for(int i=0;i<size;i++){
		  pw.println("f " + (coords[i*3] +1 ) + " " + (coords[i*3+1]+1) + " "+ (coords[i*3+2]+1) );
	  }*/
	
	  
   }
   
   
  
   
   Mesh(int var1) {
      this.maxVectors = var1;
      this.anzVectors = 0;
      this.anzCoords = 0;
      this.anzTri = 0;
      this.obbStart = 0;
      this.obbEnd = 0;
      this.normalsCalculated = false;
      this.points = new int[this.maxVectors / 3 + 1][3];
      this.coords = new int[this.maxVectors];
      this.xOrg = new float[this.maxVectors];
      this.yOrg = new float[this.maxVectors];
      this.zOrg = new float[this.maxVectors];
      this.nxOrg = new float[this.maxVectors];
      this.nyOrg = new float[this.maxVectors];
      this.nzOrg = new float[this.maxVectors];
   }

   public boolean setVertexController(IVertexController var1, boolean var2) {
      if(this.myController != null) {
         this.myController.destroy();
      }

      if(var1.init(this, var2)) {
         this.myController = var1;
         return true;
      } else {
         return false;
      }
   }

   public void applyVertexController() {
      if(this.myController != null) {
         this.myController.apply();
         this.myController.updateMesh();
      } else {
         Logger.log((String)"No controller has been assigned to this mesh", 1);
      }

   }

   public void removeVertexController() {
      if(this.myController != null) {
         this.myController.destroy();
         this.myController = null;
      }

   }

   public void strip() {
      if(!this.locked) {
         this.points = (int[][])null;
         this.coords = null;
      }
   }

   void strongStrip(World var1, Object3D var2) {
      if(var1 != null && !this.locked) {
         int var3 = var1.objectList.size();

         for(int var4 = 0; var4 < var3; ++var4) {
            Object3D var5 = var1.objectList.elementAt(var4);
            if(var5 != var2 && var5.objMesh == this && (!var5.isCompiled() || var5.dynamic)) {
               return;
            }
         }

         this.nxOrg = null;
         this.nyOrg = null;
         this.nzOrg = null;
         this.sxOrg = null;
         this.syOrg = null;
         this.szOrg = null;
         this.snxOrg = null;
         this.snyOrg = null;
         this.snzOrg = null;
      }
   }

   public void compress() {
      int var1 = this.anzCoords;
      int var2 = this.anzVectors + 8;
      if(this.obbEnd == 0) {
         var1 += 8;
      }

      float[] var3 = new float[var1];
      float[] var4 = new float[var1];
      float[] var5 = new float[var1];
      float[] var6 = new float[var1];
      float[] var7 = new float[var1];
      float[] var8 = new float[var1];

      int var9;
      for(var9 = 0; var9 < this.anzCoords; ++var9) {
         var3[var9] = this.xOrg[var9];
         var4[var9] = this.yOrg[var9];
         var5[var9] = this.zOrg[var9];
         var6[var9] = this.nxOrg[var9];
         var7[var9] = this.nyOrg[var9];
         var8[var9] = this.nzOrg[var9];
      }

      this.xOrg = var3;
      this.yOrg = var4;
      this.zOrg = var5;
      this.nxOrg = var6;
      this.nyOrg = var7;
      this.nzOrg = var8;
      if(var2 < this.maxVectors) {
         var9 = var2 / 3 + 1;
         int[][] var10 = new int[var9][3];
         int[] var11 = new int[var2];

         int var12;
         for(var12 = 0; var12 < var2; ++var12) {
            var11[var12] = this.coords[var12];
         }

         for(var12 = 0; var12 < var9; ++var12) {
            var10[var12] = this.points[var12];
         }

         this.coords = var11;
         this.points = var10;
         this.maxVectors = var2;
      }

   }

   public Mesh cloneMesh(boolean var1) {
      Mesh var2 = new Mesh(this.maxVectors);
      var2.anzCoords = this.anzCoords;
      var2.anzVectors = this.anzVectors;
      var2.anzTri = this.anzTri;
      var2.obbStart = this.obbStart;
      var2.obbEnd = this.obbEnd;

      int var3;
      for(var3 = 0; var3 < this.maxVectors; ++var3) {
         var2.coords[var3] = this.coords[var3];
         if(var3 < this.anzCoords) {
            var2.xOrg[var3] = this.xOrg[var3];
            var2.yOrg[var3] = this.yOrg[var3];
            var2.zOrg[var3] = this.zOrg[var3];
            var2.nxOrg[var3] = this.nxOrg[var3];
            var2.nyOrg[var3] = this.nyOrg[var3];
            var2.nzOrg[var3] = this.nzOrg[var3];
         }
      }

      if(this.tangentVectors != null && this.tangentsCalculated) {
         var2.tangentsCalculated = this.tangentsCalculated;
         var2.tangentVectors = new float[this.tangentVectors.length][];

         for(var3 = 0; var3 < this.anzCoords; ++var3) {
            float[] var4 = new float[]{this.tangentVectors[var3][0], this.tangentVectors[var3][1], this.tangentVectors[var3][2], this.tangentVectors[var3][3]};
            var2.tangentVectors[var3] = var4;
         }
      }

      var3 = this.maxVectors / 3 + 1;

      for(int var6 = 0; var6 < var3; ++var6) {
         for(int var5 = 0; var5 < 3; ++var5) {
            var2.points[var6][var5] = this.points[var6][var5];
         }
      }

      if(var1) {
         var2.compress();
      }

      return var2;
   }

   public int getVertexCount() {
      return this.anzVectors;
   }

   public int getUniqueVertexCount() {
      return this.anzCoords;
   }

   public int getTriangleCount() {
      return this.anzTri;
   }

   public float[] getBoundingBox() {
      return this.calcBoundingBox();
   }

   public void setLocked(boolean var1) {
      this.locked = var1;
   }

   public boolean isLocked() {
      return this.locked;
   }

   public void addVertexAttributes(VertexAttributes var1) {
      if(var1.matches(this.anzCoords)) {
         if(this.attrList == null) {
            this.attrList = new Vector(1);
         }

         if(!this.attrList.contains(var1)) {
            this.attrList.addElement(var1);
         }
      } else {
         Logger.log((String)"Number of elements in vertex attributes doesn\'t match mesh size!", 0);
      }

   }

   public VertexAttributes[] getVertexAttributes() {
      if(this.attrList != null && this.attrList.size() != 0) {
         if(this.attrsArrays == null) {
            this.attrsArrays = new VertexAttributes[this.attrList.size()];
         }

         this.attrsArrays = (VertexAttributes[])((VertexAttributes[])this.attrList.toArray(this.attrsArrays));
         return this.attrsArrays;
      } else {
         return null;
      }
   }

   void translateMesh(Matrix var1, Matrix var2) {
      for(int var3 = 0; var3 < this.anzCoords; ++var3) {
         this.zOrg[var3] += var1.mat[3][2] + var2.mat[3][2];
         this.xOrg[var3] += var1.mat[3][0] + var2.mat[3][0];
         this.yOrg[var3] += var1.mat[3][1] + var2.mat[3][1];
      }

   }

   final float getLargestCoveredDistance() {
      float var1 = -1.0F;

      for(int var2 = 0; var2 < this.anzTri; ++var2) {
         int var3 = this.coords[this.points[var2][0]];
         int var4 = this.coords[this.points[var2][1]];
         int var5 = this.coords[this.points[var2][2]];
         float var6 = this.xOrg[var3];
         float var7 = this.yOrg[var3];
         float var8 = this.zOrg[var3];
         float var9 = this.xOrg[var4];
         float var10 = this.yOrg[var4];
         float var11 = this.zOrg[var4];
         float var12 = this.xOrg[var5];
         float var13 = this.yOrg[var5];
         float var14 = this.zOrg[var5];
         float var15 = Math.abs(var6 - var9);
         float var16 = Math.abs(var9 - var12);
         float var17 = Math.abs(var6 - var12);
         float var18 = Math.abs(var7 - var10);
         float var19 = Math.abs(var10 - var13);
         float var20 = Math.abs(var7 - var13);
         float var21 = Math.abs(var8 - var11);
         float var22 = Math.abs(var11 - var14);
         float var23 = Math.abs(var8 - var14);
         if(var15 > var1) {
            var1 = var15;
         }

         if(var16 > var1) {
            var1 = var16;
         }

         if(var17 > var1) {
            var1 = var17;
         }

         if(var18 > var1) {
            var1 = var18;
         }

         if(var19 > var1) {
            var1 = var19;
         }

         if(var20 > var1) {
            var1 = var20;
         }

         if(var21 > var1) {
            var1 = var21;
         }

         if(var22 > var1) {
            var1 = var22;
         }

         if(var23 > var1) {
            var1 = var23;
         }
      }

      return var1;
   }

   synchronized void calcNormals() {
      if(this.anzTri >= Config.optimizeNormalCalcTH) {
         this.calcNormalsFast();
      } else {
         this.calcNormalsSlow();
      }

      this.normalsCalculated = true;
   }

   final Vector[] calcNormalsMD2(Vector[] var1) {
      Vector[] var2 = null;
      if(var1 == null) {
         var2 = new Vector[this.anzCoords];
      }

      for(int var3 = 0; var3 < this.anzCoords; ++var3) {
         if(var1 == null) {
            var2[var3] = new Vector();
         }

         int var4 = 0;
         double var5 = 0.0D;
         double var7 = 0.0D;
         double var9 = 0.0D;
         float var11 = this.xOrg[var3];
         float var12 = this.yOrg[var3];
         float var13 = this.zOrg[var3];
         int var14;
         int var15;
         int var16;
         int var17;
         float var19;
         float var20;
         float var21;
         float var22;
         float var23;
         float var24;
         float var25;
         float var26;
         if(var1 == null) {
            for(var14 = 0; var14 < this.anzTri; ++var14) {
               var15 = this.coords[this.points[var14][0]];
               var16 = this.coords[this.points[var14][1]];
               var17 = this.coords[this.points[var14][2]];
               float var47 = this.xOrg[var17];
               var19 = this.yOrg[var17];
               var20 = this.zOrg[var17];
               var21 = this.xOrg[var15];
               var22 = this.yOrg[var15];
               var23 = this.zOrg[var15];
               var24 = this.xOrg[var16];
               var25 = this.yOrg[var16];
               var26 = this.zOrg[var16];
               if(var11 == var47 && var12 == var19 && var13 == var20 || var11 == var24 && var12 == var25 && var13 == var26 || var11 == var21 && var12 == var22 && var13 == var23) {
                  var2[var3].addElement(IntegerC.valueOf(var14));
                  ++var4;
                  double var48 = (double)var47;
                  double var29 = (double)var19;
                  double var31 = (double)var20;
                  double var33 = (double)var21 - var48;
                  double var35 = (double)var22 - var29;
                  double var37 = (double)var23 - var31;
                  double var39 = (double)var24 - var48;
                  double var41 = (double)var25 - var29;
                  double var43 = (double)var26 - var31;
                  var5 += var35 * var43 - var37 * var41;
                  var7 += var37 * var39 - var33 * var43;
                  var9 += var33 * var41 - var35 * var39;
               }
            }
         } else {
            for(var14 = 0; var14 < var1[var3].size(); ++var14) {
               var15 = ((Integer)var1[var3].elementAt(var14)).intValue();
               var16 = this.coords[this.points[var15][0]];
               var17 = this.coords[this.points[var15][1]];
               int var18 = this.coords[this.points[var15][2]];
               var19 = this.xOrg[var18];
               var20 = this.yOrg[var18];
               var21 = this.zOrg[var18];
               var22 = this.xOrg[var16];
               var23 = this.yOrg[var16];
               var24 = this.zOrg[var16];
               var25 = this.xOrg[var17];
               var26 = this.yOrg[var17];
               float var27 = this.zOrg[var17];
               if(var11 == var19 && var12 == var20 && var13 == var21 || var11 == var25 && var12 == var26 && var13 == var27 || var11 == var22 && var12 == var23 && var13 == var24) {
                  ++var4;
                  double var28 = (double)var19;
                  double var30 = (double)var20;
                  double var32 = (double)var21;
                  double var34 = (double)var22 - var28;
                  double var36 = (double)var23 - var30;
                  double var38 = (double)var24 - var32;
                  double var40 = (double)var25 - var28;
                  double var42 = (double)var26 - var30;
                  double var44 = (double)var27 - var32;
                  var5 += var36 * var44 - var38 * var42;
                  var7 += var38 * var40 - var34 * var44;
                  var9 += var34 * var42 - var36 * var40;
               }
            }
         }

         if(var4 != 0) {
            double var46 = Math.sqrt(var5 * var5 + var7 * var7 + var9 * var9);
            if(var46 == 0.0D) {
               var46 = 9.999999960041972E-13D;
            }

            this.nxOrg[var3] = (float)(var5 / var46);
            this.nyOrg[var3] = (float)(var7 / var46);
            this.nzOrg[var3] = (float)(var9 / var46);
         }
      }

      if(var1 == null) {
         return var2;
      } else {
         return var1;
      }
   }

   SimpleVector calcCenter() {
      float var1 = 0.0F;
      float var2 = 0.0F;
      float var3 = 0.0F;
      int var4 = 0;

      for(int var5 = 0; var5 < this.anzTri; ++var5) {
         int[] var6 = this.points[var5];

         for(int var7 = 0; var7 < 3; ++var7) {
            int var8 = this.coords[var6[var7]];
            var1 += this.xOrg[var8];
            var3 += this.yOrg[var8];
            var2 += this.zOrg[var8];
            ++var4;
         }
      }

      if(var4 != 0) {
         float var9 = (float)var4;
         return new SimpleVector(var1 / var9, var3 / var9, var2 / var9);
      } else {
         return new SimpleVector(0.0F, 0.0F, 0.0F);
      }
   }

   void rotateMesh(Matrix var1, float var2, float var3, float var4, float var5) {
      float var6 = var1.mat[0][0];
      float var7 = var1.mat[1][0];
      float var8 = var1.mat[2][0];
      float var9 = var1.mat[0][1];
      float var10 = var1.mat[1][1];
      float var11 = var1.mat[2][1];
      float var12 = var1.mat[0][2];
      float var13 = var1.mat[1][2];
      float var14 = var1.mat[2][2];
      float var15 = var2;
      float var16 = var3;
      float var17 = var4;

      for(int var18 = 0; var18 < this.anzCoords; ++var18) {
         float var19 = this.zOrg[var18] - var17;
         float var20 = this.xOrg[var18] - var15;
         float var21 = this.yOrg[var18] - var16;
         float var22 = var20 * var6 + var21 * var7 + var19 * var8 + var15;
         float var23 = var20 * var9 + var21 * var10 + var19 * var11 + var16;
         float var24 = var20 * var12 + var21 * var13 + var19 * var14 + var17;
         this.xOrg[var18] = var22;
         this.yOrg[var18] = var23;
         this.zOrg[var18] = var24;
         var20 = this.nxOrg[var18];
         var21 = this.nyOrg[var18];
         var19 = this.nzOrg[var18];
         var22 = var20 * var6 + var21 * var7 + var19 * var8;
         var23 = var20 * var9 + var21 * var10 + var19 * var11;
         var24 = var20 * var12 + var21 * var13 + var19 * var14;
         this.nxOrg[var18] = var22 / var5;
         this.nyOrg[var18] = var23 / var5;
         this.nzOrg[var18] = var24 / var5;
      }

   }

   synchronized float[] calcBoundingBox() {
      float[] var1 = new float[6];
      float var2 = 9.9999998E10F;
      float var3 = -9.9999998E10F;
      float var4 = 9.9999998E10F;
      float var5 = -9.9999998E10F;
      float var6 = 9.9999998E10F;
      float var7 = -9.9999998E10F;
      int var8 = this.anzCoords;
      if(this.obbStart != 0) {
         var8 = this.obbStart;
      }

      for(int var9 = 0; var9 < var8; ++var9) {
         float var10 = this.xOrg[var9];
         float var11 = this.yOrg[var9];
         float var12 = this.zOrg[var9];
         if(var10 < var2) {
            var2 = var10;
         }

         if(var10 > var3) {
            var3 = var10;
         }

         if(var11 < var4) {
            var4 = var11;
         }

         if(var11 > var5) {
            var5 = var11;
         }

         if(var12 < var6) {
            var6 = var12;
         }

         if(var12 > var7) {
            var7 = var12;
         }
      }

      var1[0] = var2;
      var1[1] = var3;
      var1[2] = var4;
      var1[3] = var5;
      var1[4] = var6;
      var1[5] = var7;
      return var1;
   }

   private final void calcNormalsSlow() {
      for(int var1 = 0; var1 < this.anzCoords; ++var1) {
         int var2 = 0;
         float var3 = 0.0F;
         float var4 = 0.0F;
         float var5 = 0.0F;
         float var6 = this.xOrg[var1];
         float var7 = this.yOrg[var1];
         float var8 = this.zOrg[var1];

         for(int var9 = 0; var9 < this.anzTri; ++var9) {
            int var10 = this.coords[this.points[var9][0]];
            int var11 = this.coords[this.points[var9][1]];
            int var12 = this.coords[this.points[var9][2]];
            float var13 = this.xOrg[var12];
            float var14 = this.yOrg[var12];
            float var15 = this.zOrg[var12];
            float var16 = this.xOrg[var10];
            float var17 = this.yOrg[var10];
            float var18 = this.zOrg[var10];
            float var19 = this.xOrg[var11];
            float var20 = this.yOrg[var11];
            float var21 = this.zOrg[var11];
            if(var6 == var13 && var7 == var14 && var8 == var15 || var6 == var19 && var7 == var20 && var8 == var21 || var6 == var16 && var7 == var17 && var8 == var18) {
               ++var2;
               float var22 = var16 - var13;
               float var23 = var17 - var14;
               float var24 = var18 - var15;
               float var25 = var19 - var13;
               float var26 = var20 - var14;
               float var27 = var21 - var15;
               var3 += var23 * var27 - var24 * var26;
               var4 += var24 * var25 - var22 * var27;
               var5 += var22 * var26 - var23 * var25;
            }
         }

         if(var2 != 0) {
            float var28 = (float)Math.sqrt((double)(var3 * var3 + var4 * var4 + var5 * var5));
            if(var28 == 0.0F) {
               var28 = 1.0E-12F;
            }

            this.nxOrg[var1] = var3 / var28;
            this.nyOrg[var1] = var4 / var28;
            this.nzOrg[var1] = var5 / var28;
         }
      }

   }

   void calculateTangentVectors(Vectors var1) {
      long var2 = System.currentTimeMillis();
      if(this.tangentVectors == null || this.tangentVectors.length != this.anzCoords) {
         this.tangentVectors = new float[this.anzCoords][];
      }

      SimpleVector[] var4 = new SimpleVector[this.anzCoords];
      SimpleVector[] var5 = new SimpleVector[this.anzCoords];
      int var6 = var4.length;

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         var4[var7] = new SimpleVector(0.0F, 0.0F, 0.0F);
         var5[var7] = new SimpleVector(0.0F, 0.0F, 0.0F);
      }

      var6 = this.anzTri;

      int var10;
      for(var7 = 0; var7 < var6; ++var7) {
         int[] var8 = this.points[var7];
         int var9 = this.coords[var8[0]];
         var10 = this.coords[var8[1]];
         int var11 = this.coords[var8[2]];
         float var12 = this.xOrg[var9];
         float var13 = this.yOrg[var9];
         float var14 = this.zOrg[var9];
         float var15 = this.xOrg[var10];
         float var16 = this.yOrg[var10];
         float var17 = this.zOrg[var10];
         float var18 = this.xOrg[var11];
         float var19 = this.yOrg[var11];
         float var20 = this.zOrg[var11];
         float var21 = var1.nuOrg[var8[0]];
         float var22 = var1.nvOrg[var8[0]];
         float var23 = var1.nuOrg[var8[1]];
         float var24 = var1.nvOrg[var8[1]];
         float var25 = var1.nuOrg[var8[2]];
         float var26 = var1.nvOrg[var8[2]];
         float var27 = var15 - var12;
         float var28 = var18 - var12;
         float var29 = var16 - var13;
         float var30 = var19 - var13;
         float var31 = var17 - var14;
         float var32 = var20 - var14;
         float var33 = var23 - var21;
         float var34 = var25 - var21;
         float var35 = var24 - var22;
         float var36 = var26 - var22;
         if(var33 == 0.0F) {
            var33 = 1.0E-5F;
         }

         if(var34 == 0.0F) {
            var34 = 1.0E-5F;
         }

         if(var35 == 0.0F) {
            var35 = 1.0E-5F;
         }

         if(var36 == 0.0F) {
            var36 = 1.0E-5F;
         }

         float var37 = var33 * var36 - var34 * var35;
         if(var37 == 0.0F) {
            var37 = 1.0E-5F;
         }

         float var38 = 1.0F / var37;
         SimpleVector var39 = new SimpleVector((var36 * var27 - var35 * var28) * var38, (var36 * var29 - var35 * var30) * var38, (var36 * var31 - var35 * var32) * var38);
         SimpleVector var40 = new SimpleVector((var33 * var28 - var34 * var27) * var38, (var33 * var30 - var34 * var29) * var38, (var33 * var32 - var34 * var31) * var38);
         var4[var9].add(var39);
         var4[var10].add(var39);
         var4[var11].add(var39);
         var5[var9].add(var40);
         var5[var10].add(var40);
         var5[var11].add(var40);
      }

      SimpleVector var41 = new SimpleVector();
      SimpleVector var42 = new SimpleVector();
      SimpleVector var43 = new SimpleVector();
      var6 = this.anzCoords;

      for(var10 = 0; var10 < var6; ++var10) {
         var41.set(this.nxOrg[var10], this.nyOrg[var10], this.nzOrg[var10]);
         var43.set(var4[var10]);
         var42.set(var41);
         float var44 = var41.calcDot(var43);
         var42.scalarMul(var44);
         var43.sub(var42);
         var43 = var43.normalize();
         float[] var45 = this.tangentVectors[var10];
         if(var45 == null) {
            var45 = new float[4];
            this.tangentVectors[var10] = var45;
         }

         var45[0] = var43.x;
         var45[1] = var43.y;
         var45[2] = var43.z;
         SimpleVector var46 = var41.calcCross(var43);
         var44 = var46.calcDot(var5[var10]);
         var45[3] = var44 < 0.0F?-1.0F:1.0F;
      }

      this.tangentsCalculated = true;
      Logger.log("Tangent vectors calculated in " + (System.currentTimeMillis() - var2) + "ms!");
   }

   private final void calcNormalsFast() {
      Hashtable var1 = new Hashtable();

      int var2;
      float var6;
      float var7;
      float var8;
      for(var2 = 0; var2 < this.anzTri; ++var2) {
         int[] var3 = this.points[var2];

         for(int var4 = 0; var4 < 3; ++var4) {
            int var5 = this.coords[var3[var4]];
            var6 = this.xOrg[var5];
            var7 = this.yOrg[var5];
            var8 = this.zOrg[var5];
            GenericContainer var9 = new GenericContainer(3);
            var9.add(var6);
            var9.add(var7);
            var9.add(var8);
            Vector var10 = (Vector)var1.get(var9);
            if(var10 == null) {
               var10 = new Vector(4);
               var1.put(var9, var10);
            }

            var10.addElement(IntegerC.valueOf(var2));
         }
      }

      for(var2 = 0; var2 < this.anzCoords; ++var2) {
         int var33 = 0;
         float var34 = 0.0F;
         float var35 = 0.0F;
         var6 = 0.0F;
         var7 = this.xOrg[var2];
         var8 = this.yOrg[var2];
         float var36 = this.zOrg[var2];
         GenericContainer var37 = new GenericContainer(3);
         var37.add(var7);
         var37.add(var8);
         var37.add(var36);
         Object var11 = var1.get(var37);
         if(var11 != null) {
            Vector var12 = (Vector)var11;

            for(int var13 = 0; var13 < var12.size(); ++var13) {
               int var14 = ((Integer)var12.elementAt(var13)).intValue();
               int var15 = this.coords[this.points[var14][0]];
               int var16 = this.coords[this.points[var14][1]];
               int var17 = this.coords[this.points[var14][2]];
               float var18 = this.xOrg[var17];
               float var19 = this.yOrg[var17];
               float var20 = this.zOrg[var17];
               float var21 = this.xOrg[var15];
               float var22 = this.yOrg[var15];
               float var23 = this.zOrg[var15];
               float var24 = this.xOrg[var16];
               float var25 = this.yOrg[var16];
               float var26 = this.zOrg[var16];
               ++var33;
               float var27 = var21 - var18;
               float var28 = var22 - var19;
               float var29 = var23 - var20;
               float var30 = var24 - var18;
               float var31 = var25 - var19;
               float var32 = var26 - var20;
               var34 += var28 * var32 - var29 * var31;
               var35 += var29 * var30 - var27 * var32;
               var6 += var27 * var31 - var28 * var30;
            }
         }

         if(var33 != 0) {
            float var38 = (float)Math.sqrt((double)(var34 * var34 + var35 * var35 + var6 * var6));
            if(var38 == 0.0F) {
               var38 = 1.0E-12F;
            }

            this.nxOrg[var2] = var34 / var38;
            this.nyOrg[var2] = var35 / var38;
            this.nzOrg[var2] = var6 / var38;
         }
      }

   }

   public void setSerializeMethod(int var1) {
      this.serializeMethod = var1;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      if((this.serializeMethod & 2) != 0) {
         this.xOrg = this.shortToFloat(this.sxOrg);
         this.sxOrg = null;
         this.yOrg = this.shortToFloat(this.syOrg);
         this.syOrg = null;
         this.zOrg = this.shortToFloat(this.szOrg);
         this.szOrg = null;
         this.nxOrg = this.shortToFloat(this.snxOrg);
         this.snxOrg = null;
         this.nyOrg = this.shortToFloat(this.snyOrg);
         this.snyOrg = null;
         this.nzOrg = this.shortToFloat(this.snzOrg);
         this.snzOrg = null;
      }

      if((this.serializeMethod & 1) != 0) {
         this.nxOrg = new float[this.xOrg.length];
         this.nyOrg = new float[this.yOrg.length];
         this.nzOrg = new float[this.zOrg.length];
         if(this.points != null) {
            this.calcNormals();
         }
      }

   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      float[] var2 = this.xOrg;
      float[] var3 = this.yOrg;
      float[] var4 = this.zOrg;
      float[] var5 = this.nxOrg;
      float[] var6 = this.nyOrg;
      float[] var7 = this.nzOrg;
      if((this.serializeMethod & 1) != 0) {
         this.nxOrg = null;
         this.nyOrg = null;
         this.nzOrg = null;
      }

      if((this.serializeMethod & 2) != 0) {
         this.sxOrg = this.floatToShort(this.xOrg);
         this.xOrg = null;
         this.syOrg = this.floatToShort(this.yOrg);
         this.yOrg = null;
         this.szOrg = this.floatToShort(this.zOrg);
         this.zOrg = null;
         this.snxOrg = this.floatToShort(this.nxOrg);
         this.nxOrg = null;
         this.snyOrg = this.floatToShort(this.nyOrg);
         this.nyOrg = null;
         this.snzOrg = this.floatToShort(this.nzOrg);
         this.nzOrg = null;
      }

      var1.defaultWriteObject();
      this.xOrg = var2;
      this.yOrg = var3;
      this.zOrg = var4;
      this.nxOrg = var5;
      this.nyOrg = var6;
      this.nzOrg = var7;
   }

   private short[] floatToShort(float[] var1) {
      if(var1 == null) {
         return null;
      } else {
         short[] var2 = new short[var1.length];

         for(int var3 = 0; var3 < var1.length; ++var3) {
            var2[var3] = (short)(Float.floatToRawIntBits(var1[var3]) >> 16);
         }

         return var2;
      }
   }
   
   private float[] shortToFloat(short[] var1) {
      if(var1 == null) {
         return null;
      } else {
         float[] var2 = new float[var1.length];

         for(int var3 = 0; var3 < var1.length; ++var3) {
            var2[var3] = Float.intBitsToFloat(var1[var3] << 16);
         }

         return var2;
      }
   }
}
