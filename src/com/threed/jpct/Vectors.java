package com.threed.jpct;


import com.threed.jpct.Config;
import com.threed.jpct.Mesh;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
class F_I_vec{
   public List<float[]> fv =new ArrayList<float[]>();
   public int getindex(float[] tmp) {
      int r = 0;
      for(int i=0;i<fv.size();i++){
         float[] cash = fv.get(i);
         if(tmp[0] == cash[0]&&tmp[1] == cash[1]){
            return i+1;
         }
      }
      fv.add(tmp);
      return fv.size();
   }

}
final class Vectors implements Serializable {
   private static final long serialVersionUID = 1L;
   transient float[] xTr;
   transient float[] yTr;
   transient float[] zTr;
   transient float[] sx;
   transient float[] sy;
   transient float[] sz;
   transient float[] sb;
   transient float[] sr;
   transient float[] sg;
   float[] sbOrg;
   float[] srOrg;
   float[] sgOrg;
   public float[] nuOrg;
   public float[] nvOrg;
   float[] uOrg;
   float[] vOrg;
   float[][] uMul;
   float[][] vMul;
   transient float[] su;
   transient float[] sv;
   transient float[] eu;
   transient float[] ev;
   float[] buOrg;
   float[] bvOrg;
   float[] alpha;
   transient float[] bsu;
   transient float[] bsv;
   int[] vertexSector;
   int maxVectors;
   boolean hasAlpha = false;
   private Mesh baseMesh;

   public void out(PrintWriter pw){
	   //nuOrg;
	   /*F_I_vec fi  = new F_I_vec(); 
	  
	   
	   for(int i=0;i<nuOrg.length;i++){
		   int index = fi.getindex(new float[]{nuOrg[i], 1-nvOrg[i]});
	   }*/
	   
	   
   }
   
   
   Vectors(int var1, Mesh var2) {
      this.baseMesh = var2;
      this.maxVectors = var1;
      this.vertexSector = new int[this.maxVectors];
      this.xTr = new float[this.maxVectors];
      this.yTr = new float[this.maxVectors];
      this.zTr = new float[this.maxVectors];
      this.sx = new float[this.maxVectors];
      this.sy = new float[this.maxVectors];
      this.sz = new float[this.maxVectors];
      this.sbOrg = new float[this.maxVectors];
      this.sgOrg = new float[this.maxVectors];
      this.srOrg = new float[this.maxVectors];
      this.uOrg = new float[this.maxVectors];
      this.vOrg = new float[this.maxVectors];
      this.nuOrg = new float[this.maxVectors];
      this.nvOrg = new float[this.maxVectors];
      this.su = new float[this.maxVectors];
      this.sv = new float[this.maxVectors];
      if(!Config.saveMemory) {
         this.sb = new float[this.maxVectors];
         this.sg = new float[this.maxVectors];
         this.sr = new float[this.maxVectors];
         this.eu = new float[this.maxVectors];
         this.ev = new float[this.maxVectors];
         this.bsu = new float[this.maxVectors];
         this.bsv = new float[this.maxVectors];
      }

      this.buOrg = new float[this.maxVectors];
      this.bvOrg = new float[this.maxVectors];
   }

   void createMultiCoords() {
      if(this.uMul == null) {
         this.uMul = new float[Config.maxTextureLayers - 1][this.maxVectors];
         this.vMul = new float[Config.maxTextureLayers - 1][this.maxVectors];
      }

   }

   void createAlpha() {
      if(this.alpha == null) {
         this.alpha = new float[this.maxVectors];

         for(int var1 = 0; var1 < this.maxVectors; ++var1) {
            this.alpha[var1] = 1.0F;
         }

         this.hasAlpha = true;
      }

   }

   void killMultiCoords() {
      this.uMul = (float[][])null;
      this.vMul = (float[][])null;
   }

   void createScreenColors() {
      if(this.sb == null) {
         this.sb = new float[this.maxVectors];
         this.sg = new float[this.maxVectors];
         this.sr = new float[this.maxVectors];
      }

   }

   void createEnvmapCoords() {
      if(this.eu == null) {
         this.eu = new float[this.maxVectors];
         this.ev = new float[this.maxVectors];
      }

   }

   void createBumpmapCoords() {
      if(this.bsu == null) {
         this.bsu = new float[this.maxVectors];
         this.bsv = new float[this.maxVectors];
      }

   }

