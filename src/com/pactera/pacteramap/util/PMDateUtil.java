package com.pactera.pacteramap.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Title: DateUtil.java Description:日期工具类 Copyright:Copyright © <year>
 * <name>.All rights reserved.
 * 
 * @author Wang Mingfan
 * @date 2014-9-6
 * @version 2.4.2
 */
public class PMDateUtil {

	/**
	 * 获取日期中的年
	 * 
	 * @param date
	 *            日期
	 * @return 年份
	 */
	public static String getYear(Date date) {
		SimpleDateFormat f_year = new SimpleDateFormat("yyyy");
		return f_year.format(date).toString();
	}

	/**
	 * 获取日期中的月
	 * 
	 * @param date
	 *            日期
	 * @return 月份
	 */
	public static String getMonth(Date date) {
		SimpleDateFormat f_month = new SimpleDateFormat("MM");
		return f_month.format(date).toString();
	}

	/**
	 * 获取日期中天
	 * 
	 * @param date
	 *            日期
	 * @return 天
	 */
	public static String getDay(Date date) {
		SimpleDateFormat f_day = new SimpleDateFormat("dd");
		return f_day.format(date).toString();
	}

	/**
	 * 获得上个月的今天的日期
	 * @return
	 */
	public static Date getBeforeMonthDate(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}
	
	/**
	 * 获得日期的昨日
	 * @return
	 */
	public static Date getYesterdayDate(Date mDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(mDate);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
	/**
	 * 获得日期的明天
	 * @return
	 */
	public static Date getTomorrowDate(Date mDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(mDate);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}
	
	/**
	 * 获得上个月的今天的日期
	 * @param pattern yyyyMMdd yyyy-MM-dd
	 * @return
	 */
	public static String getBeforeMonthDate(Date date,String pattern){
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		String dateStr=format.format(date);
		format=null;
		return dateStr;
	}
	/**
	 * 获取日期中的星期
	 * 
	 * @param date
	 *            日期
	 * @return 星期
	 */
	public static String getWeek(Date date) {
		SimpleDateFormat f_week = new SimpleDateFormat("EEEEEEE");
		return f_week.format(date).toString();
	}

	/**
	 * 获取日期中的时间
	 * 
	 * @param date
	 *            日期 sssss
	 * @return 时间
	 */
	public static String getTime(Date date) {
		SimpleDateFormat f_time = new SimpleDateFormat("HH时mm分 ss秒");
		return f_time.format(date).toString();
	}

	/**
	 * 获取日期中的小时
	 * 
	 * @param date
	 *            日期
	 * @return 小时
	 */
	public static String getHour(Date date) {
		SimpleDateFormat f_year = new SimpleDateFormat("HH");
		return f_year.format(date).toString();
	}

	/**
	 * 获取日期中的分钟
	 * 
	 * @param date
	 *            日期
	 * @return 分钟
	 */
	public static String getMinute(Date date) {
		SimpleDateFormat f_year = new SimpleDateFormat("mm");
		return f_year.format(date).toString();
	}

	/**
	 * 获取日期中的秒
	 * 
	 * @param date
	 *            日期
	 * @return 秒
	 */
	public static String getSecond(Date date) {
		SimpleDateFormat f_year = new SimpleDateFormat("ss");
		return f_year.format(date).toString();
	}

	/**
	 * 格式化日期(年：月：日)
	 * @param year
	 * @param month
	 * @param day
	 * @param pattern  yyyyMMdd yyyy-MM-dd
	 * @return
	 */
	public static String formatDate(int year, int month,int day,String pattern) {
		String time = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		time = sdf.format(cal.getTime());
		sdf = null;
		return time;
	}

	/**
	 * 根据指定日期返回一个date
	 * @param year
	 * @param month
	 * @param day
	 */
	public static Date getDate(int year,int month,int day){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
	
	/**
	 * 格式化时间(小时：分)
	 * 
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static String formatTimeHour(int hour, int minute) {
		String time = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		time = sdf.format(cal.getTime());
		sdf = null;
		return time;
	}

	/**
	 * 格式化时间(分：秒)
	 * 
	 * @param minute
	 * @param second
	 * @return
	 */
	public static String formatTimeMinute(int minute, int second) {
		String time = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		time = sdf.format(cal.getTime());
		sdf = null;
		return time;
	}

	/**
	 * 通过一个秒，计算时间mm:ss
	 * 
	 * @param second
	 * @return
	 */
	public static String formatSecondToTime(int second) {
		int minute = second / 60;
		second = second - minute * 60;
		String time = formatTimeMinute(minute, second);
		return time;
	}

	/**
	 * 获得一个格式化日期和时间
	 * 
	 * @param pattern
	 *            "yyyy-MM-dd HH:mm"
	 * @return
	 */
	public static String getCurrentDate(Date date,String pattern) {
		String dateTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		dateTime = sdf.format(date);
		sdf = null;
		return dateTime;
	}

	/**
	 * 获取1970年1月1日0点0分0秒的毫秒数
	 * System.out.println(System.currentTimeMillis());//与改方法相同
	 * 
	 * @return
	 */
	public static long getMillisecond() {
		Date date = new Date();
		long millisecond = date.getTime();// 这就是距离1970年1月1日0点0分0秒的毫秒数
		date = null;
		return millisecond;
	}
	
	/**
	 * 更具字符串获得一个格式化日期
	 * @param dateStr			"yyyy-MM-dd HH:mm:ss"
	 * @param pattern
	 * @return
	 */
	public static Date getDateByString(String dateStr,String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = new Date();
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 两个日期进行比较大小
	 * @param date1S
	 * @param date2S
	 * @return
	 */
	public static boolean isBigger(String date1S, String date2S) {
		boolean result = false;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date1;
		Date date2;
		try {
			date1 = dateFormat.parse(date1S);
			date2 = dateFormat.parse(date2S);
			if (date1.getTime() <= date2.getTime())
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 两个日期进行比较大小
	 * @param date1S
	 * @param date2S
	 * @return
	 */
	public static boolean isBiggerNoEqual(String date1S, String date2S) {
		boolean result = false;
//		if (date1S.length() > 8) {
//			date1S = date1S.substring(0, 8);
//		}
//		if (date2S.length() > 8) {
//			date2S = date2S.substring(0, 8);
//		}
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date1;
		Date date2;
		try {
			date1 = dateFormat.parse(date1S);
			date2 = dateFormat.parse(date2S);
			if (date1.getTime() < date2.getTime())
				return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
}
