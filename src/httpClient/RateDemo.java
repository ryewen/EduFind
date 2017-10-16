package httpClient;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.cookie.Cookie;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import httpClient.post.param.object.RateBeginPagePostParams;
import teachnet.info.object.InfoObject;
import teachnet.info.object.rateLesson.RateLesson;
import teachnet.info.object.rateLesson.RateScore;

public class RateDemo {

	public static void main(String[] args) throws ClientProtocolException, IOException, DealHtmlStrException {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("ballade-beans.xml");
		TeachNetHttpClient client = (TeachNetHttpClient) context.getBean("teachNetHttpClient");
		String username = "20143939";
		String password = "a19960712";
		List<Cookie> cookies = client.loginToTeachNet(username, password, TeachNetHttpClient.LOGIN_URL, client.getLoginPostParams(username, password), TeachNetHttpClient.CHARSET);
		SaveInfoDetail saveInfoDetail = (SaveInfoDetail) context.getBean("saveRateLessonDetailImpl");
		List<InfoObject> objects = saveInfoDetail.getLessonsAndDetails(cookies, RateBeginPagePostParams.RATE_BEGIN_PAGE_URL, new RateBeginPagePostParams().getPostParams(cookies));
		PostRateLesson post = (PostRateLesson) context.getBean("postRateLesson");
		Iterator<InfoObject> it = objects.iterator();
		List<RateLesson> lessons = new LinkedList<RateLesson>();
		int i = 0;
		while(it.hasNext()) {
			RateLesson lesson = (RateLesson) it.next();
			if(true) {
				String[] scores = {RateScore.STD_SCORES_0[0], RateScore.STD_SCORES_1[0], RateScore.STD_SCORES_0[0], RateScore.STD_SCORES_1[0], RateScore.STD_SCORES_2[0], "95"};
				RateScore rateScore = new RateScore(scores, "good teacher");
				lesson.changeRateScore(rateScore);
				lessons.add(lesson);
			}
			i ++;
		}
		post.post(cookies, lessons);
	}

}
