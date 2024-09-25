<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改員工資料</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/admin/admin.css">
</head>
<body>
	<div class="container">
		<%@include file="../sidebar.jsp"%>
		<div class="main-content">
			<%@include file="../navbar.jsp"%>
			<form class="form" action="${pageContext.request.contextPath}/admin/update" method="post">
		        <%-- <div class="form-group">
		            <label for="adminId">管理員編號</label>
		            <input type="text" id="adminId" name="admin-id" placeholder="輸入管理員編號" value="${admin.getAdminId()}">
		        </div> --%>
		
		        <div class="form-group">
		            <label for="adminAccount">管理員帳號</label>
		            <input type="text" id="adminAccount" name="adminAccount" placeholder="輸入管理員帳號" value="${admin.getAdminAccount()}">
		        </div>
		
		        <div class="form-group">
		            <label for="adminPassword">管理員密碼</label>
		            <input type="password" id="adminPassword" name="adminPassword" placeholder="輸入管理員密碼" value="">
		        </div>
		
		        <div class="form-group">
		            <label for="adminName">管理員姓名</label>
		            <input type="text" id="adminName" name="adminName" placeholder="輸入管理員姓名" value="${admin.getAdminName()}">
		        </div>
		
		        <div class="form-group">
		            <label for="adminMail"> 管理員信箱</label>
		            <input type="email" id="adminMail" name="adminMail" placeholder="輸入管理員信箱" value="${admin.getAdminMail()}">
		        </div>
		
		        <div class="form-group">
		            <label for="hiredate">到職日期</label>
		            <input type="date" id="hiredate" name="hiredate" placeholder="選擇日期" value="${admin.getHiredate()}">
		        </div>
		
		        <div class="form-group">
		            <button type="submit" style="background-color: #007bff;">保存</button>
		            <button type="button" class="cancel">取消</button>
		        </div> 
		    </form>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/adminsystem/admin/admin.js"></script>
</body>
</html>

