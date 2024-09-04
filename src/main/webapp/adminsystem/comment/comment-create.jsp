<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>create</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/adminsystem/booking/booking.css">
<style>
    .star-rating {
        direction: rtl;
        display: inline-flex;
        justify-content: flex-end;
    }

    .star-rating input[type="radio"] {
        display: none;
    }

    .star-rating label {
        font-size: 3rem; /* 確保星星大小足夠大 */
        color: #ddd;
        cursor: pointer;
    }

    .star-rating input[type="radio"]:checked ~ label {
        color: #f5c518;
    }

    .star-rating label:hover,
    .star-rating label:hover ~ label {
        color: #f5c518;
    }
</style>
</head>
<body>
    <div class="container">
        <%@include file="../sidebar.jsp"%>
        <div class="main-content">
            <%@include file="../navbar.jsp"%>
            <form class="form" action="${pageContext.request.contextPath}/comment/create" method="post">
                <div class="form-group">
                    <label for="roomtypeId">房間類型ID</label>
                    <input type="number" id="roomtype-Id" name="roomtype-Id" placeholder="輸入房間類型ID" required>
                </div>

                <div class="form-group">
                    <label for="memberId">會員ID</label>
                    <input type="number" id="member-Id" name="member-Id" placeholder="輸入會員ID" required>
                </div>

                <div class="form-group">
                    <label>評論評分</label>
                    <div class="star-rating">
                        <input type="radio" id="star5" name="comment-Score" value="5" required>
                        <label for="star5" title="5顆星">&#9733;</label>
                        <input type="radio" id="star4" name="comment-Score" value="4">
                        <label for="star4" title="4顆星">&#9733;</label>
                        <input type="radio" id="star3" name="comment-Score" value="3">
                        <label for="star3" title="3顆星">&#9733;</label>
                        <input type="radio" id="star2" name="comment-Score" value="2">
                        <label for="star2" title="2顆星">&#9733;</label>
                        <input type="radio" id="star1" name="comment-Score" value="1">
                        <label for="star1" title="1顆星">&#9733;</label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="commentContent">評論內容</label>
                    <textarea id="comment-Content" name="comment-Content" placeholder="輸入評論內容" required></textarea>
                </div>

                <div class="form-group">
                    <button type="submit">新增評論</button>
                    <button type="button" class="cancel">取消</button>
                </div>
            </form>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/adminsystem/booking/booking.js"></script>
</body>
</html>

