package com.spring.app.kimkm.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.EmployeesVO;
import com.spring.app.kimkm.service.SalaryService;

@Controller
public class SalaryController {

	@Autowired
	private SalaryService service;
	
	// 급여페이지 요청하기
	@GetMapping("/salary.gw")
	public ModelAndView requiredLogin_salary(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		HttpSession session = request.getSession();
		EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
		
        if(loginuser != null) {
        	
        	String employee_id = loginuser.getEmployee_id();
		
			List<Map<String, String>> monthSalList = service.monthSal(employee_id);
			
		 //	System.out.println(monthSalList);
			
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
	 //	System.out.println(salaryStatement);
		
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
	
}
