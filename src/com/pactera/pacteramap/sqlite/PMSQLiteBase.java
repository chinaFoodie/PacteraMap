package com.pactera.pacteramap.sqlite;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author WMF
 * 
 */
public abstract class PMSQLiteBase extends PMSQLiteResult {

	public PMSQLiteBase() {

	}

	/**
	 * 执行一个sql语句,增，删，改 sql sql语句 selectionArgs 多个条件参数 database 数据库对象
	 * 
	 * @param sql
	 * @param bindArgs
	 * @param db
	 */
	public void execSQL(String sql, String[] bindArgs, SQLiteDatabase db) {
		try {
			db.execSQL(sql, bindArgs);
			this.suc=true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			this.suc=false;
		}
	}

	/**
	 * 传入一个数据库连接,执行查询SQL语句,查询一个记录
	 * 
	 * @param sql
	 * @param selectionArgs
	 * @param db
	 * @return
	 */
	public abstract String oneQuery(String sql, String[] selectionArgs,
			SQLiteDatabase db);
	/**
	 * 传入一个数据库连接,执行查询SQL语句,查询一条记录
	 * 
	 * @param sql
	 * @param selectionArgs
	 * @param db
	 * @return
	 */
	public abstract void SingleQuery(String sql, String[] selectionArgs,
			SQLiteDatabase db);

	/**
	 * 传入一个数据库连接,执行查询SQL语句,查询多条记录
	 * 
	 * @param sql
	 * @param selectionArgs
	 * @param db
	 * @return
	 */
	public abstract void MultiQuery(String sql, String[] selectionArgs,
			SQLiteDatabase db);
}
