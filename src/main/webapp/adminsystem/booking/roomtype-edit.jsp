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
			<form class="form" action="../update" method="post">
		        <div class="form-group">
		            <label for="roomtype-name">房間類型名稱 (輸入中文，至少四個)</label>
		            <input type="text" id="roomtype-name" name="roomtypeName" placeholder="輸入內容" value="${roomtype.getRoomtypeName()}">
		        </div>
		
		        <div class="form-group">
		            <label for="price">房間類型價格</label>
		            <input type="number" id="price" name="roomtypePrice" placeholder="輸入內容" value="${roomtype.getRoomtypePrice()}">
		        </div>
		
		        <div class="form-group">
		            <label for="quantity">房間數量</label>
		            <input type="number" id="quantity" name="roomtypeQuantity" placeholder="輸入內容" value="${roomtype.getRoomtypeQuantity()}">
		        </div>
		
		        <div class="form-group">
		            <label for="description">房間類型介紹 (四個中文，至少八個字)</label>
		            <input type="text" id="description" name="roomtypeDescription" placeholder="輸入內容" value="${roomtype.getRoomtypeDescription()}">
		        </div>
		
		        <div class="form-group">
		            <label for="select-city">縣市</label>
		            <select id="select-city" name="roomtypeCity"></select>
		        </div>
		        
		        <div class="form-group">
		            <label for="select-district">區域</label>
		            <select id="select-district" name="roomtypeDistrict"></select>
		        </div>
		        
		        <div class="form-group">
		            <label for="description">地址 (xx市xx區xx街xx號x樓)</label>
		            <input type="text" name="roomtypeAddress" placeholder="輸入內容" value="${roomtype.getRoomtypeAddress()}">
		        </div>
		
		        <div class="form-group roomtype-capacity-group">
		            <div class="choose-roomtype-capacity">
			            <label class="radio-label">
			            	一人房
			                <input type="radio" name="roomtypeCapacity" value="1" ${roomtype.getRoomtypeCapacity() == '1' ? 'checked' : '' }>
			            </label>
		            </div>
		            <div class="choose-roomtype-capacity">
			            <label class="radio-label">
			            	兩人房
			                <input type="radio" name="roomtypeCapacity" value="2" ${roomtype.getRoomtypeCapacity() == '2' ? 'checked' : '' }>
			            </label>
		            </div>
		            <div class="choose-roomtype-capacity">
			            <label class="radio-label">
			            	三人房
			                <input type="radio" name="roomtypeCapacity" value="3" ${roomtype.getRoomtypeCapacity() == '3' ? 'checked' : '' }>
			            </label>
		            </div>
	             	<div class="choose-roomtype-capacity">
			            <label class="radio-label">
			            	四人房
			                <input type="radio" name="roomtypeCapacity" value="4" ${roomtype.getRoomtypeCapacity() == '4' ? 'checked' : '' }>
			            </label>
		            </div>
		            <div class="choose-roomtype-capacity">
			            <label class="radio-label">
			            	五人房
			                <input type="radio" name="roomtypeCapacity" value="5" ${roomtype.getRoomtypeCapacity() == '5' ? 'checked' : '' }>
			            </label>
		            </div>
		        </div>
		
		        <div class="form-group">
		            <button type="submit" class="submit submit-edit" id="submit">保存</button>
		            <button type="button" class="cancel">取消</button>
		        </div>
		    </form>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/adminsystem/booking/taiwan_districts.js"></script>
	<script src="${pageContext.request.contextPath}/adminsystem/booking/form.js"></script>
	<script src="${pageContext.request.contextPath}/adminsystem/booking/booking.js"></script>
	<script>
		let roomtypeCity = "${roomtype.getRoomtypeCity()}";
		selectCity.value = roomtypeCity;
		
		data.find(location => location.name === roomtypeCity)?.districts.forEach(district => {
			let option = document.createElement("option");
			option.textContent = district.name;
			selectDistrict.appendChild(option);
		});
		
		selectDistrict.value = "${roomtype.getRoomtypeDistrict()}";
	</script>
</body>
</html>
