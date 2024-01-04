<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
   String ctxPath = request.getContextPath();
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
    </style>

<script type="text/javascript">

$(document).ready(function(){

    $("button#btnSearch").click(function(){
        const frm = document.searchFrm;
        const deptName = $("#deptName").val();
        const gradeLV = $("#gradeLV").val();
        const gender = $("#gender").val();
        
        
        // Append selected values to the URL query parameters
        frm.action = "<%= ctxPath%>/emp/empList.gw?deptName=" + deptName + "&gradeLV=" + gradeLV + "&gender=" + gender;
        frm.submit();
    });
    
	  // 특정 회원을 클릭하면 회원의 상세정보를 보여주도록 한다.
   		$(document).on("click", "table#emptbl tr.empinfo", function (e) {
//         alert("클릭되었습니다!");
       	const employee_id = $(this).find("td:eq(0) > span").text();
		alert(employee_id);
   		const frm = document.empOneDetail_frm;
		frm.employee_id.value = employee_id;

		
		frm.action = "<%= ctxPath%>/emp/empOneDetail.gw";
		frm.method = "POST"
		frm.submit();
      
    });
	  

   	    // 초기화 버튼 클릭 시 폼 초기화
   	    $("button#btnReset").click(function() {
   	      const frm = document.searchFrm;
   	      $("#deptName").val(''); // 부서 선택 초기화
   	      $("#gender").val(''); // 성별 선택 초기화

   	      frm.action = "<%= ctxPath %>/emp/empList.gw"; // 폼 액션 초기화
   	      frm.submit();
   	    });
   


}); // end of $(document).ready(function(){})-----------------

</script>

<div style="display: flex; justify-content: center; margin-bottom: 50px;">
    <div style="width: 80%; min-height: 1100px; margin:auto;">
   		<div id="name">
         	  <h2>급여 관리</h2>
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
           		<c:if test="${map.employee_id != 10000}">
                  <tr class="empinfo">
                     <td style="text-align: center;"><span style="display:none;">${map.employee_id}</span>${map.department_name}</td>
                     <td style="text-align: center;">${map.gradeLV}</td>
                     <td style="text-align: center;">${map.name}</td>
		             <td style="text-align: center;">${map.email}</td>  <%-- department_id 은 hr.xml 에서 정의해준 HashMap의 키이다.  --%>
                     
                     <td style="text-align: center;">${map.hire_date}</td>
                     <td style="text-align: center;"><fmt:formatNumber value="${map.Yearsal}" pattern="#,###"></fmt:formatNumber>원</td>
                     <td style="text-align: center;">${map.gender}</td>
                     <td style="text-align: center;">${map.age}</td>
                  </tr>
                </c:if>
                <c:if test="${map.employee_id == 10000}">
                  <tr class="empinfo">
                     <td style="text-align: center;"><span style="display:none;">${map.employee_id}</span>${map.department_name}</td>
                     <td style="text-align: center;">${map.gradeLV}</td>
                     <td style="text-align: center;">${map.name}</td>
		             <td style="text-align: center;">${map.email}</td>  <%-- department_id 은 hr.xml 에서 정의해준 HashMap의 키이다.  --%>
                     
                     <td style="text-align: center;">${map.hire_date}</td>
                     <td style="text-align: center;">알수없음</td>
                     <td style="text-align: center;">${map.gender}</td>
                     <td style="text-align: center;">${map.age}</td>
                  </tr>
                </c:if>
               </c:forEach>
               
            </c:if>
         </tbody>
      </table>
   
    <div align="center" style="border: solid 0px gray; width: 80%; margin: 30px auto;"> 
        ${requestScope.pageBar}
    </div>
    <div align="right" style="border: solid 0px gray; width: 80%; margin: 30px auto;"> 
       <a href="<%= ctxPath%>/emp/payEnd.gw"><button type="button" class="btn btn-sm btn-secondary" >월급 지급하기</button></a>
    </div>
    
   
   </div>
</div>


