<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>create</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/attraction/booking.css">
</head>
<body>
	<div class="container">
		<%@include file="../sidebar.jsp"%>
		<div class="main-content">
			<%@include file="../navbar.jsp"%>
			<form class="form" action="${pageContext.request.contextPath}/attraction/select">
	        	<div class="form-group">
		            <label for="name">景點名稱</label>
		            <input type="text" id="name" name="attractionName" placeholder="輸入內容" value="${attraction.getAttractionName()}">
		        </div>
		        
		        <div class="form-group">
		            <label for="view">縣市</label>
		            <select id="select-city" name="attractionCity"></select>
		        </div>
		
		        <div class="form-group">
		            <label for="address">地址</label>
		            <input type="text" id="address" name="address" placeholder="輸入內容" value="${attraction.getAddress()}">
		        </div>
		
		        <div class="form-group">
        			<label for="openinghour">開放時間</label>
        			<textarea id="openinghour" name="openingHour" placeholder="輸入內容" rows="5" style="width: 100%;"></textarea>
    			</div>
		
		        <div class="form-group">
    				<label for="type">景點類型</label>
    				<select id="type" name="attractionType" value="${attraction.getAttractionType()}">
    					<option value="">選擇類型</option>
        				<option value="自然景點">自然景點</option>
        				<option value="歷史文化">歷史文化</option>
        				<option value="親子共遊">親子共遊</option>
        				<option value="休閒娛樂">休閒娛樂</option>
    				</select>
				</div>
		
		        <div class="form-group">
        			<label for="description">景點介紹</label>
        			<textarea id="description" name="attractionDescription" placeholder="輸入內容" rows="8" style="width: 100%;"></textarea>
    			</div>
    			
		        <div class="form-group">
		            <button type="submit" style="background-color: red;">查詢</button>
		            <button type="button" class="cancel">取消</button>
		        </div>
		    </form>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/adminsystem/attraction/taiwan_districts.js"></script>
	<script src="${pageContext.request.contextPath}/adminsystem/attraction/booking.js"></script>
		<script>
		const selectCity = document.getElementById("select-city");
		data.unshift({districts: [], name: ""});
		data.forEach(location => {
			let optionCity = document.createElement("option");
			optionCity.textContent = location.name;
			selectCity.appendChild(optionCity);
		});
	
	
		selectCity.addEventListener("change", e => {
			selectDistrict.innerHTML = "";
			let selectCityName = e.target.value;
	
			const city = data.find(location => location.name === selectCityName);
			city.districts.unshift({name: ""});
			city.districts.forEach(area => {
				let option = document.createElement("option");
				option.textContent = area.name;
			});
		});
	</script>
</body>
</html>
