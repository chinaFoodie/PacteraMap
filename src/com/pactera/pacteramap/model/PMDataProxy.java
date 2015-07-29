package com.pactera.pacteramap.model;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

import com.pactera.pacteramap.notification.PMNotificationMessage;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.vo.PMUserSublistVO;

/**
 * 数据模型，用户保存内存数据，所有操作的内存数据在该类保存
 */
public class PMDataProxy {

	//所有activity
	public ArrayList<PMActivity> activity;
	//通知消息
	public PMNotificationMessage noteMessage;
	//是否显示通知栏
	public boolean hasShowNote;
	// 数据库对象
	public SQLiteDatabase db;
	//用户子表
	public PMUserSublistVO userSublist;
	//通知消息未读数量
	public int unread;
	//退出次数
	public int exitCount;
	//退出毫秒数
	public long exitMillisecond;
	
	public PMDataProxy(){
		activity=new ArrayList<PMActivity>();
		hasShowNote=true;
	}
}
