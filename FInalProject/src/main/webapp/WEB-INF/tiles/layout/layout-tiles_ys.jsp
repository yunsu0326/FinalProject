<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%-- === #24. tiles 를 사용하는 레이아웃 Final_MTS 페이지 만들기 === --%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%
   String ctxPath = request.getContextPath();
%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Final_MTS</title>

	<!-- Required meta tags -->
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> 
	
	<%-- Bootstrap CSS --%>
	<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/bootstrap-4.6.2-dist/css/bootstrap.min.css" > 
	
	<%-- Font Awesome 6 Icons --%>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
	
	<%-- 직접 만든 CSS 2 --%>
	<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/css/style.css" />
	
	<%-- Optional JavaScript --%>
	<script type="text/javascript" src="<%= ctxPath%>/resources/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="<%= ctxPath%>/resources/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" ></script> 
	<script type="text/javascript" src="<%= ctxPath%>/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
	
	<%-- 스피너 / datapicker를 사용하기 위한 jQueryUI CSS 및 JS --%>
	<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/resources/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
	<script type="text/javascript" src="<%= ctxPath%>/resources/jquery-ui-1.13.1.custom/jquery-ui.min.js"></script>
	
	<%-- ajax로 파일을 업로드 할때 가장 널리 사용하는 방법 ajaxForm 이용 --%>
	<script type="text/javascript" src="<%= ctxPath%>/resources/js/jquery.form.min.js"></script>
	
	<%-- sweet alert --%>
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

	
	<%-- 스마트 에디터 이미지 업로드 --%>
	<script type="text/javascript" src="<%=ctxPath%>/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>
	
<style>

#myHeader {
	min-height: 100px;
	display: flex;
}

#myHeader nav {
	background-color: white;
}



#myContent {
	min-height: 1200px;
}

#mySide {
	min-height: 1200px;
	background-color: #F9F9F9;
	padding-top: 4%;
}

#mySide a {
	color: black;
}

#mySide .navbar-nav {
	margin-left: 10%;
}

#mySide ul {
	list-style-type: none;
}

#mySide nav li {
	font-size: 12.5pt;
}

#mySide nav li li {
	font-size: 11.5pt;
}

.badge {
	vertical-align: middle;
}
</style>
</head>
<body>

<div id="mycontainer">
		
		
	      
	       
		<div class="row" style="width:97%; float:right;">
			<div class="col  col-2 container" id="mySide">
				<tiles:insertAttribute name="side" />
			</div>

			<div class="col m-4 pl-0 pr-4" id="myContent">
				<tiles:insertAttribute name="content" />
			</div>
		</div>
		
		<div id="mysideinfo">
	         <tiles:insertAttribute name="sideinfo" />
	    </div>
	    
	    <div id="myheader">
	         <tiles:insertAttribute name="header" />
	      </div>
		
		
</div>
</body>
</html>