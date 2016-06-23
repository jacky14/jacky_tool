package dev.mouse.ms3d;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.FloatBuffer;

//import android.opengl.GLES20;
import dev.mouse.io.LittleEndianInputStream;
import dev.mouse.lib.Vector3f;
import dev.mouse.util.BufferBuilder;
import dev.mouse.util.TextureManager;

/**
 * ms3d模型信息
 * @author DEVILIVED
 *
 */
public class MS3DModel {

	private FloatBuffer[] vertexCoordingBuffer;	//顶点缓冲
	
	
	private FloatBuffer[][] AttackvertexCoordingBuffers;	//主角攻击顶点缓冲
	/**
	 * 该模型缓存的每帧对应的时刻
	 */
	private float [] Attacktimes;
	
	
	
	public void setAttacktimes(float[] attacktimes) {
		Attacktimes = attacktimes;
	}


	public final void Attackdraw(int apoint,int maTexCoorHandle,int index) {
		   if(AttackvertexCoordingBuffers==null){
			   AttackvertexCoordingBuffers=new FloatBuffer[Attacktimes.length][];
		   }
		   if(this.AttackvertexCoordingBuffers[index]==null){
			   
		 
			float time=Attacktimes[index];
			this.updateJoint(time,null);
			this.updateVectexs();
			
			
			
			
			int group_size = this.groups.length;
	        int trian_size = 0;
	        MS3DTriangle triangle = null;
	        MS3DGroup group = null;
	        int[] indexs = null;
	        int[] vertexIndexs = null;
	        
	        
	        AttackvertexCoordingBuffers[index]=new FloatBuffer[group_size];
	        
			for(int i=0; i<group_size; i++) {
		        	group = this.groups[i];
		        	indexs = group.getIndicies();
		        	trian_size = indexs.length;
		        	
		        	this.AttackvertexCoordingBuffers[index][i]=BufferBuilder.buildFloatBuffer(trian_size * 9);
		        	
		        	for(int j=0; j<trian_size; j++) {
		        			triangle = this.triangles[indexs[j]];
		        			vertexIndexs = triangle.getIndexs();
		        			for(int k=0; k<3; k++) {
		        				this.AttackvertexCoordingBuffers[index][i].put(this.vertexs[vertexIndexs[k]].getBuffer().getVector3fArray());
		        			}
		        	}
		        	this.AttackvertexCoordingBuffers[index][i].position(0);
		        	
		   }
			
		   }
		
		   
		   
			//设置定点数组
			int group_size = this.groups.length;
	        int trian_size = 0;
	        MS3DGroup group = null;
	        int[] indexs = null;
	       
	        
	        //材质
	        MS3DMaterial material = null;
	        for(int i=0; i<group_size; i++) {
	        	group = this.groups[i];
	        	indexs = group.getIndicies();
	        	trian_size = indexs.length;
	        	//有材质
	        	if(group.getMaterialIndex() > -1) {
	        		material = this.materials[group.getMaterialIndex()];
	        		this.textureManager.fillTexture(material.getName());
	        		//gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.texCoordingBuffer[i]);
	        	}
	        	
	        	//int a=vertexCoordingBuffer[i].;
	        	//纹理uv
	        	//GLES20.glVertexAttribPointer(maTexCoorHandle,2,GLES20.GL_FLOAT,false,2*4,this.texCoordingBuffer[i]);
	        	//顶点
	        	//GLES20.glVertexAttribPointer(apoint,3,GLES20.GL_FLOAT,false,3*4,this.AttackvertexCoordingBuffers[index][i]);
	        	//允许使用顶点位置数据
		        //GLES20.glEnableVertexAttribArray(apoint);  
		        //允许使用纹理uv位置数据
		       // GLES20.glEnableVertexAttribArray(maTexCoorHandle);
		       //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0,trian_size * 3); 
	        }
	        
		}
	
	/**
	 * 使用缓存使用内存记录每一帧所需要的数据
	 */
	private FloatBuffer[][] vertexCoordingBuffers;	//顶点缓冲
	/**
	 * 该模型缓存的每帧对应的时刻
	 */
	private float [] times;
	
