package com.pactera.pacteramap.sqlite.vo;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pactera.pacteramap.sqlite.PMSQLiteBase;
import com.pactera.pacteramap.sqlite.PMSQLiteResult;

/**
 * 通知消息表
 * 
 * @author WMF
 * 
 */
public class PMNotificationMessageVO extends PMSQLiteBase {

	private int nid;//编号
	private String ntitle;//消息标题
	private String nbody;//消息主体
	private String nfrom;//消息发送者
	private String nto;//消息接收者
	private String ntype;//消息类型
	private String nread;//是否已读
	private String ndatetime;//接收时间
	
	@Override
	public String oneQuery(String sql, String[] selectionArgs,
			SQLiteDatabase db){
		String result=null;
		Cursor cur = null;
		try {
			cur = db.rawQuery(sql, selectionArgs);
			if (cur != null) {
				if (cur.moveToNext()) {
					result=cur.getString(0);
				}
			}
			cur.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	@Override
	public void SingleQuery(String sql, String[] selectionArgs,
			SQLiteDatabase db) {
		Cursor cur = null;
		try {
			cur = db.rawQuery(sql, selectionArgs);
			if (cur != null) {
				if (cur.moveToNext()) {
					nid = cur.getInt(0);
					ntitle = cur.getString(1);
					nbody=cur.getString(2);
					nfrom=cur.getString(3);
					nto=cur.getString(4);
					ntype=cur.getString(5);
					nread=cur.getString(6);
					ndatetime = cur.getString(7);
					nil = false;
				} else {
					nil = true;
				}
			} else {
				nil = true;
			}
			cur.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			nil = true;
		}
	}

	@Override
	public void MultiQuery(String sql, String[] selectionArgs, SQLiteDatabase db) {
		query = new ArrayList<PMSQLiteResult>();
		Cursor cur = null;
		try {
			cur = db.rawQuery(sql, selectionArgs);
			if (cur != null) {
				while (cur.moveToNext()) {
					PMNotificationMessageVO vo = new PMNotificationMessageVO();
					vo.nid = cur.getInt(0);
					vo.ntitle = cur.getString(1);
					vo.nbody=cur.getString(2);
					vo.nfrom=cur.getString(3);
					vo.nto=cur.getString(4);
					vo.ntype=cur.getString(5);
					vo.nread=cur.getString(6);
					vo.ndatetime = cur.getString(7);
					query.add(vo);
					vo = null;
				}
			}
			cur.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public String getNtitle() {
		return ntitle;
	}

	public void setNtitle(String ntitle) {
		this.ntitle = ntitle;
	}

	public String getNbody() {
		return nbody;
	}

	public void setNbody(String nbody) {
		this.nbody = nbody;
	}

	public String getNfrom() {
		return nfrom;
	}

	public void setNfrom(String nfrom) {
		this.nfrom = nfrom;
	}

	public String getNto() {
		return nto;
	}

	public void setNto(String nto) {
		this.nto = nto;
	}

	public String getNtype() {
		return ntype;
	}

	public void setNtype(String ntype) {
		this.ntype = ntype;
	}

	public String getNread() {
		return nread;
	}

	public void setNread(String nread) {
		this.nread = nread;
	}
	
	public String getNdatetime() {
		return ndatetime;
	}

	public void setNdatetime(String ndatetime) {
		this.ndatetime = ndatetime;
	}
}
