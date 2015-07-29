package com.pactera.pacteramap.view.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.fragment.RoutePlanFragment;
import com.pactera.pacteramap.fragment.RemarkFragment;
import com.pactera.pacteramap.fragment.LoanFragment;
import com.pactera.pacteramap.fragment.WorkTrackFragment;
import com.pactera.pacteramap.util.FragmentUtil.TabIndex;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.component.MySlidingMenu;

/**
 * 主导航页activity
 * 
 * @author ChunfaLee
 * 
 */
public class PMMainActivity extends FragmentActivity implements OnClickListener {
	private long exitTime = 0;
	private FragmentManager manager;// Fragment碎片管理器
	private FragmentTransaction transaction;
	private int tabPressedColor = 0xffff6699;// tab选中色
	private int tabNormalColor = 0xff2b2b2b;// tab未选中色
	private int oldTabIndex = TabIndex.TAB_REMARK;// 上次选中的tab索引
	private int newTabIndex = TabIndex.TAB_REMARK;// 新选中的tab索引
	private MySlidingMenu leftMenu;
	private LinearLayout llBack;
	private TextView tvMore, tvTitle;
	/**
	 * 底部导航文本
	 */
	private TextView tvTabRemark, tvTabWorkTrack, tvTabRoute, tvTabLoan;
	/**
	 * 注意这里两个list的顺序一定要对应 *
	 */
	private List<TextView> listTab = new ArrayList<TextView>();
	private List<Fragment> fragmentArry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_layout);
		init();
	}

	/**
	 * 初始化视图 *
	 */
	private void init() {
		/** 初始化 */
		tvTabRoute = (TextView) findViewById(R.id.tv_tab_route_plan);
		tvTabRemark = (TextView) findViewById(R.id.tv_tab_remark);
		tvTabWorkTrack = (TextView) findViewById(R.id.tv_tab_work_track);
		tvTabLoan = (TextView) findViewById(R.id.tv_tab_loan);
		/** 设置相应的监听事件 */
		tvTabRoute.setOnClickListener(this);
		tvTabRemark.setOnClickListener(this);
		tvTabWorkTrack.setOnClickListener(this);
		tvTabLoan.setOnClickListener(this);
		leftMenu = (MySlidingMenu) findViewById(R.id.left_menu);
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		tvMore = (TextView) findViewById(R.id.tv_base_left);
		tvMore.setText("更多");
		tvTitle = (TextView) findViewById(R.id.tv_mid_title);
		/** 添加TextView到list里面 */
		listTab.add(tvTabRemark);
		listTab.add(tvTabWorkTrack);
		listTab.add(tvTabRoute);
		listTab.add(tvTabLoan);
		/** 初始化Fragment碎片管理，并添加到list中 */
		fragmentArry = new ArrayList<Fragment>();
		fragmentArry.add(new RemarkFragment());
		fragmentArry.add(new WorkTrackFragment());
		fragmentArry.add(new RoutePlanFragment());
		fragmentArry.add(new LoanFragment());
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		transaction.add(R.id.fl_main, fragmentArry.get(TabIndex.TAB_REMARK));// 将得到的fragment替换当前的viewGroup内
		tvTitle.setText("备忘录");
		transaction.commit();
	}

	/**
	 * 切换Fragment视图
	 *
	 * @param contentId
	 *            容器ID
	 * @param fragment
	 *            切换对象
	 * @param index
	 *            tab索引位置
	 */
	private void replace(int contentId, Fragment fragment, int index) {
		if (!fragment.isAdded()) {
			changeTabState(index);
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			// 根据索引加入切换动画
			if (newTabIndex > oldTabIndex) {
				fragmentTransaction.setCustomAnimations(
						R.anim.fragment_slide_right_enter,
						R.anim.fragment_slide_left_exit);
			} else {
				fragmentTransaction.setCustomAnimations(
						R.anim.fragment_slide_left_enter,
						R.anim.fragment_slide_right_exit);
			}
			fragmentTransaction.replace(contentId, fragment).commit();
			oldTabIndex = newTabIndex;
		}
	}

	/**
	 * 改变Tab状态
	 */
	private void changeTabState(int index) {
		newTabIndex = index;
		switch (index) {
		case TabIndex.TAB_TOUTEPLAN:
			otherTextChange(listTab, TabIndex.TAB_TOUTEPLAN);
			break;
		case TabIndex.TAB_REMARK:
			otherTextChange(listTab, TabIndex.TAB_REMARK);
			break;
		case TabIndex.TAB_WORKTRACK:
			otherTextChange(listTab, TabIndex.TAB_WORKTRACK);
			break;
		case TabIndex.TAB_LOAN:
			otherTextChange(listTab, TabIndex.TAB_LOAN);
			break;
		}
	}

	private void otherTextChange(List<TextView> list, int index) {
		for (int i = 0; i < list.size(); i++) {
			if (i == index) {
				list.get(i).setTextColor(tabPressedColor);
				tvTitle.setText(list.get(i).getText().toString());
			} else {
				list.get(i).setTextColor(tabNormalColor);
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				T.showShort(PMMainActivity.this, "再按一次退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_tab_route_plan:
			replace(R.id.fl_main, fragmentArry.get(TabIndex.TAB_TOUTEPLAN),
					TabIndex.TAB_TOUTEPLAN);
			break;
		case R.id.tv_tab_remark:
			replace(R.id.fl_main, fragmentArry.get(TabIndex.TAB_REMARK),
					TabIndex.TAB_REMARK);
			break;
		case R.id.tv_tab_work_track:
			replace(R.id.fl_main, fragmentArry.get(TabIndex.TAB_WORKTRACK),
					TabIndex.TAB_WORKTRACK);
			break;
		case R.id.tv_tab_loan:
			replace(R.id.fl_main, fragmentArry.get(TabIndex.TAB_LOAN),
					TabIndex.TAB_LOAN);
			break;
		case R.id.ll_tv_base_left:
			leftMenu.toggle();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
