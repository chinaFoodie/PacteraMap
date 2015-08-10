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
public class PMDoLoginActivity extends PMActivity implements OnClickListener {

	private MyEditText etUserName, etPassWord;
	private PMSharePreferce share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_login_activity);
		share = PMSharePreferce.getInstance(PMDoLoginActivity.this);
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
			T.showShort(PMDoLoginActivity.this, "用户名或密码不能为空");
		} else {
			List<UserInfo> ui = DataSupport.where("userName = ?", userName)
					.find(UserInfo.class);
			if (ui != null && ui.size() > 0) {
				share.setCache(PMShareKey.USERNAME, userName);
				startActivity(new Intent(PMDoLoginActivity.this,
						PMWelcomeActivity.class));
				PMDoLoginActivity.this.finish();
			}
			// UserInfo ui = new UserInfo();
			// ui.setAge(Math.round(100) + "");
			// ui.setAvatarUrl("http://img5.imgtn.bdimg.com/it/u=3638412718,2736228005&fm=21&gp=0.jpg");
			// ui.setBirthday("2011-11-11");
			// ui.setPassWord(passWord);
			// ui.setSex("女");
			// ui.setUserDesc("com.lidroid.xutils.exception.HttpException:unauthorized");
			// ui.setUserName(userName);
			// ui.save();
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
