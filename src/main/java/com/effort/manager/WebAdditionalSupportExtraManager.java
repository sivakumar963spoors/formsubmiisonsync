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
import com.effort.entity.FlatDataTableConfiguration;
import com.effort.entity.Form;
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
}
