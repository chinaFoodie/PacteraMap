package com.pactera.pacteramap.mapinterface;

/***
 * 地图定位回调接口
 * 
 * @author ChunfaLee
 * @create 2015年7月28日13:26:04
 *
 */
public interface PMLocationInterface {
	/**
	 * 回调并返回数据
	 * 
	 * @param value
	 *            数据
	 */
	void locationCallBack(Object value);
}
