package com.pactera.pacteramap.view.ui;

import java.util.List;

import org.litepal.crud.DataSupport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.config.PMShareKey;
import com.pactera.pacteramap.sqlite.litepal.bean.UserInfo;
import com.pactera.pacteramap.util.PMSharePreferce;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.view.component.MyEditText;

/***
 * 登录界面
 * 
 * @author ChunfaLee
 * @create 2015年8月10日11:41:05
 *
 */
public class PMLoginActivity extends PMActivity implements OnClickListener {

	private MyEditText etUserName, etPassWord;
	private PMSharePreferce share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		share = PMSharePreferce.getInstance(PMLoginActivity.this);
		initView();
	}

	private void initView() {
		etUserName = (MyEditText) findViewById(R.id.do_login_username);
		etPassWord = (MyEditText) findViewById(R.id.do_login_password);
		etUserName.setHintText(R.string.user_name);
		etPassWord.setHintText(R.string.pass_word);
		etPassWord.setInputPassword();
		findViewById(R.id.tv_login_register).setOnClickListener(this);
		findViewById(R.id.tv_login_forget).setOnClickListener(this);
		findViewById(R.id.tv_login_do).setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void dologin(String userName, String passWord) {
		if ("".equals(userName) || "".equals(passWord)) {
			T.showShort(PMLoginActivity.this, "用户名或密码不能为空");
		} else {
			List<UserInfo> ui = DataSupport.where("userName = ?", userName)
					.find(UserInfo.class);
			if (ui != null && ui.size() > 0) {
				share.setCache(PMShareKey.USERNAME, userName);
				startActivity(new Intent(PMLoginActivity.this,
						PMWelcomeActivity.class));
				PMLoginActivity.this.finish();
			} else {
				T.showShort(PMLoginActivity.this, "用户不存在，请注册后再登录");
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (00001 == resultCode) {
			Bundle b = data.getExtras();
			dologin(b.getString("user_name"), b.getString("pass_word"));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 注册
		case R.id.tv_login_register:
			Intent registIntent = new Intent(PMLoginActivity.this,
					PMRegisterActivity.class);
			startActivityForResult(registIntent, 0000);
			break;
		// 忘记密码
		case R.id.tv_login_forget:
			break;
		// 登录
		case R.id.tv_login_do:
			dologin(etUserName.getText().toString(), etPassWord.getText()
					.toString());
			break;
		default:
			break;
		}
	}

}
