package com.pactera.pacteramap.sqlite.litepal.bean;

import org.litepal.crud.DataSupport;

/**
 * 消息
 * 
 * @author ChunfaLee
 * @create 2015年8月3日15:56:05
 *
 */
public class MessageBean extends DataSupport {
	private String msgTitle;
	private String msgContent;
	private String msgDate;
	private String msgAvatar;
	private String msgFrom;
	private String msgTo;
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}
	public String getMsgAvatar() {
		return msgAvatar;
	}
	public void setMsgAvatar(String msgAvatar) {
		this.msgAvatar = msgAvatar;
	}
	public String getMsgFrom() {
		return msgFrom;
	}
	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}
	public String getMsgTo() {
		return msgTo;
	}
	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}
}
