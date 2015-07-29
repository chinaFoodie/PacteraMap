package com.pactera.pacteramap;

import android.os.Bundle;
import android.os.Handler;

import com.pactera.pacteramap.util.PMActivityUtil;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.view.ui.PMIndexActivity;
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
		handler = new Handler();
		thread = new Thread(new childThread());
		thread.start();
	}

	/**
	 * 子线程
	 * 
	 * @author WMF
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
				PMActivityUtil.next(MainActivity.this, PMIndexActivity.class);
				MainActivity.this.finish();
			}
		}
	};
}
