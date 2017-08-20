package httpClient;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import httpClient.post.param.object.RatePagePostParams;
import teachnet.info.object.rateLesson.RateLesson;
import teachnet.info.object.rateLesson.RateScore;

@Component
public class PostRateLesson {
	
	@Autowired
	private TeachNetHttpClient client;
	
	public void post(List<Cookie> cookies, List<RateLesson> lessons) throws IOException {
		Iterator<RateLesson> it = lessons.iterator();
		int i = 1;
		while(it.hasNext()) {
			RateLesson lesson = it.next();
			if(lesson.getIfNeedPost()) recPost(cookies, lesson, i);
			i ++;
		}
	}

	private void recPost(List<Cookie> cookies, RateLesson lesson, int index) throws IOException {
		System.out.println("post...\n" + lesson.getRateScore().getWords());
		setPostParams(cookies, lesson, index);
		if(!lesson.ifHasRated()) {
			client.saveTableHtmlStr(cookies, lesson.getPostUrl(), lesson.getParams().getPostParams());
		} else {
			Map<String, String> commonParams = lesson.getParams().getCommonPostParams();
			for(int i = 1; i <= 15; i ++) {
				String num = "";
				String iStr = String.valueOf(i);
				for(int j = 3 - iStr.length(); j > 0; j --) {
					num += "0";
				}
				num += i;
				commonParams.put(RatePagePostParams.TXTSKBJ, commonParams.get(RatePagePostParams.TXTKC) + "-" + num);
				String postUrl = TeachNetHttpClient.HOME_URL + "/jxkp/Stu_WSKP_pj.aspx?s=" + index + "&pjry=20&xn=" + commonParams.get(RatePagePostParams.TXTXN) + "&xq=" + commonParams.get(RatePagePostParams.TXTXQ)
				+ "&js=" + commonParams.get(RatePagePostParams.TXTJS) + "&kclx=" + commonParams.get(RatePagePostParams.TXTKCLX) + "&kc=" + commonParams.get(RatePagePostParams.TXTKC) + "&lb="
				+ commonParams.get(RatePagePostParams.TXTLB) + "&kclb=" + commonParams.get(RatePagePostParams.TXTKCLB) + "&zcflag=1";
				lesson.setPostUrl(postUrl);
				client.saveTableHtmlStr(cookies, lesson.getPostUrl(), lesson.getParams().getPostParams());
			}
		}
	}
	
