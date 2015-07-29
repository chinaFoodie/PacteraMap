package com.pactera.pacteramap.service;

import java.util.Date;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;

import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import com.pactera.pacteramap.business.view.ui.xmpp.PMListener;
import com.pactera.pacteramap.notification.PMNotificationMessage;
import com.pactera.pacteramap.notification.PMNotificationMessageInfo;
import com.pactera.pacteramap.sqlite.vo.PMNotificationMessageVO;
import com.pactera.pacteramap.util.PMDateUtil;

/**
 * 接收服务端通知消息服务
 * @author WMF
 *
 */
public class PMNotificationService extends PMService {

	private PMListener listener;
	private Handler handler;
	private TextView count;
	
	public void setCount(TextView count) {
		this.count = count;
	}

	public PMNotificationService() {
		
	}

	/**
	 * 自定义的Binder类，这个是一个内部类，所以可以知道其外围类的对象，
	 * 通过这个类，让Activity知道其Service的对象。
	 */
	private final IBinder binder = new PMNotificationServiceBinder();
	public class PMNotificationServiceBinder extends Binder {
		/**
		 * 返回Activity所关联的Service对象，
		 * 这样在Activity里，就可调用Service里的一些公用方法和公用属性。
		 * @return
		 */
		public PMNotificationService getService() {
			return PMNotificationService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("", "onStartCommand");
		
		handler=new Handler(message);
		listener=new PMListener(handler);
		listener.start(getBaseContext());	
		
		unreadCount();
		return super.onStartCommand(intent, flags, startId);
	}
	/**
	 * 接收到服务端消息
	 */
	private Callback message=new Callback() {
		
		@Override
		public boolean handleMessage(android.os.Message msg) {
			Message xmppMsg=(Message)msg.obj;
			if(xmppMsg.getType().equals(Type.normal)){
				if(xmppMsg.getFrom().equals("127.0.0.1")){//通知消息来自服务端
					add(xmppMsg);
					unreadCount();
					showNotification("地图应用",xmppMsg.getBody());
					
				}
			}else if(xmppMsg.getType().equals(Type.chat)){
				
			}
			return false;
		}
	};
	
	/**
	 * 查询未读通知数量
	 */
	private void unreadCount(){
		app.data.unread=getUnread();
		if(count!=null){
			count.setText(""+app.data.unread);	
		}
	}
	
	/**
	 * 获取未读同通知消息数量
	 * @return
	 */
	private int getUnread(){
		PMNotificationMessageVO vo=new PMNotificationMessageVO();
		String sql="SELECT COUNT(*) FROM notification_message WHERE nread='1';";
		String result=vo.oneQuery(sql, null, app.data.db);
		return  Integer.parseInt(result);
	}
	/**
	 * 保存通知消息到数据库
	 * @param xmppMsg
	 */
	private void add(Message xmppMsg){
		PMNotificationMessageVO vo=new PMNotificationMessageVO();
		vo.setNtitle("");
		vo.setNbody(xmppMsg.getBody());
		vo.setNfrom(xmppMsg.getFrom());
		vo.setNto(xmppMsg.getTo());
		vo.setNtype(xmppMsg.getType().name());
		vo.setNread("1");
		vo.setNdatetime(PMDateUtil.getCurrentDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		String sql = "INSERT INTO notification_message(ntitle,nbody,nfrom,nto,ntype,nread,ndatetime)VALUES(?,?,?,?,?,?,?);";
		String[] bindArgs = new String[] {
				vo.getNtitle(),
				vo.getNbody(),
				vo.getNfrom(),
				vo.getNto(),
				vo.getNtype(),
				vo.getNread(),
				vo.getNdatetime()};
		vo.execSQL(sql, bindArgs, app.data.db);
		if(vo.isSuc()){
			Log.e("","保存成功");
		}
		bindArgs=null;
		vo=null;
	}
//	/**
//	 * 更新下载信息
//	 * 
//	 * @param urlStr
//	 * @param filePath
//	 * @param downloadSize
//	 * @param compeleteSize
//	 * @param startPosition
//	 * @param endPosition
//	 */
//	public void updateDownloadInfo(String urlStr, String filePath,
//			long downloadSize, long compeleteSize, long startPosition,
//			long endPosition) {
//		PDDownloadInfo downloadInfo = new PDDownloadInfo();
//		String sql = "UPDATE download_info SET filePath=?,downloadSize=?,compeleteSize=?,startPosition=?,endPosition=? WHERE urlStr=?;";
//		String urlStrBase64 = Base64.encode(urlStr);
//		String[] bindArgs = new String[] { filePath,
//				String.valueOf(downloadSize), String.valueOf(compeleteSize),
//				String.valueOf(startPosition), String.valueOf(endPosition),
//				urlStrBase64 };
//		downloadInfo.execSQL(sql, bindArgs, db);
//		System.out.println(downloadInfo.isSuccess());
//		bindArgs = null;
//		downloadInfo = null;
//	}
//
//	/**
//	 * 删除下载信息
//	 * 
//	 * @param urlStr
//	 */
//	public void deleteDownloadInfo(String urlStr) {
//		PDDownloadInfo downloadInfo = new PDDownloadInfo();
//		String sql = "DELETE FROM download_info WHERE urlStr=?;";
//		String urlStrBase64 = Base64.encode(urlStr);
//		String[] bindArgs = new String[] { urlStrBase64 };
//		downloadInfo.execSQL(sql, bindArgs, db);
//		System.out.println(downloadInfo.isSuccess());
//		bindArgs = null;
//		downloadInfo = null;
//	}
	/**
	 * 显示通知栏
	 * @param title
	 * @param text
	 */
	public void showNotification(String title,String text){
		if(app.data.hasShowNote){
			if (app.data.noteMessage == null) {
				app.data.noteMessage=new PMNotificationMessage();
			}
			PMNotificationMessageInfo noteInfo = new PMNotificationMessageInfo();
			noteInfo.setContentTitle(title);
			noteInfo.setContentText(text);
			// 通知栏显示
			app.data.noteMessage.showNotification(this, noteInfo);
			noteInfo = null;			
		}
	}

	/**
	 * 清理
	 */
	public void clean(){
		if(listener!=null){
			listener.clean();
			listener=null;
		}
	
	}
	
	@Override
	public void CallBack(Object value) {

	}
	
}
