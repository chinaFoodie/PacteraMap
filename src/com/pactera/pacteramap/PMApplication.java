package com.pactera.pacteramap;

import org.litepal.LitePalApplication;

import android.os.Build;
import android.telephony.TelephonyManager;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pactera.pacteramap.business.PMDatabaseCommand;
import com.pactera.pacteramap.model.PMDataProxy;

/**
 * 全局类
 * 
 * @author ChunfaLee
 * 
 */
public class PMApplication extends LitePalApplication {
	// 当前对象
	private static PMApplication instance;
	// 数据模型
	public PMDataProxy data;
	/** 手机唯一标识码 */
	private String phoneImei;
	/** 初始化imageLoader */
	public ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("DZApplication");
		instance = this;
		data = new PMDataProxy();
		/** 百度地图初始化 ChunfaLee 2015年6月17日10:01:22 */
		SDKInitializer.initialize(getApplicationContext());
		/** 初始化imageloader */
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		imageLoader.init(config);
		/* 初始化本地数据库 */
		PMDatabaseCommand database = new PMDatabaseCommand();
		database.execute(null);
		database = null;
	}

	/**
	 * 获取手机唯一标识
	 * <ul>
	 * <li>不一定是imei,因为有的手机可能取不到imei</li>
	 * <li>取不到的情况会生成Pseudo-Unique ID类似于imei的标识</li>
	 * </ul>
	 */
	@SuppressWarnings("deprecation")
	public String getImei() {
		TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		phoneImei = TelephonyMgr.getDeviceId();
		if ("".equals(phoneImei)) {
			phoneImei = "35" + Build.BOARD.length() % 10 + Build.BRAND.length()
					% 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length()
					% 10 + Build.DISPLAY.length() % 10 + Build.HOST.length()
					% 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length()
					% 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length()
					% 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
					+ Build.USER.length() % 10;
		}
		return phoneImei;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	public static PMApplication getInstance() {
		return instance;
	}
}