   int checkCoords(float var1, float var2, float var3, int var4) {
      int var5 = this.baseMesh.anzCoords - 1;
      float[] var6 = this.baseMesh.xOrg;
      float[] var7 = this.baseMesh.yOrg;

      for(float[] var8 = this.baseMesh.zOrg; var5 >= 0; --var5) {
         if(var6[var5] == var1 && var7[var5] == var2 && var8[var5] == var3 && (var4 == 0 || var4 == this.vertexSector[var5])) {
            return var5;
         }
      }

      return -1;
   }

   int addVertex(float var1, float var2, float var3) {
      return this.addVertex(var1, var2, var3, 0);
   }

   int addVertex(float var1, float var2, float var3, int var4) {
      int var5 = this.baseMesh.anzCoords;
      this.baseMesh.xOrg[var5] = var1;
      this.baseMesh.yOrg[var5] = var2;
      this.baseMesh.zOrg[var5] = var3;
      this.vertexSector[var5] = var4;
      ++this.baseMesh.anzCoords;
      return var5;
   }

   void setMesh(Mesh var1) {
      this.baseMesh = var1;
   }

   void strip() {
      this.sx = null;
      this.sy = null;
      this.sz = null;
      this.sr = null;
      this.sg = null;
      this.sb = null;
      this.sbOrg = null;
      this.sgOrg = null;
      this.srOrg = null;
      this.nuOrg = null;
      this.nvOrg = null;
      this.uOrg = null;
      this.vOrg = null;
      this.su = null;
      this.sv = null;
      this.buOrg = null;
      this.bvOrg = null;
      this.bsu = null;
      this.bsv = null;
      this.uMul = (float[][])null;
      this.vMul = (float[][])null;
      this.alpha = null;
      this.xTr = this.clamp(this.xTr);
      this.yTr = this.clamp(this.yTr);
      this.zTr = this.clamp(this.zTr);
   }

   private float[] clamp(float[] var1) {
      float[] var2 = new float[this.baseMesh.anzCoords];
      int var3 = this.baseMesh.anzCoords;
      if(this.baseMesh.obbStart == 0) {
         var3 += 8;
      }

      System.arraycopy(var1, 0, var2, 0, var3);
      return var2;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.xTr = new float[this.maxVectors];
      this.yTr = new float[this.maxVectors];
      this.zTr = new float[this.maxVectors];
      if(!Config.saveMemory) {
         this.sb = new float[this.maxVectors];
         this.sg = new float[this.maxVectors];
         this.sr = new float[this.maxVectors];
      }

      this.sx = new float[this.maxVectors];
      this.sy = new float[this.maxVectors];
      this.sz = new float[this.maxVectors];
      this.su = new float[this.maxVectors];
      this.sv = new float[this.maxVectors];
      if(!Config.saveMemory) {
         this.eu = new float[this.maxVectors];
         this.ev = new float[this.maxVectors];
         this.bsu = new float[this.maxVectors];
         this.bsv = new float[this.maxVectors];
      }

   }

   static final void calcCross(float[] var0, float[] var1, float[] var2) {
      var0[0] = var1[1] * var2[2] - var1[2] * var2[1];
      var0[1] = var1[2] * var2[0] - var1[0] * var2[2];
      var0[2] = var1[0] * var2[1] - var1[1] * var2[0];
   }

   static final void calcCross(double[] var0, double[] var1, double[] var2) {
      var0[0] = var1[1] * var2[2] - var1[2] * var2[1];
      var0[1] = var1[2] * var2[0] - var1[0] * var2[2];
      var0[2] = var1[0] * var2[1] - var1[1] * var2[0];
   }

   static final float[] calcCross(float[] var0, float[] var1) {
      float[] var2 = new float[]{var0[1] * var1[2] - var0[2] * var1[1], var0[2] * var1[0] - var0[0] * var1[2], var0[0] * var1[1] - var0[1] * var1[0]};
      return var2;
   }

   static final float calcDot(float[] var0, float[] var1) {
      return var0[0] * var1[0] + var0[1] * var1[1] + var0[2] * var1[2];
   }

   static final double calcDot(double[] var0, double[] var1) {
      return var0[0] * var1[0] + var0[1] * var1[1] + var0[2] * var1[2];
   }

   static final float[] calcSub(float[] var0, float[] var1) {
      float[] var2 = new float[]{var0[0] - var1[0], var0[1] - var1[1], var0[2] - var1[2]};
      return var2;
   }

   static final void calcCross(float[] var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      var0[0] = var2 * var6 - var3 * var5;
      var0[1] = var3 * var4 - var1 * var6;
      var0[2] = var1 * var5 - var2 * var4;
   }

   static final float calcDot(float var0, float var1, float var2, float[] var3) {
      return var0 * var3[0] + var1 * var3[1] + var2 * var3[2];
   }
}
