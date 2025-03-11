package com.effort.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import com.effort.dao.SyncDao;
import com.effort.dao.WorkFlowExtraDao;
import com.effort.entity.AuditFormFields;
import com.effort.entity.CustomEntityOnDemandMapping;
import com.effort.entity.CustomEntityRequisitionJmsMessageStatus;
import com.effort.entity.CustomerOnDemandMapping;
import com.effort.entity.CustomerRequisitionJmsMessageStatus;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeEventSubmissions;
import com.effort.entity.EmployeeEventSubmissionsAuditLogs;
import com.effort.entity.EmployeeOnDemandMapping;
import com.effort.entity.EmployeeRequisitionForm;
import com.effort.entity.EmployeeRequisitionJmsMessageStatus;
import com.effort.entity.EmployeeTargetsConfiguration;
import com.effort.entity.FormField;
import com.effort.entity.FormSpec;
import com.effort.entity.JmsMessage;
import com.effort.entity.WorkFlowFormStatus;
import com.effort.entity.WorkOnDemandMapping;
import com.effort.entity.WorkRequisitionJmsMessageStatus;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.settings.ReportConstants;
import com.effort.util.Api;
import com.effort.util.ContextUtils;
import com.effort.util.Log;
import com.effort.validators.FormValidator;
@Service
public class WebExtensionManager {
	
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
	private AuditDao auditDao;
	
	@Autowired
	private SyncDao syncDao;

	
	private WorkFlowManager getWorkFlowManager(){
		WorkFlowManager workFlowManager = AppContext.getApplicationContext().getBean("workFlowManager",WorkFlowManager.class);
		return workFlowManager;
	}

