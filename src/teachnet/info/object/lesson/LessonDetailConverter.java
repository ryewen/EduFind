package teachnet.info.object.lesson;

import java.util.List;

public interface LessonDetailConverter {
	
	public List<Lesson> convertDetail(Lesson lesson, int year, int month, int date);
	
	public void convertName(Lesson lesson);
	
	public void convertTime(Lesson lesson);
	
	public List<Lesson> convertDate(Lesson lesson, int year, int month, int date);
	
	public void convertXYIndex(Lesson lesson, List<String> dates);
	
	public void judgeIfToBeDone(Lesson lesson);
}
