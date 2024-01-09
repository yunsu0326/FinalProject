package com.spring.app.kimkm.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.spring.app.domain.DepartmentVO;
import com.spring.app.domain.EmployeesVO;

public interface KimkmService {

	// 회원가입
	int add_register(EmployeesVO evo);
	
	// 회원가입시 휴가 테이블 insert 하기
	int insert_vacation(String employee_id);

	// 내 정보 수정하기
	int myinfoEditEnd(EmployeesVO evo);

	// 성별 생년월일 알아오기
	Map<String, String> selectGenderBirthday(String employee_id);

	// 부서이름 팀명 알아오기
	Map<String, String> selectDeptTeam(String employee_id);
	
	// 남은 휴가일수 알아오기
	Map<String, String> selectVacation(String employee_id);

	// 회원가입시 기본 정보 읽어오기
	Map<String, String> selectRegister(String email);

	// 비밀번호 변경하기
	int pwdUpdateEnd(Map<String, String> paraMap);

	// 급여테이블 조회하기
	List<Map<String, String>> monthSal(String employee_id);

	// 급여명세서 테이블 가져오기
	Map<String, String> salaryStatement(Map<String, String> paraMap);
	
	// 급여명세서 직인 이미지 가져오기
	String selectSignimg();

	// salary 테이블에서 조건에 만족하는 급여들을 가져와서 Excel 파일로 만들기 
	void salary_to_Excel(Map<String, Object> paraMap, Model model);

	// department테이블  select하기
	List<Map<String,String>> selectdept(DepartmentVO deptvo);
	
	// 조직도 리스트 가져오기
	List<Map<String, String>> employeeList();
	
	// === Spring Scheduler(스프링 스케줄러)를 사용한 tbl_salary 테이블 insert 와 공지사항 insert === //
	void PayslipTemplate() throws Exception;

	// receipt_favorites update하기
	int receipt_favorites_update(Map<String, String> paraMap);

	// receipt_favorites 값 가져오기
	String select_receipt_favorites(String receipt_mail_seq);
	
	// email_receipt_read_count update 하기
	int email_receipt_read_count_update(String receipt_mail_seq);

	// email_receipt_read_count 값 가져오기
	String select_email_receipt_read_count(String receipt_mail_seq);

	// receipt_important 값 가져오기
	String select_receipt_important(String receipt_mail_seq);

	// receipt_important update 하기
	int receipt_important_update(Map<String, String> paraMap);

	

	
	

	

	







	
}
