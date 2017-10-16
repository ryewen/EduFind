package dao;

import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import httpClient.DealHtmlStrException;

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
