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
    <div class="container">
        <%@include file="../sidebar.jsp"%>
        <div class="main-content">
            <%@include file="../navbar.jsp"%>
            <form id="productForm" class="form" action="${pageContext.request.contextPath}/product/create" method="POST">
                <div class="form-group">
                    <label for="productName">商品名稱</label>
                    <input type="text" id="productName" name="product-name" placeholder="輸入內容不可為空">
                    <span id="productNameSP"></span>
                </div>

                <div class="form-group">
                	<label for="categoryId">分類編號</label>
              		<select id="categoryId" name="category-id">
                        <option value="" disabled selected>請選擇分類編號</option>
                        <option value="100">100</option>
                        <option value="200">200</option>
                        <option value="300">300</option>
                        <option value="400">400</option>
                        <option value="500">500</option>
                    </select>
                    <span id="categoryIdSP"></span>
                </div>

                <div class="form-group">
                    <label for="productDescription">商品描述</label>
                    <input type="text" id="productDescription" name="product-description" placeholder="輸入內容">
                </div>

                <div class="form-group">
                    <label for="productPrice">商品價格</label>
                    <input type="number" id="productPrice" name="product-price" placeholder="輸入內容" min="0">
                </div>

                <div class="form-group">
                    <label for="productSales">商品銷量</label>
                    <input type="number" id="productSales" name="product-sales" placeholder="輸入內容" min="0">
                </div>

                <div class="form-group">
                    <label for="productInventorey">商品庫存</label>
                    <input type="number" id="productInventorey" name="product-inventorey" placeholder="輸入內容" min="0">
                </div>

                <div class="form-group">
                	<label for="productState">商品狀態</label>
                	<select id="productState" name="product-state">
                        <option value="" disabled selected>請選擇商品狀態</option>
                        <option value="1">開放販售</option>
                        <option value="2">未開放販售</option>
                    </select>
                    <span id="productStateSP"></span>
                </div>

                <div class="form-group">
                    <button type="submit">新增</button><br>
                    <button type="button" class="cancel">取消</button>
                </div>
            </form>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/adminsystem/shopping/back.js"></script>
    <script src="${pageContext.request.contextPath}/adminsystem/shopping/CheckProduct.js"></script>
</body>
</html>
