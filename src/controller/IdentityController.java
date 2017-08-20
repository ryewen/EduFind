package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dao.StudentDAO;
import httpClient.DealHtmlStrException;
import httpClient.GetNameStr;
import httpClient.TeachNetHttpClient;
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
