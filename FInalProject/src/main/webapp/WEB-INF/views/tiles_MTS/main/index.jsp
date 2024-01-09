<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
%>
    <style>
         /* 전역 스타일 */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
         	paddig : 20px;
        }
        .parent {
		display: grid;
		grid-template-columns: repeat(5, 1fr);
		grid-template-rows: repeat(5, 1fr);
		grid-column-gap: 20px;
		grid-row-gap: 20px;
		height:600px;
		}
		
		.div1 { grid-area: 1 / 2 / 4 / 4; }
		.div2 { grid-area: 4 / 2 / 5 / 4; }
		.div3 { grid-area: 5 / 2 / 6 / 4; }
		.div4 { grid-area: 1 / 1 / 2 / 2; }
		.div5 { grid-area: 2 / 1 / 6 / 2; }
		.div6 { grid-area: 1 / 4 / 3 / 6; }
		.div7 { grid-area: 3 / 4 / 5 / 6; }
		.div8 { grid-area: 5 / 4 / 6 / 6; }
		
		.widget-container {
		    background-color: #ffffff;
		    padding: 20px;
		    border-radius: 8px;
		    box-shadow: 0 8px 12px 0 rgba(0, 0, 0, 0.1);   

		}
        
        .highcharts-figure,
	.highcharts-data-table table {
	    min-width: 320px;
	    max-width: 800px;
	    margin: 1em auto;
	}
	
	.highcharts-data-table table {
	    font-family: Verdana, sans-serif;
	    border-collapse: collapse;
	    border: 1px solid #ebebeb;
	    margin: 10px auto;
	    text-align: center;
	    width: 100%;
	    max-width: 500px;
	}
	
	.highcharts-data-table caption {
	    padding: 1em 0;
	    font-size: 1.2em;
	    color: #555;
	}
	
	.highcharts-data-table th {
	    font-weight: 600;
	    padding: 0.5em;
	}
	
	.highcharts-data-table td,
	.highcharts-data-table th,
	.highcharts-data-table caption {
	    padding: 0.5em;
	}
	
	.highcharts-data-table thead tr,
	.highcharts-data-table tr:nth-child(even) {
	    background: #f8f8f8;
	}
	
	.highcharts-data-table tr:hover {
	    background: #f1f7ff;
	}

    </style>
    
<script src="<%= ctxPath%>/resources/Highcharts-10.3.3/code/highcharts.js"></script>
<script src="<%= ctxPath%>/resources/Highcharts-10.3.3/code/modules/exporting.js"></script>
<script src="<%= ctxPath%>/resources/Highcharts-10.3.3/code/modules/export-data.js"></script>
<script src="<%= ctxPath%>/resources/Highcharts-10.3.3/code/modules/accessibility.js"></script>
    
<script>

