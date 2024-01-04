<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
%>
<style>

	.container{
		margin-top:30px;	
	}
    /* 위젯 컨테이너 스타일 */
    .widget-container {
        width: calc(50% - 20px); /* 2x2 그리드로 정렬하므로 50% */
        margin: 10px;
        padding: 20px;
        border: 1px solid #ccc;
        float: left;
        box-sizing: border-box;
        border-radius: 10px; /* 테두리 둥글게 처리 */
    }

    .widget-header {
        font-weight: bold;
        margin-bottom: 15px;
    }
</style>

<div class="container">
    <!-- To-Do 컨테이너 -->
    <div class="widget-container">
        <div class="widget-header" style="height:650px;">To-Do</div>
        <!-- To-Do 컨텐츠 -->
        <!-- ... -->
    </div>

    <!-- 프로필 컨테이너 -->
    <div class="widget-container">
        <div class="widget-header" style="height:200px;">프로필 조직도</div>
        <!-- 프로필 컨텐츠 -->
        <!-- ... -->
    </div>

    <!-- 웹메일 컨테이너 -->
    <div class="widget-container">
        <div class="widget-header" style="height:200px;">웹메일</div>
        <!-- 웹메일 컨텐츠 -->
        <!-- ... -->
    </div>
    
    <!-- 공지사항 컨테이너 -->
    <div class="widget-container">
        <div class="widget-header" style="height:100px;">공지사항</div>
        <!-- 공지사항 컨텐츠 -->
        <!-- ... -->
    </div>
</div>
