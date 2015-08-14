package com.pactera.pacteramap.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.view.component.CustomProgress;

/**
 * WebView显示界面
 * 
 * @author ChunfaLee
 * @create 2015年8月14日09:02:07
 *
 */
public class PMWebViewActivity extends PMActivity implements OnClickListener {
	private TextView tvTitle;
	private LinearLayout llBack;
	private String title, webUrl;
	private Intent preIntent;
	private WebView webView;
	private CustomProgress progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_activity);
		preIntent = this.getIntent();
		progress = new CustomProgress(this, "加载中");
		init();
	}

	/** 初始化view视图 */
	private void init() {
		tvTitle = (TextView) findViewById(R.id.tv_mid_title);
		title = preIntent.getStringExtra("web_title");
		tvTitle.setText(title);
		webUrl = preIntent.getStringExtra("web_url");
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		webView = (WebView) findViewById(R.id.pm_webview);
		WebSettings ws = webView.getSettings();
		ws.setJavaScriptEnabled(true);
		webView.requestFocus();
		ws.setDefaultTextEncodingName("utf-8");
		webView.loadUrl(webUrl);
		progress.show();
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					// 网页加载完成
					progress.dismiss();
				} else {
					// 加载中

				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_tv_base_left:
			PMWebViewActivity.this.finish();
			break;
		default:
			break;
		}
	}
}
