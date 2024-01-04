<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
%>

<style type="text/css">
#Navbar > li > a {
	color: gray;
	font-weight: bold;
	font-size: 17pt;
}
#Navbar > li > a:hover {
	color: black;
}

/* 휴지통 버튼 */
i.fa-angle-left:hover, i.fa-angle-right:hover {
	color: black;
}

i.fa-angle-left, i.fa-angle-right {
	cursor: pointer;
	color: gray;
}


</style>

<script type="text/javascript">
$(document).ready(function(){
	
	var now = new Date();	
	var year = now.getFullYear();  // 현재 해당 년도
	var month = now.getMonth()+1;  // 현재 해당 월
	var day = now.getDate(); 	   // 현재 해당 일자
	
	var hour = now.getHours(); 	   // 현재 해당 시각
	var minute = now.getMinutes(); // 현재 해당 분
	var second = now.getSeconds(); // 현재 해당 초
	
	// 한 자리 수일 때 앞에 0 붙이기
	minute = (minute < 10) ? '0' + minute : minute;
	second = (second < 10) ? '0' + second : second;

	const thisMonth = year+"-"+month; 			 // 2023-12
	
	const FullDate = year+"-"+month+"-"+day; 	 // 2023-12-27
	const FullTime = hour+":"+minute+":"+second; // 16:31:25
	
	$("div#thisMonth").text(thisMonth); // 현재 년도와 해당 월의 값을 꽂아줌
	
	// '<' 클릭시 이전달로 바꾸기
	$("#prevMonth").click(function(){ // ------------------------------------
		
		let monthVal = $("#thisMonth").text();
		// console.log(monthVal);
		
		monthVal = new Date(monthVal.substr(0,4), parseInt(monthVal.substr(5,2))-2);
		// console.log(monthVal);
		
		let newMonth;
		if( parseInt(monthVal.getMonth())+1 <10 ){
			newMonth = monthVal.getFullYear()+"-0"+(parseInt(monthVal.getMonth())+1);
		}
		else {
			newMonth = monthVal.getFullYear()+"-"+(parseInt(monthVal.getMonth())+1);
		}
		
		$("#thisMonth").text(newMonth);
		getMyWorkList();
		
	}); // end of $("#prevMonth").click() -----------------------------------
	
	// '>' 클릭시 다음달로 바꾸기
	$("#nextMonth").click(function(){ // ------------------------------------
		
		let monthVal = $("#thisMonth").text();
		
		if(thisMonth != monthVal){			
			monthVal = new Date(monthVal.substr(0,4), parseInt(monthVal.substr(5,2)));
			// console.log(monthVal);
			
			let newMonth;
			if( parseInt(monthVal.getMonth())+1 <10 ){
				newMonth = monthVal.getFullYear()+"-0"+(parseInt(monthVal.getMonth())+1);
			}
			else {
				newMonth = monthVal.getFullYear()+"-"+(parseInt(monthVal.getMonth())+1);
			}
			
			$("#thisMonth").text(newMonth);
			getMyWorkList();
		}
		
	}); // end of $("#nextMonth").click() ------------------------------------
	
	$("#qwer").click(function(){
		alert($("input[name='employee_id']").val());
	})
	
});
function myDetailWorkList(){
	
}
</script>


<div id="container" style="width: 75%; margin:0 auto; margin-top:100px;">
	
    <%-- 상단 메뉴바 시작 --%>
    <nav class="navbar navbar-expand-lg mt-5 mb-4" style=" margin-left: 2%; margin-right: 5%; width: 80%; background-size: cover; background-position: center; background-repeat: no-repeat; height: 70px">
		<div class="collapse navbar-collapse">
			<ul class="navbar-nav" id="Navbar">
				<li class="nav-item">
					<a class="nav-link" href="<%= ctxPath %>/my_work.gw">근퇴조회</a>
				</li>
				
				<li class="nav-item">
					<a class="nav-link ml-5" href="<%= ctxPath %>/my_work_manage.gw">근퇴관리</a>
				</li>
				
				<c:if test="${sessionScope.loginuser.gradelevel >= 5}">
					<li class="nav-item">
						<a class="nav-link ml-5" href="<%= ctxPath %>/dept_work_manage.gw">부서관리</a>
					</li>
				</c:if>
				
			</ul>
		</div>
	</nav>
	
	<%-- 본문 내용 [시작] --%>
    <div class="p-4" style="display: flex; margin-left: 40%;">
        <div>
            <span><i class="fa-solid fa-angle-left" style="font-size: 20pt;" id="prevMonth"></i></span>
        </div>
        <div class="mx-3" style="font-size: 20pt; position: relative; bottom: 6px;" id="thisMonth"></div>
        <div>
            <span><i class="fa-solid fa-angle-right" style="font-size: 20pt;" id="nextMonth"></i></span>
        </div>
    </div>
    
    <div class="mt-5">
    	<table class="table table-hover" style="width: 20%;">
    		<tr style="background-color: #e3f2fd;">
    			<th>직급</th>
    			<th>이름</th>
    		</tr>
    		<c:if test="${not empty requestScope.myDeptEmpList}">
    			<c:forEach var="myDeptEmpList" items="${requestScope.myDeptEmpList}">
    				<tr id="qwer">
    					<td>${myDeptEmpList.job_name}</td>
    					<td>${myDeptEmpList.name}</td>
    				</tr>
    				<input type="hidden" name="employee_id" value="${myDeptEmpList.employee_id}" />
    			</c:forEach>
    		</c:if>
    	</table>
    </div>

    
<%-- 본문 내용 [끝] --%>
   
</div>





