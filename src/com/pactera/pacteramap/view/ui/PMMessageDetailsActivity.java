package com.pactera.pacteramap.view.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.view.PMActivity;

/**
 * 消息详情
 * 
 * @author ChunfaLee
 * @create 2015年8月2日21:09:57
 *
 */
public class PMMessageDetailsActivity extends PMActivity implements
		OnClickListener {
	private LinearLayout llBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_details_activity);
		init();
	}

	private void init() {
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.ll_tv_base_left:
			PMMessageDetailsActivity.this.finish();
			break;
		default:
			break;
		}

	}

}
