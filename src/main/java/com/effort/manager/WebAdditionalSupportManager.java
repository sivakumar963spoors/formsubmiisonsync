package com.effort.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.effort.context.AppContext;
import com.effort.dao.AuditDao;
import com.effort.dao.EmployeeDao;
import com.effort.dao.ExtraDao;
import com.effort.dao.ExtraSupportAdditionalDao;
import com.effort.dao.ExtraSupportAdditionalSupportDao;
import com.effort.dao.ExtraSupportDao;
import com.effort.dao.WorkFlowExtraDao;
import com.effort.entity.AuditFormFields;
import com.effort.entity.AuditFormSectionFields;
import com.effort.entity.CompanyRestApis;
import com.effort.entity.CustomEntity;
import com.effort.entity.CustomEntityOnDemandMapping;
import com.effort.entity.CustomEntityRequisitionJmsMessageStatus;
import com.effort.entity.CustomEntitySpec;
import com.effort.entity.CustomerEvent;
import com.effort.entity.CustomerRequisitionJmsMessageStatus;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeGroup;
import com.effort.entity.EmployeeRequisitionJmsMessageStatus;
import com.effort.entity.EmployeeStaticField;
import com.effort.entity.Form;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.JmsMessage;
import com.effort.entity.SystemRejectedFormsLog;
import com.effort.entity.WebUser;
import com.effort.entity.WorkFlowFormStatus;
import com.effort.entity.WorkRequisitionJmsMessageStatus;
import com.effort.entity.Workflow;
import com.effort.entity.WorkflowEntityMap;
import com.effort.entity.WorkflowStage;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.settings.ReportConstants;
import com.effort.sqls.Sqls;
import com.effort.util.Api;
import com.effort.util.Api.CsvOptions;
import com.effort.util.ContextUtils;
import com.effort.util.Log;
import com.effort.validators.FormValidator;
@Service
public class WebAdditionalSupportManager {

	@Autowired
	private AuditDao auditDao;
	
	@Autowired
	private  ExtraSupportDao extraSupportDao;
	
	@Autowired
	private ReportConstants reportConstants;
	
	@Autowired
	private Constants constants;
	
	@Autowired
	private FormValidator formValidator;
	
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private WorkFlowExtraDao workFlowExtraDao;
	
	@Autowired
	private ConstantsExtra constantsExtra;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private WebManager webManager;
	
	@Autowired
	private ExtraDao extraDao;
	
	@Autowired
	private ExtraSupportAdditionalDao extraSupportAdditionalDao;
	
	private WebExtensionManager getWebExtensionManager(){
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
		return webExtensionManager;
	}
	
	private WebAdditionalSupportManager getWebAdditionalSupportManager(){
		WebAdditionalSupportManager webAdditionalSupportManager = AppContext.getApplicationContext().getBean("webAdditionalSupportManager",WebAdditionalSupportManager.class);
		return webAdditionalSupportManager;
	}
	
	private WebSupportManager getWebSupportManager(){
		WebSupportManager webSupportManager = AppContext.getApplicationContext().getBean("webSupportManager",WebSupportManager.class);
		return webSupportManager;
	}
	
	private ExtraSupportAdditionalSupportDao getExtraSupportAdditionalSupportDao(){
		ExtraSupportAdditionalSupportDao extraSupportAdditionalSupportDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalSupportDao",ExtraSupportAdditionalSupportDao.class);
		return extraSupportAdditionalSupportDao;
	}
	
	private ContextUtils getContextUtils() {
		ContextUtils contextUtils = AppContext.getApplicationContext().getBean(
				"contextUtils", ContextUtils.class);
		return contextUtils;
	}
	private WebExtraManager getWebExtraManager() {
		WebExtraManager webExtraManager = AppContext.getApplicationContext().getBean("webExtraManager",
				WebExtraManager.class);
		return webExtraManager;
	}
	
