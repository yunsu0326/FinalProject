<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
   String ctxPath = request.getContextPath(); 
	System.out.println("ctxPath" + ctxPath);
%>
<style type="text/css">
    
          /* 전체 스타일 코드 */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        h2 {
            text-align: center;
            margin: 50px 0;
        }
		/* 폼 스타일 변경 */
		form[name="searchFrm"] 
		{
		    display: flex;
		    justify-content: right;
		    align-items: center;
		    margin-bottom: 20px;
		    background-color: #ffffff;
		    padding: 15px;
		    border-radius: 8px;
		    box-shadow: 0 8px 12px 0 rgba(0, 0, 0, 0.1);
		}
		
		/* 입력 필드 및 버튼 스타일 변경 */
		select,
		input[type="text"],
		button#btnSearch,
		button#btnReset {
		    height: 30px;
		    margin-right: 40px; /* 여기서 마진값을 조절하여 간격을 조정할 수 있습니다. */
		    border-radius: 5px;
		    border: 1px solid #ddd;
		    padding: 0 15px;
		    transition: border-color 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
		}
		
		/* 검색 및 초기화 버튼 스타일 변경 */
		button#btnSearch,
		button#btnReset {
		    color: white;
		    border: none;
		    cursor: pointer;
		    padding: 0 15px;
		}
		
		/* 검색 버튼 호버 효과 */
		button#btnSearch:hover,
		button#btnReset:hover {
		    opacity: 0.8;
		}

        /* 테이블 스타일 변경 */
        table#emptbl {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 8px 12px 0 rgba(0, 0, 0, 0.1);
        }

        /* 테이블 헤더 스타일 변경 */
        table#emptbl th {
            background-color: #ffffff;
            text-align: center;
            padding: 12px;
            font-weight: bold;
        }

        /* 테이블 셀 스타일 변경 */
        table#emptbl td {
            border: solid 1px #ddd;
            padding: 8px;
            text-align: center;
        }

        /* 테이블 로우 스타일 변경 */
        tr.empinfo {
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        /* 테이블 로우 호버 효과 */
        tr.empinfo:hover {
            background-color: #f9f9f9;
        }

        /* 페이지 바 스타일 */
        div[align="center"] {
            border: solid 0px gray;
            width: 80%;
            margin: 30px auto;
        }

        /* 이름 디자인 */
        div#name {
            text-align: center;
            margin-bottom: 50px;
        }
		        
		 /* 모달 내의 테이블 스타일링 */
		.modal-body table {
		    width: 100%;
		    border-collapse: collapse;
		    margin-bottom: 20px;
		}
		
		/* 테이블 헤더 스타일 */
		.modal-body th {
		    background-color: #f2f2f2;
		    border: 1px solid #ddd;
		    padding: 8px;
		    text-align: left;
		}
		
		/* 테이블 데이터 셀 스타일 */
		.modal-body td {
		    border: 1px solid #ddd;
		    padding: 8px;
		    text-align: left;
		}
		
		/* 테이블 둥근 테두리와 그림자 효과 */
		.modal-body table {
		    border-radius: 8px;
		    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
		}

        
    </style>

<script type="text/javascript">

