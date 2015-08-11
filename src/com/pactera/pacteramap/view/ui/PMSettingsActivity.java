package com.pactera.pacteramap.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.util.PMSharePreferce;
import com.pactera.pacteramap.view.PMActivity;

/**
 * 设置界面
 * 
 * @author ChunfaLee
 * @create 2015年8月11日09:16:26
 *
 */
public class PMSettingsActivity extends PMActivity implements OnClickListener {
	private TextView tvTitle, tvLogOut;
	private LinearLayout llBack;
	private PMSharePreferce share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		share = PMSharePreferce.getInstance(this);
		init();
	}

	/** 初始化视图 */
	private void init() {
		tvTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvTitle.setText("设置");
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		tvLogOut = (TextView) findViewById(R.id.tv_settings_login_out);
		tvLogOut.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_tv_base_left:
			PMSettingsActivity.this.finish();
			break;
		// 退出应用清除内存
		case R.id.tv_settings_login_out:
			share.clearCache();
			exit(0);
			startActivity(new Intent(PMSettingsActivity.this,
					PMDoLoginActivity.class));
			break;
		default:
			break;
		}
	}

}
