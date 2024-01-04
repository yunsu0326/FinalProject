<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>

    
<style type="text/css">
.highcharts-figure,
.highcharts-data-table table {
    min-width: 360px;
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

#container h4 {
    text-transform: none;
    font-size: 14px;
    font-weight: normal;
}

#container p {
    font-size: 13px;
    line-height: 16px;
}

@media screen and (max-width: 600px) {
    #container h4 {
        font-size: 2.3vw;
        line-height: 3vw;
    }

    #container p {
        font-size: 2.3vw;
        line-height: 3vw;
    }
}

</style>

<script src="<%= ctxPath%>/resources/Highcharts-10.3.3/code/highcharts.js"></script>
<script src="<%= ctxPath%>/resources/Highcharts-10.3.3/code/modules/sankey.js"></script>
<script src="<%= ctxPath%>/resources/Highcharts-10.3.3/code/modules/organization.js"></script>
<script src="<%= ctxPath%>/resources/Highcharts-10.3.3/code/modules/exporting.js"></script>
<script src="<%= ctxPath%>/resources/Highcharts-10.3.3/code/modules/accessibility.js"></script>

<figure class="highcharts-figure">
    
    <form name="searchFrm" style="margin: 20px 0 50px 0; ">
			<select name="searchType" id="searchType" style="height: 30px;">
				<option value="">부서를 선택하세요</option>
				<option value="executive">사장 및 부서장</option>
				<option value="human_resources">인사부</option>
				<option value="development">개발부</option>
				<option value="marketing">마케팅부</option>
				<option value="sales">영엉부</option>
				<option value="facility_management">시설관리부</option>
				<option value="no_department">미발령</option>
			</select>
		</form>
    
    <div id="container"></div>
    
</figure>



<script type="text/javascript">

$(document).ready(function(){
	
	$("select#searchType").change(function(e){
		func_select($(this).val());
		// ($(this).val()) 은 "" 또는 "deptname" 또는 "gender" 또는 "genderHireYear" 또는 "deptnameGender" 이다. select 태그의 value 값이다.
	});
	
	// 문서가 로드 되어지면 "부서별 인원통계" 페이지가 보이도록 한다.
	$("select#searchType").val("executive").trigger("change");
	
}); // end of $(document).ready(function(){})--------------------------

