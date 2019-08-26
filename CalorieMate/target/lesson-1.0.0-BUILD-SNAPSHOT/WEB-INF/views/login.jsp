<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF 8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
</head>
<body>
	<h2>${message}</h2>

	<form:form modelAttribute="calLoginForm">
		<form:input path="userId" />
		<form:errors path="userId" cssStyle="color:red" />
		<br>
		<form:password path="password" />
		<form:errors path="password" cssStyle="color:red" />
		<br>
		<input type="submit">
	</form:form>
</body>
</html>