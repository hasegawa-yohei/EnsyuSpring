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
	
	<table border="1">
			<tr>
				<th>名前</th>
				<th>kcal</th>
				<th>削除</th>	
			</tr>
			<c:forEach var="CalNameListInfo" items="${nameList}">
				<tr>
					<td><c:out value="${CalNameListInfo.name}"></c:out></td>
					<td><c:out value="${CalNameListInfo.cal}"></c:out></td>
					<td>
					<form:form modelAttribute="CalNameListInfo">
					    <input type="hidden" name="id" value="${CalNameListInfo.id}">
					    <input type="submit" value="削除">
					</form:form>
					</td>
                </tr>
             </c:forEach>
		</table>
		<br>
		合計<c:out value="${totalcal}" />kcal
		<br>
		<form:form modelAttribute="calGoForm">
		 <input type="hidden" name="flg" value="1">
		<input type="submit" value="戻る">
		</form:form>
		<form:form modelAttribute="calGoForm">
		 <input type="hidden" name="flg" value="2">
		<input type="submit" value="ログアウト">
		</form:form>
</body>

</html>