package com.spring.app.kimkm.controller;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spring.app.common.FileManager;
import com.spring.app.common.Sha256;
import com.spring.app.digitalmail.domain.EmailVO;
import com.spring.app.domain.EmployeesVO;
import com.spring.app.kimkm.service.KimkmService;

@Controller
public class KimkmController {

	@Autowired
	private KimkmService service;
	
	@Autowired
	private FileManager fileManager;
	
	// 회원가입 페이지 요청
	@GetMapping("/register.gw")
	public ModelAndView register(ModelAndView mav, HttpServletRequest request) {
		
		String email = request.getParameter("email");
		  
		Map<String, String> register = service.selectRegister(email);

		mav.addObject("register", register);
		mav.setViewName("register/register");
		
		return mav;
	}
	
	
	// 회원가입
	@PostMapping("/registerEnd.gw")
	public ModelAndView registerEnd(ModelAndView mav, EmployeesVO evo, HttpServletRequest request) {
		
		String pwd = Sha256.encrypt(evo.getPwd());
		evo.setPwd(pwd);

		int idx = evo.getEmail().indexOf("@");

		if (idx != -1) {
		    String userid = evo.getEmail().substring(0, idx);
		    evo.setUserid(userid);
		}
		
		evo.setPostcode("사원증기본이미지.png");
		
		int n = service.add_register(evo);
		
		String employee_id = request.getParameter("employee_id");
		
		int n2 = service.insert_vacation(employee_id);
		
		if(n*n2==1) {
			
			String message = "입사를 축하드립니다. 로그인 후 프로필 수정 부탁드립니다.";
			String loc = request.getContextPath()+"/login.gw";
			
			mav.addObject("message", message);
			mav.addObject("loc", loc);
			
			mav.setViewName("msg");
		}
		
		return mav;
	}

	
	// 프로필 페이지 요청
	@GetMapping("/myinfo.gw")
	public ModelAndView requiredLogin_myinfo(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		HttpSession session = request.getSession();
		EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
		
        if(loginuser != null) {
        	String employee_id = loginuser.getEmployee_id();
		
			Map<String, String> gender_birthday = service.selectGenderBirthday(employee_id);
			
			Map<String, String> dept_team = service.selectDeptTeam(employee_id);
			
			int idx = dept_team.get("JOB_NAME").indexOf("팀 ");
			
			if (idx != -1) {
			    String JOB_NAME = dept_team.get("JOB_NAME").substring(idx+1);
			    dept_team.put("JOB_NAME", JOB_NAME);
			}
			
		 //	System.out.println(dept_team);
			
			mav.addObject("dept_team", dept_team);
			mav.addObject("loginuser", loginuser);
			mav.addObject("gender_birthday", gender_birthday);
			mav.setViewName("myinfo/myinfo.tiles_MTS");
			
        }
        
        return mav;
	}
	
	
	// 수정페이지 요청
	@GetMapping("/myinfoEdit.gw")
	public ModelAndView requiredLogin_myinfoEdit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		HttpSession session = request.getSession();
		EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
		
		if(loginuser != null) {
			
			String employee_id = loginuser.getEmployee_id();
        	
        	Map<String, String> gender_birthday = service.selectGenderBirthday(employee_id);
        	
        	Map<String, String> dept_team = service.selectDeptTeam(employee_id);
        	
        	mav.addObject("loginuser", loginuser);
        	mav.addObject("gender_birthday", gender_birthday);
        	mav.addObject("dept_team", dept_team);
        	mav.setViewName("myinfo/myinfoEdit.tiles_MTS");
        }
		