$(document).ready(function(){
	
	const ctxPath = '<%= ctxPath%>';


	
    $("button#btnSearch").click(function(){
        const frm = document.searchFrm;
        const deptName = $("#deptName").val();
        const gradeLV = $("#gradeLV").val();
        const gender = $("#gender").val();
        
        
        // Append selected values to the URL query parameters
        frm.action = "<%= ctxPath%>/emp/empList.gw?deptName=" + deptName + "&gradeLV=" + gradeLV + "&gender=" + gender;
        frm.submit();
    });
    

   	    // 초기화 버튼 클릭 시 폼 초기화
   	    $("button#btnReset").click(function() {
   	      const frm = document.searchFrm;
   	      $("#deptName").val(''); // 부서 선택 초기화
   	   	  $("#gradeLV").val(''); // 직급 선택 초기화
   	      $("#gender").val(''); // 성별 선택 초기화

   	      frm.action = "<%= ctxPath %>/emp/empList.gw"; // 폼 액션 초기화
   	      frm.submit();
   	    });

   	    
   	    
  	  // 특정 회원을 클릭하면 회원의 상세정보
     $(document).on("click", "table#emptbl tr.empinfo", function (e) {
//         alert("클릭");
       	const imagePath = '<%= ctxPath %>/resources/images/files/';
	    const employee_id = $(this).find("input:hidden[name='employee_id']").val();
		 // alert(employee_id);
	    const modalPhoto = document.getElementById('modal_photo');
		$.ajax({
			url:"<%= ctxPath%>/emp/empOneDetail.gw",
		     data:{"employee_id":employee_id},
		     dataType:"json",
		     success:function(json){
		           console.log(JSON.stringify(json));
		           // {"empOneDetail":{"fk_department_id":"100","t_manager_name":"이요섭","salary":"100000000","t_manager_id":"10000","gradelevel":"5","t_manager_email":"leeys@naver.com","t_manager_job_name":"개발 부서장","manager_phone":"01097370275","manager_id":"9999","t_manager_phone":"01097370275","jubun":"8607191","email":"leeys@naver.com","fk_team_id":"1","address":"  ","idle":"0","department_name":"개발부","manager_job_name":"개발 부서장","hire_date":"2023-12-05 17:07:56","manager_name":"이요섭","job_name":"개발 부서장","phone":"01097370275","employee_id":"9999","name":"이요섭","basic_salary":"100000000","manager_email":"leeys@naver.com","status":"1"}}
					if (json && json.empOneDetail) {
				        const empData = json.empOneDetail;
				
				        // 모달에 데이터 삽입
   				        
       					$("#modal_photo").html("<img src='<%= ctxPath %>/resources/empImg/" + empData.photo + "'>");
       					$("#modal_employee_id").text(empData.employee_id);
				        $("#modal_department_name").text(empData.department_name);
				        $("#modal_job_name").text(empData.job_name);
				        $("#modal_team_name").text(empData.team_name);
				        $("#modal_name").text(empData.name);
				        $("#modal_email").text(empData.email);
				        $("#modal_post_code").text(empData.postcode);
				        $("#modal_address").text(empData.address);
				        $("#modal_phone").text(empData.phone);
				        $("#modal_hire_date").text(empData.hire_date);
				        $("#modal_birthday").text(empData.birthday);
				        $("#modal_gender").text(empData.gender);

				        $("#modal_salary").text(empData.salary);
				        $("#modal_bank_code").text(empData.bank_name + ", " +empData.bank_code);
				        
				        $("#modal_annual").text(empData.annual);
				        $("#modal_family_care").text(empData.family_care);
				        $("#modal_reserve_forces").text(empData.reserve_forces);
				        $("#modal_infertility_treatment").text(empData.infertility_treatment);
				        $("#modal_childbirth").text(empData.childbirth);
				        $("#modal_marriage").text(empData.marriage);
				        $("#modal_reward").text(empData.reward);
				        
				        $("#button").html("<button type='button' id='btn_edit' onclick='javascript:location.href=\"" + '<%= ctxPath %>/infoEdit.gw?employee_id=' + empData.employee_id + "\"'>수정하기</button>");


				        
				        // 모달 열기
				        $("#modal_member").modal("show");

				       				        
				    } else {
				        alert("JSON 데이터가 유효하지 않습니다.");
				    }
		     }
		     , error: function(request, status, error){
			     alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
		     }
		          
		     });
 		
    }); //$(document).on("click", "table#emptbl tr.empinfo", function (e) {
  	  
		
    	

	// 우편번호 input태그 누르면 다음 주소찾기 띄우기
	$("input:text[name='postcode']").on("focus", function(e) {
		$("input#address").removeAttr("readonly");
	    
	    // 참고항목을 쓰기가능 으로 만들기
	    $("input#extraAddress").removeAttr("readonly");
	    
		new daum.Postcode({
	        oncomplete: function(data) {
	           
	            let addr = ''; 
	            let extraAddr = ''; 

	            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                addr = data.roadAddress;
	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                addr = data.jibunAddress;
	            }

	            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	            if(data.userSelectedType === 'R'){
	                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                    extraAddr += data.bname;
	                }
	                // 건물명이 있고, 공동주택일 경우 추가한다.
	                if(data.buildingName !== '' && data.apartment === 'Y'){
	                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                }
	                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                if(extraAddr !== ''){
	                    extraAddr = ' (' + extraAddr + ')';
	                }
	                // 조합된 참고항목을 해당 필드에 넣는다.
	                document.getElementById("extraAddress").value = extraAddr;
	            
	            } else {
	                document.getElementById("extraAddress").value = '';
	            }

	            // 우편번호와 주소 정보를 해당 필드에 넣는다.
	            document.getElementById('postcode').value = data.zonecode;
	            document.getElementById("address").value = addr;
	            $("input:hidden[id='postcode_v']").val(1);
	            $("input:hidden[id='address_v']").val(1);
	            // 커서를 상세주소 필드로 이동한다.
	            document.getElementById("detailAddress").focus();
	            
	        },
	        onclose: function(state) {
	            // 다음 우편번호 서비스 창이 닫혔을 때 호출되는 콜백
	            if (state === 'FORCE_CLOSE') {
	                // 사용자가 창을 강제로 닫았을 경우, 여기에 원하는 동작을 추가할 수 있습니다.
	                // 예를 들어, 다른 곳으로 포커스를 이동하거나 다른 동작을 수행할 수 있습니다.
	                document.getElementById("bank_name").focus();
	            }
	        }
	    }).open();
		
	    // 주소를 읽기전용(readonly) 로 만들기
	    $("input#address").attr("readonly", true);
	    
	    // 참고항목을 읽기전용(readonly) 로 만들기
	    $("input#extraAddress").attr("readonly", true);
	});
	
	
	// 주소 변경시 상세주소 비우기
	$("input:text[id='detailAddress']").on("focus", function(e){
		
		$(e.target).val("");
		
	});
	
	// 상세주소 유효성검사
	$("input:text[id='detailAddress']").on("focus", function(e){
		
		if($(e.target).val("")){
			$(e.target).addClass("error");
			$("input:hidden[id='detailaddress_v']").val(0);
		}
		else{
			$(e.target).removeClass("error");
			$("input:hidden[id='detailaddress_v']").val(1);
		}
		
	});
			
	
	// 계좌번호 유효성검사
	$("input:text[name='bank_code']").on("focusout", function(e) {
		
		const regExp_phone = new RegExp(/^\d+$/);
		
		const bool = regExp_phone.test($(e.target).val());		
		
		if(!bool) {
			$(e.target).addClass("error");
			$("input:hidden[id='bank_code']").val(0);
		}
		else {
			$(e.target).removeClass("error");
			$("input:hidden[id='bank_code']").val(1);
		}
		
	});
	
	
	// 취소버튼 클릭시 이전페이지 이동
	$("button#btnCancel").click(function() {
		const cancel = confirm("프로필 수정을 취소하시겠습니까?")
		if(cancel) {
			window.history.back();
		}
	});
	
    
	
}); // end of $(document).ready(function(){})----------------


