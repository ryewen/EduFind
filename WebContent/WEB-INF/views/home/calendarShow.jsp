<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="left">
	<form id="calendarForm" action="<s:url value='/home/calendar' />" method="get">
	<div id="tableDiv">
		<div>
		<span id="title">课表信息</span><span class="calendarInfo">${studyYear }</span><span class="calendarInfo">${studyTerm }</span>
		<span class="calendarInfo">第
		<select name="week">
		<c:forEach var="i" begin="1" end="20">
			<c:if test="${i == selectedWeek }">
				<option value="${i }" selected="selected">${i }<c:if test="${i == nowWeek }">(本周)</c:if></option>
			</c:if>
			<c:if test="${i != selectedWeek }">
				<option value="${i }">${i }<c:if test="${i == nowWeek }">(本周)</c:if></option>
			</c:if>
		</c:forEach>
		</select>周</span>
		<span class="title"><a id="exportA" href="<c:url value="/home/calendar/download" />">导出为谷歌日历</a></span>
		</div>
		<div>
		<table id="calendarTable">
			<thead>
				<tr>
				<th></th>
				<c:forEach var="i" begin="0" end="6">
				<th>
				<c:if test="${dates[i] == nowDay }">
				<div class="nowDay">星期${chiNums[i] }<br />${dates[i] }</div>
				</c:if>
				<c:if test="${dates[i] != nowDay }">
				星期${chiNums[i] }<br />${dates[i] }
				</c:if>
				</th>
				</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="i" begin="0" end="4">
					<tr>
						<th class="partNum">第${chiNums[i] }大节</th>
						<c:forEach var="j" begin="0" end="6">
						<c:set var="rowspanStr" value="${i } ${j } rowspan"></c:set>
						<c:set var="upRowspanStr" value="${i - 1 } ${j } rowspan"></c:set>
						<c:if test="${classInfoMap[upRowspanStr] != '2' }">
							<td rowspan="${classInfoMap[rowspanStr] }">
								<c:set var="maxZStr" value="${i } ${j } maxZ"></c:set>
								<c:forEach var="z" begin="0" end="${classInfoMap[maxZStr] }">
									<c:set var="str" value="${i } ${j } ${z }"></c:set>
										<div class="lessonDiv">
											<c:if test="${lessons[str] != null}">
											<c:if test="${lessons[str].ifToBeDone == true }"><div class="toBeDoneLesson"></c:if>
											${lessons[str].shortName }<br />
											<span class="lessonTime">${lessons[str].startTime }-${lessons[str].endTime }</span><br />
											${lessons[str].location }<br />
											<c:if test="${lessons[str].ifToBeDone == true }"></div></c:if>
											</c:if>
										</div>
								</c:forEach>
							</td>
						</c:if>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	</div>
	</form>
	</div>
	<script type="text/javascript" src="<c:url value="/resources/js/calendar/autoSelect.js" />"></script>
</body>
</html>