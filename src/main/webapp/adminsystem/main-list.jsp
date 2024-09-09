<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.booking.dto.booking.RoomtypeDTO"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="main-list">
  <!-- 搜索欄 -->
  <div class="search-bar">
      <input type="text" id="search-input" placeholder="搜索...">
      <button id="search-btn">進階搜索</button>
      <div class="pagination">
	      <button id="prev-page">上一頁</button>
	      <input type="number" id="page-input" value="${pageNumber.currentPage}" min="1">
	       / <span id="total-pages">${pageNumber.totalPages}</span>
	      <button id="next-page">下一頁</button>
	   </div>
	   <div class="attr-order-by">
	      <select class="attr-select-order-by" id="select-order-by">
	      	
	      	 <c:forEach var="listInfo" items="${listInfos}">
	            <div class="form-group">
                     <option value="${listInfo.attr}" ${listInfo.attr == attrOrderBy ? 'selected' : ''}>${listInfo.attrChinese}</option>
                </div>
	         </c:forEach>
	      </select>
	      <select class="attr-select-order-by" id="select-sort">
    			<option value="asc" ${selectedSort == 'asc' ? 'selected' : ''}>升冪</option>
	      		<option value="desc" ${selectedSort == 'desc' ? 'selected' : ''}>降冪</option>
	      </select>
	   </div>
      <div class="search-bar-addbtn">
          <button class="action-btn add-btn">新增</button>
      </div>
  </div> 
	  
  <div class="content-area-pages">
	  <c:forEach var="pageInfo" items="${pageInfos}">
	  	<button class="content-area-page" data-url="${pageInfo.url}">${pageInfo.page}</button>
	  </c:forEach>          
  </div>  
  <!-- 管理頁面內容 -->
  <section id="content" class="content-area">       
     <div id="modal-overlay" class="overlay">
         <div class="modal">
             <h2 id="modal-title">查看</h2>
             <form id="modal-form">
                 <c:forEach var="listInfo" items="${listInfos}">
		            <div class="form-group">
	                     <label>${listInfo.attrChinese}</label>
	                     <input type="text" class="input-show-value" placeholder="輸入內容" name="${listInfo.attr}" disabled>
	                </div>
		         </c:forEach>
                 <button type="button" id="modal-cancel-btn">取消</button>
             </form>
         </div>
     </div>
     <!-- 刪除確認視窗 -->
     <div id="delete-overlay" class="overlay">
         <div class="delete-modal">
             <h2>確認刪除</h2>
             <p>你確定要刪除此項目嗎？此操作無法撤銷。</p>
             <button id="confirmDeleteBtn">確認</button>
             <button type="button" id="cancelDeleteBtn">取消</button>
         </div>
     </div>
     <!-- 這裡是管理頁面的動態內容 -->
        <ul class="item-list">
            <c:forEach var="list" items="${lists}">
	            <li class="list-item" data-current-list-id="${list.id}">
	                <div>
	                	<span class="item-title">${list.name}</span>
	                	<div class="listInfo-group">
	                		<c:forEach var="dotProperty" items="${list.getAdditionProperties()}">
	                			<span class="item-info" style="color: darkred">${dotProperty.key}: </span>
	                			<span>${dotProperty.value}</span>
	                			<span style="color: #e3e3e373"> ┃ </span>
	                		</c:forEach>
	                	</div>
	                </div>
	                <div class="item-actions">
	                	<button class="action-btn check-btn">查看</button>
	                    <button class="action-btn edit-btn">編輯</button>
	                    <button class="action-btn delete-btn">刪除</button>
	                </div> 
	            </li>
	        </c:forEach>
        </ul>
    </section>
    <script>
    	// 因為會後續會創建script標籤appendChild執行，使用let和const會重複定義失敗，所以用var宣告
		var totalPages = "${pageNumber.totalPages}";
		var requestParameters = '${requestParameters}';
		console.log(requestParameters);
	</script>
</section>
