package com.pactera.pacteramap.view.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.adapter.PMChoicePositionAdapter;
import com.pactera.pacteramap.view.PMActivity;

/**
 * 选择终点位置
 * 
 * @author ChunfaLee
 *
 */
public class PMChoicePositionActivity extends PMActivity implements
		OnClickListener, OnItemClickListener {
	private ListView lvPositon;
	private TextView tvTitle;
	private LinearLayout llBack;
	private Intent preInent;
	private PMChoicePositionAdapter adapter;
	private List<String> s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice_position_activity);
		preInent = this.getIntent();
		init();
	}

	/** 初始化视图 */
	private void init() {
		lvPositon = (ListView) findViewById(R.id.lv_choice_position);
		lvPositon.setOnItemClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvTitle.setText("选择终点位置");
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		s = preInent.getStringArrayListExtra("list_position");
		adapter = new PMChoicePositionAdapter(this, s);
		lvPositon.setAdapter(adapter);
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
	public void onItemClick(AdapterView<?> parent, View view, int positon,
			long id) {
		setResult(00002, new Intent(this, PMRoutePlanActivity.class).putExtra(
				"position", s.get(positon)));
		PMChoicePositionActivity.this.finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_tv_base_left:
			PMChoicePositionActivity.this.finish();
			break;

		default:
			break;
		}
	}

}
