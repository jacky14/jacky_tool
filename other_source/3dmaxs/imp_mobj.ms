in_file = openFile "./ms3dtest/a.txt" 

verts = #();
faces = #();
uvs = #();
uvsfaceidx = #();


v_bones = #();

hbcbones = #();

i = 1;




a_tmpmat = #();
a_matrixs = #();


iidx = 0;


while not eof in_file do -- read until reach end of file
(
	line =   readline  in_file 
	subline = filterString  line " "  -- 用空格分解为文本数组


	if(subline[1] == "vt")then(
		append uvs [(subline[2]as float) ,(subline[3]as float) ,0 ]
	)else if (subline[1] == "v") then
	(
		append verts [ (subline[4]as float) ,(subline[3]as float), (subline[2]as float)]--读入顶点 坐标
		append v_bones (subline[5]as integer)
	)
	else if(subline[1] == "f") then
	(
		
		append uvsfaceidx  [(subline[5]as integer) ,(subline[6]as integer), (subline[7]as integer)]
		
		append faces [(subline[2]as integer) ,(subline[3]as integer), (subline[4]as integer)]
	)else if(subline[1] == "b") then
	(
		hbcbones[i] = point 	name:("b" + i as string )		size:0.1 	showLinks:true	--创建1个辅助点
		hbcbones[i].isHidden	=	false
		
		
		parentidx = subline[2] as integer
		
		
		--eulerangles
		if(parentidx==-1)  then
		(--如果没有父节点
			hbcbones[i].parent = null_point
			
		
			mm3 = matrix3 1
			mm3.row1= [ (subline[3] as float) ,(subline[4] as float), (subline[5] as float)]
			mm3.row2= [ (subline[6] as float) ,(subline[7] as float), (subline[8] as float)]
			mm3.row3= [ (subline[9] as float) ,(subline[10] as float), (subline[11] as float)]
			mm3.row4= [ (subline[12] as float) ,(subline[13] as float), (subline[14] as float)]
		 
			 hbcbones[i].transform  = mm3
			
			--hbcbones[i].rotation = eulerangles (subline[8] as float)  (subline[7] as float)   (subline[6] as float)
			--hbcbones[i].pos	= [ (subline[5] as float) ,(subline[4] as float), (subline[3] as float)];
			
		)else(--有父节点
			hbcbones[i].parent = hbcbones[parentidx]
			
		 --in coordsys parent(
				
		 	mm3 = matrix3 1
			mm3.row1= [ (subline[3] as float) ,(subline[4] as float), (subline[5] as float)]
			mm3.row2= [ (subline[6] as float) ,(subline[7] as float), (subline[8] as float)]
			mm3.row3= [ (subline[9] as float) ,(subline[10] as float), (subline[11] as float)]
			mm3.row4= [ (subline[12] as float) ,(subline[13] as float), (subline[14] as float)]
		 
			hbcbones[i].transform  = (mm3* hbcbones[parentidx].transform)
				
			--hbcbones[i].rotation =eulerangles (subline[8] as float)  (subline[7] as float)   (subline[6] as float)	
			--hbcbones[i].pos	= [ (subline[5] as float) ,(subline[4] as float), (subline[3] as float)];
			--)
		)
		
		i=i+1
	)else if(subline[1] == "a") then
	(
		  if(iidx == hbcbones.count)then
		  (
		  	iidx = 0;
		  	append a_matrixs a_tmpmat
		  	a_tmpmat = #()
		  )
		
		 	mm3 = matrix3 1
			mm3.row1= [ (subline[2] as float) ,(subline[3] as float), (subline[4] as float)]
			mm3.row2= [ (subline[5] as float) ,(subline[6] as float), (subline[7] as float)]
			mm3.row3= [ (subline[8] as float) ,(subline[9] as float), (subline[10] as float)]
			mm3.row4= [ (subline[11] as float) ,(subline[12] as float), (subline[13] as float)]
			append a_tmpmat mm3
			
			
			
		  iidx = iidx+1;
		
	)	

)
modtmp = mesh name:"modtmp" vertices: verts faces:faces  tverts:uvs    --materialIDs:lx_objs[i].face_mat 

buildTVFaces modtmp false
for  iii = 1 to faces.count do (
	setTVFace   modtmp iii  (uvsfaceidx[iii][1])  (uvsfaceidx[iii][2])  (uvsfaceidx[iii][3])
)


close in_file


skinMod = skin ()
skinMod.rigid_vertices = false		--禁用刚体顶点
skinMod.bone_Limit = 1
select	modtmp
max modify mode		
addmodifier modtmp skinMod		-- 蒙皮修改器 , 以下将使用 此修改器的方法

for v = 1 to (i-1) do
(
	if(n == (i-1)) then
				skinOps.addbone skinMod hbcbones[v] 	1	-- 向当前系统加入1个骨骼, 参数为 <skin> <boneNode> <is uptate =1 时即时刷新画面, =0 不刷新> 
	else
				skinOps.addbone skinMod hbcbones[v] 	0
)

modPanel.setCurrentObject skinMod  -- 不知道这一行有什莫用，不加上的话每次在第一次运行脚本时发生骨骼索引越界错误？？？？？？？？
 
for k = 1 to modtmp.numVerts do 
(
	skinOps.ReplaceVertexWeights  skinMod  k  (v_bones[k])   (1);
)

--max create mode 

animate on
(
	


	for jjj  =1 to a_matrixs.count do(

	  at time jjj  
		for nn=1 to hbcbones.count do 
		(
		 	hbcbones[nn].transform =  a_matrixs[jjj][nn]
		)
		
	)

	
)
