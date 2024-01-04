package com.spring.app.yunsu.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.app.domain.*;
import com.spring.app.yunsu.model.*;

@Service
public class ScheduleService_imple implements ScheduleService {

	@Autowired
	private ScheduleDAO dao;

	
	// 사내 캘린더에 캘린더 소분류 추가하기
	@Override
	public int addComCalendar(Map<String, String> paraMap) throws Throwable {
		
		int n=0;
		String com_smcatgoname = paraMap.get("com_smcatgoname");
		
		// 사내 캘린더에 캘린더 소분류 명 존재 여부 알아오기
		int m = dao.existComCalendar(com_smcatgoname);
		
		if(m==0) {
			n = dao.addComCalendar(paraMap);
		}
		
		return n;
	}

	
	// 내 캘린더에 캘린더 소분류 추가하기
	@Override
	public int addMyCalendar(Map<String, String> paraMap) throws Throwable {
		
		int n=0;
		
		// 내 캘린더에 캘린더 소분류 명 존재 여부 알아오기
		int m = dao.existMyCalendar(paraMap);
		
		if(m==0) {
			n = dao.addMyCalendar(paraMap);
		}
		
		return n;
	}


	// 사내 캘린더에서 사내캘린더 소분류  보여주기 
	@Override
	public List<Calendar_small_category_VO> showCompanyCalendar() {
		List<Calendar_small_category_VO> calendar_small_category_VO_CompanyList = dao.showCompanyCalendar(); 
		return calendar_small_category_VO_CompanyList;
	}


	// 내 캘린더에서 내캘린더 소분류  보여주기
	@Override
	public List<Calendar_small_category_VO> showMyCalendar(String fk_employee_id) {
		List<Calendar_small_category_VO> calendar_small_category_VO_MyList = dao.showMyCalendar(fk_employee_id); 
		return calendar_small_category_VO_MyList;
	}


	// 일정 등록시 내캘린더,사내캘린더 선택에 따른 서브캘린더 종류를 알아오기 
	@Override
	public List<Calendar_small_category_VO> selectSmallCategory(Map<String, String> paraMap) {
		List<Calendar_small_category_VO> small_category_VOList = dao.selectSmallCategory(paraMap);
		return small_category_VOList;
	}


	// 공유자를 찾기 위한 특정글자가 들어간 회원명단 불러오기 
	@Override
	public List<EmployeesVO> searchJoinUserList(String joinUserName) {
		List<EmployeesVO> joinUserList = dao.searchJoinUserList(joinUserName);
		return joinUserList;
	}


	// 일정 등록하기
	@Override
	public int registerSchedule_end(Map<String, String> paraMap) throws Throwable {
		int n = dao.registerSchedule_end(paraMap);
		return n;
	}


	// 등록된 일정 가져오기
	@Override
	public List<Calendar_schedule_VO> selectSchedule(Map<String, String> paraMap) {
		List<Calendar_schedule_VO> scheduleList = dao.selectSchedule(paraMap);
		return scheduleList;
	}


	// 일정 상세 보기 
	@Override
	public Map<String,String> detailSchedule(String scheduleno) {
		Map<String,String> map = dao.detailSchedule(scheduleno);
		return map;
	}


	// 일정삭제하기 
	@Override
	public int deleteSchedule(String scheduleno) throws Throwable {
		int n = dao.deleteSchedule(scheduleno);
		return n;
	}


	// 일정수정하기
	@Override
	public int editSchedule_end(Calendar_schedule_VO svo) throws Throwable {
		int n = dao.editSchedule_end(svo);
		return n;
	}


	// (사내캘린더 또는 내캘린더)속의  소분류 카테고리인 서브캘린더 삭제하기 
	@Override
	public int deleteSubCalendar(String smcatgono) throws Throwable {
		int n = dao.deleteSubCalendar(smcatgono);
		return n;
	}


	// (사내캘린더 또는 내캘린더)속의 소분류 카테고리인 서브캘린더 수정하기 
	@Override
	public int editCalendar(Map<String, String> paraMap) throws Throwable {
		int n = 0;
		
		int m = dao.existsCalendar(paraMap); 
		// 수정된 (사내캘린더 또는 내캘린더)속의 소분류 카테고리명이 이미 해당 사용자가 만든 소분류 카테고리명으로 존재하는지 유무 알아오기  
		
		if(m==0) {
			n = dao.editCalendar(paraMap);	
		}
		
		return n;
	}


