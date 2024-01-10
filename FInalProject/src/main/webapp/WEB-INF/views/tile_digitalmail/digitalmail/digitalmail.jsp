<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String ctxPath = request.getContextPath();
%>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons+Outlined"
      rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


  <script>
  
      // 페이지 로딩 후 실행되는 함수
      $(document).ready(function(){
	      
	      // 각 섹션에 대한 클릭 이벤트 리스너 등록
	      $('div.section').click(function() {
		      alert('섹션 클릭됨!');
	      	  // 기존에 선택된 섹션들에서 'section_selected' 클래스 제거
	          $('div.section').removeClass('section_selected');
	          // 현재 클릭된 섹션에 'section_selected' 클래스 추가
	          $(this).addClass('section_selected');
	      });
		  	
	      /* 안쓰는듯?
	      $('div.opti').click(function() {
	         alert('시발섹션 클릭됨!');

	         // 기존에 선택된 섹션들에서 'section_selected' 클래스 제거
	         $('div.hide').removeClass('section_selected');
	         $('div.show').addClass('section_selected');
	        	
	      });
		  */
		  
	      // 각 섹션에 대한 클릭 이벤트 리스너 등록
	  	  $('div.opt').click(function() {
	          alert('섹션 클릭됨!');
	          $('div.hide').show();
	   	      // 기존에 선택된 섹션들에서 'section_selected' 클래스 제거
	       	  $('div.section').removeClass('section_selected');
	          $('div.hide').addClass('section_selected');
	         // iconText와 menuNameText 가져오기
	      	  var iconText = $(this).find('.material-icons-outlined').text().trim();
	      	  var menuNameText = $(this).find('.menu_name').text().trim();
	  	      // console.log(iconText,menuNameText);
		      $('div.hide .material-icons-outlined').text(iconText);
	          $('div.hide .list_name').text(menuNameText);
	      });
	      
		  // 체크 메뉴
	      $(".digitalmail_check").click(function(){
	          let bFlag = false;
	          $(".digitalmail_check").each(function(){
	              var Checkedbox = $(this).prop("checked");
	              if(!Checkedbox) {
	                  $("#all_check").prop("checked",false);
	                  bFlag = true;
	                  return false;
	              }  
	          });
	          if(!bFlag) {
	            $("#all_check").prop("checked",true);
			  }
          });	      
		  
		  
      
      });
      
      // 함수 정의 시작      
      
      // 체크박스 전체 체크 해제 
      function allCheckBox() {
          
    	  var bool = $("#all_check").is(":checked");          
          $(".digitalmail_check").prop("checked", bool);
        
      }// end of function allCheckBox()------------------------- 다했음
      
  	function selectOneEmail(send_email_seq){
		
    	alert(send_email_seq);  
    	// 패스워드 체크
		$.ajax({
			url:"<%= ctxPath%>/getEmailPwd.gw",
			type:"post",
			data:{"send_email_seq":send_email_seq },
	        success:function(json){
	        	if(json != "" && json != null){
	        		alert("비밀 메일입니다. 암호를 입력해주세요.");
	        		location.href="<%=ctxPath%>/digitalmailview.gw?send_email_seq="+send_email_seq;
	  				<%--  
	        		if(value == json.pwd){
	  					if(mailRecipientNo!=null){
	  						location.href="<%=ctxPath%>/mail/viewMail.on?mailNo="+mailno+"&mailRecipientNo"+mailRecipientNo;
	  					}
	  					else{
	  						location.href="<%=ctxPath%>/mail/viewMail.on?mailNo="+mailno;
	  					}
	  				  }
	  				  else{
	  					  swal("잘못된 암호입니다. 다시 확인해주세요.");
	  				  }
	  				  --%>	 
	  			}
	        	else{
	        		alert("비밀 메일이 아닙니다.");
	        		if(mailRecipientNo!=null){
	        			location.href="<%=ctxPath%>/digitalmailview.gw?send_email_seq="+send_email_seq+"&mailRecipientNo"+mailRecipientNo;
	        		}
	        		else{
	        			location.href="<%=ctxPath%>/digitalmailview.gw?send_email_seq="+send_email_seq;
	        		}
	    		}

      
	        },
	        error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            } 
		});
	}
