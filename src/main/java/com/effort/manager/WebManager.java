package com.effort.manager;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.ANTLRInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.effort.context.AppContext;
import com.effort.dao.ConfiguratorDao;
import com.effort.dao.EmployeeDao;
import com.effort.dao.ExtendedDao;
import com.effort.dao.ExtraDao;
import com.effort.dao.ExtraSupportAdditionalDao;
import com.effort.dao.ExtraSupportAdditionalSupportDao;
import com.effort.dao.ExtraSupportDao;
import com.effort.dao.SettingsDao;
import com.effort.dao.WorkFlowExtraDao;
import com.effort.entity.ActivityStream;
import com.effort.entity.Company;
import com.effort.entity.CompanyLabel;
import com.effort.entity.Customer;
import com.effort.entity.EffortAccessSettings;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeAccessSettings;
import com.effort.entity.EmployeeEntityMap;
import com.effort.entity.EmployeeGroup;
import com.effort.entity.Entity;
import com.effort.entity.EntityField;
import com.effort.entity.EntityFieldSpec;
import com.effort.entity.Form;
import com.effort.entity.FormAndField;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormFieldSpecValidValue;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.JmsMessage;
import com.effort.entity.JobCreateOrModifiNotify;
import com.effort.entity.LabelValue;
import com.effort.entity.PermissionSet;
import com.effort.entity.Route;
import com.effort.entity.WebUser;
import com.effort.entity.WorkFlowFormStatus;
import com.effort.entity.WorkFlowFormStatusHistory;
import com.effort.entity.Workflow;
import com.effort.entity.WorkflowEntityMap;
import com.effort.entity.WorkflowStage;
import com.effort.exception.EffortError;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.settings.ReportConstants;
import com.effort.settings.ServiceCallConstants;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.ContextUtils;
import com.effort.util.Log;
import com.effort.validators.FormValidator;
@Service
public class WebManager {
	
	
	
	@Autowired
	private EmployeeDao employeeDao;
		
	@Autowired
	private ConfiguratorDao configuratorDao;
	@Autowired
	private WebSupportManager webSupportManager;

	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private FormValidator formValidator;
	
	@Autowired
	private WebManager webManager;
	
	@Autowired
	private Constants constants;
	@Autowired
	private ExtraSupportDao extraSupportDao;
	
	@Autowired
	private MainManager mainManager;
	
	@Autowired
	private ExtraDao extraDao;
	
	@Autowired
	private ExtraSupportAdditionalDao extraSupportAdditionalDao;
	
	@Autowired
	private WorkFlowExtraDao workFlowExtraDao;
	
	@Autowired
	private MediaManager mediaManager;

	@Autowired
	private ConstantsExtra constantsExtra;
	
	@Autowired
	private ReportConstants reportConstants;
	
	@Autowired
	private SettingsDao settingsDao;
	
	
	private WorkFlowManager getWorkFlowManager(){
		WorkFlowManager workFlowManager = AppContext.getApplicationContext().getBean("workFlowManager",WorkFlowManager.class);
		return workFlowManager;
	}
	private WebExtraManager getWebExtraManager(){
		WebExtraManager webExtraManager = AppContext.getApplicationContext().getBean("webExtraManager",WebExtraManager.class);
		return webExtraManager;
	}
	private WebSupportManager getWebSupportManager(){
		WebSupportManager webSupportManager = AppContext.getApplicationContext().getBean("webSupportManager",WebSupportManager.class);
		return webSupportManager;
	}
	
	private WebAdditionalManager getWebAdditionalManager(){
		WebAdditionalManager webAdditionalManager = AppContext.getApplicationContext().getBean("webAdditionalManager",WebAdditionalManager.class);
		return webAdditionalManager;
	}
	
	private WebAdditionalSupportManager getWebAdditionalSupportManager(){
		WebAdditionalSupportManager webAdditionalSupportManager = AppContext.getApplicationContext().getBean("webAdditionalSupportManager",WebAdditionalSupportManager.class);
		return webAdditionalSupportManager;
	}
	private ExtraSupportAdditionalDao getExtraSupportAdditionalDao(){
		ExtraSupportAdditionalDao extraSupportAdditionalDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalDao",ExtraSupportAdditionalDao.class);
		return extraSupportAdditionalDao;
	}
	private WebFunctionalManager getWebFunctionalManager(){
		WebFunctionalManager webFunctionalManager = AppContext.getApplicationContext().getBean("webFunctionalManager",WebFunctionalManager.class);
		return webFunctionalManager;
	}
	
	private WebAdditionalSupportExtraManager getWebAdditionalSupportExtraManager() {
		WebAdditionalSupportExtraManager webAdditionalSupportExtraManager = AppContext.getApplicationContext()
				.getBean("webAdditionalSupportExtraManager", WebAdditionalSupportExtraManager.class);
		return webAdditionalSupportExtraManager;
	}
	
	private ExtraSupportAdditionalSupportDao getExtraSupportAdditionalSupportDao(){
		ExtraSupportAdditionalSupportDao extraSupportAdditionalSupportDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalSupportDao",ExtraSupportAdditionalSupportDao.class);
		return extraSupportAdditionalSupportDao;
	}
	

	private WebExtensionManager getWebExtensionManager() {
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
		return webExtensionManager;
	}
	private ConfiguratorManager getConfiguratorManager(){
		ConfiguratorManager configuratorManager = AppContext.getApplicationContext().getBean("configuratorManager",ConfiguratorManager.class);
		return configuratorManager;
	}
	private ContextUtils getContextUtils() {
		ContextUtils contextUtils = AppContext.getApplicationContext().getBean(
				"contextUtils", ContextUtils.class);
		return contextUtils;
	}
	
	private ExtendedDao getExtendedDao(){
		 ExtendedDao extendedDao = AppContext.getApplicationContext().getBean("extendedDao",ExtendedDao.class);
			return extendedDao;
	}
	public void getEmployeesUnderWithLevels(long empId, List<Employee> outList,
			boolean filterDisable, Long type) {
		try {
			List<Employee> employees = employeeDao
					.getAllEmployeesUnderWithLevel(empId, type, filterDisable);
			/*if (filterDisable) {
				Api.filterDisableEmployees(employees);
			}*/
			if (employees != null && employees.size() > 0) {
				outList.addAll(employees);
			}

		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
		}
	}

	public List<Employee> getEmployeesUnder(long empId, boolean filterDisable,
			boolean addMe, Long type) {
		List<Employee> employees = new ArrayList<Employee>();
		if (addMe) {
			Employee employeeMe = getEmployee(empId + "");
			employees.add(employeeMe);
		}

		// getEmployeesUnder(empId, employees, filterDisable, type);
		// with levels
		getEmployeesUnderWithLevels(empId, employees, filterDisable, type);

		return employees;
	}
	public void sortEmployeesByName(List<Employee> employees) {
		Collections.sort(employees, new Comparator<Employee>() {
			@Override
			public int compare(Employee o1, Employee o2) {
				if (o1.getEmpFirstName() != null
						&& o2.getEmpFirstName() != null) {
					return o1.getEmpFirstName().toLowerCase()
							.compareTo(o2.getEmpFirstName().toLowerCase());
				} else {
					return 0;
				}
			}
		});
	}
	public List<Employee> getAllEmployees(long companyId, boolean filterDisable) {
		List<Employee> employees = employeeDao.getAllEmployees(companyId);
		if (filterDisable) {
			Api.filterDisableEmployees(employees);
		}
		return employees;
	}
	public List<Employee> getEmployeesUnder(Long empId, Integer companyId,
			boolean filterDisable, boolean addMe, boolean sortByName, Long type) {
		List<Employee> employees = null;
		if (empId != null) {
			employees = getEmployeesUnder(empId, filterDisable, addMe, type);

			if (sortByName) {
				sortEmployeesByName(employees);
			}
		} else {
			employees = getAllEmployees(companyId, filterDisable);
		}

		return employees;
	}

	
	public List<Employee> getEmployeesUnder(WebUser webUser,
			boolean filterDisable, boolean addMe, boolean sortByName, Long type) {
		return getEmployeesUnder(webUser.getEmpId(), webUser.getCompanyId(),
				filterDisable, addMe, true, type);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Long insertOrUpdateWorkFlowFormStatusHistory(Employee employee,
			WebUser webUser, WorkFlowFormStatus workFlowFormStatus, Long empId) {

		WorkFlowFormStatusHistory flowFormStatusHistory = new WorkFlowFormStatusHistory();
		Long workFlowStatusHistoryId = null;

		try {

			flowFormStatusHistory.setId(workFlowFormStatus.getId());
			flowFormStatusHistory.setFormSpecId(workFlowFormStatus
					.getFormSpecId());
			flowFormStatusHistory.setFormId(workFlowFormStatus.getFormId());
			flowFormStatusHistory.setFormIdentifier(workFlowFormStatus
					.getFormIdentifier());
			flowFormStatusHistory.setSubmittedBy(workFlowFormStatus
					.getSubmittedBy());
			flowFormStatusHistory.setSubmittedTime(workFlowFormStatus
					.getSubmittedTime());
			flowFormStatusHistory.setApprovedBy(employee.getEmpId());
			flowFormStatusHistory.setApprovedTime(Api
					.getDateTimeInUTC(new Date(System.currentTimeMillis())));
			flowFormStatusHistory.setWorkFlowId(workFlowFormStatus
					.getWorkFlowId());
			flowFormStatusHistory.setWorkFlowStageId(workFlowFormStatus
					.getWorkFlowStageId().longValue());
			flowFormStatusHistory.setStatusMessage(workFlowFormStatus
					.getStatusMessage());
			flowFormStatusHistory.setStatus(workFlowFormStatus.getStatus());
			flowFormStatusHistory.setCurrentRank(workFlowFormStatus
					.getCurrentRank());
			flowFormStatusHistory.setNextRank(workFlowFormStatus.getNextRank());
			flowFormStatusHistory.setPreviousRank(workFlowFormStatus
					.getPreviousRank());
			/*
			 * flowFormStatusHistory.setNextRank(workFlowExtraDao.getNextRank(
			 * webUser.getCompanyId(), employee.getRank()));
			 * flowFormStatusHistory
			 * .setPreviousRank(workFlowExtraDao.getPreviousRank(
			 * webUser.getCompanyId(), employee.getRank()));
			 */
			flowFormStatusHistory.setPreviousStage(workFlowFormStatus
					.getWorkFlowStageId().intValue());
			flowFormStatusHistory.setEmpgroupId(workFlowFormStatus
					.getEmpgroupId());
			flowFormStatusHistory.setFormAuditId(workFlowExtraDao
					.getAuditIfForWorkFlowHistory(workFlowFormStatus
							.getFormId()));
			flowFormStatusHistory.setRemarks(workFlowFormStatus.getRemarks());
			flowFormStatusHistory.setAuditId(workFlowFormStatus.getAuditId());
			flowFormStatusHistory.setAuditId(workFlowFormStatus.getAuditId());
			flowFormStatusHistory.setManagerCsvRanks(workFlowFormStatus
					.getManagerCsvRanks());
			flowFormStatusHistory.setManagerRank(workFlowFormStatus
					.getManagerRank());
			workFlowStatusHistoryId = workFlowExtraDao
					.insertWorkflowFormStatusHistory(flowFormStatusHistory);

			workFlowFormStatus.setAuditId(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return workFlowStatusHistoryId;

	}
	
	public Workflow getWorkflow(Long workflowId) {
		return workFlowExtraDao.getWorkFlow(workflowId);
	}
	
	
	public FormSpec getLatestFormSpec(String uniqueId) {
		FormSpec formSpec = null;

		try {
			formSpec = extraDao.getLatestFormSpec(uniqueId);
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
			StackTraceElement[] stackTrace = e.getStackTrace();
			getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getLatestFormSpec method while creating new form",e.toString(),stackTrace,0);
		}

		return formSpec;
	}
	public void populateWorkflowFormStatus(
			WorkFlowFormStatus workFlowFormStatus, String formId,
			int companyId, WorkflowStage workflowStage, Employee employee,
			long empId) throws EffortError {

		if (workFlowFormStatus.getStatus() != null
				&& workFlowFormStatus.getStatus() != 3
				&& workFlowFormStatus.getStatus() != 2) {
			workFlowFormStatus.setSubmittedBy(employee.getEmpId());
			workFlowFormStatus.setWorkFlowStageId(workflowStage.getStageId());

			String formIdentifier = extraDao.getIdentifierForGivenForm(formId);
			workFlowFormStatus.setFormIdentifier(formIdentifier);
			if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_ROLE_BASED) {
			
				if(WorkflowStage.APPROVAL_FLOW_MODE_PARALLEL_APROVAL == workflowStage.getApprovalMode()) {
					
					List<Employee> managers = getWebSupportManager().getManagersAboveMe(workFlowFormStatus.getSubmittedBy()+"");
					List<Integer> parllelRoleIds = Api.csvToIntegerList(workflowStage.getParallelApprovalRoleIds());
					Map<Integer,List<Employee>> managersRankMap = new HashMap<Integer, List<Employee>>();
					if(managers!=null && managers.size()>0)
					{
						managersRankMap = (Map) Api.getResolvedMapFromList(managers, "rank");
					}
					List<Employee> managersToApprove = new ArrayList<Employee>();
					for(Integer rank : parllelRoleIds) {
						if(managersRankMap.get(rank) != null) {
							managersToApprove.addAll(managersRankMap.get(rank));
						}
					}
					WorkflowStage workflowStage2 = workFlowExtraDao.getWorkFlowStage(
							workFlowFormStatus.getWorkFlowStageId(),
							workFlowFormStatus.getWorkFlowId(), WorkflowStage.NEXT_STAGE);
					if(!(managersToApprove != null && managersToApprove.size() > 0) && workflowStage2 != null) {
						getWorkFlowManager().moveToNextStage(workFlowFormStatus, workflowStage2,
								companyId, employee.getRank());
					}else if(!(managersToApprove != null && managersToApprove.size() > 0)) {
						workFlowFormStatus.setPreviousRank(employee.getRank());
						workFlowFormStatus.setStatus((short) 1);
						workFlowFormStatus.setStatusMessage("Approved");
					}else {
						getRanks(workFlowFormStatus, workflowStage, employee,
								companyId);
					}
				}else if (employee.getRank() <= workflowStage.getFinalApproverRank()) {
					workFlowFormStatus.setPreviousRank(employee.getRank());
					workFlowFormStatus.setStatus((short) 1);
					workFlowFormStatus.setStatusMessage("Approved");

				} else {
					getRanks(workFlowFormStatus, workflowStage, employee,
							companyId);
				}
			} else if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
				getRanks(workFlowFormStatus, workflowStage, employee, companyId);
			} else if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
				Long managerId = employee.getManagerId();
				// List<String> managerList =
				// Api.csvToList(workFlowFormStatus.getManagerCsvRanks());
				// String managerRank = getEntryFromList(managerList,
				// managerId,WorkFlowFormStatus.NEXT_MANAGER_ROLE);
				if (managerId == null) {
					workFlowFormStatus.setManagerRank(employee.getEmpId());
					//workFlowFormStatus.setManagerRank(managerId);
					workFlowFormStatus.setStatus((short) 1);
					workFlowFormStatus.setStatusMessage("Approved");
					workFlowFormStatus.setApprovedBy(employee.getEmpId());
					workFlowFormStatus.setApprovedTime(Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
				} else {
					getRanks(workFlowFormStatus, workflowStage, employee,
							companyId);
				}
			}
			if (workFlowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_CANCELED) {
				workFlowFormStatus.setStatusMessage("Cancelled");
			} else if (workFlowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED) {
				workFlowFormStatus.setStatusMessage("Resubmitted");
			}

			// EXECUTE WHEN APPROVED
			if (workFlowFormStatus.getStatus() != null
					&& workFlowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_APPROVED) {
				WebUser webUser = new WebUser();
				webUser.setCompanyId(companyId);
				webUser.setEmpId(employee.getEmpId());
				long historyId = insertOrUpdateWorkFlowFormStatusHistory(
						employee, webUser, workFlowFormStatus, empId);
				workFlowFormStatus.setHistoryId(historyId);
			}

			Integer approvalMode = 0, finalApproverRank = 0;
			// if((workflowStage.getStageType() ==
			// WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED)){
			// if(extraDao.isEmployeePartOfGivenEmployeeGroup(""+employee.getEmpId(),workflowStage.getEmpGroupIds())){
			// gotToNextStage(workFlowFormStatus, workflowStage.getStageType(),
			// approvalMode, employee, companyId);
			// }
			// }
			if ((workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_ROLE_BASED)) {
				if ((workflowStage.getFinalApproverRank() >= employee.getRank() || workflowStage
						.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED)
						&& workFlowFormStatus.getStatus() != WorkFlowFormStatus.STATUS_TYPE_REJECTED
						&& workFlowFormStatus.getStatus() != WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED
						&& workFlowFormStatus.getStatus() != WorkFlowFormStatus.STATUS_TYPE_CANCELED) {
					gotToNextStage(workFlowFormStatus,
							workflowStage.getStageType(), approvalMode,
							employee, companyId);
				}
			} else if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
				Long managerId = workFlowFormStatus.getManagerRank();
				List<String> managerList = getManagerList(""
						+ employee.getEmpId());
				
				String managerRank = getEntryFromList(managerList, managerId,WorkFlowFormStatus.NEXT_MANAGER_ROLE);
				
				if (managerRank == null
						&& workFlowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_APPROVED) {
					gotToNextStage(workFlowFormStatus,
							workflowStage.getStageType(), approvalMode,
							employee, companyId);
					/*
					 * FormSpec formSpec = getFormSpec(workFlowFormStatus
					 * .getFormSpecId() + ""); if
					 * (constants.getGspCropFormSpecUniqueId().equals(
					 * formSpec.getUniqueId())) {
					 * 
					 * gspCropManager.processGspCropForm(workFlowFormStatus
					 * .getFormId());
					 * 
					 * processGspCropForm(workFlowFormStatus.getFormId()); }
					 */
				}
			}
		}
	}
	