	// 총 일정 검색 건수(totalCount)
	@Override
	public int getTotalCount(Map<String, String> paraMap) {
		int n = dao.getTotalCount(paraMap);
		return n;
	}


	// 페이징 처리한 캘린더 가져오기(검색어가 없다라도 날짜범위 검색은 항시 포함된 것임)
	@Override
	public List<Map<String,String>> scheduleListSearchWithPaging(Map<String, String> paraMap) {
		List<Map<String,String>> scheduleList = dao.scheduleListSearchWithPaging(paraMap);
		return scheduleList;
	}

	// === 부서 캘린더에 부서캘린더 소분류 추가하기 ===
	@Override
	public int addDepCalendar(Map<String, String> paraMap) {
		
		int n=0;
		String dep_smcatgoname = paraMap.get("dep_smcatgoname");
		
		// 부서 캘린더에 캘린더 소분류 명 존재 여부 알아오기
		int m = dao.existDepCalendar(dep_smcatgoname);
		
		if(m==0) {
			n = dao.addDepCalendar(paraMap);
		}
		
		return n;
	}

	// === 부서 캘린더에서 부서 캘린더 소분류  보여주기 ===
	@Override
	public List<Calendar_small_category_VO> showDepCalendar(String fk_department_id) {
		List<Calendar_small_category_VO> calendar_small_category_VO_DepList = dao.showDepCalendar(fk_department_id); 
		return calendar_small_category_VO_DepList;
		
	}

	
	// 엑셀 다운로드를 위한 검색 목록 알아오기
	@Override
	public List<Map<String, String>> scheduleListSearchExcelDownload(Map<String, String> paraMap) {
		List<Map<String,String>> excelList = dao.scheduleListSearchExcelDownload(paraMap);
		return excelList;
	}

	// 엑셀 다운받기
	@Override
	public void downloadSearchExcelFile(List<Map<String, String>> excelList, Model model) {
		
		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		// 시트생성
		SXSSFSheet sheet = workbook.createSheet("일정검색결과");
		
		// 시트 열 너비 설정
		sheet.setColumnWidth(0, 10000);
		sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 10000);
        
        
        // 행의 위치를 나타내는 변수
        int rowLocation = 0;
        
        
        //////////////////////////////////////////////////
        
        // CellStyle 정렬하기(Alignment)
        // CellStyle 객체를 생성하여 Alignment 세팅하는 메소드를 호출해서 인자값을 넣어준다.
        // 아래는 HorizontalAlignment(가로)와 VerticalAlignment(세로)를 모두 가운데 정렬 시켰다.
        CellStyle mergeRowStyle = workbook.createCellStyle();
        mergeRowStyle.setAlignment(HorizontalAlignment.CENTER);
        mergeRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // import org.apache.poi.ss.usermodel.VerticalAlignment 으로 해야함.
        
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        
        
