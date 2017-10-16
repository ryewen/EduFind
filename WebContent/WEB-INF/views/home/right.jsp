<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<s:url value="/resources/css/home.css" />" />
</head>
<body>
	<div id="right">
		<div id="homeDiv">
		<div id="homeIndex">
		<span class="headerText" id="name">Hi, <span id="myName">${name }</span></span>
		<a id="logout" class="headerText" href="<s:url value='/static/j_spring_security_logout' />">登出</a>
		<table id="funcTable">
		<c:if test="${nowWeek > 0 }"><c:set var="week" value="${nowWeek }" /></c:if>
		<c:if test="${nowWeek <= 0 }"><c:set var="week" value="${1 }" /></c:if>
		<tr><td><div class="function" id="funcCalendar"><a class="funcA" href="<s:url value='/home/calendar?week=${week }' />">查询课表</a></div></td></tr>
		<tr><td><div class="function" id="funcScore"><a class="funcA" href="<s:url value='/home/score/select' />">查询成绩</a></div></td></tr>
		<tr><td><div class="function" id="funcRate"><a class="funcA" href="<s:url value='/home/rate' />">快速评教</a></div></td></tr>
		</table>
		</div>
		</div>
	</div>
</body>
</html>