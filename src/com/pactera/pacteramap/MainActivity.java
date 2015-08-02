package com.pactera.pacteramap;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;

import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.BaiduNaviManager;
import com.pactera.pacteramap.service.PMLocationService;
import com.pactera.pacteramap.util.L;
import com.pactera.pacteramap.util.PMActivityUtil;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.view.ui.PMWelcomeActivity;

/**
 * 入口页面
 */
public class MainActivity extends PMActivity {

	protected Handler handler;
	protected Thread thread;
	@SuppressWarnings("unused")
	private boolean mIsEngineInitSuccess = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main_activity);
		initNavi();
		handler = new Handler();
		thread = new Thread(new childThread());
		thread.start();
	}

	/**
	 * 子线程
	 * 
	 * @author ChunfaLee
	 */
	class childThread implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handler.post(mainThread);
		}
	}

	/** 初始化百度导航 */
	private void initNavi() {
		BaiduNaviManager.getInstance().initEngine(MainActivity.this,
				getSdcardDir(), mNaviEngineInitListener,
				new LBSAuthManagerListener() {
					@Override
					public void onAuthResult(int status, String msg) {
						String str = null;
						if (0 == status) {
							str = "导航key校验成功!";
						} else {
							str = "导航key校验失败, " + msg;
						}
						T.showShort(MainActivity.this, str);
					}
				});
	}

	private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {
		public void engineInitSuccess() {
			mIsEngineInitSuccess = true;
		}

		public void engineInitStart() {
		}

		public void engineInitFail() {
		}
	};

	// 获取sd卡路径
	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	/**
	 * 主线程
	 */
	private Runnable mainThread = new Runnable() {

		@Override
		public void run() {
			handler = null;
			thread = null;
			if (app.data.userSublist == null) {
				PMActivityUtil.next(MainActivity.this, PMWelcomeActivity.class);
				MainActivity.this.finish();
			} else {
				PMActivityUtil.next(MainActivity.this, PMWelcomeActivity.class);
				MainActivity.this.finish();
			}
		}
	};
}
