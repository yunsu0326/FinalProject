package com.spring.app.domain;

public class ReservLargeCategoryVO {

	private String lgcatgono;    		// 자원 대분류 번호
	private String lgcatgoname; 	 	// 자원 대분류 명 
	private String lgcategcontent;  	// 자원 대분류 설명
	private String fk_employee_id; 			// 자원 설명 편집자
	
	public String getLgcatgono() {
		return lgcatgono;
	}
	
	public void setLgcatgono(String lgcatgono) {
		this.lgcatgono = lgcatgono;
	}
	
	public String getLgcatgoname() {
		return lgcatgoname;
	}
	
	public void setLgcatgoname(String lgcatgoname) {
		this.lgcatgoname = lgcatgoname;
	}

	public String getLgcategcontent() {
		return lgcategcontent;
	}

	public void setLgcategcontent(String lgcategcontent) {
		this.lgcategcontent = lgcategcontent;
	}

	public String getFk_employee_id() {
		return fk_employee_id;
	}

	public void setFk_employee_id(String fk_employee_id) {
		this.fk_employee_id = fk_employee_id;
	}
	
	
}