	private WebAdditionalSupportExtraManager getWebAdditionalSupportExtraManager(){
		WebAdditionalSupportExtraManager webAdditionalSupportExtraManager = AppContext.getApplicationContext().getBean("webAdditionalSupportExtraManager",WebAdditionalSupportExtraManager.class);
		return webAdditionalSupportExtraManager;
	}

	private WorkFlowExtraDao getWorkFlowExtraDao() {
		WorkFlowExtraDao workFlowExtraDao = AppContext.getApplicationContext().getBean("workFlowExtraDao",
				WorkFlowExtraDao.class);
		return workFlowExtraDao;
	}
	private WebAdditionalManager getWebAdditionalManager(){
		WebAdditionalManager webAdditionalManager = AppContext.getApplicationContext().getBean("webAdditionalManager",WebAdditionalManager.class);
		return webAdditionalManager;
	}

	 @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
		public long logFormAudit(long formId, long by, long companyId, int type, Boolean byClient,String ipAddress, 
				List<JmsMessage> jmsMessages, boolean isMasterForm) {
			String time = Api
					.getDateTimeInUTC(new Date(System.currentTimeMillis()));

			long auditParent = auditDao.auditForm(formId, by, time, ipAddress,isMasterForm,null);
			//auditDao.auditFormFields(formId, auditParent, by, time);
			List<AuditFormFields> auditFormFields = new ArrayList<AuditFormFields>();
			auditFormFields = auditDao.getFormFieldsForAudit(formId, auditParent, by, time, isMasterForm);
			auditDao.auditFormFieldsByBatchUpdate(auditFormFields);
			//auditDao.auditFormSectionFields(formId, auditParent, by, time);
			List<AuditFormSectionFields> auditFormSectionFields = new ArrayList<AuditFormSectionFields>();
			auditFormSectionFields = auditDao.getFormSectionFieldsForAudit(formId, auditParent, by, time);
			auditDao.auditFormSectionFieldsByBatchUpdate(auditFormSectionFields);
			if(!isMasterForm){
				webManager.sendJmsMessage(JmsMessage.TYPE_FORM, auditParent, companyId, by, type,
					formId, null, byClient,jmsMessages);
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
				webManager.sendJmsMessage(JmsMessage.TYPE_CUSTOM_ENTITY_SUBMISSION, formId, companyId, by, type,
						formId, null, byClient);
			}
			return auditParent;
		}
	 
	 public List<CustomEntity> getCustomEntitiesByCustomEntityIds(String customEntityIdCsv) {
			return extraSupportDao.getCustomEntitiesByCustomEntityIds(customEntityIdCsv);
		}
	 
