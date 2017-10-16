package service;

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
import org.springframework.stereotype.Component;
import controller.NoNameException;
import dao.StudentDAO;
import httpClient.DealHtmlStrException;
import httpClient.GetNameStr;
import httpClient.TeachNetHttpClient;

@Component
public class FirstLogin {
	
	@Autowired
	private TeachNetHttpClient client;
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private GetNameStr nameStr;
	
	@Autowired
    @Qualifier("org.springframework.security.authenticationManager")
    private AuthenticationManager authenticationManager;


	public void loginPost(String username, String password, HttpServletRequest request) throws IOException, NoNameException, DealHtmlStrException {
		List<Cookie> cookies = client.loginToTeachNet(username, password, TeachNetHttpClient.LOGIN_URL, client.getLoginPostParams(username, password), TeachNetHttpClient.CHARSET);
		request.getSession().setAttribute("cookies", cookies);
		String name = nameStr.getNameStr(cookies);
		if(name.equals("")) throw new NoNameException();
		request.getSession().setAttribute("name", name);
		studentDAO.saveStudent(username, "");
		securityCheckToLogin(username, "", request);
	}
	
	private void securityCheckToLogin(String username, String password, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
	    token.setDetails(new WebAuthenticationDetails(request));
	    Authentication authenticatedUser = authenticationManager.authenticate(token);
	    SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}
}
