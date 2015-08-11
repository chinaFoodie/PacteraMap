package com.pactera.pacteramap.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.sqlite.litepal.bean.UserInfo;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.view.component.MyEditText;
import com.pactera.pacteramap.view.component.sortlist.CharacterParser;

/**
 * 注册界面
 * 
 * @author ChunfaLee
 * @create 2015年8月10日17:28:50
 *
 */
public class PMRegisterActivity extends PMActivity implements OnClickListener {
	private MyEditText etUserName, etPassWord, etConfir;
	private TextView tvRegister, tvTitle;
	private LinearLayout llBack;
	private CharacterParser characterParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		characterParser = CharacterParser.getInstance();
		initview();
	}

	private void initview() {
		etUserName = (MyEditText) findViewById(R.id.et_register_username);
		etPassWord = (MyEditText) findViewById(R.id.et_register_password);
		etConfir = (MyEditText) findViewById(R.id.et_confir_password);
		tvRegister = (TextView) findViewById(R.id.tv_register_do);
		etPassWord.setInputPassword();
		etConfir.setInputPassword();
		etUserName.setHintText(R.string.user_name);
		etPassWord.setHintText(R.string.pass_word);
		etConfir.setHintText(R.string.confir_password);
		tvRegister.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvTitle.setText("注册");
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void doRegister(String userName, String passWord, String confirPsw) {
		if ("".equals(userName) || "".equals(passWord) || "".equals(confirPsw)) {
			T.showShort(PMRegisterActivity.this, "用户名或密码不能为空");
		} else if (passWord.equals(confirPsw)) {
			UserInfo ui = new UserInfo();
			ui.setAge(Math.round(100) + "");
			ui.setAvatarUrl("http://img5.imgtn.bdimg.com/it/u=3638412718,2736228005&fm=21&gp=0.jpg");
			ui.setBirthday("2011-11-11");
			ui.setPassWord(passWord);
			ui.setSex("女");
			String pinyin = characterParser.getSelling(userName);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				ui.setSortLetters(sortString);
			} else {
				ui.setSortLetters("#");
			}
			ui.setUserDesc("com.lidroid.xutils.exception.HttpException:unauthorized");
			ui.setUserName(userName);
			if (ui.save()) {
				setResult(
						00001,
						new Intent(this, PMLoginActivity.class).putExtra(
								"user_name", userName).putExtra("pass_word",
								passWord));
				PMRegisterActivity.this.finish();
			} else {
				T.showShort(PMRegisterActivity.this, "注册失败");
			}
		} else {
			T.showShort(PMRegisterActivity.this, "用户密码输入不一致，请重新输入");
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
		case R.id.tv_register_do:
			doRegister(etUserName.getText().toString(), etPassWord.getText()
					.toString(), etConfir.getText().toString());
			break;
		case R.id.ll_tv_base_left:
			PMRegisterActivity.this.finish();
			break;
		default:
			break;
		}
	}
}
