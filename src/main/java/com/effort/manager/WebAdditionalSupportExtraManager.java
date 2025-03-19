package com.effort.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;

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
import com.effort.dao.WorkFlowExtraDao;
import com.effort.entity.AuditFormFields;
import com.effort.entity.CompanyRestApis;
import com.effort.entity.CustomEntityRequisitionJmsMessageStatus;
import com.effort.entity.CustomerRequisitionJmsMessageStatus;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeRequisitionJmsMessageStatus;
import com.effort.entity.ExtraProperties;
import com.effort.entity.FlatDataTableConfiguration;
import com.effort.entity.Form;
import com.effort.entity.FormAndField;
import com.effort.entity.FormCustomEntities;
import com.effort.entity.FormCustomers;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormListUpdate;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.JmsMessage;
import com.effort.entity.RichTextFormField;
import com.effort.entity.Settings;
import com.effort.entity.WebUser;
import com.effort.entity.Work;
import com.effort.entity.WorkRequisitionJmsMessageStatus;
import com.effort.exception.EffortError;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.settings.ReportConstants;
import com.effort.util.Api;
import com.effort.util.Log;
import com.effort.validators.FormValidator;
@Service
public class WebAdditionalSupportExtraManager {
	@Autowired
	private ConfiguratorDao configuratorDao;
	@Autowired
	private WebSupportManager webSupportManager;

	@Autowired
	private WorkFlowManager workFlowManager;
	
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
	private FormValidator formValidator;
	
	@Autowired
	private AuditDao auditDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	
private ExtraSupportAdditionalDao getExtraSupportAdditionalDao(){
		
		ExtraSupportAdditionalDao extraSupportAdditionalDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalDao",ExtraSupportAdditionalDao.class);
		
		return extraSupportAdditionalDao;
	}
private ExtendedDao getExtendedDao(){
		
		ExtendedDao extendedDao = AppContext.getApplicationContext().getBean("extendedDao",ExtendedDao.class);
		
		return extendedDao;
	}
	
	private SchedularManager getSchedularManager(){
		SchedularManager schedularManager = AppContext.getApplicationContext().getBean("schedularManager",SchedularManager.class);
		return schedularManager;
	}
	private WebExtensionManager getWebExtensionManager(){
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
		return webExtensionManager;
	}
	
	private ExtraSupportAdditionalSupportDao getExtraSupportAdditionalSupportDao(){
		ExtraSupportAdditionalSupportDao extraSupportAdditionalSupportDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalSupportDao",ExtraSupportAdditionalSupportDao.class);
		return extraSupportAdditionalSupportDao;
	}
	
