package com.effort.manager;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.Map;



import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.effort.entity.*;
import com.effort.beans.http.request.location.Location;
import com.effort.context.AppContext;
import com.effort.dao.AuditDao;
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
import com.effort.entity.CustomEntityFilteringCritiria;
import com.effort.entity.Customer;
import com.effort.entity.CustomerAutoFilteringCritiria;
import com.effort.entity.CustomerField;
import com.effort.entity.EffortAccessSettings;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeAccessSettings;
import com.effort.entity.EmployeeEntityMap;
import com.effort.entity.EmployeeGroup;
import com.effort.entity.Entity;
import com.effort.entity.EntityField;
import com.effort.entity.EntityFieldSpec;
import com.effort.entity.FieldSpecRestrictionGroup;
import com.effort.entity.FieldValidationCritiria;
import com.effort.entity.Form;
import com.effort.entity.FormAndField;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormFieldSpecValidValue;
import com.effort.entity.FormFilteringCritiria;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSectionSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.JmsMessage;
import com.effort.entity.JobCreateOrModifiNotify;
import com.effort.entity.LabelValue;
import com.effort.entity.ListFilteringCritiria;
import com.effort.entity.PermissionSet;
import com.effort.entity.Route;
import com.effort.entity.VisibilityDependencyCriteria;
import com.effort.entity.WebUser;
import com.effort.entity.WorkFlowFormStatus;
import com.effort.entity.WorkFlowFormStatusHistory;
import com.effort.entity.Workflow;
import com.effort.entity.WorkflowEntityMap;
import com.effort.entity.WorkflowStage;
import com.effort.exception.EffortError;
import com.effort.expression.EvalVisitor;
import com.effort.expression.ExprLexer;
import com.effort.expression.ExprParser;
import com.effort.mail.Mail;
import com.effort.mail.MailTask;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.settings.ReportConstants;
import com.effort.settings.ServiceCallConstants;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Api.CsvOptions;
import com.effort.util.Api.DateConversionType;
import com.effort.util.ContextUtils;
import com.effort.util.Log;
import com.effort.validators.FormValidator;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Session;


