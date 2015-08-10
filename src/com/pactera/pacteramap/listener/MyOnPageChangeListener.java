package com.pactera.pacteramap.listener;

import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.view.ui.PMMessageDetailsActivity;

/**
 * 当ViewPager翻页时触发
 * 
 * @author daobo.yuan
 *
 */
public class MyOnPageChangeListener implements OnPageChangeListener {

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		updateSelectedIndex(arg0);
	}

	/**
	 * 更新当前ViewPager索引
	 * 
	 * @param currentSelectIndex
	 */
	private void updateSelectedIndex(int currentSelectIndex) {
		if (null != PMMessageDetailsActivity.self) {
			int childCount = PMMessageDetailsActivity.self.ll_vp_selected_index
					.getChildCount();
			for (int i = 0; i < childCount; i++) {
				if (currentSelectIndex == i) {
					PMMessageDetailsActivity.self.ll_vp_selected_index
							.getChildAt(i).setBackgroundResource(
									R.drawable.dot_selected);
				} else {
					PMMessageDetailsActivity.self.ll_vp_selected_index
							.getChildAt(i).setBackgroundResource(
									R.drawable.dot_unselected);
				}
			}
		}
	}
}
