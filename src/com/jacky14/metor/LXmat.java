package com.jacky14.metor;

import java.util.ArrayList;
import java.util.List;

/*
 * 根据3dmax这个脚本中这个结构创建这个类
 * struct LXMAT
(
    Id,     tex,    col,    amb,    dif,    spec,   emis,   opac,   opt,    twoside
 --   Texture   nm011b01.jpg
 --   ColorKey  0 0 0 0
 --   Ambient   1.000 1.000 1.000
 --   Diffuse   1.000 1.000 1.000
 --   Specular  1.000 1.000 1.000
 --   Emissive  0.000 0.000 0.000
 --   Opacity   1.000
 --   Option    NONE
 --   TwoSide   FALSE
)	-- end struct LXMAT*/


/**
 * 解析.skc文件中材质信息
 * @author Administrator
 */
public class LXmat {
	public	String	texture;    
	public	byte[]	colorkey;    
	public	float[]	ambient;    
	public	float[]	diffuse;    
	public	float[]	specular;   
	public	float[]	emissive;   
	public	float	opacity;   
	public	String	option;    
	public	boolean	twoside;
	public List<Triangle> ts=new ArrayList<Triangle>();
}