	 public WebUser getWebUser(Employee employee) {
			
			WebUser webUser = new WebUser();
			webUser.setEmpId(employee.getEmpId());
			webUser.setTzo(employee.getTimezoneOffset());
			webUser.setCompanyId(employee.getCompanyId());
			return webUser;
		}
	 @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
		public boolean reinitateFormApprovals(String formId, WebUser webUser,boolean ignoreHistory,
				Long newWorkFlowId,boolean test){
			
			String logText = "revertSystemRejectFormApprovals() // formId = "+formId+" newWorkFlowId = "+newWorkFlowId+" ignoreHistory = "+ignoreHistory;
			boolean status = false;
			try {
				Log.info(getClass(), logText+" starts ...");
				Form form = webManager.getForm(formId+"");
				Log.info(getClass(), logText+" formId = "+form.getFormId());
				Employee employee = null;
				if(webUser == null) {
					long rootEmpId = webManager.getRootEmployeeId(form.getCompanyId());
					employee = webManager.getEmployee(rootEmpId+"");
					webUser = getWebUser(employee);
				}else {
					employee = webManager.getEmployee(webUser.getEmpId()+"");
				}
				List<SystemRejectedFormsLog> systemRejectedFormsLogs= new ArrayList<SystemRejectedFormsLog>();
				WorkFlowFormStatus workFlowFormStatus = getWorkFlowExtraDao().getWorkFlowFormStatusByFormIdOnly(form.getFormId());
				WorkflowStage workflowStage = getWorkFlowExtraDao().getWorkFlowStage(workFlowFormStatus.getWorkFlowStageId());
				if (workFlowFormStatus.getStatusMessage().equalsIgnoreCase(WorkFlowFormStatus.REJECTED_BY_SYSTEM_STATUS_MESSAGE)) {
					
					boolean reinitated = workFlowManager.reinitateSystemRejectionFormForHierarchyChanges(webUser, 
							workFlowFormStatus,employee,ignoreHistory,newWorkFlowId,test);
					Log.info(getClass(), logText+" reinitated = "+reinitated);
					return reinitated;
				}

				
				Log.info(getClass(), logText+" workFlowFormStatus Id = "+workFlowFormStatus.getId());
				long submittedBy = workFlowFormStatus.getSubmittedBy();
				Log.info(getClass(), logText+" submittedBy = "+submittedBy);
				int deleted = getWorkFlowExtraDao().deleteWorkflowFormStatus(webUser.getCompanyId(), formId);
				
				if(deleted  == 0) {
					Log.info(getClass(), logText+"deletion failed");
					return false;
				}
				//webManager.createOrUpdateWorkflowForForm(form, webUser);
				String ids = insertWorkFlowFormStatus(form, webUser, submittedBy,ignoreHistory,newWorkFlowId);
				Log.info(getClass(), logText+" workflowFormStatus Id AND historyId = "+ids);
			
				return true;
		  }catch(Exception e) {
			  Log.info(getClass(), logText+" Exception occured ",e);
			  return false;
		  }
		}
		public CustomEntityOnDemandMapping getCustomEntityOnDemandMappingForGivenFormSpec(String uniqueId, int companyId) {
			return extraSupportAdditionalDao.getCustomEntityOnDemandMappingForGivenFormSpec(uniqueId,companyId);
		}
	 
