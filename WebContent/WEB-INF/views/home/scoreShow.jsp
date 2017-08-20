<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="left">
	<form id="calendarForm" action="<c:url value="/home/score" />" method="get">
	<div id="tableDiv">
	<span id="title">成绩查询</span>
	<label for="year" class="scoreLabel">学年:</label>
	<select name="year" id="year">
	<c:forEach var="year" items="${years }">
		<c:if test="${year == selectedYear }">
		<option value="${fn:substring(year, 0, 4) }" selected="selected">${year }</option>
		</c:if>
		<c:if test='${year != selectedYear }'>
		<option value="${fn:substring(year, 0, 4) }">${year }</option>
		</c:if>
	</c:forEach>
	</select>
	<label for="term" class="scoreLabel">第</label>
	<select name="term" id="term">
	<option value="1" <c:if test="${selectedShortTerm == 0 }">selected="selected"</c:if>>1</option>
	<option value="2" <c:if test="${selectedShortTerm == 1 }">selected="selected"</c:if>>2</option>
	</select><label for="term">学期</label>
	<label for="term" class="scoreLabel"> </label><input type="submit" value="查询" />
	<div id="showScore">
	<div><span>${selectedYear }学年</span><span class="scoreLabel">${selectedTerm }</span></div>
	<table id="calendarTable">
		<thead>
		<tr><th>课程</th><th>学分</th><th>成绩</th></tr>
		</thead>
		<tbody>
			<c:forEach var="score" items="${scores }">
			<tr class="scoreTr"><td>${score.shortName }</td><td class="scoreTd">${score.credit }</td><td class="scoreTd">${score.score }</td></tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</div>
	</form>
	</div>
	<script type="text/javascript" src='<c:url value="/resources/js/login/highlight.js" />' ></script>
</body>
</html>