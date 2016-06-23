package dev.mouse.ms3d;

import java.io.IOException;

import dev.mouse.io.LittleEndianInputStream;
import dev.mouse.util.TextureManager;


/**
 * ����
 * @author DEVILIVED
 *
 */
public class MS3DMaterial {
	
	private String name;		//��������
	
	private float[] ambient_color;	//������
	
	private float[] diffuse_color;	//�����
	
	private float[] specular_color;	//�߹�
	
	private float[] emissive_color;	//�Է���
	
	private float shininess;		//0-128
	
	private float transparency;		//͸���� 0-1
	
	private byte mode;				//δʹ��
	
	private String textureName;		//�����ļ�����
	
	private String alphamap;		//͸����������
	
	private MS3DMaterial() {}

	public final static MS3DMaterial[] load(LittleEndianInputStream is, TextureManager manager, String mangname) throws IOException {
		int count = is.readUnsignedShort();
		MS3DMaterial[] mals = new MS3DMaterial[count];
		MS3DMaterial mal = null;
		for(int i=0; i<count; i++) {
			mal = new MS3DMaterial();
			mal.name = is.readString(32);
			
			mal.ambient_color = new float[4];
			for(int j=0; j<4; j++) {
				mal.ambient_color[j] = is.readFloat();
			}
			mal.diffuse_color = new float[4];
			for(int j=0; j<4; j++) {
				mal.diffuse_color[j] = is.readFloat();
			}
			mal.specular_color = new float[4];
			for(int j=0; j<4; j++) {
				mal.specular_color[j] = is.readFloat();
			}
			
			mal.emissive_color = new float[4];
			for(int j=0; j<4; j++) {
				mal.emissive_color[j] = is.readFloat();
			}
			
			mal.shininess = is.readFloat();
			mal.transparency = is.readFloat();
			mal.mode = is.readByte();
			mal.textureName = format(is.readString(128));
			mal.alphamap = is.readString(128);
			mals[i] = mal;
			//�󶨲���
			manager.addTexture(mal.name, mal.textureName,mangname);
		}
		return mals;
	}
	
	/**
	 * ֻȡ�ļ���
	 * @param path
	 * @return
	 */
	private final static String format(String path) {
		int offset = path.lastIndexOf("\\");
		if(offset > -1) {
			return path.substring(offset + 1);
		}
		return path;
	}
	
	public final float[] getDiffuse_color() {
		return diffuse_color;
	}

	public final void setDiffuse_color(float[] diffuseColor) {
		diffuse_color = diffuseColor;
	}

	public final String getName() {
		return name;
	}

	public final float[] getAmbient_color() {
		return ambient_color;
	}

	public final float[] getSpecular_color() {
		return specular_color;
	}

	public final float[] getEmissive_color() {
		return emissive_color;
	}

	public final float getShininess() {
		return shininess;
	}

	public final float getTransparency() {
		return transparency;
	}

	public final byte getMode() {
		return mode;
	}

	public final String getTextureName() {
		return textureName;
	}

	public final String getAlphamap() {
		return alphamap;
	}
	
	
	
}
