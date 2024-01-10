package com.spring.app.kimkm.model;

import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

//==== #32. Repository(DAO) 선언 ====
//@Component
@Repository
public class DigitalMailKDAO_imple implements DigitalMailKDAO {

	@Resource
	private SqlSessionTemplate sqlsession;

	// receipt_favorites update하기
	@Override
	public int receipt_favorites_update(Map<String, String> paraMap) {
		int n = sqlsession.update("kimkm.receipt_favorites_update", paraMap);
		return n;
	}

	
	// receipt_favorites 값 가져오기
	@Override
	public String select_receipt_favorites(String receipt_mail_seq) {
		String receipt_favorites = sqlsession.selectOne("kimkm.select_receipt_favorites", receipt_mail_seq);
		return receipt_favorites;
	}


	// email_receipt_read_count update 하기
	@Override
	public int email_receipt_read_count_update(String receipt_mail_seq) {
		int n = sqlsession.update("kimkm.email_receipt_read_count_update", receipt_mail_seq);
		return n;
	}

	
	// email_receipt_read_count 값 가져오기
	@Override
	public String select_email_receipt_read_count(String receipt_mail_seq) {
		String email_receipt_read_count = sqlsession.selectOne("kimkm.select_email_receipt_read_count", receipt_mail_seq);
		return email_receipt_read_count;
	}

	// receipt_important 값 가져오기
	@Override
	public String select_receipt_important(String receipt_mail_seq) {
		String receipt_important = sqlsession.selectOne("kimkm.select_receipt_important", receipt_mail_seq);
		return receipt_important;
	}

	// receipt_important update 하기
	@Override
	public int receipt_important_update(Map<String, String> paraMap) {
		int n = sqlsession.update("kimkm.receipt_important_update", paraMap);
		return n;
	}

	
	

	
	
	
	
	
	

	

	

	
	
}
