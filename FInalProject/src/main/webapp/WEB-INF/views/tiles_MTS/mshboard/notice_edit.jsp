<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">

</style>    

<script type="text/javascript">

    $(document).ready(function(){
	  
	  <%-- === 스마트 에디터 구현 시작 === --%>
	 	//전역변수
		var obj = [];
	    
	    //스마트에디터 프레임생성
	    nhn.husky.EZCreator.createInIFrame({
	        oAppRef: obj,
	        elPlaceHolder: "content", // id가 content인 textarea에 에디터를 넣어준다.
	        sSkinURI: "<%= ctxPath%>/resources/smarteditor/SmartEditor2Skin.html",
	        htParams : {
	            // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseToolbar : true,            
	            // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseVerticalResizer : true,    
	            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	            bUseModeChanger : true,
	        }
	    });
	   <%-- === 스마트 에디터 구현 끝 === --%>
	  
	  // 수정완료 버튼
	  $("button#btnUpdate").click(function(){
		  
			 <%-- === 스마트 에디터 구현 시작 === --%>
			 // id가 content인 textarea에 에디터에서 대입
		     obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
			 <%-- === 스마트 에디터 구현 끝 === --%>
			  
			  // 글제목 유효성 검사
			  const subject = $("input:text[name='subject']").val().trim();
			  if(subject == "") {
			  	 alert("글제목을 입력하세요!!");
			  	 return; // 종료
			  }
			  
		      <%-- === 글내용 유효성 검사(스마트 에디터 사용 할 경우) 시작 === --%>
			   let contentval = $("textarea#content").val();
		       
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
			else {
				if("${requestScope.boardvo.pw}" != pw) {
					alert("입력하신 글암호가 올바르지 않습니다.!!");
					return; // 종료 
				}
			}
			  
			// 폼(form)을 전송(submit)
			const frm = document.editFrm;
			frm.method = "post";
			frm.action = "<%= ctxPath%>/notice_editEnd.gw";
		    frm.submit();
	    });
	  	
    });// end of $(document).ready(function(){})-----------
function deletefile(seq) {

	if (confirm("정말로 파일을 삭제하시겠습니까? (경고! 이 버튼을 누르면 파일이 영구 삭제됩니다)")) {
        $.ajax({
            url: "<%= ctxPath%>/notice_delete_file.gw",
            type: "get",
            data: { "seq": seq },
            dataType: "json",
            success: function (json) {
          
            	if(json.success){
	            	alert("파일삭제!"); 
	            	$("#filedel").hide();
	            	$("#mycontent > div > div > form > div > a").empty();
	            	
	            	// 파일을 선택하는 input 태그를 추가합니다.
                    var inputFile = $("<input type='file' name='attach'>");
                    $("#attach_file").append(inputFile);
            	}
	            else {
	            	alert("파일삭제실패!"); 
	 			}
            },
            error: function (request, status, error) {
            	alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
            }
        });
	}
}// end of function deletefile(seq) {--------------
</script>


<div style="display: flex;">
  <div style="margin: auto; padding-left: 3%;">
     
     <h2 style="margin-bottom: 30px;">글수정</h2>
     
     <form name="editFrm" enctype="multipart/form-data">
        <table style="width: 1024px" class="table table-bordered">
			<tr>
				<th style="width: 15%; background-color: #DDDDDD;">성명</th>
				<td>
				    <input type="hidden" name="seq" value="${requestScope.boardvo.seq}" readonly />
				    <input type="text" name="name" value="${sessionScope.loginuser.name}" readonly /> 
				</td>
			</tr>
			
			<tr>
				<th style="width: 15%; background-color: #DDDDDD;">제목</th>
				<td>
				    <input type="text" name="subject" size="100" maxlength="200" value="${requestScope.boardvo.subject}" /> 
				</td>
			</tr>
			
			<tr>
				<th style="width: 15%; background-color: #DDDDDD;">내용</th> 
				<td>
				    <textarea style="width: 100%; height: 612px;" name="content" id="content">${requestScope.boardvo.content}</textarea>
				</td>
			</tr>
			
			<tr>
				<th style="width: 15%; background-color: #DDDDDD;">글암호</th> 
				<td>
				    <input type="password" name="pw" maxlength="20" />
				</td>
			</tr>	
			
			<c:if test="${empty requestScope.boardvo.file_Name}">	
				<tr>
					<th style="width: 15%; background-color: #DDDDDD;">파일첨부 교체</th>  
					<td>
					    <input type="file" name="attach" />
					</td>
				</tr>
			</c:if>
        </table>
        
        <div class="attach_file" id="attach_file" name="attach_file"></div>
        
        
        <div style="margin: 20px;">
        	<c:if test="${not empty requestScope.boardvo.file_Name}">	                                           
	            <button type="button" class="btn btn-secondary btn-sm mr-3" id="filedel" onclick="deletefile('${requestScope.boardvo.seq}')">첨부파일 삭제</button>
	        </c:if>
	        <a>${requestScope.boardvo.file_Name}</a>
            <button type="button" class="btn btn-secondary btn-sm mr-3" id="btnUpdate">수정완료</button>
            <button type="button" class="btn btn-secondary btn-sm" onclick="javascript:history.back()">취소</button>  
        </div>
        
     </form>
     
  </div>
</div>







