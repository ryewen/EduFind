package httpClient.post.param.object;

import java.util.HashMap;
import java.util.Map;

import httpClient.TeachNetHttpClient;

public class ScorePagePostParams implements PagePostParams {
	
	public static final String SCORE_PAGE_URL = TeachNetHttpClient.HOME_URL + "/xscj/Stu_MyScore_rpt.aspx";

	private static final String[] FIXED_PARAM_NAMES = {"btn_search", "SelXNXQ", "zfx_flag", "zxf"};
	
	private static final String[] FIXED_PARAM_VALUES = {"????", "2", "0", "0"};
	
	private static final String YEAR_PARAM_NAME = "sel_xn";	//学年对应的POST请求参数, 如 2015-2016学年对应2015
	
	private static final String TERM_PARAM_NAME = "sel_xq"; //学期对应的POST请求参数, 第一学期为0，第二学期为1
	
	private static final String VALID_PARAM_NAME = "SJ";
	
	private boolean ifValid; //是否为有效成绩
	
	private int year;
	
	private int term;
	
	public ScorePagePostParams(boolean ifValid, int year, int term) {
		this.ifValid = ifValid;
		this.year = year;
		this.term = term;
	}
	
	public Map<String, String> getPostParams() {
		Map<String, String> postParams = new HashMap<String, String>();
		for(int i = 0; i < FIXED_PARAM_NAMES.length; i ++) {
			postParams.put(FIXED_PARAM_NAMES[i], FIXED_PARAM_VALUES[i]);
		}
		if(ifValid) {
			postParams.put(VALID_PARAM_NAME, "1");
		} else {
			postParams.put(VALID_PARAM_NAME, "0");
		}
		postParams.put(YEAR_PARAM_NAME, String.valueOf(year));
		postParams.put(TERM_PARAM_NAME, String.valueOf(term));
		return postParams;
	}
}
