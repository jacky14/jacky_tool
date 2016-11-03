package com.jacky14.pl.xml;

public class Frame {

	
	
	

	/**
	 * @param key
	 */
	public Frame(String key) {
		this.key = key;
	}

	/**
	 * 例：
	 * <key>1003_role/0000</key>
	 */
	public String key;
	/**
	 * 例：
	 *  <key>frame</key>
	 *	<string>{{830,366},{102,162}}</string>
	 */
	public int frame[][];
	
	/**
	 * 例：
	 *  <key>offset</key>
	 *	<string>{-10,-38}</string>
	 */
	public float offset[];
	
	/**
	 * 例：
	 *  <key>rotated</key>
	 *	<true/>
	 */
	public boolean rotated;
	
	/**
	 * 例：
	 *  <key>sourceColorRect</key>
	 *	<string>{{419,277},{102,162}}</string>
	 */
	public int sourceColorRect[][];
	
	/**
	 * 例：
	 *  <key>sourceSize</key>
	 *	<string>{960,640}</string>
	 */
	public int sourceSize[];
	
}
