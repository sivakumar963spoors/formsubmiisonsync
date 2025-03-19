package com.effort.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.effort.dao.rollbackhandler.EmployeeCallBackHandler;
import com.effort.entity.Employee;
import com.effort.entity.Provisioning;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Log;
@Repository
public class EmployeeDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Employee getEmployee(String id) {
		Employee employee = null;
		try {
			employee = jdbcTemplate.queryForObject(Sqls.SELECT_EMPLOYEE_BY_ID, new Object[] { id },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		} catch (Exception e) {
			//StackTraceElement[] stackTrace = e.getStackTrace();
			//getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getEmployee method",e.toString(),stackTrace,0);
			e.printStackTrace();
		}
		return employee;
	}
	
	public void updateEmployeeDeviceStartAndStopWorkTimes(long empId, String startTime, String stopTime) {
		try {
			jdbcTemplate.update(Sqls.UPDATE_EMPLOYEE_DEVICE_START_STOP_WORK_TIMES,
					new Object[] { startTime, stopTime, empId });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public Employee getEmployeeBasicDetailsByEmpId(String empId) {
		Employee employee = new Employee();
		try {
			employee = jdbcTemplate.queryForObject(Sqls.SELECT_EMPLOYEE_BASIC_DETAILS_BY_ID, new Object[] { empId },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		} catch (Exception e) {
			//StackTraceElement[] stackTrace = e.getStackTrace();
			//getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getEmployeeBasicDetailsByEmpId method",e.toString(),stackTrace,0);
			Log.info(getClass(), " Got Exeption While getEmployeeBasicDetailsByEmpId ---> ActivityLocationService empId: "+empId);
			Log.info(getClass(), " Got Exeption While getEmployeeBasicDetailsByEmpId ---> ActivityLocationService empId: "+e);
			e.printStackTrace();
		}
		return employee;
	}
	
	public List<Employee> getAllEmployeesUnderWithLevel(long empId, Long type, boolean filterDisable) {
		String sql = Sqls.SELECT_ALL_EMPLOYEES_UNDER_WITH_LEVEL;

		if (type == null) {
			sql = sql.replace(":type", "");
		} else {
			sql = sql.replace(":type", " AND `empTypeId`=" + type + " ");
		}
		if (filterDisable) {
			sql = sql.replace(":status", "AND `Employees`.status=1  ");
		} else {
			sql = sql.replace(":status", "");
		}

		List<Employee> employees = jdbcTemplate.query(sql, new Object[] { empId },
				new BeanPropertyRowMapper<Employee>(Employee.class));
		return employees;
	}
	
	public List<Employee> getAllEmployeesIn(String empIds) {
		if (!Api.isEmptyString(empIds)) {
			List<Employee> employees = jdbcTemplate.query(Sqls.SELECT_ALL_EMPLOYEES_IN.replace(":ids", empIds),
					new Object[] {}, new BeanPropertyRowMapper<Employee>(Employee.class));
			return employees;
		} else {
			return new ArrayList<Employee>();
		}
	}
	
	public Map<Long, Short> getEmployeesAutoBlocked(String empIds) {
		String sql = Sqls.SELECT_EMPLOYEES_AUTO_BLOCKED;
		sql = sql.replace(":empIds", empIds);
		return jdbcTemplate.query(sql, new Object[]{}, (ResultSet rs) -> {
            Map<Long,Short> results = new HashMap<>();
            while (rs.next()) {
                results.put(rs.getLong("empId"), rs.getShort("blocked"));
            }
            return results;
        });

	}
	public List<String> getWebUserAuthorities(String userName) {
		List<String> authorities = jdbcTemplate.queryForList(Sqls.SELECT_WEB_USER_AUTHORITIES,
				new Object[] { userName }, String.class);
		return authorities;
	}

	public List<Employee> getAllEmployees(long companyId) {
		List<Employee> employees = jdbcTemplate.query(Sqls.SELECT_ALL_EMPLOYEES_BY_COMPANY, new Object[] { companyId },
				new BeanPropertyRowMapper<Employee>(Employee.class));
		return employees;
	}
	
	public Map<Long, Employee> getEmployeeMapIn(String ids) {
		EmployeeCallBackHandler employeeCallBackHandler = new EmployeeCallBackHandler();

		if (!Api.isEmptyString(ids)) {
			jdbcTemplate.query(Sqls.SELECT_EMPLOYEE_MAP_IN.replace(":ids", ids), employeeCallBackHandler);
		}

		return employeeCallBackHandler.getEmployeeMap();
	}
	public Provisioning getProvisioningByEmpId(long empId) {
		Provisioning provisioning = jdbcTemplate.queryForObject(Sqls.SELECT_PROVISIONING_BY_EMP_ID,
				new Object[] { empId }, new BeanPropertyRowMapper<Provisioning>(Provisioning.class));
		return provisioning;
	}
	public Employee getEmployeeBasicDetailsAndTimeZoneByEmpId(String empId) {
		Employee employee = null;
		try {
			employee = jdbcTemplate.queryForObject(Sqls.SELECT_EMPLOYEE_BASIC_DETAILS_AND_TIME_ZONE_BY_ID, new Object[] { empId },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		} catch (Exception e) {
			//StackTraceElement[] stackTrace = e.getStackTrace();
			//getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getEmployeeBasicDetailsAndTimeZoneByEmpId method",e.toString(),stackTrace,0);
			e.printStackTrace();
		}
		return employee;
	}
}