	public void resolveFieldsForIgnoreUpdateFields(List<FormField> fields,List<FormSectionField> sectionFields,
			List<FormField> ignoreUpdateFields,List<FormSectionField> ignoreUpdateSectionFields,
			List<FormField> restrictFromMobileFields,List<FormSectionField> restrictFromMobileSectionFields,
			Map<Long, FormFieldSpec> formFieldSpecMap,Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap) {
		
		//1. FormFields
		List<FormFieldSpec> formFieldSpecs = new ArrayList<FormFieldSpec>(formFieldSpecMap.values());
		Map<Boolean,List<FormFieldSpec>> restrictDataFromMobileFieldSpecsMap = (Map)Api.getGroupedMapFromList(formFieldSpecs, "restrictDataFromMobile", null); 
		List<FormFieldSpec> restrictDataFromMobileFieldSpecs = restrictDataFromMobileFieldSpecsMap.get(true);
		if(restrictDataFromMobileFieldSpecs != null && !restrictDataFromMobileFieldSpecs.isEmpty()) {
			
			List<Long> ignoreFieldSpecIds = Api.listToLongList(restrictDataFromMobileFieldSpecs, "fieldSpecId");
			List<Long> ignoreFieldSpecIdsFromFields = new ArrayList<Long>();
			Iterator<FormField> fieldsIterator = fields.iterator();
			while(fieldsIterator.hasNext())
			{
				FormField field = fieldsIterator.next();
				FormFieldSpec formFieldSpec = formFieldSpecMap.get(field.getFieldSpecId());
				if(ignoreFieldSpecIds.contains(formFieldSpec.getFieldSpecId())) {
					ignoreUpdateFields.add(field);
					restrictFromMobileFields.add(field);
					ignoreFieldSpecIdsFromFields.add(formFieldSpec.getFieldSpecId());
					fieldsIterator.remove();
				}
			}
			ignoreFieldSpecIds.removeAll(ignoreFieldSpecIdsFromFields);
			for (Long ignoreFieldSpecId : ignoreFieldSpecIds) {
				FormFieldSpec formFieldSpec = formFieldSpecMap.get(ignoreFieldSpecId);
				FormField formField = getSchedularManager().getFormField(0l, formFieldSpec, "", "");
				ignoreUpdateFields.add(formField);
			}
		}
		
		
		//2. SectionFields
		List<FormSectionFieldSpec> formSectionFieldSpecs = new ArrayList<FormSectionFieldSpec>(formSectionFieldSpecMap.values());
		Map<Boolean,List<FormSectionFieldSpec>> restrictDataFromMobileSectionFieldSpecsMap = (Map)Api.getGroupedMapFromList(formSectionFieldSpecs, "restrictDataFromMobile", null); 
		List<FormSectionFieldSpec> restrictDataFromMobileSectionFieldSpecs = restrictDataFromMobileSectionFieldSpecsMap.get(true);
		
		if(restrictDataFromMobileSectionFieldSpecs != null && !restrictDataFromMobileSectionFieldSpecs.isEmpty()) {
			List<Long> ignoreSectionFieldSpecIds = Api.listToLongList(restrictDataFromMobileSectionFieldSpecs, "sectionFieldSpecId");
			List<Long> ignoreSectionFieldSpecIdsFromFields = new ArrayList<Long>();
			
			Iterator<FormSectionField> sectionFieldsIterator = sectionFields.iterator();
			while(sectionFieldsIterator.hasNext())
			{
				FormSectionField field = sectionFieldsIterator.next();
				FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
						.get(field.getSectionFieldSpecId());
				if(ignoreSectionFieldSpecIds.contains(formSectionFieldSpec.getSectionFieldSpecId())) {
					ignoreUpdateSectionFields.add(field);
					restrictFromMobileSectionFields.add(field);
					ignoreSectionFieldSpecIdsFromFields.add(formSectionFieldSpec.getSectionFieldSpecId());
					sectionFieldsIterator.remove();
				}
			}
			ignoreSectionFieldSpecIds.removeAll(ignoreSectionFieldSpecIdsFromFields);
			
			for (Long ignoreSectionFieldSpecId : ignoreSectionFieldSpecIds) {
				FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap.get(ignoreSectionFieldSpecId);
				FormSectionField formSectionField = getSchedularManager().getFormSectionField(0l, formSectionFieldSpec, "", "",0);
				ignoreUpdateSectionFields.add(formSectionField);
			}
		}
	}
	
	
	public void insertIntoFlatTableDataStatusForNormalForms(Form form, int companyId, String formSpecUniqueId,boolean isWorkActionForm) {
    	extraSupportAdditionalDao.insertIntoFlatDataTables(form.getFormId(), companyId, formSpecUniqueId,isWorkActionForm);
    }

