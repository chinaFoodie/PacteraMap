package com.pactera.pacteramap.vo;

/**
 * 用户子表
 * @author WMF
 *
 */
public class PMUserSublistVO{

	private String userName;//用户名
	private String password;//密码
	private String signInState;//签到状态

	private String userImage;//用户头像
	private String IMEINum;//设备唯一标示
	private String lokedPassword;//解锁密码
	private String addressInfo;//地址信息

	private String successionRecord;//连续签到天数
	private String totalRecord;//累计签到多少次
	private String totalRankings;//总排名

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSignInState() {
		return signInState;
	}
	public void setSignInState(String signInState) {
		this.signInState = signInState;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public String getIMEINum() {
		return IMEINum;
	}
	public void setIMEINum(String iMEINum) {
		IMEINum = iMEINum;
	}
	public String getLokedPassword() {
		return lokedPassword;
	}
	public void setLokedPassword(String lokedPassword) {
		this.lokedPassword = lokedPassword;
	}
	public String getAddressInfo() {
		return addressInfo;
	}
	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}
	public String getSuccessionRecord() {
		return successionRecord;
	}
	public void setSuccessionRecord(String successionRecord) {
		this.successionRecord = successionRecord;
	}
	public String getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(String totalRecord) {
		this.totalRecord = totalRecord;
	}
	public String getTotalRankings() {
		return totalRankings;
	}
	public void setTotalRankings(String totalRankings) {
		this.totalRankings = totalRankings;
	}

}
