package dev.mouse.util;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 材质贴图管理
 * @author DEVILIVED
 *
 */
public class TextureManager {
	
	private final String TEXTURE_FILE_PATH = "res/";			//材质文件目录
	
	//private GL10 gl;

	private Map<String, Integer> textures = new LinkedHashMap<String, Integer>();
	
	public TextureManager() {
		
	}
	
	public final void addTexture(String name, String file_name,String mangname) {
		if(!contains(name)) {
			/*Bitmap bitmap = null;
			try {
				if(mangname!=null){
					file_name=mangname+"/"+file_name;
				}
				bitmap = BitmapReader.load(TEXTURE_FILE_PATH.concat(file_name));
			} catch(Exception e) {
				e.printStackTrace();
			}
			this.addTexture(name, bitmap);*/
		}
	}
	
	/*public final void addTexture(String name, Bitmap bitmap) {
		if(bitmap != null) {
			if(!contains(name)) {
				int texture = this.bind(bitmap);
				if(texture != 0)
					this.textures.put(name, texture);
			}
		}
	}*/
	
	/*private final int bind(Bitmap bitmap) {
		int[] textureIds = new int[1];
		GLES20.glGenTextures(1,textureIds,0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);
		bitmap.recycle(); 	
		return textureIds[0];
		gl.glEnable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameterf(
				GL10.GL_TEXTURE_2D, 
				GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_NEAREST);
        gl.glTexParameterf(
        		GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_LINEAR);
        gl.glTexParameterf(
        		GL10.GL_TEXTURE_2D, 
        		GL10.GL_TEXTURE_WRAP_S,
                GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(
        		GL10.GL_TEXTURE_2D, 
        		GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_CLAMP_TO_EDGE);
        gl.glTexEnvf(
        		GL10.GL_TEXTURE_ENV, 
        		GL10.GL_TEXTURE_ENV_MODE,
                GL10.GL_REPLACE);
	    GLUtils.texImage2D(
	        		GL10.GL_TEXTURE_2D, 0, 
	        		bitmap, 0);
	    gl.glDisable(GL10.GL_BLEND);
	    gl.glDisable(GL10.GL_TEXTURE_2D);
	    bitmap.recycle();
		
	}*/
	
	
	
	
	public final boolean contains(String name) {
		return this.textures.keySet().contains(name);
	}
	
	public final int getTexture(String name) {
		Integer tex = this.textures.get(name); 
		return tex != null ? tex.intValue() : 0;
	}
	
	public final void fillTexture(String name) {
		int texture = this.getTexture(name);
		if(texture != 0) {
			
			//绑定纹理id
		   // GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
			/*//使用材质
			gl.glEnable(GL10.GL_TEXTURE_2D);
			//纹理
	        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			//使用参数,不然有接缝
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);*/
		}
	}
	
	/**
	 * 释放单个材质
	 * @param name 材质名
	 */
	public final void dispose(String name) {
		if(this.contains(name)) {
			//GLES20.glDeleteTextures(1, new int[]{this.textures.get(name)}, 0);
			System.gc();
		}
	}
	
	
	/**
	 * 释放所有材质
	 */
	public final void dispose() {
		Set<Entry<String, Integer>> set = this.textures.entrySet();
		int size = set.size();
		int[] texs = new int[size];
		Iterator<Entry<String, Integer>> it = set.iterator();
		int i=0;
		while(i++ < size) {
			texs[i] = it.next().getValue();
		}
		this.textures.clear();
		//GLES20.glDeleteTextures(size, texs, 0);
		System.gc();
		
	}

	
	
}
