<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	String ctxPath = request.getContextPath();
%>

<style type="text/css">
	span.move  {cursor: pointer; color: navy;}
	.moveColor {color: #660029; font-weight: bold; background-color: #ffffe6;}
    a {text-decoration: none !important;}
</style>

<script type="text/javascript">
$(document).ready(function(){  

  
});// end of $(document).ready(function(){})-----------


//====이전 페이지나 다음페이지로 넘어가기 시작====
function goView(seq) {
	const goBackURL = "${requestScope.goBackURL}";

	const frm = document.goViewFrm;
	frm.seq.value = seq;
	frm.goBackURL.value = goBackURL;
		const a = "${requestScope.paraMap.searchType}"; 
		console.log(a);
		
	if(${not empty requestScope.paraMap}) { // 검색조건이 있을 경우 
		frm.searchType.value = "${requestScope.paraMap.searchType}";
		frm.searchWord.value = "${requestScope.paraMap.searchWord}";
	} 

	frm.method = "post";
	frm.action = "<%= ctxPath%>/notice_view_2.gw";
	frm.submit();

}// end of function goView(seq)---------------
//====이전 페이지나 다음페이지로 넘어가기 끝====

	
</script>


<div style="display: flex;">
<div style="margin: auto; padding-left: 3%;">
	<h2 style="margin-bottom: 30px;">글내용보기</h2>
	
	<c:if test="${not empty requestScope.boardvo}">
		<table class="table table-bordered table-dark" style="width: 1024px;">
	    	<tr>
	   			<th style="width: 15%">글번호</th>
	   	    	<td>${requestScope.boardvo.seq}</td>
	   	    </tr>	
	   	    <tr>
	   		  	<th>성명</th>
	   	        <td>${requestScope.boardvo.name}</td>
	   	    </tr> 
	   	     <tr>
	   		  	<th>부서</th>
	   	        <td>${requestScope.boardvo.department_name}</td>
	   	    </tr> 
	   	    <tr>
	   		    <th>제목</th>
	   	        <td>${requestScope.boardvo.subject}</td>
	   	    </tr>
	   	    <tr>
	   		  	<th>내용</th>
	   	        <td>
	   	        	<p style="word-break: break-all;">${requestScope.boardvo.content}</p>
	   	        </td>
	   	    </tr> 
	   	    <tr>
	   		  <th>조회수</th>
	   	      <td>${requestScope.boardvo.read_Count}</td>
	   	    </tr> 
	   	    <tr>
	   		  <th>날짜</th>
	   	      <td>${requestScope.boardvo.reg_Date}</td>
	   	    </tr>
	   	    <%-- === 첨부파일 이름 및 파일크기를 보여주고 첨부파일을 다운로드 되도록 만들기 === --%>
		    <tr>
					<th>첨부파일</th>
				<td>
					<c:if test="${sessionScope.loginuser != null}">
					    <a href="<%= ctxPath%>/notice_download.gw?seq=${requestScope.boardvo.seq}">${requestScope.boardvo.org_Filename}</a>  
					</c:if>
					<c:if test="${sessionScope.loginuser == null}">
					    ${requestScope.boardvo.org_Filename}
					</c:if>
				</td>
			</tr>
			<tr>
				<th>파일크기(bytes)</th>
				<td><fmt:formatNumber value="${requestScope.boardvo.file_Size}" pattern="#,###" /></td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${empty requestScope.boardvo}">
		<div style="padding: 20px 0; font-size: 16pt; color: red;" >존재하지 않습니다</div> 
	</c:if>
	
	<div class="mt-5">
		<%-- 글조회수 1증가를 위해서 view.action 대신에 view_2.action 으로  바꾼다. --%>
		<div style="margin-bottom: 1%;">이전글제목&nbsp;&nbsp;<span class="move" onclick="goView('${requestScope.boardvo.previousseq}')">${requestScope.boardvo.previoussubject}</span></div>
		<div style="margin-bottom: 1%;">다음글제목&nbsp;&nbsp;<span class="move" onclick="goView('${requestScope.boardvo.nextseq}')">${requestScope.boardvo.nextsubject}</span></div>	
		<br>
		
		<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/noticeboard.gw'">전체목록보기</button>
		
		<%-- === #126. 특정글을 조회한 후 "검색된결과목록보기" 버튼을 클릭했을 때 돌아갈 페이지를 만들기 위함. === --%>
		<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>${requestScope.goBackURL}'">검색된결과목록보기</button>
		
		<c:if test="${not empty sessionScope.loginuser}">
		    <c:choose>
		        <c:when test="${sessionScope.loginuser.employee_id == requestScope.boardvo.fk_emp_id or sessionScope.loginuser.gradelevel == 10}">
		            <button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/notice_edit.gw?seq=${requestScope.boardvo.seq}'">글수정하기</button>
		            <button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/notice_del.gw?seq=${requestScope.boardvo.seq}'">글삭제하기</button>
		        </c:when>
		        <c:otherwise>
         		</c:otherwise>
		    </c:choose>
		</c:if>
	
	</div>
	
</div>
</div>	
	
<form name="goViewFrm">
	<input type="hidden" name="seq" />
	<input type="hidden" name="goBackURL" />
	<input type="hidden" name="searchType" />
	<input type="hidden" name="searchWord" />
</form>	
