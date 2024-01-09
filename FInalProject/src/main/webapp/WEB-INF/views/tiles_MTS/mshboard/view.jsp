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
	
	form{
		font-family: Verdana, sans-serif;
	
	}
	
	th{
		color:#595959;
		width: 18%;
		font-weight: normal;
	}
	
	table,tr,td{
		border:none;
		font-size: 14pt;
		color:#595959;
	}
	
	#addWriteFrm > table{
	position:relative;
	right:40px;
	}
    
    #commonbutton,
	#delbutton{
	white-space : nowrap;
	}
	
	#btnReviewUpdate_NO,
	#btnReviewUpdate_OK{
		position:relative;
		left:190px;
		white-space : nowrap;
	
	}
	<%--
	#content{
	width:450px;
	height:150px;
	}
	
	#commentContent{
	width:1100px;
	}
	--%>
	
	#delbutton,
	#commonbutton{
		width:100px;
	}
	

	#mycontent > div > div > div:nth-child(9) > div,
	#mycontent > div > div > div:nth-child(10) > div{
		text-align:right;
	
	}
	
	#content{
		width:1350px;
		height:55px;
	}
	
	#mycontent > div > div > button:nth-child(8){
		margin-left:960px;
	}

	#mycontent > div > div > button:nth-child(8),
	#mycontent > div > div > button:nth-child(9),
	#mycontent > div > div > button:nth-child(10),
	#mycontent > div > div > button:nth-child(11){
		position:relative;
		left:15px;
		top:10px;
	}
	
#commentDisplay > div:nth-child(1) > div > h6 > span,
#commentDisplay > div:nth-child(1)~* > div > h6 > span{
	margin-left:20px;
}

#delbutton{
	margin-right:700px;
	position:relative;
	bottom:61px;
	background-color:white;
	color:black;
	border:none;
	font-weight:bold;
}

#commonbutton{
	position:relative;
	bottom:61px;
	right:707px;
	background-color:white;
	color:black;
	border:none;
	font-weight:bold;
}

#btnReviewUpdate_OK{
	margin-left:60px;
	position:relative;
	bottom:95px;
}

#btnReviewUpdate_NO{
	margin-left:1px;
	position:relative;
	bottom:95px;
}


	#showList{
	margin-left:1150px;
	position:relative;
		left:15px;
		top:10px;
	}

	#mycontent > div > div > div:nth-child(10) > div{
	padding-right:1170px;
	}

	#mycontent > div > div > button:nth-child(7){
		position:relative;
		left:15px;
		top:10px;
	}

#commentDisplay > div:nth-child(1) > div > h6 > a{
	position:relative;
		left:900px;
}
	
</style>


<style type="text/css">
   
.anime__details__review {
   margin-bottom: 55px;
}
.section-title {
   margin-bottom: 30px;
}

.section-title h4,
.section-title h5 {
   color: #black;
   font-weight: 600;
   line-height: 21px;
   text-transform: uppercase;
   padding-left: 20px;
   position: relative;
}

.section-title h4:after,
.section-title h5:after {
   position: absolute;
   left: 0;
   top: -6px;
   height: 32px;
   width: 4px;
   background: rgb(3, 199, 90);
   content: "";
}

.comment_bubble {
   overflow: hidden;
   margin-bottom: 15px;
}

.comment_bubble__pic {
   float: left;
   margin-right: 20px;
   position: relative;
}

.comment_bubble__pic:before {
   position: absolute;
   right: -30px;
   top: 15px;
   border-top: 15px solid transparent;
   border-left: 15px solid #1d1e39;
   content: "";
   transform: rotate(45deg);
}


.comment_bubble__text {
   overflow: hidden;
   background: #ccc;
   padding: 18px 30px 16px 20px;
   border-radius: 10px;
}

.comment_bubble__text h6 {
   color: #ffffff;
   font-weight: 700;
   margin-bottom: 10px;
}

.comment_bubble__text h6 span {
   color: #b7b7b7;
   font-weight: 400;
}

.comment_bubble__text p {
   color: #b7b7b7;
   line-height: 23px;
   margin-bottom: 0;
}

.anime__details__form form textarea {
   width: 100%;
   font-size: 15px;
   color: #b7b7b7;
   padding-left: 20px;
   padding-top: 12px;
   height: 110px;
   border: none;
   border-radius: 5px;
   resize: none;
   margin-bottom: 24px;
}

