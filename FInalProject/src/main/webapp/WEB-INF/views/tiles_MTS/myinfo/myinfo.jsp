<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>

<style type="text/css">

body {
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    margin: 0;
    padding: 0;
}

table.myinfo_tbl {
    width: 100%;
    border-collapse: collapse;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 8px 12px 0 rgba(0, 0, 0, 0.1);
}


table.myinfo_tbl th {
	border: solid 1px #ddd;
    background-color: #ffffff;
    text-align: center;
    padding: 12px;
    font-weight: bold;
    width: 15%;
}

table#table1 {
	width: 70%; 
	margin-left: 10px;
}

table#table2 {
	width: 82.3%; 
	margin: 3% auto 0;
}

table#table1 td {
    border: solid 1px #ddd;
    padding: 8px;
    text-align: center;
    width: 35%;
}

table#table2 td {
    border: solid 1px #ddd;
    padding-left: 3%;
    width: 35%;
}

div#photo {
	border: solid 1px #ddd;
	width: 150px; 
	height: 150px; 
	vertical-align: top; 
	text-align: center;
	border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 8px 12px 0 rgba(0, 0, 0, 0.1);
}

div#top {
	text-align: center; 
	display: flex; 
	align-items: center;
}

h1 {
	margin: 2% 0 2% 10%; 
	margin-right: auto;
}

button#btn_edit {
	margin-right: 1%;
}

button#btn_pwd {
	margin-right: 10%;
}

</style>


<script type="text/javascript">

$(document).ready(function(){
	
	
	
}); // end of $(document).ready(function(){})----------------



</script>

	<div id="top">
	    <h1>내정보</h1>
	    <button type="button" id="btn_edit" onclick="javascript:location.href='<%= ctxPath%>/myinfoEdit.gw?employee_id=${requestScope.loginuser.employee_id}'">수정하기</button>
	    <button type="button" id="btn_pwd" data-toggle="modal" data-target="#passwdFind" data-dismiss="modal" data-backdrop="static">비밀번호 변경</button>
	</div>
	
	<div style="display: flex; justify-content: center;">
		
		<div id="photo">
			<img src="<%= ctxPath%>/resources/images/${requestScope.loginuser.photo}" style="width: 148px; height: 150px;" />
		</div>
			
		<table id="table1" class="myinfo_tbl">
	 
			<tr>
				<th>성명</th>
				<td>${requestScope.loginuser.name}</td>
				<th>사원번호</th>
				<td>${requestScope.loginuser.employee_id}</td>
			</tr>
	 
			<tr>
				<th>부서명</th>
				<td>${requestScope.dept_team.DEPARTMENT_NAME}</td>
				<th>팀명</th>
				<td>${requestScope.dept_team.TEAM_NAME}</td>
			</tr>
			
			<tr>
				<th>직급</th>
				<td>${requestScope.dept_team.JOB_NAME}</td>
				<th>입사일자</th>
				<td>${requestScope.loginuser.hire_date}</td>
			</tr>
			
		</table>
		
	</div>
	
	<table id="table2" class="myinfo_tbl">
	
		<tr>
			<th>이메일</th>
			<td>${requestScope.loginuser.email}</td>
			<th>연락처</th>
			<td>${requestScope.loginuser.phone}</td>
		</tr>

		<tr>
			<th>생년월일</th>
			<td>${requestScope.gender_birthday.BIRTHDAY.substring(0, 4)}년 ${requestScope.gender_birthday.BIRTHDAY.substring(4, 6)}월 ${requestScope.gender_birthday.BIRTHDAY.substring(6, 8)}일</td>
			<th>성별</th>
			<td>${requestScope.gender_birthday.GENDER}</td>
		</tr>
		
		<tr>
			<th>우편번호</th>
			<td>${requestScope.loginuser.postcode}</td>
			<th>주소 참고사항</th>
			<td>${requestScope.loginuser.extraaddress}</td>
		</tr>
		
		<tr>
			<th>주소</th>
			<td>${requestScope.loginuser.address}</td>
			<th>상세주소</th>
			<td>${requestScope.loginuser.detailaddress}</td>
		</tr> 
		
		
		<tr>
			<th>은행명</th>
			<td>${requestScope.loginuser.bank_name}</td>
			<th>계좌번호</th>
			<td>${requestScope.loginuser.bank_code}</td>
		</tr>
		
	</table>
	
	
	
	<div class="modal fade" id="passwdFind">
		<div class="modal-dialog">
			<div class="modal-content">
			
				<!-- Modal header -->
				<div class="modal-header">
					<h4 class="modal-title">비밀번호 변경</h4>
					<button type="button" class="close passwdFindClose" data-dismiss="modal">&times;</button>
				</div>
				
				<!-- Modal body -->
				<div class="modal-body">
					<div id="pwFind">
						<iframe style="border: none; width: 100%; height: 500px;" src="<%= ctxPath%>/pwdUpdate.gw">  
						</iframe>
					</div>
				</div>
			
			</div>
		</div>
	</div>



