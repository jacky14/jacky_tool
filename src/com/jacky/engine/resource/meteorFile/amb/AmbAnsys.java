package com.jacky.engine.resource.meteorFile.amb;

import java.io.IOException;
import java.io.RandomAccessFile;
public class AmbAnsys {
	
	public static RandomAccessFile  rf;
	
	public static void main(String[] args) throws IOException {
		AmbBone a =AmbAnsys.ansysAmb(".\\pmodel\\p0.amb"); 
		
		System.out.println("");
	}
	
	/**
	 * 解析amb骨骼动画文件
	 * @param ambFilePath amb文件路径
	 * @throws IOException 
	 */
	public static AmbBone ansysAmb(String ambFilePath) throws IOException{
		AmbBone ab=new AmbBone();
		rf=new RandomAccessFile(ambFilePath, "rw");
		
		for (int i = 0; (i <= 4 && i<rf.length()); i++) {
			ab.authorNmae+=(char)rf.readByte();
		}
		ab.bones=readInt();
		ab.dummy=readInt();
		ab.frame_count=readInt();
		ab.unknown=readInt();
		
		
		ab.frames=new Frame[ab.frame_count];
		for (int i = 0; i<ab.frame_count; i++) {
			ab.frames[i]=new Frame();
			ab.frames[i].flag=readInt();
			rf.skipBytes(4);//关键帧索引值 可用frames下标代替
			ab.frames[i].vector3df=new float[3];
			ab.frames[i].vector3df[0]=readFloat();
			ab.frames[i].vector3df[1]=readFloat();
			ab.frames[i].vector3df[2]=readFloat();
			
			ab.frames[i].quaternion=new float[ab.bones][4];
			for (int j = 0; j<ab.bones; ++j) {
				ab.frames[i].quaternion[j][0]=readFloat();
				ab.frames[i].quaternion[j][1]=readFloat();
				ab.frames[i].quaternion[j][2]=readFloat();
				ab.frames[i].quaternion[j][3]=readFloat();
			}
			ab.frames[i].dummys=new Dummy[ab.dummy];
			for (int k = 0; k<ab.dummy; ++k) {
				ab.frames[i].dummys[k]=new Dummy();
				
				for (int x = 0; (x <= 4 && x<rf.length()); x++) {
					ab.frames[i].dummys[k].name+=(char)rf.readByte();
				}
				ab.frames[i].dummys[k].vector3df=new float[3];
				ab.frames[i].dummys[k].vector3df[0]=readFloat();
				ab.frames[i].dummys[k].vector3df[1]=readFloat();
				ab.frames[i].dummys[k].vector3df[2]=readFloat();
				ab.frames[i].dummys[k].quaternion=new float[4];
				ab.frames[i].dummys[k].quaternion[0]=readFloat();
				ab.frames[i].dummys[k].quaternion[1]=readFloat();
				ab.frames[i].dummys[k].quaternion[2]=readFloat();
				ab.frames[i].dummys[k].quaternion[3]=readFloat();
			}
			
		}
		rf.close();
		return ab;
	}
	
	/**
	 * 这个文件数据按低位优先顺序储存   重右到左 代表由高位到低位  buff[3]-->buff[0]
	 * (标准api RandomAccessFile.readInt() 中buff[0]-->buff[3]由高位到低位)
	 * @return
	 * @throws IOException
	 */
	public static int readInt() throws IOException {
		byte[] buff = new byte[4];
		rf.read(buff);
		return (buff[3] << 24)+ ((buff[2] << 24) >>> 8) + ((buff[1] << 24) >>> 16)+ ((buff[0] << 24) >>> 24);
	}
	public static  final float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}
	
	
	
	
	
}