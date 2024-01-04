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
i#deleteIcon {
  color: gray;
}

i#deleteIcon:hover {
  color: black;
  cursor: pointer;
}
</style>

<script type="text/javascript">
$(document).ready(function(){
	
	$("td#listHide").hide();
	$("td#seqHide").hide();
	
	// 휴지통 클릭시 삭제하기
	$("i#deleteIcon").click(function() {
		
		var vac_approved = confirm("근무신청을 철회 하시겠습니까?");
	
		if(vac_approved) {
			
	   		const fk_employee_id = $("td#listHide").text();
	   		const work_request_seq = $("td#seqHide").text();
	   		
	   		$("input:hidden[name='fk_employee_id']").val(fk_employee_id);
	   		$("input:hidden[name='work_request_seq']").val(work_request_seq);
	   		
			const frm = document.workRequestFrm; 
			
			frm.method = "post";
			frm.action = "<%= ctxPath %>/work_request_delete.gw";
			frm.submit();
		}
	}); 
	
});
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
	
	<!-- 대기중인 근무신청 [시작] -->
   <div class='listContainer border mb-5' style='width: 93%; margin: 0 auto; padding: 20px; border-radius: 10px;'>
      <h5 class='mb-3 ml-5' style="font-weight: bold;">근태 신청 내역</h5>
      <div class="max-form">
      <table class="table ml-5 text-center">
         <thead>
            <tr class='row table_tr' style="background-color: #e3f2fd; width: 94%;">
               	<th class='col'>&nbsp;&nbsp;no</th>
               	<th class='col'>근무일자</th>
	        	<th class='col'>시작시간</th>
	            <th class='col'>종료시간</th>
	            <th class='col'>근무종류</th>
	            <th class='col'>장소</th>
	            <th class='col'>사유</th>
	            <th class='col'>신청일자</th>
	            <th></th>
            </tr>
         </thead>
         <tbody>
            <c:if test="${not empty requestScope.workRequestList}">
       		  <c:forEach var="workRequestList" items="${requestScope.workRequestList}" varStatus="status">
		        <tr class='row' style="width: 94%;">
		        	<td class='col'>&nbsp;&nbsp;${status.index+1}</td>
		        	<td class='col'>${workRequestList.work_request_start_date}</td>
		        	<td class='col'>${workRequestList.work_request_start_time}</td>
		            <td class='col'>${workRequestList.work_request_end_time}</td>
		            
		            <c:choose>
						<c:when test="${workRequestList.work_request_type == '1'}"> 
							<td class='col'>외근</td>
						</c:when>	
						<c:when test="${workRequestList.work_request_type == '2'}"> 
							<td class='col'>출장</td>
						</c:when>
						<c:when test="${workRequestList.work_request_type == '3'}"> 
							<td class='col'>재택</td>
						</c:when>
					</c:choose>
		            <td class='col'>${workRequestList.work_request_place}</td>
		            <td class='col'>${workRequestList.work_request_reason}</td>
		            <td class='col'>${workRequestList.work_request_date}</td>
		            <td class='col' id="listHide">${workRequestList.fk_employee_id}</td>
		            <td class='col' id="seqHide">${workRequestList.work_request_seq}</td>
		            <td><i class="fa-solid fa-trash-can" style="font-size:13pt;" id="deleteIcon"></i></td>
		        </tr>
	        </c:forEach>
        </c:if>
        <c:if test="${empty requestScope.workRequestList}">
        	<tr>
        		<td class='col'>데이터가 없습니다.</td>
        	</tr>
        </c:if>
         </tbody>
      </table>
      <form name="workRequestFrm">
      	 <input type="hidden" name="work_request_seq" />
      	 <input type="hidden" name="fk_employee_id" />
      </form>
     </div>
   </div>
   <!-- 대기중인 근무신청 [끝] -->
   
</div>





