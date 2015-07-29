package com.pactera.pacteramap.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;
import com.pactera.pacteramap.R;
import com.pactera.pacteramap.business.view.ui.main.PMLoginCommand;
import com.pactera.pacteramap.util.PMActivityUtil;
import com.pactera.pacteramap.view.PMActivity;

/**
 * 用户登录
 * @author WMF
 *
 */
public class PMLoginActivity extends PMActivity {

	private EditText user_name;
	private EditText password;
	private Button login;
	
	public PMLoginActivity() {
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login_activity);
		user_name=(EditText)this.findViewById(R.id.login_user_name);
		password=(EditText)this.findViewById(R.id.login_password);
		login=(Button)this.findViewById(R.id.login_login);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				RequestParams params=new RequestParams();
//				params.add("userName", user_name.getText().toString());
//				params.add("password", password.getText().toString());
//				PMLoginCommand cmd=new PMLoginCommand();
//				cmd.execute(PMLoginActivity.this,params);
//				cmd=null;
//				params=null;
				
				PMActivityUtil.next(PMLoginActivity.this, PMIndexActivity.class);
			}
		});
	}

	@Override
	public void CallBack(Object value) {
		Log.e("", value.toString());
		PMActivityUtil.next(PMLoginActivity.this, PMIndexActivity.class);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
}
