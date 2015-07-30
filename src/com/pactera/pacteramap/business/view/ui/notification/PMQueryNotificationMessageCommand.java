package com.pactera.pacteramap.business.view.ui.notification;

import java.util.ArrayList;
import java.util.HashMap;

import com.pactera.pacteramap.business.PMInterface;
import com.pactera.pacteramap.business.PMThreadCommand;

/**
*
* @author Mingfan.Wang
* 2015-7-1
*/
public class PMQueryNotificationMessageCommand extends PMThreadCommand {

	private ArrayList<HashMap<String, Object>> dataList;
	
	public PMQueryNotificationMessageCommand() {

	}

	@Override
	public void execute(PMInterface iface) {
		super.execute(iface);
		dataList=new ArrayList<HashMap<String,Object>>();
	}
	
	@Override
	protected void childRunnable(){
//		String sql="SELECT * FROM notification_message ORDER BY ndatetime DESC;";
//		String[] selectionArgs=new String[]{};
//		PMNotificationMessageVO vo=new PMNotificationMessageVO();
//		vo.MultiQuery(sql, selectionArgs, app.data.db);
//		List<PMSQLiteResult> query=vo.getQuery();
//		
//		for(int i=0;i<query.size();i++){
//			PMNotificationMessageVO temp=(PMNotificationMessageVO)query.get(i);
//			HashMap<String, Object> hash=new HashMap<String, Object>();
//			hash.put("nid",temp.getNid());
//			hash.put("ntitle",temp.getNtitle());
//			hash.put("nbody",temp.getNbody());
//			hash.put("nfrom",temp.getNfrom());
//			hash.put("nto",temp.getNto());
//			hash.put("ntype",temp.getNtype());
//			hash.put("nread",temp.getNread());
//			hash.put("ndatetime",temp.getNdatetime());
//			dataList.add(hash);
//			hash=null;
//		}
//		selectionArgs=null;
//		vo=null;
		
		//测试，模拟数据
		for(int i=0;i<20;i++){
			HashMap<String, Object> hash=new HashMap<String, Object>();
			hash.put("nid",i+1);
			hash.put("ntitle","");
			hash.put("nbody","");
			hash.put("nfrom","");
			hash.put("nto","");
			hash.put("ntype","");
			hash.put("nread","");
			hash.put("ndatetime","");
			dataList.add(hash);
			hash=null;
		}
	}

	@Override
	protected void mainRunnable(){
		iface.CallBack(dataList);
		dataList=null;
	}
}
