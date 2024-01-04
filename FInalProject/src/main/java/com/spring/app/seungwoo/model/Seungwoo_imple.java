package com.spring.app.seungwoo.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spring.app.domain.EmployeesVO;
import com.spring.app.domain.JobsVO;
import com.spring.app.domain.TeamVO;

//==== #32. Repository(DAO) 선언 ====
//@Component
@Repository
public class Seungwoo_imple implements SeungwooDAO {

	// === #33. 의존객체 주입하기(DI: Dependency Injection) ===
	// >>> 의존 객체 자동 주입(Automatic Dependency Injection)은
	//     스프링 컨테이너가 자동적으로 의존 대상 객체를 찾아서 해당 객체에 필요한 의존객체를 주입하는 것을 말한다. 
	//     단, 의존객체는 스프링 컨테이너속에 bean 으로 등록되어 있어야 한다. 

	//     의존 객체 자동 주입(Automatic Dependency Injection)방법 3가지 
	//     1. @Autowired ==> Spring Framework에서 지원하는 어노테이션이다. 
	//                       스프링 컨테이너에 담겨진 의존객체를 주입할때 타입을 찾아서 연결(의존객체주입)한다.
	
	//     2. @Resource  ==> Java 에서 지원하는 어노테이션이다.
	//                       스프링 컨테이너에 담겨진 의존객체를 주입할때 필드명(이름)을 찾아서 연결(의존객체주입)한다.
	
	//     3. @Inject    ==> Java 에서 지원하는 어노테이션이다.
    //                       스프링 컨테이너에 담겨진 의존객체를 주입할때 타입을 찾아서 연결(의존객체주입)한다.
	
/*	
	@Autowired  // Type에 따라 알아서 Bean 을 주입해준다.
	private SqlSessionTemplate abc;
	// Type 에 따라 Spring 컨테이너가 알아서 root-context.xml 에 생성된 org.mybatis.spring.SqlSessionTemplate 의 bean 을  abc 에 주입시켜준다. 
    // 그러므로 abc 는 null 이 아니다.
*/
	
	@Resource
	private SqlSessionTemplate sqlsession;
	
	@Resource
	private SqlSessionTemplate sqlsession_hr;
	
	
	// === #38. 시작페이지에서 메인 이미지를 보여주는 것 === //
	@Override
	public List<String> getImgfilenameList() {
		List<String> imgfilenameList = sqlsession.selectList("seungwoo.getImgfilenameList");
		return imgfilenameList;
	}
	
	@Override
	public List<String> getwelcome_mailList() {			
		List<String> welcome_mailList = sqlsession.selectList("seungwoo.getwelcome_mailList");
		return welcome_mailList;
	}
	
	@Override
	public List<Map<String, String>> unready_member() {
		List<Map<String, String>> unready_member = sqlsession.selectList("seungwoo.unready_member");
		return unready_member;
	}
	


	// 부서 정보를 알아오는 것
	@Override
	public List<String> getdepartmentList() {
		
		List<String> department_List = sqlsession.selectList("seungwoo.getdepartmentList");
		
		return department_List;
	}

	// 부서에서 해당하는 팀
	@Override
	public List<TeamVO> searchteam(String deptno) {
		List<TeamVO> teamList = sqlsession.selectList("seungwoo.searchteam", deptno);
		return teamList;
	}

	// 팀에 해당하는 직업
	@Override
	public List<JobsVO> searchjob(String teamno) {
		List<JobsVO> jobList = sqlsession.selectList("seungwoo.searchjob", teamno);
		return jobList;
	}
	
	// 이메일 중복확인
	@Override
	public EmployeesVO email_uq(String uq_email) {
		EmployeesVO empvo = sqlsession.selectOne("seungwoo.email_uq", uq_email);			
		return empvo;
	}
	
	// 부서장 팀장 중복여부 체크
	@Override
	public EmployeesVO emptyjob(String jobno) {
		EmployeesVO empvo = sqlsession.selectOne("seungwoo.emptyjob", jobno);			
		return empvo;
	}

	// 정보에 해당하는 값 받아오기
	@Override
	public Map<String, String> selectinfo(Map<String, String> paraMap) {
		Map<String, String> selectinfomap = sqlsession.selectOne("seungwoo.selectinfo" , paraMap);
		return selectinfomap;
	}

	// 정보에 해당하는 값 넣기
	@Override
	public int insertinfo(EmployeesVO empvo) {
		int n = sqlsession.insert("seungwoo.insertinfo", empvo);
		return n;
	}
	
	@Override
	public String searchseq() {
		String seq = sqlsession.selectOne("seungwoo.searchseq");			
		return seq;
	}

	@Override
	public int t_m_update(Map<String, String> gradeMap) {
		int n = sqlsession.update("seungwoo.t_m_update", gradeMap);
		return n;
	}
	
	
	@Override
	public int emp_t_m_update(Map<String, String> gradeMap) {
		int n2 = sqlsession.update("seungwoo.emp_t_m_update", gradeMap);
		return n2;
	}
	
	@Override
	public int dept_team_update(Map<String, String> upMap) {
		int dept_team = sqlsession.update("seungwoo.dept_team_update", upMap);
		return dept_team;
	}
	
	// emp테이블  dept, team 테이블 책임자 번호 업데이트
	@Override
	public int emp_dept_team(Map<String, String> upMap) {
		int emp_dept_team = sqlsession.update("seungwoo.emp_dept_team", upMap);
		return emp_dept_team;
	}
	
	// 최종적으로 대기회원 삭제
	@Override
	public int last_unreadydel(Map<String, Object> empidMap) {
		int del = sqlsession.delete("seungwoo.last_unreadydel", empidMap);
		return del;
	}




	
	








	
}
