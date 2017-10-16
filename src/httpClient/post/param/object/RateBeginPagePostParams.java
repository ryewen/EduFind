package httpClient.post.param.object;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import httpClient.TeachNetHttpClient;
import httpClient.DealHtmlStrException;
import httpClient.SaveHtmlStrSupport;
import controller.HomeController;

@Component
public class RateBeginPagePostParams {
	
	@Autowired
	private TeachNetHttpClient client;

	public static final String RATE_BEGIN_PAGE_URL = TeachNetHttpClient.HOME_URL + "/jxkp/Stu_wskp_rpt.aspx";
	
	private static final String RATE_PARAMETER_URL = TeachNetHttpClient.HOME_URL + "/jxkp/Stu_wskp.aspx";
	
	public Map<String, String> getPostParams(List<Cookie> cookies) throws IOException, DealHtmlStrException {
		Map<String, String> postParams = new HashMap<String, String>();
		//postParams.put("﻿hid_2016001", "ʱ�����Σ�2016-12-08 00:00:00--2017-01-07 00:00:00");
		//postParams.put("sel_lc", "20160|2016001|1|1");
		String html = client.saveTableHtmlStr(cookies, RATE_PARAMETER_URL, null);
		String name1 = "hid_";
		String para1 = "ʱ�����Σ�" + SaveHtmlStrSupport.getValue(html, "value='时间区段：", "'>");
		String para2 = SaveHtmlStrSupport.getValue(html, "onchange=ListTime(this)><option value=", ">" + HomeController.STUDY_YEAR + "-" + (HomeController.STUDY_YEAR + 1));
		name1 += para2.split("|")[1];
		postParams.put(name1, para1);
		postParams.put("sel_lc", para2);
		return postParams;
	}

}
