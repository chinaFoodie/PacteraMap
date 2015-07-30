package com.pactera.pacteramap.sqlite;

import java.util.List;

/**
 * @author ChunfaLee
 *
 */
public class PMSQLiteResult {
	
	protected boolean suc;// 是否成功
	protected String exec;//执行添加，删除，更新返回结果
	protected List<PMSQLiteResult> query;//执行多行查询返回集合列表
	protected boolean nil;//执行单行或多行数据查询是否返回空
	
	public PMSQLiteResult() {

	}

	public boolean isSuc() {
		return suc;
	}

	public void setSuc(boolean suc) {
		this.suc = suc;
	}

	public String getExec() {
		return exec;
	}

	public void setExec(String exec) {
		this.exec = exec;
	}

	public List<PMSQLiteResult> getQuery() {
		return query;
	}

	public void setQuery(List<PMSQLiteResult> query) {
		this.query = query;
	}

	public boolean isNil() {
		return nil;
	}

	public void setNil(boolean nil) {
		this.nil = nil;
	}
}
