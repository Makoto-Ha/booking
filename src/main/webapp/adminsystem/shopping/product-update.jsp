<%@page import="com.booking.bean.shopping.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>商城管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/shopping/product.css">
    <style>
   		button[disabled] {
   			background-color: #cccccc; /* 禁用時的灰色背景 */
  			cursor: not-allowed; /* 指針顯示為禁止符號 */
  	 		opacity: 0.6; /* 減少透明度 */
		}
    </style>
</head>
<body>
	<% Product product = (Product) request.getAttribute("product"); %>
    <div class="container">
        <%@include file="../sidebar.jsp"%>
        <div class="main-content">
            <%@include file="../navbar.jsp"%>
            <form id="productForm" class="form" action="${pageContext.request.contextPath}/product/update" method="POST">

                <div class="form-group">
                    <label for="productId">商品編號</label>
                    <input type="text" id="productId" name="product-id" placeholder="輸入內容不可為空" value="<%= product.getProductId() %>" readonly>
                    <span id="productIdSP"></span>
                </div>
                
                <div class="form-group">
                    <label for="productName">商品名稱</label>
                    <input type="text" id="productName" name="product-name" placeholder="輸入內容不可為空" value="<%= product.getProductName() %>">
                    <span id="productNameSP"></span>
                </div>

                <div class="form-group">
                    <label for="categoryId">分類編號</label>
                    <select id="categoryId" name="category-id" required>
                        <option value="" disabled>請選擇分類編號</option>
                        <option value="100" <%= product.getCategoryId() == 100 ? "selected" : "" %>>100</option>
                        <option value="200" <%= product.getCategoryId() == 200 ? "selected" : "" %>>200</option>
                        <option value="300" <%= product.getCategoryId() == 300 ? "selected" : "" %>>300</option>
                        <option value="400" <%= product.getCategoryId() == 400 ? "selected" : "" %>>400</option>
                        <option value="500" <%= product.getCategoryId() == 500 ? "selected" : "" %>>500</option>
                    </select>
                    <span id="categoryIdSP"></span>
                </div>

                <div class="form-group">
                    <label for="productDescription">商品描述</label>
                    <input type="text" id="productDescription" name="product-description" placeholder="輸入內容" value="<%= product.getProductDescription() %>">
                </div>

                <div class="form-group">
                    <label for="productPrice">商品價格</label>
                    <input type="number" id="productPrice" name="product-price" placeholder="輸入內容" value="<%= product.getProductPrice() %>">
                </div>

                <div class="form-group">
                    <label for="productSales">商品銷量</label>
                    <input type="number" id="productSales" name="product-sales" placeholder="輸入內容" value="<%= product.getProductSales() %>">
                </div>

                <div class="form-group">
                    <label for="productInventorey">商品庫存</label>
                    <input type="number" id="productInventorey" name="product-inventorey" placeholder="輸入內容" value="<%= product.getProductInventorey() %>">
                </div>

                <div class="form-group">
                    <label for="productState">商品狀態</label>
                    <select id="productState" name="product-state" required>
                        <option value="" disabled>請選擇商品狀態</option>
                        <option value="1" <%= product.getProductState() == 1 ? "selected" : "" %>>開放販售</option>
                        <option value="2" <%= product.getProductState() == 2 ? "selected" : "" %>>未開放販售</option>
                    </select>
                    <span id="productStateSP"></span>
                </div>

                <div class="form-group">
                    <button type="submit">確認修改</button><br>
                    <button type="button" class="cancel">取消</button>
                </div>
                
                <input type="hidden" id="referrer" name="referrer" value="<%= request.getHeader("referer") %>">
            </form>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/adminsystem/shopping/back.js"></script>
    <script src="${pageContext.request.contextPath}/adminsystem/shopping/CheckProduct.js"></script>
    <script>
        document.getElementById("productForm").addEventListener("submit", function(event) {
            let referrer = document.getElementById("referrer").value;
            if (!referrer) {
                referrer = document.referrer;
            }
            // 檢查 referrer 是否有效
            if (referrer) {
                let form = document.getElementById("productForm");
                let inputReferrer = document.createElement("input");
                inputReferrer.type = "hidden";
                inputReferrer.name = "referrer";
                inputReferrer.value = referrer;
                form.appendChild(inputReferrer);
            }
        });
    </script>
</body>
</html>
