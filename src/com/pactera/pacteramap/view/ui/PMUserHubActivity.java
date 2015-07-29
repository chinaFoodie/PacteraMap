package com.pactera.pacteramap.view.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.view.PMActivity;

/**
 * @author 个人中心
 *
 */
public class PMUserHubActivity extends PMActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.user_hub_activity);
		
		this.findViewById(R.id.button2).setOnClickListener(click2);
		this.findViewById(R.id.button3).setOnClickListener(click3);

	}
	private OnClickListener click2=new OnClickListener() {
		
		@Override
		public void onClick(View v) {

//			PMUserInfoCommand u=new PMUserInfoCommand();
//			u.execute(this,"123");
//			u=null;
			

		}
	};
	
	
	private OnClickListener click3=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			PMUserHubActivity.this.exit(0);
		}
	};
	
	@Override
	public void CallBack(Object value) {
		new AlertDialog.Builder(this).setTitle("标题")
				.setMessage(value.toString()).setPositiveButton("确定", null)
				.show();
	}

}
