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
	href="${pageContext.request.contextPath}/adminsystem/booking/booking.css">
</head>
<body>
	<div class="container">
		<%@include file="../sidebar.jsp"%>
		<div class="main-content">
			<%@include file="../navbar.jsp"%>
			<form class="form" action="../create" method="post">
				<div class="dropdown-input">	
					<div class="form-group">
						<label for="dropdown">房間類型名稱</label>
						<input name="roomtypeName" type="text" id="dropdown" placeholder="請輸入房間類型名稱">
					</div>
					<a onclick="toggleDropdown()"></a>
					<div class="dropdown-menu" id="menu"></div>
				</div>

				<div class="form-group">
					<label for="room-number">房間號碼</label> <input type="text"
						id="room-number" name="roomNumber" placeholder="輸入內容" required>
				</div>

				<div class="form-group">
					<label for="room-description">房間說明</label> <input type="text"
						id="room-description" name="roomDescription" placeholder="輸入內容">
				</div>

				<div class="form-group roomtype-capacity-group room-status-group">
					<div class="choose-room-status">
						<label class="radio-label">閒置<input type="radio"
							name="roomStatus" value="0" checked>
						</label>
					</div>
					<div class="choose-room-status">
						<label class="radio-label">已預定<input type="radio"
							name="roomStatus" value="1">
						</label>
					</div>
					<div class="choose-room-status">
						<label class="radio-label">已入住<input type="radio"
							name="roomStatus" value="2">
						</label>
					</div>
				</div>

				<div class="form-group">
					<button type="submit" class="submit" id="submit" disabled>新增</button>
					<button type="button" class="cancel">取消</button>
				</div>
			</form>
		</div>
	</div>
	<script
		src="${pageContext.request.contextPath}/adminsystem/booking/booking.js"></script>
	<script>
		// form用到的下拉式選單

		function toggleDropdown() {
			var menu = document.getElementById("menu");
			menu.classList.toggle("show");
		}

		function selectOption(value) {
			document.getElementById("dropdown").value = value;
			document.getElementById("menu").classList.remove("show");
		}

		function filterOptions() {
			var input = document.getElementById("dropdown").value.toLowerCase();
			var menu = document.getElementById("menu");
			var options = menu.getElementsByTagName("div");
			var hasMatchingOption = false;

			for (var i = 0; i < options.length; i++) {
				var optionText = options[i].textContent.toLowerCase();
				if (optionText.includes(input)) {
					options[i].style.display = "block";
					hasMatchingOption = true;
				} else {
					options[i].style.display = "none";
				}
			}

			if (hasMatchingOption && input !== "") {
				menu.classList.add("show");
			} else {
				menu.classList.remove("show");
			}
		}

		// 點擊外面關閉
		document.addEventListener('click', function(e) {
			var dropdown = document.querySelector('.dropdown-input');
			if (!dropdown.contains(e.target)) {
				document.getElementById("menu").classList.remove("show");
			}
		});
		
		document.getElementById("dropdown").addEventListener('input', async function(e) {
			let value = this.value;
			
			if(value === '{' || value === '}' || value === '[' || value === ']') {
				return;
			}
			
			let response = await fetch('/booking/json/roomtype?name='+value);
			let roomtypes = await response.json();
			const menu = document.getElementById('menu');
			menu.innerHTML = '';
			console.log(roomtypes);
			roomtypes.forEach(roomtype => {
				let roomtypeName = roomtype.roomtypeName;
				let div = document.createElement('div');
				div.textContent = roomtypeName;
				let selectOptionValue = "\'" + roomtypeName + "\'";
				div.setAttribute('onclick', 'selectOption(' + selectOptionValue + ')');
				menu.appendChild(div);
			});
			filterOptions();
		});
		
		const submitButton = document.getElementById('submit');
		const requiredInputs = document.querySelectorAll('.form-group input[type="text"]');

		// 正則表達式
		const validationRules = {
		    'roomName': /^[\u4e00-\u9fa5]{4,}$/, // 開頭中文且必須要有四個中文
		    'roomNumber': /\w+/,  
		    'roomDescription': /^(?=(?:.*[\u4e00-\u9fa5]){4})(?=.{4,}).*$/ // 至少八個字，且必須要有四個中文
		};

		// 添加驗證所需事件
		requiredInputs.forEach(input => {
		    input.addEventListener('input', validateInput);
		    input.addEventListener('blur', validateInput);
		});

		function validateInput(event) {
		    const input = event.target;
		    const rule = validationRules[input.name];

		    if (rule) {
		        if (!rule.test(input.value)) {
		            input.classList.add('invalid'); // 添加無效類名
		        } else {
		            input.classList.remove('invalid'); // 移除無效類名
		        }
		    }

		    // 檢查所有輸入
		    validateForm();
		}

		function validateForm() {
		    let allValid = true;

		    requiredInputs.forEach(input => {
		        const rule = validationRules[input.name];

		        if (rule && !rule.test(input.value)) {
		            allValid = false; // 一個規則不成功，則失敗。
		            submitButton.disabled = true;
		        }
		    });

		    // 確認驗證是否全部通過
		    submitButton.disabled = !allValid;
		}
	</script>
</body>
</html>
