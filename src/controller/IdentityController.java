package controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import httpClient.DealHtmlStrException;
import service.FirstLogin;

@Controller
@RequestMapping("/identity")
public class IdentityController {
	
	@Autowired
	private FirstLogin firstLogin;

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String dealLoginPost(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpServletRequest request) {
		try {
			firstLogin.loginPost(username, password, request);
		} catch (IOException e) {
			model.addAttribute("error", "网络好像出了些问题");
			return "login";
		} catch(NoNameException e) {
			model.addAttribute("error", "账号密码错误");
			return "login";
		} catch(DealHtmlStrException e) {
			model.addAttribute("error", e.getMessage("姓名"));
		}
		return "redirect:/home";
	}
	
}