function btnUpdate() {
	
	let b_requiredInfo = false;
	
	const requiredInfo_list = document.querySelectorAll(".requiredInfo");
	console.log(requiredInfo_list);
    for(let i=0; i<requiredInfo_list.length; i++){
		const val = requiredInfo_list[i].value.trim();
		if(val == "") {
			alert("모든 정보를 입력해 주세요.");
		    b_requiredInfo = true;
		    break;
		}
	}// end of for-----------------------------
	
	
	if($("input:hidden[id='phone']").val() != 1) {
		alert("핸드폰 번호가 올바르지 않습니다.");
	    b_requiredInfo = true;
	}
	
	
	if($("input:hidden[id='postcode_v']").val() != 1) {
		alert("우편번호를 입력해주세요.");
	    b_requiredInfo = true;
	}
	
	if($("input:hidden[id='address_v']").val() != 1) {
		alert("주소를 입력해 주세요.");
	    b_requiredInfo = true;
	}
	
	
	if($("input:hidden[id='detailaddress_v']").val() != 1) {
		alert("상세주소를 입력해 주세요.");
	    b_requiredInfo = true;
	}
	
	if($("input:hidden[id='bank_code']").val() != 1) {
		alert("계좌번호가 올바르지 않습니다.");
	    b_requiredInfo = true;
	}
	
	
	if(b_requiredInfo) {
		return; // goRegister() 함수를 종료한다.
	}
	
	const frm = document.myinfoEditFrm;
	frm.method = "post";
	frm.action = "<%= ctxPath%>/myinfoEditEnd.gw";
	frm.submit();
	
}
</script>


