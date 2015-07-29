package com.pactera.pacteramap.business;

/**
 * Command回调接口
 * 
 * @author wmf
 *
 */
public interface PMInterface {

	
	/**
	 * 回调并返回数据
	 * @param value	数据
	 */
	void CallBack(Object value);

	
	/**
	 * 回调并返回数据
	 * @param tag	标识
	 * @param value	数据
	 */
	void CallBack(int tag, Object value);
}
