package com.effort.dao.rollbackhandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;


public class PermissionSetsEmployeeGroupCallBackHandler implements RowCallbackHandler{
	
	private Map<Long, List<Long>> permissionSetsEmployeeGroupMap = null;
	private Map<Long, List<Long>> employeeGroupPermissionSetsMap = null;
	
	public PermissionSetsEmployeeGroupCallBackHandler(){
		permissionSetsEmployeeGroupMap = new HashMap<Long, List<Long>>();
		employeeGroupPermissionSetsMap = new HashMap<Long, List<Long>>();
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		Long id = rs.getLong("permissionSetId");
		Long employeeGroupId = rs.getLong("employeeGroupId");
		
		List<Long> employeeGroupIds = permissionSetsEmployeeGroupMap.get(id);
		if(employeeGroupIds == null){
			employeeGroupIds = new ArrayList<Long>();
			permissionSetsEmployeeGroupMap.put(id, employeeGroupIds);
		}
		employeeGroupIds.add(employeeGroupId);
		
		List<Long> permissionSets = employeeGroupPermissionSetsMap.get(employeeGroupId);
		if(permissionSets == null){
			permissionSets = new ArrayList<Long>();
			employeeGroupPermissionSetsMap.put(employeeGroupId, permissionSets);
		}
		permissionSets.add(id);
		
	}

	public Map<Long, List<Long>> getPermissionSetsEmployeeGroupMap() {
		return permissionSetsEmployeeGroupMap;
	}

	public Map<Long, List<Long>> getEmployeeGroupPermissionSetsMap() {
		return employeeGroupPermissionSetsMap;
	}
}