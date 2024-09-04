<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>登入系統</title>
<style>
* {
	text-decoration: none;
}

body {
	font-family: Arial, sans-serif;
	background-size: 100%;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	color: #333;
	background-color: #f2edf3;
}

.login-container {
	background: white;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	width: 320px;
	text-align: center;
}

.logo {
	background-color: #D0D0D0;
	border-radius: 10px;
}

.login-container img {
	height: 80px;
}

.login-container h2 {
	margin-bottom: 20px;
	font-size: 24px;
	font-weight: bold;
	background: linear-gradient(to right, #da8cff, #9a55ff);
	-webkit-background-clip: text; /* 只顯示背景漸變在文字上 */
	-webkit-text-fill-color: transparent; /* 將文字顏色設為透明，讓背景顯示出來 */
}

.login-title {
	width: 320px;
	display: flex;
	align-items: center;
}

.input-group {
	margin-bottom: 20px;
	text-align: left;
}

.input-group label {
	display: block;
	font-weight: bold;
	margin-bottom: 5px;
	font-size: 14px;
}

.input-group input {
	width: 100%;
	padding: 10px;
	border: 1px solid #cccccc94;
	border-radius: 5px;
	font-size: 16px;
	box-sizing: border-box;
	outline-color: #007BFF;
	padding-left: 14px;
}

.login-button {
	width: 100%;
	padding: 10px;
	background: linear-gradient(to right, #da8cff, #9a55ff);
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
	margin-top: 10px;
	transition: all .2s;
}

.login-button:hover {
	opacity: .8;
}

.link-group {
	margin-top: 15px;
	font-size: 14px;
}

.link-group a {
	color: #009688;
	text-decoration: none;
}

.select-group {
	margin-bottom: 20px;
	text-align: left;
}

.error-message {
	color: red;
}

.note {
	font-size: 12px;
	color: gray;
	margin-top: 5px;
}

.go-register {
	width: calc(100% -20px);
	display: block;
	padding: 10px;
	background-color: #ffc5e1;
	color: white;
	border-radius: 4px;
	font-size: 15px;
	margin-top: 10px;
	transition: all .2s;
}

.go-register:hover {
	opacity: .8;
}
</style>
</head>
<body>
	<div class="login-container">
		<div class="login-title">
			<img class="img"
				src="${pageContext.request.contextPath}/adminsystem/images/03.jpg"
				alt="Logo">
			<h2>訂房後台管理系統</h2>
		</div>
		<form action="/booking/admin/login" method="post">
			<div class="input-group">
				<label for="account"></label> <input type="text" id="account"
					name="admin-account" required placeholder="帳號"> <span
					id="account-error" class="error-message"></span>
			</div>
			<div class="input-group">
				<label for="password"></label> <input type="password" id="password"
					name="admin-password" required placeholder="密碼"> <span
					id="password-error" class="error-message"></span>
			</div>
			<!-- 成功訊息區域 -->
			<c:if test="${not empty successMessage}">
				<div class="success-message"
					style="color: green; text-align: center;">
					<i class="fa-regular fa-thumbs-up"></i>${successMessage}
				</div>
			</c:if>

			<!-- 錯誤訊息區域 -->
			<c:if test="${not empty errorMessage}">
				<div class="error-message" style="color: red; text-align: center;">
					<i class='fa-solid fa-triangle-exclamation'></i>${errorMessage}
				</div>
			</c:if>
			<button type="submit" class="login-button">登錄</button>
			<div class="register"
				style="text-align: center; margin-bottom: 10px;">
				<a
					href="${pageContext.request.contextPath}/adminsystem/admin/register.jsp"
					class="go-register">我要註冊</a>
			</div>
		</form>
	</div>
	<script src="https://kit.fontawesome.com/cc1b58587a.js" crossorigin="anonymous"></script>
	<script>
		document.getElementById("account").addEventListener("blur", checkAccount);

		function checkAccount() {
			let theAccountObj = document.getElementById("account");
			let theAccountObjVal = theAccountObj.value.trim();
			let theAccountObjValLen = theAccountObjVal.length;
			let sp = document.getElementById("account-error");

			if (theAccountObjVal === "") {
				sp.innerHTML = "<i class='fa-solid fa-triangle-exclamation'></i> 帳號不可空白";
			} else {
				sp.innerHTML = ""; // 清除可能的錯誤訊息
			}
		}

		document.getElementById("password").addEventListener("blur", checkPassword);

		function checkPassword() {
			let thePwdObj = document.getElementById("password");
			let thePwdObjVal = thePwdObj.value.trim();
			let thePwdObjValLen = thePwdObjVal.length;
			let spp = document.getElementById("password-error");

			if (thePwdObjVal === "") {
				spp.innerHTML = "<i class='fa-solid fa-triangle-exclamation'></i> 密碼不可空白";
			} else {
				spp.innerHTML = ""; // 清除可能的錯誤訊息
			}
		}
	</script>
</body>
</html>