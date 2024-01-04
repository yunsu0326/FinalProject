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
		  	
	      $('div.opti').click(function() {
	         alert('시발섹션 클릭됨!');

	         // 기존에 선택된 섹션들에서 'section_selected' 클래스 제거
	         $('div.hide').removeClass('section_selected');
	         $('div.show').addClass('section_selected');
	        	
	      });
		  
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
      function allCheckBox() {
          var bool = $("#all_check").is(":checked");
          
          /*
          
          	$("#all_check").is(":checked"); 은
                     선택자 $("#all_check") 이 체크되어지면 true를 나타내고,
                     선택자 $("#all_check") 이 체크가 해제되어지면 false를 나타내어주는 것이다.
          
    	  */
          
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
  
  </script>



	<!-- 결과물 시작하기 <div class="main_body"> -->
	<!-- Inicio Email List  <div class="emailList"> -->
	<!-- Inicio Email List Settings-->
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
        <!--오른쪽 세팅-->
        <div class="emailList_settingsRight">
			${requestScope.pageBar}
			<%--
			<div class="icon_set">
				<span class="material-icons-outlined icon_img" style="font-size: 24pt;">chevron_left</span>
				<span class="icon_text">이전</span>
			</div>
			<div class="icon_set">
				<span class="material-icons-outlined icon_img" style="font-size: 24pt;">chevron_right</span>
				<span class="icon_text">다음</span>
			</div>
            --%>    
		</div>
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
				<div class="emailRow" onclick = "selectOneEmail('${emailVO.send_email_seq}')">
					<!--이메일 옵션 or 카테고리-->
					<div class="emailRow_options">
						<input type="checkbox" type="checkbox" name="empid" class="digitalmail_check">
		                <c:if test="${emailVO.receipt_favorites==0}">
		                	<span class="material-icons-outlined ml-2">favorite_border</span>
		                </c:if>
		                <c:if test="${emailVO.receipt_favorites==1}">
		                	<span class="material-icons-outlined ml-2" style="color: red;">favorite</span>
		                </c:if>
					</div>
						<c:if test="${emailVO.email_receipt_read_count==0}">
		                	<span class="material-icons-outlined ml-2">mark_email_unread</span>
		                </c:if>
		                <c:if test="${emailVO.email_receipt_read_count==1}">
		                	<span class="material-icons-outlined ml-2" style="color: red;">drafts</span>
		                </c:if>		
						<span class="emailRow_title ml-2">${emailVO.job_name}&nbsp;${emailVO.name}</span>
						<!-- 메시지 미리보기-->
						<div class="emailRow_message">
						<span>${emailVO.email_subject}</span>
						</div>
						<!-- 메시지 미리보기-->
						<!--전송시간-->
						<span class="mr-3">${emailVO.send_time}</span>
					</div>
				</c:forEach>
			</c:if>
				
		<!--가로 한줄-->
		<div class="emailRow" onclick = "selectOneEmail('25')">
			<!--이메일 옵션 or 카테고리-->
			<div class="emailRow_options">
				<input type="checkbox" type="checkbox" name="empid" class="digitalmail_check">
                <span class="material-icons-outlined ml-2" style="color: red;">favorite</span>
                <span class="material-icons-outlined ml-2">drafts</span>
			</div>
			<!--이메일 옵션 or 카테고리-->			
			<span class="emailRow_title ml-2">박승우</span>
			<!-- 메시지 미리보기-->
			<div class="emailRow_message">
				<span>입사를 환영합니다.</span>
			</div>
			<!-- 메시지 미리보기-->
			<!--전송시간-->
			<span class="mr-3">2023-12-30 15:15:20</span>

		</div>
		<!--가로 한줄 끝-->
                    
                    <!--Fim do email Row-->
                    <div class="emailRow">
                        <div class="emailRow_options">
                            <input type="checkbox" type="checkbox" name="empid" class="digitalmail_check">
                            <span class="material-icons-outlined ml-2" style="color: red;">mark_email_unread</span>
                            <span class="material-icons-outlined ml-2">drafts</span>
                            
                        </div><!--Fechamento options-->
						
						<span class="emailRow_title ml-2">박승우</span>

                        <!--Inicio do message-->
                        <div class="emailRow_message">
                        <span>입사를 환영합니다.
                        </span>
                        </div><!--Fim do Message-->

                        <!--Inicio time-->
                        <span class="mr-3">2023-12-30 15:15:20</span>

                    </div>
                    <!--Fim do email Row-->
                    
                    <!--Fim do email Row-->
                    <div class="emailRow">
                        <div class="emailRow_options">
                            <input type="checkbox" type="checkbox" name="empid" class="digitalmail_check">
                            <span class="material-icons-outlined ml-2" style="color: red;">favorite</span>
                            <span class="material-icons-outlined ml-2">drafts</span>
                            
                        </div><!--Fechamento options-->
						
						<span class="emailRow_title ml-2">박승우</span>

                        <!--Inicio do message-->
                        <div class="emailRow_message">
                        <span>입사를 환영합니다.
                        </span>
                        </div><!--Fim do Message-->

                        <!--Inicio time-->
                        <span class="mr-3">2023-12-30 15:15:20</span>

                    </div>
                    <!--Fim do email Row-->
                    
                    <!--Fim do email Row-->
                    <div class="emailRow">
                        <div class="emailRow_options">
                            <input type="checkbox" type="checkbox" name="empid" class="digitalmail_check">
                            <span class="material-icons-outlined ml-2">favorite_border</span>
                            <span class="material-icons-outlined ml-2">mark_email_unread</span>
                            
                        </div><!--Fechamento options-->
						
						<span class="emailRow_title ml-2">박승우</span>

                        <!--Inicio do message-->
                        <div class="emailRow_message">
                        <span>입사를 환영합니다.
                        </span>
                        </div><!--Fim do Message-->

                        <!--Inicio time-->
                        <span class="mr-3">2023-12-30 15:15:20</span>

                    </div>
                    <!--Fim do email Row-->
                    
                    <!--Fim do email Row-->
                    <div class="emailRow">
                        <div class="emailRow_options">
                            <input type="checkbox" type="checkbox" name="empid" class="digitalmail_check">
                            <span class="material-icons-outlined ml-2" style="color: red;">favorite</span>
                            <span class="material-icons-outlined ml-2">drafts</span>
                            
                        </div><!--Fechamento options-->
						
						<span class="emailRow_title ml-2">박승우</span>

                        <!--Inicio do message-->
                        <div class="emailRow_message">
                        <span>입사를 환영합니다.
                        </span>
                        </div><!--Fim do Message-->

                        <!--Inicio time-->
                        <span class="mr-3">2023-12-30 15:15:20</span>

                    </div>
                    <!--Fim do email Row-->
                    
                    <!--Fim do email Row-->
                    <div class="emailRow">
                        <div class="emailRow_options">
                            <input type="checkbox" type="checkbox" name="empid" class="digitalmail_check">
                            <span class="material-icons-outlined ml-2" style="color: red;">favorite</span>
                            <span class="material-icons-outlined ml-2">drafts</span>
                            
                        </div><!--Fechamento options-->
						
						<span class="emailRow_title ml-2">박승우</span>

                        <!--Inicio do message-->
                        <div class="emailRow_message">
                        <span>입사를 환영합니다.
                        </span>
                        </div><!--Fim do Message-->

                        <!--Inicio time-->
                        <span class="mr-3">2023-12-30 15:15:20</span>

                    </div>
                    <!--Fim do email Row-->
                    
                    <!--Fim do email Row-->
                    <div class="emailRow">
                        <div class="emailRow_options">
                            <input type="checkbox" type="checkbox" name="empid" class="digitalmail_check">
                            <span class="material-icons-outlined ml-2" style="color: red;">favorite</span>
                            <span class="material-icons-outlined ml-2">drafts</span>
                            
                        </div><!--Fechamento options-->
						
						<span class="emailRow_title ml-2">박승우</span>

                        <!--Inicio do message-->
                        <div class="emailRow_message">
                        <span>입사를 환영합니다.
                        </span>
                        </div><!--Fim do Message-->

                        <!--Inicio time-->
                        <span class="mr-3">2023-12-30 15:15:20</span>

                    </div>
                    <!--Fim do email Row-->
                    
                    <!--Fim do email Row-->
                    <div class="emailRow">
                        <div class="emailRow_options">
                            <input type="checkbox" type="checkbox" name="empid" class="digitalmail_check">
                            <span class="material-icons-outlined ml-2" style="color: red;">favorite</span>
                            <span class="material-icons-outlined ml-2">drafts</span>
                            
                        </div><!--Fechamento options-->
						
						<span class="emailRow_title ml-2">박승우</span>

                        <!--Inicio do message-->
                        <div class="emailRow_message">
                        <span>입사를 환영합니다.
                        </span>
                        </div><!--Fim do Message-->

                        <!--Inicio time-->
                        <span class="mr-3">2023-12-30 15:15:20</span>

                    </div>
                    <!--Fim do email Row-->
                    
                    
                    
                    
                    
                    
                    
                   
                    
                    
                    
                    
                    
                    
                    

   
                    
                </div>
                <!-- </div> Fim do Email List list-->
        <!--</div> Fim do Email List-->


     
<!-- 결과물 시작하기 -->
     
