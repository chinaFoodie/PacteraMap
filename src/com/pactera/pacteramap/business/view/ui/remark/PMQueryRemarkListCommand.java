package com.pactera.pacteramap.business.view.ui.remark;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;

import com.pactera.pacteramap.PMApplication;
import com.pactera.pacteramap.business.PMCommand;
import com.pactera.pacteramap.business.PMInterface;
import com.pactera.pacteramap.util.PMGsonUtil;
import com.pactera.pacteramap.vo.PMRemark;
import com.pactera.pacteramap.vo.PMRemark.Data;

/**
 * 查询用户备忘录列表业务逻辑
 * 
 * @author ChunfaLee
 * @create 2015年6月24日16:02:53
 *
 */
@SuppressLint("SimpleDateFormat")
public class PMQueryRemarkListCommand extends PMCommand {
	private ArrayList<Data> listData;

	@Override
	public void execute(PMInterface iface) {
		iface.CallBack(getRemarkList());
	}

	@Override
	public void execute(PMInterface iface, Object value) {
		iface.CallBack(getRemarkList());
	}

	/**
	 * 获取备忘录列表
	 * 
	 * @return 备忘录json
	 */
	private String getRemarkList() {
		Date dt = new Date();
		listData = new ArrayList<PMRemark.Data>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String temp_str = sdf.format(dt);
		PMRemark remark = new PMRemark();
		for (int i = 0; i < 20; i++) {
			Data data = new PMRemark().new Data();
			data.content = "今天天气真好啊!<img>哈哈</img>好高兴啊.....";
			data.id = PMApplication.getInstance().getImei();
			// locked 0 为未锁定，1为锁定
			data.locked = i % 2 + "";
			data.remarkDate = temp_str;
			data.remarkTime = "09:32:11";
			data.title = "第" + i + "条：李坑强总理部署促进社会办医：“松绑”药到病除";
			listData.add(data);
		}
		remark.code = "0";
		remark.msg = "操作成功";
		remark.data = listData;
		return PMGsonUtil.obj2Json(remark);
	}

}
