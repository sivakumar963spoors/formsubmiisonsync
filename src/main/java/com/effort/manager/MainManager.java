package com.effort.manager;

import java.sql.Date;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import java.util.logging.Logger;


import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import com.effort.beans.http.request.location.Location;
import com.effort.beans.http.request.location.LocationDataPostTriggeringEvents;
import com.effort.beans.http.response.extra.FormMainContainer;
import com.effort.context.AppContext;
import com.effort.dao.ActivityLocationDao;
import com.effort.dao.ConfiguratorDao;
import com.effort.dao.EmployeeDao;
import com.effort.dao.ExtendedDao;
import com.effort.dao.ExtraDao;
import com.effort.dao.ExtraSupportAdditionalDao;
import com.effort.dao.ExtraSupportAdditionalSupportDao;
import com.effort.dao.ExtraSupportDao;
import com.effort.dao.SyncDao;
import com.effort.dao.TrackDao;
import com.effort.dao.WorkFlowExtraDao;
import com.effort.entity.Company;
import com.effort.entity.CompanyRestApis;
import com.effort.entity.CustomEntity;
import com.effort.entity.CustomEntityFilteringCritiria;
import com.effort.entity.Customer;
import com.effort.entity.CustomerAutoFilteringCritiria;
import com.effort.entity.CustomerEvent;
import com.effort.entity.CustomerFilteringCritiria;
import com.effort.entity.CustomerTag;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeAutoDeactivateConfig;
import com.effort.entity.EmployeeEntityMap;
import com.effort.entity.EmployeeFilteringCritiria;
import com.effort.entity.EmployeeGroup;
import com.effort.entity.Entity;
import com.effort.entity.EntityField;
import com.effort.entity.EntityFieldSpec;
import com.effort.entity.EntitySectionField;
import com.effort.entity.EntitySectionFieldSpec;
import com.effort.entity.FieldSpecFilter;
import com.effort.entity.FieldValidationCritiria;
import com.effort.entity.Form;
import com.effort.entity.FormCleanUpRule;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldGroupSpec;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormFieldSpecValidValue;
import com.effort.entity.FormFieldSpecsExtra;
import com.effort.entity.FormFieldsColorDependencyCriterias;
import com.effort.entity.FormFilteringCritiria;
import com.effort.entity.FormHistoryForForm;
import com.effort.entity.FormPageSpec;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSectionFieldSpecValidValue;
import com.effort.entity.FormSectionFieldSpecsExtra;
import com.effort.entity.FormSectionSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.FormSpecConfigSaveOnOtpVerify;
import com.effort.entity.JmsMessage;
import com.effort.entity.ListFilteringCritiria;
import com.effort.entity.OfflineCustomEntityUpdateConfiguration;
import com.effort.entity.OfflineListUpdateConfiguration;
import com.effort.entity.Provisioning;
import com.effort.entity.RemainderFieldsMap;
import com.effort.entity.RichTextFormField;
import com.effort.entity.RichTextFormSectionField;
import com.effort.entity.Settings;
import com.effort.entity.StockFormConfiguration;
import com.effort.entity.Subscripton;
import com.effort.entity.Tag;
import com.effort.entity.VisibilityDependencyCriteria;
import com.effort.entity.WebUser;
import com.effort.entity.WorkFlowFormStatus;
import com.effort.entity.WorkFlowFormStatusHistory;
import com.effort.entity.Workflow;
import com.effort.entity.WorkflowEntityMap;
import com.effort.exception.EffortError;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.util.Api;
import com.effort.util.Api.CsvOptions;
import com.effort.util.Api.DateConversionType;
import com.effort.util.ContextUtils;
import com.effort.util.Log;
import com.effort.util.SecurityUtils;
import com.effort.validators.FormValidator;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Session;
@Service
public class MainManager {
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
	private TrackDao trackDao;
	
	@Autowired
	private SyncDao syncDao;
	@Autowired
	private ConfiguratorDao configuratorDao;
	
	@Autowired
	private ExtraSupportDao extraSupportDao;
	@Autowired
	private ExtraSupportAdditionalDao extraSupportAdditionalDao;
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private ActivityLocationDao activityLocationDao;
	
	 private ExtendedDao getExtendedDao(){
		 ExtendedDao extendedDao = AppContext.getApplicationContext().getBean("extendedDao",ExtendedDao.class);
			return extendedDao;
		}
		private ExtraSupportAdditionalDao getExtraSupportAdditionalDao(){
			ExtraSupportAdditionalDao extraSupportAdditionalDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalDao",ExtraSupportAdditionalDao.class);
			return extraSupportAdditionalDao;
		}
	
	private WebAdditionalSupportManager getWebAdditionalSupportManager(){
		WebAdditionalSupportManager webAdditionalSupportManager = AppContext.getApplicationContext().getBean("webAdditionalSupportManager",WebAdditionalSupportManager.class);
		return webAdditionalSupportManager;
	}
	
