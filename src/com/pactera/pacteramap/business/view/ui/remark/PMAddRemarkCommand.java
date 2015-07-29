package com.pactera.pacteramap.business.view.ui.remark;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pactera.pacteramap.business.PMCommand;
import com.pactera.pacteramap.business.PMInterface;
import com.pactera.pacteramap.config.AppConfig;

/**
 * 新增备忘录业务逻辑
 * 
 * @author ChunfaLee
 * @create 2015年6月24日16:01:04
 *
 */
@SuppressWarnings("deprecation")
public class PMAddRemarkCommand extends PMCommand {

	public PMAddRemarkCommand() {

	}

	@Override
	public void execute(final PMInterface iface) {

	}

	@Override
	public void execute(final PMInterface iface, final Object value) {
		RequestParams params = (RequestParams) value;
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(AppConfig.ADDREMARK, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						super.onSuccess(arg0, arg1, arg2);
						iface.CallBack(1, new String(arg2));
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						super.onFailure(arg0, arg1, arg2, arg3);
						iface.CallBack(1, "请求服务器失败");
					}

					@Override
					public void onFinish() {
						super.onFinish();
					}
				});
		params = null;
		client = null;
	}
}
