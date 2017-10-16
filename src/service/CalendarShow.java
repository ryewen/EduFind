package service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import httpClient.DealHtmlStrException;
import httpClient.SaveInfoDetail;
import httpClient.post.param.object.LessonPagePostParams;
import service.exception.ParamException;
import teachnet.info.object.InfoObject;
import teachnet.info.object.lesson.Lesson;
import teachnet.info.object.lesson.LessonDetailConverter;

@Component
public class CalendarShow {
	
	@Autowired
	@Qualifier("saveLessonDetailImpl")
	private SaveInfoDetail lessonImpl;
	
	@Autowired
	@Qualifier("lessonDetailConverterImpl")
	private LessonDetailConverter lessonConverter;

	public void showCalendar(int week, Model model, HttpServletRequest request,
			int studyYear, int beginYear, int beginMonth, int beginDay, int term) throws IOException, DealHtmlStrException, ParamException {
		model.addAttribute("studyYear", "" + studyYear + "-" + (studyYear + 1) + "学年");
		model.addAttribute("studyTerm", "第" + (term + 1) + "学期");
		model.addAttribute("nowDay", getNowDay());
		model.addAttribute("chiNums", getChiNums());
		model.addAttribute("selectedWeek", week);
		model.addAttribute("nowWeek", YearWeekGetter.getNowWeek(beginYear, beginMonth, beginDay));
		List<String> dates = getDates(week, beginYear, beginMonth, beginDay);
		model.addAttribute("dates", dates);
		Map<String, Lesson> lessonMap = new HashMap<String, Lesson>();
		Map<String, String> classInfoMap = new HashMap<String, String>(); //该课程信息图中包含课程表中各个格子中重叠的课程数和td的rowspan的值
		List<Cookie> cookies = (List<Cookie>) request.getSession().getAttribute("cookies");
		List<InfoObject> lessons = lessonImpl.getLessonsAndDetails(cookies, LessonPagePostParams.LESSON_PAGE_URL, new LessonPagePostParams(studyYear, term).getPostParams());
		Iterator<InfoObject> it = lessons.iterator();
		List<String> xyzKeys = new LinkedList<String>(); //Y代表课程表的Y轴，X即X轴，Z代表重叠课程的出现先后
		while(it.hasNext()) {
			Lesson lesson = (Lesson) it.next();
			List<Lesson> tempLessons = lessonConverter.convertDetail(lesson, beginYear, beginMonth, beginDay);
			Iterator<Lesson> it1 = tempLessons.iterator();
			while(it1.hasNext()) {
				Lesson lesson1 = it1.next();
				if(ifInWeek(lesson1, dates)) {
					lessonConverter.convertXYIndex(lesson1, dates);
					ifUpExists(classInfoMap, lesson1);
					lessonConverter.judgeIfToBeDone(lesson1);
					String xyKey = lesson1.getYIndex() + " " +lesson1.getXIndex();
					String xyzKey = getXyzKey(xyzKeys, xyKey);
					lessonMap.put(xyzKey, lesson1);
					xyzKeys.add(xyzKey);
					classInfoMap.put(xyKey + " maxZ", xyzKey.split(" ")[2]);
					classInfoMap.put(xyKey + " rowspan", String.valueOf(getMaxRowspan(lessonMap, xyKey)));
				}
			}
		}
		model.addAttribute("classInfoMap", classInfoMap);
		model.addAttribute("lessons", lessonMap);
	}
	
	private boolean ifInWeek(Lesson lesson, List<String> dates) {
		Iterator<String> it = dates.iterator();
		while(it.hasNext()) {
			if(lesson.getDate().equals(it.next())) {
				return true;
			}
		}
		return false;
	}
	
	private List<String> getDates(int week, int year, int beginMonth, int beginDay) throws ParamException {
		if(week <= 0) throw new ParamException("星期必须是大于零的整数");
		List<String> dates = new LinkedList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, beginMonth - 1, beginDay);
		calendar.add(3, week - 1);
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		for(int i = 0; i < 7; i ++) {
			if(i == 0) {
				calendar.add(5, 0);
			} else {
				calendar.add(5, 1);
			}
			dates.add(df.format(calendar.getTime()));
		}
		return dates;
	}
	
	private List<String> getChiNums() {
		List<String> weekDayNames = new LinkedList<String>();
		weekDayNames.add("一");
		weekDayNames.add("二");
		weekDayNames.add("三");
		weekDayNames.add("四");
		weekDayNames.add("五");
		weekDayNames.add("六");
		weekDayNames.add("日");
		return weekDayNames;
	}
	
	private void ifUpExists(Map<String, String> classInfoMap, Lesson lesson) { //判断td上方有没有rowspan=2的td
		int yIndex = Integer.valueOf(lesson.getYIndex());
		if(yIndex != 1 && yIndex != 3) return;
		String rowspan = classInfoMap.get((yIndex - 1) + " " + lesson.getXIndex() + " rowspan");
		if(rowspan == null) return;
		if(rowspan.equals("2")) {
			lesson.setYIndex(String.valueOf(yIndex - 1));
		}
	}
	
	private int getMaxRowspan(Map<String, Lesson> lessonMap, String xyKey) {
		int maxRowspan = 1;
		Set<Entry<String, Lesson>> entrys = lessonMap.entrySet();
		Iterator<Entry<String, Lesson>> it = entrys.iterator();
		while(it.hasNext()) {
			Entry<String, Lesson> entry = it.next();
			Lesson lesson = entry.getValue();
			String key = entry.getKey();
			if(key.startsWith(xyKey)) {
				int rowspan = lesson.getRowspan();
				if(rowspan > maxRowspan) maxRowspan = rowspan;
			}
		}
		return maxRowspan;
	}
	
	private String getXyzKey(List<String> xyzKeys, String xyKey) {
		int z = 0;
		Iterator<String> it = xyzKeys.iterator();
		while(it.hasNext()) {
			String[] strs = it.next().split(" ");
			if(xyKey.equals(strs[0] + " " + strs[1])) z ++;
		}
		return xyKey + " " + z;
	}
	
	private String getNowDay() {
		Calendar calendar = Calendar.getInstance();
		return new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
	}
}