.anime__details__form form button {
   font-size: 11px;
   color: #ffffff;
   font-weight: 700;
   letter-spacing: 2px;
   text-transform: uppercase;
   background: #e53637;
   border: none;
   padding: 10px 15px;
   border-radius: 2px;
}
       





</style>

<script type="text/javascript">
  
$(document).ready(function(){
	goViewComment(1); // 페이징 처리한 댓글 읽어오기 
	
    $("span.move").hover(function(){
		$(this).addClass("moveColor");
	},function(){
		$(this).removeClass("moveColor");
	});
	
    $("textarea[name='content']").bind("keydown", function(e){
		if(e.keyCode == 13) { // 엔터
			goAddWrite();
		}
	});
    
    var maxLength = 83; // 최대 글자 수

    // 텍스트 입력 상자의 내용이 변경될 때마다 호출되는 이벤트 핸들러
    $('#commentContent').on('input', function() {
        var text = $(this).val(); // 입력된 텍스트
        if (text.length > maxLength) {
            // 최대 글자 수를 초과하면 잘라내기
            $(this).val(text.substring(0, maxLength));
            alert("초과제한 글자수 83자를 넘어섰습니다.");
        }
    });

    $("#btnReviewUpdate_NO").on("click", function() {
        $(".commonbutton").prop("disabled", false);
    });

    // .commonbutton을 클릭하면 부모 요소 내의 다른 .commonbutton을 비활성화
    $(".commonbutton").on("click", function() {
    	$(this).siblings(".commonbutton").prop("disabled", true);

        // 비활성화 후 로직 수행 (예: 다른 동작 수행)
        // ...
    });
    
    
    $('#attach').on('change', function() {
        var fileName = $(this).val().split('\\').pop(); // 파일 이름 추출
        var maxLength2 = 30; // 최대 글자 수

        if (fileName.length > maxLength2) {
            // 최대 글자 수를 초과하면 잘라내기
            var truncatedFileName = fileName.substring(0, maxLength2);
            alert("파일 이름은 " + maxLength2 + "자 이하여야 합니다.");
            
            // 파일명 업데이트
            $(this).val("");
        }
    });
    
    
});// end of $(document).ready(function(){})-------------

  
  // Function Declaration
  // == 댓글쓰기 == 
  function goAddWrite() {
	  const comment_content = $("textarea[name='content']").val().trim();
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
				$("textarea[name='content']").val("");
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

			    $("textarea[name='content']").val("");
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
					     v_html += " <div class='comment_bubble'>"; 
					     v_html +=    "<div class='comment_bubble__text' style='background-color:white; height:120px;' ><h6 style='color:black;'>"+(item.name)+"<span>"+item.regdate+"</span>";
					     
					     if(${sessionScope.loginuser != null}) {
					         v_html += "<a href='<%= ctxPath%>/downloadComment.gw?seq="+item.seq+"'>"+ item.orgFilename +"</a></h6>";   
					     }
					     else {
					         v_html += "</h6>";
					     }
					     v_html += 	  "<p id='review" + item.seq + "'>"  +
					     				item.content +"</p>"; 
							              <%--
					     				  item.content.substring(0, 30)  + "<br>" +
							              item.content.substring(30, 60) + "<br>" +
							              item.content.substring(60, 90) + "<br>" +
							              item.content.substring(90, 100)
							              --%>
					     <%-- === 첨부파일 기능이 추가된 경우 시작 === --%>  
					     <%--
					     if(${sessionScope.loginuser != null}) {
					         v_html += "<div style='white-space : nowrap; width:30%;'><a href='<%= ctxPath%>/downloadComment.gw?seq="+item.seq+"'>"+ item.orgFilename +"</a></div>";   
					     }
					     else {
					         v_html += "<div style='width:30%; word-wrap: break-word;'>"+ item.orgFilename +"</div>";
					     }
					     --%>         
					     
					     <%-- === 첨부파일 기능이 추가된 경우 끝 === --%>
					              
					    
					     // v_html += "<div class='comment' style='color:#a6a6a6; height:20px; white-space : nowrap; width:20%;' >" + item.regdate + "</div>";
					     v_html += "<div id='review2"+item.seq+"' style='width:1%;'>"+ "     "+ "</div>";
					          
					     if (${sessionScope.loginuser != null} && "${sessionScope.loginuser.email}" == item.fk_email) {
						      if(item.orgFilename.trim() != ""){
						          v_html += "<div class='text-center'><button type='button' name= 'delbtn"+item.seq+"' id = 'delbutton' class='btn btn-secondary' onclick='deleteComment(" + item.seq + ")' style='width: 70px;'>삭제</button>";
						          v_html += "<button type='button' name= 'update"+item.seq+"' id = 'commonbutton'"+item.seq+" class='btn btn-secondary mr-3' onclick='updateMyReview(" + item.seq + ")'style='width: 70px;'>수정</button></div></div>";
						      
						      }
						      else{
						    	  v_html += "<div class='text-center'><button type='button' name= 'delbtn"+item.seq+"' id = 'delbutton' class='btn btn-secondary' onclick='deleteComment(" + item.seq + ")' style='width: 70px;'>삭제</button>";
						          v_html += "<button type='button' name= 'update"+item.seq+"' id = 'commonbutton'"+item.seq+" class='btn btn-secondary mr-3' onclick='updateMyReview(" + item.seq + ")'style='width: 70px;'>수정</button></div></div>";
						      }
					     }
					      
					     else{
					            v_html += "<br></br><br></br>";
					     }
					    v_html += "</div><br></br>";	            
				 	});
			 	}
				else{
					v_html =  "<div>";
					v_html +=   "<div colspan='4' class='text-center'>댓글이 없습니다</div>";
				    v_html += "</div>";
				}
				 
				 // $("tbody#commentDisplay").html(v_html);
				 $("div#commentDisplay").html(v_html);
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
    	
    	 /////////////////////////////////////////////
    	    $("button[name^='update']").hide();
    	 /////////////////////////////////////////////
    	let comt = $("input#one_review").val();
    	$("button[name='delbtn"+seq+"']").hide();
		
		$("button[name='update"+seq+"']").hide();

		const review_contents = $("p#review"+seq).text(); 
		var k_html = "<form name='review_update_Frm' enctype='multipart/form-data'>";
		k_html += "<textarea style='color:black;' name='content' id='content'>";
		k_html += "</textarea>"
		k_html += "</form>";
		var previousContent = review_contents; 

	     $("p#review"+seq).html(k_html); 

	     $("p#review"+seq+" textarea#content").val(previousContent);

	     $("#btnReviewUpdate_OK").on("click", function() {
	     	let comt = $("input#one_review").val();
	     }); // $("#btnReviewUpdate_OK").on("click", function() { ----------

	     let v_html = "";
	     v_html += "<td style='border:none;'><button type='submit' name='ok_update"+seq+"' class='btn btn-sm btn-outline-secondary update-btn' id='btnReviewUpdate_OK'>수정완료</button></td>"; 
	     v_html += "<td style='border:none;'><button style='margin-right:70px;' type='button' name='no_update"+seq+"' class='btn btn-sm btn-outline-secondary cancel-btn' id='btnReviewUpdate_NO'>수정취소</button></td>";

	     // 수정 버튼 비활성화
	     $(".update-btn").prop("disabled", true);

	     // 취소 버튼 클릭 시 수정 버튼 활성화
	     $("#btnReviewUpdate_NO").on("click", function() {
	         $(".update-btn").prop("disabled", false);
	         // 나머지 취소 동작 추가
	     });

	 // 원래의 제품후기 엘리먼트에 위에서 만든 "후기수정" 을 위한 엘리먼트로 교체하기
	 $("div#review2"+seq).html(v_html);
	
	       // ===== 수정취소 버튼 클릭시 =====
	     $(document).on("click", "button#btnReviewUpdate_NO", function(){
	         //////////////////////////////////////////////////////////
	         //$("button[name^='update']").show();
	         //////////////////////////////////////////////////////////
	    	 
	    	 $("td#review"+seq).html(previousContent);  // 원래의 제품후기 엘리먼트로 복원하기
	         $("button[name='no_update"+seq+"']").hide();
	         $("button[name='ok_update"+seq+"']").hide();
	         $("button[name='delbtn"+seq+"']").show(); // "후기수정" 글자 보여주기
	         $("button[name='update"+seq+"']").show();
	         location.href="<%=ctxPath%>/editAfter_view.gw?seq=${boardvo.seq}";
	         
	      }); //end of $(document).on("click", "button#btnReviewUpdate_NO", function(){--------

	   // ===== 수정완료 버튼 클릭시 =======
	      $(document).on("click", "button#btnReviewUpdate_OK", function () {
	          //var form_data = $("form[name=review_update_Frm]").serialize();
	         // var formData = new FormData($("form[name='review_update_Frm']").get(0));
	          //console.log(formData);
		  alert(seq);
	          $.ajax({
	              url:"<%= ctxPath%>/reviewUpdate.gw",
	              type:"post",
	              data:{"seq":seq ,"content":$("textarea#content").val()},
	              dataType:"json",
	              success:function(json){
	                  if(json == 1) {
	                      goViewComment(1); // 페이징 처리한 댓글 읽어오기
	                  }
	                  else{
	                      alert("댓글 수정 성공!"); // 페이징 처리한 댓글 읽어오기
	                     
	                      location.href="<%=ctxPath%>/editAfter_view.gw?seq=${boardvo.seq}";
	                      
	                  }
	              },
	              error: function(request, status, error){
	                  alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	              }
	          });
	      });
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
<div class='container'>

