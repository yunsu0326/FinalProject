package com.spring.app.digitalmail.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.spring.app.common.MyUtil;
import com.spring.app.common.digitalmail.util.DigitalmailFileManager;
import com.spring.app.digitalmail.service.DigitalmailService;
import com.spring.app.domain.EmployeesVO;

@Controller
public class DigitalmailController {
	
	@Autowired 
	private DigitalmailService service;
	
	@Autowired
	private DigitalmailFileManager fileManager;
	
    // 메일 홈페이지
    @GetMapping(value="/digitalmail.gw", produces="text/plain;charset=UTF-8")
    public ModelAndView requiredLogin_digitalmail(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

    	// HttpSession session = request.getSession();
    	// session.setAttribute("readCountPermission", "yes"); 조회수라면 해야되지만 나는 메일이기때문에 무조건 로그인 해야 들어올 수 있다.
    	
    	String searchType = request.getParameter("searchType");
    	String searchWord = request.getParameter("searchWord");
		
    	System.out.println("new 프린트"+searchType+searchWord); // 검색바
		
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(searchType == null) {
			searchType = "";
		}
		if(searchWord == null) {
			searchWord = "";
		}
		if(searchWord != null) {
			searchWord = searchWord.trim();
		}
		
		HttpSession session = request.getSession();
		EmployeesVO loginuser = (EmployeesVO)session.getAttribute("loginuser");
		String email = loginuser.getEmail();
		
		// System.out.println("email=>"+email);
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("email", email);
		
		// 먼저, 내가 받은 총 메일 건수(totalCount)를 구해와야 한다.
		// 총 게시물 건수(totalCount)는 검색조건이 있을 때와 없을때로 나뉘어진다. 
		int totalCount = 0;    		// 총 게시물 건수 
		int sizePerPage = 12;  		// 한 페이지당 보여줄 게시물 건수 
		int currentShowPageNo = 0;  // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함. 
		int totalPage = 0;          // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)
    	
		totalCount = service.getTotalCount(paraMap);
		
		System.out.println("totalCount=>"+totalCount);
		
		totalPage = (int) Math.ceil((double)totalCount/sizePerPage); 
    	
		if(str_currentShowPageNo == null) {
			 // 메일 초기에 보여지는 화면
			 currentShowPageNo = 1;
		 }
		 else {
	         
			 try {
	             currentShowPageNo = Integer.parseInt(str_currentShowPageNo); 
				 if(currentShowPageNo < 1 || currentShowPageNo > totalPage) { // 유효성 검사   
					currentShowPageNo = 1;
				 }
	         } catch(NumberFormatException e) {
				 currentShowPageNo = 1; 
	         }
		 }
		
		 int startRno = ((currentShowPageNo - 1) * sizePerPage) + 1;    // 시작 행번호 
		 int endRno = startRno + sizePerPage - 1; 						// 끝 행번호 	
		 paraMap.put("startRno", String.valueOf(startRno));
		 paraMap.put("endRno", String.valueOf(endRno));
		 
		 System.out.println("시작=>"+paraMap.get("startRno"));
		 System.out.println("끝=>"+paraMap.get("endRno"));
		 
		 mav= service.digitalmail(mav,paraMap);
		 
		 if("subject".equals(searchType) || 
		    "content".equals(searchType) ||
		    "subject_content".equals(searchType) || 
		    "name".equals(searchType)) {
			
			 mav.addObject("paraMap", paraMap);
		 
		 }
		
		// === 페이지바 만들기 (만들거면 해야되는데 굳이...?)  === //
		int blockSize = 10;
		int loop = 1;
		int pageNo = ((currentShowPageNo - 1)/blockSize) * blockSize + 1;
		// === 페이지바 만들기 (만들거면 해야되는데 굳이...?)  === //
		
		str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if(str_currentShowPageNo == null) {
			 // 메일 초기에 보여지는 화면
			 currentShowPageNo = 1;
		}
		else {
			currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
		}

		
		String url = "/FinalProject/digitalmail.gw";
		
		String pageBar = "<div class='emailList_settingsRight'>";
		
		pageBar +=	"<div class='icon_set'>";
		pageBar += "<a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo)+"'>";
		pageBar += "<span class='material-icons-outlined icon_img' style='font-size: 24pt;'>chevron_left</span></a>";
		pageBar += "<span class='icon_text'>이전</span></div>";
		pageBar +=	"<div class='icon_set'>";
		pageBar += "<a href='"+url+"?searchType="+searchType+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo)+"'>";
		pageBar += "<span class='material-icons-outlined icon_img' style='font-size: 24pt;'>chevron_right</span></a>";
		pageBar += "<span class='icon_text'>다음</span></div>";
		pageBar += "</div>";
		
		mav.addObject("pageBar", pageBar);
		
		String goBackURL = MyUtil.getCurrentURL(request);
		
		mav.addObject("goBackURL", goBackURL); // 검색 결과로 돌아갈 때 사용할것
		
	    return mav;
    
    }
    // 메일 홈페이지 끝
    
    // === 이메일 키워드 입력시 자동글 완성하기  === //
 	@ResponseBody
 	@GetMapping(value="/emailwordSearchShow.gw", produces="text/plain;charset=UTF-8")
 	public String WordSearchShow(HttpServletRequest request) {
 		
 		String searchType = request.getParameter("searchType");
 		String searchWord = request.getParameter("searchWord");
 		
 		// System.out.println("searchType=>"+searchType);
 		// System.out.println("searchType=>"+searchWord);
 		
 		Map<String, String> paraMap = new HashMap<>();
 		paraMap.put("searchType", searchType);
 		paraMap.put("searchWord", searchWord);
 		
 		List<String> wordList = service.emailWordSearchShow(paraMap);
 		
 		JSONArray jsonArr = new JSONArray(); // [] 
 		
 		if(wordList != null) {
 			for(String word : wordList) {
 				JSONObject jsonObj = new JSONObject(); // {} 
 				jsonObj.put("word", word);
 				
 				jsonArr.put(jsonObj); // [{},{},{}]
 			}// end of for------------
 		}
 		
 		return jsonArr.toString();
 	}
 	// === 이메일 키워드 입력시 자동글 완성하기  === //
 	
 	// === 이메일 쓰기 페이지 이동  === //
    @GetMapping(value="/digitalmailwrite.gw", produces="text/plain;charset=UTF-8")
    public ModelAndView requiredLogin_digitalmailwrite(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
    	
    	mav = service.digitalmailwrite(mav);
	    return mav;
	    
    }// end of public ModelAndView home(ModelAndView mav)
    // === 이메일 쓰기 페이지 이동  === //
 	
}
