<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<aside class="sidebar">
    <div class="profile">
        <img src="${pageContext.request.contextPath}/adminsystem/images/profile.png" alt="Profile Picture">
        <h3>David Grey</h3>
        <p>Project Manager</p>
    </div>
    <ul id="navList">
        <li><a href="${pageContext.request.contextPath}/admin" data-page="admin-management">管理員列表</a></li>
        <li><a href="#" data-page="member-management">會員管理</a></li>
        <li><a href="${pageContext.request.contextPath}/roomtype" data-page="booking-management">訂房管理</a></li>
        <li><a href="${pageContext.request.contextPath}/attraction" data-page="attraction-management">景點管理</a></li>
        <li><a href="${pageContext.request.contextPath}/product" data-page="shopping-management">商城管理</a></li>
        <li><a href="${pageContext.request.contextPath}/activity" data-page="marketing-management">行銷活動管理</a></li>
        <li><a href="${pageContext.request.contextPath}/comment" data-page="review-management">評論管理</a></li>
    </ul>
</aside>
