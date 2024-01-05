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
              
	<!-- 결과물 시작하기 <div class="main_body"> -->
	<!-- Inicio Email List  <div class="emailList"> -->
	<!-- Inicio Email List Settings-->
	<div class="emailList_settings">
	
		<!--왼쪽 세팅-->
		<div class="emailList_settingsLeft">
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
		<div class="section">
			<span class="material-icons-outlined">quiz</span> 
            <span class="list_name">답장하기</span>
        </div>
        <div class="section">
			<span class="material-icons-outlined">quiz</span> 
            <span class="list_name">전달하기</span>
        </div>
	</div> 
	<!--셀렉션 세팅 끝-->
	<!--이메일 리스트-->                   
	<div class="emailList_list">
  		<div style="background-color: #f4f5f6;">
    		<div style="margin:0px auto;max-width:1000px;">
      			<table style="margin: 0 auto; width: 100%; border-collapse: collapse;" role="presentation">
        			<tbody>
          				<tr>
            				<td style="direction:ltr;font-size:0px;padding:20px 0;padding-bottom:0px;text-align:center;">
              				</td>
          				</tr>
        			</tbody>
      			</table>
    		</div>
    		<div style="margin:0px auto;max-width:1000px;">
      			<table style="margin: 0 auto; width: 100%; border-collapse: collapse;" role="presentation">
        			<tbody>
          				<tr>
            				<td style="direction:ltr;font-size:0px;padding:20px 0;text-align:center;">
								<div style="background:#ffffff; background-color:#ffffff; margin:0px auto; border-radius:8px; max-width:1000px;">
                					<table style="margin: 0 auto; width: 100%; border-collapse: collapse; background: #ffffff; border-radius: 8px;" role="presentation">
                  						<tbody>
                    						<tr>
					                        	<td style="direction:ltr;font-size:0px;padding:20px 0;text-align:center;">
					                        		<div style="font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:middle;width:100%;">
					                          			<table style="border: 0; cellpadding: 0; cellspacing: 0; vertical-align: middle; width: 100%;" role="presentation">
					                            			<tr>
                              									<td align="center" style="font-size:0px;padding:10px 25px;word-break:break-word;">
                                									<table style="border: 0; cellpadding: 0; cellspacing: 0; border-collapse: collapse; border-spacing: 0;" role="presentation">
                                  										<tbody>
										                                    <tr>
										                                    	<td style="width:150px;">
										                                        	<img alt="Logo" height="auto" src="<%= ctxPath%>/resources/img/mailfooterlogo.png" style="border:0;display:block;outline:none;text-decoration:none;height:auto;width:100%;font-size:14px;" width="150">
										                                    	</td>
										                                    </tr>
                                  										</tbody>
                               										</table>
                            									</td>
                           									</tr>
                            								<tr>
                              									<td style="font-size:0px;padding:10px 25px;word-break:break-word;">
                                									<p style="border-top:solid 4px #f9f9f9;font-size:1px;margin:0px auto;width:100%;">
                                									</p>
                                								</td>
                            								</tr>
                         								</table>
                        							</div>

                        							<div style="font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;">
                          								<table style="border: 0; cellpadding: 0; cellspacing: 0; vertical-align:top; width: 100%;" role="presentation">
                            								<tr>
                            									<td align="center" style="font-size:0px;padding:10px 25px;word-break:break-word;">
                                									<table style="border: 0; cellpadding: 0; cellspacing: 0; border-collapse:collapse;border-spacing:0px;" role="presentation">
                                  										<tbody>
                                   	 										<tr></tr>
                                  										</tbody>
                                									</table>
                              									</td>
                            								</tr>
                            								<tr>
                              									<td align="right" style="padding:10px 25px;">
                                									<div style="font-size:14px;font-weight:400;line-height:24px;text-align:left;color:#000000;">발신자 : <a href="#" style="color: #428dfc; text-decoration: none; font-weight: bold;">${requestScope.emailVO.fk_sender_email}</a></div>
                              										<div style="font-size:14px;font-weight:400;line-height:24px;text-align:left;color:#000000;">수신자 : <a href="#" style="color: #428dfc; text-decoration: none; font-weight: bold;">${requestScope.emailVO.fk_recipient_email}</a></div>
                              										<div style="font-size:14px;font-weight:400;line-height:24px;text-align:left;color:#000000;">참조자 : <a href="#" style="color: #428dfc; text-decoration: none; font-weight: bold;">${requestScope.emailVO.fk_reference_email}</a></div>
                              										<div style="font-size:14px;font-weight:400;line-height:24px;text-align:left;color:#000000;">숨은참조자 : <a href="#" style="color: #428dfc; text-decoration: none; font-weight: bold;">${requestScope.emailVO.fk_hidden_reference_email}</a></div>
                              										<div style="font-size:14px;font-weight:400;line-height:24px;text-align:left;color:#000000;">숨은참조자 : <a href="#" style="color: #428dfc; text-decoration: none; font-weight: bold;">${requestScope.emailVO.filename}</a></div>
                              										<div style="font-size:14px;font-weight:400;line-height:24px;text-align:left;color:#000000;">숨은참조자 : <a href="#" style="color: #428dfc; text-decoration: none; font-weight: bold;">${requestScope.emailVO.filesize}</a></div>
                              										<div style="font-size:14px;font-weight:400;line-height:24px;text-align:left;color:#000000;">숨은참조자 : 아 이 우</div>
                              										<div style="font-size:14px;font-weight:400;line-height:24px;text-align:left;color:#000000;">	
                              											<c:forEach var="orgFileName" items="${emailVO.filename_split}" varStatus="status">
																			<a href="<%= request.getContextPath()%>/downloadfile.gw?send_email_seq=${requestScope.emailVO.send_email_seq}&index=${status.index}">${emailVO.filename_split[status.index]}</a>&nbsp;&nbsp;<span style="color: #ccc; font-size: 6pt;">${emailVO.filesize_split[status.index]}</span>&nbsp;&nbsp;
																		</c:forEach>
																	</div>	
                              									</td>
                            								</tr>
								                            <tr>
								                                <td align="left" style="font-size:0px;padding:10px 25px;word-break:break-word;">
								                                	<div style="font-size:18px;font-weight:400;line-height:24px;text-align:left;color:#000000;">
								                                  		<h1 style="margin: 0; font-size: 32px; line-height: 40px; font-weight: 700;">${requestScope.emailVO.email_subject}</h1>
								                                	</div>
								                              	</td>
								                            </tr>
                            								<tr>
                              									<td align="left" style="font-size:0px;padding:10px 25px;">
                                									<div style="font-size:18px;font-weight:400;line-height:24px;text-align:left;color:#000000;">${requestScope.emailVO.email_contents}
                                									<%--<a href="#" style="color: #428dfc; text-decoration: none; font-weight: bold;">이것저것 링크?</a> 본문이 들어갈 자리 끝 --%>
                                									</div>
                              									</td>
                            								</tr>
								                            <tr>
								                            	<td align="left" style="font-size:0px;padding:10px 25px;word-break:break-word;">
								                                </td>
								                            </tr>
								                            <tr>															
								                                <td align="center" style="font-size:0px;padding:10px 25px;word-break:break-word; vertical-align:middle; ">
	                                								<table style="border: 0; cellpadding: 0; cellspacing: 0; border-collapse:separate; line-height:100%;" role="presentation">
	                                  									<tr>
	                                    									<td align="center" role="presentation" style="border:none;border-radius:3px;cursor:auto;mso-padding-alt:10px 25px;background:#428dfc;" valign="middle">
	                                      										<%--
	                                      										<a href="#" style="display: inline-block; background: #428dfc; color: #ffffff;  font-size: 14px; font-weight: bold; line-height: 30px; margin: 0; text-decoration: none; text-transform: uppercase; padding: 10px 25px; border-radius: 3px;"> 버튼 쓸거면 </a>
	                                    										 --%>
	                                    									</td>
	                                  									</tr>
	                                								</table>
	                              								</td>
								                            </tr>
								                            <tr>
								                              <td align="center" style="font-size:0px;padding:10px 25px;word-break:break-word;">
								                              </td>
								                            </tr>
								                        </table>
								                    </div>
												</td>
                    						</tr>
                  						</tbody>
                					</table>
              					</div>
              					<div style="margin:0px auto;max-width:1000px;">
                					<table style="margin:0px auto; width:100%; border: 0; cellpadding: 0; cellspacing: 0;" role="presentation">
                  						<tbody>
                    						<tr>
                      							<td style="direction:ltr;font-size:0px;padding:20px 0;text-align:center;">
                        							<div style="font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;">
                         								<table role="presentation" style="vertical-align:top; width:100%; border: 0; cellpadding: 0; cellspacing: 0;">
                            								<tr>
                            									<td align="center" style="font-size:0px;padding:10px 25px;word-break:break-word;">
								                                <%--
								                                	<div style="font-family:Quattrocento;font-size:16px;font-weight:400;line-height:24px;text-align:center;color:#333333;">도움이 필요하시면 <a href="#" style="color: #428dfc; text-decoration: none; font-weight: bold;"> k8910275@naver.com </a> 으로 연락주세요</div>
								                              	 --%>
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

    		<div style=" margin:0px auto; width: 80%;">
    			<div class="section-title" style="margin-bottom: 30px;">
        			<h5 style="	font-weight: 600;
						line-height: 21px;
						text-transform: uppercase;
						padding-left: 20px;
						position: relative; font-size: 18px;">간편답장하기</h5>
      			</div>
      			<form action="#">
        			<textarea placeholder="왜 안나오노" style="width: 100%;
								font-size: 15px;
								color: #black;
								padding-left: 20px;
								padding-top: 12px;
								height: 150px;
								border: none;
								border-radius: 5px;
								resize: none;
								margin-bottom: 24px;"></textarea>
          			<button style="display: block;
          					margin: 10px auto;
          					font-size: 11px;
							color: #ffffff;
							font-weight: 700;
							letter-spacing: 2px;
							text-transform: uppercase;
							background: #e53637;
							border: none;
							padding: 10px 15px;
							border-radius: 2px;" type="submit"><i class="fa fa-location-arrow"></i> 보내기
					</button>
      			</form>
      			
      			<div><span style="color: #f4f5f6;">ddd</span></div>
  			</div>
	
    		
    		
	    </div>
	</div>
 
               


<!-- 결과물 시작하기 -->
