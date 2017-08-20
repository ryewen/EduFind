package teachnet.info.object.lesson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class LessonDetailConverterImpl implements LessonDetailConverter {
	
	private static final String[] OLD_CLASS_START_TIME = {"8:00 AM", "8:55 AM", "10:10 AM", "11:05 AM", "2:30 PM",
			"3:25 PM", "4:40 PM", "5:35 PM", "7:30 PM", "8:25 PM", "9:20 PM", "10:15 PM"};
	
	private static final String[] OLD_CLASS_END_TIME = {"8:45 AM", "9:40 AM", "10:55 AM", "11:50 AM", "3:15 PM", 
			"4:10 PM", "5:25 PM", "6:20 PM", "8:15 PM", "9:10 PM", "10:05 PM", "11:00 PM"};
	
	private static final String[] NEW_CLASS_START_TIME = {"8:30 AM", "9:25 AM", "10:30 AM", "11:25 AM", "2:00 PM",
			"2:55 PM", "4:00 PM", "4:55 PM", "7:00 PM", "7:55 PM", "8:50 PM", "9:45 PM"};
	
	private static final String[] NEW_CLASS_END_TIME = {"9:15 AM", "10:10 AM", "11:15 AM", "12:10 AM", "2:45 PM",
			"3:40 PM", "4:45 PM", "5:40 PM", "7:45 PM", "8:40 PM", "9:35PM", "10:30PM"};
	
	public List<Lesson> convertDetail(Lesson lesson, int year, int month, int date) {
		convertName(lesson);
		convertTime(lesson);
		return convertDate(lesson, year, month, date);
	}
	
	public void convertName(Lesson lesson) {
		String name = lesson.getName();
		String[] nameStrs = name.split("]");
		String shortName = "";
		if(nameStrs.length == 1) {
			String str = nameStrs[0];
			shortName = str.substring(str.indexOf("-") + 4, str.length());
		}
		if(nameStrs.length == 2)	shortName = nameStrs[1];
		lesson.setShortName(shortName);
	}
	
	public void convertTime(Lesson lesson) {
		String partTimes = lesson.getPartTimes();
		try {
			int startClassNum = 0;
			int endClassNum = 0;
			if(partTimes.indexOf("-") != -1) {
			startClassNum = Integer.valueOf(partTimes.substring(partTimes.indexOf("[") + 1, 
							partTimes.indexOf("-")));
			endClassNum = Integer.valueOf(partTimes.substring(partTimes.indexOf("-") + 1, 
					partTimes.indexOf("节")));
			} else {
				startClassNum = Integer.valueOf(partTimes.substring(partTimes.indexOf("[") + 1, 
						partTimes.indexOf("节")));
				if(startClassNum >= OLD_CLASS_START_TIME.length) startClassNum = OLD_CLASS_START_TIME.length - 1;
				endClassNum = startClassNum;
			}
			String location = lesson.getLocation();
			if(location != null && !location.equals("")) {
				char zone = location.charAt(0);
				if(zone != 'D') {
					lesson.setStartTime(OLD_CLASS_START_TIME[startClassNum - 1]);
					lesson.setEndTime(OLD_CLASS_END_TIME[endClassNum - 1]);
				} else {
					lesson.setStartTime(NEW_CLASS_START_TIME[startClassNum - 1]);
					lesson.setEndTime(NEW_CLASS_END_TIME[endClassNum - 1]);
				}
			} else {
				lesson.setStartTime(OLD_CLASS_START_TIME[startClassNum - 1]);
				lesson.setEndTime(OLD_CLASS_END_TIME[endClassNum - 1]);
			}
			int space = endClassNum - startClassNum;
			int rowspan = 1;
			if(space <= 1) {
				rowspan = 1;
			} else {
				if(space <= 3) {
					rowspan = 2;
				}
			}
			lesson.setRowspan(rowspan);
		} catch(NumberFormatException e) {
			System.out.println("节次partTimes 无法被转化为 int");
		}
	}
	
	public List<Lesson> convertDate(Lesson lesson, int year, int month, int date) {
		List<Lesson> lessons = new LinkedList<Lesson>();
		String[] weekStrs = lesson.getWeekTimes().split(",");
		for(int i = 0; i < weekStrs.length; i ++) {
			String[] oneWeekStrs = weekStrs[i].split("-");
			int startWeek = 0;
			int endWeek = 0;
			if(oneWeekStrs.length == 1) {
				startWeek = Integer.valueOf(oneWeekStrs[0]);
				endWeek = startWeek;
			} 
			if(oneWeekStrs.length == 2) {
				startWeek = Integer.valueOf(oneWeekStrs[0]);
				endWeek = Integer.valueOf(oneWeekStrs[1]);
			}
			for(int j = startWeek; j <= endWeek; j ++) {
				Lesson newLesson = new Lesson();
				newLesson.setShortName(lesson.getShortName());
				newLesson.setStartTime(lesson.getStartTime());
				newLesson.setEndTime(lesson.getEndTime());
				newLesson.setLocation(lesson.getLocation());
				newLesson.setPartTimes(lesson.getPartTimes());
				newLesson.setRowspan(lesson.getRowspan());
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, month - 1, date);
				calendar.add(3, j - 1);
				calendar.add(5, getChiEngNumberMap().get(lesson.getPartTimes().substring(0, 1)) - 1);
				newLesson.setDate(new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime()));
				lessons.add(newLesson);
			}
		}
		return lessons;
	}
	
	public void convertXYIndex(Lesson lesson, List<String> dates) {
		Iterator<String> it = dates.iterator();
		int i = 0;
		while(it.hasNext()) {
			if(lesson.getDate().equals(it.next())) {
				lesson.setXIndex(String.valueOf(i));
				break;
			}
			i ++;
		}
		String partTimes = lesson.getPartTimes();
		String classIndexStr = null;
		if(partTimes.indexOf("-") != -1) {
			classIndexStr = partTimes.split("\\[")[1].split("-")[0];
		} else {
			classIndexStr = partTimes.split("\\[")[1].split("节")[0];
		}
		int classIndex = Integer.valueOf(classIndexStr) / 2;
		if(classIndex <= 4) {
			lesson.setYIndex(String.valueOf(classIndex));
		} else {
			lesson.setYIndex("4");
		}
	}
	
	private Map<String, Integer> getChiEngNumberMap() {
		Map<String, Integer> chiEngNumberMap = new HashMap<String, Integer>();
		chiEngNumberMap.put("一", 1);
		chiEngNumberMap.put("二", 2);
		chiEngNumberMap.put("三", 3);
		chiEngNumberMap.put("四", 4);
		chiEngNumberMap.put("五", 5);
		chiEngNumberMap.put("六", 6);
		chiEngNumberMap.put("日", 7);
		return chiEngNumberMap;
	}

	@Override
	public void judgeIfToBeDone(Lesson lesson) {
		Date now = new Date();
		String todayStr = new SimpleDateFormat("MM/dd/yyyy").format(now);
		if(lesson.getDate().equals(todayStr)) {
			String endTime = lesson.getEndTime();
			int endHour = Integer.valueOf(endTime.split(":")[0]);
			String nowHM = new SimpleDateFormat("HH:mm").format(now);
			if(endTime.split(" ")[1].equals("PM")) {
				endHour += 12;
			}
			int nowHour = Integer.valueOf(nowHM.split(":")[0]);
			if(endHour > nowHour) {
				lesson.setIfToBeDone(true);
			} else {
				if(endHour == nowHour) {
					int endMinute = Integer.valueOf(endTime.split(":")[1].split(" ")[0]);
					int nowMinute = Integer.valueOf(nowHM.split(":")[1]);
					if(endMinute > nowMinute) lesson.setIfToBeDone(true);
				}
			}
		} else {
			lesson.setIfToBeDone(false);
		}
	}
}
