package com.effort.entity;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.effort.entity.WebUser;


public class EffortAccessSettings  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final int ACCESS_ALL = 0;
	public static final int ACCESS_UNDER_ME = 1;
	public static final int ACCESS_BY_PERMISSION = 2;
	
	private WebUser webUser;

	
	private Map<String, List<Long>> permissionEmployeeGroupMap = new HashMap<String, List<Long>>();
	private Map<Long, List<String>> employeeGroupPermissionMap = new HashMap<Long, List<String>>();
	private Map<String, Boolean> employeeAccessSettingsMap = new HashMap<String, Boolean>();
	
	private Employee employee;
	
	private boolean isAuthenticated = false;
	
	private List<String> webUserAuthorities;
	private List<Long> employeeGroupIdsOfEmployee;
	
	public WebUser getWebUser() {
		return webUser;
	}

	public void setWebUser(WebUser webUser) {
		this.webUser = webUser;
	}
	
	public Map<String, List<Long>> getPermissionEmployeeGroupMap() {
		return permissionEmployeeGroupMap;
	}

	public void setPermissionEmployeeGroupMap(
			Map<String, List<Long>> permissionEmployeeGroupMap) {
		this.permissionEmployeeGroupMap = permissionEmployeeGroupMap;
	}

	public Map<Long, List<String>> getEmployeeGroupPermissionMap() {
		return employeeGroupPermissionMap;
	}

	public void setEmployeeGroupPermissionMap(
			Map<Long, List<String>> employeeGroupPermissionMap) {
		this.employeeGroupPermissionMap = employeeGroupPermissionMap;
	}

	public Map<String, Boolean> getEmployeeAccessSettingsMap() {
		return employeeAccessSettingsMap;
	}

	public void setEmployeeAccessSettingsMap(
			Map<String, Boolean> employeeAccessSettingsMap) {
		this.employeeAccessSettingsMap = employeeAccessSettingsMap;
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public void init(WebUser webUser, List<PermissionSet> permissionSets, EmployeeAccessSettings employeeAccessSettings){
		this.webUser = webUser;
		this.employee = getEmployee();
		this.isAuthenticated = true;
		
		if(permissionSets != null){
			for (PermissionSet permissionSet : permissionSets) {
				processPermissionSet(permissionSet);
			}
		}
		
		if(employeeAccessSettings != null){
			processEmployeeAccessSettings(employeeAccessSettings);
		}
	}
	
	public List<Long> getEmployeeGroupIds(String permission){
		List<Long> employeeGroupIds = permissionEmployeeGroupMap.get(permission);
		return employeeGroupIds;
	}
	
	public List<Long> getAllEmployeeGroupIds(){
		List<Long> employeeGroupIds = new ArrayList<Long>(employeeGroupPermissionMap.keySet());
		return employeeGroupIds;
	}
	
	public List<String> getAllEmployeePermissions(){
		List<String> permissions = new ArrayList<String>(employeeAccessSettingsMap.keySet());
		return permissions;
	}
	
	public List<String> getEmployeeGroupPermissions(long employeeGroupId){
		List<String> permissions = employeeGroupPermissionMap.get(employeeGroupId);
		return permissions;
	}
	
	public List<String> getAllEmployeeGroupPermissions(){
		List<String> permissions = new ArrayList<String>(permissionEmployeeGroupMap.keySet());
		return permissions;
	}
	

	
	public void putEmployeeGroupPermission(String permission, List<Long> employeeGroupIds){
		if(permission != null){
//			permissionEmployeeGroupMap.put(permission, employeeGroupIds);
			if(employeeGroupIds != null){
				List<Long> employeeGroupIdsPrev = permissionEmployeeGroupMap.get(permission);
				if(employeeGroupIdsPrev == null){
					employeeGroupIdsPrev = new ArrayList<Long>();
					permissionEmployeeGroupMap.put(permission, employeeGroupIdsPrev);
				}
				employeeGroupIdsPrev.removeAll(employeeGroupIds);
				employeeGroupIdsPrev.addAll(employeeGroupIds);
				
				for (Long employeeGroupId : employeeGroupIds) {
					List<String> permissions = employeeGroupPermissionMap.get(employeeGroupId);
					if(permissions == null){
						permissions = new ArrayList<String>();
						employeeGroupPermissionMap.put(employeeGroupId, permissions);
					}
					permissions.add(permission);
				}
			}
		}
	}
	
	public void putEmployeeGroupPermission(String permission, long employeeGroupId){
		if(permission != null){
			List<Long> employeeGroupIds = permissionEmployeeGroupMap.get(permission);
			if(employeeGroupIds == null){
				employeeGroupIds = new ArrayList<Long>();
				permissionEmployeeGroupMap.put(permission, employeeGroupIds);
			}
			employeeGroupIds.add(employeeGroupId);
			
			List<String> permissions = employeeGroupPermissionMap.get(employeeGroupId);
			if(permissions == null){
				permissions = new ArrayList<String>();
				employeeGroupPermissionMap.put(employeeGroupId, permissions);
			}
			permissions.add(permission);
		}
	}
	
	public void putEmployeePermission(String permission, boolean access){
		if(permission != null){
			employeeAccessSettingsMap.put(permission, access);
		}
	}

	private void processPermissionSet(PermissionSet permissionSet){
		if (permissionSet.isViewJob()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_JOB_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isModifyJob()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_JOB_WRITE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isAddJob()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_JOB_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isDeleteJob()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_JOB_DELETE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewForm()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_FORM_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isModifyForm()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_FORM_WRITE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isAddForm()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_FORM_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isDeleteForm()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_FORM_DELETE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewLeave()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_LEAVE_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isModifyLeave()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_LEAVE_WRITE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isAddLeave()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_LEAVE_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isDeleteLeave()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_LEAVE_DELETE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewClaim()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_CLAIM_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isModifyClaim()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_CLAIM_WRITE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isAddClaim()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_CLAIM_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isDeleteClaim()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_CLAIM_DELETE).getFieldName(), permissionSet.getEmployeeGroupIds());
		}
		if (permissionSet.isAddRoute()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_ROUTE_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isAddRoutePlan()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_ROUTE_PLAN_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewRoute()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_ROUTE_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isDeleteRoute()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_ROUTE_DELETE).getFieldName(), permissionSet.getEmployeeGroupIds());
		}
		if (permissionSet.isViewReport()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_REPORT_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewMap()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_MAP_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewEmployee()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_EMPLOYEE_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isModifyEmployee()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_EMPLOYEE_WRITE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewFormTemplate()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_FORM_TEMPLATE_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isModifyFormTemplate()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_FORM_TEMPLATE_WRITE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewCustomer()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_CUSTOMER_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isModifyCustomer()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_CUSTOMER_WRITE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isAddCustomer()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_CUSTOMER_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isDeleteCustomer()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_CUSTOMER_DELETE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewJobInvitation()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_JOB_INVITATION_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isModifyJobInvitation()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_JOB_INVITATION_WRITE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isAddJobInvitation()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_JOB_INVITATION_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isDeleteJobInvitation()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_JOB_INVITATION_DELETE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewHoliday()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_CALENDAR_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isManageCalendar()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_CALENDAR_MANAGE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewInvoice()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_INVOICE_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isPayInvoice()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_INVOICE_PAY).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isManageAreaGroup()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_AREA_GROUP_MANAGE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isManageTypesStates()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_TYPES_STAGES_MANAGE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isManageCustomerTypes()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_CUSTOMER_TYPES_MANAGE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isManageCompanySettings()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_COMPANY_SETTINGS_MANAGE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isViewEmployeeGroup()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_GROUP_READ).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isModifyEmployeeGroup()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_GROUP_WRITE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isAddEmployeeGroup()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_GROUP_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isDeleteEmployeeGroup()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_GROUP_DELETE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isAddEmployee()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isDeleteEmployee()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_DELETE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isAddFormTemplate()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_FORM_TEMPLATE_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		} 
		if (permissionSet.isDeleteFormTemplate()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_FORM_TEMPLATE_DELETE).getFieldName(), permissionSet.getEmployeeGroupIds());
		}
		if (permissionSet.isManageSecurity()) {
			putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_SECURITY_MANAGE).getFieldName(), permissionSet.isManageSecurity());
		}
		if (permissionSet.isManageArticle()) {
			putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_ARTICLE_MANAGE).getFieldName(), permissionSet.isManageArticle());
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.COMP_EMP_ARTICLE_MANAGE).getFieldName(), permissionSet.getEmployeeGroupIds());
		}
		if (permissionSet.isViewAdmin()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_VIEW_ADMIN).getFieldName(), permissionSet.getEmployeeGroupIds());
		}
		if (permissionSet.isViewCustomEntity()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_CUSTOM_ENTITY_VIEW).getFieldName(), permissionSet.getEmployeeGroupIds());
		}
		if (permissionSet.isAddCustomEntity()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_CUSTOM_ENTITY_CREATE).getFieldName(), permissionSet.getEmployeeGroupIds());
		}
		if (permissionSet.isModifyCustomEntity()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_CUSTOM_ENTITY_MODIFY).getFieldName(), permissionSet.getEmployeeGroupIds());
		}
		if (permissionSet.isDeleteCustomEntity()) {
			putEmployeeGroupPermission(((CustomPermission)CustomPermission.EMP_CUSTOM_ENTITY_DELETE).getFieldName(), permissionSet.getEmployeeGroupIds());
		}
	}
	
	private void processEmployeeAccessSettings(EmployeeAccessSettings employeeAccessSettings){
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_JOB_READ).getFieldName(), employeeAccessSettings.isViewJob());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_JOB_WRITE).getFieldName(), employeeAccessSettings.isModifyJob());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_JOB_CREATE).getFieldName(), employeeAccessSettings.isAddJob());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_JOB_DELETE).getFieldName(), employeeAccessSettings.isDeleteJob());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_FORM_READ).getFieldName(), employeeAccessSettings.isViewForm());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_FORM_WRITE).getFieldName(), employeeAccessSettings.isModifyForm());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_FORM_CREATE).getFieldName(), employeeAccessSettings.isAddForm());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_FORM_DELETE).getFieldName(), employeeAccessSettings.isDeleteForm());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_LEAVE_READ).getFieldName(), employeeAccessSettings.isViewLeave());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_LEAVE_WRITE).getFieldName(), employeeAccessSettings.isModifyLeave());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_LEAVE_CREATE).getFieldName(), employeeAccessSettings.isAddLeave());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_LEAVE_DELETE).getFieldName(), employeeAccessSettings.isDeleteLeave());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_CLAIM_READ).getFieldName(), employeeAccessSettings.isViewClaim());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_CLAIM_WRITE).getFieldName(), employeeAccessSettings.isModifyClaim());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_CLAIM_CREATE).getFieldName(), employeeAccessSettings.isAddClaim());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_CLAIM_DELETE).getFieldName(), employeeAccessSettings.isDeleteClaim());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_ROUTE_CREATE).getFieldName(), employeeAccessSettings.isAddRoute());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_ROUTE_PLAN_CREATE).getFieldName(), employeeAccessSettings.isAddRoutePlan());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_ROUTE_READ).getFieldName(), employeeAccessSettings.isViewRoute());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_ROUTE_DELETE).getFieldName(), employeeAccessSettings.isDeleteRoute());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_REPORT_READ).getFieldName(), employeeAccessSettings.isViewReport());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_MAP_READ).getFieldName(), employeeAccessSettings.isViewMap());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_EMPLOYEE_READ).getFieldName(), true);
		//putEmployeePermission(((CustomPermission)CustomPermission.EMP_EMPLOYEE_WRITE).getFieldName(), true);
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_EMPLOYEE_WRITE).getFieldName(), employeeAccessSettings.isManageEmployee());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_FORM_TEMPLATE_READ).getFieldName(), employeeAccessSettings.isViewFormTemplate());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_FORM_TEMPLATE_WRITE).getFieldName(), employeeAccessSettings.isModifyFormTemplate());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_CUSTOMER_READ).getFieldName(), employeeAccessSettings.isViewCustomer());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_CUSTOMER_WRITE).getFieldName(), employeeAccessSettings.isModifyCustomer());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_CUSTOMER_CREATE).getFieldName(), employeeAccessSettings.isAddCustomer());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_CUSTOMER_DELETE).getFieldName(), employeeAccessSettings.isDeleteCustomer());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_JOB_INVITATION_READ).getFieldName(), employeeAccessSettings.isViewJobInvitation());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_JOB_INVITATION_WRITE).getFieldName(), employeeAccessSettings.isModifyJobInvitation());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_JOB_INVITATION_CREATE).getFieldName(), employeeAccessSettings.isAddJobInvitation());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_JOB_INVITATION_DELETE).getFieldName(), employeeAccessSettings.isDeleteJobInvitation());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_CALENDAR_READ).getFieldName(), employeeAccessSettings.isViewHoliday());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_CALENDAR_MANAGE).getFieldName(), employeeAccessSettings.isManageCalendar());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_INVOICE_READ).getFieldName(), employeeAccessSettings.isViewInvoice());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_INVOICE_PAY).getFieldName(), employeeAccessSettings.isPayInvoice());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_AREA_GROUP_MANAGE).getFieldName(), employeeAccessSettings.isManageAreaGroup());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_TYPES_STAGES_MANAGE).getFieldName(), employeeAccessSettings.isManageTypesStates());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_CUSTOMER_TYPES_MANAGE).getFieldName(), employeeAccessSettings.isManageCustomerTypes());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_COMPANY_SETTINGS_MANAGE).getFieldName(), employeeAccessSettings.isManageCompanySettings());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_GROUP_READ).getFieldName(), employeeAccessSettings.isViewEmployeeGroup());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_GROUP_WRITE).getFieldName(), employeeAccessSettings.isModifyEmployeeGroup());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_GROUP_CREATE).getFieldName(), employeeAccessSettings.isAddEmployeeGroup());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_GROUP_DELETE).getFieldName(), employeeAccessSettings.isDeleteEmployeeGroup());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_CREATE).getFieldName(), employeeAccessSettings.isAddEmployee());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_EMPLOYEE_DELETE).getFieldName(), employeeAccessSettings.isDeleteEmployee());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_FORM_TEMPLATE_CREATE).getFieldName(), employeeAccessSettings.isAddFormTemplate());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_FORM_TEMPLATE_DELETE).getFieldName(), employeeAccessSettings.isDeleteFormTemplate());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_SECURITY_MANAGE).getFieldName(), employeeAccessSettings.isManageSecurity());
		putEmployeePermission(((CustomPermission)CustomPermission.COMP_EMP_ARTICLE_MANAGE).getFieldName(), employeeAccessSettings.isManageArticle());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_VIEW_ADMIN).getFieldName(), employeeAccessSettings.isViewAdmin());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_CUSTOM_ENTITY_VIEW).getFieldName(), employeeAccessSettings.isViewCustomEntity());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_CUSTOM_ENTITY_CREATE).getFieldName(), employeeAccessSettings.isAddCustomEntity());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_CUSTOM_ENTITY_MODIFY).getFieldName(), employeeAccessSettings.isModifyCustomEntity());
		putEmployeePermission(((CustomPermission)CustomPermission.EMP_CUSTOM_ENTITY_DELETE).getFieldName(), employeeAccessSettings.isDeleteCustomEntity());
	}
	
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public List<String> getWebUserAuthorities() {
		return webUserAuthorities;
	}

	public void setWebUserAuthorities(List<String> webUserAuthorities) {
		this.webUserAuthorities = webUserAuthorities;
	}
	
	public boolean hasAnyRole(String[] roleStrings)
	{
		Boolean permission = false;
		try
		{
			List<String> roleStringsList = Arrays.asList(roleStrings);
			List<String> roles =getWebUserAuthorities();
			
			if(roleStringsList != null && !roles.isEmpty())
			{
				for(String role : roles)
				{
					if(roleStringsList.contains(role))
					{
						return true;
					}
				}
			}
		}
		catch(Exception e)
		{
			
		}
		return permission;
	}
	
	public List<Long> getEmployeeGroupIdsOfEmployee() {
		return employeeGroupIdsOfEmployee;
	}

	public void setEmployeeGroupIdsOfEmployee(
			List<Long> employeeGroupIdsOfEmployee) {
		this.employeeGroupIdsOfEmployee = employeeGroupIdsOfEmployee;
	}
	
	
	
	
	
}
