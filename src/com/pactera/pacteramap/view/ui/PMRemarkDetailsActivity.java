package com.pactera.pacteramap.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.vo.PMRemark.Data;

/**
 * 备忘录详情界面
 * 
 * @author ChunfaLee
 * @create 2015年6月24日18:59:03
 *
 */
public class PMRemarkDetailsActivity extends PMActivity implements
		OnClickListener {
	private LinearLayout llBack, llRight;
	private TextView tvMidTitle, tvRight;
	private Intent preIntent;
	private Data details;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remark_details_activity);
		preIntent = this.getIntent();
		details = (Data) preIntent.getSerializableExtra("remark_data");
		init();
	}

	/**
	 * 初始化视图控件
	 */
	private void init() {
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		tvMidTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvMidTitle.setText(details.title);
		tvRight = (TextView) findViewById(R.id.tv_base_right);
		tvRight.setText("下一条");
		llRight = (LinearLayout) findViewById(R.id.ll_tv_base_right);
		llRight.setVisibility(View.GONE);
		llRight.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/** 返回控件 */
		case R.id.ll_tv_base_left:
			PMRemarkDetailsActivity.this.finish();
			break;
		case R.id.ll_tv_base_right:
			T.showShort(PMRemarkDetailsActivity.this, "读取下一条内容...");
			break;
		default:
			break;
		}
	}

}
