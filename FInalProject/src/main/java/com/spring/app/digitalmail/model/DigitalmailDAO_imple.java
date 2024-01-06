package com.spring.app.digitalmail.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.spring.app.digitalmail.domain.EmailVO;

//==== Repository(DAO) 선언 ====
//@Component
@Repository
public class DigitalmailDAO_imple implements DigitalmailDAO {
	
	@Resource
	private SqlSessionTemplate sqlsession;
	
	// 내가 받은 총 메일 갯수
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int cnt = sqlsession.selectOne("digitalmail.getTotalCount", paraMap);
		return cnt;
	}

	// 페이징 처리된 내가 받은 총 메일 보기
	@Override 
	public List<EmailVO> SelectMyEmail_withPaging(Map<String, String> paraMap) {
		List<EmailVO> emailVOList = sqlsession.selectList("digitalmail.SelectMyEmail_withPaging",paraMap);
		return emailVOList;
	}

	// 이메일 키워드 입력시 자동글 완성하기 //
	@Override
	public List<String> emailWordSearchShow(Map<String, String> paraMap) {
		List<String> wordList = sqlsession.selectList("digitalmail.emailWordSearchShow", paraMap);
		return wordList;
	}
	// 메일 쓰기 뷰단에서 이메일 자동 완성하기
	@Override
	public List<String> getEmailList() {
		List<String> EmailList = sqlsession.selectList("digitalmail.getEmailList");
		return EmailList;
	}

}
