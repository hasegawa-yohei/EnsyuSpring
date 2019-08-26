<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>
		<c:out value="${message}" />

	</p>

	<form:form modelAttribute="calSearchForm">
		<form:input path="word" />
		<form:errors path="word" cssStyle="color:red"></form:errors>
		<form:select path="foodcate" items="${foodcate}" multiple="false" />
		<input type="submit" value="検索">
		<br />
	</form:form>

	<br />
	<c:if test="${not empty foodList}">
		<table border="1">
			<tr>
				<th>名前</th>
				<th>kcal</th>
				<th>追加</th>
			</tr>
			<c:forEach var="CalSearchInfo" items="${foodList}">
				<tr>
					<td><c:out value="${CalSearchInfo.foodname}"></c:out></td>
					<td><c:out value="${CalSearchInfo.foodcal}"></c:out></td>
					<td>
					<form:form modelAttribute="detailInfo">
					    <input type="hidden" name="foodname" value="${CalSearchInfo.foodname}">
					    <input type="hidden" name="foodcal" value="${CalSearchInfo.foodcal}">
					    <input type="submit" value="追加">
					</form:form>
				    </td>
				</tr>
			</c:forEach>
		</table>
		<br>
		
	</c:if>
	<form:form modelAttribute="calGoForm">
		 <input type="hidden" name="flg" value="1">
		<input type="submit" value="計算へ">
		</form:form>
</body>
</html>