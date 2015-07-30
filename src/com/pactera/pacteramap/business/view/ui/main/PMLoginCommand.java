package com.pactera.pacteramap.business.view.ui.main;

import org.apache.http.Header;

import com.google.gson.internal.LinkedTreeMap;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pactera.pacteramap.business.PMCommand;
import com.pactera.pacteramap.business.PMInterface;
import com.pactera.pacteramap.util.PMGsonUtil;
import com.pactera.pacteramap.vo.PMBaseVO;
import com.pactera.pacteramap.vo.PMUserSublistVO;

/**
 * 用户登录
 * @author ChunfaLee
 *
 */
public class PMLoginCommand extends PMCommand {

	public PMLoginCommand() {

	}

	@Override
	public void execute(PMInterface iface) {


	}

	@Override
	public void execute(final PMInterface iface, Object value) {
		String url="http://192.168.0.109:8080/check";
		RequestParams params=(RequestParams)value;
		AsyncHttpClient client=new AsyncHttpClient();
		client.post(url,params,new AsyncHttpResponseHandler(){

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				super.onSuccess(arg0, arg1, arg2);
				String JSON=new String(arg2);
				PMBaseVO base=PMGsonUtil.getPerson(JSON,PMBaseVO.class);
				if(base.getCode().equals("200")){
					LinkedTreeMap<?, ?> map=(LinkedTreeMap<?, ?>)base.getData();
					String mapJSON=map.toString();
					app.data.userSublist=PMGsonUtil.getPerson(mapJSON,PMUserSublistVO.class);
					iface.CallBack("SUCCESS");					
				}else{
					iface.CallBack(base.getMsg());	
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				super.onFailure(arg0, arg1, arg2, arg3);
				iface.CallBack(arg3.getLocalizedMessage());
			}

			@Override
			public void onFinish() {
				super.onFinish();
			}
		});
		params=null;
		client=null;

	}

}
