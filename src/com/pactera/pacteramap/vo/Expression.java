package com.pactera.pacteramap.vo;

/**
 * 表情对象
 * 
 * @author ChunfaLee
 *
 */
public class Expression {
	public int drableId;// 表情图像资源ID
	public String code;// 表情代码

	public Expression() {
		super();
	}

	public Expression(int drableId, String code) {
		super();
		this.drableId = drableId;
		this.code = code;
	}

	public int getDrableId() {
		return drableId;
	}

	public void setDrableId(int drableId) {
		this.drableId = drableId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
