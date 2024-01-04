package com.spring.app.yosub.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.Calendar_schedule_VO;

public interface YosubService {

	/////////////////////////////////////////////////////////
	
	// 시작페이지에서 메인 이미지를 보여주는 것
	// List<String> getImgfilenameList();
	ModelAndView index(ModelAndView mav, HttpServletRequest request);

	// 로그인 페이지 띄우기
	ModelAndView login(ModelAndView mav);
	
	// 로그인 처리하기
	ModelAndView loginEnd(ModelAndView mav, Map<String, String> paraMap, HttpServletRequest request);
	
	// 로그아웃 처리하기
	ModelAndView logout(ModelAndView mav, HttpServletRequest request);
	
	// index 에서 오늘의 업무 조회
	List<Calendar_schedule_VO> scheduleselect(Map<String, Object> paraMap);


}
