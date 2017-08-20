package service;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import httpClient.DealHtmlStrException;
import httpClient.SaveInfoDetail;
import httpClient.post.param.object.ScorePagePostParams;
import service.exception.ParamException;
import teachnet.info.object.InfoObject;
import teachnet.info.object.score.Score;

@Component
public class ScoreShow {

	@Autowired
	@Qualifier("saveScoreDetailImpl")
	private SaveInfoDetail scoreImpl;
	
	public void showScore(int year, int term, Model model, HttpServletRequest request) throws IOException, DealHtmlStrException, ParamException {
		if(term < 1 || term > 2) throw new ParamException("学期必须是1或2");
		term --;
		model.addAttribute("years", YearWeekGetter.getStudyYears());
		int nowYear = YearWeekGetter.getNowYear();
		model.addAttribute("nowYear", String.valueOf(nowYear) + "-" + String.valueOf(++ nowYear));
		String termStr = "";
		if(term == 0) termStr = "第一学期";
		if(term == 1) termStr = "第二学期";
		model.addAttribute("selectedTerm", termStr);
		model.addAttribute("selectedYear", String.valueOf(year) + "-" + String.valueOf(year + 1));
		model.addAttribute("selectedShortTerm", term);
		List<Score> scores = new LinkedList<Score>();
		List<Cookie> cookies = (List<Cookie>) request.getSession().getAttribute("cookies");
		List<InfoObject> objects = scoreImpl.getLessonsAndDetails(cookies, ScorePagePostParams.SCORE_PAGE_URL, new ScorePagePostParams(false, year, term).getPostParams());
		Iterator<InfoObject> it = objects.iterator();
		while(it.hasNext()) {
			Score score = (Score) it.next();
			score.setShortName(score.convertName());
			scores.add(score);
		}
		model.addAttribute("scores", scores);
	}
}