	private WebSupportManager getWebSupportManager(){
		WebSupportManager webSupportManager = AppContext.getApplicationContext().getBean("webSupportManager",WebSupportManager.class);
		return webSupportManager;
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
	
	public void resolveAutoGenFields(String empId,List<FormField> fields,List<FormField> autoGenSeqFields) {
		
		String logText = "resolveAutoGenFields() // empId = "+empId;
		int removedCount = 0;
		Map<String,FormField> fieldslistMap = new HashMap<String,FormField>();
        for(FormField formField : fields) {
                fieldslistMap.put(formField.getFormId()+"_"+formField.getFieldSpecId(), formField);
        }
        
        for (FormField formField : autoGenSeqFields) {
                if (fieldslistMap.get(formField.getFormId()+"_"+formField.getFieldSpecId()) != null) {
                        FormField formFieldNew = fieldslistMap.get(formField.getFormId()+"_"+formField.getFieldSpecId());
                        fields.remove(formFieldNew);
                        removedCount++;
                }
        }
        Log.info(getClass(), logText+" removedCount = "+removedCount+" fields aize = "+fields.size()+" autoGenSeqFields size = "+autoGenSeqFields.size());
        fields.addAll(autoGenSeqFields);
	}
	
	 public List<EmployeeOnDemandMapping> getEmployeesOnDemandMappingByUniqueId(String formSpecUniqueId, Integer companyId,Integer triggerEvent) {
		  return getExtendedDao().getEmployeesOnDemandMappingByUniqueId(formSpecUniqueId, companyId,triggerEvent);
	  }
	 
	 public long insertEmployeeRequisitionForm(EmployeeRequisitionForm employeeRequisitionForm) {
			return getExtendedDao().insertEmployeeRequisitionForm(employeeRequisitionForm);
		}

	public void insertEmployeeEventSubmissions(EmployeeEventSubmissions employeeEventSubmission) {
		syncDao.insertEmployeeEventSubmissions(employeeEventSubmission);
	}
	
	public List<Long> getGroupBasedEmployeesWhoHasAlreadyApprovedOrRejected(Long formId, Long workFlowStageId,
			String empIds, int status, WorkFlowFormStatus workflowFormStatus) {
		return getExtendedDao().getGroupBasedEmployeesWhoHasAlreadyApprovedOrRejected(formId,workFlowStageId,empIds,status,workflowFormStatus);
	}
	public List<EmployeeTargetsConfiguration> getEmployeeTargetsConfigurationList(long companyId,String uniqueId,String workSpecId,int moduleTypeId) 
	{
		return syncDao.getEmployeeTargetsConfigurationList(companyId,uniqueId,workSpecId,moduleTypeId);
	}
	
	public void insertEmployeeEventSubmissionsAuditId(EmployeeEventSubmissionsAuditLogs employeeEventSubmissionsAuditLog) {
		syncDao.insertEmployeeEventSubmissionsAuditLogs(employeeEventSubmissionsAuditLog);
	}
	public Employee getEmployeeBasicDetailsAndTimeZoneByEmpId(String empId) {
		return employeeDao.getEmployeeBasicDetailsAndTimeZoneByEmpId(empId);
	}
	
	public void sendExceptionDetailsMail(String exceptionType,String exception,StackTraceElement[] stackTrace,Integer companyId) {
		
		/*
		 * try{
		 * 
		 * String mailBody = constantsExtra.getExceptionDetailsBodyMessage(); String
		 * rootCause = buildExceptionReason(stackTrace); if(companyId > 0){ Company
		 * company = webManager.getCompany(companyId+""); mailBody =
		 * mailBody.replace("<companyDetails>", company.getCompanyName() +"-"
		 * +companyId) .replace("<exceptionName>", exception)
		 * .replace("<exceptionType>", exceptionType) .replace("<exceptionDetails>",
		 * rootCause+""); }else{ mailBody = mailBody.replace("<exceptionName>",
		 * exception) .replace("<companyDetails>", "") .replace("<exceptionType>",
		 * exceptionType) .replace("<exceptionDetails>", rootCause+""); }
		 * 
		 * 
		 * 
		 * String mailSubject = constants.getSendExceptionDetailsMailSubject();
		 * 
		 * String toMailIds = ""; toMailIds =
		 * constantsExtra.getMaildIdsForImportsExceptionEscalation(companyId);
		 * 
		 * String[] mailIds = toMailIds.split(",");
		 * 
		 * ArrayList<String> mailList = new ArrayList<String>( Arrays.asList(mailIds));
		 * 
		 * for (String mailId : mailList){ mailTask.sendMail("", mailId,mailSubject,
		 * mailBody,companyId + ""); } }catch(Exception e){
		 * 
		 * }
		 */

	}
	public String secondsToDurationForDurationDataType(String secondsInString) 
	{
		/*if(Api.isEmptyString(secondsInString)) {
			return "";
		}
		Double seconds = Double.parseDouble(secondsInString);
		if(seconds > 359999) {
			secondsInString = "359999";
		}else if(seconds < 0) { 
			secondsInString = "0";
		}*/
		String fieldValue = Api.secondsToDuration(secondsInString);
		return fieldValue;
	}
	
	 public void getAllReqConfiurations(Long formSpecId,CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus,
			 CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus,
			 WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus,EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus) {
			FormSpec formSpec = getWebAdditionalSupportExtraManager().getFormSpecByFormSpecId(formSpecId);

			boolean reqConfiguration = false;
			CustomerOnDemandMapping customerCreationOnDemandMapping = 
					   getExtendedDao().getCustomerOnDemandMappingForGivenFormSpec(formSpec.getUniqueId(), formSpec.getCompanyId());
			 
			CustomEntityOnDemandMapping customEntityOnDemandMapping = getWebAdditionalSupportManager().getCustomEntityOnDemandMappingForGivenFormSpec(
                    formSpec.getUniqueId(),formSpec.getCompanyId());

			WorkOnDemandMapping workOnDemandMapping = getWebFunctionalManager().getWorkOnDemandMappingForGivenFormSpec(
					formSpec.getUniqueId(),formSpec.getCompanyId());
			
			EmployeeOnDemandMapping employeeCreationOnDemand = getExtendedDao().getEmployeeOnDemandMappingForGivenFormSpec(formSpec.getUniqueId(), formSpec.getCompanyId());
			
			if(customerCreationOnDemandMapping != null) {
				getExtendedDao().insertIntoCustomerRequisitionJmsMessageStatus(customerRequisitionJmsMessageStatus,CustomerRequisitionJmsMessageStatus.UN_PROCESSED);
			}
			
			if(customEntityOnDemandMapping != null) {
				getExtendedDao().insertIntoCustomEntityRequisitionJmsMessageStatus(customEntityRequisitionJmsMessageStatus,CustomEntityRequisitionJmsMessageStatus.UN_PROCESSED);
			}
			if(workOnDemandMapping != null) {
				//getExtendedDao().insertIntoWorkRequisitionJmsMessageStatus(workRequisitionJmsMessageStatus,WorkRequisitionJmsMessageStatus.UN_PROCESSED_TEMPORARY);
				getExtendedDao().insertIntoWorkRequisitionJmsMessageStatus(workRequisitionJmsMessageStatus,WorkRequisitionJmsMessageStatus.UN_PROCESSED);
			}
			if(employeeCreationOnDemand != null) {
				getExtendedDao().insertIntoEmployeeRequisitionJmsMessageStatus(employeeRequisitionJmsMessageStatus,EmployeeRequisitionJmsMessageStatus.UN_PROCESSED);
			}
		}
			
	 
	 public boolean getFieldValidationResultForDuration(String fieldValue,String givenValue,int condition) {

			boolean result = false;
			double fieldValueInSec = 0d;
			double givenValueInSec = 0d;
			
			fieldValueInSec = Api.durationToSeconds(fieldValue);
			givenValueInSec = Api.durationToSeconds(givenValue);
			
			if (condition == 6) {
				result = fieldValueInSec < givenValueInSec;
			} else if (condition == 7) {
				result = fieldValueInSec >= givenValueInSec;
			} else if (condition == 8) {
				result = fieldValueInSec <= givenValueInSec;
			} else if (condition == 9) {
				result = fieldValueInSec == givenValueInSec;
			} else if (condition == 10) {
				result = fieldValueInSec != givenValueInSec;
			} else if (condition == 11) {
				result = fieldValueInSec > givenValueInSec;
			}else if (condition == 23) {// Empty
				if (Api.isEmptyString(fieldValue)) {
					result = true;
				}
			} else if (condition == 24) {// Not Empty
				if (!Api.isEmptyString(fieldValue)) {
					result = true;
				}
			}
		
		  return result;
		}
}


