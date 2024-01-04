package com.spring.app.minjun.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.spring.app.domain.*;
import com.spring.app.minjun.service.*;
		
	/*
	   사용자 웹브라우저 요청(View)  ==> DispatcherServlet ==> @Controller 클래스 <==>> Service단(핵심업무로직단, business logic단) <==>> Model단[Repository](DAO, DTO) <==>> myBatis <==>> DB(오라클)           
	   (http://...  *.action)                                  |                                                                                                                              
	    ↑                                                View Resolver
	    |                                                      ↓
	    |                                                View단(.jsp 또는 Bean명)
	    -------------------------------------------------------| 
	   
	   사용자(클라이언트)가 웹브라우저에서 http://localhost:9090/board/test/test_insert.action 을 실행하면
	   배치서술자인 web.xml 에 기술된 대로  org.springframework.web.servlet.DispatcherServlet 이 작동된다.
	   DispatcherServlet 은 bean 으로 등록된 객체중 controller 빈을 찾아서  URL값이 "/test_insert.action" 으로
	   매핑된 메소드를 실행시키게 된다.                                               
	   Service(서비스)단 객체를 업무 로직단(비지니스 로직단)이라고 부른다.
	   Service(서비스)단 객체가 하는 일은 Model단에서 작성된 데이터베이스 관련 여러 메소드들 중 관련있는것들만을 모아 모아서
	   하나의 트랜잭션 처리 작업이 이루어지도록 만들어주는 객체이다.
	   여기서 업무라는 것은 데이터베이스와 관련된 처리 업무를 말하는 것으로 Model 단에서 작성된 메소드를 말하는 것이다.
	   이 서비스 객체는 @Controller 단에서 넘겨받은 어떤 값을 가지고 Model 단에서 작성된 여러 메소드를 호출하여 실행되어지도록 해주는 것이다.
	   실행되어진 결과값을 @Controller 단으로 넘겨준다.
	*/
	
	
	// ==== #30. 컨트롤러 선언 ====
	//@Component
	/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
	그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. 
	즉, 여기서 bean의 이름은 boardController 이 된다. 
	여기서는 @Controller 를 사용하므로 @Component 기능이 이미 있으므로 @Component를 명기하지 않아도 BoardController 는 bean 으로 등록되어 스프링컨테이너가 자동적으로 관리해준다. 
	*/
	@Controller
	public class VacationController {
	   
	   // === #35. 의존객체 주입하기(DI: Dependency(의존) Injection(주입)) ===
	   // ※ 의존객체주입(DI : Dependency Injection) 
	   //  ==> 스프링 프레임워크는 객체를 관리해주는 컨테이너를 제공해주고 있다.
	   //      스프링 컨테이너는 bean으로 등록되어진 BoardController 클래스 객체가 사용되어질때, 
	   //      BoardController 클래스의 인스턴스 객체변수(의존객체)인 BoardService service 에 
	   //      자동적으로 bean 으로 등록되어 생성되어진 BoardService service 객체를  
	   //      BoardController 클래스의 인스턴스 변수 객체로 사용되어지게끔 넣어주는 것을 의존객체주입(DI : Dependency Injection)이라고 부른다. 
	   //      이것이 바로 IoC(Inversion (강조나 특정 운율을 만들기 위해 어순을 의도적으로 뒤집는 것) of Control == 제어의 역전) 인 것이다.
	   //      즉, 개발자가 인스턴스 변수 객체를 필요에 의해 생성해주던 것에서 탈피하여 스프링은 컨테이너에 객체를 담아 두고, 
	   //      필요할 때에 컨테이너로부터 객체를 가져와 사용할 수 있도록 하고 있다. 
	   //      스프링은 객체의 생성 및 생명주기를 관리할 수 있는 기능을 제공하고 있으므로, 더이상 개발자에 의해 객체를 생성 및 소멸하도록 하지 않고
	   //      객체 생성 및 관리를 스프링 프레임워크가 가지고 있는 객체 관리기능을 사용하므로 Inversion of Control == 제어의 역전 이라고 부른다.  
	   //      그래서 스프링 컨테이너를 IoC 컨테이너라고도 부른다.
	   
	   //  IOC(Inversion (강조나 특정 운율을 만들기 위해 어순을 의도적으로 뒤집는 것) of Control) 란 ?
	   //  ==> 스프링 프레임워크는 사용하고자 하는 객체를 빈형태로 이미 만들어 두고서 컨테이너(Container)에 넣어둔후
	   //      필요한 객체사용시 컨테이너(Container)에서 꺼내어 사용하도록 되어있다.
	   //      이와 같이 객체 생성 및 소멸에 대한 제어권을 개발자가 하는것이 아니라 스프링 Container 가 하게됨으로써 
	   //      객체에 대한 제어역할이 개발자에게서 스프링 Container로 넘어가게 됨을 뜻하는 의미가 제어의 역전 
	   //      즉, IOC(Inversion of Control) 이라고 부른다.
	   
	   
	   //  === 느슨한 결합 ===
	   //      스프링 컨테이너가 BoardController 클래스 객체에서 BoardService 클래스 객체를 사용할 수 있도록 
	   //      만들어주는 것을 "느슨한 결합" 이라고 부른다.
	   //      느스한 결합은 BoardController 객체가 메모리에서 삭제되더라도 BoardService service 객체는 메모리에서 동시에 삭제되는 것이 아니라 남아 있다.
	   
	   // ===> 단단한 결합(개발자가 인스턴스 변수 객체를 필요에 의해서 생성해주던 것)
	   // private InterBoardService service = new BoardService(); 
	   // ===> BoardController 객체가 메모리에서 삭제 되어지면  BoardService service 객체는 멤버변수(필드)이므로 메모리에서 자동적으로 삭제되어진다.
	  
	   @Autowired // Type에 따라 알아서 Bean 을 주입해준다.
	   private VacationService service;
	   
	
	   // ========= ******* 그룹웨어 시작 ******* ========= //  
	   /*
	   // === #36. 메인페이지 요청 === //
	   // 먼저, com.spring.app.HomeController 에 가서 @Controller 를 주석 처리한다.
	   @GetMapping("/")
	   public ModelAndView home(ModelAndView mav) {
		   mav.setViewName("redirect:/index.gw");
		   return mav;
	   } // end of public ModelAndView home(ModelAndView mav)
	   
	   
	   @GetMapping("/index.action")
	   public ModelAndView index(ModelAndView mav) {
		   
		   List<String> imgfilenameList = service.getImgfilenameList();
		   
		   System.out.println("확인용 imgfilenameList.size => " + imgfilenameList.size());
		   // 확인용 imgfilenameList.size => 4
		   
		   mav.addObject("imgfilenameList", imgfilenameList); // ModelAndView 에서 담을때는 addObject ==> 담는것
		   mav.setViewName("main/index.tiles1");
		   //   /WEB-INF/views/tiles1/main/index.jsp
		   
		   return mav;
	   } // end of public ModelAndView home(ModelAndView mav)
	
	// === 또는 === //   
	@GetMapping("/index.gw")
	public ModelAndView index(ModelAndView mav) {
	    mav = service.index(mav);
		return mav;
	}
	*/
	///////////////////////////////////////////////////////////////

	// 휴가 메인 페이지 이동
	@RequestMapping("/vacation.gw")
	public ModelAndView requiredLogin_vacation(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		HttpSession session = request.getSession();
        EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
        
        // System.out.println("loginuser =>" + loginuser);
        
        String employee_id = loginuser.getEmployee_id(); // 로그인 한 유저의 employee_id 정보
        
        request.setAttribute("employee_id", employee_id); // 로그인 한 유저의 사원번호
        // System.out.println("employee_id =>" + employee_id);
        // 
    
        VacationVO vacationInfo = service.vacation_select(employee_id);
        String annual = vacationInfo.getAnnual();
        String childbirth =  vacationInfo.getChildbirth();
        String family_care = vacationInfo.getFamily_care();
        String infertility_treatment = vacationInfo.getInfertility_treatment();
        String marriage = vacationInfo.getMarriage();
        String reserve_forces = vacationInfo.getReserve_forces();
        String reward = vacationInfo.getReward();
        // System.out.println("확인용 annual => " + annual);
        
        request.setAttribute("annual", annual);
        request.setAttribute("childbirth", childbirth);
        request.setAttribute("family_care", family_care);
        request.setAttribute("infertility_treatment", infertility_treatment);
        request.setAttribute("marriage", marriage);
        request.setAttribute("reserve_forces", reserve_forces);
        request.setAttribute("reward", reward);
        
		mav.setViewName("my_time_off/vacation.tiles_MTS");
		return mav;
	}
	
	/*
	// 특정 회원의 휴가 정보 가져오기
	@RequestMapping("/vacationInfo.gw")
	public ModelAndView requiredLogin_vacationInfo(ModelAndView mav, HttpServletRequest request) {
	
		HttpSession session =  request.getSession();
		EmployeesVO loginuser = (EmployeesVO)session.getAttribute("loginuser");
		
		VacationVO vacationInfo = service.vacation_select(loginuser.getEmployee_no());
		
		String annual = vacationInfo.getAnnual().toString();

		System.out.println("확인용 annual" + annual);
		
		mav.addObject("annual", annual);
        mav.addObject("loc", request.getContextPath()+"/my_vacation.gw");
		return mav;
	}
	*/
	
	// 연차 신청시 휴가관리 테이블에 insert하기
	@RequestMapping("/annual.gw")
	public ModelAndView annual(ModelAndView mav,
							    HttpServletRequest request,
							    VacationVO vacationvo,
							    Vacation_manageVO vacation_managevo,
							    @RequestParam("vacation_start_date") String vacation_start_date,
					            @RequestParam("vacation_end_date") String vacation_end_date,
					            @RequestParam("daysDiff") String daysDiff,
					            @RequestParam("vacation_reason") String vacation_reason,
					            @RequestParam("vacation_type") String vacation_type
							) {

		// === 휴가 신청에 필요한 로그인 유저의 employee_id 가져오기 [시작] ===
		HttpSession session = request.getSession();
        EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
        
        String employee_id = loginuser.getEmployee_id(); // 로그인 한 유저의 employee_id 정보
        
        
		// System.out.println("employee_id => " + employee_id);
		// === 휴가 신청에 필요한 로그인 유저의 employee_id 가져오기 [끝] ===
        /*
        System.out.println("vacation_start_date: " + vacation_start_date);
        System.out.println("vacation_end_date: " + vacation_end_date);
        System.out.println("daysDiff: " + daysDiff);
        System.out.println("vacation_reason: " + vacation_reason);
        System.out.println("vacation_type: " + vacation_type);
        
		employee_id => 5
		vacation_start_date: 2023-12-12
		vacation_end_date: 2023-12-22
		daysDiff: 10
		vacation_reason: ttt
		vacation_type: 1
        */
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("employee_id", employee_id);
		paraMap.put("vacation_start_date", vacation_start_date);
		paraMap.put("vacation_end_date", vacation_end_date);
		paraMap.put("daysDiff", daysDiff);
		paraMap.put("vacation_reason", vacation_reason);
		paraMap.put("vacation_type", vacation_type);
		paraMap.put("vacation_manager", loginuser.getManager_id());
		
		// System.out.println("확인용 employee_id =>" + paraMap.get("employee_id"));
		// 확인용 employee_no => 5
		
		// 연차 신청시 휴가관리 테이블에 insert하기
		int n = service.annual_insert(paraMap);
		
		// System.out.println("확인용 n =>" + n);
		
		if(n==1) {
            mav.addObject("message", "휴가 신청 완료");
            mav.addObject("loc", request.getContextPath()+"/vacation.gw");
            mav.setViewName("msg");
         }
         else {
            mav.addObject("message", "남은연차가 신청 연차보다 적습니다");
            mav.addObject("loc", request.getContextPath()+"/vacation.gw");
            mav.setViewName("msg");
         }

		return mav;
	}
	
	// 연차 상세 페이지 이동
	@GetMapping("/vacation_detail.gw")
	public ModelAndView requiredLogin_vacation_list(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		HttpSession session = request.getSession();
        EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
		
        String employee_id = loginuser.getEmployee_id(); // 로그인 한 유저의 employee_id 정보
        
        // 특정 사용자의 승인완료된 휴가 가져오기
        List<Vacation_manageVO> myHoldList = service.myHoldList(employee_id);
        
		// 특정 사용자의 승인완료된 휴가 가져오기
        List<Vacation_manageVO> myApprovedList = service.myApprovedList(employee_id);
        
        // 특정 사용자의 반려된 휴가 가져오기
        List<Vacation_manageVO> myReturnList = service.myReturnList(employee_id);
		
        mav.addObject("employee_id", employee_id);
        mav.addObject("myHoldList", myHoldList);
        mav.addObject("myApprovedList", myApprovedList);
        mav.addObject("myReturnList", myReturnList);
		
		mav.setViewName("my_time_off/vacation_detail.tiles_MTS");
		return mav;
	} 
	
	
	// 연차 계획 페이지 이동
	@GetMapping("/vacation_plan.gw")
	public ModelAndView requiredLogin_vacation_plan(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		mav.setViewName("my_time_off/vacation_plan.tiles_MTS");
		return mav;
	} 
	
	
	////////////////////////////[휴가 관리자용 시작]//////////////////////////////
	// 휴가 관련 관리자 전용 페이지 이동 
	@RequestMapping("/vacation_manage.gw")
	public ModelAndView requiredLogin_vacation_manage(HttpServletRequest request, HttpServletResponse response, ModelAndView mav, Vacation_manageVO vacation_managevo) {
		
		HttpSession session = request.getSession();
        EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
        
        String employee_id = loginuser.getEmployee_id(); // 로그인한 유저의 사원번호
        
		if(Integer.parseInt(loginuser.getGradelevel()) < 3) { // 로그인한 유저의 gradelevel 이 5 미만인 경우
			
			mav.addObject("message", "관리 권한이 없습니다.");
			mav.addObject("loc", request.getContextPath()+"/vacation.gw");
            mav.setViewName("msg");
        }
        else { // 로그인한 유저의 gradelevel 이 5 이상인 경우, 부서장급 이상
        	
        	// 회원들의 신청된 휴가 중 대기중인 회원 가져오기
            List<Vacation_manageVO> vacList = service.vacList(employee_id);
            
            // 대기중인 휴가 갯수 알아오기
        	String total_count = service.total_count(employee_id);
        	
        /*
    		
        	
        	
        	
            // String str_currentShowPageNo = request.getParameter("currentShowPageNo");
        	
    		int totalCount = 0;        // 총 게시물 건수
    		int sizePerPage = 5;	   // 한 페이지당 보여줄 게시물 건수
    		int currentShowPageNo = 0; // 현재 보여주는 페이지 번호, 초기치로는 1페이지로 설정함.
    		int totalPage = 0; 		   // 총 페이지 수(웹브라우저에 보여줄 총 페이지 개수)
    		
    		// 대기중인 휴가 총 건수 알아오기
    		totalCount = service.totalCount(employee_id);
    		// System.out.println("totalCount => "+totalCount); // 8
    		
    		totalPage = (int)Math.ceil((double)totalCount/sizePerPage);
    		// System.out.println("totalPage => " + totalPage); // 2
    		
    		/*
    		if(str_currentShowPageNo == null) {
    			// 게시판에 보여지는 초기화면
    			currentShowPageNo = 1;
    		}
    		else { 
    			try {
    				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
    			  
    				if(currentShowPageNo < 1 || currentShowPageNo > totalPage) {
    					// get 방식이므로 사용자가 0 이하의 숫자를 입력하여 장난친 경우
    					// get 방식이므로 사용자가 DB에 존재하는 페이지 수 보다 큰 값을 입력하여 장난친 경우
    					currentShowPageNo = 1;
    				}
    			  
    			} catch (NumberFormatException e) {
    				// get 방식이므로 사용자가 숫자가 아닌 문자를 입력하여 장난친 경우
    				currentShowPageNo = 1;
    			}
    		}
        */
            // 회원들의 신청된 휴가 중 반려된 회원 가져오기
            List<Vacation_manageVO> vacReturnList = service.vacReturnList(employee_id);
            
            // 회원들의 신청된 휴가 중 승인된 회원 가져오기
            List<Vacation_manageVO> vacApprovedList = service.vacApprovedList(employee_id);
            
        	mav.addObject("total_count", total_count);
            mav.addObject("vacList", vacList);
            mav.addObject("vacReturnList", vacReturnList);
            mav.addObject("vacApprovedList", vacApprovedList);
    		mav.setViewName("my_time_off/vacation_manage.tiles_MTS");
        }
		return mav;
	}
	
	// ==== 휴가 승인 [시작] ==== //
	// 휴가 승인시 휴가관리테이블 업데이트 하기.
	@RequestMapping("vacUpdate.gw")
	public ModelAndView vacUpdate(Vacation_manageVO vvo, HttpServletRequest request, ModelAndView mav) {
		
		// jsp 에서 가져온 값
		String vacation_seq = request.getParameter("vacation_seq"); 			  // 휴가번호
		String vacation_start_date = request.getParameter("vacation_start_date"); // 시작일자
		String vacation_end_date = request.getParameter("vacation_end_date"); 	  // 종료일자
		String fk_employee_id = request.getParameter("fk_employee_id"); 		  // 사원번호
		String vacation_type = request.getParameter("vacation_type"); 	  		  // 휴가종류
		String daysDiff = request.getParameter("daysDiff"); 					  // 날짜차이

		/*
		System.out.println("vacation_seq => " + vacation_seq); 				 // vacation_seq => 14,10
		System.out.println("vacation_start_date => " + vacation_start_date); // vacation_start_date => 2023-12-29,2023-12-12
		System.out.println("vacation_end_date => " + vacation_end_date);     // vacation_end_date => 2023-12-30,2023-12-22
		System.out.println("fk_employee_id => " + fk_employee_id); 		     // fk_employee_id => 9905,10000
		System.out.println("vacation_type => " + vacation_type); 			 // vacation_type => 2,2
		System.out.println("daysDiff => " + daysDiff); 					     // daysDiff => 1,10
		*/
		
		// System.out.println("vacation_type => " + vacation_type);
		
		String[] vacation_seq_arr = vacation_seq.split(",");
		String[] vacation_start_date_arr = vacation_start_date.split(",");
		String[] vacation_end_date_arr = vacation_end_date.split(",");
		String[] fk_employee_id_arr = fk_employee_id.split(",");
		String[] vacation_type_arr = vacation_type.split(",");
		String[] daysDiff_arr = daysDiff.split(",");
		
		// System.out.println("vacation_type_arr => " + vacation_type_arr);
		
		Map<String, String[]> paraMap = new HashMap<>();
		
		paraMap.put("vacation_seq_arr", vacation_seq_arr);
		paraMap.put("vacation_start_date_arr", vacation_start_date_arr);
		paraMap.put("vacation_end_date_arr", vacation_end_date_arr);
		paraMap.put("fk_employee_id_arr", fk_employee_id_arr);
		paraMap.put("vacation_type_arr", vacation_type_arr);
		paraMap.put("daysDiff_arr", daysDiff_arr);
		
		try {
			int n = service.vacManage_Update(paraMap);
			
			if(n>0) {
				mav.addObject("message", "결재가 완료되었습니다.");
				mav.addObject("loc", request.getContextPath()+"/vacation_manage.gw");
				mav.setViewName("msg");
			}
			else {
				mav.addObject("message", "남은 연차가 사용 연차보다 적습니다.");
				mav.addObject("loc", request.getContextPath()+"/vacation.gw");
				mav.setViewName("msg");
			}
			
   		} catch (Throwable e) {
   			e.printStackTrace();
   		}
		return mav;
	}
	
	// === #132. 대기중인휴가의 totalPage 알아오기(JSON 으로 처리) === //
	@ResponseBody
	@GetMapping(value="getVacationTotalPage.action")
	public String getVacationTotalPage(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
        EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
        
        String employee_id = loginuser.getEmployee_id(); // 로그인한 유저의 사원번호
		
		String vacation_confirm = request.getParameter("vacation_confirm");
		String sizePerPage = request.getParameter("sizePerPage");
		
	   
		Map<String, String> paraMap = new HashMap<>();
	   
		paraMap.put("vacation_confirm", vacation_confirm);
		paraMap.put("sizePerPage", sizePerPage);
		paraMap.put("employee_id", employee_id);
	   
		int totalPage = service.getVactionTotalPage(paraMap); // 대기중인휴가의 totalPage 수 알아오기
	   
		JSONObject jsonObj = new JSONObject(); // {}
		jsonObj.put("totalPage", totalPage);   // {"totalPage":"21"}
		
		return jsonObj.toString();
	}
	// ==== 휴가 승인 [끝] ==== //
	
	
	// ==== 휴가 반려 [시작] ==== //
	// 휴가 반려시 반려테이블에 insert & update 동시에 하기 (트랜젝션 처리해야함)
	@RequestMapping("vacInsert_return.gw")
	public ModelAndView vacInsert_return(HttpServletRequest request, ModelAndView mav) {
		
		String vacation_return_reason = request.getParameter("return_reason"); // 반려사유
		String vacation_seq = request.getParameter("vacation_seq"); // 휴가번호
		String employee_id = request.getParameter("employee_id"); // 휴가신청자 id
		
		HttpSession session = request.getSession();
        EmployeesVO loginuser = (EmployeesVO) session.getAttribute("loginuser");
        
        String loginuser_name = loginuser.getName(); // 관리자 이름

		// System.out.println("vacation_return_reason => "+vacation_return_reason);
		// 넘어가나?
		// System.out.println("vacation_seq"+vacation_seq);
		// 1
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("vacation_return_reason", vacation_return_reason);
		paraMap.put("vacation_seq", vacation_seq);
		paraMap.put("employee_id", employee_id);
		paraMap.put("loginuser_name", loginuser_name);
		
		try {
			int n = service.vacInsert_return(paraMap);
			// System.out.println("n => " + n);
			mav.addObject("message", "결재가 완료되었습니다.");
			mav.addObject("loc", request.getContextPath()+"/vacation_manage.gw");
			mav.setViewName("msg");
   		} catch (Throwable e) {
   			e.printStackTrace();
   		}
		return mav;
	}
	
	// ==== 휴가 반려 [끝] ==== //
	
	
	////////////////////////////[휴가 관리자용 끝]//////////////////////////////
	
	
	// 휴가 재신청 하기 위한 insert
	@PostMapping("/vac_insert_insert.gw")
	public ModelAndView vac_insert_insert(ModelAndView mav, HttpServletRequest request) {
		
		String vacation_seq = request.getParameter("vacation_seq");
		String vacation_type = request.getParameter("vacation_type");
		String vacation_manager = request.getParameter("manager_id");
		String fk_employee_id = request.getParameter("employee_id");
		String vacation_reason = request.getParameter("frm_vacation_reason");
		String vacation_start_date = request.getParameter("frm_vacation_start_date");
		String vacation_end_date = request.getParameter("frm_vacation_end_date");
		
		/*
		System.out.println("vacation_seq => "+vacation_seq);
		System.out.println("vacation_type => "+vacation_type);
		System.out.println("manager_id => "+manager_id);
		System.out.println("employee_id => "+employee_id);
		System.out.println("vacation_reason => "+vacation_reason);
		System.out.println("vacation_start_date => "+vacation_start_date);
		System.out.println("vacation_end_date => "+vacation_end_date);
		*/ 
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("vacation_seq", vacation_seq);
		paraMap.put("vacation_type", vacation_type);
		paraMap.put("vacation_manager", vacation_manager);
		paraMap.put("fk_employee_id", fk_employee_id);
		paraMap.put("vacation_reason", vacation_reason);
		paraMap.put("vacation_start_date", vacation_start_date);
		paraMap.put("vacation_end_date", vacation_end_date);
		
		int n = service.vac_insert_insert(paraMap);
		
		if(n==1) {
			mav.addObject("message", "상신이 완료되었습니다.");
			mav.addObject("loc", request.getContextPath()+"/vacation_detail.gw");
			mav.setViewName("msg");
		}
		else {
			mav.addObject("message", "남은 연차가 사용 연차보다 적습니다.");
			mav.addObject("loc", request.getContextPath()+"/vacation.gw");
			mav.setViewName("msg");
		}
		
		return mav;
	}
	
	
	// 승인 대기중인 휴가 삭제하기
	@PostMapping("/seq_delete.gw")
	public String seq_delete(HttpServletRequest request) {
		
		String vacation_seq = request.getParameter("vacation_seq");
		// System.out.println(vacation_seq);
		// 10
		
		service.seq_delete(vacation_seq);
		
		return "redirect:/vacation_detail.gw";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}