package teachnet.info.object.rateLesson;

import httpClient.post.param.object.RatePagePostParams;
import teachnet.info.object.InfoObject;

public class RateLesson extends InfoObject {
	
	private static final String STATE_UNDONE = "未评";
	
	private static final String STATE_VIEW = "查看";

	private String name;
	
	private String teacherName;
	
	private String state;
	
	private String url; //评教或查看已评教的网址
	
	private RatePagePostParams params;
	
	private String postUrl; //提交评教数据
	
	private RateScore rateScore = new RateScore();
	
	private boolean ifNeedPost;
	
	public void initIfNeedPost() {
		if(ifHasRated()) {
			ifNeedPost = false;
		} else {
			ifNeedPost = true;
		}
	}
	
	public boolean getIfNeedPost() {
		return ifNeedPost;
	}
	
	public void changeRateScore(RateScore rateScore) {
		if(ifHasRated()) {
			if(! rateScore.equals(getRateScore())) {
				ifNeedPost = true;
				this.rateScore = rateScore;
			}
		} else {
			this.rateScore = rateScore;
		}
	}
	
	public boolean ifHasRated() {
		if(state == null) return false;
		if(state.equals(STATE_UNDONE)) return false;
		if(state.equals(STATE_VIEW)) return true;
		return false;
	}
	
	public void setRateScore(RateScore rateScore) {
		this.rateScore = rateScore;
	}
	
	public RateScore getRateScore() {
		return rateScore;
	}
	
	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}
	
	public String getPostUrl() {
		return postUrl;
	}
	
	public void setParams(RatePagePostParams params) {
		this.params = params;
	}
	
	public RatePagePostParams getParams() {
		return params;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	
	public String getTeacherName() {
		return teacherName;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
}
