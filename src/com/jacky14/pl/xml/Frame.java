package com.jacky14.pl.xml;

public class Frame {

	
	
	

	/**
	 * @param key
	 */
	public Frame(String key) {
		this.key = key;
	}

	/**
	 * 절：
	 * <key>1003_role/0000</key>
	 */
	public String key;
	/**
	 * 절：
	 *  <key>frame</key>
	 *	<string>{{830,366},{102,162}}</string>
	 */
	public int frame[][];
	
	/**
	 * 절：
	 *  <key>offset</key>
	 *	<string>{-10,-38}</string>
	 */
	public float offset[];
	
	/**
	 * 절：
	 *  <key>rotated</key>
	 *	<true/>
	 */
	public boolean rotated;
	
	/**
	 * 절：
	 *  <key>sourceColorRect</key>
	 *	<string>{{419,277},{102,162}}</string>
	 */
	public int sourceColorRect[][];
	
	/**
	 * 절：
	 *  <key>sourceSize</key>
	 *	<string>{960,640}</string>
	 */
	public int sourceSize[];
	
}
