package com.jacky.engine.resource.objutile;

import com.jacky.engine.resource.BinaryFile;

import java.io.*;
import java.nio.FloatBuffer;
import java.util.*;

enum OBJ_TYPE{
	INDEXMODE,//顶点索引模式
	DATAMODE//普通模式

};
public class ObjFileLoder {

	public float[] vertex;
	public float[] normal;
	public float[] texture;
	public int [] Index;



	private ArrayList<Float> vertexlist=new ArrayList<Float>();// 变长集合加载顶点数据
	private ArrayList<Float> normallist=new ArrayList<Float>();// 变长集合加载法线数据
	private ArrayList<Float> texturelist=new ArrayList<Float>();// 变长集合加载纹理数据




	public ArrayList<Float> ivertexlist=new ArrayList<Float>();// 变长集合加载顶点数据 经过索引处理
	public ArrayList<Float> inormallist=new ArrayList<Float>();// 变长集合加载法线数据 经过索引处理
	public ArrayList<Float> itexturelist=new ArrayList<Float>();// 变长集合加载纹理数据 经过索引处理


	public ArrayList<Integer> Indexlist=new ArrayList<Integer>();// 变长集合加载顶点索引


	public static OBJ_TYPE MODE = OBJ_TYPE.INDEXMODE;




	
	//public ArrayList<ModelGroups> modelGroups=new ArrayList<ModelGroups>();// 模型分组信息
	public void loadStrforfname(String string) throws FileNotFoundException{
		loadStrforInput(new FileInputStream(string));
	}
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ObjFileLoder ol = new ObjFileLoder();
		long start = System.currentTimeMillis();
		ol.loadStrforfname("C:\\Users\\Administrator\\Desktop\\tm.obj");
		System.out.println(nummm);
		System.out.println(nummm1);

		System.out.println("text load time is "+ (System.currentTimeMillis() - start));

