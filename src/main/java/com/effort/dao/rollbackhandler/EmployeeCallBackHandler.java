package com.effort.dao.rollbackhandler;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowCallbackHandler;

import com.effort.entity.Employee;




public class EmployeeCallBackHandler implements RowCallbackHandler{
	
	private Map<Long, Employee> employeeMap = null;
	
	public EmployeeCallBackHandler(){
		employeeMap = new HashMap<Long, Employee>();
	}

	@Override
	public void processRow(ResultSet rs) throws SQLException {
		Long id = rs.getLong("empId");
		Employee employee = new Employee();
		employee.setCalendarId(rs.getLong("calendarId"));
		employee.setCompanyId(rs.getInt("companyId"));
		employee.setCreateTime(rs.getTimestamp("createTime"));
		employee.setEmpAddressArea(rs.getString("empAddressArea"));
		employee.setEmpAddressCity(rs.getString("empAddressCity"));
		employee.setEmpAddressPincode(rs.getString("empAddressPincode"));
		employee.setEmpAddressStreet(rs.getString("empAddressStreet"));
		employee.setEmpEmail(rs.getString("empEmail"));
		employee.setEmpFirstName(rs.getString("empFirstName"));
		employee.setEmpId(id);
		employee.setEmpLastName(rs.getString("empLastName"));
		employee.setEmpNo(rs.getString("empNo"));
		employee.setEmpPhone(rs.getString("empPhone"));
		employee.setImei(rs.getString("imei")); 
		employee.setHomeLat(rs.getString("homeLat")); 
		employee.setHomeLong(rs.getString("homeLong")); 
		employee.setManagerId(rs.getLong("managerId"));
		employee.setModifyTime(rs.getTimestamp("modifyTime"));
		employee.setProvisioning(rs.getBoolean("provisioning"));
		
		employeeMap.put(id, employee);
	}

	public Map<Long, Employee> getEmployeeMap() {
		return employeeMap;
	}


}