		return mav;
	}
	
	
	// 프로필 수정하기 
	@PostMapping("/myinfoEditEnd.gw")
	public ModelAndView requiredLogin_myinfoEditEnd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, EmployeesVO evo, MultipartHttpServletRequest mrequest) {
		
		MultipartFile attach = evo.getAttach();
		
		String photo = request.getParameter("photo");
		
		if(attach != null) {
		   HttpSession session = mrequest.getSession();
		   String root = session.getServletContext().getRealPath("/");
		   // System.out.println("확인용 webapp 의 절대 경로 : "+ root);
		   // 확인용 webapp 의 절대 경로 : /Users/sub/workspace_spring_framework/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/board/
		  
		   String path = root + "resources" + File.separator + "empImg";
		   
		   // System.out.println("확인용 path : "+ path);
		   // 확인용 path : /Users/sub/workspace_spring_framework/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/board/resources/files
		   
		   String newFileName = "";
	         
			byte[] bytes = null;

			try {
				bytes = attach.getBytes();

				String originalFilename = attach.getOriginalFilename();
			 //	System.out.println("~~~ 확인용 originalFilename => " + originalFilename);
				// ~~~ 확인용 originalFilename => LG_싸이킹청소기_사용설명서.pdf

			 	newFileName = fileManager.doFileUpload(bytes, originalFilename, path);
			 //	System.out.println("~~~ 확인용 newFileName => " + newFileName);
				// ~~~ 확인용 newFileName => 20231124113719342939728234042.jpg
			 	
			 	if(newFileName == null) {
			 		evo.setPhoto(photo);
			 	}
			 	else {
			 		evo.setPhoto(newFileName);
			 	}
				

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(evo.getPhoto());
		
		int n = service.myinfoEditEnd(evo);
				
		if(n==1) {
			mav.addObject("message", "프로필 수정 성공!!");
			mav.addObject("loc", request.getContextPath()+"/myinfo.gw");
			mav.setViewName("msg");
		}
		
		return mav;
	}

	
	// 비밀번호 변경 페이지 요청
	@GetMapping("/pwdUpdate.gw")
	public ModelAndView requiredLogin_pwdUpdate(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		HttpSession session = request.getSession();
		EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
		
		mav.addObject("loginuser", loginuser);
		mav.setViewName("register/pwdUpdate");
		
		return mav;
	}
	
	
	//비밀번호 변경시 현재 비밀번호 암호화해 가져오기
	@ResponseBody
	@GetMapping(value="/pwdSha256.gw", produces="text/plain;charset=UTF-8")
	public String pwdSha256(HttpServletRequest request) {
		
		 String current_pwd = request.getParameter("current_pwd");
		 
		 String Sha_pwd = Sha256.encrypt(current_pwd);
		 
		 JSONArray jsonArr = new JSONArray(); // []
		   
		   if(Sha_pwd != null) {
			   JSONObject jsonObj = new JSONObject(); // {}
			   jsonObj.put("current_pwd", Sha_pwd);
			   
			   jsonArr.put(jsonObj);
		   }
		   
		   return jsonArr.toString();
		 
	}
	
	
	// 비밀번호 변경하기
	@PostMapping("/pwdUpdateEnd.gw")
	public ModelAndView requiredLogin_pwdUpdateEnd(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, EmployeesVO evo) {
		
		String email = request.getParameter("email");
		String new_pwd = request.getParameter("pwd");
		
		String pwd = Sha256.encrypt(new_pwd);
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("email", email);
		paraMap.put("pwd", pwd);
		
		evo.setPwd(pwd);
	 //	System.out.println(pwd);
		
		int n = service.pwdUpdateEnd(paraMap);
		
		if(n==1) {
			mav.addObject("message", "비밀번호 변경 성공!!");
			mav.addObject("loc", request.getContextPath()+"/myinfo.gw");
			mav.setViewName("msg");
		}
		
		return mav;
	}
	
	
	// 급여페이지 요청하기
	@GetMapping("/salary.gw")
	public ModelAndView requiredLogin_salary(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		HttpSession session = request.getSession();
		EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
		
        if(loginuser != null) {
        	
        	String employee_id = loginuser.getEmployee_id();
		
			List<Map<String, String>> monthSalList = service.monthSal(employee_id);
			
		 	System.out.println(monthSalList);
			
			mav.addObject("monthSalList", monthSalList);
			mav.setViewName("salary/monthsalaryfile.tiles_MTS");
        }
		return mav;
		
	}
	
	
	// 급여 명세서 요청하기
	@PostMapping("/salaryStatement.gw")
	public ModelAndView requiredLogin_salaryStatement(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		String year_month = request.getParameter("year_month");
		String fk_employee_id = request.getParameter("fk_employee_id");
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("year_month", year_month);
		paraMap.put("fk_employee_id", fk_employee_id);
		
		Map<String, String> salaryStatement = service.salaryStatement(paraMap);
		
		int idx = salaryStatement.get("JOB_NAME").indexOf("팀 ");
		
		if (idx != -1) {
		    String JOB_NAME = salaryStatement.get("JOB_NAME").substring(idx+1);
		    salaryStatement.put("JOB_NAME", JOB_NAME);
		}
		
	 	System.out.println(salaryStatement);
		
		mav.addObject("salaryStatement", salaryStatement);
		mav.setViewName("salary/salary.tiles_MTS");
		
		return mav;
		
	}
	
	
	// 웹에서 보여지는 결과물을 Excel 파일로 다운받기
	@PostMapping("/salary/downloadExcelFile.gw") 
	public String downloadExcelFile(@RequestParam(defaultValue = "") String str_year_month,
                                    Model model) {
		
    	System.out.println(str_year_month);
		
    	Map<String, Object> paraMap = new HashMap<>();
    	
    	if(!"".equals(str_year_month)) {
    		String[] arr_year_month = str_year_month.split("\\,");
    		paraMap.put("arr_year_month", arr_year_month);
    	}
    	
    	service.salary_to_Excel(paraMap, model);
		
		
		return "excelDownloadView";
         
	}
	
	
	// 조직도 페이지 요청하기
	@GetMapping("chart.gw")
	public String requiredLogin_empmanager_chart(HttpServletRequest request, HttpServletResponse response) {
		
		return "emp/orgchart.tiles_MTS";
	}
	
	// 사장 부서장 조직도 
	@ResponseBody
	@GetMapping(value="/chart/executive.gw", produces="text/plain;charset=UTF-8")
	public String executive() {
		
		List<Map<String, String>> employeeList =  service.employeeList();
		
		JsonArray jsonArr = new JsonArray(); // []
		
		for(Map<String, String> map : employeeList) {
			
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("name", map.get("name"));
			jsonObj.addProperty("job_name", map.get("job_name"));
			jsonObj.addProperty("photo", map.get("photo"));
			jsonObj.addProperty("employee_id", map.get("employee_id"));
			jsonObj.addProperty("manager_id", map.get("manager_id"));
			jsonObj.addProperty("t_manager_id", map.get("t_manager_id"));
			
			jsonArr.add(jsonObj);
		}// end of for
		
		return new Gson().toJson(jsonArr);
	}
	
	
	// 인사팀 조직도
	@ResponseBody
	@GetMapping(value="/chart/human_resources.gw", produces="text/plain;charset=UTF-8")
	public String human_resources() {
		
		List<Map<String, String>> employeeList =  service.employeeList();
		
		JsonArray jsonArr = new JsonArray(); // []
		
		for(Map<String, String> map : employeeList) {
			
			if(map.get("job_name").substring(0, 2).equals("인사")) {
				JsonObject jsonObj = new JsonObject();
				
				jsonObj.addProperty("name", map.get("name"));
				jsonObj.addProperty("job_name", map.get("job_name"));
				jsonObj.addProperty("photo", map.get("photo"));
				jsonObj.addProperty("employee_id", map.get("employee_id"));
				jsonObj.addProperty("manager_id", map.get("manager_id"));
				jsonObj.addProperty("t_manager_id", map.get("t_manager_id"));
				
				jsonArr.add(jsonObj);
			}
			
		}// end of for
		
		return new Gson().toJson(jsonArr);
	}
	
	
	// 개발팀 조직도
	@ResponseBody
	@GetMapping(value="/chart/development.gw", produces="text/plain;charset=UTF-8")
	public String development() {
		
		List<Map<String, String>> employeeList =  service.employeeList();
		
		JsonArray jsonArr = new JsonArray(); // []
		
		for(Map<String, String> map : employeeList) {
			
			if(map.get("job_name").substring(0, 2).equals("개발")) {
				JsonObject jsonObj = new JsonObject();
				
				jsonObj.addProperty("name", map.get("name"));
				jsonObj.addProperty("job_name", map.get("job_name"));
				jsonObj.addProperty("photo", map.get("photo"));
				jsonObj.addProperty("employee_id", map.get("employee_id"));
				jsonObj.addProperty("manager_id", map.get("manager_id"));
				jsonObj.addProperty("t_manager_id", map.get("t_manager_id"));
				
				jsonArr.add(jsonObj);
			}
			
		}// end of for
		
		return new Gson().toJson(jsonArr);
	}
	
	
	// 마케팅팀 조직도
	@ResponseBody
	@GetMapping(value="/chart/marketing.gw", produces="text/plain;charset=UTF-8")
	public String marketing() {
		
		List<Map<String, String>> employeeList =  service.employeeList();
		
		JsonArray jsonArr = new JsonArray(); // []
		
		for(Map<String, String> map : employeeList) {
			
			System.out.println(map.get("job_name"));
			
			if(map.get("job_name").substring(0, 2).equals("마케")) {
				JsonObject jsonObj = new JsonObject();
				
				jsonObj.addProperty("name", map.get("name"));
				jsonObj.addProperty("job_name", map.get("job_name"));
				jsonObj.addProperty("photo", map.get("photo"));
				jsonObj.addProperty("employee_id", map.get("employee_id"));
				jsonObj.addProperty("manager_id", map.get("manager_id"));
				jsonObj.addProperty("t_manager_id", map.get("t_manager_id"));
				
				jsonArr.add(jsonObj);
			}
			
		}// end of for
		
		return new Gson().toJson(jsonArr);
	}
		
		
	// 영업팀 조직도
	@ResponseBody
	@GetMapping(value="/chart/sales.gw", produces="text/plain;charset=UTF-8")
	public String sales() {
		
		List<Map<String, String>> employeeList =  service.employeeList();
		
		JsonArray jsonArr = new JsonArray(); // []
		
		for(Map<String, String> map : employeeList) {
			
			if(map.get("job_name").substring(0, 2).equals("영업")) {
				JsonObject jsonObj = new JsonObject();
				
				jsonObj.addProperty("name", map.get("name"));
				jsonObj.addProperty("job_name", map.get("job_name"));
				jsonObj.addProperty("photo", map.get("photo"));
				jsonObj.addProperty("employee_id", map.get("employee_id"));
				jsonObj.addProperty("manager_id", map.get("manager_id"));
				jsonObj.addProperty("t_manager_id", map.get("t_manager_id"));
				
				jsonArr.add(jsonObj);
			}
			
		}// end of for
		
	//	System.out.println(jsonArr);
		
		return new Gson().toJson(jsonArr);
	}
		
		
		
	// 시설관리팀 조직도
	@ResponseBody
	@GetMapping(value="/chart/facility_management.gw", produces="text/plain;charset=UTF-8")
	public String facility_management() {
		
		List<Map<String, String>> employeeList =  service.employeeList();
		
		JsonArray jsonArr = new JsonArray(); // []
		
		for(Map<String, String> map : employeeList) {
			
			if(map.get("job_name").substring(0, 2).equals("시설")) {
				JsonObject jsonObj = new JsonObject();
				
				jsonObj.addProperty("name", map.get("name"));
				jsonObj.addProperty("job_name", map.get("job_name"));
				jsonObj.addProperty("photo", map.get("photo"));
				jsonObj.addProperty("employee_id", map.get("employee_id"));
				jsonObj.addProperty("manager_id", map.get("manager_id"));
				jsonObj.addProperty("t_manager_id", map.get("t_manager_id"));
				
				jsonArr.add(jsonObj);
			}
			
		}// end of for
		
		return new Gson().toJson(jsonArr);
	}
		
		
		
	// 미발령 조직도
	@ResponseBody
	@GetMapping(value="/chart/no_department.gw", produces="text/plain;charset=UTF-8")
	public String no_department() {
		
		List<Map<String, String>> employeeList =  service.employeeList();
		
		JsonArray jsonArr = new JsonArray(); // []
		
		for(Map<String, String> map : employeeList) {
			
			if(map.get("job_name").substring(0, 2).equals("발령")) {
				JsonObject jsonObj = new JsonObject();
				
				jsonObj.addProperty("name", map.get("name"));
				jsonObj.addProperty("job_name", map.get("job_name"));
				jsonObj.addProperty("photo", map.get("photo"));
				jsonObj.addProperty("employee_id", map.get("employee_id"));
				jsonObj.addProperty("manager_id", map.get("manager_id"));
				jsonObj.addProperty("t_manager_id", map.get("t_manager_id"));
				
				jsonArr.add(jsonObj);
			}
			
		}// end of for
		
		return new Gson().toJson(jsonArr);
	}
	

	// receipt_favorites update 하기
	@ResponseBody
	@PostMapping(value="/receipt_favorites_update.gw", produces="text/plain;charset=UTF-8")
	public String receipt_favorites_update(HttpServletRequest request) {
		
		String receipt_mail_seq = request.getParameter("receipt_mail_seq");
		
		String receipt_favorites = service.select_receipt_favorites(receipt_mail_seq);
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("receipt_mail_seq", receipt_mail_seq);
		paraMap.put("receipt_favorites", receipt_favorites);
		//System.out.println(paraMap);
		
		int n = service.receipt_favorites_update(paraMap);
		
		String receipt_favorites_update = service.select_receipt_favorites(receipt_mail_seq);
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("receipt_favorites", receipt_favorites_update);
		
		return jsonObj.toString();
	}
	
	
	// receipt_favorites update 하기
	@ResponseBody
	@PostMapping(value="/email_receipt_read_count_update.gw", produces="text/plain;charset=UTF-8")
	public String email_receipt_read_count_update(HttpServletRequest request) {
		
		String receipt_mail_seq = request.getParameter("receipt_mail_seq");
		
		int n = service.email_receipt_read_count_update(receipt_mail_seq);
		
		String email_receipt_read_count = service.select_email_receipt_read_count(receipt_mail_seq);
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("email_receipt_read_count", email_receipt_read_count);
		
		return jsonObj.toString();
	}
	
	
	// receipt_favorites update 하기
	@ResponseBody
	@PostMapping(value="/receipt_important_update.gw", produces="text/plain;charset=UTF-8")
	public String receipt_important_update(HttpServletRequest request) {
		
		String receipt_mail_seq = request.getParameter("receipt_mail_seq");
		
		String receipt_important = service.select_receipt_important(receipt_mail_seq);
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("receipt_mail_seq", receipt_mail_seq);
		paraMap.put("receipt_important", receipt_important);
		//System.out.println(paraMap);
		
		int n = service.receipt_important_update(paraMap);
		
		String receipt_important_update = service.select_receipt_important(receipt_mail_seq);
		
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("receipt_important", receipt_important_update);
		
		return jsonObj.toString();
	}
	
	
	
	
}
