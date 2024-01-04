package com.spring.app.seungwoo.model;

import java.util.List;
import java.util.Map;

import com.spring.app.domain.EmployeesVO;
import com.spring.app.domain.JobsVO;
import com.spring.app.domain.TeamVO;

public interface SeungwooDAO {

	///////////////////////////////////////////////////////////////
	
	// 시작페이지에서 메인 이미지를 보여주는 것 
	List<String> getImgfilenameList();
	
	// 메일 발송현황을 알아오는 것
	List<Map<String, String>> unready_member();
	
	// 메일 발송현황을 알아오는 것
	List<String> getwelcome_mailList();

	
	// 부서 정보를 알아오는 것
	List<String> getdepartmentList();
	
	// 부서에 해당하는 팀 찾기 
	List<TeamVO> searchteam(String deptno);
	
	// 팀에 해당하는 직업 찾기
	List<JobsVO> searchjob(String teamno);
	
	// 정보에 해당하는 값 받아오기
	Map<String, String> selectinfo(Map<String, String> paraMap);

	// 정보에 해당하는 값 넣기
	int insertinfo(EmployeesVO empvo);
	
	// 이메일 중복확인
	EmployeesVO email_uq(String uq_email);
	
	// 부서장 팀장 중복여부 체크
	EmployeesVO emptyjob(String jobno);
	
	// 시퀀스 채번하기
	String searchseq();
	
	// 팀장 번호 업데이트
	int t_m_update(Map<String, String> gradeMap);
	
	// 사원 테이블 팀장 번호 업데이트
	int emp_t_m_update(Map<String, String> gradeMap);
	
	
	// dept, team 테이블 책임자 번호 업데이트
	int dept_team_update(Map<String, String> upMap);
	
	// emp테이블  dept, team 테이블 책임자 번호 업데이트
	int emp_dept_team(Map<String, String> upMap);

	// 최종적으로 대기회원 삭제
	int last_unreadydel(Map<String, Object> empidMap);









	


	

	

	
	
	
	
	
	
	
}
