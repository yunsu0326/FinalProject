<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String ctxPath = request.getContextPath();
%>

<style type="text/css">

	#myfooter{
		position:relative;
		top:700px;
	}
	#mycontent > div{
		background-color:white;
	}

</style>    

<script type="text/javascript">

$(document).ready(function(){
	<%-- === 스마트 에디터 구현 시작 === --%>
    var obj = [];
	    
    //스마트에디터 프레임생성
    nhn.husky.EZCreator.createInIFrame({
	    oAppRef: obj,
	    elPlaceHolder: "content", 
	    sSkinURI: "<%= ctxPath%>/resources/smarteditor/SmartEditor2Skin.html",
	    htParams : {
	        // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	        bUseToolbar : true,            
	        // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	        bUseVerticalResizer : true,    
	        // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	        bUseModeChanger : true,
        }
    }); //end of nhn.husky.EZCreator.createInIFrame({ ---------------------
   <%-- === 스마트 에디터 구현 끝 === --%>
     
     
    // ===== 글쓰기 버튼을 눌렀을 때의 유효성 검사와 전송 시작 =====
    $("button#btnWrite").click(function(){
	  
		obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
  	   
	    // 글제목 유효성 검사
	    const subject = $("input:text[name='subject']").val().trim();
	   
        if(subject == "") {
	        alert("글제목을 입력하세요!!");
	        return; // 종료
	    } 
	     
	   <%-- === 글내용 유효성 검사(스마트 에디터 사용 할 경우) 시작 === --%>
	   let contentval = $("textarea#content").val();
		  
	   // 글내용 유효성 검사 하기 
	   contentval = contentval.replace(/&nbsp;/gi, ""); 
       contentval = contentval.substring(contentval.indexOf("<p>")+3);
       contentval = contentval.substring(0, contentval.indexOf("</p>"));
	                
       if(contentval.trim().length == 0) {
      	   alert("글내용을 입력하세요!!");
           return;
       }
	   <%-- === 글내용 유효성 검사(스마트 에디터 사용 할 경우) 끝 === --%> 
		
	   // 글암호 유효성 검사
	   const pw = $("input:password[name='pw']").val();
	   if(pw == "") {
	       alert("글암호를 입력하세요!!");
	  	   return; // 종료
	   }
		  
	   // 폼(form)을 전송(submit)
	   const frm = document.addFrm;
	   frm.method = "post";
	   frm.action = "<%= ctxPath%>/noticeaddEnd.gw";
	   frm.submit();
	   
    }); //end of  $("button#btnWrite").click(function(){ -------------
    // ===== 글쓰기 버튼을 눌렀을 때의 유효성 검사와 전송 끝 =====
	  
});// end of $(document).ready(function(){})-----------

</script>


	<div style="display: flex; background-color:white;">
		<div style="margin: auto; padding-left: 3%; background-color:white;">
	    	<h2 style="margin-bottom: 30px;">공지사항 글쓰기</h2>
		 	 <%-- ===== 파일첨부하기 시작 ====== --%>
		     <form name="addFrm" enctype="multipart/form-data">
		     	<table style="width: 1024px" class="table table-bordered">
			        <tr>
						<th style="width: 15%; background-color: #DDDDDD;">성명</th>
						<td>
						    <input type="text" name="fk_emp_id" value="${sessionScope.loginuser.employee_id}" readonly />
						    <input type="text" name="name" value="${sessionScope.loginuser.name}" readonly /> 
						</td>
					</tr>
					<tr>
						<th style="width: 15%; background-color: #DDDDDD;">제목</th>
						<td>
		     		    	<input type="text" name="subject" size="100" maxlength="200" /> 
						</td>
					</tr>
					<tr>
						<th style="width: 15%; background-color: #DDDDDD;">내용</th> 
						<td>
						    <textarea style="width: 100%; height: 612px;" name="content" id="content"></textarea>
						</td>
					</tr>
					<%-- ===== 파일첨부 타입 추가하기 ===== --%>
					<tr>
						<th style="width: 15%; background-color: #DDDDDD;">파일첨부</th>  
						<td>
						    <input type="file" name="attach" />
						</td>
					</tr>
					<tr>
						<th style="width: 15%; background-color: #DDDDDD;">글암호</th> 
						<td>
						    <input type="password" name="pw" maxlength="20" />
						</td>
					</tr>	
		        </table>
		        
		        <div style="margin: 20px;">
		            <button type="button" class="btn btn-secondary btn-sm mr-3" id="btnWrite">글쓰기</button>
		            <button type="button" class="btn btn-secondary btn-sm" onclick="javascript:history.back()">취소</button>  
		        </div>
		        
		     </form>
	         <%-- ===== 파일첨부 하기 끝 ====== --%>
		</div>
	</div>
    