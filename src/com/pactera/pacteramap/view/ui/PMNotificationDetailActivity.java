package com.pactera.pacteramap.view.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.util.PMActivityUtil;
import com.pactera.pacteramap.view.PMActivity;

/**
 * 通知消息详情
 * @author ChunfaLee
 *
 */
public class PMNotificationDetailActivity extends PMActivity {

	public PMNotificationDetailActivity() {
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.notification_detail_activity);
		init();
	}

	/**
	 * 初始化视图控件
	 */
	private void init() {
		// 返回
		LinearLayout llBack = (LinearLayout) findViewById(R.id.ll_img_base_left);
		llBack.setVisibility(View.VISIBLE);
		ImageView back = (ImageView) llBack.findViewById(R.id.img_base_left);
		back.setImageResource(R.drawable.iconfontfanhui);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PMActivityUtil.goBack(PMNotificationDetailActivity.this);
			}
		});
		// 标题
		TextView tvMidTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvMidTitle.setText("消息详情");
		// 删除
		LinearLayout rlBack = (LinearLayout) findViewById(R.id.ll_img_base_right);
		rlBack.setVisibility(View.VISIBLE);
		ImageView delete = (ImageView) rlBack.findViewById(R.id.img_base_right);
		delete.setImageResource(R.drawable.iconfontshanchu);
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}
	@Override
	protected void onStart() {
		super.onStart();
		hideNotification();
		app.data.hasShowNote=false;
	}

	@Override
	protected void onStop() {
		super.onStop();
		app.data.hasShowNote=true;
	}
	
	/**
	 * 隐藏通知栏
	 */
	public void hideNotification(){
		// 隐藏通知栏
		if (app.data.noteMessage != null) {
			app.data.noteMessage.hideNotification(this);
			app.data.noteMessage=null;
		}
	}
	
	/*
	 * 单击返回键的处理方式
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			PMActivityUtil.goBack(PMNotificationDetailActivity.this);
			return false;
		}
		return true;
	}

}