<div style="display: flex; justify-content: center; margin-bottom: 50px;">
    <div style="width: 80%; min-height: 900px; margin:auto;">
   		<div id="name">
         	  <h2>인사관리</h2>
   		</div>
   	  <form name="searchFrm" style="right: 10%;">

		<c:if test="${not empty requestScope.deptNameList}">
		    <select name="deptName" id="deptName" style="height: 30px; width: 120px;">
			    <option value="">부서선택</option>
			    <c:forEach var="dept" items="${requestScope.deptNameList}" varStatus="status">
                <option value="${dept.department_id}" ${param.deptName == dept.department_id ? 'selected' : ''}>${dept.department_name}</option>
			    </c:forEach>
			</select>
			</c:if>

			 <select name="gradeLV" id="gradeLV" style="height: 30px; width: 120px;">
            	<option value="">직급선택</option>
	   			    <option value="사장" <c:if test="${param.gradeLV eq '사장'}">selected</c:if>>사장</option>
	   			    <option value="부서장" <c:if test="${param.gradeLV eq '부서장'}">selected</c:if> >부서장</option>
	   			    <option value="팀장" <c:if test="${param.gradeLV eq '팀장'}">selected</c:if>>팀장</option>
	   			    <option value="사원" <c:if test="${param.gradeLV eq '사원'}">selected</c:if>>사원</option>
		    </select>
			<select name="gender" id="gender" style="height: 30px; width: 120px;">
			   <option value="">성별선택</option>
			   <option value="남자" <c:if test="${param.gender eq '남자'}">selected</c:if>>남자</option>
			   <option value="여자" <c:if test="${param.gender eq '여자'}">selected</c:if>>여자</option>
			</select>
	      <button type="button" class="btn btn-info btn-sm" id="btnSearch">검색하기</button>
	      <button type="reset" class="btn btn-danger btn-sm" id="btnReset">초기화</button>

      </form>
      
      <br> 	
      <table id="emptbl">
         <thead>
            <tr>
               
               <th style = "width: 10%;">부서명</th>
               <th style = "width: 10%;">직급</th>
               <th style = "width: 10%;">사원명</th>
               <th style = "width: 25%;">웹메일</th>
               <th style = "width: 10%;">입사일자</th>
               <th style = "width: 15%;">월급</th>
               <th style = "width: 10%;">성별</th>
               <th style = "width: 10%;" >나이</th>
            </tr>
         </thead>
         <tbody>
            <c:if test="${not empty requestScope.empList}">
               <c:forEach var="map" items="${requestScope.empList}">
                  <tr class="empinfo">
                     <td style="text-align: center;">
                     <input name="employee_id" type="hidden" value="${map.employee_id}" />
                     ${map.department_name}</td>
                     <td style="text-align: center;">${map.gradeLV}</td>
                     <td style="text-align: center;">${map.name}</td>
		             <td style="text-align: center;">${map.email}</td>  <%-- department_id 은 hr.xml 에서 정의해준 HashMap의 키이다.  --%>
                     
                     <td style="text-align: center;">${map.hire_date}</td>
                     <td style="text-align: center;"><fmt:formatNumber value="${map.Yearsal}" pattern="#,###"></fmt:formatNumber>원</td>
                     <td style="text-align: center;">${map.gender}</td>
                     <td style="text-align: center;">${map.age}</td>
                  </tr>
 
               </c:forEach>
               
            </c:if>
         </tbody>
      </table>
   
   
    <div align="right" style="border: solid 0px gray; width: 80%; margin: 30px auto;"> 
       <button type="button" class="btn btn-sm btn-secondary" onclick="<%= ctxPath%>/emp/payment.gw">월급 지급하기</button>
    </div>
    
    
    
    
    
