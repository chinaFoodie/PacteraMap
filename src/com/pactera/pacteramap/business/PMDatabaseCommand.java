package com.pactera.pacteramap.business;

import android.content.Context;
import android.database.SQLException;

/**
 * 数据库中所有表在此创建
 * 
 * 拷贝数据库文件到电脑
 * adb shell
 * su root
 * cp /data/data/com.pactera.pacteramap/databases/PacteraMapDatabase.db /storage/sdcard1
 * adb pull /storage/sdcard1/PacteraMapDatabase.db c:\
 * 
 * @author WMF
 * 
 */
public class PMDatabaseCommand extends PMCommand {

	// 项目数据库
	final static String DATABASE_NAME = "PacteraMapDatabase.db";

	public PMDatabaseCommand() {

	}

	@Override
	public void execute(PMInterface iface) {
		// 创建项目数据库
		app.data.db = app.getBaseContext().openOrCreateDatabase(DATABASE_NAME,
				Context.MODE_PRIVATE, null);
		if (app.data.db.isOpen()) {
			// 创建通知消息表
			try {
				app.data.db
						.execSQL("CREATE TABLE IF NOT EXISTS notification_message("
								+ "nid INTEGER PRIMARY KEY AUTOINCREMENT,"
								+ "ntitle TEXT,"
								+ "nbody TEXT,"
								+ "nfrom VARCHAR(128),"
								+ "nto VARCHAR(128),"
								+ "ntype VARCHAR(128),"
								+ "nread VARCHAR(128),"
								+ "ndatetime DATETIME);");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			// 其他表.....
		}
	}

	@Override
	public void execute(PMInterface iface, Object value) {

	}

}
