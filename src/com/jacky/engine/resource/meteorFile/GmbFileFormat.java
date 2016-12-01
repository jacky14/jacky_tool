package com.jacky.engine.resource.meteorFile;

import java.util.ArrayList;
import java.util.List;

import com.jacky.engine.resource.BinaryFile;

public class GmbFileFormat {


	String magic_version;
	int textureCount; // 纹理个数
	public List<String> textureList = new ArrayList<String>();
	int optionCount;
	List<GHeaderOption> optionList =new ArrayList<GHeaderOption>();
	public List<GMBObj> ObjectList = new ArrayList<GMBObj>();
	
	
	public void readDataFromFilename(BinaryFile bf){
		magic_version  = bf.readString(10);
		
		textureCount = bf.readInt();
		
		
		for (int i = 0; i < textureCount; ++i)
		{
			textureList.add(bf.readString(bf.readInt()));
		}
		optionCount = bf.readInt();
		
		bf.skip(4);
		
		for (int i = 0; i < optionCount; ++i)
		{
			GHeaderOption go = new GHeaderOption();
			
			go.option1 = bf.readString(bf.readInt());
			
			bf.skip(1);
			
			go.option2 = bf.readString(bf.readInt());
			
			bf.skip(4);
			
			go.ObjIndex = bf.readInt();
			
			optionList.add(go);
		}
		bf.skip(4);
		bf.skip(4);
		bf.skip(4);
		
		while(!bf.isEnd()){
			
			GMBObj obj =new GMBObj();
			obj.ObjName = bf.readString(bf.readInt());
			obj.vertexCount = bf.readInt();
			obj.indicesCount = bf.readInt();
			
			obj.Positon = new float [obj.vertexCount * 3];
			obj.Normal = new float [obj.vertexCount * 3];
			obj.Color = new int [obj.vertexCount];
			obj.UV = new float [obj.vertexCount * 2];
			
			for(int i=0;i<obj.vertexCount;i++ ){
				int index = i * 3 ;
				obj.Positon[index] = bf.readFloat();
				obj.Positon[index + 1] = bf.readFloat();
				obj.Positon[index + 2] = bf.readFloat();
				
				obj.Normal[index] = bf.readFloat();
				obj.Normal[index + 1] = bf.readFloat();
				obj.Normal[index + 2] = bf.readFloat();
				
				obj.Color[i] = bf.readInt();//--------------------
				
				int uvindex = i * 2;
				
				obj.UV[uvindex]  = bf.readFloat();
				obj.UV[uvindex+1]  = bf.readFloat();//导出到obj

			}
			
			
			obj.unknow = new int [obj.indicesCount];
			obj.Triangle = new int [obj.indicesCount * 3];
			obj.FaceNormal = new float[obj.indicesCount * 3];
			
			for(int i=0;i<obj.indicesCount;i++){
				int index = i * 3 ;
				
				obj.unknow[i] = bf.readInt();//-----------------------
				
				
				obj.Triangle[index] = bf.readInt();
				obj.Triangle[index+1] = bf.readInt();
				obj.Triangle[index+2] = bf.readInt();
				
				
				obj.FaceNormal[index] = bf.readFloat();
				obj.FaceNormal[index+1] = bf.readFloat();
				obj.FaceNormal[index+2] = bf.readFloat();
			}
			
			
			
			ObjectList.add(obj);
		}
		
		
		
	}

}