<<<<<<< HEAD
    
 // receipt_favorites update 하기
	function receipt_favorites_update(receipt_mail_seq){
		$.ajax({
			url:"<%= ctxPath%>/receipt_favorites_update.gw",
			type:"post",
			data:{"receipt_mail_seq":receipt_mail_seq},
			dataType:"json",
	        success:function(json){
	        	console.log(json);
	        	// {"receipt_favorites":"0"}
	        	
        		if(json.receipt_favorites1 === "1"){
	        		$("span#"+receipt_mail_seq).text("favorite");
	        		$("span#"+receipt_mail_seq).css("color", "red");
	        	}
	        	
	        	else{
	        		$("span#"+receipt_mail_seq).text("favorite_border");
	        		$("span#"+receipt_mail_seq).css("color", "black");
	        	}
	        	
	        },
	        error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
			
		});
		
	};
	
	
	// receipt_favorites update 하기
	function receipt_favorites_update(receipt_mail_seq){
		$.ajax({
			url:"<%= ctxPath%>/receipt_favorites_update.gw",
			type:"post",
			data:{"receipt_mail_seq":receipt_mail_seq},
			dataType:"json",
	        success:function(json){
	        //	console.log(json);
	        	// {"receipt_favorites":"0"}
        		if(json.receipt_favorites === "1"){
	        		$("span#"+receipt_mail_seq+"fav").text("favorite");
	        		$("span#"+receipt_mail_seq+"fav").css("color", "red");
	        	}
	        	else{
	        		$("span#"+receipt_mail_seq+"fav").text("favorite_border");
	        		$("span#"+receipt_mail_seq+"fav").css("color", "black");
	        	}
	        },
	        error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
	};
	
	
	// email_receipt_read_count update 하기
	function email_receipt_read_count_update(receipt_mail_seq){
		$.ajax({
			url:"<%= ctxPath%>/email_receipt_read_count_update.gw",
			type:"post",
			data:{"receipt_mail_seq":receipt_mail_seq},
			dataType:"json",
	        success:function(json){
	        //	console.log(json);
	        	
        		$("span#"+receipt_mail_seq+"rc").text("drafts");
        		$("span#"+receipt_mail_seq+"rc").css("color", "black");
	        	
	        },
	        error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
	};
	
	
	// receipt_important update 하기
	function receipt_important_update(receipt_mail_seq){
		$.ajax({
			url:"<%= ctxPath%>/receipt_important_update.gw",
			type:"post",
			data:{"receipt_mail_seq":receipt_mail_seq},
			dataType:"json",
	        success:function(json){
	        	console.log(json);
	        	// {"receipt_favorites":"0"}
        		if(json.receipt_important === "1"){
	        		$("span#"+receipt_mail_seq+"imp").text("priority_high");
	        		$("span#"+receipt_mail_seq+"imp").css("color", "orange");
	        	}
	        	else{
	        		$("span#"+receipt_mail_seq+"imp").text("priority_high");
	        		$("span#"+receipt_mail_seq+"imp").css("color", "black");
	        	}
	        },
	        error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
	};
      
  
=======
	
	// receipt_favorites update 하기
	function receipt_favorites_update(receipt_mail_seq){
		$.ajax({
			url:"<%= ctxPath%>/receipt_favorites_update.gw",
			type:"post",
			data:{"receipt_mail_seq":receipt_mail_seq},
			dataType:"json",
	        success:function(json){
	        	console.log(json);
	        	// {"receipt_favorites":"0"}
	        	
        		if(json.receipt_favorites1 === "1"){
	        		$("span#"+receipt_mail_seq).text("favorite");
	        		$("span#"+receipt_mail_seq).css("color", "red");
	        	}
	        	
	        	else{
	        		$("span#"+receipt_mail_seq).text("favorite_border");
	        		$("span#"+receipt_mail_seq).css("color", "black");
	        	}
	        	
	        },
	        error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
			
		});
		
	};
	
	
	// receipt_favorites update 하기
	function receipt_favorites_update(receipt_mail_seq){
		$.ajax({
			url:"<%= ctxPath%>/receipt_favorites_update.gw",
			type:"post",
			data:{"receipt_mail_seq":receipt_mail_seq},
			dataType:"json",
	        success:function(json){
	        //	console.log(json);
	        	// {"receipt_favorites":"0"}
        		if(json.receipt_favorites === "1"){
	        		$("span#"+receipt_mail_seq+"fav").text("favorite");
	        		$("span#"+receipt_mail_seq+"fav").css("color", "red");
	        	}
	        	else{
	        		$("span#"+receipt_mail_seq+"fav").text("favorite_border");
	        		$("span#"+receipt_mail_seq+"fav").css("color", "black");
	        	}
	        },
	        error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
	};
	
	
	// email_receipt_read_count update 하기
	function email_receipt_read_count_update(receipt_mail_seq){
		$.ajax({
			url:"<%= ctxPath%>/email_receipt_read_count_update.gw",
			type:"post",
			data:{"receipt_mail_seq":receipt_mail_seq},
			dataType:"json",
	        success:function(json){
	        //	console.log(json);
	        	
        		$("span#"+receipt_mail_seq+"rc").text("drafts");
        		$("span#"+receipt_mail_seq+"rc").css("color", "black");
	        	
	        },
	        error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
	};
	
	
	// receipt_important update 하기
	function receipt_important_update(receipt_mail_seq){
		$.ajax({
			url:"<%= ctxPath%>/receipt_important_update.gw",
			type:"post",
			data:{"receipt_mail_seq":receipt_mail_seq},
			dataType:"json",
	        success:function(json){
	        	console.log(json);
	        	// {"receipt_favorites":"0"}
        		if(json.receipt_important === "1"){
	        		$("span#"+receipt_mail_seq+"imp").text("priority_high");
	        		$("span#"+receipt_mail_seq+"imp").css("color", "orange");
	        	}
	        	else{
	        		$("span#"+receipt_mail_seq+"imp").text("priority_high");
	        		$("span#"+receipt_mail_seq+"imp").css("color", "black");
	        	}
	        },
	        error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
	};
	
	
	
	
	
	
	
>>>>>>> branch 'main' of https://github.com/YosubL/FinalProject.git
  </script>



	<!-- 결과물 시작하기 <div class="main_body"> -->
	<!-- Email List  <div class="emailList"> -->
	<!-- 메일함 리스트 세팅-->
	
	<div class="emailList_settings">
		
		<!--왼쪽 세팅-->
		<div class="emailList_settingsLeft">
	        <input type="checkbox" id="all_check" onclick="allCheckBox();">                        
			<div class="icon_set ml-3 mr-3">
				<span class="material-icons-outlined icon_img" style="font-size: 24pt;">redo</span>
				<span class="icon_text">새로고침</span>
			</div>
			<div class="icon_set mr-3">
				<span class="material-icons-outlined icon_img" style="font-size: 24pt; color: red;">favorite</span>
				<span class="icon_text">즐겨찾기</span>
			</div>						           
			<div class="icon_set mr-3">
				<span class="material-icons-outlined icon_img" style="font-size: 24pt;">delete</span>
				<span class="icon_text">휴지통</span>
			</div>                        
		</div>
        <!--왼쪽 세팅-->
        
        <!--오른쪽 세팅-->
        <div class="emailList_settingsRight">
			${requestScope.pageBar}  
		</div>
		<!--오른쪽 세팅-->
		
	</div>
	
	<!--셀렉션 세팅-->
	<div class="emailList_sections">
    	<div class="section section_selected show">
        	<span class="material-icons-outlined" style="font-size:24px;"> inbox </span> 
			<span class="list_name">받은메일함</span>
		</div>
		<div class="section hide">
			<span class="material-icons-outlined">quiz</span> 
            <span class="list_name">메뉴를 선택해주세요</span>
        </div>
	</div> 
	<!--셀렉션 세팅 끝-->
	
	<!--이메일 리스트-->
	<div class="emailList_list">
		
		<c:if test="${empty requestScope.emailVOList}">
    		<div class="emailRow" style="width: 100%; display: flex;">
				<span style="display:inline-block; margin: 0 auto;">받은 메일이 없습니다.</span>
			</div>
		</c:if>
		
		<c:if test="${not empty requestScope.emailVOList}">
			<c:forEach var="emailVO" items="${requestScope.emailVOList}" varStatus="status">
				<div class="emailRow">
					
					<!-- 즐겨찾기 여부 -->
					<div class="emailRow_options">
						<input type="checkbox" type="checkbox" name="empid" class="digitalmail_check">
		                <c:if test="${emailVO.receipt_favorites==0}">
		                	<span id="${emailVO.receipt_mail_seq}fav" class="material-icons-outlined ml-2" onclick="receipt_favorites_update('${emailVO.receipt_mail_seq}')">favorite_border</span>
		                </c:if>
		                <c:if test="${emailVO.receipt_favorites==1}">
		                	<span id="${emailVO.receipt_mail_seq}fav" class="material-icons-outlined ml-2" onclick="receipt_favorites_update('${emailVO.receipt_mail_seq}')" style="color: red;">favorite</span>
		                </c:if>
					</div>
					<!-- 즐겨찾기 여부 -->
					
					<!-- 읽음 여부 정보 -->
					<c:if test="${emailVO.email_receipt_read_count==0}">
	                	<span id="${emailVO.receipt_mail_seq}rc" class="material-icons-outlined ml-2" onclick="email_receipt_read_count_update('${emailVO.receipt_mail_seq}')" style="color: red;">mark_email_unread</span>
	                </c:if>
	                <c:if test="${emailVO.email_receipt_read_count==1}">
	                	<span id="${emailVO.receipt_mail_seq}rc" class="material-icons-outlined ml-2" onclick="email_receipt_read_count_update('${emailVO.receipt_mail_seq}')">drafts</span>
	                </c:if>		
			        <!-- 읽음 여부 정보 -->
			        
			        <!-- 중요메일 여부 -->
					<c:if test="${emailVO.receipt_important==0}">
	                	<span id="${emailVO.receipt_mail_seq}imp" class="material-icons-outlined" onclick="receipt_important_update('${emailVO.receipt_mail_seq}')" style="color: black;"> priority_high </span>
	                </c:if>
	                <c:if test="${emailVO.receipt_important==1}">
	                	<span id="${emailVO.receipt_mail_seq}imp" class="material-icons-outlined" onclick="receipt_important_update('${emailVO.receipt_mail_seq}')" style="color: orange;"> priority_high </span>
	                </c:if>		
			        <!-- 중요메일 여부 -->
<<<<<<< HEAD
=======
			        
>>>>>>> branch 'main' of https://github.com/YosubL/FinalProject.git
			                
			        
					<!-- 수신자 정보 -->
					<span class="emailRow_title ml-2">${emailVO.job_name}&nbsp;${emailVO.name}</span>
					<!-- 수신자 정보 -->
					
					<!-- 제목-->
					<div class="emailRow_message" onclick = "selectOneEmail('${emailVO.send_email_seq}')">
						<span>${emailVO.email_subject}</span>
					</div>
					<!-- 제목 -->
						
					<!--전송시간-->
					<span class="mr-3" onclick = "selectOneEmail('${emailVO.send_email_seq}')">${emailVO.send_time}</span>
					<!--전송시간-->
				</div>
			</c:forEach>
		</c:if>
	</div>

     
