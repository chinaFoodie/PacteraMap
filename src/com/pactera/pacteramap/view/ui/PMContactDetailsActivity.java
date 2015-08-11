package com.pactera.pacteramap.view.ui;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.sqlite.litepal.bean.UserInfo;
import com.pactera.pacteramap.view.PMActivity;

/***
 * 联系人详情界面
 * 
 * @author ChunfaLee
 * @create 2015年8月11日13:22:57
 *
 */
public class PMContactDetailsActivity extends PMActivity implements
		OnClickListener {

	private TextView tvTitle, tvSend, tvBirthday, tvSex, tvName, tvDesc;
	private LinearLayout llBack;
	private Intent preIntent;
	private List<UserInfo> ui = new ArrayList<UserInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_details_activity);
		preIntent = this.getIntent();
		init();
	}

	private void init() {
		tvTitle = (TextView) findViewById(R.id.tv_mid_title);
		String midName = preIntent.getStringExtra("chat_name");
		// 查询数据库
		ui = DataSupport.where("userName = ?", midName).find(UserInfo.class);
		tvTitle.setText(ui.get(0).getUserName());
		tvBirthday = (TextView) findViewById(R.id.tv_contact_birthday_value);
		tvBirthday.setText(ui.get(0).getBirthday());
		tvSex = (TextView) findViewById(R.id.tv_contact_sex_value);
		tvSex.setText(ui.get(0).getSex());
		tvDesc = (TextView) findViewById(R.id.tv_contact_desc);
		tvDesc.setText(ui.get(0).getUserDesc());
		tvName = (TextView) findViewById(R.id.tv_contact_name);
		tvName.setText(ui.get(0).getUserName());
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		tvSend = (TextView) findViewById(R.id.tv_contact_send);
		tvSend.setOnClickListener(this);
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
			PMContactDetailsActivity.this.finish();
			break;
		// 发送消息跳转
		case R.id.tv_contact_send:
			Intent chatIntent = new Intent(PMContactDetailsActivity.this,
					PMMessageDetailsActivity.class);
			chatIntent.putExtra("chat_name",
					preIntent.getStringExtra("chat_name"));
			startActivity(chatIntent);
			PMContactDetailsActivity.this.finish();
			break;
		default:
			break;
		}
	}

}
