package com.pactera.pacteramap.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.pactera.pacteramap.PMApplication;
import com.pactera.pacteramap.business.PMInterface;

/**
 * 所有Activity的基类，所有页面的Activity都需要继承该Activity
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class PMActivity extends Activity implements PMInterface {

	public PMApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 添加到activity集合中
		PMApplication.getInstance().data.activity.add(this);
		app = PMApplication.getInstance();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 从activity集合中移除
		PMApplication.getInstance().data.activity.remove(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * 退出
	 * 
	 * @param code
	 */
	public void exit(int code) {
		PMApplication app = PMApplication.getInstance();
		for (int i = 0; i < app.data.activity.size(); i++) {
			PMActivity activity = app.data.activity.get(i);
			activity.finish();
		}
		// PMUtil.applicationExit();
	}

	@Override
	public void CallBack(Object value) {
		// 子类根据回调参数实现
	}

	@Override
	public void CallBack(int tag, Object value) {

	}
}
