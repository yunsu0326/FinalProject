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
	goViewComment(1); // 페이징 처리한 댓글 읽어오기 
	
    $("span.move").hover(function(){
		$(this).addClass("moveColor");
	},function(){
		$(this).removeClass("moveColor");
	});
	
	$("input:text[name='content']").bind("keydown", function(e){
		if(e.keyCode == 13) { // 엔터
			goAddWrite();
		}
	});
	  
});// end of $(document).ready(function(){})-------------

  
  // Function Declaration
  // == 댓글쓰기 == 
  function goAddWrite() {
	  
	  const comment_content = $("input:text[name='content']").val().trim();
	  if(comment_content == "") {
	      alert("댓글 내용을 입력하세요!!");
		  return; // 종료
	  }
	  
	  if($("input:file[name='attach']").val() == "") {
		  // 첨부파일이 없는 댓글쓰기인 경우 
		  goAddWrite_noAttach();
	  }
	  
	  else {
		  // 첨부파일이 있는 댓글쓰기인 경우
		  goAddWrite_withAttach();
	  }
	  
  }// end of function goAddWrite(){}------------------
  
	// 첨부파일이 없는 댓글쓰기인 경우 
	function goAddWrite_noAttach() {
		  
		const queryString = $("form[name='addWriteFrm']").serialize();
		  
		$.ajax({
			url:"<%= ctxPath%>/addComment.gw",
		    data:queryString,
			type:"post",
			dataType:"json",
			success:function(json){
			    goViewComment(1); // 페이징 처리한 댓글 읽어오기
				$("input:text[name='content']").val("");
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
		  
	}// end of function goAddWrite_noAttach()-----------
  
  
  // ==== 파일첨부가 있는 댓글쓰기  시작==== // 
    function goAddWrite_withAttach() {
	  
	    const queryString = $("form[name='addWriteFrm']").serialize();
	  
	    $("form[name='addWriteFrm']").ajaxForm({
	        url:"<%= ctxPath%>/addComment_withAttach.gw",
	        data:queryString,
		    type:"post",
		    enctype:"multipart/form-data",
		    dataType:"json",
		    success:function(json){
		      
			    goViewComment(1); // 페이징 처리한 댓글 읽어오기

			    $("input:text[name='content']").val("");
			    $("input:file[name='attach']").val("");
		    },
		    error: function(request, status, error){
		        alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		    }
	    });
	  
	    $("form[name='addWriteFrm']").submit();
	  
    }// end of function goAddWrite_withAttach()-----------
     // ==== 파일첨부가 있는 댓글쓰기  끝==== // 
    
    
    // =====  Ajax 로 불러온 댓글내용들을 페이징 처리 하기 시작 ===== //
    function goViewComment(currentShowPageNo) {
    	  
		$.ajax({
		    url:"<%= ctxPath%>/commentList.gw",
		    data:{"parentSeq":"${requestScope.boardvo.seq}",
			      "currentShowPageNo":currentShowPageNo},
		    dataType:"json",
	  
		    success:function(json){
		    
			     let v_html = "";
			     if(json.length > 0) {
				     $.each(json, function(index, item){
					     v_html += "<tr>"; 
					     v_html +=    "<td class='text-center'>"+(index+1)+"</td>";
					     v_html +=    "<td id='review"+item.seq+"'>"+item.content+"</td>";            
					     <%-- === 첨부파일 기능이 추가된 경우 시작 === --%>  
					     if(${sessionScope.loginuser != null}) {
					         v_html += "<td><a href='<%= ctxPath%>/downloadComment.gw?seq="+item.seq+"'>"+ item.orgFilename +"</a></td>";   
					     }
					     else {
					         v_html += "<td>"+ item.orgFilename +"</td>";
					     }
					               
					     if(item.fileSize.trim() == "") {
					         v_html += "<td></td>";
					     }
					     else {
					         v_html += "<td>"+ Number(item.fileSize).toLocaleString('en') +"</td>"; 
					     }
					     <%-- === 첨부파일 기능이 추가된 경우 끝 === --%>
					              
					     v_html += "<td class='text-center'>" + item.name + "</td>";
					     v_html += "<td class='text-center'>" + item.regdate + "</td>";
					     v_html += "<td id='review2"+item.seq+"'>"+ "     "+ "</td>";
					          
					     if (${sessionScope.loginuser != null} && "${sessionScope.loginuser.email}" == item.fk_email) {
						      if(item.orgFilename.trim() != ""){
						          v_html += "<td class='text-center'><button type='button' name= 'delbtn"+item.seq+"' id = 'delbutton' class='btn btn-secondary btn-sm mr-3' onclick='deleteComment(" + item.seq + ")'>댓글삭제하기</button></td>";
						          v_html += "<td class='text-center'><button type='button' name= 'update"+item.seq+"' id = 'commonbutton'"+item.seq+" class='btn btn-secondary btn-sm mr-3' onclick='updateMyReview(" + item.seq + ")'>댓글 내용 수정하기</button></td>";
						      }
						      else{
						          v_html += "<td class='text-center'><button type='button' name= 'delbtn"+item.seq+"' id = 'delbutton' class='btn btn-secondary btn-sm mr-3' onclick='deleteCommentNoFile(" + item.seq + ")'>댓글삭제하기</button></td>";
						          v_html += "<td class='text-center'><button type='button' name= 'update"+item.seq+"' id = 'commonbutton'"+item.seq+" class='btn btn-secondary btn-sm mr-3' onclick='updateMyReview(" + item.seq + ")'>댓글 내용 수정하기</button></td>";
						      }
					     }
					      
					     else{
					           v_html += "<td></td>";
					     }
					    v_html += "</tr>";	            
				 	});
			 	}
				else{
					v_html =  "<tr>";
					v_html +=   "<td colspan='4' class='text-center'>댓글이 없습니다</td>";
				    v_html += "</tr>";
				}
				 
				 $("tbody#commentDisplay").html(v_html);
				 // 페이지바 함수 호출
				 makeCommentPageBar(currentShowPageNo);
			  },	 	  
			  error: function(request, status, error){
				 alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			  }
	      });	  
     }// end of function goViewComment(currentShowPageNo)--------   
  // =====  Ajax 로 불러온 댓글내용들을 페이징 처리 하기 끝 ===== //
  
  
    // ==== 댓글을 수정하기 시작 ========
    function updateMyReview(seq) {
		 
		$("button[name='delbtn"+seq+"']").hide();
		$("button[name='update"+seq+"']").hide();

		const review_contents = $("td#review"+seq).text(); 
		var k_html = "<form name='review_update_Frm' enctype='multipart/form-data'>";
		k_html += "<input name='content' id='content' type='text'>";
		k_html += "</form>";
		var previousContent = review_contents; 

	     $("td#review"+seq).html(k_html); 

	     $("td#review"+seq+" input").val(previousContent);

	     $("#btnReviewUpdate_OK").on("click", function() {
	     	let comt = $("input#one_review").val();
	     }); // $("#btnReviewUpdate_OK").on("click", function() { ----------

	 let v_html = "";
	 v_html += "<td><button type='submit' name='ok_update"+seq+"' class='btn btn-sm btn-outline-secondary' id='btnReviewUpdate_OK'>수정완료</button></td>"; 
	 v_html += "<td><button type='button' name='no_update"+seq+"' class='btn btn-sm btn-outline-secondary' id='btnReviewUpdate_NO'>수정취소</button></td>";

	 // 원래의 제품후기 엘리먼트에 위에서 만든 "후기수정" 을 위한 엘리먼트로 교체하기
	 $("td#review2"+seq).html(v_html);
	
	       // ===== 수정취소 버튼 클릭시 =====
	     $(document).on("click", "button#btnReviewUpdate_NO", function(){
	         $("td#review"+seq).html(previousContent);  // 원래의 제품후기 엘리먼트로 복원하기
	         $("button[name='no_update"+seq+"']").hide();
	         $("button[name='ok_update"+seq+"']").hide();
	         $("button[name='delbtn"+seq+"']").show(); // "후기수정" 글자 보여주기
	         $("button[name='update"+seq+"']").show();
	      }); //end of $(document).on("click", "button#btnReviewUpdate_NO", function(){--------

	       // ===== 수정완료 버튼 클릭시 =======
	       $(document).on("click", "button#btnReviewUpdate_OK", function(){
	    	  
	           $.ajax({
	               url:"<%= ctxPath%>/reviewUpdate.gw",
	               type:"post",
	               data:{"seq":seq ,"content":$("input#content").val()},
	               dataType:"json",
	               success:function(json){
	                 
		               if(json.suc) {
		                   goViewComment(1); // 페이징 처리한 댓글 읽어오기
		               }
		               else{
		            		alert("댓글실패!"); // 페이징 처리한 댓글 읽어오기
		 			  	}

	           		},
	           error: function(request, status, error){
	               alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	           }
	         });
	          
	       }); //end of function updateMyReview(seq) { -----------
	 // ===============댓글을 수정 끝===================
	}// end of function updateMyReview(index, review_seq)------------------- 
  
  
  	// ===== 첨부파일 있는 댓글 삭제하기 시작 =======
    function deleteComment(seq) {
	    if (confirm("정말로 댓글을 삭제하시겠습니까?")) {
	        $.ajax({
	            url: "<%= ctxPath%>/comment_Del.gw",
	            type: "post",
	            data: { "seq": seq },
	            dataType: "json",
	            success: function (json) {
	          
	            	if(json.success){
		            	goViewComment(1); // 페이징 처리한 댓글 읽어오기
		            }
		            else {
		            	alert("댓글실패!"); // 페이징 처리한 댓글 읽어오기
		 			}
	            },
	            error: function (request, status, error) {
	            	alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
	            }
	        });
	    }
	}// end of function deleteComment(seq) {--------------
     // ===== 첨부파일 있는 댓글삭제하기 끝 =======
	
	//===== 첨부파일 없는 댓글 삭제하기 삭제하기 시작 =======
	function deleteCommentNoFile(seq) {
		if (confirm("정말로 댓글을 삭제하시겠습니까?")) {
			$.ajax({
		   	    url: "<%= ctxPath%>/comment_Del_nofile.gw",
		        type: "post",
		        data: { "seq": seq },
		        dataType: "json",
		        success: function (json) {
  	
		        if(json.success){
	          		goViewComment(1); // 페이징 처리한 댓글 읽어오기
	            }
	          	else{
	          		goViewComment(1); // 페이징 처리한 댓글 읽어오기
				}
	        
		        },
	        	error: function (request, status, error) {
	        		alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
	            }
	        });
	    }
    }// end of function deleteCommentNoFile(seq) { ----------
	//===== 첨부파일 없는 댓글 삭제하기 삭제하기 끝 =======

    // === 댓글내용 페이지바 Ajax로 만들기 시작=== //
	function makeCommentPageBar(currentShowPageNo) {
        		  
		  <%-- === 원글에 대한 댓글의 totalPage 수를 알아와야 한다. === --%> 
		  $.ajax({
		      url:"<%= ctxPath%>/getCommentTotalPage.gw",
			  data:{"parentSeq":"${requestScope.boardvo.seq}",
				    "sizePerPage":"5"},
		      type:"get",
		      dataType:"json",
			  success:function(json){
				  
		          if(json.totalPage > 0) {
				      const totalPage = json.totalPage;
					  const blockSize = 10;
					  
					    let loop = 1;	
				
				      	if(typeof currentShowPageNo == "string") {
				     	currentShowPageNo = Number(currentShowPageNo);
				      }
					  
				      let pageNo = Math.floor( (currentShowPageNo - 1)/blockSize ) * blockSize + 1; 
					  let pageBarHTML = "<ul style='list-style: none;'>";
					   
					   // === [맨처음][이전] 만들기 === //
					  if(pageNo != 1) {
						   pageBarHTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:goViewComment(\"1\")'>[맨처음]</a></li>";
						   pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewComment(\""+(pageNo-1)+"\")'>[이전]</a></li>";
					  }
					   
					  while( !(loop > blockSize || pageNo > totalPage) ){
						   
					      if(pageNo == currentShowPageNo) {
						      pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"+pageNo+"</li>";  
						  }
						  else{
						   	  pageBarHTML += "<li style='display:inline-block; width:30px; font-size:12pt;'><a href='javascript:goViewComment(\""+pageNo+"\")'>"+pageNo+"</a></li>"; 
						  }
						   
						  loop++;
						  pageNo++;
						  
					 }// end of while-------------------
					   
					      // === [다음][마지막] 만들기 === //
					      if(pageNo <= totalPage) {
						      pageBarHTML += "<li style='display:inline-block; width:50px; font-size:12pt;'><a href='javascript:goViewComment(\""+pageNo+"\")'>[다음]</a></li>";
						      pageBarHTML += "<li style='display:inline-block; width:70px; font-size:12pt;'><a href='javascript:goViewComment(\""+totalPage+"\")'>[마지막]</a></li>";
					      }
					   
					pageBarHTML += "</ul>";					   
					$("div#pageBar").html(pageBarHTML);
				    }// end of if(json.totalPage > 0){}----------------
	
			  },
			  error: function(request, status, error){
				 alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			  }
		  });
		  
	  }// end of function makeCommentPageBar(currentShowPageNo)---------
	  // === 댓글내용 페이지바 Ajax로 만들기 끝=== //

	   
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
		frm.action = "<%= ctxPath%>/view_2.gw";
		frm.submit();

	}// end of function goView(seq)---------------
	//====이전 페이지나 다음페이지로 넘어가기 끝====
  
		
	function goView2() {

		const goBackURL = "${requestScope.goBackURL}";
		const frm = document.goViewFrm;
	  	//frm.seq.value = input ;
		frm.goBackURL.value = goBackURL;

		frm.method = "post";
		frm.action = "<%= ctxPath%>/view_2.gw";
		frm.submit();
	
    }// end of function goView2(seq)---------------
  

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
	   	      <td>${requestScope.boardvo.readCount}</td>
	   	    </tr> 
	   	    <tr>
	   		  <th>날짜</th>
	   	      <td>${requestScope.boardvo.regDate}</td>
	   	    </tr>
	   	    <%-- === 첨부파일 이름 및 파일크기를 보여주고 첨부파일을 다운로드 되도록 만들기 === --%>
		    <tr>
					<th>첨부파일</th>
				<td>
					<c:if test="${sessionScope.loginuser != null}">
					    <a href="<%= ctxPath%>/download.gw?seq=${requestScope.boardvo.seq}">${requestScope.boardvo.orgFilename}</a>  
					</c:if>
					<c:if test="${sessionScope.loginuser == null}">
					    ${requestScope.boardvo.orgFilename}
					</c:if>
				</td>
			</tr>
			<tr>
				<th>파일크기(bytes)</th>
				<td><fmt:formatNumber value="${requestScope.boardvo.fileSize}" pattern="#,###" /></td>
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
		
		<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/freeboard.gw'">전체목록보기</button>
		
		<%-- === #126. 특정글을 조회한 후 "검색된결과목록보기" 버튼을 클릭했을 때 돌아갈 페이지를 만들기 위함. === --%>
		<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>${requestScope.goBackURL}'">검색된결과목록보기</button>
		
		<c:if test="${not empty sessionScope.loginuser}">
			<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/add.gw?subject=${requestScope.boardvo.subject}&groupno=${requestScope.boardvo.groupno}&fk_seq=${requestScope.boardvo.seq}&depthno=${requestScope.boardvo.depthno}'">답변글쓰기</button>
		</c:if> 

		<c:if test="${not empty sessionScope.loginuser && sessionScope.loginuser.email == requestScope.boardvo.fk_email}">
		    <button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/edit.gw?seq=${requestScope.boardvo.seq}'">글수정하기</button>
		    
		</c:if> 
		
		<c:if test="${not empty sessionScope.loginuser && (sessionScope.loginuser.email == requestScope.boardvo.fk_email or sessionScope.loginuser.gradelevel == 10)}">
			<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/del.gw?seq=${requestScope.boardvo.seq}'">글삭제하기</button>
		</c:if> 
				
		<%-- ==== 댓글쓰기 폼 추가 시작 ==== --%>
		<c:if test="${not empty sessionScope.loginuser}">
		    <h3 style="margin-top: 50px;">댓글쓰기</h3>
		
			<form name="addWriteFrm" id="addWriteFrm" style="margin-top: 20px;">
    	   	    <table class="table" style="width: 1024px">
					<tr style="height: 30px;">
				    	<th width="10%">성명</th>
				      	<td>
				        	<input type="hidden" name="fk_email" value="${sessionScope.loginuser.email}" readonly /> 
				        	<input type="text" name="name" value="${sessionScope.loginuser.name}" readonly /> 
				        </td>
				    </tr>
				    <tr style="height: 30px;">
				        <th>댓글내용</th>
				        <td>
				        	<input type="text" name="content" size="100" maxlength="1000" /> 
				         <%-- 댓글에 달리는 원게시물 글번호(즉, 댓글의 부모글 글번호) --%>
				            <input type="hidden" name="parentSeq" value="${requestScope.boardvo.seq}" readonly /> 
				        </td>
				    </tr>
				    <tr style="height: 30px;">
				        <th>파일첨부</th>
				        <td>
				      		<input type="file" name="attach" />
				        </td>
				   </tr>				   
				   <tr>
				        <th colspan="2">
				      		<button type="button" class="btn btn-success btn-sm mr-3" onclick="goAddWrite()">댓글쓰기 확인</button>
				      		<button type="reset" class="btn btn-success btn-sm">댓글쓰기 취소</button>
				        </th>
				   </tr>
				</table>	      
			</form>   
		</c:if>
		<%-- ==== 댓글쓰기 폼 추가 끝 ==== --%>
		
		<%-- ===== 댓글 내용 보여주기 시작 ==== --%>
		<h3 style="margin-top: 50px;">댓글내용</h3>
			<table class="table" style="width: 1024px; margin-top: 2%; margin-bottom: 3%;">
				<thead>
					<tr>
						<th style="width: 6%; text-align: center;">번호</th>
						<th style="text-align: center;">내용</th>
						<th style="width: 15%;">첨부파일</th>
						<th style="width:  8%;">bytes</th>
				 		<th style="width: 8%; text-align: center;">작성자</th>
						<th style="width: 12%; text-align: center;">작성일자</th>
		      		</tr>
		        </thead>
				<tbody id="commentDisplay"></tbody>
			</table> 
		<%-- ===== 댓글 내용 보여주기 끝 ==== --%>
		
		<%-- === 댓글 페이지바 === --%>
		<div style="display: flex; margin-bottom: 50px;">
			<div id="pageBar" style="margin: auto; text-align: center;"></div>
		</div>
	</div>
	
</div>
</div>	
	

<form name="goViewFrm">
	<input type="hidden" name="seq" />
	<input type="hidden" name="goBackURL" />
	<input type="hidden" name="searchType" />
	<input type="hidden" name="searchWord" />
</form>	
