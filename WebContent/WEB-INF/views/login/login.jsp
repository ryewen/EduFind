<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Ballade</title>
</head>
<body>
	<div id="right">
		<s:url value="/identity/login" var="authUrl"></s:url>
		<form method="post" action="${authUrl }" id="loginForm" class="rightIndex">
			<table id="loginTable">
				<tr><td>Welcome to Ballade</td></tr>
				<tr><td>教务查询</td></tr>
				<tr><td colspan="2"><input name="username" type="text" placeholder="学号" class="loginInput"></td></tr>
				<tr><td colspan="2"><input name="password" type="password" placeholder="密码" class="loginInput"></td></tr>
				<tr>
					<td><input id="rememberMe" name="_spring_security_remember_me" type="checkbox" />
					<label for="rememberMe" id="rememberMe">记住我</label></td>
				</tr>
				<tr>
					<td colspan="2"><input id="loginSubmit" type="submit" value="登录" class="loginInput"/></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>