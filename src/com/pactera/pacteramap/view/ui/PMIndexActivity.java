package com.pactera.pacteramap.view.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.BaiduNaviManager;
import com.pactera.pacteramap.R;
import com.pactera.pacteramap.business.view.ui.index.PMExitApplication;
import com.pactera.pacteramap.service.PMNotificationService;
import com.pactera.pacteramap.service.PMNotificationService.PMNotificationServiceBinder;
import com.pactera.pacteramap.sqlite.vo.PMNotificationMessageVO;
import com.pactera.pacteramap.util.PMActivityUtil;
import com.pactera.pacteramap.util.PMUtil;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.PMActivity;

/**
 * 首页
 * 
 * @author WMF
 * 
 */
public class PMIndexActivity extends PMActivity {

	private TextView count;
	private PMNotificationService notiService;
	private boolean bound = false;
	@SuppressWarnings("unused")
	private boolean mIsEngineInitSuccess = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.index_activity);
		count = (TextView) this.findViewById(R.id.index_count);
		//
		this.findViewById(R.id.index_notification).setOnClickListener(onClick);
		this.findViewById(R.id.index_track_point).setOnClickListener(onClick);
		this.findViewById(R.id.index_work_track).setOnClickListener(onClick);
		this.findViewById(R.id.index_loan).setOnClickListener(onClick);
		this.findViewById(R.id.index_remark).setOnClickListener(onClick);
		initNavi();
		// 启动服务
		if (!bound) {
			// 绑定Service，绑定后就会调用serviceConnection里的onServiceConnected方法
			Intent service = new Intent(PMIndexActivity.this,
					PMNotificationService.class);
			bindService(service, notificationConnection,
					Context.BIND_AUTO_CREATE);
		}
		// 判断当前通知消息服务是否已经启动
		if (!PMUtil.isWorked(PMIndexActivity.this,
				"com.pactera.pacteramap.service.PMNotificationService")) {
			// 启动服务
			if (!bound) {
				// 绑定Service，绑定后就会调用serviceConnection里的onServiceConnected方法
				Intent service = new Intent(PMIndexActivity.this,
						PMNotificationService.class);
				bindService(service, notificationConnection,
						Context.BIND_AUTO_CREATE);
			}
		} else {
			// 查询未读通知数量
			app.data.unread = getUnread();
			if (count != null) {
				count.setText("" + app.data.unread);
			}
			T.showShort(this, "已经启动通知消息服务");
		}
		// //判断当前通知消息服务是否已经启动
		// if(!PMUtil.isWorked(PMIndexActivity.this,
		// "com.pactera.pacteramap.service.PMNotificationService")){
		// // 启动服务
		// if (!bound) {
		// // 绑定Service，绑定后就会调用serviceConnection里的onServiceConnected方法
		// Intent service = new Intent(PMIndexActivity.this,
		// PMNotificationService.class);
		// bindService(service, notificationConnection,
		// Context.BIND_AUTO_CREATE);
		// }
		// }else{
		// //查询未读通知数量
		// app.data.unread=getUnread();
		// if(count!=null){
		// count.setText(""+app.data.unread);
		// }
		// T.showShort(this,"已经启动通知消息服务");
		// }
		// 测试数据
		app.data.unread = 10;
		count.setText("10");
		Log.e("PMIndexActivity", "onCreate");
	}

	private void initNavi() {
		BaiduNaviManager.getInstance().initEngine(PMIndexActivity.this,
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
						T.showShort(PMIndexActivity.this, str);
					}
				});

	}

	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
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

	private OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.index_notification:
				PMActivityUtil.next(PMIndexActivity.this,
						PMNotificationHubActivity.class);
				break;
			case R.id.index_track_point:
				// 工作轨迹
				PMActivityUtil.next(PMIndexActivity.this,
						PMWorkTrackActivity.class);
				break;
			case R.id.index_work_track:
				// 路线规划
				PMActivityUtil.next(PMIndexActivity.this,
						PMRoutePlanActivity.class);
				break;
			case R.id.index_loan:
				// 贷款
				break;
			case R.id.index_remark:
				// 备忘录
				PMActivityUtil.next(PMIndexActivity.this,
						PMRemarkActivity.class);
				break;
			// case R.id.btn_main_work_track:
			// PMActivityUtil.next(PMIndexActivity.this,
			// PMWorkTrackActivity.class);
			// break;
			// case R.id.btn_location_start:
			// PMActivityUtil.next(PMIndexActivity.this,
			// PMAddRemarkActivity.class);
			// break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 解绑Service，节约内存
		if (bound) {
			unbindService(notificationConnection);
			bound = false;
		}
	}

	/**
	 * 清理
	 */
	public void clean() {
		// 停止服务
		Intent stopService = new Intent(PMIndexActivity.this,
				PMNotificationService.class);
		stopService(stopService);
		// 清理服务
		if (notiService != null) {
			notiService.clean();
			notiService = null;
		}
		// 解绑Service，节约内存
		if (bound) {
			unbindService(notificationConnection);
			bound = false;
		}
	}

	/**
	 * 定义downloadConnection，用于绑定Service的
	 */
	private ServiceConnection notificationConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			bound = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// 已经绑定了PMNotificationServiceBinder
			PMNotificationServiceBinder binder = (PMNotificationServiceBinder) service;
			notiService = binder.getService();
			notiService.setCount(count);
			// 启动通知服务
			Intent startService = new Intent(PMIndexActivity.this,
					PMNotificationService.class);
			startService(startService);
			startService = null;
			bound = true;
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	/*
	 * 单击返回键的处理方式
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			PMExitApplication exit = new PMExitApplication();
			exit.execute(this);
			exit = null;
			return false;
		}
		return true;
	}

	/**
	 * 获取未读同通知消息数量
	 * 
	 * @return
	 */
	private int getUnread() {
		PMNotificationMessageVO vo = new PMNotificationMessageVO();
		String sql = "SELECT COUNT(*) FROM notification_message WHERE nread='1';";
		String result = vo.oneQuery(sql, null, app.data.db);
		return Integer.parseInt(result);
	}
	// /**
	// * 获取未读同通知消息数量
	// * @return
	// */
	// private int getUnread(){
	// PMNotificationMessageVO vo=new PMNotificationMessageVO();
	// String sql="SELECT COUNT(*) FROM notification_message WHERE nread='1';";
	// String result=vo.oneQuery(sql, null, app.data.db);
	// return Integer.parseInt(result);
	// }

}