		/*long start1 = System.currentTimeMillis();
		BinaryFile  bf = new BinaryFile(new FileInputStream("C:\\Users\\Administrator\\Desktop\\tm"));
		float [] vex;
		float [] uv;
		int length = bf.readInt();
		vex = new float[length];
		for(int i=0;i<length;i++){
			vex[i] = bf.readFloat();
		}
		length = bf.readInt();
		uv= new float[length];
		for(int i=0;i<length;i++){
			uv[i] = bf.readFloat();
		}
		System.out.println("bin load time is "+ (System.currentTimeMillis() - start1));*/
		/*OutputStream is = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\tm");
		is.write(BinaryFile.getByte(ol.vertex.length));
		for(int i=0;i<ol.vertex.length;i++){
			is.write(BinaryFile.getByte(Float.floatToIntBits(ol.vertex[i])));
		}
		is.write(BinaryFile.getByte(ol.texture.length));
		for(int i=0;i<ol.texture.length;i++){
			is.write(BinaryFile.getByte(Float.floatToIntBits(ol.texture[i])));
		}
		is.close();*/
		/*OutputStream is = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\tm");
		int a = 32568;
		is.write(BinaryFile.getByte(a));
		is.close();
		BinaryFile  bf = new BinaryFile(new FileInputStream("C:\\Users\\Administrator\\Desktop\\tm"));
		int length = bf.readInt();*/
		System.out.println(ol);
	}
	public  void loadStrforInput(InputStream in){
		if (in == null)
			return;
		Scanner sc = new Scanner(new InputStreamReader(in));
		String[] tempsa=null;
		String line =null;
		int plane=0;//当前模型组面数
		int count=0;
		
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (line == null || line.trim().length() < 1)
				continue;
			tempsa = line.split("[ ]+");
			if ("v".equals(tempsa[0])) {// 顶点数据
				vertexlist.add(Float.parseFloat(tempsa[1]));
				vertexlist.add(Float.parseFloat(tempsa[2]));
				vertexlist.add(Float.parseFloat(tempsa[3]));
				// 未进行数据正确性验证如果抛数组越界说明obj文件存在异常的顶点数据
			} else if ("vn".equals(tempsa[0])) {// 法线数据
				normallist.add(Float.parseFloat(tempsa[1]));
				normallist.add(Float.parseFloat(tempsa[2]));
				normallist.add(Float.parseFloat(tempsa[3]));
			} else if ("vt".equals(tempsa[0])) {// 纹理数据
				texturelist.add(Float.parseFloat(tempsa[1]));
				texturelist.add(1-Float.parseFloat(tempsa[2]));
			} else if ("f".equals(tempsa[0])) {
				// 顶点索引，法线索引，纹理坐标索引
				if(MODE ==OBJ_TYPE.INDEXMODE){
					indexdataI(tempsa[1].split("/"));
					indexdataI(tempsa[2].split("/"));
					indexdataI(tempsa[3].split("/"));
				}else if(MODE ==OBJ_TYPE.DATAMODE){
					indexdata(tempsa[1].split("/"));
					indexdata(tempsa[2].split("/"));
					indexdata(tempsa[3].split("/"));
				}


				//indexdata1(tempsa[1].split("/"));
				//indexdata1(tempsa[2].split("/"));
				//indexdata1(tempsa[3].split("/"));

				plane++;//面数增加1
				count++;
			}else if("g".equals(tempsa[0])){
				/*ModelGroups mg=new ModelGroups(tempsa[1],plane);
				if(modelGroups.size()>0){
					ModelGroups last=modelGroups.get(modelGroups.size()-1);
					last.lengh=count;
					count=0;
				}
				modelGroups.add(mg);*/
			}
		}
		/*if(modelGroups.size()>0){//完成加载后最后一个模型分组设置顶点长度
			ModelGroups last=modelGroups.get(modelGroups.size()-1);
			last.lengh=count;
			count=0;
		}*/
		
		vertexlist.clear();normallist.clear();texturelist.clear();
		sc.close();
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


		//vertex=listToBuff(ivertexlist);
		//normal=listToBuff(inormallist);
		//texture=listToBuff(itexturelist);
		//Index = listToBuffI(Indexlist);
		//textureIndex = listToBuffI(textureIndexlist);
	}
	public static float[]  listToBuff(List<Float> list){
		float[] floa = new float[list.size()];
		for (int i = 0; i < list.size(); i++){
			floa[i]=list.get(i);
		}
		return floa;
	}
	public static int[] listToBuffI(List<Integer> list){
		int[] floa = new int[list.size()];
		for (int i = 0; i < list.size(); i++){
			floa[i]=list.get(i);
		}
		return floa;

	}
	private  HashMap<Integer,Integer> index_map = new HashMap<Integer,Integer>();
	private void indexdataI(String[] indexs){
		if(indexs.length==0){
			return;
		}else if(indexs.length==1){
			int vindex = Integer.parseInt(indexs[0]) - 1;
			ivertexlist.add(vertexlist.get(vindex * 3));
			ivertexlist.add(vertexlist.get(vindex * 3 + 1));
			ivertexlist.add(vertexlist.get(vindex * 3 + 2));
		}else if(indexs.length==3 || indexs.length==2){
			int vindex = Integer.parseInt(indexs[0]) - 1;
			int tindex = Integer.parseInt(indexs[1]) - 1;

			int key = vindex*10000 + tindex;
			Integer val = index_map.get(key);
			if(val == null){
				int ix = ivertexlist.size() / 3;
				Indexlist.add(ix);
				index_map.put(key,ix);
				ivertexlist.add(vertexlist.get(vindex * 3));
				ivertexlist.add(vertexlist.get(vindex * 3 + 1));
				ivertexlist.add(vertexlist.get(vindex * 3 + 2));
				itexturelist.add((texturelist.get(tindex * 2)));
				itexturelist.add((texturelist.get(tindex * 2 + 1)));

			}else{
				Indexlist.add(val);
			}




			/*int nindex = Integer.parseInt(indexs[2]) - 1;
			inormallist.add(normallist.get(nindex * 3));
			inormallist.add(normallist.get(nindex * 3 + 1));
			inormallist.add(normallist.get(nindex * 3 + 2));*/
		}

	}
	private  void indexdata(String[] indexs){
		if(indexs.length==0){
			return;
		}else if(indexs.length==1){
			int vindex = Integer.parseInt(indexs[0]) - 1;
			ivertexlist.add(vertexlist.get(vindex * 3));
			ivertexlist.add(vertexlist.get(vindex * 3 + 1));
			ivertexlist.add(vertexlist.get(vindex * 3 + 2));
		}else if(indexs.length==2){
			int vindex = Integer.parseInt(indexs[0]) - 1;
			ivertexlist.add(vertexlist.get(vindex * 3));
			ivertexlist.add(vertexlist.get(vindex * 3 + 1));
			ivertexlist.add(vertexlist.get(vindex * 3 + 2));
			
			int tindex = Integer.parseInt(indexs[1]) - 1;
			itexturelist.add((texturelist.get(tindex * 2)));
			itexturelist.add((texturelist.get(tindex * 2 + 1)));
		}else if(indexs.length==3){
			int vindex = Integer.parseInt(indexs[0]) - 1;
			ivertexlist.add(vertexlist.get(vindex * 3));
			ivertexlist.add(vertexlist.get(vindex * 3 + 1));
			ivertexlist.add(vertexlist.get(vindex * 3 + 2));
			
			int tindex = Integer.parseInt(indexs[1]) - 1;
			itexturelist.add((texturelist.get(tindex * 2)));
			itexturelist.add((texturelist.get(tindex * 2 + 1)));

			int k = vindex*100000 +tindex;
			if(keys.contains(k)){
				nummm++;
			}else{
				keys.add(k );
			}
			nummm1++;


			int nindex = Integer.parseInt(indexs[2]) - 1;
			inormallist.add(normallist.get(nindex * 3));
			inormallist.add(normallist.get(nindex * 3 + 1));
			inormallist.add(normallist.get(nindex * 3 + 2));
		}
		/*for (int i = 0; i < indexs.length; i++) {
			int index = Integer.parseInt(indexs[i]) - 1;
			if (i == 0) {
				ivertexlist.add(vertexlist.get(index * 3));
				ivertexlist.add(vertexlist.get(index * 3 + 1));
				ivertexlist.add(vertexlist.get(index * 3 + 2));
			} else if (i == 1) {
				itexturelist.add((texturelist.get(index * 2)));
				itexturelist.add((texturelist.get(index * 2 + 1)));
				
			} else if (i == 2) {
				inormallist.add(normallist.get(index * 3));
				inormallist.add(normallist.get(index * 3 + 1));
				inormallist.add(normallist.get(index * 3 + 2));
			}

		}*/
	}
	public static int nummm = 0;
	public static int nummm1 = 0;
	public  static HashSet<Integer> keys=new HashSet<Integer>();

}
