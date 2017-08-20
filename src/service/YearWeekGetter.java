package service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class YearWeekGetter {
	
	public static List<String> getStudyYears() {
		List<String> years = new LinkedList<String>();
		int beginYear = Integer.valueOf(getUsername().substring(0, 4));
		int nowYear = getNowYear();
		while(beginYear <= nowYear) {
			String year = String.valueOf(beginYear) + "-" + String.valueOf(++ beginYear);
			years.add(year);
		}
		return years;
	}
	
	public static String getUsername() {
		SecurityContext sc = SecurityContextHolder.getContext();
		Authentication auth = sc.getAuthentication();
		return auth.getName();
	}
	
	public static int getNowYear() {
		String nowYearStr = new SimpleDateFormat("yyyy").format(new Date());
		int nowYear = Integer.valueOf(nowYearStr);
		String nowMonthStr = new SimpleDateFormat("MM").format(new Date());
		int nowMonth = Integer.valueOf(nowMonthStr);
		if(nowMonth < 9) {
			nowYear --;
		}
		return nowYear;
	}
	
	public static String getNowWeek(int year, int beginMonth, int beginDay) { //开学日期
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.set(year, beginMonth - 1, beginDay);
		Calendar nowCalendar = Calendar.getInstance();
		Date beginDate = beginCalendar.getTime();
		Date nowDate = nowCalendar.getTime();
		int week = (int) ((nowDate.getTime() - beginDate.getTime()) / 1000 / 60 / 60 / 24 / 7);
		return String.valueOf(week + 1);
	}
}
