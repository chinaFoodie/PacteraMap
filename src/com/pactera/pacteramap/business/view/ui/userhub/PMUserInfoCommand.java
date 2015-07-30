package com.pactera.pacteramap.business.view.ui.userhub;

import org.apache.http.Header;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pactera.pacteramap.business.PMCommand;
import com.pactera.pacteramap.business.PMInterface;

@SuppressWarnings("deprecation")
public class PMUserInfoCommand extends PMCommand {

	public PMUserInfoCommand() {
		
	}

	@Override
	public void execute(final PMInterface iface) {

	}

	@Override
	public void execute(final PMInterface iface, final Object value) {

		String url="http://192.168.0.109:8080/version/newVersion";
		RequestParams params=new RequestParams();
//		params.add("name", "zhangsan");
//		params.add("age", "18");
		AsyncHttpClient client=new AsyncHttpClient();
		client.post(url,params,new AsyncHttpResponseHandler(){

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				super.onSuccess(arg0, arg1, arg2);
				Log.e("a", new String(arg2));
				iface.CallBack(new String(arg2));
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				
				super.onFailure(arg0, arg1, arg2, arg3);
				
				Log.e("b", new String(arg2));
			}

			@Override
			public void onFinish() {
				super.onFinish();
				Log.e("c", "onFinish");
			}
		});
		params=null;
		client=null;
	}

}
