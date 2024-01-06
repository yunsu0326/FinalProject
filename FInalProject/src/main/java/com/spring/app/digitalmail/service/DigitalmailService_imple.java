package com.spring.app.digitalmail.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.digitalmail.model.DigitalmailDAO;
import com.spring.app.digitalmail.domain.EmailVO;

//==== Service 선언 ====
//트랜잭션 처리를 담당하는 곳, 업무를 처리하는 곳, 비지니스(Business)단 
//@Component
@Service
public class DigitalmailService_imple implements DigitalmailService {
 	
 	@Autowired  
	private DigitalmailDAO dao;
	
	// 내가 받은 총 메일 갯수
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int cnt = dao.getTotalCount(paraMap);
		return cnt;
	}
	// 전자메일 시작
	@Override
	public ModelAndView digitalmail(ModelAndView mav, Map<String, String> paraMap) {
		List<EmailVO> emailVOList = null;
		emailVOList = dao.SelectMyEmail_withPaging(paraMap);
		// System.out.println("이거 떠야되는데 =>"+emailVOList.size());
		mav.addObject("emailVOList", emailVOList);
		mav.setViewName("digitalmail/digitalmail.tiles_digitalmail");	
		return mav;
	}
	
	// 이메일 키워드 입력시 자동글 완성하기 //
	@Override
	public List<String> emailWordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = dao.emailWordSearchShow(paraMap);
		return wordList;
	}
	
	// === 이메일 쓰기 페이지 이동  === //
	@Override
	public ModelAndView digitalmailwrite(ModelAndView mav) {
		List<String> EmailList = dao.getEmailList();
		mav.addObject("EmailList", EmailList);
		mav.setViewName("digitalmailwrite/digitalmailwrite.tiles_digitalmail");
		return mav;
	}
	
	

}
