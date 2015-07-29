package com.pactera.pacteramap.mapinterface;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * 定位位置处理业务逻辑类
 * 
 * @author ChunfaLee
 * @create 2015年7月28日13:32:23
 *
 */
public class PMLocationCommand extends PMLocation {
	private Context context;
	public static LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public BDLocation locationResult;
	public PMLocationInterface interFace;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor = "gcj02";

	public PMLocationCommand(Context context) {
		this.context = context;
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			locationResult = location;
			/** 可以在这里定义个接口回调定位信息回去 */
			if (!"".equals(location.getAddrStr())) {
				interFace.locationCallBack(locationResult);
				mLocationClient.stop();
			}
		}
	}

	@Override
	public void execute(PMLocationInterface inFace) {
		/** 百度定位初始化 */
		interFace = inFace;
		mLocationClient = new LocationClient(context);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		InitLocation();
		mLocationClient.start();
	}

	//
	/** 初始化百度定位 */
	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 设置定位模式
		option.setCoorType(tempcoor);// 返回的定位结果是百度经纬度，默认值gcj02
		int span = 5000;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
}
