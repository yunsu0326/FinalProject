package com.spring.app.kimkm.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.spring.app.domain.DepartmentVO;
import com.spring.app.domain.EmployeesVO;


//==== #32. Repository(DAO) 선언 ====
//@Component
@Repository
public class KimkmDAO_imple implements KimkmDAO {

	@Resource
	private SqlSessionTemplate sqlsession;

	// 회원가입
	@Override
	public int add_register(EmployeesVO evo) {
		int n = sqlsession.update("kimkm.add_register", evo);
		return n;
	}
	
	// 회원가입시 휴가 테이블 insert 하기
	@Override
	public int insert_vacation(String employee_id) {
		int n = sqlsession.insert("kimkm.insert_vacation", employee_id);
		return n;
	}
	
	// 내 정보 수정하기
	@Override
	public int myinfoEditEnd(EmployeesVO evo) {
		int n = sqlsession.update("kimkm.myinfoEditEnd", evo);
		return n;
	}

	
	// 성별 알아오기
	@Override
	public Map<String, String> selectGenderBirthday(String employee_id) {
		Map<String, String> gender_birthday = sqlsession.selectOne("kimkm.selectGenderBirthday", employee_id);
		return gender_birthday;
	}

	
	// 부서이름 팀명 알아오기
	@Override
	public Map<String, String> selectDeptTeam(String employee_id) {
		Map<String, String> dept_team = sqlsession.selectOne("kimkm.selectDeptTeam", employee_id);
		return dept_team;
	}

	
	// 회원가입시 기본 정보 읽어오기
	@Override
	public Map<String, String> selectRegister(String email) {
		Map<String, String> register = sqlsession.selectOne("kimkm.selectRegister",email);
		return register;
	}

	// 비밀번호 변경하기
	@Override
	public int pwdUpdateEnd(Map<String, String> paraMap) {
		int n = sqlsession.update("kimkm.pwdUpdateEnd", paraMap);
		return n;
	}

	// 급여 조회하기
	@Override
	public List<Map<String, String>> monthSal(String employee_id) {
		List<Map<String, String>> monthSalList = sqlsession.selectList("kimkm.monthSal", employee_id);
		return monthSalList;
	}

	// 급여명세서 가져오기
	@Override
	public Map<String, String> salaryStatement(Map<String, String> paraMap) {
		Map<String, String> salaryStatement = sqlsession.selectOne("kimkm.salaryStatement", paraMap);
		return salaryStatement;
	}

	// salay 테이블에서 Excel 담을 값 가져오기
	@Override
	public List<Map<String, String>> salaryList(Map<String, Object> paraMap) {
		List<Map<String, String>> salaryList = sqlsession.selectList("kimkm.salaryList", paraMap);
		return salaryList;
	}
	
	// department테이블  select하기
	@Override
	public List<Map<String,String>> selectdept(DepartmentVO deptvo) {
		List<Map<String,String>> deptList = sqlsession.selectList("kimkm.selectdept", deptvo);
		return deptList;
	}

	// 조직도 리스트 가져오기
	@Override
	public List<Map<String, String>> employeeList() {
		List<Map<String, String>> employeeList = sqlsession.selectList("kimkm.employeeList");
		return employeeList;
	}
	
	// 월급 테이블 insert 하기위한 값 가져오기
	@Override
	public List<Map<String, String>> emp_salary_List(String lastMonth) {
		List<Map<String, String>> emp_salary_List = sqlsession.selectList("kimkm.emp_salary_List", lastMonth);
		return emp_salary_List;
	}

	// 월급 테이블 insert 하기
	@Override
	public int insert_PayslipTemplate(List<Map<String, String>> emp_salary_List) {
		int n = sqlsession.insert("kimkm.insert_PayslipTemplate", emp_salary_List);
		return n;
	}
	
	// 공지사항 글쓰기를 위한 급여명세서 발급자 정보 select 하기
	@Override
	public Map<String, String> select_human_resources_manager(String manager) {
		Map<String, String> manager_name_empId = sqlsession.selectOne("kimkm.select_human_resources_manager", manager);
		return manager_name_empId;
	}
	
	// 공지사항 insert 하기
	@Override
	public int insert_notice_board(Map<String, String> manager_name_empId) {
		int n = sqlsession.insert("kimkm.insert_notice_board", manager_name_empId);
		return n;
	}
	

	// receipt_favorites update하기
	@Override
	public int receipt_favorites_update(Map<String, String> paraMap) {
		int n = sqlsession.update("kimkm.receipt_favorites_update", paraMap);
		return n;
	}

	
	// receipt_favorites 값 가져오기
	@Override
	public String select_receipt_favorites(String receipt_mail_seq) {
		String receipt_favorites = sqlsession.selectOne("kimkm.select_receipt_favorites", receipt_mail_seq);
		return receipt_favorites;
	}


	// email_receipt_read_count update 하기
	@Override
	public int email_receipt_read_count_update(String receipt_mail_seq) {
		int n = sqlsession.update("kimkm.email_receipt_read_count_update", receipt_mail_seq);
		return n;
	}

	
	// email_receipt_read_count 값 가져오기
	@Override
	public String select_email_receipt_read_count(String receipt_mail_seq) {
		String email_receipt_read_count = sqlsession.selectOne("kimkm.select_email_receipt_read_count", receipt_mail_seq);
		return email_receipt_read_count;
	}

	// receipt_important 값 가져오기
	@Override
	public String select_receipt_important(String receipt_mail_seq) {
		String receipt_important = sqlsession.selectOne("kimkm.select_receipt_important", receipt_mail_seq);
		return receipt_important;
	}

	// receipt_important update 하기
	@Override
	public int receipt_important_update(Map<String, String> paraMap) {
		int n = sqlsession.update("kimkm.receipt_important_update", paraMap);
		return n;
	}
	
	
	
	
	

	

	

	
	
}