	public float[] getTimes() {
		return times;
	}


	public void setTimes(float[] times) {
		this.times = times;
	}
	
	//某一骨骼所有帧信息
	MS3DJoint[] timejoint ;
	
	
	public MS3DJoint[] getTimejoint() {
		return timejoint;
	}


	public void updateWeapon(MS3DJoint[] joint){
		
		vertexCoordingBuffers=new FloatBuffer[joint.length][];
		
		for(int m=0;m<joint.length;m++){
			MS3DJoint mj=joint[m];
			
			
			int count = this.vertexs.length;

			for(int i=0; i<count; i++){
				MS3DVertex vertex=this.vertexs[i];
				if(vertex.getNewverbuff()==null){
					vertex.setNewverbuff(mj.getAbsolute().invTransformAndRotate(vertex.getVertex()));
				}
				vertex.setBuffer(mj.getMatrix().transform(vertex.getNewverbuff()));
			}
			
			
			int group_size = this.groups.length;
	        int trian_size = 0;
	        MS3DTriangle triangle = null;
	        MS3DGroup group = null;
	        int[] indexs = null;
	        int[] vertexIndexs = null;
	        
	        
	        vertexCoordingBuffers[m]=new FloatBuffer[group_size];
	        
			for(int i=0; i<group_size; i++) {
		        	group = this.groups[i];
		        	indexs = group.getIndicies();
		        	trian_size = indexs.length;
		        	
		        	this.vertexCoordingBuffers[m][i]=BufferBuilder.buildFloatBuffer(trian_size * 9);
		        	
		        	for(int j=0; j<trian_size; j++){
		        			triangle = this.triangles[indexs[j]];
		        			vertexIndexs = triangle.getIndexs();
		        			for(int k=0; k<3; k++){
		        				this.vertexCoordingBuffers[m][i].put(this.vertexs[vertexIndexs[k]].getBuffer().getVector3fArray());
		        			}
		        	}
		        	this.vertexCoordingBuffers[m][i].position(0);
		        	
		      }
			
			
		}
		
		
		
		
	}
	/**
	 * 根据传入的时间计算缓存数据，一个时刻为一帧
	 *
	 * @param isMan 是否是人物模型 如果是则在移动帧更新骨骼时 不更新移动
	 * join  该模型需要记录那块特殊骨骼所有帧变化情况   null 为不记录谷歌变化情况    maxindex 代表记录该骨骼0到那一帧的数据
	 */
	public  void setbuff( Boolean  isMan,Integer join,Integer maxindex){
		vertexCoordingBuffers=new FloatBuffer[times.length][];
		//长期以来的问题是总是出现卡顿现象，，，，唯一能想到的解决方案使用缓存  以牺牲内存换取CPU  目前手机内存普遍较大  这种方法应该适用
		if(join!=null){
			timejoint=new MS3DJoint[maxindex];
		}
		
		for(int m=0;m<times.length;m++){
			float time=times[m];
			this.updateJoint(time,isMan);
			this.updateVectexs();
			
			
			if(join!=null&&m<maxindex){
				timejoint[m]=new MS3DJoint();
				timejoint[m].setAbsolute(this.joints[join].getAbsolute().copy());		
				timejoint[m].setMatrix(this.joints[join].getMatrix().copy())	;	
						//this.joints[join];
			}
			
			int group_size = this.groups.length;
	        int trian_size = 0;
	        MS3DTriangle triangle = null;
	        MS3DGroup group = null;
	        int[] indexs = null;
	        int[] vertexIndexs = null;
	        
	        
	        vertexCoordingBuffers[m]=new FloatBuffer[group_size];
	        
			for(int i=0; i<group_size; i++) {
		        	group = this.groups[i];
		        	indexs = group.getIndicies();
		        	trian_size = indexs.length;
		        	
		        	this.vertexCoordingBuffers[m][i]=BufferBuilder.buildFloatBuffer(trian_size * 9);
		        	
		        	for(int j=0; j<trian_size; j++) {
		        			triangle = this.triangles[indexs[j]];
		        			vertexIndexs = triangle.getIndexs();
		        			for(int k=0; k<3; k++) {
		        				this.vertexCoordingBuffers[m][i].put(this.vertexs[vertexIndexs[k]].getBuffer().getVector3fArray());
		        			}
		        	}
		        	this.vertexCoordingBuffers[m][i].position(0);
		      }
		}
	}
	
	
	
