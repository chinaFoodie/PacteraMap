package com.pactera.pacteramap.view.component;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pactera.pacteramap.R;

/**
 * 菊花进度条
 * 
 * @author ChunfaLee
 * @create 2015年7月28日17:37:20
 * 
 */
public class CustomProgress extends Dialog {

	@SuppressWarnings("unused")
	private Context context;
	private View contentView;
	private String message;

	public CustomProgress(Context context, String message) {
		super(context, R.style.Custom_Progress);
		this.context = context;
		this.message = message;
	}

	public CustomProgress(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			contentView = LayoutInflater.from(getContext()).inflate(
					R.layout.progress_custom, null);
			setContentView(contentView);
			if (message == null || message.length() == 0) {
				findViewById(R.id.message).setVisibility(View.GONE);
			} else {
				TextView txt = (TextView) findViewById(R.id.message);
				txt.setText(message);
			}
			getWindow().getAttributes().gravity = Gravity.CENTER;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		try {
			ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
			// 获取ImageView上的动画背景
			AnimationDrawable spinner = (AnimationDrawable) imageView
					.getBackground();
			// 开始动画
			spinner.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
