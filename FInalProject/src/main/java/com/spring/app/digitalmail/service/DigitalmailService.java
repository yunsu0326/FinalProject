package com.spring.app.digitalmail.service;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

public interface DigitalmailService {
	
	// 내가 받은 총 메일 갯수
	int getTotalCount(Map<String, String> paraMap);
	// 전자메일 뷰단
	ModelAndView digitalmail(ModelAndView mav, Map<String, String> paraMap);

}
