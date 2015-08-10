package com.pactera.pacteramap.adapter.message;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.GridView;

/**
 * ViewPage数据适配器
 * 
 * @author ChunfaLee
 *
 */
public class MyPagerAdapter extends PagerAdapter {
	ArrayList<GridView> grids;

	public MyPagerAdapter(ArrayList<GridView> grids) {
		this.grids = grids;
	}

	public void setGrids(ArrayList<GridView> grids) {
		this.grids = grids;
	}

	@Override
	public int getCount() {
		return grids.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(grids.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(grids.get(position));
		return grids.get(position);
	}
}