        // CellStyle 배경색(ForegroundColor)만들기
        // setFillForegroundColor 메소드에 IndexedColors Enum인자를 사용한다.
        // setFillPattern은 해당 색을 어떤 패턴으로 입힐지를 정한다.
        mergeRowStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex()); // IndexedColors.DARK_BLUE.getIndex() 는 색상(남색)의 인덱스값을 리턴시켜준다.
        mergeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());// IndexedColors.LIGHT_YELLOW.getIndex() 는 연한노랑의 인덱스값을 리턴시켜준다.
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        
        
        
        
        // Cell 폰트(Font) 설정하기
        // 폰트 적용을 위해 POI 라이브러리의 Font 객체를 생성해준다.
        // 해당 객체의 세터를 사용해 폰트를 설정해준다. 대표적으로 글씨체, 크기, 색상, 굵기만 설정한다.
        // 이후 CellStyle의 setFont 메소드를 사용해 인자로 폰트를 넣어준다.
	    Font mergeRowFont = workbook.createFont(); // import org.apache.poi.ss.usermodel.Font; 으로 한다.
	    mergeRowFont.setFontName("나눔고딕");
	    mergeRowFont.setFontHeight((short)500);
	    mergeRowFont.setColor(IndexedColors.WHITE.getIndex());
	    mergeRowFont.setBold(true);
	    
	    mergeRowStyle.setFont(mergeRowFont);
        
	    
	    // CellStyle 테두리 Border
        // 테두리는 각 셀마다 상하좌우 모두 설정해준다.
        // setBorderTop, Bottom, Left, Right 메소드와 인자로 POI라이브러리의 BorderStyle 인자를 넣어서 적용한다.
	    headerStyle.setBorderTop(BorderStyle.THICK);
	    headerStyle.setBorderBottom(BorderStyle.THICK);
	    headerStyle.setBorderLeft(BorderStyle.THIN);
	    headerStyle.setBorderRight(BorderStyle.THIN);
	    
	    // Cell Merge 셀 병합시키기
        /* 셀병합은 시트의 addMergeRegion 메소드에 CellRangeAddress 객체를 인자로 하여 병합시킨다.
           CellRangeAddress 생성자의 인자로(시작 행, 끝 행, 시작 열, 끝 열) 순서대로 넣어서 병합시킬 범위를 정한다. 배열처럼 시작은 0부터이다.  
        */
        // 병합할 행 만들기
	    Row mergeRow = sheet.createRow(rowLocation);  // 엑셀에서 행의 시작은 0 부터 시작한다. 
        
	    // 병합할 행에 "우리회사 사원정보" 로 셀을 만들어 셀에 스타일을 주기
	    for(int i=0; i<4; i++) {
	    	Cell cell = mergeRow.createCell(i);
	    	cell.setCellStyle(mergeRowStyle);
	    	cell.setCellValue("일정 검색결과");
	    }// end of for-------------
	    
	    // 셀 병합하기
	    sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0, 4)); // 시작 행, 끝 행, 시작 열, 끝 열 
	    
	    // 헤더 행 생성
        Row headerRow = sheet.createRow(++rowLocation); // 엑셀에서 행의 시작은 0 부터 시작한다.
                                                        // ++rowLocation는 전위연산자임.
        // 해당 행의 첫번째 열 셀 생성
        Cell headerCell = headerRow.createCell(0); // 엑셀에서 열의 시작은 0 부터 시작한다.
        headerCell.setCellValue("일자");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 두번째 열 셀 생성
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("캘린더종류");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 세번째 열 셀 생성
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("등록자");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 네번째 열 셀 생성
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("제목");
        headerCell.setCellStyle(headerStyle);
        
        // 해당 행의 다섯번째 열 셀 생성
        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("내용");
        headerCell.setCellStyle(headerStyle);
        
       
	    //////////////////////////////////////////////////
        
        
        // === HR 사원정보 내용에 해당하는 행 및 셀 생성하기 === //
        Row bodyRow = null;
        Cell bodyCell = null;
        
       
        
        for(int i=0; i<excelList.size(); i++) {
        	
        	Map<String, String> excelMap = excelList.get(i);
        	
        	// 행생성 
        	bodyRow = sheet.createRow(i + (rowLocation+1));
        	
        	// 일정표시
        	bodyCell = bodyRow.createCell(0);
        	bodyCell.setCellValue(excelMap.get("STARTDATE")+ " - "+ excelMap.get("ENDDATE"));
        	
        	// 캘린더 종류
        	bodyCell = bodyRow.createCell(1);
        	bodyCell.setCellValue(excelMap.get("LGCATGONAME")+" - "+excelMap.get("SMCATGONAME"));
        	
        	// 등록자
        	bodyCell = bodyRow.createCell(2);
        	bodyCell.setCellValue(excelMap.get("NAME"));
        	
        	// 제목
        	bodyCell = bodyRow.createCell(3);
        	bodyCell.setCellValue(excelMap.get("SUBJECT"));
        	
        	// 데이터 입사일자 표시
        	bodyCell = bodyRow.createCell(4);
        	bodyCell.setCellValue(excelMap.get("CONTENT"));
        	
        	
        	
    
        	
        }// end of for--------------
        
        model.addAttribute("locale", Locale.KOREA);
        model.addAttribute("workbook",workbook);
        model.addAttribute("workbookName", "일정 검색결과");
		
	}

	
	
	

	
	
	
	
	
	
}
