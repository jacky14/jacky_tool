package com.jacky14.metor;


import com.jacky14.ObjFileUtile;
import com.jacky14.timehelp.MyClock;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class LXobj {

	public Vertex[] vertexs;
	public LXmat[] lxmats;
	public Triangle[] triangles;
	public int num_mat;
	public int num_ver;
	public int num_tri;
	
	public void genObj(String path) throws IOException {
			List<float[]>	 ver = new ArrayList<float[]>();
			List<float[]>	 uv = new ArrayList<float[]>();
			
			for(int i=0;i<vertexs.length;i++){
					ver.add(vertexs[i].verts);
					uv.add(vertexs[i].uvs);
			}
						
			List<int[]>	 idx = new ArrayList<int[]>();
			
			for(int i=0;i<triangles.length;i++){
					idx.add(triangles[i].v_index);
			}
			
			ObjFileUtile bfu = new ObjFileUtile(ver,uv,idx);
			
			bfu.save(path);
		
	}
	
	public static void main(String[] args) throws Exception {
		LXobj l=new LXobj();

		MyClock mc =new MyClock();
		l.loadObj("C:\\Users\\Administrator\\Desktop\\p0.skc");
		l.genObj("C:\\Users\\Administrator\\Desktop\\tmp\\");
		mc.showtime();
		System.out.print("test");

	}
	public void loadObj(String string) throws Exception {
		Scanner sc=new Scanner(new File(string));
		int index_mat=-1;
		int index_ver=-1;
		int index_tri=-1;
		while(sc.hasNext()){
			String[] lines=sc.nextLine().trim().split("\\s+");
			if("Materials:".equals(lines[0])){
				num_mat=Integer.parseInt(lines[1]);
				lxmats=new LXmat[num_mat];
			}else if ("Material".equals(lines[0])) {
				index_mat++;
			}else if ("Texture".equals(lines[0])) {
				lxmats[index_mat]=new LXmat();
				lxmats[index_mat].texture=lines[1];
			}else if ("Vertices:".equals(lines[0])) {
				num_ver=Integer.parseInt(lines[1]);
				vertexs=new Vertex[num_ver];
			}else if ("v".equals(lines[0])) {
				index_ver++;
				vertexs[index_ver]=new Vertex();
				vertexs[index_ver].verts=new float[]{Float.parseFloat(lines[1]),Float.parseFloat(lines[2]),Float.parseFloat(lines[3])};
				vertexs[index_ver].uvs=new float[]{Float.parseFloat(lines[5]),1-Float.parseFloat(lines[6])};
				int bone=Integer.parseInt(lines[8]);
				vertexs[index_ver].bone_index=new short[bone];
				vertexs[index_ver].bone_weight=new float[bone];
				for(int i=0;i<bone;i++){
					vertexs[index_ver].bone_index[i]=Short.parseShort(lines[9+i*2]);
					vertexs[index_ver].bone_weight[i]=Float.parseFloat(lines[10+i*2]);
				}
			}else if ("Triangles:".equals(lines[0])) {
				num_tri=Integer.parseInt(lines[1]);
				triangles=new Triangle[num_tri];
			}else if ("f".equals(lines[0])){
				index_tri++;
				triangles[index_tri]=new Triangle();
				triangles[index_tri].mat_index=Integer.parseInt(lines[2]);
				triangles[index_tri].v_index=new int[]{Integer.parseInt(lines[3]) ,Integer.parseInt(lines[4])  ,Integer.parseInt(lines[5]) };

				int oldmatid0 = vertexs[triangles[index_tri].v_index[0]].matid,oldmatid1 = vertexs[triangles[index_tri].v_index[1]].matid,oldmatid2 = vertexs[triangles[index_tri].v_index[2]].matid;

				if((oldmatid0!=-1&& oldmatid0 != triangles[index_tri].mat_index)||(oldmatid1!=-1&& oldmatid1 != triangles[index_tri].mat_index)||  (oldmatid2!=-1&& oldmatid2 != triangles[index_tri].mat_index)){
					System.out.println("这里似乎出现了问题，一个顶点对应2个不同的纹理？？？？？？");
				}
				//为每一个顶点记录对应材质信息
				vertexs[triangles[index_tri].v_index[0]].matid = triangles[index_tri].mat_index;
				vertexs[triangles[index_tri].v_index[1]].matid = triangles[index_tri].mat_index;
				vertexs[triangles[index_tri].v_index[2]].matid = triangles[index_tri].mat_index;

			}
		}
	}

	public float[] transformationf(List<Float> F){
		float[] f=new float[F.size()];
		for(int i=0;i<f.length;i++){
			f[i]=F.get(i);
		}
		return f;
	}
	public int[] transformationi(List<Integer> Int){
		int[] intarray=new int[Int.size()];
		for(int i=0;i<intarray.length;i++){
			intarray[i]=Int.get(i);
		}
		return intarray;
	}

}
