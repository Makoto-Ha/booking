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
			<form class="form" action="create">
		        <div class="form-group">
		            <label for="roomtype-name">房間類型名稱 (輸入中文，至少四個)</label>
		            <input type="text" id="roomtype-name" name="roomtype-name" placeholder="輸入內容" required>
		        </div>
		
		        <div class="form-group">
		            <label for="price">房間類型價格</label>
		            <input type="number" id="price" name="roomtype-price" placeholder="輸入內容" required>
		        </div>
		
		        <div class="form-group">
		            <label for="quantity">房間數量</label>
		            <input type="number" id="quantity" name="roomtype-quantity" placeholder="輸入內容" required value="1" min="1">
		        </div>
		
		        <div class="form-group">
		            <label for="description">房間類型介紹 (四個中文，至少八個字)</label>
		            <input type="text" id="description" name="roomtype-description" placeholder="輸入內容">
		        </div>
		
		        <div class="form-group">
		            <label for="view">縣市</label>
		            <select id="select-city" name="roomtype-city"></select>
		        </div>
		        
		        <div class="form-group">
		            <label for="view">區域</label>
		            <select id="select-district" name="roomtype-district"></select>
		        </div>
		        
		        <div class="form-group">
		            <label for="description">地址 (xx市xx區xx街xx號x樓)</label>
		            <input type="text" id="description" name="roomtype-address" placeholder="輸入內容" required>
		        </div>
		
		        <div class="form-group roomtype-capacity-group">
		            <div class="choose-roomtype-capacity">
			            <label class="radio-label">
			            	一人房
			                <input type="radio" name="roomtype-capacity" value="1" checked>
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
		
		        <div class="form-group">
		            <button type="submit" class="submit" id="submit" disabled>新增</button>
		            <button type="button" class="cancel">取消</button>
		        </div>
		    </form>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/adminsystem/booking/taiwan_districts.js"></script>
	<script src="${pageContext.request.contextPath}/adminsystem/booking/form.js"></script>
	<script src="${pageContext.request.contextPath}/adminsystem/booking/booking.js"></script>
</body>
</html>
