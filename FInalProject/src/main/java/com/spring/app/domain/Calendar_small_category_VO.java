package com.spring.app.domain;

public class Calendar_small_category_VO {

	private String smcatgono;     // 캘린더 소분류 번호
	private String fk_lgcatgono;  // 캘린더 대분류 번호
	private String smcatgoname;   // 캘린더 소분류 명
	private String fk_employee_id;     // 캘린더 소분류 작성자 사원번호
	private String fk_department_id; // 캘린더 소분류 작성자 부서
	
	
	


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


	public String getFk_employee_id() {
		return fk_employee_id;
	}


	public void setFk_employee_id(String fk_employee_id) {
		this.fk_employee_id = fk_employee_id;
	}


	public String getFk_department_id() {
		return fk_department_id;
	}


	public void setFk_department_id(String fk_department_id) {
		this.fk_department_id = fk_department_id;
	}
	
	
	
	
}
