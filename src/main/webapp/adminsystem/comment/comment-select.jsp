<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>查詢評論</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/booking/booking.css">
<style>
    /* 表格样式 */
    .results-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
        background-color: #fff;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
        overflow: hidden;
    }
    
    .results-table th, .results-table td {
        padding: 12px 15px;
        text-align: left;
    }

    .results-table th {
        background-color: #f5f5f5;
        font-weight: bold;
        text-transform: uppercase;
        font-size: 14px;
        color: #333;
    }

    .results-table td {
        border-bottom: 1px solid #ddd;
        font-size: 14px;
        color: #555;
    }

    .results-table tr:last-child td {
        border-bottom: none;
    }

    .results-table tr:hover {
        background-color: #f9f9f9;
    }

    /* 调整按钮大小 */
    .form button[type="submit"] {
        padding: 4px 10px; /* 调整内边距，按钮变小 */
        font-size: 14px;
        background-color: green;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        width: auto; /* 取消默认宽度 */
        min-width: 80px; /* 设置最小宽度，保证按钮大小一致 */
    }

    .form button[type="submit"]:hover {
        background-color: darkred;
    }
    .form input[type="text"] {
        width: 150px; /* 调整为你需要的宽度 */
        padding: 8px; /* 内边距 */
        border: 1px solid #ccc; /* 边框颜色 */
        border-radius: 4px; /* 圆角 */
        box-sizing: border-box; /* 确保内边距和边框包含在宽度和高度内 */
    }
</style>
</head>
<body>
    <div class="container">
        <%@include file="../sidebar.jsp"%>
        <div class="main-content">
            <%@include file="../navbar.jsp"%>
            
            <h2>依條件查詢評論</h2>
            
            <!-- 查詢房型編號 -->
            <form class="form" action="${pageContext.request.contextPath}/comment/selectByRoomtypeId" method="GET">
                <div class="form-group">
                    <label for="roomtypeId">房型編號</label>
                    <input type="text" id="roomtype-Id" name="roomtype-Id" placeholder="輸入房型編號">
                    <button type="submit">查詢房型</button>
                </div>
            </form>
            <!-- 查詢會員編號 -->
            <form class="form" action="${pageContext.request.contextPath}/comment/selectByMemberId" method="GET">
                <div class="form-group">
                    <label for="memberId">會員帳號</label>
                    <input type="text" id="member-Id" name="member-Id" placeholder="輸入會員帳號">
                    <button type="submit">查詢會員帳號</button>
                </div>
            </form>
            <!-- 查詢員工編號 -->
            <form class="form" action="${pageContext.request.contextPath}/comment/selectByEmployeeId" method="GET">
                <div class="form-group">
                    <label for="employeeId">員工編號</label>
                    <input type="text" id="employee-Id" name="employee-Id" placeholder="輸入員工編號">
                    <button type="submit">查詢員工</button>
                </div>
            </form>
      <form class="form" action="${pageContext.request.contextPath}/comment/searchComments" method="GET">
    <div class="form-group">
        <label for="commentcontent">關鍵字查詢</label>
        <input type="text" id="comment-content" name="comment-content" placeholder="輸入評論內容">
        <button type="submit">查詢關鍵字</button>
    </div>
</form>
            <!-- 顯示查詢結果 -->
            <c:if test="${not empty comments}">
                <h2>評論列表</h2>
                <table class="results-table">
                    <thead>
                        <tr>
                            <th>評論ID</th>
                            <th>房型編號</th>
                            <th>會員編號</th>
                            <th>評分</th>
                            <th>評論內容</th>
                            <th>創建時間</th>
                            <th>員工回覆</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="comment" items="${comments}">
                            <tr>
                                <td>${comment.commentId}</td>
                                <td>${comment.roomtypeId}</td>
                                <td>${comment.memberId}</td>
                                <td>${comment.commentScore}</td>
                                <td>${comment.commentContent}</td>
                                <td>${comment.createdTime}</td>
                                <td>${comment.employeeReply}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</body>
</html>

