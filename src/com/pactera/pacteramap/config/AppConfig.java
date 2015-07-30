package com.pactera.pacteramap.config;

/**
 * 全局配置
 * 
 * @author ChunfaLee
 *
 */
public class AppConfig {
	/** 服务器IP ***/
	public static String IP = "172.19.64.52";
	/** 服务器XMPP_PORT ***/
	public static int XMPP_PORT = 5222;
	/** 服务器域名 ***/
	public static String HOSTIP = "http://192.168.0.107:8080/";
	/** 获取轨迹点list **/
	public static String GETTRACK = HOSTIP + "track/getTackByTime";
	/** 添加备忘录 */
	public static String ADDREMARK = HOSTIP + "addressInfo/insert";

}