	private void setPostParams(List<Cookie> cookies, RateLesson lesson, int index) throws IOException {
		RatePagePostParams params = new RatePagePostParams();
		lesson.setParams(params);
		Map<String, String> specialParams = params.getSpecialPostParams();
		Map<String, String> commonParams = params.getCommonPostParams();
		RateScore rateScore = lesson.getRateScore();
		String[] scores = rateScore.getScores();
		specialParams.put(RatePagePostParams.CJ0, scores[0]); //17.50 14 10.50 7 3.50
		specialParams.put(RatePagePostParams.CJ1, scores[1]); //10.50 8.40 6.30 4.20 2.10
		specialParams.put(RatePagePostParams.CJ2, scores[2]); //17.50 14 10.50 7 3.50
		specialParams.put(RatePagePostParams.CJ3, scores[3]); //10.50 8.40 6.30 4.20 2.10
		specialParams.put(RatePagePostParams.CJ4, scores[4]);    //14 11.20 8.40 5.60 2.80
		specialParams.put(RatePagePostParams.CQL, "0"); //same
		specialParams.put(RatePagePostParams.DJDM, rateScore.addedOrders()); //01|05|01|01|04||
		//specialParams.put(RatePagePostParams.HID_ITEM, "3487|3489|3494|3502|3503|3499|"); //same
		specialParams.put(RatePagePostParams.HID_ITEM, "3527|3529|3534|3542|3543|3539|");
		specialParams.put(RatePagePostParams.HID_PERCENTALL, "1|1|1|1|1|0.30|"); //same
		specialParams.put(RatePagePostParams.HIDQKYY, "");
		specialParams.put(RatePagePostParams.SCORE, rateScore.addedScores()); //different 17.50|2.10|17.50|10.50|5.60|95|
		specialParams.put(RatePagePostParams.SEL_SCORECJ5, scores[5]); //50-95
		specialParams.put(RatePagePostParams.TXTQTYJ, rateScore.getWords());
		String url = lesson.getUrl();
		if(!lesson.ifHasRated()) {
			String html = client.saveTableHtmlStr(cookies, url, null);
			commonParams.put(RatePagePostParams.TXT_COUNT, getCommonValue(html, RatePagePostParams.TXT_COUNT));
			commonParams.put(RatePagePostParams.TXTJS, getCommonValue(html, RatePagePostParams.TXTJS));
			commonParams.put(RatePagePostParams.TXTKC, getCommonValue(html, RatePagePostParams.TXTKC));
			commonParams.put(RatePagePostParams.TXTKCLB, getCommonValue(html, RatePagePostParams.TXTKCLB));
			commonParams.put(RatePagePostParams.TXTKCLX, getCommonValue(html, RatePagePostParams.TXTKCLX));
			commonParams.put(RatePagePostParams.TXTLB, getCommonValue(html, RatePagePostParams.TXTLB));
			commonParams.put(RatePagePostParams.TXTPJLC, getCommonValue(html, RatePagePostParams.TXTPJLC));
			commonParams.put(RatePagePostParams.TXTS, getCommonValue(html, RatePagePostParams.TXTS));
			commonParams.put(RatePagePostParams.TXTSKBJ, getCommonValue(html, RatePagePostParams.TXTSKBJ));
			commonParams.put(RatePagePostParams.TXTSKFS, getCommonValue(html, RatePagePostParams.TXTSKFS));
			commonParams.put(RatePagePostParams.TXTUSER, getCommonValue(html, RatePagePostParams.TXTUSER));
			commonParams.put(RatePagePostParams.TXTXN, getCommonValue(html, RatePagePostParams.TXTXN));
			commonParams.put(RatePagePostParams.TXTXQ, getCommonValue(html, RatePagePostParams.TXTXQ));
			commonParams.put(RatePagePostParams.TXTZJ, getCommonValue(html, RatePagePostParams.TXTZJ));
			String postUrl = TeachNetHttpClient.HOME_URL + "/jxkp/Stu_WSKP_pj.aspx?s=" + index + "&pjry=20&xn=" + commonParams.get(RatePagePostParams.TXTXN) + "&xq=" + commonParams.get(RatePagePostParams.TXTXQ)
			+ "&js=" + commonParams.get(RatePagePostParams.TXTJS) + "&kclx=" + commonParams.get(RatePagePostParams.TXTKCLX) + "&kc=" + commonParams.get(RatePagePostParams.TXTKC) + "&lb="
			+ commonParams.get(RatePagePostParams.TXTLB) + "&kclb=" + commonParams.get(RatePagePostParams.TXTKCLB) + "&zcflag=1";
			lesson.setPostUrl(postUrl);
		} else {
			rePutComParams(commonParams, url);
		}
	}
	
	private String scoreAdder(String score0, String score1, String score2, String score3, String score4, String score5) {
		return score0 + "|" + score1 + "|" + score2 + "|" + score3 + "|" + score4 + "|" + score5 + "|";
	}
	
	private void rePutComParams(Map<String, String> commonParams, String url) {
		commonParams.put(RatePagePostParams.TXT_COUNT, "6");
		commonParams.put(RatePagePostParams.TXTJS, urlSplit(url, "&jsdm="));
		commonParams.put(RatePagePostParams.TXTKC, urlSplit(url, "&kcdm="));
		commonParams.put(RatePagePostParams.TXTKCLB, urlSplit(url, "&kclb="));
		commonParams.put(RatePagePostParams.TXTKCLX, urlSplit(url, "&kclx="));
		commonParams.put(RatePagePostParams.TXTLB, urlSplit(url, "&lb="));
		commonParams.put(RatePagePostParams.TXTPJLC, urlSplit(url, "&pjlc="));
		commonParams.put(RatePagePostParams.TXTS, "1");
		//commonParams.put(RatePagePostParams.TXTSKBJ, getCommonValue(html, RatePagePostParams.TXTSKBJ));
		commonParams.put(RatePagePostParams.TXTSKFS, "");
		commonParams.put(RatePagePostParams.TXTUSER, urlSplit(url, "&xh="));
		commonParams.put(RatePagePostParams.TXTXN, urlSplit(url, "\\?xn="));
		commonParams.put(RatePagePostParams.TXTXQ, urlSplit(url, "&xq="));
		commonParams.put(RatePagePostParams.TXTZJ, "1");
	}
	
	private String urlSplit(String url, String str) {
		return url.split(str)[1].split("&")[0];
	}
	
	private String getCommonValue(String html, String key) {
		int begin = html.indexOf(key);
		int i = begin;
		while(html.charAt(i) != '>') i ++;
		i -= 2;
		int right = i;
		if(!key.equals(RatePagePostParams.TXTS)) {
			while(html.charAt(i) != '\'' && html.charAt(i) != '"') i --;
		} else {
			i ++;
			right ++;
			while(html.charAt(i) != '=') i --;
		}
		int left = i;
		return html.substring(left + 1, right + 1);
	}
}
