package httpClient.post.param.object;

import java.util.HashMap;
import java.util.Map;

import httpClient.TeachNetHttpClient;

public class LessonPagePostParams implements PagePostParams {
	
	public static final String LESSON_PAGE_URL = TeachNetHttpClient.HOME_URL + "/znpk/Pri_StuSel_rpt.aspx";
	
	private static final String[] PARAM_NAMES = {"px", "rad"};

	private static final String[] PARAM_VALUES = {"1", "on"};
	
	private static final String PARAM_YEAR_TERM_NAME = "Sel_XNXQ";
	
	private int year;
	
	private int term;
	
	public LessonPagePostParams(int year, int term) {
		this.year = year;
		this.term = term;
	}
	
	public Map<String, String> getPostParams() {
		Map<String, String> postParams = new HashMap<String, String>();
		for(int i = 0; i < PARAM_NAMES.length; i ++) {
			postParams.put(PARAM_NAMES[i], PARAM_VALUES[i]);
		}
		postParams.put(PARAM_YEAR_TERM_NAME, "" + year + term);
		return postParams;
	}
}
