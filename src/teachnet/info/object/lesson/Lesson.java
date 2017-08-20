package teachnet.info.object.lesson;

import teachnet.info.object.InfoObject;

public class Lesson extends InfoObject {

	private String name;
	
	private String shortName;
	
	private String weekTimes;	//课程周次
	
	private String partTimes;	//课程节次
	
	private String date;
	
	private String startTime;
	
	private String endTime;
	
	private String location;
	
	private String yIndex;
	
	private String xIndex;
	
	private boolean ifToBeDone;
	
	private int rowspan;
	
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	
	public int getRowspan() {
		return rowspan;
	}
	
	public void setIfToBeDone(boolean ifToBeDone) {
		this.ifToBeDone = ifToBeDone;
	}
	
	public boolean getIfToBeDone() {
		return ifToBeDone;
	}
	
	public void setYIndex(String yIndex) {
		this.yIndex = yIndex;
	}
	
	public String getYIndex() {
		return yIndex;
	}
	
	public void setXIndex(String xIndex) {
		this.xIndex = xIndex;
	}
	
	public String getXIndex() {
		return xIndex;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setShortName(String shortTime) {
		this.shortName = shortTime;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public void setWeekTimes(String weekTimes) {
		this.weekTimes = weekTimes;
	}
	
	public String getWeekTimes() {
		return weekTimes;
	}
	
	public void setPartTimes(String partTimes) {
		this.partTimes = partTimes;
	}
	
	public String getPartTimes() {
		return partTimes;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}
}
