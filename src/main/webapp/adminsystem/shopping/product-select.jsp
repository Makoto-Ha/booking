<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.booking.bean.shopping.Product"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商城管理</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/adminsystem/styles.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/adminsystem/shopping/product.css">
<style>
.form-group label{
	 font-size: 18px;
    width: auto;
    display: inline-block;
    margin-bottom: 0;
}
.form-group input[type="text"], .form-group select, .form-group input[type="number"]{
	width:200px;
}
.form-group button[type="submit"] {
	padding-top:8px;
	padding-bottom:8px;
	margin-left:5px;
}
.form-group .btn {
	background-color:#6666CC;
	padding-top:6px;
	padding-bottom:6px;
}
</style>
</head>

<body>
	<div class="container">
		<%@include file="../sidebar.jsp"%>
		<div class="main-content">
			<%@include file="../navbar.jsp"%>
			
			<form class="form" action="${pageContext.request.contextPath}/product/selectName" id="searchForm">
				<div class="form-group">
					<label for="selectName">搜尋名稱:</label> 
					<input type="text" id="productName" name="product-name" placeholder="輸入內容" 
						value="${param['product-name'] != null ? param['product-name'] : ''}">
						
					<label for="sortBy">排序依據:</label>
					<select id="sortBy" name="sortBy">
						<option value="productId" ${param.sortBy == 'productId' ? 'selected' : ''}>編號</option>
						<option value="categoryId" ${param.sortBy == 'categoryId' ? 'selected' : ''}>分類</option>
						<option value="productName" ${param.sortBy == 'productName' ? 'selected' : ''}>名稱</option>
						<option value="productPrice" ${param.sortBy == 'productPrice' ? 'selected' : ''} >價格</option>
						<option value="productSales" ${param.sortBy == 'productSales' ? 'selected' : ''} >銷量</option>
						<option value="productInventorey" ${param.sortBy == 'productInventorey' ? 'selected' : ''} >庫存</option>
					</select>
					
					 <label for="sortOrder">順序:</label> 
					 <select id="sortOrder"	name="sortOrder">
						<option value="DESC" ${param.sortOrder == 'DESC' ? 'selected' : ''}>降冪</option>
						<option value="ASC" ${param.sortOrder == 'ASC' ? 'selected' : ''}>升冪</option>
					</select>
					<button type="submit" style="width: 60px">查詢</button>
					
					<br>
					<a href="${pageContext.request.contextPath}/adminsystem/shopping/product-create.jsp">
					<button type="button" class="btn" style="width:100px;">新增商品</button></a>
					
					<a href="${pageContext.request.contextPath}/product/selectAll">
					<button type="button" class="btn" style="width: 100px;">列出全部</button></a>
					
				</div>
			</form>
			
			<div class="table">
				<%
				boolean hasTerm = request.getParameter("product-name") != null && !request.getParameter("product-name").trim().isEmpty();
				List<Product> products = (List<Product>) request.getAttribute("products");
				
				if (products != null && !products.isEmpty()) {
				%>
				
				<table border="1">
					<thead>
						<tr>
							<th>商品ID</th>
							<th>商品名稱</th>
							<th>商品分類</th>
							<th>商品描述</th>
							<th>商品價格</th>
							<th>商品銷售</th>
							<th>商品庫存</th>
							<th>商品狀態</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<%
						for (Product product : products) {
						%>
						<tr>
							<td><%=product.getProductId()%></td>
							<td><%=product.getProductName()%></td>
							<td><%=product.getCategoryId()%></td>
							<td><%=product.getProductDescription()%></td>
							<td><%=product.getProductPrice()%></td>
							<td><%=product.getProductSales()%></td>
							<td><%=product.getProductInventorey()%></td>
							<td><%=product.getProductState()%></td>
							<td><a href="${pageContext.request.contextPath}/product/selectUpdate?product-id=<%=product.getProductId()%>"><button>修改</button></a>
								<button type="button" onclick="deleteProduct(<%=product.getProductId()%>)">刪除</button>
							</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
				<%} else if(hasTerm) {
				%>
					<p>找不到符合條件的商品。</p>
				<%
					}
				 
				%>
			</div>
		</div>
	</div>
	
	<script>
	
		var contextPath = "${pageContext.request.contextPath}";		
		// 刪除並刷新
		function deleteProduct(productId) {
    if (confirm("確定要刪除這個產品嗎？")) {
        fetch(contextPath + '/product/delete?product-id=' + productId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("刪除成功！");
                location.reload(); // 刪除成功後重新加載頁面
            } else {
                alert("刪除失敗: " + data.message);
            }
        })
        .catch(error => {
            alert("發生錯誤: " + error.message);
        });
    }
}

		// 檢查按下搜尋時是否輸入
		document.getElementById("searchForm").addEventListener("submit",function(event) {
			let productName = document.getElementById("productName").value.trim();
			if (productName === "") {
				alert("商品名稱不可為空");
				event.preventDefault(); // 阻止表單提交
			}
		});
		
		// 當排序變更時，立即提交表單 --------------------------------------------------------------
		document.getElementById("sortBy").addEventListener("change", function() {
			document.getElementById("searchForm").submit();
		});

		document.getElementById("sortOrder").addEventListener("change", function() {
			document.getElementById("searchForm").submit();
		});
		
	</script>

</body>
</html>
