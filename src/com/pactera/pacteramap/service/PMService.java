package com.pactera.pacteramap.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.pactera.pacteramap.PMApplication;
import com.pactera.pacteramap.business.PMInterface;

/**
 * service基类
 * @author WMF
 *
 */
public class PMService extends Service implements PMInterface {

	public PMApplication app;
	
	public PMService() {
		app=PMApplication.getInstance();
	}

	@Override
	public void CallBack(Object value) {

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void CallBack(int tag, Object value) {
		
	}

}
