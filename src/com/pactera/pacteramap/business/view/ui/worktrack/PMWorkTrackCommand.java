package com.pactera.pacteramap.business.view.ui.worktrack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;

import com.pactera.pacteramap.PMApplication;
import com.pactera.pacteramap.business.PMCommand;
import com.pactera.pacteramap.business.PMInterface;
import com.pactera.pacteramap.util.PMGsonUtil;
import com.pactera.pacteramap.vo.PMWorkTrack;
import com.pactera.pacteramap.vo.PMWorkTrack.AddressInfo;
import com.pactera.pacteramap.vo.PMWorkTrack.Data;
import com.pactera.pacteramap.vo.PMWorkTrack.Sublist;

@SuppressLint("SimpleDateFormat")
public class PMWorkTrackCommand extends PMCommand {
	private PMWorkTrack wTrack;
	private ArrayList<Data> list;
	private List<AddressInfo> listAddress;

	public PMWorkTrackCommand() {

	}

	@Override
	public void execute(final PMInterface iface) {

	}

	@Override
	public void execute(final PMInterface iface, final Object value) {
		// RequestParams params = (RequestParams) value;
		// AsyncHttpClient client = new AsyncHttpClient();
		// client.post(AppConfig.GETTRACK, params, new
		// AsyncHttpResponseHandler() {
		//
		// @Override
		// public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
		// super.onSuccess(arg0, arg1, arg2);
		// iface.CallBack(getTrackPoint());
		// }
		//
		// @Override
		// public void onFailure(int arg0, Header[] arg1, byte[] arg2,
		// Throwable arg3) {
		// super.onFailure(arg0, arg1, arg2, arg3);
		// }
		//
		// @Override
		// public void onFinish() {
		// super.onFinish();
		// }
		// });
		// params = null;
		// client = null;
		iface.CallBack(getTrackPoint());
	}

	/** 模拟轨迹点数据并返回给客户端 */
	private String getTrackPoint() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String temp_str = sdf.format(dt);
		list = new ArrayList<PMWorkTrack.Data>();
		listAddress = new ArrayList<PMWorkTrack.AddressInfo>();
		for (int i = 0; i < 5; i++) {
			AddressInfo aInfo = new PMWorkTrack().new AddressInfo();
			aInfo.addr_str = "成都市高新区天府软件园A8";
			Random random = new Random();
			double num = Double.valueOf(Math.floor(random.nextDouble() / 200));
			aInfo.latitude = Double.valueOf("30.554145") + num + "";
			aInfo.longitude = Double.valueOf("104.076137") + num + "";
			aInfo.time = temp_str;
			listAddress.add(aInfo);
		}
		for (int i = 0; i < 1; i++) {
			Sublist subList = new PMWorkTrack().new Sublist();
			subList.imeinum = PMApplication.getInstance().getImei();
			Data data = new PMWorkTrack().new Data();
			data.id = i + "";
			data.trackTime = temp_str;
			data.addressInfo = listAddress;
			data.userSublist = subList;
			list.add(data);
		}
		wTrack = new PMWorkTrack();
		wTrack.code = "0";
		wTrack.msg = "操作成功";
		wTrack.data = list;
		return PMGsonUtil.obj2Json(wTrack);
	}
}
