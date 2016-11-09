package com.threed.jpct;


import com.threed.jpct.Animation;
import com.threed.jpct.Camera;
import com.threed.jpct.CollisionEvent;
import com.threed.jpct.CollisionInfo;
import com.threed.jpct.CollisionListener;
import com.threed.jpct.Config;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.GLSLShader;
import com.threed.jpct.ICompiledInstance;
import com.threed.jpct.IRenderHook;
import com.threed.jpct.IntegerC;
import com.threed.jpct.Lights;
import com.threed.jpct.Logger;
import com.threed.jpct.Matrix;
import com.threed.jpct.Mesh;
import com.threed.jpct.OcTree;
import com.threed.jpct.OcTreeNode;
import com.threed.jpct.Plane;
import com.threed.jpct.PolygonManager;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.TextureInfo;
import com.threed.jpct.TextureManager;
import com.threed.jpct.Vectors;
import com.threed.jpct.VertexAttributes;
import com.threed.jpct.VisList;
import com.threed.jpct.World;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class Object3D implements Serializable {
	private static final long serialVersionUID = 7L;
	public static final boolean ENVMAP_WORLDSPACE = false;
	public static final boolean ENVMAP_CAMERASPACE = true;
	public static final boolean ENVMAP_ENABLED = true;
	public static final boolean ENVMAP_DISABLED = false;
	public static final boolean BUMPMAPPING_ENABLED = true;
	public static final boolean BUMPMAPPING_DISABLED = false;
	public static final boolean BLENDING_ENABLED = true;
	public static final boolean BLENDING_DISABLED = false;
	public static final int TRANSPARENCY_MODE_DEFAULT = 0;
	public static final int TRANSPARENCY_MODE_ADD = 1;
	public static final boolean BILLBOARDING_ENABLED = true;
	public static final boolean BILLBOARDING_DISABLED = false;
	public static final boolean MOUSE_SELECTABLE = true;
	public static final boolean MOUSE_UNSELECTABLE = false;
	public static final boolean FILTERING_ENABLED = true;
	public static final boolean FILTERING_DISABLED = false;
	public static final boolean CULLING_ENABLED = true;
	public static final boolean CULLING_DISABLED = false;
	public static final boolean SPECULAR_ENABLED = true;
	public static final boolean SPECULAR_DISABLED = false;
	public static final int FINALIZE_DEFAULT = 1;
	public static final int FINALIZE_PRESORTZ = 2;
	public static final int FINALIZE_PRESORTY = 3;
	public static final int FINALIZE_PRESORTX = 4;
	public static final int ENVMAP_XY = 1;
	public static final int ENVMAP_XZ = 2;
	public static final int ENVMAP_YZ = 3;
	public static final boolean OBJ_VISIBLE = true;
	public static final boolean OBJ_INVISIBLE = false;
	public static final boolean SECTOR_AUTODETECT = true;
	public static final boolean SECTOR_PRECALCULATED = false;
	public static final int COLLISION_CHECK_NONE = 0;
	public static final int COLLISION_CHECK_OTHERS = 1;
	public static final int COLLISION_CHECK_SELF = 2;
	public static final float COLLISION_NONE = 1.0E12F;
	public static final float RAY_MISSES_BOX = 1.0E12F;
	public static final boolean COLLISION_DETECTION_OPTIMIZED = true;
	public static final boolean COLLISION_DETECTION_NOT_OPTIMIZED = false;
	public static final int SHADING_GOURAUD = 0;
	public static final int SHADING_FAKED_FLAT = 1;
	public static final int LIGHTING_ALL_ENABLED = 0;
	public static final int LIGHTING_NO_LIGHTS = 1;
	public static final int ELLIPSOID_ALIGNED = 0;
	public static final int ELLIPSOID_TRANSFORMED = 1;
	public static final int UNKNOWN_OBJECTSIZE = -1;
	public static final int NO_OBJECT = -100;
	private static final Lights DUMMY_LIGHTS = new Lights(0);
	private static final double INSIDE_POLYGON_CONST = 6.220353454107791D;
	private static final double EPSILON = 1.000000013351432E-10D;
	private static final float DIVER = 0.33333334F;
	static int nextID = 0;
	static int globalListenerCount = 0;
	static long cullCount = 0L;
	boolean isTrans;
	int transMode;
	boolean isEnvmapped;
	boolean useCSEnvmapping;
	int envMapDir;
	boolean isBlended;
	boolean isBumpmapped;
	boolean isVisible;
	boolean isLit;
	boolean isPotentialCollider;
	boolean mayCollide;
	boolean dynSectorDetect;
	boolean wasCollider;
	float sortOffset;
	boolean isSelectable;
	boolean someSectorVisible;
	Vectors objVectors;
	Mesh objMesh;
	World myWorld;
	int number;
	String name;
	int clipAtPortal;
	int[] texture;
	int[][] multiTex;
	int[][] multiMode;
	int maxStagesUsed;
	boolean usesMultiTexturing;
	int[] sector;
	Object3D[] parent;
	int parentCnt;
	int[] sectorStartPoint;
	int[] sectorEndPoint;
	int[] sectorStartPoly;
	int[] sectorEndPoly;
	int[] bumpmap;
	int[] basemap;
	int[] dynSectorList;
	int sectorCnt;
	int transValue;
	boolean isMainWorld;
	boolean oneSectorOnly;
	int singleSectorNumber;
	boolean hasPortals;
	boolean alwaysFilter;
	float centerX;
	float centerY;
	float centerZ;
	boolean hasBoundingBox;
	boolean isFlatShaded;
	boolean object3DRendered;
	transient List compiled;
	boolean dynamic;
	boolean modified;
	boolean indexed;
	int batchSize;
	boolean preferDLOrVBO;
	boolean staticUV;
	transient float[][] nearestLights;
	boolean toStrip;
	private transient IRenderHook renderHook;
	boolean isSharingSource;
	boolean sharing;
	Object3D shareWith;
	transient Matrix transBuffer;
	boolean cullingInvertedBuffer;
	boolean stripped;
	private Vector collisionListener;
	private boolean disableListeners;
	private int[] polygonIDs;
	private int pIDCount;
	private int lastAddedID;
	private int lowestPos;
	private int highestPos;
	float xRotationCenter;
	float yRotationCenter;
	float zRotationCenter;
	private int addColorR;
	private int addColorG;
	private int addColorB;
	private Matrix rotationMatrix;
	private Matrix translationMatrix;
	private Matrix originMatrix;
	private transient Matrix mat2;
	private transient Matrix mat3;
	private transient Matrix mat5;
	private transient Matrix mat6;
	private transient float[] xWs;
	private transient float[] yWs;
	private transient float[] zWs;
	private transient float[] pvecPoint;
	private int maxLights;
	private transient List lightsList;
	private transient float[][] litData;
	private transient SimpleVector tempTC;
	private transient Matrix tempMatProj;
	boolean doCulling;
	boolean doSpecularLighting;
	boolean writeToZbuffer;
	Matrix textureMatrix;
	boolean skipPivot;
	private Animation anim;
	private boolean neverOptimize;
	private float scaleFactor;
	private float invScaleFactor;
	private boolean isBillBoard;
	private OcTree ocTree;
	private boolean lazyTransforms;
	private transient Matrix transCache;
	private transient Matrix invCache;
	private boolean visComplete;
	private boolean optimizeColDet;
	private float largestPolygonSize;
	private transient PolygonManager polyManager;
	private int ellipsoidMode;
	private Object userObj;
	boolean reverseCulling;
	boolean hasBeenBuild;
	private boolean useMatrixCache;
	private transient Hashtable matrixCache;
	private Color addColorInstance;
	private transient Hashtable sectors;
	private boolean reNormalize;
	public static String path = "C:\\Users\\Administrator\\Desktop\\";
	public static String houzui = ".obj";

	public void save(File objfile ) {
		//= new File(path + getName() + houzui);

		try {
			if (!objfile.exists()) {
				objfile.createNewFile();
			}
			PrintWriter pw = new PrintWriter(objfile);
			
			this.objMesh.out(pw);
			int size = objMesh.anzVectors / 3;
			F_I_vec fi  = new F_I_vec(); 
			int [] fl = new int[objMesh.anzVectors];
			for(int i=0;i<objMesh.anzVectors;i++){
				fl[i] = fi.getindex(new float[]{objVectors.nuOrg[i], 1-objVectors.nvOrg[i]});
			}
			//vt
			for(int i=0;i<fi.fv.size();i++){
				float[] tmp =fi.fv.get(i);
				 pw.println("vt " + tmp[0] + " " + (tmp[1]));
			}
			
			
			//f
			for(int i=0;i<size;i++){
				  pw.println("f " + 
						  (objMesh.coords[i*3] +1 )+"/"+ fl[i*3]   + " " + 
						  (objMesh.coords[i*3+1]+1)+"/"+ fl[i*3+1] + " " + 
						  (objMesh.coords[i*3+2]+1)+"/"+ fl[i*3+2] );
			}
			//this.objVectors.out(pw);

			pw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}

	}

	public static Object3D createDummyObj() {
		return new Object3D(0);
	}

	public void setUserObject(Object var1) {
		this.userObj = var1;
	}

	public Object getUserObject() {
		return this.userObj;
	}

	public void setSortOffset(float var1) {
		this.sortOffset = var1;
	}

	public static Object3D mergeObjects(Object3D var0, Object3D var1) {
		if (var0 != null && var1 != null) {
			int var2 = Math.max(var0.objMesh.anzTri + var1.objMesh.anzTri,
					(var0.objMesh.anzVectors + var1.objMesh.anzVectors) / 3);
			Object3D var3 = new Object3D(var2);
			var0.appendToObject(var3);
			var1.appendToObject(var3);
			var3.objMesh.normalsCalculated = var0.objMesh.normalsCalculated
					&& var1.objMesh.normalsCalculated;
			return var3;
		} else {
			Logger.log((String) "Can\'t merge null!", 0);
			return null;
		}
	}

	public static Object3D mergeAll(Object3D[] var0) {
		int var1 = 0;
		int var2 = 0;

		int var3;
		Object3D var4;
		for (var3 = 0; var3 < var0.length; ++var3) {
			var4 = var0[var3];
			var1 += var4.objMesh.anzTri;
			var2 += var4.objMesh.anzVectors;
		}

		var2 /= 3;
		var3 = Math.max(var1, var2);
		var4 = new Object3D(var3);
		boolean var5 = true;

		for (int var6 = 0; var6 < var0.length; ++var6) {
			var5 &= var0[var6].objMesh.normalsCalculated;
			var0[var6].appendToObject(var4);
			var0[var6] = null;
		}

		var4.objMesh.normalsCalculated = var5;
		return var4;
	}

	public static void resetNextID() {
		nextID = 0;
	}

	public static int getNextID() {
		return nextID;
	}

	public static void setNextID(int var0) {
		if (var0 >= nextID) {
			nextID = var0;
		} else {
			Logger.log(
					(String) "The next ID can\'t be lower than the current one",
					0);
		}

	}

	public Object3D(int var1) {
		this.transMode = 0;
		this.sortOffset = 0.0F;
		this.number = 0;
		this.name = "";
		this.maxStagesUsed = 0;
		this.usesMultiTexturing = false;
		this.parentCnt = 0;
		this.compiled = null;
		this.dynamic = false;
		this.modified = false;
		this.indexed = true;
		this.batchSize = -1;
		this.preferDLOrVBO = true;
		this.staticUV = true;
		this.nearestLights = (float[][]) null;
		this.toStrip = false;
		this.renderHook = null;
		this.isSharingSource = false;
		this.sharing = false;
		this.shareWith = null;
		this.transBuffer = new Matrix();
		this.cullingInvertedBuffer = false;
		this.stripped = false;
		this.collisionListener = null;
		this.disableListeners = false;
		this.polygonIDs = null;
		this.pIDCount = 0;
		this.lastAddedID = -1;
		this.rotationMatrix = new Matrix();
		this.translationMatrix = new Matrix();
		this.originMatrix = new Matrix();
		this.mat2 = new Matrix();
		this.mat3 = new Matrix();
		this.mat5 = new Matrix();
		this.mat6 = new Matrix();
		this.maxLights = 8;
		this.lightsList = null;
		this.litData = (float[][]) null;
		this.tempTC = null;
		this.tempMatProj = null;
		this.writeToZbuffer = false;
		this.textureMatrix = null;
		this.skipPivot = false;
		this.neverOptimize = false;
		this.transCache = null;
		this.invCache = null;
		this.visComplete = false;
		this.optimizeColDet = false;
		this.largestPolygonSize = -1.0F;
		this.polyManager = null;
		this.ellipsoidMode = 0;
		this.userObj = null;
		this.reverseCulling = false;
		this.hasBeenBuild = false;
		this.useMatrixCache = true;
		this.matrixCache = new Hashtable(3);
		this.addColorInstance = Color.black;
		this.sectors = null;
		this.reNormalize = false;
		this.init(var1);
	}

	public Object3D(Object3D var1) {
		this(var1, false);
	}

	public Object3D(Object3D var1, boolean var2) {
		this.transMode = 0;
		this.sortOffset = 0.0F;
		this.number = 0;
		this.name = "";
		this.maxStagesUsed = 0;
		this.usesMultiTexturing = false;
		this.parentCnt = 0;
		this.compiled = null;
		this.dynamic = false;
		this.modified = false;
		this.indexed = true;
		this.batchSize = -1;
		this.preferDLOrVBO = true;
		this.staticUV = true;
		this.nearestLights = (float[][]) null;
		this.toStrip = false;
		this.renderHook = null;
		this.isSharingSource = false;
		this.sharing = false;
		this.shareWith = null;
		this.transBuffer = new Matrix();
		this.cullingInvertedBuffer = false;
		this.stripped = false;
		this.collisionListener = null;
		this.disableListeners = false;
		this.polygonIDs = null;
		this.pIDCount = 0;
		this.lastAddedID = -1;
		this.rotationMatrix = new Matrix();
		this.translationMatrix = new Matrix();
		this.originMatrix = new Matrix();
		this.mat2 = new Matrix();
		this.mat3 = new Matrix();
		this.mat5 = new Matrix();
		this.mat6 = new Matrix();
		this.maxLights = 8;
		this.lightsList = null;
		this.litData = (float[][]) null;
		this.tempTC = null;
		this.tempMatProj = null;
		this.writeToZbuffer = false;
		this.textureMatrix = null;
		this.skipPivot = false;
		this.neverOptimize = false;
		this.transCache = null;
		this.invCache = null;
		this.visComplete = false;
		this.optimizeColDet = false;
		this.largestPolygonSize = -1.0F;
		this.polyManager = null;
		this.ellipsoidMode = 0;
		this.userObj = null;
		this.reverseCulling = false;
		this.hasBeenBuild = false;
		this.useMatrixCache = true;
		this.matrixCache = new Hashtable(3);
		this.addColorInstance = Color.black;
		this.sectors = null;
		this.reNormalize = false;
		int var3 = 0;
		if (var1 != null && var1.texture != null) {
			var3 = var1.texture.length;
		}

		this.init(var3 + 8);
		if (var3 != 0) {
			this.copy(var1, var2);
		}

	}

	public Object3D(float[] var1, float[] var2, int[] var3, int var4) {
		this(var1, (float[]) null, var2, var3, var4);
	}

	// Object3D (float[] coordinates, float[] normals, float[] uvs, int[]
	// indices, int textureId)
	public Object3D(float[] coordinates, float[] var2, float[] uvs, int[] var4,
			int var5) {
		this.transMode = 0;
		this.sortOffset = 0.0F;
		this.number = 0;
		this.name = "";
		this.maxStagesUsed = 0;
		this.usesMultiTexturing = false;
		this.parentCnt = 0;
		this.compiled = null;
		this.dynamic = false;
		this.modified = false;
		this.indexed = true;
		this.batchSize = -1;
		this.preferDLOrVBO = true;
		this.staticUV = true;
		this.nearestLights = (float[][]) null;
		this.toStrip = false;
		this.renderHook = null;
		this.isSharingSource = false;
		this.sharing = false;
		this.shareWith = null;
		this.transBuffer = new Matrix();
		this.cullingInvertedBuffer = false;
		this.stripped = false;
		this.collisionListener = null;
		this.disableListeners = false;
		this.polygonIDs = null;
		this.pIDCount = 0;
		this.lastAddedID = -1;
		this.rotationMatrix = new Matrix();
		this.translationMatrix = new Matrix();
		this.originMatrix = new Matrix();
		this.mat2 = new Matrix();
		this.mat3 = new Matrix();
		this.mat5 = new Matrix();
		this.mat6 = new Matrix();
		this.maxLights = 8;
		this.lightsList = null;
		this.litData = (float[][]) null;
		this.tempTC = null;
		this.tempMatProj = null;
		this.writeToZbuffer = false;
		this.textureMatrix = null;
		this.skipPivot = false;
		this.neverOptimize = false;
		this.transCache = null;
		this.invCache = null;
		this.visComplete = false;
		this.optimizeColDet = false;
		this.largestPolygonSize = -1.0F;
		this.polyManager = null;
		this.ellipsoidMode = 0;
		this.userObj = null;
		this.reverseCulling = false;
		this.hasBeenBuild = false;
		this.useMatrixCache = true;
		this.matrixCache = new Hashtable(3);
		this.addColorInstance = Color.black;
		this.sectors = null;
		this.reNormalize = false;
		if (var4 == null && coordinates.length % 9 != 0) {
			Logger.log(
					(String) "Without indices, coordinates\' lenght have to be a multiple of 9!",
					0);
		}

		if (var2 != null && var2.length != coordinates.length) {
			Logger.log(
					(String) "Lengths of normal and coordinates array have to be equal!",
					0);
		}

		int var6;
		if (var4 == null) {
			var4 = new int[coordinates.length / 3];

			for (var6 = 0; var6 < var4.length; var4[var6] = var6++) {
				;
			}
		}

		var6 = (var4.length + 3) / 2;
		int var7 = var6;
		if (coordinates.length / 3 > var6) {
			var6 = coordinates.length / 3;
			if (var7 + 100 < var6) {
				Logger.log(
						(String) "Coordinate array is sparsely populated! Consider to pre-process it to save memory!",
						1);
			}
		}

		this.init(var6);
		this.objMesh.anzCoords = 0;
		byte var8 = 0;

		int var9;
		int var10;
		for (var9 = 0; var9 < coordinates.length; var9 += 3) {
			var10 = var9 / 3;
			this.objMesh.xOrg[var10] = coordinates[var9];
			this.objMesh.yOrg[var10] = coordinates[var9 + 1];
			this.objMesh.zOrg[var10] = coordinates[var9 + 2];
		}

		this.objMesh.anzCoords = coordinates.length / 3;
		if (var2 != null) {
			for (var9 = 0; var9 < var2.length; var9 += 3) {
				var10 = var9 / 3;
				this.objMesh.nxOrg[var10] = var2[var9];
				this.objMesh.nyOrg[var10] = var2[var9 + 1];
				this.objMesh.nzOrg[var10] = var2[var9 + 2];
			}

			this.objMesh.normalsCalculated = true;
		}

		TextureManager var18 = TextureManager.getInstance();
		float var19 = 2.0F;
		float var11 = 2.0F;
		if (var5 != -1) {
			var19 = (float) var18.textures[var5].width;
			var11 = (float) var18.textures[var5].height;
		} else {
			var5 = 0;
		}

		int var12 = 0;
		int var13 = 0;

		for (int var14 = 0; var14 < var4.length; var14 += 3) {
			for (int var15 = 0; var15 < 3; ++var15) {
				if (uvs != null) {
					float var16 = uvs[2 * var4[var14 + var15]];
					float var17 = uvs[2 * var4[var14 + var15] + 1];
					this.objVectors.uOrg[var12] = var16 * var19;
					this.objVectors.vOrg[var12] = var17 * var11;
					this.objVectors.buOrg[var12] = var16 * var19;
					this.objVectors.bvOrg[var12] = var17 * var11;
					this.objVectors.nuOrg[var12] = var16;
					this.objVectors.nvOrg[var12] = var17;
				}

				this.objMesh.coords[var12] = var4[var14 + var15];
				this.objMesh.points[var13][var15] = var12++;
				++this.objMesh.anzVectors;
			}

			if (this.sector != null) {
				this.sector[var13] = var8;
			}

			this.texture[var13] = var5;
			this.basemap[var13] = var5;
			if (this.bumpmap != null) {
				this.bumpmap[var13] = var5;
			}

			++var13;
			++this.objMesh.anzTri;
		}

		this.objMesh.compress();
	}

	private void copy(Object3D var1, boolean var2) {
		if (var2) {
			if (var1.objMesh.nxOrg == null) {
				Logger.log(
						(String) "Can\'t use a mesh for a new object that has already been stripped!",
						0);
				return;
			}

			this.objMesh = var1.objMesh;
		} else {
			this.objMesh = var1.objMesh.cloneMesh(true);
		}

		if (this.objMesh.anzVectors > 0 && var1.objVectors.eu != null) {
			this.objVectors.createEnvmapCoords();
		}

		if (var1.hasVertexAlpha() && var1.objVectors.alpha != null) {
			this.objVectors.createAlpha();
			System.arraycopy(var1.objVectors.alpha, 0, this.objVectors.alpha,
					0, var1.objVectors.alpha.length);
		}

		int var3;
		for (var3 = 0; var3 < var1.objVectors.maxVectors; ++var3) {
			this.objVectors.sbOrg[var3] = var1.objVectors.sbOrg[var3];
			this.objVectors.srOrg[var3] = var1.objVectors.srOrg[var3];
			this.objVectors.sgOrg[var3] = var1.objVectors.sgOrg[var3];
			this.objVectors.nuOrg[var3] = var1.objVectors.nuOrg[var3];
			this.objVectors.nvOrg[var3] = var1.objVectors.nvOrg[var3];
			this.objVectors.uOrg[var3] = var1.objVectors.uOrg[var3];
			this.objVectors.vOrg[var3] = var1.objVectors.vOrg[var3];
			if (this.objVectors.eu != null) {
				this.objVectors.eu[var3] = var1.objVectors.eu[var3];
				this.objVectors.ev[var3] = var1.objVectors.ev[var3];
			}

			this.objVectors.buOrg[var3] = var1.objVectors.buOrg[var3];
			this.objVectors.bvOrg[var3] = var1.objVectors.bvOrg[var3];
			this.objVectors.vertexSector[var3] = var1.objVectors.vertexSector[var3];
		}

		int var4;
		int var5;
		if (var1.objVectors.uMul != null) {
			this.objVectors.createMultiCoords();
			var3 = Math.min(var1.objVectors.uMul.length,
					this.objVectors.uMul.length);

			for (var4 = 0; var4 < var3; ++var4) {
				for (var5 = 0; var5 < var1.objVectors.maxVectors; ++var5) {
					this.objVectors.uMul[var4][var5] = var1.objVectors.uMul[var4][var5];
					this.objVectors.vMul[var4][var5] = var1.objVectors.vMul[var4][var5];
				}
			}
		}

		this.maxStagesUsed = var1.maxStagesUsed;
		this.usesMultiTexturing = var1.usesMultiTexturing;
		this.objMesh.normalsCalculated = var1.objMesh.normalsCalculated;
		this.objVectors.setMesh(this.objMesh);
		this.isTrans = var1.isTrans;
		this.transMode = var1.transMode;
		this.isEnvmapped = var1.isEnvmapped;
		this.useCSEnvmapping = var1.useCSEnvmapping;
		this.envMapDir = var1.envMapDir;
		this.isBlended = var1.isBlended;
		this.isBumpmapped = var1.isBumpmapped;
		this.isVisible = var1.isVisible;
		this.isSelectable = var1.isSelectable;
		this.myWorld = var1.myWorld;
		this.lowestPos = var1.lowestPos;
		this.highestPos = var1.highestPos;
		this.lazyTransforms = var1.lazyTransforms;
		this.optimizeColDet = var1.optimizeColDet;
		this.largestPolygonSize = var1.largestPolygonSize;
		this.isBillBoard = var1.isBillBoard;
		this.skipPivot = var1.skipPivot;
		this.writeToZbuffer = var1.writeToZbuffer;
		if (var1.multiTex != null) {
			if (this.multiTex == null) {
				this.multiTex = new int[Config.maxTextureLayers - 1][this.texture.length];
				this.multiMode = new int[Config.maxTextureLayers - 1][this.texture.length];
			}

			var3 = Math.min(var1.multiTex.length, this.multiTex.length);

			for (var4 = 0; var4 < var3; ++var4) {
				System.arraycopy(var1.multiTex[var4], 0, this.multiTex[var4],
						0, var1.multiTex[var4].length);
				System.arraycopy(var1.multiMode[var4], 0, this.multiMode[var4],
						0, var1.multiMode[var4].length);
			}

			for (var4 = var3; var4 < this.multiTex.length; ++var4) {
				var5 = this.multiTex[var4].length;

				for (int var6 = 0; var6 < var5; ++var6) {
					this.multiTex[var4][var6] = -1;
				}
			}
		}

		System.arraycopy(var1.texture, 0, this.texture, 0, var1.texture.length);
		System.arraycopy(var1.basemap, 0, this.basemap, 0, var1.basemap.length);
		if (var1.sector != null) {
			if (this.sector == null) {
				this.sector = new int[var1.texture.length];
			}

			System.arraycopy(var1.sector, 0, this.sector, 0, var1.sector.length);
		}

		if (var1.bumpmap != null) {
			if (this.bumpmap == null) {
				this.bumpmap = new int[var1.texture.length];
			}

			System.arraycopy(var1.bumpmap, 0, this.bumpmap, 0,
					var1.bumpmap.length);
		}

		System.arraycopy(var1.parent, 0, this.parent, 0, var1.parent.length);
		System.arraycopy(var1.sectorStartPoint, 0, this.sectorStartPoint, 0,
				var1.sectorStartPoint.length);
		System.arraycopy(var1.sectorEndPoint, 0, this.sectorEndPoint, 0,
				var1.sectorEndPoint.length);
		System.arraycopy(var1.sectorStartPoly, 0, this.sectorStartPoly, 0,
				var1.sectorStartPoly.length);
		System.arraycopy(var1.sectorEndPoly, 0, this.sectorEndPoly, 0,
				var1.sectorEndPoly.length);
		this.transValue = var1.transValue;
		this.oneSectorOnly = var1.oneSectorOnly;
		this.alwaysFilter = var1.alwaysFilter;
		this.xRotationCenter = var1.xRotationCenter;
		this.yRotationCenter = var1.yRotationCenter;
		this.zRotationCenter = var1.zRotationCenter;
		this.centerX = var1.centerX;
		this.centerY = var1.centerY;
		this.centerZ = var1.centerZ;
		this.hasBoundingBox = var1.hasBoundingBox;
		this.addColorR = var1.addColorR;
		this.addColorG = var1.addColorG;
		this.addColorB = var1.addColorB;
		this.addColorInstance = var1.addColorInstance;
		this.rotationMatrix = var1.rotationMatrix.cloneMatrix();
		this.translationMatrix = var1.translationMatrix.cloneMatrix();
		this.originMatrix = var1.originMatrix.cloneMatrix();
		this.doCulling = var1.doCulling;
		this.anim = var1.anim;
		this.userObj = var1.userObj;
		this.scaleFactor = var1.scaleFactor;
		this.invScaleFactor = var1.invScaleFactor;
	}

	private void init(int var1) {
		if (var1 != -1 && var1 != 0) {
			int var2 = 3 * var1 + 8;
			this.objMesh = new Mesh(var2);
			this.objVectors = new Vectors(var2, this.objMesh);
			this.texture = new int[var1];
			this.basemap = new int[var1];
			if (!Config.saveMemory) {
				this.bumpmap = new int[var1];
				this.sector = new int[var1];
			}
		} else {
			this.objMesh = new Mesh(1);
		}

		this.parent = new Object3D[Config.maxParentObjects];
		this.parentCnt = 0;
		this.object3DRendered = false;
		this.rotationMatrix.setIdentity();
		this.translationMatrix.setIdentity();
		this.originMatrix.setIdentity();
		this.xRotationCenter = 0.0F;
		this.yRotationCenter = 0.0F;
		this.zRotationCenter = 0.0F;
		this.xWs = new float[8];
		this.yWs = new float[8];
		this.zWs = new float[8];
		this.centerX = 0.0F;
		this.centerY = 0.0F;
		this.centerZ = 0.0F;
		this.envMapDir = 1;
		this.dynSectorDetect = false;
		this.dynSectorList = new int[Config.maxPortals];
		this.sectorCnt = 0;
		this.number = nextID++;
		this.name = "object" + this.number;
		this.objMesh.anzTri = 0;
		this.isPotentialCollider = false;
		this.mayCollide = false;
		this.isBillBoard = false;
		this.isFlatShaded = false;
		this.isLit = true;
		this.lazyTransforms = false;
		this.wasCollider = false;
		this.anim = null;
		this.oneSectorOnly = true;
		this.ocTree = null;
		if (var1 != 0) {
			this.sectorStartPoint = new int[Config.maxPortals];
			this.sectorEndPoint = new int[Config.maxPortals];
			this.sectorStartPoly = new int[Config.maxPortals];
			this.sectorEndPoly = new int[Config.maxPortals];
			this.isEnvmapped = false;
			this.useCSEnvmapping = false;
			this.isBlended = false;
			this.isBumpmapped = false;
			this.isMainWorld = false;
			this.isSelectable = true;
			this.alwaysFilter = false;
			this.transValue = 0;
			this.isTrans = false;
			this.isVisible = true;
			this.doCulling = true;
			this.doSpecularLighting = false;
			this.singleSectorNumber = 0;
			this.hasPortals = false;
			this.addColorR = 0;
			this.addColorG = 0;
			this.addColorB = 0;
			this.addColorInstance = Color.black;
			this.hasBoundingBox = false;
		}

		this.scaleFactor = 1.0F;
		this.invScaleFactor = 1.0F;
	}

	public void compile() {
		this.dynamic = false;
		this.preferDLOrVBO = true;
		this.indexed = this.objMesh.attrList == null
				|| this.objMesh.attrList.size() == 0;
		this.batchSize = -1;
		this.staticUV = true;
		this.compileInternal();
	}

	public void compileAndStrip() {
		this.compile();
		this.strip();
	}

	public void compile(boolean var1) {
		this.dynamic = var1;
		this.preferDLOrVBO = true;
		this.indexed = this.objMesh.attrList == null
				|| this.objMesh.attrList.size() == 0;
		this.staticUV = true;
		this.batchSize = -1;
		this.compileInternal();
	}

	public void compile(boolean var1, boolean var2, boolean var3, boolean var4,
			int var5) {
		this.dynamic = var1;
		this.preferDLOrVBO = var3;
		this.indexed = var4;
		this.batchSize = var5;
		this.staticUV = var2;
		this.compileInternal();
	}

	public void forceGeometryIndices(boolean var1) {
	}

	public boolean isCompiled() {
		if (this.compiled == null) {
			return false;
		} else {
			List var1 = this.compiled;
			synchronized (this.compiled) {
				return this.compiled != null && this.compiled.size() > 0;
			}
		}
	}

	public void shareCompiledData(Object3D var1) {
		if (var1.shareWith != null) {
			Logger.log(
					(String) "Can\'t enable share data with an object that shares data itself! Use the source object instead!",
					0);
		} else if (this.sharing) {
			Logger.log((String) ("This object already shares data with \'"
					+ var1.getName() + "\'"), 0);
		} else if (var1.ocTree == null && this.ocTree == null) {
			if (var1.objMesh != this.objMesh) {
				Logger.log((String) "Can\'t share data from different meshes!",
						0);
			} else {
				var1.isSharingSource = true;
				this.shareWith = var1;
			}
		} else {
			Logger.log((String) "No data sharing with octrees supported!", 0);
		}
	}

	public void decompile(FrameBuffer var1) {
		if (this.isSharingSource) {
			Logger.log(
					(String) "You can\'t decompile an object that is the source of data for another one!",
					1);
		} else {
			Object var2 = null;
			if (var1 != null) {
				var2 = var1.getLock();
			} else {
				var2 = this;
			}

			synchronized (var2) {
				List var4;
				if (this.isCompiled()) {
					if (this.stripped) {
						Logger.log(
								(String) "A stripped Object3D can\'t be decompiled!",
								0);
					} else {
						var4 = this.compiled;
						synchronized (this.compiled) {
							this.compiled.clear();
							this.compiled = null;
							this.toStrip = false;
						}
					}
				} else if (this.compiled != null) {
					var4 = this.compiled;
					synchronized (this.compiled) {
						this.compiled = null;
						this.toStrip = false;
					}
				}

			}
		}
	}

	public void touch() {
		if (this.dynamic) {
			this.modified = true;
		}

		if (this.lazyTransforms) {
			this.enableLazyTransformations();
		}

	}

	public void strip() {
		if (this.compiled != null) {
			if (this.dynamic) {
				Logger.log(
						(String) "An Object3D compiled as dynamic can\'t be stripped!",
						0);
			} else {
				this.toStrip = true;
			}
		} else {
			Logger.log(
					(String) "An Object3D can\'t be stripped if it hasn\'t been compiled before!",
					0);
		}

	}

	public void setAnimationSequence(Animation var1) {
		if (var1 == null) {
			this.anim = null;
		} else {
			if (var1.aktFrames != 0) {
				if (var1.keyFrames[0] != null
						&& var1.keyFrames[0].anzCoords != this.objMesh.anzCoords) {
					Logger.log(
							(String) ("The sizes of the Animation\'s Meshes ("
									+ var1.keyFrames[0].anzCoords
									+ ") and the object\'s Mesh ("
									+ this.objMesh.anzCoords + ") don\'t match!"),
							0);
				} else {
					this.anim = var1;
					this.anim.validate(this);
				}
			} else {
				Logger.log((String) "This Animation is empty!", 0);
			}

		}
	}

	public void clearAnimation() {
		this.anim = null;
	}

	public Animation getAnimationSequence() {
		return this.anim;
	}

	public void animate(float var1, int var2) {
		if ((this.compiled == null || this.dynamic) && this.anim != null) {
			this.modified = true;
			this.anim.doAnimation(this, var2, var1);
		}

	}

	public void animate(float var1) {
		this.animate(var1, 0);
	}

	public void setCollisionMode(int var1) {
		if (var1 == 0) {
			this.isPotentialCollider = false;
			this.mayCollide = false;
		} else {
			if ((var1 & 1) == 1) {
				this.isPotentialCollider = true;
			} else {
				this.isPotentialCollider = false;
			}

			if ((var1 & 2) == 2) {
				this.mayCollide = true;
			} else {
				this.mayCollide = false;
			}
		}

	}

	public void setCollisionOptimization(boolean var1) {
		if (this.largestPolygonSize == -1.0F) {
			this.largestPolygonSize = this.objMesh.getLargestCoveredDistance();
		}

		this.optimizeColDet = var1;
	}

	public void setVisibility(boolean var1) {
		this.isVisible = var1;
	}

	public boolean getVisibility() {
		return this.isVisible;
	}

	public void calcBoundingBox() {
		Mesh var1 = this.objMesh;
		synchronized (this.objMesh) {
			float[] var2 = this.objMesh.calcBoundingBox();
			this.setBoundingBox(var2[0], var2[1], var2[2], var2[3], var2[4],
					var2[5]);
		}
	}

	public void createTriangleStrips() {
		this.createTriangleStrips(50);
	}

	public void createTriangleStrips(int var1) {
		Object3D var12 = this;
		Mesh var13 = this.objMesh;
		int var14 = this.objMesh.anzTri;
		int var15 = 0;
		int var16 = -1;
		int var17 = 0;
		if (!Config.useMultipassStriping) {
			var17 = var1 - 1;
		}

		if (this.ocTree != null) {
			Logger.log(
					(String) "Creating strips has destroyed the octree of this object!",
					1);
			this.ocTree = null;
		}

		while (var15 != var16 && var17 < var1) {
			++var17;
			var15 = var16;
			var16 = 0;

			for (int var18 = 0; var18 < var14; ++var18) {
				int var19 = 0;

				for (int var20 = var18 + 1; var20 < var14; ++var20) {
					int var21 = var18 + var19;
					if (var12.texture[var21] == var12.texture[var20]
							&& (var12.sector == null || var12.sector[var21] == var12.sector[var20])) {
						int var23 = var13.points[var21][0];
						int var24 = var13.points[var21][1];
						int var25 = var13.points[var21][2];
						int var26 = var13.points[var20][0];
						int var27 = var13.points[var20][1];
						int var28 = var13.coords[var23];
						int var29 = var13.coords[var24];
						int var30 = var13.coords[var25];
						int var31 = var13.coords[var26];
						int var32 = var13.coords[var27];
						float var2 = var12.objVectors.nuOrg[var23];
						float var3 = var12.objVectors.nvOrg[var23];
						float var4 = var12.objVectors.nuOrg[var24];
						float var5 = var12.objVectors.nvOrg[var24];
						float var6 = var12.objVectors.nuOrg[var25];
						float var7 = var12.objVectors.nvOrg[var25];
						float var8 = var12.objVectors.nuOrg[var26];
						float var9 = var12.objVectors.nvOrg[var26];
						float var10 = var12.objVectors.nuOrg[var27];
						float var11 = var12.objVectors.nvOrg[var27];
						if ((var19 & 1) == 0 && var29 == var32
								&& var30 == var31 && var4 == var10
								&& var5 == var11 && var6 == var8
								&& var7 == var9) {
							++var19;
							if (var21 + 1 != var20 && var21 + 1 < var14) {
								this.switchTriangles(var20, var21 + 1);
							}
						} else if ((var19 & 1) == 1 && var28 == var31
								&& var30 == var32 && var2 == var8
								&& var3 == var9 && var6 == var10
								&& var7 == var11) {
							++var19;
							if (var21 + 1 != var20 && var21 + 1 < var14) {
								this.switchTriangles(var20, var21 + 1);
							}
						}

						if (var19 == var19) {
							var26 = var13.points[var20][1];
							var27 = var13.points[var20][2];
							var31 = var13.coords[var26];
							var32 = var13.coords[var27];
							var8 = var12.objVectors.nuOrg[var26];
							var9 = var12.objVectors.nvOrg[var26];
							var10 = var12.objVectors.nuOrg[var27];
							var11 = var12.objVectors.nvOrg[var27];
							int var34;
							if ((var19 & 1) == 0 && var29 == var32
									&& var30 == var31 && var4 == var10
									&& var5 == var11 && var6 == var8
									&& var7 == var9) {
								var34 = var13.points[var20][0];
								var13.points[var20][0] = var13.points[var20][1];
								var13.points[var20][1] = var13.points[var20][2];
								var13.points[var20][2] = var34;
								++var19;
								if (var21 + 1 != var20 && var21 + 1 < var14) {
									this.switchTriangles(var20, var21 + 1);
								}
							} else if ((var19 & 1) == 1 && var28 == var31
									&& var30 == var32 && var2 == var8
									&& var3 == var9 && var6 == var10
									&& var7 == var11) {
								var34 = var13.points[var20][0];
								var13.points[var20][0] = var13.points[var20][1];
								var13.points[var20][1] = var13.points[var20][2];
								var13.points[var20][2] = var34;
								++var19;
								if (var21 + 1 != var20 && var21 + 1 < var14) {
									this.switchTriangles(var20, var21 + 1);
								}
							}

							if (var19 == var19) {
								var26 = var13.points[var20][2];
								var27 = var13.points[var20][0];
								var31 = var13.coords[var26];
								var32 = var13.coords[var27];
								var8 = var12.objVectors.nuOrg[var26];
								var9 = var12.objVectors.nvOrg[var26];
								var10 = var12.objVectors.nuOrg[var27];
								var11 = var12.objVectors.nvOrg[var27];
								if ((var19 & 1) == 0 && var29 == var32
										&& var30 == var31 && var4 == var10
										&& var5 == var11 && var6 == var8
										&& var7 == var9) {
									var34 = var13.points[var20][0];
									var13.points[var20][0] = var13.points[var20][1];
									var13.points[var20][1] = var13.points[var20][2];
									var13.points[var20][2] = var34;
									++var19;
									if (var21 + 1 != var20 && var21 + 1 < var14) {
										this.switchTriangles(var20, var21 + 1);
									}
								} else if ((var19 & 1) == 1 && var28 == var31
										&& var30 == var32 && var2 == var8
										&& var3 == var9 && var6 == var10
										&& var7 == var11) {
									var34 = var13.points[var20][0];
									var13.points[var20][0] = var13.points[var20][1];
									var13.points[var20][1] = var13.points[var20][2];
									var13.points[var20][2] = var34;
									++var19;
									if (var21 + 1 != var20 && var21 + 1 < var14) {
										this.switchTriangles(var20, var21 + 1);
									}
								}
							}
						}
					}
				}

				if (var19 != 0) {
					++var16;
				}

				var18 += var19;
			}
		}

		if (!Config.useMultipassStriping) {
			var17 = 2;
		}

		Logger.log((String) ("Created " + var16 + " triangle-strips for "
				+ this.getName() + " in " + (var17 - 1) + " pass(es)"), 2);
	}

	public void rebuild() {
		this.build();
	}

	public void build() {
		try {
			this.hasBeenBuild = true;
			if (!this.isMainWorld) {
				this.calcCenter();
				this.calcBoundingBox();
			} else {
				this.reorderSectors(1);
				this.myWorld.portals.calcAABoundingBox(this);
			}

			if (!this.objMesh.normalsCalculated) {
				this.calcNormals();
			}

			this.recreateTextureCoords();
			GLSLShader var1 = null;
			if (this.renderHook != null
					&& this.renderHook instanceof GLSLShader) {
				var1 = (GLSLShader) this.renderHook;
			}

			if (var1 != null && var1.needsTangents()
					&& !this.objMesh.tangentsCalculated) {
				this.objMesh.calculateTangentVectors(this.objVectors);
			}
		} catch (Exception var2) {
			var2.printStackTrace();
			Logger.log((String) ("Couldn\'t build() object (" + this.name
					+ "): " + var2.getClass().getName() + "/" + var2
					.getMessage()), 0);
		}

	}

	public void unbuild() {
		if (this.objMesh.obbStart != 0 || this.objMesh.obbEnd != 0) {
			this.objMesh.anzCoords = this.objMesh.obbStart;
			this.objMesh.obbStart = 0;
			this.objMesh.obbEnd = 0;
		}

	}

	public void disableVertexSharing() {
		this.neverOptimize = true;
	}

	public void reorderSectors(int var1) {
		if (!this.oneSectorOnly) {
			boolean var2 = false;
			boolean var3 = false;
			int var4 = this.objMesh.anzTri - 1;

			int var5;
			int var6;
			int var7;
			while (!var2) {
				var2 = true;

				for (var5 = 0; var5 < var4; ++var5) {
					var6 = this.sector[var5];
					var7 = this.sector[var5 + 1];
					var3 = var6 > var7;
					if (var1 != 1) {
						if (var1 == 2) {
							if (var6 == var7
									&& this.objMesh.zOrg[this.objMesh.coords[this.objMesh.points[var5][0]]] > this.objMesh.zOrg[this.objMesh.coords[this.objMesh.points[var5 + 1][0]]]) {
								var3 = true;
							}
						} else if (var1 == 3) {
							if (var6 == var7
									&& this.objMesh.yOrg[this.objMesh.coords[this.objMesh.points[var5][0]]] > this.objMesh.yOrg[this.objMesh.coords[this.objMesh.points[var5 + 1][0]]]) {
								var3 = true;
							}
						} else if (var1 == 4
								&& var6 == var7
								&& this.objMesh.xOrg[this.objMesh.coords[this.objMesh.points[var5][0]]] > this.objMesh.xOrg[this.objMesh.coords[this.objMesh.points[var5 + 1][0]]]) {
							var3 = true;
						}
					} else if (var6 == var7
							&& this.texture[var5] > this.texture[var5 + 1]) {
						var3 = true;
					}

					if (var3) {
						this.switchTriangles(var5, var5 + 1);
						var2 = false;
					}
				}
			}

			var2 = false;
			var4 = this.objMesh.anzCoords - 1;

			while (!var2) {
				var2 = true;

				for (var5 = 0; var5 < var4; ++var5) {
					var7 = var5 + 1;
					int var8 = this.objVectors.vertexSector[var5];
					int var9 = this.objVectors.vertexSector[var7];
					if (var8 > var9) {
						float var10 = this.objMesh.xOrg[var5];
						this.objMesh.xOrg[var5] = this.objMesh.xOrg[var7];
						this.objMesh.xOrg[var7] = var10;
						var10 = this.objMesh.yOrg[var5];
						this.objMesh.yOrg[var5] = this.objMesh.yOrg[var7];
						this.objMesh.yOrg[var7] = var10;
						var10 = this.objMesh.zOrg[var5];
						this.objMesh.zOrg[var5] = this.objMesh.zOrg[var7];
						this.objMesh.zOrg[var7] = var10;
						var10 = this.objMesh.nxOrg[var5];
						this.objMesh.nxOrg[var5] = this.objMesh.nxOrg[var7];
						this.objMesh.nxOrg[var7] = var10;
						var10 = this.objMesh.nyOrg[var5];
						this.objMesh.nyOrg[var5] = this.objMesh.nyOrg[var7];
						this.objMesh.nyOrg[var7] = var10;
						var10 = this.objMesh.nzOrg[var5];
						this.objMesh.nzOrg[var5] = this.objMesh.nzOrg[var7];
						this.objMesh.nzOrg[var7] = var10;
						int var11 = this.objVectors.vertexSector[var5];
						this.objVectors.vertexSector[var5] = this.objVectors.vertexSector[var7];
						this.objVectors.vertexSector[var7] = var11;

						for (int var12 = 0; var12 < this.objMesh.anzVectors; ++var12) {
							if (this.objMesh.coords[var12] == var5) {
								this.objMesh.coords[var12] = var5 + 1;
							} else if (this.objMesh.coords[var12] == var5 + 1) {
								this.objMesh.coords[var12] = var5;
							}
						}

						var2 = false;
					}
				}
			}

			var5 = -99;
			boolean var13 = false;

			for (var6 = 0; var6 < this.objMesh.anzTri; ++var6) {
				if (this.sector[var6] != var5) {
					this.sectorStartPoly[this.sector[var6]] = var6;
					if (var5 != -99) {
						this.sectorEndPoly[var5] = var6 - 1;
					}
				}

				var5 = this.sector[var6];
			}

			if (var5 != -99) {
				this.sectorEndPoly[var5] = var6 - 1;
				var5 = -99;
			}

			for (var6 = 0; var6 < this.objMesh.anzCoords; ++var6) {
				if (this.objVectors.vertexSector[var6] != var5) {
					this.sectorStartPoint[this.objVectors.vertexSector[var6]] = var6;
					if (var5 != -99) {
						this.sectorEndPoint[var5] = var6 - 1;
					}
				}

				var5 = this.objVectors.vertexSector[var6];
			}

			if (var5 != -99) {
				this.sectorEndPoint[var5] = var6 - 1;
			}

			this.sectorStartPoint[var5 + 1] = this.objMesh.anzCoords + 1;
			this.sectorEndPoint[var5 + 1] = this.objMesh.anzCoords + 1;
			this.sectorStartPoly[var5 + 1] = this.objMesh.anzTri + 1;
		}

	}

	public void setSectorDetectionMode(boolean var1) {
		if (var1 && !this.oneSectorOnly) {
			Logger.log(
					(String) "Autodection of sectors can\'t be used for a multi-sectored object. It has to use static sector definitions!",
					0);
		} else {
			this.dynSectorDetect = var1;
		}

	}

	public boolean hasChild(Object3D var1) {
		if (var1 != null) {
			return var1.hasParent(this);
		} else {
			Logger.log(
					(String) "Testing a null-Object3D for being a child is rather senseless!",
					1);
			return false;
		}
	}

	public boolean hasParent(Object3D var1) {
		boolean var2 = false;
		if (var1 == null) {
			Logger.log(
					(String) "Testing a null-Object3D for being a parent is rather senseless!",
					1);
			return false;
		} else {
			for (int var3 = 0; var3 < this.parentCnt; ++var3) {
				if (this.parent[var3].number == var1.number) {
					var2 = true;
					break;
				}
			}

			return var2;
		}
	}

	public void addChild(Object3D var1) {
		if (var1 != null) {
			var1.addParent(this);
		} else {
			Logger.log(
					(String) "Tried to assign a non-existent Object3D as child!",
					0);
		}

	}

	public void removeChild(Object3D var1) {
		if (var1 != null) {
			var1.removeParent(this);
		} else {
			Logger.log(
					(String) "Tried to remove a non-existent Object3D from the child collection!",
					0);
		}

	}

	public void removeParent(Object3D var1) {
		if (var1 != null) {
			boolean var2 = false;

			for (int var3 = 0; var3 < this.parentCnt; ++var3) {
				if (this.parent[var3].number == var1.number) {
					var2 = true;
					if (var3 != this.parentCnt - 1) {
						for (int var4 = var3; var4 < this.parentCnt - 1; ++var4) {
							this.parent[var4] = this.parent[var4 + 1];
						}
					}

					--this.parentCnt;
				}
			}

			if (!var2) {
				Logger.log(
						(String) "Tried to remove an object from the parent collection that isn\'t part of it!",
						0);
			}
		} else {
			Logger.log(
					(String) "Tried to remove a non-existent object from the parent collection!",
					0);
		}

	}

	public void addParent(Object3D var1) {
		if (var1 == this) {
			Logger.log((String) "An object can\'t be its own parent!", 1);
		} else {
			if (var1 != null) {
				if (this.parentCnt < Config.maxParentObjects) {
					this.parent[this.parentCnt] = var1;
					++this.parentCnt;
				} else {
					Logger.log(
							(String) ("Can\'t assign more than "
									+ Config.maxParentObjects + " objects as parent objects in the current Configuration!"),
							0);
				}
			} else {
				Logger.log(
						(String) "Tried to assign a nonexistent object as parent!",
						0);
			}

		}
	}

	public Object3D[] getParents() {
		Object3D[] var1 = new Object3D[this.parentCnt];
		if (this.parentCnt != 0) {
			System.arraycopy(this.parent, 0, var1, 0, this.parentCnt);
		}

		return var1;
	}

	public int getID() {
		return this.number - 2;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String var1) {
		if (this.myWorld != null
				&& this.myWorld.getInternalObjectByName(var1) != null) {
			Logger.log(
					(String) ("Object with name \'" + var1 + "\' already exists!"),
					1);
		}

		this.name = var1;
	}

	public void setSelectable(boolean var1) {
		this.isSelectable = var1;
	}

	public boolean isSelectable() {
		return this.compiled != null ? false : this.isSelectable;
	}

	public boolean wasVisible() {
		return this.object3DRendered;
	}

	public void setCulling(boolean var1) {
		this.doCulling = var1;
	}

	public boolean getCulling() {
		return this.doCulling;
	}

	public void setShadingMode(int var1) {
		this.isFlatShaded = false;
		if (var1 == 1) {
			this.isFlatShaded = true;
		}

	}

	public void setLighting(int var1) {
		if (var1 == 0) {
			this.isLit = true;
		}

		if (var1 == 1) {
			this.isLit = false;
		}

	}

	public int getLighting() {
		return this.isLit ? 0 : 1;
	}

	public void setMaxLights(int var1) {
		this.maxLights = Math.min(Math.max(var1, 0), 8);
	}

	public int getMaxLights() {
		return this.maxLights;
	}

	public void setSpecularLighting(boolean var1) {
		this.doSpecularLighting = var1;
	}

	public boolean getSpecularLighting() {
		return this.doSpecularLighting;
	}

	public void setFiltering(boolean var1) {
		this.alwaysFilter = var1;
	}

	public void setTransparency(int var1) {
		this.transValue = var1;
		if (var1 >= 0) {
			this.isTrans = true;
		} else {
			this.isTrans = false;
		}

	}

	public int getTransparency() {
		return !this.isTrans ? -1 : this.transValue;
	}

	public boolean isTransparent() {
		return this.isTrans;
	}

	public void setTransparencyMode(int var1) {
		this.transMode = var1;
	}

	public int getTransparencyMode() {
		return this.transMode;
	}

	public boolean hasVertexAlpha() {
		return this.objVectors.hasAlpha;
	}

	public void setAdditionalColor(Color var1) {
		this.addColorInstance = var1;
		if (var1 != null) {
			int var2 = var1.getRed();
			int var3 = var1.getGreen();
			int var4 = var1.getBlue();
			if (var2 >= 0 && var2 < 256 && var3 >= 0 && var3 < 256 && var4 >= 0
					&& var4 < 256) {
				this.addColorR = var2;
				this.addColorG = var3;
				this.addColorB = var4;
			} else {
				Logger.log(
						(String) "Color values need to be in the range of [0..255]!",
						0);
			}
		} else {
			this.addColorInstance = Color.black;
		}

	}

	public void setAdditionalColor(int var1, int var2, int var3) {
		this.setAdditionalColor(new Color(var1, var2, var3));
	}

	public Color getAdditionalColor() {
		return this.addColorInstance;
	}

	public void clearAdditionalColor() {
		this.addColorR = 0;
		this.addColorG = 0;
		this.addColorB = 0;
		this.addColorInstance = Color.black;
	}

	public void clearObject() {
		this.objMesh.obbStart = 0;
		this.objMesh.obbEnd = 0;
		this.objMesh.anzTri = 0;
		this.objMesh.anzCoords = 0;
		this.objMesh.anzVectors = 0;
	}

	public void setMatrixCacheUsage(boolean var1) {
		this.useMatrixCache = var1;
		if (!this.useMatrixCache) {
			this.matrixCache = null;
		} else if (this.matrixCache == null) {
			this.matrixCache = new Hashtable(3);
		}

	}

	public void setReNormalization(boolean var1) {
		this.reNormalize = var1;
	}

	public void setDepthBufferWrites(boolean var1) {
		this.writeToZbuffer = var1;
	}

	public void decoupleMesh() {
		this.objMesh = new Mesh(0);
		this.objVectors.setMesh(this.objMesh);
	}

	public void setBlending(boolean var1) {
		this.isEnvmapped = var1;
		this.isBlended = var1;
		this.isBumpmapped = var1;
		TextureManager var2 = TextureManager.getInstance();
		if (var1) {
			this.checkBumpmap();
			if (!var2.textures[this.bumpmap[0]].isBumpmap) {
				var2.textures[this.bumpmap[0]].createBumpmap();
			}

			if (var2.textures[this.basemap[0]].height != var2.textures[this.bumpmap[0]].height
					|| var2.textures[this.basemap[0]].width != var2.textures[this.bumpmap[0]].width) {
				Logger.log(
						(String) "Correct blending can only be applied, if texture- and bumpmap are equal in size!",
						1);
			}
		}

	}

	public boolean getBlending() {
		return this.isBlended;
	}

	public void setBumpmapped(boolean var1) {
		this.isBumpmapped = var1;
		this.isEnvmapped = var1;
		TextureManager var2 = TextureManager.getInstance();
		if (var1) {
			this.checkBumpmap();
			if (!var2.textures[this.bumpmap[0]].isBumpmap) {
				var2.textures[this.bumpmap[0]].createBumpmap();
			}

			if (var2.textures[this.texture[0]].height != 256
					|| var2.textures[this.texture[0]].height != 256) {
				Logger.log(
						(String) "The environment-map used for bumpmapping should be 256x256 pixels in size.",
						1);
			}
		}

	}

	public void setBillboarding(boolean var1) {
		this.isBillBoard = var1;
	}

	public boolean isBumpmapped() {
		return this.isBumpmapped;
	}

	public boolean isEnvmapped() {
		return this.isEnvmapped;
	}

	public void setEnvmapped(boolean var1) {
		this.isEnvmapped = var1;
	}

	public void setEnvmapMode(boolean var1) {
		this.useCSEnvmapping = var1;
	}

	public void setEnvmapDirection(int var1) {
		this.envMapDir = var1;
	}

	public boolean getEnvmapMode() {
		return this.useCSEnvmapping;
	}

	public void rotateX(float var1) {
		this.rotationMatrix.rotateX(var1);
	}

	public void rotateY(float var1) {
		this.rotationMatrix.rotateY(var1);
	}

	public void rotateZ(float var1) {
		this.rotationMatrix.rotateZ(var1);
	}

	public void rotateAxis(SimpleVector var1, float var2) {
		this.rotationMatrix.rotateAxis(var1, var2);
	}

	public void translateMesh() {
		this.objMesh.translateMesh(this.translationMatrix, this.originMatrix);
		if (this.anim != null) {
			this.anim.translateMesh(this.translationMatrix, this.originMatrix);
		}

		this.skipPivot = false;
		this.calcBoundingBox();
	}

	public void translate(SimpleVector var1) {
		this.translationMatrix.translate(var1);
	}

	public void translate(float var1, float var2, float var3) {
		this.translationMatrix.translate(var1, var2, var3);
	}

	public void align(Camera var1) {
		float var2 = this.getScale();
		this.setScale(1.0F);
		this.rotationMatrix = var1.backMatrix.invert3x3();
		this.setScale(var2);
	}

	public void align(Object3D var1) {
		float var2 = this.getScale();
		this.setScale(1.0F);
		this.rotationMatrix = var1.rotationMatrix.cloneMatrix();
		this.setScale(var2);
	}

	public void setOrientation(SimpleVector var1, SimpleVector var2) {
		float var3 = this.getScale();
		this.setScale(1.0F);
		this.rotationMatrix.setOrientation(var1, var2);
		this.setScale(var3);
	}

	public void enableLazyTransformations() {
		this.lazyTransforms = true;
		this.transCache = null;
		this.invCache = null;
	}

	public void disableLazyTransformations() {
		this.lazyTransforms = false;
		this.transCache = null;
		this.invCache = null;
	}

	public void scale(float var1) {
		if (var1 > 0.0F) {
			this.scaleFactor *= var1;
			this.invScaleFactor = 1.0F / this.scaleFactor;
			this.rotationMatrix.scalarMul(var1);
		} else {
			Logger.log((String) "Scale has to be greater than zero!", 0);
		}

	}

	public void setScale(float var1) {
		if (this.scaleFactor != 0.0F && var1 > 0.0F) {
			float var2 = var1 / this.scaleFactor;
			if (var2 < 1.0E-4F) {
				var2 = 1.0E-4F;
			}

			this.scale(var2);
		} else {
			Logger.log((String) "Invalid scale!", 0);
		}

	}

	public float getScale() {
		return this.scaleFactor;
	}

	public SimpleVector getTranslation() {
		return this.translationMatrix.getTranslation();
	}

	public SimpleVector getTranslation(SimpleVector var1) {
		if (var1 == null) {
			var1 = new SimpleVector();
		}

		var1.set(this.translationMatrix.mat[3][0],
				this.translationMatrix.mat[3][1],
				this.translationMatrix.mat[3][2]);
		return var1;
	}

	public SimpleVector getOrigin() {
		return this.originMatrix.getTranslation();
	}

	public SimpleVector getXAxis() {
		SimpleVector var1 = this.rotationMatrix.getXAxis();
		var1.scalarMul(this.invScaleFactor);
		return var1;
	}

	public SimpleVector getYAxis() {
		SimpleVector var1 = this.rotationMatrix.getYAxis();
		var1.scalarMul(this.invScaleFactor);
		return var1;
	}

	public SimpleVector getZAxis() {
		SimpleVector var1 = this.rotationMatrix.getZAxis();
		var1.scalarMul(this.invScaleFactor);
		return var1;
	}

	public SimpleVector getXAxis(SimpleVector var1) {
		SimpleVector var2 = this.rotationMatrix.getXAxis(var1);
		var2.scalarMul(1.0F / this.scaleFactor);
		return var2;
	}

	public SimpleVector getYAxis(SimpleVector var1) {
		SimpleVector var2 = this.rotationMatrix.getYAxis(var1);
		var2.scalarMul(1.0F / this.scaleFactor);
		return var2;
	}

	public SimpleVector getZAxis(SimpleVector var1) {
		SimpleVector var2 = this.rotationMatrix.getZAxis(var1);
		var2.scalarMul(1.0F / this.scaleFactor);
		return var2;
	}

	public Matrix getRotationMatrix() {
		return this.rotationMatrix;
	}

	public Matrix getTranslationMatrix() {
		return this.translationMatrix;
	}

	public Matrix getOriginMatrix() {
		return this.originMatrix;
	}

	public int getLightCount() {
		if (this.nearestLights == null) {
			return 0;
		} else {
			float[][] var1 = this.nearestLights;
			synchronized (this.nearestLights) {
				for (int var2 = 0; var2 < this.nearestLights.length; ++var2) {
					if (this.nearestLights[var2][0] == -9999.0F) {
						return var2;
					}
				}

				return this.nearestLights.length;
			}
		}
	}

	public void setRotationMatrix(Matrix var1) {
		this.rotationMatrix = var1;
	}

	public void setTextureMatrix(Matrix var1) {
		this.textureMatrix = var1;
	}

	public Matrix getTextureMatrix() {
		return this.textureMatrix;
	}

	public void clearRotation() {
		this.setScale(1.0F);
		this.rotationMatrix.setIdentity();
	}

	public void clearTranslation() {
		this.translationMatrix.setIdentity();
	}

	public void rotateMesh() {
		this.objMesh.rotateMesh(this.rotationMatrix, this.xRotationCenter,
				this.yRotationCenter, this.zRotationCenter, this.scaleFactor);
		if (this.anim != null) {
			this.anim.rotateMesh(this.rotationMatrix, this.xRotationCenter,
					this.yRotationCenter, this.zRotationCenter,
					this.scaleFactor);
		}

		this.skipPivot = false;
		this.calcBoundingBox();
	}

	public void setTranslationMatrix(Matrix var1) {
		this.translationMatrix = var1;
	}

	public void setMesh(Mesh var1) {
		this.objMesh = var1;
		if (this.objVectors != null) {
			this.objVectors.setMesh(this.objMesh);
		}

	}

	public Mesh getMesh() {
		return this.objMesh;
	}

	public PolygonManager getPolygonManager() {
		if (this.polyManager == null) {
			this.polyManager = new PolygonManager(this);
		}

		return this.polyManager;
	}

	public void setBoundingBox(float var1, float var2, float var3, float var4,
			float var5, float var6) {
		Mesh var7 = this.objMesh;
		synchronized (this.objMesh) {
			if (!this.isMainWorld && this.oneSectorOnly) {
				if (this.objMesh.obbStart != 0) {
					this.objMesh.anzCoords = this.objMesh.obbStart;
					this.objMesh.obbStart = 0;
					this.objMesh.obbEnd = 0;
				}

				if (this.objVectors != null) {
					this.objMesh.obbStart = this.objVectors.addVertex(var1,
							var3, var5);
					this.objVectors.addVertex(var1, var3, var6);
					this.objVectors.addVertex(var2, var3, var5);
					this.objVectors.addVertex(var2, var3, var6);
					this.objVectors.addVertex(var2, var4, var5);
					this.objVectors.addVertex(var2, var4, var6);
					this.objVectors.addVertex(var1, var4, var5);
					this.objMesh.obbEnd = this.objVectors.addVertex(var1, var4,
							var6);
					this.hasBoundingBox = true;
				}
			} else {
				Logger.log(
						(String) "Can\'t create a bounding box for a multi-sectored object!",
						1);
			}

		}
	}

	public Object3D cloneObject() {
		return new Object3D(this, true);
	}

	void enlarge(int var1) {
		int var2 = this.texture.length + var1;
		if (var2 > this.texture.length) {
			Object3D var3 = new Object3D(var2);
			Vectors var4 = var3.objVectors;
			Mesh var5 = var3.objMesh;
			if (this.objVectors.sb != null) {
				var4.createScreenColors();
			}

			if (this.objVectors.eu != null) {
				var3.objVectors.createEnvmapCoords();
			}

			if (this.objVectors.alpha != null) {
				var3.objVectors.createAlpha();
			}

			if (this.objVectors.bsu != null) {
				var3.objVectors.createBumpmapCoords();
			}

			int var6;
			for (var6 = 0; var6 < this.objVectors.maxVectors; ++var6) {
				var4.sbOrg[var6] = this.objVectors.sbOrg[var6];
				var4.srOrg[var6] = this.objVectors.srOrg[var6];
				var4.sgOrg[var6] = this.objVectors.sgOrg[var6];
				var4.nuOrg[var6] = this.objVectors.nuOrg[var6];
				var4.nvOrg[var6] = this.objVectors.nvOrg[var6];
				var4.uOrg[var6] = this.objVectors.uOrg[var6];
				var4.vOrg[var6] = this.objVectors.vOrg[var6];
				if (this.objVectors.eu != null) {
					var4.eu[var6] = this.objVectors.eu[var6];
					var4.ev[var6] = this.objVectors.ev[var6];
				}

				var4.buOrg[var6] = this.objVectors.buOrg[var6];
				var4.bvOrg[var6] = this.objVectors.bvOrg[var6];
				var4.vertexSector[var6] = this.objVectors.vertexSector[var6];
				var4.sx[var6] = this.objVectors.sx[var6];
				var4.sy[var6] = this.objVectors.sy[var6];
				var4.sz[var6] = this.objVectors.sz[var6];
				var4.su[var6] = this.objVectors.su[var6];
				var4.sv[var6] = this.objVectors.sv[var6];
				if (var4.bsu != null) {
					var4.bsu[var6] = this.objVectors.bsu[var6];
					var4.bsv[var6] = this.objVectors.bsv[var6];
				}

				if (this.objVectors.sb != null) {
					var4.sb[var6] = this.objVectors.sb[var6];
					var4.sr[var6] = this.objVectors.sr[var6];
					var4.sg[var6] = this.objVectors.sg[var6];
				}

				if (this.objVectors.alpha != null) {
					var4.alpha[var6] = this.objVectors.alpha[var6];
				}
			}

			this.objVectors.sbOrg = var4.sbOrg;
			this.objVectors.srOrg = var4.srOrg;
			this.objVectors.sgOrg = var4.sgOrg;
			this.objVectors.nuOrg = var4.nuOrg;
			this.objVectors.nvOrg = var4.nvOrg;
			this.objVectors.uOrg = var4.uOrg;
			this.objVectors.vOrg = var4.vOrg;
			this.objVectors.eu = var4.eu;
			this.objVectors.ev = var4.ev;
			this.objVectors.buOrg = var4.buOrg;
			this.objVectors.bvOrg = var4.bvOrg;
			this.objVectors.vertexSector = var4.vertexSector;
			this.objVectors.maxVectors = var4.maxVectors;
			this.objVectors.sx = var4.sx;
			this.objVectors.sy = var4.sy;
			this.objVectors.sz = var4.sz;
			this.objVectors.su = var4.su;
			this.objVectors.sv = var4.sv;
			this.objVectors.sb = var4.sb;
			this.objVectors.sr = var4.sr;
			this.objVectors.sg = var4.sg;
			this.objVectors.bsu = var4.bsu;
			this.objVectors.bsv = var4.bsv;
			this.objVectors.setMesh(this.objMesh);
			this.objMesh.maxVectors = var5.maxVectors;

			for (var6 = 0; var6 < this.objMesh.points.length; ++var6) {
				var5.points[var6][0] = this.objMesh.points[var6][0];
				var5.points[var6][1] = this.objMesh.points[var6][1];
				var5.points[var6][2] = this.objMesh.points[var6][2];
			}

			this.objMesh.points = var5.points;

			for (var6 = 0; var6 < this.objMesh.xOrg.length; ++var6) {
				var5.xOrg[var6] = this.objMesh.xOrg[var6];
				var5.yOrg[var6] = this.objMesh.yOrg[var6];
				var5.zOrg[var6] = this.objMesh.zOrg[var6];
				var5.nxOrg[var6] = this.objMesh.nxOrg[var6];
				var5.nyOrg[var6] = this.objMesh.nyOrg[var6];
				var5.nzOrg[var6] = this.objMesh.nzOrg[var6];
				var5.coords[var6] = this.objMesh.coords[var6];
			}

			this.objMesh.xOrg = var5.xOrg;
			this.objMesh.yOrg = var5.yOrg;
			this.objMesh.zOrg = var5.zOrg;
			this.objMesh.nxOrg = var5.nxOrg;
			this.objMesh.nyOrg = var5.nyOrg;
			this.objMesh.nzOrg = var5.nzOrg;
			this.objMesh.coords = var5.coords;
			System.arraycopy(this.texture, 0, var3.texture, 0,
					this.texture.length);
			System.arraycopy(this.basemap, 0, var3.basemap, 0,
					this.basemap.length);
			this.texture = var3.texture;
			this.basemap = var3.basemap;
			if (this.sector != null) {
				if (var3.sector == null) {
					var3.sector = new int[this.texture.length];
				}

				System.arraycopy(this.sector, 0, var3.sector, 0,
						this.sector.length);
				this.sector = var3.sector;
			}

			if (this.bumpmap != null) {
				if (var3.bumpmap == null) {
					var3.bumpmap = new int[this.texture.length];
				}

				System.arraycopy(this.bumpmap, 0, var3.bumpmap, 0,
						this.bumpmap.length);
				this.bumpmap = var3.bumpmap;
			}

		}
	}

	public Matrix getWorldTransformation() {
		Matrix var1;
		if (this.lazyTransforms && this.transCache != null) {
			var1 = this.transCache.cloneMatrix();
		} else {
			var1 = new Matrix();
			Matrix var2 = this.getCachedMatrix(0);
			var1.mat[3][0] = -this.xRotationCenter;
			var1.mat[3][1] = -this.yRotationCenter;
			var1.mat[3][2] = -this.zRotationCenter;
			var2.mat[3][0] = this.xRotationCenter
					+ this.translationMatrix.mat[3][0]
					+ this.originMatrix.mat[3][0];
			var2.mat[3][1] = this.yRotationCenter
					+ this.translationMatrix.mat[3][1]
					+ this.originMatrix.mat[3][1];
			var2.mat[3][2] = this.zRotationCenter
					+ this.translationMatrix.mat[3][2]
					+ this.originMatrix.mat[3][2];
			if (!this.isBillBoard) {
				var1.matMul(this.rotationMatrix);
			} else {
				if (this.myWorld == null) {
					return new Matrix();
				}

				this.mat2 = this.myWorld.camera.frontMatrix;
				if (this.myWorld.camera.billBoardMatrix != null) {
					this.mat2 = this.myWorld.camera.billBoardMatrix;
				}

				Matrix var3 = this.mat2.invert();
				var3.scalarMul(this.scaleFactor);
				var1.matMul(var3);
			}

			var1.matMul(var2);
			if (this.parentCnt != 0) {
				if (this.isBillBoard && !Config.oldStyleBillBoarding) {
					var1 = this.recurseObjectsBillboarded(var1);
				} else {
					var1 = this.recurseObjects(var1);
				}
			}

			if (this.lazyTransforms) {
				this.transCache = var1.cloneMatrix();
			}
		}

		return var1;
	}

	public Matrix getWorldTransformationTweaked(Matrix var1) {
		Matrix var2 = var1;
		if (var1 == null) {
			var2 = new Matrix();
		}

		if (this.lazyTransforms && this.transCache != null) {
			var2.setTo(this.transCache);
		} else {
			var2.setIdentity();
			Matrix var3 = this.getCachedMatrix(0);
			var2.mat[3][0] = -this.xRotationCenter;
			var2.mat[3][1] = -this.yRotationCenter;
			var2.mat[3][2] = -this.zRotationCenter;
			var3.mat[3][0] = this.xRotationCenter
					+ this.translationMatrix.mat[3][0]
					+ this.originMatrix.mat[3][0];
			var3.mat[3][1] = this.yRotationCenter
					+ this.translationMatrix.mat[3][1]
					+ this.originMatrix.mat[3][1];
			var3.mat[3][2] = this.zRotationCenter
					+ this.translationMatrix.mat[3][2]
					+ this.originMatrix.mat[3][2];
			if (!this.isBillBoard) {
				var2.matMul(this.rotationMatrix);
			} else {
				if (this.myWorld == null) {
					return new Matrix();
				}

				this.mat2 = this.myWorld.camera.frontMatrix;
				if (this.myWorld.camera.billBoardMatrix != null) {
					this.mat2 = this.myWorld.camera.billBoardMatrix;
				}

				Matrix var4 = this.mat2.invert();
				var4.scalarMul(this.scaleFactor);
				var2.matMul(var4);
			}

			var2.matMul(var3);
			if (this.parentCnt != 0) {
				if (this.isBillBoard && !Config.oldStyleBillBoarding) {
					var2 = this.recurseObjectsBillboarded(var2);
				} else {
					var2 = this.recurseObjects(var2);
				}
			}

			if (this.lazyTransforms) {
				this.transCache = var2.cloneMatrix();
			}
		}

		return var2;
	}

	public Matrix getWorldTransformation(Matrix var1) {
		if (var1 == null) {
			var1 = new Matrix();
		}

		if (this.lazyTransforms && this.transCache != null) {
			var1.setTo(this.transCache);
		} else {
			Matrix var2 = this.getWorldTransformation();
			var1.setTo(var2);
		}

		return var1;
	}

	public synchronized void addCollisionListener(CollisionListener var1) {
		if (this.collisionListener == null) {
			this.collisionListener = new Vector(2);
		}

		this.collisionListener.addElement(var1);
		++globalListenerCount;
	}

	public synchronized void removeCollisionListener(CollisionListener var1) {
		if (this.collisionListener != null) {
			this.collisionListener.removeElement(var1);
			--globalListenerCount;
			if (this.collisionListener.size() == 0) {
				this.collisionListener = null;
			}
		}

	}

	public void disableCollisionListeners() {
		this.disableListeners = true;
	}

	public void enableCollisionListeners() {
		this.disableListeners = false;
	}

	public Enumeration getCollisionListeners() {
		return this.collisionListener != null ? this.collisionListener
				.elements() : (new Vector(1)).elements();
	}

	public void setRenderHook(IRenderHook var1) {
		if (var1 != null && var1 != this.renderHook
				&& var1 instanceof GLSLShader) {
			++((GLSLShader) var1).assignedTo;
		}

		if (var1 == null && this.renderHook != null
				&& this.renderHook instanceof GLSLShader) {
			--((GLSLShader) this.renderHook).assignedTo;
		}

		this.renderHook = var1;
	}

	public IRenderHook getRenderHook() {
		return this.renderHook;
	}

	public int checkForCollision(SimpleVector var1, float var2) {
		this.checkWorld();
		return this.myWorld.checkObjCollision(this, var1, var2);
	}

	public SimpleVector checkForCollisionSpherical(SimpleVector var1, float var2) {
		this.checkWorld();
		return this.myWorld.checkObjCollisionSpherical(this, var1, var2);
	}

	public SimpleVector checkForCollisionEllipsoid(SimpleVector var1,
			SimpleVector var2, int var3) {
		if (var3 < 1) {
			var3 = 1;
		}

		this.checkWorld();
		return this.myWorld.checkObjCollisionEllipsoid(this, var1, var2, var3);
	}

	private void checkWorld() {
		if (this.myWorld == null) {
			Logger.log(
					(String) "Object has to be assigned to a world for collision detection!",
					0);
		}

	}

	public void setEllipsoidMode(int var1) {
		this.ellipsoidMode = var1;
	}

	public int getEllipsoidMode() {
		return this.ellipsoidMode;
	}

	public boolean wasTargetOfLastCollision() {
		return this.wasCollider;
	}

	public void resetCollisionStatus() {
		this.wasCollider = false;
	}

	public float calcMinDistance(SimpleVector var1, SimpleVector var2) {
		this.wasCollider = false;
		this.resetPolygonIDCount();
		float var3 = this.collide(new float[] { var1.x, var1.y, var1.z },
				new float[] { var2.x, var2.y, var2.z }, 0.0F, 1.0E12F, false);
		if (var3 != 1.0E12F) {
			SimpleVector var4 = new SimpleVector(var2.x, var2.y, var2.z);
			var4.scalarMul(var3);
			var4.add(new SimpleVector(var1.x, var1.y, var1.z));
			this.notifyCollisionListeners(0, 0, new Object3D[] { this }, var4);
			this.wasCollider = true;
		}

		return var3;
	}

	public float calcMinDistance(SimpleVector var1, SimpleVector var2,
			float var3) {
		return this.calcMinDistance(var1, var2, var3, true);
	}

	float calcMinDistance(SimpleVector var1, SimpleVector var2, float var3,
			boolean var4) {
		this.wasCollider = false;
		this.resetPolygonIDCount();
		float var5 = Config.collideOffset;
		Config.collideOffset = var3;
		float var6 = this.collide(new float[] { var1.x, var1.y, var1.z },
				new float[] { var2.x, var2.y, var2.z }, 0.0F, var3, false);
		Config.collideOffset = var5;
		if (var4 && var6 != 1.0E12F) {
			SimpleVector var7 = new SimpleVector(var2.x, var2.y, var2.z);
			var7.scalarMul(var6);
			var7.add(new SimpleVector(var1.x, var1.y, var1.z));
			this.notifyCollisionListeners(0, 0, new Object3D[] { this }, var7);
			this.wasCollider = true;
		}

		return var6;
	}

	public void setCenter(SimpleVector var1) {
		this.centerX = var1.x;
		this.centerY = var1.y;
		this.centerZ = var1.z;
	}

	public SimpleVector getCenter() {
		return new SimpleVector(this.centerX, this.centerY, this.centerZ);
	}

	public SimpleVector getTransformedCenter() {
		return this.getTransformedCenter(new SimpleVector());
	}

	public SimpleVector getTransformedCenter(SimpleVector var1) {
		if (var1 == null) {
			var1 = new SimpleVector();
		}

		this.getProjectedPoint(this.centerX, this.centerY, this.centerZ, var1,
				(float[]) null);
		return var1;
	}

	public void setRotationPivot(SimpleVector var1) {
		this.xRotationCenter = var1.x;
		this.yRotationCenter = var1.y;
		this.zRotationCenter = var1.z;
	}

	public SimpleVector getRotationPivot() {
		return new SimpleVector(this.xRotationCenter, this.yRotationCenter,
				this.zRotationCenter);
	}

	public void calcCenter() {
		SimpleVector var1 = this.objMesh.calcCenter();
		if (!this.skipPivot) {
			this.xRotationCenter = var1.x;
			this.yRotationCenter = var1.y;
			this.zRotationCenter = var1.z;
		}

		this.centerX = var1.x;
		this.centerY = var1.y;
		this.centerZ = var1.z;
	}

	public void setOcTree(OcTree var1) {
		if (Config.doPortalHsr && var1 != null) {
			Logger.log(
					(String) "Octree is null or portal rendering is being used!",
					1);
		} else {
			this.ocTree = var1;
		}

	}

	public OcTree getOcTree() {
		return this.ocTree;
	}

	public void setSector(int var1) {
		if (this.oneSectorOnly) {
			int var2 = Config.maxPortals - 1;
			if (this.myWorld != null) {
				var2 = this.myWorld.portals.anzSectors;
			}

			for (int var3 = 1; var3 <= var2; ++var3) {
				if (var3 == var1) {
					this.sectorEndPoint[var3] = this.objMesh.anzCoords;
					this.sectorEndPoly[var3] = this.objMesh.anzTri;
				} else {
					this.sectorEndPoint[var3] = 0;
					this.sectorEndPoly[var3] = 0;
				}

				this.sectorStartPoint[var3] = 0;
				this.sectorStartPoly[var3] = 0;
			}

			this.singleSectorNumber = var1;
		} else {
			Logger.log(
					(String) "Multi-sectored objects can\'t be assigned to a single sector!",
					0);
		}

	}

	public void setAsMultiSectored() {
		this.oneSectorOnly = false;
	}

	public void setOrigin(SimpleVector var1) {
		this.originMatrix.setIdentity();
		this.originMatrix.translate(var1.x, var1.y, var1.z);
	}

	public void invert() {
		for (int var1 = 0; var1 < this.objMesh.anzTri; ++var1) {
			int var2 = this.objMesh.points[var1][0];
			int var3 = this.objMesh.points[var1][2];
			int var4 = this.objMesh.coords[var2];
			int var5 = this.objMesh.coords[var3];
			this.objMesh.coords[var2] = var5;
			this.objMesh.coords[var3] = var4;
			float var6 = this.objVectors.nuOrg[var2];
			float var7 = this.objVectors.nuOrg[var3];
			this.objVectors.nuOrg[var2] = var7;
			this.objVectors.nuOrg[var3] = var6;
			var6 = this.objVectors.nvOrg[var2];
			var7 = this.objVectors.nvOrg[var3];
			this.objVectors.nvOrg[var2] = var7;
			this.objVectors.nvOrg[var3] = var6;
			var6 = this.objVectors.uOrg[var2];
			var7 = this.objVectors.uOrg[var3];
			this.objVectors.uOrg[var2] = var7;
			this.objVectors.uOrg[var3] = var6;
			var6 = this.objVectors.vOrg[var2];
			var7 = this.objVectors.vOrg[var3];
			this.objVectors.vOrg[var2] = var7;
			this.objVectors.vOrg[var3] = var6;
			var6 = this.objVectors.buOrg[var2];
			var7 = this.objVectors.buOrg[var3];
			this.objVectors.buOrg[var2] = var7;
			this.objVectors.buOrg[var3] = var6;
			var6 = this.objVectors.bvOrg[var2];
			var7 = this.objVectors.bvOrg[var3];
			this.objVectors.bvOrg[var2] = var7;
			this.objVectors.bvOrg[var3] = var6;
		}

	}

	public void invertCulling(boolean var1) {
		this.reverseCulling = var1;
	}

	public boolean cullingIsInverted() {
		return this.reverseCulling;
	}

	public void calcNormals() {
		this.objMesh.calcNormals();
	}

	public void calcTangentVectors() {
		this.objMesh.calculateTangentVectors(this.objVectors);
	}

	public void calcTextureWrap() {
		short var1 = 256;
		short var2 = 256;
		short var3 = 256;
		short var4 = 256;
		int var5 = var1 >> 1;
		int var6 = var2 >> 1;
		int var20 = var1 - 1;
		int var21 = var2 - 1;
		int var7 = var4 >> 1;
		int var8 = var3 >> 1;
		int var23 = var4 - 1;
		int var22 = var3 - 1;
		Hashtable var9 = new Hashtable();

		int var10;
		for (var10 = 0; var10 < this.objMesh.anzVectors; ++var10) {
			Integer var11 = IntegerC.valueOf(this.objMesh.coords[var10]);
			Vector var12 = (Vector) var9.get(var11);
			if (var12 == null) {
				var12 = new Vector();
				var9.put(var11, var12);
			}

			var12.addElement(IntegerC.valueOf(var10));
		}

		for (var10 = 0; var10 < this.objMesh.anzCoords; ++var10) {
			float var24 = this.objMesh.nxOrg[var10];
			float var25 = this.objMesh.nyOrg[var10];
			float var13 = (float) var5 + var24 * (float) var5;
			float var14 = (float) var6 + var25 * (float) var6;
			float var15 = (float) var7 + var24 * (float) var7;
			float var16 = (float) var8 + var25 * (float) var8;

			while (var13 > (float) var20 || var13 < 0.0F
					|| var14 > (float) var21 || var14 < 0.0F) {
				if (var13 > (float) var20) {
					var13 = (float) (-var20);
				} else if (var13 < 0.0F) {
					var13 = (float) var20;
				}

				if (var14 > (float) var21) {
					var14 = (float) (-var21);
				} else if (var14 < 0.0F) {
					var14 = (float) var21;
				}
			}

			while (var15 > (float) var23 || var15 < 0.0F
					|| var16 > (float) var22 || var16 < 0.0F) {
				if (var15 > (float) var23) {
					var15 = (float) (-var23);
				} else if (var15 < 0.0F) {
					var15 = (float) var23;
				}

				if (var16 > (float) var22) {
					var16 = (float) (-var22);
				} else if (var16 < 0.0F) {
					var16 = (float) var22;
				}
			}

			Vector var17 = (Vector) var9.get(IntegerC.valueOf(var10));
			if (var17 != null) {
				for (int var18 = 0; var18 < var17.size(); ++var18) {
					int var19 = ((Integer) var17.elementAt(var18)).intValue();
					this.objVectors.uOrg[var19] = var13;
					this.objVectors.vOrg[var19] = var14;
					this.objVectors.buOrg[var19] = var15;
					this.objVectors.bvOrg[var19] = var16;
					this.objVectors.nuOrg[var19] = var13 / (float) var20;
					this.objVectors.nvOrg[var19] = var14 / (float) var21;
				}
			}
		}

	}

	public void calcTextureWrapSpherical() {
		short var1 = 256;
		short var2 = 256;
		short var3 = 256;
		short var4 = 256;
		this.calcCenter();
		double var5 = 1.0D;
		double var7 = 1.0D;
		Hashtable var9 = new Hashtable();

		int var10;
		for (var10 = 0; var10 < this.objMesh.anzVectors; ++var10) {
			Integer var11 = IntegerC.valueOf(this.objMesh.coords[var10]);
			Vector var12 = (Vector) var9.get(var11);
			if (var12 == null) {
				var12 = new Vector();
				var9.put(var11, var12);
			}

			var12.addElement(IntegerC.valueOf(var10));
		}

		for (var10 = 0; var10 < this.objMesh.anzCoords; ++var10) {
			float var22 = this.objMesh.xOrg[var10] - this.centerX;
			float var23 = this.objMesh.yOrg[var10] - this.centerY;
			float var13 = this.objMesh.zOrg[var10] - this.centerZ;
			float var14 = (float) Math.sqrt((double) (var22 * var22 + var23
					* var23 + var13 * var13));
			var22 /= var14;
			var23 /= var14;
			float var15 = (float) ((Math.asin((double) var22) / 3.141592653589793D + 0.5D) * var5);
			float var16 = (float) ((Math.asin((double) var23) / 3.141592653589793D + 0.5D) * var7);
			float var17 = var15 * (float) var4;
			float var18 = var16 * (float) var3;
			var15 *= (float) var1;
			var16 *= (float) var2;

			while (var15 > (float) var1 || var15 < 0.0F || var16 > (float) var2
					|| var16 < 0.0F) {
				if (var15 > (float) var1) {
					var15 = (float) (-var1);
				} else if (var15 < 0.0F) {
					var15 = (float) var1;
				}

				if (var16 > (float) var2) {
					var16 = (float) (-var2);
				} else if (var16 < 0.0F) {
					var16 = (float) var2;
				}
			}

			while (var17 > (float) var4 || var17 < 0.0F || var18 > (float) var3
					|| var18 < 0.0F) {
				if (var17 > (float) var4) {
					var17 = (float) (-var4);
				} else if (var17 < 0.0F) {
					var17 = (float) var4;
				}

				if (var18 > (float) var3) {
					var18 = (float) (-var3);
				} else if (var18 < 0.0F) {
					var18 = (float) var3;
				}
			}

			Vector var19 = (Vector) var9.get(IntegerC.valueOf(var10));
			if (var19 != null) {
				for (int var20 = 0; var20 < var19.size(); ++var20) {
					int var21 = ((Integer) var19.elementAt(var20)).intValue();
					this.objVectors.uOrg[var21] = var15;
					this.objVectors.vOrg[var21] = var16;
					this.objVectors.buOrg[var21] = var17;
					this.objVectors.bvOrg[var21] = var18;
					this.objVectors.nuOrg[var21] = var15 / (float) var1;
					this.objVectors.nvOrg[var21] = var16 / (float) var2;
				}
			}
		}

	}

	public void recreateTextureCoords() {
		TextureManager var1 = TextureManager.getInstance();

		for (int var2 = 0; var2 < this.objMesh.anzTri
				&& this.texture[var2] != -1; ++var2) {
			int var3 = var1.textures[this.texture[var2]].width;
			int var4 = var1.textures[this.texture[var2]].height;
			int var5 = 1;
			int var6 = 1;
			if (this.bumpmap != null) {
				var5 = var1.textures[this.bumpmap[var2]].width;
				var6 = var1.textures[this.bumpmap[var2]].height;
			}

			for (int var7 = 0; var7 < 3; ++var7) {
				int var8 = this.objMesh.points[var2][var7];
				this.objVectors.buOrg[var8] = this.objVectors.nuOrg[var8]
						* (float) var5;
				this.objVectors.bvOrg[var8] = this.objVectors.nvOrg[var8]
						* (float) var6;
				this.objVectors.uOrg[var8] = this.objVectors.nuOrg[var8]
						* (float) var3;
				this.objVectors.vOrg[var8] = this.objVectors.nvOrg[var8]
						* (float) var4;
			}
		}

	}

	public void setAllTextures(String var1, String var2) {
		this.setAllTextures(var1, var1, var2);
	}

	public void setAllTextures(String var1, String var2, String var3) {
		this.checkBumpmap();
		TextureManager var4 = TextureManager.getInstance();
		int var5 = var4.getTextureID(var2);
		int var6 = var4.getTextureID(var1);
		int var7 = var4.getTextureID(var3);
		if (var5 != -1 && var6 != -1 && var7 != -1) {
			for (int var8 = 0; var8 < this.objMesh.anzTri; ++var8) {
				this.texture[var8] = var5;
				this.basemap[var8] = var6;
				this.bumpmap[var8] = var7;
			}
		} else {
			Logger.log((String) ("Tried to set an undefined texture (" + var1
					+ "/" + var2 + "/" + var3 + ")!"), 0);
		}

		if (!var4.textures[var7].isBumpmap) {
			var4.textures[var7].createBumpmap();
		}

	}

	public void setBaseTexture(String var1) {
		TextureManager var2 = TextureManager.getInstance();
		int var3 = var2.getTextureID(var1);
		if (var3 != -1) {
			for (int var4 = 0; var4 < this.objMesh.anzTri; ++var4) {
				this.basemap[var4] = var3;
			}
		} else {
			Logger.log(
					(String) ("Tried to set an undefined texture (" + var1 + ") as base!"),
					0);
		}

	}

	public void setTexture(String var1) {
		TextureManager var2 = TextureManager.getInstance();
		int var3 = var2.getTextureID(var1);
		if (var3 != -1) {
			for (int var4 = 0; var4 < this.objMesh.anzTri; ++var4) {
				this.texture[var4] = var3;
			}
		} else {
			Logger.log((String) ("Tried to set an undefined texture: " + var1),
					0);
		}

	}

	public void shareTextureData(Object3D var1) {
		this.texture = var1.texture;
		this.basemap = var1.basemap;
		this.bumpmap = var1.bumpmap;
		this.multiTex = var1.multiTex;
		this.multiMode = var1.multiMode;
	}

	public void setTexture(TextureInfo var1) {
		if (this.texture != null) {
			int var2;
			int var3;
			if (var1.stageCnt <= 1) {
				this.usesMultiTexturing = false;
			} else {
				if (this.multiTex == null) {
					this.multiTex = new int[Config.maxTextureLayers - 1][this.texture.length];
					this.multiMode = new int[Config.maxTextureLayers - 1][this.texture.length];

					for (var2 = 0; var2 < this.texture.length; ++var2) {
						for (var3 = 0; var3 < Config.maxTextureLayers - 1; ++var3) {
							this.multiTex[var3][var2] = -1;
						}
					}
				}

				this.objVectors.createMultiCoords();
				this.usesMultiTexturing = true;
			}

			var2 = var1.textures[0];
			if (var2 != -1) {
				for (var3 = 0; var3 < this.objMesh.anzTri; ++var3) {
					this.texture[var3] = var2;
				}
			} else {
				Logger.log((String) "Tried to set an undefined texture!", 0);
			}

			for (var3 = 1; var3 < var1.stageCnt; ++var3) {
				int var4 = var1.textures[var3];
				int var5 = var1.mode[var3];
				int var6 = var3 - 1;

				int var7;
				for (var7 = 0; var7 < this.objMesh.anzTri; ++var7) {
					this.multiTex[var6][var7] = var4;
					this.multiMode[var6][var7] = var5;
				}

				for (var7 = 0; var7 < this.objVectors.nuOrg.length; ++var7) {
					this.objVectors.uMul[var6][var7] = this.objVectors.nuOrg[var7];
					this.objVectors.vMul[var6][var7] = this.objVectors.nvOrg[var7];
				}
			}

			this.maxStagesUsed = var1.stageCnt;
		}

	}

	public void setBumpmapTexture(String var1) {
		this.checkBumpmap();
		TextureManager var2 = TextureManager.getInstance();
		int var3 = var2.getTextureID(var1);
		if (var3 != -1) {
			for (int var4 = 0; var4 < this.objMesh.anzTri; ++var4) {
				this.bumpmap[var4] = var3;
			}

			if (!var2.textures[var3].isBumpmap) {
				var2.textures[var3].createBumpmap();
			}
		} else {
			Logger.log(
					(String) ("Tried to set an undefined texture (" + var1 + ") as bumpmap!"),
					0);
		}

	}

	public void removeMultiTexturing() {
		this.maxStagesUsed = 1;
		this.usesMultiTexturing = false;
		this.multiMode = (int[][]) null;
		this.multiTex = (int[][]) null;
	}

	public void invertTextureCoords(boolean var1, boolean var2) {
		for (int var3 = 0; var3 < this.objVectors.nuOrg.length; ++var3) {
			if (var1) {
				this.objVectors.nuOrg[var3] = 1.0F - this.objVectors.nuOrg[var3];
			}

			if (var2) {
				this.objVectors.nvOrg[var3] = 1.0F - this.objVectors.nvOrg[var3];
			}

			if (this.objVectors.uMul != null) {
				for (int var4 = 0; var4 < this.objVectors.uMul.length; ++var4) {
					if (var1) {
						this.objVectors.uMul[var4][var3] = 1.0F - this.objVectors.uMul[var4][var3];
					}

					if (var2) {
						this.objVectors.vMul[var4][var3] = 1.0F - this.objVectors.vMul[var4][var3];
					}
				}
			}
		}

		this.recreateTextureCoords();
	}

	public float rayIntersectsAABB(SimpleVector var1, SimpleVector var2,
			boolean var3) {
		return this.rayIntersectsAABB(new float[] { var1.x, var1.y, var1.z },
				new float[] { var2.x, var2.y, var2.z }, var3);
	}

	public float rayIntersectsAABB(SimpleVector var1, SimpleVector var2) {
		return this.rayIntersectsAABB(new float[] { var1.x, var1.y, var1.z },
				new float[] { var2.x, var2.y, var2.z }, false);
	}

	final float rayIntersectsAABB(float[] var1, float[] var2, boolean var3) {
		if (!this.hasBoundingBox) {
			return 1.0E12F;
		} else {
			if (var2[0] == 0.0F && var2[1] == 0.0F && var2[2] != 0.0F) {
				var2[0] = 1.0E-6F;
			} else if (var2[0] == 0.0F && var2[2] == 0.0F && var2[1] != 0.0F) {
				var2[0] = 1.0E-6F;
			} else if (var2[1] == 0.0F && var2[2] == 0.0F && var2[0] != 0.0F) {
				var2[1] = 1.0E-6F;
			}

			Matrix var10 = this.getInverseWorldTransformation();
			float var11 = var10.mat[0][0];
			float var12 = var10.mat[1][0];
			float var13 = var10.mat[1][1];
			float var14 = var10.mat[2][1];
			float var15 = var10.mat[2][0];
			float var16 = var10.mat[0][1];
			float var17 = var10.mat[2][2];
			float var18 = var10.mat[1][2];
			float var19 = var10.mat[0][2];
			float var20 = var10.mat[3][0];
			float var21 = var10.mat[3][1];
			float var22 = var10.mat[3][2];
			float var4 = var2[0] * var11 + var2[1] * var12 + var2[2] * var15;
			float var5 = var2[0] * var16 + var2[1] * var13 + var2[2] * var14;
			float var6 = var2[0] * var19 + var2[1] * var18 + var2[2] * var17;
			float var7 = var1[0] * var11 + var1[1] * var12 + var1[2] * var15
					+ var20;
			float var8 = var1[0] * var16 + var1[1] * var13 + var1[2] * var14
					+ var21;
			float var9 = var1[0] * var19 + var1[1] * var18 + var1[2] * var17
					+ var22;
			float var23 = -9.9999998E10F;
			float var24 = -9.9999998E10F;
			float var25 = -9.9999998E10F;
			float var26 = 9.9999998E10F;
			float var27 = 9.9999998E10F;
			float var28 = 9.9999998E10F;
			if (!var3) {
				float var29 = (float) Math.sqrt((double) (var4 * var4 + var5
						* var5 + var6 * var6));
				var4 /= var29;
				var5 /= var29;
				var6 /= var29;
			}

			int var40 = this.objMesh.obbStart;
			float var30 = this.objMesh.xOrg[var40];
			float var31 = this.objMesh.yOrg[var40];
			float var32 = this.objMesh.zOrg[var40];
			float var33 = var30;
			float var34 = var31;
			float var35 = var32;

			float var37;
			for (int var36 = 1; var36 < 8; ++var36) {
				var37 = this.objMesh.xOrg[var36 + var40];
				float var38 = this.objMesh.zOrg[var36 + var40];
				float var39 = this.objMesh.yOrg[var36 + var40];
				if (var37 < var30) {
					var30 = var37;
				} else if (var37 > var33) {
					var33 = var37;
				}

				if (var39 < var31) {
					var31 = var39;
				} else if (var39 > var34) {
					var34 = var39;
				}

				if (var38 < var32) {
					var32 = var38;
				} else if (var38 > var35) {
					var35 = var38;
				}
			}

			float var41;
			if ((double) Math.abs(var4) > 1.000000013351432E-10D) {
				var23 = (var30 - var7) / var4;
				var26 = (var33 - var7) / var4;
				if (var23 > var26) {
					var41 = var23;
					var23 = var26;
					var26 = var41;
				}
			}

			if ((double) Math.abs(var5) > 1.000000013351432E-10D) {
				var24 = (var31 - var8) / var5;
				var27 = (var34 - var8) / var5;
				if (var24 > var27) {
					var41 = var24;
					var24 = var27;
					var27 = var41;
				}
			}

			if ((double) Math.abs(var6) > 1.000000013351432E-10D) {
				var25 = (var32 - var9) / var6;
				var28 = (var35 - var9) / var6;
				if (var25 > var28) {
					var41 = var25;
					var25 = var28;
					var28 = var41;
				}
			}

			var41 = var23;
			if (var23 < var24) {
				var41 = var24;
			}

			if (var41 < var25) {
				var41 = var25;
			}

			var37 = var26;
			if (var26 > var27) {
				var37 = var27;
			}

			if (var37 > var28) {
				var37 = var28;
			}

			if (var41 <= var37 && var37 > 0.0F) {
				return var41;
			} else {
				return 1.0E12F;
			}
		}
	}

	public boolean ellipsoidIntersectsAABB(SimpleVector var1, SimpleVector var2) {
		if (!Config.useFastCollisionDetection) {
			return true;
		} else if (!this.hasBoundingBox) {
			return false;
		} else {
			boolean var3 = true;
			Matrix var7 = this.getInverseWorldTransformation();
			float var8 = var7.mat[0][0];
			float var9 = var7.mat[1][0];
			float var10 = var7.mat[1][1];
			float var11 = var7.mat[2][1];
			float var12 = var7.mat[2][0];
			float var13 = var7.mat[0][1];
			float var14 = var7.mat[2][2];
			float var15 = var7.mat[1][2];
			float var16 = var7.mat[0][2];
			float var17 = var7.mat[3][0];
			float var18 = var7.mat[3][1];
			float var19 = var7.mat[3][2];
			float var4 = var1.x * var8 + var1.y * var9 + var1.z * var12 + var17;
			float var5 = var1.x * var13 + var1.y * var10 + var1.z * var11
					+ var18;
			float var6 = var1.x * var16 + var1.y * var15 + var1.z * var14
					+ var19;
			float var20 = Math.abs(var2.x * var8 + var2.y * var9 + var2.z
					* var12);
			float var21 = Math.abs(var2.x * var13 + var2.y * var10 + var2.z
					* var11);
			float var22 = Math.abs(var2.x * var16 + var2.y * var15 + var2.z
					* var14);
			var4 /= var20;
			var5 /= var21;
			var6 /= var22;
			int var23 = this.objMesh.obbStart;
			float var24 = 1.0F / var20;
			float var25 = 1.0F / var21;
			float var26 = 1.0F / var22;
			float var27 = this.objMesh.xOrg[var23] * var24;
			float var28 = this.objMesh.yOrg[var23] * var25;
			float var29 = this.objMesh.zOrg[var23] * var26;
			float var30 = var27;
			float var31 = var28;
			float var32 = var29;
			int var33 = 1 + var23;
			int var34 = 8 + var23;

			for (int var35 = var33; var35 < var34; ++var35) {
				float var36 = this.objMesh.xOrg[var35] * var24;
				float var37 = this.objMesh.yOrg[var35] * var25;
				float var38 = this.objMesh.zOrg[var35] * var26;
				if (var36 < var27) {
					var27 = var36;
				} else if (var36 > var30) {
					var30 = var36;
				}

				if (var37 < var28) {
					var28 = var37;
				} else if (var37 > var31) {
					var31 = var37;
				}

				if (var38 < var29) {
					var29 = var38;
				} else if (var38 > var32) {
					var32 = var38;
				}
			}

			if (var4 + 1.0F < var27 || var4 - 1.0F > var30
					|| var5 + 1.0F < var28 || var5 - 1.0F > var31
					|| var6 + 1.0F < var29 || var6 - 1.0F > var32) {
				var3 = false;
			}

			return var3;
		}
	}

	public boolean sphereIntersectsAABB(SimpleVector var1, float var2) {
		return this.sphereIntersectsAABB(var1.toArray(), var2);
	}

	final boolean sphereIntersectsAABB(float[] var1, float var2) {
		if (!Config.useFastCollisionDetection) {
			return true;
		} else if (!this.hasBoundingBox) {
			return false;
		} else {
			boolean var3 = true;
			Matrix var7 = this.getInverseWorldTransformation();
			float var8 = var7.mat[0][0];
			float var9 = var7.mat[1][0];
			float var10 = var7.mat[1][1];
			float var11 = var7.mat[2][1];
			float var12 = var7.mat[2][0];
			float var13 = var7.mat[0][1];
			float var14 = var7.mat[2][2];
			float var15 = var7.mat[1][2];
			float var16 = var7.mat[0][2];
			float var17 = var7.mat[3][0];
			float var18 = var7.mat[3][1];
			float var19 = var7.mat[3][2];
			float var4 = var1[0] * var8 + var1[1] * var9 + var1[2] * var12
					+ var17;
			float var5 = var1[0] * var13 + var1[1] * var10 + var1[2] * var11
					+ var18;
			float var6 = var1[0] * var16 + var1[1] * var15 + var1[2] * var14
					+ var19;
			int var20 = this.objMesh.obbStart;
			float var21 = this.objMesh.xOrg[var20];
			float var22 = this.objMesh.yOrg[var20];
			float var23 = this.objMesh.zOrg[var20];
			float var24 = var21;
			float var25 = var22;
			float var26 = var23;

			for (int var27 = 1; var27 < 8; ++var27) {
				float var28 = this.objMesh.xOrg[var27 + var20];
				float var29 = this.objMesh.zOrg[var27 + var20];
				float var30 = this.objMesh.yOrg[var27 + var20];
				if (var28 < var21) {
					var21 = var28;
				} else if (var28 > var24) {
					var24 = var28;
				}

				if (var30 < var22) {
					var22 = var30;
				} else if (var30 > var25) {
					var25 = var30;
				}

				if (var29 < var23) {
					var23 = var29;
				} else if (var29 > var26) {
					var26 = var29;
				}
			}

			if (var4 + var2 < var21 || var4 - var2 > var24
					|| var5 + var2 < var22 || var5 - var2 > var25
					|| var6 + var2 < var23 || var6 - var2 > var26) {
				var3 = false;
			}

			return var3;
		}
	}

	final float collide(float[] var1, float[] var2, float var3, float var4) {
		return this.collide(var1, var2, var3, var4, true);
	}

	private final float collide(float[] var1, float[] var2, float var3,
			float var4, boolean var5) {
		double[] var7 = new double[3];
		float[] var8 = new float[3];
		double[] var9 = new double[3];
		double[] var10 = new double[3];
		double[] var11 = new double[3];
		double[] var12 = new double[3];
		double[] var13 = new double[3];
		float var14 = Config.collideOffset;
		if (this.optimizeColDet && this.largestPolygonSize != -1.0F && var5) {
			float var15 = this.largestPolygonSize + 2.0F + var3;
			if (var15 < var14) {
				var14 = var15;
			}
		}

		int[] var61 = null;
		int var16 = 0;
		float var17;
		float var18;
		float var19;
		float var22;
		if (!this.isMainWorld) {
			Matrix var20 = this.getWorldTransformation();
			Matrix var21 = this.getInverseWorldTransformation();
			var22 = var21.mat[0][0];
			float var23 = var21.mat[1][0];
			float var24 = var21.mat[1][1];
			float var25 = var21.mat[2][1];
			float var26 = var21.mat[2][0];
			float var27 = var21.mat[0][1];
			float var28 = var21.mat[2][2];
			float var29 = var21.mat[1][2];
			float var30 = var21.mat[0][2];
			float var31 = var21.mat[3][0];
			float var32 = var21.mat[3][1];
			float var33 = var21.mat[3][2];
			var7[0] = (double) (var2[0] * var22 + var2[1] * var23 + var2[2]
					* var26);
			var7[1] = (double) (var2[0] * var27 + var2[1] * var24 + var2[2]
					* var25);
			var7[2] = (double) (var2[0] * var30 + var2[1] * var29 + var2[2]
					* var28);
			var17 = var1[0] * var22 + var1[1] * var23 + var1[2] * var26 + var31;
			var18 = var1[0] * var27 + var1[1] * var24 + var1[2] * var25 + var32;
			var19 = var1[0] * var30 + var1[1] * var29 + var1[2] * var28 + var33;
			var8[0] = var17;
			var8[1] = var18;
			var8[2] = var19;
			if (Config.doPortalHsr && this.dynSectorDetect && !this.hasPortals
					&& this.objMesh.obbEnd - this.objMesh.obbStart == 7) {
				float[] var34 = new float[8];
				float[] var35 = new float[8];
				float[] var36 = new float[8];
				var22 = var20.mat[0][0];
				var23 = var20.mat[1][0];
				var24 = var20.mat[1][1];
				var25 = var20.mat[2][1];
				var26 = var20.mat[2][0];
				var27 = var20.mat[0][1];
				var28 = var20.mat[2][2];
				var29 = var20.mat[1][2];
				var30 = var20.mat[0][2];
				var31 = var20.mat[3][0];
				var32 = var20.mat[3][1];
				var33 = var20.mat[3][2];

				for (int var37 = 0; var37 < 8; ++var37) {
					float var38 = this.objMesh.zOrg[var37
							+ this.objMesh.obbStart];
					float var39 = this.objMesh.xOrg[var37
							+ this.objMesh.obbStart];
					float var40 = this.objMesh.yOrg[var37
							+ this.objMesh.obbStart];
					var34[var37] = var39 * var22 + var40 * var23 + var38
							* var26 + var31;
					var35[var37] = var39 * var27 + var40 * var24 + var38
							* var25 + var32;
					var36[var37] = var39 * var30 + var40 * var29 + var38
							* var28 + var33;
				}

				var61 = new int[Config.maxPortals];
				var61 = this.myWorld.portals.detectAllCoveredSectors(var61,
						var34, var35, var36);
				var16 = var61[0];
			}
		} else {
			var7[0] = (double) var2[0];
			var7[1] = (double) var2[1];
			var7[2] = (double) var2[2];
			var8[0] = var1[0];
			var8[1] = var1[1];
			var8[2] = var1[2];
			var17 = var8[0];
			var18 = var8[1];
			var19 = var8[2];
		}

		int var62 = 0;
		Object[] var63 = null;
		if (this.ocTree != null && !Config.doPortalHsr
				&& this.ocTree.getCollisionUse()) {
			var22 = var4 + var3;
			var63 = this.ocTree.getColliderLeafs(var8[0], var8[1], var8[2],
					var22 * this.ocTree.getRadiusMultiplier());
			var62 = ((Integer) var63[0]).intValue();
			if (var62 == 0) {
				return 1.0E12F;
			}
		}

		int var64 = 0;
		int var65 = 0;
		int var66 = 0;
		boolean var67 = false;
		float var6 = 1.0E12F;
		int var68 = -1;
		boolean var69 = false;
		boolean var71 = false;
		int[] var73 = new int[Math.max(Config.maxPortals, 1)];
		int var74 = 0;
		int var75;
		if (this.myWorld != null) {
			for (var75 = 1; var75 <= this.myWorld.portals.anzSectors; ++var75) {
				if (var4 == 1.0E12F
						|| var1[0] >= this.myWorld.portals.bounding[var75][0]
								- var4
						&& var1[0] <= this.myWorld.portals.bounding[var75][1]
								+ var4
						&& var1[1] >= this.myWorld.portals.bounding[var75][2]
								- var4
						&& var1[1] <= this.myWorld.portals.bounding[var75][3]
								+ var4
						&& var1[2] >= this.myWorld.portals.bounding[var75][4]
								- var4
						&& var1[2] <= this.myWorld.portals.bounding[var75][5]
								+ var4) {
					var73[var74] = var75;
					++var74;
				}
			}
		}

		if (var74 == 0) {
			var74 = 1;
			var73[0] = 0;
		}

		for (var75 = 0; var75 < var74; ++var75) {
			int var70 = 0;
			int var72 = 0;
			if (!this.isMainWorld && this.oneSectorOnly) {
				if ((this.dynSectorDetect || var73[var75] != this.singleSectorNumber)
						&& this.singleSectorNumber != 0) {
					if (this.dynSectorDetect) {
						if (var16 == 1) {
							if (var61[1] == var73[var75]) {
								var70 = 0;
								var72 = this.objMesh.anzTri;
								var75 = var74;
							}
						} else {
							for (int var76 = 1; var76 <= var16; ++var76) {
								if (var61[var76] == var73[var75]) {
									var70 = 0;
									var72 = this.objMesh.anzTri;
									var75 = var74;
									break;
								}
							}
						}
					}
				} else {
					var70 = 0;
					var72 = this.objMesh.anzTri;
					var75 = var74;
				}
			} else {
				var70 = this.sectorStartPoly[var73[var75]];
				var72 = this.sectorEndPoly[var73[var75]] + 1;
				if (var70 + 1 == var72) {
					var72 = 0;
					var70 = 0;
				}
			}

			boolean var77 = false;
			OcTreeNode[] var78 = null;
			int[] var79 = null;
			int var80 = 0;
			if (this.ocTree != null && !Config.doPortalHsr
					&& this.ocTree.getCollisionUse()) {
				var78 = (OcTreeNode[]) ((OcTreeNode[]) var63[1]);
				var77 = true;
			}

			int[] var81 = this.objMesh.coords;
			float[] var82 = this.objMesh.xOrg;
			float[] var83 = this.objMesh.yOrg;
			float[] var84 = this.objMesh.zOrg;

			do {
				if (var77) {
					var79 = var78[var80].getPolygons();
					var70 = 0;
					var72 = var78[var80].getPolyCount();
					++var80;
				}

				for (int var85 = var70; var85 < var72; ++var85) {
					int var41 = var85;
					if (var77) {
						var41 = var79[var85];
					}

					int var42 = var81[this.objMesh.points[var41][0]];
					double var43 = (double) var82[var42];
					double var45 = (double) var83[var42];
					double var47 = (double) var84[var42];
					if (var4 == 1.0E12F
							|| Math.abs(var43 - (double) var17) <= (double) var14
							&& Math.abs(var45 - (double) var18) <= (double) var14
							&& Math.abs(var47 - (double) var19) <= (double) var14) {
						int var49 = var81[this.objMesh.points[var41][2]];
						int var50 = var81[this.objMesh.points[var41][1]];
						var12[0] = (double) var82[var50] - var43;
						var12[1] = (double) var83[var50] - var45;
						var12[2] = (double) var84[var50] - var47;
						var13[0] = (double) var82[var49] - var43;
						var13[1] = (double) var83[var49] - var45;
						var13[2] = (double) var84[var49] - var47;
						Vectors.calcCross(var9, var7, var13);
						double var51 = Vectors.calcDot(var12, var9);
						if (var51 >= 1.000000013351432E-10D) {
							double var53 = 1.0D / var51;
							var10[0] = (double) var17 - var43;
							var10[1] = (double) var18 - var45;
							var10[2] = (double) var19 - var47;
							double var55 = Vectors.calcDot(var10, var9) * var53;
							if (var55 >= 0.0D && var55 <= 1.0D) {
								Vectors.calcCross(var11, var10, var12);
								double var57 = Vectors.calcDot(var7, var11)
										* var53;
								if (var57 >= 0.0D && var55 + var57 <= 1.0D) {
									double var59 = Vectors
											.calcDot(var13, var11) * var53;
									if (var59 < (double) var3 && var59 >= 0.0D) {
										var67 = true;
										var6 = (float) var59;
										var68 = var41;
									} else {
										var67 = false;
										if (var59 < (double) var6
												&& var59 >= 0.0D) {
											var6 = (float) var59;
											var68 = var41;
										}
									}

									++var64;
								}
							}
						}
					} else {
						++var65;
					}

					++var66;
					if (var67) {
						var75 = var74;
						break;
					}
				}
			} while (var77 && var80 < var62);
		}

		if (var68 != -1) {
			this.addPolygonID(var68);
		}

		return var6;
	}

	final float[] collideSpherical(float[] var1, float var2, float var3,
			boolean[] var4, boolean var5) {
		float[] var6 = new float[3];
		double[] var7 = new double[3];
		double[] var8 = new double[3];
		int[] var9 = null;
		int var10 = 0;
		Matrix var11 = null;
		float var21 = Config.collideOffset;
		if (this.optimizeColDet && this.largestPolygonSize != -1.0F) {
			float var22 = this.largestPolygonSize + var2 + 1.0F;
			if (var22 < var21) {
				var21 = var22;
			}
		}

		float var12;
		float var13;
		float var14;
		float var35;
		float var36;
		float var37;
		float var38;
		float var39;
		float var40;
		int var44;
		float var46;
		float var47;
		if (!this.isMainWorld) {
			var11 = this.getWorldTransformation();
			Matrix var28 = this.getInverseWorldTransformation();
			float var29 = var28.mat[0][0];
			float var30 = var28.mat[1][0];
			float var31 = var28.mat[1][1];
			float var32 = var28.mat[2][1];
			float var33 = var28.mat[2][0];
			float var34 = var28.mat[0][1];
			var35 = var28.mat[2][2];
			var36 = var28.mat[1][2];
			var37 = var28.mat[0][2];
			var38 = var28.mat[3][0];
			var39 = var28.mat[3][1];
			var40 = var28.mat[3][2];
			var12 = var1[0] * var29 + var1[1] * var30 + var1[2] * var33 + var38;
			var13 = var1[0] * var34 + var1[1] * var31 + var1[2] * var32 + var39;
			var14 = var1[0] * var37 + var1[1] * var36 + var1[2] * var35 + var40;
			var6[0] = var12;
			var6[1] = var13;
			var6[2] = var14;
			if (Config.doPortalHsr && this.dynSectorDetect && !this.hasPortals
					&& this.objMesh.obbEnd - this.objMesh.obbStart == 7) {
				float[] var41 = new float[8];
				float[] var42 = new float[8];
				float[] var43 = new float[8];
				var29 = var11.mat[0][0];
				var30 = var11.mat[1][0];
				var31 = var11.mat[1][1];
				var32 = var11.mat[2][1];
				var33 = var11.mat[2][0];
				var34 = var11.mat[0][1];
				var35 = var11.mat[2][2];
				var36 = var11.mat[1][2];
				var37 = var11.mat[0][2];
				var38 = var11.mat[3][0];
				var39 = var11.mat[3][1];
				var40 = var11.mat[3][2];

				for (var44 = 0; var44 < 8; ++var44) {
					int var45 = var44 + this.objMesh.obbStart;
					var46 = this.objMesh.zOrg[var45];
					var47 = this.objMesh.xOrg[var45];
					float var48 = this.objMesh.yOrg[var45];
					var41[var44] = var47 * var29 + var48 * var30 + var46
							* var33 + var38;
					var42[var44] = var47 * var34 + var48 * var31 + var46
							* var32 + var39;
					var43[var44] = var47 * var37 + var48 * var36 + var46
							* var35 + var40;
				}

				var9 = new int[Config.maxPortals];
				var9 = this.myWorld.portals.detectAllCoveredSectors(var9,
						var41, var42, var43);
				var10 = var9[0];
			}
		} else {
			var6[0] = var1[0];
			var6[1] = var1[1];
			var6[2] = var1[2];
			var12 = var6[0];
			var13 = var6[1];
			var14 = var6[2];
		}

		int var86 = 0;
		Object[] var87 = null;
		if (this.ocTree != null && !Config.doPortalHsr
				&& this.ocTree.getCollisionUse()) {
			var87 = this.ocTree.getColliderLeafs(var6[0], var6[1], var6[2],
					var2 * this.ocTree.getRadiusMultiplier());
			var86 = ((Integer) var87[0]).intValue();
			if (var86 == 0) {
				var6[0] = var1[0];
				var6[1] = var1[1];
				var6[2] = var1[2];
				return var6;
			}
		}

		boolean var88 = false;
		boolean var89 = false;
		boolean var91 = false;
		int[] var93 = new int[Math.max(Config.maxPortals, 1)];
		int var94 = 0;
		int var95;
		if (this.myWorld != null && Config.doPortalHsr) {
			for (var95 = 1; var95 <= this.myWorld.portals.anzSectors; ++var95) {
				if (var3 == 1.0E12F
						|| var1[0] >= this.myWorld.portals.bounding[var95][0]
								- var3
						&& var1[0] <= this.myWorld.portals.bounding[var95][1]
								+ var3
						&& var1[1] >= this.myWorld.portals.bounding[var95][2]
								- var3
						&& var1[1] <= this.myWorld.portals.bounding[var95][3]
								+ var3
						&& var1[2] >= this.myWorld.portals.bounding[var95][4]
								- var3
						&& var1[2] <= this.myWorld.portals.bounding[var95][5]
								+ var3) {
					var93[var94] = var95;
					++var94;
				}
			}
		}

		if (var94 == 0) {
			var94 = 1;
			var93[0] = 0;
		}

		float var108;
		for (var95 = 0; var95 < var94; ++var95) {
			int var90 = 0;
			int var92 = 0;
			if (!this.isMainWorld && this.oneSectorOnly) {
				if ((this.dynSectorDetect || var93[var95] != this.singleSectorNumber)
						&& this.singleSectorNumber != 0) {
					if (this.dynSectorDetect) {
						if (var10 == 1) {
							if (var9[1] == var93[var95]) {
								var90 = 0;
								var92 = this.objMesh.anzTri;
								var95 = var94;
							}
						} else {
							for (int var96 = 1; var96 <= var10; ++var96) {
								if (var9[var96] == var93[var95]) {
									var90 = 0;
									var92 = this.objMesh.anzTri;
									var95 = var94;
									break;
								}
							}
						}
					}
				} else {
					var90 = 0;
					var92 = this.objMesh.anzTri;
					var95 = var94;
				}
			} else {
				var90 = this.sectorStartPoly[var93[var95]];
				var92 = this.sectorEndPoly[var93[var95]] + 1;
				if (var90 + 1 == var92) {
					var92 = 0;
					var90 = 0;
				}
			}

			double var97 = 0.0D;
			boolean var98 = false;
			OcTreeNode[] var99 = null;
			int[] var100 = null;
			int var101 = 0;
			if (this.ocTree != null && !Config.doPortalHsr
					&& this.ocTree.getCollisionUse()) {
				var99 = (OcTreeNode[]) ((OcTreeNode[]) var87[1]);
				var98 = true;
			}

			do {
				if (var98) {
					var100 = var99[var101].getPolygons();
					var90 = 0;
					var92 = var99[var101].getPolyCount();
					++var101;
				}

				for (int var103 = var90; var103 < var92; ++var103) {
					int var105 = var103;
					if (var98) {
						var105 = var100[var103];
					}

					var44 = this.objMesh.coords[this.objMesh.points[var105][0]];
					var108 = this.objMesh.xOrg[var44];
					var46 = this.objMesh.yOrg[var44];
					var47 = this.objMesh.zOrg[var44];
					boolean var109 = false;
					if (var3 == 1.0E12F || Math.abs(var108 - var12) <= var21
							&& Math.abs(var46 - var13) <= var21
							&& Math.abs(var47 - var14) <= var21) {
						int var49 = this.objMesh.coords[this.objMesh.points[var105][2]];
						int var50 = this.objMesh.coords[this.objMesh.points[var105][1]];
						double var51 = (double) (this.objMesh.xOrg[var50] - var108);
						double var53 = (double) (this.objMesh.yOrg[var50] - var46);
						double var55 = (double) (this.objMesh.zOrg[var50] - var47);
						double var57 = (double) (this.objMesh.xOrg[var49] - var108);
						double var59 = (double) (this.objMesh.yOrg[var49] - var46);
						double var61 = (double) (this.objMesh.zOrg[var49] - var47);
						double var15 = var53 * var61 - var55 * var59;
						double var17 = var55 * var57 - var51 * var61;
						double var19 = var51 * var59 - var53 * var57;
						double var63 = Math.sqrt(var15 * var15 + var17 * var17
								+ var19 * var19);
						var15 /= var63;
						var17 /= var63;
						var19 /= var63;
						double var65 = var15
								* (double) var6[0]
								+ var17
								* (double) var6[1]
								+ var19
								* (double) var6[2]
								- (var15 * (double) var108 + var17
										* (double) var46 + var19
										* (double) var47);
						if (Math.abs(var65) < (double) var2) {
							double var85 = (double) var6[0] - var15 * var65;
							double var24 = (double) var6[1] - var17 * var65;
							double var26 = (double) var6[2] - var19 * var65;
							double var67 = 0.0D;

							int var69;
							int var70;
							float var71;
							float var72;
							float var73;
							int var74;
							for (var69 = 0; var69 < 3; ++var69) {
								var70 = this.objMesh.coords[this.objMesh.points[var105][var69]];
								var71 = this.objMesh.xOrg[var70];
								var72 = this.objMesh.yOrg[var70];
								var73 = this.objMesh.zOrg[var70];
								var7[0] = (double) var71 - var85;
								var7[1] = (double) var72 - var24;
								var7[2] = (double) var73 - var26;
								var74 = (var69 + 1) % 3;
								var70 = this.objMesh.coords[this.objMesh.points[var105][var74]];
								var71 = this.objMesh.xOrg[var70];
								var72 = this.objMesh.yOrg[var70];
								var73 = this.objMesh.zOrg[var70];
								var8[0] = (double) var71 - var85;
								var8[1] = (double) var72 - var24;
								var8[2] = (double) var73 - var26;
								double var75 = Vectors.calcDot(var7, var8);
								double var77 = Math
										.sqrt(var7[0] * var7[0] + var7[1]
												* var7[1] + var7[2] * var7[2]);
								double var79 = Math
										.sqrt(var8[0] * var8[0] + var8[1]
												* var8[1] + var8[2] * var8[2]);
								double var81 = var77 * var79;
								double var83 = Math.acos(var75 / var81);
								if (Double.isNaN(var83)) {
									var83 = 0.0D;
								}

								var67 += var83;
								if (var67 >= 6.220353454107791D) {
									break;
								}
							}

							if (var67 >= 6.220353454107791D) {
								var109 = true;
							} else {
								for (var69 = 0; var69 < 3; ++var69) {
									var70 = this.objMesh.coords[this.objMesh.points[var105][var69]];
									var71 = this.objMesh.xOrg[var70];
									var72 = this.objMesh.yOrg[var70];
									var73 = this.objMesh.zOrg[var70];
									var7[0] = (double) (var6[0] - var71);
									var7[1] = (double) (var6[1] - var72);
									var7[2] = (double) (var6[2] - var73);
									var74 = (var69 + 1) % 3;
									var70 = this.objMesh.coords[this.objMesh.points[var105][var74]];
									float var111 = this.objMesh.xOrg[var70];
									float var76 = this.objMesh.yOrg[var70];
									float var112 = this.objMesh.zOrg[var70];
									var8[0] = (double) (var111 - var71);
									var8[1] = (double) (var76 - var72);
									var8[2] = (double) (var112 - var73);
									var63 = Math.sqrt(var8[0] * var8[0]
											+ var8[1] * var8[1] + var8[2]
											* var8[2]);
									var8[0] /= var63;
									var8[1] /= var63;
									var8[2] /= var63;
									double var78 = Vectors.calcDot(var7, var8);
									if (var78 <= 0.0D) {
										var85 = (double) var71;
										var24 = (double) var72;
										var26 = (double) var73;
									} else {
										var97 = Math
												.sqrt((double) ((var71 - var111)
														* (var71 - var111)
														+ (var72 - var76)
														* (var72 - var76) + (var73 - var112)
														* (var73 - var112)));
										if (var78 >= var97) {
											var85 = (double) var111;
											var24 = (double) var76;
											var26 = (double) var112;
										} else {
											var7[0] = var8[0] * var78;
											var7[1] = var8[1] * var78;
											var7[2] = var8[2] * var78;
											var85 = (double) var71 + var7[0];
											var24 = (double) var72 + var7[1];
											var26 = (double) var73 + var7[2];
										}
									}

									var97 = Math
											.sqrt((var85 - (double) var6[0])
													* (var85 - (double) var6[0])
													+ (var24 - (double) var6[1])
													* (var24 - (double) var6[1])
													+ (var26 - (double) var6[2])
													* (var26 - (double) var6[2]));
									float var80 = var2;
									if (var5) {
										var80 = var2 * Config.collideEdgeMul;
									}

									if (var97 < (double) var80) {
										var109 = true;
										break;
									}
								}
							}

							if (var109) {
								this.addPolygonID(var105);
								double var110 = (double) var2 - var65;
								var88 = true;
								var6[0] = (float) ((double) var6[0] + var15
										* var110);
								var6[1] = (float) ((double) var6[1] + var17
										* var110);
								var6[2] = (float) ((double) var6[2] + var19
										* var110);
							}
						}
					}
				}
			} while (var98 && var101 < var86);
		}

		if (!this.isMainWorld) {
			var35 = var11.mat[0][0];
			var36 = var11.mat[1][0];
			var37 = var11.mat[1][1];
			var38 = var11.mat[2][1];
			var39 = var11.mat[2][0];
			var40 = var11.mat[0][1];
			float var102 = var11.mat[2][2];
			float var104 = var11.mat[1][2];
			float var106 = var11.mat[0][2];
			float var107 = var11.mat[3][0];
			var108 = var11.mat[3][1];
			var46 = var11.mat[3][2];
			var12 = var6[0] * var35 + var6[1] * var36 + var6[2] * var39
					+ var107;
			var13 = var6[0] * var40 + var6[1] * var37 + var6[2] * var38
					+ var108;
			var14 = var6[0] * var106 + var6[1] * var104 + var6[2] * var102
					+ var46;
			var6[0] = var12;
			var6[1] = var13;
			var6[2] = var14;
		}

		var4[0] |= var88;
		return var6;
	}

	final void collideEllipsoid(CollisionInfo var1, float var2) {
		var1.setScale(this.getScale());
		Matrix var3 = null;
		if (var1.addTransMat != null) {
			var3 = var1.addTransMat.cloneMatrix();
		}

		Matrix var4 = null;
		Matrix var5 = null;
		boolean var6 = false;
		float var7 = 0.0F;
		float var8 = 0.0F;
		float var9 = 0.0F;
		float var10 = 0.0F;
		float var11 = 0.0F;
		float var12 = 0.0F;
		float var13 = 0.0F;
		float var14 = 0.0F;
		float var15 = 0.0F;
		float var16 = 0.0F;
		float var17 = 0.0F;
		float var18 = 0.0F;
		int[] var19 = null;
		int var20 = 0;
		Matrix var21 = null;
		if (var3 != null) {
			var6 = true;
			var4 = var3.invert();
			Matrix var22 = this.getWorldTransformation();
			var22.matMul(var4);
			var5 = var1.addRotMat.invert3x3();
			var7 = var22.mat[0][0];
			var8 = var22.mat[1][0];
			var9 = var22.mat[1][1];
			var10 = var22.mat[2][1];
			var11 = var22.mat[2][0];
			var12 = var22.mat[0][1];
			var13 = var22.mat[2][2];
			var14 = var22.mat[1][2];
			var15 = var22.mat[0][2];
			var16 = var22.mat[3][0];
			var17 = var22.mat[3][1];
			var18 = var22.mat[3][2];
		}

		SimpleVector var78 = new SimpleVector(var1.r3Pos);
		SimpleVector var23 = new SimpleVector(var78);
		SimpleVector var24 = new SimpleVector(var1.r3Velocity);
		SimpleVector var25 = new SimpleVector();
		SimpleVector var26 = new SimpleVector();
		SimpleVector var27 = null;
		SimpleVector var28 = null;
		SimpleVector var29 = null;
		boolean var30 = this.ocTree != null && !Config.doPortalHsr
				&& this.ocTree.getCollisionUse();
		float var33;
		int var47;
		int var48;
		if (this.isMainWorld && !var6) {
			var23.x = var78.x;
			var23.y = var78.y;
			var23.z = var78.z;
			var28 = var23;
			var29 = var24;
		} else {
			Matrix var31 = null;
			float var32;
			float var34;
			float var35;
			float var36;
			float var37;
			float var38;
			float var39;
			float var40;
			float var41;
			float var42;
			float var43;
			float var44;
			float var45;
			float var46;
			if (!var6 || var30) {
				var21 = this.getWorldTransformation();
				var31 = this.getInverseWorldTransformation();
				var32 = var31.mat[0][0];
				var33 = var31.mat[1][0];
				var34 = var31.mat[1][1];
				var35 = var31.mat[2][1];
				var36 = var31.mat[2][0];
				var37 = var31.mat[0][1];
				var38 = var31.mat[2][2];
				var39 = var31.mat[1][2];
				var40 = var31.mat[0][2];
				var41 = var31.mat[3][0];
				var42 = var31.mat[3][1];
				var43 = var31.mat[3][2];
				var23.x = var78.x * var32 + var78.y * var33 + var78.z * var36
						+ var41;
				var23.y = var78.x * var37 + var78.y * var34 + var78.z * var35
						+ var42;
				var23.z = var78.x * var40 + var78.y * var39 + var78.z * var38
						+ var43;
				var44 = var24.x * var32 + var24.y * var33 + var24.z * var36;
				var45 = var24.x * var37 + var24.y * var34 + var24.z * var35;
				var46 = var24.x * var40 + var24.y * var39 + var24.z * var38;
				var24.x = var44;
				var24.y = var45;
				var24.z = var46;
				if (var6) {
					var28 = new SimpleVector(var23);
					var29 = new SimpleVector(var24);
				} else {
					var28 = var23;
					var29 = var24;
				}
			}

			if (var6) {
				var24 = new SimpleVector(var1.r3Velocity);
				var32 = var4.mat[0][0];
				var33 = var4.mat[1][0];
				var34 = var4.mat[1][1];
				var35 = var4.mat[2][1];
				var36 = var4.mat[2][0];
				var37 = var4.mat[0][1];
				var38 = var4.mat[2][2];
				var39 = var4.mat[1][2];
				var40 = var4.mat[0][2];
				var41 = var4.mat[3][0];
				var42 = var4.mat[3][1];
				var43 = var4.mat[3][2];
				var23.x = var78.x * var32 + var78.y * var33 + var78.z * var36
						+ var41;
				var23.y = var78.x * var37 + var78.y * var34 + var78.z * var35
						+ var42;
				var23.z = var78.x * var40 + var78.y * var39 + var78.z * var38
						+ var43;
				var24.matMul(var5);
			}

			if (!this.isMainWorld && Config.doPortalHsr && this.dynSectorDetect
					&& !this.hasPortals
					&& this.objMesh.obbEnd - this.objMesh.obbStart == 7) {
				float[] var80 = new float[8];
				float[] var82 = new float[8];
				float[] var84 = new float[8];
				var35 = var21.mat[0][0];
				var36 = var21.mat[1][0];
				var37 = var21.mat[1][1];
				var38 = var21.mat[2][1];
				var39 = var21.mat[2][0];
				var40 = var21.mat[0][1];
				var41 = var21.mat[2][2];
				var42 = var21.mat[1][2];
				var43 = var21.mat[0][2];
				var44 = var21.mat[3][0];
				var45 = var21.mat[3][1];
				var46 = var21.mat[3][2];

				for (var47 = 0; var47 < 8; ++var47) {
					var48 = var47 + this.objMesh.obbStart;
					float var49 = this.objMesh.zOrg[var48];
					float var50 = this.objMesh.xOrg[var48];
					float var51 = this.objMesh.yOrg[var48];
					var80[var47] = var50 * var35 + var51 * var36 + var49
							* var39 + var44;
					var82[var47] = var50 * var40 + var51 * var37 + var49
							* var38 + var45;
					var84[var47] = var50 * var43 + var51 * var42 + var49
							* var41 + var46;
				}

				var19 = new int[Config.maxPortals];
				var19 = this.myWorld.portals.detectAllCoveredSectors(var19,
						var80, var82, var84);
				var20 = var19[0];
			}
		}

		int var79 = 0;
		Object[] var81 = null;
		if (var30) {
			var33 = (float) Math.sqrt((double) (var29.x * var29.x + var29.y
					* var29.y + var29.z * var29.z));
			var81 = this.ocTree.getColliderLeafs(
					var28.x,
					var28.y,
					var28.z,
					(var1.getMaxRadius() + var33)
							* this.ocTree.getRadiusMultiplier());
			var79 = ((Integer) var81[0]).intValue();
			if (var79 == 0) {
				return;
			}
		}

		boolean var83 = false;
		boolean var86 = false;
		int[] var88 = new int[Math.max(Config.maxPortals, 1)];
		int var89 = 0;
		int var90;
		if (this.myWorld != null && Config.doPortalHsr) {
			for (var90 = 1; var90 <= this.myWorld.portals.anzSectors; ++var90) {
				if (var2 == 1.0E12F
						|| var78.x >= this.myWorld.portals.bounding[var90][0]
								- var2
						&& var78.x <= this.myWorld.portals.bounding[var90][1]
								+ var2
						&& var78.y >= this.myWorld.portals.bounding[var90][2]
								- var2
						&& var78.y <= this.myWorld.portals.bounding[var90][3]
								+ var2
						&& var78.z >= this.myWorld.portals.bounding[var90][4]
								- var2
						&& var78.z <= this.myWorld.portals.bounding[var90][5]
								+ var2) {
					var88[var89] = var90;
					++var89;
				}
			}
		}

		if (var89 == 0) {
			var89 = 1;
			var88[0] = 0;
		}

		for (var90 = 0; var90 < var89; ++var90) {
			int var85 = 0;
			int var87 = 0;
			if (!this.isMainWorld && this.oneSectorOnly) {
				if ((this.dynSectorDetect || var88[var90] != this.singleSectorNumber)
						&& this.singleSectorNumber != 0) {
					if (this.dynSectorDetect) {
						if (var20 == 1) {
							if (var19[1] == var88[var90]) {
								var85 = 0;
								var87 = this.objMesh.anzTri;
								var90 = var89;
							}
						} else {
							for (int var91 = 1; var91 <= var20; ++var91) {
								if (var19[var91] == var88[var90]) {
									var85 = 0;
									var87 = this.objMesh.anzTri;
									var90 = var89;
									break;
								}
							}
						}
					}
				} else {
					var85 = 0;
					var87 = this.objMesh.anzTri;
					var90 = var89;
				}
			} else {
				var85 = this.sectorStartPoly[var88[var90]];
				var87 = this.sectorEndPoly[var88[var90]] + 1;
				if (var85 + 1 == var87) {
					var87 = 0;
					var85 = 0;
				}
			}

			OcTreeNode[] var92 = null;
			int[] var93 = null;
			int var94 = 0;
			if (var30) {
				var92 = (OcTreeNode[]) ((OcTreeNode[]) var81[1]);
			}

			var26.x = var23.x * var1.invERadius.x;
			var26.y = var23.y * var1.invERadius.y;
			var26.z = var23.z * var1.invERadius.z;
			var25.x = var24.x * var1.invERadius.x;
			var25.y = var24.y * var1.invERadius.y;
			var25.z = var24.z * var1.invERadius.z;
			var27 = var25.normalize();
			SimpleVector var95 = new SimpleVector();
			SimpleVector var96 = new SimpleVector();
			SimpleVector var97 = new SimpleVector();
			SimpleVector var98 = new SimpleVector();
			Plane var99 = new Plane();
			float[] var100 = new float[1];

			do {
				if (var30) {
					var93 = var92[var94].getPolygons();
					var85 = 0;
					var87 = var92[var94].getPolyCount();
					++var94;
				}

				for (var47 = var85; var47 < var87; ++var47) {
					var48 = var47;
					if (var30) {
						var48 = var93[var47];
					}

					int var101 = this.objMesh.coords[this.objMesh.points[var48][0]];
					int var102 = this.objMesh.coords[this.objMesh.points[var48][1]];
					int var103 = this.objMesh.coords[this.objMesh.points[var48][2]];
					float var53;
					float var54;
					float var56;
					if (var6) {
						float var52 = this.objMesh.xOrg[var101];
						var53 = this.objMesh.yOrg[var101];
						var54 = this.objMesh.zOrg[var101];
						float var55 = var52 * var7 + var53 * var8 + var54
								* var11 + var16;
						var56 = var52 * var12 + var53 * var9 + var54 * var10
								+ var17;
						float var57 = var52 * var15 + var53 * var14 + var54
								* var13 + var18;
						var95.x = var55 * var1.invERadius.x;
						var95.y = var56 * var1.invERadius.y;
						var95.z = var57 * var1.invERadius.z;
						var52 = this.objMesh.xOrg[var102];
						var53 = this.objMesh.yOrg[var102];
						var54 = this.objMesh.zOrg[var102];
						var55 = var52 * var7 + var53 * var8 + var54 * var11
								+ var16;
						var56 = var52 * var12 + var53 * var9 + var54 * var10
								+ var17;
						var57 = var52 * var15 + var53 * var14 + var54 * var13
								+ var18;
						var96.x = var55 * var1.invERadius.x;
						var96.y = var56 * var1.invERadius.y;
						var96.z = var57 * var1.invERadius.z;
						var52 = this.objMesh.xOrg[var103];
						var53 = this.objMesh.yOrg[var103];
						var54 = this.objMesh.zOrg[var103];
						var55 = var52 * var7 + var53 * var8 + var54 * var11
								+ var16;
						var56 = var52 * var12 + var53 * var9 + var54 * var10
								+ var17;
						var57 = var52 * var15 + var53 * var14 + var54 * var13
								+ var18;
						var97.x = var55 * var1.invERadius.x;
						var97.y = var56 * var1.invERadius.y;
						var97.z = var57 * var1.invERadius.z;
					} else {
						var95.x = this.objMesh.xOrg[var101] * var1.invERadius.x;
						var95.y = this.objMesh.yOrg[var101] * var1.invERadius.y;
						var95.z = this.objMesh.zOrg[var101] * var1.invERadius.z;
						var96.x = this.objMesh.xOrg[var102] * var1.invERadius.x;
						var96.y = this.objMesh.yOrg[var102] * var1.invERadius.y;
						var96.z = this.objMesh.zOrg[var102] * var1.invERadius.z;
						var97.x = this.objMesh.xOrg[var103] * var1.invERadius.x;
						var97.y = this.objMesh.yOrg[var103] * var1.invERadius.y;
						var97.z = this.objMesh.zOrg[var103] * var1.invERadius.z;
					}

					var99.setTo(var95, var96, var97);
					boolean var104 = true;
					if (var99.isFrontFacingTo(var27)) {
						var53 = 0.0F;
						var54 = 0.0F;
						boolean var105 = false;
						var56 = var99.distanceTo(var26);
						SimpleVector var106 = var99.normal;
						float var58 = var106.x * var25.x + var106.y * var25.y
								+ var106.z * var25.z;
						if (var58 == 0.0F) {
							if (Math.abs(var56) >= 1.0F) {
								var104 = false;
							} else {
								var105 = true;
								var53 = 0.0F;
								var54 = 1.0F;
							}
						} else {
							var53 = (-1.0F - var56) / var58;
							var54 = (1.0F - var56) / var58;
							if (var53 > var54) {
								float var59 = var53;
								var53 = var54;
								var54 = var59;
							}

							if (var53 > 1.0F || var54 < 0.0F) {
								var104 = false;
							}

							if (var53 < -1.0F) {
								var53 = 0.0F;
							}

							if (var54 < 0.0F) {
								var54 = 0.0F;
							}

							if (var53 > 1.0F) {
								var53 = 1.0F;
							}

							if (var54 > 1.0F) {
								var54 = 1.0F;
							}
						}

						if (var104) {
							SimpleVector var107 = null;
							boolean var60 = false;
							float var61 = 1.0F;
							SimpleVector var62;
							if (!var105) {
								var62 = new SimpleVector(var25);
								var62.scalarMul(var53);
								SimpleVector var63 = var26
										.calcSub(var99.normal);
								var63.add(var62);
								SimpleVector var64 = var63.calcSub(var26);
								if (this.checkPointInTriangle(var64, var26,
										var95, var96, var97)) {
									var60 = true;
									var61 = var53;
									var107 = var63;
								}
							}

							if (!var60) {
								var62 = var26;
								float var108 = var25.x * var25.x + var25.y
										* var25.y + var25.z * var25.z;
								float var109 = 0.0F;
								float var65 = 0.0F;
								float var66 = 0.0F;
								float var67 = 0.0F;
								float var68 = 0.0F;
								float var69 = 0.0F;
								SimpleVector var70 = null;

								int var71;
								for (var71 = 0; var71 < 3; ++var71) {
									switch (var71) {
									case 0:
										var67 = var95.x;
										var68 = var95.y;
										var69 = var95.z;
										var70 = var95;
										break;
									case 1:
										var67 = var96.x;
										var68 = var96.y;
										var69 = var96.z;
										var70 = var96;
										break;
									case 2:
										var67 = var97.x;
										var68 = var97.y;
										var69 = var97.z;
										var70 = var97;
									}

									var98.x = var62.x - var67;
									var98.y = var62.y - var68;
									var98.z = var62.z - var69;
									var65 = 2.0F * (var25.x * var98.x + var25.y
											* var98.y + var25.z * var98.z);
									var98.x = var67 - var62.x;
									var98.y = var68 - var62.y;
									var98.z = var69 - var62.z;
									var66 = var98.x * var98.x + var98.y
											* var98.y + var98.z * var98.z
											- 1.0F;
									if (this.getLowestRoot(var108, var65,
											var66, var61, var100)) {
										var61 = var100[0];
										var60 = true;
										var107 = new SimpleVector(var70);
									}
								}

								for (var71 = 0; var71 < 3; ++var71) {
									switch (var71) {
									case 0:
										var98.x = var96.x - var95.x;
										var98.y = var96.y - var95.y;
										var98.z = var96.z - var95.z;
										var70 = var95;
										break;
									case 1:
										var98.x = var97.x - var96.x;
										var98.y = var97.y - var96.y;
										var98.z = var97.z - var96.z;
										var70 = var96;
										break;
									case 2:
										var98.x = var95.x - var97.x;
										var98.y = var95.y - var97.y;
										var98.z = var95.z - var97.z;
										var70 = var97;
									}

									SimpleVector var73 = var70.calcSub(var62);
									float var74 = var98.x * var98.x + var98.y
											* var98.y + var98.z * var98.z;
									float var75 = var98.x * var25.x + var98.y
											* var25.y + var98.z * var25.z;
									float var76 = var98.x * var73.x + var98.y
											* var73.y + var98.z * var73.z;
									var109 = var74 * -var108 + var75 * var75;
									var65 = var74
											* 2.0F
											* (var25.x * var73.x + var25.y
													* var73.y + var25.z
													* var73.z) - 2.0F * var75
											* var76;
									var66 = var74
											* (1.0F - (var73.x * var73.x
													+ var73.y * var73.y + var73.z
													* var73.z)) + var76 * var76;
									if (this.getLowestRoot(var109, var65,
											var66, var61, var100)) {
										float var77 = (var75 * var100[0] - var76)
												/ var74;
										if (var77 >= 0.0F && var77 <= 1.0F) {
											var61 = var100[0];
											var60 = true;
											var107 = new SimpleVector(var98);
											var107.scalarMul(var77);
											var107.add(var70);
										}
									}
								}
							}

							if (var60) {
								this.addPolygonID(var48);
								if (!var1.foundCollision
										|| var61 <= var1.nearestDistance) {
									var1.nearestDistance = var61;
									var1.intersectionPoint = var107;
									var1.foundCollision = true;
									var1.collision = true;
									var1.eSpaceBasePoint = var26;
									var1.eSpaceVelocity = var25;
									var1.collisionObject = this;
									var1.isPartOfCollision = true;
								}
							}
						}
					}
				}
			} while (var30 && var94 < var79);
		}

	}

	private final boolean checkPointInTriangle(SimpleVector var1,
			SimpleVector var2, SimpleVector var3, SimpleVector var4,
			SimpleVector var5) {
		if (this.pvecPoint == null) {
			this.pvecPoint = new float[3];
		}

		float[] var6 = this.pvecPoint;
		float var7 = var3.x;
		float var8 = var3.y;
		float var9 = var3.z;
		float var10 = var4.x - var7;
		float var11 = var4.y - var8;
		float var12 = var4.z - var9;
		float var13 = var5.x - var7;
		float var14 = var5.y - var8;
		float var15 = var5.z - var9;
		Vectors.calcCross(var6, var1.x, var1.y, var1.z, var13, var14, var15);
		float var16 = Vectors.calcDot(var10, var11, var12, var6);
		if (var16 >= 1.0E-17F) {
			float var17 = 1.0F / var16;
			var13 = var2.x - var7;
			var14 = var2.y - var8;
			var15 = var2.z - var9;
			float var18 = Vectors.calcDot(var13, var14, var15, var6) * var17;
			if ((double) var18 >= 0.0D && var18 <= 1.0F) {
				Vectors.calcCross(var6, var13, var14, var15, var10, var11,
						var12);
				float var19 = Vectors.calcDot(var1.x, var1.y, var1.z, var6)
						* var17;
				if ((double) var19 >= 0.0D && (double) (var18 + var19) <= 1.0D) {
					return true;
				}
			}
		}

		return false;
	}

	private final boolean getLowestRoot(float var1, float var2, float var3,
			float var4, float[] var5) {
		float var6 = var2 * var2 - 4.0F * var1 * var3;
		if (var6 < 0.0F) {
			return false;
		} else {
			float var7 = (float) Math.sqrt((double) var6);
			float var8 = (-var2 - var7) / (2.0F * var1);
			float var9 = (-var2 + var7) / (2.0F * var1);
			if (var8 > var9) {
				float var10 = var9;
				var9 = var8;
				var8 = var10;
			}

			if (var8 > 0.0F && var8 < var4) {
				var5[0] = var8;
				return true;
			} else if (var9 > 0.0F && var9 < var4) {
				var5[0] = var9;
				return true;
			} else {
				return false;
			}
		}
	}

	final SimpleVector reverseTransform(SimpleVector var1, boolean var2) {
		Matrix var3 = this.getWorldTransformation();
		if (!this.isMainWorld) {
			float var4 = var3.mat[0][0];
			float var5 = var3.mat[1][0];
			float var6 = var3.mat[1][1];
			float var7 = var3.mat[2][1];
			float var8 = var3.mat[2][0];
			float var9 = var3.mat[0][1];
			float var10 = var3.mat[2][2];
			float var11 = var3.mat[1][2];
			float var12 = var3.mat[0][2];
			float var13 = var3.mat[3][0];
			float var14 = var3.mat[3][1];
			float var15 = var3.mat[3][2];
			float var16 = var1.x * var4 + var1.y * var5 + var1.z * var8;
			float var17 = var1.x * var9 + var1.y * var6 + var1.z * var7;
			float var18 = var1.x * var12 + var1.y * var11 + var1.z * var10;
			if (var2) {
				var16 += var13;
				var17 += var14;
				var18 += var15;
			}

			return new SimpleVector(var16, var17, var18);
		} else {
			return new SimpleVector(var1);
		}
	}

	public int addTriangle(SimpleVector var1, SimpleVector var2,
			SimpleVector var3) {
		return this.addTriangle(var1.x, var1.y, var1.z, 0.0F, 0.0F, var2.x,
				var2.y, var2.z, 0.0F, 0.0F, var3.x, var3.y, var3.z, 0.0F, 0.0F,
				-1, 0, false);
	}

	final int addTriangle(float var1, float var2, float var3, float var4,
			float var5, float var6, float var7, float var8, float var9) {
		return this.addTriangle(var1, var2, var3, 0.0F, 0.0F, var4, var5, var6,
				0.0F, 0.0F, var7, var8, var9, 0.0F, 0.0F, -1, 0, false);
	}

	public int addTriangle(SimpleVector var1, float var2, float var3,
			SimpleVector var4, float var5, float var6, SimpleVector var7,
			float var8, float var9) {
		return this.addTriangle(var1.x, var1.y, var1.z, var2, var3, var4.x,
				var4.y, var4.z, var5, var6, var7.x, var7.y, var7.z, var8, var9,
				-1, 0, false);
	}

	final int addTriangle(float var1, float var2, float var3, float var4,
			float var5, float var6, float var7, float var8, float var9,
			float var10, float var11, float var12, float var13, float var14,
			float var15) {
		return this.addTriangle(var1, var2, var3, var4, var5, var6, var7, var8,
				var9, var10, var11, var12, var13, var14, var15, -1, 0, false);
	}

	public int addTriangle(SimpleVector var1, float var2, float var3,
			SimpleVector var4, float var5, float var6, SimpleVector var7,
			float var8, float var9, int var10) {
		return this.addTriangle(var1.x, var1.y, var1.z, var2, var3, var4.x,
				var4.y, var4.z, var5, var6, var7.x, var7.y, var7.z, var8, var9,
				var10, 0, false);
	}

	public int addTriangle(SimpleVector var1, float var2, float var3,
			SimpleVector var4, float var5, float var6, SimpleVector var7,
			float var8, float var9, int var10, SimpleVector var11) {
		return this.addTriangle(var1.x, var1.y, var1.z, var2, var3, var4.x,
				var4.y, var4.z, var5, var6, var7.x, var7.y, var7.z, var8, var9,
				var10, 0, false, (int[]) null, (TextureInfo) null, var11.x,
				var11.y, var11.z);
	}

	final int addTriangle(float var1, float var2, float var3, float var4,
			float var5, float var6, float var7, float var8, float var9,
			float var10, float var11, float var12, float var13, float var14,
			float var15, int var16) {
		return this
				.addTriangle(var1, var2, var3, var4, var5, var6, var7, var8,
						var9, var10, var11, var12, var13, var14, var15, var16,
						0, false);
	}

	public int addTriangle(SimpleVector var1, float var2, float var3,
			SimpleVector var4, float var5, float var6, SimpleVector var7,
			float var8, float var9, int var10, int var11) {
		return this.addTriangle(var1.x, var1.y, var1.z, var2, var3, var4.x,
				var4.y, var4.z, var5, var6, var7.x, var7.y, var7.z, var8, var9,
				var10, var11, true);
	}

	public int addTriangle(SimpleVector var1, SimpleVector var2,
			SimpleVector var3, TextureInfo var4, SimpleVector var5) {
		return this.addTriangle(var1.x, var1.y, var1.z, 0.0F, 0.0F, var2.x,
				var2.y, var2.z, 0.0F, 0.0F, var3.x, var3.y, var3.z, 0.0F, 0.0F,
				0, 0, false, (int[]) null, var4, var5.x, var5.y, var5.z);
	}

	public int addTriangle(SimpleVector var1, SimpleVector var2,
			SimpleVector var3, TextureInfo var4) {
		return this.addTriangle(var1.x, var1.y, var1.z, 0.0F, 0.0F, var2.x,
				var2.y, var2.z, 0.0F, 0.0F, var3.x, var3.y, var3.z, 0.0F, 0.0F,
				0, 0, false, (int[]) null, var4);
	}

	public int addTriangle(SimpleVector var1, SimpleVector var2,
			SimpleVector var3, TextureInfo var4, int var5) {
		return this.addTriangle(var1.x, var1.y, var1.z, 0.0F, 0.0F, var2.x,
				var2.y, var2.z, 0.0F, 0.0F, var3.x, var3.y, var3.z, 0.0F, 0.0F,
				0, var5, true, (int[]) null, var4);
	}

	final int addTriangle(float var1, float var2, float var3, float var4,
			float var5, float var6, float var7, float var8, float var9,
			float var10, float var11, float var12, float var13, float var14,
			float var15, int var16, int var17, boolean var18) {
		return this.addTriangle(var1, var2, var3, var4, var5, var6, var7, var8,
				var9, var10, var11, var12, var13, var14, var15, var16, var17,
				var18, (int[]) null);
	}

	final int addTriangle(float var1, float var2, float var3, float var4,
			float var5, float var6, float var7, float var8, float var9,
			float var10, float var11, float var12, float var13, float var14,
			float var15, int var16, int var17, boolean var18, int[] var19) {
		return this.addTriangle(var1, var2, var3, var4, var5, var6, var7, var8,
				var9, var10, var11, var12, var13, var14, var15, var16, var17,
				var18, var19, (TextureInfo) null);
	}

	final int addTriangle(float var1, float var2, float var3, float var4,
			float var5, float var6, float var7, float var8, float var9,
			float var10, float var11, float var12, float var13, float var14,
			float var15, int var16, int var17, boolean var18, int[] var19,
			TextureInfo var20) {
		return this.addTriangle(var1, var2, var3, var4, var5, var6, var7, var8,
				var9, var10, var11, var12, var13, var14, var15, var16, var17,
				var18, var19, var20, -1.0F, -1.0F, -1.0F);
	}

	final int addTriangle(float var1, float var2, float var3, float var4,
			float var5, float var6, float var7, float var8, float var9,
			float var10, float var11, float var12, float var13, float var14,
			float var15, int var16, int var17, boolean var18, int[] var19,
			TextureInfo var20, float var21, float var22, float var23) {
		try {
			if (var21 != -1.0F) {
				this.objVectors.createAlpha();
			}

			if (var20 != null) {
				if (this.multiTex == null && var20.stageCnt > 1) {
					this.multiTex = new int[Config.maxTextureLayers - 1][this.texture.length];
					this.multiMode = new int[Config.maxTextureLayers - 1][this.texture.length];

					for (int var24 = 0; var24 < this.texture.length; ++var24) {
						for (int var25 = 0; var25 < Config.maxTextureLayers - 1; ++var25) {
							this.multiTex[var25][var24] = -1;
						}
					}

					this.objVectors.createMultiCoords();
					this.usesMultiTexturing = true;
				}

				var4 = var20.u0[0];
				var5 = var20.v0[0];
				var9 = var20.u1[0];
				var10 = var20.v1[0];
				var14 = var20.u2[0];
				var15 = var20.v2[0];
				var16 = var20.textures[0];
				if (this.maxStagesUsed < var20.stageCnt) {
					this.maxStagesUsed = var20.stageCnt;
				}
			}

			boolean var41 = this.usesMultiTexturing && var20 != null;
			TextureManager var42 = TextureManager.getInstance();
			if (var18 && this.sector == null) {
				this.sector = new int[this.texture.length];
			}

			float var26 = 2.0F;
			float var27 = 2.0F;
			if (var16 != -1) {
				var26 = (float) var42.textures[var16].width;
				var27 = (float) var42.textures[var16].height;
			} else {
				var16 = 0;
			}

			float var28 = var4;
			float var29 = var5;
			float var30 = var9;
			float var31 = var10;
			float var32 = var14;
			float var33 = var15;
			var4 *= var26;
			var5 *= var27;
			var9 *= var26;
			var10 *= var27;
			var14 *= var26;
			var15 *= var27;
			if (this.objMesh.anzVectors + 3 < this.objMesh.maxVectors) {
				int var34 = this.objMesh.anzVectors;
				int var35 = this.objMesh.anzTri;
				boolean var36 = false;
				boolean var37 = false;
				boolean var38 = false;
				int var43 = -1;
				if (!this.neverOptimize) {
					var43 = this.objVectors
							.checkCoords(var1, var2, var3, var17);
				} else if (var19 != null) {
					var43 = var19[0];
				}

				if (var43 == -1) {
					var43 = this.objVectors.addVertex(var1, var2, var3, var17);
				}

				int var45 = var43;
				this.objMesh.coords[var34] = var43;
				this.objVectors.uOrg[var34] = var4;
				this.objVectors.vOrg[var34] = var5;
				int var39;
				if (var41) {
					for (var39 = 0; var39 < var20.stageCnt - 1; ++var39) {
						this.objVectors.uMul[var39][var34] = var20.u0[var39 + 1];
						this.objVectors.vMul[var39][var34] = var20.v0[var39 + 1];
					}
				}

				this.objVectors.buOrg[var34] = var4;
				this.objVectors.bvOrg[var34] = var5;
				this.objVectors.nuOrg[var34] = var28;
				this.objVectors.nvOrg[var34] = var29;
				if (var21 != -1.0F) {
					this.objVectors.alpha[var34] = var21;
				}

				++this.objMesh.anzVectors;
				this.objMesh.points[var35][0] = var34;
				var34 = this.objMesh.anzVectors;
				var43 = -1;
				if (!this.neverOptimize) {
					var43 = this.objVectors
							.checkCoords(var6, var7, var8, var17);
				} else if (var19 != null) {
					var43 = var19[1];
				}

				if (var43 == -1) {
					var43 = this.objVectors.addVertex(var6, var7, var8, var17);
				}

				int var44 = var43;
				this.objMesh.coords[var34] = var43;
				this.objVectors.uOrg[var34] = var9;
				this.objVectors.vOrg[var34] = var10;
				if (var41) {
					for (var39 = 0; var39 < var20.stageCnt - 1; ++var39) {
						this.objVectors.uMul[var39][var34] = var20.u1[var39 + 1];
						this.objVectors.vMul[var39][var34] = var20.v1[var39 + 1];
					}
				}

				this.objVectors.buOrg[var34] = var9;
				this.objVectors.bvOrg[var34] = var10;
				this.objVectors.nuOrg[var34] = var30;
				this.objVectors.nvOrg[var34] = var31;
				if (var22 != -1.0F) {
					this.objVectors.alpha[var34] = var22;
				}

				++this.objMesh.anzVectors;
				this.objMesh.points[var35][1] = var34;
				var34 = this.objMesh.anzVectors;
				var43 = -1;
				if (!this.neverOptimize) {
					var43 = this.objVectors.checkCoords(var11, var12, var13,
							var17);
				} else if (var19 != null) {
					var43 = var19[2];
				}

				if (var43 == -1) {
					var43 = this.objVectors.addVertex(var11, var12, var13,
							var17);
				}

				this.objMesh.coords[var34] = var43;
				this.objVectors.uOrg[var34] = var14;
				this.objVectors.vOrg[var34] = var15;
				if (var41) {
					for (var39 = 0; var39 < var20.stageCnt - 1; ++var39) {
						this.objVectors.uMul[var39][var34] = var20.u2[var39 + 1];
						this.objVectors.vMul[var39][var34] = var20.v2[var39 + 1];
					}
				}

				this.objVectors.buOrg[var34] = var14;
				this.objVectors.bvOrg[var34] = var15;
				this.objVectors.nuOrg[var34] = var32;
				this.objVectors.nvOrg[var34] = var33;
				if (var22 != -1.0F) {
					this.objVectors.alpha[var34] = var23;
				}

				++this.objMesh.anzVectors;
				if (var19 != null) {
					var19[0] = var45;
					var19[1] = var44;
					var19[2] = var43;
				}

				if (var43 != var45 && var43 != var44 && var44 != var45) {
					if (var43 < this.lowestPos) {
						this.lowestPos = var43;
					}

					if (var44 < this.lowestPos) {
						this.lowestPos = var44;
					}

					if (var45 < this.lowestPos) {
						this.lowestPos = var45;
					}

					if (var43 > this.highestPos) {
						this.highestPos = var43;
					}

					if (var44 > this.highestPos) {
						this.highestPos = var44;
					}

					if (var45 > this.highestPos) {
						this.highestPos = var45;
					}

					this.objMesh.points[var35][2] = var34;
					if (this.sector != null) {
						this.sector[var35] = var17;
					}

					this.texture[var35] = var16;
					this.basemap[var35] = var16;
					if (this.bumpmap != null) {
						this.bumpmap[var35] = var16;
					}

					if (var41) {
						for (var39 = 0; var39 < var20.stageCnt - 1; ++var39) {
							this.multiTex[var39][var35] = var20.textures[var39 + 1];
							this.multiMode[var39][var35] = var20.mode[var39 + 1];
						}
					}

					++this.objMesh.anzTri;
				}
			} else {
				Logger.log(
						(String) "Polygon index out of range - object is too large!",
						0);
			}
		} catch (ArrayIndexOutOfBoundsException var40) {
			Logger.log(
					(String) "Polygon index out of range - object is too large!",
					0);
		}

		return this.objMesh.anzTri - 1;
	}

	final int addMD2Triangle(int var1, float var2, float var3, int var4,
			float var5, float var6, int var7, float var8, float var9) {
		byte var10 = 0;
		byte var11 = 0;
		float var12 = 2.0F;
		float var13 = 2.0F;
		float var14 = var2;
		float var15 = var3;
		float var16 = var5;
		float var17 = var6;
		float var18 = var8;
		float var19 = var9;
		var2 *= var12;
		var3 *= var13;
		var5 *= var12;
		var6 *= var13;
		var8 *= var12;
		var9 *= var13;
		if (this.objMesh.anzVectors + 3 < this.objMesh.maxVectors) {
			int var20 = this.objMesh.anzVectors;
			int var21 = this.objMesh.anzTri;
			boolean var22 = false;
			boolean var23 = false;
			boolean var24 = false;
			this.objMesh.coords[var20] = var1;
			this.objVectors.uOrg[var20] = var2;
			this.objVectors.vOrg[var20] = var3;
			this.objVectors.buOrg[var20] = var2;
			this.objVectors.bvOrg[var20] = var3;
			this.objVectors.nuOrg[var20] = var14;
			this.objVectors.nvOrg[var20] = var15;
			++this.objMesh.anzVectors;
			this.objMesh.points[var21][0] = var20;
			var20 = this.objMesh.anzVectors;
			int var25 = var20;
			this.objMesh.coords[var20] = var4;
			this.objVectors.uOrg[var20] = var5;
			this.objVectors.vOrg[var20] = var6;
			this.objVectors.buOrg[var20] = var5;
			this.objVectors.bvOrg[var20] = var6;
			this.objVectors.nuOrg[var20] = var16;
			this.objVectors.nvOrg[var20] = var17;
			++this.objMesh.anzVectors;
			this.objMesh.points[var21][1] = var20;
			var20 = this.objMesh.anzVectors;
			this.objMesh.coords[var20] = var7;
			this.objVectors.uOrg[var20] = var8;
			this.objVectors.vOrg[var20] = var9;
			this.objVectors.buOrg[var20] = var8;
			this.objVectors.bvOrg[var20] = var9;
			this.objVectors.nuOrg[var20] = var18;
			this.objVectors.nvOrg[var20] = var19;
			++this.objMesh.anzVectors;
			if (var7 < this.lowestPos) {
				this.lowestPos = var7;
			}

			if (var25 < this.lowestPos) {
				this.lowestPos = var25;
			}

			if (var1 < this.lowestPos) {
				this.lowestPos = var1;
			}

			if (var7 > this.highestPos) {
				this.highestPos = var7;
			}

			if (var25 > this.highestPos) {
				this.highestPos = var25;
			}

			if (var1 > this.highestPos) {
				this.highestPos = var1;
			}

			this.objMesh.points[var21][2] = var20;
			if (this.sector != null) {
				this.sector[var21] = var10;
			}

			this.texture[var21] = var11;
			this.basemap[var21] = var11;
			if (this.bumpmap != null) {
				this.bumpmap[var21] = var11;
			}

			++this.objMesh.anzTri;
		} else {
			Logger.log(
					(String) "Polygon index out of range - object is too large!",
					0);
		}

		return this.objMesh.anzTri - 1;
	}

	private final Matrix recurseObjectsBillboarded(Matrix var1) {
		Matrix var2 = var1.cloneMatrix();
		var1 = this.recurseObjects(var1);
		float var3 = this.recurseScaling(this.getScale());
		Matrix var4 = var1.cloneMatrix().invert3x3();
		var4.mat[3][0] = 0.0F;
		var4.mat[3][1] = 0.0F;
		var4.mat[3][2] = 0.0F;
		var4.mat[3][3] = 1.0F;
		Matrix var5 = new Matrix();
		var5.mat[3][0] = -var1.mat[3][0];
		var5.mat[3][1] = -var1.mat[3][1];
		var5.mat[3][2] = -var1.mat[3][2];
		var1.matMul(var5);
		var1.matMul(var4);
		var2.mat[3][0] = 0.0F;
		var2.mat[3][1] = 0.0F;
		var2.mat[3][2] = 0.0F;
		var2.mat[3][3] = 1.0F;
		var1.matMul(var2);
		var5.mat[3][0] = -var5.mat[3][0];
		var5.mat[3][1] = -var5.mat[3][1];
		var5.mat[3][2] = -var5.mat[3][2];
		var1.matMul(var5);
		var1.scalarMul(1.0F / (var3 * this.getScale()));
		return var1;
	}

	private final Matrix recurseObjects(Matrix var1) {
		Matrix var2 = this.getCachedMatrix(1);

		for (int var3 = 0; var3 < this.parentCnt; ++var3) {
			Object3D var4 = this.parent[var3];
			var2.setIdentity();
			float var5 = var4.originMatrix.mat[3][0];
			float var6 = var4.originMatrix.mat[3][1];
			float var7 = var4.originMatrix.mat[3][2];
			var2.mat[3][0] = -var4.xRotationCenter - var5;
			var2.mat[3][1] = -var4.yRotationCenter - var6;
			var2.mat[3][2] = -var4.zRotationCenter - var7;
			var1.matMul(var2);
			var1.matMul(var4.rotationMatrix);
			var2.mat[3][0] = var4.xRotationCenter
					+ var4.translationMatrix.mat[3][0] + var5;
			var2.mat[3][1] = var4.yRotationCenter
					+ var4.translationMatrix.mat[3][1] + var6;
			var2.mat[3][2] = var4.zRotationCenter
					+ var4.translationMatrix.mat[3][2] + var7;
			var1.matMul(var2);
			if (var4.parentCnt != 0) {
				var1 = var4.recurseObjects(var1);
			}
		}

		return var1;
	}

	private final float recurseScaling(float var1) {
		for (int var2 = 0; var2 < this.parentCnt; ++var2) {
			Object3D var3 = this.parent[var2];
			var1 *= var3.getScale();
			if (var3.parentCnt != 0) {
				var1 = var3.recurseScaling(var1);
			}
		}

		return var1;
	}

	final boolean transformVertices(FrameBuffer var1, Integer var2) {
		if (Config.autoBuild && !this.hasBeenBuild) {
			Logger.log(
					(String) ("Auto building object \'" + this.getName() + "\'!"),
					2);
			this.build();
		}

		float var3 = 0.0F;
		float var4 = 0.0F;
		float var5 = 0.0F;
		this.someSectorVisible = true;
		boolean var9 = var1.hasRenderTarget && var1.renderTarget.isShadowMap;
		float var16 = 0.0F;
		float var17 = 0.0F;
		float var18 = 0.0F;
		float var19 = 0.0F;
		float var20 = 0.0F;
		float var21 = 0.0F;
		float var22 = 0.0F;
		float var23 = 0.0F;
		float var24 = 0.0F;
		float var28 = 0.0F;
		float var29 = 0.0F;
		float var30 = 0.0F;
		int var31 = 0;
		int var32 = 0;
		int var33 = 0;
		int var34 = 0;
		int var35 = 0;
		int var36 = 0;
		int var37 = 0;
		int var38 = this.myWorld.portals.viewSector;
		float var39 = this.myWorld.camera.divx;
		float var40 = this.myWorld.camera.divy;
		float var41 = this.myWorld.camera.scaleX;
		float var42 = this.myWorld.camera.scaleY;
		float var43 = Config.nearPlane;
		float var44 = 0.0F;
		float var45 = var1.middleX;
		float var46 = var1.middleY;
		if (Config.useLocking) {
			this.myWorld.lockMatrices();
		}

		this.mat2 = this.myWorld.camera.frontMatrix;
		this.mat3.setIdentity();
		this.mat3.mat[3][0] = -this.myWorld.camera.frontBx;
		this.mat3.mat[3][1] = -this.myWorld.camera.frontBy;
		this.mat3.mat[3][2] = -this.myWorld.camera.frontBz;
		this.visComplete = false;
		float[] var47 = this.objMesh.zOrg;
		float[] var48 = this.objMesh.xOrg;
		float[] var49 = this.objMesh.yOrg;
		float[] var50 = this.objVectors.xTr;
		float[] var51 = this.objVectors.yTr;
		float[] var52 = this.objVectors.zTr;
		if (this.lazyTransforms && !this.isBillBoard) {
			this.getWorldTransformation(this.mat5);
		} else {
			this.mat5.setIdentity();
			this.mat6.setIdentity();
			this.mat5.mat[3][0] = -this.xRotationCenter;
			this.mat5.mat[3][1] = -this.yRotationCenter;
			this.mat5.mat[3][2] = -this.zRotationCenter;
			this.mat6.mat[3][0] = this.xRotationCenter
					+ this.translationMatrix.mat[3][0]
					+ this.originMatrix.mat[3][0];
			this.mat6.mat[3][1] = this.yRotationCenter
					+ this.translationMatrix.mat[3][1]
					+ this.originMatrix.mat[3][1];
			this.mat6.mat[3][2] = this.zRotationCenter
					+ this.translationMatrix.mat[3][2]
					+ this.originMatrix.mat[3][2];
			if (!this.isBillBoard) {
				this.mat5.matMul(this.rotationMatrix);
			} else {
				Matrix var53 = this.mat2;
				if (this.myWorld.camera.billBoardMatrix != null) {
					var53 = this.myWorld.camera.billBoardMatrix;
				}

				Matrix var54 = var53.invert();
				var54.scalarMul(this.scaleFactor);
				this.mat5.matMul(var54);
			}

			this.mat5.matMul(this.mat6);
			if (this.parentCnt != 0) {
				if (this.isBillBoard && !Config.oldStyleBillBoarding) {
					this.mat5 = this.recurseObjectsBillboarded(this.mat5);
				} else {
					this.mat5 = this.recurseObjects(this.mat5);
				}
			}
		}

		boolean var83 = this.isCompiled();
		this.transBuffer.setTo(this.mat5);
		this.cullingInvertedBuffer = this.reverseCulling
				^ this.myWorld.invertCulling;
		if (Config.useLocking) {
			this.myWorld.unlockMatrices();
		}

		float[][] var84 = this.mat5.mat;
		if (!var83 || Config.doPortalHsr) {
			var16 = var84[0][0];
			var17 = var84[1][0];
			var20 = var84[1][1];
			var21 = var84[2][1];
			var18 = var84[2][0];
			var19 = var84[0][1];
			var24 = var84[2][2];
			var23 = var84[1][2];
			var22 = var84[0][2];
			var29 = var84[3][0];
			var30 = var84[3][1];
			var28 = var84[3][2];
		}

		this.mat5.matMul(this.mat3);
		this.mat5.matMul(this.mat2);
		float var6 = var84[0][0];
		float var7 = var84[1][0];
		float var11 = var84[1][1];
		float var12 = var84[2][1];
		float var15 = var84[2][2];
		float var14 = var84[1][2];
		float var8 = var84[2][0];
		float var13 = var84[0][2];
		float var10 = var84[0][1];
		float var26 = var84[3][0];
		float var27 = var84[3][1];
		float var25 = var84[3][2];
		boolean var55 = true;
		boolean var56 = false;
		boolean var57 = false;
		int var58 = 0;
		float[][] var59 = this.myWorld.getWorldProcessor().getArrays(
				this.getMesh().getVertexCount() + 8, var2);
		float[] var60 = var59[0];
		boolean var61 = this.ocTree != null && !Config.doPortalHsr
				&& this.ocTree.getRenderingUse();
		int var62;
		int var63;
		int var64;
		OcTreeNode var65;
		int var66;
		if (var61) {
			var58 = this.ocTree.getVisibleLeafs(this.mat5, var39, var40,
					this.myWorld.camera.frustumOffset);
			if (var58 == 0) {
				return true;
			}

			if (!this.isCompiled()) {
				if (var58 == this.ocTree.getTotalLeafs()) {
					for (var62 = 0; var62 < this.objMesh.anzCoords; ++var62) {
						var60[var62] = -1.0F;
					}
				} else {
					OcTreeNode[] var88 = this.ocTree.getLeafList();
					var63 = this.ocTree.getLeafCount();

					for (var64 = 0; var64 < var63; ++var64) {
						var65 = var88[var64];
						var66 = var65.getPointCount();
						int[] var67 = var65.getPoints();

						for (int var68 = 0; var68 < var66; ++var68) {
							var60[var67[var68]] = -1.0F;
						}
					}
				}
			}
		}

		var62 = this.myWorld.portals.anzVisSectors;
		if (this.oneSectorOnly || this.hasPortals || !Config.doPortalHsr) {
			var62 = 1;
		}

		boolean var89;
		int var90;
		if (Config.doPortalHsr && this.dynSectorDetect && !this.hasPortals
				&& this.objMesh.obbEnd - this.objMesh.obbStart == 7) {
			for (var63 = 0; var63 < 8; ++var63) {
				var64 = var63 + this.objMesh.obbStart;
				var4 = var47[var64];
				var3 = var48[var64];
				var5 = var49[var64];
				this.xWs[var63] = var3 * var16 + var5 * var17 + var4 * var18
						+ var29;
				this.yWs[var63] = var3 * var19 + var5 * var20 + var4 * var21
						+ var30;
				this.zWs[var63] = var3 * var22 + var5 * var23 + var4 * var24
						+ var28;
			}

			this.dynSectorList = this.myWorld.portals.detectAllCoveredSectors(
					this.dynSectorList, this.xWs, this.yWs, this.zWs);
			this.sectorCnt = this.dynSectorList[0];
			if (this.sectorCnt != 0) {
				var89 = false;

				for (var64 = 1; var64 <= this.sectorCnt; ++var64) {
					if (this.myWorld.portals.viewSector == this.dynSectorList[var64]) {
						var89 = true;
						this.someSectorVisible = true;
						var62 = 1;
						var64 = this.sectorCnt + 1;
					} else {
						for (var90 = 0; var90 < this.myWorld.portals.anzVisSectors; ++var90) {
							if (this.myWorld.portals.visSectors[var90] == this.dynSectorList[var64]) {
								var89 = true;
								this.someSectorVisible = true;
								var62 = 1;
								var64 = this.sectorCnt + 1;
								var90 = this.myWorld.portals.anzVisSectors;
							}
						}
					}
				}

				if (!var89) {
					this.someSectorVisible = false;
					var62 = 0;
				}
			} else {
				this.someSectorVisible = false;
				var62 = 0;
			}
		}

		var89 = false;

		for (var64 = 0; var64 < var62; ++var64) {
			int var86;
			int var87;
			if (!this.oneSectorOnly && !this.hasPortals && Config.doPortalHsr
					&& !this.dynSectorDetect) {
				int var85 = this.myWorld.portals.visSectors[var64];
				var87 = this.sectorStartPoint[var85];
				var86 = this.sectorEndPoint[var85] + 1;
				if (var87 + 1 == var86) {
					var86 = 0;
					var87 = 0;
				}
			} else {
				var87 = 0;
				var86 = this.objMesh.anzCoords;
			}

			boolean var69;
			float var91;
			if (Config.useBB && this.hasBoundingBox && Config.useFrustumCulling
					|| var61 && this.hasBoundingBox) {
				for (var90 = this.objMesh.obbStart; var90 <= this.objMesh.obbEnd; ++var90) {
					var4 = var47[var90];
					var3 = var48[var90];
					var5 = var49[var90];
					var91 = var3 * var6 + var5 * var7 + var4 * var8 + var26;
					float var93 = var3 * var10 + var5 * var11 + var4 * var12
							+ var27;
					float var99 = var3 * var13 + var5 * var14 + var4 * var15
							+ var25;
					var50[var90] = var91;
					var51[var90] = var93;
					var52[var90] = var99;
					if (Config.useFrustumCulling) {
						if (this.dynSectorDetect) {
							if (this.sectorCnt == 1
									&& this.dynSectorList[1] == var38) {
								++var37;
							}
						} else if (!this.oneSectorOnly) {
							if (this.objVectors.vertexSector[var90] == var38) {
								++var37;
							}
						} else if (this.singleSectorNumber == var38
								|| this.singleSectorNumber == 0) {
							++var37;
						}

						var69 = false;
						if (var99 < var43) {
							++var35;
							var69 = true;
						} else if (var99 > Config.farPlane) {
							++var36;
							var69 = true;
						}

						var44 = var99 * var39;
						if (var91 < -var44) {
							++var32;
							var69 = true;
						} else if (var91 > var44) {
							++var31;
							var69 = true;
						}

						var44 = var99 * var40;
						float var70 = var44 * this.myWorld.camera.frustumOffset;
						if (var93 < -var44 - var70) {
							++var33;
							var69 = true;
						} else if (var93 > var44 - var70) {
							++var34;
							var69 = true;
						}

						if (var83 && !var69) {
							break;
						}
					}
				}

				var90 = this.objMesh.obbEnd + 1 - this.objMesh.obbStart;
				if (var34 != var90 && var31 != var90 && var33 != var90
						&& var32 != var90 && var35 != var90 && var36 != var90) {
					if (Config.doPortalHsr && var37 != var90) {
						var89 = !this.myWorld.portals.testObbAgainstPortals(
								this, var45, var46, var41, var42);
					} else {
						var89 = false;
					}

					if (!var89 && var34 == 0 && var31 == 0 && var33 == 0
							&& var32 == 0 && var35 == 0 && var36 == 0) {
						this.visComplete = true;
					}
				} else {
					var89 = true;
				}

				if (this.objMesh.obbEnd + 1 == var86) {
					var86 = this.objMesh.obbStart;
				}
			}

			if (!var89) {
				if (var83) {
					var65 = null;
					if (var61) {
						if (this.sectors == null) {
							this.sectors = new Hashtable();
						}

						this.sectors.clear();
						OcTreeNode[] var98 = this.ocTree.getLeafList();

						for (var66 = 0; var66 < var58; ++var66) {
							Integer var96 = IntegerC.valueOf(var98[var66]
									.getID());
							this.sectors.put(var96, var96);
						}
					}

					var91 = this.centerX * var13 + this.centerY * var14
							+ this.centerZ * var15 + var25;

					for (int var97 = 0; var97 < this.compiled.size(); ++var97) {
						ICompiledInstance var101 = null;
						if (this.shareWith != null) {
							var101 = (ICompiledInstance) this.shareWith.compiled
									.get(var97);
						} else {
							var101 = (ICompiledInstance) this.compiled
									.get(var97);
						}

						var69 = true;
						if (var61 && var101.getTreeID() != -1) {
							var69 = this.sectors.containsKey(IntegerC
									.valueOf(var101.getTreeID()));
						}

						if (var69) {
							int var103 = var101.getPolyIndex();
							this.myWorld.visList.addToList(this, this, var103,
									var103, var91, var97,
									var101.getStageCount() - 1, false);
						}
					}

					return false;
				}

				float[] var94 = this.objVectors.sz;
				float[] var92 = this.objMesh.nzOrg;
				float[] var95 = this.objMesh.nxOrg;
				float[] var100 = this.objMesh.nyOrg;
				float[] var104 = var59[3];
				float[] var102 = var59[4];
				float[] var71 = var59[5];
				float[] var72 = var59[6];
				float[] var73 = var59[7];
				float[] var74 = var59[8];
				OcTreeNode[] var75 = null;
				int[] var76 = null;
				int var77 = 0;
				boolean var78 = false;
				if (var61) {
					var75 = this.ocTree.getLeafList();
					var78 = true;
				}

				boolean var79 = this.isEnvmapped && !this.useCSEnvmapping;
				if (var79) {
					this.myWorld.createWSNormals();
					var72 = var59[6];
					var73 = var59[7];
					var74 = var59[8];
				}

				do {
					if (var78) {
						var76 = var75[var77].getPoints();
						var87 = 0;
						var86 = var75[var77].getPointCount();
						++var77;
					}

					for (int var80 = var87; var80 < var86; ++var80) {
						int var81 = var80;
						if (var78) {
							var81 = var76[var80];
						}

						if (!var78 || var60[var81] == -1.0F) {
							var4 = var47[var81];
							var3 = var48[var81];
							var5 = var49[var81];
							var50[var81] = var3 * var6 + var5 * var7 + var4
									* var8 + var26;
							var51[var81] = var3 * var10 + var5 * var11 + var4
									* var12 + var27;
							if (var9) {
								var52[var81] = var3 * var13 + var5 * var14
										+ var4 * var15 + var25
										+ Config.glShadowZBias;
							} else {
								var52[var81] = var3 * var13 + var5 * var14
										+ var4 * var15 + var25;
							}

							var94[var81] = -1.01F;
							var4 = var92[var81];
							var3 = var95[var81];
							var5 = var100[var81];
							var104[var81] = var3 * var6 + var5 * var7 + var4
									* var8;
							var102[var81] = var3 * var10 + var5 * var11 + var4
									* var12;
							var71[var81] = var3 * var13 + var5 * var14 + var4
									* var15;
							if (var79) {
								var72[var81] = var3 * var16 + var5 * var17
										+ var4 * var18;
								var73[var81] = var3 * var19 + var5 * var20
										+ var4 * var21;
								var74[var81] = var3 * var22 + var5 * var23
										+ var4 * var24;
							}

							if (this.reNormalize) {
								float var82 = (float) Math
										.sqrt((double) (var104[var81]
												* var104[var81] + var102[var81]
												* var102[var81] + var71[var81]
												* var71[var81]));
								if (var82 != 0.0F) {
									var104[var81] /= var82;
									var102[var81] /= var82;
									var71[var81] /= var82;
								}

								if (var79) {
									var82 = (float) Math
											.sqrt((double) (var72[var81]
													* var72[var81]
													+ var73[var81]
													* var73[var81] + var74[var81]
													* var74[var81]));
									if (var82 != 0.0F) {
										var72[var81] /= var82;
										var73[var81] /= var82;
										var74[var81] /= var82;
									}
								}
							}

							var60[var81] = 1.2345671E7F;
						}
					}
				} while (var78 && var77 < var58);
			} else {
				++cullCount;
			}
		}

		return var89;
	}

	final void addClippedPoly(int var1, float var2, float var3, float var4,
			float var5, float var6, float var7, float var8, float var9,
			float var10, float var11, float var12, float var13, float var14,
			float var15, float var16, float var17, float var18, float var19,
			float var20, float var21, float var22, float var23, float var24,
			float var25, float var26, float var27, float var28, float var29,
			float var30, float var31) {
		Object3D var32 = this.myWorld.clippedPolys;
		if (var32.objMesh.anzVectors + 2 + 8 >= var32.objMesh.maxVectors) {
			this.myWorld.rescaleClippedPolys();
		}

		Vectors var33 = var32.objVectors;
		Mesh var34 = var32.objMesh;
		int var35 = var34.anzVectors;
		int var36 = var34.anzCoords;
		var32.texture[var34.anzTri] = this.texture[var1];
		var32.basemap[var34.anzTri] = this.basemap[var1];
		if (this.bumpmap != null) {
			var32.bumpmap[var34.anzTri] = this.bumpmap[var1];
		}

		var33.sx[var36] = var2;
		var33.sy[var36] = var3;
		var33.sz[var36] = var4;
		var34.coords[var35] = var36;
		var33.su[var36] = var5;
		var33.sv[var36] = var6;
		var33.sb[var36] = var9;
		var33.sr[var36] = var7;
		var33.sg[var36] = var8;
		if (var33.bsu != null) {
			var33.bsu[var36] = var26;
			var33.bsv[var36] = var27;
		}

		var34.points[var34.anzTri][0] = var36++;
		++var35;
		var33.sx[var36] = var10;
		var33.sy[var36] = var11;
		var33.sz[var36] = var12;
		var34.coords[var35] = var36;
		var33.su[var36] = var13;
		var33.sv[var36] = var14;
		var33.sb[var36] = var17;
		var33.sr[var36] = var15;
		var33.sg[var36] = var16;
		if (var33.bsu != null) {
			var33.bsu[var36] = var28;
			var33.bsv[var36] = var29;
		}

		var34.points[var34.anzTri][1] = var36++;
		++var35;
		var33.sx[var36] = var18;
		var33.sy[var36] = var19;
		var33.sz[var36] = var20;
		var34.coords[var35] = var36;
		var33.su[var36] = var21;
		var33.sv[var36] = var22;
		var33.sb[var36] = var25;
		var33.sr[var36] = var23;
		var33.sg[var36] = var24;
		if (var33.bsu != null) {
			var33.bsu[var36] = var30;
			var33.bsv[var36] = var31;
		}

		var34.points[var34.anzTri][2] = var36++;
		++var35;
		var34.anzCoords = var36;
		var34.anzVectors = var35;
		++var34.anzTri;
	}

	final void addCompiled(ICompiledInstance var1) {
		List var2 = this.compiled;
		synchronized (this.compiled) {
			this.compiled.add(var1);
		}
	}

	final void render(boolean var1, float var2, float var3, float var4,
			float var5, float var6, float var7, boolean var8, Integer var9,
			FrameBuffer var10) {
		Lights var11;
		if (!this.isLit) {
			var11 = DUMMY_LIGHTS;
		} else {
			var11 = this.myWorld.lights;
		}

		int var12 = var11.lightCnt;
		VisList var13 = this.myWorld.visList;
		int var15;
		int var18;
		int var19;
		int var21;
		if (this.isCompiled()) {
			if (this.nearestLights == null) {
				this.nearestLights = new float[8][7];
			}

			float[][] var165 = this.nearestLights;
			synchronized (this.nearestLights) {
				for (var15 = 0; var15 < 8; ++var15) {
					this.nearestLights[var15][0] = -9999.0F;
				}

				if (this.isLit && var12 > 0) {
					SimpleVector var166 = null;
					if (this.lightsList == null) {
						this.lightsList = new ArrayList(var12);
					}

					if (this.litData == null || this.litData.length < var12) {
						this.litData = new float[var12][2];
					}

					if (this.tempTC == null) {
						this.tempTC = new SimpleVector();
					}

					List var167 = this.lightsList;
					var167.clear();
					int var170 = 0;

					float var173;
					for (var18 = 0; var18 < var12; ++var18) {
						if (var11.isVisible[var18]) {
							if (var166 == null) {
								var166 = this.getTransformedCenter(this.tempTC);
							}

							float var172 = var11.distanceOverride[var18];
							if (var172 == -1.0F) {
								var173 = var166.x - var11.xOrg[var18];
								float var175 = var166.y - var11.yOrg[var18];
								float var176 = var166.z - var11.zOrg[var18];
								var172 = (float) Math.sqrt((double) (var173
										* var173 + var175 * var175 + var176
										* var176));
							} else {
								++var170;
							}

							if (var172 <= var11.discardDistance[var18]
									|| var11.discardDistance[var18] < 0.0F
									&& (Config.lightDiscardDistance < 0.0F || Config.lightDiscardDistance > var172)) {
								boolean var174 = false;
								if (var12 > 8 || var170 > 0) {
									for (var21 = 0; var21 < var167.size(); ++var21) {
										float[] var177 = (float[]) ((float[]) var167
												.get(var21));
										if (var172 < var177[0]) {
											this.litData[var18][0] = var172;
											this.litData[var18][1] = (float) var18;
											var167.add(var21,
													this.litData[var18]);
											var174 = true;
											break;
										}
									}
								}

								if (!var174) {
									this.litData[var18][0] = var172;
									this.litData[var18][1] = (float) var18;
									var167.add(this.litData[var18]);
								}
							}
						}
					}

					for (var18 = 0; var18 < this.maxLights
							&& var18 < var167.size(); ++var18) {
						var19 = (int) ((float[]) ((float[]) var167.get(var18)))[1];
						var173 = var11.getAttenuation(var19);
						if (var173 != -1.0F && var173 < 0.0F
								&& Config.fadeoutLight) {
							var173 = Config.linearDiv;
						}

						this.nearestLights[var18][0] = var173;
						this.nearestLights[var18][1] = var11.xTr[var19];
						this.nearestLights[var18][2] = -var11.yTr[var19];
						this.nearestLights[var18][3] = -var11.zTr[var19];
						this.nearestLights[var18][4] = var11.rOrg[var19] / 255.0F;
						this.nearestLights[var18][5] = var11.gOrg[var19] / 255.0F;
						this.nearestLights[var18][6] = var11.bOrg[var19] / 255.0F;
					}
				}

				if (this.objMesh.attrList != null && this.dynamic) {
					Vector var169 = this.objMesh.attrList;

					for (int var168 = 0; var168 < var169.size(); ++var168) {
						VertexAttributes var171 = (VertexAttributes) var169
								.elementAt(var168);
						if (var171.updated) {
							var171.updated = false;
							this.modified = true;
							break;
						}
					}
				}

				if (this.dynamic && this.modified) {
					var13.addToFill(this);
					if (this.shareWith != null) {
						this.shareWith.modified = true;
					}
				}

				if (this.dynamic && this.shareWith != null
						&& this.shareWith.modified) {
					var13.addToFill(this.shareWith);
				}

				this.object3DRendered = true;
			}
		} else {
			if (!var8) {
				this.objVectors.createScreenColors();
			}

			TextureManager var14 = TextureManager.getInstance();
			if (this.isBumpmapped && this.bumpmap != null
					&& !var14.textures[this.bumpmap[0]].isBumpmap) {
				var14.textures[this.bumpmap[0]].createBumpmap();
			}

			var15 = this.myWorld.lights.maxLightValue;
			float var16 = Config.farPlane;
			float var17 = Config.nearPlane;
			var18 = this.myWorld.portals.viewSector;
			var19 = 0;
			int var20 = 0;
			var21 = 0;
			int var22 = 0;
			int var23 = -1;
			int var24 = 0;
			int var25 = 0;
			int var26 = ((int) var2 << 1) - 1;
			int var27 = ((int) var3 << 1) - 1;
			float var28 = var2 + var2 * 2.0F * Config.viewportOffsetX;
			float var29 = var3 + var3 * 2.0F * Config.viewportOffsetY;
			float var30 = 0.0F;
			float var31 = 0.0F;
			float var32 = 0.0F;
			float var33 = 0.0F;
			float var34 = 0.0F;
			float var35 = 0.0F;
			float var36 = 0.0F;
			float var37 = 0.0F;
			float var38 = 0.0F;
			float var39 = 0.0F;
			float var40 = 0.0F;
			float var50 = 0.0F;
			float var51 = 0.0F;
			float var52 = 0.0F;
			float var53 = 0.0F;
			float var54 = 0.0F;
			float var55 = 0.0F;
			float var56 = 0.0F;
			float var57 = 0.0F;
			float var58 = 0.0F;
			float var68 = 1.0F / Config.linearDiv;
			float var69 = Config.lightDiscardDistance;
			float var70 = var69 * var69;
			boolean var71 = false;
			float var73 = 1.0E-4F;
			if (!var71) {
				this.object3DRendered = true;
				float var75 = (float) (this.myWorld.ambientRed + this.addColorR);
				float var76 = (float) (this.myWorld.ambientGreen + this.addColorG);
				float var77 = (float) (this.myWorld.ambientBlue + this.addColorB);
				boolean var78 = !Config.doPortalHsr && this.ocTree != null
						&& this.ocTree.getRenderingUse();
				int var79 = 0;
				int var80 = 0;
				int var81 = 0;
				int[] var82 = null;
				if (var78 && this.ocTree.getLeafCount() == 0) {
					return;
				}

				int[][] var83 = this.objMesh.points;
				int[] var84 = this.objMesh.coords;
				float[] var85 = this.objVectors.xTr;
				float[] var86 = this.objVectors.yTr;
				float[] var87 = this.objVectors.zTr;
				float[] var88 = this.objVectors.sz;
				float[] var89 = this.objVectors.sx;
				float[] var90 = this.objVectors.sy;
				float[] var91 = this.objVectors.su;
				float[] var92 = this.objVectors.sv;
				float[] var93 = this.objVectors.bsu;
				float[] var94 = this.objVectors.bsv;
				if (var93 == null) {
					var93 = this.objVectors.su;
					var94 = this.objVectors.sv;
				}

				float[] var95 = this.objVectors.uOrg;
				float[] var96 = this.objVectors.vOrg;
				float[] var97 = this.objVectors.sg;
				float[] var98 = this.objVectors.sr;
				float[] var99 = this.objVectors.sb;
				float[] var100 = this.objVectors.sgOrg;
				float[] var101 = this.objVectors.srOrg;
				float[] var102 = this.objVectors.sbOrg;
				float[][] var103 = this.myWorld.getWorldProcessor().getArrays(
						this.getMesh().getVertexCount() + 8, var9);
				float[] var104 = var103[3];
				float[] var105 = var103[4];
				float[] var106 = var103[5];
				float[] var107 = var103[6];
				float[] var108 = var103[7];
				float[] var109 = var103[8];

				do {
					boolean var110 = this.visComplete;
					if (var78) {
						OcTreeNode[] var111 = this.ocTree.getLeafList();
						if (this.ocTree.isCompletelyVisible(var79)) {
							var110 = true;
						}

						var82 = var111[var79].getPolygons();
						var81 = var111[var79].getPolyCount();
						var80 = 0;
						++var79;
					}

					do {
						do {
							if (var78) {
								var24 = var82[var80];
								++var80;
							}

							boolean var178 = true;
							int var112;
							int var114;
							int var115;
							if (!this.oneSectorOnly && this.sector != null
									&& var23 != this.sector[var24]
									&& Config.doPortalHsr) {
								var178 = false;
								var112 = 1;
								boolean var113 = false;

								while (var24 < this.objMesh.anzTri && !var178) {
									if (!var113) {
										var178 = this.myWorld.portals
												.isSectorVisible(this,
														this.sector[var24]);
									}

									var113 = false;
									if (!var178) {
										var114 = var24;
										var115 = this.sectorEndPoly[this.sector[var24]
												+ var112];
										var24 = this.sectorStartPoly[this.sector[var24]
												+ var112];
										if (var24 == var115) {
											var24 = var114;
											++var112;
											var113 = true;
											if (this.sector[var114] + var112 > this.myWorld.portals.anzSectors) {
												var24 = this.objMesh.anzTri;
												var178 = false;
											}
										}
									}
								}
							}

							if (var24 >= this.objMesh.anzTri) {
								var178 = false;
							} else if (!this.oneSectorOnly
									&& this.sector != null) {
								var23 = this.sector[var24];
							} else {
								var23 = this.singleSectorNumber;
							}

							if (var178) {
								var71 = false;
								var112 = var84[var83[var24][0]];
								int var179 = var84[var83[var24][1]];
								var114 = var84[var83[var24][2]];
								var32 = var85[var114];
								var33 = var86[var114];
								var34 = var87[var114];
								var35 = var85[var179];
								var36 = var86[var179];
								var37 = var87[var179];
								var38 = var85[var112];
								var39 = var86[var112];
								var40 = var87[var112];
								if ((var34 >= var17 || var37 >= var17 || var40 >= var17)
										&& (var34 <= var16 || var37 <= var16 || var40 <= var16)) {
									if (Config.useFrustumCulling && !var110) {
										float var41 = var34 * var6;
										float var42 = var37 * var6;
										float var43 = var40 * var6;
										if ((var32 >= -var41 || var35 >= -var42 || var38 >= -var43)
												&& (var32 <= var41
														|| var35 <= var42 || var38 <= var43)) {
											var41 = var34 * var7;
											var42 = var37 * var7;
											var43 = var40 * var7;
											if (var33 < -var41
													&& var36 < -var42
													&& var39 < -var43
													|| var33 > var41
													&& var36 > var42
													&& var39 > var43) {
												var71 = true;
												var53 = 1.0F;
												++var25;
											}
										} else {
											var71 = true;
											var53 = 1.0F;
											++var25;
										}
									}
								} else {
									var71 = true;
									var53 = 1.0F;
								}

								if (!var71) {
									if (!this.doCulling && !this.isFlatShaded) {
										var53 = -1.0F;
									} else {
										float var44 = var38 - var32;
										float var45 = var39 - var33;
										float var46 = var40 - var34;
										float var47 = var35 - var32;
										float var48 = var36 - var33;
										float var49 = var37 - var34;
										var50 = var45 * var49 - var46 * var48;
										var51 = var46 * var47 - var44 * var49;
										var52 = var44 * var48 - var45 * var47;
										if (this.doCulling) {
											var53 = var50 * var32 + var51
													* var33 + var52 * var34;
											if (this.reverseCulling
													^ this.myWorld.invertCulling) {
												var53 *= -1.0F;
											}
										} else {
											var53 = -1.0F;
										}
									}
								}

								if (var53 <= 0.0F) {
									var73 = (2.5F - (float) (var24 & 5)) * 1.0E-4F;
									if (this.isFlatShaded) {
										float var180 = (float) Math
												.sqrt((double) (var50 * var50
														+ var51 * var51 + var52
														* var52));
										var50 /= var180;
										var51 /= var180;
										var52 /= var180;
									}

									int var116;
									int var117;
									int var118;
									int var119;
									int var120;
									if (!var71) {
										var115 = 0;
										var116 = -2;
										var19 = 0;
										var20 = 0;
										var21 = 0;
										var22 = 0;
										var117 = var14.textures[this.texture[var24]].width;
										var118 = var14.textures[this.texture[var24]].height;
										var119 = var117 >> 1;
										var120 = var118 >> 1;
										--var117;
										--var118;

										int var122;
										int var123;
										int var127;
										float var130;
										float var131;
										float var134;
										float var135;
										float var136;
										float var137;
										float var138;
										float var139;
										float var140;
										int var181;
										int var182;
										float var187;
										float var188;
										for (int var121 = 0; var121 < 3; ++var121) {
											var122 = var83[var24][var121];
											var123 = var84[var122];
											float var124;
											float var125;
											float var126;
											if (this.isFlatShaded
													&& var121 != 0) {
												var181 = this.objMesh.points[var24][0];
												var182 = this.objMesh.points[var24][1];
												this.objVectors.sbOrg[var182] = this.objVectors.sbOrg[var181];
												this.objVectors.srOrg[var182] = this.objVectors.srOrg[var181];
												this.objVectors.sgOrg[var182] = this.objVectors.sgOrg[var181];
												var182 = this.objMesh.points[var24][2];
												this.objVectors.sbOrg[var182] = this.objVectors.sbOrg[var181];
												this.objVectors.srOrg[var182] = this.objVectors.srOrg[var181];
												this.objVectors.sgOrg[var182] = this.objVectors.sgOrg[var181];
											} else if (var103[0][var123] != 1.2345671E7F
													&& !this.isFlatShaded) {
												this.objVectors.sbOrg[var122] = var103[2][var123];
												this.objVectors.srOrg[var122] = var103[0][var123];
												this.objVectors.sgOrg[var122] = var103[1][var123];
											} else {
												var124 = var85[var123];
												var125 = var86[var123];
												var126 = var87[var123];
												float var65;
												float var66;
												float var67;
												if (!this.isFlatShaded) {
													if (this.invScaleFactor != 1.0F) {
														var65 = var104[var123]
																* this.invScaleFactor;
														var66 = var105[var123]
																* this.invScaleFactor;
														var67 = var106[var123]
																* this.invScaleFactor;
													} else {
														var65 = var104[var123];
														var66 = var105[var123];
														var67 = var106[var123];
													}
												} else {
													var65 = var50;
													var66 = var51;
													var67 = var52;
													var127 = var84[var83[var24][0]];
													var124 = var85[var127];
													var125 = var86[var127];
													var126 = var87[var127];
												}

												float var183 = 0.0F;
												float var128 = 0.0F;
												float var129 = var75;
												var130 = var76;
												var131 = var77;
												int var132 = 0;

												while (true) {
													if (var132 >= var12) {
														if (var131 > (float) var15) {
															var131 = (float) var15;
														}

														if (var129 > (float) var15) {
															var129 = (float) var15;
														}

														if (var130 > (float) var15) {
															var130 = (float) var15;
														}

														if (this.myWorld.useFogging
																&& !this.myWorld.perPixelFogging) {
															var187 = var126
																	/ this.myWorld.fogDistance;
															if (var187 > 1.0F) {
																var187 = 1.0F;
															} else if (var187 < 0.0F) {
																var187 = 0.0F;
															}

															var188 = 1.0F - var187;
															var129 = var129
																	* var188
																	+ this.myWorld.fogColorR
																	* var187;
															var130 = var130
																	* var188
																	+ this.myWorld.fogColorG
																	* var187;
															var131 = var131
																	* var188
																	+ this.myWorld.fogColorB
																	* var187;
														}

														var103[2][var123] = var131;
														var103[0][var123] = var129;
														var103[1][var123] = var130;
														this.objVectors.sbOrg[var122] = var131;
														this.objVectors.srOrg[var122] = var129;
														this.objVectors.sgOrg[var122] = var130;
														break;
													}

													boolean var72 = false;
													if (var11.isVisible[var132]) {
														if (Config.gouraud) {
															boolean var133 = Config.fadeoutLight;
															var134 = var11.attenuation[var132];
															var135 = var68;
															if (var134 == -1.0F) {
																var133 = false;
															} else if (var134 != -2.0F) {
																var133 = true;
																var135 = 1.0F / var134;
															}

															var136 = var11.discardDistance[var132];
															var137 = 0.0F;
															if (var136 == -2.0F) {
																var136 = var69;
																var137 = var70;
															} else if (var136 != -1.0F) {
																var137 = var136
																		* var136;
															}

															var138 = var11.xTr[var132]
																	- var124;
															var139 = var11.yTr[var132]
																	- var125;
															var140 = var11.zTr[var132]
																	- var126;
															var54 = var138
																	* var138
																	+ var139
																	* var139
																	+ var140
																	* var140;
															if (var136 != -1.0F
																	&& var54 > var137) {
																var72 = true;
															} else {
																var54 = (float) Math
																		.sqrt((double) var54);
																if (this.doSpecularLighting) {
																	var56 = var11.xTr[var132] * 0.5F;
																	var57 = var11.yTr[var132] * 0.5F;
																	var58 = var11.zTr[var132] * 0.5F;
																	var55 = var56
																			* var56
																			+ var57
																			* var57
																			+ var58
																			* var58;
																	if (var55 != 0.0F) {
																		var183 = (float) ((double) (var56
																				* var65
																				+ var57
																				* var66 + var58
																				* var67) / Math
																				.sqrt((double) var55));
																	} else {
																		var183 = -1.0F;
																	}

																	if (var183 < 0.0F) {
																		var183 = 0.0F;
																	} else {
																		if (!Config.useFastSpecular) {
																			var183 = Config.specTerm
																					* (float) Math
																							.pow((double) var183,
																									(double) Config.specPow);
																		} else {
																			var183 = Config.specTerm
																					* (var183 / (Config.specPow
																							- Config.specPow
																							* var183 + var183));
																		}

																		if (var133) {
																			var183 -= var54
																					* var135;
																		}

																		if (var183 < 0.0F) {
																			var183 = 0.0F;
																		}
																	}
																} else {
																	var183 = 0.0F;
																}

																var128 = (float) Config.lightMul
																		* (var65
																				* var138
																				+ var66
																				* var139 + var67
																				* var140)
																		/ var54;
																if (var133) {
																	var128 -= var54
																			* var135;
																}

																if (var128 < 0.0F) {
																	var128 = 0.0F;
																}
															}
														} else {
															var128 = 0.0F;
														}

														if (!var72) {
															var128 += var183;
															var129 += var11.rOrg[var132]
																	* var128;
															var130 += var11.gOrg[var132]
																	* var128;
															var131 += var11.bOrg[var132]
																	* var128;
														}
													}

													++var132;
												}
											}

											var124 = this.objVectors.sz[var123];
											if (this.objVectors.zTr[var123] > var17) {
												if (var124 == -1.01F) {
													var124 = 1.0F / this.objVectors.zTr[var123];
													var89[var123] = var4
															* var85[var123]
															* var124 + var28;
													var90[var123] = var5
															* var86[var123]
															* var124
															+ var29
															+ var10.middleY
															* this.myWorld.camera.frustumOffset;
													var88[var123] = var124;
												}

												if (var89[var123] < 0.0F) {
													++var19;
												} else if (var89[var123] > (float) var26) {
													++var20;
												}

												if (var90[var123] < 0.0F) {
													++var21;
												} else if (var90[var123] > (float) var27) {
													++var22;
												}

												if (var116 == -2) {
													var116 = -1;
												}
											} else {
												++var115;
												if (var116 == -1) {
													var116 = var121;
												}
											}

											if (!this.isEnvmapped
													|| this.isBlended && var8) {
												if (!var8
														&& var87[var123] > var17) {
													var91[var122] = var95[var122]
															* var124;
													var92[var122] = var96[var122]
															* var124;
												}
											} else {
												if (!this.useCSEnvmapping) {
													switch (this.envMapDir) {
													case 1:
														var30 = var107[var123];
														var31 = var108[var123];
														break;
													case 2:
														var30 = var107[var123];
														var31 = var109[var123];
														break;
													case 3:
														var30 = var108[var123];
														var31 = var109[var123];
													}
												} else {
													switch (this.envMapDir) {
													case 1:
														var30 = var104[var123];
														var31 = var105[var123];
														break;
													case 2:
														var30 = var104[var123];
														var31 = var106[var123];
														break;
													case 3:
														var30 = var105[var123];
														var31 = var106[var123];
													}
												}

												var125 = (float) var119 + var30
														* this.invScaleFactor
														* (float) var119;
												var126 = (float) var120 + var31
														* this.invScaleFactor
														* (float) var120;
												if (var125 > (float) var117) {
													var125 = (float) var117;
												} else if (var125 < 0.0F) {
													var125 = 0.0F;
												}

												if (var126 > (float) var118) {
													var126 = (float) var118;
												} else if (var126 < 0.0F) {
													var126 = 0.0F;
												}

												if (var87[var123] > var17) {
													var91[var122] = var125
															* var124;
													var92[var122] = var126
															* var124;
													if (this.objVectors.bsu == null) {
														this.objVectors
																.createBumpmapCoords();
													}

													this.objVectors.bsu[var122] = this.objVectors.buOrg[var122]
															* var124;
													this.objVectors.bsv[var122] = this.objVectors.bvOrg[var122]
															* var124;
												}

												if (this.objVectors.eu == null) {
													this.objVectors
															.createEnvmapCoords();
												}

												this.objVectors.eu[var122] = var125;
												this.objVectors.ev[var122] = var126;
											}

											if (!var8) {
												var99[var122] = var102[var122]
														* var124;
												var98[var122] = var101[var122]
														* var124;
												var97[var122] = var100[var122]
														* var124;
											}
										}

										if (var8 && var115 != 0 && var115 < 3) {
											var115 = 0;
										}

										if (var115 != 0 && var116 == -1) {
											var116 = 0;
										}

										float var59;
										float var60;
										float var61;
										float var62;
										float var63;
										float var64;
										float var141;
										float var142;
										float var143;
										float var144;
										float var145;
										float var146;
										float var147;
										float var148;
										float var149;
										int var153;
										float var157;
										float var158;
										float var159;
										float var160;
										float var161;
										float var162;
										int var184;
										int var185;
										int var186;
										if (var115 == 2) {
											var122 = var116 - 1;
											if (var122 == -1) {
												var122 = 2;
											}

											var123 = var116 + 1;
											if (var123 == 3) {
												var123 = 0;
											}

											var181 = var83[var24][var122];
											var182 = var84[var181];
											var184 = var83[var24][var116];
											var127 = var84[var184];
											var185 = var83[var24][var123];
											var186 = var84[var185];
											if (this.isEnvmapped) {
												var59 = this.objVectors.eu[var181];
												var60 = this.objVectors.ev[var181];
												var61 = this.objVectors.eu[var184];
												var62 = this.objVectors.ev[var184];
												var63 = this.objVectors.eu[var185];
												var64 = this.objVectors.ev[var185];
											} else {
												var59 = var95[var181];
												var60 = var96[var181];
												var61 = var95[var184];
												var62 = var96[var184];
												var63 = var95[var185];
												var64 = var96[var185];
											}

											var130 = (var17 - var87[var182])
													/ (var87[var127] - var87[var182]);
											var131 = var85[var182]
													+ var130
													* (var85[var127] - var85[var182]);
											var187 = var86[var182]
													+ var130
													* (var86[var127] - var86[var182]);
											var188 = var59 + var130
													* (var61 - var59);
											var134 = var60 + var130
													* (var62 - var60);
											var135 = this.objVectors.buOrg[var181]
													+ var130
													* (this.objVectors.buOrg[var184] - this.objVectors.buOrg[var181]);
											var136 = this.objVectors.bvOrg[var181]
													+ var130
													* (this.objVectors.bvOrg[var184] - this.objVectors.bvOrg[var181]);
											var137 = var102[var181]
													+ var130
													* (var102[var184] - var102[var181]);
											var138 = var100[var181]
													+ var130
													* (var100[var184] - var100[var181]);
											var139 = var101[var181]
													+ var130
													* (var101[var184] - var101[var181]);
											var140 = 1.0F / var17;
											var141 = var4 * var131 * var140
													+ var28;
											var142 = var5 * var187 * var140
													+ var29;
											var143 = var188 * var140;
											var144 = var134 * var140;
											var145 = var135 * var140;
											var146 = var136 * var140;
											var147 = var137 * var140;
											var148 = var138 * var140;
											var149 = var139 * var140;
											var130 = (var17 - var87[var182])
													/ (var87[var186] - var87[var182]);
											var131 = var85[var182]
													+ var130
													* (var85[var186] - var85[var182]);
											var187 = var86[var182]
													+ var130
													* (var86[var186] - var86[var182]);
											float var150 = 1.0F / var17;
											float var151 = var4 * var131
													* var150 + var28;
											float var152 = var5 * var187
													* var150 + var29;
											var153 = 0;
											int var154 = 0;
											int var155 = 0;
											int var156 = 0;
											if (var89[var182] < 0.0F) {
												++var153;
											} else if (var89[var182] > (float) var26) {
												++var154;
											}

											if (var90[var182] < 0.0F) {
												++var155;
											} else if (var90[var182] > (float) var27) {
												++var156;
											}

											if (var141 < 0.0F) {
												++var153;
											} else if (var141 > (float) var26) {
												++var154;
											}

											if (var142 < 0.0F) {
												++var155;
											} else if (var142 > (float) var27) {
												++var156;
											}

											if (var151 < 0.0F) {
												++var153;
											} else if (var151 > (float) var26) {
												++var154;
											}

											if (var152 < 0.0F) {
												++var155;
											} else if (var152 > (float) var27) {
												++var156;
											}

											if (var153 < 3 && var155 < 3
													&& var154 < 3 && var156 < 3) {
												var188 = var59 + var130
														* (var63 - var59);
												var134 = var60 + var130
														* (var64 - var60);
												var135 = this.objVectors.buOrg[var181]
														+ var130
														* (this.objVectors.buOrg[var185] - this.objVectors.buOrg[var181]);
												var136 = this.objVectors.bvOrg[var181]
														+ var130
														* (this.objVectors.bvOrg[var185] - this.objVectors.bvOrg[var181]);
												var137 = var102[var181]
														+ var130
														* (var102[var185] - var102[var181]);
												var138 = var100[var181]
														+ var130
														* (var100[var185] - var100[var181]);
												var139 = var101[var181]
														+ var130
														* (var101[var185] - var101[var181]);
												var157 = var188 * var150;
												var158 = var134 * var150;
												var159 = var135 * var150;
												var160 = var136 * var150;
												var161 = var137 * var150;
												var162 = var138 * var150;
												float var163 = var139 * var150;
												var13.addToList(
														this.myWorld.clippedPolys,
														this,
														this.myWorld.clippedPolys.objMesh.anzTri,
														var24,
														(var34 + var37 + var40 + var73) * 0.33333334F,
														99999999, var1);
												this.addClippedPoly(var24,
														var89[var182],
														var90[var182],
														var88[var182],
														var91[var181],
														var92[var181],
														var98[var181],
														var97[var181],
														var99[var181], var141,
														var142, var140, var143,
														var144, var149, var148,
														var147, var151, var152,
														var150, var157, var158,
														var163, var162, var161,
														var93[var181],
														var94[var181], var145,
														var146, var159, var160);
											}

											var71 = true;
										} else if (var115 == 1) {
											var122 = var116 - 1;
											if (var122 == -1) {
												var122 = 2;
											}

											var123 = var116 + 1;
											if (var123 == 3) {
												var123 = 0;
											}

											var181 = var83[var24][var122];
											var182 = var84[var181];
											var184 = var83[var24][var116];
											var127 = var84[var184];
											var185 = var83[var24][var123];
											var186 = var84[var185];
											if (this.isEnvmapped) {
												var59 = this.objVectors.eu[var181];
												var60 = this.objVectors.ev[var181];
												var61 = this.objVectors.eu[var184];
												var62 = this.objVectors.ev[var184];
												var63 = this.objVectors.eu[var185];
												var64 = this.objVectors.ev[var185];
											} else {
												var59 = var95[var181];
												var60 = var96[var181];
												var61 = var95[var184];
												var62 = var96[var184];
												var63 = var95[var185];
												var64 = var96[var185];
											}

											var130 = (var17 - var87[var182])
													/ (var87[var127] - var87[var182]);
											var131 = var85[var182]
													+ var130
													* (var85[var127] - var85[var182]);
											var187 = var86[var182]
													+ var130
													* (var86[var127] - var86[var182]);
											var188 = var59 + var130
													* (var61 - var59);
											var134 = var60 + var130
													* (var62 - var60);
											var135 = this.objVectors.buOrg[var181]
													+ var130
													* (this.objVectors.buOrg[var184] - this.objVectors.buOrg[var181]);
											var136 = this.objVectors.bvOrg[var181]
													+ var130
													* (this.objVectors.bvOrg[var184] - this.objVectors.bvOrg[var181]);
											var137 = var102[var181]
													+ var130
													* (var102[var184] - var102[var181]);
											var138 = var100[var181]
													+ var130
													* (var100[var184] - var100[var181]);
											var139 = var101[var181]
													+ var130
													* (var101[var184] - var101[var181]);
											var140 = 1.0F / var17;
											var141 = var4 * var131 * var140
													+ var28;
											var142 = var5 * var187 * var140
													+ var29;
											var143 = var188 * var140;
											var144 = var134 * var140;
											var145 = var135 * var140;
											var146 = var136 * var140;
											var147 = var137 * var140;
											var148 = var138 * var140;
											var149 = var139 * var140;
											int var189 = 0;
											int var190 = 0;
											int var191 = 0;
											var153 = 0;
											if (this.objVectors.sx[var182] < 0.0F) {
												++var189;
											} else if (this.objVectors.sx[var182] > (float) var26) {
												++var190;
											}

											if (this.objVectors.sy[var182] < 0.0F) {
												++var191;
											} else if (this.objVectors.sy[var182] > (float) var27) {
												++var153;
											}

											if (var141 < 0.0F) {
												++var189;
											} else if (var141 > (float) var26) {
												++var190;
											}

											if (var142 < 0.0F) {
												++var191;
											} else if (var142 > (float) var27) {
												++var153;
											}

											if (this.objVectors.sx[var186] < 0.0F) {
												++var189;
											} else if (this.objVectors.sx[var186] > (float) var26) {
												++var190;
											}

											if (this.objVectors.sy[var186] < 0.0F) {
												++var191;
											} else if (this.objVectors.sy[var186] > (float) var27) {
												++var153;
											}

											if (var189 < 3 && var191 < 3
													&& var190 < 3 && var153 < 3) {
												var13.addToList(
														this.myWorld.clippedPolys,
														this,
														this.myWorld.clippedPolys.objMesh.anzTri,
														var24,
														(var34 + var37 + var40 + var73) * 0.33333334F,
														99999999, var1);
												this.addClippedPoly(var24,
														var89[var182],
														var90[var182],
														var88[var182],
														var91[var181],
														var92[var181],
														var98[var181],
														var97[var181],
														var99[var181], var141,
														var142, var140, var143,
														var144, var149, var148,
														var147, var89[var186],
														var90[var186],
														var88[var186],
														var91[var185],
														var92[var185],
														var98[var185],
														var97[var185],
														var99[var185],
														var93[var181],
														var94[var181], var145,
														var146, var93[var185],
														var94[var185]);
											}

											var130 = (var17 - var87[var186])
													/ (var87[var127] - var87[var186]);
											var131 = this.objVectors.xTr[var186]
													+ var130
													* (var85[var127] - var85[var186]);
											var187 = this.objVectors.yTr[var186]
													+ var130
													* (var86[var127] - var86[var186]);
											float var192 = var4 * var131
													* var140 + var28;
											float var193 = var5 * var187
													* var140 + var29;
											var189 = 0;
											var190 = 0;
											var191 = 0;
											var153 = 0;
											if (var192 < 0.0F) {
												++var189;
											} else if (var192 > (float) var26) {
												++var190;
											}

											if (var193 < 0.0F) {
												++var191;
											} else if (var193 > (float) var27) {
												++var153;
											}

											if (var141 < 0.0F) {
												++var189;
											} else if (var141 > (float) var26) {
												++var190;
											}

											if (var142 < 0.0F) {
												++var191;
											} else if (var142 > (float) var27) {
												++var153;
											}

											if (var89[var186] < 0.0F) {
												++var189;
											} else if (var89[var186] > (float) var26) {
												++var190;
											}

											if (var90[var186] < 0.0F) {
												++var191;
											} else if (var90[var186] > (float) var27) {
												++var153;
											}

											if (var189 < 3 && var191 < 3
													&& var190 < 3 && var153 < 3) {
												var188 = var63 + var130
														* (var61 - var63);
												var134 = var64 + var130
														* (var62 - var64);
												var135 = this.objVectors.buOrg[var185]
														+ var130
														* (this.objVectors.buOrg[var184] - this.objVectors.buOrg[var185]);
												var136 = this.objVectors.bvOrg[var185]
														+ var130
														* (this.objVectors.bvOrg[var184] - this.objVectors.bvOrg[var185]);
												var137 = var102[var185]
														+ var130
														* (var102[var184] - var102[var185]);
												var138 = this.objVectors.sgOrg[var185]
														+ var130
														* (var100[var184] - var100[var185]);
												var139 = this.objVectors.srOrg[var185]
														+ var130
														* (var101[var184] - var101[var185]);
												float var194 = var188 * var140;
												var157 = var134 * var140;
												var158 = var135 * var140;
												var159 = var136 * var140;
												var160 = var137 * var140;
												var161 = var138 * var140;
												var162 = var139 * var140;
												var13.addToList(
														this.myWorld.clippedPolys,
														this,
														this.myWorld.clippedPolys.objMesh.anzTri,
														var24,
														(var34 + var37 + var40 + var73) * 0.33333334F,
														99999999, var1);
												this.addClippedPoly(var24,
														var141, var142, var140,
														var143, var144, var149,
														var148, var147, var192,
														var193, var140, var194,
														var157, var162, var161,
														var160, var89[var186],
														var90[var186],
														var88[var186],
														var91[var185],
														var92[var185],
														var98[var185],
														var97[var185],
														var99[var185], var145,
														var146, var158, var159,
														var93[var185],
														var94[var185]);
											}

											var71 = true;
										} else if (var115 != 0) {
											var71 = true;
										}
									}

									if (!var71 && var19 < 3 && var21 < 3
											&& var20 < 3 && var22 < 3) {
										var115 = 0;
										if (!this.dynSectorDetect) {
											if (!this.oneSectorOnly
													&& this.sector != null) {
												var115 = this.sector[var24];
											} else {
												var115 = this.singleSectorNumber;
											}
										} else if (this.sectorCnt == 1) {
											var115 = this.dynSectorList[1];
										} else {
											for (var116 = 1; var116 <= this.sectorCnt; ++var116) {
												if (this.dynSectorList[var116] == var18) {
													var115 = var18;
													break;
												}
											}
										}

										var116 = 99999999;
										if (Config.doPortalHsr
												&& var115 != var18
												&& var115 != 0) {
											var116 = this.myWorld.portals
													.testAgainstPortals(this,
															var112, var179,
															var114, var24);
										}

										if (var116 != -1) {
											if (!var8) {
												var13.addToList(
														this,
														this,
														var24,
														var24,
														(var34 + var37 + var40 + var73) * 0.33333334F,
														var116, 0, var1);
											} else if (this.isTrans
													&& !this.isBumpmapped) {
												var117 = 0;
												if (this.usesMultiTexturing) {
													for (var118 = 0; var118 < this.maxStagesUsed - 1
															&& this.multiTex[var118][var24] != -1; ++var118) {
														++var117;
													}
												}

												var13.addToList(
														this,
														this,
														var24,
														var24,
														(var34 + var37 + var40 + var73) * 0.33333334F,
														var116, var117, var1);
											} else {
												var117 = this.texture[var24];
												var118 = 0;
												if (this.usesMultiTexturing) {
													for (var119 = 0; var119 < this.maxStagesUsed - 1; ++var119) {
														var120 = this.multiTex[var119][var24];
														if (var120 == -1) {
															break;
														}

														var117 += var120 << this.multiMode[var119][var24];
														++var118;
													}
												}

												var13.addToList(
														this,
														this,
														var24,
														var24,
														(float) (99999 - var117),
														var116, var118, var1);
											}
										}
									}
								}
							}

							++var24;
						} while (!var78 && var24 < this.objMesh.anzTri);
					} while (var78 && var80 < var81);
				} while (var78 && var79 < this.ocTree.getLeafCount());
			}

		}
	}

	boolean getLazyTransformationState() {
		return this.lazyTransforms;
	}

	public Matrix getInverseWorldTransformation() {
		Matrix var2 = this.getWorldTransformation();
		Matrix var1;
		if (this.lazyTransforms && this.invCache != null) {
			var1 = this.invCache.cloneMatrix();
		} else {
			var1 = var2.invert();
			if (this.lazyTransforms) {
				this.invCache = var1.cloneMatrix();
			}
		}

		return var1;
	}

	public Matrix getInverseWorldTransformation(Matrix var1) {
		if (var1 == null) {
			var1 = new Matrix();
		}

		Matrix var2 = this.getWorldTransformation(var1);
		if (this.lazyTransforms && this.invCache != null) {
			var1.setTo(this.invCache);
		} else {
			var2 = var2.invert(var1);
			if (this.lazyTransforms) {
				this.invCache = var2.cloneMatrix();
			}

			if (var1 != var2) {
				var1.setTo(var2);
			}
		}

		return var1;
	}

	final void setThisAsMain() {
		if (this.myWorld != null) {
			for (int var1 = 0; var1 < this.myWorld.objectList.size(); ++var1) {
				this.myWorld.objectList.elementAt(var1).isMainWorld = false;
				this.myWorld.objectList.elementAt(var1).oneSectorOnly = true;
			}

			this.isMainWorld = true;
			this.oneSectorOnly = false;
			this.singleSectorNumber = -1;
		} else {
			Logger.log(
					(String) "Error: An object that isn\'t part of the world can\'t be assigned as main. Add the object to the world before performing this operation.",
					0);
		}

	}

	private final void appendToObject(Object3D var1) {
		Object3D var2 = var1;
		int var3 = var1.objMesh.anzVectors;
		int var4 = var1.objMesh.anzCoords;
		int var5 = var1.objMesh.anzTri;
		int var6 = var1.sectorCnt;
		int var7;
		int var8;
		int var9;
		if (this.objVectors != null && this.objVectors.uMul != null) {
			var1.usesMultiTexturing = true;
			var1.maxStagesUsed = Math.max(var1.maxStagesUsed,
					this.maxStagesUsed);
			var1.objVectors.createMultiCoords();

			for (var7 = 0; var7 < Config.maxTextureLayers - 1; ++var7) {
				for (var8 = 0; var8 < this.objMesh.anzVectors; ++var8) {
					var9 = var8 + var3;
					var2.objVectors.uMul[var7][var9] = this.objVectors.uMul[var7][var8];
					var2.objVectors.vMul[var7][var9] = this.objVectors.vMul[var7][var8];
				}
			}
		}

		if (this.multiTex != null) {
			for (var7 = 0; var7 < Config.maxTextureLayers - 1; ++var7) {
				if (var2.multiTex == null) {
					var2.multiTex = new int[Config.maxTextureLayers - 1][var2.texture.length];
					var2.multiMode = new int[Config.maxTextureLayers - 1][var2.texture.length];
				}

				System.arraycopy(this.multiTex[var7], 0, var2.multiTex[var7],
						var5, this.objMesh.anzTri);
				System.arraycopy(this.multiMode[var7], 0, var2.multiMode[var7],
						var5, this.objMesh.anzTri);
			}
		}

		if (this.objMesh.anzVectors > 0 && this.objVectors.eu != null) {
			var2.objVectors.createEnvmapCoords();
		}

		for (var7 = 0; var7 < this.objMesh.anzVectors; ++var7) {
			var8 = var7 + var3;
			var2.objVectors.sbOrg[var8] = this.objVectors.sbOrg[var7];
			var2.objVectors.srOrg[var8] = this.objVectors.srOrg[var7];
			var2.objVectors.sgOrg[var8] = this.objVectors.sgOrg[var7];
			var2.objVectors.nuOrg[var8] = this.objVectors.nuOrg[var7];
			var2.objVectors.nvOrg[var8] = this.objVectors.nvOrg[var7];
			var2.objVectors.uOrg[var8] = this.objVectors.uOrg[var7];
			var2.objVectors.vOrg[var8] = this.objVectors.vOrg[var7];
			if (this.objVectors.eu != null) {
				var2.objVectors.eu[var8] = this.objVectors.eu[var7];
				var2.objVectors.ev[var8] = this.objVectors.ev[var7];
			}

			if (this.objVectors.alpha != null) {
				var2.objVectors.alpha[var8] = this.objVectors.alpha[var7];
			}

			var2.objVectors.buOrg[var8] = this.objVectors.buOrg[var7];
			var2.objVectors.bvOrg[var8] = this.objVectors.bvOrg[var7];
			var2.objVectors.vertexSector[var8] = this.objVectors.vertexSector[var7];
			var2.objMesh.coords[var8] = this.objMesh.coords[var7] + var4;
		}

		for (var7 = 0; var7 < this.objMesh.anzTri; ++var7) {
			var8 = var7 + var5;
			var2.objMesh.points[var8][0] = this.objMesh.points[var7][0] + var3;
			var2.objMesh.points[var8][1] = this.objMesh.points[var7][1] + var3;
			var2.objMesh.points[var8][2] = this.objMesh.points[var7][2] + var3;
		}

		var7 = this.objMesh.anzCoords;
		if (this.objMesh.obbStart != 0) {
			var7 = this.objMesh.obbStart;
		}

		for (var8 = 0; var8 < var7; ++var8) {
			var9 = var8 + var4;
			var2.objMesh.xOrg[var9] = this.objMesh.xOrg[var8];
			var2.objMesh.yOrg[var9] = this.objMesh.yOrg[var8];
			var2.objMesh.zOrg[var9] = this.objMesh.zOrg[var8];
			var2.objMesh.nxOrg[var9] = this.objMesh.nxOrg[var8];
			var2.objMesh.nyOrg[var9] = this.objMesh.nyOrg[var8];
			var2.objMesh.nzOrg[var9] = this.objMesh.nzOrg[var8];
		}

		var8 = this.objMesh.anzTri;
		if (this.texture != null) {
			System.arraycopy(this.texture, 0, var2.texture, var5, var8);
		}

		if (this.sector != null) {
			System.arraycopy(this.sector, 0, var2.sector, var5, var8);
		}

		if (this.bumpmap != null) {
			System.arraycopy(this.bumpmap, 0, var2.bumpmap, var5, var8);
		}

		if (this.basemap != null) {
			System.arraycopy(this.basemap, 0, var2.basemap, var5, var8);
		}

		if (this.sectorStartPoint != null) {
			System.arraycopy(this.sectorStartPoint, 0, var2.sectorStartPoint,
					var6, this.sectorCnt);
		}

		if (this.sectorEndPoint != null) {
			System.arraycopy(this.sectorEndPoint, 0, var2.sectorEndPoint, var6,
					this.sectorCnt);
		}

		if (this.sectorStartPoly != null) {
			System.arraycopy(this.sectorStartPoly, 0, var2.sectorStartPoly,
					var6, this.sectorCnt);
		}

		if (this.sectorEndPoly != null) {
			System.arraycopy(this.sectorEndPoly, 0, var2.sectorEndPoly, var6,
					this.sectorCnt);
		}

		var2.objMesh.anzTri += this.objMesh.anzTri;
		var2.objMesh.anzCoords += var7;
		var2.objMesh.anzVectors += this.objMesh.anzVectors;
	}

	private final void switchTriangles(int var1, int var2) {
		int var3;
		if (this.sector != null) {
			var3 = this.sector[var1];
			this.sector[var1] = this.sector[var2];
			this.sector[var2] = var3;
		}

		var3 = this.texture[var1];
		this.texture[var1] = this.texture[var2];
		this.texture[var2] = var3;
		if (this.usesMultiTexturing) {
			for (int var4 = 0; var4 < this.maxStagesUsed - 1; ++var4) {
				var3 = this.multiTex[var4][var1];
				this.multiTex[var4][var1] = this.multiTex[var4][var2];
				this.multiTex[var4][var2] = var3;
				var3 = this.multiMode[var4][var1];
				this.multiMode[var4][var1] = this.multiMode[var4][var2];
				this.multiMode[var4][var2] = var3;
			}
		}

		var3 = this.basemap[var1];
		this.basemap[var1] = this.basemap[var2];
		this.basemap[var2] = var3;
		if (this.bumpmap != null) {
			var3 = this.bumpmap[var1];
			this.bumpmap[var1] = this.bumpmap[var2];
			this.bumpmap[var2] = var3;
		}

		int[] var5 = this.objMesh.points[var1];
		this.objMesh.points[var1] = this.objMesh.points[var2];
		this.objMesh.points[var2] = var5;
	}

	final void getProjectedPoint(float var1, float var2, float var3,
			SimpleVector var4, float[] var5) {
		if (this.tempMatProj == null) {
			this.tempMatProj = new Matrix();
		}

		Matrix var6 = this.getWorldTransformationTweaked(this.tempMatProj);
		float var7 = var6.mat[0][0];
		float var8 = var6.mat[1][0];
		float var9 = var6.mat[1][1];
		float var10 = var6.mat[2][1];
		float var11 = var6.mat[2][0];
		float var12 = var6.mat[0][1];
		float var13 = var6.mat[2][2];
		float var14 = var6.mat[1][2];
		float var15 = var6.mat[0][2];
		float var16 = var6.mat[3][0];
		float var17 = var6.mat[3][1];
		float var18 = var6.mat[3][2];
		float var19 = var1 * var7 + var2 * var8 + var3 * var11 + var16;
		float var20 = var1 * var12 + var2 * var9 + var3 * var10 + var17;
		float var21 = var1 * var15 + var2 * var14 + var3 * var13 + var18;
		if (var4 != null) {
			var4.x = var19;
			var4.y = var20;
			var4.z = var21;
		}

		if (var5 != null) {
			var5[0] = var19;
			var5[1] = var20;
			var5[2] = var21;
		}

	}

	void notifyCollisionListeners(int var1, int var2, Object3D[] var3,
			SimpleVector var4) {
		this.notifyCollisionListeners((Object3D) null, var1, var2, var3, var4);
	}

	void notifyCollisionListeners(Object3D var1, int var2, int var3,
			Object3D[] var4, SimpleVector var5) {
		if (this.collisionListener != null && !this.disableListeners) {
			CollisionEvent var6 = new CollisionEvent(this, var1, var2, var3,
					var4, var5);
			int var7 = this.collisionListener.size();

			for (int var8 = 0; var8 < var7; ++var8) {
				CollisionListener var9 = (CollisionListener) this.collisionListener
						.elementAt(var8);
				if (var9.requiresPolygonIDs() && var6.getPolygonIDs() == null) {
					var6.setPolygonIDs(this.polygonIDs, this.pIDCount);
				}

				var9.collision(var6);
			}

		}
	}

	void reallyStrip() {
		this.stripped = true;
		this.toStrip = false;
		this.objVectors.strip();
		this.objMesh.strongStrip(this.myWorld, this);
	}

	void resetPolygonIDCount() {
		this.pIDCount = 0;
		this.lastAddedID = -1;
	}

	IRenderHook getHookInternal() {
		return (IRenderHook) (this.renderHook == null && this.myWorld != null
				&& this.myWorld.globalShader != null ? this.myWorld.globalShader
				: this.renderHook);
	}

	float[] getWorldSpaceBounds() {
		float[] var1 = this.getMesh().getBoundingBox();
		SimpleVector var2 = new SimpleVector(var1[0], var1[2], var1[4]);
		SimpleVector var3 = new SimpleVector(var1[1], var1[3], var1[5]);
		SimpleVector[] var4 = new SimpleVector[] {
				new SimpleVector(var2.x, var2.y, var3.z),
				new SimpleVector(var2.x, var2.y, var2.z),
				new SimpleVector(var3.x, var2.y, var2.z),
				new SimpleVector(var3.x, var2.y, var3.z),
				new SimpleVector(var3.x, var3.y, var2.z),
				new SimpleVector(var3.x, var3.y, var3.z),
				new SimpleVector(var2.x, var3.y, var2.z),
				new SimpleVector(var2.x, var3.y, var3.z) };
		float var5 = Float.MAX_VALUE;
		float var6 = Float.MAX_VALUE;
		float var7 = Float.MAX_VALUE;
		float var8 = -3.4028235E38F;
		float var9 = -3.4028235E38F;
		float var10 = -3.4028235E38F;

		for (int var11 = 0; var11 < 8; ++var11) {
			var4[var11].matMul(this.getWorldTransformation());
			if (var4[var11].x < var5) {
				var5 = var4[var11].x;
			}

			if (var4[var11].y < var6) {
				var6 = var4[var11].y;
			}

			if (var4[var11].z < var7) {
				var7 = var4[var11].z;
			}

			if (var4[var11].x > var8) {
				var8 = var4[var11].x;
			}

			if (var4[var11].y > var9) {
				var9 = var4[var11].y;
			}

			if (var4[var11].z > var10) {
				var10 = var4[var11].z;
			}
		}

		float[] var12 = new float[] { var5, var8, var6, var9, var7, var10 };
		return var12;
	}

	private void addPolygonID(int var1) {
		if (this.collisionListener != null && !this.disableListeners) {
			if (this.polygonIDs == null) {
				this.polygonIDs = new int[Config.polygonIDLimit];
				this.pIDCount = 0;
			}

			if (this.pIDCount < this.polygonIDs.length) {
				if (var1 == this.lastAddedID) {
					return;
				}

				for (int var2 = 0; var2 < this.pIDCount; ++var2) {
					if (this.polygonIDs[var2] == var1) {
						return;
					}
				}

				this.polygonIDs[this.pIDCount] = var1;
				this.lastAddedID = var1;
				++this.pIDCount;
			}

		}
	}

	private Matrix getCachedMatrix(int var1) {
		if (!this.useMatrixCache) {
			return new Matrix();
		} else {
			Matrix[] var2 = (Matrix[]) ((Matrix[]) this.matrixCache.get(Thread
					.currentThread()));
			if (var2 == null) {
				var2 = new Matrix[] { new Matrix(), new Matrix() };
				this.matrixCache.put(Thread.currentThread(), var2);
			}

			if (this.matrixCache.size() > 50) {
				this.matrixCache.clear();
			}

			var2[var1].setIdentity();
			return var2[var1];
		}
	}

	private void checkBumpmap() {
		if (this.bumpmap == null) {
			this.bumpmap = new int[this.texture.length];
		}

	}

	private void compileInternal() {
		if (this.compiled == null) {
			this.compiled = new Vector();
		}

		if (!this.hasBeenBuild && Config.autoBuild) {
			this.build();
		}

		Config.lightMul = 1;
		Config.glVertexArrays = true;
	}

	private void readObject(ObjectInputStream var1) throws IOException,
			ClassNotFoundException {
		var1.defaultReadObject();
		this.mat2 = new Matrix();
		this.mat3 = new Matrix();
		this.mat5 = new Matrix();
		this.mat6 = new Matrix();
		this.matrixCache = new Hashtable(3);
		this.transBuffer = new Matrix();
		this.xWs = new float[8];
		this.yWs = new float[8];
		this.zWs = new float[8];
	}
}
