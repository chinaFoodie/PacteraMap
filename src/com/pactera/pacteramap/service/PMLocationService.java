package com.pactera.pacteramap.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.pactera.pacteramap.business.PMInterface;
import com.pactera.pacteramap.util.L;

/**
 * 定位service
 * 
 * @author ChunfaLee
 * @create 2015年8月2日13:20:46
 *
 */
public class PMLocationService extends Service {
	private PMInterface pInterface;
	private final IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		public PMLocationService getService() {
			return PMLocationService.this;
		}
	}

	public PMLocationService() {
	}

	public PMLocationService(PMInterface pInterface) {
		this.pInterface = pInterface;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		L.e("Service onCreate");
	}

	@Override
	public IBinder onBind(Intent intent) {
		IBinder myIBinder = null;
		if (null == myIBinder)
			myIBinder = new LocalBinder();
		return myIBinder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		L.e("onDestroy~~~");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		L.e("onStartCommand~~~");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		L.e("onUnbind~~~");
		return super.onUnbind(intent);
	}
}