	 public String insertWorkFlowFormStatus(Form form,WebUser webUser,long submittedBy,boolean ignoreHistory,Long newWorkFlowId) {

			String ids = "";
			try {
			
		    String logText = "insertWorkFlowFormStatus() // formId = "+form.getFormId()+" webUser EmpId = "+webUser.getEmpId();
			FormSpec formSpec = webManager.getFormSpec("" + form.getFormSpecId());
			/*Long workflowId = getWorkFlowExtraDao()
					.getWorkflowIdIfExistFromWorkflowEntityMap(
							formSpec.getUniqueId(),
							WorkflowEntityMap.WORKFLOW_ENTITY_TYPE_FORM);*/
			
			Long workflowId = newWorkFlowId;
			if(workflowId == null) {
				workflowId = workFlowManager.getWorkFlowIdByFormSpecUniqueIdAndFormId(formSpec.getUniqueId(),form.getFormId()+"",WorkflowEntityMap.WORKFLOW_ENTITY_TYPE_FORM);
				if(workflowId == null) {
					return "now matching workflowId found"; 
				}
			}
			
			Workflow workflow = getWorkFlowExtraDao().getWorkFlow(workflowId);
			
			List<WorkflowStage> workflowStageList = getWorkFlowExtraDao().getWorkFlowStages("" + workflow.getWorkflowId());
			
			Employee employee = getWebExtensionManager().getEmployeeBasicDetailsAndTimeZoneByEmpId(submittedBy+"");
			
			WorkFlowFormStatus workFlowFormStatus = getWorkFlowExtraDao()
					.getWorkFlowFormStatusByFormId(form.getFormId());
			
			WorkflowStage workflowStage = null;
			workflowStage = workflowStageList.get(0);
			workFlowFormStatus = new WorkFlowFormStatus();
			workFlowFormStatus.setWorkFlowId(workflow.getWorkflowId());
			workFlowFormStatus.setWorkFlowName(workflow.getWorkflowName());
			workFlowFormStatus.setFormSpecId(form.getFormSpecId());
			workFlowFormStatus.setFormId(form.getFormId());
			workFlowFormStatus.setStatus(WorkFlowFormStatus.STATUS_TYPE_WAITING);

			webManager.populateWorkflowFormStatus(workFlowFormStatus,"" + form.getFormId(), webUser.getCompanyId(),
					workflowStage, employee, webUser.getEmpId());
			
			getWorkFlowExtraDao().insertWorkflowStatus(workFlowFormStatus);
			
			Long id = workFlowFormStatus.getId();
			Long historyId = null;
			if(!ignoreHistory) {
			   
				workFlowFormStatus.setRemarks("Reinitiated");
				historyId = webManager.insertOrUpdateWorkFlowFormStatusHistory(employee, webUser, workFlowFormStatus, employee.getEmpId());

				Log.info(getClass(), logText+" workFlowFormStatus Id = "+id+" historyId = "+historyId);
				
				if (workFlowFormStatus.getHistoryId() != null) {
					webManager.sendJmsMessage(JmsMessage.TYPE_WORK_FLOW,
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
					webManager.sendJmsMessage(JmsMessage.TYPE_WORK_FLOW,
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
		    ids = id+"_"+historyId;
		  }catch(Exception e) {
			  Log.info(getClass(), "insertWorkFlowFormStatus() // Exception occured ",e);
		  }
			return ids;
		}
	 
		public List<CustomerEvent> getCutomerEventsByCustomerId(String customerIds) {
			
			return extraSupportDao.getCutomerEventsByCustomerId(customerIds);
		}
		public FormSpec getLatestEmployeeFormspec(Integer companyId,
				int purpouseEmployeeForm)
		{
			return getExtraSupportAdditionalSupportDao().getLatestEmployeeFormspec(companyId,purpouseEmployeeForm);
		}

		public List<FormFieldSpec> getEmployeeUserDefinedFieldsForComputation(WebUser webUser) 
		{
			try
			{
				FormSpec employeeFormSpec = getLatestEmployeeFormspec(webUser.getCompanyId(),FormSpec.PURPOUSE_EMPLOYEE_FORM);
				if(employeeFormSpec != null)
				{
					return webManager.getFormFieldSpecs(employeeFormSpec.getFormSpecId());
				}
			}
			catch(Exception e)
			{
				StackTraceElement[] stackTrace = e.getStackTrace();
				getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getEmployeeUserDefinedFieldsForComputation method",e.toString(),stackTrace,webUser.getCompanyId());
			}
			
			return new ArrayList<FormFieldSpec>();
		}
		public List<CustomEntitySpec> getCustomEntitySpecs(Integer companyId)
		{
			return extraSupportDao.getCustomEntitySpecs(companyId);
		}
		public Map<Long, List<FormFieldSpec>> getCustomEntitySpecIdAndFormFieldSpesMap(
				List<CustomEntitySpec> customEntitySpecs)
		{
			Map<Long, List<FormFieldSpec>> customEntitySpecIdAndFormFieldSpecsMap = new HashMap<Long, List<FormFieldSpec>>();
			if(customEntitySpecs!=null && customEntitySpecs.size()>0)
			{
				String formSpecUniqueIdCsv = Api.toCSV(customEntitySpecs, "formSpecUniqueId",CsvOptions.FILTER_NULL_OR_EMPTY);
				Map<String,Long> formSpecUniqueIdAndCustomEntitySpecIdMap = (Map) Api.getMapFromList(customEntitySpecs,
						"formSpecUniqueId", "customEntitySpecId");
				
				String formSpecUniqueIds = Api.processStringValuesList1(Api.csvToList(formSpecUniqueIdCsv));
				List<FormSpec> formSpecs = new ArrayList<FormSpec>();
				if(!Api.isEmptyString(formSpecUniqueIds))
				{
					formSpecs = webManager.getLatestFormSpecsForUnquids(formSpecUniqueIds);
				}
				List<FormFieldSpec> formFieldSpecs = null;
				for(FormSpec formSpec : formSpecs)
				{
					formFieldSpecs = webManager.getFormFieldSpecs(formSpec
							.getFormSpecId());
					if(formSpecUniqueIdAndCustomEntitySpecIdMap.get(formSpec.getUniqueId()) != null)
					{
						Long customEntitySpecId = formSpecUniqueIdAndCustomEntitySpecIdMap.get(formSpec.getUniqueId());
						customEntitySpecIdAndFormFieldSpecsMap.put(customEntitySpecId, formFieldSpecs);
					}
					
				}
			}
			
			return customEntitySpecIdAndFormFieldSpecsMap;
		}
		 public List<Employee> resolveEmployeesForComputedFields(List<Employee> employees,
					WebUser webUser, String formSpecId, String fieldSpecId, String isSectionField) {
			 
			List<Employee> filteredEmployees = new ArrayList<Employee>();
			
			if(!Api.isEmptyString(fieldSpecId))
			{
				if (fieldSpecId.startsWith("F")) {
					fieldSpecId = fieldSpecId.substring(1);
				}
				else{
					fieldSpecId = fieldSpecId.substring(2);
				}
				if("true".equalsIgnoreCase(isSectionField))
				{
					FormSectionFieldSpec formSectionFieldSpec = getWebAdditionalManager()
							.getFormSectionFieldByFormSpecIdAndSectionFieldSpecId(formSpecId,fieldSpecId);
					if(formSectionFieldSpec != null && !Api.isEmptyString(formSectionFieldSpec.getPickEmployeesFromGroupIds())){
						
						List<EmployeeGroup> groupsWithEmpIds = extraSupportDao.getEmployeesOfEmployeeGroups(formSectionFieldSpec.getPickEmployeesFromGroupIds());
						String empIdsInGroup = Api.toCSVFromList(groupsWithEmpIds, "empIds");
						List<Employee> employeesInGroups = webManager.getEmployeesIn(empIdsInGroup);
						employees.addAll(employeesInGroups);
						filteredEmployees.addAll(employees);
					}
					else
					{
						filteredEmployees.addAll(employees);
					}
				}
				else
				{
					FormFieldSpec formFieldSpec = getFormFieldSpecByFormSpecIdAndFieldSpecId(formSpecId,fieldSpecId);
					if(!Api.isEmptyString(formFieldSpec.getPickEmployeesFromGroupIds())){
						
						List<EmployeeGroup> groupsWithEmpIds = extraSupportDao.getEmployeesOfEmployeeGroups(formFieldSpec.getPickEmployeesFromGroupIds());
						String empIdsInGroup = Api.toCSVFromList(groupsWithEmpIds, "empIds");
						List<Employee> employeesInGroups = webManager.getEmployeesIn(empIdsInGroup);
						employees.addAll(employeesInGroups);
						filteredEmployees.addAll(employees);
					}
					else
					{
						filteredEmployees.addAll(employees);
					}
				}
			}
			else
			{
				filteredEmployees.addAll(employees);
			}
			return filteredEmployees;
		}
		 
			private FormFieldSpec getFormFieldSpecByFormSpecIdAndFieldSpecId(
					String formSpecId, String fieldSpecId) {
				
				return extraSupportDao.getFormFieldSpecByFormSpecIdAndFieldSpecId(formSpecId,fieldSpecId);
			}
			
		 public FormSectionFieldSpec getFormSectionFieldByFormSpecIdAndSectionFieldSpecId(String formSpecId, String sectionFieldSpecId)
			{
				return extraSupportDao.getFormSectionFieldByFormSpecIdAndSectionFieldSpecId(formSpecId,sectionFieldSpecId);
			}
		 
		 
		 public CustomEntity getCustomEntityByCustomEntityId(String customEntityId) {
				return extraSupportDao.getCustomEntityByCustomEntityId(customEntityId);
			}
		 

			
		 public void populateCustomEntityFields(Map<String, Object> values,
					String id, String value, String instanceId,
					List<String> accessibleEmployeeIds, WebUser webUser, Map<Long,List<FormFieldSpec>> customEntitySpecIdAndFormFieldSpesMap) 
			{

				values.put(id, value);
				
				CustomEntity customEntity = null;
				try
				{
					if(!Api.isEmptyString(value))
						customEntity = getCustomEntityByCustomEntityId(value);
				}
				catch(Exception e)
				{
					
				}
				if(customEntity != null)
				{
					if(customEntitySpecIdAndFormFieldSpesMap.get(customEntity.getCustomEntitySpecId()) !=null)
					{
						boolean addData;
						List<FormFieldSpec> customEntityFormFieldSpecs = customEntitySpecIdAndFormFieldSpesMap.get(customEntity.getCustomEntitySpecId());
						
						if(customEntityFormFieldSpecs != null && customEntityFormFieldSpecs.size() > 0 &&
								customEntity.getFormId() != null && customEntity.getFormId() != 0 && customEntity.getFormId() != -1)
						{
							//List<FormField> formFields = webManager.getFormFields(customEntity.getFormId());
							List<FormField> formFields = getWebAdditionalSupportExtraManager().getMasterFormFields(customEntity.getFormId());
							if(formFields != null && formFields.size() > 0)
							{
								Employee webUserEmp = employeeDao.getEmployeeBasicDetailsAndTimeZoneByEmpId(webUser.getEmpId()+"");
								Map<String, FormFieldSpec> uniqueIdAndFormFieldSpecMap = (Map)Api.getMapFromList(customEntityFormFieldSpecs, "uniqueId");
								for(FormField formField : formFields)
								{
									String fieldValue = "";
									FormFieldSpec formFieldSpec = uniqueIdAndFormFieldSpecMap.get(formField.getUniqueId());
									if(formFieldSpec != null
											&& formFieldSpec.getFieldType() == formField.getFieldType())
									{
										addData = true;
										if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE
												&& accessibleEmployeeIds != null && !accessibleEmployeeIds.contains(formField.getFieldValue()))
										{
											addData = false;
										}
										else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_LIST &&
												formField.getFieldValue() != null && Api.isNumber(formField.getFieldValue()))
										{
											if(!getWebExtraManager().isAccessibleEntity(formFieldSpec.getFieldTypeExtra(), formField.getFieldValue(), webUser))
											{
												addData = false;
											}
										}
										else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER &&
												formField.getFieldValue() != null && Api.isNumber(formField.getFieldValue()))
										{
											if(!getWebExtraManager().isAccessibleCustomer(formField.getFieldValue(), webUser))
											{
												addData = false;
											}
										}
										else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_LIST &&
												!Api.isEmptyString(formField.getFieldValue()))
										{
											String entityIds = getWebExtraManager().getAccessibleEntititesFromEntityIds(formFieldSpec.getFieldTypeExtra(), formField.getFieldValue(), webUser);
											if(Api.isEmptyString(entityIds))
											{
												addData = false;
											}
											else
											{
												formField.setFieldValue(entityIds);
											}
										}
										else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME &&
												!Api.isEmptyString(formField.getFieldValue()))
										{
													Api.convertDateTimesToGivenType(formField, ""+webUserEmp.getTimezoneOffset(),
															DateConversionType.UTC_DATETIME_TO_DATETIME_WITH_TZO, "fieldValue");
										}
										if (addData) 
										{
											String key = id + "_"
													+ formFieldSpec.getExpression();
											String id1 = id;
											if (instanceId != null) 
											{
												key = key + "[" + instanceId + "]";
												id1 = id1 + "[" + instanceId + "]";
											}

											if (Api.isAutoComputedEnabledNumericDataType(formFieldSpec
													.getFieldType())) 
											{
												webManager.populateValueInMap(key,
														formField.getFieldValue(), values);
											}
											else 
											{
												webManager.populateValueInObjectMap(key,
														formField.getFieldValue(), values);
											}
										}
									}
								}
							}
						}//
					}
				}
			}
		 
		 
		 public void addToResultList(List<String> result, boolean flag)
			{
				if(flag)
				{
					result.add("true");
				}
				else
				{
					result.add("false");
				}
			}	
		 
		 public void populateEmployeeFieldsForFilledBy(Map<String, Object> values,Integer instanceId,
					List<String> accessibleEmployeeIds, WebUser webUser,
					List<FormFieldSpec> employeeUserDefinedFields,
					List<Employee> allEmployees) 
			{

				String value = webUser.getEmpId()+"";
				String id = "E1009";
				Employee employee = null;
				if(!Api.isEmptyString(value))
				{
					employee = employeeDao.getEmployeeBasicDetailsByEmpId(value);
				}
				
				if(employee != null)
				{
					List<EmployeeStaticField> employeeStaticFields = getWebExtraManager().getEmployeeStaticFieldsForComputation();
					EmployeeStaticField employeeStaticFld = new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_FORM_FILLED);
					employeeStaticFields.add(employeeStaticFld);
					boolean addData;
					for(EmployeeStaticField employeeStaticField : employeeStaticFields)
					{
						String staticValue = "";
						addData = false;
						if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_FIRST_NAME_EXPR))
						{
							staticValue = employee.getEmpFirstName();
							if(!Api.isEmptyString(staticValue))
								addData = true;
							
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_LAST_NAME_EXPR))
						{
							staticValue = employee.getEmpLastName();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_ID_EXPR))
						{
							staticValue = employee.getEmpNo();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_REPORTING_MANAGER_EXPR))
						{
							staticValue = employee.getManagerId()+"";
							if(!Api.isEmptyString(staticValue))
							{
								/*if(accessibleEmployeeIds.contains(staticValue))
								{
									addData = true;
								}*/ //under employees check condition removed on 03-08-2018 Based On Abhinav's Requirement
								addData = true;
							}
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_EMAIL_EXPR))
						{
							staticValue = employee.getEmpEmail();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_MOBILE_EXPR))
						{
							staticValue = employee.getEmpPhone();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_EFFORT_ID_EXPR))
						{
							staticValue = employee.getImei();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_DESIGNATION_EXPR))
						{
							staticValue = employee.getDesignation();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_ADDRESS_STREET_EXPR))
						{
							staticValue = employee.getEmpAddressStreet();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_ADDRESS_AREA_EXPR))
						{
							staticValue = employee.getEmpAddressArea();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_ADDRESS_CITY_EXPR))
						{
							staticValue = employee.getEmpAddressCity();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_ADDRESS_DISTRICT_EXPR))
						{
							staticValue = employee.getEmpAddressDistrict();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_ADDREESS_PINCODE_EXPR))
						{
							staticValue = employee.getEmpAddressPincode();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_ADDRESS_LANDMARK_EXPR))
						{
							staticValue = employee.getEmpAddressLandMark();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_ADDRESS_COUNTRY_EXPR))
						{
							staticValue = employee.getEmpAddressCountry();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_ADDRESS_STATE_EXPR))
						{
							staticValue = employee.getEmpAddressState();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_HOME_LOCATION_EXPR))
						{
							if(!Api.isEmptyString(employee.getHomeLat())
									&& !Api.isEmptyString(employee.getHomeLong()))
							{
								staticValue = employee.getHomeLat()+","+employee.getHomeLong();
								if(!Api.isEmptyString(staticValue))
									addData = true;
							}
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_WORK_LOCATION_EXPR))
						{
							if(!Api.isEmptyString(employee.getWorkLat())
									&& !Api.isEmptyString(employee.getWorkLong()))
							{
								staticValue = employee.getWorkLat()+","+employee.getWorkLong();
								if(!Api.isEmptyString(staticValue))
									addData = true;
							}
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_JOINING_DATE_EXPR))
						{
							if(!Api.isEmptyString(employee.getEmpJoiningDate()))
							{
								staticValue = employee.getEmpJoiningDate();
								if(!Api.isEmptyString(staticValue))
									addData = true;
							}
						}
						else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_FORM_FILLED_EXPR))
						{
						/*
						 * staticValue = employee.getEmpId()+""; if(!Api.isEmptyString(staticValue)) {
						 * addData = true; }
						 */
						}
						
						if (addData) 
						{
							String key = id + "_"
									+ employeeStaticField.getExpression();
							String id1 = id;
							if (instanceId != null) 
							{
								key = key + "[" + instanceId + "]";
								id1 = id1 + "[" + instanceId + "]";
							}

							if (Api.isAutoComputedEnabledNumericDataType(employeeStaticField
									.getFieldType())) 
							{
								webManager.populateValueInMap(key,
										staticValue, values);
							}
							else 
							{
								webManager.populateValueInObjectMap(key,
										staticValue, values);
							}
						}
					}
					
					if(employeeUserDefinedFields != null && employeeUserDefinedFields.size() > 0 &&
							employee.getEmpFormId() != null &&employee.getEmpFormId() != 0 && employee.getEmpFormId() != -1)
					{
						accessibleEmployeeIds = Api.listToList(allEmployees, "empId");
						
						//List<FormField> formFields = webManager.getFormFields(employee.getEmpFormId());
						List<FormField> formFields = getWebAdditionalSupportExtraManager().getMasterFormFields(employee.getEmpFormId());
						if(formFields != null && formFields.size() > 0)
						{
							Map<String, FormFieldSpec> uniqueIdAndFormFieldSpecMap = (Map)Api.getMapFromList(employeeUserDefinedFields, "uniqueId");
							for(FormField formField : formFields)
							{
								String fieldValue = "";
								FormFieldSpec formFieldSpec = uniqueIdAndFormFieldSpecMap.get(formField.getUniqueId());
								if(formFieldSpec != null
										&& formFieldSpec.getFieldType() == formField.getFieldType())
								{
									addData = true;
									if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE
											&& accessibleEmployeeIds != null && !accessibleEmployeeIds.contains(formField.getFieldValue()))
									{
										
										addData = false;
									}
									else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_LIST &&
											formField.getFieldValue() != null && Api.isNumber(formField.getFieldValue()))
									{
										if(!getWebExtraManager().isAccessibleEntity(formFieldSpec.getFieldTypeExtra(), formField.getFieldValue(), webUser))
										{
											addData = false;
										}
									}
									else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER &&
											formField.getFieldValue() != null && Api.isNumber(formField.getFieldValue()))
									{
										if(!getWebExtraManager().isAccessibleCustomer(formField.getFieldValue(), webUser))
										{
											addData = false;
										}
									}
									else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_LIST &&
											!Api.isEmptyString(formField.getFieldValue()))
									{
										String entityIds = getWebExtraManager().getAccessibleEntititesFromEntityIds(formFieldSpec.getFieldTypeExtra(), formField.getFieldValue(), webUser);
										if(Api.isEmptyString(entityIds))
										{
											addData = false;
										}
										else
										{
											formField.setFieldValue(entityIds);
										}
									}
									if (addData) 
									{
										String key = id + "_"
												+ formFieldSpec.getExpression();
										String id1 = id;
										if (instanceId != null) 
										{
											key = key + "[" + instanceId + "]";
											id1 = id1 + "[" + instanceId + "]";
										}

										if (Api.isAutoComputedEnabledNumericDataType(formFieldSpec
												.getFieldType())) 
										{
											webManager.populateValueInMap(key,
													formField.getFieldValue(), values);
										}
										else 
										{
											webManager.populateValueInObjectMap(key,
													formField.getFieldValue(), values);
										}
									}
								}
							}
						}
					}
				}
			}
		
}