</div>		
		<c:if test="${not empty requestScope.boardvo}">
		
			<div class="text-left" style="margin-top: 80px;">
		      <div name="subject_name" style="font-weight: bold; font-size: 30px;">${requestScope.boardvo.subject}<br>
			  <div style="margin-bottom:20px;"></div>
		      	<div>
			
				  	
				  <span style="font-size: 15.5px; font-weight:bold; margin-bottom: 10px;">작성자 &nbsp; ${requestScope.boardvo.name}</span>
			      <span style="font-size: 15.5px; margin-bottom: 10px;"><i class="far fa-eye mx-2"></i>${requestScope.boardvo.readCount}</span>
			      <span style="font-size: 15.5px; margin-bottom: 10px;"><i class="far fa-clock mx-2"></i> ${requestScope.boardvo.regDate}</span>
		    	</div>
		    </div>
		  
		
	    <hr style="border-top: solid 1.2px black">
			
		<%-- 글수정, 삭제 버튼은 작성자만 보임 --%>		
		
		
		<!-- 글 내용 -->
		</div>
			<p>${requestScope.boardvo.content}</p>
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
					<a href="<%= ctxPath%>/download.gw?fileno=${file.fileno}">${file.orgFilename}</a>
					<c:if test="${sts.count != fn:length(fileList) }">,</c:if>
				    </c:forEach>

			</p>


			<button type="button" id="showList" class="btn btn-secondary btn-sm mr-3"  onclick="javascript:location.href='<%= ctxPath%>${requestScope.goBackURL}'">검색된 결과 보기</button>
	
			<c:if test="${not empty sessionScope.loginuser}">
				<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/add.gw?subject=${requestScope.boardvo.subject}&groupno=${requestScope.boardvo.groupno}&fk_seq=${requestScope.boardvo.seq}&depthno=${requestScope.boardvo.depthno}'">답변글쓰기</button>
			</c:if> 
			<%-- 글수정, 삭제 버튼은 작성자만 보임 --%>	
			<c:if test="${not empty sessionScope.loginuser && sessionScope.loginuser.email == requestScope.boardvo.fk_email}">
			    <button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/edit.gw?seq=${requestScope.boardvo.seq}'">글수정하기</button>
			    
			</c:if> 
			
			<c:if test="${not empty sessionScope.loginuser && (sessionScope.loginuser.email == requestScope.boardvo.fk_email or sessionScope.loginuser.gradelevel == 10)}">
				<button type="button" class="btn btn-secondary btn-sm mr-3" onclick="javascript:location.href='<%= ctxPath%>/del.gw?seq=${requestScope.boardvo.seq}'">글삭제하기</button>
			</c:if> 

		
		<c:if test="${empty requestScope.boardvo}">
			<div style="padding: 20px 0; font-size: 16pt; color: red;" >존재하지 않습니다</div> 
		</c:if>
								
			<div style='margin-top: 30px;'>
			  	<div style="display: inline-block; float:right"> 
			  		<%-- === #126. 특정글을 조회한 후 "검색된결과목록보기" 버튼을 클릭했을 때 돌아갈 페이지를 만들기 위함. === --%>
				  
			    </div>
			</div>
		
	
	    <hr style="border-top: solid 1.2px black">
	    	<div>
	    	<div style="margin-bottom: 1%;"><i style='vertical-align: bottom;' class="fas fa-sort-up"></i> 다음글제목&nbsp;&nbsp;
	    	<span class="move" onclick="goView('${requestScope.boardvo.nextseq}')">${requestScope.boardvo.nextsubject}</span></div>
	    	</div>
	    	<div>
	    	<div style="margin-bottom: 1%;"><i style='vertical-align: top;' class="fas fa-sort-down"></i> 이전글제목&nbsp;&nbsp;
	    	<span class="move" onclick="goView('${requestScope.boardvo.previousseq}')">${requestScope.boardvo.previoussubject}</span></div>
	    	</div>
	    <hr style="border-top: solid 1.2px black">
	    
		<%-- ==== 댓글쓰기 폼 추가 시작 ==== --%>
		<c:if test="${not empty sessionScope.loginuser}">
		    
		    <div class="anime__details__form">
				<div class="section-title">
					<h5>댓글쓰기</h5>
				</div>
				<form name="addWriteFrm" id="addWriteFrm">
					<input type="hidden" name="fk_email" id="fk_email" value="${sessionScope.loginuser.email}" />
					<input type="hidden" name="name" id="name" value="${sessionScope.loginuser.name}" />
					<textarea style="color:black;" name="content" id="commentContent" placeholder="댓글을 입력하세요"></textarea>
                    <input type="hidden" name="parentSeq" id="parentSeq" value="${requestScope.boardvo.seq}" readonly />
                    <button type="button" onclick="goAddWrite()"><i class="fa fa-location-arrow"></i> 댓글쓰기</button>
                    <button type="reset">댓글취소</button>
                    <input type="file" name="attach" id="attach" />
				</form>
			</div>
		    
		    
		    
		    <%--
		    <h3 style="margin-top: 50px;">댓글쓰기</h3>
			
			<form name="addWriteFrm" id="addWriteFrm" style="margin: 20px 0 0 40px"> 
				
				<table class="table" name="inputs" style="width: 1500px; background-color: #f8f8f9" >
					<tr style="height: 35px">
					   <td colspan="2"><i class="fa fa-user" style="font-size: 18pt; color:gray"></i>
					   	  <input type="hidden" name="fk_email" id="fk_email" value="${sessionScope.loginuser.email}" />
					   	  <input type="hidden" name="name" id="name" value="${sessionScope.loginuser.name}" />
					   	  ${sessionScope.loginuser.name}
					   </td>
					</tr>
					<tr style="height: 35px">
					   <td style="border-top: none; width:77%">
					   	  <textarea name="content" id="commentContent" style="margin-left:37px; border:none; height: 100px;" size="100" placeholder="&nbsp;댓글을 입력하세요."></textarea> 
					   	  <input type="hidden" name="parentSeq" id="parentSeq" value="${requestScope.boardvo.seq}" readonly />
					   </td>
					   <td style="border-top: none;">
					   	  <button type="button" class="btn btn-lg mr-3 " onclick="goAddWrite()" style="background-color: white; color:#0070C0; font-size: 14pt;">등록</button> 			   	 
					   	  <button type="reset" class="btn btn-lg mr-3 " style="background-color: white; color:#0070C0; font-size: 14pt;">취소</button>
					 </td>
					</tr>
					<tr style="height: 30px;">
				        <th>파일첨부 <input type="file" name="attach" id="attach" /></th>
				   </tr>	
				</table>
			</form>
 			 --%>		
		</c:if>
		<%-- ==== 댓글쓰기 폼 추가 끝 ==== --%>
		
		<%-- ===== 댓글 내용 보여주기 시작 ==== --%>
		
		
			<div class="row mt-5">
                <div class="col-lg-8">
                    <div class="anime__details__review">
                        <div class="section-title"><h5>Reviews</h5></div>
                        <div style="width: 1400px;" id="commentDisplay"></div>
                    </div>
                    
                </div>
            </div>
			
			
			
			
			<%-- 
			<table class="table" style="width: 1400px; margin-top: 2%; margin-bottom: 3%;">
				<tbody id="commentDisplay"></tbody>
			</table> 
			--%>
		<%-- ===== 댓글 내용 보여주기 끝 ==== --%>
		
		<%-- === 댓글 페이지바 === --%>
		<div style="display: flex; margin-bottom: 50px;">
			<div id="pageBar" style="margin: auto; text-align: center;"></div>
		</div>
</div>
</div>	
	

<form name="goViewFrm">
	<input type="hidden" name="seq" />
	<input type="hidden" name="goBackURL" />
	<input type="hidden" name="searchType" />
	<input type="hidden" name="searchWord" />
</form>	

