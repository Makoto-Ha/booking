<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>create</title>
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
			<form class="form" action="update">
				<div class="form-group">
					<label for="name">活動名稱</label> <input type="text" id="name"
						name="activity-name" placeholder="輸入內容"
						value="${activity.getActivityName()}">
				</div>

				<div class="form-group">
					<label for="startdate">活動開始日期</label> <input type="date"
						id="startdate" name="start-date" placeholder="輸入內容"
						value="${activity.getStartDate()}">
				</div>

				<div class="form-group">
					<label for="deadline">活動截止日期</label> <input type="date"
						id="deadline" name="deadline" placeholder="輸入內容"
						value="${activity.getDeadline()}">
				</div>

				<div class="form-group">
					<label for="limit">使用次數限制</label> <input type="number" id="limit"
						name="limit-of-times" placeholder="輸入內容"
						value="${activity.getLimitOfTimes()}">
				</div>

				<div class="form-group">
					<label for="discount">折扣碼</label> <input type="text" id="discount"
						name="discount-code" placeholder="輸入內容"
						value="${activity.getDiscountCode()}">
				</div>

				<div class="form-group">
					<label for="detail">活動詳情</label> <input type="text" id="detail"
						name="activity-detail" placeholder="輸入內容"
						value="${activity.getActivityDetail()}">
				</div>
				<div class="form-group">
					<button type="submit" style="background-color: #007bff;">保存</button>
					<button type="button" class="cancel">取消</button>
				</div>
			</form>
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/adminsystem/activity/taiwan_districts.js"></script>
	<script
		src="${pageContext.request.contextPath}/adminsystem/activity/booking.js"></script>

</body>
</html>