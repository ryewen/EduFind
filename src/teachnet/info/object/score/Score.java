package teachnet.info.object.score;

import teachnet.info.object.InfoObject;

public class Score extends InfoObject {

	private String name;
	
	private String credit;
	
	private String score;
	
	private String shortName;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	public String getCredit() {
		return credit;
	}
	
	public void setScore(String score) {
		this.score = score;
	}
	
	public String getScore() {
		return score;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public String convertName() {
		return name.split("]")[1];
	}
}
