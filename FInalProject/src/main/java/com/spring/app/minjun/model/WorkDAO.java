
package com.spring.app.minjun.model;

import java.util.*;

import com.spring.app.domain.WorkVO;
import com.spring.app.domain.Work_requestVO;

public interface WorkDAO {

	// 특정 사원의 근무내역 가져오기
	List<WorkVO> my_workList(String employee_id);

	// 출근 버튼 클릭시 근태테이블에 insert 하기
	int goToWorkInsert(Map<String, String> paraMap);

	// 퇴근 버튼 클릭시 근무테이블에 update 하기
	int leaveWork(Map<String, String> paraMap);

	// 특정 사원의 근무내역 가져오기
	List<WorkVO> workList(Map<String, String> paraMap);

	// 사용자가 신청한 근무를 근무신청테이블에 insert
	int workRequestInsert(Map<String, String> paraMap);

	// 특정 사용자가 신청한 근무신청 가져오기
	List<Work_requestVO> workRequestList(String employee_id);

	// 특정 근무신청 삭제하기
	void work_request_delete(String work_request_seq);

	// 오늘날짜의 출근시간 얻어오기
	String todayStartTime(Map<String, String> paraMap);

	// 근무테이블에 퇴근시간 update 하기
	int goToWorkUpdate(Map<String, String> paraMap);

}
