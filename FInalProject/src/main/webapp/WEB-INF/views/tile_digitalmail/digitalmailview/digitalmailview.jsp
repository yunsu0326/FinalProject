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
      }); 
        
        
        
  </script>
  
  <style type="text/css">
  	
  	div.range{
  		margin:0px auto;
  		max-width:1000px;
  	
  	}
  	
  	div.main{
  		background:#ffffff; 
  		border-radius:8px;
  	
  	}
  	
  	td.tdsize{
  		padding:20px 0; 
  		text-align:center;
  	}
  	
  	table.tablesize{
  		margin: 0 auto; 
  		width: 100%; 
  		border-collapse: collapse; 
  		background: #ffffff; 
  		border-radius: 8px;" 
  	}
  	
  	p.pline{
  		border-top:solid 4px #f9f9f9;
  		font-size:1px;
  		margin:0px auto;
  		width:100%;
  	}
  	
  	div.title{
  		font-size:16px;
  		font-weight: bold;
  		line-height:24px;
  		text-align:left;
  		color: black;"
  	}
  	
  	div.Emailinfo{
  	   display: inline-block;
   	   float: left;
  	}
  	
  	div.Emailinfotable {
   		font-size: small;
	}

	.Emailinfotable th {
	   background-color: #E0F8EB;
	   vertical-align: middle;
	   text-align: center;
	}	
	
	
	.o_span{
	     padding: 1px 5px;
	     margin: 1px 5px;
	     background-color: #E0F8EB;
	     align-items: center;
	     width: 33%;
	     text-align: center;
	     border-radius: 5px; 
	 }
	 
	 div.file_span{
	 
	 	 padding: 1px 5px;
	     margin: 1px 5px;
	     background-color: #E0F8EB;
	     align-items: center;
	     width: 45%;
	     text-align: center;
	     border-radius: 5px; 
	 
	 
	 }
  
  </style>
	<div class="emailList_settings">
	<!--왼쪽 세팅-->
		<div class="emailList_settingsLeft">
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
			<div class="icon_set">
				<span class="material-icons-outlined icon_img" style="font-size: 24pt;">chevron_left</span>
				<span class="icon_text">이전</span>
			</div>
			<div class="icon_set">
				<span class="material-icons-outlined icon_img" style="font-size: 24pt;">chevron_right</span>
				<span class="icon_text">다음</span>
			</div>     
		</div>
	</div>
	<!--셀렉션 세팅-->
	
	
	
	<div class="emailList_sections">
    	<div class="section section_selected show">
        	<span class="material-icons-outlined" style="font-size:24px;"> inbox </span> 
			<span class="list_name">메일보기</span>
		</div>
	</div> 
	<!--셀렉션 세팅 끝-->
	
	<!--이메일 리스트-->                   
	<div class="emailList_list">
  		<div style="background-color: #f4f5f6;">
    		<div class="range">
      			<table class="tablesize">
        			<tbody>
          				<tr>
            				<td class="tdsize">
            					<div class="range main">
                				<table class="tablesize">
                  				<tbody>
                    				<tr>
										<td class="tdsize">
											<div class="range">
											<table class="tablesize">
												<tr>
					                            	<td align="center" style="padding:10px 25px;">
                                					<table>
                                  						<tbody>
															<tr>
																<td style="width:200px;"><img style="border:0; display:block; height:auto; width:100%;" alt="Logo" src="<%= ctxPath%>/resources/img/logo.png"></td>
															</tr>
                                  						</tbody>
                               						</table>
                            						</td>
                            									<%-- 상단 이미지 공간 --%>
                           						</tr>
                         					</table>
                        					</div>

                        					<div style="display:inline-block; vertical-align:top; width:100%;">
                          						<table class="tablesize" >
                          							<tr>
                            							<td class="tdsize"><p class="pline"></p></td>
                                					</tr>		
                            						<tr>
                              							<td style="padding:10px 25px;">
												            <h5 class='text-left' style="margin: 0; font-size: 20px; line-height: 40px; font-weight: 700;">메일 정보</h5>
															<h5 class='text-left' style="font-size: 10px;">시간 : ${requestScope.emailVO.send_time}</h5>
														<table class='table Emailinfotable table-bordered text-left'>
															<tr>
																<th>보낸사람</th>
																<td><div class="o_span">${requestScope.emailVO.fk_sender_email}</div></td>
															</tr>
											                <tr>
											                    <th>받은사람</th>
											                    <td>
												                    <div style="display: flex; flex-wrap: wrap;">
																	<c:forEach var="fk_recipient_email" items="${emailVO.fk_recipient_email_split}" varStatus="status">
																	    <div class="o_span">${fk_recipient_email}</div>
																	</c:forEach>   
																    </div>
											                    </td>
										                    </tr>
															<tr>
																<th>참조자</th>
																<td>
																	<c:if test="${not empty emailVO.fk_reference_email}">
																		<div style="display: flex; flex-wrap: wrap;">
														                  	<c:forEach var="Fk_reference_email" items="${emailVO.fk_reference_email_split}" varStatus="status">
																				<div class="o_span">${Fk_reference_email}</div>
																			</c:forEach>
																		</div>
																	</c:if> 
																</td>
															</tr>              
															<c:if test="${not empty emailVO.fk_hidden_reference_email}">
											                	<c:forEach var="Fk_hidden_reference_email" items="${emailVO.fk_hidden_reference_email_split}" varStatus="status">
															    	<c:if test="${Fk_hidden_reference_email == sessionScope.loginuser.email}">
																		<tr>																																	               
												                    		<th>숨은참조자</th>
												               	    		<td><div class="o_span">${sessionScope.loginuser.email}</div></td>	
																		</tr>
																	</c:if>
															    </c:forEach>
															</c:if>
															<tr>
																<th>첨부파일</th>
																<td>
																<c:if test="${not empty emailVO.filename}">
																	<div style="display: flex; flex-wrap: wrap;">
														             	<c:forEach var="orgFileName" items="${emailVO.filename_split}" varStatus="status">
																			<div class="file_span"><a href="<%= request.getContextPath()%>/email_downloadfile.gw?send_email_seq=${requestScope.emailVO.send_email_seq}&index=${status.index}">${emailVO.filename_split[status.index]}</a>&nbsp;&nbsp;<span style="font-size: 8pt;">${emailVO.filesize_split[status.index]}</span></div>
																		 </c:forEach>
																	  </div>
																  </c:if>
																</td>
															</tr>
														</table>
														</td>
                            						</tr>		                             								<tr>
													<tr>
                              							<td class="tdsize"><p class="pline"></p></td>
                            						</tr>
                            						<tr>
								                    	<td align="left" style="padding:10px 25px;">
								                        <div style="font-size:18px;font-weight:400;line-height:24px;text-align:left;color:#000000;">
								                        	<h5 style="margin: 0; font-size: 20px; line-height: 40px; font-weight: 700;">제목 :${requestScope.emailVO.email_subject}</h5>
								                        </div>        	
								                        </td>       	
								                    </tr>     	
								                    <tr>
                              							<td align="left" style="font-size:0px;padding:10px 25px;">
                                							<div style="font-size:18px;font-weight:400;line-height:24px;text-align:left;color:#000000;">${requestScope.emailVO.email_contents}
                                							</div>		
                              							</td>
                            						</tr>        		
                            					</table>		
                            				</div>			
								        </td>	                  
								    </tr>                  
                            	</tbody>							
							</table>	                        
						</div>		                   
						</td>						
                    </tr>						
                </tbody>						
               </table> 					
              </div>					
		</div>				
	</div>			