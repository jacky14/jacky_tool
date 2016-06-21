package com.jacky14.metor;

import java.util.ArrayList;
import java.util.List;

/*
 * ����3dmax����ű�������ṹ���������
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
 * ����.skc�ļ��в�����Ϣ
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
