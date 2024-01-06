package com.spring.app.minjun.service;


import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.spring.app.domain.VacationVO;
import com.spring.app.domain.Vacation_manageVO;
import com.spring.app.minjun.model.*;


// ==== #31. Service 선언 ====
// 트랜잭션 처리를 담당하는 곳, 업무를 처리하는 곳, 비지니스(Business)단 
// @Component
@Service
public class VacationService_imple implements VacationService {

	// === #34. 의존객체 주입하기(DI: Dependency Injection) ===
 	@Autowired  // Type에 따라 알아서 Bean 을 주입해준다.
	private VacationDAO dao;
	// Type 에 따라 Spring 컨테이너가 알아서 bean 으로 등록된 com.spring.board.model.BoardDAO_imple 의 bean 을  dao 에 주입시켜준다. 
    // 그러므로 dao 는 null 이 아니다.
	
/*
	// === #37. 시작페이지에서 메인 이미지를 보여주는 것 === //
	@Override
	public List<String> getImgfilenameList() {
		List<String> imgfilenameList = dao.getImgfilenameList();
		return imgfilenameList;
	}
*/
    ////////////////////////////////////////////////////////	
	@Override
	public ModelAndView index(ModelAndView mav) {
		List<String> imgfilenameList = dao.getImgfilenameList();
		
	// System.out.println("~~~ 확인용 imgfilenameList.size() => " + imgfilenameList.size());
	//  ~~~ 확인용 imgfilenameList.size() => 4
			
		mav.addObject("imgfilenameList", imgfilenameList);
		mav.setViewName("main/index.tiles_MTS");
		//  /WEB-INF/views/tiles1/main/index.jsp 파일을 생성한다.
					
		return mav;
	}
    ////////////////////////////////////////////////////////
	
	
	// 연차 신청시 휴가관리 테이블에 insert하기
	@Override
	public int annual_insert(Map<String, String> paraMap) {
		int n = dao.annual_insert(paraMap);
		return n;
	}


	// 특정 회원의 휴가 정보 가져오기
	@Override
	public VacationVO vacation_select(String employee_id) {
		VacationVO vacationInfo = dao.vacation_select(employee_id);
		return vacationInfo;
	}
	
	
	// 대기중인 휴가 갯수 알아오기
	@Override
	public String total_count(String employee_id) {
		String total_count = dao.total_count(employee_id);
		return total_count;
	}


	// 회원들의 신청된 휴가 중 대기중인 회원 가져오기
	@Override
	public List<Vacation_manageVO> vacList(String employee_id) {
		List<Vacation_manageVO> vacList = dao.vacList(employee_id);
		return vacList;
	}
	
	// 회원들의 신청된 휴가 중 반려된 회원 가져오기
	@Override
	public List<Vacation_manageVO> vacReturnList(String employee_id) {
		List<Vacation_manageVO> vacReturnList = dao.vacReturnList(employee_id);
		return vacReturnList;
	}
	
	
	// 회원들의 신청된 휴가 중 승인된 회원 가져오기
	@Override
	public List<Vacation_manageVO> vacApprovedList(String employee_id) {
		List<Vacation_manageVO> vacApprovedList = dao.vacApprovedList(employee_id);
		return vacApprovedList;
	}


