package com.jacky14.pl.xml;

public class Frame {

	
	
	

	/**
	 * @param key
	 */
	public Frame(String key) {
		this.key = key;
	}

	/**
	 * ����
	 * <key>1003_role/0000</key>
	 */
	public String key;
	/**
	 * ����
	 *  <key>frame</key>
	 *	<string>{{830,366},{102,162}}</string>
	 */
	public int frame[][];
	
	/**
	 * ����
	 *  <key>offset</key>
	 *	<string>{-10,-38}</string>
	 */
	public float offset[];
	
	/**
	 * ����
	 *  <key>rotated</key>
	 *	<true/>
	 */
	public boolean rotated;
	
	/**
	 * ����
	 *  <key>sourceColorRect</key>
	 *	<string>{{419,277},{102,162}}</string>
	 */
	public int sourceColorRect[][];
	
	/**
	 * ����
	 *  <key>sourceSize</key>
	 *	<string>{960,640}</string>
	 */
	public int sourceSize[];
	
}
