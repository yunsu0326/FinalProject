package com.spring.app.domain;

public class TeamVO {
	
	private String team_id;
	private String team_name;
	private String t_manager_id;
	private String fk_department_id;
	
	
	
	public String getTeam_id() {
		return team_id;
	}


	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}


	public String getTeam_name() {
		return team_name;
	}


	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}


	public String getT_manager_id() {
		return t_manager_id;
	}


	public void setT_manager_id(String t_manager_id) {
		this.t_manager_id = t_manager_id;
	}


	public String getFk_department_id() {
		return fk_department_id;
	}


	public void setFk_department_id(String fk_department_id) {
		this.fk_department_id = fk_department_id;
	}
	
	

}
