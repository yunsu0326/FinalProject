package com.spring.app.minjun.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.domain.*;
import com.spring.app.minjun.model.*;

@Service
public class WorkService_imple implements WorkService {
	
	// === #34. 의존객체 주입하기(DI: Dependency Injection) ===
 	@Autowired  // Type에 따라 알아서 Bean 을 주입해준다.
	private WorkDAO dao;
	// Type 에 따라 Spring 컨테이너가 알아서 bean 으로 등록된 com.spring.board.model.BoardDAO_imple 의 bean 을  dao 에 주입시켜준다. 
    // 그러므로 dao 는 null 이 아니다.

	// 특정 사원의 근무내역 가져오기
	@Override
	public List<WorkVO> my_workList(String employee_id) {
		List<WorkVO> my_workList = dao.my_workList(employee_id);
		return my_workList;
	}

	// 출근 버튼 클릭시 근태테이블에 insert 하기
	@Override
	public int goToWorkInsert(Map<String, String> paraMap) {
		int n = dao.goToWorkInsert(paraMap);
		return n;
	}

	// 퇴근 버튼 클릭시 근무테이블에 update 하기
	@Override
	public int leaveWork(Map<String, String> paraMap) {
		int n = dao.leaveWork(paraMap);
		return n;
	}

	// 특정 사원의 근무내역 가져오기
	@Override
	public List<WorkVO> workList(Map<String, String> paraMap) {
		List<WorkVO> workList = dao.workList(paraMap);
		return workList;
	}

	// 사용자가 신청한 근무를 근무신청테이블에 insert
	@Override
	public int workRequestInsert(Map<String, String> paraMap) {
		int n = dao.workRequestInsert(paraMap);
		return n;
	}

	// 특정 사용자가 신청한 근무신청 가져오기
	@Override
	public List<Work_requestVO> workRequestList(String employee_id) {
		List<Work_requestVO> workRequestList = dao.workRequestList(employee_id);
		return workRequestList;
	}

	// 특정 근무신청 삭제하기
	@Override
	public void work_request_delete(String work_request_seq) {
		dao.work_request_delete(work_request_seq);
	}

	// 오늘날짜의 출근시간 얻어오기
	@Override
	public String todayStartTime(Map<String, String> paraMap) {
		String todayStartTime = dao.todayStartTime(paraMap);
		return todayStartTime;
	}

	// 근무테이블에 퇴근시간 update 하기
	@Override
	public int goToWorkUpdate(Map<String, String> paraMap) {
		int n = dao.goToWorkUpdate(paraMap);
		return n;
	}

}
