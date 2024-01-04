package com.spring.app.kimkm.domain;

public class EmployeeVO {

	private int employee_no;      	  // 사원번호
	private String name;          	  // 사원명
	private String userid;			  // 아이디
	private String pwd;           	  // 비밀번호
	private String email;         	  // 이메일
	private String phone;         	  // 핸드폰
	private String postcode;      	  //우편번호
	private String address;       	  //주소
	private String detailaddress; 	  // 상세주소
	private String extraaddress; 	  // 참고항목
	private int jubun;             	  //주민번호
	private String hire_date;    	  // 입사일자
	private int salary;           	  // 월급
	private int commission_pct;  	  // 수당
	private String department_no; 	  // 부서번호
	private String manager_id;   	  //부서장번호
	private int status;           	  // 근무현황   1: 퇴근 / 2:근무중 / 3:출장
	private int idle;            	  // 휴면유무      0 : 활동중  /  1 : 휴면중 
	private int gradelevel;       	  // 사원등급  1: 일반 / 5: 중간관리자 / 10:총관리자
	private String lastpwdchangedate; // 비밀번호 마지막변경 날짜
	
	
	public EmployeeVO() {}
	
	// 회원가입시 기본 생성자
	public EmployeeVO(int employee_no, String name, String userid, String pwd, String email, String phone, String postcode,
			String address, String detailaddress, String extraaddress, int jubun, String hire_date, int salary,
			int commission_pct, String department_no, String manager_id, int status, int idle, int gradelevel,
			String lastpwdchangedate) {
		this.employee_no = employee_no;
		this.name = name;
		this.userid = userid;
		this.pwd = pwd;
		this.email = email;
		this.phone = phone;
		this.postcode = postcode;
		this.address = address;
		this.detailaddress = detailaddress;
		this.extraaddress = extraaddress;
		this.jubun = jubun;
		this.hire_date = hire_date;
		this.salary = salary;
		this.commission_pct = commission_pct;
		this.department_no = department_no;
		this.manager_id = manager_id;
		this.status = status;
		this.idle = idle;
		this.gradelevel = gradelevel;
		this.lastpwdchangedate = lastpwdchangedate;
	}
	

	public int getEmployee_no() {
		return employee_no;
	}

	public void setEmployee_no(int employee_no) {
		this.employee_no = employee_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetailaddress() {
		return detailaddress;
	}

	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}

	public String getExtraaddress() {
		return extraaddress;
	}

	public void setExtraaddress(String extraaddress) {
		this.extraaddress = extraaddress;
	}

	public int getJubun() {
		return jubun;
	}

	public void setJubun(int jubun) {
		this.jubun = jubun;
	}

	public String getHire_date() {
		return hire_date;
	}

	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getCommission_pct() {
		return commission_pct;
	}

	public void setCommission_pct(int commission_pct) {
		this.commission_pct = commission_pct;
	}

	public String getDepartment_no() {
		return department_no;
	}

	public void setDepartment_no(String department_no) {
		this.department_no = department_no;
	}

	public String getManager_id() {
		return manager_id;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIdle() {
		return idle;
	}

	public void setIdle(int idle) {
		this.idle = idle;
	}

	public int getGradelevel() {
		return gradelevel;
	}

	public void setGradelevel(int gradelevel) {
		this.gradelevel = gradelevel;
	}

	public String getLastpwdchangedate() {
		return lastpwdchangedate;
	}

	public void setLastpwdchangedate(String lastpwdchangedate) {
		this.lastpwdchangedate = lastpwdchangedate;
	}

	
	
	
	
	
	
}
