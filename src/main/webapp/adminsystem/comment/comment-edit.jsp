<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>更新評論</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/booking/booking.css">
</head>
<body>
    <div class="container">
        <%@include file="../sidebar.jsp"%>
        <div class="main-content">
            <%@include file="../navbar.jsp"%>
            <form class="form" action="${pageContext.request.contextPath}/comment/replyToComment" method="post">
                <div class="form-group">
                    <label for="comment-id"">評論編號</label>
                    <input type="text" id="comment-id" name="comment-id" placeholder="評論編號" value="${comment.commentId}" readonly>
                </div>
                
                <div class="form-group">
                    <label for="roomtype-id"">房間類型編號</label>
                    <input type="text" id="roomtype-id"" name="roomtype-id"" placeholder="房間類型編號" value="${comment.getRoomtypeId()}" readonly>
                </div>

                <div class="form-group">
                    <label for="member-id"">會員編號</label>
                    <input type="text" id="member-id"" name="member-id"" placeholder="會員編號" value="${comment.getMemberId()}" readonly>
                </div>

                <div class="form-group">
                    <label for="comment-Score">評分</label>
                    <input type="text" id="comment-Score" name="comment-score" placeholder="評分" value="${comment.getCommentScore()}" readonly>
                </div>

                <div class="form-group">
                    <label for="comment-Content">評論內容</label>
                    <textarea id="comment-Content" name="comment-content" placeholder="評論內容" readonly>${comment.getCommentContent()}</textarea>
                </div>

                <div class="form-group">
                    <label for="created-Time">評論時間</label>
                    <input type="text" id="created-Time" name="created-time" placeholder="評論時間" value="${comment.getCreatedTime()}" readonly>
                </div>

                <div class="form-group">
                    <label for="admin-id">員工編號</label>
                    <input type="text" id="admin-id" name="admin-id" placeholder="輸入員工編號" value="${comment.adminId}">
                </div>

                <div class="form-group">
                    <label for="admin-reply">員工回覆</label>
                    <textarea id="admin-reply" name="admin-reply" placeholder="輸入員工回覆">${comment.adminReply}</textarea>
                </div>

                <div class="form-group">
                    <button type="submit" style="background-color: #007bff;">保存</button>
                    <button type="button" class="cancel">取消</button>
                </div>
            </form>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/adminsystem/booking/booking.js"></script>
</body>
</html>