	public void insertFormListUpdateAuditLogs(long formId) {
		auditDao.insertFormListUpdateAuditLogs(formId);
	}
	public long insertOrUpdateFormListUpdates(FormListUpdate formListUpdate) {
		return extraSupportAdditionalDao.insertOrUpdateFormListUpdates(formListUpdate);
	}
	public void auditRestrictFromMobileFields(Employee employee, List<FormField> restrictFromMobileFields,
			List<FormSectionField> restrictFromMobileSectionFields, Map<String, Long> formNewKeys,
			Map<String, Long> customerNewKeys, Map<String, Long> customEntityNewKeys,
			List<Long> hardDeletedFormIdsInReq, Map<Long, FormFieldSpec> formFieldSpecMap,
			Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap) throws EffortError {

		String logText = "auditRestrictFromMobileFields() // empId = " + employee.getEmpId();
		Log.info(getClass(), logText + " restrictFromMobileFields size = " + restrictFromMobileFields.size()
				+ " restrictFromMobileSectionFields size = " + restrictFromMobileSectionFields.size());
		if (restrictFromMobileFields.isEmpty() && restrictFromMobileSectionFields.isEmpty()) {
			return;
		}
		// 1. FormFields
		for (FormField formField : restrictFromMobileFields) {

			if (!Api.isEmptyString(formField.getClientFormId())) {
				long formId = formNewKeys.get(formField.getClientFormId());
				formField.setFormId(formId);
			}
			if (hardDeletedFormIdsInReq.contains(formField.getFormId())) {
				continue;
			}

			FormFieldSpec formFieldSpec = formFieldSpecMap.get(formField.getFieldSpecId());

			if (formFieldSpec != null) {
				if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER) {
					if (Api.isEmptyString(formField.getFieldValue())
							&& !Api.isEmptyString(formField.getFieldValueSubstitute())) {
						Long cusId = customerNewKeys.get(formField.getFieldValueSubstitute());
						if (cusId != null) {
							formField.setFieldValue(cusId.longValue() + "");
						} else {
							throw new EffortError(4034, "Invalid clientCustomerId", HttpStatus.PRECONDITION_FAILED);
						}
					}
				}

				if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOMER) {
					if (Api.isEmptyString(formField.getFieldValue())
							&& !Api.isEmptyString(formField.getFieldValueSubstitute())) {
						String[] custIdArray = null;
						if (formField.getFieldValueSubstitute().contains(",")) {
							try {
								custIdArray = formField.getFieldValueSubstitute().split(",");
							} catch (Exception e) {
								Log.info(this.getClass(),
										"Exception while validating multi pick customer in form fields " + e);
							}
						} else {
							custIdArray = new String[1];
							custIdArray[0] = formField.getFieldValueSubstitute();
						}
						if (custIdArray != null && custIdArray.length > 0) {
							for (String str : custIdArray) {
								Long cusId = customerNewKeys.get(str);
								if (cusId != null) {
									formField.setFieldValue(cusId.longValue() + "");
								} else {
									throw new EffortError(4034, "Invalid clientCustomerId",
											HttpStatus.PRECONDITION_FAILED);
								}
							}
						}
					}
				}

				if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_FORM) {
					if (Api.isEmptyString(formField.getFieldValue())
							&& !Api.isEmptyString(formField.getFieldValueSubstitute())) {
						Long formId = formNewKeys.get(formField.getFieldValueSubstitute());
						if (formId != null) {
							formField.setFieldValue(formId.longValue() + "");
						} else {
							throw new EffortError(4034, "Invalid clientFormId", HttpStatus.PRECONDITION_FAILED);
						}
					}
				}