$(document).ready(function(){
	
	showWeather();
	   // AJAX 요청
    $.ajax({
        url: "<%= ctxPath%>/scheduleselect.gw",
        type: "get",
        dataType: "json",
        success: function (json) {
        	// console.log(JSON.stringify(json));
        	
        	
        	console.log(json.length);
        	$("span#scheduleCnt").html(json.length);
        	
        	let s_html = "";
        	
		      if(json.length > 0) {
		    	  $.each(json, function(index, item){
		    		  
		    		  s_html += "<tr class='scheduleinfo'>";
		    		  s_html +=    "<td style='font-size:7pt;'>"+ item.enddate +"</td>";
		    		  s_html +=    "<td>"+ item.subject +"</td>"; 
		    		  s_html +=  "</tr>"; 
		    	  });
		      }
		      
		      else {
		    	  s_html +=  "<tr>";
		    	  s_html +=  "<td colspan='3'>일정 없음</td>";
		    	  s_html +=  "</tr>";
		      }
		      
		        
		      $("tbody#scheduleinfo").html(s_html);
         		 	
        },
        error: function (request, status, error) {
            alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
        }
    }); // end of ajax
    
    
    // 출근, 퇴근 신청 [시작]
    var now = new Date();	
	var year = now.getFullYear();  // 현재 해당 년도
	var month = now.getMonth()+1;  // 현재 해당 월
	var day = now.getDate(); 	   // 현재 해당 일자
	
	var hour = now.getHours(); 	   // 현재 해당 시각
	var minute = now.getMinutes(); // 현재 해당 분
	var second = now.getSeconds(); // 현재 해당 초
	
	// 한 자리 수일 때 앞에 0 붙이기 (함수 활용)
	hour = padZero(hour);
	minute = padZero(minute);
	second = padZero(second);
	month = padZero(month);
	day = padZero(day);

	const FullDate = year+"-"+month+"-"+day; 	 // 2023-12-27
	const FullTime = hour+":"+minute+":"+second; // 16:31:25
    
 	// 출근 버튼 클릭시  근무 테이블에 insert
    $("button#goToWork").click(function(){

    	$("input[name='work_date']").val(FullDate);
    	$("input[name='work_start_time']").val(FullTime);
    	
    	const frm = document.goToWorkInsert;
	    frm.method = "post";
	    frm.action = "<%= ctxPath %>/goToWorkInsertIndex.gw";
	    frm.submit(); 
	    
    }); // end of $("button#goToWork").click(function()--------------------
    
 	// 퇴근 버튼 클릭시  근무 테이블 update
    $("button#leaveWork").click(function(){
    	
    	$("input[name='work_date']").val(FullDate);
	    $("input:hidden[name='work_end_time']").val(FullTime);
	    
		/////////////////////////////////////////////////////
		// 출근시간 날짜형식으로 변환
	    var TodayStartTime = $('#todayST').val(); // 로그인 한 사원의 오늘 출근 시간
	    
	    var TodayHour = TodayStartTime.substr(0, 2); // 출근 시 구하기
	    
	    var TodayMinute = TodayStartTime.substr(3, 2); // 출근 분 구하기
	    
	    var TodaySecond = TodayStartTime.substr(6); // 출근 초 구하기
	    
	    todayStartTimeVal = new Date();
	    
	    todayStartTimeVal.setHours(TodayHour);
	    todayStartTimeVal.setMinutes(TodayMinute);
	    todayStartTimeVal.setSeconds(TodaySecond);
	    
	    /////////////////////////////////////////////////////
	    // 근무 시간 가져오기
	    var workTimeNow = now - todayStartTimeVal;
	  
	    var workTimeH = Math.floor(workTimeNow / (1000 * 60 * 60)); // 근무 시간
	    
	    var workTimeM = Math.floor(workTimeNow / (1000 * 60) % 60); // 근무 분
	    
	    var workTimeC = Math.floor((workTimeNow / 1000) % 60);	    // 근무 초
	    
	    var workTimeVal = padZero(workTimeH)+":"+padZero(workTimeM)+":"+padZero(workTimeC);
	 	///////////////////////////////////////////////////////////////
	 	// 연장 근무 시간 구하기
	 	// 9시간의 밀리초는 32400000 - 23611081 = 12345125
	 	
	 	if(workTimeNow > 32400000) { // 연장근무인 경우
	 		var calculateTime = workTimeNow - 32400000;

	 		var calH = Math.floor(calculateTime / (1000 * 60 * 60)); // 연장 시간
		    
		    var calM = Math.floor(calculateTime / (1000 * 60) % 60); // 연장 분
		    
		    var calC = Math.floor((calculateTime / 1000) % 60); 	 // 연장 초
	 		
		    var overTimeVal = padZero(calH)+":"+padZero(calM)+":"+padZero(calC);
		 	            
		 	$("input[name='extended_end_time']").val(overTimeVal);
		 	
		 	const frm = document.goToWorkUpdateWithExtended; 
			frm.method = "post";
		    frm.action = "<%= ctxPath %>/goToWorkUpdateWithExtendedIndex.gw";
			frm.submit();
	 	}
	 	else {
	 		const frm = document.goToWorkUpdateWithExtended; 
			frm.method = "post";
		    frm.action = "<%= ctxPath %>/goToWorkUpdateIndex.gw";
			frm.submit();
	 	}
    }); // end of $("button#goToWork").click(function()--------------------
 	// 출근, 퇴근 신청 [끝]

}); // end of $(document).ready(function(){})-----------------

