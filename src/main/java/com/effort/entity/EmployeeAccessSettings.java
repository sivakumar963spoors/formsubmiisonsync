package com.effort.entity;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class EmployeeAccessSettings implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonProperty(access = Access.WRITE_ONLY)
	private long id;
	@JsonProperty(access = Access.WRITE_ONLY)
	private long empId;
	private boolean viewCustomer = true;
	private boolean addCustomer = false;
	private boolean modifyCustomer = false;
	private boolean deleteCustomer = false;
	private boolean viewJob = true;
	private boolean addJob = true;
	private boolean modifyJob = true;
	private boolean deleteJob = true;
	private boolean viewJobInvitation = true;
	private boolean addJobInvitation = true;
	private boolean modifyJobInvitation = true;
	private boolean deleteJobInvitation = true;
	private boolean viewForm = true;
	private boolean addForm = true;
	private boolean modifyForm = true;
	private boolean deleteForm = true;
	private boolean viewHoliday = true;
//	private boolean addHoliday = true;
//	private boolean modifyHoliday = true;
//	private boolean deleteHoliday = true;
	private boolean viewLeave = true;
	private boolean addLeave = true;
	private boolean modifyLeave = true;
	private boolean deleteLeave = true;
	private boolean viewClaim = true;
	private boolean addClaim = true;
	private boolean modifyClaim = true;
	private boolean deleteClaim = true;
	private boolean viewHomeLocation = true;
	private boolean addHomeLocation = true;
	private boolean modifyHomeLocation = true;
	private boolean viewReport = true;
	private boolean viewMap = true;
	private boolean addEmployee = true;
	private boolean deleteEmployee = true;
	private boolean manageEmployee = true;
	private boolean manageCalendar = false;
	private boolean viewInvoice = true;
	private boolean payInvoice = false;
	private boolean manageAreaGroup = true;
	private boolean manageTypesStates = true;
	private boolean manageCustomerTypes = true;
	private boolean manageCompanySettings = false;
	private boolean viewEmployeeGroup = true;
	private boolean addEmployeeGroup = true;
	private boolean modifyEmployeeGroup = true;
	private boolean deleteEmployeeGroup = true;
	private boolean viewFormTemplate = true;
	private boolean addFormTemplate = true;
	private boolean modifyFormTemplate = true;
	private boolean deleteFormTemplate = true;
	private boolean manageSecurity = false;
	private boolean manageArticle = false;
	private boolean manageNamedLocation = false;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String createdDate = "1970-01-01 00:00:00";
	@JsonProperty(access = Access.WRITE_ONLY)
	private String modifiedDate = "1970-01-01 00:00:00";
	private boolean addRoute = false;
	private boolean viewRoute = false;
	private boolean deleteRoute = false;
	private boolean addRoutePlan = false;
	private boolean viewAdmin = true;
	private boolean viewCustomEntity = true;
	private boolean addCustomEntity = true;
	private boolean modifyCustomEntity = true;
	private boolean deleteCustomEntity = true;
	
	private boolean modifyAccessSettingsThroughUpload = false;
	
	
	public EmployeeAccessSettings() {
	}
	
	public EmployeeAccessSettings(long empId) {
		this.empId = empId;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public boolean isViewCustomer() {
		return viewCustomer;
	}
	public void setViewCustomer(boolean viewCustomer) {
		this.viewCustomer = viewCustomer;
	}
	public boolean isAddCustomer() {
		return addCustomer;
	}
	public void setAddCustomer(boolean addCustomer) {
		this.addCustomer = addCustomer;
	}
	public boolean isModifyCustomer() {
		return modifyCustomer;
	}
	public void setModifyCustomer(boolean modifyCustomer) {
		this.modifyCustomer = modifyCustomer;
	}
	public boolean isDeleteCustomer() {
		return deleteCustomer;
	}
	public void setDeleteCustomer(boolean deleteCustomer) {
		this.deleteCustomer = deleteCustomer;
	}
	public boolean isViewJob() {
		return viewJob;
	}
	public void setViewJob(boolean viewJob) {
		this.viewJob = viewJob;
	}
	public boolean isAddJob() {
		return addJob;
	}
	public void setAddJob(boolean addJob) {
		this.addJob = addJob;
	}
	public boolean isModifyJob() {
		return modifyJob;
	}
	public void setModifyJob(boolean modifyJob) {
		this.modifyJob = modifyJob;
	}
	public boolean isDeleteJob() {
		return deleteJob;
	}
	public void setDeleteJob(boolean deleteJob) {
		this.deleteJob = deleteJob;
	}
	public boolean isViewJobInvitation() {
		return viewJobInvitation;
	}
	public void setViewJobInvitation(boolean viewJobInvitation) {
		this.viewJobInvitation = viewJobInvitation;
	}
	public boolean isAddJobInvitation() {
		return addJobInvitation;
	}
	public void setAddJobInvitation(boolean addJobInvitation) {
		this.addJobInvitation = addJobInvitation;
	}
	public boolean isModifyJobInvitation() {
		return modifyJobInvitation;
	}
	public void setModifyJobInvitation(boolean modifyJobInvitation) {
		this.modifyJobInvitation = modifyJobInvitation;
	}
	public boolean isDeleteJobInvitation() {
		return deleteJobInvitation;
	}
	public void setDeleteJobInvitation(boolean deleteJobInvitation) {
		this.deleteJobInvitation = deleteJobInvitation;
	}
	public boolean isViewForm() {
		return viewForm;
	}
	public void setViewForm(boolean viewForm) {
		this.viewForm = viewForm;
	}
	public boolean isAddForm() {
		return addForm;
	}
	public void setAddForm(boolean addForm) {
		this.addForm = addForm;
	}
	public boolean isModifyForm() {
		return modifyForm;
	}
	public void setModifyForm(boolean modifyForm) {
		this.modifyForm = modifyForm;
	}
	public boolean isDeleteForm() {
		return deleteForm;
	}
	public void setDeleteForm(boolean deleteForm) {
		this.deleteForm = deleteForm;
	}
	public boolean isViewHoliday() {
		return viewHoliday;
	}
	public void setViewHoliday(boolean viewHoliday) {
		this.viewHoliday = viewHoliday;
	}
	public boolean isViewLeave() {
		return viewLeave;
	}
	public void setViewLeave(boolean viewLeave) {
		this.viewLeave = viewLeave;
	}
	public boolean isAddLeave() {
		return addLeave;
	}
	public void setAddLeave(boolean addLeave) {
		this.addLeave = addLeave;
	}
	public boolean isModifyLeave() {
		return modifyLeave;
	}
	public void setModifyLeave(boolean modifyLeave) {
		this.modifyLeave = modifyLeave;
	}
	public boolean isDeleteLeave() {
		return deleteLeave;
	}
	public void setDeleteLeave(boolean deleteLeave) {
		this.deleteLeave = deleteLeave;
	}
	public boolean isViewHomeLocation() {
		return viewHomeLocation;
	}
	public void setViewHomeLocation(boolean viewHomeLocation) {
		this.viewHomeLocation = viewHomeLocation;
	}
	public boolean isAddHomeLocation() {
		return addHomeLocation;
	}
	public void setAddHomeLocation(boolean addHomeLocation) {
		this.addHomeLocation = addHomeLocation;
	}
	public boolean isModifyHomeLocation() {
		return modifyHomeLocation;
	}
	public void setModifyHomeLocation(boolean modifyHomeLocation) {
		this.modifyHomeLocation = modifyHomeLocation;
	}
	public boolean isViewReport() {
		return viewReport;
	}
	public void setViewReport(boolean viewReport) {
		this.viewReport = viewReport;
	}
	public boolean isViewMap() {
		return viewMap;
	}
	public void setViewMap(boolean viewMap) {
		this.viewMap = viewMap;
	}
	public boolean isAddEmployee() {
		return addEmployee;
	}
	public void setAddEmployee(boolean addEmployee) {
		this.addEmployee = addEmployee;
	}
	public boolean isDeleteEmployee() {
		return deleteEmployee;
	}
	public void setDeleteEmployee(boolean deleteEmployee) {
		this.deleteEmployee = deleteEmployee;
	}
	
	
	public boolean isManageEmployee() {
		return manageEmployee;
	}

	public void setManageEmployee(boolean manageEmployee) {
		this.manageEmployee = manageEmployee;
	}

	public boolean isManageCalendar() {
		return manageCalendar;
	}
	public void setManageCalendar(boolean manageCalendar) {
		this.manageCalendar = manageCalendar;
	}
	public boolean isViewInvoice() {
		return viewInvoice;
	}
	public void setViewInvoice(boolean viewInvoice) {
		this.viewInvoice = viewInvoice;
	}
	public boolean isPayInvoice() {
		return payInvoice;
	}
	public void setPayInvoice(boolean payInvoice) {
		this.payInvoice = payInvoice;
	}
	public boolean isManageAreaGroup() {
		return manageAreaGroup;
	}
	public void setManageAreaGroup(boolean manageAreaGroup) {
		this.manageAreaGroup = manageAreaGroup;
	}
	public boolean isManageTypesStates() {
		return manageTypesStates;
	}
	public void setManageTypesStates(boolean manageTypesStates) {
		this.manageTypesStates = manageTypesStates;
	}
	public boolean isManageCustomerTypes() {
		return manageCustomerTypes;
	}
	public void setManageCustomerTypes(boolean manageCustomerTypes) {
		this.manageCustomerTypes = manageCustomerTypes;
	}
	public boolean isManageCompanySettings() {
		return manageCompanySettings;
	}
	public void setManageCompanySettings(boolean manageCompanySettings) {
		this.manageCompanySettings = manageCompanySettings;
	}
	public boolean isViewEmployeeGroup() {
		return viewEmployeeGroup;
	}
	public void setViewEmployeeGroup(boolean viewEmployeeGroup) {
		this.viewEmployeeGroup = viewEmployeeGroup;
	}
	public boolean isAddEmployeeGroup() {
		return addEmployeeGroup;
	}
	public void setAddEmployeeGroup(boolean addEmployeeGroup) {
		this.addEmployeeGroup = addEmployeeGroup;
	}
	public boolean isModifyEmployeeGroup() {
		return modifyEmployeeGroup;
	}
	public void setModifyEmployeeGroup(boolean modifyEmployeeGroup) {
		this.modifyEmployeeGroup = modifyEmployeeGroup;
	}
	public boolean isDeleteEmployeeGroup() {
		return deleteEmployeeGroup;
	}
	public void setDeleteEmployeeGroup(boolean deleteEmployeeGroup) {
		this.deleteEmployeeGroup = deleteEmployeeGroup;
	}
	public boolean isViewFormTemplate() {
		return viewFormTemplate;
	}
	public void setViewFormTemplate(boolean viewFormTemplate) {
		this.viewFormTemplate = viewFormTemplate;
	}
	public boolean isAddFormTemplate() {
		return addFormTemplate;
	}
	public void setAddFormTemplate(boolean addFormTemplate) {
		this.addFormTemplate = addFormTemplate;
	}
	public boolean isModifyFormTemplate() {
		return modifyFormTemplate;
	}
	public void setModifyFormTemplate(boolean modifyFormTemplate) {
		this.modifyFormTemplate = modifyFormTemplate;
	}
	public boolean isDeleteFormTemplate() {
		return deleteFormTemplate;
	}
	public void setDeleteFormTemplate(boolean deleteFormTemplate) {
		this.deleteFormTemplate = deleteFormTemplate;
	}
	public boolean isManageSecurity() {
		return manageSecurity;
	}
	public void setManageSecurity(boolean manageSecurity) {
		this.manageSecurity = manageSecurity;
	}
	public boolean isManageArticle() {
		return manageArticle;
	}
	public void setManageArticle(boolean manageArticle) {
		this.manageArticle = manageArticle;
	}
	
	public boolean isAddRoute() {
		return addRoute;
	}

	public void setAddRoute(boolean addRoute) {
		this.addRoute = addRoute;
	}

	public boolean isViewRoute() {
		return viewRoute;
	}

	public void setViewRoute(boolean viewRoute) {
		this.viewRoute = viewRoute;
	}

	public boolean isDeleteRoute() {
		return deleteRoute;
	}

	public void setDeleteRoute(boolean deleteRoute) {
		this.deleteRoute = deleteRoute;
	}

	public boolean isAddRoutePlan() {
		return addRoutePlan;
	}

	public void setAddRoutePlan(boolean addRoutePlan) {
		this.addRoutePlan = addRoutePlan;
	}

	@JsonProperty("manageNamedLocations")
	public boolean isManageNamedLocation() {
		return manageNamedLocation;
	}

	public void setManageNamedLocation(boolean manageNamedLocation) {
		this.manageNamedLocation = manageNamedLocation;
	}

	
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	
	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public boolean isViewClaim() {
		return viewClaim;
	}

	public void setViewClaim(boolean viewClaim) {
		this.viewClaim = viewClaim;
	}

	public boolean isAddClaim() {
		return addClaim;
	}

	public void setAddClaim(boolean addClaim) {
		this.addClaim = addClaim;
	}

	public boolean isModifyClaim() {
		return modifyClaim;
	}

	public void setModifyClaim(boolean modifyClaim) {
		this.modifyClaim = modifyClaim;
	}

	public boolean isDeleteClaim() {
		return deleteClaim;
	}

	public void setDeleteClaim(boolean deleteClaim) {
		this.deleteClaim = deleteClaim;
	}

	

	public void setAllFalse() {
		this.viewCustomer = false;
		this.addCustomer = false;
		this.modifyCustomer = false;
		this.deleteCustomer = false;
		this.viewJob = false;
		this.addJob = false;
		this.modifyJob = false;
		this.deleteJob = false;
		this.viewJobInvitation = false;
		this.addJobInvitation = false;
		this.modifyJobInvitation = false;
		this.deleteJobInvitation = false;
		this.viewForm = false;
		this.addForm = false;
		this.modifyForm = false;
		this.deleteForm = false;
		this.viewHoliday = false;
		this.viewLeave = false;
		this.addLeave = false;
		this.modifyLeave = false;
		this.deleteLeave = false;
		this.viewClaim = false;
		this.addClaim = false;
		this.modifyClaim = false;
		this.deleteClaim = false;
		this.viewHomeLocation = false;
		this.addHomeLocation = false;
		this.modifyHomeLocation = false;
		this.viewReport = false;
		this.viewMap = false;
		this.addEmployee = false;
		this.deleteEmployee = false;
		this.manageEmployee = false;
		this.manageCalendar = false;
		this.viewInvoice = false;
		this.payInvoice = false;
		this.manageAreaGroup = false;
		this.manageTypesStates = false;
		this.manageCustomerTypes = false;
		this.manageCompanySettings = false;
		this.viewEmployeeGroup = false;
		this.addEmployeeGroup = false;
		this.modifyEmployeeGroup = false;
		this.deleteEmployeeGroup = false;
		this.viewFormTemplate = false;
		this.addFormTemplate = false;
		this.modifyFormTemplate = false;
		this.deleteFormTemplate = false;
		this.manageSecurity = false;
		this.manageArticle = false;
		this.manageNamedLocation = false;
		this.addRoute = false;
		this.viewRoute = false;
		this.deleteRoute = false;
		this.addRoutePlan = false;
		this.viewAdmin = false;
		this.addCustomEntity = false;
		this.viewCustomEntity = false;
		this.modifyCustomEntity = false;
		this.deleteCustomEntity = false;
	}
	
	
	
	
	public boolean isSame(EmployeeAccessSettings employeeAccessSettings) {
		if(employeeAccessSettings.isViewCustomer() != isViewCustomer()){
			return false;
		}
		if(employeeAccessSettings.isAddCustomer() != isAddCustomer()){
			return false;
		}
		if(employeeAccessSettings.isModifyCustomer() != isModifyCustomer()){
			return false;
		}
		if(employeeAccessSettings.isDeleteCustomer() != isDeleteCustomer()){
			return false;
		}
		if(employeeAccessSettings.isViewJob() != isViewJob()){
			return false;
		}
		if(employeeAccessSettings.isAddJob() != isAddJob()){
			return false;
		}
		if(employeeAccessSettings.isModifyJob() != isModifyJob()){
			return false;
		}
		if(employeeAccessSettings.isDeleteJob() != isDeleteJob()){
			return false;
		}
		if(employeeAccessSettings.isViewJobInvitation() != isViewJobInvitation()){
			return false;
		}
		if(employeeAccessSettings.isAddJobInvitation() != isAddJobInvitation()){
			return false;
		}
		if(employeeAccessSettings.isModifyJobInvitation() != isModifyJobInvitation()){
			return false;
		}
		if(employeeAccessSettings.isDeleteJobInvitation() != isDeleteJobInvitation()){
			return false;
		}
		if(employeeAccessSettings.isViewForm() != isViewForm()){
			return false;
		}
		if(employeeAccessSettings.isAddForm() != isAddForm()){
			return false;
		}
		if(employeeAccessSettings.isModifyForm() != isModifyForm()){
			return false;
		}
		if(employeeAccessSettings.isDeleteForm() != isDeleteForm()){
			return false;
		}
		if(employeeAccessSettings.isViewHoliday() != isViewHoliday()){
			return false;
		}
		if(employeeAccessSettings.isViewLeave() != isViewLeave()){
			return false;
		}
		if(employeeAccessSettings.isAddLeave() != isAddLeave()){
			return false;
		}
		if(employeeAccessSettings.isModifyLeave() != isModifyLeave()){
			return false;
		}
		if(employeeAccessSettings.isDeleteLeave() != isDeleteLeave()){
			return false;
		}
		if(employeeAccessSettings.isViewHomeLocation() != isViewHomeLocation()){
			return false;
		}
		if(employeeAccessSettings.isAddHomeLocation() != isAddHomeLocation()){
			return false;
		}
		if(employeeAccessSettings.isModifyHomeLocation() != isModifyHomeLocation()){
			return false;
		}
		if(employeeAccessSettings.isViewReport() != isViewReport()){
			return false;
		}
		if(employeeAccessSettings.isViewMap() != isViewMap()){
			return false;
		}
		if(employeeAccessSettings.isAddEmployee() != isAddEmployee()){
			return false;
		}
		if(employeeAccessSettings.isDeleteEmployee() != isDeleteEmployee()){
			return false;
		}
		
		if(employeeAccessSettings.isManageEmployee() != isManageEmployee()){
			return false;
		}
		
		if(employeeAccessSettings.isManageCalendar() != isManageCalendar()){
			return false;
		}
		if(employeeAccessSettings.isViewInvoice() != isViewInvoice()){
			return false;
		}
		if(employeeAccessSettings.isPayInvoice() != isPayInvoice()){
			return false;
		}
		if(employeeAccessSettings.isManageAreaGroup() != isManageAreaGroup()){
			return false;
		}
		if(employeeAccessSettings.isManageTypesStates() != isManageTypesStates()){
			return false;
		}
		if(employeeAccessSettings.isManageCustomerTypes() != isManageCustomerTypes()){
			return false;
		}
		if(employeeAccessSettings.isManageCompanySettings() != isManageCompanySettings()){
			return false;
		}
		if(employeeAccessSettings.isViewEmployeeGroup() != isViewEmployeeGroup()){
			return false;
		}
		if(employeeAccessSettings.isAddEmployeeGroup() != isAddEmployeeGroup()){
			return false;
		}
		if(employeeAccessSettings.isModifyEmployeeGroup() != isModifyEmployeeGroup()){
			return false;
		}
		if(employeeAccessSettings.isDeleteEmployeeGroup() != isDeleteEmployeeGroup()){
			return false;
		}
		if(employeeAccessSettings.isViewFormTemplate() != isViewFormTemplate()){
			return false;
		}
		
		if(employeeAccessSettings.isAddFormTemplate() != isAddFormTemplate()){
			return false;
		}
		
		if(employeeAccessSettings.isModifyFormTemplate() != isModifyFormTemplate()){
			return false;
		}
		if(employeeAccessSettings.isDeleteFormTemplate() != isDeleteFormTemplate()){
			return false;
		}
		if(employeeAccessSettings.isManageSecurity() != isManageSecurity()){
			return false;
		}
		if(employeeAccessSettings.isManageArticle() != isManageArticle()){
			return false;
		}
		if(employeeAccessSettings.isViewCustomer() != isViewCustomer()){
			return false;
		}
		// Claims
		if(employeeAccessSettings.isAddClaim() != isAddClaim()){
			return false;
		}
		if(employeeAccessSettings.isModifyClaim() != isModifyClaim()){
			return false;
		}
		if(employeeAccessSettings.isViewClaim() != isViewClaim()){
			return false;
		}
		if(employeeAccessSettings.isDeleteClaim() != isDeleteClaim()){
			return false;
		}	
		// Route
		if(employeeAccessSettings.isAddRoute() != isAddRoute()){
			return false;
		}
		if(employeeAccessSettings.isViewRoute() != isViewRoute()){
			return false;
		}
		if(employeeAccessSettings.isDeleteRoute() != isDeleteRoute()){
			return false;
		}
		if(employeeAccessSettings.isAddRoutePlan() != isAddRoutePlan()){
			return false;
		}
		if(employeeAccessSettings.isViewAdmin() != isViewAdmin()){
			return false;
		}
		if(employeeAccessSettings.isViewCustomEntity() != isViewCustomEntity())
		{
			return false;
		}
		if(employeeAccessSettings.isAddCustomEntity() != isAddCustomEntity())
		{
			return false;
		}
		if(employeeAccessSettings.isModifyCustomEntity() != isModifyCustomEntity())
		{
			return false;
		}
		if(employeeAccessSettings.isDeleteCustomEntity() != isDeleteCustomEntity())
		{
			return false;
		}
		 
		return true;
	}

	public boolean isViewAdmin() {
		return viewAdmin;
	}

	public void setViewAdmin(boolean viewAdmin) {
		this.viewAdmin = viewAdmin;
	}

	public boolean isViewCustomEntity() {
		return viewCustomEntity;
	}

	public void setViewCustomEntity(boolean viewCustomEntity) {
		this.viewCustomEntity = viewCustomEntity;
	}

	public boolean isAddCustomEntity() {
		return addCustomEntity;
	}

	public void setAddCustomEntity(boolean addCustomEntity) {
		this.addCustomEntity = addCustomEntity;
	}

	public boolean isModifyCustomEntity() {
		return modifyCustomEntity;
	}

	public void setModifyCustomEntity(boolean modifyCustomEntity) {
		this.modifyCustomEntity = modifyCustomEntity;
	}

	public boolean isDeleteCustomEntity() {
		return deleteCustomEntity;
	}

	public void setDeleteCustomEntity(boolean deleteCustomEntity) {
		this.deleteCustomEntity = deleteCustomEntity;
	}

	public boolean isModifyAccessSettingsThroughUpload() {
		return modifyAccessSettingsThroughUpload;
	}

	public void setModifyAccessSettingsThroughUpload(boolean modifyAccessSettingsThroughUpload) {
		this.modifyAccessSettingsThroughUpload = modifyAccessSettingsThroughUpload;
	}

	

}
