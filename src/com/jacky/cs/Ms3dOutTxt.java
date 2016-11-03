package com.jacky.cs;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.jacky14.pl.xml.PlistData;

import dev.mouse.lib.Matrix4f;
import dev.mouse.lib.Vector3f;
import dev.mouse.ms3d.MS3DModel;

public class Ms3dOutTxt {
	
	private MS3DModel ms3dmodel;
	public Ms3dOutTxt(MS3DModel m){
		this.ms3dmodel = m;
	}
	public static float cf =(float)(180/Math.PI);
	
	//public static float cf =1;
	public static String path = "C:\\Users\\Administrator\\Desktop\\ms3dtest\\a.txt";
	public void save() throws Exception{
		
		
		PlistData pd = new PlistData("C:\\Users\\Administrator\\Desktop\\akskin\\Plist.plist");
		
		PrintWriter pw = new PrintWriter(path);
		
		for(int i=0;i<ms3dmodel.getVertexs().length;i++){
			Vector3f v = ms3dmodel.getVertexs()[i].getVertex();
			pw.println("v " +  v.getZ() + " " + v.getY() + " " + v.getX() + " " + (ms3dmodel.getVertexs()[i].getBone()+1));
		}
		
		for(int i=0;i<ms3dmodel.getTriangles().length;i++){
			int[] indexs = ms3dmodel.getTriangles()[i].getIndexs();
			
			
			Vector3f s = ms3dmodel.getTriangles()[i].getS();
			Vector3f t = ms3dmodel.getTriangles()[i].getT();
			
			String tmpn=ms3dmodel.getMaterials()[ms3dmodel.getTriangles()[i].getGroupIndex()].getName();
			String key ="akskin/"+ tmpn.substring(0, tmpn.length()-3)   + "jpg";
			
			float [] uv1 = pd.transition(key, new float[]{s.getX(),(t.getX())});
			float [] uv2 = pd.transition(key, new float[]{s.getY(),(t.getY())});
			float [] uv3 = pd.transition(key, new float[]{s.getZ(),(t.getZ())});
			if(uv1 == null){
				uv1 =  new float[]{s.getX(),(t.getX())};
			}
			if(uv2 == null){
				uv2 = new float[]{s.getY(),(t.getY())};
			}
			if(uv3==null){
				uv3 = new float[]{s.getZ(),(t.getZ())};
			}
			
			
			pw.println("vt "+uv1[0] +" "+(1-uv1[1]));
			pw.println("vt "+uv2[0] +" "+(1-uv2[1]));
			pw.println("vt "+uv3[0] +" "+(1-uv3[1]));
			//+ (i*3+1) +"/" + (i*3+2) +"/" + (i*3+3)
			pw.println("f " +(indexs[0]+1)   +" " + (indexs[1]+1) + " " + (indexs[2]+1)    +  " " + (i*3+1) + " " +(i*3+2)+" " + (i*3+3) );
			//pw.println("f " + (indexs[0]+1) + " " + (indexs[1]+1) + " " + (indexs[2]+1));
		}
		Map<String, Integer> name_idx = new HashMap<String, Integer>();
		
		
		for(int i=0;i<ms3dmodel.getJoints().length;i++){
			name_idx.put(ms3dmodel.getJoints()[i].getName(), i+1);
			Integer idx = name_idx.get(ms3dmodel.getJoints()[i].getParentName());
			
			if(idx==null){
				idx = -1;
			}
			Vector3f p = ms3dmodel.getJoints()[i].getPosition();
			
			Vector3f rol = ms3dmodel.getJoints()[i].getRotate();
			
			
			Matrix4f juzhen = new Matrix4f();
			juzhen.loadIdentity();
			//设置旋转
			juzhen.setRotationRadians(rol);//[-1.5707964, -1.570795, 0.0]
			//设置位移
			juzhen.setTranslation(p);//[0.233849, 38.19215, 2.25168]
			
			String juzhenstr  = 
			juzhen.getMatrixArray()[0][0] + " " + juzhen.getMatrixArray()[1][0] +" " + juzhen.getMatrixArray()[2][0]+" "
			+juzhen.getMatrixArray()[0][1] + " " + juzhen.getMatrixArray()[1][1] +" " + juzhen.getMatrixArray()[2][1]+" "
			+juzhen.getMatrixArray()[0][2] + " " + juzhen.getMatrixArray()[1][2] +" " + juzhen.getMatrixArray()[2][2]+" "
			+juzhen.getMatrixArray()[0][3] + " " + juzhen.getMatrixArray()[1][3] +" " + juzhen.getMatrixArray()[2][3];
			
			pw.println("b " + idx + " " + juzhenstr);
			
			
			
		}
	
		for(int i=0;i<ms3dmodel.getJoints()[0].getPositions().length;i++){
			for(int j=0;j<ms3dmodel.getJoints().length;j++){
				
				ms3dmodel.getJoints()[j].update(ms3dmodel.getJoints()[j].getPositions()[i].time, false);
				Matrix4f juzhen = ms3dmodel.getJoints()[j].matrix;
				String juzhenstr  = 
						 juzhen.getMatrixArray()[0][0] + " " + juzhen.getMatrixArray()[1][0] +" " + juzhen.getMatrixArray()[2][0]+" "
						+juzhen.getMatrixArray()[0][1] + " " + juzhen.getMatrixArray()[1][1] +" " + juzhen.getMatrixArray()[2][1]+" "
						+juzhen.getMatrixArray()[0][2] + " " + juzhen.getMatrixArray()[1][2] +" " + juzhen.getMatrixArray()[2][2]+" "
						+juzhen.getMatrixArray()[0][3] + " " + juzhen.getMatrixArray()[1][3] +" " + juzhen.getMatrixArray()[2][3];
				//String jstraa
				pw.println("a "  + juzhenstr );
				//System.out.println("父骨骼的索引值是：" + idx);
			}
		}
		
		
		
		pw.close();
		
	}
	
	

}
