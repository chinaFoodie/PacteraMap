package com.pactera.pacteramap.business;

import android.view.View;

/**
*
* @author Mingfan.Wang
* 2015-7-15
*/
public interface PMAdapterCallback {

	// 执行回调操作的方法
	void down(View v);

	void down(View v, int id);

	void up(View v);

	void up(View v, int id);
}