	public Form getForm(String formId) {
		Form form = null;

		try {
			form = extraDao.getForm(formId);
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
			//StackTraceElement[] stackTrace = e.getStackTrace();
			//getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getForm method",e.toString(),stackTrace,0);
			
		}

		return form;
	}
	public void addActivityStream(long companyId, long createdBy,
			String createdByName, long targetId, int type, String title,
			String extra) {
		try {
			WebUser webUser = new WebUser();
			
			
			Map<String, String> companyLabelsMap = getCompanyLabelsMap((int) companyId,createdBy+"");
			String employeeLabel = companyLabelsMap
					.get(CompanyLabel.EMPLOYEE_LABEL);
			String customerlabel = companyLabelsMap
					.get(CompanyLabel.CUSTOMER_LABEL);
			String joblabel = companyLabelsMap.get(CompanyLabel.JOB_LABEL);
			String formLabel = companyLabelsMap.get(CompanyLabel.FORM_LABEL);
			
			String workLabel = companyLabelsMap.get(CompanyLabel.WORK_LABEL);

			String jobBaseUrl = "{contextPath}/web/job/view/{id}/-330";
			String formBaseUrl = "{contextPath}/web/form/data/view/{id}";
			String workBaseUrl = "{contextPath}/web/work/details/view/{id}";
			String routeBaseUrl = "{contextPath}/web/route/create/view/{id}";
			String assigenedRouteBaseUrl = "{contextPath}/web/route/assignedDetails/new?assignedRouteId={id}&assignedEmployeeId={empId}";
			
			if (type == ActivityStream.ACTIVITY_STREAM_FORM_ADDED
					|| type == ActivityStream.ACTIVITY_STREAM_FORM_MODIFIED) {
				extra = extraDao.getIdentifierForGivenForm("" + targetId);
				if (Api.isEmptyString(extra)) {
					extra = "" + targetId;
				}
			}
			
			if(type == ActivityStream.ACTIVITY_STREAM_WORK_ADDED
					|| type == ActivityStream.ACTIVITY_STREAM_WORK_ADDED_AND_ASSIGNED
					|| type == ActivityStream.ACTIVITY_STREAM_WORK_ASSIGNED
					|| type == ActivityStream.ACTIVITY_STREAM_WORK_ACTION_COMPLETED
					|| type == ActivityStream.ACTIVITY_STREAM_WORK_COMPLETED
					|| type == ActivityStream.ACTIVITY_STREAM_WORK_SHARED
					|| type == ActivityStream.ACTIVITY_STREAM_WORK_SCHEDULING_ASSIGNMENT_FAILED)
			{
				if(Api.isEmptyString(extra))
				{
					extra = targetId+"";
				}
			}
			
			
			if(type == ActivityStream.ACTIVITY_STREAM_ROUTE_ADDED
					|| type == ActivityStream.ACTIVITY_STREAM_ROUTE_ASSIGNED
					|| type == ActivityStream.ACTIVITY_STREAM_MODIFIED_ROUTE_ASSIGNED
					|| type == ActivityStream.ACTIVITY_STREAM_ROUTE_COMPLETED
					|| type == ActivityStream.ACTIVITY_STREAM_ROUTE_ACTIVITY_PERFORMED_FOR_CUSTOMER)
			{
				if(Api.isEmptyString(extra))
				{
					extra = targetId+"";
				}
			}
			

			if (createdByName == null) {
				Employee employee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId("" + createdBy);
				createdByName = employee.getEmpName();
			}

			ActivityStream activityStream = new ActivityStream();
			activityStream.setCompanyId(companyId);
			activityStream.setCreatedBy(createdBy);
			activityStream.setTargetId(targetId);

			String message = "";
			String url = "";

			switch (type) {
			case ActivityStream.ACTIVITY_STREAM_JOB_CREATED_AND_ASSIGNED:

				url = jobBaseUrl.replace("{id}", targetId + "");
				Employee employee = extraDao.getAssignedEmployeeForGivenJob(""
						+ targetId);
				String assignedEmployee = employee != null ? employee
						.getEmpName() : "";
				message = "{Employee_Name} scheduled a new " + joblabel
						+ ": {Job_Title} for {Assigned_Employee_Name}";
				message = message
						.replace("{Employee_Name}", createdByName)
						.replace("{Assigned_Employee_Name}", assignedEmployee)
						.replace(
								"{Job_Title}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(1);
				sendJobUpdateToJobViaSms(JobCreateOrModifiNotify.JOB_ADDED,
						targetId);
				break;
			case ActivityStream.ACTIVITY_STREAM_JOB_CREATED_BUT_NOT_ASSIGNED:

				url = jobBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} scheduled a new unassigned "
						+ joblabel + ": {Job_Title}";
				message = message.replace("{Employee_Name}", createdByName)
						.replace(
								"{Job_Title}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(1);
				break;
			case ActivityStream.ACTIVITY_STREAM_JOB_ASSIGNED:

				employee = extraDao.getAssignedEmployeeForGivenJob(""
						+ targetId);
				assignedEmployee = employee != null ? employee.getEmpName()
						: "";
				url = jobBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} assigned new " + joblabel
						+ ": {Job_Title} to {Assigned_Employee_Name}";
				message = message
						.replace("{Employee_Name}", createdByName)
						.replace("{Assigned_Employee_Name}", assignedEmployee)
						.replace(
								"{Job_Title}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(1);
				sendJobUpdateToJobViaSms(JobCreateOrModifiNotify.JOB_MODIFIED,
						targetId);
				break;
			case ActivityStream.ACTIVITY_STREAM_JOB_MODIFIED:

				url = jobBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} modified {Job_Title} ";
				message = message.replace("{Employee_Name}", createdByName)
						.replace(
								"{Job_Title}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(1);
				sendJobUpdateToJobViaSms(JobCreateOrModifiNotify.JOB_MODIFIED,
						targetId);
				break;
			case ActivityStream.ACTIVITY_STREAM_JOB_COMPLETED:

				url = jobBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} completed {Job_Title} ";
				message = message.replace("{Employee_Name}", createdByName)
						.replace(
								"{Job_Title}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(1);
				break;
			case ActivityStream.ACTIVITY_STREAM_JOB_COMMENT_ADDED:

				url = jobBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} added a new note, \"{note}\" for {Job_Title}";
				message = message
						.replace("{Employee_Name}", createdByName)
						.replace("{note}", extra)
						.replace(
								"{Job_Title}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(1);
				break;
			case ActivityStream.ACTIVITY_STREAM_JOB_COMMENT_MODIFIED:

				url = jobBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} modified note, \"{note}\" for {Job_Title}";
				message = message
						.replace("{Employee_Name}", createdByName)
						.replace("{note}", extra)
						.replace(
								"{Job_Title}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(1);
				break;
			case ActivityStream.ACTIVITY_STREAM_JOB_FILE_ADDED:

				url = jobBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} captured a new Proof/Extra for {Job_Title}";
				message = message.replace("{Employee_Name}", createdByName)
						.replace(
								"{Job_Title}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(1);
				break;
			case ActivityStream.ACTIVITY_STREAM_JOB_STAGE_COMPLETED:

				url = jobBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} completed {Stage_name} in {Job_Title}";
				message = message
						.replace("{Employee_Name}", createdByName)
						.replace("{Stage_name}", extra)
						.replace(
								"{Job_Title}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(1);
				break;
			case ActivityStream.ACTIVITY_STREAM_FORM_ADDED:

				url = formBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} submitted a {form_identifier_value}  "
						+ formLabel + " of type {form_Spec_name}";
				message = message
						.replace("{Employee_Name}", createdByName)
						.replace("{form_identifier_value}", extra)
						.replace(
								"{form_Spec_name}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(2);
				break;
			case ActivityStream.ACTIVITY_STREAM_FORM_MODIFIED:

				url = formBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} modified {form_identifier_value} "
						+ formLabel + " of type {form_Spec_name}";
				message = message
						.replace("{Employee_Name}", createdByName)
						.replace("{form_identifier_value}", extra)
						.replace(
								"{form_Spec_name}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(2);
				break;
			case ActivityStream.ACTIVITY_STREAM_WORK_ADDED:

				url = workBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} scheduled a new unassigned "+workLabel+" of Type "
						+ title + " : {Work_Name}";
				message = message.replace("{Employee_Name}", createdByName)
						.replace(
								"{Work_Name}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ extra + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(3);
				break;
				
			case ActivityStream.ACTIVITY_STREAM_WORK_ADDED_AND_ASSIGNED:

				url = workBaseUrl.replace("{id}", targetId + "");
				Employee workEmployee = extraSupportDao.getAssignedEmployeeForGivenWork(""
						+ targetId);
				String workAssignedEmployee = workEmployee != null ? workEmployee
						.getEmpName() : "";
				
				message = "{Employee_Name} assigned a new "+workLabel+" of Type " + title
						+ ": {Work_Name} for {Assigned_Employee_Name}";
				message = message
						.replace("{Employee_Name}", createdByName)
						.replace("{Assigned_Employee_Name}", workAssignedEmployee)
						.replace(
								"{Work_Name}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ extra + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(3);
				
				break;
			case ActivityStream.ACTIVITY_STREAM_WORK_ACTION_COMPLETED:

				url = workBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} completed {Action_Name} in {Work_Name}";
				message = message
						.replace("{Employee_Name}", createdByName)
						.replace("{Action_Name}", title)
						.replace(
								"{Work_Name}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ extra + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(3);
				break;
			case ActivityStream.ACTIVITY_STREAM_WORK_COMPLETED:

				url = workBaseUrl.replace("{id}", targetId + "");

				message = "{Employee_Name} completed "+workLabel+" of Type "
					+ title +" : {Work_Name} ";
				message = message.replace("{Employee_Name}", createdByName)
						.replace(
								"{Work_Name}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ extra + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(3);
				break;	
				
			case ActivityStream.ACTIVITY_STREAM_ROUTE_ADDED:

				url = routeBaseUrl.replace("{id}", targetId + "");
				
				message = "{Employee_Name} created the route : {Route_Name}";
				message = message.replace("{Employee_Name}", createdByName)
						.replace(
								"{Route_Name}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ title + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(3);
				break;
				
			case ActivityStream.ACTIVITY_STREAM_ROUTE_ASSIGNED:
				
				/*
				 *  Here 'extra' is used for Assigned Route employeeId
				 *  Here 'title' is used for Assigened Route Id
				 */
				
				String assignedEmpId = extra;
				String routeId = title;
				
				webUser.setCompanyId(Integer.parseInt(companyId+""));
				Route routePlan = getRouteById(webUser,
						routeId + "");
				Employee assignedRouteEmployee = employeeDao.getEmployeeBasicDetailsByEmpId(assignedEmpId);

				url = assigenedRouteBaseUrl.replace("{id}", targetId + "").replace("{empId}",assignedEmpId);
				
				
				message = "{Employee_Name} assigned the route {Route_Name} to {Assigned_Emp}";
				message = message.replace("{Employee_Name}", createdByName).replace("{Assigned_Emp}", assignedRouteEmployee.getEmpName())
						.replace(
								"{Route_Name}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ routePlan.getRouteName() + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(3);
				break;
				
			case ActivityStream.ACTIVITY_STREAM_MODIFIED_ROUTE_ASSIGNED:
				
				/*
				 *  Here 'extra' is used for Assigned Route employeeId
				 *  Here 'title' is used for Assigened Route Id
				 */
				
				String assignedEmpIdForModify = extra;
				String routeIdForModify = title;
				
				webUser.setCompanyId(Integer.parseInt(companyId+""));
				Route routePlanForModify = getRouteById(webUser,
						routeIdForModify + "");
				Employee assignedRouteEmployeeForModify = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(assignedEmpIdForModify);

				url = assigenedRouteBaseUrl.replace("{id}", targetId + "").replace("{empId}",assignedEmpIdForModify);
				
				
				message = "{Employee_Name} modified assigned route {Route_Name} to {Assigned_Emp}";
				message = message.replace("{Employee_Name}", createdByName).replace("{Assigned_Emp}", assignedRouteEmployeeForModify.getEmpName())
						.replace(
								"{Route_Name}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ routePlanForModify.getRouteName() + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(3);
				break;
				
			case ActivityStream.ACTIVITY_STREAM_ROUTE_ACTIVITY_PERFORMED_FOR_CUSTOMER:
				
				/*
				 *  Here 'title' is used for Assigened Route customer Id
				 *  Here extra Is Used to set assigned Route emp Id.
				 */
				String customerId = title;
				Customer customer = getCustomer(customerId);
				
				webUser.setCompanyId(Integer.parseInt(companyId+""));
				String empIdForAssignedRoute = extra;
				url = assigenedRouteBaseUrl.replace("{id}", targetId + "").replace("{empId}",empIdForAssignedRoute+"");
				
				message = "{Employee_Name} completed the activity for the {Customer_Name}";
				message = message.replace("{Employee_Name}", createdByName)
						.replace(
								"{Customer_Name}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ customer.getCustomerName() + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(3);
				break;
				
				
			case ActivityStream.ACTIVITY_STREAM_ROUTE_COMPLETED:
				
				/*
				 *  Here ExtraForRoute Is Used to set assigned Route emp Id when completed from web.
				 */
				String routeId1 = title;
				
				webUser.setCompanyId(Integer.parseInt(companyId+""));
				Route routePlan1 = getRouteById(webUser,
						routeId1 + "");
				webUser.setCompanyId(Integer.parseInt(companyId+""));
				String empIdForAssignedRoute1 = extra;
				url = assigenedRouteBaseUrl.replace("{id}", targetId + "").replace("{empId}",empIdForAssignedRoute1+"");
				
				message = "{Employee_Name} completed the route {Route_Name}";
				message = message.replace("{Employee_Name}", createdByName)
						.replace(
								"{Route_Name}",
								"<a href=\"" + url + "\" target=\"_blank\">"
										+ routePlan1.getRouteName() + "</a>");

				activityStream.setMessage(message);
				activityStream.setTargetType(3);
				break;
			
				case ActivityStream.ACTIVITY_STREAM_WORK_SCHEDULING_ASSIGNMENT_FAILED:
				
				    //work scheduling assignment failed case
					
					url = workBaseUrl.replace("{id}", targetId + "");

					message = "Unable to assign {Work_Name} due to unavailability of "+employeeLabel;
					message = message.replace("{Employee_Name}", createdByName)
							.replace("{Work_Name}","<a href=\"" + url + "\" target=\"_blank\">"+ extra + "</a>");

					activityStream.setMessage(message);
					activityStream.setTargetType(3);
					
					Log.info(getClass(), "addActivityStream() // In ACTIVITY_STREAM_WORK_SCHEDULING_ASSIGNMENT_FAILED createdBy = "+createdBy+" message = "+message);
				break;
				
			default:
				break;
			}

			long id =extraDao.insertActivityStream(activityStream);
			getWebAdditionalManager().insertActivityForWebNotification(id,activityStream.getCreatedBy());
		} catch (Exception ex) {
			Log.info(getClass(), "exception in Activity Stream", ex);
		}

	}
	public void sendJmsMessage(int type, long id, long companyId, long empId,
			int changeType, long entityId, Serializable extra, Boolean byClient) {
		
		String logText = "sendJmsMessage() id = "+id+" entityId = "+entityId+" companyId = "+companyId+" type = "+type+" changeType = "+changeType;
		if (constants.isPrintLogs()) {
			Log.info(getClass(), logText+" sending ... logText = "+Api.getLogText());
		}else {
			Log.info(getClass(), logText+" sending ...");	
		}
		try {
			final JmsMessage message = new JmsMessage(type, id, companyId,
					empId, changeType, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())), entityId, extra, byClient, 0);
				String dest=constants.getJmsDestination(); 
			try {
				jmsTemplate.send(dest,
						new MessageCreator() {

							public Message createMessage(Session session)
									throws JMSException {
								ObjectMessage om = session
										.createObjectMessage(message);
								return om;
							}
						});

				Log.info(this.getClass(), logText+"   >>>Meaasge sent: " + message);
			} catch (Exception e) {
				Log.error(this.getClass(), logText+" Exception occucred : "+e.toString(), e);
				try 
				{
					mailTask.sendMail("", "shiva.amunabolu@spoors.in, ops_team@spoors.in",
							"URGENT!!! JMS TRIGGER FAILED FOR COMPANY ID :"+companyId,logText +" and Exception occucred : "+e.toString(),
							Mail.BODY_TYPE_HTML, companyId + "", false,Mail.PRODUCTIVITY_RELATED);
				}catch (MessagingException e1) {
					e1.printStackTrace();
					Log.info(this.getClass(), " " + message);
				}
			}
		} catch (Exception e) {
			Log.error(this.getClass(), logText+" Exception occucred : "+e.toString(), e);
			try {
				mailTask.sendMail("", "shiva.amunabolu@spoors.in, ops_team@spoors.in",
						"URGENT!!! JMS TRIGGER FAILED FOR COMPANY ID :"+companyId,logText +" and Exception occucred : "+e.toString(),
						Mail.BODY_TYPE_HTML, companyId + "", false,Mail.PRODUCTIVITY_RELATED);
			} catch (MessagingException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void sendJmsMessage(int type, long id, long companyId, long empId,
			int changeType, long entityId, Serializable extra, Boolean byClient, List<JmsMessage> jmsMessages) {
		
		String logText = "sendJmsMessage() id = "+id+" entityId = "+entityId+" companyId = "+companyId+" type = "+type+" changeType = "+changeType;
		try {
			final JmsMessage message = new JmsMessage(type, id, companyId,
					empId, changeType, Api.getDateTimeInUTC(new Date(System
							.currentTimeMillis())), entityId, extra, byClient, 0);
			if(entityId!=0)
			{
				jmsMessages.add(message);
			}

			/*try {
				jmsTemplate.send(constants.getJmsDestination(),
						new MessageCreator() {

							public Message createMessage(Session session)
									throws JMSException {
								ObjectMessage om = session
										.createObjectMessage(message);
								return om;
							}

						});

				Log.info(this.getClass(), ">>>Meaasge send: " + message);
			} catch (Exception e) {
				Log.info(this.getClass(), e.toString(), e);
			}*/
		} catch (Exception e) {
			Log.error(this.getClass(), logText+" Exception occucred : "+e.toString(), e);
		}
	}
	
	public FormSpec getFormSpec(String formSpecId) {
		FormSpec formSpec = null;

		try {
			formSpec = extraDao.getFormSpec(formSpecId);
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
			StackTraceElement[] stackTrace = e.getStackTrace();
			getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getFormSpec method",e.toString(),stackTrace,0);
			
		}

		return formSpec;
	}
	
	public Employee getEmployee(String empId) {
		return employeeDao.getEmployee(empId);
	}
	public List<PermissionSet> getPermissionSetsForEmployee(String empId) {
		List<PermissionSet> permissionSets = extraDao
				.getPermissionSetsForEmployee(empId);
		Map<Long, List<Long>> permissionSetsEmployeeGroupMap = extraDao
				.getPermissionSetEmployeeGroups(permissionSets);
		for (PermissionSet permissionSet : permissionSets) {
			permissionSet.setEmployeeGroupIds(permissionSetsEmployeeGroupMap
					.get(permissionSet.getPermissionSetId()));
		}
		return permissionSets;
	}
	
		public List<FormSpec> getFormSpecsIn(String ids) {
		return extraDao.getFormSpecsIn(ids);

	}
		public EffortAccessSettings getEffortAccessSettings(WebUser webUser) {
			EffortAccessSettings accessSettings = new EffortAccessSettings();

			List<PermissionSet> permissionSets = getPermissionSetsForEmployee(webUser
					.getEmpId() + "");
			EmployeeAccessSettings employeeAccessSettings = getEmployeeAccessSettings(webUser
					.getEmpId());

			accessSettings.setEmployee(getEmployee(webUser.getEmpId()+""));
			accessSettings.setWebUserAuthorities(getWebUserAuthorities(webUser.getUsername()));
			accessSettings.setEmployeeGroupIdsOfEmployee(getEmployeeGroupIdsOfEmployee(webUser.getEmpId()));
			
			accessSettings.init(webUser, permissionSets, employeeAccessSettings);
			return accessSettings;
		}
		public List<Long> getEmployeeGroupIdsOfEmployee(Long... empIds) {
			List<Long> employeeGroupIds = new ArrayList<Long>();
			List<EmployeeGroup> employeeGroups = getEmployeeGroupOfEmployee(empIds);

			for (EmployeeGroup employeeGroup : employeeGroups) {
				employeeGroupIds.add(employeeGroup.getEmpGroupId());
			}

			return employeeGroupIds;
		}
		public List<EmployeeGroup> getEmployeeGroupOfEmployee(Long... empIds) {
			return extraDao.getEmployeeGroupOfEmployee(empIds);
		}
		public List<String> getWebUserAuthorities(String userName) {
			return employeeDao.getWebUserAuthorities(userName);
		}
		public EmployeeAccessSettings getEmployeeAccessSettings(long empId) {
			try {
				EmployeeAccessSettings employeeAccessSettings = settingsDao
						.getEmployeeAccessSettings(empId);
				return employeeAccessSettings;
			} catch (IncorrectResultSizeDataAccessException e) {
				return new EmployeeAccessSettings(empId);
			}
		}

		public void getRanks(WorkFlowFormStatus workFlowFormStatus,
				WorkflowStage workflowStage, Employee employee, int companyId) {
			if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_ROLE_BASED) {
				String roleName = null;
				if (workflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_STRICT_HIERARCHY) {
					List<String> rankList = getRankListCsv("" + employee.getEmpId());
					// String currentRank = getEntryFromList(rankList,
					// employee.getRank(), WorkFlowFormStatus.NEXT_MANAGER_ROLE);

					String currentRank = validateAndGetRank(
							rankList,
							workFlowExtraDao.getNextRank(companyId,
									employee.getRank()),
							WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					workFlowFormStatus.setPreviousRank(employee.getRank());
					workFlowFormStatus
							.setCurrentRank(Integer.parseInt(currentRank));

					// Integer currentRank = workFlowExtraDao.getNextRank(companyId,
					// employee.getRank());
					Integer nextRank = workFlowExtraDao.getNextRank(companyId,
							Integer.parseInt(currentRank));
					workFlowFormStatus.setNextRank(nextRank);

					// String nextRank = getEntryFromList(rankList,
					// Long.parseLong(currentRank),
					// WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					// if(!Api.isEmptyString(nextRank)){
					// workFlowFormStatus.setNextRank(Integer.parseInt(nextRank));
					// }
					roleName = workFlowExtraDao.getRoleName(
							Integer.parseInt(currentRank), companyId);
				} else if (workflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_APPROVAL) {
					workFlowFormStatus.setPreviousRank(employee.getRank());
					List<String> rankList = getRankListCsv("" + employee.getEmpId());
					String currentRank = validateAndGetRank(rankList,
							workflowStage.getFinalApproverRank(),
							WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					workFlowFormStatus
							.setCurrentRank(Integer.parseInt(currentRank));
					roleName = workFlowExtraDao.getRoleName(
							Integer.parseInt(currentRank), companyId);
				} else if (workflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_WITH_IMEDIATE) {
					workFlowFormStatus.setPreviousRank(employee.getRank());
					List<String> rankList = getRankListCsv("" + employee.getEmpId());
					String currentRank = validateAndGetRank(
							rankList,
							workFlowExtraDao.getNextRank(companyId,
									employee.getRank()),
							WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					workFlowFormStatus
							.setCurrentRank(Integer.parseInt(currentRank));
					// String nextRank =workflowStage.getFinalApproverRank();
					// //validateAndGetRank(rankList,workflowStage.getFinalApproverRank(),WorkFlowFormStatus.NEXT_MANAGER_ROLE);
					workFlowFormStatus.setNextRank(workflowStage
							.getFinalApproverRank());
					roleName = workFlowExtraDao.getRoleName(
							Integer.parseInt(currentRank), companyId);
				}else if (workflowStage.getApprovalMode() == WorkflowStage.APPROVAL_FLOW_MODE_PARALLEL_APROVAL) {
					// here need find the roles..
					workFlowFormStatus.setPreviousRank(employee.getRank());
					workFlowFormStatus.setCurrentRank(employee.getRank());
					int submittedEmpRank = employee.getRank();
					List<Integer> approverRoles = Api.csvToIntegerList(workflowStage.getParallelApprovalRoleIds());
					List<Integer> waitingForRoles = new ArrayList<Integer>();
					if(approverRoles != null && approverRoles.size() > 0) {
						for(Integer approverRole : approverRoles) {
							if(approverRole < submittedEmpRank) {
								waitingForRoles.add(approverRole);
							}
						}
					}
					for(Integer waitingForRole : waitingForRoles) {
						String roleNameTemp = workFlowExtraDao.getRoleName(
								waitingForRole, companyId);
						if(!Api.isEmptyString(roleName)) {
							roleName = roleName+","+roleNameTemp;
						}else {
							roleName = roleNameTemp;
						}
					}
					
				}
				if (roleName != null) {
					workFlowFormStatus.setStatusMessage("Waiting for " + roleName);
				} else {
					roleName = workFlowExtraDao.getRoleName(
							workflowStage.getFinalApproverRank(), companyId);
					workFlowFormStatus.setStatusMessage("Waiting for " + roleName);
				}
			} else if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
				workFlowFormStatus.setEmpgroupId(workflowStage.getEmpGroupIds());
				//workFlowFormStatus.setStatusMessage("waiting for group");
				String message = getWorkFlowManager().getWorkFlowStatusMessageForEmpGroup(workFlowFormStatus);
				workFlowFormStatus.setStatusMessage(message);
				
			} else if (workflowStage.getStageType() == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
				if (workflowStage.getApprovalMode() == WorkflowStage.ONLY_IMMEDIATE_MANAGER) {
					Long managerRank = employee.getManagerId();
					List<String> managerList = new ArrayList<String>();
					managerList.add("" + employee.getEmpId());
					managerList.add("" + managerRank);
					workFlowFormStatus.setManagerRank(managerRank);
					String managerCsvRanks = Api.toCSV1(managerList);
					workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
					if (managerRank != null) {
						Employee managerEmployee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId("" + managerRank);
						workFlowFormStatus.setStatusMessage("Waiting for "
								+ managerEmployee.getEmpName());
					}
				} else if (workflowStage.getApprovalMode() == WorkflowStage.EVERYONE_IN_HIERARCHY_UP_TO_TOP_LEVEL) {
					Long managerRank = employee.getManagerId();
					workFlowFormStatus.setManagerRank(managerRank);
					List<String> managerList = getManagerList(""
							+ employee.getEmpId());
					String managerCsvRanks = Api.toCSV1(managerList);
					workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
					if (managerRank != null) {
						Employee managerEmployee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId("" + managerRank);
						workFlowFormStatus.setStatusMessage("Waiting for "
								+ managerEmployee.getEmpName());
					}
				}
				else if (workflowStage.getApprovalMode() == WorkflowStage.IMMIEDATE_MANAGER_CUSTOM_LEVEL) {
					
					Integer approvalModeLevel = workflowStage.getApprovalModeLevel();
					Long managerRank = employee.getManagerId();
					List<String> managerList = getManagerList(""+ employee.getEmpId());
					String managerCsvRanks = null;
					int requiredLevel =  approvalModeLevel+1;  // we need to add +1 because managerList contain submitter also
	                if(requiredLevel >= managerList.size()) {
	                	requiredLevel = managerList.size() - 1;
	                }
					List<String> customLevelList = new ArrayList<String>();
					customLevelList.add(managerList.get(0));
					customLevelList.add(managerList.get(requiredLevel));
					managerRank = Long.parseLong(managerList.get(requiredLevel));
					workFlowFormStatus.setManagerRank(managerRank);
					managerCsvRanks = Api.toCSV1(customLevelList);
					workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
					if (managerRank != null) {
						Employee managerEmployee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId("" + managerRank);
						workFlowFormStatus.setStatusMessage("Waiting for "+ managerEmployee.getEmpName());
					}
				}

			}
		}
		
		public void gotToNextStage(WorkFlowFormStatus workFlowFormStatus,
				Integer stageType, Integer approvalMode, Employee employee,
				int companyId) throws EffortError {
			WebUser webUser = new WebUser();
			webUser.setCompanyId(employee.getCompanyId());
			webUser.setEmpId(employee.getEmpId());
			webUser.setTzo(employee.getTimezoneOffset());
			List<String> rankList = getRankListCsv("" + employee.getEmpId());
			workFlowFormStatus.setStatusMessage("Waiting for group");
			Long nextStageId = null;
			String empIds = null;
			nextStageId = workFlowFormStatus.getWorkFlowStageId();
			Integer previousRank = null, currentRank = null, nextRank = null;
			short status = 0;
			String message = "Waiting for group";
			WorkflowStage workflowStage2 = workFlowExtraDao.getWorkFlowStage(
					workFlowFormStatus.getWorkFlowStageId(),
					workFlowFormStatus.getWorkFlowId(), WorkflowStage.NEXT_STAGE);

			if (workflowStage2 != null) {
				nextStageId = workflowStage2.getStageId();
				stageType = workflowStage2.getStageType();
				approvalMode = workflowStage2.getApprovalMode();
				if (stageType == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
					empIds = workflowStage2.getEmpGroupIds();
					previousRank = employee.getRank();
					
					message = getWorkFlowManager().getWorkFlowStatusMessageForEmpGroup(workFlowFormStatus);
					
				} else if (stageType == WorkflowStage.STAGE_TYPE_ROLE_BASED) {
					Employee employee2 =getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(""
							+ workFlowFormStatus.getSubmittedBy().intValue());
					if (approvalMode == WorkflowStage.APPROVAL_FLOW_MODE_STRICT_HIERARCHY) {
						previousRank = employee2.getRank();
						/*
						 * if(previousRank ==
						 * workflowStage2.getFinalApproverRank()){ currentRank =
						 * previousRank; }else{
						 */
						String currentRank1 = validateAndGetRank(rankList,
								workFlowExtraDao.getNextRank(companyId,
										previousRank),
								WorkFlowFormStatus.NEXT_MANAGER_ROLE);

						if ("0".equalsIgnoreCase(currentRank1)) {
							currentRank = workflowStage2.getFinalApproverRank();
						} else {
							currentRank = Integer.parseInt(currentRank1);
						}
						// currentRank = workFlowExtraDao.getNextRank(companyId,
						// previousRank);
						nextRank = workFlowExtraDao.getNextRank(companyId,
								currentRank);
						previousRank = employee.getRank();
						/* } */
					} else if (approvalMode == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_APPROVAL) {
						previousRank = employee2.getRank();
						String currentRank1 = validateAndGetRank(rankList,
								workflowStage2.getFinalApproverRank(),
								WorkFlowFormStatus.NEXT_MANAGER_ROLE);
						currentRank = Integer.parseInt(currentRank1);
					} else if (approvalMode == WorkflowStage.APPROVAL_FLOW_MODE_DIRECT_WITH_IMEDIATE) {
						previousRank = employee2.getRank();
						String currentRank1 = validateAndGetRank(rankList,
								workflowStage2.getFinalApproverRank(),
								WorkFlowFormStatus.NEXT_MANAGER_ROLE);
						currentRank = Integer.parseInt(currentRank1);
						// currentRank = workflowStage2.getFinalApproverRank();
						// nextRank=workflowStage.getFinalApproverRank();
					}
					status = 0;
					message = "Waiting for Next Stage";
				} else if (stageType == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
					Employee employee2 = getEmployee(""
							+ workFlowFormStatus.getSubmittedBy().intValue());
					if (approvalMode == WorkflowStage.ONLY_IMMEDIATE_MANAGER) {
						Long managerRank = employee2.getManagerId();
						List<String> managerList = new ArrayList<String>();
						managerList.add("" + employee2.getEmpId());
						managerList.add("" + managerRank);
						workFlowFormStatus.setManagerRank(managerRank);
						String managerCsvRanks = Api.toCSV1(managerList);
						workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
					} else if (approvalMode == WorkflowStage.EVERYONE_IN_HIERARCHY_UP_TO_TOP_LEVEL) {
						Long managerRank = employee2.getManagerId();
						if (managerRank == null) {
							workFlowFormStatus.setManagerRank(employee2.getEmpId());
						} else {
							workFlowFormStatus.setManagerRank(managerRank);
						}
						List<String> managerList = getManagerList(""
								+ employee2.getEmpId());
						String managerCsvRanks = Api.toCSV1(managerList);
						workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
					}
					else if (approvalMode == WorkflowStage.IMMIEDATE_MANAGER_CUSTOM_LEVEL) {
						
						Integer approvalModeLevel = workflowStage2.getApprovalModeLevel();
						List<String> managerList = getManagerList(""+ employee2.getEmpId());
		                int requiredLevel =  approvalModeLevel+1;  // we need to add +1 because managerList contain submitter also
		                if(requiredLevel >= managerList.size()) {
		                	requiredLevel = managerList.size() - 1;
		                }
		                List<String> customLevelList = new ArrayList<String>();
						customLevelList.add(managerList.get(0));
						customLevelList.add(managerList.get(requiredLevel));
						managerList.clear();
						managerList.addAll(customLevelList);
						long managerRank = Long.parseLong(managerList.get(0));
						workFlowFormStatus.setManagerRank(managerRank);
						String managerCsvRanks = Api.toCSV1(managerList);
						workFlowFormStatus.setManagerCsvRanks(managerCsvRanks);
					}
					status = 0;
					message = "Waiting for Next Stage";
				}
			} else {
				status = 1;
				message = "Approved";
				FormSpec formSpec = getFormSpec(workFlowFormStatus.getFormSpecId()
						+ "");
				if (constants.getGspCropFormSpecUniqueId().equals(
						formSpec.getUniqueId())) {
					if (constants.isGspCropFollowingWorkflow()) {
						Log.info(this.getClass(),
								"calling processGspCropForm for formId:"
										+ workFlowFormStatus.getFormId());
						processGspCropForm(workFlowFormStatus.getFormId());
					}
				}
				// if (productConstants.getIsService()) {
				Form form = getForm(workFlowFormStatus.getFormId() + "");
				FormSpec formSpecForService = getFormSpec("" + form.getFormSpecId());
				WorkflowEntityMap workflowEntityMap = workFlowExtraDao
						.getWorkflowEntityMap(formSpecForService.getUniqueId(),
								WorkflowEntityMap.WORKFLOW_ENTITY_TYPE_FORM);
				if (workflowEntityMap != null
						&& workflowEntityMap.getIsEnabled() == 1) {
					ServiceCallConstants serviceCallConstants = getServiceCallConstants(form
							.getCompanyId());
					updateFormFieldValueOfCustomer(form, serviceCallConstants);
				}
				// }

				if (formSpec.getSkeletonFormSpecId() != null
						&& formSpec.getSkeletonFormSpecId() == constants
								.getSales_new_leads_form_spec_id().longValue()) {

					processNewLeadFormToCreateCustomer(
							workFlowFormStatus.getFormId(), companyId, webUser);
				}
			}
			// workFlowFormStatus.setStageId(nextStageId);
			workFlowFormStatus.setPreviousRank(previousRank);
			workFlowFormStatus.setCurrentRank(currentRank);
			workFlowFormStatus.setNextRank(nextRank);
			workFlowFormStatus.setEmpgroupId(empIds);
			workFlowFormStatus.setStatus(status);
			workFlowFormStatus.setStatusMessage(message);
			workFlowFormStatus.setWorkFlowStageId(nextStageId);
		}

		
		
		public List<String> getManagerList(String empId) {
			List<String> managerList = new ArrayList<String>();
			Employee employee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(empId);
			managerList.add("" + employee.getEmpId());
			while (employee != null && employee.getManagerId() != null) {
				String managerId = employee.getManagerId() + "";
				managerList.add(managerId);
				employee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(managerId);
			}
			return managerList;
		}

	public String getEntryFromList(List<String> list, long findItem, short mode) {
			if (list != null) {
				if (list.size() == 0) {
					return "";
				} else if (list.contains(String.valueOf(findItem))) {
					int itemIndex = list.indexOf(String.valueOf(findItem));
					if (mode == WorkFlowFormStatus.NEXT_MANAGER_ROLE) {
						if ((list.size() - 1) == itemIndex)
							return null;
						else
							return list.get(itemIndex + 1);
					} else if (mode == WorkFlowFormStatus.PREVIOUS_MANAGER_ROLE) {
						if (itemIndex == 0)
							return null;
						else
							return list.get(itemIndex - 1);
					}
				}
			}
			return "";
		}
	
	public Map<String, String> getCompanyLabelsMap(int companyId,String empId) {
		Map<String ,String>clienLabelMap=new LinkedHashMap<String,String>();
		/*WebUser webUser =  null;
		try
		{
			webUser = sessionData.getWebUser();
		}
		catch(Exception e)
		{
			
		}*/
		Long appId=(long) 0;
		if(!Api.isEmptyString(empId)){
			Employee employee=employeeDao.getEmployeeBasicDetailsByEmpId(empId);
			appId=employee.getAppId();
	       List<LabelValue> labelValues = getLabelForCompany(companyId,appId);
			
			if(labelValues!=null){
			for (LabelValue labelValue : labelValues) {
				clienLabelMap.put(""+labelValue.getLabelId(), labelValue.getLabelValue());
			 }
			}
		}else{
			clienLabelMap=settingsDao.getCompanyLabelsMap(companyId,appId);
		}
		
	return clienLabelMap;
		//return settingsDao.getCompanyLabelsMap(companyId);
	}
	public List<LabelValue> getLabelForCompany(long companyId,Long appId) {
		return settingsDao.getLableValuesForCompany(companyId,appId);

	}
	
	private void sendJobUpdateToJobViaSms(int type, long visitId) {
		try {
			JobCreateOrModifiNotify jobCreateOrModifiNotify = extraDao
					.getjobDetails("" + visitId);
			jobCreateOrModifiNotify.setType(type);
			long empId = jobCreateOrModifiNotify.getEmpId();
			if (empId != 0) {
				if ("true".equalsIgnoreCase(constants.getJobsViaSms(empId))) {
					Employee employee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId("" + empId);
					jobCreateOrModifiNotify.setEmpFirstName(employee
							.getEmpFirstName());
					jobCreateOrModifiNotify.setEmpLastName(employee
							.getEmpLastName());
					jobCreateOrModifiNotify.setEmpPhone(employee.getEmpPhone());
					String visitStartTime = Api
							.getDateTimeInTzToXsd(
									jobCreateOrModifiNotify.getVisitStartTime(),
									"-330");
					jobCreateOrModifiNotify.setVisitStartTime(visitStartTime);
					Log.info(this.getClass(),
							jobCreateOrModifiNotify.toString());
					sendJmsMessage(
							constants.getJmsDestinationForJobAddOrModifi(),
							jobCreateOrModifiNotify);
				}
			}
		} catch (Exception e) {
			Log.error(this.getClass(), e.toString(), e);

		}

	}
	
	public List<String> getRankListCsv(String empId) {
		List<String> rankListCsv = new ArrayList<String>();
		String lastRank = "0";
		Employee employee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(empId);
		while (employee != null && employee.getManagerId() != null) {
			String rank = employee.getRank() + "";
			if (!rank.equalsIgnoreCase("0")) {
				rankListCsv.add(rank);
			}

			employee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId("" + employee.getManagerId());

		}
		if (employee != null && employee.getRank() != 0) {
			rankListCsv.add("" + employee.getRank());
		}
		return rankListCsv;
	}
	
	
	public Route getRouteById(WebUser webUser, String routeId) {
		try {
			return extraDao.getRouteById(webUser.getCompanyId(), routeId);
		} catch (IncorrectResultSizeDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public Customer getCustomer(String customerId) {
		Customer customer = null;
		try {
			customer = extraDao.getCustomer(customerId);
		} catch (Exception e) {
			StackTraceElement[] stackTrace = e.getStackTrace();
			getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getCustomer method",e.toString(),stackTrace,0);
			Log.info(this.getClass(), e.toString(), e);
		}
		return customer;
	}
	
	
	public String validateAndGetRank(List<String> list, long rank, short mode) {

		if (list != null && list.size() != 0) {
			if (list.contains(String.valueOf(rank))) {
				return "" + rank;
				// int itemIndex=list.indexOf(String.valueOf(rank));
				// if(mode==WorkFlowFormStatus.NEXT_MANAGER_ROLE){
				// if((list.size()-1)==itemIndex)
				// return null;
				// else
				// return list.get(itemIndex+1);
				// }else if(mode==WorkFlowFormStatus.PREVIOUS_MANAGER_ROLE){
				// if(itemIndex==0)
				// return null;
				// else
				// return list.get(itemIndex-1);
				// }
			} else {
				if (mode == WorkFlowFormStatus.NEXT_MANAGER_ROLE) {
					for (int i = 0; i < list.size(); i++) {
						if (Long.parseLong(list.get(i)) < rank) {
							return list.get(i);
						}
					}

				} else if (mode == WorkFlowFormStatus.PREVIOUS_MANAGER_ROLE) {
					int length = list.size() - 1;
					for (int i = length; i >= 0; i--) {
						if (Long.parseLong(list.get(i)) > rank) {
							return list.get(i);
						}
					}
				}

			}
		}

		return "0";
	}
	
	public Map<Long, EntityFieldSpec> getEntityFieldSpecMap(String entitySpecIds) {
		return extraDao.getEntityFieldSpecMapIn(entitySpecIds);
	}
	
	public List<Employee> getEmployeesIn(String ids) {
		return employeeDao.getAllEmployeesIn(ids);
	}

	
	public String processGspCropForm(Long formId) {

		Log.info(this.getClass(), "processing GspCropForm for formId:" + formId);
		String nextScheduledTime = "";
		try {
			String jcoResponse = null;
			String errorResponse = null;

			try {

				java.util.Calendar now = java.util.Calendar.getInstance();
				now.add(java.util.Calendar.MINUTE,
						constants.getGspCropNextScheduleInterval());
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				nextScheduledTime = sdf.format(now.getTime());
				gspCropWebDao.updateFailureCount(formId, nextScheduledTime);
				jcoResponse = callJco(formId);
			} catch (Throwable e) {
				e.printStackTrace();
				jcoResponse = null;
				errorResponse = e.toString();
			}

			if (errorResponse != null) {
				updateFormFields(errorResponse, formId);
			}

			if (jcoResponse != null) {

				gspCropWebDao.deleteProcessedTask(formId);
				updateFormFields(jcoResponse, formId);

			} else {

				/*
				 * java.util.Calendar now = java.util.Calendar.getInstance();
				 * now.add(java.util.Calendar.MINUTE,
				 * constants.getGspCropNextScheduleInterval()); SimpleDateFormat
				 * sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
				 * nextScheduledTime = sdf.format(now.getTime());
				 */
				Long failures = gspCropWebDao.getFailureCount(formId);
				// gspCropWebDao.updateFailureCount(formId, nextScheduledTime);
				if (failures != null && failures > 2) {
					Form form = getForm(formId+"");
					mailTask.sendMail("",
							constants.getJco_error_mail_address(),
							"SAP ORDER IS FAILED FOR ORDER ID : " + formId,
							"SAP ORDER IS FAILED FOR ORDER ID : " + formId, form.getCompanyId()+"",Mail.PRODUCTIVITY_RELATED);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;
	}
	

	private void updateFormFields(String jcoResponse, Long formId) {
		String gspCropFormFieldSpecFormIdUniqueId = constants
				.getGspCropFormFieldSpecformIDUniqueId();
		Form form = getForm(formId + "");

		Long empId = form.getFilledBy();
		int companyId = form.getCompanyId();

		WebUser webUser = new WebUser();
		webUser.setEmpId(empId);
		webUser.setCompanyId(companyId);
		if (form != null) {
			List<FormFieldSpec> formFieldSpecList = getFormFieldSpecs(form
					.getFormSpecId());
			List<FormField> formFieldList = getFormFields(formId);
			List<FormSectionField> sectionFields = getFormSectionFields(form
					.getFormId());
			Long gspCropFormFieldSpecId = 0l;
			for (FormFieldSpec formFieldSpec : formFieldSpecList) {
				if (formFieldSpec.getUniqueId().equals(
						gspCropFormFieldSpecFormIdUniqueId)) {
					gspCropFormFieldSpecId = formFieldSpec.getFieldSpecId();
				}
			}

			/*
			 * for(FormFieldSpec fieldSpec:formFieldSpecList){
			 * System.out.println
			 * (fieldSpec.getFieldLabel()+":"+fieldSpec.getFieldSpecId()); }
			 */
			boolean flag = true;
			for (FormField fil : formFieldList) {
				if (fil.getFieldSpecId() == gspCropFormFieldSpecId) {
					fil.setFieldValue(jcoResponse);
					flag = false;
					break;
				}
			}
			if (flag) {
				FormField field = new FormField();
				field.setFieldSpecId(gspCropFormFieldSpecId);
				field.setFieldValue(jcoResponse);
				field.setFormId(formId);
				field.setFormSpecId(form.getFormSpecId());
				field.setClientFormId(null);
				formFieldList.add(field);
			}
			FormAndField formAndField = new FormAndField();
			formAndField.setFields(formFieldList);
			formAndField.setFormSpecId(form.getFormSpecId());
			formAndField.setSectionFields(sectionFields);
			if (form.getAssignTo() != null) {
				formAndField.setAssignTo("" + form.getAssignTo());
			}
			formAndField.setFormId(form.getFormId());
			try {
				// addForm(formAndField, webUser, null);
				modifyForm(form, formAndField, webUser, null, "",null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public Map<Long, List<Entity>> getEntityMapBySpecIdForFieldsInSync(
			List<FormFieldSpec> formFieldSpecs,
			List<FormSectionFieldSpec> formSectionFieldSpecs,
			boolean includeDeleted) {
		Map<Long, List<Entity>> map = new HashMap<Long, List<Entity>>();

		List<Entity> entities = getEntityListMapBySpecIdForFieldsForSync(
				formFieldSpecs, formSectionFieldSpecs);

		for (Entity entity : entities) {
			if (entity.isDeleted() && !includeDeleted) {
				continue;
			}

			List<Entity> entitiesTemp = map.get(entity.getEntitySpecId());
			if (entitiesTemp == null) {
				entitiesTemp = new ArrayList<Entity>();
				map.put(entity.getEntitySpecId(), entitiesTemp);
			}

			entitiesTemp.add(entity);
		}

		return map;
	}
	public Company getCompany(String companyId) {
		Company company = null;
		try {
			company = extraDao.getCompanyInfo(companyId);
		} catch (Exception e1) {
			Log.info(this.getClass(), e1.toString(), e1);
		}

		return company;
	}
	public Long getRootEmployeeId(long companyId) {
		Long rootEmpId = null;
		try {
			rootEmpId = Long.parseLong(settingsDao.getCompanySetting(companyId,
					constants.getRootEmpKey()));
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
			StackTraceElement[] stackTrace = e.getStackTrace();
			getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getRootEmployeeId method",e.toString(),stackTrace,(int)companyId);
		}

		return rootEmpId;
	}

	public List<EmployeeEntityMap> getMappedEntityIds(Long empId,
			String entitySpecId) {
		List<EmployeeEntityMap> employeeEntityMaps = extraDao
				.getMappedEntityIds(empId, entitySpecId);
		return employeeEntityMaps;
	}
	public String getEmployeeGroupIdsForEmployee(String empId) {
		return extraDao.getEmployeeGroupIdsForEmployee(empId);

	}
	public List<FormSpec> getLatestFormSpecsForUnquids(String uniqueIds) {
		return extraDao.getLatestFormSpecsForUnquids(uniqueIds);
	}
	
	public List<Long> getEntitySpecIdsForFormSpec(String formSpecId) {
		return extraDao.getEntitySpecIdsForFormSpec(formSpecId);
	}
	
	public List<FormFieldSpec> getFormFieldSpecs(long formSpecId) {
		return extraDao.getFormFieldSpecs(formSpecId);
	}
	
	public void populateValuesWithEntityValues(Map<String, Object> values,
			Map<Long, List<EntityField>> entityAndFieldsMap,
			Map<Long, EntityFieldSpec> entityFieldSpecMap, String id,
			String value, Integer instanceId, List<String> accessibleEmployeeIds, WebUser webUser) {
		if (Api.isNumber(value)) {
			Long entity = Long.parseLong(value);
			populateValueInMap(id, value, values);
			List<EntityField> entityFieldsList = entityAndFieldsMap.get(entity);
			if (entityFieldsList != null) {
				for (EntityField entityField : entityFieldsList) {
					EntityFieldSpec entityFieldSpec = entityFieldSpecMap
							.get(entityField.getEntityFieldSpecId());
					
					if (entityFieldSpec != null) {
						boolean addData = true;
						/*if(entityFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE
								&& accessibleEmployeeIds != null && !accessibleEmployeeIds.contains(entityField.getFieldValue()))
						{
							addData = false;
						}*/ //under employees check condition removed on 03-08-2018 Based On Abhinav's Requirement
						if(entityFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE)
						{
							addData = true;
						}
						else if(entityFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_LIST &&
								entityField.getFieldValue() != null && Api.isNumber(entityField.getFieldValue()))
						{
							if(!getWebExtraManager().isAccessibleEntity(entityFieldSpec.getFieldTypeExtra(), entityField.getFieldValue(), webUser))
							{
								addData = false;
							}
						}
						else if(entityFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER &&
								entityField.getFieldValue() != null && Api.isNumber(entityField.getFieldValue()))
						{
							if(!getWebExtraManager().isAccessibleCustomer(entityField.getFieldValue(), webUser))
							{
								addData = false;
							}
						}
						if (addData) 
						{
							String key = id + "_"
									+ entityFieldSpec.getExpression();
							String id1 = id;
							if (instanceId != null) 
							{
								key = key + "[" + instanceId + "]";
								id1 = id1 + "[" + instanceId + "]";
							}

							if (Api.isAutoComputedEnabledNumericDataType(entityFieldSpec
									.getFieldType())) 
							{
								populateValueInMap(key,
										entityField.getFieldValue(), values);
							}
							else 
							{
								populateValueInObjectMap(key,
										entityField.getFieldValue(), values);
							}
						}
					}

				}
			}

		}
	}
	
	public String getAllCustomersUnderMe(long empId) {
		List<Employee> employees = new ArrayList<Employee>();

		Employee employeeMe = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(empId + "");
		employees.add(employeeMe);

		getEmployeesUnderWithLevels(empId, employees, true, null);

		String empIds = Api.toCSVListOfEmployeeIds(employees);

		String customerIds = extraDao.getCustomerIdsForEmpsUnderMe(empIds);

		return customerIds;
	}
	public List<Long> getCustomerIdsForEmpWithCustomerFilter1(long empId,
			int formCustomerFilter, int companyId,List<Long> customerIds) {
		Long currentTime = Api.getCurrentTimeInUTCLong();
		if (formCustomerFilter == Constants.FORM_CUSTOMER_FILTER_TYPE_ALL_CUSTOMERS) {
			List<Long> allCustomers = extraDao.getAllCustomerIdsForCompany(companyId);
			Log.info(this.getClass(), "getCustomerIdsForEmpWithCustomerFilter1 : getAllCustomerIdsForCompany End. Time taken to process: "+(Api.getCurrentTimeInUTCLong() - currentTime));
			customerIds.addAll(allCustomers);

		} else if (formCustomerFilter == Constants.FORM_CUSTOMER_FILTER_TYPE_CUSTOMERS_IN_MY_HIERARCHY) {
			currentTime = Api.getCurrentTimeInUTCLong();
			String customersUnder = getAllCustomersUnderMe(empId);
			Log.info(this.getClass(), "getCustomerIdsForEmpWithCustomerFilter1 : getAllCustomersUnderMe End. Time taken to process: "+(Api.getCurrentTimeInUTCLong() - currentTime));
			List<Long> customerMappedToEmployees = Api
					.csvToLongList(customersUnder);
			customerIds.addAll(customerMappedToEmployees);
		} 
		return customerIds;

	}
	
	public void populateValueInMap(String key, String value,
			Map<String, Object> values) {
		
		/*key = key.replaceAll("\\[0\\]", "");
		key = key.replaceAll("\\[1\\]","");
		key = key.replaceAll("\\[2\\]","");
		key = key.replaceAll("\\[3\\]","");*/
		if (Api.isNumber(value)) {
			values.put(key, Double.parseDouble(value));
			//values.put(key, value);
		} else {
			values.put(key, 0.0);
		}

	}
	
	public void populateValueInObjectMap(String key, String value,
			Map<String, Object> values) {
		
		/*key = key.replaceAll("\\[0\\]", "");
		key = key.replaceAll("\\[1\\]","");
		key = key.replaceAll("\\[2\\]","");
		key = key.replaceAll("\\[3\\]","");*/
		values.put(key, value);
	}
	

	public void populateEntityAndFieldsMap(
			Map<Long, List<EntityField>> entityAndFieldsMap,
			List<EntityField> entityFields) {
		for (EntityField entityField : entityFields) {
			List<EntityField> entityFiedsForEntity = entityAndFieldsMap
					.get(entityField.getEntityId());
			if (entityFiedsForEntity == null) {
				entityFiedsForEntity = new ArrayList<EntityField>();
				entityAndFieldsMap.put(entityField.getEntityId(),
						entityFiedsForEntity);
			}
			entityFiedsForEntity.add(entityField);

		}

	}
	
	public List<FormFieldSpecValidValue> getFormFieldSpecValidValues(
			List<FormFieldSpec> formFieldSpeccs) {
		return extraDao.getFormFieldSpecValidValuesIn(formFieldSpeccs);
	}
	
	public List<FormField> getFormFields(long formId) {
		return extraDao.getFormFieldByForm(formId);
	}
	
	public Object evaluateFormula(String formula, int instanceId,
			Map<String, Object> values) {
		ANTLRInputStream input = new ANTLRInputStream(formula);
		ExprLexer lexer = new ExprLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ExprParser parser = new ExprParser(tokens);

		ParseTree tree = parser.expr_();
		EvalVisitor visitor = new EvalVisitor(values, instanceId);
		Object result = visitor.visit(tree);
		//Double result = visitor.visit(tree);

		return result;
	}

	
	public boolean resolveCondition(List<String> resultList, String fieldValue,
			String givenValue, int condition, int fieldDataType) {
		boolean result = false;
		try {
			if (fieldDataType == 1 || fieldDataType == 8 || fieldDataType == 9
					|| fieldDataType == 10) {
				if (!Api.isEmptyString(fieldValue)) {
					fieldValue = fieldValue.toLowerCase();
				}
				if (!Api.isEmptyString(givenValue)) {
					givenValue = givenValue.toLowerCase();
				}

				if (condition == 1) {
					result = fieldValue.equalsIgnoreCase(givenValue);
				} else if (condition == 2) {
					result = fieldValue.indexOf(givenValue) != -1;

				} else if (condition == 3) {
					result = fieldValue.indexOf(givenValue) == -1;

				} else if (condition == 4) {
					result = fieldValue.startsWith(givenValue);
				} else if (condition == 5) {
					result = fieldValue.endsWith(givenValue);
				} else if (condition == 23) {// Empty
					if (Api.isEmptyString(fieldValue)) {
						result = true;
					}
				} else if (condition == 24) {// Not Empty
					if (!Api.isEmptyString(fieldValue)) {
						result = true;
					}
				}else if (condition == 36) {
					result = !fieldValue.equalsIgnoreCase(givenValue);
				}

			} else if (fieldDataType == 2 || fieldDataType == 16) {

				if (condition == 6) {
					result = Double.parseDouble(fieldValue) < Double
							.parseDouble(givenValue);
				} else if (condition == 7) {
					result = Double.parseDouble(fieldValue) >= Double
							.parseDouble(givenValue);
				} else if (condition == 8) {
					result = Double.parseDouble(fieldValue) <= Double
							.parseDouble(givenValue);
				} else if (condition == 9) {
					result = Double.parseDouble(fieldValue) == Double
							.parseDouble(givenValue);
				} else if (condition == 10) {
					result = Double.parseDouble(fieldValue) != Double
							.parseDouble(givenValue);
				} else if (condition == 11) {
					result = Double.parseDouble(fieldValue) > Double
							.parseDouble(givenValue);
				}else if (condition == 36) {
					result = !fieldValue.equalsIgnoreCase(givenValue);
				}else if (condition == 23) {// Empty
					if (Api.isEmptyString(fieldValue)) {
						result = true;
					}
				} else if (condition == 24) {// Not Empty
					if (!Api.isEmptyString(fieldValue)) {
						result = true;
					}
				}
			}

			else if (fieldDataType == 3 || fieldDataType == 11) {
				String givenValue1 = givenValue;
				if (fieldDataType == 3) {
					fieldValue = fieldValue + " 00:00:00";
					givenValue = givenValue + " 00:00:00";

				}

				if (fieldDataType == 11) {
					fieldValue = "2014-02-01" + " " + fieldValue + ":00";
					givenValue = "2014-02-01" + " " + givenValue + ":00";
				}

				if (condition == 12) {
					result = Api.getDateTimeInUTC(fieldValue).getTime() > Api
							.getDateTimeInUTC(givenValue).getTime();
				} else if (condition == 13) {
					result = Api.getDateTimeInUTC(fieldValue).getTime() < Api
							.getDateTimeInUTC(givenValue).getTime();

				} else if (condition == 14) {
					String dates[] = givenValue1.split(",");
					if (fieldDataType == 3) {
						dates[0] = dates[0] + " 00:00:00";
						dates[1] = dates[1] + " 00:00:00";
					} else if (fieldDataType == 11) {
						dates[0] = "2014-02-01" + " " + dates[0] + ":00";
						dates[1] = "2014-02-01" + " " + dates[1] + ":00";
					}

					result = Api.getDateTimeInUTC(dates[0]).getTime() <= Api
							.getDateTimeInUTC(fieldValue).getTime()
							&& Api.getDateTimeInUTC(fieldValue).getTime() <= Api
									.getDateTimeInUTC(dates[1]).getTime();
				} else if (condition == 15) {
					result = Api.getDateTimeInUTC(fieldValue).getTime() == Api
							.getDateTimeInUTC(givenValue).getTime();
				} else if (condition == 16) {
					result = Api.getDateTimeInUTC(fieldValue).getTime() != Api
							.getDateTimeInUTC(givenValue).getTime();
				}
				else if(condition == 25)
				{
					if(givenValue!=null && givenValue!="")
					{
						Date fieldValueDate = Api.getDateFromString(fieldValue);
						Date givenValueDate = Api.getDateFromString(givenValue);
						
						if(fieldValueDate.getTime() == givenValueDate.getTime() )
						{
							result = true;
						}
						else if(fieldValueDate.getTime() < givenValueDate.getTime() )
						{
							result = true;
						}
					}		
				}
				else if(condition == 26) 
				{
					if(givenValue!=null && givenValue!="")
					{
						Date fieldValueDate = Api.getDateFromString(fieldValue);
						Date givenValueDate = Api.getDateFromString(givenValue);
						
						if(fieldValueDate.getTime() == givenValueDate.getTime() )
						{
							result = true;
						}
						else if(fieldValueDate.getTime() > givenValueDate.getTime() )
						{
							result = true;
						}
					}		
					
				}else if (condition == 36) {
					result = !fieldValue.equalsIgnoreCase(givenValue);
				}else if (condition == 23) {// Empty
					if (Api.isEmptyString(fieldValue)) {
						result = true;
					}
				} else if (condition == 24) {// Not Empty
					if (!Api.isEmptyString(fieldValue)) {
						result = true;
					}
				}

			} else if (fieldDataType == 5 || fieldDataType == 6
					|| fieldDataType == 7 || fieldDataType == 14
					|| fieldDataType == 15 || fieldDataType == 17 
					|| fieldDataType == 24 || fieldDataType == 32 || fieldDataType == 18 
					|| fieldDataType == 12 || fieldDataType == 13 || fieldDataType == 20 
					|| fieldDataType == 22 || fieldDataType == 25 || fieldDataType == 26
					|| fieldDataType == 27 || fieldDataType == 31 || fieldDataType == 33 || fieldDataType == 35 || fieldDataType == 37 || fieldDataType == 38 || fieldDataType == 19) {
				String[] givenValueArray = null;
				String[] fieldValueArray = null;
				// var valuesArray;
				givenValue = Api.makeEmptyIfNull(givenValue);
				fieldValue = Api.makeEmptyIfNull(fieldValue);
				if (!Api.isEmptyString(givenValue)) {
					givenValueArray = givenValue.split(",");
				}
				if (!Api.isEmptyString(fieldValue)) {
					fieldValueArray = fieldValue.split(",");
				}
				int count = 0;
				if (fieldValueArray != null && givenValueArray != null) {
					for (int i = 0; i < fieldValueArray.length; i++) {
						for (int j = 0; j < givenValueArray.length; j++) {

							if (Long.parseLong(givenValueArray[j].trim()) == Long
									.parseLong(fieldValueArray[i].trim())) {
								// result= true;
								count++;
							}
						}
					}
				}
				if (condition == 21) {
					if(fieldValueArray!=null)
					{//Added check for multipicklist work field handling by SRS team
						if (fieldDataType == 17)
						{
							if (count > 0) {
								result = true;	
							}
						} else if (count == fieldValueArray.length) {
							result = true;
						}
					}
					
				} else if (condition == 22) {
					if (count == 0 && fieldValueArray!=null) {
						result = true;
					}
				} else if (condition == 23) {// Empty
					if (Api.isEmptyString(fieldValue)) {
						result = true;
					}
				} else if (condition == 24) {// Not Empty
					if (!Api.isEmptyString(fieldValue)) {
						result = true;
					}
				}else if (condition == 36) {
					result = !fieldValue.equalsIgnoreCase(givenValue);
				}

			} else if (fieldDataType == 4) {
				if (condition == 19) {
					givenValue = "true";
				    result = givenValue.equals(fieldValue);
				}else if (condition == 36) {
					result = !fieldValue.equalsIgnoreCase(givenValue);
				}else if (condition == 23) {// Empty
					if (Api.isEmptyString(fieldValue)) {
						result = true;
					}
				} else if (condition == 24) {// Not Empty
					if (!Api.isEmptyString(fieldValue)) {
						result = true;
					}
				} else {
					givenValue = "false";
					result = fieldValue.equals(givenValue);
				}
				
			}else if (fieldDataType == 44) {
				result = getWebExtensionManager().getFieldValidationResultForDuration(fieldValue, givenValue, condition);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		resultList.add("" + result);
        return result;
	}
	public ServiceCallConstants getServiceCallConstants(int companyId) {

		ServiceCallConstants serviceCallConstants = new ServiceCallConstants();
		Long formSpecId = null, sectionSpecId = null, sectionFieldSpecId = null, formFieldSpecId = null, entitySpecId = null, entityFieldSpecId = null;

		/*formSpecId = extraDao
				.getFormSpecIdFromSkeleton(
						constants
								.getService_call_system_defined_fields_in_work_template_form_spec_id(),
						companyId);
		if (formSpecId != null) {
			serviceCallConstants
					.setService_call_system_defined_fields_in_work_template_form_spec_id(formSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_work_template_customer_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_work_template_customer_form_field_spec_id(formFieldSpecId);

		}*/
		/* service rendered form */

		formSpecId = extraDao.getFormSpecIdFromSkeleton(
				constants.getService_call_service_rendered_form_spec_id(),
				companyId);
		if (formSpecId != null) {
			serviceCallConstants
					.setService_call_service_rendered_form_spec_id(formSpecId);

			sectionSpecId = extraDao
					.getFormSectionSpecIdFromSkeleton(
							constants
									.getService_call_service_rendered_serviced_product_details_form_section_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_service_rendered_serviced_product_details_form_section_spec_id(sectionSpecId);
			if (sectionSpecId != null) {
				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getService_call_service_rendered_product_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setService_call_service_rendered_product_form_section_field_spec_id(sectionFieldSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getService_call_service_rendered_part_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setService_call_service_rendered_part_form_section_field_spec_id(sectionFieldSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getService_call_service_rendered_in_warranty_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setService_call_service_rendered_in_warranty_form_section_field_spec_id(sectionFieldSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getService_call_service_rendered_in_warranty_amount_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setService_call_service_rendered_in_warranty_amount_form_section_field_spec_id(sectionFieldSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getService_call_service_rendered_out_warranty_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setService_call_service_rendered_out_warranty_form_section_field_spec_id(sectionFieldSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getService_call_service_rendered_tax_amount_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setService_call_service_rendered_tax_amount_form_section_field_spec_id(sectionFieldSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getService_call_service_rendered_quantity_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setService_call_service_rendered_quantity_form_section_field_spec_id(sectionFieldSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getService_call_service_rendered_amount_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setService_call_service_rendered_amount_form_section_field_spec_id(sectionFieldSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getService_call_service_rendered_amount_tax_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setService_call_service_rendered_amount_tax_form_section_field_spec_id(sectionFieldSpecId);
			}
			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_service_rendered_customer_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_service_rendered_customer_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_service_rendered_total_amount_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_service_rendered_total_amount_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_service_rendered_total_tax_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_service_rendered_total_tax_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_service_rendered_grand_total_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_service_rendered_grand_total_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_service_rendered_remarks_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_service_rendered_remarks_form_field_spec_id(formFieldSpecId);


			
		}
		/* service rendered form end */
		
		
		/* entity spec for product */

		entitySpecId = extraDao.getEntitySpecIdFromSkeleton(constants
				.getService_call_service_rendered_product_entity_spec_id(),
				companyId);
		if (entitySpecId != null) {
			serviceCallConstants
					.setService_call_service_rendered_product_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getService_call_product_product_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setService_call_product_product_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getService_call_product_product_code_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setService_call_product_product_code_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getService_call_product_description_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setService_call_product_description_entity_field_spec_id(entityFieldSpecId);
		}
		/* entity spec for product */

		/* entity spec for part */

		entitySpecId = extraDao.getEntitySpecIdFromSkeleton(constants
				.getService_call_service_rendered_part_entity_spec_id(),
				companyId);
		if (entitySpecId != null) {
			serviceCallConstants
					.setService_call_service_rendered_part_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao.getEntityFieldSpecIdFromSkeleton(
					constants.getService_call_part_part_entity_field_spec_id(),
					entitySpecId);
			serviceCallConstants
					.setService_call_part_part_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getService_call_part_product_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setService_call_part_product_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getService_call_part_in_warranty_cost_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setService_call_part_in_warranty_cost_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getService_call_part_out_warranty_cost_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setService_call_part_out_warranty_cost_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getService_call_part_tax_rate_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setService_call_part_tax_rate_entity_field_spec_id(entityFieldSpecId);
		}
		/* entity spec for part end */

		
		/* entity spec for employee_payment_options */

		/*entitySpecId = extraDao.getEntitySpecIdFromSkeleton(constants
				.getService_call_employee_payment_options_entity_spec_id(),
				companyId);
		if (entitySpecId != null) {
			serviceCallConstants
					.setService_call_employee_payment_options_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getService_call_employee_payment_options_payment_option_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setService_call_employee_payment_options_payment_option_entity_field_spec_id(entityFieldSpecId);
		}*/
		/* entity spec for employee_payment_options end */

		/* payment_collection_and_feedback_form */

		formSpecId = extraDao
				.getFormSpecIdFromSkeleton(
						constants
								.getService_call_payment_collection_and_feedback_form_spec_id(),
						companyId);
		if (formSpecId != null) {
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_form_spec_id(formSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_customer_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_customer_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_payment_amount_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_payment_amount_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_payment_mode_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_payment_mode_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_payment_particulars_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_payment_particulars_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_remarks_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_remarks_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_responsiveness_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_responsiveness_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_competence_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_competence_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_quality_of_work_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_quality_of_work_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_your_satisfaction_level_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_your_satisfaction_level_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_suggestions_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_suggestions_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_area_of_improvement_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_area_of_improvement_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getService_call_payment_collection_and_feedback_signature_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setService_call_payment_collection_and_feedback_signature_form_field_spec_id(formFieldSpecId);

		}
		formSpecId = extraDao.getFormSpecIdFromSkeleton(
				constants.getSerivce_call_Job_form_specId(), companyId);
		serviceCallConstants.setSerivce_call_Job_form_specId(formSpecId);

		/* customer_form start */
		Long serviceCallVisitTypeId = extraDao
				.getVisitTypeIdBySkeletonVisitTypeId(
						constants.getService_call_visit_type(), companyId);
		if(serviceCallVisitTypeId != null) {
       serviceCallConstants.setService_call_visit_type(serviceCallVisitTypeId);
		}
		/* customer_form end */

		/* entity spec for product */

		/*entitySpecId = extraDao.getEntitySpecIdFromSkeleton(
				constants.getSales_product_entity_spec_id(), companyId);
		if (entitySpecId != null) {
			serviceCallConstants.setSales_product_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao.getEntityFieldSpecIdFromSkeleton(
					constants.getSales_product_name_entity_field_spec_id(),
					entitySpecId);
			serviceCallConstants
					.setSales_product_name_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getSales_product_description_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setSales_product_description_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao.getEntityFieldSpecIdFromSkeleton(
					constants.getSales_product_price_entity_field_spec_id(),
					entitySpecId);
			serviceCallConstants
					.setSales_product_price_entity_field_spec_id(entityFieldSpecId);
		}*/
		/* sales collection from spec id */

		/*formSpecId = extraDao
				.getFormSpecIdFromSkeleton(
						constants.getSales_payment_collection_form_spec_id(),
						companyId);
		if (formSpecId != null) {
			serviceCallConstants
					.setSales_payment_collection_form_spec_id(formSpecId);

			 sales collection from feild spec ids 

			formFieldSpecId = extraDao.getFormFieldSpecIdFromSkeleton(constants
					.getSales_payment_collection_customer_form_field_spec_id(),
					formSpecId);
			serviceCallConstants
					.setSales_payment_collection_customer_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getSales_payment_collection_payment_amount_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setSales_payment_collection_payment_amount_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getSales_payment_collection_payment_mode_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setSales_payment_collection_payment_mode_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getSales_payment_collection_payment_particulars_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setSales_payment_collection_payment_particulars_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao.getFormFieldSpecIdFromSkeleton(constants
					.getSales_payment_collection_remarks_form_field_spec_id(),
					formSpecId);
			serviceCallConstants
					.setSales_payment_collection_remarks_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getSales_payment_collection_customer_signature_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setSales_payment_collection_customer_signature_form_field_spec_id(formFieldSpecId);
		}

		 Collection Payment 

		formSpecId = extraDao.getFormSpecIdFromSkeleton(
				constants.getCollection_payment_collection_form_spec_id(),
				companyId);
		if (formSpecId != null) {
			serviceCallConstants
					.setCollection_payment_collection_form_spec_id(formSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getCollection_payment_collection_customer_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setCollection_payment_collection_customer_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getCollection_payment_collection_amount_payed_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setCollection_payment_collection_amount_payed_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getCollection_payment_collection_payment_mode_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setCollection_payment_collection_payment_mode_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getCollection_payment_collection_payment_particulars_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setCollection_payment_collection_payment_particulars_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getCollection_payment_collection_remarks_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setCollection_payment_collection_remarks_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(
							constants
									.getCollection_payment_collection_customer_signature_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setCollection_payment_collection_customer_signature_form_field_spec_id(formFieldSpecId);
		}*/
		/* End of Collection Payment */

		// delivery_formIds_formfieldSpecIds

		/*formSpecId = extraDao.getFormSpecIdFromSkeleton(
				constants.getDelivery_delivery_form_spec_id(), companyId);
		if (formSpecId != null) {
			serviceCallConstants.setDelivery_delivery_form_spec_id(formSpecId);

			// delivery_section_formSpecs_and section fieldSpecs

			sectionSpecId = extraDao
					.getFormSectionSpecIdFromSkeleton(constants
							.getDelivery_delivery_items_form_section_spec_id(),
							formSpecId);
			if (sectionSpecId != null) {
				serviceCallConstants
						.setDelivery_delivery_items_form_section_spec_id(sectionSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(constants
								.getDelivery_item_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setDelivery_item_form_section_field_spec_id(sectionFieldSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getDelivery_quantity_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setDelivery_quantity_form_section_field_spec_id(sectionFieldSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getDelivery_price_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setDelivery_price_form_section_field_spec_id(sectionFieldSpecId);

				sectionFieldSpecId = extraDao
						.getFormSectionFieldSpecIdFromSkeleton(
								constants
										.getDelivery_amount_form_section_field_spec_id(),
								formSpecId, sectionSpecId);
				serviceCallConstants
						.setDelivery_amount_form_section_field_spec_id(sectionFieldSpecId);
			}
		}
		// delivery_formFeildSpecs

		if (formSpecId != null) {

			formFieldSpecId = extraDao.getFormFieldSpecIdFromSkeleton(
					constants.getDelivery_customer_form_field_spec_id(),
					formSpecId);
			serviceCallConstants
					.setDelivery_customer_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao
					.getFormFieldSpecIdFromSkeleton(constants
							.getDelivery_delivery_charges_form_field_spec_id(),
							formSpecId);
			serviceCallConstants
					.setDelivery_delivery_charges_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao.getFormFieldSpecIdFromSkeleton(
					constants.getDelivery_total_form_field_spec_id(),
					formSpecId);
			serviceCallConstants
					.setDelivery_total_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao.getFormFieldSpecIdFromSkeleton(
					constants.getDelivery_amount_form_field_spec_id(),
					formSpecId);
			serviceCallConstants
					.setDelivery_amount_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao.getFormFieldSpecIdFromSkeleton(
					constants.getDelivery_payment_mode_form_field_spec_id(),
					formSpecId);
			serviceCallConstants
					.setDelivery_payment_mode_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao.getFormFieldSpecIdFromSkeleton(constants
					.getDelivery_payment_particulars_form_field_spec_id(),
					formSpecId);
			serviceCallConstants
					.setDelivery_payment_particulars_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao.getFormFieldSpecIdFromSkeleton(
					constants.getDelivery_remarks_form_field_spec_id(),
					formSpecId);
			serviceCallConstants
					.setDelivery_remarks_form_field_spec_id(formFieldSpecId);

			formFieldSpecId = extraDao.getFormFieldSpecIdFromSkeleton(constants
					.getDelivery_customer_signature_form_field_spec_id(),
					formSpecId);
			serviceCallConstants
					.setDelivery_customer_signature_form_field_spec_id(formFieldSpecId);
		}*/
		// delivery formSpecs Ended

		/* entity spec for product */

		/*entitySpecId = extraDao.getEntitySpecIdFromSkeleton(
				constants.getSales_product_entity_spec_id(), companyId);
		if (entitySpecId != null) {
			serviceCallConstants.setSales_product_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao.getEntityFieldSpecIdFromSkeleton(
					constants.getSales_product_name_entity_field_spec_id(),
					entitySpecId);
			serviceCallConstants
					.setSales_product_name_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getSales_product_description_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setSales_product_description_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao.getEntityFieldSpecIdFromSkeleton(
					constants.getSales_product_price_entity_field_spec_id(),
					entitySpecId);
			serviceCallConstants
					.setSales_product_price_entity_field_spec_id(entityFieldSpecId);
		}*/

		/*entitySpecId = extraDao.getEntitySpecIdFromSkeleton(
				constants.getSales_payment_options_entity_spec_id(), companyId);
		if (entitySpecId != null) {
			serviceCallConstants
					.setSales_payment_options_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao.getEntityFieldSpecIdFromSkeleton(
					constants.getSales_payment_options_entity_field_spec_id(),
					entitySpecId);
			serviceCallConstants
					.setSales_payment_options_entity_field_spec_id(entityFieldSpecId);
		}*/

		/*entitySpecId = extraDao.getEntitySpecIdFromSkeleton(constants
				.getCollections_collection_payment_options_entity_spec_id(),
				companyId);
		if (entitySpecId != null) {
			serviceCallConstants
					.setCollections_collection_payment_options_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getCollections_payment_option_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setCollections_payment_option_entity_field_spec_id(entityFieldSpecId);
		}*/

		/*entitySpecId = extraDao.getEntitySpecIdFromSkeleton(
				constants.getDelivery_items_entity_spec_id(), companyId);
		if (entitySpecId != null) {
			serviceCallConstants.setDelivery_items_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao.getEntityFieldSpecIdFromSkeleton(
					constants.getDelivery_items_item_entity_field_spec_id(),
					entitySpecId);
			serviceCallConstants
					.setDelivery_items_item_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getDelivery_items_description_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setDelivery_items_description_entity_field_spec_id(entityFieldSpecId);

			entityFieldSpecId = extraDao.getEntityFieldSpecIdFromSkeleton(
					constants.getDelivery_items_price_entity_field_spec_id(),
					entitySpecId);
			serviceCallConstants
					.setDelivery_items_price_entity_field_spec_id(entityFieldSpecId);
		}*/

		/*entitySpecId = extraDao.getEntitySpecIdFromSkeleton(
				constants.getDelivery_payment_options_entity_spec_id(),
				companyId);
		if (entitySpecId != null) {
			serviceCallConstants
					.setDelivery_payment_options_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getDelivery_payment_options_payment_option_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setDelivery_payment_options_payment_option_entity_field_spec_id(entityFieldSpecId);
		}*/

		/*entitySpecId = extraDao.getEntitySpecIdFromSkeleton(constants
				.getDocument_verification_identity_proofs_entity_spec_id(),
				companyId);
		if (entitySpecId != null) {
			serviceCallConstants
					.setDocument_verification_identity_proofs_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getDocument_verification_identity_proofs_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setDocument_verification_identity_proofs_entity_field_spec_id(entityFieldSpecId);
		}*/

		/*entitySpecId = extraDao.getEntitySpecIdFromSkeleton(
				constants.getDocument_verification_age_proofs_entity_spec_id(),
				companyId);
		if (entitySpecId != null) {
			serviceCallConstants
					.setDocument_verification_age_proofs_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getDocument_verification_age_proofs_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setDocument_verification_age_proofs_entity_field_spec_id(entityFieldSpecId);
		}*/

		/*entitySpecId = extraDao.getEntitySpecIdFromSkeleton(constants
				.getDocument_verification_residence_proofs_entity_spec_id(),
				companyId);
		if (entitySpecId != null) {
			serviceCallConstants
					.setDocument_verification_residence_proofs_entity_spec_id(entitySpecId);

			entityFieldSpecId = extraDao
					.getEntityFieldSpecIdFromSkeleton(
							constants
									.getDocument_verification_residence_proofs_entity_field_spec_id(),
							entitySpecId);
			serviceCallConstants
					.setDocument_verification_residence_proofs_entity_field_spec_id(entityFieldSpecId);
		}*/

		/* entity spec for product */

		getSalesFormSpecsAndFieldSpec(companyId, serviceCallConstants);
		getDeliveryFromSpecAndFormFieldSpecs(companyId, serviceCallConstants);
		getCollectionFormSpecIdAndFieldSpecIds(companyId, serviceCallConstants);
		getDocumentVerificationFormSpecsAndFieldSpecs(companyId,
				serviceCallConstants);
		getGlobalCompanyFormSpecsAndFieldSpecs(companyId, serviceCallConstants);

		return serviceCallConstants;

	}

	public void updateFormFieldValueOfCustomer(Form form,
			ServiceCallConstants serviceCallConstants) {
		// ServiceCallConstants serviceCallConstants =
		// webUser.getServiceCallConstants();
		if (serviceCallConstants != null) {
			/*
			 * Long outStandingAmountFieldSpecId = serviceCallConstants
			 * .getService_call_customer_form_outstanding_amount_form_field_spec_id
			 * ();
			 */
			Long outStandingAmountFieldSpecId = serviceCallConstants
					.getGlobal_company_customer_outstanding_amount_form_field_spec_id();

			if (serviceCallConstants
					.getService_call_service_rendered_form_spec_id() != null
					&& (serviceCallConstants
							.getService_call_service_rendered_form_spec_id()
							.longValue() == form.getFormSpecId())) {
				Long customerFieldSpecId = serviceCallConstants
						.getService_call_service_rendered_customer_form_field_spec_id();
				Long grandTotalFieldSpecId = serviceCallConstants
						.getService_call_service_rendered_grand_total_form_field_spec_id();

				updateFormField(customerFieldSpecId, grandTotalFieldSpecId,
						outStandingAmountFieldSpecId, form, 1);

			} else if (serviceCallConstants
					.getService_call_payment_collection_and_feedback_form_spec_id() != null
					&& (serviceCallConstants
							.getService_call_payment_collection_and_feedback_form_spec_id()
							.longValue() == form.getFormSpecId())) {
				Long customerFieldSpecId = serviceCallConstants
						.getService_call_payment_collection_and_feedback_customer_form_field_spec_id();
				Long grandTotalFieldSpecId = serviceCallConstants
						.getService_call_payment_collection_and_feedback_payment_amount_form_field_spec_id();

				updateFormField(customerFieldSpecId, grandTotalFieldSpecId,
						outStandingAmountFieldSpecId, form, 2);
			} else if (serviceCallConstants.getSales_orderform_form_spec_id() != null
					&& (serviceCallConstants.getSales_orderform_form_spec_id()
							.longValue() == form.getFormSpecId())) {

				Long customerFieldSpecId = serviceCallConstants
						.getSales_orderform_customer_form_field_spec_id();
				Long totalFieldSpecId = serviceCallConstants
						.getSales_orderform_total_amount_form_field_spec_id();

				updateFormFieldForSales(customerFieldSpecId, totalFieldSpecId,
						outStandingAmountFieldSpecId, form, 1);
			} else if (serviceCallConstants
					.getSales_payment_collection_form_spec_id() != null
					&& (serviceCallConstants
							.getSales_payment_collection_form_spec_id()
							.longValue() == form.getFormSpecId())) { // for
																		// sales

				Long customerFieldSpecId = serviceCallConstants
						.getSales_payment_collection_customer_form_field_spec_id();
				Long totalFieldSpecId = serviceCallConstants
						.getSales_payment_collection_payment_amount_form_field_spec_id();

				updateFormFieldForSales(customerFieldSpecId, totalFieldSpecId,
						outStandingAmountFieldSpecId, form, 2);
			} else if (serviceCallConstants
					.getCollection_payment_collection_form_spec_id() != null
					&& (serviceCallConstants
							.getCollection_payment_collection_form_spec_id()
							.longValue() == form.getFormSpecId())) {// for
																	// collection
																	// product
																	// line
				/*
				 * Long lastServiceDateFieldSpecId = serviceCallConstants .
				 * getService_call_customer_form_last_service_date_form_field_spec_id
				 * ();
				 */
				Long customerFieldSpecId = serviceCallConstants
						.getCollection_payment_collection_customer_form_field_spec_id();
				Long totalFieldSpecId = serviceCallConstants
						.getCollection_payment_collection_amount_payed_form_field_spec_id();

				updateFormFieldForCollections(customerFieldSpecId,
						totalFieldSpecId, outStandingAmountFieldSpecId, form, 2);// subtract
			}

			else if (serviceCallConstants.getDelivery_delivery_form_spec_id() != null
					&& (serviceCallConstants
							.getDelivery_delivery_form_spec_id().longValue() == form
							.getFormSpecId())) {// for delivery product line
				Long paymentAmountFieldSpecId = serviceCallConstants
						.getDelivery_amount_form_section_field_spec_id();
				Long customerFieldSpecId = serviceCallConstants
						.getDelivery_customer_form_field_spec_id();
				Long orderFieldSpecId = serviceCallConstants
						.getDelivery_amount_form_field_spec_id();

				updateFormFieldForDelivery(customerFieldSpecId,
						orderFieldSpecId, outStandingAmountFieldSpecId, form, 2);// subtract
			}

		}
	}
	
	private void updateFormFieldForSales(Long customerFieldSpecId,
			Long grandTotalFieldSpecId, Long outStandingAmountFieldSpecId,
			Form form, int type) {
		String customerId = extraDao.getFormFieldValue(customerFieldSpecId,
				form.getFormId(),true);
		String grandTotal = extraDao.getFormFieldValue(grandTotalFieldSpecId,
				form.getFormId(),true);
		Customer customer = getCustomer(customerId);
		Form customerForm = extraSupportAdditionalDao.getMasterForm(customer.getCustomerFormId());
		String outStandingAmount = extraDao.getFormFieldValue(
				outStandingAmountFieldSpecId, customer.getCustomerFormId(),true);

		Double value = 0.0;

		if (outStandingAmount != null) {
			value = (Double.parseDouble(outStandingAmount));
		}

		if (type == 1) {
			value = value + (Double.parseDouble(grandTotal));
		} else if (type == 2) {
			value = value - Double.parseDouble(grandTotal);
		}

		List<FormField> formFields = extraSupportAdditionalDao.getMasterFormFieldsByFormId(customerForm.getFormId());

		Map<Long, FormField> formFieldsMap = (Map) Api.getMapFromList(
				formFields, "fieldSpecId");
		FormField formField = formFieldsMap.get(outStandingAmountFieldSpecId);
		if (formField != null) {
			updateFieldValueOfOutstandingAmount(formField.getFieldSpecId(),
					value + "", customerForm.getFormId());
			// extraDao.updateForm(customerForm, customerForm.getFilledBy());
		} else {
			List<FormField> formFieldList = new ArrayList<FormField>();
			formField = new FormField();
			formField.setFieldSpecId(outStandingAmountFieldSpecId);
			formField.setFormSpecId(customerForm.getFormSpecId());
			formField.setFormId(customerForm.getFormId());
			formField.setFieldValue("" + value);
			formFieldList.add(formField);
			extraDao.insertFormFields(formFieldList,true,false,false);
		}
		extraDao.updateCustomersModifiedTime(customer.getCustomerId());

		sendEmailForService(customer, form);
	}
	private void updateFieldValueOfOutstandingAmount(long fieldSpecId,
			String value, long formId) {
		extraDao.updateFormFieldValue(fieldSpecId, value, formId,true);
	}
	
	private void updateFormField(Long customerFieldSpecId,
			Long grandTotalFieldSpecId, Long outStandingAmountFieldSpecId,
			Form form, int type) {
		String customerId = extraDao.getFormFieldValue(customerFieldSpecId,form.getFormId(),true);
		String grandTotal = extraDao.getFormFieldValue(grandTotalFieldSpecId,form.getFormId(),true);
		Customer customer = getCustomer(customerId);
		Form customerForm = extraSupportAdditionalDao.getMasterForm(customer.getCustomerFormId());
		String outStandingAmount = extraDao.getFormFieldValue(outStandingAmountFieldSpecId, 
				customer.getCustomerFormId(),true);

		Double value = 0.0;

		if (outStandingAmount != null) {
			value = (Double.parseDouble(outStandingAmount));
		}

		if (type == 1) {
			value = value + (Double.parseDouble(grandTotal));
		} else if (type == 2) {
			value = value - Double.parseDouble(grandTotal);
		}

		List<FormField> formFields = extraSupportAdditionalDao.getMasterFormFieldsByFormId(customerForm.getFormId());

		Map<Long, FormField> formFieldsMap = (Map) Api.getMapFromList(
				formFields, "fieldSpecId");
		FormField formField = formFieldsMap.get(outStandingAmountFieldSpecId);
		if (formField != null) {
			updateFieldValueOfOutstandingAmount(formField.getFieldSpecId(),
					value + "", customerForm.getFormId());
			// extraDao.updateForm(customerForm, customerForm.getFilledBy());
		} else {
			List<FormField> formFieldList = new ArrayList<FormField>();
			formField = new FormField();
			formField.setFieldSpecId(outStandingAmountFieldSpecId);
			formField.setFormSpecId(customerForm.getFormSpecId());
			formField.setFormId(customerForm.getFormId());
			formField.setFieldValue("" + value);
			formFieldList.add(formField);
			extraDao.insertFormFields(formFieldList,true,false,false);
		}
		extraDao.updateCustomersModifiedTime(customer.getCustomerId());

		sendEmailForService(customer, form);
	}
	public void sendEmailForService(Customer customer, Form form) {

		FormSpec formSpec = getFormSpec("" + form.getFormSpecId());
		String htmlData = getResolvedEmailTemplateDataForFrom(form, formSpec);

		if (!Api.isEmptyString(htmlData)) {
			Employee filledByEmployee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId("" + form.getFilledBy());
			Employee managerEmployee = null;
			List<String> email = new ArrayList<String>();

			if (filledByEmployee.getManagerId() != null) {
				managerEmployee = getEmployee(""
						+ filledByEmployee.getManagerId());
				email.add(managerEmployee.getEmpEmail());
			}

			email.add(customer.getPrimaryContactEmail());
			try {
				mailTask.sendMail(filledByEmployee.getManagerId() + "",
						Api.toCSV1(email), formSpec.getFormTitle(), htmlData,
						Mail.BODY_TYPE_HTML, customer.getCompanyId()+"", false,Mail.WEBSITE_RELATED);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void processNewLeadFormToCreateCustomer(Long formId,
			Integer companyId, WebUser webUser) throws EffortError {

		Log.info(this.getClass(),
				" processNewLeadFormToCreateCustomer for formId:" + formId
						+ " companyId:" + companyId);

		/*
		 * Long
		 * newLeadSkeletonFormSPecId=constants.getSales_new_leads_form_spec_id
		 * ();
		 * 
		 * 
		 * FormSpec
		 * newLeadFormSpec=extraDao.getCompaniesNewLeadFormSpecId(companyId,
		 * newLeadSkeletonFormSPecId);
		 */

		Form newLeadForm = getForm(formId + "");

		List<FormFieldSpec> formFieldSpecs = getFormFieldSpecs(newLeadForm
				.getFormSpecId());

		Map<Long, Long> skeletonFormFieldSpecMap = new HashMap<Long, Long>();

		for (FormFieldSpec formFieldSpec : formFieldSpecs) {

			if (formFieldSpec.getSkeletonFormFieldSpecId() == constants
					.getSales_new_leads_name_form_field_spec_id().longValue()) {
				skeletonFormFieldSpecMap.put(
						constants.getSales_new_leads_name_form_field_spec_id(),
						formFieldSpec.getFieldSpecId());
			} else if (formFieldSpec.getSkeletonFormFieldSpecId() == constants
					.getSales_new_leads_primary_contact_email_form_field_spec_id()
					.longValue()) {
				skeletonFormFieldSpecMap
						.put(constants
								.getSales_new_leads_primary_contact_email_form_field_spec_id(),
								formFieldSpec.getFieldSpecId());
			} else if (formFieldSpec.getSkeletonFormFieldSpecId() == constants
					.getSales_new_leads_primary_contact_estimated_value_form_field_spec_id()
					.longValue()) {
				skeletonFormFieldSpecMap
						.put(constants
								.getSales_new_leads_primary_contact_estimated_value_form_field_spec_id(),
								formFieldSpec.getFieldSpecId());
			} else if (formFieldSpec.getSkeletonFormFieldSpecId() == constants
					.getSales_new_leads_primary_contact_first_name_form_field_spec_id()
					.longValue()) {
				skeletonFormFieldSpecMap
						.put(constants
								.getSales_new_leads_primary_contact_first_name_form_field_spec_id(),
								formFieldSpec.getFieldSpecId());
			} else if (formFieldSpec.getSkeletonFormFieldSpecId() == constants
					.getSales_new_leads_primary_contact_interested_products_form_field_spec_id()
					.longValue()) {
				skeletonFormFieldSpecMap
						.put(constants
								.getSales_new_leads_primary_contact_interested_products_form_field_spec_id(),
								formFieldSpec.getFieldSpecId());
			} else if (formFieldSpec.getSkeletonFormFieldSpecId() == constants
					.getSales_new_leads_primary_contact_last_name_form_field_spec_id()
					.longValue()) {
				skeletonFormFieldSpecMap
						.put(constants
								.getSales_new_leads_primary_contact_last_name_form_field_spec_id(),
								formFieldSpec.getFieldSpecId());
			} else if (formFieldSpec.getSkeletonFormFieldSpecId() == constants
					.getSales_new_leads_primary_contact_phone_form_field_spec_id()
					.longValue()) {
				skeletonFormFieldSpecMap
						.put(constants
								.getSales_new_leads_primary_contact_phone_form_field_spec_id(),
								formFieldSpec.getFieldSpecId());
			}

		}

		List<FormField> newLeadFormFields = extraDao.getFormFieldByForm(formId);

		if (newLeadFormFields != null && !newLeadFormFields.isEmpty()) {
			Customer customer = new Customer();

			customer.setCompanyId(companyId);
			for (FormField formField : newLeadFormFields) {
				if (formField.getFieldSpecId() == skeletonFormFieldSpecMap
						.get(constants
								.getSales_new_leads_name_form_field_spec_id())) {

					customer.setCustomerName(formField.getFieldValue());

				} else if (formField.getFieldSpecId() == skeletonFormFieldSpecMap
						.get(constants
								.getSales_new_leads_primary_contact_email_form_field_spec_id())
						.longValue()) {

					customer.setPrimaryContactEmail(formField.getFieldValue());

				} else if (formField.getFieldSpecId() == skeletonFormFieldSpecMap
						.get(constants
								.getSales_new_leads_primary_contact_first_name_form_field_spec_id())
						.longValue()) {

					customer.setPrimaryContactFirstName(formField
							.getFieldValue());

				} else if (formField.getFieldSpecId() == skeletonFormFieldSpecMap
						.get(constants
								.getSales_new_leads_primary_contact_last_name_form_field_spec_id())
						.longValue()) {

					customer.setPrimaryContactLastName(formField
							.getFieldValue());

				} else if (formField.getFieldSpecId() == skeletonFormFieldSpecMap
						.get(constants
								.getSales_new_leads_primary_contact_phone_form_field_spec_id())
						.longValue()) {

					customer.setPrimaryContactPhone(formField.getFieldValue());

				}

			}

			long customerFormSpecId = extraDao.getFormSpecIdFromSkeleton(
					constants.getGlobal_company_customer_form_spec_id(),
					companyId);
			// FormSpec formSpec=getFormSpec(""+customerFormSpecId);
			FormAndField formAndField = new FormAndField();
			formAndField.setFormSpecId(customerFormSpecId);
			List<JmsMessage> jmsMessages = new ArrayList<JmsMessage>();
			addForm(formAndField, webUser, null, null, null,"",jmsMessages);
			if (jmsMessages != null && !jmsMessages.isEmpty()) {
				for (JmsMessage message : jmsMessages) {
					try {
						getMainManager().sendJMSMessage(message);
					} catch (Exception e) {
						Log.info(this.getClass(), "JMS Message : " + message + e.toString(), e);
						e.printStackTrace();
					}
				}
			}

			customer.setCustomerFormId(formAndField.getFormId());
			customer.setParentFormId(formId);
			customer.setCustomerCreatedBy(webUser.getEmpId());
			customer.setCustomerModifiedBy(webUser.getEmpId());
			insertCustomer(customer);

		}

	}

	public long insertCustomer(Customer customer) {
		return extraDao.insertCustomer(customer, null);

	}

	
}
