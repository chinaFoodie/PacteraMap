package com.pactera.pacteramap.sqlite.litepal.bean;

import org.litepal.crud.DataSupport;

/**
 * 工作轨迹litepal
 * 
 * @author ChunfaLee
 *
 */
public class WorkTrackBean extends DataSupport {
	public static WorkTrackBean mTrack;

	public WorkTrackBean() {
	}

	public static WorkTrackBean getInstance() {
		if (mTrack == null) {
			return mTrack = new WorkTrackBean();
		} else {
			return mTrack;
		}
	}

	/** id */
	private Long id;
	/** 日期 */
	private String date;
	/** 用户名 */
	private String userName;
	/** 用户Imei */
	private String userImei;
	/** 经度 */
	private String longitude;
	/** 纬度 */
	private String latitude;
	/** 地址信息 */
	private String locAddress;
	/** 是否在地图上标识 */
	private String isMark;
	/** 描述 */
	private String desc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImei() {
		return userImei;
	}

	public void setUserImei(String userImei) {
		this.userImei = userImei;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLocAddress() {
		return locAddress;
	}

	public void setLocAddress(String locAddress) {
		this.locAddress = locAddress;
	}

	public String getIsMark() {
		return isMark;
	}

	public void setIsMark(String isMark) {
		this.isMark = isMark;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