	private FloatBuffer[] texCoordingBuffer;//材质缓冲
	
	private TextureManager textureManager;	//材质管理器

	private MS3DHeader header;				//头信息
	
	private MS3DVertex[] vertexs;			//顶点信息
	
	private MS3DTriangle[] triangles;		//三角形索引
	
	private MS3DGroup[] groups;				//组信息
	
	private MS3DMaterial[] materials;		//材质信息
	
	public float fps;						//fps信息
	
	public float current_time;				//当前时间
	
	public float total_time;				//总时间
	
	public float frame_count;				//关键帧数
	
	public MS3DJoint[] joints;				//关节信息
	
	private MS3DModel() {}
	
	
	public  void saveObj(String filename) throws IOException{
		File f = new File(filename);
		if(!f.exists()){
			f.createNewFile();
		}
		PrintWriter pw =new PrintWriter(f);
		for(int i=0;i<vertexs.length;i++){
			Vector3f  v =  vertexs[i].getVerbuff();
			pw.println("v " +v.getX() +" " + v.getY() + " " + v.getZ());
		}
		
		for(int i=0;i<triangles.length;i++){
			int[] id = triangles[i].getIndexs();
			Vector3f s = triangles[i].getS();
			Vector3f t = triangles[i].getT();
			
			pw.println("vt "+s.getX() +" "+(1-t.getX()));
			pw.println("vt "+s.getY() +" "+(1-t.getY()));
			pw.println("vt "+s.getZ() +" "+(1-t.getZ()));
			
			
			pw.println("f " +(id[0]+1)+"/" + (i*3+1)  +" " + (id[1]+1) +"/" + (i*3+2)+ " " + (id[2]+1)+"/" + (i*3+3));
		}
		pw.close();
		
		
	}
	
	/**
	 * 
	 * @param joint
	 * @param apoint
	 * @param maTexCoorHandle
	 * @param newver 是否更新初始顶点
	 */
	/*public void updatewpean(MS3DJoint joint ,int apoint,int maTexCoorHandle,boolean newver,float time){
		int count = this.vertexs.length;
		
		if(this.current_time != time){
			
		
		for(int i=0; i<count; i++){
			MS3DVertex vertex=this.vertexs[i];
			if(newver){
				vertex.setNewverbuff(joint.getAbsolute().invTransformAndRotate(vertex.getVertex()));
			}
			vertex.setBuffer(joint.getMatrix().transform(vertex.getNewverbuff()));
			
		}
		this.draw(apoint, maTexCoorHandle,true);
		this.current_time = time;
		}else {
			this.draw(apoint, maTexCoorHandle,false);
		}
		
		
	}*/
	/*public final void animate(float time,int apoint,int maTexCoorHandle) {
		//相同时间不做更新
		if(this.current_time != time){
			this.updateJoint(time);
			this.updateVectexs();
			this.draw(apoint, maTexCoorHandle,true);
		}else{
			this.draw(apoint, maTexCoorHandle,false);
		}
	}*/
	
	
	
	
	/**
	 * 更新关节
	 * @param time 时间  isman 更新骨骼时是否更新移动的平移信息
	 */
	public void updateJoint(float time,Boolean isMan){
		this.current_time = time;
		if(this.current_time > this.total_time)
			this.current_time = 0.0f;
		//更新骨骼
		int size = this.joints.length;
		for(int i=0; i<size; i++){
			if(i==0){//不是指定的骨骼 不该动平移
				this.joints[i].update(this.current_time,isMan);
			}else {
				this.joints[i].update(this.current_time,null);
			}
			
		}
	}
    public final void draw(int apoint,int maTexCoorHandle,int index) {
		//设置定点数组
		int group_size = this.groups.length;
        int trian_size = 0;
        MS3DGroup group = null;
        int[] indexs = null;
       
        
        //材质
        MS3DMaterial material = null;
        for(int i=0; i<group_size; i++) {
        	group = this.groups[i];
        	indexs = group.getIndicies();
        	trian_size = indexs.length;
        	//有材质
        	if(group.getMaterialIndex() > -1) {
        		material = this.materials[group.getMaterialIndex()];
        		this.textureManager.fillTexture(material.getName());
        		//gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.texCoordingBuffer[i]);
        	}
        	
        	/*//是否更新顶点缓冲
        	if(isupdate){
        		buffer = this.vertexCoordingBuffer[i];
        		for(int j=0; j<trian_size; j++) {
        			triangle = this.triangles[indexs[j]];
        			vertexIndexs = triangle.getIndexs();
        			for(int k=0; k<3; k++) {
        				buffer.put(this.vertexs[vertexIndexs[k]].getBuffer().getVector3fArray());
        			}
        		}
        		buffer.position(0);
        	}*/
        	//int a=vertexCoordingBuffer[i].;
        	//纹理uv
        	//GLES20.glVertexAttribPointer(maTexCoorHandle,2,GLES20.GL_FLOAT,false,2*4,this.texCoordingBuffer[i]);
        	//顶点
        	
        	//如果顶点缓存为空则  说明该模型为英雄模型绘制待机时动作
        	if(this.vertexCoordingBuffers==null||this.vertexCoordingBuffers[index]==null||this.vertexCoordingBuffers[index][i]==null){
        		//GLES20.glVertexAttribPointer(apoint,3,GLES20.GL_FLOAT,false,3*4,this.AttackvertexCoordingBuffers[0][i]);
        	}else {
        		//GLES20.glVertexAttribPointer(apoint,3,GLES20.GL_FLOAT,false,3*4,this.vertexCoordingBuffers[index][i]);
			}
        	
        	//允许使用顶点位置数据
	        //GLES20.glEnableVertexAttribArray(apoint);  
	        //允许使用纹理uv位置数据
	       // GLES20.glEnableVertexAttribArray(maTexCoorHandle);
	       // GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0,trian_size * 3); 
        }
        
	}
	
