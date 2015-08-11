package com.pactera.pacteramap.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.adapter.SortAdapter;
import com.pactera.pacteramap.base.BaseFragment;
import com.pactera.pacteramap.sqlite.litepal.bean.UserInfo;
import com.pactera.pacteramap.view.component.SideBar;
import com.pactera.pacteramap.view.component.SideBar.OnTouchingLetterChangedListener;
import com.pactera.pacteramap.view.component.sortlist.PinyinComparator;
import com.pactera.pacteramap.view.ui.PMMessageDetailsActivity;

/**
 * 联系人界面
 * 
 * @author ChunfaLee
 *
 */
public class ContactlistFragment extends BaseFragment implements
		OnClickListener, OnItemClickListener {

	private TextView tvTitle, dialog;
	private ListView lvContact;
	private SideBar sideBar;
	private SortAdapter adapter;
	private LinearLayout llBack;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	private List<UserInfo> listUser = new ArrayList<UserInfo>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_contact_list, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
		pinyinComparator = new PinyinComparator();
		tvTitle = (TextView) getView().findViewById(R.id.tv_mid_title);
		tvTitle.setText("联系人");
		llBack = (LinearLayout) getView().findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		lvContact = (ListView) getView().findViewById(R.id.country_lvcountry);
		sideBar = (SideBar) getView().findViewById(R.id.sidrbar);
		dialog = (TextView) getView().findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		listUser = getContactList();
		Collections.sort(listUser, pinyinComparator);
		adapter = new SortAdapter(activity, listUser);
		lvContact.setAdapter(adapter);
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					lvContact.setSelection(position);
				}
			}
		});
		lvContact.setOnItemClickListener(this);
	}

	/** 查询联系人数据库 */
	private List<UserInfo> getContactList() {
		return DataSupport.findAll(UserInfo.class);
	}

	/** 切换界面刷新 */
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ll_tv_base_left:
			activity.finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View parent, int position,
			long id) {
		Intent chatIntent = new Intent(activity, PMMessageDetailsActivity.class);
		chatIntent.putExtra("chat_name", listUser.get(position).getUserName());
		activity.startActivity(chatIntent);
	}
}
