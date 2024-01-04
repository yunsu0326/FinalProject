package com.spring.app.domain;

public class ReservSmallCategoryVO {

	private String smcatgono;     // 자원 항목 번호
	private String fk_lgcatgono;  // 자원 대분류 번호
	private String smcatgoname;   // 자원 항목명
	private String smcatgocontent;   // 자원 항목 설명
	private String fk_employee_id;     // 자원 항목 작성자 유저아이디
	private String sc_status; 	// 자원 상태  -- 0은 이용 불가능 1 은 이용 가능
	
	public String getSmcatgono() {
		return smcatgono;
	}
	
	public void setSmcatgono(String smcatgono) {
		this.smcatgono = smcatgono;
	}
	
	public String getFk_lgcatgono() {
		return fk_lgcatgono;
	}
	
	public void setFk_lgcatgono(String fk_lgcatgono) {
		this.fk_lgcatgono = fk_lgcatgono;
	}
	
	public String getSmcatgoname() {
		return smcatgoname;
	}
	
	public void setSmcatgoname(String smcatgoname) {
		this.smcatgoname = smcatgoname;
	}
	
	public String getSmcatgocontent() {
		return smcatgocontent;
	}

	public void setSmcatgocontent(String smcatgocontent) {
		this.smcatgocontent = smcatgocontent;
	}

	public String getFk_employee_id() {
		return fk_employee_id;
	}
	
	public void setFk_employee_id(String fk_employee_id) {
		this.fk_employee_id = fk_employee_id;
	}
	
	public String getSc_status() {
		return sc_status;
	}

	public void setSc_status(String sc_status) {
		this.sc_status = sc_status;
	}
}
