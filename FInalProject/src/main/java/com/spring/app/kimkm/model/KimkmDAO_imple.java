package com.spring.app.kimkm.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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

	// 급여계산 하기
	@Override
	public Map<String, String> selectSalary(String employee_id) {
		Map<String, String> salary = sqlsession.selectOne("kimkm.selectSalary", employee_id);
		return salary;
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

	@Override
	public List<Map<String, String>> employeeList() {
		List<Map<String, String>> employeeList = sqlsession.selectList("kimkm.employeeList");
		return employeeList;
	}

	
	

	

	

	
	
}
