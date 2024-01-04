package com.spring.app.yunsu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChattingController {
	
	// ==== #196. (웹채팅관련4) ==== //
			@GetMapping(value="/chatting/multichat.gw") 
			public String requiredLogin_multichat(HttpServletRequest request, HttpServletResponse response) {
				   
				 return "/chatting/multichat.tiles_MTS2";
			}
}
