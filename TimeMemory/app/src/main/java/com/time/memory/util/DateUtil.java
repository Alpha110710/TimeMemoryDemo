package com.time.memory.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 取得当前时间
 *
 * @author Qiu
 */
public class DateUtil {
	// 取得当前时间(0-23)
	public static Integer getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return Integer.parseInt(formatter.format(curDate));
	}

	// 取得当前日期
	public static String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return formatter.format(curDate);
	}

	// 取得当前日期
	public static String getCurrentDotDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return formatter.format(curDate);
	}

	// 取得当前日期
	public static String getCurrentDateLine() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return formatter.format(curDate);
	}

	// 取得当前日期
	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return formatter.format(curDate);
	}

	// 日期转换
	public static String getDate(String date) {
		try {
			Date parse = new SimpleDateFormat("yyyy年MM月dd").parse(date);
			return new SimpleDateFormat("yyyy-MM-dd").format(parse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取今天,昨天日期数据
	 *
	 * @return
	 */
	public static List<String> getYestDate() {
		List<String> dates = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date();// 取时间
		//今天
		String curDate = formatter.format(date);
		//转换日历类
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		// 把日期往后增加一天.整数往后推,负数往前移动
		calendar.add(calendar.DATE, -1);
		// 这个时间就是日期往后推一天的结果
		date = calendar.getTime();
		String yeatDate = formatter.format(date);
		dates.add(yeatDate);
		dates.add(curDate);
		return dates;
	}


	/**
	 * 日历获取当前时间
	 *
	 * @return
	 */
	public static String getCurDate() {
		Calendar mCalendar = Calendar.getInstance();
		// 获取当前对应的年、月、日的信息
		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH);
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);

		String mMonth = null;
		String mDay = null;
		if (month < 9)
			mMonth = "0" + (month + 1);
		else
			mMonth = String.valueOf(month + 1);
		if (day < 10)
			mDay = "0" + day;
		else {
			mDay = String.valueOf(day);
		}
		return year + "-" + mMonth + "-" + mDay + "    ";
	}
}