import org.springframework.jms.core.JmsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;



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
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private MailTask mailTask;
	
	@Autowired
	private AuditDao auditDao;
	
	private ComputeFieldsManager getComputeFieldsManager() {
		ComputeFieldsManager computeFieldsManager =  AppContext.getApplicationContext().getBean("computeFieldsManager",ComputeFieldsManager.class);
	    return computeFieldsManager;
	}
	
	private BulkActionPerformManager getBulkActionPerformManager(){
		BulkActionPerformManager bulkActionPerformManager = AppContext.getApplicationContext().getBean("bulkActionPerformManager",BulkActionPerformManager.class);
		return bulkActionPerformManager;
	}
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
	
	private MainManager getMainManager(){
		MainManager mainManager = AppContext.getApplicationContext().getBean("mainManager",MainManager.class);
		return mainManager;
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
	
public void sendJmsMessage(String jmsDestination, final Serializable message) {
		
		String logText = "sendJmsMessage() ";
		if (constants.isPrintLogs()) {
			Log.info(getClass(), logText+" sending ... logText = "+Api.getLogText());
		}else {
			Log.info(getClass(), logText+" sending ...");	
		}
		try {
			jmsTemplate.send(jmsDestination, new MessageCreator() {

				public Message createMessage(Session session)
						throws JMSException {
					ObjectMessage om = session.createObjectMessage(message);
					return om;
				}

			});

			Log.info(this.getClass(), ">>>Meaasge send: " + message);
		} catch (Exception e) {
			Log.error(this.getClass(), " Exception occucred : "+e.toString(), e);
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
	
	public List<FormSectionField> getFormSectionFields(long formId) {
		return extraDao.getFormSectionFieldsForForm(formId);
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
	
	
	public void createOrUpdateWorkflowForForm(Form form, WebUser webUser)
			throws EffortError {
		FormSpec formSpec = getFormSpec("" + form.getFormSpecId());
		/*Long workflowId = workFlowExtraDao
				.getWorkflowIdIfExistFromWorkflowEntityMap(
						formSpec.getUniqueId(),
						WorkflowEntityMap.WORKFLOW_ENTITY_TYPE_FORM);*/
		
		Long workflowId = getWorkFlowManager().getWorkflowIdByEntityIdAndType(form.getFormId()+"", formSpec.getUniqueId(), WorkflowEntityMap.WORKFLOW_ENTITY_TYPE_FORM);
		if(workflowId == null) {
			Log.info(getClass(), "createOrUpdateWorkflowForForm() formId="+form.getFormId()+" no workflow found"); 
			return;
		}
		List<WorkflowStage> workflowStageList = new ArrayList<WorkflowStage>();
		Workflow workflow = workFlowExtraDao.getWorkFlow(workflowId);
		try {
			workflowStageList = workFlowExtraDao.getWorkFlowStages("" + workflow.getWorkflowId());
		} catch (Exception e) {
			workflowStageList = null;
		}
		Employee employee = null;
		if (form.getAssignTo() != null) {
			employee = getWebExtensionManager().getEmployeeBasicDetailsAndTimeZoneByEmpId("" + form.getAssignTo());
		} else {
			employee = getWebExtensionManager().getEmployeeBasicDetailsAndTimeZoneByEmpId("" + webUser.getEmpId());
		}
		/*WorkFlowFormStatus workFlowFormStatus = workFlowExtraDao
				.getWorkFlowFormStatus(form.getFormId());*/
		WorkFlowFormStatus workFlowFormStatus = workFlowExtraDao
				.getWorkFlowFormStatusByFormIdOnly(form.getFormId());
		WorkflowStage workflowStage = null;
		if (workflowStageList != null) {
			workflowStage = workflowStageList.get(0);

			if (workFlowFormStatus == null) {
				workFlowFormStatus = new WorkFlowFormStatus();
				workFlowFormStatus.setWorkFlowId(workflow.getWorkflowId());
				workFlowFormStatus.setWorkFlowName(workflow.getWorkflowName());
				workFlowFormStatus.setFormSpecId(form.getFormSpecId());
				workFlowFormStatus.setFormId(form.getFormId());
				workFlowFormStatus
						.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);

				populateWorkflowFormStatus(workFlowFormStatus,
						"" + form.getFormId(), webUser.getCompanyId(),
						workflowStage, employee, webUser.getEmpId());
				workFlowExtraDao.insertWorkflowStatus(workFlowFormStatus);

				if (workFlowFormStatus.getHistoryId() != null) {
					sendJmsMessage(JmsMessage.TYPE_WORK_FLOW,
							workFlowFormStatus.getHistoryId(),
							webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(), null, false);
					CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus = new CustomerRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus = new CustomEntityRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus = new WorkRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus = new EmployeeRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					getWebExtensionManager().getAllReqConfiurations(workFlowFormStatus.getFormSpecId(),customerRequisitionJmsMessageStatus,customEntityRequisitionJmsMessageStatus,workRequisitionJmsMessageStatus,employeeRequisitionJmsMessageStatus);
					if(webUser.getCompanyId().longValue() == reportConstants.getCdsgCompanyId()) {
						  extraSupportAdditionalDao.insertWorkFlowForCdsgStockUpdate(workFlowFormStatus.getId(), webUser.getCompanyId(), JmsMessage.TYPE_WORK_FLOW, -1, 0);
					  }
				} else {
					sendJmsMessage(JmsMessage.TYPE_WORK_FLOW,
							JmsMessage.NO_WORKFLOW_HISTORY_FOUND,
							webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(), null, false);
					CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus = new CustomerRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus = new CustomEntityRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus = new WorkRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus = new EmployeeRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					getWebExtensionManager().getAllReqConfiurations(workFlowFormStatus.getFormSpecId(),customerRequisitionJmsMessageStatus,customEntityRequisitionJmsMessageStatus,workRequisitionJmsMessageStatus,employeeRequisitionJmsMessageStatus);
					
					if(webUser.getCompanyId().longValue() == reportConstants.getCdsgCompanyId()) {
						  extraSupportAdditionalDao.insertWorkFlowForCdsgStockUpdate(workFlowFormStatus.getId(), webUser.getCompanyId(), JmsMessage.TYPE_WORK_FLOW, -1, 0);
					  }
				}

			} else {
				
				
				
				if(workflowId != null && workflowId.longValue() != workFlowFormStatus.getWorkFlowId().longValue()) {
					boolean reinitiated = getWebAdditionalSupportManager().reinitateFormApprovals(workFlowFormStatus.getFormId()+"", webUser,true,workflowId,false);
					Log.info(getClass(), "insertOrUpdateWorkFlowFormStatus() // as workFlow formId changed detected, reintiating for formId = "+workFlowFormStatus.getFormId()
					  +" reinitiation status = "+reinitiated+" old workflowId = "+workFlowFormStatus.getWorkFlowId()+" new workflowId = "+workflowId);
					return;
				}
				
				workFlowFormStatus
						.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);

				populateWorkflowFormStatus(workFlowFormStatus,
						"" + form.getFormId(), webUser.getCompanyId(),
						workflowStage, employee, webUser.getEmpId());
				workFlowExtraDao
						.updateWorkFlowFormStatusForform(workFlowFormStatus);
				if (workFlowFormStatus.getHistoryId() != null) {
					sendJmsMessage(JmsMessage.TYPE_WORK_FLOW,
							workFlowFormStatus.getHistoryId(),
							webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(), null, false);
					CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus = new CustomerRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus = new CustomEntityRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus = new WorkRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus = new EmployeeRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					getWebExtensionManager().getAllReqConfiurations(workFlowFormStatus.getFormSpecId(),customerRequisitionJmsMessageStatus,customEntityRequisitionJmsMessageStatus,workRequisitionJmsMessageStatus,employeeRequisitionJmsMessageStatus);
					
					if(webUser.getCompanyId().longValue() == reportConstants.getCdsgCompanyId()) {
						  extraSupportAdditionalDao.insertWorkFlowForCdsgStockUpdate(workFlowFormStatus.getId(), webUser.getCompanyId(), JmsMessage.TYPE_WORK_FLOW, -1, 0);
					  }
				} else {
					sendJmsMessage(JmsMessage.TYPE_WORK_FLOW,
							JmsMessage.NO_WORKFLOW_HISTORY_FOUND,
							webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(), null, false);
					CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus = new CustomerRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus = new CustomEntityRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus = new WorkRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus = new EmployeeRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, webUser.getCompanyId(), webUser.getEmpId(), -1,
							workFlowFormStatus.getId(),false,workFlowFormStatus.getFormId());
					getWebExtensionManager().getAllReqConfiurations(workFlowFormStatus.getFormSpecId(),customerRequisitionJmsMessageStatus,customEntityRequisitionJmsMessageStatus,workRequisitionJmsMessageStatus,employeeRequisitionJmsMessageStatus);
					if(webUser.getCompanyId().longValue() == reportConstants.getCdsgCompanyId()) {
						  extraSupportAdditionalDao.insertWorkFlowForCdsgStockUpdate(workFlowFormStatus.getId(), webUser.getCompanyId(), JmsMessage.TYPE_WORK_FLOW, -1, 0);
					  }
				}

			}
			
			// here... 
			//invalidating.... previous history....
			List<Long> stageIdsToIgnore = new ArrayList<Long>();
			for(WorkflowStage stage : workflowStageList) {
				if(stage.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED && stage.getApprovalType() == 1) {
					stageIdsToIgnore.add(stage.getStageId());
				}
			}
			getExtendedDao().updateWorkFlowFormStatusInvalidateHistory(workFlowFormStatus.getFormId(),Api.toCSV(stageIdsToIgnore));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void modifyForm(Form form, FormAndField formAndField,
			WebUser webUser, String saveHidden, String followupJobJson, ExtraProperties extraProperties)
			throws EffortError {

		FormSpec formSpec = getFormSpec("" + formAndField.getFormSpecId());
		List<FormFieldSpec> formFieldSpecs = getFormFieldSpecs(formAndField
				.getFormSpecId());
		List<FormSectionSpec> formSectionSpecs = getFormSectionSpecs(formAndField
				.getFormSpecId());
		List<FormSectionFieldSpec> formSectionFieldSpec = extraDao
				.getFormSectionFieldSpecForIn(formSectionSpecs);
		form.setModifiedBy(webUser.getEmpId());
		int formSpecPurpose = formSpec.getPurpose();
		
		if (formSpecPurpose == FormSpec.PURPOUSE_WORK) 
		{
			String value = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkEmployeeFieldSpecId());
			if (!Api.isEmptyString(value)) {
				form.setAssignTo(Long.parseLong(value));
			}else{
				form.setAssignTo(null);
			}
		}else{
			if (!Api.isEmptyString(formAndField.getAssignTo())) {
				form.setAssignTo(Long.parseLong(formAndField.getAssignTo()));
			} else {
				form.setAssignTo(null);
			}
		}

		boolean isMasterForm = true;
		List<Location> locations = new ArrayList<Location>();
		long saveLocationId = 0;
		try {
			Form formOld = null;
			if(formSpec.getPurpose() == -1 || formSpec.getPurpose() == 0 ||
					formSpec.getPurpose() == FormSpec.PURPOUSE_WORK ||
					formSpec.getPurpose() == FormSpec.PURPOUSE_JOB) {
				isMasterForm = false;
				formOld = extraDao.getForm(form.getFormId() + "");
			}else {
				formOld = extraSupportAdditionalDao.getMasterForm(form.getFormId());
			}

			boolean clifu = constants.getCaptureLocationInFormUpdate(webUser
					.getCompanyId());
			if (!clifu) {
				form.setLocationId(formOld.getLocationId());
			}

			if(formSpec.getPurpose() != FormSpec.PURPOUSE_CUSTOM_ENTITY) {
				if (!Api.isEqualLong(formOld.getAssignTo(), form.getAssignTo())) {
					if (formOld.getAssignTo() != null) {
						extraDao.deleteAssignAway(ExtraDao.ELEMENT_FORM,
								form.getFormId(), formOld.getAssignTo());
						extraDao.insertAssignAway(ExtraDao.ELEMENT_FORM,
								form.getFormId(), formOld.getAssignTo());
					}

					if (form.getAssignTo() != null) {
						extraDao.deleteAssignAway(ExtraDao.ELEMENT_FORM,
								form.getFormId(), form.getAssignTo());
					}
				}
			}

			
			Location location = new Location();

			if(formAndField.getFields() != null){
				FormField formField = new FormField();
				Iterator<FormField> iter = formAndField.getFields().iterator();
				while(iter.hasNext()) {
					formField = iter.next();
					if(formField != null && formField.getLattitude() !=null && formField.getLongitude() !=null){
						location.setLatitude(formField.getLattitude());
						location.setLongitude(formField.getLongitude());
						location.setEmpId(webUser.getEmpId());
						location.setCompanyId(webUser.getCompanyId());
						location.setPurpose(Location.FOR_FORM);
					}
					if(location != null && location.getLatitude()!=null && location.getLongitude()!=null && location.getLatitude() > 0 && location.getLongitude() > 0)
					{

						long formsAddedLocationCurrentTime = Api.getCurrentTimeInUTCLong();
						saveLocationId = trackDao.saveLocation(location);

						if (location != null) {
							locations.add(location);
						}

						if (constants.isPrintLogs())
							Log.info(this.getClass(),
									"processOnlineFormSubmission - formsAddedLocationCurrentTime End. Time taken to process for empId "
											+ webUser.getEmpId() + " : "
											+ (Api.getCurrentTimeInUTCLong() - formsAddedLocationCurrentTime));

						if (locations != null && locations.size() > 0) {
							Location lastLocation = locations.get(locations.size() - 1);
							form.setLocation(lastLocation);
						}
						break;
					}
				}
			}
			
			Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
					"form", "modified", form.toCSV(), formOld.toCSV());
			
			List<FormField> formFields = new ArrayList<FormField>();
			if(isMasterForm) {
				formFields = extraSupportAdditionalDao.getMasterFormFieldsByFormId(form.getFormId());
			}else {
				formFields = extraDao.getFormFieldByForm(form.getFormId());
			}
		
			Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
					"formFields", "deleted", formFields.toString(), null);

			List<FormSectionField> formSectionFields = extraDao
					.getFormSectionFieldsForForm(form.getFormId());
			Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
					"formSectionFields", "deleted",
					formSectionFields.toString(), null);
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
		}

		form.setFormSpecId(formAndField.getFormSpecId());
		form.setDraftForm(formAndField.getDraftForm() == 0?0:1);
		if (formAndField != null) {
			form.setFormFieldsUniqueKey(formAndField.getFormFieldsUniqueKey());
		}
		extraDao.updateForm(form, webUser.getEmpId(),isMasterForm,form.getAppVersion());
		
		if(extraProperties!= null && extraProperties.getIsWebLiteSignIn()) {
			 if(saveLocationId > 0) {
					String startTime = Api.getCurrentLocalTime("-330","yyyy-MM-dd HH:mm:ss");
					StartWorkStopWorkLocations startWorkStopWorkLocation = new StartWorkStopWorkLocations();
					startWorkStopWorkLocation.setStartWorkLocationId(saveLocationId+"");
					startWorkStopWorkLocation.setStartWorkLocationTime(startTime);
					startWorkStopWorkLocation.setSignInFormId(form.getFormId());
					startWorkStopWorkLocation.setSignInSource(StartWorkStopWorkLocations.SIGNED_IN_WEBLITE);
					getWebExtensionManager().insertStartWorkStopWorkLocations(startWorkStopWorkLocation,webUser.getEmpId(),webUser.getCompanyId());
				}
		}
		if(extraProperties!= null && extraProperties.getIsWebLiteSignOut()) {
			 if(saveLocationId > 0) {
					String startTime = Api.getCurrentLocalTime("-330","yyyy-MM-dd HH:mm:ss");
					StartWorkStopWorkLocations startWorkStopWorkLocation = new StartWorkStopWorkLocations();
					StartWorkStopWorkLocations startWorkStopWorkLocationFromDB = extraDao.getStartWorkStopWorkLocationsForEmpIdAndCustId(webUser.getEmpId());
					if(startWorkStopWorkLocationFromDB != null) {
					startWorkStopWorkLocation.setStopWorkLocationId(saveLocationId+"");
					startWorkStopWorkLocation.setStopWorkLocationTime(startTime);
					startWorkStopWorkLocation.setSignOutFormId(form.getFormId());
					startWorkStopWorkLocation.setSignOutSource(StartWorkStopWorkLocations.SIGNOUT_IN_WEBLITE);
					if(extraProperties.isAutoSignOut()) {
						startWorkStopWorkLocation.setAutoStopWork(1);
					}else {
						startWorkStopWorkLocation.setAutoStopWork(1);
					}
					getExtendedDao().updateStopWorkLocationForStartWorkLocation(startWorkStopWorkLocation,startWorkStopWorkLocationFromDB.getId());
					if(!Api.isEmptyString(startWorkStopWorkLocation.getStartWorkLocationTime())) {
					getWebExtensionManager().updateEmployeeDeviceStartAndStopWorkTimes(webUser.getEmpId(),
							startWorkStopWorkLocation.getStartWorkLocationTime(),
							startWorkStopWorkLocation.getStopWorkLocationTime());
					}
					}
			 }
		}

		
		List<Location> formLocationsNew = new ArrayList<Location>();
		for (Location loc : locations) {
			if (loc.getLocationId() != 0) {
				getExtendedDao().insertWebLocations(loc.getLocationId(), webUser.getBrowserInfo(),webUser.getIpAddress());
				formLocationsNew.add(loc);
			}
		}

		if (formLocationsNew.size() > 0) {
			extraDao.insertFormLocationList(formLocationsNew, form.getFormId(), 1);
		}
		if(!formAndField.isIgnoreComputedFieldsComputation()) {
		getComputeFieldsManager(). computeFields(formAndField.getFields(),
				formAndField.getSectionFields(), formAndField.getFormSpecId(), webUser,false,false,formAndField);
		}

		if (formSpecPurpose == FormSpec.PURPOUSE_CUSTOM_ENTITY) {
			getWebAdditionalSupportExtraManager().canProcessEmpMasterMappingConfigurationWhenMasterModified(
					Long.parseLong(formAndField.getCustomEntityId()),form.getFormId(),formAndField.getCustomEntitySpecId(),
					EmpMasterMappingStatus.CUSTOM_ENTITY_EVENT_TRIGGER, 
					formAndField.getFields(), webUser);

		}
		getExtraSupportAdditionalSupportDao().deleteRichTextFormFields(form.getFormId());
		extraDao.deleteFormCustomersForForm(form.getFormId());
		extraSupportAdditionalDao.deleteFormCustomEntitiesForForm(form.getFormId());
		extraDao.deleteFormFields(form.getFormId(),isMasterForm);
		resolveFormFields(formSpec, formFieldSpecs, formSectionSpecs,
				formAndField, Form.FORM_ACTION_SAVE_OR_UPDATE_TO_DB, webUser);
		if (formAndField.getFields() != null) {
			Iterator<FormField> formFieldIterator = formAndField.getFields()
					.iterator();
			while (formFieldIterator.hasNext()) {
				FormField formField = formFieldIterator.next();
				if (Api.isEmptyString(formField.getFieldValue()) || formField.getFieldSpecId() <= 0) {
					formFieldIterator.remove();
				}
			}

			for (FormField formField : formAndField.getFields()) {
				formField.setFormSpecId(form.getFormSpecId());
				Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
						"formField", "added", formField.toCSV(), null);
			}
			
			FormField autoGenSeqFormField = null;
			if(formAndField.getSectionFields() != null) {
				autoGenSeqFormField = getWebSupportManager().getAutoGenFormField(form, formAndField.getFields(), formSpec,
						webUser.getTzo()+"",isMasterForm,formAndField.getSectionFields());
			}else {
				autoGenSeqFormField = getWebSupportManager().
						getAutoGenFormField(form, formAndField.getFields(), formSpec,webUser.getTzo()+"",isMasterForm,null);
			}
			
			if(autoGenSeqFormField != null) {
				Iterator<FormField> itr = formAndField.getFields().iterator();
				while (itr.hasNext()) {
					FormField faf = (FormField) itr.next();
					if (Constants.FORM_FIELD_TYPE_AUTOGENERATED == faf.getFieldType()){
						itr.remove();
					}
				}
				formAndField.getFields().add(autoGenSeqFormField);
			}
			
			getWebFunctionalManager().resolveRichTextFormFields(form, formAndField.getFields(), formSpec);
			extraDao.insertFormFields(formAndField.getFields(),isMasterForm,false,false);
		}
		
		getExtraSupportAdditionalSupportDao().deleteRichTextFormSectionFields(form.getFormId());
		extraDao.deleteFormSectionFields(form.getFormId(),isMasterForm);

		if (formAndField.getSectionFields() != null) {
			Iterator<FormSectionField> formSectionFieldIterator = formAndField
					.getSectionFields().iterator();
			while (formSectionFieldIterator.hasNext()) {
				FormSectionField formSectionField = formSectionFieldIterator
						.next();
				if (Api.isEmptyString(formSectionField.getFieldValue()) || formSectionField.getSectionFieldSpecId() <= 0) {
					formSectionFieldIterator.remove();
				}
			}

			for (FormSectionField formSectionField : formAndField
					.getSectionFields()) {
				formSectionField.setFormId(form.getFormId());
				formSectionField.setFormSpecId(form.getFormSpecId());
				Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
						"formSectionField", "added", formSectionField.toCSV(),
						null);
			}
			getWebFunctionalManager().resolveRichTextFormSectionFields(form, formAndField.getSectionFields(), formSpec);
			extraDao.insertFormSectionFields(formAndField.getSectionFields(),false,false,isMasterForm);
		}


		if ("save_approval".equalsIgnoreCase(saveHidden)) {
			createOrUpdateWorkflowForForm(form, webUser);
		}
		if (!Api.isEmptyString(followupJobJson)) {
			createFollowupVisitwhenFormFilled(form, webUser, followupJobJson);
		}

		if(formSpec.getPurpose() == -1)
		{
			CustomEntitySpecStockConfiguration customEntitySpecStockConfiguration = getExtraSupportAdditionalSupportDao().getCustomEntitySpecStockConfigurationByFormSpecUniqueId(formSpec.getUniqueId(),0);
			
			if(!Api.isEmptyObj(customEntitySpecStockConfiguration))
			{
				getExtraSupportAdditionalSupportDao().insertIntoCustomEntityCreationOnStockUpdation(form.getFormId(),customEntitySpecStockConfiguration);
			}
		}
		// FormSpec formSpec= getFormSpec(formAndField.getFormSpecId()+"");
		
		if (extraProperties != null) {
			
			if (!Api.isEmptyString(extraProperties.getWorkId())
					&& !Api.isEmptyString(extraProperties.getWorkActionSpecId())) {
				Work work = getWork("" + extraProperties.getWorkId());

				getWebAdditionalSupportExtraManager().insertOrUpdateWorkStatusHistory(extraProperties, form, webUser,
						work, formAndField);

				getWebAdditionalSupportExtraManager().updateFlatTableDataStatusForWorks(work, webUser.getCompanyId(),work.getWorkSpecId());
			}

		}
		try {
			if (extraProperties != null) {
				if (!Api.isEmptyString(extraProperties.getWorkActionType())
						&& ExtraProperties.WORK_FORM_TYPE_ATTACHMENT
								.equalsIgnoreCase(extraProperties.getWorkActionType())
						&& extraProperties.isFormInstance() && formAndField.getDraftForm() == 0) {
					createWorkAtachment(extraProperties.getWorkId(), form.getFormId());
				}
			}
		} catch (Exception e) {

		}


		if (formSpecPurpose == FormSpec.PURPOUSE_WORK) {

			modifyWork(formAndField, webUser, formSpec,null);
			
			

		}
		if (formSpecPurpose == FormSpec.PURPOUSE_CUSTOM_ENTITY) {
			
			CustomEntity customEntity = getWebAdditionalSupportManager().getCustomEntity(formAndField, webUser.getEmpId(), 
					formSpec,webUser.getCompanyId(),null);
			extraSupportDao.modifyCustomEntity(customEntity);
		}
		
		if((formSpecPurpose == 0 || formSpecPurpose == -1)) {
			sendJmsMessage(JmsMessage.TYPE_FORM_MEDIA_COMMITED, -1, webUser.getCompanyId(), 
					webUser.getEmpId(), JmsMessage.CHANGE_TYPE_OTHER,
					form.getFormId(), null, null);
		}
		
		try {
		    Log.info(this.getClass(), "addForm() // inside updateFlatTableDataStatus()");
            getWebAdditionalSupportExtraManager().updateFlatTableDataStatusForForms(form, webUser.getCompanyId());
        }
        catch(Exception ex) {
            Log.info(this.getClass(), "addForm() // inside updateFlatTableDataStatus() Got Exception :-"+ex.toString());
            ex.printStackTrace();
        }
	}
	

	public JobFormMapBean getJobFormDataMappingBasedOnFromSpec(int companyId,
			String formSpecUniqueId) {
		JobFormMapBean jobFormMapBean = null;
		try {
			jobFormMapBean = extraDao.getJobFormDataMappingBasedOnFromSpec(
					companyId, formSpecUniqueId);
		} catch (Exception e) {
		}
		return jobFormMapBean;
	}
	
	public List<VisitState> getVisitStatesForType(long typeId,
			boolean removeDefaults) {
		List<VisitState> visitStates = extraDao.getVisitStatesForType(typeId);

		if (removeDefaults) {
			Iterator<VisitState> visitStateIterator = visitStates.iterator();
			while (visitStateIterator.hasNext()) {
				VisitState visitState = (VisitState) visitStateIterator.next();
				if (visitState.isMakeDefault()) {
					visitStateIterator.remove();
				}
			}
		}

		return visitStates;
	}
	public String resolveAndGetFieldValue(FormField formField2,
			FormFieldSpec formFieldSpec) {
		if (formField2 != null) {
			formField2.setFieldLabel(formFieldSpec.getFieldLabel());
			formField2.setUniqueId(formFieldSpec.getUniqueId());
			switch (formFieldSpec.getFieldType()) {

			case Constants.FORM_FIELD_TYPE_YES_NO:
				if ("true".equalsIgnoreCase(formField2.getFieldValue())) {
					return "Yes";
				}
				return "No";
			case Constants.FORM_FIELD_TYPE_CUSTOMER:
				Customer customer = getCustomer(formField2.getFieldValue());
				formField2.setExternalId(customer.getExternalId());
				return customer.getCustomerName();
			case Constants.FORM_FIELD_TYPE_EMPLOYEE:
				Employee employee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(formField2.getFieldValue());
				// formField2.setExternalId(employee.getEmpNo());
				return employee.getEmpName();
			case Constants.FORM_FIELD_TYPE_SINGLE_SELECT_LIST:
				FormFieldSpecValidValue formFieldSpecValidValue = getFormFieldSpecValidValue(formField2
						.getFieldValue());
				return formFieldSpecValidValue.getValue();
			case Constants.FORM_FIELD_TYPE_MULTI_SELECT_LIST:
				List<FormFieldSpecValidValue> formFieldSpecValidValues = extraDao
						.getFormFieldSpecValidValueList(formField2
								.getFieldValue());
				return Api.toCSVFromList(formFieldSpecValidValues, "value");
			case Constants.FORM_FIELD_TYPE_LIST:
				List<Entity> entites = extraDao
						.getEntitiesForEntityIn(formField2.getFieldValue());
				if (!"null".equalsIgnoreCase(Api.toCSVFromList(entites,
						"externalId", "null"))) {
					formField2.setExternalId(Api.toCSVFromList(entites,
							"externalId", "null"));
				}
				return Api.toCSVFromList(entites, "identifier");
			case Constants.FORM_FIELD_TYPE_MULTIPICK_LIST:
				entites = extraDao.getEntitiesForEntityIn(formField2
						.getFieldValue());
				formField2.setExternalId(Api.toCSVFromList(entites,
						"externalId", "null"));
				return Api.toCSVFromList(entites, "identifier");

			default:
				return formField2.getFieldValue();
			}
		}
		return "";
	}
	
	public void createFollowupVisitwhenFormFilled(Form form, WebUser webUser,
			String followupJobJson) throws EffortError {
		FormSpec formSpec = getFormSpec("" + form.getFormSpecId());
		JobFormMapBean jobFormMapBean = getJobFormDataMappingBasedOnFromSpec(
				webUser.getCompanyId(), formSpec.getUniqueId());
		List<FormFieldSpec> formFieldSpecsForFollowUp = getFormFieldSpecs(form
				.getFormSpecId());
		Map<String, FormFieldSpec> formFieldMap = (Map) Api.getMapFromList(
				formFieldSpecsForFollowUp, "uniqueId");
		FormFieldSpec formFieldSpecForFollowUp = formFieldMap
				.get(jobFormMapBean.getDateFieldSpecUniqueId());
		FormFieldSpec formFieldSpecforTitle = formFieldMap.get(jobFormMapBean
				.getJobTitle());
		FormFieldSpec formFieldSpecforDesc = formFieldMap.get(jobFormMapBean
				.getJobDescription());
		FormFieldSpec formFieldSpecforCustomer = formFieldMap
				.get(jobFormMapBean.getCustomer());
		FormFieldSpec formFieldSpecforJobType = formFieldMap.get(jobFormMapBean
				.getJobType());

		// formFieldSpecForFollowUp.getFieldSpecId()
		List<FormField> formFields = getFormFields(form.getFormId());
		List<FormFieldSpec> formFieldSpecs = getFormFieldSpecs(form
				.getFormSpecId());
		Map<Long, FormFieldSpec> formFieldSpecMap = (Map) Api.getMapFromList(
				formFieldSpecs, "fieldSpecId");
		Visit followUpVisit = new Visit();

		for (FormField formField2 : formFields) {

			FormFieldSpec formFieldSpec = formFieldSpecMap.get(formField2
					.getFieldSpecId());
			String fieldSpecId = "" + formFieldSpec.getFieldSpecId();
			String value = resolveAndGetFieldValue(formField2, formFieldSpec);
			if (formFieldSpecforTitle != null
					&& fieldSpecId.equalsIgnoreCase(""
							+ formFieldSpecforTitle.getFieldSpecId())) {
				followUpVisit.setEmpVisitTitle("Follow up: " + value);
			}

			if (formFieldSpecforDesc != null
					&& fieldSpecId.equalsIgnoreCase(""
							+ formFieldSpecforDesc.getFieldSpecId())) {
				followUpVisit.setEmpVisitDesc(value);
			}

			if (formFieldSpecforCustomer != null
					&& fieldSpecId.equalsIgnoreCase(""
							+ formFieldSpecforCustomer.getFieldSpecId())) {
				followUpVisit.setCustomerId(Long.parseLong(formField2
						.getFieldValue()));
			}
		}
		FollowupBean followupBean;
		try {
			followupBean = (FollowupBean) Api.fromJson(followupJobJson,
					FollowupBean.class);
			if (Api.isEmptyString(followUpVisit.getEmpVisitTitle())) {
				followUpVisit.setEmpVisitTitle("Follow up: Untitled Job");
			}
			if (Api.isEmptyString(followUpVisit.getEmpVisitDesc())) {
				followUpVisit.setEmpVisitDesc("");
			}
			followUpVisit.setVisitStartTime(followupBean.getStartTime());
			followUpVisit.setVisitEndTime(followupBean.getEndTime());
			if (followupBean.getJobType() != null) {
				followUpVisit.setVisitTypeId(Long.parseLong(followupBean
						.getJobType()));
			}

			List<VisitState> visitStates = getVisitStatesForType(
					followUpVisit.getVisitTypeId(), false);
			followUpVisit.setVisitStateId(visitStates.get(0).getVisitStateId());
			if (followupBean.getComments() != null) {
				String description = followUpVisit.getEmpVisitDesc();
				followUpVisit.setEmpVisitDesc(followupBean.getComments() + " "
						+ description);
			}
			if (form.getAssignTo() != null) {
				followUpVisit.setEmpId(form.getAssignTo());
			} else {
				followUpVisit.setEmpId(webUser.getEmpId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		followUpVisit.setAddressSameAsCustomer(true);
		processVisitAddress(followUpVisit, webUser.getCompanyId());
		addVisit(followUpVisit, webUser.getTzo() + "", webUser, null);

		logVisitAudit(followUpVisit.getVisitId(), webUser.getEmpId(),
				webUser.getCompanyId(), Constants.CHANGE_TYPE_ADD, false);

		int type = followUpVisit.getEmpId() == 0
				|| followUpVisit.getEmpId() == -1 ? ActivityStream.ACTIVITY_STREAM_JOB_CREATED_BUT_NOT_ASSIGNED
				: ActivityStream.ACTIVITY_STREAM_JOB_CREATED_AND_ASSIGNED;
		addActivityStream(webUser.getCompanyId(), webUser.getEmpId(),
				webUser.getEmpFirstName(), followUpVisit.getVisitId(), type,
				followUpVisit.getEmpVisitTitle(), null);

		extraDao.insertJobFormMappingForForm(null, followUpVisit.getVisitId(),
				form.getFormId());

	}
	public void processVisitAddress(Visit visit, long companyId) {
		try {
			if (visit.isAddressSameAsCustomer() && visit.getCustomerId() > 0) {
				AddressBean addressBean = extraDao.getCustomerAddress(
						visit.getCustomerId() + "", companyId);
				visit.setPhoneNo(addressBean.getPhone());
				visit.setAddressStreet(addressBean.getStreet());
				visit.setAddressArea(addressBean.getArea());
				visit.setAddressCity(addressBean.getCity());
				visit.setAddressPincode(addressBean.getPincode());
				visit.setAddressLandMark(addressBean.getLandmark());
				visit.setAddressState(addressBean.getState());
				visit.setAddressCountry(addressBean.getCountry());
				visit.setAddressLat(addressBean.getLat());
				visit.setAddressLng(addressBean.getLng());
			}
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
		}
	}

	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void addVisit(Visit visit, String tzo, WebUser webUser,
			FormAndField formAndField) throws EffortError {
		try {
			String startTime = visit.getVisitStartTime();
			startTime = Api.makeNullIfEmpty(startTime);
			visit.setVisitStartTime(startTime);
			if (startTime != null && tzo != null && tzo.length() > 0) {
				visit.setVisitStartTime(Api.getDateTimeInTzToUtc(
						DatatypeConverter.parseDateTime(startTime), tzo));
			}

			String endTime = visit.getVisitEndTime();
			endTime = Api.makeNullIfEmpty(endTime);
			visit.setVisitEndTime(endTime);
			if (endTime != null && tzo != null && tzo.length() > 0) {
				visit.setVisitEndTime(Api.getDateTimeInTzToUtc(
						DatatypeConverter.parseDateTime(endTime), tzo));
			}
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
		}

		VisitState visitState = extraDao.getVisitState(visit.getVisitStateId()
				+ "");
		if (visitState.isEndState()) {
			visit.setCompleted(true);
			try {
				visit.setCompleteTime(Api.toUtcXsd(Api.getDateTimeInUTC(Api
						.getCalendarNowInUtc())));
			} catch (ParseException e) {
				Log.info(this.getClass(), e.toString(), e);
			}
		} else {
			visit.setCompleted(false);
			visit.setCompleteTime(null);
		}

		List<VisitState> visitStates = getVisitStatesForType(
				visit.getVisitTypeId(), true);
		if (visitState.isStartState() && visitStates.size() > 0) {
			visit.setVisitStateId(visitStates.get(0).getVisitStateId());
		}

		visit.setApproved(true);

		if (formAndField != null) {
			formAndField
					.setAssignTo(visit.getEmpId() > 0 ? (visit.getEmpId() + "")
							: null);
			if (formAndField.getFormId() > 0) {
				Form form = getForm(formAndField.getFormId() + "");
				modifyForm(form, formAndField, webUser, null, "",null);
			} else {
				addForm(formAndField, webUser, "", "", null,"",null);
			}

		}

		if (visit.getFormAndField() != null) {
			addForm(visit.getFormAndField(), webUser, "", "", null,"",null);
			logFormAudit(visit.getFormAndField().getFormId(),
					webUser.getEmpId(), webUser.getCompanyId(),
					Constants.CHANGE_TYPE_ADD, false,webUser.getIpAddress(),false,webUser.getOppUserName());
			visit.setEmpVisitFormId(visit.getFormAndField().getFormId());
		}
		// visit.set
		// added new column in employeeVisits.
		visit.setCompanyIdForVisit(webUser.getCompanyId());
		long visitId=extraDao.insertVisit(visit, webUser.getEmpId(), null);
		extraDao.insertVisitEntitySkill(Api.csvToList(visit.getSkillEntityIds()),visitId);
		sendMailForJobUpdate(visit.getVisitId(), 1);

		try {
			Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
					"visit", "add", visit.toCSV(), null);
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
		}

	}
	public void sendMailForJobUpdate(long visitId, int type) {
		extraDao.insertVisitEvent(visitId, type);
	}

	public void createWorkAtachment(String workId, long formId) {
		WorkFormMap workFormMap = new WorkFormMap();
		workFormMap.setFormId(formId);
		workFormMap.setWorkId(Long.parseLong(workId));
		extraDao.insertWorkFormMap(workFormMap, null);
	}
	
	public void resolveFormFields(FormSpec formSpec,
			List<FormFieldSpec> formFieldSpecs,
			List<FormSectionSpec> formSectionSpecs, FormAndField formAndField,
			int type, WebUser webUser) {

		// if(formSpec.getPurpose()!=FormSpec.PURPOUSE_WORK){
		Map<Long, FormFieldSpec> formFieldSpecMap = (Map) Api.getMapFromList(
				formFieldSpecs, "fieldSpecId");
		List<FormSectionFieldSpec> formSectionFieldSpecs = new ArrayList<FormSectionFieldSpec>();
		for (FormSectionSpec formSectionSpec : formSectionSpecs) {
			formSectionFieldSpecs.addAll(formSectionSpec
					.getFormSectionFieldSpecs());
		}
		Map<Long, FormSectionFieldSpec> FormSectionFieldsMap = (Map) Api
				.getMapFromList(formSectionFieldSpecs, "sectionFieldSpecId");

		List<FormField> formFields = formAndField.getFields();
		List<FormSectionField> formSectionFields = formAndField
				.getSectionFields();

		if (formFields != null) {
			for (FormField formField : formFields) {
				
				FormFieldSpec formFieldSpec = formFieldSpecMap.get(formField
						.getFieldSpecId());
				if(formFieldSpec == null)
					continue;
				if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {
					if (formFieldSpec.getSkeletonFormFieldSpecId() == null
							|| (formFieldSpec.getSkeletonFormFieldSpecId()
									.longValue() != constants
									.getSkeletonWorkStartsFieldSpecId()
									.longValue() && formFieldSpec
									.getSkeletonFormFieldSpecId().longValue() != constants
									.getSkeletonWorkEndsFieldSpecId()
									.longValue())) {
						if (type == Form.FORM_ACTION_SAVE_OR_UPDATE_TO_DB) {
							Api.convertDateTimesToGivenType(
									formField,
									"" + webUser.getTzo(),
									DateConversionType.DATETIME_TO_UTC_DATETIME_WITH_TZO,
									"fieldValue");
						} else if (type == Form.FORM_ACTION_RETRIVED_FROM_DB) {
							Api.convertDateTimesToGivenType(
									formField,
									"" + webUser.getTzo(),
									DateConversionType.UTC_DATETIME_TO_DATETIME_WITH_TZO,
									"fieldValue");
						}
					}
				}
			}
		}

		if (formSectionFields != null) {
			for (FormSectionField formSectionField : formSectionFields) {
				FormSectionFieldSpec formSectionFieldSpec = FormSectionFieldsMap
						.get(formSectionField.getSectionFieldSpecId());
				if(formSectionFieldSpec != null)
				{
					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {
						if (type == Form.FORM_ACTION_SAVE_OR_UPDATE_TO_DB) {
							Api.convertDateTimesToGivenType(
									formSectionField,
									"" + webUser.getTzo(),
									DateConversionType.DATETIME_TO_UTC_DATETIME_WITH_TZO,
									"fieldValue");
						} else if (type == Form.FORM_ACTION_RETRIVED_FROM_DB) {
							Api.convertDateTimesToGivenType(
									formSectionField,
									"" + webUser.getTzo(),
									DateConversionType.UTC_DATETIME_TO_DATETIME_WITH_TZO,
									"fieldValue");
						}
					}
				}
				
			}
		}
		// }

	}

	public List<Entity> getEntityListMapBySpecIdForFieldsForSync(
			List<FormFieldSpec> formFieldSpecs,
			List<FormSectionFieldSpec> formSectionFieldSpecs) {
		String entitySpecIds = "";
		if (formFieldSpecs != null) {
			for (FormFieldSpec formFieldSpec : formFieldSpecs) {
				if (formFieldSpec.getFieldType() == 14
						|| formFieldSpec.getFieldType() == 17) {
					if (!Api.isEmptyString(entitySpecIds)) {
						entitySpecIds += ",";
					}

					entitySpecIds += formFieldSpec.getFieldTypeExtra();
				}
			}
		}
		if (formSectionFieldSpecs != null) {
			for (FormSectionFieldSpec formSectionFieldSpec : formSectionFieldSpecs) {
				if (formSectionFieldSpec.getFieldType() == 14
						|| formSectionFieldSpec.getFieldType() == 17) {
					if (!Api.isEmptyString(entitySpecIds)) {
						entitySpecIds += ",";
					}

					entitySpecIds += formSectionFieldSpec.getFieldTypeExtra();
				}
			}
		}
		List<Entity> entities = getEntitiesForEntitySpecs(entitySpecIds, null,
				null);

		return entities;
	}


	public List<Entity> getEntitiesForEntitySpecs(String entitySpecIds,
			String mappedEntityIds, String mappedEntitySpecIds) {
		List<Entity> entities = extraDao.getEntitiesForEntitySpecs(
				entitySpecIds, mappedEntityIds, mappedEntitySpecIds);
		return entities;
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

		/*getSalesFormSpecsAndFieldSpec(companyId, serviceCallConstants);
		getDeliveryFromSpecAndFormFieldSpecs(companyId, serviceCallConstants);
		getCollectionFormSpecIdAndFieldSpecIds(companyId, serviceCallConstants);
		getDocumentVerificationFormSpecsAndFieldSpecs(companyId,
				serviceCallConstants);
		getGlobalCompanyFormSpecsAndFieldSpecs(companyId, serviceCallConstants);*/

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
	
	private void updateFormFieldForCollections(Long customerFieldSpecId,
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
	private void updateFormFieldForDelivery(Long customerFieldSpecId,
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
				e.printStackTrace();
			}
		}

	}
	
	public void getFormDisplayMapForForm(List<FormDisplay> formDisplays,
			Form form, String tzo, Map<String, Object> map) {
		if (map == null) {
			map = new HashMap<String, Object>();
		}

		for (FormDisplay formDisplay : formDisplays) {
			if (formDisplay.getType() == 1) {
				map.put("F" + formDisplay.getId(),
						formDisplay.getFieldDisplay());
			} else if (formDisplay.getType() == 2) {
				map.put("S" + formDisplay.getId(),
						formDisplay.getSectionDisplay());
			}
		}

		try {
			Employee employee = getEmployee(form.getFilledBy() + "");
			Company company = getCompany(employee.getCompanyId()+"");
            String apiKey = "";
            if(company.getEnableMaps() == 1 && company.getApiKey()==null) {
                    apiKey = "AIzaSyCeAqbV77gbWCEG-2nl2dHSOHYcn_oUzxw";//properties.getProperty("googleMapsApiKey");
            }else if(company.getEnableMaps() == 1 && company.getApiKey()!=null){
                    apiKey = company.getApiKey();
            }
			map.put("-100", Api.makeEmptyIfNull(employee.getEmpName()));
			map.put("-101", Api.makeEmptyIfNull(employee.getEmpNo()));
			map.put("-102",
					Api.getDateTimeInTz(Api.toCalendar(Api
							.getDateTimeInUTC(form.getCreatedTime())), tzo, 1));
			map.put("-103", Api.getDateTimeInTz(Api.toCalendar(Api
					.getDateTimeInUTC(form.getModifiedTime())), tzo, 1));
			map.put("-104",
					Api.makeEmptyIfNull(form.getLatitude()) + ","
							+ Api.makeEmptyIfNull(form.getLongitude()));

			map.put("-105", Api.makeEmptyIfNull(form.getLocationAddress()));
			String imageUrl = "http://maps.googleapis.com/maps/api/staticmap?center=17.4280462,78.4568198&zoom=14&size=200x200&scale=2&sensor=false&key="+apiKey;
			if (!Api.isEmptyString(form.getLatitude())
					&& !Api.isEmptyString(form.getLongitude())) {
				imageUrl = "http://maps.googleapis.com/maps/api/staticmap?center="
						+ form.getLatitude()
						+ ","
						+ form.getLongitude()
						+ "&zoom=14&size=200x200&scale=2&markers=color:blue|label:|"
						+ form.getLatitude()
						+ ","
						+ form.getLongitude()
						+ "&sensor=false&key="+apiKey;
			}else{
				imageUrl="";
			}
			String imageSrc = "<img src='" + imageUrl + "'/>";
			if(imageUrl!=null &&imageUrl.trim().isEmpty())
				imageSrc="";
			map.put("-106", imageSrc);
			map.put("-107", form.getFormId()+"");
			String workId = getExtraSupportAdditionalSupportDao().getWorkIdByActionFormId(form.getFormId());
			map.put("-108", workId);
			
			FormSpec formSpec = getFormSpec(form.getFormSpecId()+"");
			
			Workflow workflow = getWebSupportManager().getWorkFlowMappedFormEntity(formSpec.getCompanyId(), formSpec.getUniqueId(), (short)1);
			

			if(workflow!=null)
			{
				List<WorkFlowFormStatusHistory> workflowFormStatusHistories  = getWorkFlowManager().getWorkFlowFormStatusHistoriesForPrintTemplate(form.getFormId(),tzo); 
				if(workflowFormStatusHistories != null )
				{
					if(!workflowFormStatusHistories.isEmpty() && workflowFormStatusHistories.size()>0)
					{
						String approvedByNames = Api.toCSVWithSpacesBetween(workflowFormStatusHistories, "approvedEmp", CsvOptions.FILTER_NULL_OR_EMPTY);
						if(approvedByNames!=null)
						{
							map.put("-1000", approvedByNames);
						}
						else
						{
							map.put("-1000", "Not yet approved.");
						}
					}
					else
					{
						WorkFlowFormStatus workFlowFormStatus = workFlowExtraDao.getWorkFlowFormStatus(form.getFormId());
						if(workFlowFormStatus != null)
						{
							map.put("-1000", "Not yet approved.");
						}
						else
						{
							map.put("-1000", "");
						}
					}
				}
				else
				{
					map.put("-1000", "Not yet approved.");
				}
			}
			else
			{
				map.put("-1000", "");
			}
			
			
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
		}
	}

	public String getResolvedEmailTemplateDataForFrom(Form form,
			FormSpec formSpec) {
		// Form form =extraDao.getForm(""+formId);
		// FormSpec formSpec=extraDao.getFormSpec(""+form.getFormId());
		String replaceString = ":SectionFieldTrs";
		List<String> sectionFieldSpecIdCsvList = new ArrayList<String>();
		List<String> sectionSpecIdCsv = new ArrayList<String>();
		String emailTemplate = formSpec.getEmailTemplate();

		if (Api.isEmptyString(emailTemplate))
			return null;

		Employee employee =getWebExtensionManager().getEmployeeBasicDetailsAndTimeZoneByEmpId("" + form.getFilledBy());
		List<FormDisplay> formDisplays = getFormFieldsForDisplay(form,
				form.getFilledBy(), form.getCompanyId(), true,false,false,false);
		Map<String, Object> fieldDisplaysMap = new LinkedHashMap<String, Object>();
		getFormDisplayMapForForm(formDisplays, form,
				"" + employee.getTimezoneOffset(), fieldDisplaysMap);
		Pattern pattern = Pattern
				.compile("<sectionFieldSpecIds>(.+?)</sectionFieldSpecIds>");
		Matcher matcher = pattern.matcher(emailTemplate);
		while (matcher.find()) {
			sectionFieldSpecIdCsvList.add((matcher.group(1)));
		}

		pattern = Pattern.compile("<sectionSpecIds>(.+?)</sectionSpecIds>");
		matcher = pattern.matcher(emailTemplate);
		while (matcher.find()) {
			sectionSpecIdCsv.add((matcher.group(1)));
		}

		try {
			if (!Api.isEmptyString(emailTemplate)) {
				for (Entry<String, Object> entry : fieldDisplaysMap.entrySet()) {
					if (entry.getValue() instanceof FormFieldDisplay) {
						FormFieldDisplay formFieldDisplay = (FormFieldDisplay) entry
								.getValue();
						if (formFieldDisplay.getFieldValue() != null) {
							emailTemplate = emailTemplate.replace(
									"<" + entry.getKey() + ">", Api
											.makeEmptyIfNull(formFieldDisplay
													.getFieldValueDispaly()));
						}
					} else if (entry.getValue() instanceof String) {
						String data = "";
						if (entry.getValue() != null) {
							data = entry.getValue().toString();
						}
						emailTemplate = emailTemplate.replace(
								"<" + entry.getKey() + ">", data);
					}

				}

				for (int i = 0; i < sectionSpecIdCsv.size(); i++) {
					StringBuffer sectintableString = new StringBuffer();

					String sectionSpecId = sectionSpecIdCsv.get(i);
					String sectionFieldSpecIdsCsv = sectionFieldSpecIdCsvList
							.get(i);
					Map<Integer, List<FormSectionFieldDisplay>> formSectionFieldDisplayMap = (Map) fieldDisplaysMap
							.get(sectionSpecId);
					if (formSectionFieldDisplayMap != null) {

						for (Entry<Integer, List<FormSectionFieldDisplay>> entry : formSectionFieldDisplayMap
								.entrySet()) {
							List<FormSectionFieldDisplay> formSectionFieldDisplay = entry
									.getValue();
							Map<Long, FormSectionFieldDisplay> formSectionFieldDisplayInstanceMap = (Map) Api
									.getMapFromList(formSectionFieldDisplay,
											"sectionFieldSpecId");
							List<String> sectionFiedSpecIdsList = Api
									.csvToList(sectionFieldSpecIdsCsv);
							sectintableString.append("<tr>");
							for (String sectionFieldSpecId : sectionFiedSpecIdsList) {
								FormSectionFieldDisplay formSectionFieldDisplayField = formSectionFieldDisplayInstanceMap
										.get(Long.parseLong(sectionFieldSpecId
												.substring(1).trim()));
								sectintableString
										.append("<td style=\"border: 1px solid #000;padding:8px\">");
								sectintableString
										.append(Api
												.makeEmptyIfNull(formSectionFieldDisplayField
														.getFieldValueDispaly()));
								sectintableString.append("</td>");

							}
							sectintableString.append("</tr>");

						}
					}

					emailTemplate = emailTemplate.replace(":SectionFieldTrs"
							+ (i + 1), sectintableString.toString());

					// Map<Integer, List<FormSectionFieldDisplay>>
					// sectionDisplay=formDisplay.getSectionDisplay();

				}

				emailTemplate = emailTemplate.replaceAll(
						"<sectionFieldSpecIds>(.+?)</sectionFieldSpecIds>", "");
				emailTemplate = emailTemplate.replaceAll(
						"<sectionSpecIds>(.+?)</sectionSpecIds>", "");

				// for (String csvSectionFieldIds : sectionSpecIdCsv) {
				//
				// List<String>
				// sectionFieldIds=Api.csvToList(csvSectionFieldIds);
				// StringBuffer sectintableString=new StringBuffer();
				// for (String sectionFieldSpecId : sectionFieldIds) {
				//
				// sectintableString.append("<tr>");
				// for (FormDisplay formDisplay : formDisplays) {
				// if(formDisplay.getType()==2 &&
				// sectionFieldSpecId.equalsIgnoreCase("T"+formDisplay.getId())){
				// Map<Integer, List<FormSectionFieldDisplay>>
				// sectionDisplay=formDisplay.getSectionDisplay();
				// if(sectionDisplay!=null)
				// for (Entry<Integer, List<FormSectionFieldDisplay>> string :
				// sectionDisplay.entrySet()) {
				// sectintableString.append("<td>");
				//
				//
				// sectintableString.append("</td>");
				// }
				//
				// }
				//
				// }
				//
				// }
				// }

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return emailTemplate;
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

	public long addWork(FormAndField formAndField, WebUser webUser,
			FormSpec formSpec, ExtraProperties extraProperties) {
		// if formSpec.purpose is 3 then Add Work
		String logText = "addWork() //  ";
	    WorkSpec workSpec = getWebExtraManager().
				getWorkSpecsForFromSpecUniqueIdAndCompany(formSpec.getUniqueId(), webUser.getCompanyId());
		
	    long workSpecId = workSpec.getWorkSpecId();
	    
		Work work = new Work();

		work.setWorkSpecId(workSpecId);
		work.setFormId(formAndField.getFormId());
		if(formAndField!= null &&  formAndField.isFromMigrationFields()) {
			work.setFromMigrationFields(formAndField.isFromMigrationFields());
			work.setMigrationFilledBy(Long.parseLong(formAndField.getCreatedBy()));
			work.setMigrationModifiedBy(Long.parseLong(formAndField.getCreatedBy()));
			work.setMigrationCreatedTime(formAndField.getCreatedTime());
			work.setMigrationModifiedTime(formAndField.getCreatedTime());
		}
		formAndField.setWorkSpecId(workSpecId+"");
		String value = null;
		work.setSourceOfAction(formAndField.getSourceOfAction());
		

		if(formAndField.getWorkSpec() != null && (formAndField.getWorkSpec().getWorkSpecId() == constants.getTvsDeliveryWorkSpecId()))
		{
			value = getWebAdditionalSupportManager().getWorkFieldValueForGivenUniqueId(formAndField,constants.getTvsZoneUserFormFieldUniqueId(),constants.getSkeletonWorkEmployeeFieldSpecId());
		}
		else {
			value = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkEmployeeFieldSpecId());
		}
		String workName = getWorkFieldValueForGivenSkeleton(formAndField,
				constants.getSkeletonWorkNameFieldSpecId());
		String location = "";
		try
		{
			String workStartTime = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkStartsFieldSpecId());
			String workEndTime = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkEndsFieldSpecId());
			String phoneNumber = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkPhoneNumberFieldSpecId());
			String description = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkDescriptionFieldSpecId());
			String priority = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkProrityFieldSpecId());
			String country = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkCountryFieldSpecId());
			String pincode = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkPincodeFieldSpecId());
			String state = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkStateFieldSpecId());
			String landMark = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkLandMarkFieldSpecId());
			String city = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkCityFieldSpecId());
			String area = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkAreaFieldSpecId());
			String street = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkStreetFieldSpecId());
			String customerId = getWorkFieldValueForGivenSkeleton(formAndField,
					Long.parseLong(constants.getSkeletonCustomerFieldSpecId()));
			location = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkLocationFieldSpecId());
			
			/*String startTime = "";
			String endTime = "";
			List<FormFieldSpec> formFieldSpecs = getFormFieldSpecs(formAndField.getFormSpecId());
			long startStartTimeFieldSpecId = constants.getSkeletonWorkStartsFieldSpecId();
			long startEndTimeFieldSpecId = constants.getSkeletonWorkEndsFieldSpecId();
			startTime = Api.getDateTimeInTzToUtcDateTime(workStartTime, webUser.getTzo()+"");
			endTime = Api.getDateTimeInTzToUtcDateTime(workEndTime, webUser.getTzo()+"");
			for (FormFieldSpec formFieldSpec : formFieldSpecs) {
				if(formFieldSpec.getSkeletonFormFieldSpecId() != null && formFieldSpec.getSkeletonFormFieldSpecId() == startStartTimeFieldSpecId) {
					if(formFieldSpec.isComputedField()) {
						startTime = startTime;
					}else {
						startTime = workStartTime;
					}
				}
				if(formFieldSpec.getSkeletonFormFieldSpecId() != null && formFieldSpec.getSkeletonFormFieldSpecId() == startEndTimeFieldSpecId) {
					if(formFieldSpec.isComputedField()) {
						endTime = endTime;
					}else {
						endTime = workEndTime;
					}
				}
			}*/
			
			if(!Api.isEmptyString(workName))
				work.setWorkName(workName);
			if(!Api.isEmptyString(description))
				work.setDescription(description);
			if(!Api.isEmptyString(priority))
				work.setPriority(priority);
			if(!Api.isEmptyString(country))
				work.setCoutry(country);
			if(!Api.isEmptyString(pincode))
				work.setPincode(pincode);
			if(!Api.isEmptyString(state))
				work.setState(state);
			if(!Api.isEmptyString(landMark))
				work.setLandMark(landMark);
			if(!Api.isEmptyString(city))
				work.setCity(city);
			if(!Api.isEmptyString(area))
				work.setArea(area);
			if(!Api.isEmptyString(street))
				work.setStreet(street);
			if(!Api.isEmptyString(customerId))
				work.setCustomerId(Long.parseLong(customerId));
			if(!Api.isEmptyString(workStartTime))
				work.setStartTime(workStartTime);
			if(!Api.isEmptyString(workEndTime))
				work.setEndTime(workEndTime);
			if(!Api.isEmptyString(phoneNumber))
				work.setPhoneNumber(phoneNumber);
		}
		catch(Exception e)
		{
			Log.info(this.getClass(), "addWork() //Got Exception while capturing Work start and end time in work :"+e);
		}
		
		if (formAndField.getAssignmentType() != null && formAndField.getAssignmentType() == 1
				&& !Api.isEmptyString(formAndField.getAssignTo())) {
			value = formAndField.getAssignTo();
		}
		if (value != null) {
			// if(work.getAssignTo()!=null &&
			// work.getAssignTo()!=Long.parseLong(value)){
			// extraDao.insertAssignAway(ExtraDao.ELEMENT_WORK,
			// work.getWorkId(), work.getAssignTo());
			// }
			work.setAssignTo(Long.parseLong(value));
		}
		// work.setCreatedBy(webUser.getEmpId());
		// work.setModifiedBy(webUser.getEmpId());
		// work.setCompanyId(webUser.getCompanyId());
		if (extraProperties != null) {
			if (!Api.isEmptyString(extraProperties.getParentWorkId())
					&& !Api.isEmptyString(extraProperties
							.getParentWorkActionSpecId())) {
				work.setParentWorkId(Long.parseLong(extraProperties
						.getParentWorkId()));
				work.setParentWorkActionSpecId(Long.parseLong(extraProperties
						.getParentWorkActionSpecId()));
		
			}
			work.setExternalId(extraProperties.getExternalId());
			work.setWorkFieldsUniqueKey(extraProperties.getWorkFieldsUniqueKey());
			work.setApiUserId(extraProperties.getApiUserId());
			if(!Api.isEmptyString(extraProperties.getRecurringParentId()+""))
				work.setRecurringParentId(extraProperties.getRecurringParentId());
			work.setTemplate(extraProperties.getIsTemplate());
		}
		
		
		if (formAndField.getIsWorkInviation()) {
			work.setInvitation(formAndField.getIsWorkInviation());
			work.setInvitationState(Work.INVITATION);
			if(workSpec.isCanRejectWorkInvitation())
			{
				work.setCanRejectWorkInvitation(true);
			}
			else
			{
				work.setCanRejectWorkInvitation(false);
			}
		}
		work.setWorkSharing(formAndField.isWorkSharing());
		
		WorkSpecSchedulingConfig workSpecSchedulingConfig = getWebAdditionalManager().getWorkSpecSchedulingConfig(workSpecId);
		if(workSpecSchedulingConfig != null && !formAndField.getIsWorkInviation()){
			work.setSmartScheduling(true);
		}
		if(formAndField.getAssignmentType() != null && formAndField.getAssignmentType() == 2) {
			formAndField.setIsWorkInviation(true);
			if (workSpec.isCanRejectWorkInvitation()) {
				work.setCanRejectWorkInvitation(true);
			} else {
				work.setCanRejectWorkInvitation(false);
			}
		}
		work.setSubTaskParentWorkId(formAndField.getSubTaskParentWorkId());
		work.setWorkProcessSubTaskSpecId(formAndField.getWorkProcessSubTaskSpecId());
		boolean isSubTaskWork = (formAndField.getSubTaskParentWorkId() == null) ? false : true;
		work.setSubTaskWork(isSubTaskWork);
		long workId  = extraDao.addWork(work, webUser.getEmpId(), webUser.getCompanyId(), null, work.getAppVersion());
		getWebExtensionManager().resolveWorkExtensionData(work,formAndField);
		getExtendedDao().modifyWorkAssignee(work, webUser.getEmpId(),webUser);
		getWebExtensionManager().updateWorkLocation(workId+"",location,webUser.getEmpId(),work);
		Log.info(getClass(), logText+" updateWorkLocation done");
		try{
			work.setCompanyId(webUser.getCompanyId());
			formAndField.setWork(work);
		}
		catch(Exception ex) {
		}
		
		String time = Api
				.getDateTimeInUTC(new Date(System.currentTimeMillis()));
		WorkStatus workStatus = new WorkStatus();
		workStatus.setWorkId(work.getWorkId());
		workStatus.setWorkSpecId(work.getWorkSpecId());
		workStatus.setFormId(null);
		workStatus.setModifiedBy(webUser.getEmpId());
		// workStatus.setCompanyId(webUser.getCompanyId());
		workStatus.setCreatedTime(time);
		workStatus.setModifiedTime(time);
		workStatus.setWorkActionSpecId(null);

		extraDao.insertOrUpdateWorkStatus(workStatus, null,webUser);
		if(workStatus.isCompleted()) {
			syncDao.updateProcessingStatusForWorkCompletion(work.getFormId());
		}
		Log.info(getClass(), logText+" insertOrUpdateWorkStatus done isWorkSharing = "+formAndField.isWorkSharing());
		
		if(formAndField.isWorkSharing() && !Api.isEmptyString(formAndField.getWorkSharingEmpIds())){
			List<Long> empIds =  Api.csvToLongList(formAndField.getWorkSharingEmpIds());
			empIds.remove(webUser.getEmpId());
			extraSupportDao.insertWorkSharingEmployeesForWork(empIds, work, webUser);
		}
		Log.info(getClass(), logText+" workId = "+work.getWorkId()+" before logWorkAudit isSkipSendJmsMessage = "+formAndField.isSkipSendJmsMessage());
	
		if(!formAndField.isSkipSendJmsMessage()) {
			logWorkAudit(work.getWorkId(), webUser.getEmpId(),
					webUser.getCompanyId(), JmsMessage.CHANGE_TYPE_ADD, false);
		}
		
		Log.info(getClass(), logText+" workId = "+work.getWorkId()+" insertWorkReassignAuditLog starts...");
		auditDao.insertWorkReassignAuditLog(work, formAndField.getAssignTo(), webUser.getEmpId(),webUser);
		
		if(formAndField.getAssignmentType() != null && formAndField.getAssignmentType() == 2) {
			if(formAndField.getEmpSelectionType() == 1) {
				if(!Api.isEmptyString(formAndField.getAllInvitaionEmpIds())){
					long rootEmpId = getRootEmployeeId(webUser);
					List<String> invitationEmpIds = Api.csvToList(formAndField.getAllInvitaionEmpIds());
					invitationEmpIds.remove(rootEmpId+"");
					String csvInvitationEmpIds = Api.toCSV(invitationEmpIds);
					formAndField.setWorkInvitationEmpIds(csvInvitationEmpIds);
				}
			}
		}
		if (formAndField.getAssignmentType() != null && formAndField.getAssignmentType() == 2
				&& !Api.isEmptyString(formAndField.getWorkInvitationEmpIds())) {
			List<String> empIds = Api.csvToList(formAndField.getWorkInvitationEmpIds());
			List<WorkInvitationAndEmployeeMap> workInvitationAndEmployeeMapList = new ArrayList<WorkInvitationAndEmployeeMap>();
			if (empIds != null && !empIds.isEmpty()) {
				for (String empId : empIds) {
					WorkInvitationAndEmployeeMap invitationEmpMap = new WorkInvitationAndEmployeeMap();
					invitationEmpMap.setWorkId(work.getWorkId());
					invitationEmpMap.setEmpId(Long.parseLong(empId));
					invitationEmpMap.setDelivered(false);
					invitationEmpMap.setAccepted(false);
					invitationEmpMap.setActive(true);
					invitationEmpMap.setRejected(false);
					invitationEmpMap.setDeleted(false);
					invitationEmpMap.setCreatedBy(webUser.getEmpId());
					invitationEmpMap.setInvitationType(1);
					workInvitationAndEmployeeMapList.add(invitationEmpMap);
				}
				extraDao.insetWorkInvitatonEmpoyeeMap(workInvitationAndEmployeeMapList);
				try {
					// work Audit log for invitation broad cast.
					Log.info(this.getClass(),
							"Invitation broadcasted from Add Work. Work Assign to" + work.getAssignTo() + "");
					work.setRemarks("Invitation broadcasted from Add Work. Work Assign to" + work.getAssignTo() + "");
					work.setAuditType(Work.TYPE_WORK_INVITATION_BROADCAST_INFO_LOGS);
					getWebAdditionalSupportExtraManager().auditWorkLog(workId + "", work);
				} catch (Exception e) {

				}
				extraDao.updateAllWorkInviationEmpMapActiveState(true, work.getWorkId(), Api.toCSV(empIds), "IN", false,work);
				
				formAndField.setIsWorkInviation(true);	
				work.setInvitation(formAndField.getIsWorkInviation());
			    work.setInvitationState(Work.INVITATION);
				if (workSpec.isCanRejectWorkInvitation()) {
					work.setCanRejectWorkInvitation(true);
				} else {
					work.setCanRejectWorkInvitation(false);
				}
				
				if(work!= null &&  work.isFromMigrationFields()) {
				getExtendedDao().updateWorkInvitationStateByUsingWork(work.getWorkId(),work.isInvitation(),work.getInvitationState(),work.getMigrationModifiedTime());
				}else {
				getExtendedDao().updateWorkInvitationState(work.getWorkId(),work.isInvitation(),work.getInvitationState());
				}
				getExtendedDao().updateWorkSmartScheduling(work.getWorkId());
				try {
					/*if (constantsExtra.getMaruthiCompanyId().equals(work.getCompanyId() + "")) {
						ProcessJmsMessageStatus processJmsMessageStatus = new ProcessJmsMessageStatus(JmsMessage.TYPE_WORK_INVITATION, work.getWorkId(), webUser.getCompanyId(),
								webUser.getEmpId(), JmsMessage.TYPE_WORK_INVITATION_SENT, work.getWorkId(), false);
						getExtendedDao().insertIntoProcessJmsMessageStatus(processJmsMessageStatus,
								ProcessJmsMessageStatus.UN_PROCESSED);
					} else {
					sendJmsMessage(JmsMessage.TYPE_WORK_INVITATION, work.getWorkId(), webUser.getCompanyId(),
							webUser.getEmpId(), JmsMessage.TYPE_WORK_INVITATION_SENT, work.getWorkId(), null, false);
					}*/
					sendJmsMessage(JmsMessage.TYPE_WORK_INVITATION, work.getWorkId(), webUser.getCompanyId(),
							webUser.getEmpId(), JmsMessage.TYPE_WORK_INVITATION_SENT, work.getWorkId(), null, false);
				} catch (Exception e) {
					Log.info(getClass(), " JMS Message Sent For Work invitations Failed Exception = " + e);
				}

				getWebExtensionManager().processAndInsertIntoWorkInvitationLogs(empIds,webUser,work.getWorkId());
			}

			if(constantsExtra.isAllowGigUserCreation(webUser.getCompanyId())) {
				try {
					List<Long> empIdsLong = Api.csvToLongList(Api.toCSV(empIds));
					PushNotificationTriggerStatus notificationTriggerStatus = new PushNotificationTriggerStatus();
					notificationTriggerStatus.setCompanyId(webUser.getCompanyId());
					notificationTriggerStatus.setEmpId(-1l);
					notificationTriggerStatus.setTriggerType(PushNotificationTriggerStatus.WORK_INVITATION);
					notificationTriggerStatus.setMessageBody("Invitation is waiting for your acceptance");
					getWebExtensionManager().insertIntoPushNotificationTriggerStatus(notificationTriggerStatus,empIdsLong);
				}catch(Exception e) {
					
				}
			}else {
				if (empIds != null && !empIds.isEmpty()) {
					//getWebAdditionalSupportManager().sendPushNotification(Api.toCSV(empIds), Constants.ACTION_TYPE_SYNC);
					Log.info(getClass(), "isEnableWorkInvitationAlarm = "+workSpec.isEnableWorkInvitationAlarm());
					if(workSpec.isEnableWorkInvitationAlarm()) {
						Log.info(getClass(), logText+" isEnableWorkInvitationAlarm is true. so no need regular fire base push notification.");
					}else {
						JmsMessage jmsMessage = Api.getJmsMessage(JmsMessage.TYPE_WORK_INVITATION, work.getWorkId(), webUser.getCompanyId(),
								webUser.getEmpId(), JmsMessage.TYPE_WORK_INVITATION_SENT, work.getWorkId(), null, false,Api.toCSV(empIds),false);
						jmsMessage.setWork(work);
						getEffortPluginManager().sendGoogleFirebaseMultiCastMessages(Api.toCSV(empIds), Constants.ACTION_TYPE_SYNC, jmsMessage);
					}
				}
			}
		}else if(formAndField.getIsWorkInviation()){
			getWebExtraManager().updateWorkInvitationDetails(work, formAndField, false, webUser,null);
		}
		
		NextActionSpec nextActionSpec=extraDao.getNextActionSpecByWorkSpec(work.getWorkSpecId());
		if(nextActionSpec!=null)
		 getWebExtraManager().UpdateWorkActionsInfo(work, null, null, nextActionSpec);
		
		
		if((!formAndField.getIsWorkInviation() && !formAndField.isWorkSharing()) || formAndField.isIncludeWorkInvitations()){
		// Smart Allocation work Action level assignment for auto allocation.
		List<WorkActionSpec> workActionSpecs = getWorkActionSpecsBySpecWorkSpecId(workSpecId);
		
		if(workActionSpecs != null && !workActionSpecs.isEmpty()){
			for(WorkActionSpec actionSpec : workActionSpecs){
				if(actionSpec.isActionLevelAssignment()){
					List<Employee> underEmployees = new ArrayList<Employee>();
					if(extraProperties!=null) {
						underEmployees = extraProperties.getUnderEmployees();
					}
					getWebSupportManager().resolveEligibleEmployeesForEachActionForAssign(workActionSpecs,workId+"",null,
							underEmployees, webUser, false);
					break;
				}
			}
		}
	}
		
	  //Deva,2018-03-08,processActionVisibilityAssignments
	  getWebAdditionalSupportManager().processActionVisibilityAssignments( work.getWorkId(), webUser);
	  
	  //Resolving visisbility of parent work for employees in parentWorkFields.
	  getWebAdditionalSupportManager().processSubTasksVisibilityEvaluations(work.getWorkId(),webUser,work);
	  
	  getWebAdditionalSupportExtraManager().sendNextActionInvitation(work, workSpecId, webUser);
		
		
		if(!work.isTemplate())
		{
			if(work.getAssignTo() != null)
			{
				addActivityStream(webUser.getCompanyId(),
						webUser.getEmpId(), null,
						work.getWorkId(),
						ActivityStream.ACTIVITY_STREAM_WORK_ADDED_AND_ASSIGNED, workSpec.getWorkSpecTitle(), workName);
			}
			else
			{
				addActivityStream(webUser.getCompanyId(),
						webUser.getEmpId(), null,
						work.getWorkId(),
						ActivityStream.ACTIVITY_STREAM_WORK_ADDED, workSpec.getWorkSpecTitle(), workName);
			}
		}
		// Checks WorkDataPostingConfiguration and triggers Jms to push work through restApi.
		List<WorkDataPostingConfiguration> workDataPostingConfig = extraSupportAdditionalDao
				.getWorkDataPostingConfigurationForGivenCompany(webUser.getCompanyId());
		
		if (workDataPostingConfig != null && workDataPostingConfig.size() > 0) {
			 String workSpecIdsCsv = Api.toCSV(workDataPostingConfig, "workSpecId", CsvOptions.FILTER_NULL_OR_EMPTY);
			 if (!Api.isEmptyString(workSpecIdsCsv)) {
				  List < Long > workSpecIdsList = new ArrayList <Long>();
				  try {
					  workSpecIdsList = Api.csvToLongList(workSpecIdsCsv);
				  } catch (Exception e) {
					  e.printStackTrace();
				  }
				  if (workSpecIdsList.contains(work.getWorkSpecId())) {
					  if(formAndField.isDisableDataPost() && !Api.isEmptyString(formAndField.getRestrictWorkDataPostConfigId()) && !"-1".equals(formAndField.getRestrictWorkDataPostConfigId())) 
					  {
						  if(constantsExtra.getMaruthiCompanyId().equals(webUser.getCompanyId()+"")) {
							  getExtendedDao().insertIntoWorkDataPostingTriggerEvents(work.getWorkId(), webUser.getCompanyId(),
									  webUser.getEmpId(),JmsMessage.CHANGE_TYPE_ADD, work.getWorkId(),null,formAndField.getRestrictWorkDataPostConfigId());
						  }else {
							  getWebExtensionManager().sendJmsMessage(JmsMessage.TYPE_SIMPLIFIED_WORK_DATA_SYNC,workId, webUser.getCompanyId(), webUser.getEmpId(),
									    JmsMessage.CHANGE_TYPE_ADD, workId, null, false, formAndField.getRestrictWorkDataPostConfigId());
						  }
					  }
					  else 
					  {
						  if(constantsExtra.getMaruthiCompanyId().equals(webUser.getCompanyId()+"")) {
							  extraSupportAdditionalDao.insertIntoWorkDataPostingTriggerEvents(work.getWorkId(), webUser.getCompanyId(),
									  webUser.getEmpId(),JmsMessage.CHANGE_TYPE_ADD, work.getWorkId(),null);
						  }else {
						  sendJmsMessage(JmsMessage.TYPE_SIMPLIFIED_WORK_DATA_SYNC,workId, webUser.getCompanyId(), webUser.getEmpId(),
								    JmsMessage.CHANGE_TYPE_ADD, workId, null, false);
						  }
					  }
					  
				  }
				  else
				  {
					  sendJmsMessage(JmsMessage.TYPE_EXTERNAL_INTEGRATION,workId, webUser.getCompanyId(), webUser.getEmpId(),
							    JmsMessage.CHANGE_TYPE_ADD, workId, null, false);
				  }
			 }
		}//End of WorkDataPostingConfiguration Check.
		else {
			if(!formAndField.isSkipSendJmsMessage()) {
				sendJmsMessage(JmsMessage.TYPE_EXTERNAL_INTEGRATION, workId, webUser.getCompanyId(), webUser.getEmpId(),
						JmsMessage.CHANGE_TYPE_ADD, workId, null, false);
			}
		}
	
		
		
		long insertIntoFlatTableStartTime = System.currentTimeMillis();
		if(constantsExtra.getPushingBulkUploadsWorksToDWHDailyNight().contentEquals("true") && formAndField.getBulkUploadId() != 0) {
			int insertedCount = getExtraSupportAdditionalSupportDao().insertIntoFlatDataTablesForBulkUploadWorks(work.getWorkId(), webUser.getCompanyId(), work.getWorkSpecId());
			Log.info(getClass(), logText+" insertIntoFlatDataTablesForBulkUploadWorks workId = "+workId+" insertedCount = "+insertedCount+" took "+" "+(System.currentTimeMillis()-insertIntoFlatTableStartTime)+" ms");
		}else {
			int insertedCount = getWebAdditionalSupportExtraManager().insertIntoFlatTableDataStatusForWorks(work, webUser.getCompanyId(), work.getWorkSpecId());
			Log.info(getClass(), logText+" insertIntoFlatTableDataStatusForWorks workId = "+workId+" insertedCount = "+insertedCount+" took "+" "+(System.currentTimeMillis()-insertIntoFlatTableStartTime)+" ms");
		 }
		
		if(!formAndField.isSkipSendJmsMessage()) {
			sendJmsMessage(JmsMessage.TYPE_WORKS_ADD_MODIFY, workId,webUser.getCompanyId(), webUser.getEmpId(), 
						JmsMessage.CHANGE_TYPE_ACTIONABLE_WORKS, workId, null, false);
		}
		
		//getWebAdditionalSupportExtraManager().resolveActionableWork(work);
		
		return workId;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void addForm(FormAndField formAndField, WebUser webUser,
			String saveHidden, String followupJobJson,
			ExtraProperties extraProperties, String workFollowUp, List<JmsMessage> jmsMessages) throws EffortError {
		
		String logText = "addForm() // formSpecId = "+formAndField.getFormSpecId();
		Log.info(getClass(), logText+" starts ...");
		FormSpec formSpec = getFormSpec("" + formAndField.getFormSpecId());
		long workIdForFollowUpWork=0;
		long recurrenceWorkId=0;
		boolean isWorkActionForm = false;
		List<FormFieldSpec> formFieldSpecs = getFormFieldSpecs(formAndField
				.getFormSpecId());
		List<FormSectionSpec> formSectionSpecs = getFormSectionSpecs(formAndField
				.getFormSpecId());
		Log.info(getClass(), logText+" getting formFieldSpecs and formSectionSpecs done");
		Form form = new Form();
		form.setCompanyId(webUser.getCompanyId());
		form.setFilledBy(webUser.getEmpId());
		form.setFormSpecId(formAndField.getFormSpecId());
		form.setModifiedBy(webUser.getEmpId());
		if(formAndField!= null &&  formAndField.isFromMigrationFields()) {
		form.setFromMigrationFields(formAndField.isFromMigrationFields());
		form.setMigrationFilledBy(Long.parseLong(formAndField.getCreatedBy()));
		form.setMigrationModifiedBy(Long.parseLong(formAndField.getCreatedBy()));
		form.setMigrationCreatedTime(formAndField.getCreatedTime());
		form.setMigrationModifiedTime(formAndField.getCreatedTime());
		}
		
		if(!Api.isEmptyString(formAndField.getExternalFormId())){
			form.setExternalId(formAndField.getExternalFormId());
		}
		List<Location> locations = new ArrayList<Location>();
		long saveLocationId = 0;
		Location location = new Location();
		if(formAndField.getFields() != null){
			FormField formField = new FormField();
			Iterator<FormField> iter = formAndField.getFields().iterator();
			while(iter.hasNext()) {
				formField = iter.next();
				if(formField != null && formField.getLattitude() !=null && formField.getLongitude() !=null){
				location.setLatitude(formField.getLattitude());
				location.setLongitude(formField.getLongitude());
				location.setEmpId(webUser.getEmpId());
				location.setCompanyId(webUser.getCompanyId());
				location.setPurpose(Location.FOR_FORM);
			   }
				if(location != null && location.getLatitude()!=null && location.getLongitude()!=null && location.getLatitude() > 0 && location.getLongitude() > 0){

					long formsAddedLocationCurrentTime = Api.getCurrentTimeInUTCLong();
					saveLocationId = trackDao.saveLocation(location);

					if (location != null) {
						locations.add(location);
					}

					if (constants.isPrintLogs())
						Log.info(this.getClass(),
								"processOnlineFormSubmission - formsAddedLocationCurrentTime End. Time taken to process for empId "
										+ webUser.getEmpId() + " : "
										+ (Api.getCurrentTimeInUTCLong() - formsAddedLocationCurrentTime));

					if (locations != null && locations.size() > 0) {
						Location lastLocation = locations.get(locations.size() - 1);
						form.setLocation(lastLocation);
					}
					break;
				}
				
			}
			
		}
		
		int formSpecPurpose = formSpec.getPurpose();
		
		if (formSpecPurpose == FormSpec.PURPOUSE_WORK) 
		{
			String value = null;
			if(formAndField.getWorkSpec() != null && (formAndField.getWorkSpec().getWorkSpecId() == constants.getTvsDeliveryWorkSpecId()))
			{
				value = getWebAdditionalSupportManager().getWorkFieldValueForGivenUniqueId(formAndField,constants.getTvsZoneUserFormFieldUniqueId(),constants.getSkeletonWorkEmployeeFieldSpecId());
			}
			else {
				value = getWorkFieldValueForGivenSkeleton(formAndField,
						constants.getSkeletonWorkEmployeeFieldSpecId());
			}
			
			if (!Api.isEmptyString(value) && formAndField.isWorkSharing() && !formAndField.isWorkSharingSetUpDone()) {
					
						form.setAssignTo(webUser.getEmpId());
						formAndField.setAssignTo(webUser.getEmpId()+"");
						getWebSupportManager().replaceWorkFieldValueForGivenSkeleton(formAndField, 
								constants.getSkeletonWorkEmployeeFieldSpecId(), webUser.getEmpId()+"");
						formAndField.setWorkSharingEmpIds(value);
					
			}else if (!Api.isEmptyString(value)) {
				form.setAssignTo(Long.parseLong(value));
				formAndField.setAssignTo(value);
			}else{
				form.setAssignTo(null);
			}
			
			 //Map<String, String> settingKeyAndValue = getWebExtraManager().getCompanyWebSettings(webUser.getCompanyId(), true, false);
			 //String includeWorkInvitations = settingKeyAndValue.get(constantsExtra.getIncludeWorkInvitationsKey());
			String includeWorkInvitations = getWebAdditionalSupportExtraManager().getCompanySettingValueFromSettingsTable(webUser.getCompanyId(),"includeWorkInvitations");
			 if(!Api.isEmptyString(includeWorkInvitations) && includeWorkInvitations.equals("true")) {
				 formAndField.setIncludeWorkInvitations(true);
			 }
		}
		else
		{
			if (!Api.isEmptyString(formAndField.getAssignTo())) {
				form.setAssignTo(Long.parseLong(formAndField.getAssignTo()));
			} else {
				form.setAssignTo(null);
			}
		}
		
		try {
			Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
					"form", "add", form.toCSV(), null);
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
		}

		boolean isMasterForm = true;
		if(formSpec.getPurpose() == -1 || formSpec.getPurpose() == 0 ||
				formSpec.getPurpose() == FormSpec.PURPOUSE_WORK ||
				formSpec.getPurpose() == FormSpec.PURPOUSE_JOB) {
			isMasterForm = false;
		}
		form.setTurnAroundTime(formAndField.getTatTime());
		
		if(extraProperties!= null && extraProperties.getCheckInId()!= null && extraProperties.getCheckInId() != -1) {
			form.setCheckInId(extraProperties.getCheckInId());
		}
		if(extraProperties!= null) {
			form.setForcePerformActivity(extraProperties.isForceActivity());
		}
		form.setDraftForm(formAndField.getDraftForm() == 0?0:1);
		if(formAndField!= null) {
		form.setSourceOfAction(formAndField.getSourceOfAction());
		form.setFormFieldsUniqueKey(formAndField.getFormFieldsUniqueKey());
		}
		extraDao.insertForm(form, webUser.getCompanyId(), webUser.getEmpId(),
				null,isMasterForm,form.getAppVersion());
		formAndField.setFormId(form.getFormId());
		 logText = "addForm() // formSpecId = "+formAndField.getFormSpecId()+" formId = "+form.getFormId();
		 Log.info(getClass(), logText+" insertForm done isSkipSendJmsMessage = "+formAndField.isSkipSendJmsMessage());
		
		
		 getWebExtensionManager().autoSignOutBasedOnForm(extraProperties,webUser,saveLocationId,form);
		List<Location> formLocationsNew = new ArrayList<Location>();
		for (Location loc : locations) {
			if (loc.getLocationId() != 0) {
				getExtendedDao().insertWebLocations(loc.getLocationId(), webUser.getBrowserInfo(),webUser.getIpAddress());
				formLocationsNew.add(loc);
			}
		}

		if (formLocationsNew.size() > 0) {
			extraDao.insertFormLocationList(formLocationsNew, form.getFormId(), 1);
			 Log.info(getClass(), logText+" insertFormLocationList done");
		}
		
		if (formSpec.getPurpose() == -1) {
			FormSubmissionInstanceConfiguration formSubmissionInstanceConfiguration = getExtraSupportAdditionalSupportDao()
					.getFormSubmissionInstanceConfigurationByFormUniqueId(formSpec.getUniqueId());

			if (formSubmissionInstanceConfiguration != null && formSpec.getUniqueId()
					.equals(formSubmissionInstanceConfiguration.getFormUniqueInstanceUniqueId())) {
				
				FormSubmissionInstanceStatus formSubmissionInstanceStatus = getExtraSupportAdditionalSupportDao()
						.getFormSubmissionInstanceStatusByEmpId(
								formSubmissionInstanceConfiguration.getFormSubmissionInstanceConfigurationId(),
								webUser.getEmpId(),FormSubmissionInstanceStatus.STATUS_OPEN);
				if (Api.isEmptyObj(formSubmissionInstanceStatus)) {
					getExtraSupportAdditionalSupportDao().insertIntoFormSubmissionInstanceStatus(form.getFormId(),
							formSubmissionInstanceConfiguration.getFormSubmissionInstanceConfigurationId(), webUser);
				}
				
			} else if (formSubmissionInstanceConfiguration != null
					&& formSpec.getUniqueId().equals(formSubmissionInstanceConfiguration.getCloseFormUniqueId())) {
				FormSubmissionInstanceStatus formSubmissionInstanceStatus = getExtraSupportAdditionalSupportDao()
						.getFormSubmissionInstanceStatusByEmpId(
								formSubmissionInstanceConfiguration.getFormSubmissionInstanceConfigurationId(),
								webUser.getEmpId(),FormSubmissionInstanceStatus.STATUS_OPEN);
				if (!Api.isEmptyObj(formSubmissionInstanceStatus)) {

					getExtraSupportAdditionalSupportDao().updateFormSubmissionInstanceStatus(form.getFormId(),
							formSubmissionInstanceStatus.getFormSubmissionInstanceStatusId());
				}
			}

		}
		
		if(!formAndField.isIgnoreComputedFieldsComputation()) {
			getComputeFieldsManager().computeFields(formAndField.getFields(),
				formAndField.getSectionFields(), formAndField.getFormSpecId(), webUser,false,false,formAndField);
			 Log.info(getClass(), logText+" computeFields done");
		}

		if(formAndField.isFromMigrationFields()) {
		getWebExtensionManager().defaultFields(formAndField.getFields(),
				formAndField.getSectionFields(), formAndField.getFormSpecId(), webUser,false,false);
		Log.info(getClass(), logText+" defaultFields done");
		}
		resolveFormFields(formSpec, formFieldSpecs, formSectionSpecs,
				formAndField, Form.FORM_ACTION_SAVE_OR_UPDATE_TO_DB, webUser);
		Log.info(getClass(), logText+" resolveFormFields done");
		
		//To Set the guid for Text fields of GUID Enabled.
		getWebAdditionalSupportManager().generateGuidForFields(formFieldSpecs, formSectionSpecs,
				formAndField, formSpec.getPurpose());
		
		if (formAndField.getFields() != null) {
			Iterator<FormField> formFieldIterator = formAndField.getFields()
					.iterator();
			while (formFieldIterator.hasNext()) {
				FormField formField = formFieldIterator.next();
				if (Api.isEmptyString(formField.getFieldValue()) || formField.getFieldSpecId() <= 0) {
					formFieldIterator.remove();
				}
			}
			
			long startStartTimeFieldSpecId = constants.getSkeletonWorkStartsFieldSpecId();
			long startEndTimeFieldSpecId = constants.getSkeletonWorkEndsFieldSpecId();
			// number to word support in bulkuploads
			try {
				if (formAndField.getSourceOfAction() != null && formAndField.getSourceOfAction() == Work.BULKUPLOAD) {
					List<FormField> newNumberToWordFormFields = getBulkActionPerformManager()
							.populateNumberToWordFieldsNew(form.getFormId(), form.getFormSpecId() + "", formFieldSpecs,
									formAndField.getFields());
					formAndField.getFields().addAll(newNumberToWordFormFields);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (FormField formField : formAndField.getFields()) {
				formField.setFormId(form.getFormId());
				formField.setFormSpecId(form.getFormSpecId());
				/*
				 * if(formField.isFromMigrationFields()) {
				 * formField.setFromMigrationFields(formAndField.isFromMigrationFields());
				 * formField.setMigrationFilledBy(Long.parseLong(formAndField.getCreatedBy()));
				 * formField.setMigrationModifiedBy(Long.parseLong(formAndField.getCreatedBy()))
				 * ; formField.setMigrationCreatedTime(formAndField.getCreatedTime());
				 * formField.setMigrationModifiedTime(formAndField.getCreatedTime()); }
				 */
				Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
						"formField", "added", formField.toCSV(), null);
				for (FormFieldSpec formFieldSpec : formFieldSpecs) {
					if(formFieldSpec.getSkeletonFormFieldSpecId() != null && formFieldSpec.getSkeletonFormFieldSpecId() == startStartTimeFieldSpecId) {
						if(formFieldSpec.isComputedField()) {
						if(formField.getFieldSpecId() == formFieldSpec.getFieldSpecId()) {
							String fieldValue = formField.getFieldValue();
							String startTime = "";
							try {
								 startTime = Api.getDateTimeInTzToUtc(Api.getCalendar(Api.getDateTimeInUTC(fieldValue)), webUser.getTzo()+"");
								formField.setFieldValue(startTime);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				}
					if(formFieldSpec.getSkeletonFormFieldSpecId() != null && formFieldSpec.getSkeletonFormFieldSpecId() == startEndTimeFieldSpecId) {
						if(formFieldSpec.isComputedField()) {
						if(formField.getFieldSpecId() == formFieldSpec.getFieldSpecId()) {
							String fieldValue = formField.getFieldValue();
							String endTime = "";
							try {
								endTime = Api.getDateTimeInTzToUtc(Api.getCalendar(Api.getDateTimeInUTC(fieldValue)), webUser.getTzo()+"");
								formField.setFieldValue(endTime);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}

				}

			}

			}
			
			FormField autoGenSeqFormField = null;
			if(formAndField.getSectionFields() != null) {
				autoGenSeqFormField = getWebSupportManager().
						getAutoGenFormField(form, formAndField.getFields(), formSpec,webUser.getTzo()+"",
								isMasterForm,formAndField.getSectionFields());
			}else {
				autoGenSeqFormField = getWebSupportManager().
						getAutoGenFormField(form, formAndField.getFields(), formSpec,webUser.getTzo()+"",
								isMasterForm,null);
			}
			if(autoGenSeqFormField != null)
				formAndField.getFields().add(autoGenSeqFormField);
			
			getWebFunctionalManager().resolveRichTextFormFields(form, formAndField.getFields(), formSpec);
			extraDao.insertFormFields(formAndField.getFields(),isMasterForm,false,false);
			Log.info(getClass(), logText+" insertFormFields done");
		}

		if (formAndField.getSectionFields() != null) {
			Iterator<FormSectionField> formSectionFieldIterator = formAndField
					.getSectionFields().iterator();
			while (formSectionFieldIterator.hasNext()) {
				FormSectionField formSectionField = formSectionFieldIterator
						.next();
				if (Api.isEmptyString(formSectionField.getFieldValue())) {
					formSectionFieldIterator.remove();
				}
			}

			for (FormSectionField formSectionField : formAndField
					.getSectionFields()) {
				formSectionField.setFormId(form.getFormId());
				formSectionField.setFormSpecId(form.getFormSpecId());
				Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
						"formSectionField", "added", formSectionField.toCSV(),
						null);
			}
			getWebFunctionalManager().resolveRichTextFormSectionFields(form, formAndField.getSectionFields(), formSpec);
			extraDao.insertFormSectionFields(formAndField.getSectionFields(),false,false,isMasterForm);
		}

		formAndField.setFormId(form.getFormId());
		
		if(formSpec.getPurpose() == -1)
		{
			CustomEntitySpecStockConfiguration customEntitySpecStockConfiguration = getExtraSupportAdditionalSupportDao().getCustomEntitySpecStockConfigurationByFormSpecUniqueId(formSpec.getUniqueId(),0);
			
			if(!Api.isEmptyObj(customEntitySpecStockConfiguration))
			{
				getExtraSupportAdditionalSupportDao().insertIntoCustomEntityCreationOnStockUpdation(form.getFormId(),customEntitySpecStockConfiguration);
			}
		}
		
		boolean onsubmitApproval = getWebExtensionManager().reqTriggerStatus(formSpec.getUniqueId(),webUser.getCompanyId(),webUser);
		Log.info(getClass(), logText+" onsubmitApproval = "+onsubmitApproval+" isSkipSendJmsMessage = "+formAndField.isSkipSendJmsMessage()
		+" saveHidden = "+saveHidden);
		if ("save_approval".equalsIgnoreCase(saveHidden) || onsubmitApproval) {
			createOrUpdateWorkflowForForm(form, webUser);
		}

		if (!Api.isEmptyString(followupJobJson)) {
			createFollowupVisitwhenFormFilled(form, webUser, followupJobJson);
		}

		if (formSpecPurpose == FormSpec.PURPOUSE_WORK) {
			if(extraProperties!=null && extraProperties.getCheckRecurring())
			{
				extraProperties.setIsTemplate(true);
			}
			
			long workId=addWork(formAndField, webUser, formSpec, extraProperties);
			logText = "addForm() // formSpecId = "+formAndField.getFormSpecId()+" formId = "+form.getFormId()+" workId = "+workId;
			Log.info(getClass(), logText+" addWork done");
			Work work  = extraDao.getWork(workId+"");
			WorkSpec workSpec =getWorkSpec(work.getWorkSpecId(), work.getCompanyId());
			if (workSpec.isEnableAssigmentService()) {
				getWebAdditionalSupportExtraManager().updateWorkAssignmentInfo(
						WorksAssignmentInfo.STATUS_FOR_UNASSIGNED_BY_USER, webUser.getEmpId(), workId);
				if (work.getAssignTo() != null) {
						getWebAdditionalSupportExtraManager().insertWorkAssignmentInfo(work.getWorkId(),
								work.getWorkSpecId(), work.getAssignTo(), webUser.getEmpId(), webUser.getCompanyId());
					} 
			}
			
			workIdForFollowUpWork = workId;
			formAndField.setWorkId(workId+"");
			if(extraProperties!=null &&  extraProperties.getCheckRecurring())
			{
				extraProperties.setRecurringParentId(workId);
				extraProperties.setIsTemplate(false);
				try
				{
					long recurrenceId = insertRecurrence(2,
							extraProperties.getFrequency(), extraProperties.getEventCount(),
							webUser.getEmpId(), webUser.getCompanyId(), false,
							workId, Api.getCurrentTimeTz24(),
							Api.getCurrentTimeTz24(), extraProperties.getRecurringFromDate(),extraProperties.getRecurringEndsNever(),extraProperties.getRecurrenceDays(),extraProperties.getRecurringEndDate());
					recurrenceWorkId = addFormForWorkRecurrence(formAndField, webUser, saveHidden, followupJobJson, extraProperties,jmsMessages);
					
					String transactionId = Api.getDateToStringForTransactionId(new Date());
					RecurringEvent recurringEvent = getWebFunctionalManager().getRecurringEvent(webUser.getCompanyId(),recurrenceWorkId, extraProperties.getFrequency(), 
							extraProperties.getEventCount(), extraProperties.getRecurringFromDate(), RecurringEventScheduler.RECCURENCE_TYPE_WORKS, extraProperties.getRecurrenceDays(), extraProperties.getRecurringEndDate(),extraProperties.getRecurringEndsNever(),transactionId);

					processRecurrenceForWork(webUser, extraProperties, formAndField, saveHidden, followupJobJson, workId, null, null, null,"create",workFollowUp,extraProperties.getRecurringFromDate(),recurringEvent);
				}
				catch(Exception e)
				{
					Log.info(getClass(), logText+" Error while creating child works: "+e);
				}
			}
		}
		
		if (formSpecPurpose == FormSpec.PURPOUSE_CUSTOM_ENTITY) {
			CustomEntity customEntity = getWebAdditionalSupportManager().getCustomEntity(formAndField, webUser.getEmpId(), 
					formSpec,webUser.getCompanyId(),null);
			long customEntityId = extraSupportDao.addCustomEntity(customEntity,null);
			formAndField.setCustomEntityId(customEntityId+"");
			CustomEntitySpec customEntitySpec = extraSupportDao.getCustomEntitySpec(customEntity.getCustomEntitySpecId(), webUser.getCompanyId());
			if (!formAndField.getFields().isEmpty() && CustomEntitySpec.CONFIGURATION_BASED_MAPPING == customEntitySpec.getCustomEntityEmpMappingType()) {
				getWebAdditionalSupportExtraManager().insertIntoEmpMasterMappingStatus(webUser.getCompanyId().longValue(), 
						customEntityId, null, EmpMasterMappingStatus.CUSTOM_ENTITY_EVENT_TRIGGER, webUser.getEmpId());
			}
			if(CustomEntitySpec.BASIC_MAPPING == customEntitySpec.getCustomEntityEmpMappingType()) {
				getWebAdditionalSupportManager().mapCustomEntityToEmployee(customEntity,webUser.getEmpId(),webUser.getCompanyId(),customEntityId);
			}
		}
		
		if("true".equalsIgnoreCase(workFollowUp) && workIdForFollowUpWork != 0){
			if(recurrenceWorkId != 0){
				insertWorkIdForFollowUpWorkAttchment(recurrenceWorkId,workFollowUp,webUser.getCompanyId(),webUser.getEmpId());
			}else{
				insertWorkIdForFollowUpWorkAttchment(workIdForFollowUpWork,workFollowUp,webUser.getCompanyId(),webUser.getEmpId());
			}
			
		}
		
		
		List<WorkAttachmentForFollowUpWork> attachmentsForFollowUpWork = extraDao.getWorkAttachmentforFollowUpWork(webUser.getCompanyId(),webUser.getEmpId());
		if (extraProperties != null) {

			if (ExtraProperties.WORK_FORM_TYPE_ATTACHMENT
					.equalsIgnoreCase(extraProperties.getWorkActionType())) {
				createWorkAtachment(extraProperties.getWorkId(),
						form.getFormId());
			}
		
			// check for workstatus
			else if (!Api.isEmptyString(extraProperties.getWorkId())
					&& !Api.isEmptyString(extraProperties.getWorkActionSpecId())) {
				
				Work work = getWork("" + extraProperties.getWorkId());
				String auditType = "";
				if(formAndField.getSourceOfAction() != null && formAndField.getSourceOfAction() == Work.WEB) {
					auditType = "Web";
				}if(formAndField.getSourceOfAction() != null && formAndField.getSourceOfAction() == Work.API) {
					auditType = "Api";
				}
				WorkSpec workSpec =getWorkSpec(work.getWorkSpecId(), work.getCompanyId());
				WorkActionSpec workActionSpec = getWorkActionSpec(extraProperties.getWorkActionSpecId());
				if(workSpec.isWorkTreeStructureEnabled())
				{
					long actionCount = getExtraSupportAdditionalSupportDao().findWorkActionSpecsCountBySpecWorkSpecId(workSpec.getWorkSpecId());
					long  workStatusHistories = getExtraSupportAdditionalSupportDao().getWorkStatusHistoriesCount(work.getWorkId());
					if(actionCount == workStatusHistories + 1)
					{
						formAndField.setWorkCompleted(true);
					}
				}
				
				FormLocation formLocation = extraDao
						.getLatestLocationIdForFormIdIn(form.getFormId());
				if(form.getDraftForm() == 1 || workActionSpec.isCaseManagementAction()) {
					String time = Api
							.getDateTimeInUTC(new Date(System.currentTimeMillis()));
					WorkStatusHistory workStatusHistory = new WorkStatusHistory();
					// workStatusHistory.setWorkStatusId(workStatus.getWorkStatusId());
					workStatusHistory.setFormId(form.getFormId());
					workStatusHistory.setModifiedBy(webUser.getEmpId());
					workStatusHistory.setWorkSpecId(work.getWorkSpecId());
					workStatusHistory.setWorkId(Long.parseLong(extraProperties.getWorkId()));
					workStatusHistory.setWorkActionSpecId(Long.parseLong(extraProperties.getWorkActionSpecId()));
					workStatusHistory.setWorkStatusCreatedTime(time);
					workStatusHistory.setWorkStatusModifiedTime(time);
					workStatusHistory.setWorkStatusCreatedTime(time);
					workStatusHistory.setWorkStatuHistoryCreatedTime(time);
					workStatusHistory.setSourceOfAction(auditType);
					workStatusHistory.setBulkActionPerform(extraProperties.getBulkActionPerform());
					if (formLocation != null) {
						workStatusHistory.setLocationId(formLocation.getLocationId());
					}
					if (form.getDraftForm() == 1) {
						workStatusHistory.setDraft(true);
					}
					
					extraDao.insertWorkStatusHistory(workStatusHistory, null);
					
					// Handling previousactionperformed
					boolean resolveActionableMethod = false;
					resolveActionableMethod = getWebExtensionManager().validateAndResolveActionAssignments(
							workStatusHistory.getWorkSpecId(), workStatusHistory.getWorkActionSpecId());
					if (resolveActionableMethod) {
						getWebExtensionManager().resolveActionAssignments(work.getWorkSpecId(), webUser, workStatusHistory);
					}
					
				    workFlowExtraDao.updateWorkActionExcalatedEmpIds(work.getWorkId(),Long.parseLong(extraProperties.getWorkActionSpecId()));

				}
				else {
					changeWorkStatus(Long.parseLong(extraProperties.getWorkId()),
							extraProperties.getWorkActionSpecId(),
							form.getFormId(), webUser.getCompanyId(),
							webUser.getEmpId(), formAndField.isWorkCompleted(),null,auditType,extraProperties,null,jmsMessages);
				}
				
				Log.info(getClass(), logText+" insertWorkStatusHistory and changeWorkStatus done");
				
				if(extraProperties.isTelenorWork()){//for Telenor Company.
					getWebSupportManager().updateAssignToForTelenorWorkIfRejected(extraProperties.getWorkActionSpecId(), extraProperties.getWorkId(), webUser);
				}
				isWorkActionForm = true;
				getWebAdditionalSupportExtraManager().updateFlatTableDataStatusForWorks(work, webUser.getCompanyId(), work.getWorkSpecId());
			}

		}
		if(attachmentsForFollowUpWork != null)
		{
			for (WorkAttachmentForFollowUpWork workAttachmentForFollowUpWork : attachmentsForFollowUpWork) {
				
				if("false".equalsIgnoreCase(workFollowUp) && !workAttachmentForFollowUpWork.isDeleted())
				{
					createWorkAtachmentForFollowUpWork(workAttachmentForFollowUpWork.getWorkId()+"",
							form.getFormId());
					extraDao.updateAttachmentsForFollowUpWork(workAttachmentForFollowUpWork.getId());
				}
			}
		}
		if((formSpecPurpose == 0 || formSpecPurpose == -1) && !formAndField.isSkipSendJmsMessage()) {
			sendJmsMessage(JmsMessage.TYPE_FORM_MEDIA_COMMITED, -1, webUser.getCompanyId(), 
					webUser.getEmpId(), JmsMessage.CHANGE_TYPE_OTHER,
					form.getFormId(), null, null);
		}

		if((formSpecPurpose == 0 || formSpecPurpose == -1) && !isWorkActionForm) {
			try {
			    Log.info(this.getClass(), logText+" inside insertIntoFlatTableDataStatus() // formId = "+form.getFormId());
		        getWebAdditionalSupportExtraManager().insertIntoFlatTableDataStatusForNormalForms(form, webUser.getCompanyId(), formSpec.getUniqueId(),false);
		    }
		    catch(Exception ex) {
		        Log.info(this.getClass(), logText+" insertIntoFlatDataTables() Got Exception :-"+ex.toString());
		        ex.printStackTrace();
		    }
		}
		if((extraProperties != null) && (Api.isNumber(extraProperties.getWorkId()))){
			
			if(webUser.getCompanyId() == Long.parseLong(constantsExtra.getMaruthiCompanyId())) 
			{
				getWebAdditionalSupportExtraManager().insertOrUpdateMarutiActionableWorksToProcess(Long.parseLong(extraProperties.getWorkId()));
			}
			else
			{
				getWebAdditionalSupportExtraManager().insertOrUpdateActionableWorksToProcess(Long.parseLong(extraProperties.getWorkId()));
			}
		}
		Log.info(getClass(), logText+" ends.");
	}
	
	

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public long addFormForWorkRecurrence(FormAndField formAndField, WebUser webUser,
			String saveHidden, String followupJobJson,
			ExtraProperties extraProperties, List<JmsMessage> jmsMessages) throws EffortError {
		FormSpec formSpec = getFormSpec("" + formAndField.getFormSpecId());
		List<FormFieldSpec> formFieldSpecs = getFormFieldSpecs(formAndField
				.getFormSpecId());
		List<FormSectionSpec> formSectionSpecs = getFormSectionSpecs(formAndField
				.getFormSpecId());
		
		Long skeletonFieldSpecIdWorkEmployee = constants
				.getSkeletonWorkEmployeeFieldSpecId();
		Long fieldSpecIdWorkEmployee = Long.parseLong(extraDao
				.getFormFieldSpecId(formSpec.getFormSpecId() + "",
						skeletonFieldSpecIdWorkEmployee + ""));
		
		long recurringWorkId = 0;
		Form form = new Form();
		form.setCompanyId(webUser.getCompanyId());
		form.setFilledBy(webUser.getEmpId());
		form.setFormSpecId(formAndField.getFormSpecId());
		form.setModifiedBy(webUser.getEmpId());
		if (!Api.isEmptyString(formAndField.getAssignTo()) && !"null".equalsIgnoreCase(formAndField.getAssignTo())) {
			form.setAssignTo(Long.parseLong(formAndField.getAssignTo()));
		} else {
			form.setAssignTo(null);
		}
		try {
			Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
					"form", "add", form.toCSV(), null);
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
		}
		   if(formAndField!= null) {
			form.setFormFieldsUniqueKey(formAndField.getFormFieldsUniqueKey());
			}
		extraDao.insertForm(form, webUser.getCompanyId(), webUser.getEmpId(),
				null,false,form.getAppVersion());

		getComputeFieldsManager().computeFields(formAndField.getFields(),
				formAndField.getSectionFields(), formAndField.getFormSpecId(), webUser,false,false,formAndField);

		resolveFormFields(formSpec, formFieldSpecs, formSectionSpecs,
				formAndField, Form.FORM_ACTION_SAVE_OR_UPDATE_TO_DB, webUser);
		if (formAndField.getFields() != null) {
			Iterator<FormField> formFieldIterator = formAndField.getFields()
					.iterator();
			while (formFieldIterator.hasNext()) {
				FormField formField = formFieldIterator.next();
				if (Api.isEmptyString(formField.getFieldValue())) {
					formFieldIterator.remove();
				}
				if(formField.getFieldSpecId() == fieldSpecIdWorkEmployee)
				{
					List<Employee> employee = getWebSupportManager().getEnableEmployees(formField.getFieldValue()+"");
					if(employee.size() >0)
					{
						
					}else
					{
						formFieldIterator.remove();
					}
					
				}
			}
			
			

			for (FormField formField : formAndField.getFields()) {
				formField.setFormId(form.getFormId());
				formField.setFormSpecId(form.getFormSpecId());
				Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
						"formField", "added", formField.toCSV(), null);
			}
			
			FormField autoGenSeqFormField = null;
			if(formAndField.getSectionFields() != null) {
				autoGenSeqFormField = getWebSupportManager().getAutoGenFormField(form, formAndField.getFields(), formSpec,
						webUser.getTzo()+"",false,formAndField.getSectionFields());
			}else {
				autoGenSeqFormField = getWebSupportManager().getAutoGenFormField(form, formAndField.getFields(), formSpec,
						webUser.getTzo()+"",false,null);
			}
			
			if(autoGenSeqFormField != null)
				formAndField.getFields().add(autoGenSeqFormField);
			extraDao.insertFormFields(formAndField.getFields(),false,false,false);
		}

		if (formAndField.getSectionFields() != null) {
			Iterator<FormSectionField> formSectionFieldIterator = formAndField
					.getSectionFields().iterator();
			while (formSectionFieldIterator.hasNext()) {
				FormSectionField formSectionField = formSectionFieldIterator
						.next();
				if (Api.isEmptyString(formSectionField.getFieldValue())) {
					formSectionFieldIterator.remove();
				}
			}

			for (FormSectionField formSectionField : formAndField
					.getSectionFields()) {
				formSectionField.setFormId(form.getFormId());
				formSectionField.setFormSpecId(form.getFormSpecId());
				Log.audit(webUser.getCompanyId() + "", webUser.getEmpId() + "",
						"formSectionField", "added", formSectionField.toCSV(),
						null);
			}
			extraDao.insertFormSectionFields(formAndField.getSectionFields(),false,false,false);
		}

		formAndField.setFormId(form.getFormId());

		boolean onsubmitApproval = getWebExtensionManager().reqTriggerStatus(formSpec.getUniqueId(),webUser.getCompanyId(),webUser);

		if ("save_approval".equalsIgnoreCase(saveHidden) || onsubmitApproval) {
			createOrUpdateWorkflowForForm(form, webUser);
		}

		if (!Api.isEmptyString(followupJobJson)) {
			createFollowupVisitwhenFormFilled(form, webUser, followupJobJson);
		}
     // long workId=addWork(formAndField, webUser, formSpec, extraProperties);
		long workId=getWebAdditionalManager().addWorkForRecurring(formAndField, webUser, formSpec, extraProperties);
      recurringWorkId=workId;
      Work workForRecurrence = extraDao.getWork(recurringWorkId+"");
	  getWebAdditionalSupportExtraManager().sendNextActionInvitation(workForRecurrence, workForRecurrence.getWorkSpecId(), webUser);
	  sendJmsMessage(JmsMessage.TYPE_WORKS_ADD_MODIFY, workId,webUser.getCompanyId(), webUser.getEmpId(), 
				JmsMessage.CHANGE_TYPE_ACTIONABLE_WORKS, workId, null, false);
	  
	  if (extraProperties != null) {

			if (ExtraProperties.WORK_FORM_TYPE_ATTACHMENT
					.equalsIgnoreCase(extraProperties.getWorkActionType())) {
				createWorkAtachment(extraProperties.getWorkId(),
						form.getFormId());

			}
			// check for workstatus
			else if (!Api.isEmptyString(extraProperties.getWorkId())
					&& !Api.isEmptyString(extraProperties.getWorkActionSpecId())) {
				changeWorkStatus(Long.parseLong(extraProperties.getWorkId()),
						extraProperties.getWorkActionSpecId(),
						form.getFormId(), webUser.getCompanyId(),
						webUser.getEmpId(), formAndField.isWorkCompleted(),null,null,extraProperties,null,jmsMessages);

			}
                    }

		return recurringWorkId;
		}
	
	
	
	public boolean isRootEmployee(WebUser webUser) {
		Long rootEmpId = getRootEmployeeId(webUser);

		if (rootEmpId != null && webUser.getEmpId() != null
				&& webUser.getEmpId().longValue() == rootEmpId.longValue()) {
			return true;
		}

		return false;
	}
	
	public Long getRootEmployeeId(WebUser webUser) {
		Long rootEmpId = null;
		try {
			rootEmpId = Long.parseLong(settingsDao.getCompanySetting(
					webUser.getCompanyId(), constants.getRootEmpKey()));
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
		}

		return rootEmpId;
	}
	public String getWorkFieldValueForGivenSkeleton(FormAndField formAndField,
			long skeletonFieldSpecId) {
		Map<Long, FormField> fielSpecIdAndFieldMap = new HashMap<Long, FormField>();
		Map<Long, FormFieldSpec> skeletonFieldSpecIdAndFormFieldSpecMap = new HashMap<Long, FormFieldSpec>();

		List<FormField> formFields = formAndField.getFields();

		// FormSpec formSpec=getFormSpec(""+formAndField.getFormSpecId());
		List<FormFieldSpec> formFieldSpecs = getFormFieldSpecs(formAndField
				.getFormSpecId());

		fielSpecIdAndFieldMap = (Map) Api.getMapFromList(formFields,
				"fieldSpecId");
		skeletonFieldSpecIdAndFormFieldSpecMap = (Map) Api.getMapFromList(
				formFieldSpecs, "skeletonFormFieldSpecId");
		FormFieldSpec formFieldSpec = skeletonFieldSpecIdAndFormFieldSpecMap
				.get(skeletonFieldSpecId);

		if(formFieldSpec == null) {
			return null;
		}
		FormField formFied = fielSpecIdAndFieldMap.get(formFieldSpec
				.getFieldSpecId());
		if (formFied != null) {
			return formFied.getFieldValue();
		}

		return null;

	}

	public long insertCustomer(Customer customer) {
		return extraDao.insertCustomer(customer, null);

	}
	public List<FormSectionSpec> getFormSectionSpecs(long formSpecId) {
		List<FormSectionSpec> formSectionSpecs = extraDao
				.getFormSectionSpecs(formSpecId);
		Map<Long, List<FormSectionFieldSpec>> sectionFieldSpecMap = extraDao
				.getFormSectionFieldSpecMap(formSectionSpecs);

		for (FormSectionSpec formSectionSpec : formSectionSpecs) {
			List<FormSectionFieldSpec> sectionFieldSpecs = sectionFieldSpecMap
					.get(formSectionSpec.getSectionSpecId());
			getWebExtensionManager().resolveFormSectionFieldStyleProperties(sectionFieldSpecs);
			formSectionSpec.setFormSectionFieldSpecs(sectionFieldSpecs);
		}

		return formSectionSpecs;
	}
	
	public List<VisibilityDependencyCriteria> getVisibilityDependencyCriterias(
			String formSpecId) {
		return extraDao.getVisibilityDependencyCriterias(formSpecId);
	}
	
	public void resolveVisibiltyDependecyForForForm(long formSpecId,
			List<FormFieldSpec> fieldSpecs,
			List<FormSectionSpec> formSectionSpecs, List<FormField> fields,
			List<FormSectionField> sectionFields,
			Map<String, Integer> visibilityCondtionSectionFieldSpecMap,
			boolean isConsiderEditRestiction, Map<String, Boolean> condtionMandatorySectionFieldSpecMap, Customer customer) {
		
		List<VisibilityDependencyCriteria> visibilityDependencyCriterias = getVisibilityDependencyCriterias(""+ formSpecId);
		
		Map<Long, FormField> formFieldsMap = new HashMap<Long, FormField>();
		Map<String, Long> formFieldspecIdExpressionMap = new HashMap<String, Long>();
		Map<Long, FormFieldSpec> formFieldspecMap = new HashMap<Long, FormFieldSpec>();
		Map<String, FormSectionField> formSectionFieldDataMap = new HashMap<String, FormSectionField>();

		Map<Long, List<FormSectionField>> formSectionFieldsMap = new HashMap<Long, List<FormSectionField>>();
		Map<String, Long> formSectionFieldspecIdExpressionMap = new HashMap<String, Long>();
		Map<Long, FormSectionFieldSpec> formSectionFieldspecMap = new HashMap<Long, FormSectionFieldSpec>();
		List<FormSectionFieldSpec> allFormSectionFieldSpecs = new ArrayList<FormSectionFieldSpec>();
		Map<Long, Integer> sectionSpecMaxInstanceMap = new HashMap<Long, Integer>();
		FormSpec formSpec = getFormSpec(formSpecId+"");
		for (FormSectionField formSectionField : sectionFields) {
			String formSectionFieldKey = formSectionField
					.getSectionFieldSpecId()
					+ "_"
					+ formSectionField.getInstanceId();
			List<FormSectionField> formSectionFields = formSectionFieldsMap
					.get(formSectionField.getSectionFieldSpecId());
			if (formSectionFields == null) {
				formSectionFields = new ArrayList<FormSectionField>();
				formSectionFieldsMap.put(
						formSectionField.getSectionFieldSpecId(),
						formSectionFields);
			}
			formSectionFields.add(formSectionField);
			formSectionFieldDataMap.put(formSectionFieldKey, formSectionField);
			Integer maxInstanceId = sectionSpecMaxInstanceMap
					.get(formSectionField.getSectionSpecId());
			if (maxInstanceId == null) {
				sectionSpecMaxInstanceMap.put(
						formSectionField.getSectionSpecId(),
						formSectionField.getInstanceId());
			} else {
				if (maxInstanceId < formSectionField.getInstanceId()) {
					sectionSpecMaxInstanceMap.put(
							formSectionField.getSectionSpecId(),
							formSectionField.getInstanceId());
				}
			}
		}

		for (FormSectionSpec formSectionSpec : formSectionSpecs) {
			List<FormSectionFieldSpec> formSectionFieldSpecs = formSectionSpec
					.getFormSectionFieldSpecs();
			allFormSectionFieldSpecs.addAll(formSectionFieldSpecs);
			for (FormSectionFieldSpec formSectionFieldSpec : formSectionFieldSpecs) {
				formSectionFieldspecIdExpressionMap.put(
						formSectionFieldSpec.getExpression(),
						formSectionFieldSpec.getSectionFieldSpecId());
				formSectionFieldspecMap.put(
						formSectionFieldSpec.getSectionFieldSpecId(),
						formSectionFieldSpec);
			}

		}

		for (FormFieldSpec formFieldSpec : fieldSpecs) {
			formFieldspecIdExpressionMap.put(formFieldSpec.getExpression(),
					formFieldSpec.getFieldSpecId());
			formFieldspecMap.put(formFieldSpec.getFieldSpecId(), formFieldSpec);
		}
		
		Map<String, CustomerField> customerFieldExpressionMap = new HashMap<String, CustomerField>();
		if(formSpec.getPurpose()==FormSpec.PURPOUSE_CUSTOMER){
			List<CustomerField> customerFields = getWebExtraManager().getCustomerFieldsForCompany(formSpec.getCompanyId());
			if(customerFields == null || (customerFields.isEmpty() && customerFields.size() == 0) )
					customerFields=getWebExtraManager().getCustomerFields();
			for(CustomerField systemField : customerFields){
				if(systemField.getCustomerFieldId()!= null){
					formFieldspecIdExpressionMap.put("SDCF"+systemField.getCustomerFieldId(),
							Long.parseLong(systemField.getCustomerFieldId()+""));
				}
				systemField.setFieldSelector("SDCF"+systemField.getCustomerFieldId());
				customerFieldExpressionMap.put("SDCF"+systemField.getCustomerFieldId(), systemField);
			}
		}

		if(fields != null){
			for (FormField formField : fields) {
				formFieldsMap.put(formField.getFieldSpecId(), formField);
			}
		}

		// List<FormSectionFieldSpecValidValue> formSectionFieldSpecValidValue=
		// extraDao.getFormSectionFieldSpecValidValuesIn(allFormSectionFieldSpecs);
		// List<FormFieldSpecValidValue>
		// formFieldSpecValidValue=extraDao.getFormFieldSpecValidValuesIn(fieldSpecs);
		//
		// for (VisibilityDependencyCriteria visibilityDependencyCriteria :
		// visibilityDependencyCriterias) {
		// if(visibilityDependencyCriteria.getFieldDataType()==5||visibilityDependencyCriteria.getFieldDataType()==6){
		// if(visibilityDependencyCriteria.getTargetFieldExpression().startsWith("F")){
		// Long
		// fieldSpecid=formFieldspecIdExpressionMap.get(visibilityDependencyCriteria.getTargetFieldExpression());
		// String valueIds=getFormFieldValidValueIdsForGivenSpecId(fieldSpecid,
		// formFieldSpecValidValue, visibilityDependencyCriteria.getValue());
		// visibilityDependencyCriteria.setValue(valueIds);
		//
		// }else{
		// Long
		// fieldSpecid=formSectionFieldspecIdExpressionMap.get(visibilityDependencyCriteria.getTargetFieldExpression());
		// String
		// valueIds=getFormSectionFieldValidValueIdsForGivenSpecId(fieldSpecid,formSectionFieldSpecValidValue,visibilityDependencyCriteria.getValue());
		// visibilityDependencyCriteria.setValue(valueIds);
		//
		// }
		// }
		//
		// }

		Map<Long, List<VisibilityDependencyCriteria>> visibilityDependencyCriteriaMapForFormFields = getVisibilityDependencyCriteriaMap(
				"" + formSpecId, FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD,
				visibilityDependencyCriterias);

		Map<Long, List<VisibilityDependencyCriteria>> visibilityDependencyCriteriaMapForSectionFields = getVisibilityDependencyCriteriaMap(
				"" + formSpecId,
				FieldSpecRestrictionGroup.FIELD_IS_SECTIONFIELD,
				visibilityDependencyCriterias);
		
		Map<Long, List<VisibilityDependencyCriteria>> visibilityDependencyCriteriaMapForSections = getVisibilityDependencyCriteriaMap(
				"" + formSpecId,
				FieldSpecRestrictionGroup.TYPE_SECTION,
				visibilityDependencyCriterias);
		

		Set<Long> hiddenFields = new HashSet<Long>();
		Set<String> hiddenSectionFields = new HashSet<String>();
		
		boolean hasNewHiddenField = getWebAdditionalManager().resolveVisibilityCriteriaNew(fieldSpecs, visibilityDependencyCriteriaMapForFormFields, isConsiderEditRestiction
				, formFieldspecIdExpressionMap, formFieldsMap, formFieldspecMap, 
				formSectionFieldspecIdExpressionMap, formSectionFieldDataMap, formSectionFieldspecMap
				,formSectionSpecs, visibilityDependencyCriteriaMapForSectionFields, formSectionFieldsMap
				,sectionSpecMaxInstanceMap,condtionMandatorySectionFieldSpecMap, visibilityCondtionSectionFieldSpecMap
				, visibilityDependencyCriteriaMapForSections 
				,hiddenFields , hiddenSectionFields, customerFieldExpressionMap, customer);

		while(hasNewHiddenField)
		{
			hasNewHiddenField = getWebAdditionalManager().resolveVisibilityCriteriaNew(fieldSpecs, visibilityDependencyCriteriaMapForFormFields, isConsiderEditRestiction
					, formFieldspecIdExpressionMap, formFieldsMap, formFieldspecMap, 
					formSectionFieldspecIdExpressionMap, formSectionFieldDataMap, formSectionFieldspecMap
					,formSectionSpecs, visibilityDependencyCriteriaMapForSectionFields, formSectionFieldsMap
					,sectionSpecMaxInstanceMap,condtionMandatorySectionFieldSpecMap, visibilityCondtionSectionFieldSpecMap
					, visibilityDependencyCriteriaMapForSections 
					,hiddenFields , hiddenSectionFields, customerFieldExpressionMap, customer);
		}
			}
	
	
	
	public Map<Long, List<VisibilityDependencyCriteria>> getVisibilityDependencyCriteriaMap(
			String formSpecId, int fieldType,
			List<VisibilityDependencyCriteria> visibilityDependencyCriterias) {
		Map<Long, List<VisibilityDependencyCriteria>> visibilityDependencyCriteriaMap = new HashMap<Long, List<VisibilityDependencyCriteria>>();
		if (visibilityDependencyCriterias != null) {
			for (VisibilityDependencyCriteria visibilityDependencyCriteria : visibilityDependencyCriterias) {
				if (visibilityDependencyCriteria.getFieldType() == fieldType) {
					List<VisibilityDependencyCriteria> visibilityDependencyCriteriasForField = visibilityDependencyCriteriaMap
							.get(visibilityDependencyCriteria.getFieldSpecId());
					if (visibilityDependencyCriteriasForField == null) {
						visibilityDependencyCriteriasForField = new ArrayList<VisibilityDependencyCriteria>();
						visibilityDependencyCriteriaMap.put(
								visibilityDependencyCriteria.getFieldSpecId(),
								visibilityDependencyCriteriasForField);
					}
					visibilityDependencyCriteriasForField
							.add(visibilityDependencyCriteria);

				}
			}
		}
		return visibilityDependencyCriteriaMap;
	}

	public Map<Long, List<ListFilteringCritiria>> getListFilteringCritiriaMap(
			List<ListFilteringCritiria> listFilteringCritiria, int type) {
		Map<Long, List<ListFilteringCritiria>> listFilteringCritiriaMap = new HashMap<Long, List<ListFilteringCritiria>>();
		if (listFilteringCritiria != null) {
			for (ListFilteringCritiria listFilteringCritiria2 : listFilteringCritiria) {
				if (listFilteringCritiria2.getType() == type) {
					List<ListFilteringCritiria> listFilteringCritirias = listFilteringCritiriaMap
							.get(listFilteringCritiria2.getFieldSpecId());
					if (listFilteringCritirias == null) {
						listFilteringCritirias = new ArrayList<ListFilteringCritiria>();
						listFilteringCritiriaMap.put(
								listFilteringCritiria2.getFieldSpecId(),
								listFilteringCritirias);
					}
					listFilteringCritirias.add(listFilteringCritiria2);
				}
			}
		}
		return listFilteringCritiriaMap;
	}
	
	public void resolveFormFieldsWithRestrictions(
			List<FormFieldSpec> formFieldSpecs, String formSpeId,
			String groupIds,
			List<ListFilteringCritiria> listFilteringCritirias,
			List<VisibilityDependencyCriteria> visibilityDependencyCriterias, List<FieldValidationCritiria> fieldValidationFilteringCritirias,List<CustomEntityFilteringCritiria> customEntityFilteringCritirias,
			boolean inVisibleFieldsDisplay, List<CustomerAutoFilteringCritiria> customerAutoFilteringCritirias) {
		if (formFieldSpecs != null) {
			Map<Long, List<ListFilteringCritiria>> listFilteringCritiriasMap = getListFilteringCritiriaMap(
					listFilteringCritirias,
					FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD);
			Map<Long, List<CustomerAutoFilteringCritiria>> customerAutoFilteringCritiriasMap = getWebExtensionManager().getCustomerAutoFilteringCritiriaMap(
					customerAutoFilteringCritirias,
					FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD);
			Map<Long, Integer> fieldVisibilityRestrictionMap = new HashMap<Long, Integer>();
			Map<Long, Integer> fieldEditRestrictionMap = new HashMap<Long, Integer>();
			Map<Long, List<VisibilityDependencyCriteria>> visibilityDependencyCriteriaMap = getVisibilityDependencyCriteriaMap(
					formSpeId, FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD,
					visibilityDependencyCriterias);
			Map<Long, List<FieldValidationCritiria>> fieldValidationCritiriasMap = getWebSupportManager().getFieldValidationCritiriaMap(
					fieldValidationFilteringCritirias,
					FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD);

			List<FieldRestrictCritria> fieldRestrictCritrias = extraDao
					.getFieldSpecRestrictedCritiria(formSpeId,
							FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD,
							groupIds);
			
			getWebAdditionalManager().resolveFieldRestrictCritriaOfNotMine(fieldRestrictCritrias,formSpeId,groupIds,FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD);
			
			populateVisibilityRestictionsToMaps(fieldRestrictCritrias,
					fieldVisibilityRestrictionMap, fieldEditRestrictionMap);
			boolean inVisibleFormField = false;
			if (inVisibleFieldsDisplay) {
				FormSpec formSpec = extraDao.getFormSpec(formSpeId + "");
				Integer purpose = formSpec.getPurpose();
				if (purpose == 3) {
					WorkSpec workSpec = extraSupportDao.getWorkSpecUsingFormSpecUniqueId(formSpec.getUniqueId());
					inVisibleFormField = workSpec.isHiddenFieldsToDisplayInViewMode();
				} else {
					inVisibleFormField = formSpec.isHiddenFieldsToDisplayInViewMode();
				}
			}
			Map<Long, List<FormFilteringCritiria>> formFilteringCritiriasMap = 
					getWebAdditionalManager().getFormFilteringCritiriasMap(formSpeId,FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD);
			Map<Long,List<CustomEntityFilteringCritiria>> customEntityFilteringCritiriasMap =getWebFunctionalManager().getCustomEntityCritiriaMap(customEntityFilteringCritirias,
					FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD);
			for (FormFieldSpec formFieldSpec : formFieldSpecs) {

				Integer restriction = null;
				restriction = fieldVisibilityRestrictionMap.get(formFieldSpec
						.getFieldSpecId());
				if (restriction != null && !(inVisibleFormField)) {
					formFieldSpec.setVisible(restriction);
				}
				restriction = fieldEditRestrictionMap.get(formFieldSpec
						.getFieldSpecId());
				if (restriction != null) {
					formFieldSpec.setEditable(restriction);
				}

				if (formFieldSpec.getFieldTypeExtra() != null) {
					List<ListFilteringCritiria> listFilteringCritiriasforField = listFilteringCritiriasMap
							.get(formFieldSpec.getFieldSpecId());
					if (listFilteringCritiriasforField != null) {
						String json = null;
						try {
							json = Api.toJson(listFilteringCritiriasforField);
						} catch (Exception e) {
							StackTraceElement[] stackTrace = e.getStackTrace();
							getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in resolveFormFieldsWithRestrictions method",e.toString(),stackTrace,0);
							e.printStackTrace();
						}
						formFieldSpec.setListFilteringCritiria(json);
						formFieldSpec
								.setListFilteringCritirias(listFilteringCritiriasforField);
					}

				}
				
				List<CustomerAutoFilteringCritiria> customerAutoFilteringCritiriasforField = customerAutoFilteringCritiriasMap
						.get(formFieldSpec.getFieldSpecId());
				if (customerAutoFilteringCritiriasforField != null) {
					String json = null;
					try {
						json = Api.toJson(customerAutoFilteringCritiriasforField);
					} catch (Exception e) {
						
					}
					formFieldSpec.setCustomerAutoFilteringCritiria(json);
					formFieldSpec
							.setCustomerAutoFilteringCritirias(customerAutoFilteringCritiriasforField);
				}

				
				// formFilteringCritiria json starts
				List<FormFilteringCritiria> formFilteringCritiriasforField = formFilteringCritiriasMap
						.get(formFieldSpec.getFieldSpecId());
				if (formFilteringCritiriasforField != null) {
					String json = null;
					try {
						json = Api.toJson(formFilteringCritiriasforField);
					} catch (Exception e) {
						e.printStackTrace();
					}
					formFieldSpec.setFormFilteringCritiria(json);
					formFieldSpec
							.setFormFilteringCritirias(formFilteringCritiriasforField);
				}
				// formFilteringCritiria json ends
				// custom entity filter criteria
				List<CustomEntityFilteringCritiria> customEntityCritiriasForField = customEntityFilteringCritiriasMap.get(formFieldSpec.getFieldSpecId());
				if(customEntityCritiriasForField!=null) {
					String json = null;
					try {
						json = Api.toJson(customEntityCritiriasForField);
					}catch(Exception e) {
						StackTraceElement[] stackTrace = e.getStackTrace();
						getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in resolveFormFieldsWithRestrictions while creating new form",e.toString(),stackTrace,0);
						e.printStackTrace();
					}
					formFieldSpec.setCustomEntityFilteringCritiria(json);
					formFieldSpec.setCustomEntityFilteringCritirias(customEntityFilteringCritirias);
				}
				List<VisibilityDependencyCriteria> visibilityDependencyCriteriasForField = visibilityDependencyCriteriaMap
						.get(formFieldSpec.getFieldSpecId());
				if (visibilityDependencyCriteriasForField != null) {
					String VisibilityDependencyCriteriaForFieldJsonString = null;
					try {
						VisibilityDependencyCriteriaForFieldJsonString = Api
								.toJson(visibilityDependencyCriteriasForField);
					} catch (Exception e) {
						StackTraceElement[] stackTrace = e.getStackTrace();
						getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in resolveFormFieldsWithRestrictions while creating new form",e.toString(),stackTrace,0);
						e.printStackTrace();
					}
					formFieldSpec
							.setVisibilityDependencyCriteria(VisibilityDependencyCriteriaForFieldJsonString);

				}
				
				if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_NUMBER
						|| formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CURRENCY
						|| formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TIME
						|| formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE
						|| formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME)
				{
					List<FieldValidationCritiria> fieldValidationCritiriasForField = fieldValidationCritiriasMap
							.get(formFieldSpec.getFieldSpecId());
					if (fieldValidationCritiriasForField != null) {
						String fieldValidationCritiriasForFieldJsonString = null;
						try {
							fieldValidationCritiriasForFieldJsonString = Api
									.toJson(fieldValidationCritiriasForField);
						} catch (Exception e) {
							StackTraceElement[] stackTrace = e.getStackTrace();
							getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in resolveFormFieldsWithRestrictions while creating new form",e.toString(),stackTrace,0);
							e.printStackTrace();
						}
						formFieldSpec
								.setFieldValidationCritiria(fieldValidationCritiriasForFieldJsonString);
						
						formFieldSpec
						.setFieldValidationCritirias(fieldValidationCritiriasForField);
	
					}
				}
				
				
				
			}

		}
	}
	
	public Map<Long, List<CustomerAutoFilteringCritiria>> getCustomerAutoFilteringCritiriaMap(
			List<CustomerAutoFilteringCritiria> listFilteringCritiria, int type) {
		Map<Long, List<CustomerAutoFilteringCritiria>> listFilteringCritiriaMap = new HashMap<Long, List<CustomerAutoFilteringCritiria>>();
		if (listFilteringCritiria != null) {
			for (CustomerAutoFilteringCritiria listFilteringCritiria2 : listFilteringCritiria) {
				if (listFilteringCritiria2.getType() == type) {
					List<CustomerAutoFilteringCritiria> listFilteringCritirias = listFilteringCritiriaMap
							.get(listFilteringCritiria2.getFieldSpecId());
					if (listFilteringCritirias == null) {
						listFilteringCritirias = new ArrayList<CustomerAutoFilteringCritiria>();
						listFilteringCritiriaMap.put(
								listFilteringCritiria2.getFieldSpecId(),
								listFilteringCritirias);
					}
					listFilteringCritirias.add(listFilteringCritiria2);
				}
			}
		}
		return listFilteringCritiriaMap;
	}
	
	
	public FormFieldSpecValidValue getFormFieldSpecValidValue(String id) {
		FormFieldSpecValidValue formFieldSpecValidValue = null;

		try {
			formFieldSpecValidValue = extraDao.getFormFieldSpecValidValue(id);
			
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
			//StackTraceElement[] stackTrace = e.getStackTrace();
			//getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getFormFieldSpecValidValue method",e.toString(),stackTrace,0);
		}

		return formFieldSpecValidValue;
	}
	
	public void resolveFormSectionFieldsWithRestrictions(
			List<FormSectionSpec> formSectionSpecs, String formSpeId,
			String groupIds,
			List<ListFilteringCritiria> listFilteringCritirias,
			List<VisibilityDependencyCriteria> visibilityDependencyCriterias, List<FieldValidationCritiria> fieldValidationFilteringCritirias,
			List<CustomEntityFilteringCritiria> customEntityFilteringCritirias,boolean inVisibleFieldsDisplay) {
		if (formSectionSpecs != null) {
			Map<Long, List<ListFilteringCritiria>> listFilteringCritiriasMap = getListFilteringCritiriaMap(
					listFilteringCritirias,
					FieldSpecRestrictionGroup.FIELD_IS_SECTIONFIELD);
			Map<Long, Integer> fieldVisibilityRestrictionMap = new HashMap<Long, Integer>();
			Map<Long, Integer> fieldEditRestrictionMap = new HashMap<Long, Integer>();
			Map<Long, List<VisibilityDependencyCriteria>> visibilityDependencyCriteriaMap = getVisibilityDependencyCriteriaMap(
					formSpeId, FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD,
					visibilityDependencyCriterias);
			List<FieldRestrictCritria> fieldRestrictCritrias = extraDao
					.getFieldSpecRestrictedCritiria(formSpeId,
							FieldSpecRestrictionGroup.FIELD_IS_SECTIONFIELD,
							groupIds);
			getWebAdditionalManager().resolveFieldRestrictCritriaOfNotMine(fieldRestrictCritrias,formSpeId,groupIds,FieldSpecRestrictionGroup.FIELD_IS_SECTIONFIELD);
			
			Map<Long, List<FieldValidationCritiria>> fieldValidationCritiriasMap = getWebSupportManager().getFieldValidationCritiriaMap(
					fieldValidationFilteringCritirias,
					FieldSpecRestrictionGroup.FIELD_IS_SECTIONFIELD);
			
			populateVisibilityRestictionsToMaps(fieldRestrictCritrias,
					fieldVisibilityRestrictionMap, fieldEditRestrictionMap);
			boolean inVisibleFormField = false;
			if (inVisibleFieldsDisplay) {
				FormSpec formSpec = extraDao.getFormSpec(formSpeId + "");
				Integer purpose = formSpec.getPurpose();
				if (purpose == 3) {
					WorkSpec workSpec = extraSupportDao.getWorkSpecUsingFormSpecUniqueId(formSpec.getUniqueId());
					inVisibleFormField = workSpec.isHiddenFieldsToDisplayInViewMode();
				} else {
					inVisibleFormField = formSpec.isHiddenFieldsToDisplayInViewMode();
				}
			}
			
			Map<Long, List<FormFilteringCritiria>> formFilteringCritiriasMap = 
					getWebAdditionalManager().getFormFilteringCritiriasMap(formSpeId,FieldSpecRestrictionGroup.FIELD_IS_SECTIONFIELD);
			Map<Long,List<CustomEntityFilteringCritiria>> customEntityFilteringCritiriasMap = getWebFunctionalManager().getCustomEntityCritiriaMap(customEntityFilteringCritirias,
					FieldSpecRestrictionGroup.FIELD_IS_SECTIONFIELD);
			for (FormSectionSpec formSectionSpec : formSectionSpecs) {
				
				List<VisibilityDependencyCriteria> visibilityDependencyCriteriasForSection = visibilityDependencyCriteriaMap
						.get(formSectionSpec.getSectionSpecId());
				if (visibilityDependencyCriteriasForSection != null) {
					String VisibilityDependencyCriteriaForSectionJsonString = null;
					try {
						VisibilityDependencyCriteriaForSectionJsonString = Api
								.toJson(visibilityDependencyCriteriasForSection);
					} catch (Exception e) {
						e.printStackTrace();
					}
					formSectionSpec
							.setVisibilityDependencyCriteria(VisibilityDependencyCriteriaForSectionJsonString);
				}
				
				List<FormSectionFieldSpec> formSectionFieldSpecs = formSectionSpec
						.getFormSectionFieldSpecs();
				for (FormSectionFieldSpec formSectionFieldSpec : formSectionFieldSpecs) {

					Integer restriction = null;
					restriction = fieldVisibilityRestrictionMap
							.get(formSectionFieldSpec.getSectionFieldSpecId());
					if (restriction != null && !(inVisibleFormField)) {
						formSectionFieldSpec.setVisible(restriction);
					}
					restriction = fieldEditRestrictionMap
							.get(formSectionFieldSpec.getSectionFieldSpecId());
					if (restriction != null) {
						formSectionFieldSpec.setEditable(restriction);
					}

					if (formSectionFieldSpec.getFieldTypeExtra() != null) {
						List<ListFilteringCritiria> listFilteringCritiriasforField = listFilteringCritiriasMap
								.get(formSectionFieldSpec
										.getSectionFieldSpecId());
						if (listFilteringCritiriasforField != null) {
							String json = null;
							try {
								json = Api
										.toJson(listFilteringCritiriasforField);
							} catch (Exception e) {
								e.printStackTrace();
							}
							formSectionFieldSpec.setListFilteringCritiria(json);
							formSectionFieldSpec
									.setListFilteringCritirias(listFilteringCritiriasforField);
						}

					}
					
					// formFilteringCritiria json starts
					List<FormFilteringCritiria> formFilteringCritiriasforField = formFilteringCritiriasMap
							.get(formSectionFieldSpec.getSectionFieldSpecId());
					if (formFilteringCritiriasforField != null) {
						String json = null;
						try {
							json = Api.toJson(formFilteringCritiriasforField);
						} catch (Exception e) {
							e.printStackTrace();
						}
						formSectionFieldSpec.setFormFilteringCritiria(json);
						formSectionFieldSpec
								.setFormFilteringCritirias(formFilteringCritiriasforField);
					}
					// formFilteringCritiria json ends

					// custom entity filter criteria
					List<CustomEntityFilteringCritiria> customEntityCritiriasForField = customEntityFilteringCritiriasMap.get(formSectionFieldSpec.getSectionFieldSpecId());
					if(customEntityCritiriasForField!=null) {
						String json = null;
						try {
							json = Api.toJson(customEntityCritiriasForField);
						}catch(Exception e) {
							e.printStackTrace();
						}
						formSectionFieldSpec.setCustomEntityFilteringCritiria(json);
						formSectionFieldSpec.setCustomEntityFilteringCritirias(customEntityFilteringCritirias);
					}
					List<VisibilityDependencyCriteria> visibilityDependencyCriteriasForField = visibilityDependencyCriteriaMap
							.get(formSectionFieldSpec.getSectionFieldSpecId());
					if (visibilityDependencyCriteriasForField != null) {
						String VisibilityDependencyCriteriaForFieldJsonString = null;
						try {
							VisibilityDependencyCriteriaForFieldJsonString = Api
									.toJson(visibilityDependencyCriteriasForField);
						} catch (Exception e) {
							e.printStackTrace();
						}
						formSectionFieldSpec
								.setVisibilityDependencyCriteria(VisibilityDependencyCriteriaForFieldJsonString);
					}
					
					if(formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_NUMBER 
						|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CURRENCY
						|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TIME
						|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE
						|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME 
						|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TEXT
						|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TIMESPAN)
					{
						List<FieldValidationCritiria> fieldValidationCritiriasForField = fieldValidationCritiriasMap
								.get(formSectionFieldSpec.getSectionFieldSpecId());
						if (fieldValidationCritiriasForField != null) {
							String fieldValidationCritiriasForFieldJsonString = null;
							try {
								fieldValidationCritiriasForFieldJsonString = Api
										.toJson(fieldValidationCritiriasForField);
							} catch (Exception e) {
								e.printStackTrace();
							}
							formSectionFieldSpec
									.setFieldValidationCritiria(fieldValidationCritiriasForFieldJsonString);
							
							formSectionFieldSpec
								.setFieldValidationCritirias(fieldValidationCritiriasForField);
		
						}
					}
				}

			}
		}

	}
	
	
	public void populateVisibilityRestictionsToMaps(
			List<FieldRestrictCritria> fieldRestrictCritrias,
			Map<Long, Integer> fieldVisibilityRestrictionMap,
			Map<Long, Integer> fieldEditRestrictionMap) {
		if (fieldVisibilityRestrictionMap != null
				&& fieldEditRestrictionMap != null) {
			for (FieldRestrictCritria fieldRestrictCritria : fieldRestrictCritrias) {
				if (fieldRestrictCritria.getType() == FieldSpecRestrictionGroup.MAKE_VISIBILITY) {
					fieldRestrictCritria.setVisibility(1);
					fieldVisibilityRestrictionMap.put(
							fieldRestrictCritria.getFieldSpecId(),
							fieldRestrictCritria.getVisibility());
				}
				if (fieldRestrictCritria.getType() == FieldSpecRestrictionGroup.MAKE_EDIT) {
					fieldRestrictCritria.setVisibility(1);
					fieldEditRestrictionMap.put(
							fieldRestrictCritria.getFieldSpecId(),
							fieldRestrictCritria.getVisibility());
				}
				if (fieldRestrictCritria.getType() == FieldSpecRestrictionGroup.VISIBILITY_RESTRICTION) {
					fieldVisibilityRestrictionMap.put(
							fieldRestrictCritria.getFieldSpecId(),
							fieldRestrictCritria.getVisibility());
				}
				if (fieldRestrictCritria.getType() == FieldSpecRestrictionGroup.EDIT_RESTRICTION) {
					fieldEditRestrictionMap.put(
							fieldRestrictCritria.getFieldSpecId(),
							fieldRestrictCritria.getVisibility());
				}
			}
		}

	}
	
	
	public List<FormSectionFieldSpecValidValue> getFormSectionFieldSpecValidValue(
			List<FormSectionFieldSpec> sectionFieldSpecs) {
		return extraDao.getFormSectionFieldSpecValidValuesIn(sectionFieldSpecs);
	}

	
	public List<FormSectionFieldSpecValidValue> getFormSectionFieldSpecValidValues(
			List<FormSectionSpec> formSectionSpecs) {
		List<FormSectionFieldSpec> formSectionFieldSpecs = new ArrayList<FormSectionFieldSpec>();
		for (FormSectionSpec formSectionSpec : formSectionSpecs) {
			formSectionFieldSpecs.addAll(formSectionSpec
					.getFormSectionFieldSpecs());
		}

		List<FormSectionFieldSpecValidValue> formSectionFieldSpecValidValues = getFormSectionFieldSpecValidValue(formSectionFieldSpecs);

		return formSectionFieldSpecValidValues;
	}
	
	
	public List<Long> getCustomerIdsForEmpWithCustomerFilter(long empId,
			int formCustomerFilter, int companyId) {
		List<Long> customerIds = new ArrayList<Long>();
		if (formCustomerFilter == Constants.FORM_CUSTOMER_FILTER_TYPE_ALL_CUSTOMERS) {
			List<Long> allCustomers = extraDao.getAllCustomerIdsForCompany(companyId);
			customerIds.addAll(allCustomers);

		} else if (formCustomerFilter == Constants.FORM_CUSTOMER_FILTER_TYPE_CUSTOMERS_IN_MY_HIERARCHY) {
			customerIds = extraDao.getCustomerIdsForEmp(empId);
			String customersUnder = getAllCustomersUnderMe(empId);
			List<Long> customerMappedToEmployees = Api
					.csvToLongList(customersUnder);
			customerIds.addAll(customerMappedToEmployees);

		} else if (formCustomerFilter == Constants.FORM_CUSTOMER_FILTER_TYPE_CUSTOMERS_MAPPED_TO_ME) {
			customerIds = extraDao.getCustomerIdsForEmp(empId);
		}
		return customerIds;

		// List<Long> customerIds = extraDao.getCustomerIdsForEmp(empId);
		// // customer mapped to employees under
		// Employee employee = getEmployee(empId + "");
		//
		// if (employee.isManager()||employee.getEmpTypeId() ==
		// Employee.TYPE_BACK_OFFICE) {
		// if (isShowCustomersOfEmployeesToManager(employee.getCompanyId())) {
		//
		// String customersUnder = getAllCustomersUnderMe(empId);
		// List<Long> customerMappedToEmployees =
		// Api.csvToLongList(customersUnder);
		// customerIds.addAll(customerMappedToEmployees);
		// }
		//
		// }
		//
		//
		// if (customerIds != null && customerIds.size() == 0) {
		// List<Customer> allCustomers = getAllCustomers(employee
		// .getCompanyId());
		//
		// String customersUnder = Api.toCSVFromList(allCustomers,
		// "customerId");
		// List<Long> customerMappedToEmployees = Api
		// .csvToLongList(customersUnder);
		// customerIds.addAll(customerMappedToEmployees);
		//
		// }

		// return customerIds;
	}

	public List<Long> getCustomerIdsForEmp(long empId) {
		//	Long currentTime = Api.getCurrentTimeInUTCLong();
			List<Long> customerIds = extraDao.getCustomerIdsForEmp(empId);
		//	Log.info(this.getClass(), "getCustomerIdsForEmp End. Time taken to process: "+(Api.getCurrentTimeInUTCLong() - currentTime));
			// customer mapped to employees under
			Employee employee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(empId + "");

		//	currentTime = Api.getCurrentTimeInUTCLong();
			if (customerIds.size() == 0
					&& "102".equalsIgnoreCase(constants.getMapTo(empId))) {
				customerIds = getCustomerIdsForEmpWithCustomerFilter(empId,
						Constants.FORM_CUSTOMER_FILTER_TYPE_ALL_CUSTOMERS,
						employee.getCompanyId());
			} else {
				customerIds = getCustomerIdsForEmpWithCustomerFilter(
						empId,
						Constants.FORM_CUSTOMER_FILTER_TYPE_CUSTOMERS_IN_MY_HIERARCHY,
						employee.getCompanyId());
			}
		//	Log.info(this.getClass(), "getCustomerIdsForEmpWithCustomerFilter End. Time taken to process: "+(Api.getCurrentTimeInUTCLong() - currentTime));
			return customerIds;
		}
	
	public Customer getCustomerOrNull(String customerId, long empId) {
		Customer customer = null;
		try {
			customer = extraDao.getCustomer(customerId, empId);
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
			StackTraceElement[] stackTrace = e.getStackTrace();
			getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getCustomerOrNull method",e.toString(),stackTrace,0);
		}
		return customer;
	}
	
	public void resolveFormFieldsWithRestrictionsForSync(
			List<FormFieldSpec> formFieldSpecs, String formSpeId,
			String groupIds) {
		if (formFieldSpecs != null) {
			// Map<Long, List<ListFilteringCritiria>> listFilteringCritiriasMap
			// = getListFilteringCritiriaMap(listFilteringCritirias,
			// FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD);
			Map<Long, Integer> fieldVisibilityRestrictionMap = new HashMap<Long, Integer>();
			Map<Long, Integer> fieldEditRestrictionMap = new HashMap<Long, Integer>();
			// Map<Long,List<VisibilityDependencyCriteria>>visibilityDependencyCriteriaMap=getVisibilityDependencyCriteriaMap(formSpeId,FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD,visibilityDependencyCriterias);

			List<FieldRestrictCritria> fieldRestrictCritrias = extraDao
					.getFieldSpecRestrictedCritiria(formSpeId,
							FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD,
							groupIds);
			getWebAdditionalManager().resolveFieldRestrictCritriaOfNotMine(fieldRestrictCritrias,formSpeId,groupIds,FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD);
			
			populateVisibilityRestictionsToMaps(fieldRestrictCritrias,
					fieldVisibilityRestrictionMap, fieldEditRestrictionMap);
			for (FormFieldSpec formFieldSpec : formFieldSpecs) {

				Integer restriction = null;
				restriction = fieldVisibilityRestrictionMap.get(formFieldSpec
						.getFieldSpecId());
				if (restriction != null) {
					formFieldSpec.setVisible(restriction);
				}
				restriction = fieldEditRestrictionMap.get(formFieldSpec
						.getFieldSpecId());
				if (restriction != null) {
					formFieldSpec.setEditable(restriction);
				}

				// if(formFieldSpec.getFieldTypeExtra()!=null){
				// List<ListFilteringCritiria> listFilteringCritiriasforField=
				// listFilteringCritiriasMap.get(formFieldSpec.getFieldSpecId());
				// if(listFilteringCritiriasforField!=null){
				// String json=null;
				// try {
				// json = Api.toJson(listFilteringCritiriasforField);
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// formFieldSpec.setListFilteringCritiria(json);
				// formFieldSpec.setListFilteringCritirias(listFilteringCritiriasforField);
				// }
				//
				// }
				//
				// List<VisibilityDependencyCriteria>visibilityDependencyCriteriasForField=visibilityDependencyCriteriaMap.get(formFieldSpec.getFieldSpecId());
				// if(visibilityDependencyCriteriasForField!=null){
				// String VisibilityDependencyCriteriaForFieldJsonString=null;
				// try {
				// VisibilityDependencyCriteriaForFieldJsonString=Api.toJson(visibilityDependencyCriteriasForField);
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// formFieldSpec.setVisibilityDependencyCriteria(VisibilityDependencyCriteriaForFieldJsonString);
				//
				// }
			}

		}
	}
	
	
	public void resolveFormSectionFieldsWithRestrictionsForSync(
			List<FormSectionFieldSpec> formSectionFieldSpecs, String formSpeId,
			String groupIds) {
		if (formSectionFieldSpecs != null) {
			Map<Long, Integer> fieldVisibilityRestrictionMap = new HashMap<Long, Integer>();
			Map<Long, Integer> fieldEditRestrictionMap = new HashMap<Long, Integer>();
			List<FieldRestrictCritria> fieldRestrictCritrias = extraDao
					.getFieldSpecRestrictedCritiria(formSpeId,
							FieldSpecRestrictionGroup.FIELD_IS_SECTIONFIELD,
							groupIds);
			getWebAdditionalManager().resolveFieldRestrictCritriaOfNotMine(fieldRestrictCritrias,formSpeId,groupIds,FieldSpecRestrictionGroup.FIELD_IS_SECTIONFIELD);
			
			populateVisibilityRestictionsToMaps(fieldRestrictCritrias,
					fieldVisibilityRestrictionMap, fieldEditRestrictionMap);

			for (FormSectionFieldSpec formSectionFieldSpec : formSectionFieldSpecs) {

				Integer restriction = null;
				restriction = fieldVisibilityRestrictionMap
						.get(formSectionFieldSpec.getSectionFieldSpecId());
				if (restriction != null) {
					formSectionFieldSpec.setVisible(restriction);
				}
				restriction = fieldEditRestrictionMap.get(formSectionFieldSpec
						.getSectionFieldSpecId());
				if (restriction != null) {
					formSectionFieldSpec.setEditable(restriction);
				}
			}
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public long logFormAudit(long formId, long by, long companyId, int type, Boolean byClient,String ipAddress,
			boolean isMasterForm, String oppUserName) {
		String time = Api
				.getDateTimeInUTC(new Date(System.currentTimeMillis()));
		long auditParent = auditDao.auditForm(formId, by, time, ipAddress, isMasterForm,oppUserName);
		List<AuditFormFields> auditFormFields = new ArrayList<AuditFormFields>();
		auditFormFields = auditDao.getFormFieldsForAudit(formId, auditParent, by, time, isMasterForm);
		auditDao.auditFormFieldsByBatchUpdate(auditFormFields);
		List<AuditFormSectionFields> auditFormSectionFields = new ArrayList<AuditFormSectionFields>();
		auditFormSectionFields = auditDao.getFormSectionFieldsForAudit(formId, auditParent, by, time);
		auditDao.auditFormSectionFieldsByBatchUpdate(auditFormSectionFields);
		auditDao.auditRichTextFormFields(formId,by,time);
		auditDao.auditRichTextFormSectionFields(formId,by,time);
		if(!isMasterForm){
			sendJmsMessage(JmsMessage.TYPE_FORM, auditParent, companyId, by, type,
					formId, null, byClient);
			if(type == JmsMessage.CHANGE_TYPE_MODIFY) {
				Form form = extraDao.getForm(formId+"");
				CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus = new CustomerRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, auditParent, companyId, by, type,
						formId, byClient,form.getFormId());
				CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus = new CustomEntityRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, auditParent, companyId, by, type,
						formId, byClient,form.getFormId());
				WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus = new WorkRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, auditParent, companyId, by, type,
						formId, byClient,form.getFormId());
				EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus = new EmployeeRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, auditParent, companyId, by, type,
						formId, byClient,form.getFormId());
				getWebExtensionManager().getAllReqConfiurations(form.getFormSpecId(),customerRequisitionJmsMessageStatus,customEntityRequisitionJmsMessageStatus,workRequisitionJmsMessageStatus,employeeRequisitionJmsMessageStatus);
				
			}
		}
		if(isMasterForm) {
			sendJmsMessage(JmsMessage.TYPE_CUSTOM_ENTITY_SUBMISSION, formId, companyId, by, type,
					formId, null, byClient);
		}
		return auditParent;
	}
	
	public List<FormDisplay> getFormFieldsForDisplay(Form form, long empId,
			long companyId, boolean forCustomer, boolean isMasterForm,boolean supportCurrencyFormat,boolean inVisibleFieldsDisplay) {
		Employee employee = getWebExtensionManager().getEmployeeBasicDetailsAndTimeZoneByEmpId("" + empId);
		List<FormDisplay> formDisplays = new ArrayList<FormDisplay>();
		List<FormField> fields = new ArrayList<FormField>();
		if(isMasterForm) {
			fields =  getWebAdditionalSupportExtraManager().getMasterFormFields(form.getFormId());
		}else {
			fields = getFormFields(form.getFormId());
		}
		
		List<FormSectionField> sectionFields = new ArrayList<FormSectionField>();
		if(isMasterForm) {
			sectionFields = getWebExtensionManager().getMasterFormSectionFieldsForForm(form.getFormId());
		}else {
			sectionFields = getFormSectionFields(form.getFormId());
		}
		FormSpec oldFormSoec = getFormSpec(form.getFormSpecId()+"");
		FormSpec latestFormSpec = getLatestFormSpec(oldFormSoec.getUniqueId());
		List<FormFieldSpec> newfieldSpecs = getFormFieldSpecs(latestFormSpec.getFormSpecId());
		List<FormFieldSpec> fieldSpecs = getFormFieldSpecs(form.getFormSpecId());
		List<FormSectionSpec> sectionSpecs = getFormSectionSpecs(form
				.getFormSpecId());
		
		
		//comma Seperated Valuee
		if(supportCurrencyFormat == true) {
		getWebFunctionalManager().CommaSeperatedValueFormFieldsForUI(fields,fieldSpecs);
		getWebFunctionalManager().CommaSeperatorValueFormSectionFieldsForUI(sectionFields,sectionSpecs);
		//getWebExtensionManager().resolveFormFieldStyleProperties(fieldSpecs);

		}

		List<FormFieldGroupSpec> formFieldGroupSpecs = getWebAdditionalManager().getFormFieldGroupSpecForIn(form.getFormSpecId()+"");
		Iterator<FormFieldSpec> formFieldSpeciterator1 = fieldSpecs.iterator();
		while (formFieldSpeciterator1.hasNext())
		{
			FormFieldSpec formFieldSpec = (FormFieldSpec) formFieldSpeciterator1
					.next();
			for (FormFieldSpec newfieldSpec : newfieldSpecs) {
				if(newfieldSpec.getUniqueId().equalsIgnoreCase(formFieldSpec.getUniqueId())){
					if(newfieldSpec.getIsVisible() == false){
						formFieldSpeciterator1.remove();
					}
				}
			}
		 }
		
		
		List<FormFieldSpecValidValue> fieldSpecValidValues = getFormFieldSpecValidValues(fieldSpecs);
		List<FormSectionFieldSpecValidValue> sectionFieldSpecValidValues = getFormSectionFieldSpecValidValues(sectionSpecs);
		Map<String, Integer> visibilityCondtionSectionFieldSpecMap = new HashMap<String, Integer>();
		 Map<String, Boolean> condtionMandatorySectionFieldSpecMap = new HashMap<String, Boolean>();
		String empgroupIds = getEmployeeGroupIdsForEmployee("" + empId);
		// if(!Api.isEmptyString(empgroupIds)){
		resolveFormFieldsWithRestrictions(fieldSpecs,
				form.getFormSpecId() + "", empgroupIds, null, null, null,null,inVisibleFieldsDisplay,null);
		resolveFormSectionFieldsWithRestrictions(sectionSpecs,
				form.getFormSpecId() + "", empgroupIds, null, null, null,null,inVisibleFieldsDisplay);
		// resolveVisibiltyDependecyForForForm(fieldSpecs,sectionFields,fields,sectionFields);
		resolveVisibiltyDependecyForForForm(form.getFormSpecId(), fieldSpecs,
				sectionSpecs, fields, sectionFields,
				visibilityCondtionSectionFieldSpecMap, false, condtionMandatorySectionFieldSpecMap, form.getCustomer());
		getWebAdditionalManager().resolveSectionVisibilityByEmpGropsForForm(empgroupIds, sectionSpecs,visibilityCondtionSectionFieldSpecMap,
				form.getFormSpecId(),sectionFields,inVisibleFieldsDisplay);
		
		getWebAdditionalManager().resolveBackgroundColorDependencyCriteriaForForm(form.getFormSpecId(), fieldSpecs, 
				sectionSpecs, fields, sectionFields,form.getFormId());
		
		Long appId = employee.getAppId();
		if(appId != null && appId > 0) {
				App app = getWebExtraManager().getAppForAppId(appId, employee.getCompanyId());
				if(app != null) {
					appId = app.getAppId();
				} else {
					appId = 0l;
				}
		} else {
			appId = 0l;
		}
		//For FormFieldLoaclization
		List<CustomField> customFormFieldLabels = getWebAdditionalManager().getLocalizeFormFieldLabels(appId, latestFormSpec.getUniqueId());
		Map<String, String> customFieldMap = new HashMap<>();
		if(customFormFieldLabels != null && !customFormFieldLabels.isEmpty()) {
			customFieldMap =  customFormFieldLabels.stream().collect(Collectors.toMap(CustomField::getFieldSpecUniqueId, CustomField::getCustomLabelName));
		}
	
		Iterator<FormFieldSpec> formFieldSpeciterator = fieldSpecs.iterator();
		while (formFieldSpeciterator.hasNext()) {
			FormFieldSpec formFieldSpec = (FormFieldSpec) formFieldSpeciterator
					.next();
			if (formFieldSpec.getVisbleOnVisibilityCondition() == 0
					|| formFieldSpec.getVisible() == 0
					|| formFieldSpec.getIsVisible() == false) {
				formFieldSpeciterator.remove();
			}
		}

		for (FormSectionSpec formSectionSpec : sectionSpecs) {
			List<FormSectionFieldSpec> formSectionFieldSpec = formSectionSpec
					.getFormSectionFieldSpecs();
			Iterator<FormSectionFieldSpec> formSectionFieldSpecIterator = formSectionFieldSpec
					.iterator();
			while (formSectionFieldSpecIterator.hasNext()) {
				FormSectionFieldSpec formSectionFieldSpec2 = (FormSectionFieldSpec) formSectionFieldSpecIterator
						.next();
				if (formSectionFieldSpec2.getVisible() == 0
						|| formSectionFieldSpec2
								.getVisibleOnVisibilityCondition() == 0) {
					formSectionFieldSpecIterator.remove();
				}

			}
		}

        List<FormDisplay> groupedFormDisplays = new ArrayList<FormDisplay>();
		for (FormFieldSpec formFieldSpec : fieldSpecs) {
			
			
			/*if(formFieldSpec.getIsGroupFieldSpec() == 1){
				groupFormFieldSpecs.add(formFieldSpec);
				continue;
			}*/
			
			if(formFieldSpec.getFieldLabelFontId() != null) {
				CompanyFont companyFont = getWebAdditionalSupportManager().getCompanyFont(formFieldSpec.getFieldLabelFontId());
				if(companyFont != null) {
					if(companyFont.getFontColor() != null) {
						formFieldSpec.setFontColor(companyFont.getFontColor());
					}
					if(companyFont.getFontSize() != null) {
						formFieldSpec.setFontSize(companyFont.getFontSize());
					}
					formFieldSpec.setBold(companyFont.isBold());
					formFieldSpec.setItalic(companyFont.isItalic());
					formFieldSpec.setUnderLine(companyFont.isUnderLine());
				}
			}
			
			FormFieldDisplay formFieldDisplay = new FormFieldDisplay();
			formFieldDisplay.setFieldSpecId(formFieldSpec.getFieldSpecId());
			formFieldDisplay.setFieldType(formFieldSpec.getFieldType());
			formFieldDisplay.setVisible(formFieldSpec.getVisible());
			formFieldDisplay.setSkeletonFormFieldSpecId(formFieldSpec.getSkeletonFormFieldSpecId());
			formFieldDisplay.setUniqueId(formFieldSpec.getUniqueId());
			formFieldDisplay.setStaticField(formFieldSpec.isStaticField());
			if (formFieldSpec.getFieldType() == 18) {
				formFieldDisplay.setFieldLabel(formFieldSpec.getFieldLabel()
						+ "(Lat,Long)");
			}
			else {
				formFieldDisplay.setFieldLabel(formFieldSpec.getFieldLabel());
			}
			String localizedFieldLabel = customFieldMap.get(formFieldSpec.getUniqueId());
			if(!Api.isEmptyString(localizedFieldLabel)) {
				formFieldDisplay.setFieldLabel(localizedFieldLabel);
			}
			formFieldDisplay.setDisplayOrder(formFieldSpec.getDisplayOrder());
			formFieldDisplay.setBackgroundColor(formFieldSpec.getBackgroundColor());
			for (FormField formField : fields) {
				if (formField.getFieldSpecId() == formFieldSpec
						.getFieldSpecId()) {
					formFieldDisplay.setFieldValue(formField.getFieldValue());
					// Added code to capture the the thumbnail image of pdf document file for normal formfields.
					if (formField.getFieldType() == 33) {
						List<Media> pdfMedias = new ArrayList<Media>();
						pdfMedias = getWebExtensionManager().getMediaFiles(formField.getFieldValue() + "");
						Media pdfMedia = null;
						if (pdfMedias != null && pdfMedias.size() > 0) {
							pdfMedia = pdfMedias.get(0);
							if (pdfMedia.getFileName().substring(pdfMedia.getFileName().lastIndexOf("."))
									.equalsIgnoreCase(".pdf")) {
								PdfThumbnailMedias pdfThumbnailMedias = null;
								pdfThumbnailMedias = mediaDao
										.getPdfThumbNailMediaFileBasedOnParentId(formField.getFieldValue());
								if (pdfThumbnailMedias != null) {
									formFieldDisplay.setPdfThumbNailMediaId(pdfThumbnailMedias.getMediaId() + "");
									List<GenericMultiSelect> genericMultiSelects = new ArrayList<GenericMultiSelect>();
									String fieldValuePdfDisplay = getFieldDisplayValue(empId, companyId,
											formFieldSpec.getFieldType(), formFieldSpec.getFieldTypeExtra(),
											pdfThumbnailMedias.getMediaId() + "", formFieldSpec.getFieldSpecId(), null,
											fieldSpecValidValues, null, null, null, genericMultiSelects);
									formFieldDisplay.setFieldValuePdfDisplay(fieldValuePdfDisplay);
								} else {
									MediaResponse mediaResponse = null;
									int config = 0;
									try {
										mediaResponse = mediaManager.saveMediaForPdfThumbnail(empId + "", null,
												pdfMedia, config);
									} catch (Exception e) {
										formFieldDisplay.setDocumentMediaIcon("PDF.svg");
										Log.ignore(this.getClass(), e);
									}
									if (mediaResponse != null) {
										formFieldDisplay.setPdfThumbNailMediaId(mediaResponse.getMediaId() + "");
										List<GenericMultiSelect> genericMultiSelects = new ArrayList<GenericMultiSelect>();
										String fieldValueDisplayNew = getFieldDisplayValue(empId, companyId,
												formFieldSpec.getFieldType(), formFieldSpec.getFieldTypeExtra(),
												mediaResponse.getMediaId() + "", formFieldSpec.getFieldSpecId(), null,
												fieldSpecValidValues, null, null, null, genericMultiSelects);
										formFieldDisplay.setFieldValuePdfDisplay(fieldValueDisplayNew);
										if (fieldValueDisplayNew == null) {
											pdfMedia.setDocumentMediaIcon("PDF.svg");
										}
									}
								}
							} else {
								getWebExtensionManager().resolveDocumentMediaIconsForFormFieldDisplay(
										pdfMedia.getMimeType(), pdfMedia, formFieldDisplay);
							}
						}
					}
					
				
					if(formFieldSpec.getIsGroupFieldSpec() == 1){
						formFieldDisplay.setGroupExpression(formField.getGroupExpression());
					}
					if(formField.getBackgroundColor() != null){
						formFieldDisplay.setBackgroundColor(formField.getBackgroundColor());
					}
					break;
					
				}
			}			
			List<GenericMultiSelect> genericMultiSelects = new ArrayList<GenericMultiSelect>();
			String fieldValueDispaly = getFieldDisplayValue(empId, companyId,
					formFieldSpec.getFieldType(),
					formFieldSpec.getFieldTypeExtra(),
					formFieldDisplay.getFieldValue(),
					formFieldSpec.getFieldSpecId(), null, fieldSpecValidValues,
					null, null, null, genericMultiSelects);
			if (constants.getCaptureLocationInFormMediaUpdate(companyId)) {
				if ((formFieldSpec.getFieldType() == 12)
						|| (formFieldSpec.getFieldType() == 13) || (formFieldSpec.getFieldType() == 22) || (formFieldSpec.getFieldType() == 27)
						|| formFieldSpec.getFieldType() == 28 || formFieldSpec.getFieldType() == 33 ) {
					Location location = extraDao
							.getMediaLocation(formFieldDisplay.getFieldValue());
					if (location != null) {
						if (location.getLatitude() != null) {
							formFieldDisplay.setLocationLatLong(location
									.getLatitude()
									+ ","
									+ location.getLongitude());
						}
						if (location.getLocation() != null) {
							formFieldDisplay.setLocationAddress(location
									.getLocation());
						}
					}

				}
			}
			
			formFieldDisplay.setFieldValueDispaly(fieldValueDispaly);
			if (formFieldDisplay.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {
				Api.convertDateTimesToGivenType(
						formFieldDisplay,
						"" + employee.getTimezoneOffset(),
						DateConversionType.UTC_DATETIME_TO_DATETIME_DISPLAY_FORMAT,
						"fieldValueDispaly");
			}
			formFieldDisplay.setGenericMultiSelects(genericMultiSelects);

			FormDisplay formDisplay = new FormDisplay();
			formDisplay.setId(formFieldSpec.getFieldSpecId());
			formDisplay.setType(1);
			formDisplay.setDisplayOrder(formFieldSpec.getDisplayOrder());
			formDisplay.setPageId(formFieldSpec.getPageId());
			formDisplay.setLabel(formFieldSpec.getFieldLabel());
			if(formFieldSpec.getFontColor() != null) {
			String fontColor = "#"+formFieldSpec.getFontColor();
			formDisplay.setFontColor(fontColor);
			}
			String fontSize = "";
			if(formFieldSpec.getFontSize() != null) {
				if(formFieldSpec.getFontSize().equalsIgnoreCase("m")) {
					fontSize = "";
				}else if(formFieldSpec.getFontSize().equalsIgnoreCase("l")){
					fontSize = "20px";
				}else if(formFieldSpec.getFontSize().equalsIgnoreCase("s")) {
					fontSize = "10px";
				}
				formDisplay.setFontSize(fontSize);
			}
			String bold = "";
			String italic = "";
			String underLine = "";
			
			if(formFieldSpec.isBold() == true) {
				bold = "bold";
			}else {
				bold = "";
			}
			if(formFieldSpec.isItalic() == true) {
				italic = "italic";
			}else {
				italic = "";
			}
			if(formFieldSpec.isUnderLine() == true) {
				underLine = "underline";
			}else {
				underLine = "";
			}
			
			if (formFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_MULTI_DOCUMENT) {
				List<Media> multiDocMedias = getWebExtensionManager().getMediaFiles(formFieldDisplay.getFieldValue());
				if (multiDocMedias != null && !multiDocMedias.isEmpty()) {
					for (Media pdfMedia : multiDocMedias) {
						if (pdfMedia.getFileName().substring(pdfMedia.getFileName().lastIndexOf("."))
								.equalsIgnoreCase(".pdf")) {
							PdfThumbnailMedias pdfThumbnailMedias = null;
							pdfThumbnailMedias = mediaDao
									.getPdfThumbNailMediaFileBasedOnParentId(pdfMedia.getId() + "");
							if (pdfThumbnailMedias != null) {
								pdfMedia.setPdfThumbNailMediaId(pdfThumbnailMedias.getMediaId() + "");
								List<GenericMultiSelect> genericMultiSelectsForMultiDoc = new ArrayList<GenericMultiSelect>();
								String fieldValuePdfDisplay = getFieldDisplayValue(empId, companyId,
										formFieldSpec.getFieldType(), formFieldSpec.getFieldTypeExtra(),
										pdfThumbnailMedias.getMediaId() + "", formFieldSpec.getFieldSpecId(), null,
										fieldSpecValidValues, null, null, null, genericMultiSelectsForMultiDoc);
								pdfMedia.setFieldValuePdfDisplay(fieldValuePdfDisplay);
							} else {
								MediaResponse mediaResponse = null;
								int config = 0;
								try {
									mediaResponse = mediaManager.saveMediaForPdfThumbnail(empId + "", null, pdfMedia,
											config);
								} catch (Exception e) {
									pdfMedia.setDocumentMediaIcon("PDF.svg");
									Log.ignore(this.getClass(), e);
								}
								if (mediaResponse != null) {
									pdfMedia.setPdfThumbNailMediaId(mediaResponse.getMediaId() + "");
									List<GenericMultiSelect> genericMultiSelectsForMultiDocNew = new ArrayList<GenericMultiSelect>();
									String fieldValueDisplayNew = getFieldDisplayValue(empId, companyId,
											formFieldSpec.getFieldType(), formFieldSpec.getFieldTypeExtra(),
											mediaResponse.getMediaId() + "", formFieldSpec.getFieldSpecId(), null,
											fieldSpecValidValues, null, null, null, genericMultiSelectsForMultiDocNew);
									pdfMedia.setFieldValuePdfDisplay(fieldValueDisplayNew);
									if (fieldValueDisplayNew == null) {
										pdfMedia.setDocumentMediaIcon("PDF.svg");
									}
								}
							}
						} else {
							getWebExtensionManager().resolveMultiDocumentMediaIcons(pdfMedia.getMimeType(), pdfMedia);
						}
					}
				}
				formFieldDisplay.setMultiDocMedias(multiDocMedias);
			}
			
			if(formFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_MULTI_IMAGE)
			{
				List<Media> multiDocMedias = getWebExtensionManager().getMediaFiles(formFieldDisplay.getFieldValue());
				formFieldDisplay.setMultiDocMedias(multiDocMedias);
			}
			formDisplay.setBold(bold);
			formDisplay.setItalic(italic);
			formDisplay.setUnderLine(underLine);
			if(!Api.isEmptyString(localizedFieldLabel)) {
				formDisplay.setLabel(localizedFieldLabel);
			}
			formDisplay.setFieldDisplay(formFieldDisplay);
			formDisplay.setGroupExpression(formFieldSpec.getGroupExpression());
			if(formFieldSpec.getIsGroupFieldSpec() == 1 && latestFormSpec.getPurpose() != FormSpec.PURPOUSE_CUSTOMER){
				groupedFormDisplays.add(formDisplay);
				continue;
			}
			
			formDisplays.add(formDisplay);
		}

		//Map<String,List<FormFieldSpec>> grpExpAndGroupFormFieldSpecs = (Map)Api.getGroupedMapFromList(groupFormFieldSpecs, "", null);
		Map<String,List<FormDisplay>> grpExpAndFormDisplayMap = (Map)Api.getGroupedMapFromList(groupedFormDisplays, "groupExpression", null);
		List<FormDisplay> groupWiseFormDisplayList = null;
		for (FormFieldGroupSpec formFieldGroupSpec : formFieldGroupSpecs) {
			
			groupWiseFormDisplayList = grpExpAndFormDisplayMap.get(formFieldGroupSpec.getExpression());
			if(groupWiseFormDisplayList == null || groupWiseFormDisplayList.isEmpty()){
				continue;
			}
			Map<String, List<FormDisplay>> groupDisplay = new HashMap<String, List<FormDisplay>>();
			groupDisplay.put(formFieldGroupSpec.getExpression(), groupWiseFormDisplayList);
			FormDisplay formDisplay = new FormDisplay();
			formDisplay.setId(formFieldGroupSpec.getFormFieldGroupSpecId());
			formDisplay.setType(3);
			formDisplay.setDisplayOrder(groupWiseFormDisplayList.get(0).getDisplayOrder());
			formDisplay.setPageId(formFieldGroupSpec.getPageId());
			formDisplay.setLabel(formFieldGroupSpec.getGroupTitle());
			formDisplay.setGroupDisplay(groupDisplay);
			formDisplay.setGroupExpression(formFieldGroupSpec.getExpression());
			formDisplays.add(formDisplay);
		}
		
		Map<Long,List<FormSectionField>> sectionSpecIdAndSectionFieldsMap = new HashMap<Long,List<FormSectionField>>();
		if(sectionFields!=null && sectionFields.size()>0){
			sectionSpecIdAndSectionFieldsMap = (Map)Api.getResolvedMapFromList(sectionFields,"sectionSpecId");
		}
		
		for (FormSectionSpec formSectionSpec : sectionSpecs) {
			Map<Integer, List<FormSectionFieldDisplay>> sectionDisplay = new HashMap<Integer, List<FormSectionFieldDisplay>>();
			
			List<FormSectionField> formSectionFields = new ArrayList<FormSectionField>();
			if(sectionSpecIdAndSectionFieldsMap!=null && !sectionSpecIdAndSectionFieldsMap.isEmpty()){
				if(sectionSpecIdAndSectionFieldsMap.get(formSectionSpec.getSectionSpecId()) !=null){
					formSectionFields = sectionSpecIdAndSectionFieldsMap.get(formSectionSpec.getSectionSpecId());
				}
			}
			boolean isContainsSectionFields = false;
			for (FormSectionField sectionField : formSectionFields) {
				isContainsSectionFields = true;
				for (FormSectionFieldSpec sectionFieldSpec : formSectionSpec
						.getFormSectionFieldSpecs()) {
					
					String localizedFieldLabel = customFieldMap.get(sectionFieldSpec.getUniqueId());
					
					if (sectionField.getSectionFieldSpecId() == sectionFieldSpec
							.getSectionFieldSpecId()) {
						String key = sectionField.getSectionFieldSpecId() + "_"
								+ sectionField.getInstanceId();
						FormSectionFieldDisplay formSectionFieldDisplay = new FormSectionFieldDisplay();
						formSectionFieldDisplay
								.setSectionFieldSpecId(sectionFieldSpec
										.getSectionFieldSpecId());
						formSectionFieldDisplay.setFieldType(sectionFieldSpec
								.getFieldType());
						formSectionFieldDisplay.setFieldLabel(sectionFieldSpec
								.getFieldLabel());
						if(!Api.isEmptyString(localizedFieldLabel)) {
							formSectionFieldDisplay.setFieldLabel(localizedFieldLabel);
						}
						formSectionFieldDisplay
								.setDisplayOrder(sectionFieldSpec
										.getDisplayOrder());
						formSectionFieldDisplay.setVisible(sectionFieldSpec
								.getVisible());

						formSectionFieldDisplay.setFieldValue(sectionField
								.getFieldValue());

						if (visibilityCondtionSectionFieldSpecMap
								.containsKey(key)) {
							formSectionFieldDisplay
									.setVisibleOnVisibilityDependencyCritiria(0);
						}
						if(sectionField.getInstanceId() == 1) {
							
							if (visibilityCondtionSectionFieldSpecMap
									.containsKey("S"+sectionFieldSpec.getSectionSpecId())) {
								formSectionFieldDisplay.setSectionFieldVisibleOnVisibilityDependencyCritiria(0);
							}
						}
						formSectionFieldDisplay.setInstanceId(sectionField
								.getInstanceId());
						
						if(sectionField.getBackgroundColor() != null){
							formSectionFieldDisplay.setBackgroundColor(sectionField.getBackgroundColor());
						}/*else {
							formSectionFieldDisplay.setBackgroundColor(formFieldSpec.getBackgroundColor());
						}*/
						
						if(sectionFieldSpec.getFontColor() != null) {
							String fontColor = "#"+sectionFieldSpec.getFontColor();
							formSectionFieldDisplay.setFontColor(fontColor);
							}
							String fontSize = "";
							if(sectionFieldSpec.getFontSize() != null) {
								if(sectionFieldSpec.getFontSize().equalsIgnoreCase("m")) {
									fontSize = "";
								}else if(sectionFieldSpec.getFontSize().equalsIgnoreCase("l")){
									fontSize = "20px";
								}else if(sectionFieldSpec.getFontSize().equalsIgnoreCase("s")) {
									fontSize = "10px";
								}
								formSectionFieldDisplay.setFontSize(fontSize);
							}
							String bold = "";
							String italic = "";
							String underLine = "";
							
							if(sectionFieldSpec.isBold() == true) {
								bold = "bold";
							}else {
								bold = "";
							}
							if(sectionFieldSpec.isItalic() == true) {
								italic = "italic";
							}else {
								italic = "";
							}
							if(sectionFieldSpec.isUnderLine() == true) {
								underLine = "underline";
							}else {
								underLine = "";
							}
							formSectionFieldDisplay.setBold(bold);
							formSectionFieldDisplay.setItalic(italic);
							formSectionFieldDisplay.setUnderLine(underLine);
			
						List<GenericMultiSelect> genericMultiSelects = new ArrayList<GenericMultiSelect>();
						String fieldValueDispaly = getFieldDisplayValue(empId,
								companyId, sectionFieldSpec.getFieldType(),
								sectionFieldSpec.getFieldTypeExtra(),
								formSectionFieldDisplay.getFieldValue(), null,
								sectionFieldSpec.getSectionFieldSpecId(), null,
								sectionFieldSpecValidValues, null, null,
								genericMultiSelects);
						if (constants
								.getCaptureLocationInFormMediaUpdate(companyId)) {
							if (sectionFieldSpec.getFieldType() == 12
									|| sectionFieldSpec.getFieldType() == 13 || sectionFieldSpec.getFieldType() == 22 
									|| sectionFieldSpec.getFieldType() == 27 || sectionFieldSpec.getFieldType() == 33 ) {
								Location location = extraDao
										.getMediaLocation(formSectionFieldDisplay
												.getFieldValue());
								if (location != null) {
									if (location.getLatitude() != null) {
										formSectionFieldDisplay
												.setLocationLatLong(location
														.getLatitude()
														+ ","
														+ location
																.getLongitude());
									}
									if (location.getLocation() != null) {
										formSectionFieldDisplay
												.setLocationAddress(location
														.getLocation());
									}
								}
							}
						}
						formSectionFieldDisplay
								.setFieldValueDispaly(fieldValueDispaly);
						
			// Added code to capture the the thumbnail image of pdf document file for section formfields.
						if (sectionField.getFieldType() == 33) {
							List<Media> medias = getWebExtensionManager()
									.getMediaFiles(sectionField.getFieldValue() + "");
							Media mediasNew = null;
							if (medias != null && medias.size() > 0) {
								mediasNew = medias.get(0);
								if (mediasNew.getFileName().substring(mediasNew.getFileName().lastIndexOf("."))
										.equalsIgnoreCase(".pdf")) {
									PdfThumbnailMedias pdfThumbnailMedias = null;
									pdfThumbnailMedias = mediaDao
											.getPdfThumbNailMediaFileBasedOnParentId(sectionField.getFieldValue());
									if (pdfThumbnailMedias != null) {
										formSectionFieldDisplay
												.setPdfThumbNailMediaId(pdfThumbnailMedias.getMediaId() + "");
										List<GenericMultiSelect> genericMultiSelectsNew = new ArrayList<GenericMultiSelect>();
										String fieldValueDispalyNew = getFieldDisplayValue(empId, companyId,
												sectionFieldSpec.getFieldType(), sectionFieldSpec.getFieldTypeExtra(),
												pdfThumbnailMedias.getMediaId() + "",
												sectionFieldSpec.getSectionFieldSpecId(), null, fieldSpecValidValues,
												null, null, null, genericMultiSelectsNew);
										formSectionFieldDisplay.setFieldValuePdfDisplay(fieldValueDispalyNew);
									} else {
										MediaResponse mediaResponse = null;
										int config = 0;
										try {
											mediaResponse = mediaManager.saveMediaForPdfThumbnail(empId + "", null,
													mediasNew, config);
										} catch (Exception e) {
											formSectionFieldDisplay.setDocumentMediaIcon("PDF.svg");
											Log.ignore(this.getClass(), e);
										}
										if (mediaResponse != null) {
											formSectionFieldDisplay
													.setPdfThumbNailMediaId(mediaResponse.getMediaId() + "");
											List<GenericMultiSelect> genericMultiSelectsNew = new ArrayList<GenericMultiSelect>();
											String fieldValueDispalyNew = getFieldDisplayValue(empId, companyId,
													sectionFieldSpec.getFieldType(),
													sectionFieldSpec.getFieldTypeExtra(),
													mediaResponse.getMediaId() + "",
													sectionFieldSpec.getSectionFieldSpecId(), null,
													fieldSpecValidValues, null, null, null, genericMultiSelectsNew);
											formSectionFieldDisplay.setFieldValuePdfDisplay(fieldValueDispalyNew);
											if (fieldValueDispalyNew == null) {
												formSectionFieldDisplay.setDocumentMediaIcon("PDF.svg");
											}
										}
									}
								} else {
									getWebExtensionManager().resolveDocumentMediaIconsForFormSectionFieldDisplay(
											mediasNew, mediasNew.getMimeType(), formSectionFieldDisplay);
								}
							}
						}

						if (formSectionFieldDisplay.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {
							Api.convertDateTimesToGivenType(
									formSectionFieldDisplay,
									"" + employee.getTimezoneOffset(),
									DateConversionType.UTC_DATETIME_TO_DATETIME_DISPLAY_FORMAT,
									"fieldValueDispaly");
						}

						if (formSectionFieldDisplay.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_MULTI_DOCUMENT) {
							List<Media> multiDocMedias = getWebExtensionManager().getMediaFiles(formSectionFieldDisplay.getFieldValue());
							if (multiDocMedias != null && !multiDocMedias.isEmpty()) {
								for(Media pdfMedia : multiDocMedias)
								{
								if(pdfMedia.getFileName().substring(pdfMedia.getFileName().lastIndexOf(".")).equalsIgnoreCase(".pdf"))
								{
								PdfThumbnailMedias pdfThumbnailMedias = null;
								pdfThumbnailMedias = mediaDao
										.getPdfThumbNailMediaFileBasedOnParentId(pdfMedia.getId()+"");
								if (pdfThumbnailMedias != null) {
									pdfMedia.setPdfThumbNailMediaId(pdfThumbnailMedias.getMediaId() + "");
									List<GenericMultiSelect> genericMultiSelectsForMultiDoc = new ArrayList<GenericMultiSelect>();
									
									String fieldValuePdfDisplay = getFieldDisplayValue(empId, companyId,
											sectionFieldSpec.getFieldType(), sectionFieldSpec.getFieldTypeExtra(),
											pdfThumbnailMedias.getMediaId() + "",
											sectionFieldSpec.getSectionFieldSpecId(), null, fieldSpecValidValues,
											null, null, null, genericMultiSelectsForMultiDoc);
									pdfMedia.setFieldValuePdfDisplay(fieldValuePdfDisplay);
								} else {
									MediaResponse mediaResponse = null;
									int config = 0;
									try {
											mediaResponse = mediaManager.saveMediaForPdfThumbnail(empId + "", null, pdfMedia,
													config);
									} catch (Exception e) {
										pdfMedia.setDocumentMediaIcon("PDF.svg");
										Log.ignore(this.getClass(), e);
									}
									if (mediaResponse != null) {
										pdfMedia.setPdfThumbNailMediaId(mediaResponse.getMediaId() + "");
										List<GenericMultiSelect> genericMultiSelectsForMultiDocNew = new ArrayList<GenericMultiSelect>();
										String fieldValueDispalyNew = getFieldDisplayValue(empId, companyId,
												sectionFieldSpec.getFieldType(),
												sectionFieldSpec.getFieldTypeExtra(),
												mediaResponse.getMediaId() + "",
												sectionFieldSpec.getSectionFieldSpecId(), null,
												fieldSpecValidValues, null, null, null, genericMultiSelectsForMultiDocNew);
										pdfMedia.setFieldValuePdfDisplay(fieldValueDispalyNew);
										if(fieldValueDispalyNew == null)
										{
											pdfMedia.setDocumentMediaIcon("PDF.svg");
										}
									}
								}
							}
							else 
							{
								getWebExtensionManager().resolveMultiDocumentMediaIcons(pdfMedia.getMimeType(),pdfMedia);
							}
							}
							}
							formSectionFieldDisplay.setMultiDocMedias(multiDocMedias);
						}
						if (formSectionFieldDisplay.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_MULTI_IMAGE) {
							List<Media> multiDocMedias = getWebExtensionManager().getMediaFiles(formSectionFieldDisplay.getFieldValue());
							formSectionFieldDisplay.setMultiDocMedias(multiDocMedias);
						}
						formSectionFieldDisplay
								.setGenericMultiSelects(genericMultiSelects);

						List<FormSectionFieldDisplay> sectionInstance = sectionDisplay
								.get(formSectionFieldDisplay.getInstanceId());
						if (sectionInstance == null) {
							sectionInstance = new ArrayList<FormSectionFieldDisplay>();
							sectionDisplay.put(
									formSectionFieldDisplay.getInstanceId(),
									sectionInstance);
						}
						sectionInstance.add(formSectionFieldDisplay);

						break;
					}
				}
			}
			
			Iterator<Integer> instanceIterator = sectionDisplay.keySet()
					.iterator();
			while (instanceIterator.hasNext()) {
				isContainsSectionFields = true;
				Integer instanceId = (Integer) instanceIterator.next();
				List<FormSectionFieldDisplay> fieldsTemp = sectionDisplay
						.get(instanceId);
				for (FormSectionFieldSpec sectionFieldSpec : formSectionSpec
						.getFormSectionFieldSpecs()) {
					boolean present = false;
					for (FormSectionFieldDisplay formSectionFieldDisplay : fieldsTemp) {
						if (formSectionFieldDisplay.getSectionFieldSpecId() == sectionFieldSpec
								.getSectionFieldSpecId()) {
							present = true;
							break;
						}
					}

					if (!present) {
						String localizedFieldLabel = customFieldMap.get(sectionFieldSpec.getUniqueId());
						String key = sectionFieldSpec.getSectionFieldSpecId()
								+ "_" + instanceId;
						FormSectionFieldDisplay formSectionFieldDisplay = new FormSectionFieldDisplay();
						formSectionFieldDisplay
								.setSectionFieldSpecId(sectionFieldSpec
										.getSectionFieldSpecId());
						formSectionFieldDisplay.setFieldType(sectionFieldSpec
								.getFieldType());
						formSectionFieldDisplay.setFieldLabel(sectionFieldSpec
								.getFieldLabel());
						if(!Api.isEmptyString(localizedFieldLabel)) {
							formSectionFieldDisplay.setFieldLabel(sectionFieldSpec
									.getFieldLabel());
						}
						formSectionFieldDisplay
								.setDisplayOrder(sectionFieldSpec
										.getDisplayOrder());
						formSectionFieldDisplay.setVisible(sectionFieldSpec
								.getVisible());
						if (visibilityCondtionSectionFieldSpecMap
								.containsKey(key)) {
							formSectionFieldDisplay
									.setVisibleOnVisibilityDependencyCritiria(0);
						}

						formSectionFieldDisplay.setInstanceId(instanceId);
						if(sectionFieldSpec.getFontColor() != null) {
							String fontColor = "#"+sectionFieldSpec.getFontColor();
							formSectionFieldDisplay.setFontColor(fontColor);
							}
							String fontSize = "";
							if(sectionFieldSpec.getFontSize() != null) {
								if(sectionFieldSpec.getFontSize().equalsIgnoreCase("m")) {
									fontSize = "";
								}else if(sectionFieldSpec.getFontSize().equalsIgnoreCase("l")){
									fontSize = "20px";
								}else if(sectionFieldSpec.getFontSize().equalsIgnoreCase("s")) {
									fontSize = "10px";
								}
								formSectionFieldDisplay.setFontSize(fontSize);
							}
							String bold = "";
							String italic = "";
							String underLine = "";
							
							if(sectionFieldSpec.isBold() == true) {
								bold = "bold";
							}else {
								bold = "";
							}
							if(sectionFieldSpec.isItalic() == true) {
								italic = "italic";
							}else {
								italic = "";
							}
							if(sectionFieldSpec.isUnderLine() == true) {
								underLine = "underline";
							}else {
								underLine = "";
							}
							formSectionFieldDisplay.setBold(bold);
							formSectionFieldDisplay.setItalic(italic);
							formSectionFieldDisplay.setUnderLine(underLine);
						fieldsTemp.add(formSectionFieldDisplay);
					}
				}
			}
			
			boolean inVisibleFormField = false;
			if (inVisibleFieldsDisplay) {
				FormSpec formSpec = extraDao.getFormSpec(form.getFormSpecId()+"");
				Integer purpose = formSpec.getPurpose();
				if (purpose == 3) {
					WorkSpec workSpec = extraSupportDao.getWorkSpecUsingFormSpecUniqueId(formSpec.getUniqueId());
					inVisibleFormField = workSpec.isHiddenFieldsToDisplayInViewMode();
				} else {
					inVisibleFormField = formSpec.isHiddenFieldsToDisplayInViewMode();
				}
			}
			
			if  (!(isContainsSectionFields) && (inVisibleFormField)) {
				Integer instanceId = 1;
				List<FormSectionFieldDisplay> sectionInstance = new ArrayList<FormSectionFieldDisplay>();
				for (FormSectionFieldSpec sectionFieldSpec : formSectionSpec.getFormSectionFieldSpecs()) {
					FormSectionFieldDisplay formSectionFieldDisplay = new FormSectionFieldDisplay();
					String key = sectionFieldSpec.getSectionFieldSpecId() + "_" + instanceId;
					formSectionFieldDisplay.setSectionFieldSpecId(sectionFieldSpec.getSectionFieldSpecId());
					formSectionFieldDisplay.setFieldType(sectionFieldSpec.getFieldType());
					formSectionFieldDisplay.setFieldLabel(sectionFieldSpec.getFieldLabel());
					String localizedFieldLabel = customFieldMap.get(sectionFieldSpec.getUniqueId());
					if(!Api.isEmptyString(localizedFieldLabel)) {
						formSectionFieldDisplay.setFieldLabel(localizedFieldLabel);
					}
					formSectionFieldDisplay.setDisplayOrder(sectionFieldSpec.getDisplayOrder());
					formSectionFieldDisplay.setVisible(sectionFieldSpec.getVisible());
					formSectionFieldDisplay.setVisibleOnVisibilityDependencyCritiria(1);
					formSectionFieldDisplay.setInstanceId(instanceId);
					formSectionFieldDisplay.setFieldValueDispaly("");
					sectionInstance.add(formSectionFieldDisplay);
				}
					sectionDisplay.put(1,
							sectionInstance);
				}
		
			for (List<FormSectionFieldDisplay> list : sectionDisplay.values()) {
				Collections.sort(list,
						new Comparator<FormSectionFieldDisplay>() {
							@Override
							public int compare(FormSectionFieldDisplay o1,
									FormSectionFieldDisplay o2) {
								return o1.getDisplayOrder()
										- o2.getDisplayOrder();
							}
						});
			}
			
			//Log.info(getClass(), "sectionDisplay_"+formSectionSpec.getSectionSpecId()+" :"+sectionDisplay);

			FormDisplay formDisplay = new FormDisplay();
			formDisplay.setId(formSectionSpec.getSectionSpecId());
			formDisplay.setType(2);
			formDisplay.setDisplayOrder(formSectionSpec.getDisplayOrder());
			formDisplay.setPageId(formSectionSpec.getPageId());
			formDisplay.setLabel(formSectionSpec.getSectionTitle());
			formDisplay.setSectionDisplay(sectionDisplay);
			formDisplays.add(formDisplay);
		}

		Collections.sort(formDisplays, new Comparator<FormDisplay>() {
			@Override
			public int compare(FormDisplay o1, FormDisplay o2) {
				return o1.getDisplayOrder() - o2.getDisplayOrder();
			}
		});

		return formDisplays;
	}

	
	public Long modifyWork(FormAndField formAndField, WebUser webUser,
			FormSpec formSpec,ExtraProperties extraProperties) {

		// long workSpecId =
		// extraDao.getWorkSpecIdByFormSpecUniqueId(formSpec.getUniqueId(),
		// webUser.getCompanyId());

		WorkSpec workSpec = getWebExtraManager().
					getWorkSpecsForFromSpecUniqueIdAndCompany(formSpec.getUniqueId(), webUser.getCompanyId());
		 
		Work work = extraDao.getWorkByFormId(formAndField.getFormId());
		Work oldWorkOld = getWebAdditionalSupportManager().getClonedWork(work);
		
		if(formAndField!= null && formAndField.isFromMigrationFields()) {
			work.setFromMigrationFields(formAndField.isFromMigrationFields());
			work.setMigrationFilledBy(Long.parseLong(formAndField.getCreatedBy()));
			work.setMigrationModifiedBy(Long.parseLong(formAndField.getModifiedBy()));
			work.setMigrationCreatedTime(formAndField.getCreatedTime());
			work.setMigrationModifiedTime(formAndField.getModifiedTime());
			if(!Api.isEmptyString(formAndField.getCompletedTime())) {
				work.setCompletedTime(formAndField.getCompletedTime());
			}
		}
		String value = getWorkFieldValueForGivenSkeleton(formAndField,
				constants.getSkeletonWorkEmployeeFieldSpecId());
		if (value != null) {
			if (work.getAssignTo() != null
					&& work.getAssignTo() != Long.parseLong(value)) {
				extraDao.deleteAssignAway(ExtraDao.ELEMENT_WORK,
						work.getWorkId(), Long.parseLong(value));
				extraDao.insertAssignAway(ExtraDao.ELEMENT_WORK,
						work.getWorkId(), work.getAssignTo());
				getExtendedDao().deleteWorkActionExcalationNotificaionLogs(work.getWorkId());
				workFlowExtraDao.updateWorkExcalatedEmpIds(work.getWorkId());
				getExtendedSupportDao().insertIntoWorkActionExcalationNotificaionLogsHistory(work.getWorkSpecId(),(long)-1, 
						(long)-1, 0, work.getCompanyId(), (long)-1, 0, work.getWorkId(),(long)-1,1);
			
			}
			work.setAssignTo(Long.parseLong(value));
			work.setRejectedOn(null);
			work.setRejected(false);
		} else {
			if (formAndField.getAssignmentType() != null && formAndField.getAssignmentType() == 1
					&& !Api.isEmptyString(formAndField.getAssignTo())) {
				value = formAndField.getAssignTo();
				work.setAssignTo(Long.parseLong(value));
				work.setRejectedOn(null);
				work.setRejected(false);
			} else {
				work.setAssignTo(null);
			}

		}

		try
		{
			String workStartTime = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkStartsFieldSpecId());
			String workEndTime = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkEndsFieldSpecId());
			String phoneNumber = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkPhoneNumberFieldSpecId());
			String description = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkDescriptionFieldSpecId());
			String priority = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkProrityFieldSpecId());
			String country = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkCountryFieldSpecId());
			String pincode = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkPincodeFieldSpecId());
			String state = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkStateFieldSpecId());
			String landMark = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkLandMarkFieldSpecId());
			String city = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkCityFieldSpecId());
			String area = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkAreaFieldSpecId());
			String street = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkStreetFieldSpecId());
			String customerId = getWorkFieldValueForGivenSkeleton(formAndField,
					Long.parseLong(constants.getSkeletonCustomerFieldSpecId()));
			String workName = getWorkFieldValueForGivenSkeleton(formAndField,
					constants.getSkeletonWorkNameFieldSpecId());
			
			if(!Api.isEmptyString(workName))
				work.setWorkName(workName);
			if(!Api.isEmptyString(description))
				work.setDescription(description);
			if(!Api.isEmptyString(priority))
				work.setPriority(priority);
			if(!Api.isEmptyString(country))
				work.setCoutry(country);
			if(!Api.isEmptyString(pincode))
				work.setPincode(pincode);
			if(!Api.isEmptyString(state))
				work.setState(state);
			if(!Api.isEmptyString(landMark))
				work.setLandMark(landMark);
			if(!Api.isEmptyString(city))
				work.setCity(city);
			if(!Api.isEmptyString(area))
				work.setArea(area);
			if(!Api.isEmptyString(street))
				work.setStreet(street);
			if(!Api.isEmptyString(customerId)) {
				work.setCustomerId(Long.parseLong(customerId));
			}else {
				work.setCustomerId(null);
			}
			if(!Api.isEmptyString(workStartTime))
			{
				work.setStartTime(workStartTime);
			}
			if(!Api.isEmptyString(workEndTime))
			{
				work.setEndTime(workEndTime);
			}
			if(!Api.isEmptyString(phoneNumber))
			{
				work.setPhoneNumber(phoneNumber);
			}
		}
		catch(Exception e)
		{
			Log.info(this.getClass(), "modifyWork() //Got Exception while capturing Work start and end time in work :"+e);
		}
		
		if(extraProperties!=null)
		{
			work.setRecurringParentId(extraProperties.getRecurringParentId());
			work.setTemplate(extraProperties.getIsTemplate());
			work.setWorkFieldsUniqueKey(extraProperties.getWorkFieldsUniqueKey());
		}
		
		if(work.isInvitation() && work.getInvitationState()!=2){
				work.setInvitationState(Work.INVITATION);
				if(workSpec.isCanRejectWorkInvitation())
				{
					work.setCanRejectWorkInvitation(true);
				}
				else
				{
					work.setCanRejectWorkInvitation(false);
				}
		}
		
		work.setWorkFieldsUniqueKey(formAndField.getWorkFieldsUniqueKey());
		if(formAndField.getSourceOfModification() != null) {
		work.setSourceOfModification(formAndField.getSourceOfModification());
		}
		extraDao.modifyWork(work, webUser.getEmpId(), work.getAppVersion());
		getWebExtensionManager().resolveWorkExtensionData(work,formAndField);
		getExtendedDao().modifyWorkAssignee(work, webUser.getEmpId(),webUser);
		if(work.isInvitation()){
			getWebExtraManager().updateWorkInvitationDetails(work, formAndField, true, webUser,oldWorkOld);
		}
		if(formAndField.getAssignmentType() != null && formAndField.getAssignmentType() == 2) {
			if(formAndField.getEmpSelectionType() == 1) {
				if(!Api.isEmptyString(formAndField.getAllInvitaionEmpIds())){
					long rootEmpId = getRootEmployeeId(webUser);
					List<String> invitationEmpIds = Api.csvToList(formAndField.getAllInvitaionEmpIds());
					invitationEmpIds.remove(rootEmpId+"");
					String csvInvitationEmpIds = Api.toCSV(invitationEmpIds);
					formAndField.setWorkInvitationEmpIds(csvInvitationEmpIds);
				}
			}
		}
		if (formAndField.getAssignmentType() != null && formAndField.getAssignmentType() == 2
				&& !Api.isEmptyString(formAndField.getWorkInvitationEmpIds())) {
			List<String> empIds = Api.csvToList(formAndField.getWorkInvitationEmpIds());
			List<WorkInvitationAndEmployeeMap> workInvitationAndEmployeeMapList = new ArrayList<WorkInvitationAndEmployeeMap>();
			if (empIds != null && !empIds.isEmpty()) {
				for (String empId : empIds) {
					WorkInvitationAndEmployeeMap invitationEmpMap = new WorkInvitationAndEmployeeMap();
					invitationEmpMap.setWorkId(work.getWorkId());
					invitationEmpMap.setEmpId(Long.parseLong(empId));
					invitationEmpMap.setDelivered(false);
					invitationEmpMap.setAccepted(false);
					invitationEmpMap.setActive(true);
					invitationEmpMap.setRejected(false);
					invitationEmpMap.setDeleted(false);
					invitationEmpMap.setCreatedBy(webUser.getEmpId());
					invitationEmpMap.setInvitationType(1);
					workInvitationAndEmployeeMapList.add(invitationEmpMap);
				}
				extraDao.insetWorkInvitatonEmpoyeeMap(workInvitationAndEmployeeMapList);
				try {
					// work Audit log for invitation broad cast.
					Log.info(this.getClass(), "Invitation broadcasted from Modify Work. Work Assign to"+work.getAssignTo()+"");
						work.setRemarks("Invitation broadcasted from Modify Work. Work Assign to"+work.getAssignTo()+"");
						work.setAuditType(Work.TYPE_WORK_INVITATION_BROADCAST_INFO_LOGS);
						getWebAdditionalSupportExtraManager().auditWorkLog(work.getWorkId()+"",work);
				}catch(Exception e) {

				}
				extraDao.updateAllWorkInviationEmpMapActiveState(true, work.getWorkId(), Api.toCSV(empIds), "IN", false,work);

				WorkSpecSchedulingConfig workSpecSchedulingConfig = getWebAdditionalManager().getWorkSpecSchedulingConfig(work.getWorkSpecId());
				if(workSpecSchedulingConfig != null){
					getExtendedDao().updateWorkSmartScheduling(work.getWorkId());
				}
				work.setInvitation(true);
				work.setInvitationState(Work.INVITATION);
				getExtendedDao().updateWorkInvitationState(work.getWorkId(),work.isInvitation(),work.getInvitationState());
				getExtendedDao().updateWorkSmartScheduling(work.getWorkId());
				
				try {
					/*if (constantsExtra.getMaruthiCompanyId().equals(work.getCompanyId() + "")) {
						ProcessJmsMessageStatus processJmsMessageStatus = new ProcessJmsMessageStatus(
								JmsMessage.TYPE_WORK_INVITATION, work.getWorkId(), webUser.getCompanyId(),
								webUser.getEmpId(), JmsMessage.TYPE_WORK_INVITATION_SENT, work.getWorkId(), false);
						getExtendedDao().insertIntoProcessJmsMessageStatus(processJmsMessageStatus,
								ProcessJmsMessageStatus.UN_PROCESSED);
					} else {
						sendJmsMessage(JmsMessage.TYPE_WORK_INVITATION, work.getWorkId(), webUser.getCompanyId(),
								webUser.getEmpId(), JmsMessage.TYPE_WORK_INVITATION_SENT, work.getWorkId(), null,
								false);
					}*/
					sendJmsMessage(JmsMessage.TYPE_WORK_INVITATION, work.getWorkId(), webUser.getCompanyId(),
							webUser.getEmpId(), JmsMessage.TYPE_WORK_INVITATION_SENT, work.getWorkId(), null,
							false);
				} catch (Exception e) {
					Log.info(getClass(), " JMS Message Sent For Work invitations Failed Exception = " + e);
				}

				getWebExtensionManager().processAndInsertIntoWorkInvitationLogs(empIds,webUser,work.getWorkId());
			}

			if(constantsExtra.isAllowGigUserCreation(webUser.getCompanyId())) {
				try {
					List<Long> empIdsLong = Api.csvToLongList(Api.toCSV(empIds));
					PushNotificationTriggerStatus notificationTriggerStatus = new PushNotificationTriggerStatus();
					notificationTriggerStatus.setCompanyId(webUser.getCompanyId());
					notificationTriggerStatus.setEmpId(-1l);
					notificationTriggerStatus.setTriggerType(PushNotificationTriggerStatus.WORK_INVITATION);
					notificationTriggerStatus.setMessageBody("Invitation is waiting for your acceptance");
					getWebExtensionManager().insertIntoPushNotificationTriggerStatus(notificationTriggerStatus,empIdsLong);
				}catch(Exception e) {
					
				}
			}else {
				if (empIds != null && !empIds.isEmpty()) {
					//getWebAdditionalSupportManager().sendPushNotification(Api.toCSV(empIds), Constants.ACTION_TYPE_SYNC);
					JmsMessage jmsMessage = Api.getJmsMessage(JmsMessage.TYPE_WORK_INVITATION, work.getWorkId(), webUser.getCompanyId(),
							webUser.getEmpId(), JmsMessage.TYPE_WORK_INVITATION_SENT, work.getWorkId(), null, false,Api.toCSV(empIds),false);
					jmsMessage.setWork(work);
					getEffortPluginManager().sendGoogleFirebaseMultiCastMessages(Api.toCSV(empIds), Constants.ACTION_TYPE_SYNC, jmsMessage);

				}
			}
		}
		if (formAndField.getAssignmentType() != null && (formAndField.getAssignmentType() == 3 || formAndField.getAssignmentType() == 4))
		{
			getExtendedDao().updateWorkSmartScheduling(work.getWorkId());
			getExtendedDao().deleteProcessedWorksByWorkSchedular(work.getWorkId());
		}
		// extraDao.modifyWork(workSpecId, formAndField.getFormId(),
		// webUser.getCompanyId(), webUser.getEmpId());
		
		if(formAndField.isWorkSharing())
			getWebSupportManager().updateWorkSharingEmployeesForWork(formAndField.getWorkSharingEmpIds(), work, webUser);
		
		if(formAndField!= null && formAndField.isFromMigrationFields()) {
			getExtendedDao().updateMigrationWork(work,webUser.getEmpId(), work.getAppVersion());
		}
		
		return work.getWorkId();
	}

}
