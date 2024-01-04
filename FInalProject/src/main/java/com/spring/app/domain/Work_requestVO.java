package com.spring.app.domain;

// === 휴가 관련 VO 생성 === // 

public class Work_requestVO {
	private String fk_employee_id;    		// 사원번호
	private String work_request_seq;        // 근태신청번호
	private String work_request_type;	  	// 근태신청종류 1=외근 / 2=출장 / 3=재택
	private String work_request_start_date; // 근무일자
	private String work_request_start_time;	// 시작시간
	private String work_request_end_time; 	// 종료시간
	private String work_request_place;		// 장소
	private String work_request_reason;	  	// 사유
	private String work_request_date;	  	// 신청일자
	
	public String getFk_employee_id() {
		return fk_employee_id;
	}
	public void setFk_employee_id(String fk_employee_id) {
		this.fk_employee_id = fk_employee_id;
	}
	
	public String getWork_request_seq() {
		return work_request_seq;
	}
	public void setWork_request_seq(String work_request_seq) {
		this.work_request_seq = work_request_seq;
	}
	
	public String getWork_request_type() {
		return work_request_type;
	}
	public void setWork_request_type(String work_request_type) {
		this.work_request_type = work_request_type;
	}
	
	public String getWork_request_start_date() {
		return work_request_start_date;
	}
	public void setWork_request_start_date(String work_request_start_date) {
		this.work_request_start_date = work_request_start_date;
	}
	
	public String getWork_request_start_time() {
		return work_request_start_time;
	}
	public void setWork_request_start_time(String work_request_start_time) {
		this.work_request_start_time = work_request_start_time;
	}
	
	public String getWork_request_end_time() {
		return work_request_end_time;
	}
	public void setWork_request_end_time(String work_request_end_time) {
		this.work_request_end_time = work_request_end_time;
	}
	
	public String getWork_request_place() {
		return work_request_place;
	}
	public void setWork_request_place(String work_request_place) {
		this.work_request_place = work_request_place;
	}
	
	public String getWork_request_reason() {
		return work_request_reason;
	}
	public void setWork_request_reason(String work_request_reason) {
		this.work_request_reason = work_request_reason;
	}
	
	public String getWork_request_date() {
		return work_request_date;
	}
	public void setWork_request_date(String work_request_date) {
		this.work_request_date = work_request_date;
	}
	
	
}