				if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOM_ENTITY) {
					if (Api.isEmptyString(formField.getFieldValue())
							&& !Api.isEmptyString(formField.getFieldValueSubstitute())) {
						Long customEntityId = customEntityNewKeys.get(formField.getFieldValueSubstitute());
						if (customEntityId != null) {
							formField.setFieldValue(customEntityId.longValue() + "");
						} else {
							throw new EffortError(4034, "Invalid clientCustomEntityId", HttpStatus.PRECONDITION_FAILED);
						}
					}
				}

				if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {
					Api.convertDateTimesToGivenType(formField, DateConversionType.XSD_TO_STADARD, "fieldValue");
				}
				if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOM_ENTITY) {
					if (Api.isEmptyString(formField.getFieldValue())
							&& !Api.isEmptyString(formField.getFieldValueSubstitute())) {
						String[] custIdArray = null;
						if (formField.getFieldValueSubstitute().contains(",")) {
							try {
								custIdArray = formField.getFieldValueSubstitute().split(",");
							} catch (Exception e) {
								Log.info(this.getClass(),
										"Exception while validating multi pick customer in form fields " + e);
							}
						} else {
							custIdArray = new String[1];
							custIdArray[0] = formField.getFieldValueSubstitute();
						}
						if (custIdArray != null && custIdArray.length > 0) {
							for (String str : custIdArray) {
								Long cusId = customEntityNewKeys.get(str);
								if (cusId != null) {
									formField.setFieldValue(cusId.longValue() + "");
								} else {
									throw new EffortError(4034, "Invalid clientCustomEntityId",
											HttpStatus.PRECONDITION_FAILED);
								}
							}
						}
					}
				}

			}

		}

		// 2. Section FormFields
		for (FormSectionField formSectionField : restrictFromMobileSectionFields) {

			if (!Api.isEmptyString(formSectionField.getClientFormId())) {
				long formId = formNewKeys.get(formSectionField.getClientFormId());
				formSectionField.setFormId(formId);
			}

			if (hardDeletedFormIdsInReq.contains(formSectionField.getFormId())) {
				continue;
			}
			FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
					.get(formSectionField.getSectionFieldSpecId());

			if (formSectionFieldSpec != null) {

				if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER) {
					if (Api.isEmptyString(formSectionField.getFieldValue())
							&& !Api.isEmptyString(formSectionField.getFieldValueSubstitute())) {
						Long cusId = customerNewKeys.get(formSectionField.getFieldValueSubstitute());
						if (cusId != null) {
							formSectionField.setFieldValue(cusId.longValue() + "");
						} else {
							throw new EffortError(4034, "Invalid clientCustomerId", HttpStatus.PRECONDITION_FAILED);
						}
					}
				}

				if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOMER) {
					if (Api.isEmptyString(formSectionField.getFieldValue())
							&& !Api.isEmptyString(formSectionField.getFieldValueSubstitute())) {
						String[] custIdArray = null;
						if (formSectionField.getFieldValueSubstitute().contains(",")) {
							try {
								custIdArray = formSectionField.getFieldValueSubstitute().split(",");
							} catch (Exception e) {
								Log.info(this.getClass(),
										"Exception while validating multi pick customer in form fields " + e);
							}
						} else {
							custIdArray = new String[1];
							custIdArray[0] = formSectionField.getFieldValueSubstitute();
						}
						if (custIdArray != null && custIdArray.length > 0) {
							for (String str : custIdArray) {
								Long cusId = customerNewKeys.get(str);
								if (cusId != null) {
									formSectionField.setFieldValue(cusId.longValue() + "");
								} else {
									throw new EffortError(4034, "Invalid clientCustomerId",
											HttpStatus.PRECONDITION_FAILED);
								}
							}
						}
					}
				}

				if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_FORM) {
					if (Api.isEmptyString(formSectionField.getFieldValue())
							&& !Api.isEmptyString(formSectionField.getFieldValueSubstitute())) {
						Long formId = formNewKeys.get(formSectionField.getFieldValueSubstitute());
						if (formId != null) {
							formSectionField.setFieldValue(formId.longValue() + "");
						} else {
							throw new EffortError(4034, "Invalid clientFormId", HttpStatus.PRECONDITION_FAILED);
						}
					}
				}

				if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOM_ENTITY) {
					if (Api.isEmptyString(formSectionField.getFieldValue())
							&& !Api.isEmptyString(formSectionField.getFieldValueSubstitute())) {
						Long customEntityId = customEntityNewKeys.get(formSectionField.getFieldValueSubstitute());
						if (customEntityId != null) {
							formSectionField.setFieldValue(customEntityId.longValue() + "");
						} else {
							throw new EffortError(4034, "Invalid clientCustomEntityId", HttpStatus.PRECONDITION_FAILED);
						}
					}
				}

				if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {
					Api.convertDateTimesToGivenType(formSectionField, DateConversionType.XSD_TO_STADARD, "fieldValue");
				}
				if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOM_ENTITY) {
					if (Api.isEmptyString(formSectionField.getFieldValue())
							&& !Api.isEmptyString(formSectionField.getFieldValueSubstitute())) {
						String[] custIdArray = null;
						if (formSectionField.getFieldValueSubstitute().contains(",")) {
							try {
								custIdArray = formSectionField.getFieldValueSubstitute().split(",");
							} catch (Exception e) {
								Log.info(this.getClass(),
										"Exception while validating multi pick customer in form fields " + e);
							}
						} else {
							custIdArray = new String[1];
							custIdArray[0] = formSectionField.getFieldValueSubstitute();
						}
						if (custIdArray != null && custIdArray.length > 0) {
							for (String str : custIdArray) {
								Long cusId = customEntityNewKeys.get(str);
								if (cusId != null) {
									formSectionField.setFieldValue(cusId.longValue() + "");
								} else {
									throw new EffortError(4034, "Invalid clientCustomEntityId",
											HttpStatus.PRECONDITION_FAILED);
								}
							}
						}
					}
				}
			}

		}

		// Inserting into FormFields into RestrictDataFromMobileFormFields
		if (!restrictFromMobileFields.isEmpty()) {
			List<ObjectError> errorsTemp = new ArrayList<ObjectError>();
			boolean isMasterForm = false;
			Long insertFormFieldsCurrentTime = Api.getCurrentTimeInUTCLong();
			formValidator.validateFieldsForSync(restrictFromMobileFields, false, employee.getCompanyId(),
					employee.getEmpId(), formFieldSpecMap, errorsTemp, true, isMasterForm);
			if (errorsTemp.size() > 0) {
				throw new EffortError(4034, Api.getErrorMesg(errorsTemp), HttpStatus.PRECONDITION_FAILED);
			} else {
				extraDao.insertFormFields(restrictFromMobileFields, false, true,false);
			}
		}

		// Inserting into Section FormFields into
		// RestrictDataFromMobileSectionFormFields
		if (!restrictFromMobileSectionFields.isEmpty()) {
			List<ObjectError> errorsTemp = new ArrayList<ObjectError>();
			boolean isMasterForm = false;
			Long insertFormSectionFieldsCurrentTime = Api.getCurrentTimeInUTCLong();
			formValidator.validateSectionFieldsForSync(restrictFromMobileSectionFields, false, employee.getCompanyId(),
					employee.getEmpId(), formSectionFieldSpecMap, errorsTemp, true,isMasterForm);
			if (errorsTemp.size() > 0) {
				throw new EffortError(4034, Api.getErrorMesg(errorsTemp), HttpStatus.PRECONDITION_FAILED);
			} else {
				extraDao.insertFormSectionFields(restrictFromMobileSectionFields, true,false,false);
			}
		}

	}
	
	public void canProcessEmpMasterMappingConfigurationWhenMasterModified(Long masterId,Long masterFormId,Long masterSpecId,Integer eventType,
			List<FormField> updatedFields,  WebUser webUser) {
		EmpMasterMappingConfiguration empMasterMappingConfiguration = getEmpMasterMappingConfiguration(webUser.getCompanyId(),eventType,masterSpecId);
		if(empMasterMappingConfiguration == null) {
			return;
		}
		if(updatedFields!=null && !updatedFields.isEmpty()) {
			Map<String,FormField> uniqueIdAndFieldMap = (Map) Api.getMapFromList(updatedFields, "uniqueId");
			List<EmpMasterFieldsMapping> empMasterFieldsMappingList = empMasterMappingConfiguration.getEmpMasterFieldsMappingList();
			for (EmpMasterFieldsMapping empMasterFieldMapping : empMasterFieldsMappingList) {
				String oldFieldValue = getMasterFormFieldValueByFormIdAndFieldUniqueId(masterFormId,
								empMasterFieldMapping.getMasterFieldUniqueId());
				
				
				FormField formField = uniqueIdAndFieldMap.getOrDefault(empMasterFieldMapping.getMasterFieldUniqueId(),null);
				if(Api.isEmptyString(oldFieldValue) && formField != null && !Api.isEmptyString(formField.getFieldValue())) {
					insertIntoEmpMasterMappingStatus(webUser.getCompanyId().longValue(), 
							masterId,null, eventType,webUser.getEmpId());
					return;
				}
				if(!Api.isEmptyString(oldFieldValue) && (formField != null && !Api.isEmptyString(formField.getFieldValue()))) {
					if(Constants.FORM_FIELD_TYPE_TEXT == empMasterFieldMapping.getFieldDataType() 
							|| Constants.FORM_FIELD_TYPE_LIST == empMasterFieldMapping.getFieldDataType() ) {
						if(!oldFieldValue.equals(formField.getFieldValue())) {
							insertIntoEmpMasterMappingStatus(webUser.getCompanyId().longValue(), 
									masterId,null, eventType,webUser.getEmpId());
							return;
						}
					}else {
						List<String> oldValues = Api.csvToList(oldFieldValue);
						List<String> newValues = Api.csvToList(formField.getFieldValue());
						if(oldValues.retainAll(newValues)) {
							insertIntoEmpMasterMappingStatus(webUser.getCompanyId().longValue(), 
									masterId,null, eventType,webUser.getEmpId());
							return;
						}
					}
				}
			}
		}
		return;
	}	
	public String getCompanySettingValueFromSettingsTable(int companyId,String key){
  		
  		//key = Api.processStringValuesList(Api.csvToList(key));
  		Settings setting = extraSupportAdditionalDao.getSettingValueByKeyAndCompanyId(key,companyId);
  		if(setting == null) {
  			return null;
  		}
  		//Settings setting = webSettings.get(0);
  		
  		return setting.getValue();
  	}
	
 	public void updateFlatTableDataStatusForForms(Form form, int companyId) {
    	Log.info(getClass(), "inside updateFlatTableDataStatusForForms() companyId :- "+companyId+" formId :- "+form.getFormId()+" uniqueId :- "+Api.makeEmptyIfNull(form.getUniqueId()));
    	List<FlatDataTableConfiguration> flatDataTableConfig = getFlatDataTableConfigurationFromColumnId(form.getUniqueId(), companyId);
    	Log.info(getClass(), "inside updateFlatTableDataStatusForForms() Configurations :- "+flatDataTableConfig.size());
    	for(FlatDataTableConfiguration flatDataTableConfiguration : flatDataTableConfig) {
    		if(flatDataTableConfiguration.isUpdateDataOnModify()) {
    			Log.info(getClass(), "inside updateFlatTableDataStatusForForms() flatDataTableConfiguration :- "+flatDataTableConfiguration.getFlatDataTableId());
    			//extraSupportAdditionalDao.updateFlatTableDataStatus(form.getFormId(), flatDataTableConfiguration.getFlatDataTableId(), companyId);
    			getExtraSupportAdditionalSupportDao().insertOrUpdateFlatTableDataStatus(form.getFormId(), flatDataTableConfiguration.getFlatDataTableId(), companyId);
    		}
    	}
    }
 	
 	public Employee getEmployeeBasicDetailsByEmpId(String empId) {
		return employeeDao.getEmployeeBasicDetailsByEmpId(empId);
	}
 	
 	public List<FlatDataTableConfiguration> getFlatDataTableConfigurationFromColumnId(String columnId, int companyId) {
        return extraSupportAdditionalDao.getFlatDataTableConfigurationFromColumnId(columnId, companyId);
  }
 	public boolean isActivityLocationEnabledForLocationPush(int purpose)
	{
		if(purpose == Location.FOR_FORM
				|| purpose == Location.FOR_START_WORK 
				||purpose == Location.FOR_STOP_WORK
				||purpose == Location.FOR_WORK_CHECKIN
				||purpose == Location.FOR_WORK_CHECKOUT
				||purpose == Location.FOR_CUSTOM_ENTITY_CHECKIN
				||purpose == Location.FOR_CUSTOM_ENTITY_CHECKOUT
				||purpose == Location.FOR_CUSTOMER_CHECKIN
				||purpose == Location.FOR_CUSTOMER_CHECKOUT){
			return true;
		}
		return false;
	}
 	
 	public void insertOrUpdateWorkStatusHistory(
 	  		  ExtraProperties extraProperties,Form form,WebUser webUser,Work work,FormAndField formAndField) {
 	  	  
 	  	  
 	  	  WorkStatusHistory workStatusHistoryAleadyExisting = extraSupportDao.getWorkStatusHistoryByActionSpecIdAndFormId(
 						Long.parseLong(extraProperties.getWorkActionSpecId()),work.getWorkId(),form.getFormId());
 	  	  
 	  	String auditType = "";
 		if(formAndField.getSourceOfAction() != null && formAndField.getSourceOfAction() == Work.WEB) {
 			auditType = "Web";
 		}if(formAndField.getSourceOfAction() != null && formAndField.getSourceOfAction() == Work.API) {
 			auditType = "Api";
 		}
 	  	FormLocation formLocation = extraDao
 				.getLatestLocationIdForFormIdIn(form.getFormId());
 	  	  if(workStatusHistoryAleadyExisting == null) {
 	  		  if(form.getDraftForm() == 1) {
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
 						if (formLocation != null) {
 						workStatusHistory.setLocationId(formLocation.getLocationId());
 						}
 						workStatusHistory.setDraft(true);
 						
 						extraDao.insertWorkStatusHistory(workStatusHistory, null);
 						
 						// Handling previousactionperformed
 						boolean resolveActionableMethod = false;
 						resolveActionableMethod = getWebExtensionManager().validateAndResolveActionAssignments(
 								workStatusHistory.getWorkSpecId(), workStatusHistory.getWorkActionSpecId());
 						if (resolveActionableMethod) {
 							getWebExtensionManager().resolveActionAssignments(work.getWorkSpecId(), webUser, workStatusHistory);
 						}
 						
 					    workFlowExtraDao.updateWorkActionExcalatedEmpIds(Long.parseLong(extraProperties.getWorkId()),Long.parseLong(extraProperties.getWorkActionSpecId()));
 					}
 					else {
 						
 						if(extraProperties!=null && extraProperties.isAddJmsMessages()) {
 							List<JmsMessage> jmsMessages = new ArrayList<JmsMessage>();
 							webManager.changeWorkStatus(Long.parseLong(extraProperties.getWorkId()),
 									extraProperties.getWorkActionSpecId(),
 									form.getFormId(), webUser.getCompanyId(),
 									webUser.getEmpId(), formAndField.isWorkCompleted(),null,auditType,null,null,jmsMessages);
 							extraProperties.setJmsMessages(jmsMessages);
 						}else {
 							webManager.changeWorkStatus(Long.parseLong(extraProperties.getWorkId()),
 									extraProperties.getWorkActionSpecId(),
 									form.getFormId(), webUser.getCompanyId(),
 									webUser.getEmpId(), formAndField.isWorkCompleted(),null,auditType,null,null,null);
 						}
 						
 						
 					}
 	  	  }else {
 	  		  if(form.getDraftForm() == 1) {
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
 						workStatusHistory.setDraft(true);
 						extraSupportAdditionalDao.updateWorkStatusHistory(workStatusHistory, null);
 					}
 					else {
 						if(extraProperties!=null && extraProperties.isAddJmsMessages()) {
 							List<JmsMessage> jmsMessages = new ArrayList<JmsMessage>();
 							getWebAdditionalSupportManager().changeWorkStatus(Long.parseLong(extraProperties.getWorkId()),
 									extraProperties.getWorkActionSpecId(),
 									form.getFormId(), webUser.getCompanyId(),
 									webUser.getEmpId(), formAndField.isWorkCompleted(),null,jmsMessages);
 							extraProperties.setJmsMessages(jmsMessages);
 						}else {
 							getWebAdditionalSupportManager().changeWorkStatus(Long.parseLong(extraProperties.getWorkId()),
 									extraProperties.getWorkActionSpecId(),
 									form.getFormId(), webUser.getCompanyId(),
 									webUser.getEmpId(), formAndField.isWorkCompleted(),null,null);
 						}
 						
 					}
 	  	  }
 	    }

 	
 	public boolean isCompanyRestApiExistForType(int type, String companyId)
	{
		List<CompanyRestApis> companyRestApis = extraSupportAdditionalDao.getCompanyRestApiDataWithCompanyId(type, companyId);
		return !companyRestApis.isEmpty();
	}
 	public List<FormField> getMasterFormFields(long formId) {
		return extraSupportAdditionalDao.getMasterFormFieldsByForm(formId);
	}
	
	public FormSpec getFormSpecByFormSpecId(long formSpecId) {
		return extraSupportAdditionalDao.getFormSpecByFormSpecId(formSpecId);
	}
	
	public Form getMasterForm(Long formId) {
		return extraSupportAdditionalDao.getMasterForm(formId);
	}
}
