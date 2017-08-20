package dao;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import controller.HomeController;
import httpClient.DealHtmlStrException;
import httpClient.GetNameStr;
import httpClient.SaveHtmlStr;
import httpClient.SaveInfoDetail;
import httpClient.TeachNetHttpClient;
import httpClient.post.param.object.LessonPagePostParams;
import httpClient.post.param.object.PagePostParams;
import httpClient.post.param.object.ScorePagePostParams;
import teachnet.info.object.InfoObject;
import teachnet.info.object.lesson.Lesson;
import teachnet.info.object.lesson.LessonDetailConverter;
import teachnet.info.object.score.Score;

public class Test {

	public static void main(String[] args) throws DealHtmlStrException, IOException {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("ballade-beans.xml");
		StudentDAO dao = (StudentDAO) context.getBean("studentDAOImpl");
		for(int i = 0; i < 20; i ++) {
			dao.saveStudent("" + i, "" + i);
		}
	}

}