	private WebSupportManager getWebSupportManager(){
		WebSupportManager webSupportManager = AppContext.getApplicationContext().getBean("webSupportManager",WebSupportManager.class);
		return webSupportManager;
	}
	private WebAdditionalSupportExtraManager getWebAdditionalSupportExtraManager(){
		WebAdditionalSupportExtraManager webAdditionalSupportExtraManager = AppContext.getApplicationContext().getBean("webAdditionalSupportExtraManager",WebAdditionalSupportExtraManager.class);
		return webAdditionalSupportExtraManager;
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
	
	
public List<FormField> getFieldsToInsert(String empId,List<FormField> allFields,List<Long> hardDeletedFormIdsInReq) {
		
		String logText = "getFieldsToInsert() // empId = "+empId;
		List<FormField> fieldsToInsert = new ArrayList<FormField>();
		for (FormField formField : allFields) {
			
			if(!hardDeletedFormIdsInReq.contains(formField.getFormId())) {
				fieldsToInsert.add(formField);
			}
		}
		Log.info(getClass(), logText+" allFields size = "+allFields.size()+" hardDeletedFormIdsInReq = "+hardDeletedFormIdsInReq+" fieldsToInsert = "+fieldsToInsert.size());
		return fieldsToInsert;
	}
	
public String errorToString(List<ObjectError> errors) {
	String str = "";
	for (ObjectError objectError : errors) {
		if (!Api.isEmptyString(str)) {
			str += ", ";
		}
		str += objectError.getDefaultMessage();
	}
	return str;
}
	public List<WorkFlowFormStatus> getExistedWorkFlows(
			List<WorkFlowFormStatus> workFlowFormStatusList) {
		List<WorkFlowFormStatus> existedWorkFlowFormStatus = new ArrayList<WorkFlowFormStatus>();
		if (workFlowFormStatusList != null) {
			for (WorkFlowFormStatus workFlowFormStatus : workFlowFormStatusList) {
				if (workFlowFormStatus.getId() != null) {
					existedWorkFlowFormStatus.add(workFlowFormStatus);
				}

			}
		}

		return existedWorkFlowFormStatus;

	}
	
public List<FormSectionField> getSectionFieldsToInsert(String empId,List<FormSectionField> allSectionFields,List<Long> hardDeletedFormIdsInReq) {
		
		String logText = "getSectionFieldsToInsert() // empId = "+empId;
		List<FormSectionField> sectionFieldsToInsert = new ArrayList<FormSectionField>();
		for (FormSectionField formSectionField : allSectionFields) {
			
			if(!hardDeletedFormIdsInReq.contains(formSectionField.getFormId())) {
				sectionFieldsToInsert.add(formSectionField);
			}
		}
		Log.info(getClass(), logText+" allSectionFields size = "+allSectionFields.size()+" hardDeletedFormIdsInReq = "+hardDeletedFormIdsInReq+" sectionFieldsToInsert = "+sectionFieldsToInsert.size());
		return sectionFieldsToInsert;
	}
	
	
	public void processCustomerFormFieldsBasedOnEditPerssion(
			Employee employee, String empId,
			Map<Long, FormFieldSpec> formFieldSpecMap,
			Map<String, FormFieldSpec> uniqueIdAndCustomerFormFieldSpecMap,
			List<ObjectError> errorsTemp,
			List<FormField> masterCustomerFormFields) throws EffortError {
		
		Log.info(getClass(), "processCustomerFormFieldsBasedOnEditPerssion() //--> masterCustomerFormFieldItr empId :"+empId+" starts ");
		boolean isMasterForm = true;
		Iterator<FormField> masterCustomerFormFieldItr = masterCustomerFormFields.iterator();
		while(masterCustomerFormFieldItr.hasNext()){
			FormField masterCustomerFormField = masterCustomerFormFieldItr.next();
			if(formFieldSpecMap.get(masterCustomerFormField.getFieldSpecId()) !=null)
			{
				FormFieldSpec formFieldSpec = formFieldSpecMap.get(masterCustomerFormField.getFieldSpecId());
				if(formFieldSpec.getSkeletonFormFieldSpecId() !=null && ( 
					formFieldSpec.getSkeletonFormFieldSpecId() != constants.getGlobal_company_customer_form_last_activity_time_skeleton_field_spec_id().longValue()
					&& formFieldSpec.getSkeletonFormFieldSpecId() != constants.getGlobal_company_customer_form_last_activity_name_skeleton_field_spec_id().longValue()))
				{
					masterCustomerFormFieldItr.remove();
				}
			}
			else {
				/*Added FieldsFormSpecIds Overlook up due to customer 
				  fields are coming with two different formSpecids while insert or
				   update to reject the older formSpec fields in sync and 
				   sync successfully we are adding this relookup on 11 Aug 2021 00:11 AM issue occured in GO_MMT.
				*/
				Log.info(getClass(), "masterCustomerFormFieldItr empId :"+empId+" remove masterCustomerFormField.getFieldSpecId():"+masterCustomerFormField.getFieldSpecId()+" masterCustomerFormField.getFormSpecId :"+masterCustomerFormField.getFormSpecId());
				masterCustomerFormFieldItr.remove();
			}
		}
		
		if(masterCustomerFormFields!=null && masterCustomerFormFields.size()>0){
			formValidator.validateFieldsForSync(masterCustomerFormFields, false, employee.getCompanyId(), employee.getEmpId(),
					formFieldSpecMap, errorsTemp, true, isMasterForm);
			if (errorsTemp.size() > 0) {
				throw new EffortError(4034, errorToString(errorsTemp), HttpStatus.PRECONDITION_FAILED);
			} else {
				Map<Long,List<FormField>> formIdAndFormFieldsMap = (Map)Api.getResolvedMapFromList(masterCustomerFormFields, "formId");
				for(Map.Entry<Long, List<FormField>> entry : formIdAndFormFieldsMap.entrySet()){
					Long formId = entry.getKey();
					List<FormField> customerFormFields = entry.getValue();
					List<FormField> existingFormFields = extraSupportAdditionalDao.getMasterFormFieldsByFormId(formId);
					Map<Long, FormField>  existingFormFieldsMap = (Map) Api.getMapFromList(
							existingFormFields, "fieldSpecId");
					
					for(FormField customerFormField : customerFormFields){
						FormField existingFormField = existingFormFieldsMap.get(customerFormField.getFieldSpecId());
						if(existingFormField !=null){
							Log.audit(employee.getCompanyId() + "", empId, "formField", "Updating", customerFormField.toCSV(), null);
							getExtraSupportAdditionalSupportDao().updateMasterFormFieldValueAndDisplayValue(customerFormField.getFieldSpecId(),
									customerFormField.getFieldValue(), formId,customerFormField.getFieldValue());
						}else{
							Log.audit(employee.getCompanyId() + "", empId, "formField", "added", customerFormField.toCSV(), null);
							List<FormField> newCustomerFormFields = new ArrayList<FormField>();
							newCustomerFormFields.add(customerFormField);
							//extraDao.insertFormFields(newCustomerFormFields, true,false,false);
							SecurityUtils.stripInvalidCharacters(newCustomerFormFields, SecurityUtils.INPUT_TYPE_TEXT, "fieldValue", "displayValue");
							getExtendedDao().insertFormFieldsForSync(newCustomerFormFields, true,false,false,formFieldSpecMap);
						}
					}
					
					if(!uniqueIdAndCustomerFormFieldSpecMap.isEmpty() && existingFormFields!=null && existingFormFields.size()>0)
					{
						for(FormField existingFormField : existingFormFields){
							if(uniqueIdAndCustomerFormFieldSpecMap.get(existingFormField.getUniqueId()) !=null){
								FormFieldSpec customerFormFieldSpec = uniqueIdAndCustomerFormFieldSpecMap.get(existingFormField.getUniqueId());
								getExtraSupportAdditionalSupportDao().updateMasterFormFieldFormSpecIdAndFieldSpecId(customerFormFieldSpec.getFieldSpecId(), customerFormFieldSpec.getFormSpecId(),
										existingFormField.getFormId(), existingFormField.getUniqueId());
							}
						}
					}
				}
			}
		}
	}
	public void resolveFormFieldSpecs(
			Map<Long, List<FormFieldSpec>> formSpecIdAndComputedFieldMap,
			List<FormFieldSpec> formFieldSpecs) {
		for (FormFieldSpec formFieldSpec : formFieldSpecs) {
			if (formFieldSpec.isComputedField()) {
				List<FormFieldSpec> formFieldSpecList = formSpecIdAndComputedFieldMap
						.get(formFieldSpec.getFormSpecId());
				if (formFieldSpecList == null) {
					formFieldSpecList = new ArrayList<FormFieldSpec>();
					formSpecIdAndComputedFieldMap.put(
							formFieldSpec.getFormSpecId(), formFieldSpecList);

				}
				formFieldSpecList.add(formFieldSpec);
			}

		}

	}
	public void resolveFormSectionFieldSpes(

			Map<Long, List<FormSectionFieldSpec>> formSpecIdsAndComputedSectionFiledMap,
			List<FormSectionFieldSpec> formSectionFieldSpecs) {
		for (FormSectionFieldSpec formSectionFieldSpec : formSectionFieldSpecs) {
			if (formSectionFieldSpec.isComputedField()) {
				List<FormSectionFieldSpec> formSectionFieldSpecList = formSpecIdsAndComputedSectionFiledMap
						.get(formSectionFieldSpec.getFormSpecId());
				if (formSectionFieldSpecList == null) {
					formSectionFieldSpecList = new ArrayList<FormSectionFieldSpec>();
					formSpecIdsAndComputedSectionFiledMap.put(
							formSectionFieldSpec.getFormSpecId(),
							formSectionFieldSpecList);

				}
				formSectionFieldSpecList.add(formSectionFieldSpec);
			}

		}

	}
	 private ComputeFieldsManager getComputeFieldsManager() {
			ComputeFieldsManager computeFieldsManager =  AppContext.getApplicationContext().getBean("computeFieldsManager",ComputeFieldsManager.class);
		    return computeFieldsManager;
		}
	public void computeFieldsForSync(List<FormField> fields,
			List<FormSectionField> sectionFields,
			Map<Long, FormFieldSpec> formFieldSpecMap,
			Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap,
			List<Form> allforms, Employee employee, List<Long> hardDeletedFormIdsInReq) {
		try {
			Map<Long, List<FormField>> formIdAndFormFieldListMap = new HashMap<Long, List<FormField>>();
			Map<Long, List<FormSectionField>> formIdAndFormSectionFieldListMap = new HashMap<Long, List<FormSectionField>>();
			Map<Long, List<FormFieldSpec>> formSpecIdAndComputedFieldMap = new HashMap<Long, List<FormFieldSpec>>();
			Map<Long, List<FormSectionFieldSpec>> formSpecIdsAndComputedSectionFiledMap = new HashMap<Long, List<FormSectionFieldSpec>>();

			List<FormFieldSpec> formFieldSpecs = new ArrayList<FormFieldSpec>(
					formFieldSpecMap.values());
			List<FormSectionFieldSpec> formSectionFieldSpecs = new ArrayList<FormSectionFieldSpec>(
					formSectionFieldSpecMap.values());

			Map<Long, List<Entity>> entitiesMaps = webManager
					.getEntityMapBySpecIdForFieldsInSync(formFieldSpecs,
							formSectionFieldSpecs, true);

			resolveFormFieldSpecs(formSpecIdAndComputedFieldMap, formFieldSpecs);
			resolveFormSectionFieldSpes(formSpecIdsAndComputedSectionFiledMap,
					formSectionFieldSpecs);

			for (FormField formField : fields) {
				if(hardDeletedFormIdsInReq.contains(formField.getFormId())) {
					continue;
				}
				List<FormField> formfieldsList = formIdAndFormFieldListMap
						.get(formField.getFormId());
				if (formfieldsList == null) {
					formfieldsList = new ArrayList<FormField>();
					formIdAndFormFieldListMap.put(formField.getFormId(),
							formfieldsList);
				}
				formfieldsList.add(formField);
			}

			for (FormSectionField formSectionField : sectionFields) {
				if(hardDeletedFormIdsInReq.contains(formSectionField.getFormId())) {
					continue;
				}
				List<FormSectionField> formSectionFieldsList = formIdAndFormSectionFieldListMap
						.get(formSectionField.getFormId());
				if (formSectionFieldsList == null) {
					formSectionFieldsList = new ArrayList<FormSectionField>();
					formIdAndFormSectionFieldListMap
							.put(formSectionField.getFormId(),
									formSectionFieldsList);
				}
				formSectionFieldsList.add(formSectionField);
			}

			// Set<Long> keySetForFormFields =
			// formIdAndFormFieldListMap.keySet();
			// Set<Long> keySetForSectionFields =
			// formIdAndFormSectionFieldListMap
			// .keySet();
			// keySetForFormFields.addAll(keySetForSectionFields);

			WebUser webUser = new WebUser();
			webUser.setEmpId(employee.getEmpId());
			webUser.setCompanyId(employee.getCompanyId());
			List<Employee> accessibleEmployees = getContextUtils().getEmployeesUnderMe(true, true, null, webUser);
			List<String> accessibleEmployeeIds = Api.listToList(accessibleEmployees, "empId");
			
			List<Employee> allEmployees = new ArrayList<Employee>(accessibleEmployees);
			getWebSupportManager().resolveWorkReassignmentRights(allEmployees, webUser);
			
			for (Form form : allforms) {
				Long formId = form.getFormId();
				if(hardDeletedFormIdsInReq.contains(formId)) {
					continue;
				}
				List<FormFieldSpec> formFieldSpec = formSpecIdAndComputedFieldMap
						.get(form.getFormSpecId());
				List<FormSectionFieldSpec> formSectionFieldSpec = formSpecIdsAndComputedSectionFiledMap
						.get(form.getFormSpecId());
				// List<FormFieldSpec> formFieldSpec=
				getComputeFieldsManager().computeFields(form,
						formIdAndFormFieldListMap.get(formId),
						formIdAndFormSectionFieldListMap.get(formId),
						formFieldSpecMap, formSectionFieldSpecMap,
						formFieldSpec, formSectionFieldSpec, entitiesMaps,
						fields, sectionFields, accessibleEmployeeIds, webUser,allEmployees);
			}

		} catch (Exception ex) {
			Log.ignore(getClass(), ex);
		}

	}
	public void processAndSaveListOfLocations(List<Location> locations,Employee employee, List<JmsMessage> jmsMessages) throws EffortError{
		  
		  if(locations!=null){
				for (Location location : locations) {
					try {
						processLocation(location);
					} catch (ParseException e) {
						Log.info(this.getClass(),
								e.toString(), e);
						throw new EffortError(
								4001,
								HttpStatus.PRECONDITION_FAILED);
					}
					location.setEmpId(employee
							.getEmpId());
					location.setCompanyId(employee.getCompanyId());
					location.setPurpose(Location.FOR_FORM);
					saveLocation(location);
					//commented because this is been using in Endurence Query and Employee Form Location the queries were replaced with locations
					/*if(location.getLocationId()!=0)
					{
						Long currentTimeTYPE_ACTIVITY_LOCATION = Api.getCurrentTimeInUTCLong();
						webManager.sendJmsMessage(
								JmsMessage.TYPE_ACTIVITY_LOCATION,
								location.getLocationId() , 
								employee.getCompanyId(), 
								employee.getEmpId(),
								JmsMessage.TYPE_ACTIVITY_LOCATION, 
								location.getLocationId(), 
								null, true, jmsMessages);
						Log.info(this.getClass(), "JmsMessage for TYPE_ACTIVITY_LOCATION End. Time taken to process for empId "+employee.getEmpId()+" : "+(Api.getCurrentTimeInUTCLong() - currentTimeTYPE_ACTIVITY_LOCATION));
					}*/
				
				}
		  }
	  }
	public long saveLocation(Location location) {
		if (location != null) {
		//	System.out.println("<<<<<<<<<<<<< " + location.getClientTime());
			if (location.getLatitude() == null
					&& location.getLongitude() == null) {
				location.setStatus(Location.STATUS_TYPE_INVALID_TRACK);
			}
			else if((location.getPurpose() != Location.FOR_TRACK)) {
				/*
				 * This setting is used to stop or enable the Activity locations address resolving. 
				 * This setting default value is false which means will stop resolving the address.
				 */
				Settings setting = extraSupportAdditionalDao.getSettingValueByKeyAndCompanyId(constantsExtra.getEnableActivityLocationToResolveAddressKey(),
						location.getCompanyId());
				if(setting!=null && "false".equalsIgnoreCase(setting.getValue())){
					if(location.getPurpose() == Location.FOR_COMMENT){
						location.setStatus(Location.STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_COMMENT);
					}else{
						location.setStatus(-location.getPurpose());
					}
				}
			}
			
			if(!Api.isEmptyString(location.getLocation())) {
				try {
					String locationAddress = location.getLocation();
					locationAddress = locationAddress.trim();
					locationAddress = locationAddress.replace("\n", "");
					locationAddress = locationAddress.replace("\n\n", "");
					location.setLocation(locationAddress);
				}catch(Exception e1) {
					Log.error(getClass(), "Got exception while removing location address new lines");
				}
				location.setStatus(Location.STATUS_TYPE_ADDRESS_RESOLVED_FROM_MOBILE);
			}
			
			
			trackDao.saveLocation(location);
			if(location.getLocationId()!=0)
			{
				extraSupportDao.saveLocationExtra(location);
				extraSupportDao.saveMockLocation(location);
				if (location != null) {
					extraDao.saveNearByCellInfo(location.getLocationId(),
							location.getNearByCellInfo());
				}
				if((location.getPurpose() != Location.FOR_TRACK) && 
						!Api.isEmptyString(location.getClientTime())) {
					
					/*Settings setting = extraSupportDao.getSettingsHavingKeyForSync(location.getCompanyId(),-1l,
							constantsExtra.getEnableActivityBasedLocationKey());*/
					Company company = webManager.getCompany(location.getCompanyId()+"");
					if(company.isEnableActivityBasedLocation()){
						activityLocationDao.saveActivityLocation(location);
					}
					if(getWebAdditionalSupportExtraManager().isActivityLocationEnabledForLocationPush(location.getPurpose())
							&& getWebAdditionalSupportExtraManager().isCompanyRestApiExistForType(CompanyRestApis.ACTIVITY_BASED_EMPLOYEE_TRACKING_LOCATION, location.getCompanyId()+""))
					{
					//	activityLocationDao.saveActivityBasedLocationsToPushExternalSystem(location, location.getPurpose(), LocationDataPostTriggeringEvents.PROCESSING_STATUS_PENDING);
					}
				}
				if(location.getPurpose() == Location.FOR_TRACK) {
					try
					{
						Log.info(getClass(), "saveLocation() // insertIntoFlatDataTablesForLocation inside the method calling");
						extraSupportAdditionalDao.insertIntoFlatDataTablesForLocation(location.getLocationId());
					}
					catch(Exception e)
					{
						Log.info(getClass(), "saveLocation() // insertIntoFlatDataTablesForLocation Exception ouured : "+e.getMessage(), e);
					}
				}
				
				//Commenting this code as Ador are using activity geo locations 
				/*String locationGeoPathEnabledCompanyIds = constantsExtra.getLocationGeoPathResolvingEnabledCompanyIds();
				if(!Api.isEmptyString(locationGeoPathEnabledCompanyIds) && !locationGeoPathEnabledCompanyIds.equals("-1")) {
					List<String> companyIdsList = Api.csvToList(locationGeoPathEnabledCompanyIds);
					if(companyIdsList.contains(location.getCompanyId()+"")) {
						
						//Log.info(getClass(), "saveLocationGeoPath() // getClientTime = "+location.getClientTime()+" companyId="+location.getCompanyId()+" purpose = "+location.getPurpose().intValue());
						if(!Api.isEmptyString(location.getClientTime()) && 
						    (location.getPurpose().intValue() == Location.FOR_TRACK || location.getPurpose().intValue() == Location.FOR_START_WORK || location.getPurpose().intValue() == Location.FOR_STOP_WORK)) {  
							locationGeoPathDao.saveLocationGeoPath(location);
							//Log.info(getClass(), "saveLocationGeoPath done for "+location.getLocationId()+" companyId="+location.getCompanyId());
						}
					}
				}*/
				
			}
			return location.getLocationId();
		} else {
			return -1;
		}

		
	}
	
	public void sendJMSMessage(final JmsMessage message)
	{
		
		String logText = "sendJmsMessage() id = "+message.getId()+" entityId = "+message.getEntityId()+" companyId = "+message.getCompanyId()+" type = "+message.getType()+" changeType = "+message.getChangeType();
		if (constants.isPrintLogs()) {
			Log.info(getClass(), logText+" sending ... logText = "+Api.getLogText());
		}else {
			Log.info(getClass(), logText+" sending ...");	
		}
		try {
			long currentTime =System.currentTimeMillis();
			jmsTemplate.send(constants.getJmsDestination(),
					new MessageCreator() {

						public Message createMessage(Session session)
								throws JMSException {
							ObjectMessage om = session
									.createObjectMessage(message);
							return om;
						}

					});

			Log.info(this.getClass(), ">>>Message send: " + message+"   Time Took :"+(System.currentTimeMillis() - currentTime));
		} catch (Exception e) {
			Log.error(this.getClass(), logText+" Exception occucred : "+e.toString(), e);
		}
	}

	private boolean isEmployeeAutoBlocked(Short blocked) {
		return (blocked != null && 
				(blocked ==  EmployeeAutoDeactivateConfig.ACCESS_TYPE_MOBILE || blocked ==  EmployeeAutoDeactivateConfig.ACCESS_TYPE_BOTH) );
	}
	
	public Subscripton getSubscripton(long companyId) {
	    Subscripton subscripton = null;
	    try {
	      subscripton = extraDao.getActiveSubscripton(companyId);
	    } catch (Exception e) {
	      Log.ignore(this.getClass(), e);
	    }
	    return subscripton;
	  }
 
	public boolean isCompanyActive(long companyId) {
		try {
			Company company = extraDao.getCompany(companyId);

			if (company.isActive()) {
				Subscripton subscripton = getSubscripton(companyId);
				if (subscripton == null) {
					return false;
				}

				long now = System.currentTimeMillis();
				long expired = Api
						.getDateTimeInUTC(subscripton.getExpiryTime())
						.getTime();

				long diff = (expired - now);
				diff += (subscripton.getGracePreiod() * 24 * 60 * 60 * 1000l);

				if (diff <= 0) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
			return false;
		}
	}
	public Employee validateAndGetUser(String empId, String code,
			boolean checkCompanyActivation, String effortToken, String apiLevel) throws EffortError {
		try {
			Employee employee = employeeDao.getEmployee(empId);
			
			Map<Long, Short> employeeAutoBlockedBySchedularMap = employeeDao.getEmployeesAutoBlocked(employee.getEmpId()+"");
			Short blocked = employeeAutoBlockedBySchedularMap.get(employee.getEmpId());
			
			if(!constantsExtra.getImeiBasedProvisioning(employee.getCompanyId()))
			{
				if (effortToken != null && effortToken.length() > 0) {
					Provisioning provisioning = null;
					try {
						provisioning = employeeDao.getProvisioningByEmpId(Long
								.parseLong(empId));
					} catch (IncorrectResultSizeDataAccessException e) {
						Log.info(this.getClass(), "IncorrectResultSizeDataAccessException occcured while fetching the provitioning record the exception is:"+e);
					} catch (Exception e)
					{
						Log.info(this.getClass(), "Exception occcured while fetching the provitioning record the exception is:"+e);
						throw new EffortError(9000, HttpStatus.PRECONDITION_FAILED);
					}
					if (provisioning != null
							&& !Api.isEmptyString(provisioning.getEffortToken())
							&& provisioning.getEffortToken().equals(effortToken)) {
						if (employee.getStatus() == 0) {
							Log.info(this.getClass(), "Employee is disabled for empId ="+empId);
							throw new EffortError(4020,
									HttpStatus.PRECONDITION_FAILED);
						} else if(isEmployeeAutoBlocked(blocked)  ) {
							Log.info(this.getClass(), "Blocked by schedular for employee id:"+empId);
							throw new EffortError(4041, HttpStatus.PRECONDITION_FAILED);
						}

						if (!checkCompanyActivation
								|| isCompanyActive(employee.getCompanyId())) {
							return employee;
						} else {
							Log.info(this.getClass(), "Company is InActive for CompanyId ="+employee.getCompanyId()+" And EmpId="+empId);
							throw new EffortError(4028,
									HttpStatus.PRECONDITION_FAILED);
						}
					} else {
						if (employee.getStatus() == 0) {
							Log.info(this.getClass(), "Disabled and no provisioning record for employee id:"+empId);
							throw new EffortError(4020, HttpStatus.PRECONDITION_FAILED);
						} else if(isEmployeeAutoBlocked(blocked)  ) {
							Log.info(this.getClass(), "Blocked by schedular for employee id:"+empId);
							throw new EffortError(4041, HttpStatus.PRECONDITION_FAILED);
						} else
						{
							Log.info(this.getClass(), "no provisioning record for employee id:"+empId);
							throw new EffortError(4090, HttpStatus.PRECONDITION_FAILED);
						}
					}
				} else {
					Log.info(this.getClass(), "effortToken Id is Empty:"+empId);
					throw new EffortError(5020, HttpStatus.PRECONDITION_FAILED);
				}
			}
			else{
				if (code != null && code.length() > 0) {
					Provisioning provisioning = null;
					try {
						provisioning = employeeDao.getProvisioningByEmpId(Long
								.parseLong(empId));
					} catch (IncorrectResultSizeDataAccessException e) {
						//Log.ignore(this.getClass(), e);
						Log.info(this.getClass(), "IncorrectResultSizeDataAccessException occcured while fetching the provitioning record the exception is:"+e);
					} catch (Exception e)
					{
						Log.info(this.getClass(), "Exception occcured while fetching the provitioning record the exception is:"+e);
						throw new EffortError(9000, HttpStatus.PRECONDITION_FAILED);
					}

					if (provisioning != null
							&& !Api.isEmptyString(provisioning.getCode())
							&& provisioning.getCode().equals(code)) {
						if (employee.getStatus() == 0) {
							Log.info(this.getClass(), "Employee is disabled for empId ="+empId);
							throw new EffortError(4020,
									HttpStatus.PRECONDITION_FAILED);
						} else if(isEmployeeAutoBlocked(blocked)  ) {
							Log.info(this.getClass(), "Blocked by schedular for employee id:"+empId);
							throw new EffortError(4041, HttpStatus.PRECONDITION_FAILED);
						}

						if (!checkCompanyActivation
								|| isCompanyActive(employee.getCompanyId())) {
							return employee;
						} else {
							Log.info(this.getClass(), "Company is InActive for CompanyId ="+employee.getCompanyId()+" And EmpId="+empId);
							throw new EffortError(4028,
									HttpStatus.PRECONDITION_FAILED);
						}
					} else {
						if (employee.getStatus() == 0) {
							Log.info(this.getClass(), "Disabled and no provisioning record for employee id:"+empId);
							throw new EffortError(4020, HttpStatus.PRECONDITION_FAILED);
						} else if(isEmployeeAutoBlocked(blocked)  ) {
							Log.info(this.getClass(), "Blocked by schedular for employee id:"+empId);
							throw new EffortError(4041, HttpStatus.PRECONDITION_FAILED);
						} else
						{
							Log.info(this.getClass(), "no provisioning record for employee id:"+empId);
							throw new EffortError(4090, HttpStatus.PRECONDITION_FAILED);
						}
					}
				} else {
					Log.info(this.getClass(), "Code Id is Empty:"+empId);
					throw new EffortError(4020, HttpStatus.PRECONDITION_FAILED);
				}
			}
		} catch (EffortError e) {
			throw e;
		}
	}

	public FormHistoryForForm getFromDetailsWithFormId(String formIds, String empId,
			String code, String formSpcIdsWonByClient, String effortToken, String apiLevel, String isActionForm, boolean skipValidation,boolean isMasterForm,String entityIdsString,int purpose) throws EffortError 
	{
		Employee employee = null;
		if(skipValidation) {
			employee = employeeDao.getEmployee(empId);
		}else {
			employee = validateAndGetUser(empId, code, false,effortToken,apiLevel);
		}
		FormMainContainer formMainContainer = new FormMainContainer();
		List<Long> formSpecidsWonByclient = Api.csvToLongList(formSpcIdsWonByClient);
		
			List<Form> forms =  new ArrayList<Form>();
			
			List<String> matserFormIds = new ArrayList<String>();
			List<Form> matserFormsList = new ArrayList<Form>();
			if (employee != null) {
				
				
				if(isMasterForm) {
					forms = extraSupportAdditionalDao
							.getMasterFormsIn(formIds);
					
				}else {
					List<String> fromIdsList = Api.csvToList(formIds);
					
					for(String formId : fromIdsList) {
						Form formWithGivenFormID=webManager.getForm(formId);
						if(formWithGivenFormID==null || formWithGivenFormID.getCompanyId()!=employee.getCompanyId() ){
							List<Form> masterForms = extraSupportAdditionalDao.getMasterFormsIn(formId);
							if(masterForms != null && masterForms.size() > 0) {
								matserFormsList.removeAll(masterForms);
								matserFormsList.addAll(masterForms);
								matserFormIds.add(formId);
							}
						}else {
							forms.add(formWithGivenFormID);
						}
						
					}
				}
			
			
			List<FormField> fields=new ArrayList<FormField>();
			List<FormSectionField> sectionFields = new ArrayList<FormSectionField>();
			Map<Long, FormFieldSpec> formFieldSpecMap = new HashMap<Long, FormFieldSpec>();
			Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap = new HashMap<Long, FormSectionFieldSpec>();
			FormHistoryForForm formHistory = new FormHistoryForForm();
			formHistory.setFormMainContainer(formMainContainer);
			
			String custIds = "";
			String entityIds = "";
			String formEmpIds = "";
			String formInFormIds = "";
			String customEntityIdsForForms = "";
			
			if(isMasterForm) {
				fields = extraSupportAdditionalDao
						.getMasterFormFieldsForSync(formIds);
				 sectionFields = syncDao
						.getMasterFormSectionFieldsForSync(formIds);
			}else {
				 fields = extraDao.getFormFieldsForSync(forms);
				 sectionFields = extraDao
						.getFormSectionFieldsForFormIn(forms);
			}
			
			if(matserFormIds != null && matserFormIds.size() > 0) {
				
				forms.addAll(matserFormsList);
				forms.removeAll(matserFormsList);
				List<FormField> mfields = extraSupportAdditionalDao
						.getMasterFormFieldsForSync(Api.toCSV(matserFormIds));
				if(mfields != null && mfields.size() > 0) {
					fields.addAll(mfields);
				}
				List<FormSectionField> msectionFields = syncDao
						.getMasterFormSectionFieldsForSync(Api.toCSV(matserFormIds));
				 
				 if(msectionFields != null && msectionFields.size() > 0) {
					 sectionFields.addAll(msectionFields);
				}
			}
			
			
			if(purpose == 7) {
				customEntityIdsForForms = entityIdsString;
			}else if(purpose == 4) {
				custIds = entityIdsString;
			}

			String empIds = "";
			for (Form form : forms) {
				if (!Api.isEmptyString(empIds)) {
					empIds += ",";
				}
				if (Api.isNonZeroPositiveLong(form.getFilledBy())) {
					empIds += form.getFilledBy() + "";
				}

				if (Api.isNonZeroPositiveLong(form.getFilledBy())) {
					empIds += form.getFilledBy() + "";
				}
			}

			Map<Long, Employee> employeeMap = employeeDao
					.getEmployeeMapIn(empIds);
			for (Form form : forms) {
				try {
					if (Api.isNonZeroPositiveLong(form.getFilledBy())) {
						Employee employee2 = employeeMap
								.get(form.getFilledBy());
						if (employee2 != null) {
							form.setFilledByName(employee2.getEmpFirstName());
						}
					}

					if (Api.isNonZeroPositiveLong(form.getModifiedBy())) {
						Employee employee3 = employeeMap.get(form
								.getModifiedBy());
						if (employee3 != null) {
							form.setModifiedByName(employee3.getEmpFirstName());
						}
					}
				} catch (Exception e) {
					Log.info(this.getClass(), e.toString(), e);
				}
			}

			 formFieldSpecMap = extraDao
					.getFormFieldSpecMapForFields(fields);
			formSectionFieldSpecMap = extraDao
					.getFormSectionFieldSpecMapForFields(sectionFields);

			
			for (FormField formField : fields) {
				FormFieldSpec formFieldSpec = formFieldSpecMap.get(formField
						.getFieldSpecId());

				if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER) {
					if (!Api.isEmptyString(custIds)) {
						custIds += ",";
					}
					custIds += formField.getFieldValue();
				}

				else if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_LIST) {
					if (!Api.isEmptyString(entityIds)) {
						entityIds += ",";
					}
					entityIds += formField.getFieldValue();
				}

				else if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_LIST) {
					if (!Api.isEmptyString(entityIds)) {
						entityIds += ",";
					}
					entityIds += formField.getFieldValue();
				}

				else if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE) {
					if (!Api.isEmptyString(formEmpIds)) {
						formEmpIds += ",";
					}
					formEmpIds += formField.getFieldValue();
				}
				
				else if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_FORM) {
					if (!Api.isEmptyString(formInFormIds)) {
						formInFormIds += ",";
					}
					formInFormIds += formField.getFieldValue();
				}
				else if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOMER) {
					if (!Api.isEmptyString(custIds)) {
						custIds += ",";
					}
					custIds += formField.getFieldValue();
				}
				else if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOM_ENTITY || 
						formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOM_ENTITY) {
					if (!Api.isEmptyString(customEntityIdsForForms)) {
						customEntityIdsForForms += ",";
					}
					customEntityIdsForForms += formField.getFieldValue();
				}
			}
			for (FormSectionField formSectionField : sectionFields) {
				FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
						.get(formSectionField.getSectionFieldSpecId());

				if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER) {
					if (!Api.isEmptyString(custIds)) {
						custIds += ",";
					}
					custIds += formSectionField.getFieldValue();
				}

				else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_LIST) {
					if (!Api.isEmptyString(entityIds)) {
						entityIds += ",";
					}
					entityIds += formSectionField.getFieldValue();
				}

				else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_LIST) {
					if (!Api.isEmptyString(entityIds)) {
						entityIds += ",";
					}
					entityIds += formSectionField.getFieldValue();
				}

				else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE) {
					if (!Api.isEmptyString(formEmpIds)) {
						formEmpIds += ",";
					}
					formEmpIds += formSectionField.getFieldValue();
				}
				
				else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_FORM) {
					if (!Api.isEmptyString(formInFormIds)) {
						formInFormIds += ",";
					}
					formInFormIds += formSectionField.getFieldValue();
				}
				else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOMER) {
					if (!Api.isEmptyString(custIds)) {
						custIds += ",";
					}
					custIds += formSectionField.getFieldValue();
				}
				else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOM_ENTITY || 
						formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOM_ENTITY) {
					if (!Api.isEmptyString(customEntityIdsForForms)) {
						customEntityIdsForForms += ",";
					}
					customEntityIdsForForms += formSectionField.getFieldValue();
				}
			}
			
			if (!Api.isEmptyString(customEntityIdsForForms)) 
			{
				List<CustomEntity> customEntitiesInForms = getWebAdditionalSupportManager().
						getCustomEntitiesByCustomEntityIds(customEntityIdsForForms);
				
				if(customEntitiesInForms!=null && customEntitiesInForms.size()>0)
				{
					String customEntityFormIds = Api.toCSV(customEntitiesInForms, "formId", CsvOptions.FILTER_NULL_OR_EMPTY);
					if (!Api.isEmptyString(formInFormIds)) {
						formInFormIds += ",";
					}
					formInFormIds += customEntityFormIds;
				}
			}

			if(!Api.isEmptyString(formInFormIds))
			{
				List<Form> formInForms = extraDao.getFormsIn(formInFormIds);
				processFormInFormsData(formInForms,custIds,
						entityIds, formEmpIds, formMainContainer,
						fields,sectionFields,customEntityIdsForForms); 
				forms.addAll(formInForms);
			}
			
			//  workflowformstatus history
			List<WorkFlowFormStatus> workFlowFormStatuses = workFlowExtraDao.getWorkFlowFormApprovalsByFormIds(formIds,employee.getCompanyId(),0);
			
			 List<WorkFlowFormStatusHistory> workFlowFormStatusHistories = workFlowExtraDao
		                .getWorkFlowFormStatusesHistoryForSync(formIds,null);
			 if(workFlowFormStatuses != null && workFlowFormStatuses.size() > 0) {
				 for(WorkFlowFormStatus workFlowFormStatus : workFlowFormStatuses)
					{
						if(workFlowFormStatus.getStatus() == WorkFlowFormStatus.STATUS_TYPE_REJECTED)
						{
							workFlowFormStatus.setCanApprove(0);
						}
						workFlowFormStatus.toCreatedTimeXSD();
						workFlowFormStatus.toModifiedTimeXSD();
						workFlowFormStatus.toApprovedTimeXSD();
						workFlowFormStatus.toSubmittedTimeXSD();
					}
				 formHistory.setWorkflowFormStatus(workFlowFormStatuses);
			 }
			 if(workFlowFormStatusHistories != null && workFlowFormStatusHistories.size() > 0) {
				 for (WorkFlowFormStatusHistory workFlowFormStatusHistoryBean : workFlowFormStatusHistories) {
						workFlowFormStatusHistoryBean.toCreatedTimeXSD();
						workFlowFormStatusHistoryBean.toModifiedTimeXSD();
						workFlowFormStatusHistoryBean.toApprovedTimeXSD();
						workFlowFormStatusHistoryBean.toSubmittedTimeXSD();
					}
				 formHistory.setWorkflowFormStatusHistory(workFlowFormStatusHistories);
			 }
			
			if (!Api.isEmptyString(entityIds)) {
				List<Entity> entities = extraDao.getEntitiesIn(entityIds);
				getExtraEntitesForEntites(entities);
				List<EntityField> entityFields = extraDao
						.getEntityFieldByEntityIn(entities);
				
				List<EntitySectionField> entitySectionFields = extraSupportDao.getEntitySectionFieldsByEntityIn(entities);

				String entitySpecId = Api.toCSVFromList(entityFields,
						"entitySpecId");
				Map<Long, EntityFieldSpec> entityFieldSpecMap = webManager
						.getEntityFieldSpecMap(entitySpecId);

				for (EntityField entityField : entityFields) {
					EntityFieldSpec entityFieldSpec = entityFieldSpecMap
							.get(entityField.getEntityFieldSpecId());
					if (entityFieldSpec.getFieldType() == 7) {
						if (!Api.isEmptyString(custIds)) {
							custIds += ",";
						}
						custIds += entityField.getFieldValue();
					}

					else if (entityFieldSpec.getFieldType() == 15) {
						if (!Api.isEmptyString(formEmpIds)) {
							formEmpIds += ",";
						}
						formEmpIds += entityField.getFieldValue();
					}
				}
				
				Map<Long, EntitySectionFieldSpec> entitySectionFieldSpecMap = extraSupportDao.
						getEntitySectionFieldSpecMapForFields(entitySectionFields);

				
				
				for (EntitySectionField entitySectionField : entitySectionFields) {
					EntitySectionFieldSpec entitySectionFieldSpec = entitySectionFieldSpecMap
							.get(entitySectionField.getSectionFieldSpecId());

					if (entitySectionFieldSpec.getFieldType() == 7) {
						if (!Api.isEmptyString(custIds)) {
							custIds += ",";
						}
						custIds += entitySectionField.getFieldValue();
					} else if (entitySectionFieldSpec.getFieldType() == 15) {
						if (!Api.isEmptyString(custIds)) {
							formEmpIds += ",";
						}
						formEmpIds += entitySectionField.getFieldValue();
					}
				}

				formHistory.setEntities(entities);
				formHistory.setEntityFields(entityFields);
				formHistory.setEntitySectionFields(entitySectionFields);
				populateAddedAndDeletedEntites(formHistory,entities,empId);

			}

			if (!Api.isEmptyString(custIds)) {
				List<Customer> formCustomers = extraDao
						.getCustomersInForSync(custIds);
				formHistory.setCustomers(formCustomers);
				populateCustomerForms(forms, fields, formCustomers);

			}

			if (!Api.isEmptyString(formEmpIds)) {
				List<Employee> employees = webManager
						.getEmployeesIn(formEmpIds);
				formHistory.setEmployees(employees);
			}
			
			if (!Api.isEmptyString(custIds)) {
				setCustomerTagsAndTagsToContainer(formHistory, custIds, employee.getCompanyId());
				setCustomersEventsForCustomers(formHistory,custIds);
			}
			
			if (!Api.isEmptyString(customEntityIdsForForms)) {
				List<CustomEntity> customEntitiesInForms = getWebAdditionalSupportManager().
						getCustomEntitiesByCustomEntityIds(customEntityIdsForForms);
				if(customEntitiesInForms!=null && customEntitiesInForms.size()>0){
					Api.convertDateTimesToGivenTypeList(customEntitiesInForms,
							DateConversionType.STADARD_TO_XSD, "createdTime","modifiedTime");
				}
				formHistory.setCustomEntities(customEntitiesInForms);
				populateCustomEntityForm(forms, fields, customEntitiesInForms);
			}
						
			populateMainManager(forms, fields, sectionFields,
					formMainContainer, formSpecidsWonByclient, empId,
					formFieldSpecMap, formSectionFieldSpecMap);

			return formHistory;
		} else {
			throw new EffortError(4004, HttpStatus.PRECONDITION_FAILED);
		}

	}
	
	public void setCustomersEventsForCustomers(Object container,String customerIds) 
	{
		if(!Api.isEmptyString(customerIds))
		{
			List<CustomerEvent> anniversarys = getWebAdditionalSupportManager().getCutomerEventsByCustomerId(customerIds);
			if(anniversarys!=null && anniversarys.size()>0){
				Api.convertDateTimesToGivenTypeList(anniversarys,
						DateConversionType.STADARD_TO_XSD, "createdTime");
			}
			try {
	            PropertyUtils.setProperty(container, "anniversaryEvents", anniversarys);
	        } catch(Exception ex){
	            Log.info(this.getClass(), "exception while setting data",ex);
	        }
			
		}
	}
	
	 public void setCustomerTagsAndTagsToContainer(Object container,String customerIds,Integer companyId)
	  {
		  if(!Api.isEmptyString(customerIds))
		  {
		    List<CustomerTag> customerTags = extraDao.getTagsByCustomerIdsforSync(customerIds);
			try {
	            PropertyUtils.setProperty(container, "customerTags", customerTags);
	        } catch(Exception ex){
	            Log.info(this.getClass(), "exception while setting data",ex);
	        }
			
			String tagIds = Api.toCSV(customerTags, "tagId", CsvOptions.NONE);
			List<Tag> tags=new ArrayList<Tag>();
			if(!Api.isEmptyString(tagIds))
			{
				tags = extraDao.getCompanyTagsForSync((int)companyId,tagIds);
			}
			try {
	            PropertyUtils.setProperty(container, "tags", tags);
	        } catch(Exception ex){
	            Log.info(this.getClass(), "exception while setting data",ex);
	        }
			List<String> customerTerritoryIds = extraDao.getCustomerTerritoryIdsFromCustomersInSync(customerIds);
			
			/*if(customerTerritoryIds != null && !customerTerritoryIds.isEmpty())
			{
				String customerTerritoryIdsCsv = Api.toCSV(customerTerritoryIds);
				List<Territory> territoriesForCustomer = extraDao.getTerritories(customerTerritoryIdsCsv);
				if(territoriesForCustomer.size()>0)
				{
					if(territoriesForCustomer!= null && !territoriesForCustomer.isEmpty())
					{
						Api.convertDateTimesToGivenTypeList(territoriesForCustomer,
								DateConversionType.STADARD_TO_XSD, "createdTime",
								"modifiedTime");
					}
					
					try {
			            PropertyUtils.setProperty(container, "territories", territoriesForCustomer);
			        } catch(Exception ex){
			            Log.info(this.getClass(), "exception while setting data",ex);
			        }
				}
				
			}
			*/
			
			
			
		  }
	  }
	
	public void populateAddedAndDeletedEntites(Object object,List<Entity> entities,String empId){
		if(entities!=null&&!entities.isEmpty()){
			List<Long>added=new ArrayList<Long>();
			List<Long>deleted=new ArrayList<Long>();
			String entitySpecIdsCsv= Api.toCSV(entities,"entitySpecId",CsvOptions.NONE);
			List<EmployeeEntityMap> employeeEntityMaps=	webManager.getMappedEntityIds(Long.parseLong(empId), entitySpecIdsCsv);
			Map<String,List<String>>entitySpecIdEntityIdsMap=(Map)Api.getGroupedMapFromList(employeeEntityMaps, "entitySpecId", "entityId");
			String entityIdsCsv=Api.toCSV(employeeEntityMaps,"entityId",CsvOptions.NONE);
			List<Long> entityIds=Api.csvToLongList(entityIdsCsv);
			if(!entityIds.isEmpty()){
				for (Entity entity : entities) {
				List<String>entityIdsList=	entitySpecIdEntityIdsMap.get(""+entity.getEntitySpecId());
				if(entityIdsList!=null){
					if(entityIdsList.contains(""+entity.getEntityId())){
					   	added.add(entity.getEntityId());
					}else{
						deleted.add(entity.getEntityId());
					}
				}
				
			}
				try {
					PropertyUtils.setProperty(object, "addedMappedEntityIds", added);
					PropertyUtils.setProperty(object, "deletedMappedEntityIds", deleted);
				} catch(Exception ex){
					Log.info(this.getClass(), "exception while setting data",ex);
				}
				
				
		}
			
			
		  }	
			
		}
	
	public void populateCustomerForms(List<Form> forms,
			List<FormField> fields, List<Customer> customers) {
		if (customers != null) {
			Api.convertDateTimesToGivenTypeList(customers, DateConversionType.STADARD_TO_XSD, "lockedTime");
			String formIdsForCustomers = Api.toCSV(customers, "customerFormId",
					Api.CsvOptions.FILTER_NULL_OR_EMPTY);
			if (!Api.isEmptyString(formIdsForCustomers)) {
				List<Form> formsForCustomers = extraSupportAdditionalDao
						.getMasterFormsIn(formIdsForCustomers);
				List<FormField> formFieldsForCsutomerForms = extraSupportAdditionalDao
						.getMasterFormFieldsForSync(formIdsForCustomers);
				forms.removeAll(formsForCustomers);
				fields.removeAll(formFieldsForCsutomerForms);
				forms.addAll(formsForCustomers);
				fields.addAll(formFieldsForCsutomerForms);
			}
		}
		
		//code for sending parent customers also
		if(customers!=null){
			try {
				String parentIds="-1";
				parentIds=Api.toCSV(customers, "parentId");
				if(!parentIds.isEmpty()&&!parentIds.equals("-1")){
					List<Customer>parentCustomers=extraDao.getCustomers(parentIds);
					customers.removeAll(parentCustomers);
					customers.addAll(parentCustomers);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		

	}
	
	public void populateMainManager(List<Form> forms,
			List<FormField> formFields, List<FormSectionField> sectionFields,
			FormMainContainer formMainContainer,
			List<Long> formSpecidsWonByclient, String empId,
			Map<Long, FormFieldSpec> formFieldSpecMap,
			Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap) {
		List<Long> formSpecIdsForFormList = new ArrayList<Long>();
		if (forms != null && !forms.isEmpty()) {

			String formSpecIdsForForm = "-1";
			try {
				formSpecIdsForForm = Api.toCSV(forms, "formSpecId");
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			formSpecIdsForFormList = Api.csvToLongList(formSpecIdsForForm);
			if(formSpecidsWonByclient !=null){
				formSpecIdsForFormList.removeAll(formSpecidsWonByclient);
			}
			

			if (formSpecIdsForFormList != null
					&& !formSpecIdsForFormList.isEmpty()) {
				List<FormSpec> formSpecs = extraDao.getFormSpecsIn(Api
						.toCSV(formSpecIdsForFormList));

				if (formSpecs != null) {

					Api.convertDateTimesToGivenTypeList(formSpecs,
							DateConversionType.STADARD_TO_XSD, "modifiedTime","createdTime","formSubmissionFrequencyModifiedTime");
					formMainContainer.getFormSpecContainer().getFormSpecs()
							.addAll(formSpecs);

					List<FormPageSpec> pageSpecs = extraDao
							.getFormPageSpecsForFormSpec(formMainContainer
									.getFormSpecContainer().getFormSpecs());
					formMainContainer.getFormSpecContainer().setPageSpecs(
							pageSpecs);

					List<FormFieldSpec> fieldSpecs = extraDao
							.getFormFieldSpecForIn(formMainContainer
									.getFormSpecContainer().getFormSpecs());
					Api.convertDateTimesToGivenTypeList(fieldSpecs,
							DateConversionType.STADARD_TO_XSD, "modifiedTime");
					formMainContainer.getFormSpecContainer().setFields(
							fieldSpecs);
					
					List<FormFieldSpecsExtra> fieldSpecsExtra = getExtraSupportAdditionalSupportDao().
							getFormFieldSpecsExtraForIn(formMainContainer
									.getFormSpecContainer().getFormSpecs());
					
					formMainContainer.getFormSpecContainer().setFieldsExtra(fieldSpecsExtra);
					
					// webManager.resolveFormFieldsWithRestrictions(formFieldSpecs,
					// currentFormSpecIds, groupIds)	

					List<FormFieldSpecValidValue> fieldSpecValidValues = extraDao
							.getFormFieldSpecValidValuesIn(fieldSpecs);
					formMainContainer.getFormSpecContainer()
							.setFieldValidValues(fieldSpecValidValues);

					List<FormSectionSpec> sectionSpecs = extraDao
							.getFormSectionSpecForIn(formMainContainer
									.getFormSpecContainer().getFormSpecs());
					Api.convertDateTimesToGivenTypeList(sectionSpecs,
							DateConversionType.STADARD_TO_XSD, "modifiedTime");
					
					formMainContainer.getFormSpecContainer().setSections(
							sectionSpecs);

					List<FormSectionFieldSpec> sectionFieldSpecs = extraDao
							.getFormSectionFieldSpecForIn(sectionSpecs);
					
					Api.convertDateTimesToGivenTypeList(sectionFieldSpecs,
							DateConversionType.STADARD_TO_XSD, "modifiedTime");
					
					formMainContainer.getFormSpecContainer().setSectionFields(
							sectionFieldSpecs);
					
					List<FormSectionFieldSpecsExtra> sectionFieldSpecsExtra = getExtraSupportAdditionalSupportDao().
							getFormSectionFieldSpecsForIn(formMainContainer
									.getFormSpecContainer().getFormSpecs());
					
					formMainContainer.getFormSpecContainer().setSectionFieldsExtra(
							sectionFieldSpecsExtra);
					
					List<FormSectionFieldSpecValidValue> sectionFieldSpecValidValues = extraDao
							.getFormSectionFieldSpecValidValuesIn(sectionFieldSpecs);
					formMainContainer.getFormSpecContainer()
							.setSectionFieldValidValues(
									sectionFieldSpecValidValues);
					
					List<FormFieldGroupSpec> formFieldGroupSpecs = extraSupportDao.getFormFieldGroupSpecForIn
							(formMainContainer.getFormSpecContainer().getFormSpecs());
					formMainContainer.getFormSpecContainer().setFormFieldGroupSpecs(formFieldGroupSpecs);
					// String currFormSpecIds="";
					String empgroupIds = webManager
							.getEmployeeGroupIdsForEmployee("" + empId);
					if (formMainContainer.getFormSpecContainer().getFormSpecs() != null
							&& formMainContainer.getFormSpecContainer()
									.getFormSpecs().size() > 0) {
						String currFormSpecIds1 = "-99";
						try {
							currFormSpecIds1 = Api.toCSV(formMainContainer
									.getFormSpecContainer().getFormSpecs(),
									"formSpecId");
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						List<VisibilityDependencyCriteria> visibilityDependencyCriterias = extraDao
								.getVisibilityDependencyCriteriasForFormSpecs(currFormSpecIds1);
						formMainContainer.getFormSpecContainer()
								.setVisibilityDependencyCriterias(
										visibilityDependencyCriterias);
						List<FormFieldsColorDependencyCriterias> formFieldsColorDependencyCriterias =extraSupportDao
								.getFormFieldsColorDependencyCriteriasByFormSpecId(currFormSpecIds1);
						formMainContainer.getFormSpecContainer()
						.setFormFieldsColorDependencyCriterias(formFieldsColorDependencyCriterias);
						
						List<FormCleanUpRule> formCleanUpRule=extraSupportDao
								.getFormDataCleanUpRulesForFormSpecId(currFormSpecIds1);
						Api.convertDateTimesToGivenTypeList(formCleanUpRule,
								DateConversionType.STADARD_TO_XSD, "createdTime","modifiedTime");
						
						formMainContainer.getFormSpecContainer()
						.setFormCleanUpRule(formCleanUpRule);
								
						List<ListFilteringCritiria> lisFilteringCritirias = extraDao
								.getListFilteringCritiriasForFormSpecs(currFormSpecIds1);
						formMainContainer.getFormSpecContainer()
								.setListFilteringCriterias(
										lisFilteringCritirias);
						
						List<RemainderFieldsMap> remainderFieldsMap = getExtendedDao()
								.getRemainderFieldsMapForFormSpecs(currFormSpecIds1);
						formMainContainer.getFormSpecContainer()
								.setRemainderFieldsMap(
										remainderFieldsMap);
						
						List<CustomerAutoFilteringCritiria> customerAutoFilteringCritirias = getExtendedDao()
								.getCustomerAutoFilteringCritiriasForFormSpecs(currFormSpecIds1);
						
						formMainContainer.getFormSpecContainer()
								.setCustomerAutoFilteringCritirias(customerAutoFilteringCritirias);
						List<CustomerFilteringCritiria> customerFilteringCritirias = extraDao
								.getListOfCustomerFilterCriterias(currFormSpecIds1);
						formMainContainer.getFormSpecContainer()
								.setCustomerFilteringCriterias(
										customerFilteringCritirias);
						//employee filtering , 2018-05-01,Deva
						List<EmployeeFilteringCritiria> employeeFilteringCritirias = extraSupportAdditionalDao
								.getListOfEmployeeFilterCriterias(currFormSpecIds1);
						formMainContainer.getFormSpecContainer()
								.setEmployeeFilteringCriterias(
										employeeFilteringCritirias);
						
						List<FieldValidationCritiria> fieldValidationCritirias = extraSupportDao
								.getFieldValidationCritirias(currFormSpecIds1);
						formMainContainer.getFormSpecContainer()
							.setFieldValidationCritirias(
									fieldValidationCritirias);
						
						List<FormFilteringCritiria> formFilteringCritirias = extraSupportDao
								.getFormFilteringCritiriasForFormSpecs(currFormSpecIds1);
						formMainContainer.getFormSpecContainer()
								.setFormFilteringCriterias(
										formFilteringCritirias);
						

						List<CustomEntityFilteringCritiria> customEntityFilteringCritirias = getExtraSupportAdditionalSupportDao()
								.getListOfCustomEntityFilteringCritiriaForFormSpecs(currFormSpecIds1);
						formMainContainer.getFormSpecContainer().setCustomEntityFilteringCritirias(customEntityFilteringCritirias);
						
						List<StockFormConfiguration> stockFormConfigurations = extraSupportDao
								.getStockFieldConfigurationsForSync(currFormSpecIds1);
						if (stockFormConfigurations != null
								&& stockFormConfigurations.size() > 0) {
							Api.convertDateTimesToGivenTypeList(stockFormConfigurations,
									DateConversionType.STADARD_TO_XSD, "createdTime", "modifiedTime");
						}
						
						formMainContainer.getFormSpecContainer().setStockFormConfigurations(stockFormConfigurations);
						
						List<OfflineListUpdateConfiguration> offlineListUpdateConfiguration = extraSupportAdditionalDao
								.getOfflineListUpdateConfigurationsForSync(currFormSpecIds1);
						
						if (offlineListUpdateConfiguration != null
								&& offlineListUpdateConfiguration.size() > 0) {
							Api.convertDateTimesToGivenTypeList(offlineListUpdateConfiguration,
									DateConversionType.STADARD_TO_XSD, "createdTime", "modifiedTime");
						}
						formMainContainer.getFormSpecContainer().setOfflineListUpdateConfigurations(offlineListUpdateConfiguration);
						
						List<OfflineCustomEntityUpdateConfiguration> offlineCustomEntityUpdateConfiguration =  configuratorDao
																.getOfflineCustomEntityUpdateConfigurationForSync(currFormSpecIds1);
						if (offlineCustomEntityUpdateConfiguration != null
								&& offlineCustomEntityUpdateConfiguration.size() > 0) {
							Api.convertDateTimesToGivenTypeList(offlineCustomEntityUpdateConfiguration,
									DateConversionType.STADARD_TO_XSD, "createdTime", "modifiedTime");
						}
						formMainContainer.getFormSpecContainer().setOfflineCustomEntityUpdateConfigurations(offlineCustomEntityUpdateConfiguration);

						List<FieldSpecFilter> formFieldSpecFilters = extraSupportDao.getFieldSpecFiltersFormFormSpecIds(currFormSpecIds1, FieldSpecFilter.FIELD_IS_FORMFIELD);
						formMainContainer.getFormSpecContainer().setFormFieldSpecFilters(formFieldSpecFilters);
						List<FieldSpecFilter> formSectionFieldSpecFilters = extraSupportDao.getFieldSpecFiltersFormFormSpecIds(currFormSpecIds1, FieldSpecFilter.FIELD_IS_SECTIONFIELD);
						formMainContainer.getFormSpecContainer().setFormSectionFieldSpecFilters(formSectionFieldSpecFilters);
						
						if (!Api.isEmptyString(currFormSpecIds1)) {
							webManager
									.resolveFormFieldsWithRestrictionsForSync(
											fieldSpecs, currFormSpecIds1,
											empgroupIds);
							webManager
									.resolveFormSectionFieldsWithRestrictionsForSync(
											sectionFieldSpecs,
											currFormSpecIds1, empgroupIds);

							webManager
									.resolveFormSectionFieldsWithRestrictionsForSync(
											sectionFieldSpecs,
											currFormSpecIds1, empgroupIds);
						}
						
						
						//PaymentMappings in Sync.
						String currFormUniqueIds = "-99";
						try{
						/*currFormUniqueIds = Api.toCSV(formMainContainer
								.getFormSpecContainer().getFormSpecs(),
								"uniqueId");*/
						List<String> uIds =  Api.listToList(formMainContainer
								.getFormSpecContainer().getFormSpecs(),
								"uniqueId");
						
						currFormUniqueIds =  Api.processStringValuesList(uIds);
						}catch(Exception e){
							Log.info(this.getClass(), e.toString());
						}
						/*List<PaymentMapping> paymentMappings = extraDao.getPaymentMappingByFormSpec(currFormUniqueIds);
						if(paymentMappings != null && !paymentMappings.isEmpty()){
							Api.convertDateTimesToGivenTypeList(paymentMappings,
									DateConversionType.STADARD_TO_XSD, "createdTime", "modifiedTime");
							
							formMainContainer.getFormSpecContainer().setPaymentMappings(paymentMappings);
						}*/
					}
				}

			}

			String formIds = Api.toCSVFromList(forms, "formId");
			
			//RTF
			List<RichTextFormField> richTextFormField = getExtraSupportAdditionalSupportDao().
					getRichTextFormFieldForIn(formIds);
			//RTF
			List<RichTextFormSectionField> richTextFormSectionField = getExtraSupportAdditionalSupportDao().
					getRichTextFormSectionFieldForIn(formIds);
			
			formMainContainer.getFormDataContainer().setRichTextFormField(richTextFormField);
			formMainContainer.getFormDataContainer().setRichTextFormSectionField(richTextFormSectionField);
			
			
			if (formFieldSpecMap != null) {
				for (FormField formField : formFields) {
					FormFieldSpec formFieldSpec = formFieldSpecMap
							.get(formField.getFieldSpecId());

					if (formFieldSpec!=null && formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {
						Api.convertDateTimesToGivenType(formField,
								DateConversionType.STADARD_TO_XSD, "fieldValue");

					}
				}
			}

			if (formSectionFieldSpecMap != null) {
				for (FormSectionField formSectionField : sectionFields) {
					FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
							.get(formSectionField.getSectionFieldSpecId());

					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {
						Api.convertDateTimesToGivenType(formSectionField,
								DateConversionType.STADARD_TO_XSD, "fieldValue");
					}
				}
			}

			if (!Api.isEmptyString(formIds)) {
				List<WorkFlowFormStatusHistory> workFlowFormStatusHistory = workFlowExtraDao.getWorkFlowFormStatusesHistoryForSync(formIds, "");
				
				Api.convertDateTimesToGivenTypeList(workFlowFormStatusHistory,
						DateConversionType.STADARD_TO_XSD, "createdTime","modifiedTime","submittedTime","approvedTime");
						
				//List<WorkFlowFormStatusHistory> workFlowFormStatusHistory = workFlowExtraDao.getWorkFlowFormStatusHistoryForSync(empId, formIds,null);
				/*
				 * List<WorkFlowFormStatus> workflowFormStatus = workFlowExtraDao
				 * .getWorkFlowFormStatusForSync(empId, formIds, null);
				 */
				List<WorkFlowFormStatus> workflowFormStatus = workFlowExtraDao
						.getWorkflowFormStatus(formIds);
				
				Api.convertDateTimesToGivenTypeList(workflowFormStatus,
						DateConversionType.STADARD_TO_XSD, "submittedTime","approvedTime","createdTime","modifiedTime");
				
				if(workflowFormStatus != null)
				{
					workFlowManager.populateWorkFlowFormStatusApproval(Long.parseLong(empId), workflowFormStatus);
					for(WorkFlowFormStatus workFlowFormStatus1 :workflowFormStatus)
					{
						if(workFlowFormStatus1.getStatus() == WorkFlowFormStatus.STATUS_TYPE_REJECTED)
						{
							workFlowFormStatus1.setCanApprove(0);
						}
					}
				}
				
				
				List<Workflow> workflows = workFlowExtraDao
						.getWorkflowForFormSpecs(formSpecIdsForFormList, null);
				Api.convertDateTimesToGivenTypeList(workflows,
						DateConversionType.STADARD_TO_XSD, "createdTime",
						"modifiedTime");
				List<WorkflowEntityMap> workflowEntityMaps = workFlowExtraDao
						.getWorkflowEntityMap(formSpecIdsForFormList, null);
				formMainContainer.getFormWorkflowContainer()
						.setWorkflowFormStatus(workflowFormStatus);
				formMainContainer
						.getFormWorkflowContainer()
						.setWorkFlowFormStatusHistory(workFlowFormStatusHistory);
				formMainContainer.getFormWorkflowContainer().setWorkflows(
						workflows);
				formMainContainer.getFormWorkflowContainer()
						.setWorkflowEntityMap(workflowEntityMaps);
				
				/*List<Payment> payments = extraDao.getPaymentsFormIds(formIds);

				if(payments != null && !payments.isEmpty()){
					Api.convertDateTimesToGivenTypeList(payments,
							DateConversionType.STADARD_TO_XSD, "createdTime", "modifiedTime", "serverCreatedTime", "serverModifiedTime");
					
					formMainContainer.setPayments(payments);
				}*/
			}
			formMainContainer.getFormDataContainer().getAdded().addAll(forms);
			formMainContainer.getFormDataContainer().getFields()
					.addAll(formFields);
			formMainContainer.getFormDataContainer().getSectionFields()
					.addAll(sectionFields);
			
			
			List<RichTextFormField> richTextFormFields = getExtraSupportAdditionalSupportDao().getRichTextFormFieldForFieldSpecsIn(formFields);
			
			List<RichTextFormSectionField> richTextFormSectionFields = getExtraSupportAdditionalSupportDao().getRichTextFormSectionFieldForFieldSpecsIn(sectionFields);
			
			formMainContainer.getFormDataContainer().getRichTextFormField().addAll(richTextFormFields);
			formMainContainer.getFormDataContainer().getRichTextFormSectionField().addAll(richTextFormSectionFields);
			
			try {
				if(formMainContainer.getFormSpecContainer()!=null && formMainContainer.getFormSpecContainer().getFormSpecs()!= null && 
						!formMainContainer.getFormSpecContainer().getFormSpecs().isEmpty()) {
					String formSpecUniqueIdsCsv = Api.toCSV(formMainContainer.getFormSpecContainer().getFormSpecs(),"uniqueId",CsvOptions.NONE);
					formSpecUniqueIdsCsv = Api.processStringValuesList(Api.csvToList(formSpecUniqueIdsCsv));
					List<FormSpecConfigSaveOnOtpVerify> saveFormOnOtpVerifyList = getExtraSupportAdditionalDao().getFormSpecConfigSaveOnOtpVerifyList(formSpecUniqueIdsCsv);
					formMainContainer.getFormSpecContainer().setSaveFormOnOtpVerify(saveFormOnOtpVerifyList);
				}
			}catch(Exception e) {
				Log.info(getClass(), "Got Exception while setting setSaveFormOnOtpVerify"+e);
				Log.error(getClass(), "Got Exception while setting setSaveFormOnOtpVerify"+e);
				e.printStackTrace();
			}
			
			
			

		}

	}
	
	 public void processFormInFormsData(List<Form> formInForms, String custIds,
				String entityIdsForForms, String formEmpIds, FormMainContainer formMainContainer,
				List<FormField> formFields,List<FormSectionField> formSectionFields, String customEntityIdsForForms) {
				List<FormField> allFormFields = new ArrayList<FormField>();
				List<FormSectionField> allFormSectionFields = new ArrayList<FormSectionField>();
				
				List<RichTextFormField> allRichTextFormFields = new ArrayList<RichTextFormField>();
				List<RichTextFormSectionField> allRichTextFormSectionFields = new ArrayList<RichTextFormSectionField>();
				
				List<FormField> formFields1 = extraDao.getFormFieldsForSync(formInForms);
				List<RichTextFormField> richTextFormFields1 = getExtraSupportAdditionalSupportDao().getRichTextFormFieldForIn(formInForms);
				List<RichTextFormField> richTextFormFields2 = getExtraSupportAdditionalSupportDao().getRichTextFormFieldForFieldSpecsIn(formFields);
				
				List<FormSectionField> formSectionFields1 = extraDao
						.getFormSectionFieldsForSync(formInForms);

				List<RichTextFormSectionField> richTextFormSectionFields1 = getExtraSupportAdditionalSupportDao().getRichTextFormSectionFieldForIn(formInForms);
				List<RichTextFormSectionField> richTextFormSectionFields2 = getExtraSupportAdditionalSupportDao().getRichTextFormSectionFieldForFieldSpecsIn(formSectionFields);
				
				Map<Long, FormFieldSpec> formFieldSpecMap = extraDao
						.getFormFieldSpecMapForFields(formFields1);
				Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap = extraDao
						.getFormSectionFieldSpecMapForFields(formSectionFields1);
				
				if (formFieldSpecMap != null) {
					for (FormField formField : formFields1) {
						FormFieldSpec formFieldSpec = formFieldSpecMap
								.get(formField.getFieldSpecId());

						if (formFieldSpec!=null && formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {
							Api.convertDateTimesToGivenType(formField,
									DateConversionType.STADARD_TO_XSD, "fieldValue");

						}
					}
				}
				
				allFormFields.addAll(formFields1);
				allFormFields.addAll(formFields);
				formMainContainer.getFormDataContainer().setFields(allFormFields);
				
				allRichTextFormFields.addAll(richTextFormFields1);
				allRichTextFormFields.addAll(richTextFormFields2);
				formMainContainer.getFormDataContainer().setRichTextFormField(allRichTextFormFields);

				if (formSectionFieldSpecMap != null) {
					for (FormSectionField formSectionField : formSectionFields1) {
						FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
								.get(formSectionField.getSectionFieldSpecId());

						if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {
							Api.convertDateTimesToGivenType(formSectionField,
									DateConversionType.STADARD_TO_XSD, "fieldValue");
						}
					}
				}
				
				allFormSectionFields.addAll(formSectionFields1);
				allFormSectionFields.addAll(formSectionFields);
				formMainContainer.getFormDataContainer().setSectionFields(allFormSectionFields);
				
				allRichTextFormSectionFields.addAll(richTextFormSectionFields1);
				allRichTextFormSectionFields.addAll(richTextFormSectionFields2);
				formMainContainer.getFormDataContainer().setRichTextFormSectionField(allRichTextFormSectionFields);

				StringBuilder custIdsBuilder = new StringBuilder("");
				StringBuilder entityIdsForFormsBuilder = new StringBuilder("");
				StringBuilder formEmpIdsBuilder = new StringBuilder("");
				StringBuilder customEntityIdsForFormsBuilder = new StringBuilder("");
				for (FormField formField : formFields1) {
					FormFieldSpec formFieldSpec = formFieldSpecMap
							.get(formField.getFieldSpecId());

					/*if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER) {
						if (!Api.isEmptyString(custIds)) {
							custIds += ",";
						}
						custIds += formField.getFieldValue();
					}

					if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_LIST) {
						if (!Api.isEmptyString(entityIdsForForms)) {
							entityIdsForForms += ",";
						}
						entityIdsForForms += formField.getFieldValue();
					}

					if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_LIST) {
						if (!Api.isEmptyString(entityIdsForForms)) {
							entityIdsForForms += ",";
						}
						entityIdsForForms += formField.getFieldValue();
					}

					if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE) {
						if (!Api.isEmptyString(formEmpIds)) {
							formEmpIds += ",";
						}
						formEmpIds += formField.getFieldValue();
					}*/
					
					if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER) {
						
						if ((custIdsBuilder.length() > 0)) {
							custIdsBuilder.append(",");
						}
						custIdsBuilder.append(formField.getFieldValue());
					}

					if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_LIST) {
						
						if ((entityIdsForFormsBuilder.length() > 0)) {
							entityIdsForFormsBuilder.append(",");
						}
						entityIdsForFormsBuilder.append(formField.getFieldValue());
					}

					if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_LIST) {
						
						if ((entityIdsForFormsBuilder.length() > 0)) {
							entityIdsForFormsBuilder.append(",");
						}
						entityIdsForFormsBuilder.append(formField.getFieldValue());
					}

					if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE) {
						
						if ((formEmpIdsBuilder.length() > 0)) {
							formEmpIdsBuilder.append(",");
						}
						formEmpIdsBuilder.append(formField.getFieldValue());
					}
					
					if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOMER) {
						
						if ((custIdsBuilder.length() > 0)) {
							custIdsBuilder.append(",");
						}
						custIdsBuilder.append(formField.getFieldValue());
					}
					
					if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOM_ENTITY || 
							formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOM_ENTITY) {
						if ((customEntityIdsForFormsBuilder.length() > 0)) {
							customEntityIdsForFormsBuilder.append(",");
						}
						customEntityIdsForFormsBuilder.append(formField.getFieldValue());
					}
					
					
					
					
					if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {

						Api.convertDateTimesToGivenType(formField,
								DateConversionType.STADARD_TO_XSD, "fieldValue");

					}

				}

				for (FormSectionField formSectionField : formSectionFields1) {
					FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
							.get(formSectionField.getSectionFieldSpecId());

					/*if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE) {
						if (!Api.isEmptyString(formEmpIds)) {
							formEmpIds += ",";
						}
						formEmpIds += formSectionField.getFieldValue();
					}

					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER) {
						if (!Api.isEmptyString(custIds)) {
							custIds += ",";
						}
						custIds += formSectionField.getFieldValue();
					}

					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_LIST) {
						if (!Api.isEmptyString(entityIdsForForms)) {
							entityIdsForForms += ",";
						}
						entityIdsForForms += formSectionField.getFieldValue();
					}

					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_LIST) {
						if (!Api.isEmptyString(entityIdsForForms)) {
							entityIdsForForms += ",";
						}
						entityIdsForForms += formSectionField.getFieldValue();
					}*/
					
					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER) {
						
						if ((custIdsBuilder.length() > 0)) {
							custIdsBuilder.append(",");
						}
						custIdsBuilder.append(formSectionField.getFieldValue());
					}

					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_LIST) {
						
						if ((entityIdsForFormsBuilder.length() > 0)) {
							entityIdsForFormsBuilder.append(",");
						}
						entityIdsForFormsBuilder.append(formSectionField.getFieldValue());
					}

					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_LIST) {
						
						if ((entityIdsForFormsBuilder.length() > 0)) {
							entityIdsForFormsBuilder.append(",");
						}
						entityIdsForFormsBuilder.append(formSectionField.getFieldValue());
					}

					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE) {
						
						if ((formEmpIdsBuilder.length() > 0)) {
							formEmpIdsBuilder.append(",");
						}
						formEmpIdsBuilder.append(formSectionField.getFieldValue());
					}
					
					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOMER) {
						
						if ((custIdsBuilder.length() > 0)) {
							custIdsBuilder.append(",");
						}
						custIdsBuilder.append(formSectionField.getFieldValue());
					}
					
					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOM_ENTITY || 
							formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_CUSTOM_ENTITY) {
						if ((customEntityIdsForFormsBuilder.length() > 0)) {
							customEntityIdsForFormsBuilder.append(",");
						}
						customEntityIdsForFormsBuilder.append(formSectionField.getFieldValue());
					}

					if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_DATE_TIME) {

						Api.convertDateTimesToGivenType(formSectionField,
								DateConversionType.STADARD_TO_XSD, "fieldValue");

					}
				}
				
				if (!Api.isEmptyString(custIds) && (custIdsBuilder.length() > 0)) {
					custIds += ",";
				}
				custIds += custIdsBuilder.toString();
				
				if (!Api.isEmptyString(entityIdsForForms) && (entityIdsForFormsBuilder.length() > 0)) {
					entityIdsForForms += ",";
				}
				entityIdsForForms += entityIdsForFormsBuilder.toString();
				
				if (!Api.isEmptyString(formEmpIds) && (formEmpIdsBuilder.length() > 0)) {
					formEmpIds += ",";
				}
				formEmpIds += formEmpIdsBuilder.toString();
				
				if (!Api.isEmptyString(customEntityIdsForForms) && (customEntityIdsForFormsBuilder.length() > 0)) {
					custIds += ",";
				}
				customEntityIdsForForms += customEntityIdsForFormsBuilder.toString();
				
			}
		

		public List<Entity> getExtraEntitesForEntites(List<Entity> entities) {
			List<Long> existedIds = Api.csvToLongList(Api.toCSVFromList(entities,
					"entityId"));
			List<Long> extraEntityIds = resolveListInLists(existedIds, existedIds);
			List<Entity> extraEntities = extraDao.getEntitiesIn(Api
					.toCSV(extraEntityIds));
			entities.addAll(extraEntities);
			return extraEntities;
		}
		private List<Long> resolveListInLists(List<Long> extraListIds,
				List<Long> existedIds) {
			List<Long> extra = new ArrayList<Long>();
			if (!extraListIds.isEmpty()) {
				List<Long> entityIds = extraDao.getEntityIdsForEntity(Api
						.toCSV(extraListIds));
				entityIds.removeAll(existedIds);
				existedIds.addAll(entityIds);
				extra.addAll(entityIds);
				extra.addAll(resolveListInLists(entityIds, existedIds));

			}

			return extra;
		}
	 
		
		public void processLocation(Location location) throws ParseException,
		EffortError {
			try {
		        if (!Api.isEmptyString(location.getGpsFixTime())) {
		            ZonedDateTime zonedDateTime = ZonedDateTime.parse(location.getGpsFixTime(), DateTimeFormatter.ISO_DATE_TIME);
		            long gpsFixTimeMillis = zonedDateTime.toInstant().toEpochMilli();

		            if (Math.abs(gpsFixTimeMillis) <= getMaxDateAllowed().getTime()) {
		                // Convert ZonedDateTime to Calendar
		                Calendar calendar = Calendar.getInstance();
		                calendar.setTimeInMillis(gpsFixTimeMillis);

		                // Now call getDateTimeInUTC with Calendar
		                String dateTime = Api.getDateTimeInUTC(calendar);
		                location.setGpsFixTime(dateTime);
		            } else {
		                throw new EffortError(4005, HttpStatus.PRECONDITION_FAILED);
		            }
		        }
		    } catch (IllegalArgumentException e) {
		        Log.info(this.getClass(), e.toString(), e);
		        throw new EffortError(4005, HttpStatus.PRECONDITION_FAILED);
		    }

	try {
	    if (!Api.isEmptyString(location.getClientTime())) {
	        ZonedDateTime zonedDateTime = ZonedDateTime.parse(location.getClientTime(), DateTimeFormatter.ISO_DATE_TIME);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(zonedDateTime.toInstant().toEpochMilli());
	        if (Math.abs(calendar.getTimeInMillis()) <= getMaxDateAllowed().getTime()) {
	            String dateTime = Api.getDateTimeInUTC(calendar);
	            location.setClientTime(dateTime);
	        } else {
	            throw new EffortError(4005, HttpStatus.PRECONDITION_FAILED);
	        }
	    }
	}  catch (IllegalArgumentException e) {
		Log.info(this.getClass(), e.toString(), e);
		throw new EffortError(4005, HttpStatus.PRECONDITION_FAILED);
	}


	try {
	    if (!Api.isEmptyString(location.getClientTime())) {
	        ZonedDateTime zonedDateTime = ZonedDateTime.parse(location.getClientTime(), DateTimeFormatter.ISO_DATE_TIME);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(zonedDateTime.toInstant().toEpochMilli());
	        if (Math.abs(calendar.getTimeInMillis()) <= getMaxDateAllowed().getTime()) {
	            String dateTime = Api.getDateTimeInUTC(calendar);
	            location.setClientTime(dateTime);
	        } else {
	            throw new EffortError(4005, HttpStatus.PRECONDITION_FAILED);
	        }
	    }
	} catch (IllegalArgumentException e) {
		Log.info(this.getClass(), e.toString(), e);
		throw new EffortError(4005, HttpStatus.PRECONDITION_FAILED);
	}

	try {
	    if (!Api.isEmptyString(location.getClientTime())) {
	        ZonedDateTime zonedDateTime = ZonedDateTime.parse(location.getClientTime(), DateTimeFormatter.ISO_DATE_TIME);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(zonedDateTime.toInstant().toEpochMilli());
	        if (Math.abs(calendar.getTimeInMillis()) <= getMaxDateAllowed().getTime()) {
	            String dateTime = Api.getDateTimeInUTC(calendar);
	            location.setClientTime(dateTime);
	        } else {
	            throw new EffortError(4005, HttpStatus.PRECONDITION_FAILED);
	        }
	    }
	}  catch (IllegalArgumentException e) {
		Log.info(this.getClass(), e.toString(), e);
		throw new EffortError(4005, HttpStatus.PRECONDITION_FAILED);
	}

	if (location.getUnknownLat() != null
			&& location.getUnknownLng() != null) {
		// use unknown
		location.setLatitude(location.getUnknownLat());
		location.setLongitude(location.getUnknownLng());
		location.setAccuracy(location.getUnknownAccuracy());
		location.setMedium(0);
		location.setFixTime(location.getUnknownFixTime());
		location.setResolvedBy(1);
		return;
	}

	if ((location.getGpsLat() == null || location.getGpsLng() == null)
			&& (location.getCellLat() == null || location.getCellLng() == null)) {

		// null location
		return;
	}

	if (location.getGpsLat() == null || location.getGpsLng() == null) {
		// use network
		location.setLatitude(location.getCellLat());
		location.setLongitude(location.getCellLng());
		location.setAccuracy(location.getCellAccuracy());
		location.setMedium(2);
		location.setFixTime(location.getCellFixTime());
		location.setResolvedBy(2);
		return;
	} else if (location.getCellLat() == null
			|| location.getCellLng() == null) {
		// use gps
		location.setLatitude(location.getGpsLat());
		location.setLongitude(location.getGpsLng());
		location.setAltitude(location.getGpsAlt());
		location.setAccuracy(location.getGpsAccuracy());
		location.setMedium(1);
		location.setFixTime(location.getGpsFixTime());
		location.setResolvedBy(3);
		return;
	}

	if (location.isGpsProvider()) {
		// use gps
		location.setLatitude(location.getGpsLat());
		location.setLongitude(location.getGpsLng());
		location.setAltitude(location.getGpsAlt());
		location.setAccuracy(location.getGpsAccuracy());
		location.setMedium(1);
		location.setFixTime(location.getGpsFixTime());
		location.setResolvedBy(13);
		return;
	}

	// if(location.isGpsProvider() && location.isCellProvider()){
	// Double gpsAccuracy = location.getGpsAccuracy();
	// Double cellAccuracy = location.getCellAccuracy();
	//
	// if(gpsAccuracy == null){
	// gpsAccuracy = constants.getDefaultGpsAccuracy();
	// }
	//
	// if(cellAccuracy == null){
	// cellAccuracy = constants.getDefaultCellAccuracy();
	// }
	//
	// double dist = Api.latLongDistanceInMeter(location.getGpsLat(),
	// location.getGpsLng(), location.getCellLat(), location.getCellLng());
	// double threshold = 2 * (gpsAccuracy + cellAccuracy);
	//
	// if(dist <= threshold){
	// // use gps
	// location.setLatitude(location.getGpsLat());
	// location.setLongitude(location.getGpsLng());
	// location.setAltitude(location.getGpsAlt());
	// location.setAccuracy(location.getGpsAccuracy());
	// location.setMedium(1);
	// location.setFixTime(location.getGpsFixTime());
	// location.setResolvedBy(13);
	// } else {
	// location.setResolvedBy(12);
	// }
	// return;
	// }

	if (!location.isGpsProvider() && location.isCellProvider()) {
		// use network
		location.setLatitude(location.getCellLat());
		location.setLongitude(location.getCellLng());
		location.setAccuracy(location.getCellAccuracy());
		location.setMedium(2);
		location.setFixTime(location.getCellFixTime());
		location.setResolvedBy(4);
	}

	if (location.isGpsProvider() && !location.isCellProvider()) {
		// use gps
		location.setLatitude(location.getGpsLat());
		location.setLongitude(location.getGpsLng());
		location.setAltitude(location.getGpsAlt());
		location.setAccuracy(location.getGpsAccuracy());
		location.setMedium(1);
		location.setFixTime(location.getGpsFixTime());
		location.setResolvedBy(5);
		return;
	}

	if (!location.isGpsProvider() && !location.isCellProvider()) {
		location.setResolvedBy(6);
		return;
	}

	Double gpsAccuracy = location.getGpsAccuracy();
	Double cellAccuracy = location.getCellAccuracy();

	if (gpsAccuracy == null) {
		String glGPSThreshold = constants.getGlGPSThreshold();
		gpsAccuracy = glGPSThreshold == null ? 0 : Double
				.parseDouble(glGPSThreshold);
	}

	if (cellAccuracy == null) {
		String glSTThreshold = constants.getGlSTThreshold();
		cellAccuracy = glSTThreshold == null ? 0 : Double
				.parseDouble(glSTThreshold);
	}

	// double dist = Api.latLongDistanceInMeter(location.getGpsLat(),
	// location.getGpsLng(), location.getCellLat(), location.getCellLng());
	// if(dist > (gpsAccuracy.doubleValue() + cellAccuracy.doubleValue())){
	// location.setResolvedBy(12);
	// return;
	// }

	final int TWO_MINUTES = 1000 * 60 * 2;

	long timeDelta = (Api.getDateTimeInUTC(location.getGpsFixTime())
			.getTime() - Api.getDateTimeInUTC(location.getCellFixTime())
			.getTime());
	long timeDeltaAbs = Math.abs(timeDelta);
	boolean isSignificantlyNewer = (timeDeltaAbs > TWO_MINUTES && timeDelta >= 0);
	boolean isSignificantlyOlder = (timeDeltaAbs > TWO_MINUTES && timeDelta < 0);
	boolean isNewer = timeDelta > 0;

	if (isSignificantlyNewer) {
		// use gps
		location.setLatitude(location.getGpsLat());
		location.setLongitude(location.getGpsLng());
		location.setAltitude(location.getGpsAlt());
		location.setAccuracy(location.getGpsAccuracy());
		location.setMedium(1);
		location.setFixTime(location.getGpsFixTime());
		location.setResolvedBy(7);
		return;
	} else if (isSignificantlyOlder) {
		// use network
		location.setLatitude(location.getCellLat());
		location.setLongitude(location.getCellLng());
		location.setAccuracy(location.getCellAccuracy());
		location.setMedium(2);
		location.setFixTime(location.getCellFixTime());
		location.setResolvedBy(8);
		return;
	}

	// if(location.getCellAccuracy() == null){
	// // use gps
	// location.setLatitude(location.getGpsLat());
	// location.setLongitude(location.getGpsLng());
	// location.setAltitude(location.getGpsAlt());
	// location.setAccuracy(location.getGpsAccuracy());
	// location.setMedium(1);
	// location.setFixTime(location.getGpsFixTime());
	// return;
	// }
	//
	// if(location.getGpsAccuracy() == null){
	// // use network
	// location.setLatitude(location.getCellLat());
	// location.setLongitude(location.getCellLng());
	// location.setAccuracy(location.getCellAccuracy());
	// location.setMedium(2);
	// location.setFixTime(location.getCellFixTime());
	// return;
	// }

	double accuracyDelta = gpsAccuracy - cellAccuracy;
	boolean isLessAccurate = accuracyDelta > 0;
	boolean isMoreAccurate = accuracyDelta < 0;
	// boolean isSignificantlyLessAccurate = accuracyDelta > 250;

	if (isMoreAccurate) {
		// use gps
		location.setLatitude(location.getGpsLat());
		location.setLongitude(location.getGpsLng());
		location.setAltitude(location.getGpsAlt());
		location.setAccuracy(location.getGpsAccuracy());
		location.setMedium(1);
		location.setFixTime(location.getGpsFixTime());
		location.setResolvedBy(9);
		return;
	} else if (isNewer && !isLessAccurate) {
		// use gps
		location.setLatitude(location.getGpsLat());
		location.setLongitude(location.getGpsLng());
		location.setAltitude(location.getGpsAlt());
		location.setAccuracy(location.getGpsAccuracy());
		location.setMedium(1);
		location.setFixTime(location.getGpsFixTime());
		location.setResolvedBy(10);
		return;
	} else {
		// use network
		location.setLatitude(location.getCellLat());
		location.setLongitude(location.getCellLng());
		location.setAccuracy(location.getCellAccuracy());
		location.setMedium(2);
		location.setFixTime(location.getCellFixTime());
		location.setResolvedBy(11);
		return;
	}

}
		public void populateCustomEntityForm(List<Form> forms,
				List<FormField> fields, List<CustomEntity> customEntities) {
			if (customEntities != null) {
				String formIdsForCustomers = Api.toCSV(customEntities, "formId",
						Api.CsvOptions.FILTER_NULL_OR_EMPTY);
				if (!Api.isEmptyString(formIdsForCustomers)) {
					List<Form> formsForCustomers = extraSupportAdditionalDao
							.getMasterFormsIn(formIdsForCustomers);
					List<FormField> formFieldsForCsutomerForms = extraSupportAdditionalDao
							.getMasterFormFieldsForSync(formIdsForCustomers);
					forms.removeAll(formsForCustomers);
					fields.removeAll(formFieldsForCsutomerForms);
					forms.addAll(formsForCustomers);
					fields.addAll(formFieldsForCsutomerForms);
				}
			}

		}

		
		private Date getMaxDateAllowed() {
			try {
				return Api.getDateTimeInUTC("2900-01-01 00:00:00");
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
}
