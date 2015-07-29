package com.pactera.pacteramap.notification;

/**
 * @author WMF
 *
 */
public class PMNotificationMessageInfo {

	public PMNotificationMessageInfo() {
	
	}

	private String contentTitle;
	private String contentText;
	
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
}
