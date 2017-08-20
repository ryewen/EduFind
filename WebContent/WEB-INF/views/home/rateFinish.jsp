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
	<form id="calendarForm">
	<div id="tableDiv">
	<span id="title">评教</span>
	<table id="finishTable"><tr><td><a href="<c:url value="/home/rate" />"><img id="okImg" src='<c:url value="/resources/img/ok.png" />' /></a></td><td><a id="return" href="<c:url value="/home/rate" />">评教完成，点击查看结果</a></td></tr></table>
	</div>
	</form>
	</div>
	<script type="text/javascript" src='<c:url value="/resources/js/login/highlight.js" />' ></script>
</body>
</html>