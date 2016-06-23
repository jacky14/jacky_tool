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
 * ms3dģ����Ϣ
 * @author DEVILIVED
 *
 */
public class MS3DModel {

	private FloatBuffer[] vertexCoordingBuffer;	//���㻺��
	
	
	private FloatBuffer[][] AttackvertexCoordingBuffers;	//���ǹ������㻺��
	/**
	 * ��ģ�ͻ����ÿ֡��Ӧ��ʱ��
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
		
		   
		   
			//���ö�������
			int group_size = this.groups.length;
	        int trian_size = 0;
	        MS3DGroup group = null;
	        int[] indexs = null;
	       
	        
	        //����
	        MS3DMaterial material = null;
	        for(int i=0; i<group_size; i++) {
	        	group = this.groups[i];
	        	indexs = group.getIndicies();
	        	trian_size = indexs.length;
	        	//�в���
	        	if(group.getMaterialIndex() > -1) {
	        		material = this.materials[group.getMaterialIndex()];
	        		this.textureManager.fillTexture(material.getName());
	        		//gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.texCoordingBuffer[i]);
	        	}
	        	
	        	//int a=vertexCoordingBuffer[i].;
	        	//����uv
	        	//GLES20.glVertexAttribPointer(maTexCoorHandle,2,GLES20.GL_FLOAT,false,2*4,this.texCoordingBuffer[i]);
	        	//����
	        	//GLES20.glVertexAttribPointer(apoint,3,GLES20.GL_FLOAT,false,3*4,this.AttackvertexCoordingBuffers[index][i]);
	        	//����ʹ�ö���λ������
		        //GLES20.glEnableVertexAttribArray(apoint);  
		        //����ʹ������uvλ������
		       // GLES20.glEnableVertexAttribArray(maTexCoorHandle);
		       //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0,trian_size * 3); 
	        }
	        
		}
	
	/**
	 * ʹ�û���ʹ���ڴ��¼ÿһ֡����Ҫ������
	 */
	private FloatBuffer[][] vertexCoordingBuffers;	//���㻺��
	/**
	 * ��ģ�ͻ����ÿ֡��Ӧ��ʱ��
	 */
	private float [] times;
	
	public float[] getTimes() {
		return times;
	}


	public void setTimes(float[] times) {
		this.times = times;
	}
	
