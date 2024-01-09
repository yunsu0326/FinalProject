<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String ctxPath = request.getContextPath(); %>

<style>
	img#profile {
		border-radius: 50%;
		width: 35px;
	}
	
	button { border-style: none;	}
	
	textarea {
	    height: 30px;
	    overflow-y: hidden;
	    resize: none;
	}
	
	a{ color:black;	}
	
	span.move  {cursor: pointer; color: navy;}
	.moveColor {color: #660029; font-weight: bold; background-color: #ffffe6;}
    a {text-decoration: none !important;}
    
   body,h1,h2,h3,h4,h5,h6 {font-family: Verdana, sans-serif;}
	

	#mycontent > div > div{
	position:relative;
	right:200px;
	}
	
	#mycontent > div > div > p:nth-child(4){
	text-align:left;
	}
	
	#mycontent > div > div > span{
	margin-right:1240px;
    }
	
	#mycontent > div > div > div:nth-child(15) > div,
	#mycontent > div > div > div:nth-child(14) > div{
	text-align:left;
	}
	
	#mycontent > div > div > div:nth-child(13) > div,
	#mycontent > div > div > div:nth-child(12) > div{
	text-align:left;	
	}
	
</style>

<script type="text/javascript">
  
$(document).ready(function(){
	
    $("span.move").hover(function(){
		$(this).addClass("moveColor");
	},function(){
		$(this).removeClass("moveColor");
	});
	


  
    
});// end of $(document).ready(function(){})-------------


	   
	//====이전 페이지나 다음페이지로 넘어가기 시작====
    function goView(seq) {
		const goBackURL = "${requestScope.goBackURL}";

		const frm = document.goViewFrm;
		frm.seq.value = seq;
		frm.goBackURL.value = goBackURL;
 
		if(${not empty requestScope.paraMap}) { // 검색조건이 있을 경우 
			frm.searchType.value = "${requestScope.paraMap.searchType}";
			frm.searchWord.value = "${requestScope.paraMap.searchWord}";
		} 
 
		frm.method = "post";
		frm.action = "<%= ctxPath%>/notice_view_2.gw";
		frm.submit();

	}// end of function goView(seq)---------------
	//====이전 페이지나 다음페이지로 넘어가기 끝====
  
		
	function goView2() {

		const goBackURL = "${requestScope.goBackURL}";
		const frm = document.goViewFrm;
	  	//frm.seq.value = input ;
		frm.goBackURL.value = goBackURL;

		frm.method = "post";
		frm.action = "<%= ctxPath%>/notice_view_2.gw";
		frm.submit();
	
    }// end of function goView2(seq)---------------
  

</script>


<div style="width: 90%;" class="text-center container">
<div style="margin: auto; width:1600px;">
<div class='container'>

</div>		
		<c:if test="${not empty requestScope.boardvo}">
		
			<div class="text-left" style="margin-top: 80px;">
		      <div name="subject_name" style="font-weight: bold; font-size: 30px;">${requestScope.boardvo.subject}<br>
			  <div style="margin-bottom:20px;"></div>
		      	<div>
			
				  	
				  <span style="font-size: 15.5px; font-weight:bold; margin-bottom: 10px;">작성자 &nbsp; ${requestScope.boardvo.name}</span>
			      <span style="font-size: 15.5px; margin-bottom: 10px;"><i class="far fa-eye mx-2"></i>${requestScope.boardvo.read_Count}</span>
			      <span style="font-size: 15.5px; margin-bottom: 10px;"><i class="far fa-clock mx-2"></i> ${requestScope.boardvo.reg_Date}</span>
		    	</div>
		    </div>
		  
		
	    <hr style="border-top: solid 1.2px black">
			
		<%-- 글수정, 삭제 버튼은 작성자만 보임 --%>		
		
		
		<!-- 글 내용 -->
		</div>
			<p style="text-align:left;">${requestScope.boardvo.content}</p>
			<div style="height:220px;"></div>
		</c:if>  		
			<!-- 첨부파일 -->		
			<p style='margin-top: 30px;' class='text-small text-right'>
				<span>첨부파일: </span>
	
						<c:if test="${sessionScope.loginuser != null}">
					    <a href="<%= ctxPath%>/download.gw?seq=${requestScope.boardvo.seq}">${requestScope.fileboardvo.orgFilename}</a>  
					</c:if>
					<c:if test="${sessionScope.loginuser == null}">
					    ${requestScope.fileboardvo.orgFilename}
					</c:if>
					<c:forEach items="${fileList}" var="file" varStatus="sts">
					<a href="<%= ctxPath%>/notice_download.gw?fileno=${file.fileno}">${file.orgFilename}</a>
					<c:if test="${sts.count != fn:length(fileList) }">,</c:if>
				    </c:forEach>

			</p>

			<c:if test="${not empty sessionScope.loginuser && sessionScope.loginuser.employee_id == requestScope.boardvo.fk_emp_id}">
				<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/noticeboard.gw'">전체목록보기</button>
			</c:if> 
		
				<button style="	position:relative; right:735px;" type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/noticeboard.gw'">전체목록보기</button>
			
			<%-- 글수정, 삭제 버튼은 작성자만 보임 --%>	
			<c:if test="${not empty sessionScope.loginuser && sessionScope.loginuser.employee_id == requestScope.boardvo.fk_emp_id}">
			    <button type="button" style= "position:relative; right:120px;" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/notice_edit.gw?seq=${requestScope.boardvo.seq}'">글수정하기</button>
			    
			</c:if> 
			
			<c:if test="${not empty sessionScope.loginuser && (sessionScope.loginuser.employee_id == requestScope.boardvo.fk_emp_id or sessionScope.loginuser.gradelevel == 10)}">
				<button style= "position:relative; right:111px;" type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/notice_del.gw?seq=${requestScope.boardvo.seq}'">글삭제하기</button>	
				<span></span>
			</c:if> 

		
		<c:if test="${empty requestScope.boardvo}">
			<div style="padding: 20px 0; font-size: 16pt; color: red;" >존재하지 않습니다</div> 
		</c:if>
								
			<div style='margin-top: 30px;'>
			  	<div style="display: inline-block; float:right"> 
			  		<%-- === #126. 특정글을 조회한 후 "검색된결과목록보기" 버튼을 클릭했을 때 돌아갈 페이지를 만들기 위함. === --%>
				  <button type="button" id="showList" class="btn-secondary listView rounded" onclick="javascript:location.href='<%= ctxPath%>${requestScope.goBackURL}'">검색된 결과 목록보기</button>
			    </div>
			</div>
		
	
	    <hr style="border-top: solid 1.2px black">
	    	<div>
	    	<div style="margin-bottom: 1%; text-align:left;"><i style='vertical-align: bottom;' class="fas fa-sort-up"></i> 다음글제목&nbsp;&nbsp;
	    	<span class="move" onclick="goView('${requestScope.boardvo.nextseq}')">${requestScope.boardvo.nextsubject}</span></div>
	    	</div>
	    	<div>
	    	<div style="margin-bottom: 1%; text-align:left;"><i style='vertical-align: top;' class="fas fa-sort-down"></i> 이전글제목&nbsp;&nbsp;
	    	<span class="move" onclick="goView('${requestScope.boardvo.previousseq}')">${requestScope.boardvo.previoussubject}</span></div>
	    	</div>
	    <hr style="border-top: solid 1.2px black">
	    
		
</div>
</div>	
	

<form name="goViewFrm">
	<input type="hidden" name="seq" />
	<input type="hidden" name="goBackURL" />
	<input type="hidden" name="searchType" />
	<input type="hidden" name="searchWord" />
</form>	