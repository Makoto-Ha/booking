<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,com.booking.bean.activity.Activity"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Activity</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/adminsystem/styles.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/adminsystem/activity/booking.css">
</head>
<body>
	<div class="container">
		<%@include file="../sidebar.jsp"%>
		<div class="main-content">
			<%@include file="../navbar.jsp"%>
			<h2>Create Activity</h2>
			<form class="form"
				action="${pageContext.request.contextPath}/activity/create"
				method="post">
				<div class="form-group">
					<label for="activity-name">活動名稱</label> <input type="text"
						id="activity-name" name="activity-name" placeholder="輸入內容"
						required>
				</div>

				<div class="form-group">
					<label for="start-date">開始日期</label> <input type="date"
						id="start-date" name="start-date" required>
				</div>

				<div class="form-group">
					<label for="deadline">截止日期</label> <input type="date" id="deadline"
						name="deadline" required>
				</div>

				<div class="form-group">
					<label for="limit-of-times">次數限制</label> <input type="number"
						id="limit-of-times" name="limit-of-times" placeholder="輸入內容"
						required>
				</div>

				<div class="form-group">
					<label for="discount-code">折扣碼</label> <input type="text"
						id="discount-code" name="discount-code" placeholder="輸入內容"
						required>
				</div>

				<div class="form-group">
					<label for="activity-detail">活動詳情</label> <input type="text"
						id="activity-detail" name="activity-detail" placeholder="輸入內容"
						required>
				</div>

				<div class="form-group">
					<button type="submit">建立活動</button>
					<button type="button" class="cancel">取消</button>
				</div>
			</form>
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/adminsystem/activity/booking.js"></script>
</body>
</html>