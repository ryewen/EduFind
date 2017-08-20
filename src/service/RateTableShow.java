package service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import httpClient.DealHtmlStrException;
import httpClient.SaveInfoDetail;
import httpClient.post.param.object.RateBeginPagePostParams;
import teachnet.info.object.InfoObject;
import teachnet.info.object.rateLesson.RateLesson;
import teachnet.info.object.rateLesson.RateScore;

@Component
public class RateTableShow {
	
	@Autowired
	@Qualifier("saveRateLessonDetailImpl")
	private SaveInfoDetail rateLessonImpl;
	
	@Autowired
	private RateBeginPagePostParams rateBeginPagePostParams;

	public void showRateTable(Model model, HttpServletRequest request) throws IOException, DealHtmlStrException {
		String[] scoreNames = {"教学方法", "课程考核", "学习收获", "教学设计与内容", "课程管理"};
		String[] scoreLevelNames = {"非常满意", "满意", "基本满意", "不满意", "非常不满意"};
		Map<String, String> scoreLevels = new HashMap<String, String>();
		for(int i = 0; i < 5; i ++) {
			if(i == 0 || i == 2) {
				putScoreLevels(scoreLevels, i, RateScore.STD_SCORES_0);
			}
			if(i == 1 || i == 3) {
				putScoreLevels(scoreLevels, i, RateScore.STD_SCORES_1);
			}
			if(i == 4) {
				putScoreLevels(scoreLevels, i, RateScore.STD_SCORES_2);
			}
		}
		List<Cookie> cookies = (List<Cookie>) request.getSession().getAttribute("cookies");
		List<InfoObject> objects = rateLessonImpl.getLessonsAndDetails(cookies,
							RateBeginPagePostParams.RATE_BEGIN_PAGE_URL, rateBeginPagePostParams.getPostParams(cookies));
		List<RateLesson> lessons = new LinkedList<RateLesson>();
		Iterator<InfoObject> it = objects.iterator();
		while(it.hasNext()) {
			RateLesson lesson = (RateLesson) it.next();
			lessons.add(lesson);
		}
		model.addAttribute("scoreNames", scoreNames);
		model.addAttribute("scoreLevelNames", scoreLevelNames);
		model.addAttribute("scoreLevels", scoreLevels);
		request.getSession().setAttribute("lessons", lessons);
	}
	
	private void putScoreLevels(Map<String, String> scoreLevels, int i, String[] std_levels) {
		for(int j = 0; j < 5; j ++) {
			scoreLevels.put("" + i + "_" + j, std_levels[j]);
		}
	}
}
