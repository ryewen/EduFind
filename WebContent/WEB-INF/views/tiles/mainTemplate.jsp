<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><tiles:insertAttribute name="title" ignore="true" /></title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/template.css" />" />
</head>
<body>
	<div id="container">
	<tiles:insertAttribute name="right" />
	<tiles:insertAttribute name="left" />
	</div>
	<c:if test="${error != null }"><span id="error">${error }</span></c:if>
	<tiles:insertAttribute name="scripts" />
	<script type="text/javascript" src='<s:url value="/resources/js/login/imgBlur.js" />' ></script>
</body>
</html>