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
import com.effort.entity.AuditFormSectionFields;
import com.effort.entity.CompanyFont;
import com.effort.entity.CustomEntityOnDemandMapping;
import com.effort.entity.CustomEntityRequisitionJmsMessageStatus;
import com.effort.entity.CustomerAutoFilteringCritiria;
import com.effort.entity.CustomerOnDemandMapping;
import com.effort.entity.CustomerRequisitionJmsMessageStatus;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeEventSubmissions;
import com.effort.entity.EmployeeEventSubmissionsAuditLogs;
import com.effort.entity.EmployeeOnDemandMapping;
import com.effort.entity.EmployeeRequisitionForm;
import com.effort.entity.EmployeeRequisitionJmsMessageStatus;
import com.effort.entity.EmployeeTargetsConfiguration;
import com.effort.entity.ExtraProperties;
import com.effort.entity.Form;
import com.effort.entity.FormAndField;
import com.effort.entity.FormField;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.JmsMessage;
import com.effort.entity.MobileNumberVerification;
import com.effort.entity.ProcessJmsMessageStatus;
import com.effort.entity.StartWorkStopWorkLocations;
import com.effort.entity.WebUser;
import com.effort.entity.Work;
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
	 public void resolveFormSectionFieldStyleProperties(List<FormSectionFieldSpec> formSectionFieldSpecsList) {
			
			if(formSectionFieldSpecsList!=null) {
				for(FormSectionFieldSpec fsf : formSectionFieldSpecsList){
					if(fsf.getFieldLabelFontId() != null) {
						CompanyFont companyFont = getWebAdditionalSupportManager().getCompanyFont(fsf.getFieldLabelFontId());
						if(companyFont != null) {
							if(companyFont.getFontColor() != null) {
								fsf.setFontColor(companyFont.getFontColor());
							}
							if(companyFont.getFontSize() != null) {
								fsf.setFontSize(companyFont.getFontSize());
							}
							fsf.setBold(companyFont.isBold());
							fsf.setItalic(companyFont.isItalic());
							fsf.setUnderLine(companyFont.isUnderLine());
						}
					}
				}
			}
						
		}
	 
	 
		public Long verifyMobileNumberAndOtpForWeb(MobileNumberVerification mobileNumberVerification) {
			Long isValid = getExtendedDao().verifyMobileNumberAndOtp(mobileNumberVerification);
			if(isValid == MobileNumberVerification.OTP_VERIFIED)
				extraSupportDao.updateOtpGenerationData(mobileNumberVerification,MobileNumberVerification.OTP_VERIFIED);
			else if(isValid == MobileNumberVerification.OTP_EXPIRED)
				extraSupportDao.updateOtpGenerationData(mobileNumberVerification,MobileNumberVerification.OTP_EXPIRED);
			else
				extraSupportDao.updateOtpGenerationData(mobileNumberVerification,MobileNumberVerification.INVALID_OTP);
			
			return isValid;
		}
		
		public String getExistedValueAgainstFormSpecAndFieldSpec(long formId,long formSpecId,long fieldSpecId){
			FormField formFieldDto = new FormField();
			formFieldDto.setFormId(formId);
			formFieldDto.setFormSpecId(formSpecId);
			formFieldDto.setFieldSpecId(fieldSpecId);
			String mobileNumber = extraSupportDao.getExistedValueAgainstFormSpecAndFieldSpec(formFieldDto);
			
			return mobileNumber;
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
	 
	 @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
		public long logFormAuditForOnlineForms(long formId, long by, long companyId, int type, Boolean byClient,String ipAddress, 
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
				ProcessJmsMessageStatus processJmsMessageStatus = new ProcessJmsMessageStatus(JmsMessage.TYPE_FORM, auditParent, companyId, by, type,
						formId,byClient);
				processJmsMessageStatus.setMarutiCompany(false);
				getExtendedDao().insertIntoProcessJmsMessageStatus(processJmsMessageStatus,ProcessJmsMessageStatus.UN_PROCESSED);
			}
			return auditParent;
		}
	 
	 public CustomerOnDemandMapping getCustomerOnDemandForListScreen(String formSpecUniqueId, Integer companyId) {
			return  getExtendedDao().getCustomerOnDemandForListScreen(formSpecUniqueId,companyId);
	 }
	 
	 public WorkOnDemandMapping getWorkOnDemandForListScreen(String formSpecUniqueId, Integer companyId) {
			return  getExtendedDao().getWorkOnDemandForListScreen(formSpecUniqueId,companyId);
	 }
	 
	 public CustomEntityOnDemandMapping getCustomEntityOnDemandForListScreen(String formSpecUniqueId, Integer companyId) {
			return  getExtendedDao().getCustomEntityOnDemandForListScreen(formSpecUniqueId,companyId);
	 }
	 
	 
	 public EmployeeOnDemandMapping getEmployeeOnDemandForListScreen(String formSpecUniqueId, Integer companyId) {
			return  getExtendedDao().getEmployeeOnDemandForListScreen(formSpecUniqueId,companyId);
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

	 public boolean isRootEmployee(WebUser webUser) {
			Long rootEmpId = getRootEmployeeId(webUser);

			if (rootEmpId != null && webUser.getEmpId() != null
					&& webUser.getEmpId().longValue() == rootEmpId.longValue()) {
				return true;
			}

			return false;
		}
		public void updateEmployeeDeviceStartAndStopWorkTimes(long empId, String startTime, String stopTime) {
			try {
				employeeDao.updateEmployeeDeviceStartAndStopWorkTimes(empId, startTime, stopTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		public List<FormSectionField> getMasterFormSectionFieldsForForm(long formId) {
			return syncDao.getMasterFormSectionFieldsForForm(formId);
		}
		
	 public void insertStartWorkStopWorkLocations(StartWorkStopWorkLocations startWorkStopWorkLocation, long empId,
				int companyId) {
			getExtendedDao().insertStartWorkStopWorkLocations(startWorkStopWorkLocation, empId, companyId);
			try {
				employeeDao.updateEmployeeDeviceStartAndStopWorkTimes(empId,
						startWorkStopWorkLocation.getStartWorkLocationTime(),
						startWorkStopWorkLocation.getStopWorkLocationTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 public boolean reqTriggerStatus(String formSpecUniqueId,Integer companyId,WebUser webUser) {
		 CustomerOnDemandMapping customerReqForStatus = getCustomerOnDemandForListScreen(formSpecUniqueId,companyId);
			WorkOnDemandMapping workReqForStatus = getWorkOnDemandForListScreen(formSpecUniqueId,companyId);
			CustomEntityOnDemandMapping customEntityReqForStatus = getCustomEntityOnDemandForListScreen(formSpecUniqueId,companyId);
			EmployeeOnDemandMapping employeeReqForStatus = getEmployeeOnDemandForListScreen(formSpecUniqueId,companyId);

			boolean isRootEmployee = webManager.isRootEmployee(webUser);

			boolean onsubmitApproval = false;
			if((customerReqForStatus != null && customerReqForStatus.getTriggerEvent() == 2) || isRootEmployee) {
				onsubmitApproval = true;
			}
			if((workReqForStatus != null && workReqForStatus.getTriggerEvent() == 2) || isRootEmployee) {
				onsubmitApproval = true;
			}
			if((customEntityReqForStatus != null && customEntityReqForStatus.getTriggerEvent() == 2) || isRootEmployee) {
				onsubmitApproval = true;
			}
			if((employeeReqForStatus != null && employeeReqForStatus.getTriggerEvent() == 2) || isRootEmployee) {
				onsubmitApproval = true;
			}
			return onsubmitApproval;
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
	 
	 
	 
	 public void defaultFields(List<FormField> fields,
				List<FormSectionField> sectionFields, long formSpecId, WebUser webUser,boolean addWorkTroughBulkUpload,boolean sendWorksBasedOnStartTime) {

			Map<String, Object> values = new HashMap<String, Object>();
			Map<Long, FormFieldSpec> formFieldSpecMap = new HashMap<Long, FormFieldSpec>();
			Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap = new HashMap<Long, FormSectionFieldSpec>();
			Map<Long, List<EntityField>> entityAndFieldsMap = new HashMap<Long, List<EntityField>>();

			List<Long> entitySpecsIds =  webManager.getEntitySpecIdsForFormSpec("" + formSpecId);
			Map<Long, EntityFieldSpec> entityFieldSpecMap =webManager.getEntityFieldSpecMap(Api
					.toCSV(entitySpecsIds));
			
			List<Employee> accessibleEmployees = getContextUtils().getEmployeesUnderMe(true, true, null, webUser);
			List<Employee> allEmployees = new ArrayList<Employee>(accessibleEmployees);
			getWebSupportManager().resolveWorkReassignmentRights(allEmployees, webUser);
			
			List<String> accessibleEmployeeIds = Api.listToList(accessibleEmployees, "empId");
			
			List<FormFieldSpec> customerUserDefinedFields = getWebExtraManager().getCustomerUserDefinedFieldsForComputation(webUser);
			List<FormFieldSpec> employeeUserDefinedFields = getWebAdditionalSupportManager().getEmployeeUserDefinedFieldsForComputation(webUser);
			
			List<CustomEntitySpec> customEntitySpecs = getWebAdditionalSupportManager().getCustomEntitySpecs(webUser.getCompanyId());
			Map<Long,List<FormFieldSpec>> customEntitySpecIdAndFormFieldSpesMap = 
					getWebAdditionalSupportManager().getCustomEntitySpecIdAndFormFieldSpesMap(customEntitySpecs);

			try {
					List<FormFieldSpec> formFieldSpecs = webManager.getFormFieldSpecs(formSpecId);
					webManager.resolveFieldsWithExtraProperties(formFieldSpecs, ""+ formSpecId);

					List<FormSectionSpec> formSectionSpecs = webManager.getFormSectionSpecs(formSpecId);
					webManager.resolveSectionFieldsWithExtraProperties(formSectionSpecs, "" + formSpecId);
		
					if (fields != null) {
						for (FormFieldSpec formFieldSpec : formFieldSpecs) {
							formFieldSpecMap.put(formFieldSpec.getFieldSpecId(),
									formFieldSpec);
						}
					}
		
					if (sectionFields != null) {
						
						for (FormSectionSpec formSectionSpec : formSectionSpecs) {
							List<FormSectionFieldSpec> formSectionFieldSpecs = formSectionSpec
									.getFormSectionFieldSpecs();
							for (FormSectionFieldSpec formSectionFieldSpec : formSectionFieldSpecs) {
								formSectionFieldSpecMap.put(
										formSectionFieldSpec.getSectionFieldSpecId(),
										formSectionFieldSpec);
							}
						}
					}
		
					List<Long> entityIds = new ArrayList<Long>();
					List<Employee> allEligibleEmps = new ArrayList<Employee>(allEmployees);
					if (fields != null) {
						for (FormField formField : fields) {
							FormFieldSpec formFieldSpec = formFieldSpecMap
									.get(formField.getFieldSpecId());
							String fieldSpecId = "F"+formFieldSpec.getFieldSpecId();
							allEligibleEmps = getWebAdditionalSupportManager().resolveEmployeesForComputedFields(allEligibleEmps, webUser,formSpecId+"",fieldSpecId,"false");
							if (formFieldSpec!=null && formFieldSpec.getFieldType() == 14) {
								if (Api.isNumber(formField.getFieldValue())) {
									Double fieldValue1 = (Double.parseDouble(formField.getFieldValue()));
									formField.setFieldValue(fieldValue1.longValue()+"");
									entityIds.add(Long.parseLong(formField
											.getFieldValue()));
								}
		
							}
						}
					}
					
					if (sectionFields != null) {
						for (FormSectionField formSectionField : sectionFields) {
							FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
									.get(formSectionField.getSectionFieldSpecId());
							if(formSectionFieldSpec != null)
							{ 
								String fieldSpecId = "SF"+formSectionFieldSpec.getSectionFieldSpecId();
								allEligibleEmps = getWebAdditionalSupportManager().resolveEmployeesForComputedFields(allEligibleEmps, webUser,formSpecId+"",fieldSpecId,"true");
								if (formSectionFieldSpec!=null && formSectionFieldSpec.getFieldType() == 14) {
									if (Api.isNumber(formSectionField.getFieldValue())) {
										Double fieldValue1 = (Double.parseDouble(formSectionField.getFieldValue()));
										formSectionField.setFieldValue(fieldValue1.longValue()+"");
										entityIds.add(Long.parseLong(formSectionField
												.getFieldValue()));
									}
			
								}
								
							}
							
						}
					}
		
					List<EntityField> entityFields = extraDao
							.getEntityFieldByEntityIn(Api.toCSV(entityIds));
					webManager.populateEntityAndFieldsMap(entityAndFieldsMap, entityFields);
		
					if (fields != null) {
						Iterator<FormField> fieldsIterator = fields.iterator();
						while (fieldsIterator.hasNext()) {
							FormField formField = (FormField) fieldsIterator
									.next();
							FormFieldSpec formFieldSpec = formFieldSpecMap
									.get(formField.getFieldSpecId());
							
							if(formFieldSpec!=null && Api.isAutoComputedEnabledDataType(formFieldSpec.getFieldType())){
				
								String id = formFieldSpec.getExpression();
								String value = formField.getFieldValue();
				
								
								if (formFieldSpec.getFieldType() == 11) {
									value = getWebExtraManager().getValueOfTimeField(value);
								}
								if (formFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_DURATION) {
									value = Api.durationToSeconds(value)+"";
								}
								else if (formFieldSpec.getFieldType() == 14) {
									webManager.populateValuesWithEntityValues(values,
											entityAndFieldsMap, entityFieldSpecMap,
											id, value, null, accessibleEmployeeIds, webUser);
									continue;
								}
								else if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE) {
									getWebExtraManager().populateEmployeeFields(values,id, value, null, accessibleEmployeeIds, webUser,employeeUserDefinedFields,allEligibleEmps);
									continue;
								}
								else if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER) {
									getWebExtraManager().populateCustomerFields(values,id, value, null, accessibleEmployeeIds, webUser, customerUserDefinedFields);
									continue;
								}
								else if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOM_ENTITY) {
									getWebAdditionalSupportManager().populateCustomEntityFields(values,id, value, null, accessibleEmployeeIds, webUser, customEntitySpecIdAndFormFieldSpesMap);
									continue;
								}
							     if(Api.isAutoComputedEnabledNumericDataType(formFieldSpec.getFieldType())){
									
									if(Api.isNumber(value))
										values.put(id, Double.parseDouble(value));
									else
										values.put(id, value);
								  }else{
									  values.put(id, value);
								  }
							}
						}
					}
					
					if (sectionFields != null) {
						Iterator<FormSectionField> sectionFieldsIterator = sectionFields.iterator();
						while (sectionFieldsIterator.hasNext()) {
							FormSectionField formField = (FormSectionField) sectionFieldsIterator
									.next();
							FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
									.get(formField.getSectionFieldSpecId());
				
						   if(formSectionFieldSpec!=null && Api.isAutoComputedEnabledDataType(formSectionFieldSpec.getFieldType())){
				
								String id = formSectionFieldSpec.getExpression();
								String value = formField.getFieldValue();
											
								if (formSectionFieldSpec.getFieldType() == 11) {
									value = getWebExtraManager().getValueOfTimeField(value);
								}
								if (formSectionFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_DURATION) {
									value = Api.durationToSeconds(value)+"";
								}
								else if (formSectionFieldSpec.getFieldType() == 14) {
									webManager.populateValuesWithEntityValues(values,
											entityAndFieldsMap, entityFieldSpecMap,
											id, value,
											formField.getInstanceId(), accessibleEmployeeIds, webUser);
									continue;
								}
								else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE) {
									getWebExtraManager().populateEmployeeFields(values,id, value, formField.getInstanceId(), accessibleEmployeeIds, webUser,employeeUserDefinedFields,allEligibleEmps);
									continue;
								}
								else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER) {
									getWebExtraManager().populateCustomerFields(values,id, value, formField.getInstanceId()+"", accessibleEmployeeIds, webUser, customerUserDefinedFields);
									continue;
								}
								else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOM_ENTITY) {
									getWebAdditionalSupportManager().populateCustomEntityFields(values,id, value, formField.getInstanceId()+"", accessibleEmployeeIds, webUser, customEntitySpecIdAndFormFieldSpesMap);
									continue;
								}
				
								id = id + "[" + formField.getInstanceId()
										+ "]";
								if(Api.isAutoComputedEnabledNumericDataType(formSectionFieldSpec.getFieldType())){
									 
									webManager.populateValueInMap(id, value,values);
								  }else{
									  webManager.populateValueInObjectMap(id, value,values);
								  }
							}
						}
					}
		
					if (fields != null) {
						Iterator<FormField> fieldsIterator = fields.iterator();
						while (fieldsIterator.hasNext()) {
							FormField formField = (FormField) fieldsIterator
									.next();
							FormFieldSpec formFieldSpec = formFieldSpecMap
									.get(formField.getFieldSpecId());
							if(formFieldSpec!=null)
							{
								int type = formFieldSpec.getComputedFieldType();
								if (formFieldSpec.isDefaultField()
										&& type == FormFieldSpec.COMPUTED_FIELD_TYPE_DEFAULT) {
									
									String fieldValue = null;
									Double value = null;
									String formula = formFieldSpec.getFormula();
									int instanceId = 1;
									int fieldType = formFieldSpec.getFieldType();
									String expression =  formFieldSpec.getExpression();
									Long skeletonFormFieldSpecId = formFieldSpec.getSkeletonFormFieldSpecId();
									
									if(fieldType == Constants.FORM_FIELD_TYPE_DATE_TIME)
									{
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										String currentUTCTime = Api.getDateTimeInUTC(new Date(System.currentTimeMillis()));
										String localTime = Api.addTzoAndGetDateTime(currentUTCTime, webUser.getTzo()+"", "yyyy-MM-dd HH:mm:ss");        
										values.put("now",localTime);	
									}
									else if(fieldType == Constants.FORM_FIELD_TYPE_TIME)
									{
										SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
										values.put("now", sdf.format(new Date(System.currentTimeMillis())));
									}
									else if(fieldType == Constants.FORM_FIELD_TYPE_DATE)
									{
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										values.put("today", sdf.format(new Date(System.currentTimeMillis())));
									}
									
									String filledByKey = "E1009_E25";
									String  staticValue = "";
									if (formula.equals(filledByKey)) 
									{
										filledByKey = filledByKey + "[" + instanceId + "]";
										String filledByEmp = (String) values.get(expression);
										if (Api.isEmptyString(filledByEmp)) {
											staticValue = webUser.getEmpId() + "";
										} else {
											staticValue = filledByEmp;
										}
										
										webManager.populateValueInMap(filledByKey,
												staticValue, values);
									}
									getWebAdditionalSupportManager().populateEmployeeFieldsForFilledBy(values,
											null,accessibleEmployeeIds, webUser, employeeUserDefinedFields,allEligibleEmps);
									Object valueObj = null;
									if(formFieldSpec.getFieldType() ==1 && formFieldSpec.getEnableUserInputsForTextFieldFormula()!=null && formFieldSpec.getEnableUserInputsForTextFieldFormula()==1) {
										valueObj = formFieldSpec.getFormula();
									}else {
									 valueObj = getWebExtraManager().
											getValueForFormula(formula, instanceId, values,fieldType,FormFieldWraper.TYPE_FIELD);
									}
									if (constants.getSkeletonWorkStartsFieldSpecId().equals(skeletonFormFieldSpecId) && addWorkTroughBulkUpload && sendWorksBasedOnStartTime) {
		                                 String startDateFieldValue = formField.getFieldValue();
		                                 if(!Api.isEmptyString(startDateFieldValue)) {
		                                	 valueObj = startDateFieldValue;
		                                 }else {
		 									Log.info(getClass(), "computeFields() // StartDateTime Before Update " + valueObj);
											String updateStartDateTime = Api.addTimeToDateTime(valueObj.toString(),Long.valueOf(1));
											valueObj = updateStartDateTime;
											Log.info(getClass(), "computeFields() // StartDateTime After Update " + valueObj);
			                                 }
									}
									if(Api.isNumber(valueObj)){
										
									   if(Api.isAutoComputedEnabledNumericDataType(formFieldSpec.getFieldType())){ 	
									    	value =  Double.parseDouble(valueObj.toString());
									        if(formFieldSpec.getDecimalValueLimit() != null && formFieldSpec.getDecimalValueLimit().intValue() > 0){
									        	fieldValue = Api.roundToString(value.doubleValue(), formFieldSpec.getDecimalValueLimit().intValue());
									        }else{
									        	fieldValue = Api.roundToString(value.doubleValue(), 2);
									        }
									    	
									    }
									   else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_PHONE)
										{
											try {
											value =  Double.parseDouble(valueObj.toString());
											fieldValue = Api.roundToString(value.doubleValue(), 0);
											}catch(Exception e) {
												Log.info(getClass(), "Got Exception while parsing"+e);
											}
											
											 try {
													if (!Api.isEmptyString(constantsExtra.getMaruthiCallCenterFieldSpecUniqueId())
															&& !Api.isEmptyString(formFieldSpec.getUniqueId())
															&& formFieldSpec.getUniqueId().equals(
																	constantsExtra.getMaruthiCallCenterFieldSpecUniqueId())) {
														fieldValue = formula;
													}

												} catch (Exception e) {
												}
											
										}
									   else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TIME){
										   if(valueObj != null)
										   {
											   value =  Double.parseDouble(valueObj.toString());
											   fieldValue = Api.round(value.doubleValue(), 2)+"";
										   }
									    }
									   else if(formFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_DURATION)
										{
										   fieldValue = secondsToDurationForDurationDataType(valueObj.toString()+"");
										}
									   else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_LIST
												|| formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_FORM
												|| formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER
												|| formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE
												|| formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER_TYPE
												|| formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TERRITORY
												|| formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOM_ENTITY)
										{
											Double fieldValue1 = (Double.parseDouble(valueObj.toString()));
											fieldValue = fieldValue1.longValue()+"";
											
										}
									    else
									    	fieldValue = valueObj.toString();
									}else{
										if(!Api.isEmptyObj(valueObj))
										   fieldValue = valueObj.toString();
									}
									
									if (fieldValue != null) {
										formField.setFieldValue(fieldValue);
										values.put(expression, fieldValue);
										if(Api.isAutoComputedEnabledNumericDataType(formFieldSpec.getFieldType()) && Api.isNumber(fieldValue))
										   values.put(expression, Double.parseDouble(fieldValue));

									}
									
									if (value != null) {
										if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TIMESPAN
												|| formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TIME)
										{
											formField.setFieldValue(Api.secondsToTime(value));
										}
										else if(formFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_DURATION)
										{
											formField.setFieldValue(Api.secondsToDuration(value+""));
										}
										else
										{
											
											if(formFieldSpec.getFieldType() == 16){
												if(formFieldSpec.getDecimalValueLimit() != null && formFieldSpec.getDecimalValueLimit().intValue() > 0){
													formField.setFieldValue(Api.roundToString(value.doubleValue(), formFieldSpec.getDecimalValueLimit().intValue()));
												}else{
													formField.setFieldValue(String.format("%.2f",(value.doubleValue())));
												}
												
											
										} else{
											if(formFieldSpec.getDecimalValueLimit() != null && formFieldSpec.getDecimalValueLimit().intValue() > 0){
												formField.setFieldValue(Api.roundToString(value.doubleValue(), formFieldSpec.getDecimalValueLimit().intValue()));
											}else{
												
												formField.setFieldValue(Api.roundToString(
														value.doubleValue(), 2));
												 try {
														if (!Api.isEmptyString(constantsExtra.getMaruthiCallCenterFieldSpecUniqueId())
																&& !Api.isEmptyString(formFieldSpec.getUniqueId())
																&& formFieldSpec.getUniqueId().equals(
																		constantsExtra.getMaruthiCallCenterFieldSpecUniqueId())) {
															formField.setFieldValue(formula);
														}

													} catch (Exception e) {
													}
											}
											
										}
											
										}
									}
								}
								
								if(Api.isNumber(formField.getFieldValue()) && formFieldSpec.getFieldType() != Constants.FORM_FIELD_TYPE_CURRENCY)
								{
									String[] arr = formField.getFieldValue().split("\\.");
									if(arr.length >= 2 &&
											("0".equalsIgnoreCase(arr[1]) || "00".equalsIgnoreCase(arr[1])))
									{
										formField.setFieldValue(arr[0]);
									}
								}
							}
						}
				}
				
				if (sectionFields != null) {
					Iterator<FormSectionField>	sectionFieldsIterator = sectionFields.iterator();
					while (sectionFieldsIterator.hasNext()) {
				
							FormSectionField formSectionField = (FormSectionField) sectionFieldsIterator
									.next();
							FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
									.get(formSectionField.getSectionFieldSpecId());
							if(formSectionFieldSpec!=null)
							{
								int type = formSectionFieldSpec.getComputedFieldType();
								if (formSectionFieldSpec.isDefaultField()
										&& type == FormSectionFieldSpec.COMPUTED_FIELD_TYPE_DEFAULT) {
									 
									String fieldValue = null;
									Double value = null;
									String formula = formSectionFieldSpec.getFormula();
									int instanceId = formSectionField.getInstanceId();
									int fieldType = formSectionFieldSpec.getFieldType();
									
									if(fieldType == Constants.FORM_FIELD_TYPE_DATE_TIME)
									{
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										String currentUTCTime = Api.getDateTimeInUTC(new Date(System.currentTimeMillis()));
										String localTime = Api.addTzoAndGetDateTime(currentUTCTime, webUser.getTzo()+"", "yyyy-MM-dd HH:mm:ss");        
										values.put("now",localTime);	
									}
									else if(fieldType == Constants.FORM_FIELD_TYPE_TIME)
									{
										SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
										values.put("now", sdf.format(new Date(System.currentTimeMillis())));
									}
									else if(fieldType == Constants.FORM_FIELD_TYPE_DATE)
									{
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										values.put("today", sdf.format(new Date(System.currentTimeMillis())));
									}
									getWebAdditionalSupportManager().populateEmployeeFieldsForFilledBy(values,
											instanceId,accessibleEmployeeIds, webUser, employeeUserDefinedFields,allEligibleEmps);
									Object valueObj = null;
									if(formSectionFieldSpec.getFieldType()==1 && formSectionFieldSpec.getEnableUserInputsForTextFieldFormula()!=null && formSectionFieldSpec.getEnableUserInputsForTextFieldFormula()==1) {
										valueObj = formSectionFieldSpec.getFormula();
									}else {
									 valueObj = getWebExtraManager().
											getValueForFormula(formula, instanceId, values,fieldType,FormFieldWraper.TYPE_SECTION_FIELD);
									}
									
									if(Api.isNumber(valueObj)){
										
										   if(Api.isAutoComputedEnabledNumericDataType(formSectionFieldSpec.getFieldType())){ 	
										    	value =  Double.parseDouble(valueObj.toString());
										    	if(formSectionFieldSpec.getDecimalValueLimit() != null && formSectionFieldSpec.getDecimalValueLimit().intValue() > 0){
										    		fieldValue =  Api.roundToString(value.doubleValue(), formSectionFieldSpec.getDecimalValueLimit().intValue());
												}else{
													fieldValue = Api.roundToString(value.doubleValue(), 2);
												}
										    	
										    }
										   else if(formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_PHONE)
											{
												try {
												value =  Double.parseDouble(valueObj.toString());
												fieldValue = Api.roundToString(value.doubleValue(), 0);
												}catch(Exception e) {
													Log.info(getClass(), "Got Exception while parsing"+e);
												}
											}
										   else if(formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TIME){
											   if(valueObj != null)
											   {
												   value =  Double.parseDouble(valueObj.toString());
												   fieldValue = Api.round(value.doubleValue(), 2)+"";
											   }
										    }
										   else if(formSectionFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_DURATION)
		                                   {
		                                      fieldValue = secondsToDurationForDurationDataType(valueObj.toString()+"");
		                                   }
										   else if(formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_LIST
													|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_FORM
													|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER
													|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE
													|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER_TYPE
													|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TERRITORY
													|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOM_ENTITY)
											{
												Double fieldValue1 = (Double.parseDouble(valueObj.toString()));
												fieldValue = fieldValue1.longValue()+"";
												
											}
										    else
										    	fieldValue = valueObj.toString();
										}else{
										if(!Api.isEmptyObj(valueObj))
											fieldValue = valueObj.toString();
									}
									
									if (fieldValue != null) {
										formSectionField.setFieldValue(fieldValue);
									}
									
									if (value != null) {
										if(formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TIMESPAN
												|| formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_TIME)
										{
											formSectionField.setFieldValue(Api.secondsToTime(value));
										}
										else if(formSectionFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_DURATION)
		                                {
											formSectionField.setFieldValue(Api.secondsToDuration(value+""));
		                                }
										else
										{
											
											if(formSectionFieldSpec.getFieldType() == 16){
												if(formSectionFieldSpec.getDecimalValueLimit() != null && formSectionFieldSpec.getDecimalValueLimit().intValue() > 0){
													formSectionField.setFieldValue(Api.roundToString(value.doubleValue(), formSectionFieldSpec.getDecimalValueLimit().intValue()));
												}else{
													formSectionField.setFieldValue(String.format("%.2f",(value.doubleValue())));
												}
												
										} else{
											if(formSectionFieldSpec.getDecimalValueLimit() != null && formSectionFieldSpec.getDecimalValueLimit().intValue() > 0){
												formSectionField.setFieldValue(Api.roundToString(value.doubleValue(), formSectionFieldSpec.getDecimalValueLimit().intValue()));
											}else{
												formSectionField.setFieldValue(Api.roundToString(value.doubleValue(), 2));
											}
											
										}
											
										}
									}
									
									if(Api.isNumber(formSectionField.getFieldValue()) && formSectionFieldSpec.getFieldType() != Constants.FORM_FIELD_TYPE_CURRENCY)
									{
										String[] arr = formSectionField.getFieldValue().split("\\.");
										if(arr.length >= 2 &&
												("0".equalsIgnoreCase(arr[1]) || "00".equalsIgnoreCase(arr[1])))
										{
											formSectionField.setFieldValue(arr[0]);
										}
									}
								}
							}
					}
				}
					

			} catch (NumberFormatException e) {
				Log.info(this.getClass(), e.toString(), e);
			}
		}
	 
	 public void updateWorkLocation(String workId, String location, Long empId,Work work) {
			if(work.isFromMigrationFields()) {
				getExtendedDao().updateWorkLocationByUsingWork(workId,location,empId,work);
			}else {
			getExtendedDao().updateWorkLocation(workId,location,empId);
			}
		}
	 public void resolveWorkExtensionData(Work work, FormAndField formAndField) {
			//in future if we have multiple columns in this table then accordingly will change the insertion logic. Right now there's only one column, so to avoid null record condition included.
			if(work.getWorkId() != 0) {
				String workStateTypeEntityId = work.getWorkStateTypeEntityId() == null ? 
						webManager.getWorkFieldValueForGivenSkeleton(formAndField,constantsExtra.getSkeletonWorkStateTypeFieldSpecId()) : work.getWorkStateTypeEntityId()+"";
				if(!Api.isEmptyString(workStateTypeEntityId)){
					work.setWorkStateTypeEntityId(Long.parseLong(workStateTypeEntityId));			
				}
				updateWorkExtensionData(work);
			}
		}
	 
		public void updateWorkExtensionData(Work work) {
			getExtraSupportAdditionalSupportDao().deleteWorkExtensionData(work.getWorkId());
			if(work.getWorkStateTypeEntityId() != null && work.getWorkStateTypeEntityId() != -1){
				getExtraSupportAdditionalSupportDao().insertWorkExtensionData(work);
			}
		}
	 public void autoSignOutBasedOnForm(ExtraProperties extraProperties,WebUser webUser,long saveLocationId,Form form) {
			
			String signOutCondition = constants.getSignOutCondition(webUser.getEmpId());
			if(extraProperties!= null && extraProperties.getIsWebLiteSignIn()) {
				 if(saveLocationId > 0) {
						String startTime = Api.getDateTimeInUTC(new Date(System.currentTimeMillis()));
						StartWorkStopWorkLocations startWorkStopWorkLocation = new StartWorkStopWorkLocations();
						startWorkStopWorkLocation.setStartWorkLocationId(saveLocationId+"");
						startWorkStopWorkLocation.setSignInFormId(form.getFormId());
						startWorkStopWorkLocation.setSignInSource(StartWorkStopWorkLocations.SIGNED_IN_WEBLITE);
						if(extraProperties.isAutoSignIn()) {
							StartWorkStopWorkLocations startLastWorkStopWorkLocationFromDB = getExtendedDao().getLastStartWorkStopWorkLocationsForEmpIdAndCustId(webUser.getEmpId());
							Employee signInmployee = getWebAdditionalSupportExtraManager()
									.getEmployeeBasicDetailsByEmpId(startLastWorkStopWorkLocationFromDB.getEmpId());
							if ("4".equalsIgnoreCase(signOutCondition)) {
								String startWorkDateTime = "";
								Api.DateType dateType = Api.DateType.DAY;
								String StartWorkDate = Api
										.getOnlyDateInStandardFormat(startLastWorkStopWorkLocationFromDB.getStartWorkLocationTime());
								Integer day = Api.getGivenFieldFromDateAndTime(StartWorkDate, dateType);
								List<WorkingHour> workingHours = extraDao.getWorkingHours(signInmployee.getCalendarId(), day);
								if (workingHours == null || workingHours.isEmpty()) {
									workingHours = extraSupportAdditionalDao
											.getSpecialWorkingHoursForGivenDate(signInmployee.getCalendarId(), StartWorkDate);
								}
								if (workingHours != null && !workingHours.isEmpty()) {
									WorkingHour workingHour = workingHours.get(0);
									if (!Api.isEmptyString(workingHour.getEndTime())) {
										String endTimestring = workingHour.getEndTime().substring(0,
												workingHour.getEndTime().length() - 1);
										startWorkDateTime = StartWorkDate + " " + endTimestring;
									}
								}
								startWorkStopWorkLocation.setStartWorkLocationTime(startWorkDateTime);
								startWorkStopWorkLocation.setAutoStartWork(1);
							}else if ("5".equalsIgnoreCase(signOutCondition)) {
								String startWorkDate = Api
										.getOnlyDateInStandardFormat(startLastWorkStopWorkLocationFromDB.getStartWorkLocationTime());
								String stopWorkDateTime = startWorkDate + " 23:59:59";
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								 LocalDate date = LocalDate.parse(startWorkDate, formatter);
								 LocalDate newDate = date.plusDays(1);
								  // Format the new date as a string
								String startWorkDate1 = newDate.format(formatter);
								String autoStartWork = startWorkDate1 + " 00:00:00";
								String dateTimeInUTC = null;
								try {
									dateTimeInUTC = Api.getDateTimeInTzToUtc(Api.getCalendar(Api.getDateTimeInUTC(autoStartWork)), webUser.getTzo()+"");
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								startWorkStopWorkLocation.setStartWorkLocationTime(dateTimeInUTC);
								startWorkStopWorkLocation.setAutoStartWork(1);
							}else {
								startWorkStopWorkLocation.setStartWorkLocationTime(startTime);
							}
						}else {
							startWorkStopWorkLocation.setStartWorkLocationTime(startTime);
						}
						insertStartWorkStopWorkLocations(startWorkStopWorkLocation,webUser.getEmpId(),webUser.getCompanyId());
					}
			}
			if(extraProperties!= null && extraProperties.getIsWebLiteSignOut()) {
				if(!extraProperties.isAutoSignIn()) {
				 if(saveLocationId > 0) {
						String startTime = Api.getDateTimeInUTC(new Date(System.currentTimeMillis()));
						StartWorkStopWorkLocations startWorkStopWorkLocation = new StartWorkStopWorkLocations();
						StartWorkStopWorkLocations startWorkLocation = new StartWorkStopWorkLocations();
						StartWorkStopWorkLocations startWorkStopWorkLocationFromDB = extraDao.getStartWorkStopWorkLocationsForEmpIdAndCustId(webUser.getEmpId());
						boolean isSignInFrom = false;
						if(startWorkStopWorkLocationFromDB != null) {
						Employee employee = getWebAdditionalSupportExtraManager()
									.getEmployeeBasicDetailsByEmpId(startWorkStopWorkLocationFromDB.getEmpId());
						startWorkStopWorkLocation.setStopWorkLocationId(saveLocationId+"");
						if (extraProperties.isAutoSignOut()) {
							if ("2".equalsIgnoreCase(signOutCondition)) {
								String StartWorkDateTime = "";
								Api.DateType dateType = Api.DateType.DAY;
								String StartWorkDate = Api
										.getOnlyDateInStandardFormat(startWorkStopWorkLocationFromDB.getStartWorkLocationTime());
								Integer day = Api.getGivenFieldFromDateAndTime(StartWorkDate, dateType);
								List<WorkingHour> workingHours = extraDao.getWorkingHours(employee.getCalendarId(), day);
								if (workingHours == null || workingHours.isEmpty()) {
									workingHours = extraSupportAdditionalDao
											.getSpecialWorkingHoursForGivenDate(employee.getCalendarId(), StartWorkDate);
								}
								if (workingHours != null && !workingHours.isEmpty()) {
									WorkingHour workingHour = workingHours.get(0);
									if (!Api.isEmptyString(workingHour.getEndTime())) {
										String endTimestring = workingHour.getEndTime().substring(0,
												workingHour.getEndTime().length() - 1);
										StartWorkDateTime = StartWorkDate + " " + endTimestring;
									}
								}
								startWorkStopWorkLocation.setStopWorkLocationTime(StartWorkDateTime);
							}else if ("3".equalsIgnoreCase(signOutCondition)) {
								String startWorkDate = Api
										.getOnlyDateInStandardFormat(startWorkStopWorkLocationFromDB.getStartWorkLocationTime());
								String stopWorkDateTime = startWorkDate + " 23:59:59";
								String dateTimeInUTC = "";
								try {
									dateTimeInUTC = Api.getDateTimeInTzToUtc(Api.getCalendar(Api.getDateTimeInUTC(stopWorkDateTime)), webUser.getTzo()+"");
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								startWorkStopWorkLocation.setStopWorkLocationTime(dateTimeInUTC);
							}else if ("4".equalsIgnoreCase(signOutCondition)) {
								String StartWorkDateTime = "";
								Api.DateType dateType = Api.DateType.DAY;
								String StartWorkDate = Api
										.getOnlyDateInStandardFormat(startWorkStopWorkLocationFromDB.getStartWorkLocationTime());
								Integer day = Api.getGivenFieldFromDateAndTime(StartWorkDate, dateType);
								List<WorkingHour> workingHours = extraDao.getWorkingHours(employee.getCalendarId(), day);
								if (workingHours == null || workingHours.isEmpty()) {
									workingHours = extraSupportAdditionalDao
											.getSpecialWorkingHoursForGivenDate(employee.getCalendarId(), StartWorkDate);
								}
								if (workingHours != null && !workingHours.isEmpty()) {
									WorkingHour workingHour = workingHours.get(0);
									if (!Api.isEmptyString(workingHour.getEndTime())) {
										String endTimestring = workingHour.getEndTime().substring(0,
												workingHour.getEndTime().length() - 1);
										StartWorkDateTime = StartWorkDate + " " + endTimestring;
									}
								}
								startWorkStopWorkLocation.setStopWorkLocationTime(StartWorkDateTime);
										CustomerActivityConfiguration signInActivityCong = getWebAdditionalManager().
									 	getSignInSignOutActivityConfigurations(employee.getAppId(),webUser.getCompanyId(),
									 	constants.getSignInActivitiyVisibilityKey());

									 	String signInFormSpecUniqueId = "";
										if (signInActivityCong != null) {
											if (!Api.isEmptyString(signInActivityCong.getActivityFormSpecUniqueId())
													&& !"-1".equals(signInActivityCong.getActivityFormSpecUniqueId())) {
											 	signInFormSpecUniqueId = signInActivityCong.getActivityFormSpecUniqueId();
												isSignInFrom = true;
											}else{
											 	signInFormSpecUniqueId = "-1";
										 	}
										}else{
										 	signInFormSpecUniqueId = "-1";
									 	}
										
										if(isSignInFrom) {
											FormSpec signInLatestFormSpec = null;
											if( !Api.isEmptyString(signInFormSpecUniqueId)) {
											 	 signInLatestFormSpec = webManager.getLatestPublishedFormSpec(signInFormSpecUniqueId);
										 	}
											extraProperties.setAutoSignInInWeblite(true);
											if(signInLatestFormSpec != null) {
											extraProperties.setAutoSignInFormSpecUniqueId(signInLatestFormSpec.getFormSpecId());
											}
										}
										if(!isSignInFrom) {
											startWorkLocation.setStartWorkLocationId(saveLocationId+"");
											startWorkLocation.setStartWorkLocationTime(StartWorkDateTime);
											startWorkLocation.setAutoStartWork(1);
											startWorkLocation.setSignInSource(StartWorkStopWorkLocations.SIGNED_IN_WEBLITE);
										}
							  
							}else if ("5".equalsIgnoreCase(signOutCondition)) {
								String startWorkDate = Api
										.getOnlyDateInStandardFormat(startWorkStopWorkLocationFromDB.getStartWorkLocationTime());
								String stopWorkDateTime = startWorkDate + " 23:59:59";
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								 LocalDate date = LocalDate.parse(startWorkDate, formatter);
								 LocalDate newDate = date.plusDays(1);
								  // Format the new date as a string
								String startWorkDate1 = newDate.format(formatter);
								String autoStartWork = startWorkDate1 + " 00:00:00";
								String autoStartWorkInUTC = null;
								try {
									autoStartWorkInUTC = Api.getDateTimeInTzToUtc(Api.getCalendar(Api.getDateTimeInUTC(autoStartWork)), webUser.getTzo()+"");
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String dateTimeInUTC = null;
								try {
									dateTimeInUTC = Api.getDateTimeInTzToUtc(Api.getCalendar(Api.getDateTimeInUTC(stopWorkDateTime)), webUser.getTzo()+"");
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								startWorkStopWorkLocation.setStopWorkLocationTime(dateTimeInUTC);
							
								CustomerActivityConfiguration signInActivityCong = getWebAdditionalManager().
									 	getSignInSignOutActivityConfigurations(employee.getAppId(),webUser.getCompanyId(),
									 	constants.getSignInActivitiyVisibilityKey());

									 	String signInFormSpecUniqueId = "";
										if (signInActivityCong != null) {
											if (!Api.isEmptyString(signInActivityCong.getActivityFormSpecUniqueId())
													&& !"-1".equals(signInActivityCong.getActivityFormSpecUniqueId())) {
											 	signInFormSpecUniqueId = signInActivityCong.getActivityFormSpecUniqueId();
												isSignInFrom = true;
											}else{
											 	signInFormSpecUniqueId = "-1";
										 	}
										}else{
										 	signInFormSpecUniqueId = "-1";
									 	}
										
										if(isSignInFrom) {
											FormSpec signInLatestFormSpec = null;
											if( !Api.isEmptyString(signInFormSpecUniqueId)) {
											 	 signInLatestFormSpec = webManager.getLatestPublishedFormSpec(signInFormSpecUniqueId);
										 	}
											extraProperties.setAutoSignInInWeblite(true);
											if(signInLatestFormSpec != null) {
											extraProperties.setAutoSignInFormSpecUniqueId(signInLatestFormSpec.getFormSpecId());
											}
										}
										if(!isSignInFrom) {
											startWorkLocation.setStartWorkLocationId(saveLocationId+"");
											startWorkLocation.setStartWorkLocationTime(autoStartWorkInUTC);
											startWorkLocation.setAutoStartWork(1);
											startWorkLocation.setSignInSource(StartWorkStopWorkLocations.SIGNED_IN_WEBLITE);
										}
							}else if ("6".equalsIgnoreCase(signOutCondition)) {
									Api.DateType dateType = Api.DateType.DAY;
									String StartWorkDate = Api
											.getOnlyDateInStandardFormat(startWorkStopWorkLocationFromDB.getStartWorkLocationTime());
									Integer day = Api.getGivenFieldFromDateAndTime(StartWorkDate, dateType);
									List<WorkingHour> workingHours = extraDao.getWorkingHours(employee.getCalendarId(), day);
									if (workingHours == null || workingHours.isEmpty()) {
										workingHours = extraSupportAdditionalDao
												.getSpecialWorkingHoursForGivenDate(employee.getCalendarId(), StartWorkDate);
									}
									if (workingHours != null && !workingHours.isEmpty()) {
										WorkingHour workingHour = workingHours.get(0);
										if (!Api.isEmptyString(workingHour.getEndTime())) {
											String endTimestring = workingHour.getEndTime().substring(0,
													workingHour.getEndTime().length() - 1);
											String StartWorkDateTime = StartWorkDate + " " + endTimestring;
											startWorkStopWorkLocation.setStopWorkLocationTime(StartWorkDateTime);
										}
									}
									String startWorkDateTime = Api
											.getOnlyDateInStandardFormat(startWorkStopWorkLocationFromDB.getStartWorkLocationTime());
									 String startWorkFromDate = startWorkDateTime + " 00:00:00";
									 String startWorkToDate = startWorkDateTime + " 23:59:59"; 
									StartWorkStopWorkLocations autoStartWorkStopWorkLocation = getExtendedDao().getTodayStartWorkToStopWork(employee.getEmpId(),startWorkFromDate,startWorkToDate,webUser.getTzo());
									
									if(!Api.isEmptyObj(autoStartWorkStopWorkLocation)) {
										Long stopWorkTimeOutMillis = Api.getDateTimeInMillies(autoStartWorkStopWorkLocation.getStopWorkLocationTime());
										Long startWorkTimeOutMillis = Api.getDateTimeInMillies(startWorkStopWorkLocationFromDB.getStartWorkLocationTime());
										if(startWorkTimeOutMillis > stopWorkTimeOutMillis) {
											String startWorkDate = Api
													.getOnlyDateInStandardFormat(startWorkStopWorkLocationFromDB.getStartWorkLocationTime());
											String stopWorkDateTime = startWorkDate + " 23:59:59";
											Long startTimeOutMillis1 = Api.getDateTimeInMillies(stopWorkDateTime);
											String currentDateTime = Api.getDateTimeInUTC(new Date(System.currentTimeMillis()));
											Long currentDateTimeMillis = Api.getDateTimeInMillies(currentDateTime);
											String dateTimeInUTC = "";
											try {
												dateTimeInUTC = Api.getDateTimeInTzToUtc(Api.getCalendar(Api.getDateTimeInUTC(stopWorkDateTime)), webUser.getTzo()+"");
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											if (currentDateTimeMillis > startTimeOutMillis1) {
												startWorkStopWorkLocation.setStopWorkLocationTime(dateTimeInUTC);
											}
										}
									 }
								}else if ("7".equalsIgnoreCase(signOutCondition)) {
								int signOutConditionDurationInHours = constantsExtra.getSignOutConditionDurationInHours(employee.getEmpId());
								String StartWorkDate = startWorkStopWorkLocationFromDB.getStartWorkLocationTime();
								Date date = Api.getDateFromString(StartWorkDate);
								Date addHoursToJavaUtilDate = Api.addHoursToJavaUtilDate(date, signOutConditionDurationInHours);
								startWorkStopWorkLocation.setStopWorkLocationTime(Api.getDateToString(addHoursToJavaUtilDate));
								
								String startWorkDateTime = Api
										.getOnlyDateInStandardFormat(startWorkStopWorkLocationFromDB.getStartWorkLocationTime());
								 String startWorkFromDate = startWorkDateTime + " 00:00:00";
								 String startWorkToDate = startWorkDateTime + " 23:59:59"; 
								StartWorkStopWorkLocations autoStartWorkStopWorkLocation = getExtendedDao().getTodayStartWorkToStopWork(employee.getEmpId(),startWorkFromDate,startWorkToDate,webUser.getTzo());
								
								if(!Api.isEmptyObj(autoStartWorkStopWorkLocation)) {
								Long stopWorkTimeOutMillis = Api.getDateTimeInMillies(autoStartWorkStopWorkLocation.getStopWorkLocationTime());
								Long startWorkTimeOutMillis = Api.getDateTimeInMillies(startWorkStopWorkLocationFromDB.getStartWorkLocationTime());
								if(startWorkTimeOutMillis > stopWorkTimeOutMillis) {
									String startWorkDate = Api
											.getOnlyDateInStandardFormat(startWorkStopWorkLocationFromDB.getStartWorkLocationTime());
									String stopWorkDateTime = startWorkDate + " 23:59:59";
									Long startTimeOutMillis1 = Api.getDateTimeInMillies(stopWorkDateTime);
									String currentDateTime = Api.getDateTimeInUTC(new Date(System.currentTimeMillis()));
									Long currentDateTimeMillis = Api.getDateTimeInMillies(currentDateTime);
									String dateTimeInUTC = "";
									try {
										dateTimeInUTC = Api.getDateTimeInTzToUtc(Api.getCalendar(Api.getDateTimeInUTC(stopWorkDateTime)), webUser.getTzo()+"");
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									if (currentDateTimeMillis > startTimeOutMillis1) {
										startWorkStopWorkLocation.setStopWorkLocationTime(dateTimeInUTC);
									}
								}
							  }
							}else {
								startWorkStopWorkLocation.setStopWorkLocationTime(startTime);
							}
						}else {
							startWorkStopWorkLocation.setStopWorkLocationId(saveLocationId+"");
							startWorkStopWorkLocation.setStopWorkLocationTime(startTime);
						}
						startWorkStopWorkLocation.setSignOutFormId(form.getFormId());
						startWorkStopWorkLocation.setSignOutSource(StartWorkStopWorkLocations.SIGNOUT_IN_WEBLITE);
						if(extraProperties.isAutoSignOut()) {
						startWorkStopWorkLocation.setAutoStopWork(1);
						}else {
							startWorkStopWorkLocation.setAutoStopWork(0);
						}
						getExtendedDao().updateStopWorkLocationForStartWorkLocation(startWorkStopWorkLocation,startWorkStopWorkLocationFromDB.getId());
						if(!Api.isEmptyString(startWorkStopWorkLocation.getStartWorkLocationTime())) {
						employeeDao.updateEmployeeDeviceStartAndStopWorkTimes(employee.getEmpId(),
								startWorkStopWorkLocation.getStartWorkLocationTime(),
								startWorkStopWorkLocation.getStopWorkLocationTime());
						}
						if ("4".equalsIgnoreCase(signOutCondition) || "5".equalsIgnoreCase(signOutCondition)) {
							if (!isSignInFrom) {
								insertStartWorkStopWorkLocations(startWorkLocation,
										employee.getEmpId(), employee.getCompanyId());
							}
						}
						}
				 }
				}
			}
		}

}


