package com.spring.app.yunsu.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;


import com.spring.app.domain.*;

@Repository
public class ScheduleDAO_imple implements ScheduleDAO {

	@Resource
	private SqlSessionTemplate sqlsession;

	
	// 사내 캘린더에 캘린더 소분류 명 존재 여부 알아오기
	@Override
	public int existComCalendar(String com_smcatgoname) {
		int m = sqlsession.selectOne("yunsu.existComCalendar", com_smcatgoname);
		return m;
	}
	
	// 사내 캘린더에 캘린더 소분류 추가하기
	@Override
	public int addComCalendar(Map<String, String> paraMap) throws Throwable {
		int n = sqlsession.insert("yunsu.addComCalendar", paraMap);
		return n;
	}

	
	// 내 캘린더에 캘린더 소분류 명 존재 여부 알아오기
	@Override
	public int existMyCalendar(Map<String, String> paraMap) {
		int m = sqlsession.selectOne("yunsu.existMyCalendar", paraMap);
		return m;
	}

	// 내 캘린더에 캘린더 소분류 추가하기
	@Override
	public int addMyCalendar(Map<String, String> paraMap) throws Throwable {
		int n = sqlsession.insert("yunsu.addMyCalendar", paraMap);
		return n;
	}

	
	// 사내 캘린더에서 사내캘린더 소분류  보여주기 
	@Override
	public List<Calendar_small_category_VO> showCompanyCalendar() {
		List<Calendar_small_category_VO> calendar_small_category_VO_CompanyList = sqlsession.selectList("yunsu.showCompanyCalendar");  
		return calendar_small_category_VO_CompanyList;
	}

	
	// 내 캘린더에서 내캘린더 소분류  보여주기
	@Override
	public List<Calendar_small_category_VO> showMyCalendar(String fk_employee_id) {
		List<Calendar_small_category_VO> calendar_small_category_VO_MyList = sqlsession.selectList("yunsu.showMyCalendar", fk_employee_id);  
		return calendar_small_category_VO_MyList;
	}

	
	// 일정 등록시 내캘린더,사내캘린더 선택에 따른 서브캘린더 종류를 알아오기
	@Override
	public List<Calendar_small_category_VO> selectSmallCategory(Map<String, String> paraMap) {
		List<Calendar_small_category_VO> small_category_VOList = sqlsession.selectList("yunsu.selectSmallCategory", paraMap);
		return small_category_VOList;
	}

	
	// 공유자를 찾기 위한 특정글자가 들어간 회원명단 불러오기
	@Override
	public List<EmployeesVO> searchJoinUserList(String joinUserName) {
		List<EmployeesVO> joinUserList = sqlsession.selectList("yunsu.searchJoinUserList", joinUserName);
		return joinUserList;
	}

	
	// 일정 등록하기
	@Override
	public int registerSchedule_end(Map<String, String> paraMap) throws Throwable {
		int n = sqlsession.insert("yunsu.registerSchedule_end", paraMap);
		return n;
	}

	
	// 등록된 일정 가져오기
	@Override
	public List<Calendar_schedule_VO> selectSchedule(Map<String, String> paraMap) {
		List<Calendar_schedule_VO> scheduleList = sqlsession.selectList("yunsu.selectSchedule", paraMap);
		return scheduleList;
	}

	
	// 일정 상세 보기 
	@Override
	public Map<String,String> detailSchedule(String scheduleno) {
		Map<String,String> map = sqlsession.selectOne("yunsu.detailSchedule", scheduleno);
		return map;
	}

	
	// 일정삭제하기
	@Override
	public int deleteSchedule(String scheduleno) throws Throwable {
		int n = sqlsession.delete("yunsu.deleteSchedule", scheduleno);
		return n;
	}

	
	// 일정수정하기
	@Override
	public int editSchedule_end(Calendar_schedule_VO svo) throws Throwable {
		int n = sqlsession.update("yunsu.editSchedule_end", svo);
		return n;
	}

	
	// (사내캘린더 또는 내캘린더)속의  소분류 카테고리인 서브캘린더 삭제하기 
	@Override
	public int deleteSubCalendar(String smcatgono) throws Throwable {
		int n = sqlsession.delete("yunsu.deleteSubCalendar", smcatgono);
		return n;
	}

	
	// (사내캘린더 또는 내캘린더)속의 소분류 카테고리인 서브캘린더 수정하기 
	@Override
	public int editCalendar(Map<String, String> paraMap) {
		int n = sqlsession.update("yunsu.editCalendar", paraMap);
		return n;
	}

	
	// 수정된 (사내캘린더 또는 내캘린더)속의 소분류 카테고리명이 이미 해당 사용자가 만든 소분류 카테고리명으로 존재하는지 유무 알아오기  
	@Override
	public int existsCalendar(Map<String, String> paraMap) {
		int m = sqlsession.selectOne("yunsu.existsCalendar", paraMap);
		return m;
	}

	
	// 총 일정 검색 건수(totalCount)
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = sqlsession.selectOne("yunsu.getTotalCount", paraMap);
		return n;
	}

	
	// 페이징 처리한 캘린더 가져오기(검색어가 없다라도 날짜범위 검색은 항시 포함된 것임)
	@Override
	public List<Map<String,String>> scheduleListSearchWithPaging(Map<String, String> paraMap) { 
		List<Map<String,String>> scheduleList = sqlsession.selectList("yunsu.scheduleListSearchWithPaging", paraMap);
		return scheduleList;
	}
	
	// 부서 캘린더에 캘린더 소분류 명 존재 여부 알아오기
	@Override
	public int existDepCalendar(String dep_smcatgoname) {
		int m = sqlsession.selectOne("yunsu.existDepCalendar", dep_smcatgoname);
		return m;
	}
	
	// === 부서 캘린더에 부서캘린더 소분류 추가하기 ===
	@Override
	public int addDepCalendar(Map<String, String> paraMap) {
		int n = sqlsession.insert("yunsu.addDepCalendar", paraMap);
		return n;
	}

	@Override
	public List<Calendar_small_category_VO> showDepCalendar(String fk_department_id) {
		List<Calendar_small_category_VO> calendar_small_category_VO_DepList = sqlsession.selectList("yunsu.showDepCalendar", fk_department_id);  
		return calendar_small_category_VO_DepList;
	}

	@Override
	public List<Map<String, String>> scheduleListSearchExcelDownload(Map<String, String> paraMap) {
		List<Map<String,String>> excelList = sqlsession.selectList("yunsu.scheduleListSearchExcelDownload", paraMap);
		return excelList;
	}
	
	
	
}
