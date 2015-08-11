package com.pactera.pacteramap.view.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.fragment.ChatAllHistoryFragment;
import com.pactera.pacteramap.fragment.ContactlistFragment;
import com.pactera.pacteramap.view.PMActivity;

/**
 * 消息界面
 * 
 * @author ChunfaLee
 * @create 2015年8月11日10:02:56
 */
public class PMMessageActivity extends PMActivity {
	private ChatAllHistoryFragment chatHistoryFragment;
	private ContactlistFragment contactListFragment;
	private Fragment[] fragments;
	private int index;
	// 当前fragment的index
	private int currentTabIndex;
	private Button[] mTabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_activity);
		init();
	}

	private void init() {
		chatHistoryFragment = new ChatAllHistoryFragment();
		contactListFragment = new ContactlistFragment();
		fragments = new Fragment[] { chatHistoryFragment, contactListFragment };
		mTabs = new Button[2];
		mTabs[0] = (Button) findViewById(R.id.btn_conversation);
		mTabs[1] = (Button) findViewById(R.id.btn_address_list);
		mTabs[0].setSelected(true);
		registerForContextMenu(mTabs[1]);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, chatHistoryFragment)
				.add(R.id.fragment_container, contactListFragment)
				.hide(contactListFragment).show(chatHistoryFragment).commit();
	}

	/**
	 * button点击事件
	 * 
	 * @param view
	 */
	public void onTabClicked(View view) {

		switch (view.getId()) {
		case R.id.btn_conversation:
			index = 0;
			break;
		case R.id.btn_address_list:
			index = 1;
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
		// 把当前tab设为选中状态
		mTabs[index].setSelected(true);
		currentTabIndex = index;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