	public void updateVectexs(){
		int count = this.vertexs.length;
		for(int i=0; i<count; i++){
			this.updateVectex(i);
		}
	}
	/**
	 * 更新顶点
	 */
	private void updateVectex(int index){
		//骨骼变换顶点坐标
		MS3DVertex vertex = this.vertexs[index];
		//是否有骨骼
		if(vertex.getBone() == -1){
			//无骨骼控制
			vertex.setBuffer(vertex.getVertex());
		}else{
			//有骨骼控制
			MS3DJoint joint = this.joints[vertex.getBone()];
			
			if(vertex.getVerbuff()==null){
				vertex.setVerbuff(joint.getAbsolute().invTransformAndRotate(vertex.getVertex()));
			}
			vertex.setBuffer(joint.getMatrix().transform(vertex.getVerbuff()));
		}
	}
	
	
	/**
	 * 加载模型
	 * @param is
	 * @param manager 材质管理器
	 * @return
	 */
	public final static MS3DModel load(InputStream is,float[] mMMatrix,TextureManager manager,String mangname) {
		MS3DModel model = null;
		LittleEndianInputStream fis = null;
		try {
			fis = new LittleEndianInputStream(is);
			model = new MS3DModel();
			//材质管理器
			model.textureManager = manager;
			//头信息
			model.header = MS3DHeader.load(fis);
			//顶点
			model.vertexs = MS3DVertex.load(fis, mMMatrix );
			//三角形索引
			model.triangles = MS3DTriangle.load(fis);
			//Group
			model.groups = MS3DGroup.load(fis);
			//材质信息
			model.materials = MS3DMaterial.load(fis,manager,mangname);
			//FPS
			model.fps = fis.readFloat();
			//当前时间
			model.current_time = fis.readFloat();
			//关键帧数
			model.frame_count = fis.readInt();
			//总时间
			model.total_time = model.frame_count / model.fps;
			//关节
			model.joints = MS3DJoint.load(fis);
			//初始化缓冲
			model.initBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.gc();
		return model;
	}

	/**
	 * 初始化
	 */
	private void initBuffer() {
		//将关节更新到0.0f
		this.updateJoint(0.0f,null);
		//更新顶点坐标
		this.updateVectexs();
		//材质、顶点坐标缓冲
		//组数量
		int count = this.groups.length;
		//每组三角形个数
		int trian_size = 0;
		//临时组信息
		MS3DGroup group = null;
		//临时三角形信息
		MS3DTriangle triangle = null;
		//材质坐标缓冲
		this.texCoordingBuffer = new FloatBuffer[count];
		//顶点坐标缓冲
		this.vertexCoordingBuffer = new FloatBuffer[count];
		//三角形序号
		int[] indexs = null;
		//顶点序号
		int[] vertexIndexs = null;
		FloatBuffer buffer = null;
		for(int i=0; i<count; i++) {
			group = this.groups[i];
			indexs = group.getIndicies();
			trian_size = indexs.length;
			this.texCoordingBuffer[i] = BufferBuilder.buildFloatBuffer(trian_size * 6);
			this.vertexCoordingBuffer[i] = BufferBuilder.buildFloatBuffer(trian_size * 9);
			for(int j=0; j<trian_size; j++) {
				triangle = this.triangles[indexs[j]];
				vertexIndexs = triangle.getIndexs();
				//缓冲
				for(int k=0; k<3; k++){
					//设置材质贴图缓冲
					buffer = this.texCoordingBuffer[i];
					buffer.put(triangle.getS().getVector3fArray()[k]);
					buffer.put(triangle.getT().getVector3fArray()[k]);
					//初始化顶点缓冲
					buffer = this.vertexCoordingBuffer[i];
					buffer.put(this.vertexs[vertexIndexs[k]].getBuffer().getVector3fArray());
				}
			}
			this.texCoordingBuffer[i].position(0);
			this.vertexCoordingBuffer[i].position(0);
		}
		
	}
	
	public final MS3DHeader getHeader() {
		return header;
	}

	public final MS3DVertex[] getVertexs() {
		return vertexs;
	}

	public final MS3DTriangle[] getTriangles() {
		return triangles;
	}

	public final MS3DGroup[] getGroups() {
		return groups;
	}

	public final MS3DMaterial[] getMaterials() {
		return materials;
	}

	public final float getFps() {
		return fps;
	}

	public final float getCurrent_time() {
		return current_time;
	}

	public final float getFrame_count() {
		return frame_count;
	}

	public final MS3DJoint[] getJoints() {
		return joints;
	}

	public final TextureManager getTextureManager() {
		return textureManager;
	}


	public final float getTotal_time() {
		return total_time;
	}


	public FloatBuffer[] getVertexCoordingBuffer() {
		return vertexCoordingBuffer;
	}


	public void setVertexCoordingBuffer(FloatBuffer[] vertexCoordingBuffer) {
		this.vertexCoordingBuffer = vertexCoordingBuffer;
	}


	public FloatBuffer[] getTexCoordingBuffer() {
		return texCoordingBuffer;
	}


	public void setTexCoordingBuffer(FloatBuffer[] texCoordingBuffer) {
		this.texCoordingBuffer = texCoordingBuffer;
	}


	public void setTextureManager(TextureManager textureManager) {
		this.textureManager = textureManager;
	}


	public void setHeader(MS3DHeader header) {
		this.header = header;
	}


	public void setVertexs(MS3DVertex[] vertexs) {
		this.vertexs = vertexs;
	}


	public void setTriangles(MS3DTriangle[] triangles) {
		this.triangles = triangles;
	}


	public void setGroups(MS3DGroup[] groups) {
		this.groups = groups;
	}


	public void setMaterials(MS3DMaterial[] materials) {
		this.materials = materials;
	}


	public void setFps(float fps) {
		this.fps = fps;
	}


	public void setCurrent_time(float current_time) {
		this.current_time = current_time;
	}


	public void setTotal_time(float total_time) {
		this.total_time = total_time;
	}


	public void setFrame_count(float frame_count) {
		this.frame_count = frame_count;
	}


	public void setJoints(MS3DJoint[] joints) {
		this.joints = joints;
	}
	
	
	
	
}
