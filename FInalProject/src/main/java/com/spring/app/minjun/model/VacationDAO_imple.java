package com.spring.app.minjun.model;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spring.app.domain.VacationVO;
import com.spring.app.domain.Vacation_manageVO;

//==== #32. Repository(DAO) 선언 ====
//@Component
@Repository
public class VacationDAO_imple implements VacationDAO {

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
		List<String> imgfilenameList = sqlsession.selectList("minjun.getImgfilenameList");
		return imgfilenameList;
	}


	// 연차 신청시  tbl_vacation 테이블의 annual 컬럼 업데이트 하기
	@Override
	public int annual_insert(Map<String, String> paraMap) {
		
		int n=0;
		
		n = sqlsession.insert("minjun.annual_insert", paraMap);
		return n;
	}


	// 특정 회원의 휴가 정보 가져오기
	@Override
	public VacationVO vacation_select(String employee_id) {
		VacationVO vacationInfo = sqlsession.selectOne("minjun.vacation_select", employee_id);
		return vacationInfo;
	}
	
	// 대기중인 휴가 갯수 알아오기
	@Override
	public String total_count(String employee_id) {
		String total_count = sqlsession.selectOne("minjun.total_count", employee_id);
		return total_count;
	}
	

	// 회원들의 신청된 휴가 중 대기중인 회원 가져오기
	@Override
	public List<Vacation_manageVO> vacList(String employee_id) {
		List<Vacation_manageVO> vacList = sqlsession.selectList("minjun.vacList", employee_id);
		return vacList;
	}
	
	
	// 회원들의 신청된 휴가 중 반려된 회원 가져오기
	@Override
	public List<Vacation_manageVO> vacReturnList(String employee_id) {
		List<Vacation_manageVO> vacReturnList = sqlsession.selectList("minjun.vacReturnList", employee_id);
		return vacReturnList;
	}

	
	// 회원들의 신청된 휴가 중 승인된 회원 가져오기
	@Override
	public List<Vacation_manageVO> vacApprovedList(String employee_id) {
		List<Vacation_manageVO> vacApprovedList = sqlsession.selectList("minjun.vacApprovedList", employee_id);
		return vacApprovedList;
	}
	
		
	//////////////////////////////////////////////////////////////////////
	// 휴가 결재시 휴가관리테이블 업데이트 하기
	@Override
	public int vacManage_Update(Map<String, String[]> paraMap) {
		int n = sqlsession.update("minjun.vacManage_Update", paraMap);
		return n;
	}
	// 휴가 테이블의 annual 컬럼을 신청수만큼 빼기
	@Override
	public int vacUpdate_annual2(Map<String, String> paraMap2) {
		int n = sqlsession.update("minjun.vacUpdate_annual2", paraMap2);
		return n;
	}
	// 연차를 제외한 나머지 휴가는  plus 처리 해줌
	@Override
	public int vacUpdate_plus(Map<String, String> paraMap2) {
		int n = sqlsession.update("minjun.vacUpdate_plus", paraMap2);
		return n;
	}
	//////////////////////////////////////////////////////////////////////

	

	// 특정 사용자의 승인완료된 휴가 가져오기
	@Override
	public List<Vacation_manageVO> myApprovedList(String employee_id) {
		List<Vacation_manageVO> myApprovedList = sqlsession.selectList("minjun.myApprovedList", employee_id);
		return myApprovedList;
	}
	
	// 특정 사용자의 반려된 휴가 가져오기
	@Override
	public List<Vacation_manageVO> myReturnList(String employee_id) {
		List<Vacation_manageVO> myReturnList = sqlsession.selectList("minjun.myReturnList", employee_id);
		return myReturnList;
	}


	// 특정 회원의 신청한 휴가 중 대기중인 휴가 정보 가져오기
	@Override
	public Map<String, String> vacHoldList_one(String vacation_seq) {
		Map<String, String> vacHoldList_one = sqlsession.selectOne("minjun.vacHoldList_one", vacation_seq);
		System.out.println(vacation_seq);
		return vacHoldList_one;
	}


	// 휴가 반려시 반려테이블에 insert 하기
	@Override
	public int vacInsert_return(Map<String, String> paraMap) {
		int n = sqlsession.insert("minjun.vacInsert_return", paraMap);
		return n;
	}


	// 휴가 반려시 반려테이블에 update 하기
	@Override
	public int vacUpdate_return(Map<String, String> paraMap) {
		int n = sqlsession.insert("minjun.vacUpdate_return", paraMap);
		return n;
	}


	


	// 휴가 재신청 하기 위한 insert
	@Override
	public int vac_insert_insert(Map<String, String> paraMap) {
		int n = sqlsession.insert("minjun.vac_insert_insert", paraMap);
		return n;
	}


	// 특정 사용자의 승인완료된 휴가 가져오기
	@Override
	public List<Vacation_manageVO> myHoldList(String employee_id) {
		List<Vacation_manageVO> myHoldList = sqlsession.selectList("minjun.myHoldList", employee_id);
		return myHoldList;
	}


	// 대기중인 휴가 총 건수 알아오기
	@Override
	public int totalCount(String employee_id) {
		int totalCount = sqlsession.selectOne("minjun.totalCount", employee_id);
		return totalCount;
	}


	// 대기중인휴가의 totalPage 수 알아오기
	@Override
	public int getCommentTotalPage(Map<String, String> paraMap) {
		int totalPage = sqlsession.selectOne("minjun.getCommentTotalPage", paraMap);
		return totalPage;
	}


	// 승인 대기중인 휴가 삭제하기
	@Override
	public void seq_delete(String vacation_seq) {
		sqlsession.delete("minjun.seq_delete", vacation_seq);
	}


	

	

	
	


	


	

	



	


	

	
	
	
	
	

	




	


	
	
	
	
	
}
