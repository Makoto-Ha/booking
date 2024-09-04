<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Booking</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/styles.css">
</head>
<body>
	<div class="container">
		<%@include file="sidebar.jsp" %>
		<div class="main-content">
			<%@include file="navbar.jsp" %>
			<%@include file="main-list.jsp" %>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/adminsystem/index.js"></script>
</body>
</html>