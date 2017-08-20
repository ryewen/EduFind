package service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import httpClient.PostRateLesson;
import teachnet.info.object.rateLesson.RateLesson;
import teachnet.info.object.rateLesson.RateScore;

@Component
public class SendRateDetails {

	@Autowired
	private PostRateLesson post;
	
	public void send(HttpServletRequest request) throws IOException, NumberFormatException {
		List<RateLesson> lessons = (List<RateLesson>) request.getSession().getAttribute("lessons");
		Iterator<RateLesson> it = lessons.iterator();
		int i = 0;
		while(it.hasNext()) {
			String[] scores = new String[6];
			for(int j = 0; j < 6; j ++) {
				scores[j] = request.getParameter("score" + i + "_" + j);
				Double.valueOf(scores[j]);
			}
			String words = request.getParameter("words" + i);
			RateScore rateScore = new RateScore(scores, words);
			it.next().changeRateScore(rateScore);
			i ++;
		}
		post.post((List<Cookie>) request.getSession().getAttribute("cookies"), lessons);
	}
}
