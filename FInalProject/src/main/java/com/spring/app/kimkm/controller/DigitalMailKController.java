package com.spring.app.kimkm.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.spring.app.kimkm.service.DigitalMailKService;

@Controller
public class DigitalMailKController {

	@Autowired
	private DigitalMailKService service;
	
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
