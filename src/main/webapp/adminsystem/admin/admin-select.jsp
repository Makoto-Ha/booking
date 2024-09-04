<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>查詢管理員資料</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/admin/admin.css">
</head>
<body>
	<div class="container">
		<%@include file="../sidebar.jsp"%>
		<div class="main-content">
			<%@include file="../navbar.jsp"%>
			<form class="form" action="${pageContext.request.contextPath}/admin/select" method="post">
		        <div class="form-group">
		            <label for="adminId">管理員編號</label>
		            <input type="text" id="adminId" name="admin-id" placeholder="輸入編號" value="${admin.getAdminId()}">
		        </div>
		
		        <div class="form-group">
		            <label for="adminAccount">管理員帳號</label>
		            <input type="text" id="adminAccount" name="admin-account" placeholder="輸入帳號" value="${admin.getAdminAccount()}">
		        </div>
		
		        <div class="form-group">
		            <label for="adminName">管理員姓名</label>
		            <input type="text" id="adminName" name="admin-name" placeholder="輸入姓名" value="${admin.getAdminName()}">
		        </div>
		
		        <div class="form-group">
		            <label for="adminMail">管理員信箱</label>
		            <input type="email" id="adminMail" name="admin-mail" placeholder="輸入信箱" value="${admin.getAdminMail()}">
		        </div>
		
		        <div class="form-group">
		            <label for="hiredate">到職日</label>
		            <input type="date" id="hiredate" name="hiredate" placeholder="選擇日期" value="${admin.getHiredate()}">
		        </div> 
		
		        <div class="form-group">
		            <label for="adminStatus">管理員狀態</label>
		            <select id="adminStatus" name="admin-status">
		                <option value="1" ${admin.getAdminStatus() == 1 ? 'selected' : ''}>在職</option>
		                <option value="0" ${admin.getAdminStatus() == 0 ? 'selected' : ''}>離職</option>
		            </select>
		        </div>
		
		        <div class="form-group">
		            <button type="submit" style="background-color: green;">查詢</button>
		            <button type="button" class="cancel">取消</button>
		        </div>
		    </form>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/adminsystem/admin/admin.js"></script>
</body>
</html>

