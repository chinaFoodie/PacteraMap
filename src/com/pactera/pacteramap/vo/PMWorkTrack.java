package com.pactera.pacteramap.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 工作轨迹对象
 * 
 * @author Chunfalee
 * @create 2015年6月18日16:30:34
 *
 */
public class PMWorkTrack implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6217748674600639525L;
	public String msg;
	public String code;
	public ArrayList<Data> data;

	public class Data {
		public String id;
		public String trackTime;
		public Sublist userSublist;
		public List<AddressInfo> addressInfo;
	}

	public class Sublist {
		public String id;
		public String signInState;
		public String userImage;
		public String lokedPassword;
		public String successionRecord;
		public String totalRecord;
		public String totalRankings;
		public String addressInfo;
		public String imeinum;
	}

	public class AddressInfo {
		public String id;
		/**
		 * 省份
		 */
		public String province;
		/**
		 * 城市
		 */
		public String city;
		/**
		 * 区/县 信息
		 */
		public String district;
		/**
		 * 街道信息
		 */
		public String Street;
		/**
		 * 街道号码
		 */
		public String Street_number;
		/**
		 * 是否有地址信息
		 */
		public boolean has_addr;
		/**
		 * 详细地址信息
		 */
		public String addr_str;
		/**
		 * 经度坐标
		 */
		public String longitude;
		/**
		 * 纬度 坐标
		 */
		public String latitude;
		/**
		 * 当前时间
		 */
		public String time;
		/**
		 * gps定位结果时,获取gps锁定位用的卫星数
		 */
		public String satellite_number;
		/**
		 * 定位精度
		 */
		public String radius;
		/**
		 * 是否包含速度信息
		 */
		public String has_speed;
		/**
		 * 速度,仅gps定位结果时有速度信息
		 */
		public String speed;
		/**
		 * 手机当前朝向
		 */
		public String direction;
		/**
		 * 楼层信息,仅室内定位有效
		 */
		public String floor;
		/**
		 * 在网络定位结果的情况下,获取网络定位结果 是通过基站还是通过wifi定位得到
		 */
		public String network_location_type;
		/**
		 * 运营商信息
		 */
		public String operators;
	}

}
