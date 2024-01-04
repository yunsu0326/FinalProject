package com.spring.app.domain;

public class ReservationVO {

	private String reservationno;    	// 자원예약 번호
	private String startdate;     		// 시작일자
	private String enddate;       		// 종료일자
	private String realuser;       		// 실사용자
	private String fk_smcatgono;        // 예약 항목 번호
	private String fk_lgcatgono;        // 예약 대분류 번호
	private String fk_employee_id;         	// 예약자 사원번호
	private String reservdate;         	// 예약일자
	private String confirm;         	// 승인여부(0 미승인, 1 승인)
	private String status;         		// 취소 및 반납 여부(예약상태 0, 취소 1, 반납 2)
	private String return_time;         // 반납 시간
	
	
	public String getReservationno() {
		return reservationno;
	}
	public void setReservationno(String reservationno) {
		this.reservationno = reservationno;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getRealuser() {
		return realuser;
	}
	public void setRealuser(String realuser) {
		this.realuser = realuser;
	}
	public String getFk_smcatgono() {
		return fk_smcatgono;
	}
	public void setFk_smcatgono(String fk_smcatgono) {
		this.fk_smcatgono = fk_smcatgono;
	}
	public String getFk_lgcatgono() {
		return fk_lgcatgono;
	}
	public void setFk_lgcatgono(String fk_lgcatgono) {
		this.fk_lgcatgono = fk_lgcatgono;
	}
	public String getFk_employee_id() {
		return fk_employee_id;
	}
	public void setFk_employee_id(String fk_employee_id) {
		this.fk_employee_id = fk_employee_id;
	}
	public String getReservdate() {
		return reservdate;
	}
	public void setReservdate(String reservdate) {
		this.reservdate = reservdate;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReturn_time() {
		return return_time;
	}
	public void setReturn_time(String return_time) {
		this.return_time = return_time;
	}
	
}
