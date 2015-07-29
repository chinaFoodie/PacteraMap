package com.pactera.pacteramap.vo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 备忘录对象
 * 
 * @author ChunfaLee
 * @create 2015年6月24日15:35:55
 *
 */
public class PMRemark implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3452193548684077145L;
	public String msg;
	public String code;
	public ArrayList<Data> data;

	public class Data {
		public String id;
		/**
		 * 备忘录标题
		 */
		public String title;
		/**
		 * 备忘录内容
		 */
		public String content;
		/**
		 * 是否加锁,默认为false,不加锁
		 */
		public String locked;
		/**
		 * 备忘录日期
		 */
		public String remarkDate;
		/**
		 * 备忘录时间
		 */
		public String remarkTime;
	}
}
