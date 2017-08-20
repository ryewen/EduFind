package httpClient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import teachnet.info.object.InfoObject;
import teachnet.info.object.lesson.Lesson;

@Component
public class SaveLessonDetailImpl extends SaveInfoDetailSupport {
	
	private static final String LESSON_FILE_DETAIL_KIND_NAME_THINKING = "课程";
	
	private static final String LESSON_FILE_DETAIL_KIND_NAME_HAND = "项目代码名称";
	
	private static final String LESSON_FILE_DETAIL_KIND_TIME = "时间";
	
	private static final String LESSON_FILE_DETAIL_KIND_LOCATION = "地点";
	
	private static final String LESSON_FILE_DETAIL_KIND_LENGTH = "length";
	
	private static final String LESSON_DETAIL_KIND_NAME = "name";
	
	private static final String LESSON_DETAIL_KIND_WEEKTIMES = "weekTimes";
	
	private static final String LESSON_DETAIL_KIND_PARTTIMES = "partTimes";
	
	private static final String LESSON_DETAIL_KIND_LOCATION = "location";
	
	@Autowired
	@Qualifier("saveLessonHtmlStrImpl")
	private SaveHtmlStr saveHtmlStr;
	
	protected SaveHtmlStr getSaveHtmlStr() {
		return saveHtmlStr;
	}
	
	protected List<InfoObject> getOneListObjectsAndDetails(List<String> objectsStrs) {
		List<InfoObject> lessons = new LinkedList<InfoObject>();
		Map<String, Integer> detailNameAndIndex = getDetailKindAndIndex(objectsStrs);
		Iterator<String> it = objectsStrs.iterator();
		int detailNameLength = detailNameAndIndex.get(LESSON_FILE_DETAIL_KIND_LENGTH);
		for(int i = 0; i < detailNameLength; i ++) {
			it.next();
		}
		int i = 0;
		Lesson lesson = null;
		int j = 0;
		while(it.hasNext()) {
			if(i == 0) lesson = new Lesson();
			String str = it.next();
			if(i == detailNameAndIndex.get(LESSON_DETAIL_KIND_NAME)) {
				if(str == null || str.equals("")) {
					str = ((Lesson) lessons.get(j - 1)).getName();
				}
				lesson.setName(str);
				j ++;
			}
			if(i == detailNameAndIndex.get(LESSON_DETAIL_KIND_WEEKTIMES)) {
				lesson.setWeekTimes(str);
			}
			if(i == detailNameAndIndex.get(LESSON_DETAIL_KIND_PARTTIMES)) {
				lesson.setPartTimes(str);
			}
			if(i == detailNameAndIndex.get(LESSON_DETAIL_KIND_LOCATION)) {
				String[] strs = str.split(";");
				lesson.setLocation(strs[0]);
			}
			i ++;
			if(i == detailNameLength + 1) {
				i = 0;
				lessons.add(lesson);
			}
		}
		return lessons;
	}
	
	protected Map<String, Integer> getDetailKindAndIndex(List<String> objectsStrs) {
		Map<String, Integer> detailNameAndIndex = new HashMap<String, Integer>();
		Iterator<String> it = objectsStrs.iterator();
		Integer i = 0;
		while(it.hasNext()) {
			String str = it.next();
			try {
				int temp = Integer.valueOf(str);
				detailNameAndIndex.put(LESSON_FILE_DETAIL_KIND_LENGTH, i);
				return detailNameAndIndex;
			} catch (NumberFormatException e) {
				if(str.equals(LESSON_FILE_DETAIL_KIND_NAME_THINKING)) {
					detailNameAndIndex.put(LESSON_DETAIL_KIND_NAME, i);
				}
				if(str.equals(LESSON_FILE_DETAIL_KIND_NAME_HAND)) {
					detailNameAndIndex.put(LESSON_DETAIL_KIND_NAME, i);
				}
				if(str.equals(LESSON_FILE_DETAIL_KIND_TIME)) {
					detailNameAndIndex.put(LESSON_DETAIL_KIND_WEEKTIMES, i);
					detailNameAndIndex.put(LESSON_DETAIL_KIND_PARTTIMES, i + 1);
				}
				if(str.equals(LESSON_FILE_DETAIL_KIND_LOCATION)) {
					detailNameAndIndex.put(LESSON_DETAIL_KIND_LOCATION, i + 1);
				}
				i ++;
				continue;
			}
		}
		return detailNameAndIndex;
	}
}
