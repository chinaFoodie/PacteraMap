package com.pactera.pacteramap.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.base.BaseFragment;


public class RoutePlanFragment extends BaseFragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (super.onCreateView(inflater, container, savedInstanceState) != null) {
			return rootView;
		}
		rootView = inflater.inflate(R.layout.financical_fragment, null);
		return rootView;
	}
}
