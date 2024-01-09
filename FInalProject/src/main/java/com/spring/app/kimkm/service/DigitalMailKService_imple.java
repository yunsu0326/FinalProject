package com.spring.app.kimkm.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.app.kimkm.model.DigitalMailKDAO;

// ==== #31. Service 선언 ====
// 트랜잭션 처리를 담당하는 곳, 업무를 처리하는 곳, 비지니스(Business)단 
// @Component
@Service
public class DigitalMailKService_imple implements DigitalMailKService {

	@Autowired
	private DigitalMailKDAO dao;
	
	// receipt_favorites update하기
	@Override
	public int receipt_favorites_update(Map<String, String> paraMap) {
		int n = dao.receipt_favorites_update(paraMap);
		return n;
	}

	// receipt_favorites 값 가져오기
	@Override
	public String select_receipt_favorites(String receipt_mail_seq) {
		String receipt_favorites = dao.select_receipt_favorites(receipt_mail_seq);
		return receipt_favorites;
	}
	
	// email_receipt_read_count update 하기
	@Override
	public int email_receipt_read_count_update(String receipt_mail_seq) {
		int n = dao.email_receipt_read_count_update(receipt_mail_seq);
		return n;
	}

	// email_receipt_read_count 값 가져오기
	@Override
	public String select_email_receipt_read_count(String receipt_mail_seq) {
		String email_receipt_read_count = dao.select_email_receipt_read_count(receipt_mail_seq);
		return email_receipt_read_count;
	}

	// receipt_important 값 가져오기
	@Override
	public String select_receipt_important(String receipt_mail_seq) {
		String receipt_important = dao.select_receipt_important(receipt_mail_seq);
		return receipt_important;
	}

	// receipt_important update 하기
	@Override
	public int receipt_important_update(Map<String, String> paraMap) {
		int n = dao.receipt_important_update(paraMap);
		return n;
	}

	

	

	

	

	

	

	
	



	

	
}