// ------ 기상청 날씨정보 공공API XML 데이터 호출하기 ------ //
function showWeather(){
	
	$.ajax({
		url:"<%= ctxPath%>/opendata/weatherXML.gw",
		type:"get",
		dataType:"xml",
		success:function(xml){
			const rootElement = $(xml).find(":root"); 
		//	console.log("확인용 : " + $(rootElement).prop("tagName") );
			// 확인용 : current
			
			const weather = rootElement.find("weather"); 
			const updateTime = $(weather).attr("year")+"년 "+$(weather).attr("month")+"월 "+$(weather).attr("day")+"일 "+$(weather).attr("hour")+"시"; 
		//	console.log(updateTime);
			// 2023년 12월 19일 10시
			
			const localArr = rootElement.find("local");
		//	console.log("지역개수 : " + localArr.length);
			// 지역개수 : 97
			
			let html = "날씨정보 발표시각 : <span style='font-weight:bold;'>"+updateTime+"</span>&nbsp;";
	            html += "<span style='color:blue; cursor:pointer; font-size:9pt;' onclick='javascript:showWeather();'>업데이트</span><br/><br/>";
	            html += "<table class='table table-hover' align='center'>";
		        html += "<tr>";
		        html += "<th>지역</th>";
		        html += "<th>날씨</th>";
		        html += "<th>기온</th>";
		        html += "</tr>";
		    
		     // ====== XML 을 JSON 으로 변경하기  시작 ====== //
				var jsonObjArr = [];
			 // ====== XML 을 JSON 으로 변경하기  끝 ====== //    
		        
		    for(let i=0; i<localArr.length; i++){
		    	
		    	let local = $(localArr).eq(i);
				/* .eq(index) 는 선택된 요소들을 인덱스 번호로 찾을 수 있는 선택자이다. 
				      마치 배열의 인덱스(index)로 값(value)를 찾는 것과 같은 효과를 낸다.
				*/
				
		    //	console.log( $(local).text() + " stn_id:" + $(local).attr("stn_id") + " icon:" + $(local).attr("icon") + " desc:" + $(local).attr("desc") + " ta:" + $(local).attr("ta") ); 
		      //	속초 stn_id:90 icon:03 desc:구름많음 ta:-2.5
		      //	북춘천 stn_id:93 icon:03 desc:구름많음 ta:-7.0
		    	
		        let icon = $(local).attr("icon");  
		        if(icon == "") {
		        	icon = "없음";
		        }
		      
		        html += "<tr>";
				html += "<td>"+$(local).text()+"</td><td><img src='<%= ctxPath%>/resources/images/weather/"+icon+".png' />"+$(local).attr("desc")+"</td><td>"+$(local).attr("ta")+"</td>";
				html += "</tr>";
		        
				
				// ====== XML 을 JSON 으로 변경하기  시작 ====== //
				   var jsonObj = {"locationName":$(local).text(), "ta":$(local).attr("ta")};
				   
				   jsonObjArr.push(jsonObj);
				// ====== XML 을 JSON 으로 변경하기  끝 ====== //
				
		    }// end of for------------------------ 
		    
		    html += "</table>";
		    
		    $("div#displayWeather").html(html);
		    
		    
		 // ====== XML 을 JSON 으로 변경된 데이터를 가지고 차트그리기 시작  ====== //
			var str_jsonObjArr = JSON.stringify(jsonObjArr); 
			                  // JSON객체인 jsonObjArr를 String(문자열) 타입으로 변경해주는 것 
			                  
			$.ajax({
				url:"<%= ctxPath%>/opendata/weatherXMLtoJSON.gw",
				type:"POST",
				data:{"str_jsonObjArr":str_jsonObjArr},
				dataType:"JSON",
				success:function(json){
					
				//	alert(json.length);
					
					// ======== chart 그리기 ========= // 
					var dataArr = [];
					$.each(json, function(index, item){
						dataArr.push([item.locationName, 
							          Number(item.ta)]);
					});// end of $.each(json, function(index, item){})------------
					
					
					Highcharts.chart('weather_chart_container', {
					    chart: {
					        type: 'column'
					    },
					    title: {
					        text: '오늘의 전국 기온(℃)'   // 'ㄹ' 을 누르면 ℃ 가 나옴.
					    },
					    subtitle: {
					    //    text: 'Source: <a href="http://en.wikipedia.org/wiki/List_of_cities_proper_by_population">Wikipedia</a>'
					    },
					    xAxis: {
					        type: 'category',
					        labels: {
					            rotation: -45,
					            style: {
					                fontSize: '10px',
					                fontFamily: 'Verdana, sans-serif'
					            }
					        }
					    },
					    yAxis: {
					        min: -10,
					        title: {
					            text: '온도 (℃)'
					        }
					    },
					    legend: {
					        enabled: false
					    },
					    tooltip: {
					        pointFormat: '현재기온: <b>{point.y:.1f} ℃</b>'
					    },
					    series: [{
					        name: '지역',
					        data: dataArr, // **** 위에서 만든것을 대입시킨다. **** 
					        dataLabels: {
					            enabled: true,
					            rotation: -90,
					            color: '#FFFFFF',
					            align: 'right',
					            format: '{point.y:.1f}', // one decimal
					            y: 10, // 10 pixels down from the top
					            style: {
					                fontSize: '10px',
					                fontFamily: 'Verdana, sans-serif'
					            }
					        }
					    }]
					});
					// ====== XML 을 JSON 으로 변경된 데이터를 가지고 차트그리기 끝  ====== //
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});                  
			///////////////////////////////////////////////////
			
		},// end of success: function(xml){ }------------------
		
		error: function(request, status, error){
			alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		}
	});
	
}// end of function showWeather()--------------------

// 주어진 값이 10보다 작을 경우 그 앞에 0을 붙여서 두 자리 수로 만드는 함수
function padZero(value) {
    return value < 10 ? "0" + value : value;
}

</script>
	
    
    
    
    
<body>
   <div class="parent">
	   <div class="widget-container div4">
	            <div class="widget-header">프로필</div>
		            <div >
				
					<div id="photo" class="mx-2">
						<img src="<%= ctxPath%>/resources/images/${requestScope.loginuser.photo}" style="width: 100px; height: 100px; border-radius: 50%;" />
					</div>
						
					<table id="table1" class="myinfo_tbl">
						<tr>
							<th width="50%;">성명</th>
							<td>${requestScope.loginuser.name}</td>
						</tr>
						<tr>	
							<th>이메일</th>
							<td>${requestScope.loginuser.email}</td>
						</tr>
						<tr>
							<th>입사일자</th>
							<td>${requestScope.loginuser.hire_date}</td>
						</tr>
					</table>
				
			</div>
        </div>
	    <!-- todo -->
	    <div class="widget-container div5">
	        <div class="widget-header">To-Do</div>
		        <div class="listContainer ">
					<h5 class="mb-3">결재 대기 문서</h5>
					<h6 class="mb-3">결재해야 할 문서가 <span style="color:#086BDE" id="draftCnt">${requestedDraftCnt}</span>건 있습니다.</h6>
					
						<div class="text-right mr-2 more">
						<a href="<%= ctxPath%>/approval/requested.gw"><i class="fas fa-angle-double-right"></i> 더보기</a>
					</div>
				</div>
				 <div class="listContainer">
					<h5 class="my-3">오늘의 일정 <span id="scheduleCnt"></span>건</h5> 
					
					<div class="today_work_schedule">
						<table>
						<thead>
							<tr>
								
					            <th>종료일</th>
					            <th style='text-align:center;'>내용</th>
				            </tr>
			            </thead>
			            <tbody id="scheduleinfo">
			            </tbody>
			            
			            </table>
					</div>
						<div class="text-right mr-2 more">
						<a href="<%= ctxPath%>/schedule/scheduleManagement.gw"><i class="fas fa-angle-double-right"></i> 더보기</a>
					</div>
				</div>
	    </div>
	    
	     	<!-- 일정관리 -->
	        <div class="widget-container div1 ">
	            <div class="widget-header">일정관리</div>
	            <!-- 웹메일 컨텐츠 -->
	            <!-- ... -->
	        </div>
	        
	        <!-- 일정관리 -->
	        <div class="widget-container div3 ">
	            <div class="widget-header">검색</div>
	            <!-- 웹메일 컨텐츠 -->
	            <!-- ... -->
	        </div>
	         <!-- 출퇴근 -->
	        <div class="widget-container div6 ">
	            <div class="widget-header">출퇴근</div>
	           <ul>
	            <li class="ml-auto mt-2" style="margin-right: 13%;">
					<button id="goToWork" class="btn btn-sm btn-success" style="width: 50px;">출근</button>
                	<button id="leaveWork" class="btn btn-sm btn-danger ml-3" style="width: 50px;">퇴근</button>
                	
                	<form name="goToWorkInsert">
                		<input type="hidden" name="work_date"/>
                		<input type="hidden" name="work_start_time"/>
                		<input type="hidden" name="fk_employee_id" value="${sessionScope.loginuser.employee_id}"/>
                	</form>
                	
                	<%-- 연장근무인 경우 보낼 form --%>
                	<form name="goToWorkUpdateWithExtended">
                		<input type="hidden" name="work_date"/>
                		<input type="hidden" name="work_end_time"/>
                		<input type="hidden" name="extended_end_time"/>
                		<input type="hidden" name="fk_employee_id" value="${sessionScope.loginuser.employee_id}"/>
                	</form>
                	<input type='hidden' id='todayST' value='${requestScope.myTodayStartTime}'/>
				</li>
		      </ul>
	        </div>
	    	 <!-- 웹메일 -->
	        <div class="widget-container div7 ">
	            <div class="widget-header">웹메일</div>
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
	        </div>
	        
	        <!-- 공지사항 -->
	        <div class="widget-container div2 ">
	            <div class="widget-header">공지사항</div>
	            <!-- 공지사항 컨텐츠 -->
	            <!-- ... -->
	        </div>
	
	
	       
	  
	    
	     <!-- 나머지 위젯을 옆으로 배치하기 위한 div 추가 -->
	   <div class="div8">
			<%-- 차트그리기 --%>
			<figure class="highcharts-figure">
			    <div id="weather_chart_container" class=" widget-container"></div>
			</figure>
			</div> 
 </div>
		
    