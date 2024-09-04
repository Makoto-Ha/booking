<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>管理員註冊</title>
<style>
* {
	box-sizing: border-box;
	text-decoration: none;
}

html, body {
	height: 100%;
	margin: 0;
	padding: 0;
	font-family: Arial, sans-serif;
	display: flex;
	justify-content: center;
	align-items: center;
	background-color: #f2edf3;
}

.register-container {
	background-color: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	text-align: center;
	width: 360px;
	margin: auto 0; /* 上下方向自動居中 */
	display: flex;
	flex-direction: column;
	align-items: center;;
	justify-content: center;
}

.register-container img.img {
	width: 80px;
}

.register-container h2 {
	height: 32px;
	font-size: 24px; /* 字體大小可以根據需求調整 */
	font-weight: bold; /* 字體粗細 */
	background: linear-gradient(to right, #da8cff, #9a55ff); /* 設定漸變色 */
	-webkit-background-clip: text; /* 只顯示背景漸變在文字上 */
	-webkit-text-fill-color: transparent; /* 將文字顏色設為透明，讓背景顯示出來 */
}

.input-group {
	margin-bottom: 15px;
	text-align: left;
}

.input-group label {
	display: block;
	font-weight: bold;
	margin-bottom: 5px;
	font-size: 14px;
}

.input-group input {
	font-size: 16px;
	width: 320px !important;
	border-radius: 5px;
	border: 1px solid #cccccc94;
	padding: 10px;
	outline-color: #007BFF;
	padding-left: 14px;
}

.input-group input[type="text"], .input-group input[type="password"],
	.input-group input[type="name"], .input-group input[type="email"],
	.input-group input[type="date"] {
	width: 100%;
}

.error-message {
	color: red;
	font-size: 12px;
	text-align: center;
	margin-top: 5px;
}

.register-button {
	width: 100%;
	padding: 10px;
	background: linear-gradient(to right, #da8cff, #9a55ff);
	color: white;
	border: none;
	border-radius: 4px;
	font-size: 15px;
	cursor: pointer;
	margin-top: 10px;
	transition: all .2s;
}

.register-button:hover {
	opacity: .8;
}

.register {
	margin-top: 20px;
	text-align: center;
}

.register a {
	color: #007BFF;
	text-decoration: none;
	font-size: 14px;
}

.register a:hover {
	text-decoration: underline;
}

.register-title {
	display: flex;
	text-align: start;
	align-items: center;
	width: 320px;
}

form {
	width: 320px;
	height: 100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}

.input-group:last-of-type {
	width: 320px;
}

label[for="status"] {
	
}

select[name="admin-status"] {
	padding: 5px 10px;
}

.go-login {
	width: 100%;
	padding: 10px;
	background-color: #ffc5e1;
	color: white;
	border-radius: 4px;
	font-size: 15px;
	margin-top: 10px;
	transition: all .2s;
}

.go-login:hover {
	opacity: .8;
}
</style>
</head>
<body>
	<div class="register-container">
		
		<div class="register-title">
			<img class="img"
			src="${pageContext.request.contextPath}/adminsystem/images/03.jpg"
			alt="Logo">
			<h2>管理員註冊</h2>
		</div>
		<form action="/booking/admin/register" method="post">
			<div class="input-group">
				<label for="account"></label> <input type="text" id="account"
					name="admin-account" required="" placeholder="帳號"> <span
					id="account-error" class="error-message"></span>
			</div>
			<div class="input-group">
				<label for="password"></label> <input type="password" id="password"
					name="admin-password" required="" placeholder="密碼"> <span
					id="password-error" class="error-message"></span>
			</div>
			<div class="input-group">
				<label for="name"></label> <input type="text" id="name"
					name="admin-name" required="" placeholder="姓名"> <span
					id="name-error" class="error-message"></span>
			</div>
			<div class="input-group">
				<label for="email"></label> <input type="email" id="email"
					name="admin-mail" required="" placeholder="信箱"> <span
					id="email-error" class="error-message"></span>
			</div>
			<div class="input-group">
				<label for="hiredate">到職日期</label> <input type="date" id="hiredate"
					name="hiredate"
					value="${pageContext['request'].getAttribute('today')}" required="">
				<span id="hiredate-error" class="error-message"></span>
			</div>
			<div class="input-group">
				<label for="status">狀態</label> <select id="status"
					name="admin-status" required>
					<option value="1" selected>在職</option>
					<option value="0">離職</option>
				</select> <span id="status-error" class="error-message"></span>
			</div>
			<!-- 錯誤訊息區域 -->
			<c:if test="${not empty errorMessage}">
				<div class="error-message"
					style="color: red; text-align: center; font-size: 18px;">
					<i class='fa-solid fa-triangle-exclamation'></i>${errorMessage}
				</div>
			</c:if>
			<button type="submit" class="register-button">註冊</button>
			<a class="go-login"
				href="${pageContext.request.contextPath}/adminsystem/admin/login.jsp">我要登入</a>
		</form>
	</div>
	<script src="https://kit.fontawesome.com/cc1b58587a.js"
		crossorigin="anonymous"></script>
	<script>
		document.getElementById("account").addEventListener("blur",
				checkAccount);

		function checkAccount() {
			let theAccountObj = document.getElementById("account");
			let theAccountObjVal = theAccountObj.value.trim();
			let theAccountObjValLen = theAccountObjVal.length;
			let sp = document.getElementById("account-error");

			if (theAccountObjVal === "") {
				sp.innerHTML = "<i class='fa-solid fa-triangle-exclamation'></i> 帳號欄位不可空白";
			} else {
				sp.innerHTML = ""; // 清除可能的錯誤訊息
			}
		}

		document.getElementById("password").addEventListener("blur",
				checkPassword);

		function checkPassword() {
			let thePwdObj = document.getElementById("password");
			let thePwdObjVal = thePwdObj.value.trim();
			let thePwdObjValLen = thePwdObjVal.length;
			let spp = document.getElementById("password-error");

			let flag1 = false, flag2 = false, flag3 = false, flag4 = false;

			if (thePwdObjVal === "") {
				spp.innerHTML = "<i class='fa-solid fa-triangle-exclamation'></i> 密碼不可空白";
			} else if (thePwdObjValLen >= 8) {
				for (let i = 0; i < thePwdObjValLen; i++) {
					let ch = thePwdObjVal.charAt(i);
					if (ch >= "A" && ch <= "Z") {
						flag1 = true;
					} else if (ch >= "a" && ch <= "z") {
						flag2 = true;
					} else if (ch >= "0" && ch <= "9") {
						flag3 = true;
					}
					if (flag1 && flag2 && flag3)
						break;
				}
				if (flag1 && flag2 && flag3) {
					spp.innerHTML = "格式正確";
					spp.style.color = "green";
				} else {
					spp.innerHTML = "格式錯誤";
				}
			} else {
				spp.innerHTML = "<i class='fa-solid fa-triangle-exclamation'></i> 密碼至少8個字";
			}
			sp.innerHTML = ""; // 清除可能的錯誤訊息
		}

		document.getElementById("name").addEventListener("blur", checkName);

		function checkName() {
			let theNameObj = document.getElementById("name");
			let theNameObjVal = theNameObj.value.trim();
			let sppp = document.getElementById("name-error");

			if (theNameObjVal === "") {
				sppp.innerHTML = "<i class='fa-solid fa-triangle-exclamation'></i> 名字欄位不可空白";
			} else {
				sppp.innerHTML = ""; // 清除可能的錯誤訊息
			}
		}
	</script>
</body>
</html>



