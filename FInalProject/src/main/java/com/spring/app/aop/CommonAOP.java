package com.spring.app.aop;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



//=== #53. 공통 관심사 클래서 Aspect 클래스 생성하기 === //
// AOP 
@Aspect // 공통 관심사 클래스로 등록된다
@Component // bean에 저장시킨다 
public class CommonAOP {
	
	// ===== Before Advice(보조업무) 만들기 ====== // 
	   /*
	       주업무(<예: 글쓰기, 글수정, 댓글쓰기 직원목록조회 등등>)를 실행하기 앞서서  
	       이러한 주업무들은 먼저 로그인을 해야만 사용가능한 작업이므로
	       주업무에 대한 보조업무<예: 로그인 유무검사> 객체로 로그인 여부를 체크하는
	       관심 클래스(Aspect 클래스)를 생성하여 포인트컷(주업무)과 어드바이스(보조업무)를 생성하여
	       동작하도록 만들겠다.
	   */   

	// === Pointcut(주업무)을 설정해야 한다. === //
	   //     Pointcut 이란 공통관심사<예: 로그인 유무검사>를 필요로 하는 메소드를 말한다.
	   @Pointcut("execution(public * com.spring.app..*Controller.requiredLogin_*(..) )")// ..는 이 베이스 패키지 안에 있는 것(서브패키지)들을 의미 한다. / *Controller 어떤 건지는 모르겠지만 끝나는게 Controller로 끝나는 클래스들을 포함한다. / 메소드 이름의 접두어가 requiredLogin_인 메소드를 잡아준 것. / 파라미터도 잡아줘야하는데 있을 수도 없을 수도 있으므로 (..)
	   public void requiredLogin() {}
	   
	   // === Before Advice(공통관심사, 보조업무)를 구현한다. === //
	   @Before("requiredLogin()")
	   public void loginCheck(JoinPoint joinpoint) { // 로그인 유뮤 검사를 하는 메소드 작성하기
	      // JoinPoint joinPoint 는 포인트컷 되어진 주업무의 메소드이다.
	      
	      // 로그인 유무를 확인하기 위해서는 request를 통해 session 을 얻어와야 한다.
	      HttpServletRequest request = (HttpServletRequest)joinpoint.getArgs()[0]; // 주업무 메소드의 첫번째 파라미터를 얻어오는 것이다.
	      HttpServletResponse response = (HttpServletResponse)joinpoint.getArgs()[1];
	      HttpSession session = request.getSession();
	      if(session.getAttribute("loginuser") == null) {
	         String message = "1조 그룹웨어 입니다. 먼저 로그인 하세요.";
	         String loc = request.getContextPath()+"/login.gw";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	         RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg.jsp");
	         try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      
	   }
	
	

	
	
}