	//ĳһ��������֡��Ϣ
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
	 * ���ݴ����ʱ����㻺�����ݣ�һ��ʱ��Ϊһ֡
	 *
	 * @param isMan �Ƿ�������ģ�� ����������ƶ�֡���¹���ʱ �������ƶ�
	 * join  ��ģ����Ҫ��¼�ǿ������������֡�仯���   null Ϊ����¼�ȸ�仯���    maxindex �����¼�ù���0����һ֡������
	 */
	public  void setbuff( Boolean  isMan,Integer join,Integer maxindex){
		vertexCoordingBuffers=new FloatBuffer[times.length][];
		//�������������������ǳ��ֿ������󣬣�����Ψһ���뵽�Ľ������ʹ�û���  �������ڴ滻ȡCPU  Ŀǰ�ֻ��ڴ��ձ�ϴ�  ���ַ���Ӧ������
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
	
	
	
	private FloatBuffer[] texCoordingBuffer;//���ʻ���
	
	private TextureManager textureManager;	//���ʹ�����

	private MS3DHeader header;				//ͷ��Ϣ
	
	private MS3DVertex[] vertexs;			//������Ϣ
	
	private MS3DTriangle[] triangles;		//����������
	
	private MS3DGroup[] groups;				//����Ϣ
	
	private MS3DMaterial[] materials;		//������Ϣ
	
	public float fps;						//fps��Ϣ
	
	public float current_time;				//��ǰʱ��
	
	public float total_time;				//��ʱ��
	
	public float frame_count;				//�ؼ�֡��
	
	public MS3DJoint[] joints;				//�ؽ���Ϣ
	
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
	 * @param newver �Ƿ���³�ʼ����
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
		//��ͬʱ�䲻������
		if(this.current_time != time){
			this.updateJoint(time);
			this.updateVectexs();
			this.draw(apoint, maTexCoorHandle,true);
		}else{
			this.draw(apoint, maTexCoorHandle,false);
		}
	}*/
	
	
	
	
	/**
	 * ���¹ؽ�
	 * @param time ʱ��  isman ���¹���ʱ�Ƿ�����ƶ���ƽ����Ϣ
	 */
	public void updateJoint(float time,Boolean isMan){
		this.current_time = time;
		if(this.current_time > this.total_time)
			this.current_time = 0.0f;
		//���¹���
		int size = this.joints.length;
		for(int i=0; i<size; i++){
			if(i==0){//����ָ���Ĺ��� ���ö�ƽ��
				this.joints[i].update(this.current_time,isMan);
			}else {
				this.joints[i].update(this.current_time,null);
			}
			
		}
	}
    public final void draw(int apoint,int maTexCoorHandle,int index) {
		//���ö�������
		int group_size = this.groups.length;
        int trian_size = 0;
        MS3DGroup group = null;
        int[] indexs = null;
       
        
        //����
        MS3DMaterial material = null;
        for(int i=0; i<group_size; i++) {
        	group = this.groups[i];
        	indexs = group.getIndicies();
        	trian_size = indexs.length;
        	//�в���
        	if(group.getMaterialIndex() > -1) {
        		material = this.materials[group.getMaterialIndex()];
        		this.textureManager.fillTexture(material.getName());
        		//gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.texCoordingBuffer[i]);
        	}
        	
        	/*//�Ƿ���¶��㻺��
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
        	//����uv
        	//GLES20.glVertexAttribPointer(maTexCoorHandle,2,GLES20.GL_FLOAT,false,2*4,this.texCoordingBuffer[i]);
        	//����
        	
        	//������㻺��Ϊ����  ˵����ģ��ΪӢ��ģ�ͻ��ƴ���ʱ����
        	if(this.vertexCoordingBuffers==null||this.vertexCoordingBuffers[index]==null||this.vertexCoordingBuffers[index][i]==null){
        		//GLES20.glVertexAttribPointer(apoint,3,GLES20.GL_FLOAT,false,3*4,this.AttackvertexCoordingBuffers[0][i]);
        	}else {
        		//GLES20.glVertexAttribPointer(apoint,3,GLES20.GL_FLOAT,false,3*4,this.vertexCoordingBuffers[index][i]);
			}
        	
        	//����ʹ�ö���λ������
	        //GLES20.glEnableVertexAttribArray(apoint);  
	        //����ʹ������uvλ������
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
	 * ���¶���
	 */
	private void updateVectex(int index){
		//�����任��������
		MS3DVertex vertex = this.vertexs[index];
		//�Ƿ��й���
		if(vertex.getBone() == -1){
			//�޹�������
			vertex.setBuffer(vertex.getVertex());
		}else{
			//�й�������
			MS3DJoint joint = this.joints[vertex.getBone()];
			
			if(vertex.getVerbuff()==null){
				vertex.setVerbuff(joint.getAbsolute().invTransformAndRotate(vertex.getVertex()));
			}
			vertex.setBuffer(joint.getMatrix().transform(vertex.getVerbuff()));
		}
	}
	
	
	/**
	 * ����ģ��
	 * @param is
	 * @param manager ���ʹ�����
	 * @return
	 */
	public final static MS3DModel load(InputStream is,float[] mMMatrix,TextureManager manager,String mangname) {
		MS3DModel model = null;
		LittleEndianInputStream fis = null;
		try {
			fis = new LittleEndianInputStream(is);
			model = new MS3DModel();
			//���ʹ�����
			model.textureManager = manager;
			//ͷ��Ϣ
			model.header = MS3DHeader.load(fis);
			//����
			model.vertexs = MS3DVertex.load(fis, mMMatrix );
			//����������
			model.triangles = MS3DTriangle.load(fis);
			//Group
			model.groups = MS3DGroup.load(fis);
			//������Ϣ
			model.materials = MS3DMaterial.load(fis,manager,mangname);
			//FPS
			model.fps = fis.readFloat();
			//��ǰʱ��
			model.current_time = fis.readFloat();
			//�ؼ�֡��
			model.frame_count = fis.readInt();
			//��ʱ��
			model.total_time = model.frame_count / model.fps;
			//�ؽ�
			model.joints = MS3DJoint.load(fis);
			//��ʼ������
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
	 * ��ʼ��
	 */
	private void initBuffer() {
		//���ؽڸ��µ�0.0f
		this.updateJoint(0.0f,null);
		//���¶�������
		this.updateVectexs();
		//���ʡ��������껺��
		//������
		int count = this.groups.length;
		//ÿ�������θ���
		int trian_size = 0;
		//��ʱ����Ϣ
		MS3DGroup group = null;
		//��ʱ��������Ϣ
		MS3DTriangle triangle = null;
		//�������껺��
		this.texCoordingBuffer = new FloatBuffer[count];
		//�������껺��
		this.vertexCoordingBuffer = new FloatBuffer[count];
		//���������
		int[] indexs = null;
		//�������
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
				//����
				for(int k=0; k<3; k++){
					//���ò�����ͼ����
					buffer = this.texCoordingBuffer[i];
					buffer.put(triangle.getS().getVector3fArray()[k]);
					buffer.put(triangle.getT().getVector3fArray()[k]);
					//��ʼ�����㻺��
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
