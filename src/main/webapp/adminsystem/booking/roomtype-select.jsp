<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>create</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/booking/booking.css">
</head>
<body>
	<div class="container">
		<%@include file="../sidebar.jsp"%>
		<div class="main-content">
			<%@include file="../navbar.jsp"%>
			<form class="form" action="select">
		        <div class="form-group">
		            <label for="roomtype-name">房間類型名稱</label>
		            <input type="text" id="roomtype-name" name="roomtype-name" placeholder="輸入內容" value="${roomtype.getRoomtypeName()}">
		        </div>
		
		        <div class="form-group">
		            <label for="price">房間類型價格</label>
		            <input type="number" id="roomtype-price" name="roomtype-price" placeholder="輸入內容" value="${roomtype.getRoomtypePrice()}">
		        </div>
		
		        <div class="form-group">
		            <label for="quantity">房間剩餘數量</label>
		            <input type="number" id="roomtype-quantity" name="roomtype-quantity" placeholder="輸入內容" value="${roomtype.getRoomtypeQuantity()}">
		        </div>
		
		        <div class="form-group">
		            <label for="description">房間類型介紹</label>
		            <input type="text" id="roomtype-description" name="roomtype-description" placeholder="輸入內容" value="${roomtype.getRoomtypeDescription()}">
		        </div>
		        
		        <div class="form-group">
		            <label for="select-city">縣市</label>
		            <select id="select-city" name="roomtype-city"></select>
		        </div>
		        
		        <div class="form-group">
		            <label for="select-district">區域</label>
		            <select id="select-district" name="roomtype-district"></select>
		        </div>
		        
		        <label class="money-range-label" for="money"></label>
			    <input class="money-range" type="range" id="min-range-money" name="min-money" min="0" max="50000" value="0">
			    <p class="show-range-money">最低金額: $<span id="min-money" class="volumeValue">0</span></p>
			    
			    <label class="money-range-label" for="money"></label>
			    <input class="money-range" type="range" id="max-range-money" name="max-money" min="0" max="50000" value="0">
			    <p class="show-range-money">最高金額: $<span id="max-money" class="volumeValue">0</span></p>
	
		        <div class="form-group roomtype-capacity-group">
		            <div class="choose-roomtype-capacity">
			            <label class="radio-label">
			            	一人房
			                <input type="radio" name="roomtype-capacity" value="1">
			            </label>
		            </div>
		            <div class="choose-roomtype-capacity">
			            <label class="radio-label">
			            	兩人房
			                <input type="radio" name="roomtype-capacity" value="2">
			            </label>
		            </div>
		            <div class="choose-roomtype-capacity">
			            <label class="radio-label">
			            	三人房
			                <input type="radio" name="roomtype-capacity" value="3">
			            </label>
		            </div>
		             <div class="choose-roomtype-capacity">
			            <label class="radio-label">
			            	四人房
			                <input type="radio" name="roomtype-capacity" value="4">
			            </label>
		            </div>
		            <div class="choose-roomtype-capacity">
			            <label class="radio-label">
			            	五人房
			                <input type="radio" name="roomtype-capacity" value="5">
			            </label>
		            </div>
		        </div>
		
		        <!--   <div class="form-group checkbox-group">
		            <label class="checkbox-label">
		                <input type="checkbox" id="breakfast" name="breakfast">
		                附贈早餐
		            </label>
		        </div> -->
		        <div class="form-group">
		            <button type="submit" style="background-color: #6c63ff;">查詢</button>
		            <button type="button" class="cancel">取消</button>
		        </div>
		    </form>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/adminsystem/booking/taiwan_districts.js"></script>
	<script src="${pageContext.request.contextPath}/adminsystem/booking/booking.js"></script>
	<script>
		const selectCity = document.getElementById("select-city");
		
		{
			let option = document.createElement("option");
			option.setAttribute('disabled', true);
	        option.setAttribute('selected', true);
			option.textContent = "請選擇縣市";
			selectCity.appendChild(option);
		}
		
		data.forEach(location => {
			let optionCity = document.createElement("option");
			optionCity.textContent = location.name;
			selectCity.appendChild(optionCity);
		});
		
		const selectDistrict = document.getElementById("select-district");
		
		{
			let option = document.createElement("option");
			option.setAttribute('disabled', true);
	        option.setAttribute('selected', true);
			option.textContent = "請選擇地區";
			selectDistrict.appendChild(option);
		}
	
		selectCity.addEventListener("change", e => {
			selectDistrict.innerHTML = "";
			let selectCityName = e.target.value;
			
			const city = data.find(location => location.name === selectCityName);
			let option = document.createElement("option");
			option.setAttribute('disabled', true);
			option.setAttribute('selected', true);
			option.textContent = "請選擇地區";
			selectDistrict.appendChild(option);
			
			city.districts.forEach(area => {
				let option = document.createElement("option");
				option.textContent = area.name;
				selectDistrict.appendChild(option);
			});
		});
		const roomtypePrice = document.getElementById('roomtype-price');
		const minMoney = document.getElementById('min-money');
		const maxMoney = document.getElementById('max-money');
		const minRange = document.getElementById('min-range-money');
		const maxRange = document.getElementById('max-range-money');

		// 當拉動最小值的滑塊時
		minRange.addEventListener('input', function() {
			roomtypePrice.value = '';
		    minMoney.textContent = this.value;
		    if (parseInt(maxRange.value) < parseInt(this.value)) {
		        maxRange.value = this.value;  // maxRange 跟隨 minRange
		        maxMoney.textContent = this.value;
		    }
		});

		// 當拉動最大值的滑塊時
		maxRange.addEventListener('input', function() {
			roomtypePrice.value = '';
		    if (parseInt(this.value) < parseInt(minRange.value)) {
		        // 如果 maxRange 的值小於 minRange，將 minRange 一起移動
		        minRange.value = this.value;
		        minMoney.textContent = this.value;
		    }
		    maxMoney.textContent = this.value;
		});
		
		roomtypePrice.addEventListener('input', function() {
			minMoney.textContent = "0";
			maxMoney.textContent = "0";
			minRange.value = "0";
			maxRange.value = "0";
		});
	</script>
</body>
</html>
