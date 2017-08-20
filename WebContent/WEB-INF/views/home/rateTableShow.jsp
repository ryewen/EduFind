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
	<form id="calendarForm" action="<c:url value="/home/rate/send" />" method="post">
	<div id="tableDiv">
	<span id="title">评教</span><span class="calendarInfo"><input type="button" id="submit" value="提交" /></span>
	<table id="calendarTable">
		<tbody>
			<c:set var="count" value="0"></c:set>
			<c:forEach var="lesson" items="${lessons }">
				<tr>
					<td>
						<p>
						<span>${lesson.name }</span><span class="calendarInfo">${lesson.teacherName }</span>
						<span class="calendarInfo">状态:<c:if test="${lesson.state == '查看'}">已评</c:if><c:if test="${lesson.state == '未评'}">${lesson.state }</c:if></span>
						</p>
						<c:forEach var="i" begin="0" end="4">
							<span>
								<label for="score${count }_${i }">${scoreNames[i] }</label>
								<select name="score${count }_${i }" id="score${count }_${i }">
									<c:forEach var="j" begin="0" end="4">
										<c:set var="str" value="${i }_${j }"></c:set>
										<c:if test="${lesson.rateScore.scores[i] == scoreLevels[str]}">
											<option value="${scoreLevels[str] }" selected="selected">${scoreLevelNames[j] }</option>
										</c:if>
										<c:if test="${lesson.rateScore.scores[i] != scoreLevels[str]}">
											<option value="${scoreLevels[str] }">${scoreLevelNames[j] }</option>
										</c:if>
									</c:forEach>
								</select>
							</span>
						</c:forEach><br />
						<table>
						<tr>
						<td><label for="score${count }_5">总体印象评价(50-95分)</label></td><td><input type="text" name="score${count }_5" id="score${count }_5" value="${lesson.rateScore.scores[5] }"/></td>
						<td><label for="words${count }">建议，评语</label></td><td><textarea name="words${count }" id="words${count }" rows="2" cols="38">${lesson.rateScore.words }</textarea></td>
						</tr>
						</table>
						<c:set var="count" value="${count + 1}"></c:set>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</form>
	</div>
	<script type="text/javascript" src='<c:url value="/resources/js/login/highlight.js" />' ></script>
	<script type="text/javascript" src='<c:url value="/resources/js/login/rateAjax.js" />' ></script>
</body>
</html>