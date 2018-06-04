package com.blackops.model;

import java.io.Serializable;
import java.util.ArrayList;

public class District implements Serializable {
	
	String supervisor;
	String districtName;
	
	ArrayList<Assignment> assignmentList;
	
	public District() {
		supervisor = "None";
		districtName = "None";
		assignmentList = new ArrayList<Assignment>();
	}
	
	public void addAssignment(Assignment a) {
		a.setParent(this);
		assignmentList.add(a);		
	}
	
	public void removeAssignment(Assignment a) {
		assignmentList.remove(a);
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public ArrayList<Assignment> getAssignmentList() {
		return assignmentList;
	}

	public void setAssignmentList(ArrayList<Assignment> assignmentList) {
		this.assignmentList = assignmentList;
	}
	
	

}
