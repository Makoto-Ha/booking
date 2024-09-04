<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header class="navbar">
    <h1 id="page-title">${manageListName}</h1>
    <div class="user-nav">
        <button class="nav-icon"><img src="${pageContext.request.contextPath}/adminsystem/images/mail-icon.png" alt="Mail"></button>
        <button class="nav-icon"><img src="${pageContext.request.contextPath}/adminsystem/images/bell-icon.png" alt="Notifications"></button>
        <button class="nav-icon"><img src="${pageContext.request.contextPath}/adminsystem/images/power-icon.png" alt="Logout"></button>
        <div class="user-info">
            <span>David Greymaax</span>
            <img src="${pageContext.request.contextPath}/adminsystem/images/profile.png" alt="User Picture">
        </div>
    </div>
</header>
