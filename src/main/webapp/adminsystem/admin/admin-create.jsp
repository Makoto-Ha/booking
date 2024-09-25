<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Admin data</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/adminsystem/styles.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/adminsystem/admin/admin.css">
</head>
<body>
	<div class="container">
		<%@include file="../sidebar.jsp"%>
		<div class="main-content">
			<%@include file="../navbar.jsp"%>
			<form class="form"
				action="${pageContext.request.contextPath}/admin/create"
				method="post">
				<div class="form-group">
					<label for="admin-account">新增員工帳號</label> <input type="text"
						id="admin-account" name="adminAccount" placeholder="輸入內容">
				</div>
				<!-- 錯誤訊息區域 -->
            <c:if test="${not empty errorMessage}">
                <span class="error-message" style="color: red; display: inline-block; margin-left: 10px; vertical-align: middle;">
                    ${errorMessage}
                </span>
            </c:if>
				<div class="form-group">
					<label for="admin-password">新增員工密碼</label> <input type="text"
						id="admin-password" name="adminPassword" placeholder="輸入內容">
				</div>

				<div class="form-group">
					<label for="admin-name">新增員工姓名</label> <input type="text"
						id="admin-name" name="adminName" placeholder="輸入內容">
				</div>

				<div class="form-group">
					<label for="admin-mail">新增員工信箱</label> <input type="text"
						id="admin-mail" name="adminMail" placeholder="輸入內容">
				</div>

				<div class="form-group">
					<label for="hiredate">新增到職日期</label> <input type="date"
						id="hiredate" name="hiredate" placeholder="選擇日期">
				</div>

				<div class="form-group">
					<label for="adminStatus">新增員工狀態</label> <select id="adminStatus"
						name="adminStatus">
						<option value="1">在職</option>
						<option value="0">離職</option>
					</select>
				</div>


				<div class="form-group">
					<button type="submit">確定</button>
					<button type="button" class="cancel">取消</button>
				</div>
			</form>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/adminsystem/booking/booking.js"></script>
</body>
</html>
