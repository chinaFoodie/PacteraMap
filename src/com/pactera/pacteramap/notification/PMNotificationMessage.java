package com.pactera.pacteramap.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.pactera.pacteramap.R;

/**
 * @author ChunfaLee
 *
 */
public class PMNotificationMessage {

	private NotificationManager manager;
	private Notification note;
	
	public PMNotificationMessage() {
		
	}

	/**
	 * 在状态栏显示通知
	 * 
	 * @param context
	 * @param nVo
	 */
	public void showNotification(Context context, PMNotificationMessageInfo nVo) {
		// 创建一个NotificationManager的引用
		manager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		// 设置通知的事件消息，新建一个Intent，点击该通知后要跳转的Activity
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);            
		ComponentName cn = new ComponentName("com.pactera.pacteramap", "com.pactera.pacteramap.view.ui.PMNotificationDetailActivity");            
		intent.setComponent(cn);
		cn=null;
		// 创建一个pendingIntent。
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent, 0);
		// 定义Notification的各种属性
		note = new Notification.Builder(context)
				.setContentTitle(nVo.getContentTitle()).setContentText(nVo.getContentText())
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(pendingIntent).build();
		// 将此通知放到通知栏的"Ongoing"即"正在运行"组中
		note.flags |= Notification.FLAG_ONGOING_EVENT;
		// 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用
		//note.flags |= Notification.FLAG_NO_CLEAR;
		// 发送此通知的时间戳
		note.when = System.currentTimeMillis();
		//播放系统默认声音
		note.defaults=Notification.DEFAULT_SOUND;
		// 把Notification传递给NotificationManager
		manager.notify(0, note);	
	}

	/**
	 * 删除通知
	 * 
	 * @param context
	 */
	public void hideNotification(Context context) {
		// 启动后删除之前我们定义的通知
		if (manager != null) {
			manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.cancel(0);
			manager = null;
			note = null;
		}
	}
}