<%-- ============ 모달 [시작] ============= --%>
<div class="modal fade" id="modal_member" role="dialog" data-backdrop="static">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
    
      <!-- Modal header -->
      <div class="modal-header">
        <h4 class="modal-title">개인 프로필</h4>
        <button type="button" class="close modal_close" data-dismiss="modal">&times;</button>
      </div>
      
      <!-- Modal body -->
    <div class="modal-body">
<div style="display: flex; justify-content: center;">
		
		<div id="photo">
			<img src="<%= ctxPath%>/resources/empImg/${requestScope.loginuser.photo}" />
		</div>
			
		<table id="table1" class="myinfo_tbl">
	 
			<tr>
				<th>성명</th>
				<td id="modal_name"></td>
			
				<th>사원번호</th>
				<td id="modal_employee_id"></td> 
			</tr>
	 
			<tr>
				<th>부서명</th>
				<td id="modal_department_name"></td>
				<th>팀명</th>
				<td id="modal_team_name"></td>
			</tr>
			
			<tr>
				<th>직급</th>
				<td id="modal_job_name"></td>
				<th>입사일자</th>
				<td id="modal_hire_date"></td>
			</tr>
			
		</table>
		
	</div>
	
	<table id="table2" class="myinfo_tbl">
	
		<tr>
			<th>이메일</th>
			<td id="modal_email"></td>
			<th>연락처</th>
			<td id="modal_phone"></td>
		</tr>

		<tr>
			<th>생년월일</th>
			<td id="modal_birthday"></td>
			<th>성별</th>
			<td id="modal_gender"></td>
		</tr>
		
		<tr>
			<th>우편번호</th>
			<td id="modal_post_code"></td>
			<th>주소 참고사항</th>
			<td id="modal_address"></td>
		</tr>
		
		<tr>
			<th>연봉</th>
			<td id="modal_salary"></td>
			<th>계좌번호</th>
			<td id="modal_bank_code"></td>
		</tr>
		
	</table>

	<table id="table3" class="myinfo_tbl">

		<tr>
			<th>연차</th>
			<th>가족돌봄</th>
			<th>군소집훈련</th>
			<th>난임치료</th>
			<th>배우자출산</th>
			<th>결혼</th>
			<th>포상</th>
		</tr>
		
		<tr>
			<td id="modal_annual"></td>
			<td id="modal_family_care"></td>
			<td id="modal_reserve_forces"></td>
			<td id="modal_infertility_treatment"></td>
			<td id="modal_childbirth"></td>
			<td id="modal_marriage"></td>
			<td id="modal_reward"></td>
		</tr>
		
	</table>

  <div id="button">
	</div>


	</div>
      
    </div>
  </div>
</div>
<%-- ============ 모달 [끝] ============= --%>






   </div>
</div>



