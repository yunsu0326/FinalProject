package com.spring.app.minjun.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.spring.app.domain.WorkVO;
import com.spring.app.domain.Work_requestVO;

@Repository
public class WorkDAO_Imple implements WorkDAO {

	@Resource
	private SqlSessionTemplate sqlsession;
	
	// 특정 사원의 근무내역 가져오기
	@Override
	public List<WorkVO> my_workList(String employee_id) {
		List<WorkVO> my_workList = sqlsession.selectList("work.my_workList", employee_id);
		return my_workList;
	}

	// 출근 버튼 클릭시 근태테이블에 insert 하기
	@Override
	public int goToWorkInsert(Map<String, String> paraMap) {
		int n = sqlsession.insert("work.goToWorkInsert", paraMap);
		return n;
	}

	// 퇴근 버튼 클릭시 근무테이블에 update 하기
	@Override
	public int leaveWork(Map<String, String> paraMap) {
		int n = sqlsession.update("work.leaveWorkUpdate", paraMap);
		return n;
	}

	// 특정 사원의 근무내역 가져오기
	@Override
	public List<WorkVO> workList(Map<String, String> paraMap) {
		List<WorkVO> workList = sqlsession.selectList("work.workList", paraMap);
		return workList;
	}

	// 사용자가 신청한 근무를 근무신청테이블에 insert
	@Override
	public int workRequestInsert(Map<String, String> paraMap) {
		int n = sqlsession.insert("work.workRequestInsert", paraMap);
		return n;
	}

	// 특정 사용자가 신청한 근무신청 가져오기
	@Override
	public List<Work_requestVO> workRequestList(String employee_id) {
		List<Work_requestVO> workRequestList = sqlsession.selectList("work.workRequestList", employee_id);
		return workRequestList;
	}

	// 특정 근무신청 삭제하기
	@Override
	public void work_request_delete(String work_request_seq) {
		sqlsession.delete("work.work_request_delete", work_request_seq);
	}

	// 오늘날짜의 출근시간 얻어오기
	@Override
	public String todayStartTime(Map<String, String> paraMap) {
		String todayStartTime = sqlsession.selectOne("work.todayStartTime", paraMap);
		return todayStartTime;
	}

	// 근무테이블에 퇴근시간 update 하기
	@Override
	public int goToWorkUpdate(Map<String, String> paraMap) {
		int n = sqlsession.update("work.goToWorkUpdate", paraMap);
		return n;
	}

}
