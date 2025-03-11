package com.effort.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class PermissionSet {
	
	
	private List<Long> employeeGroupIds = new ArrayList<Long>();

	@JsonProperty(access = Access.WRITE_ONLY)
	private long permissionSetId;
	public long getPermissionSetId() {
		return permissionSetId;
	}
	
	public List<Long> getEmployeeGroupIds() {
		return employeeGroupIds;
	}
	public void setEmployeeGroupIds(List<Long> employeeGroupIds) {
		this.employeeGroupIds = employeeGroupIds;
	}
}