	// 휴가 결재시 휴가관리테이블 업데이트 하기
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int vacManage_Update(Map<String, String[]> paraMap) throws Throwable {
		
		int n1=0, n2=0, n3=0;
		
		n1 = dao.vacManage_Update(paraMap); // 휴가 관리 테이블에서 vacation_confirm 컬럼을 2로 업데이트
		
		//System.out.println(n1);
		
		if(n1>0) {
			//n2 = dao.vacUpdate_annual(paraMap); // 휴가 테이블의 annual 컬럼을 신청수만큼 빼기
			//System.out.println("넘어옴");
			for( int i=0; i<paraMap.get("fk_employee_id_arr").length; i++) {
				String fk_employee_id = paraMap.get("fk_employee_id_arr")[i];
				String daysDiff = paraMap.get("daysDiff_arr")[i];
				String vacation_type = paraMap.get("vacation_type_arr")[i];
				String fk_department_id = paraMap.get("fk_department_id_arr")[i];
				String vacation_start_date_arr = paraMap.get("vacation_start_date_arr")[i];
				String vacation_end_date_arr = paraMap.get("vacation_end_date_arr")[i];
				String name_arr = paraMap.get("name_arr")[i];
				String email_arr = paraMap.get("email_arr")[i];
				
				//System.out.println("daysDiff = " +daysDiff);
				//System.out.println("fk_employee_id = " +fk_employee_id);
				// System.out.println("vacation_type = " +vacation_type);
				
				Map<String, String> paraMap2 = new HashMap<>();
				paraMap2.put("fk_employee_id", fk_employee_id);
				paraMap2.put("daysDiff", daysDiff);
				
				switch (vacation_type) {
				case "1": vacation_type = "annual"; 
						paraMap2.put("vacation_type", vacation_type);
						n2 = dao.vacUpdate_annual2(paraMap2);
					break;
				
				case "2": vacation_type = "family_care"; 
						paraMap2.put("vacation_type", vacation_type);
						n2 = dao.vacUpdate_plus(paraMap2);
					break;
					
				case "3": vacation_type = "reserve_forces"; 
						paraMap2.put("vacation_type", vacation_type);
						n2 = dao.vacUpdate_plus(paraMap2);
					break;
					
				case "4": vacation_type = "infertility_treatment"; 
						paraMap2.put("vacation_type", vacation_type);
						n2 = dao.vacUpdate_plus(paraMap2);
					break;
					
				case "5": vacation_type = "childbirth"; 
						paraMap2.put("vacation_type", vacation_type);
						n2 = dao.vacUpdate_plus(paraMap2);
					break;
					
				case "6": vacation_type = "marriage"; 
						paraMap2.put("vacation_type", vacation_type);
						n2 = dao.vacUpdate_plus(paraMap2);
					break;
					
				case "7": vacation_type = "reward"; 
						paraMap2.put("vacation_type", vacation_type);
						n2 = dao.vacUpdate_plus(paraMap2);
					break;
				}
				System.out.println("n2 => "+n2);
				if(n2 == 1) {
					Map<String, String> paraMap3 = new HashMap<>();
					paraMap3.put("fk_employee_id", fk_employee_id);
					paraMap3.put("fk_department_id", fk_department_id);
					paraMap3.put("vacation_start_date_arr", vacation_start_date_arr);
					paraMap3.put("vacation_end_date_arr", vacation_end_date_arr);
					paraMap3.put("name_arr", name_arr);
					paraMap3.put("email_arr", email_arr);
					 
					System.out.println("paraMap3 => "+paraMap3);
					// paraMap3=> {fk_department_id=200, fk_employee_id=9, vacation_end_date_arr=2024-01-11, vacation_start_date_arr=2024-01-09}
					
					// 휴가 승인이 모두 끝나면 스케쥴 달력에 insert 하기
					n3 = dao.calendarInsert(paraMap3);
				}
			}
		} 
		
		return n1*n2*n3;
	}


	// 특정 사용자의 승인완료된 휴가 가져오기
	@Override
	public List<Vacation_manageVO> myApprovedList(String employee_id) {
		List<Vacation_manageVO> myApprovedList = dao.myApprovedList(employee_id);
		return myApprovedList;
	}
	
	
	// 특정 사용자의 반려된 휴가 가져오기
	@Override
	public List<Vacation_manageVO> myReturnList(String employee_id) {
		List<Vacation_manageVO> myReturnList = dao.myReturnList(employee_id);
		return myReturnList;
	}


	// 특정 회원의 신청한 휴가 중 대기중인 휴가 정보 가져오기
	@Override
	public Map<String, String> vacHoldList_one(String vacation_seq) {
		Map<String, String> vacHoldList_one = dao.vacHoldList_one(vacation_seq);
		return vacHoldList_one;
	}


	// 휴가 반려시 반려테이블에 insert 하기
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor= {Throwable.class})
	public int vacInsert_return(Map<String, String> paraMap) throws Throwable {
		
		int n1=0, n2=0, result=n1*n2;
		
		n1 = dao.vacInsert_return(paraMap); // 휴가 반려시 반려테이블에 insert 하기
		
		if(n1==1) {
			n2 = dao.vacUpdate_return(paraMap); // 휴가 반려시 휴가관리테이블에 update 하기
		}
		return result;
	}


	// 휴가 재신청 하기 위한 insert
	@Override
	public int vac_insert_insert(Map<String, String> paraMap) {
		int n = dao.vac_insert_insert(paraMap);
		return n;
	}

	
	// 특정 사용자의 승인완료된 휴가 가져오기
	@Override
	public List<Vacation_manageVO> myHoldList(String employee_id) {
		List<Vacation_manageVO> myHoldList = dao.myHoldList(employee_id);
		return myHoldList;
	}


	// 대기중인 휴가 총 건수 알아오기
	@Override
	public int totalCount(String employee_id) {
		int totalCount = dao.totalCount(employee_id);
		return totalCount;
	}


	// 대기중인휴가의 totalPage 수 알아오기
	@Override
	public int getVactionTotalPage(Map<String, String> paraMap) {
		int totalPage = dao.getCommentTotalPage(paraMap);
		return totalPage;
	}


	// 승인 대기중인 휴가 삭제하기
	@Override
	public void seq_delete(String vacation_seq) {
		dao.seq_delete(vacation_seq);
	}


	// 차트그리기 (ajax) 월별 휴가사용 수
	@Override
	public List<Map<String, String>> monthlyVacCnt() {
		List<Map<String, String>> monthlyVacCnt = dao.monthlyVacCnt();
		return monthlyVacCnt;
	}




	



	


	


	


	
	

	











	
	

	
}
