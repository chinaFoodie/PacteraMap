package com.pactera.pacteramap.vo;


/**
 * 响应数据返回格式
 * @author WMF
 *
 */
public class PMBaseVO {

	//错误码
	private String code;
	//消息
	private String msg;
	//数据
	private Object data;
	
	public PMBaseVO() {
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


}