function func_select(searchTypeVal){
	
	switch (searchTypeVal) {
		case "":
			$("div#container").empty();
			$("div.highcharts-data-table").empty();
			break;
			
		case "executive":
			$.ajax({
				url:"<%= ctxPath%>/chart/executive.gw",
				dataType:"json",
				success:function(json){
				 //	console.log(JSON.stringify(json));
				/*
					[
					 {"name":"배민준","team_name":"관리자","department_name":"인사부","job_name":"부서장","gradelevel":"5","employee_id":"9998","manager_id":"9998","t_manager_id":"10000"}
					,{"name":"박보영","team_name":"영업1팀","department_name":"영업부","job_name":"팀장","gradelevel":"3","employee_id":"9910","manager_id":"9996","t_manager_id":"9910"}
					,{"name":"장원영","team_name":"마케팅1팀","department_name":"마케팅부","job_name":"팀장","gradelevel":"3","employee_id":"9907","manager_id":"9997","t_manager_id":"9907"}
					,{"name":"유해진","team_name":"시설관리1팀","department_name":"장비관리부","job_name":"팀장","gradelevel":"3","employee_id":"9913","manager_id":"9995","t_manager_id":"9913"}
					,{"name":"공석","team_name":"발령대기","department_name":"발령대기","job_name":"발령대기","gradelevel":"1","employee_id":"7777","manager_id":"9998","t_manager_id":"9995"}
					]
				*/
				
					$("div#container").empty();
					$("div.highcharts-data-table").empty();
					
				 	let CEOArr = [];
				 	let managerArr = [];
				 	let t_managerArr = [];
				 	let employeeArr = [];
					 	
					for(let i=0; i<json.length; i++) {
						const employee_id = json[i].employee_id;
						const t_manager_id = json[i].t_manager_id;
						const manager_id = json[i].manager_id;
						 	
						if(employee_id == t_manager_id && t_manager_id == manager_id) {
							CEOArr.push(json[i].job_name);
						}
						else if(employee_id == manager_id ) {
							managerArr.push(json[i].job_name);
						}
						else if(employee_id == t_manager_id ) {
							t_managerArr.push(json[i].job_name);
						}
						else {
							employeeArr.push(json[i].job_name);
						}
						
					}// end of for-------------------------------
					
					
					const ceoarr = [];
					for(let i=0; i<managerArr.length; i++){
						let obj = [
									CEOA= CEOArr[0],
									manager= managerArr[i]
								  ];
						ceoarr.push(obj);
					}// end of for----------------
					 	
					 	
					let resultArr = [];
					for(let i=0; i<json.length; i++) {
						let obj = {
									id: json[i].job_name,
									title: json[i].job_name,
									name: json[i].name,
									image: '<%= ctxPath%>/resources/images/'+json[i].photo
								  };
						resultArr.push(obj);
					}// end of for-------------------------------
					
				//	console.log(ceoarr);
				//	console.log(resultArr);
					 
					////////////////////////////////////////////////////////
					Highcharts.chart('container', {
						chart: {
							height: 600,
							inverted: true
						},
						title: {
							text: 'MTS 조직도'
						},
						accessibility: {
							point: {
								descriptionFormatter: function (point) {
									var nodeName = point.toNode.name,
									nodeId = point.toNode.id,
									nodeDesc = nodeName === nodeId ? nodeName : nodeName + ', ' + nodeId,
									parentDesc = point.fromNode.id;
									return point.index + '. ' + nodeDesc + ', reports to ' + parentDesc + '.';
								}
							}
						},
						series: [{
							type: 'organization',
							name: 'MTS',
							keys: ['from', 'to'],
							data: ceoarr,
							levels: [{
								level: 1,
								color: '#980104'
							}],
							nodes: resultArr,
							colorByPoint: false,
							color: '#007ad0',
							dataLabels: {
								color: 'white'
							},
							borderColor: 'white',
							nodeWidth: 65
						}],
						tooltip: {
							outside: true
						},
						exporting: {
							allowHTML: true,
							sourceWidth: 800,
							sourceHeight: 600
						}
					});
					////////////////////////////////////////////////////////
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
			break;
			
		case "human_resources":
			$.ajax({
				url:"<%= ctxPath%>/chart/human_resources.gw",
				dataType:"json",
				success:function(json){
				
					$("div#container").empty();
					$("div.highcharts-data-table").empty();
					
				 	let CEOArr = [];
				 	let managerArr = [];
				 	let t_managerArr = [];
				 	let employeeArr = [];
					 	
					for(let i=0; i<json.length; i++) {
						const employee_id = json[i].employee_id;
						const t_manager_id = json[i].t_manager_id;
						const manager_id = json[i].manager_id;
						 	
						if(employee_id == t_manager_id && t_manager_id == manager_id) {
							CEOArr.push(json[i].job_name);
						}
						else if(employee_id == manager_id ) {
							managerArr.push(json[i].job_name);
						}
						else if(employee_id == t_manager_id ) {
							t_managerArr.push(json[i].job_name);
						}
						else {
							employeeArr.push(json[i].job_name);
						}
					}// end of for-------------------
					
			 		const ceoarr = [];
				 	for(let i = 0; i < managerArr.length; i++) {
						let deptname = managerArr[i].substring(0, 2);
						
						for(let j = 0; j < t_managerArr.length; j++) {
					 	const tname = t_managerArr[j].substring(0, 2);
						
							if(tname === deptname) {
								let obj = [
											marArr= managerArr[i],
											t_marr= t_managerArr[j]
										  ];
								ceoarr.push(obj);
							}
						}// end of for----------------
					}// end of for----------------
				 
					for(let i = 0; i < t_managerArr.length; i++) {
						const index = t_managerArr[i].indexOf(' ');
						const tname = t_managerArr[i].substring(0, index + 1);
						  
						for(let j = 0; j < employeeArr.length; j++) {
							const index = employeeArr[j].indexOf(' ');
							const ename = employeeArr[j].substring(0, index + 1);
							
							if(ename === tname) {
								let obj = [
											marArr= t_managerArr[i],
											t_marr= employeeArr[j]
										  ];
								ceoarr.push(obj);
							}
						}// end of for----------------
					} // end of for----------------
				 //	console.log(ceoarr);
					 	
					let resultArr = [];
					for(let i=0; i<json.length; i++) {
						let obj = {
									id: json[i].job_name,
									title: json[i].job_name,
									name: json[i].name,
									image: '<%= ctxPath%>/resources/images/'+json[i].photo
								  };
						resultArr.push(obj); 
					}// end of for-------------------------------
					
					////////////////////////////////////////////////////////
					Highcharts.chart('container', {
						chart: {
							height: 600,
							inverted: true
						},
						title: {
							text: 'MTS 조직도'
						},
						accessibility: {
							point: {
								descriptionFormatter: function (point) {
									var nodeName = point.toNode.name,
									nodeId = point.toNode.id,
									nodeDesc = nodeName === nodeId ? nodeName : nodeName + ', ' + nodeId,
									parentDesc = point.fromNode.id;
									return point.index + '. ' + nodeDesc + ', reports to ' + parentDesc + '.';
								}
							}
						},
						series: [{
							type: 'organization',
							name: 'MTS',
							keys: ['from', 'to'],
							data: ceoarr,
							levels: [{
					            level: 0,
					            color: '#980104'
					        }, {
					            level: 1,
					            color: '#359154'
					        }, {
					            level: 2,
					            color: '#359154'
					        }],
							nodes: resultArr,
							colorByPoint: false,
							color: '#007ad0',
							dataLabels: {
								color: 'white'
							},
							borderColor: 'white',
							nodeWidth: 65
						}],
						tooltip: {
							outside: true
						},
						exporting: {
							allowHTML: true,
							sourceWidth: 800,
							sourceHeight: 600
						}
					});
					////////////////////////////////////////////////////////
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
			break;
			
			
		case "development":
			$.ajax({
				url:"<%= ctxPath%>/chart/development.gw",
				dataType:"json",
				success:function(json){
					
					$("div#container").empty();
					$("div.highcharts-data-table").empty();
					
				 	let CEOArr = [];
				 	let managerArr = [];
				 	let t_managerArr = [];
				 	let employeeArr = [];
					 	
					for(let i=0; i<json.length; i++) {
						const employee_id = json[i].employee_id;
						const t_manager_id = json[i].t_manager_id;
						const manager_id = json[i].manager_id;
						 	
						if(employee_id == t_manager_id && t_manager_id == manager_id) {
							CEOArr.push(json[i].job_name);
						}
						else if(employee_id == manager_id ) {
							managerArr.push(json[i].job_name);
						}
						else if(employee_id == t_manager_id ) {
							t_managerArr.push(json[i].job_name);
						}
						else {
							employeeArr.push(json[i].job_name);
						}
					 		
					}// end of for----------------
					 	
			 		const ceoarr = [];
				 	for(let i = 0; i < managerArr.length; i++) {
						let deptname = managerArr[i].substring(0, 2);
						
						for(let j = 0; j < t_managerArr.length; j++) {
					 	const tname = t_managerArr[j].substring(0, 2);
						
							if(tname === deptname) {
								let obj = [
											marArr= managerArr[i],
											t_marr= t_managerArr[j]
										  ];
								ceoarr.push(obj);
							}
						}// end of for----------------
					} // end of for----------------
				 
					for(let i = 0; i < t_managerArr.length; i++) {
						const index = t_managerArr[i].indexOf(' ');
						const tname = t_managerArr[i].substring(0, index + 1);
						  
						for(let j = 0; j < employeeArr.length; j++) {
							const index = employeeArr[j].indexOf(' ');
							const ename = employeeArr[j].substring(0, index + 1);

							if(ename === tname) {
								let obj = [
											marArr= t_managerArr[i],
											t_marr= employeeArr[j]
										  ];
								ceoarr.push(obj);
							}
						}// end of for----------------
					} // end of for----------------
					 	
					let resultArr = [];
					for(let i=0; i<json.length; i++) {
						let obj = {
									id: json[i].job_name,
									title: json[i].job_name,
									name: json[i].name,
									image: '<%= ctxPath%>/resources/images/'+json[i].photo
								  };
						resultArr.push(obj); 
					}// end of for-------------------------------
					
					////////////////////////////////////////////////////////
					Highcharts.chart('container', {
						chart: {
							height: 600,
							inverted: true
						},
						title: {
							text: 'MTS 조직도'
						},
						accessibility: {
							point: {
								descriptionFormatter: function (point) {
									var nodeName = point.toNode.name,
									nodeId = point.toNode.id,
									nodeDesc = nodeName === nodeId ? nodeName : nodeName + ', ' + nodeId,
									parentDesc = point.fromNode.id;
									return point.index + '. ' + nodeDesc + ', reports to ' + parentDesc + '.';
								}
							}
						},
						series: [{
							type: 'organization',
							name: 'MTS',
							keys: ['from', 'to'],
							data: ceoarr,
							levels: [{
					            level: 0,
					            color: '#980104'
					        }, {
					            level: 1,
					            color: '#359154'
					        }, {
					            level: 2,
					            color: '#359154'
					        }],
							nodes: resultArr,
							colorByPoint: false,
							color: '#007ad0',
							dataLabels: {
								color: 'white'
							},
							borderColor: 'white',
							nodeWidth: 65
						}],
						tooltip: {
							outside: true
						},
						exporting: {
							allowHTML: true,
							sourceWidth: 800,
							sourceHeight: 600
						}
					});
					////////////////////////////////////////////////////////
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
			break;
			
		case "marketing":
			$.ajax({
				url:"<%= ctxPath%>/chart/marketing.gw",
				dataType:"json",
				success:function(json){
					
					$("div#container").empty();
					$("div.highcharts-data-table").empty();
					
				 	let CEOArr = [];
				 	let managerArr = [];
				 	let t_managerArr = [];
				 	let employeeArr = [];
					 	
					for(let i=0; i<json.length; i++) {
						const employee_id = json[i].employee_id;
						const t_manager_id = json[i].t_manager_id;
						const manager_id = json[i].manager_id;
						 	
						if(employee_id == t_manager_id && t_manager_id == manager_id) {
							CEOArr.push(json[i].job_name);
						}
						else if(employee_id == manager_id ) {
							managerArr.push(json[i].job_name);
						}
						else if(employee_id == t_manager_id ) {
							t_managerArr.push(json[i].job_name);
						}
						else {
							employeeArr.push(json[i].job_name);
						}
					 		
					}// end of for----------------
					 	
			 		const ceoarr = [];
				 	for(let i = 0; i < managerArr.length; i++) {
						let deptname = managerArr[i].substring(0, 2);
						
						for(let j = 0; j < t_managerArr.length; j++) {
					 	const tname = t_managerArr[j].substring(0, 2);
						
							if(tname === deptname) {
								let obj = [
											marArr= managerArr[i],
											t_marr= t_managerArr[j]
										  ];
								ceoarr.push(obj);
							}
						}// end of for----------------
					} // end of for----------------
				 
					for(let i = 0; i < t_managerArr.length; i++) {
						const index = t_managerArr[i].indexOf(' ');
						const tname = t_managerArr[i].substring(0, index + 1);
						  
						for(let j = 0; j < employeeArr.length; j++) {
							const index = employeeArr[j].indexOf(' ');
							const ename = employeeArr[j].substring(0, index + 1);

							if(ename === tname) {
								let obj = [
											marArr= t_managerArr[i],
											t_marr= employeeArr[j]
										  ];
								ceoarr.push(obj);
							}
						}// end of for----------------
					} // end of for----------------
					 	
					let resultArr = [];
					for(let i=0; i<json.length; i++) {
						let obj = {
									id: json[i].job_name,
									title: json[i].job_name,
									name: json[i].name,
									image: '<%= ctxPath%>/resources/images/'+json[i].photo
								  };
						resultArr.push(obj); 
					}// end of for-------------------------------
					
					////////////////////////////////////////////////////////
					Highcharts.chart('container', {
						chart: {
							height: 600,
							inverted: true
						},
						title: {
							text: 'MTS 조직도'
						},
						accessibility: {
							point: {
								descriptionFormatter: function (point) {
									var nodeName = point.toNode.name,
									nodeId = point.toNode.id,
									nodeDesc = nodeName === nodeId ? nodeName : nodeName + ', ' + nodeId,
									parentDesc = point.fromNode.id;
									return point.index + '. ' + nodeDesc + ', reports to ' + parentDesc + '.';
								}
							}
						},
						series: [{
							type: 'organization',
							name: 'MTS',
							keys: ['from', 'to'],
							data: ceoarr,
							levels: [{
					            level: 0,
					            color: '#980104'
					        }, {
					            level: 1,
					            color: '#359154'
					        }, {
					            level: 2,
					            color: '#359154'
					        }],
							nodes: resultArr,
							colorByPoint: false,
							color: '#007ad0',
							dataLabels: {
								color: 'white'
							},
							borderColor: 'white',
							nodeWidth: 65
						}],
						tooltip: {
							outside: true
						},
						exporting: {
							allowHTML: true,
							sourceWidth: 800,
							sourceHeight: 600
						}
					});
					////////////////////////////////////////////////////////
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
			break;
			
		case "sales":
			$.ajax({
				url:"<%= ctxPath%>/chart/sales.gw",
				dataType:"json",
				success:function(json){
					
					$("div#container").empty();
					$("div.highcharts-data-table").empty();
					
				 	let CEOArr = [];
				 	let managerArr = [];
				 	let t_managerArr = [];
				 	let employeeArr = [];
					 	
					for(let i=0; i<json.length; i++) {
						const employee_id = json[i].employee_id;
						const t_manager_id = json[i].t_manager_id;
						const manager_id = json[i].manager_id;
						 	
						if(employee_id == t_manager_id && t_manager_id == manager_id) {
							CEOArr.push(json[i].job_name);
						}
						else if(employee_id == manager_id ) {
							managerArr.push(json[i].job_name);
						}
						else if(employee_id == t_manager_id ) {
							t_managerArr.push(json[i].job_name);
						}
						else {
							employeeArr.push(json[i].job_name);
						}
					 		
					}// end of for----------------
					 	
			 		const ceoarr = [];
				 	for(let i = 0; i < managerArr.length; i++) {
						let deptname = managerArr[i].substring(0, 2);
						
						for(let j = 0; j < t_managerArr.length; j++) {
					 	const tname = t_managerArr[j].substring(0, 2);
						
							if(tname === deptname) {
								let obj = [
											marArr= managerArr[i],
											t_marr= t_managerArr[j]
										  ];
								ceoarr.push(obj);
							}
						}// end of for----------------
					} // end of for----------------
				 
					for(let i = 0; i < t_managerArr.length; i++) {
						const index = t_managerArr[i].indexOf(' ');
						const tname = t_managerArr[i].substring(0, index + 1);
						  
						for(let j = 0; j < employeeArr.length; j++) {
							const index = employeeArr[j].indexOf(' ');
							const ename = employeeArr[j].substring(0, index + 1);

							if(ename === tname) {
								let obj = [
											marArr= t_managerArr[i],
											t_marr= employeeArr[j]
										  ];
								ceoarr.push(obj);
							}
						}// end of for----------------
					} // end of for----------------
					 	
					let resultArr = [];
					for(let i=0; i<json.length; i++) {
						let obj = {
									id: json[i].job_name,
									title: json[i].job_name,
									name: json[i].name,
									image: '<%= ctxPath%>/resources/images/'+json[i].photo
								  };
						resultArr.push(obj); 
					}// end of for-------------------------------
					
					////////////////////////////////////////////////////////
					Highcharts.chart('container', {
						chart: {
							height: 600,
							inverted: true
						},
						title: {
							text: 'MTS 조직도'
						},
						accessibility: {
							point: {
								descriptionFormatter: function (point) {
									var nodeName = point.toNode.name,
									nodeId = point.toNode.id,
									nodeDesc = nodeName === nodeId ? nodeName : nodeName + ', ' + nodeId,
									parentDesc = point.fromNode.id;
									return point.index + '. ' + nodeDesc + ', reports to ' + parentDesc + '.';
								}
							}
						},
						series: [{
							type: 'organization',
							name: 'MTS',
							keys: ['from', 'to'],
							data: ceoarr,
							levels: [{
					            level: 0,
					            color: '#980104'
					        }, {
					            level: 1,
					            color: '#359154'
					        }, {
					            level: 2,
					            color: '#359154'
					        }],
							nodes: resultArr,
							colorByPoint: false,
							color: '#007ad0',
							dataLabels: {
								color: 'white'
							},
							borderColor: 'white',
							nodeWidth: 65
						}],
						tooltip: {
							outside: true
						},
						exporting: {
							allowHTML: true,
							sourceWidth: 800,
							sourceHeight: 600
						}
					});
					////////////////////////////////////////////////////////
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
			break;
			
		case "facility_management":
			$.ajax({
				url:"<%= ctxPath%>/chart/facility_management.gw",
				dataType:"json",
				success:function(json){
					
					$("div#container").empty();
					$("div.highcharts-data-table").empty();
					
				 	let CEOArr = [];
				 	let managerArr = [];
				 	let t_managerArr = [];
				 	let employeeArr = [];
					 	
					for(let i=0; i<json.length; i++) {
						const employee_id = json[i].employee_id;
						const t_manager_id = json[i].t_manager_id;
						const manager_id = json[i].manager_id;
						 	
						if(employee_id == t_manager_id && t_manager_id == manager_id) {
							CEOArr.push(json[i].job_name);
						}
						else if(employee_id == manager_id ) {
							managerArr.push(json[i].job_name);
						}
						else if(employee_id == t_manager_id ) {
							t_managerArr.push(json[i].job_name);
						}
						else {
							employeeArr.push(json[i].job_name);
						}
					 		
					}// end of for----------------
					 	
			 		const ceoarr = [];
				 	for(let i = 0; i < managerArr.length; i++) {
						let deptname = managerArr[i].substring(0, 2);
						
						for(let j = 0; j < t_managerArr.length; j++) {
					 	const tname = t_managerArr[j].substring(0, 2);
						
							if(tname === deptname) {
								let obj = [
											marArr= managerArr[i],
											t_marr= t_managerArr[j]
										  ];
								ceoarr.push(obj);
							}
						}// end of for----------------
					} // end of for----------------
				 
					for(let i = 0; i < t_managerArr.length; i++) {
						const index = t_managerArr[i].indexOf(' ');
						const tname = t_managerArr[i].substring(0, index + 1);
						  
						for(let j = 0; j < employeeArr.length; j++) {
							const index = employeeArr[j].indexOf(' ');
							const ename = employeeArr[j].substring(0, index + 1);

							if(ename === tname) {
								let obj = [
											marArr= t_managerArr[i],
											t_marr= employeeArr[j]
										  ];
								ceoarr.push(obj);
							}
						}// end of for----------------
					} // end of for----------------
					 	
					let resultArr = [];
					for(let i=0; i<json.length; i++) {
						let obj = {
									id: json[i].job_name,
									title: json[i].job_name,
									name: json[i].name,
									image: '<%= ctxPath%>/resources/images/'+json[i].photo
								  };
						resultArr.push(obj); 
					}// end of for-------------------------------
					
					////////////////////////////////////////////////////////
					Highcharts.chart('container', {
						chart: {
							height: 600,
							inverted: true
						},
						title: {
							text: 'MTS 조직도'
						},
						accessibility: {
							point: {
								descriptionFormatter: function (point) {
									var nodeName = point.toNode.name,
									nodeId = point.toNode.id,
									nodeDesc = nodeName === nodeId ? nodeName : nodeName + ', ' + nodeId,
									parentDesc = point.fromNode.id;
									return point.index + '. ' + nodeDesc + ', reports to ' + parentDesc + '.';
								}
							}
						},
						series: [{
							type: 'organization',
							name: 'MTS',
							keys: ['from', 'to'],
							data: ceoarr,
							levels: [{
					            level: 0,
					            color: '#980104'
					        }, {
					            level: 1,
					            color: '#359154'
					        }, {
					            level: 2,
					            color: '#359154'
					        }],
							nodes: resultArr,
							colorByPoint: false,
							color: '#007ad0',
							dataLabels: {
								color: 'white'
							},
							borderColor: 'white',
							nodeWidth: 65
						}],
						tooltip: {
							outside: true
						},
						exporting: {
							allowHTML: true,
							sourceWidth: 800,
							sourceHeight: 600
						}
					});
					////////////////////////////////////////////////////////
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
			break;
			
		case "no_department":
			$.ajax({
				url:"<%= ctxPath%>/chart/no_department.gw",
				dataType:"json",
				success:function(json){
					
					$("div#container").empty();
					$("div.highcharts-data-table").empty();
					
					const ceoarr = [];
					for(let i=0; i<json.length; i++) {
						
						let obj = [
							marArr= json[i].job_name
						  ];
						
						ceoarr.push(obj);
					 		
					}// end of for----------------
					 	
					 	
					let resultArr = [];
					for(let i=0; i<json.length; i++) {
						let obj = {
									id: json[i].job_name,
									title: json[i].job_name,
									name: json[i].name,
									image: '<%= ctxPath%>/resources/images/'+json[i].photo
								  };
						resultArr.push(obj); 
					}// end of for-------------------------------
					
					////////////////////////////////////////////////////////
					Highcharts.chart('container', {
						chart: {
							height: 600,
							inverted: true
						},
						title: {
							text: 'MTS 조직도'
						},
						accessibility: {
							point: {
								descriptionFormatter: function (point) {
									var nodeName = point.toNode.name,
									nodeId = point.toNode.id,
									nodeDesc = nodeName === nodeId ? nodeName : nodeName + ', ' + nodeId,
									parentDesc = point.fromNode.id;
									return point.index + '. ' + nodeDesc + ', reports to ' + parentDesc + '.';
								}
							}
						},
						series: [{
							type: 'organization',
							name: 'MTS',
							keys: ['from', 'to'],
							data: ceoarr,
							levels: [{
					            level: 0,
					            color: 'silver',
					            dataLabels: {
					                color: 'black'
					            },
					            height: 25
					        }],
							nodes: resultArr,
							colorByPoint: false,
							color: '#007ad0',
							dataLabels: {
								color: 'white'
							},
							borderColor: 'white',
							nodeWidth: 65
						}],
						tooltip: {
							outside: true
						},
						exporting: {
							allowHTML: true,
							sourceWidth: 800,
							sourceHeight: 600
						}
					});
					////////////////////////////////////////////////////////
				},
				error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				}
			});
			break;
	}
	
	
}// end of function func_select(searchTypeVal)-------------------


	
</script>







