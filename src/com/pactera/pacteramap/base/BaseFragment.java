package com.pactera.pacteramap.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pactera.pacteramap.business.PMInterface;

/**
 * fragment基类
 * 
 * @author Chunfa Lee
 * @since 2015年5月1日12:24:10
 * @version 1.00
 */
public class BaseFragment extends Fragment implements PMInterface {
	protected final String TAG = this.getClass().getName();
	/** 当前这个fragment所依赖的Activity */
	protected Activity activity;
	/** 全局的view */
	protected View rootView;

	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/** 当前fragment的view */
		if (rootView != null) {
			/*
			 * 缓存的rootView需要判断是否已经被加过parent，
			 * 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
			 */
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
			return rootView;
		}
		return null;
	}

	@Override
	public void CallBack(Object value) {

	}

	@Override
	public void CallBack(int tag, Object value) {

	}
}
