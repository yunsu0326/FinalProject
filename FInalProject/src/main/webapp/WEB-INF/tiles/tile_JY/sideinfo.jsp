<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
%>

<style>.sidebar {
        width: 230px;
        position: fixed;
        height: 100%;
        overflow-y: auto;
        top: 0;
        left: 0; /* Initially hide the sidebar */
        background-color: rgb(3, 199, 90);
        transition: all 0.1s;
        padding-top: 30px;
    }

    .sidebar.hidden {
        left: -200px; /* Hide the sidebar by moving it to the left */
    }

    .sidebar ul {
        list-style: none;
        padding: 15px;
        margin-top: 5px;
    }

    .sidebar ul li.nav-item {
        padding: 15px 7px;
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    }

    .toggle-btn {
        position: fixed;
        left: 20px;
        top: 20px;
        cursor: pointer;
        color: #fff;
        font-size: 10px;
        transition: all 0.3s;
        z-index: 999; /* Set a higher z-index to keep the toggle button above other elements */
    }

    .sidebar ul li .nav-link-text,
    .nav-icon {
        color: #fff !important;
        text-decoration: none !important;
        font-size: 14pt !important;
    }
    .sidebar ul li .nav-link-text{
        padding-left:20px;
    }
    
    .nav-icon {
        position: fixed; /* Fix the icons */
        left : 15px;
    }
</style>

<script>
$(document).ready(function(){
	toggleSidebar();
})

function toggleSidebar() {
    const sidebar = document.querySelector('.sidebar');
    sidebar.classList.toggle('hidden');
}

document.addEventListener('DOMContentLoaded', function () {
    const navLinks = document.querySelectorAll('.sidebar .nav-item');
    navLinks.forEach(function (link) {
        link.addEventListener('click', function () {
            const sidebar = document.querySelector('.sidebar');
            sidebar.classList.add('hidden');
        });
    });

    const sidebar = document.querySelector('.sidebar');
    document.addEventListener('click', function (event) {
        const isClickInside = sidebar.contains(event.target);
        if (!isClickInside && !sidebar.classList.contains('hidden')) {
            sidebar.classList.add('hidden');
        }
    });
});
</script>
<div class="sidebar" id="sidebar">

    <div class="toggle-btn" onclick="toggleSidebar()">
        <i class="fas fa-bars nav-icon"></i>
        <i class="fas fa-times" style="display: none;"></i>
    </div>
    <ul class="list-unstyled">
        <!-- 여기에 목차를 추가하세요 -->
       
        <li class="nav-item">
            <a href="<%= ctxPath %>/viewOrgChart.gw">
                <i class="fa-solid fa-id-card nav-icon"></i>
                <span class="nav-link-text">프로필</span>
            </a>
        </li>
        <li class="nav-item">
            <a href="<%= ctxPath %>/notice.gw">
                <i class="fa-solid fa-bullhorn nav-icon"></i>
                <span class="nav-link-text">공지사항</span>
            </a>
        </li>
        <!-- 홈으로 항목도 동일하게 추가 -->
        <li class="nav-item">
            <a href="<%= ctxPath %>/index.gw">
                <i class="fa-solid fa-house-user nav-icon"></i>
                <span class="nav-link-text">홈으로</span>
            </a>
        </li>
        <!-- 일정관리 항목 추가 -->
        <li class="nav-item">
            <a href="#">
                <i class="fa-solid fa-calendar-days nav-icon"></i>
                <span class="nav-link-text">일정관리</span>
            </a>
        </li>
        <!-- 채팅 항목 추가 -->
        <li class="nav-item">
            <a href="#">
                <i class="fa-solid fa-comments nav-icon"></i>
                <span class="nav-link-text">채팅</span>
            </a>
        </li>
        <!-- 근무 항목 추가 -->
        <li class="nav-item">
            <a href="<%= ctxPath %>/my_work.gw">
                <i class="fa-solid fa-file-word nav-icon"></i>
                <span class="nav-link-text">근무</span>
            </a>
        </li>
        <!-- 휴가 항목 추가 -->
        <li class="nav-item">
            <a href="<%= ctxPath %>/vacation.gw">
                <i class="fa-solid fa-plane nav-icon"></i>
                <span class="nav-link-text">휴가</span>
            </a>
        </li>
        <!-- 워크플로우 A 항목 추가 -->
        <li class="nav-item">
            <a href="<%= ctxPath %>/approval/home.gw">
                <i class="fa-solid fa-list-check nav-icon"></i>
                <span class="nav-link-text">워크플로우 A</span>
            </a>
        </li>
        <!-- 워크플로우 B 항목 추가 -->
        <li class="nav-item">
            <a href="<%= ctxPath %>/workflow_b.gw">
                <i class="fa-brands fa-resolving nav-icon"></i>
                <span class="nav-link-text">워크플로우 B</span>
            </a>
        </li>
        <!-- 급여 항목 추가 -->
        <li class="nav-item">
            <a href="#">
                <i class="fa-solid fa-sack-dollar nav-icon"></i>
                <span class="nav-link-text">급여</span>
            </a>
        </li>
        <!-- 문서 및 증명서 발급 항목 추가 -->
        <li class="nav-item">
            <a href="#">
                <i class="fa-regular fa-folder-open nav-icon"></i>
                <span class="nav-link-text">문서 및 증명서 발급</span>
            </a>
        </li>
        <!-- 자유게시판 항목 추가 -->
        <li class="nav-item">
            <a href="#">
                <i class="fa-solid fa-face-meh nav-icon"></i>
                <span class="nav-link-text">자유게시판</span>
            </a>
        </li>
    </ul>
</div>