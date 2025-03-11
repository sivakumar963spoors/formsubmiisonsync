package com.effort.util;

import java.util.List;

import com.effort.context.AppContext;
import com.effort.entity.EffortAccessSettings;
import com.effort.entity.Employee;
import com.effort.entity.WebUser;
import com.effort.manager.WebManager;

public class ContextUtils {

	
	private WebManager getWebManager() {
		WebManager webManager = AppContext.getApplicationContext().getBean(
				"webManager", WebManager.class);
		return webManager;
	}
	public List<Employee> getEmployeesUnderMe(boolean filterDisable, boolean addMe, Long type, WebUser webUser){
		List<Employee> employees = getWebManager().getEmployeesUnder(webUser, filterDisable, addMe, true, type);
		for (Employee employee : employees) {
			employee.setDirectAccess(true);
		}
		return employees;
	}
	
	public boolean hasPermission(String targetType, String permissionTypeString,EffortAccessSettings effortAccessSettings)
	{
		
		if(effortAccessSettings.getEmployee().getEmpTypeId() == Employee.TYPE_FIELD)
		{
			if(effortAccessSettings.getEmployeeAccessSettingsMap().containsKey(permissionTypeString) && effortAccessSettings.getEmployeeAccessSettingsMap().get(permissionTypeString))
			{
				return true;
			}
		}
		else if(effortAccessSettings.getEmployee().getEmpTypeId() == Employee.TYPE_BACK_OFFICE)
		{
			if(effortAccessSettings.getPermissionEmployeeGroupMap().containsKey(permissionTypeString))
			{
				return true;
			}
		}
		return false;
	}
	
}
