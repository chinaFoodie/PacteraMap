package com.pactera.pacteramap.view.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.adapter.PMNotificationHubAdapter;
import com.pactera.pacteramap.business.view.ui.notification.PMQueryNotificationMessageCommand;
import com.pactera.pacteramap.util.PMActivityUtil;
import com.pactera.pacteramap.view.PMActivity;

/**
 * 通知消息中心
 * 
 * @author ChunfaLee
 * 
 */
public class PMNotificationHubActivity extends PMActivity {

	private ListView listView;
	private PMNotificationHubAdapter adapter;
	private ArrayList<HashMap<String, Object>> dataList;
	private PMQueryNotificationMessageCommand queryCmd;

	public PMNotificationHubActivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.notification_hub_activity);
		listView = (ListView) this.findViewById(R.id.notification_hub_listView);
		queryCmd = new PMQueryNotificationMessageCommand();
		queryCmd.execute(this);
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
				PMActivityUtil.goBack(PMNotificationHubActivity.this);
			}
		});
		// 标题
		TextView tvMidTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvMidTitle.setText("消息中心");
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

	@SuppressWarnings("unchecked")
	@Override
	public void CallBack(Object value) {
		dataList=(ArrayList<HashMap<String, Object>>) value;
		adapter = new PMNotificationHubAdapter(this,
				R.layout.notification_hub_list_item, dataList);
		adapter.setCallback(this);
		// 设置适配器
		listView.setAdapter(adapter);
		// // 通知listView刷新数据
		// adapter.notifyDataSetChanged();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void up(View v, int id) {
		//跳转到详情页面
		System.out.println("跳转到详情页面");
		PMActivityUtil.next(this, PMNotificationDetailActivity.class);
	}
	
	/*
	 * 单击返回键的处理方式
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			PMActivityUtil.goBack(PMNotificationHubActivity.this);
			return false;
		}
		return true;
	}

}
