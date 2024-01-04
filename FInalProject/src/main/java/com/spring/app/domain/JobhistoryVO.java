package com.spring.app.domain;

public class JobhistoryVO {
	
	private String job_history_no;    // 퇴직번호
	private String fk_employee_id;    // 사원번호
	private String start_date; 	   // 시작날짜
	private String end_date;	   // 퇴직날짜
	private String fk_job_id;  	   // 직급번호
	private String fk_department_id;  // 부서번호
	private String fk_team_id;        // 팀번호
	
	private JobhistoryVO() {}

	public String getJob_history_no() {
		return job_history_no;
	}

	public void setJob_history_no(String job_history_no) {
		this.job_history_no = job_history_no;
	}

	public String getFk_employee_id() {
		return fk_employee_id;
	}

	public void setFk_employee_id(String fk_employee_id) {
		this.fk_employee_id = fk_employee_id;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getFk_job_id() {
		return fk_job_id;
	}

	public void setFk_job_id(String fk_job_id) {
		this.fk_job_id = fk_job_id;
	}

	public String getFk_department_id() {
		return fk_department_id;
	}

	public void setFk_department_id(String fk_department_id) {
		this.fk_department_id = fk_department_id;
	}

	public String getFk_team_id() {
		return fk_team_id;
	}

	public void setFk_team_id(String fk_team_id) {
		this.fk_team_id = fk_team_id;
	};
	
	
	
}
