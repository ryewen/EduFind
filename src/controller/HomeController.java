package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.SaveExcelDAO;
import httpClient.DealHtmlStrException;
import httpClient.SaveInfoDetail;
import httpClient.post.param.object.LessonPagePostParams;
import service.CalendarShow;
import service.RateTableShow;
import service.ScoreShow;
import service.SendRateDetails;
import service.YearWeekGetter;
import service.exception.ParamException;
import teachnet.info.object.InfoObject;

@Controller
public class HomeController {
	
	@Autowired
	@Qualifier("saveLessonDetailImpl")
	private SaveInfoDetail lessonImpl;
	
	@Autowired
	private ScoreShow scoreShow;
	
	@Autowired
	private CalendarShow calendarShow;
	
	@Autowired
	private RateTableShow rateTableShow;
	
	@Autowired
	private SendRateDetails sendRateDetails;
	
	@Autowired
	private SaveExcelDAO excelDAO;
	
	public static final int STUDY_YEAR = 2017;
	
	private static final int BEGIN_YEAR = 2017;
	
	public static final int TERM = 0; //0-第一学期 1-第二学期
	
	private static final int BEGIN_MONTH = 9;
	
	private static final int BEGIN_DAY = 4;
	
	@RequestMapping("/")
	public String defaultIndex(Model model, HttpServletRequest request) {
		return showHomePage(model, request);
	}
	
	@RequestMapping("/home")
	public String showHomePage(Model model, HttpServletRequest request) {
		request.getSession().setAttribute("nowWeek", YearWeekGetter.getNowWeek(BEGIN_YEAR, BEGIN_MONTH, BEGIN_DAY));
		System.out.println();
		return "home";
	}
	
	@RequestMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	
	@RequestMapping(value="/home/calendar", method=RequestMethod.GET)
	public String calendarSelect(@RequestParam("week") int week, Model model, HttpServletRequest request) {
		try {
			calendarShow.showCalendar(week, model, request, STUDY_YEAR, BEGIN_YEAR, BEGIN_MONTH, BEGIN_DAY, TERM);
		} catch (IOException e) {
			model.addAttribute("error", "网络好像出了些问题");
			return "home";
		} catch (DealHtmlStrException e) {
			model.addAttribute("error", e.getMessage("课表"));
			return "home";
		} catch (ParamException e) {
			// TODO Auto-generated catch block
			model.addAttribute("error", e.toString());
			return "home";
		}
		return "calendar/show";
	}
	
	@RequestMapping("/home/calendar/download")
	public String downloadCalendar(HttpServletRequest request, HttpServletResponse response, Model model) {
		response.setContentType("application/x-msdownload;");
        String username = YearWeekGetter.getUsername();
        response.setHeader("Content-disposition", "attachment; filename="
                 + "lessonTable_" + username + ".csv");
		try {
			List<Cookie> cookies = (List<Cookie>) request.getSession().getAttribute("cookies");
			List<InfoObject> lessons = lessonImpl.getLessonsAndDetails(cookies, LessonPagePostParams.LESSON_PAGE_URL, new LessonPagePostParams(STUDY_YEAR, TERM).getPostParams());
			excelDAO.saveIntoExcel(lessons, response.getOutputStream(), BEGIN_YEAR, BEGIN_MONTH, BEGIN_DAY);
		} catch (IOException e) {
			model.addAttribute("error", "网络好像出了些问题");
			return "home";
		} catch (DealHtmlStrException e) {
			model.addAttribute("error", e.getMessage("课表"));
			return "home";
		}
		return null;
	}
	
	@RequestMapping("/home/score/select")
	public String studyYearTermSelect(Model model, @RequestParam(value = "error", required = false) String error) {
		model.addAttribute("years", YearWeekGetter.getStudyYears());
		int nowYear = YearWeekGetter.getNowYear();
		model.addAttribute("nowYear", String.valueOf(nowYear) + "-" + String.valueOf(++ nowYear));
		model.addAttribute("error", error);
		return "score/select";
	}
	
	@RequestMapping(value="/home/score", method=RequestMethod.GET)
	public String showScore(@RequestParam("year") int year, @RequestParam("term") int term, Model model, HttpServletRequest request) {
		try {
			scoreShow.showScore(year, term, model, request);
		} catch (IOException e) {
			model.addAttribute("error", "网络好像出了些问题");
			return "redirect:/home/score/select";
		} catch (DealHtmlStrException e) {
			model.addAttribute("error", e.getMessage("成绩"));
			return "redirect:/home/score/select";
		} catch (ParamException e) {
			// TODO Auto-generated catch block
			model.addAttribute("error", e.toString());
			return "home";
		}
		return "score/show";
	}

	@RequestMapping(value="/home/rate", method=RequestMethod.GET)
	public String showRateTable(Model model, HttpServletRequest request) {
		try {
			rateTableShow.showRateTable(model, request);
		} catch (IOException e) {
			model.addAttribute("error", "网络好像出了些问题");
			return "home";
		} catch (DealHtmlStrException e) {
			model.addAttribute("error", e.getMessage("评教"));
			return "home";
		}
		return "rate";
	}
	
	@RequestMapping(value="/home/rate/send", method=RequestMethod.GET)
	@ResponseBody
	public void sendRateDetails(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			sendRateDetails.send(request);
		} catch (IOException e) {
			request.getSession().setAttribute("error", "网络好像出了些问题");
			try {
				request.getRequestDispatcher("/home/rate").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (NumberFormatException e) {
			request.getSession().setAttribute("error", "评教输入的分数必须是数字");
			try {
				request.getRequestDispatcher("/home/rate").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
