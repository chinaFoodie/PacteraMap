package com.pactera.pacteramap.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.adapter.PMRemarkAdapter;
import com.pactera.pacteramap.base.BaseFragment;
import com.pactera.pacteramap.business.PMInterface;
import com.pactera.pacteramap.business.view.ui.remark.PMQueryRemarkListCommand;
import com.pactera.pacteramap.util.PMGsonUtil;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.ui.PMLoginActivity;
import com.pactera.pacteramap.vo.PMRemark;

public class RemarkFragment extends BaseFragment implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener, PMInterface {
	private ListView lvRecord;
	private PMRemarkAdapter remarkAdapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (super.onCreateView(inflater, container, savedInstanceState) != null) {
			return rootView;
		}
		rootView = inflater.inflate(R.layout.remark_fragment, null);
		init(rootView);
		return rootView;
	}

	private void init(View view) {
		lvRecord = (ListView) view.findViewById(R.id.lv_remark);
		lvRecord.setOnItemClickListener(this);
		lvRecord.setOnItemLongClickListener(this);
		getRemarkList();
	}

	/**
	 * 获取备忘录列表
	 */
	private void getRemarkList() {
		new PMQueryRemarkListCommand().execute(this, "");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_goback:
			Intent backIntent = new Intent(activity, PMLoginActivity.class);
			activity.startActivity(backIntent);
			activity.finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		return false;
	}

	@Override
	public void CallBack(Object value) {
		PMRemark remark = PMGsonUtil
				.getPerson(value.toString(), PMRemark.class);
		if (remark != null) {
			remarkAdapter = new PMRemarkAdapter(activity, remark.data);
			lvRecord.setAdapter(remarkAdapter);
		} else {
			T.showShort(activity, "返回结果有误");
		}
	}

	@Override
	public void CallBack(int tag, Object value) {

	}
}
