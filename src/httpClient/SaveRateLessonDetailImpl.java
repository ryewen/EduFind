package httpClient;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import httpClient.post.param.object.RateBeginPagePostParams;
import teachnet.info.object.InfoObject;
import teachnet.info.object.lesson.Lesson;
import teachnet.info.object.rateLesson.RateLesson;
import teachnet.info.object.rateLesson.RateScore;

@Component
public class SaveRateLessonDetailImpl implements SaveInfoDetail {

	@Autowired
	private TeachNetHttpClient httpClient;
	
	@Autowired
	@Qualifier("saveRateLessonHtmlImpl")
	private SaveHtmlStr saveHtmlStr;
	
	@Autowired
	private RateBeginPagePostParams rateBeginPagePostParams;
	
	@Override
	public List<InfoObject> getLessonsAndDetails(List<Cookie> cookies, String tableUrl,
			Map<String, String> queryPostParams) throws IOException, DealHtmlStrException {
		List<InfoObject> objects = new LinkedList<InfoObject>();
		String html = httpClient.saveTableHtmlStr(cookies, RateBeginPagePostParams.RATE_BEGIN_PAGE_URL, rateBeginPagePostParams.getPostParams(cookies));
		List<List<String>> strsList = saveHtmlStr.saveHtmlIntoLists(html);
		List<String> urls = getUrls(html);
		Iterator<List<String>> it1 = strsList.iterator();
		Iterator<String> urlIt = urls.iterator();
		while(it1.hasNext()) {
			Iterator<String> it2 = it1.next().iterator();
			int i = 0;
			RateLesson lesson = null;
			while(it2.hasNext()) {
				if(i == 0) {
					lesson = new RateLesson();
					it2.next();
				}
				if(i == 1) lesson.setName(it2.next());
				if(i == 2) lesson.setTeacherName(it2.next());
				if(i == 3) {
					lesson.setState(it2.next());
					lesson.initIfNeedPost();
				}
				i ++;
				if(i == 4) {
					lesson.setUrl(urlIt.next());
					if(lesson.ifHasRated()) {
						RateScore rateScore = new RateScore();
						String ratedHtml = httpClient.saveTableHtmlStr(cookies, lesson.getUrl(), null);
						String[] strs = ratedHtml.split("<font color=red>");
						String[] scores = new String[6];
						String words = null;
						for(int j = 1; j < scores.length + 1; j ++) {
							String score = strs[j].split("</font>")[0];
							if(j < scores.length) {
								if(score.indexOf(".0") != -1) {
									score = score.split("\\.")[0];
								} else {
									score += "0";
								}
								scores[j - 1] = score;
							} else {
								double dScore = Double.valueOf(score);
								dScore /= 0.3;
								scores[j - 1] = "" + (int) dScore;
							}
						}
						rateScore.setScores(scores);
						String wordsBeginSign = "<textarea style=\"OVERFLOW: auto; WIDTH: 100%;border:0px;background-color:#f4fffb;\" name='txtqtyj' id='txtqtyj' rows='15' cols='10' readonly>";
						words = strs[strs.length - 1].split(wordsBeginSign)[1].split("</textarea>")[0];
						rateScore.setWords(words);
						lesson.setRateScore(rateScore);
					}
					objects.add(lesson);
					i = 0;
				}
			}
		}
		return objects;
	}

	private List<String> getUrls(String html) {
		List<String> urls = new LinkedList<String>();
		String[] strs = html.split("MM_openBrWindow\\('");
		for(int i = 1; i < strs.length; i ++) {
			urls.add(TeachNetHttpClient.HOME_URL + "/jxkp/" + strs[i].split("'")[0]);
		}
		return urls;
	}
}
