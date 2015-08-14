package com.pactera.pacteramap.view.ui;

import java.io.Serializable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.adapter.PMRemarkAdapter;
import com.pactera.pacteramap.business.view.ui.remark.PMQueryRemarkListCommand;
import com.pactera.pacteramap.util.PMActivityUtil;
import com.pactera.pacteramap.util.PMGsonUtil;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.vo.PMRemark;

/**
 * 备忘录
 * 
 * @author ChunfaLee
 * @create 2015年6月17日13:50:31
 *
 */
public class PMRemarkActivity extends PMActivity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {
	private TextView tvMidTitle, tvMoreRight;
	private LinearLayout llBack, llMore;
	private ListView lvRecord;
	private PMRemarkAdapter remarkAdapter;
	private PMRemark remark;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remark_activity);
		init();
	}

	/** 初始化控件 */
	private void init() {
		tvMidTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvMidTitle.setText("备忘录");
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		tvMoreRight = (TextView) findViewById(R.id.tv_base_right);
		tvMoreRight.setText("新增");
		lvRecord = (ListView) findViewById(R.id.lv_remark);
		lvRecord.setOnItemClickListener(this);
		lvRecord.setOnItemLongClickListener(this);
		llMore = (LinearLayout) findViewById(R.id.ll_tv_base_right);
		llMore.setVisibility(View.VISIBLE);
		llMore.setOnClickListener(this);
		getRemarkList();
	}

	/**
	 * 获取备忘录列表
	 */
	private void getRemarkList() {
		new PMQueryRemarkListCommand().execute(this, "");
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
		/** 返回控件 */
		case R.id.ll_tv_base_left:
			PMRemarkActivity.this.finish();
			break;
		case R.id.ll_tv_base_right:
			PMActivityUtil.next(PMRemarkActivity.this,
					PMAddRemarkActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent detailsIntent = new Intent(PMRemarkActivity.this,
				PMRemarkDetailsActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("remark_data", (Serializable) remark.data.get(position));
		detailsIntent.putExtras(bundle);
		startActivity(detailsIntent);
	}

	/**
	 * 接收请求服务器返回数据
	 */
	@Override
	public void CallBack(Object value) {
		super.CallBack(value);
		remark = PMGsonUtil.getPerson(value.toString(), PMRemark.class);
		if (remark != null) {
			remarkAdapter = new PMRemarkAdapter(PMRemarkActivity.this,
					remark.data);
			lvRecord.setAdapter(remarkAdapter);
		} else {
			T.showShort(this, "返回结果有误");
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		T.showShort(PMRemarkActivity.this, "提示是否删除" + position);
		return false;
	}
}
