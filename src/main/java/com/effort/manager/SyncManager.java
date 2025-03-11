package com.effort.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import com.effort.beans.http.request.location.Location;
import com.effort.beans.http.response.extra.FormSubmissionData;
import com.effort.context.AppContext;
import com.effort.dao.ConfiguratorDao;
import com.effort.dao.ExtendedDao;
import com.effort.dao.ExtraDao;
import com.effort.dao.ExtraSupportAdditionalDao;
import com.effort.dao.ExtraSupportAdditionalSupportDao;
import com.effort.dao.ExtraSupportDao;
import com.effort.dao.WorkFlowExtraDao;
import com.effort.entity.ActivityStream;
import com.effort.entity.CustomEntityRequisitionJmsMessageStatus;
import com.effort.entity.CustomEntitySpecStockConfiguration;
import com.effort.entity.CustomerOnDemandMapping;
import com.effort.entity.CustomerRequisitionJmsMessageStatus;
import com.effort.entity.EffortAccessSettings;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeEventSubmissions;
import com.effort.entity.EmployeeEventSubmissionsAuditLogs;
import com.effort.entity.EmployeeOnDemandMapping;
import com.effort.entity.EmployeeRequisitionForm;
import com.effort.entity.EmployeeRequisitionJmsMessageStatus;
import com.effort.entity.EmployeeTargetsConfiguration;
import com.effort.entity.Form;
import com.effort.entity.FormCustomEntityUpdate;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormListUpdate;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.JmsMessage;
import com.effort.entity.OfflineCustomEntityUpdateConfiguration;
import com.effort.entity.OfflineListUpdateConfiguration;
import com.effort.entity.Payment;
import com.effort.entity.WebAppTool;
import com.effort.entity.WebUser;
import com.effort.entity.Work;
import com.effort.entity.WorkFlowFormStatus;
import com.effort.entity.WorkFlowFormStatusHistory;
import com.effort.entity.WorkFlowRealData;
import com.effort.entity.WorkRequisitionJmsMessageStatus;
import com.effort.entity.Workflow;
import com.effort.entity.WorkflowStage;
import com.effort.exception.EffortError;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.settings.ReportConstants;
import com.effort.util.Api;
import com.effort.util.Api.CsvOptions;
import com.effort.util.Api.DateConversionType;
import com.effort.util.ContextUtils;
import com.effort.util.Log;
import com.effort.util.SecurityUtils;
import com.effort.validators.FormValidator;
@Service
public class SyncManager {
	
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
	public void sendMessages(List<JmsMessage> jmsMessages,long empId) {
		
		if (jmsMessages != null && !jmsMessages.isEmpty()) {
			for (JmsMessage message : jmsMessages) {
				try {
					mainManager.sendJMSMessage(message);
				} catch (Exception e) {
					Log.error(this.getClass(),"sync() // EmpId: " + empId + ", JMS Message : " + message + e.toString(), e);
				}
			}
		}
	}
	

	public List<Long> processFormSubmissionSync(FormSubmissionData formSubmissionData, String empId, String code,
		String ipAddress, Employee employee, Map<String, Long> formOldClientKeys, Map<String, Long> formNewKeys,
		Map<Long, String> formClientKeys, Map<String, Long> customerNewKeys,
		Map<Long, String> formWorkflowStatusClientKeys, Map<String, Long> customEntityNewKeys,
		Map<String, Boolean> customerMandatoryFormFieldsMissingMap, List<Form> formsNotInDb, List<JmsMessage> jmsMessages) throws EffortError {	
	
	String hasAutoComputeCapability= "";
	String approvalAware="";
	String platformId="";
	
	Set<Long> workFlowFormIds =  new HashSet<Long>();
	Set<Long> syncAddedOrModifedForms = new HashSet<Long>();
	int apiLevel=0;
	Set<Long> syncAddedOrModifedMasterForms = new HashSet<Long>();
	List<Long> hardDeletedFormIdsInReq =  new ArrayList<Long>();
	String clientVersion="";
	
	List<Long> addedModifiedFormIds = new ArrayList<Long>();

	List<FormField> fields = formSubmissionData.getFormDataContainer().getFields();

	List<FormSectionField> sectionFields = formSubmissionData.getFormDataContainer()
			.getSectionFields();
	

	List<Payment> payments = new ArrayList<Payment>();
	Map<Long, List<FormField>> oldFormFieldsMap = new HashMap<Long, List<FormField>>();
	Long paymentsCurrentTime = Api.getCurrentTimeInUTCLong();
	if (payments != null && payments.size() > 0) {
		for (Payment payment : payments) {
			Api.convertDateTimesToGivenType(payment, DateConversionType.XSD_TO_STADARD, "createdTime",
					"modifiedTime");

			List<Payment> oldPayments = new ArrayList<Payment>();
			oldPayments = extraDao.getPaymentByClientId(payment.getClientId());
			if (oldPayments == null || oldPayments.size() == 0)
				extraDao.insertPayment(payment);
			else
				extraDao.updatePaymentByClientId(payment);
		}
	}

	if (constants.isPrintLogs())
		Log.info(this.getClass(), "getFormContainer-paymentsCurrentTime End. Time taken to process for empId "
				+ empId + " : " + (Api.getCurrentTimeInUTCLong() - paymentsCurrentTime));

	Long ignoreUpdateFieldsCurrentTime = Api.getCurrentTimeInUTCLong();
	List<FormField> ignoreUpdateFields = new ArrayList<FormField>();
	Iterator<FormField> formFieldIterator = fields.iterator();
	while (formFieldIterator.hasNext()) {
		FormField formField = (FormField) formFieldIterator.next();
		if (formField.getCanIgnoreUpdate() == 1) {
			ignoreUpdateFields.add(formField);
			formFieldIterator.remove();
		}
	}

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-ignoreUpdateFieldsCurrentTime End. Time taken to process for empId " + empId
						+ " : " + (Api.getCurrentTimeInUTCLong() - ignoreUpdateFieldsCurrentTime));

	Long ignoreUpdateSectionFieldsCurrentTime = Api.getCurrentTimeInUTCLong();
	List<FormSectionField> ignoreUpdateSectionFields = new ArrayList<FormSectionField>();
	Iterator<FormSectionField> sectionFieldIterator = sectionFields.iterator();
	while (sectionFieldIterator.hasNext()) {
		FormSectionField formSectionField = (FormSectionField) sectionFieldIterator.next();
		if (formSectionField.getCanIgnoreUpdate() == 1) {
			ignoreUpdateSectionFields.add(formSectionField);
			sectionFieldIterator.remove();
		}
	}

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-ignoreUpdateSectionFieldsCurrentTime End. Time taken to process for empId "
						+ empId + " : " + (Api.getCurrentTimeInUTCLong() - ignoreUpdateSectionFieldsCurrentTime));

	List<Form> added = formSubmissionData.getFormDataContainer().getAdded();

	List<Form> modified = formSubmissionData.getFormDataContainer().getModified();

	String ids = "";
	for (Form form : added) {
		if (!Api.isEmptyString(ids)) {
			ids += ",";
		}

		ids += "'" + form.getFormSpecId() + "'";
	}
	for (Form form : modified) {
		if (!Api.isEmptyString(ids)) {
			ids += ",";
		}

		ids += "'" + form.getFormSpecId() + "'";
	}

	List<FormSpec> formSpecs = webManager.getFormSpecsIn(ids);
	Map<Long, Boolean> formSpecAndMasterFormFlagMap = new HashMap<Long, Boolean>();
	Map<Long, Boolean> formSpecAndCustomerMasterFormFlagMap = new HashMap<Long, Boolean>();
	
	List<Long> modifiedCustomerFormsIds = new ArrayList<Long>();
	Set<Long> customerFormSpecIds = new HashSet<Long>();
	for (FormSpec formSpec : formSpecs) {
		if (formSpec.getPurpose() == -1 || formSpec.getPurpose() == 0
				|| formSpec.getPurpose() == FormSpec.PURPOUSE_WORK
				|| formSpec.getPurpose() == FormSpec.PURPOUSE_JOB) {
			formSpecAndMasterFormFlagMap.put(formSpec.getFormSpecId(), false);
		} else {
			formSpecAndMasterFormFlagMap.put(formSpec.getFormSpecId(), true);
		}
		
		if(formSpec.getPurpose() == FormSpec.PURPOUSE_CUSTOMER || formSpec.getPurpose() == FormSpec.PURPOUSE_CUSTOMER_CREATION)
		{
			formSpecAndCustomerMasterFormFlagMap.put(formSpec.getFormSpecId(), true);
			customerFormSpecIds.add(formSpec.getFormSpecId());
		}
	}
	WebUser user = new WebUser();
	user.setCompanyId(employee.getCompanyId());
	user.setEmpId(employee.getEmpId());
	boolean customerEditPermission = true;
	if(!formSpecAndCustomerMasterFormFlagMap.isEmpty()){
		EffortAccessSettings accessSettings = webManager.getEffortAccessSettings(user);
		if(!getContextUtils().hasPermission("foremp","COMP_EMP_CUSTOMER_WRITE",accessSettings))
		{
			customerEditPermission = false;
		}
	}
	
	Long formFieldSpecMapCurrentTime = Api.getCurrentTimeInUTCLong();
	Map<Long, FormFieldSpec> formFieldSpecMap = extraDao.getFormFieldSpecMapIn(ids);
	
	Map<Long, FormFieldSpec> customerFormFieldSpecMap = new HashMap<Long,FormFieldSpec>();
	
	Map<String,FormFieldSpec> uniqueIdAndCustomerFormFieldSpecMap = new HashMap<String,FormFieldSpec>();
	if(customerFormSpecIds!=null && customerFormSpecIds.size()>0){
		if(!customerEditPermission){
			String customerFormSpecIdsCsv = Api.toCSV(customerFormSpecIds);
			List<FormFieldSpec> customerFormFieldSpecs = extraDao.getFormFieldSpecForFieldSpecIdsIn(customerFormSpecIdsCsv);
			if(customerFormFieldSpecs!=null && customerFormFieldSpecs.size()>0){
				uniqueIdAndCustomerFormFieldSpecMap = (Map) Api.getMapFromList(customerFormFieldSpecs, "uniqueId");
			}
			customerFormFieldSpecMap = extraDao.getFormFieldSpecMapForFields(customerFormSpecIdsCsv);
		}
	}
	

	Long formSectionFieldSpecMapCurrentTime = Api.getCurrentTimeInUTCLong();
	Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap = extraDao.getFormSectionFieldSpecForFormSpecMap(ids);

	List<FormField> restrictFromMobileFields = new ArrayList<FormField>(); 
	List<FormSectionField> restrictFromMobileSectionFields = new ArrayList<FormSectionField>();
	getWebAdditionalSupportExtraManager().resolveFieldsForIgnoreUpdateFields(fields, sectionFields,
			ignoreUpdateFields, ignoreUpdateSectionFields,restrictFromMobileFields,restrictFromMobileSectionFields,
			formFieldSpecMap, formSectionFieldSpecMap);

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-formSectionFieldSpecMapCurrentTime End. Time taken to process for empId " + empId
						+ " : " + (Api.getCurrentTimeInUTCLong() - formSectionFieldSpecMapCurrentTime));

	String ignoreUpdateformFieldSpecsCsv = Api.toCSVFromList(ignoreUpdateFields, "fieldSpecId");
	String ignoreUpdateFormSectionFieldSpecIds = Api.toCSVFromList(ignoreUpdateSectionFields, "sectionFieldSpecId");

	Long formOldClientKeysCurrentTime = Api.getCurrentTimeInUTCLong();

	List<Form> activityAddedForms = new ArrayList<Form>();

	List<Form> addedForms = new ArrayList<Form>();
	List<Form> addedMasterForms = new ArrayList<Form>();

	for (Form form : added) {
		if (formSpecAndMasterFormFlagMap.get(form.getFormSpecId())) {
			addedMasterForms.add(form);
		} else {
			addedForms.add(form);
		}
	}
	formOldClientKeys = extraDao.getFormClientSideIds(addedForms, code);

	Map<String, Long> masterFormOldClientKeys = extraSupportAdditionalDao
			.getMasterFormClientSideIds(addedMasterForms, code);

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-formOldClientKeysCurrentTime End. Time taken to process for empId " + empId
						+ " : " + (Api.getCurrentTimeInUTCLong() - formOldClientKeysCurrentTime));

	Long formsAddedCurrentTime = Api.getCurrentTimeInUTCLong();
	for (Form form : added) {

		List<ObjectError> errorsTemp = new ArrayList<ObjectError>();
		Long formsAddedvalidateFormCurrentTime = Api.getCurrentTimeInUTCLong();
		formValidator.validateForm(form, employee.getCompanyId(), errorsTemp);

		if (constants.isPrintLogs())
			Log.info(this.getClass(),
					"getFormContainer-formsAddedvalidateFormCurrentTime End. Time taken to process for empId "
							+ empId + " : " + (Api.getCurrentTimeInUTCLong() - formsAddedvalidateFormCurrentTime));

		if (errorsTemp.size() > 0) {
			throw new EffortError(4033, errorToString(errorsTemp), HttpStatus.PRECONDITION_FAILED);
		} else {
			form.setAssignTo(employee.getEmpId());
			form.setFilledBy(employee.getEmpId());

			if (form.getClientFormId() != null) {

				Long id = null;
				if (formSpecAndMasterFormFlagMap.get(form.getFormSpecId())) {
					id = masterFormOldClientKeys.get(form.getClientFormId());
				} else {
					id = formOldClientKeys.get(form.getClientFormId());
				}

				if (id == null) {
					//
					Location location = form.getLocation();
					List<Location> locations = form.getLocations();
					if (locations == null) {
						locations = new ArrayList<Location>();
					}

					if (location != null) {
						locations.add(location);
					}
					Long formsAddedLocationCurrentTime = Api.getCurrentTimeInUTCLong();
					mainManager.processAndSaveListOfLocations(locations, employee, jmsMessages);

					if (constants.isPrintLogs())
						Log.info(this.getClass(),
								"getFormContainer-formsAddedLocationCurrentTime End. Time taken to process for empId "
										+ empId + " : "
										+ (Api.getCurrentTimeInUTCLong() - formsAddedLocationCurrentTime));

					if (locations != null && locations.size() > 0) {
						Location lastLocation = locations.get(locations.size() - 1);
						form.setLocation(lastLocation);
					}
					Long formsAddedinsertFormCurrentTime = Api.getCurrentTimeInUTCLong();
					boolean isMasterForm = formSpecAndMasterFormFlagMap.get(form.getFormSpecId());
					form.setSourceOfAction(Work.ANDROID);
					Log.info(this.getClass(),"getFormContainer- form createdTime : "+form.getCreatedTime());
					extraDao.insertForm(form, employee.getCompanyId(), employee.getEmpId(), code, isMasterForm, clientVersion);
					FormSpec formSpec = webManager.getFormSpec(form.getFormSpecId()+"");
					if(formSpec.getPurpose() == -1)
					{
						CustomEntitySpecStockConfiguration customEntitySpecStockConfiguration = getExtraSupportAdditionalSupportDao().getCustomEntitySpecStockConfigurationByFormSpecUniqueId(formSpec.getUniqueId(),0);
						
						if(!Api.isEmptyObj(customEntitySpecStockConfiguration))
						{
							getExtraSupportAdditionalSupportDao().insertIntoCustomEntityCreationOnStockUpdation(form.getFormId(),customEntitySpecStockConfiguration);
						}
					}
					if (constants.isPrintLogs())
						Log.info(this.getClass(),
								"getFormContainer-formsAddedinsertFormCurrentTime End. Time taken to process for empId "
										+ empId + " : "
										+ (Api.getCurrentTimeInUTCLong() - formsAddedinsertFormCurrentTime));

					Long formsAddedinsertFormLocationListCurrentTime = Api.getCurrentTimeInUTCLong();
					/*if(form.isMediasCommitted())
					{*/
						Long currentTimeTYPE_FORM = Api.getCurrentTimeInUTCLong();
							/*webManager.sendJmsMessage(JmsMessage.TYPE_FORM, -1, form.getCompanyId(), Long.parseLong(empId), JmsMessage.CHANGE_TYPE_ADD, form.getFormId(),
									null, true, jmsMessages);*/
						 
						if(form.getListUpdateStatus() == 0 && !isMasterForm) {
							Log.info(getClass(), "TYPE_LIST_UPDATE_FORM companyId = "+employee.getCompanyId()+" formId = "+form.getFormId());
							List<OfflineListUpdateConfiguration> offlineListUpdateConfigurations = extraSupportAdditionalDao.getFormSpecOfflineListUpdateConfiguration(formSpec);
							if(offlineListUpdateConfigurations!=null && !offlineListUpdateConfigurations.isEmpty()) {
								
								if(constantsExtra.isAsynchronousProcess()){
									Log.info(getClass(), "OfflineListUpdateConfiguration logged through activeMq for formId :"+form.getFormId());
									webManager.sendJmsMessage(JmsMessage.TYPE_LIST_UPDATE_FORM, -1, employee.getCompanyId(), Long.parseLong(empId), JmsMessage.CHANGE_TYPE_ADD, form.getFormId(),
											null, false, jmsMessages);
								}else{
										FormListUpdate formListUpdate = new FormListUpdate();
										formListUpdate.setFormId(form.getFormId());
										formListUpdate.setCompanyId(Long.parseLong(employee.getCompanyId()+""));
										getWebAdditionalSupportExtraManager().insertOrUpdateFormListUpdates(formListUpdate);
										Log.info(getClass(), "OfflineListUpdateConfiguration logged through sequential for formId :"+form.getFormId());
										getWebAdditionalSupportExtraManager().insertFormListUpdateAuditLogs(formListUpdate.getFormId());
								}
							}
							
						}
						
					if (form.getCustomEntityUpdateStatus() == 0 && !isMasterForm) {
						Log.info(getClass(), "TYPE_CUSTOM_ENTITY_UPDATE_FORM companyId=" + employee.getCompanyId() + "formId=" + form.getFormId());
						List<OfflineCustomEntityUpdateConfiguration> offlineCustomEntityUpdateConfigurations = configuratorDao
								.getFormSpecOfflineCustomEntityUpdateConfiguration(formSpec);
						if(offlineCustomEntityUpdateConfigurations!=null && !offlineCustomEntityUpdateConfigurations.isEmpty()) {
							if (constantsExtra.isAsynchronousCustomEntityProcess()) {
								Log.info(getClass(), "OfflineCustomEntityUpdateConfiguration formId:" + form.getFormId());
								webManager.sendJmsMessage(JmsMessage.TYPE_CUSTOM_ENTITY_UPDATE_FORM, -1, employee.getCompanyId(), Long.parseLong(empId), JmsMessage.CHANGE_TYPE_ADD, form.getFormId(), null, false, jmsMessages);

							} else {
								FormCustomEntityUpdate formCustomEntityUpdate = new FormCustomEntityUpdate();
								formCustomEntityUpdate.setFormId(form.getFormId());
								formCustomEntityUpdate.setCompanyId(Long.parseLong(employee.getCompanyId() + ""));
								getConfiguratorManager().insertOrUpdateFormCustomEntityUpdates(formCustomEntityUpdate);
								Log.info(getClass(), "OfflineCustomEntityUpdateConfiguration logged through sequential for formId:"+ form.getFormId());
								getConfiguratorManager().insertFormCustomEntityUpdateAuditLogs(formCustomEntityUpdate.getFormId());
							}
						}
						
					}
						if(constants.isPrintLogs())
							Log.info(this.getClass(), "JmsMessage for TYPE_FORM_CHANGE_TYPE_ADD End. Time taken to process for empId "+empId+" : "+(Api.getCurrentTimeInUTCLong() - currentTimeTYPE_FORM));
					//}
						
						if (formSpec.getPurpose() == FormSpec.PURPOUSE_CUSTOM_ENTITY) {
							webManager.sendJmsMessage(JmsMessage.TYPE_CUSTOM_ENTITY, form.getFormId(), employee.getCompanyId(), employee.getEmpId(), Constants.CHANGE_TYPE_ADD,
									form.getFormId(), null, false);
						}
						
					if(form.isMediasCommitted() && !isMasterForm) {
						List<WebAppTool> webApptools = extraSupportAdditionalDao.getWebAppToolsByFormSpecUniqueId(formSpec.getUniqueId());
						if(webApptools!=null && webApptools.size()>0) {
							webManager.sendJmsMessage(JmsMessage.TYPE_FORM_MEDIA_COMMITED, -1, form.getCompanyId(), Long.parseLong(empId), JmsMessage.CHANGE_TYPE_OTHER, form.getFormId(),
								null, true, jmsMessages);
						}
					}
					List<Location> formLocationsNew = new ArrayList<Location>();
					for (Location loc : locations) {
						if (loc.getLocationId() != 0) {
							formLocationsNew.add(loc);
						}
					}

					if (formLocationsNew.size() > 0) {
						extraDao.insertFormLocationList(formLocationsNew, form.getFormId(), 1);
					}

					if (constants.isPrintLogs())
						Log.info(this.getClass(),
								"getFormContainer-formsAddedinsertFormLocationListCurrentTime End. Time taken to process for empId "
										+ empId + " : " + (Api.getCurrentTimeInUTCLong()
												- formsAddedinsertFormLocationListCurrentTime));

					formNewKeys.put(form.getClientFormId(), form.getFormId());
					formClientKeys.put(form.getFormId(), form.getClientFormId());

					if (formSpecAndMasterFormFlagMap.get(form.getFormSpecId())) {
						syncAddedOrModifedMasterForms.add(form.getFormId());
					} else {
						syncAddedOrModifedForms.add(form.getFormId());
					}

					// Log.info(this.getClass(), "New Added Form Id:"+form.getFormId()+" And New
					// Added Client Side Id :"+ form.getClientFormId());

					try {
						Log.audit(employee.getCompanyId() + "", empId, "form", "add", form.toCSV(), null);
					} catch (Exception e) {
						Log.info(this.getClass(), e.toString(), e);
					}
					
					getExtraSupportAdditionalSupportDao().insertIntoFormExtra(form);

					activityAddedForms.add(form);
					try {
					    Log.info(this.getClass(), "addForm() // inside insertIntoFlatTableDataStatus()");
					    
			            getWebAdditionalSupportExtraManager().insertIntoFlatTableDataStatusForNormalForms(form, employee.getCompanyId(), formSpec.getUniqueId(),false);
			        }
			        catch(Exception ex) {
			            Log.info(this.getClass(), "addForm() // insertIntoFlatDataTables() Got Exception :-"+ex.toString());
			            ex.printStackTrace();
			        }
				} else {
					form.setFormId(id);
					if(hardDeletedFormIdsInReq.contains(id)) {
						continue;
					}
					Long formsAddedUpdatedRecordsCurrentTime = Api.getCurrentTimeInUTCLong();
					try {

						Form formOld = null;
						if (formSpecAndMasterFormFlagMap.get(form.getFormSpecId())) {
							formOld = extraSupportAdditionalDao.getMasterForm(form.getFormId());
						} else {
							formOld = extraDao.getForm(form.getFormId() + "");
						}
						 if(formOld == null){
                             formsNotInDb.add(form);
                         }

						form.setAssignTo(formOld.getAssignTo());
						
						if(!Api.isEmptyString(formOld.getLocationId()))
						{
							form.setLocationId(formOld.getLocationId());
						}
						
						Log.audit(employee.getCompanyId() + "", empId, "form", "modified", form.toCSV(),
								formOld.toCSV());

						List<FormField> formFields = new ArrayList<FormField>();
						if (formSpecAndMasterFormFlagMap.get(form.getFormSpecId())) {
							formFields = extraSupportAdditionalDao.getMasterFormFieldsByFormId(form.getFormId());
						} else {
							formFields = extraDao.getFormFieldByForm(form.getFormId());
						}

						oldFormFieldsMap.put(form.getFormId(), formFields);
						Log.audit(employee.getCompanyId() + "", empId, "formField", "deleted",
								formFields.toString(), null);
					} catch (Exception e) {
						Log.info(this.getClass(), e.toString(), e);
					}

					boolean isMasterForm = formSpecAndMasterFormFlagMap.get(form.getFormSpecId());

				//	form.setSourceOfModification(Work.ANDROID);
					extraDao.updateForm(form, employee.getEmpId(), isMasterForm, clientVersion);

					if (formSpecAndMasterFormFlagMap.get(form.getFormSpecId())) {
						syncAddedOrModifedMasterForms.add(form.getFormId());
					} else {
						syncAddedOrModifedForms.add(form.getFormId());
					}
					FormSpec formSpec = webManager.getFormSpec(form.getFormSpecId()+"");
					if(formSpec.getPurpose() == -1)
					{
						CustomEntitySpecStockConfiguration customEntitySpecStockConfiguration = getExtraSupportAdditionalSupportDao().getCustomEntitySpecStockConfigurationByFormSpecUniqueId(formSpec.getUniqueId(),0);
						
						if(!Api.isEmptyObj(customEntitySpecStockConfiguration))
						{
							getExtraSupportAdditionalSupportDao().insertIntoCustomEntityCreationOnStockUpdation(form.getFormId(),customEntitySpecStockConfiguration);
						}
					}
					
					boolean deleteFormFields = true;
					if(formSpecAndCustomerMasterFormFlagMap!=null && formSpecAndCustomerMasterFormFlagMap.get(form.getFormSpecId()) != null){
						if(!customerEditPermission){
							modifiedCustomerFormsIds.add(form.getFormId());
							deleteFormFields = false;
						}
						
					}
					

					/*if(form.isMediasCommitted())
					{*/
						Long currentTimeTYPE_FORM = Api.getCurrentTimeInUTCLong();
						if(!isMasterForm){
							webManager.sendJmsMessage(JmsMessage.TYPE_FORM, -1, form.getCompanyId(), Long.parseLong(empId), JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(),
									null, true, jmsMessages);
							CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus = new CustomerRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, -1, form.getCompanyId(), Long.parseLong(empId), JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(), true,form.getFormId());
							CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus = new CustomEntityRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, -1, form.getCompanyId(), Long.parseLong(empId), JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(), true,form.getFormId());
							WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus = new WorkRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, -1, form.getCompanyId(), Long.parseLong(empId), JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(), true,form.getFormId());
							EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus = new EmployeeRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, -1, form.getCompanyId(), Long.parseLong(empId), JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(), true,form.getFormId());
							getWebExtensionManager().getAllReqConfiurations(form.getFormSpecId(),customerRequisitionJmsMessageStatus,customEntityRequisitionJmsMessageStatus,workRequisitionJmsMessageStatus,employeeRequisitionJmsMessageStatus);
						
						}
						if (formSpec.getPurpose() == FormSpec.PURPOUSE_CUSTOM_ENTITY) {
							webManager.sendJmsMessage(JmsMessage.TYPE_CUSTOM_ENTITY, form.getFormId(), employee.getCompanyId(), employee.getEmpId(), Constants.CHANGE_TYPE_MODIFY,
									form.getFormId(), null, false);
						}
						if(form.getListUpdateStatus() == 0 && !isMasterForm) {
							Log.info(getClass(), "TYPE_LIST_UPDATE_FORM companyId = "+employee.getCompanyId()+" formId = "+form.getFormId());
							List<OfflineListUpdateConfiguration> offlineListUpdateConfigurations = extraSupportAdditionalDao.getFormSpecOfflineListUpdateConfiguration(formSpec);
							if(offlineListUpdateConfigurations!=null && !offlineListUpdateConfigurations.isEmpty()) {
								if(constantsExtra.isAsynchronousProcess()){
									Log.info(getClass(), "OfflineListUpdateConfiguration logged through activeMq for formId :"+form.getFormId());
									webManager.sendJmsMessage(JmsMessage.TYPE_LIST_UPDATE_FORM, -1, employee.getCompanyId(), Long.parseLong(empId), JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(),
											null, false, jmsMessages);
								}else{
									FormListUpdate formListUpdate = new FormListUpdate();
									formListUpdate.setFormId(form.getFormId());
									formListUpdate.setCompanyId(Long.parseLong(employee.getCompanyId()+""));
									getWebAdditionalSupportExtraManager().insertOrUpdateFormListUpdates(formListUpdate);
									Log.info(getClass(), "OfflineListUpdateConfiguration logged through sequential for formId :"+form.getFormId());
									getWebAdditionalSupportExtraManager().insertFormListUpdateAuditLogs(formListUpdate.getFormId());
								}
							}
							
						}
						
					if (form.getCustomEntityUpdateStatus() == 0 && !isMasterForm) {
						Log.info(getClass(), "TYPE_CUSTOM_ENTITY_UPDATE_FORM companyId=" + employee.getCompanyId()+ "formId=" + form.getFormId());
						List<OfflineCustomEntityUpdateConfiguration> offlineCustomEntityUpdateConfigurations = configuratorDao
								.getFormSpecOfflineCustomEntityUpdateConfiguration(formSpec);
						if(offlineCustomEntityUpdateConfigurations!=null && !offlineCustomEntityUpdateConfigurations.isEmpty()) {
							if (constantsExtra.isAsynchronousCustomEntityProcess()) {
								Log.info(getClass(),"OfflineCustomEntityUpdateConfiguration formId:" + form.getFormId());
								webManager.sendJmsMessage(JmsMessage.TYPE_CUSTOM_ENTITY_UPDATE_FORM, -1, employee.getCompanyId(), Long.parseLong(empId), JmsMessage.CHANGE_TYPE_MODIFY,
										form.getFormId(), null, false, jmsMessages);

							} else {
								FormCustomEntityUpdate formCustomEntityUpdate = new FormCustomEntityUpdate();
								formCustomEntityUpdate.setFormId(form.getFormId());
								formCustomEntityUpdate.setCompanyId(Long.parseLong(employee.getCompanyId() + ""));
								getConfiguratorManager().insertOrUpdateFormCustomEntityUpdates(formCustomEntityUpdate);
								Log.info(getClass(),"OfflineCustomEntityUpdateConfiguration logged through sequential for formId:"+ form.getFormId());
								getConfiguratorManager().insertFormCustomEntityUpdateAuditLogs(formCustomEntityUpdate.getFormId());
							}
						}
					}
					
					if(constants.isPrintLogs())
						Log.info(this.getClass(), "JmsMessage for TYPE_FORM_CHANGE_TYPE_MODIFY End. Time taken to process for empId "+empId+" : "+(Api.getCurrentTimeInUTCLong() - currentTimeTYPE_FORM));
					//}
//					if(!Api.isEmptyString(form.getLocationId())){
//						extraDao.insertFormLocation(form.getFormId(), form.getLocationId(), form.getModifiedTime());	
//					}
					if (form.isMediasCommitted() && !isMasterForm) {
						List<WebAppTool> webApptools = extraSupportAdditionalDao.getWebAppToolsByFormSpecUniqueId(formSpec.getUniqueId());
						if(webApptools!=null && webApptools.size()>0) {
							webManager.sendJmsMessage(JmsMessage.TYPE_FORM_MEDIA_COMMITED, -1, form.getCompanyId(),
								Long.parseLong(empId), JmsMessage.CHANGE_TYPE_OTHER, form.getFormId(), null, true,
								jmsMessages);
						}
					}

					if(deleteFormFields){
						extraDao.deleteFormCustomersForForm(form.getFormId());
						extraSupportAdditionalDao.deleteFormCustomEntitiesForForm(form.getFormId());
						extraDao.deleteFormFields(form.getFormId(), ignoreUpdateformFieldSpecsCsv,
								formSpecAndMasterFormFlagMap.get(form.getFormSpecId()));
						//Delete RTF FormFields
						getExtraSupportAdditionalSupportDao().deleteRichTextFormFields(form.getFormId(), ignoreUpdateformFieldSpecsCsv,
								formSpecAndMasterFormFlagMap.get(form.getFormSpecId()));
					}
					extraDao.deleteFormSectionFields(form.getFormId(), ignoreUpdateFormSectionFieldSpecIds,formSpecAndMasterFormFlagMap.get(form.getFormSpecId()));
					//Delete RTF SectionFields
					getExtraSupportAdditionalSupportDao().deleteRichTextFormSectionFields(form.getFormId(), ignoreUpdateFormSectionFieldSpecIds);

					formNewKeys.put(form.getClientFormId(), id);
					formClientKeys.put(id, form.getClientFormId());

					if (constants.isPrintLogs())
						Log.info(this.getClass(),
								"getFormContainer-formsAddedUpdatedRecordsCurrentTime End. Time taken to process for empId "
										+ empId + " : "
										+ (Api.getCurrentTimeInUTCLong() - formsAddedUpdatedRecordsCurrentTime));

					// Log.info(this.getClass(), "Existed added Form Id:"+form.getFormId()+" Existed
					// Added Client Side Id :"+ form.getClientFormId());
					
					getExtraSupportAdditionalSupportDao().insertIntoFormExtra(form);
					
					try {
					    Log.info(this.getClass(), "addForm() // inside updateFlatTableDataStatus()");
					    form.setUniqueId(formSpec.getUniqueId());
                        getWebAdditionalSupportExtraManager().updateFlatTableDataStatusForForms(form, employee.getCompanyId());
                    }
                    catch(Exception ex) {
                        Log.info(this.getClass(), "addForm() // inside updateFlatTableDataStatus() Got Exception :-"+ex.toString());
                        ex.printStackTrace();
                    }
					
					try {
						
						if(form.isMediasCommitted() && form.getDependentWorkId()!=null && form.getDependentType()!=null)
						{
							if(form.getDependentType() == Form.DEPENDENT_TYPE_FOR_WORK_FORM) {
								String modifiedTime = Api.getDateTimeInUTC(new Date(System.currentTimeMillis()));
								getExtendedDao().updateWorkModifiedTime(modifiedTime, form.getDependentWorkId());
								
								Log.info(this.getClass(),"DEPENDENT_TYPE FOR_WORK_FORM of formId : "+form.getFormId()+""
										+ " and Work Id :"+form.getDependentWorkId()+" Modified Time Updated");
								Log.debug(this.getClass(),"DEPENDENT_TYPE FOR_WORK_FORM of formId : "+form.getFormId()+""
										+ " and Work Id :"+form.getDependentWorkId()+" Modified Time Updated");
								
							}else if(form.getDependentType() == Form.DEPENDENT_TYPE_FOR_WORK_ACTION_FORM) {
								extraSupportDao.updateWorkStatusModifiedTime(form.getDependentWorkId()+"");
								
								Log.info(this.getClass(),"DEPENDENT_TYPE FOR_WORK_ACTION_FORM of formId : "+form.getFormId()+""
										+ " and Work Id :"+form.getDependentWorkId()+" Modified Time Updated");
								Log.debug(this.getClass(),"DEPENDENT_TYPE FOR_WORK_ACTION_FORM of formId : "+form.getFormId()+""
										+ " and Work Id :"+form.getDependentWorkId()+" Modified Time Updated");
							}
						}
						
					}catch(Exception e1) {
						Log.info(this.getClass(),"DEPENDENT_TYPE Got Exception while updating "+ e1);
						Log.debug(this.getClass(),"DEPENDENT_TYPE Got Exception while updating "+ e1);
					}
					
				}
			} else {
				throw new EffortError(4001, HttpStatus.PRECONDITION_FAILED);
			}
		}
	}

	if (constants.isPrintLogs())
		Log.info(this.getClass(), "getFormContainer-formsAddedCurrentTime End. Time taken to process for empId "
				+ empId + " : " + (Api.getCurrentTimeInUTCLong() - formsAddedCurrentTime));

	Long formsModifiedCurrentTime = Api.getCurrentTimeInUTCLong();

	for (Form form : modified) {
		if(hardDeletedFormIdsInReq.contains(form.getFormId())) {
			continue;
		}
		List<ObjectError> errorsTemp = new ArrayList<ObjectError>();
		Long formsModifiedvalidateFormCurrentTime = Api.getCurrentTimeInUTCLong();
		formValidator.validateForm(form, employee.getCompanyId(), errorsTemp);

		if (constants.isPrintLogs())
			Log.info(this.getClass(),
					"getFormContainer-formsModifiedvalidateFormCurrentTime End. Time taken to process for empId "
							+ empId + " : "
							+ (Api.getCurrentTimeInUTCLong() - formsModifiedvalidateFormCurrentTime));

		if (errorsTemp.size() > 0) {
			throw new EffortError(4033, errorToString(errorsTemp), HttpStatus.PRECONDITION_FAILED);
		} else {
			//
			Location location = form.getLocation();
			List<Location> locations = form.getLocations();
			if (locations == null) {
				locations = new ArrayList<Location>();
			}

			if (location != null) {
				locations.add(location);
			}
			Long formsModifiedprocessAndSaveListOfLocationsCurrentTime = Api.getCurrentTimeInUTCLong();
			mainManager.processAndSaveListOfLocations(locations, employee, jmsMessages);

			if (constants.isPrintLogs())
				Log.info(this.getClass(),
						"getFormContainer-formsModifiedprocessAndSaveListOfLocationsCurrentTime End. Time taken to process for empId "
								+ empId + " : " + (Api.getCurrentTimeInUTCLong()
										- formsModifiedprocessAndSaveListOfLocationsCurrentTime));

			if (locations != null && locations.size() > 0) {
				Location lastLocation = locations.get(locations.size() - 1);
				form.setLocation(lastLocation);
				if(lastLocation!=null) {
					form.setLocationId(lastLocation.getLocationId()+"");
				}
			}

			form.setModifiedBy(employee.getEmpId());
			try {
				Form formOld = null;
				if (formSpecAndMasterFormFlagMap.get(form.getFormSpecId())) {
					formOld = extraSupportAdditionalDao.getMasterForm(form.getFormId());
				} else {
					formOld = extraDao.getForm(form.getFormId() + "");
				}

				form.setAssignTo(formOld.getAssignTo());

				Log.audit(employee.getCompanyId() + "", empId, "form", "modified", form.toCSV(), formOld.toCSV());

				List<FormField> formFields = new ArrayList<FormField>();
				if (formSpecAndMasterFormFlagMap.get(form.getFormSpecId())) {
					formFields = extraSupportAdditionalDao.getMasterFormFieldsByFormId(form.getFormId());
				} else {
					formFields = extraDao.getFormFieldByForm(form.getFormId());
				}

				oldFormFieldsMap.put(form.getFormId(), formFields);
				Log.audit(employee.getCompanyId() + "", empId, "formField", "deleted", formFields.toString(), null);
			} catch (Exception e) {
				Log.info(this.getClass(), e.toString(), e);
			}

			Long formsModifiedupdateFormCurrentTime = Api.getCurrentTimeInUTCLong();
			boolean isMasterForm = formSpecAndMasterFormFlagMap.get(form.getFormSpecId());
			form.setSourceOfModification(Work.ANDROID);
			extraDao.updateForm(form, employee.getEmpId(), isMasterForm, clientVersion);
			FormSpec formSpec = webManager.getFormSpec(form.getFormSpecId()+"");
			if(formSpec.getPurpose() == -1)
			{
				CustomEntitySpecStockConfiguration customEntitySpecStockConfiguration = getExtraSupportAdditionalSupportDao().getCustomEntitySpecStockConfigurationByFormSpecUniqueId(formSpec.getUniqueId(),0);
				
				if(!Api.isEmptyObj(customEntitySpecStockConfiguration))
				{
					getExtraSupportAdditionalSupportDao().insertIntoCustomEntityCreationOnStockUpdation(form.getFormId(),customEntitySpecStockConfiguration);
				}
			}

			if (isMasterForm) {
				syncAddedOrModifedMasterForms.add(form.getFormId());
			} else {
				syncAddedOrModifedForms.add(form.getFormId());
			}
			
			boolean deleteFormFields = true;
			if(formSpecAndCustomerMasterFormFlagMap!=null && formSpecAndCustomerMasterFormFlagMap.get(form.getFormSpecId()) != null){
				if(!customerEditPermission){
					modifiedCustomerFormsIds.add(form.getFormId());
					deleteFormFields = false;
				}
				
			}
			

			if (constants.isPrintLogs())
				Log.info(this.getClass(),
						"getFormContainer-formsModifiedupdateFormCurrentTime End. Time taken to process for empId "
								+ empId + " : "
								+ (Api.getCurrentTimeInUTCLong() - formsModifiedupdateFormCurrentTime));

			/*
			 * if(form.isMediasCommitted()) {
			 */
			Long currentTimeTYPE_FORM = Api.getCurrentTimeInUTCLong();
			if (!isMasterForm) {
				webManager.sendJmsMessage(JmsMessage.TYPE_FORM, -1, form.getCompanyId(), Long.parseLong(empId),
						JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(), null, true, jmsMessages);
				CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus = new CustomerRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, -1, form.getCompanyId(), Long.parseLong(empId),
						JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(), true,form.getFormId());
				CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus = new CustomEntityRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, -1, form.getCompanyId(), Long.parseLong(empId),
						JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(), true,form.getFormId());
				WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus = new WorkRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, -1, form.getCompanyId(), Long.parseLong(empId),
						JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(), true,form.getFormId());
				EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus = new EmployeeRequisitionJmsMessageStatus(JmsMessage.TYPE_FORM, -1, form.getCompanyId(), Long.parseLong(empId),
						JmsMessage.CHANGE_TYPE_MODIFY, form.getFormId(), true,form.getFormId());
				getWebExtensionManager().getAllReqConfiurations(form.getFormSpecId(),customerRequisitionJmsMessageStatus,customEntityRequisitionJmsMessageStatus,workRequisitionJmsMessageStatus,employeeRequisitionJmsMessageStatus);
				
			}
			if (formSpec.getPurpose() == FormSpec.PURPOUSE_CUSTOM_ENTITY) {
				webManager.sendJmsMessage(JmsMessage.TYPE_CUSTOM_ENTITY, form.getFormId(), employee.getCompanyId(), employee.getEmpId(), Constants.CHANGE_TYPE_MODIFY,
						form.getFormId(), null, false);
			}
			if (constants.isPrintLogs())
				Log.info(this.getClass(),
						"JmsMessage for TYPE_FORM_CHANGE_TYPE_MODIFY End. Time taken to process for empId " + empId
								+ " : " + (Api.getCurrentTimeInUTCLong() - currentTimeTYPE_FORM));
			// }
			if (form.isMediasCommitted() && !isMasterForm) {
				List<WebAppTool> webApptools = extraSupportAdditionalDao.getWebAppToolsByFormSpecUniqueId(formSpec.getUniqueId());
				if(webApptools!=null && webApptools.size()>0) {
					webManager.sendJmsMessage(JmsMessage.TYPE_FORM_MEDIA_COMMITED, -1, form.getCompanyId(),
						Long.parseLong(empId), JmsMessage.CHANGE_TYPE_OTHER, form.getFormId(), null, true,
						jmsMessages);
				}
			}
			Long formsModifiedinsertFormLocationListCurrentTime = Api.getCurrentTimeInUTCLong();

			List<Location> formLocationsNew = new ArrayList<Location>();
			for (Location loc : locations) {
				if (loc.getLocationId() != 0) {
					formLocationsNew.add(loc);
				}
			}

			if (formLocationsNew.size() > 0) {
				extraDao.insertFormLocationList(formLocationsNew, form.getFormId(), 2);
			}

			if (constants.isPrintLogs())
				Log.info(this.getClass(),
						"getFormContainer-formsModifiedinsertFormLocationListCurrentTime End. Time taken to process for empId "
								+ empId + " : "
								+ (Api.getCurrentTimeInUTCLong() - formsModifiedinsertFormLocationListCurrentTime));

			if(deleteFormFields){
				extraDao.deleteFormCustomersForForm(form.getFormId());
				extraDao.deleteFormFields(form.getFormId(), ignoreUpdateformFieldSpecsCsv,
						formSpecAndMasterFormFlagMap.get(form.getFormSpecId()));
				extraSupportAdditionalDao.deleteFormCustomEntitiesForForm(form.getFormId());
				//Delete RTF FormFields
				getExtraSupportAdditionalSupportDao().deleteRichTextFormFields(form.getFormId(), ignoreUpdateformFieldSpecsCsv,
						formSpecAndMasterFormFlagMap.get(form.getFormSpecId()));
			}
			extraDao.deleteFormSectionFields(form.getFormId(), ignoreUpdateFormSectionFieldSpecIds,formSpecAndMasterFormFlagMap.get(form.getFormSpecId()));
			//Delete RTF SectionFields
			getExtraSupportAdditionalSupportDao().deleteRichTextFormSectionFields(form.getFormId(), ignoreUpdateFormSectionFieldSpecIds);

			getExtraSupportAdditionalSupportDao().insertIntoFormExtra(form);
			
			try {
			    Log.info(this.getClass(), "addForm() // inside updateFlatTableDataStatus()");
			   
			    form.setUniqueId(formSpec.getUniqueId());
                getWebAdditionalSupportExtraManager().updateFlatTableDataStatusForForms(form, employee.getCompanyId());
            }
            catch(Exception ex) {
                Log.info(this.getClass(), "addForm() // inside updateFlatTableDataStatus() Got Exception :-"+ex.toString());
                ex.printStackTrace();
            }
			
			try {
				
				if(form.isMediasCommitted() && form.getDependentWorkId()!=null && form.getDependentType()!=null)
				{
					if(form.getDependentType() == Form.DEPENDENT_TYPE_FOR_WORK_FORM) {
						String modifiedTime = Api.getDateTimeInUTC(new Date(System.currentTimeMillis()));
						getExtendedDao().updateWorkModifiedTime(modifiedTime, form.getDependentWorkId());
						
						Log.info(this.getClass(),"DEPENDENT_TYPE FOR_WORK_FORM of formId : "+form.getFormId()+""
								+ " and Work Id :"+form.getDependentWorkId()+" Modified Time Updated");
						Log.debug(this.getClass(),"DEPENDENT_TYPE FOR_WORK_FORM of formId : "+form.getFormId()+""
								+ " and Work Id :"+form.getDependentWorkId()+" Modified Time Updated");
						
					}else if(form.getDependentType() == Form.DEPENDENT_TYPE_FOR_WORK_ACTION_FORM) {
						extraSupportDao.updateWorkStatusModifiedTime(form.getDependentWorkId()+"");
						
						Log.info(this.getClass(),"DEPENDENT_TYPE FOR_WORK_ACTION_FORM of formId : "+form.getFormId()+""
								+ " and Work Id :"+form.getDependentWorkId()+" Modified Time Updated");
						Log.debug(this.getClass(),"DEPENDENT_TYPE FOR_WORK_ACTION_FORM of formId : "+form.getFormId()+""
								+ " and Work Id :"+form.getDependentWorkId()+" Modified Time Updated");
					}
				}
				
			}catch(Exception e1) {
				Log.info(this.getClass(),"DEPENDENT_TYPE Got Exception while updating "+ e1);
				Log.debug(this.getClass(),"DEPENDENT_TYPE Got Exception while updating "+ e1);
			}
			
			
		}
	}

	
	for(Form form : added) {
		addedModifiedFormIds.add(form.getFormId());
	}
	for(Form form : modified) {
		addedModifiedFormIds.add(form.getFormId());
	}
	
	
	if (constants.isPrintLogs())
		Log.info(this.getClass(), "getFormContainer-formsModifiedCurrentTime End. Time taken to process for empId "
				+ empId + " : " + (Api.getCurrentTimeInUTCLong() - formsModifiedCurrentTime));

	Long formsDeletedCurrentTime = Api.getCurrentTimeInUTCLong();
	List<Long> deleted = formSubmissionData.getFormDataContainer().getDeleted();
	for (Long formId : deleted) {
		if(hardDeletedFormIdsInReq.contains(formId)) {
			continue;
		}
		try {
			Log.audit(employee.getCompanyId() + "", empId, "form", "deleted", formId + "", null);
		} catch (Exception e) {
			Log.info(this.getClass(), e.toString(), e);
		}
		extraDao.deleteForm(formId, employee.getEmpId(), employee.getCompanyId(), false);
	}

	if (constants.isPrintLogs())
		Log.info(this.getClass(), "getFormContainer-formsDeletedCurrentTime End. Time taken to process for empId "
				+ empId + " : " + (Api.getCurrentTimeInUTCLong() - formsDeletedCurrentTime));

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-formFieldSpecMapCurrentTime End. Time taken to process for empId " + empId + " : "
						+ (Api.getCurrentTimeInUTCLong() - formFieldSpecMapCurrentTime));

	Long formFieldsItterationResolvingCurrentTime = Api.getCurrentTimeInUTCLong();

	Iterator<FormField> formFiedsItr = fields.iterator();
	while (formFiedsItr.hasNext()) {
		FormField field = formFiedsItr.next();

		if (!Api.isEmptyString(field.getClientFormId()) && platformId.equals("7")) {
			if (formNewKeys.get(field.getClientFormId()) == null) {
				formFiedsItr.remove();
				Log.info(getClass(), "Ignoring field : empId=" + empId + " clientCode = " + code + " formSpecId = "
						+ field.getFormSpecId() + " fieldSpecId = " + field.getFieldSpecId() + " clientFormId = "
						+ field.getClientFormId() + " fieldValue = " + field.getFieldValue()
						+ " In fields Array we have clientFormId and no corresponding data in added.So ignoring this field");
			}
		}
	}
	for (FormField formField : fields) {
		if (!Api.isEmptyString(formField.getClientFormId())) {
			long formId = formNewKeys.get(formField.getClientFormId());
			formField.setFormId(formId);
		}

		FormFieldSpec formFieldSpec = formFieldSpecMap.get(formField.getFieldSpecId());
		
		/*if(hardDeletedFormIdsInReq.contains(formField.getFormId())) {
			continue;
		}*/
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
			
			
			if ((formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_IMAGE)
					|| (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_AUDIO)
					|| (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_VIDEO)
					|| (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_SIGNATURE)
					|| (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTI_MEDIA)) {
				
				
				List<Map<String,String>> medias = formField.getMedia();
				
				if ((medias == null || medias.size() <= 0) && Api.isEmptyString(formField.getFieldValue())) {
					
					  throw new EffortError(4034, "Media Not Available for fieldSpecId : " +
					  formFieldSpec.getFieldSpecId(), HttpStatus.PRECONDITION_FAILED);
					 
				} else if(medias != null && medias.size() > 0){
					
					Map<String,String> media = medias.get(0);
					String mimeType = media.get("mimeType");// workJsonObj.getString("mimeType");
					String extension = media.get("ext");// workJsonObj.getString("ext");
					String fileStream = media.get("fileStream");// workJsonObj.getString("fileStream");
					
					if(!Api.isEmptyString(fileStream)){
						Long mediaId = null;
						try {
							mediaId = mediaManager.saveMedia(mimeType, extension, fileStream, employee.getEmpId(),
									employee.getCompanyId(),true);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (mediaId == null) {
							throw new EffortError(4034, "Invalid Media for fieldSpecId : "+formFieldSpec.getFieldSpecId(), HttpStatus.PRECONDITION_FAILED);
						}
						formField.setFieldValue(mediaId + "");
						formField.setDisplayValue(mediaId + "");
					}
				}


			}
			
			if ((formFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_MULTI_DOCUMENT)
					|| (formFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_MULTI_IMAGE)) {
				
				
				List<Map<String,String>> medias = formField.getMedia();
				
				if ((medias == null || medias.size() <= 0) && Api.isEmptyString(formField.getFieldValue())) {
					
					  throw new EffortError(4034, "Media Not Available for fieldSpecId : " +
					  formFieldSpec.getFieldSpecId(), HttpStatus.PRECONDITION_FAILED);
					 
				} else if(medias != null && medias.size() > 0){
					
					List<Long> mediaIds = new ArrayList<Long>();
					for(Map<String,String> media : medias) {
						String mimeType = media.get("mimeType");// workJsonObj.getString("mimeType");
						String extension = media.get("ext");// workJsonObj.getString("ext");
						String fileStream = media.get("fileStream");// workJsonObj.getString("fileStream");
						
						if(!Api.isEmptyString(fileStream)){
							Long mediaId = null;
							try {
								mediaId = mediaManager.saveMedia(mimeType, extension, fileStream, employee.getEmpId(),
										employee.getCompanyId(),true);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							if (mediaId == null) {
								throw new EffortError(4034, "Invalid Media for fieldSpecId : "+formFieldSpec.getFieldSpecId(), HttpStatus.PRECONDITION_FAILED);
							}else {
								mediaIds.add(mediaId);
							}
						}
						
					}
					
					if(mediaIds != null && mediaIds.size() > 0) {
						formField.setFieldValue(Api.toCSV(mediaIds) + "");
						formField.setDisplayValue(Api.toCSV(mediaIds) + "");
					}
					
				}


			}
		}

	}

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-formFieldsItterationResolvingCurrentTime End. Time taken to process for empId "
						+ empId + " : "
						+ (Api.getCurrentTimeInUTCLong() - formFieldsItterationResolvingCurrentTime));

	Long sectionFieldsItterationCurrentTime = Api.getCurrentTimeInUTCLong();

	Iterator<FormSectionField> sectionFieldsItr = sectionFields.iterator();
	while (sectionFieldsItr.hasNext()) {
		FormSectionField field = sectionFieldsItr.next();
		if (!Api.isEmptyString(field.getClientFormId()) && platformId.equals("7")) {
			if (formNewKeys.get(field.getClientFormId()) == null) {
				sectionFieldsItr.remove();
				Log.info(getClass(), "Ignoring section field : empId=" + empId + " clientCode = " + code
						+ " formSpecId = " + field.getFormSpecId() + " fieldSpecId = "
						+ field.getSectionFieldSpecId() + " clientFormId = " + field.getClientFormId()
						+ " fieldValue = " + field.getFieldValue()
						+ " In fields Array we have clientFormId and no corresponding data in added.So ignoring this field");

			}
		}
	}
	for (FormSectionField formSectionField : sectionFields) {
		if (!Api.isEmptyString(formSectionField.getClientFormId())) {
			long formId = formNewKeys.get(formSectionField.getClientFormId());
			formSectionField.setFormId(formId);
		}

		
		FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
				.get(formSectionField.getSectionFieldSpecId());
		
		/*if(hardDeletedFormIdsInReq.contains(formSectionField.getFormId())) {
			continue;
		}*/
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
			
			if ((formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_IMAGE)
					|| (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_AUDIO)
					|| (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_VIDEO)
					|| (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_SIGNATURE)
					|| (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTI_MEDIA)) {
				
				
				List<Map<String,String>> medias = formSectionField.getMedia();
				
				if ((medias == null || medias.size() <= 0) && Api.isEmptyString(formSectionField.getFieldValue())) {
					
					  throw new EffortError(4034, "Media Not Available for fieldSpecId : " +
							  formSectionFieldSpec.getSectionFieldSpecId(), HttpStatus.PRECONDITION_FAILED);
					 
				} else if(medias != null && medias.size() > 0){
					Map<String,String> media = medias.get(0);
					String mimeType = media.get("mimeType");// workJsonObj.getString("mimeType");
					String extension = media.get("ext");// workJsonObj.getString("ext");
					String fileStream = media.get("fileStream");// workJsonObj.getString("fileStream");
					if(!Api.isEmptyString(fileStream)){
						Long mediaId = null;
						try {
							mediaId = mediaManager.saveMedia(mimeType, extension, fileStream, employee.getEmpId(),
									employee.getCompanyId(),true);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (mediaId == null) {
							throw new EffortError(4034, "Invalid Media for fieldSpecId : "+formSectionFieldSpec.getSectionFieldSpecId(), HttpStatus.PRECONDITION_FAILED);
						}
						formSectionField.setFieldValue(mediaId + "");
						formSectionField.setDisplayValue(mediaId + "");
					}
					
				}


			}
			
			if ((formSectionFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_MULTI_DOCUMENT)
					|| (formSectionFieldSpec.getFieldType() == ConstantsExtra.FORM_FIELD_TYPE_MULTI_IMAGE)) {
				
				
				List<Map<String,String>> medias = formSectionField.getMedia();
				
				if ((medias == null || medias.size() <= 0) && Api.isEmptyString(formSectionField.getFieldValue())) {
					
					  throw new EffortError(4034, "Media Not Available for fieldSpecId : " +
							  formSectionFieldSpec.getSectionFieldSpecId(), HttpStatus.PRECONDITION_FAILED);
					 
				} else if(medias != null && medias.size() > 0){
					
					List<Long> mediaIds = new ArrayList<Long>();
					for(Map<String,String> media : medias) {
						String mimeType = media.get("mimeType");// workJsonObj.getString("mimeType");
						String extension = media.get("ext");// workJsonObj.getString("ext");
						String fileStream = media.get("fileStream");// workJsonObj.getString("fileStream");
						
						if(!Api.isEmptyString(fileStream)){
							Long mediaId = null;
							try {
								mediaId = mediaManager.saveMedia(mimeType, extension, fileStream, employee.getEmpId(),
										employee.getCompanyId(),true);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							if (mediaId == null) {
								throw new EffortError(4034, "Invalid Media for fieldSpecId : "+formSectionFieldSpec.getSectionFieldSpecId(), HttpStatus.PRECONDITION_FAILED);
							}else {
								mediaIds.add(mediaId);
							}
						}
						
					}
					
					if(mediaIds != null && mediaIds.size() > 0) {
						formSectionField.setFieldValue(Api.toCSV(mediaIds) + "");
						formSectionField.setDisplayValue(Api.toCSV(mediaIds) + "");
					}
					
				}


			}
			
			
		}
	}

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-sectionFieldsItterationCurrentTime End. Time taken to process for empId " + empId
						+ " : " + (Api.getCurrentTimeInUTCLong() - sectionFieldsItterationCurrentTime));

	List<ObjectError> errorsTemp = new ArrayList<ObjectError>();
	if ("0".equalsIgnoreCase(hasAutoComputeCapability)) {
		List<Form> allForms = new ArrayList<Form>();
		allForms.addAll(added);
		allForms.addAll(modified);
		mainManager.computeFieldsForSync(fields, sectionFields, formFieldSpecMap, formSectionFieldSpecMap, allForms, employee, hardDeletedFormIdsInReq);

	}
	
	/*Added FieldsFormSpecIds Overlook up due to customer 
	  fields are coming with two different formSpecids while insert or
	   update to reject the older formSpec fields in sync and 
	   sync successfully we are adding this relookup on 11 Aug 2021 00:11 AM issue occured in GO_MMT.
	*/
	String fieldsFormSpecIds = null;
	if(fields!=null && fields.size()>0) {
		fieldsFormSpecIds = Api.toCSV(fields, "formSpecId",CsvOptions.FILTER_NULL_OR_EMPTY);
	}
	Map<Long, Boolean> fieldsFormSpecAndMasterFormFlagMap = new HashMap<Long, Boolean>();
	if(!Api.isEmptyString(fieldsFormSpecIds))
	{
		List<String> fieldsFormSpecIdsList = Api.csvToList(fieldsFormSpecIds);
		Set<String> uniqueFieldsFormSpecIdsList = new HashSet<String>(fieldsFormSpecIdsList);
		Log.info(getClass(), "masterCustomerFormFieldItr empId :"+empId+" uniqueFieldsFormSpecIdsList:"+uniqueFieldsFormSpecIdsList); 
		List<FormSpec> fieldFormSpecs = webManager.getFormSpecsIn(Api.toCSV(uniqueFieldsFormSpecIdsList));
		for (FormSpec formSpec : fieldFormSpecs) {
			if(formSpec.getPurpose() == FormSpec.PURPOUSE_CUSTOMER || formSpec.getPurpose() == FormSpec.PURPOUSE_CUSTOMER_CREATION)
			{
				Log.info(getClass(), "masterCustomerFormFieldItr empId :"+empId+" fieldsFormSpecAndMasterFormFlagMap set as true");
				fieldsFormSpecAndMasterFormFlagMap.put(formSpec.getFormSpecId(), true);
			}
		}
	}
	
	List<FormField> masterFormFields = new ArrayList<FormField>();
	List<FormField> masterCustomerFormFields = new ArrayList<FormField>();
	List<FormField> masterCustomerFormFieldsToValidate = new ArrayList<FormField>();
	//if (apiLevel <= 23) {
		Iterator<FormField> fieldsItr = fields.iterator();
		while (fieldsItr.hasNext()) {
			FormField field = fieldsItr.next();
			FormFieldSpec formFieldSpec = formFieldSpecMap.get(field.getFieldSpecId());
			
			/*Added FieldsFormSpecIds Overlook up due to customer 
			  fields are coming with two different formSpecids while insert or
			   update to reject the older formSpec fields in sync and 
			   sync successfully we are adding this relookup on 11 Aug 2021 00:11 AM issue occured in GO_MMT.
				Added formFieldSpec !=null extra check.
			*/
			Log.info(getClass(), "masterCustomerFormFieldItr empId :"+empId+" formFieldSpecMap field.getFieldSpecId()"+field.getFieldSpecId()+" field.getFormSpecId():"+field.getFormSpecId()+" formFieldSpec :"+formFieldSpec);
			
			if (formFieldSpec !=null && formSpecAndMasterFormFlagMap.get(formFieldSpec.getFormSpecId())) {
				Log.info(getClass(), "masterCustomerFormFieldItr empId :"+empId+" added/modifed Master formSpecId matches");
				if(modifiedCustomerFormsIds.contains(field.getFormId())){
					masterCustomerFormFields.add(field);
				}else{
					masterFormFields.add(field);
				}
				if(formSpecAndCustomerMasterFormFlagMap!=null && formSpecAndCustomerMasterFormFlagMap.get(formFieldSpec.getFormSpecId()) != null && formSpecAndCustomerMasterFormFlagMap.get(formFieldSpec.getFormSpecId())){
					masterCustomerFormFieldsToValidate.add(field);
				}
				fieldsItr.remove();
			}
			else 
			{
				/*Added FieldsFormSpecIds Overlook up due to customer 
				  fields are coming with two different formSpecids while insert or
				   update to reject the older formSpec fields in sync and 
				   sync successfully we are adding this relookup on 11 Aug 2021 00:11 AM issue occured in GO_MMT.
				*/
				if(fieldsFormSpecAndMasterFormFlagMap.get(field.getFormSpecId())!=null &&
						fieldsFormSpecAndMasterFormFlagMap.get(field.getFormSpecId())) 
				{
					Log.info(getClass(), "masterCustomerFormFieldItr empId :"+empId+" removing older customer field for field.getFormSpecId() :"+field.getFormSpecId());
					fieldsItr.remove();
				}
			}
		}
	//}

	boolean isMasterForm = false;
	Long insertFormFieldsCurrentTime = Api.getCurrentTimeInUTCLong();
	formValidator.validateFieldsForSync(fields, false, employee.getCompanyId(), employee.getEmpId(),
			formFieldSpecMap, errorsTemp, true, isMasterForm);
	if (errorsTemp.size() > 0) {
		throw new EffortError(4034, errorToString(errorsTemp), HttpStatus.PRECONDITION_FAILED);
	} else {
		for (FormField formField : fields) {
			Log.audit(employee.getCompanyId() + "", empId, "formField", "added", formField.toCSV(), null);
		}
		List<FormField> autoGenSeqFields = webSupportManager.getAutoGenSeqFields(fields,
				employee.getTimezoneOffset() + "", oldFormFieldsMap, formNewKeys.values(), isMasterForm,hardDeletedFormIdsInReq,sectionFields);
		
		getWebExtensionManager().resolveAutoGenFields(empId, fields, autoGenSeqFields);
		
		SecurityUtils.stripInvalidCharacters(fields, SecurityUtils.INPUT_TYPE_TEXT, "fieldValue", "displayValue");
		//extraDao.insertFormFields(fields, false,false,true);
		List<FormField> fieldsToInsert = mainManager.getFieldsToInsert(empId,fields, hardDeletedFormIdsInReq);
		getExtendedDao().insertFormFieldsForSync(fieldsToInsert, false,false,true,formFieldSpecMap);
	}

	//if (apiLevel <= 23) {
		if(masterFormFields!=null && masterFormFields.size()>0){
			isMasterForm = true;
			formValidator.validateFieldsForSync(masterFormFields, false, employee.getCompanyId(), employee.getEmpId(),
					formFieldSpecMap, errorsTemp, true, isMasterForm);
			if (errorsTemp.size() > 0) {
				throw new EffortError(4034, errorToString(errorsTemp), HttpStatus.PRECONDITION_FAILED);
			} else {
				for (FormField formField : masterFormFields) {
					Log.audit(employee.getCompanyId() + "", empId, "formField", "added", formField.toCSV(), null);
				}
				List<FormField> autoGenSeqFields = webSupportManager.getAutoGenSeqFields(masterFormFields,
						employee.getTimezoneOffset() + "", oldFormFieldsMap, formNewKeys.values(), isMasterForm,hardDeletedFormIdsInReq,sectionFields);
				masterFormFields.addAll(autoGenSeqFields);
				
				//extraDao.insertFormFields(masterFormFields, true,false,true);
				SecurityUtils.stripInvalidCharacters(fields, SecurityUtils.INPUT_TYPE_TEXT, "fieldValue", "displayValue");
				getExtendedDao().insertFormFieldsForSync(masterFormFields, true,false,true,formFieldSpecMap);
			}
		}
	//}
	
	/*Added this method to update Customer Last Activity Name and Time instead of deleting all
	existing fields and inserting new fields when employee does not have customer edit permission.*/
	if(!masterCustomerFormFields.isEmpty()){
		mainManager.processCustomerFormFieldsBasedOnEditPerssion(employee, empId,
				formFieldSpecMap, uniqueIdAndCustomerFormFieldSpecMap,
				errorsTemp, masterCustomerFormFields);
	}

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-insertFormFieldsCurrentTime End. Time taken to process for empId " + empId + " : "
						+ (Api.getCurrentTimeInUTCLong() - insertFormFieldsCurrentTime));

	List<FormSectionField> masterFormSectionFields = new ArrayList<FormSectionField>();
	Iterator<FormSectionField> sectionFieldItr = sectionFields.iterator();
	while (sectionFieldItr.hasNext()) {
		FormSectionField sectionField = sectionFieldItr.next();
		FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap.get(sectionField.getSectionFieldSpecId());
		
		if (formSectionFieldSpec !=null && formSpecAndMasterFormFlagMap.get(formSectionFieldSpec.getFormSpecId())) {
			masterFormSectionFields.add(sectionField);
			sectionFieldItr.remove();
		}
	}
	
	
	Long insertFormSectionFieldsCurrentTime = Api.getCurrentTimeInUTCLong();
	formValidator.validateSectionFieldsForSync(sectionFields, false, employee.getCompanyId(), employee.getEmpId(),
			formSectionFieldSpecMap, errorsTemp, true,isMasterForm);
	
	if (errorsTemp.size() > 0) {
		throw new EffortError(4034, errorToString(errorsTemp), HttpStatus.PRECONDITION_FAILED);
	} else {
		for (FormSectionField formSectionField : sectionFields) {
			Log.audit(employee.getCompanyId() + "", empId, "formSectionField", "added", formSectionField.toCSV(),
					null);
		}
		//extraDao.insertFormSectionFields(sectionFields,false,true);
		SecurityUtils.stripInvalidCharacters(sectionFields, SecurityUtils.INPUT_TYPE_TEXT, "fieldValue", "displayValue");
		List<FormSectionField> sectionFieldsToInsert = mainManager.getSectionFieldsToInsert(empId,sectionFields, hardDeletedFormIdsInReq);
		getExtendedDao().insertFormSectionFieldsForSync(sectionFieldsToInsert,false,true,formSectionFieldSpecMap,false);
	}
	
	if(masterFormSectionFields!=null && masterFormSectionFields.size()>0) {
		formValidator.validateSectionFieldsForSync(masterFormSectionFields, false, employee.getCompanyId(), employee.getEmpId(),
				formSectionFieldSpecMap, errorsTemp, true,isMasterForm);
		
		if (errorsTemp.size() > 0) {
			throw new EffortError(4034, errorToString(errorsTemp), HttpStatus.PRECONDITION_FAILED);
		} else {
			for (FormSectionField formSectionField : masterFormSectionFields) {
				Log.audit(employee.getCompanyId() + "", empId, "formSectionField", "added", formSectionField.toCSV(),
						null);
			}
			//extraDao.insertFormSectionFields(sectionFields,false,true);
			SecurityUtils.stripInvalidCharacters(sectionFields, SecurityUtils.INPUT_TYPE_TEXT, "fieldValue", "displayValue");
			List<FormSectionField> sectionFieldsToInsert = mainManager.getSectionFieldsToInsert(empId,masterFormSectionFields, hardDeletedFormIdsInReq);
			getExtendedDao().insertFormSectionFieldsForSync(sectionFieldsToInsert,false,true,formSectionFieldSpecMap,true);
		}
	}

	
	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-insertFormSectionFieldsCurrentTime End. Time taken to process for empId " + empId
						+ " : " + (Api.getCurrentTimeInUTCLong() - insertFormSectionFieldsCurrentTime));

	Long formSpecIdsAndTitleMapCurrentTime = Api.getCurrentTimeInUTCLong();
	Map<String, String> formSpecIdsAndTitleMap = extraDao.getFormSpecIdsAndTitleMap(added, modified);
	Map<String, String> fromSpecidAndTitleMap = formSpecIdsAndTitleMap;

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-formSpecIdsAndTitleMapCurrentTime End. Time taken to process for empId " + empId
						+ " : " + (Api.getCurrentTimeInUTCLong() - formSpecIdsAndTitleMapCurrentTime));

	Long formSpecIdsAndFormSpecMapCurrentTime = Api.getCurrentTimeInUTCLong();
	Map<String, FormSpec> formSpecIdsAndFormSpecMap = extraDao.getFormSpecIdsAndFormSpecMap(added, modified);
	Map<String, FormSpec> formSpecidAndFormSpecMap = formSpecIdsAndFormSpecMap;

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-formSpecIdsAndFormSpecMapCurrentTime End. Time taken to process for empId "
						+ empId + " : " + (Api.getCurrentTimeInUTCLong() - formSpecIdsAndFormSpecMapCurrentTime));

	Long addedlogFormAuditCurrentTime = Api.getCurrentTimeInUTCLong();
	for (final Form form : added) {
		// Runnable runnable= new Runnable() {
		// Form form1 = form;
		// @Override
		// public void run() {
		if(hardDeletedFormIdsInReq.contains(form.getFormId())) {
			continue;
		}
		//
		if(constantsExtra.getIntronLifeScienceCompanyId().contains(employee.getCompanyId()+"")) {
			getWebExtensionManager().logFormAuditForOnlineForms(form.getFormId(), employee.getEmpId(),
					employee.getCompanyId(), Constants.CHANGE_TYPE_ADD, true, ipAddress, jmsMessages,
					formSpecAndMasterFormFlagMap.get(form.getFormSpecId()));
		}else {
			getWebAdditionalSupportManager().logFormAudit(form.getFormId(), employee.getEmpId(),
					employee.getCompanyId(), Constants.CHANGE_TYPE_ADD, true, ipAddress, jmsMessages,
					formSpecAndMasterFormFlagMap.get(form.getFormSpecId()));
		}
		
		FormSpec formSpec = formSpecidAndFormSpecMap.get("" + form.getFormSpecId());

		try {
		if (formSpec.getPurpose() == -1) {
			List<EmployeeTargetsConfiguration> employeeTargetsConfigurationList = getWebExtensionManager()
					.getEmployeeTargetsConfigurationList(employee.getCompanyId(), formSpec.getUniqueId(),
							null, 1);
			if (employeeTargetsConfigurationList != null && employeeTargetsConfigurationList.size() > 0) {
				for (EmployeeTargetsConfiguration employeeTargetsConfiguration : employeeTargetsConfigurationList) {
				EmployeeEventSubmissions employeeEventSubmissions = new EmployeeEventSubmissions(
						employee.getCompanyId(), employee.getEmpId(), form.getFormId() + "",
						employeeTargetsConfiguration.getModuleTypeId(),
						employeeTargetsConfiguration.getId());
				getWebExtensionManager().insertEmployeeEventSubmissions(employeeEventSubmissions);
				EmployeeEventSubmissionsAuditLogs employeeEventSubmissionsAuditLogs = new EmployeeEventSubmissionsAuditLogs(
						employee.getCompanyId(), employee.getEmpId(), form.getFormId() + "",
						employeeTargetsConfiguration.getModuleTypeId(),
						employeeTargetsConfiguration.getId(),employee.getEmpId());
				getWebExtensionManager().insertEmployeeEventSubmissionsAuditId(employeeEventSubmissionsAuditLogs);
				}
			}
		}
		}catch (Exception e) {
			Log.info(this.getClass(),
					"getFormContainer-employeeTargetsConfigurationList got Exception"+e);
		}

		// }
		// };

		// runnableTasksAfterTransactionCommited.add(runnable);
		// runnable.run();

	}

	for (Form activityAddedForm : activityAddedForms) {
		if(hardDeletedFormIdsInReq.contains(activityAddedForm.getFormId())) {
			continue;
		}
		String formSpecTitle = fromSpecidAndTitleMap.get("" + activityAddedForm.getFormSpecId());
		FormSpec formSpec = formSpecidAndFormSpecMap.get("" + activityAddedForm.getFormSpecId());
		// check for job,work and customer purpose forms..skip
		// job,work and customer purpose forms addition into
		// activity feeds
		if (formSpec.getPurpose() != FormSpec.PURPOUSE_JOB && formSpec.getPurpose() != FormSpec.PURPOUSE_CUSTOMER
				&& formSpec.getPurpose() != FormSpec.PURPOUSE_WORK) {
			webManager.addActivityStream(employee.getCompanyId(), employee.getEmpId(), employee.getEmpName(),
					activityAddedForm.getFormId(), ActivityStream.ACTIVITY_STREAM_FORM_ADDED, formSpecTitle, null);
		}
	}

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-addedlogFormAuditCurrentTime End. Time taken to process for empId " + empId
						+ " : " + (Api.getCurrentTimeInUTCLong() - addedlogFormAuditCurrentTime));

	Long modifiedlogFormAuditCurrentTime = Api.getCurrentTimeInUTCLong();
	for (Form form : modified) {
		if(hardDeletedFormIdsInReq.contains(form.getFormId())) {
			continue;
		}
		Form formOld = null;
		if (formSpecAndMasterFormFlagMap.get(form.getFormSpecId())) {
			formOld = extraSupportAdditionalDao.getMasterForm(form.getFormId());
		}else {
			formOld = webManager.getForm(form.getFormId() + "");
		}
		 if(formOld == null){
             formsNotInDb.add(form);
             continue;
         }

		String formSpecTitle = fromSpecidAndTitleMap.get("" + form.getFormSpecId());
		
		if(constantsExtra.getIntronLifeScienceCompanyId().contains(employee.getCompanyId()+"")) {
			getWebExtensionManager().logFormAuditForOnlineForms(form.getFormId(), employee.getEmpId(),
					employee.getCompanyId(), Constants.CHANGE_TYPE_MODIFY, true, ipAddress, jmsMessages,
					formSpecAndMasterFormFlagMap.get(form.getFormSpecId()));
		}else {
		getWebAdditionalSupportManager().logFormAudit(form.getFormId(), employee.getEmpId(),
				employee.getCompanyId(), Constants.CHANGE_TYPE_MODIFY, true, ipAddress,
				jmsMessages, formSpecAndMasterFormFlagMap.get(form.getFormSpecId()));
		}
		FormSpec formSpec = formSpecidAndFormSpecMap.get("" + form.getFormSpecId());

		try {
		if (formSpec.getPurpose() == -1) {
			List<EmployeeTargetsConfiguration> employeeTargetsConfigurationList = getWebExtensionManager()
					.getEmployeeTargetsConfigurationList(employee.getCompanyId(), formSpec.getUniqueId(),
							null, 1);
			if (employeeTargetsConfigurationList != null && employeeTargetsConfigurationList.size() > 0) {
				for (EmployeeTargetsConfiguration employeeTargetsConfiguration : employeeTargetsConfigurationList) {
				EmployeeEventSubmissions employeeEventSubmissions = new EmployeeEventSubmissions(
						employee.getCompanyId(), employee.getEmpId(), form.getFormId() + "",
						employeeTargetsConfiguration.getModuleTypeId(),
						employeeTargetsConfiguration.getId());
				getWebExtensionManager().insertEmployeeEventSubmissions(employeeEventSubmissions);
				EmployeeEventSubmissionsAuditLogs employeeEventSubmissionsAuditLogs = new EmployeeEventSubmissionsAuditLogs(
						employee.getCompanyId(), employee.getEmpId(), form.getFormId() + "",
						employeeTargetsConfiguration.getModuleTypeId(),
						employeeTargetsConfiguration.getId(),employee.getEmpId());
				getWebExtensionManager().insertEmployeeEventSubmissionsAuditId(employeeEventSubmissionsAuditLogs);
				}
			}
		}
		}catch (Exception e) {
			Log.info(this.getClass(),
					"getFormContainer-employeeTargetsConfigurationList got Exception"+e);
		}
		// check for job,work and customer purpose forms..skip
		// job,work and customer purpose forms addition into
		// activity feeds
		if (formSpec.getPurpose() != FormSpec.PURPOUSE_JOB && formSpec.getPurpose() != FormSpec.PURPOUSE_CUSTOMER
				&& formSpec.getPurpose() != FormSpec.PURPOUSE_WORK) {
			webManager.addActivityStream(employee.getCompanyId(), employee.getEmpId(), employee.getEmpName(),
					form.getFormId(), ActivityStream.ACTIVITY_STREAM_FORM_MODIFIED, formSpecTitle, null);
		}
	}

	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-modifiedlogFormAuditCurrentTime End. Time taken to process for empId " + empId
						+ " : " + (Api.getCurrentTimeInUTCLong() - modifiedlogFormAuditCurrentTime));

	Long deletedlogFormAuditCurrentTime = Api.getCurrentTimeInUTCLong();
	for (Long formId : deleted) {
		getWebAdditionalSupportManager().logFormAudit(formId, employee.getEmpId(), employee.getCompanyId(),
				Constants.CHANGE_TYPE_DELETE, true, ipAddress, jmsMessages, false);
	}
	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-deletedlogFormAuditCurrentTime End. Time taken to process for empId " + empId
						+ " : " + (Api.getCurrentTimeInUTCLong() - deletedlogFormAuditCurrentTime));
	
	long processRestrictFromMobileFieldsStartTime = Api.getCurrentTimeInUTCLong();
	getWebAdditionalSupportExtraManager().auditRestrictFromMobileFields(employee, restrictFromMobileFields, restrictFromMobileSectionFields, formNewKeys, customerNewKeys, customEntityNewKeys, hardDeletedFormIdsInReq, formFieldSpecMap, formSectionFieldSpecMap);
	if (constants.isPrintLogs())
		Log.info(this.getClass(),"auditRestrictFromMobileFields End. Time taken to process for empId " + empId
						+ " : " + (Api.getCurrentTimeInUTCLong() - processRestrictFromMobileFieldsStartTime));
	
	//Validating the Customers Fields and escaling to spoors team when madatory fields are missing.
	if(!masterCustomerFormFieldsToValidate.isEmpty() && masterCustomerFormFieldsToValidate.size()>0 
			&& customerFormFieldSpecMap !=null && !customerFormFieldSpecMap.isEmpty()
			&& constantsExtra.isBackUpSqliteFile(employee.getCompanyId())) {
		Log.info(getClass(), "masterCustomerFormFieldItr masterCustomerFormFieldsToValidate happens");
		isMasterForm = true;
		Map<Long,List<FormField>> formIdAndFormFieldsMap = (Map)Api.getResolvedMapFromList(masterCustomerFormFieldsToValidate, "formId");
		for(Map.Entry<Long,List<FormField>> entry : formIdAndFormFieldsMap.entrySet())
		{
			List<FormField> customerFormFieldsEntry = entry.getValue();
			boolean customerMandatoryFormFieldsMissing = getWebFunctionalManager().validateCustomerFieldsForMissingMandatoryFields(
					customerFormFieldsEntry,customerFormFieldSpecMap, errorsTemp,isMasterForm);
			if(customerMandatoryFormFieldsMissing){
				customerMandatoryFormFieldsMissingMap.put("customerMandatoryFormFieldsMissing", true);
				break;
			}
		}
	}else
	{
		Log.info(getClass(), "masterCustomerFormFieldItr empId :"+empId+" masterCustomerFormFieldsToValidate rejected");
	}

	
	Long workflowContainerCurrentTime = Api.getCurrentTimeInUTCLong();
	if (formSubmissionData.getWorkflowDataContainer() != null) {
		List<WorkFlowFormStatus> workFlowFormStatusList = formSubmissionData.getWorkflowDataContainer()
				.getWorkflowFormStatus();
		List<WorkFlowFormStatus> existedWorkFlowFormStatus = mainManager.getExistedWorkFlows(workFlowFormStatusList);
		String formIds = Api.toCSVFromList(existedWorkFlowFormStatus, "formId");
		if (workFlowFormStatusList != null && !workFlowFormStatusList.isEmpty()) {
			List<WorkFlowFormStatus> bulkActionWorkFlowFormStatusList = new ArrayList<WorkFlowFormStatus>();
			// String formIds=Api.toCSVFromList(modified,
			// "formId");
			Map<String, String> formIdAuditMap = extraDao.getFormIdAndAuditMap(formIds);
			// formWorkflowStatusOldClientKeys=workFlowExtraDao.getFormWorkFlowStatusClientSideIds(workFlowFormStatusList,
			// code);
			Long workflowIdAndInitialStagesMapCurrentTime = Api.getCurrentTimeInUTCLong();
			String workflowIds = Api.toCSVFromList(workFlowFormStatusList, "workFlowId");
			Map<Long, WorkflowStage> workflowIdAndInitialStagesMap = workFlowExtraDao
					.getWorkflowIdAndInitialStagesMap(workflowIds);

			if (constants.isPrintLogs())
				Log.info(this.getClass(),
						"getFormContainer-deletedlogFormAuditCurrentTime End. Time taken to process for empId "
								+ empId + " : "
								+ (Api.getCurrentTimeInUTCLong() - workflowIdAndInitialStagesMapCurrentTime));

			
			
			for (WorkFlowFormStatus workFlowFormStatus : workFlowFormStatusList) {


				Api.convertDateTimesToGivenType(workFlowFormStatus,
						DateConversionType.XSD_TO_STADARD,
						"clientModifiedTime");
				
				Long formId = workFlowFormStatus.getFormId();
				if (formId == null) {
					formId = formNewKeys.get(workFlowFormStatus.getClientFormId());
					workFlowFormStatus.setFormId(formId);
				}
				if(hardDeletedFormIdsInReq.contains(workFlowFormStatus.getFormId())) {
					continue;
				}
				Long workFlowFormStatusOldCurrentTime = Api.getCurrentTimeInUTCLong();
				WorkFlowFormStatus workFlowFormStatusOld = workFlowExtraDao.getWorkFlowFormStatus(formId);
				WorkFlowFormStatus workFlowFormStatusOldOriginal = null;
				if (formId != null) {
					workFlowFormIds.add(formId);
				}

				if (constants.isPrintLogs())
					Log.info(this.getClass(),
							"getFormContainer-workFlowFormStatusOldCurrentTime End. Time taken to process for empId "
									+ empId + " : "
									+ (Api.getCurrentTimeInUTCLong() - workFlowFormStatusOldCurrentTime));

				if (workFlowFormStatusOld == null) {

					Long workFlowFormStatusOldNULLRecordProcessingCurrentTime = Api.getCurrentTimeInUTCLong();
					WorkflowStage workflowStage = workflowIdAndInitialStagesMap
							.get(workFlowFormStatus.getWorkFlowId());

					webManager.populateWorkflowFormStatus(workFlowFormStatus, "" + formId, employee.getCompanyId(),
							workflowStage, employee, employee.getEmpId());

					FormSpec formSpec = webManager.getFormSpec(workFlowFormStatus.getFormSpecId() + "");
					formSpec = webManager.getLatestFormSpec(formSpec.getUniqueId());
					if (formSpec.isDeleted()) {
						workFlowFormStatus.setStatus(WorkFlowFormStatus.STATUS_TYPE_REJECTED);
						workFlowFormStatus.setStatusMessage(constants.getRejectedBySytemDueToFormModify());
					}	

					//
					workFlowFormStatus.setClientCode(code);
					// workFlowFormStatus.setStatusMessage("waiting");
					workFlowExtraDao.insertWorkflowStatus(workFlowFormStatus);
					formWorkflowStatusClientKeys.put(workFlowFormStatus.getId(),
							workFlowFormStatus.getClientSideId());
					if (workFlowFormStatus.getHistoryId() != null) {
						// long historyId=
						// workFlowManager.insertOrUpdateWorkFlowFormStatusHistory(employee,
						// null, workFlowFormStatus,
						// employee.getEmpId());
						Long currentTimeTYPE_WORK_FLOW = Api.getCurrentTimeInUTCLong();
						
						/*webManager.sendJmsMessage(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(),
								employee.getCompanyId(), employee.getEmpId(), -1, workFlowFormStatus.getId(), null,
								true, jmsMessages);*/
						boolean ignoreFirebasePushNotification = false;
						if(workFlowFormStatus.getStatus() != null && (workFlowFormStatus.getStatus().shortValue()  == WorkFlowFormStatus.STATUS_TYPE_APPROVED ||
								workFlowFormStatus.getStatus().shortValue()  == WorkFlowFormStatus.STATUS_TYPE_REJECTED)) {
							ignoreFirebasePushNotification = true;
							bulkActionWorkFlowFormStatusList.add(workFlowFormStatus);
						}
						JmsMessage jmsMessage = Api.getJmsMessage(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(),
								employee.getCompanyId(), employee.getEmpId(), -1, workFlowFormStatus.getId(), null,
								true,employee.getEmpId()+"",ignoreFirebasePushNotification);
						jmsMessages.add(jmsMessage);
						
						CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus = new CustomerRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), employee.getCompanyId(), employee.getEmpId(), -1,
								workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
						CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus = new CustomEntityRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), employee.getCompanyId(), employee.getEmpId(), -1,
								workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
						WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus = new WorkRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), employee.getCompanyId(), employee.getEmpId(), -1,
								workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
						EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus = new EmployeeRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowFormStatus.getHistoryId(), employee.getCompanyId(), employee.getEmpId(), -1,
								workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
						getWebExtensionManager().getAllReqConfiurations(workFlowFormStatus.getFormSpecId(),customerRequisitionJmsMessageStatus,customEntityRequisitionJmsMessageStatus,workRequisitionJmsMessageStatus,employeeRequisitionJmsMessageStatus);
						
						if(employee.getCompanyId() == reportConstants.getCdsgCompanyId()) {
							  getExtraSupportAdditionalDao().insertWorkFlowForCdsgStockUpdate(workFlowFormStatus.getId(), employee.getCompanyId(), JmsMessage.TYPE_WORK_FLOW, -1, 0);
						  }
						if(formSpec.getPurpose() == -1)
						{
							CustomEntitySpecStockConfiguration customEntitySpecStockConfiguration = getExtraSupportAdditionalSupportDao().getCustomEntitySpecStockConfigurationByFormSpecUniqueId(formSpec.getUniqueId(),1);
							
							if(!Api.isEmptyObj(customEntitySpecStockConfiguration))
							{
								getExtraSupportAdditionalSupportDao().insertIntoCustomEntityCreationOnStockUpdation(formId,customEntitySpecStockConfiguration);
							}
						}
						
						if (formSpec.getPurpose() == 12) {
						    try {
									int processingStatus = CustomerOnDemandMapping.UNPROCESSED_STATUS;
									int triggerEvent = CustomerOnDemandMapping.TRIGGER_EVENT_TYPE_APPROVED_FORM;
		
									List<EmployeeOnDemandMapping> employeeCreationOnDemandList = getWebExtensionManager()
											.getEmployeesOnDemandMappingByUniqueId(formSpec.getUniqueId(),
													formSpec.getCompanyId(), triggerEvent);
		
									if (employeeCreationOnDemandList != null && employeeCreationOnDemandList.size() > 0) {
										EmployeeOnDemandMapping employeeOnDemandMapping = employeeCreationOnDemandList
												.get(0);
										EmployeeRequisitionForm	 empRequisitionForm = new EmployeeRequisitionForm(
												Integer.parseInt(employee.getCompanyId() + ""), workFlowFormStatus.getId(),
												workFlowFormStatus.getFormId(), triggerEvent, processingStatus,
												employeeOnDemandMapping.getId());
		
										getWebExtensionManager().insertEmployeeRequisitionForm(empRequisitionForm);
		
									}
		
							} catch (Exception e) {
		

							}
					    }

						if (constants.isPrintLogs())
							Log.info(this.getClass(),
									"JmsMessage for TYPE_WORK_FLOW End. Time taken to process for empId " + empId
											+ " : " + (Api.getCurrentTimeInUTCLong() - currentTimeTYPE_WORK_FLOW));

					} else {
						Long currentTimeTYPE_WORK_FLOW = Api.getCurrentTimeInUTCLong();
						webManager.sendJmsMessage(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND,
								employee.getCompanyId(), employee.getEmpId(), -1, workFlowFormStatus.getId(), null,
								true, jmsMessages);
						CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus = new CustomerRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, employee.getCompanyId(), employee.getEmpId(), -1,
								workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
						CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus = new CustomEntityRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, employee.getCompanyId(), employee.getEmpId(), -1,
								workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
						WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus = new WorkRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, employee.getCompanyId(), employee.getEmpId(), -1,
								workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
						EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus = new EmployeeRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, JmsMessage.NO_WORKFLOW_HISTORY_FOUND, employee.getCompanyId(), employee.getEmpId(), -1,
								workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
						getWebExtensionManager().getAllReqConfiurations(workFlowFormStatus.getFormSpecId(),customerRequisitionJmsMessageStatus,customEntityRequisitionJmsMessageStatus,workRequisitionJmsMessageStatus,employeeRequisitionJmsMessageStatus);
						
						if(employee.getCompanyId() == reportConstants.getCdsgCompanyId()) {
							  getExtraSupportAdditionalDao().insertWorkFlowForCdsgStockUpdate(workFlowFormStatus.getId(), employee.getCompanyId(), JmsMessage.TYPE_WORK_FLOW, -1, 0);
						  }
						if(formSpec.getPurpose() == -1)
						{
							CustomEntitySpecStockConfiguration customEntitySpecStockConfiguration = getExtraSupportAdditionalSupportDao().getCustomEntitySpecStockConfigurationByFormSpecUniqueId(formSpec.getUniqueId(),1);
							
							if(!Api.isEmptyObj(customEntitySpecStockConfiguration))
							{
								getExtraSupportAdditionalSupportDao().insertIntoCustomEntityCreationOnStockUpdation(formId,customEntitySpecStockConfiguration);
							}
						}
						
						if (formSpec.getPurpose() == 12) {
						    try {
							
									int processingStatus = CustomerOnDemandMapping.UNPROCESSED_STATUS;
									int triggerEvent = CustomerOnDemandMapping.TRIGGER_EVENT_TYPE_APPROVED_FORM;
		
									List<EmployeeOnDemandMapping> employeeCreationOnDemandList = getWebExtensionManager()
											.getEmployeesOnDemandMappingByUniqueId(formSpec.getUniqueId(),
													formSpec.getCompanyId(), triggerEvent);
		
									if (employeeCreationOnDemandList != null && employeeCreationOnDemandList.size() > 0) {
										EmployeeOnDemandMapping employeeOnDemandMapping = employeeCreationOnDemandList
												.get(0);
										EmployeeRequisitionForm empRequisitionForm = new EmployeeRequisitionForm(
												Integer.parseInt(employee.getCompanyId() + ""), workFlowFormStatus.getId(),
												workFlowFormStatus.getFormId(), triggerEvent, processingStatus,
												employeeOnDemandMapping.getId());
		
										getWebExtensionManager().insertEmployeeRequisitionForm(empRequisitionForm);
		
									}
		
							} catch (Exception e) {
		

							}
					    }

						if (constants.isPrintLogs())
							Log.info(this.getClass(),
									"JmsMessage for TYPE_WORK_FLOW_NO_WORKFLOW_HISTORY_FOUND End. Time taken to process for empId "
											+ empId + " : "
											+ (Api.getCurrentTimeInUTCLong() - currentTimeTYPE_WORK_FLOW));

					}

					// webManager.sendJmsMessage(JmsMessage.TYPE_WORK_FLOW,
					// JmsMessage.NO_WORKFLOW_HISTORY_FOUND,
					// employee.getCompanyId(),
					// employee.getEmpId(), -1,
					// workFlowFormStatus.getId(), null);
					if (constants.isPrintLogs())
						Log.info(this.getClass(),
								"getFormContainer-workFlowFormStatusOldNULLRecordProcessingCurrentTime End. Time taken to process for empId "
										+ empId + " : " + (Api.getCurrentTimeInUTCLong()
												- workFlowFormStatusOldNULLRecordProcessingCurrentTime));

				} else {
					// WorkflowStage workflowStage=
					// workflowIdAndInitialStagesMap.get(workFlowFormStatusOld.getWorkFlowId());
					try {
						workFlowFormStatusOldOriginal = (WorkFlowFormStatus) workFlowFormStatusOld.clone();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
					Long workFlowFormStatusOldUpdateRecordProcessingCurrentTime = Api.getCurrentTimeInUTCLong();
					workFlowFormStatusOld
							.setStatusMessage(workFlowManager.getStatusMessage(workFlowFormStatus.getStatus()));
					workFlowFormStatusOld.setClientCode(code);
					workFlowFormStatusOld.setClientSideId(workFlowFormStatus.getClientSideId());
					workFlowFormStatusOld.setRemarks(workFlowFormStatus.getRemarks());
					workFlowFormStatusOld
							.setModifiedTime(Api.getDateTimeInUTC(new Date(System.currentTimeMillis())));
					WorkflowStage workflowStage = workflowIdAndInitialStagesMap
							.get(workFlowFormStatus.getWorkFlowId());

					/*
					 * workFlowFormStatusOld .setStatus(workFlowFormStatus .getStatus());
					 */

					boolean check = false;
					WorkflowStage oldWorkFlowStage = workFlowManager
							.getWorkFlowStage(workFlowFormStatusOld.getWorkFlowStageId());
					WorkflowStage newWorkFlowStage = workFlowManager
							.getWorkFlowStage(workFlowFormStatus.getWorkFlowStageId());
					if (oldWorkFlowStage.getStageType() == WorkflowStage.STAGE_TYPE_EMPLOYEE_GROUP_BASED) {
						if (workFlowFormStatusOld.getStatus() != null && workFlowFormStatusOld.getStatus()
								.shortValue() != workFlowFormStatus.getStatus()) {
							check = true;
						}
						if (workFlowFormStatusOld.getApprovedBy() == null
								|| (workFlowFormStatusOld.getApprovedBy() != employee.getEmpId())) {
							check = true;
						}

					} else if (oldWorkFlowStage.getStageType() == WorkflowStage.STAGE_TYPE_HIERARCHY_BASED) {
						if (workFlowFormStatusOld.getManagerRank() != null
								&& workFlowFormStatusOld.getManagerRank() == employee.getEmpId()) {
							check = true;
						}
						if (workFlowFormStatusOld.getSubmittedBy() != null
								&& workFlowFormStatusOld.getSubmittedBy().longValue() == employee.getEmpId()) {
							check = true;
						}
					} else if (oldWorkFlowStage.getStageType() == WorkflowStage.STAGE_TYPE_ROLE_BASED) {
						if (workFlowFormStatusOld.getCurrentRank() != null
								&& workFlowFormStatusOld.getCurrentRank() == employee.getRank()) {
							check = true;
						}
					}
					// Deva,2018-09-03,if approved comes again in sync we are not updating status
					Workflow workFlow = webManager.getWorkflow(oldWorkFlowStage.getWorkflowId());
					if (workFlowFormStatusOld.getStatus().shortValue() == WorkFlowFormStatus.STATUS_TYPE_APPROVED
							&& !workFlow.isCanRejectAfterApproval()) {

						if (workFlowFormStatus.getStatus()
								.shortValue() == WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED
								&& workFlowFormStatus.getSubmittedBy() == employee.getEmpId()
								&& workFlow.isReintiateApprovedFormAfterModify()) {
							check = true;
							Log.info(getClass(), "reintiating for formId = " + workFlowFormStatus.getFormId());
						} else
							check = false;
					}

					if (workFlowFormStatusOld != null && workFlowFormStatusOld.getWorkFlowId()
							.longValue() != workFlowFormStatus.getWorkFlowId().longValue()) {
						check = true;
					}

					if(workFlowFormStatusOld!=null ) {
						String clientModifiedTime = workFlowFormStatusOld.getClientModifiedTime();
						Log.info(getClass(), "workFlowFormStatusOld FormId is = "+workFlowFormStatusOld.getFormId());
						String currentClientModifiedTime = workFlowFormStatus.getClientModifiedTime();
						Log.info(getClass(), "workFlowFormStatus currentClientModifiedTime is = "+currentClientModifiedTime);
						Log.info(getClass(), "workFlowFormStatus getSubmittedBy() is = "+workFlowFormStatus.getSubmittedBy()
										+ "workFlowFormStatusOld getSubmittedBy() "+workFlowFormStatusOld.getSubmittedBy());
						
						if(!Api.isEmptyString(clientModifiedTime) && !Api.isEmptyString(currentClientModifiedTime) && 
								( (workFlowFormStatus.getSubmittedBy() !=null && workFlowFormStatusOld.getSubmittedBy() !=null) &&
										(workFlowFormStatus.getSubmittedBy() == workFlowFormStatusOld.getSubmittedBy().longValue()) ) &&
							!Api.compareTwoDateTimesBefore(clientModifiedTime,currentClientModifiedTime)) {
							Log.info(getClass(), "check = "+false);
							check = false;
						}
					}
					
					if (check) {

						workFlowFormStatusOld.setStatus(workFlowFormStatus.getStatus());

						if (Api.isEmptyString(approvalAware) || "0".equalsIgnoreCase(approvalAware)) {
							// old code : does not support workflow approval for sync
							if (workFlowFormStatusOld.getStatus() == WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED
									|| workFlowFormStatusOld
											.getStatus() == WorkFlowFormStatus.STATUS_TYPE_CANCELED) {
								WorkFlowFormStatusHistory workFlowFormStatusHistory = new WorkFlowFormStatusHistory();
								workFlowFormStatusHistory.setApprovedBy(workFlowFormStatusOld.getApprovedBy());
								String auditId = formIdAuditMap.get("" + workFlowFormStatusOld.getFormId());
								if (!Api.isEmptyString(auditId) && workFlowFormStatusOld
										.getStatus() != WorkFlowFormStatus.STATUS_TYPE_CANCELED) {
									workFlowFormStatusOld.setAuditId(Long.parseLong(auditId));
								}
								if (workFlowFormStatusOld
										.getStatus() == WorkFlowFormStatus.STATUS_TYPE_RESUBMITTED) {

									workFlowManager
											.populateWorkflowFormStatusBasedOnApproveOrResubmit(
													workFlowFormStatusOld,
													workflowStage,
													employee.getRank(),
													employee.getCompanyId(),
													workflowStage
															.getStageType(),
													employee.getEmpId(),oldWorkFlowStage.isEndRuleType(), false , false);
								}

								long historyId = webManager.insertOrUpdateWorkFlowFormStatusHistory(employee, null,
										workFlowFormStatusOld, employee.getEmpId());

								Long currentTimeTYPE_WORK_FLOW = Api.getCurrentTimeInUTCLong();
								
								
								/*webManager.sendJmsMessage(JmsMessage.TYPE_WORK_FLOW, historyId,
										employee.getCompanyId(), employee.getEmpId(), -1,
										workFlowFormStatus.getId(), null, true, jmsMessages);*/
								boolean ignoreFirebasePushNotification = false;
								if(workFlowFormStatus.getStatus() != null && (workFlowFormStatus.getStatus().shortValue()  == WorkFlowFormStatus.STATUS_TYPE_APPROVED ||
										workFlowFormStatus.getStatus().shortValue()  == WorkFlowFormStatus.STATUS_TYPE_REJECTED)) {
									ignoreFirebasePushNotification = true;
									bulkActionWorkFlowFormStatusList.add(workFlowFormStatus);
								}
								JmsMessage jmsMessage = Api.getJmsMessage(JmsMessage.TYPE_WORK_FLOW, historyId,
										employee.getCompanyId(), employee.getEmpId(), -1, workFlowFormStatus.getId(), null,
										true,employee.getEmpId()+"",ignoreFirebasePushNotification);
								jmsMessages.add(jmsMessage);
								
								CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus = new CustomerRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, historyId, employee.getCompanyId(), employee.getEmpId(), -1,
										workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
								CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus = new CustomEntityRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW,historyId, employee.getCompanyId(), employee.getEmpId(), -1,
										workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
								WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus = new WorkRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW,historyId, employee.getCompanyId(), employee.getEmpId(), -1,
										workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
								EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus = new EmployeeRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW,historyId, employee.getCompanyId(), employee.getEmpId(), -1,
										workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
								getWebExtensionManager().getAllReqConfiurations(workFlowFormStatus.getFormSpecId(),customerRequisitionJmsMessageStatus,customEntityRequisitionJmsMessageStatus,workRequisitionJmsMessageStatus,employeeRequisitionJmsMessageStatus);
								
								if(employee.getCompanyId() == reportConstants.getCdsgCompanyId()) {
									  getExtraSupportAdditionalDao().insertWorkFlowForCdsgStockUpdate(workFlowFormStatus.getId(), employee.getCompanyId(), JmsMessage.TYPE_WORK_FLOW, -1, 0);
								  }
								
								if (constants.isPrintLogs())
									Log.info(this.getClass(),
											"JmsMessage for TYPE_WORK_FLOW End. Time taken to process for empId "
													+ empId + " : "
													+ (Api.getCurrentTimeInUTCLong() - currentTimeTYPE_WORK_FLOW));
							}

							// old code : does not support work flow approval for sync
							webManager.populateWorkflowFormStatus(workFlowFormStatusOld, "" + formId,
									employee.getCompanyId(), workflowStage, employee, employee.getEmpId());

							FormSpec formSpec = webManager.getFormSpec(workFlowFormStatus.getFormSpecId() + "");
							formSpec = webManager.getLatestFormSpec(formSpec.getUniqueId());
							if (formSpec.isDeleted()) {
								workFlowFormStatus.setStatus(WorkFlowFormStatus.STATUS_TYPE_REJECTED);
								workFlowFormStatus.setStatusMessage(constants.getRejectedBySytemDueToFormModify());
							}
							if (formSpec.getPurpose() == -1) {
								CustomEntitySpecStockConfiguration customEntitySpecStockConfiguration = getExtraSupportAdditionalSupportDao()
										.getCustomEntitySpecStockConfigurationByFormSpecUniqueId(
												formSpec.getUniqueId(), 1);

								if (!Api.isEmptyObj(customEntitySpecStockConfiguration)) {
									getExtraSupportAdditionalSupportDao()
											.insertIntoCustomEntityCreationOnStockUpdation(formId,
													customEntitySpecStockConfiguration);
								}
							}
							
							if (formSpec.getPurpose() == 12) {
							    try {
								
										int processingStatus = CustomerOnDemandMapping.UNPROCESSED_STATUS;
										int triggerEvent = CustomerOnDemandMapping.TRIGGER_EVENT_TYPE_APPROVED_FORM;
			
										List<EmployeeOnDemandMapping> employeeCreationOnDemandList = getWebExtensionManager()
												.getEmployeesOnDemandMappingByUniqueId(formSpec.getUniqueId(),
														formSpec.getCompanyId(), triggerEvent);
			
										if (employeeCreationOnDemandList != null && employeeCreationOnDemandList.size() > 0) {
											EmployeeOnDemandMapping employeeOnDemandMapping = employeeCreationOnDemandList
													.get(0);
											EmployeeRequisitionForm empRequisitionForm = new EmployeeRequisitionForm(
													Integer.parseInt(employee.getCompanyId() + ""), workFlowFormStatus.getId(),
													workFlowFormStatus.getFormId(), triggerEvent, processingStatus,
													employeeOnDemandMapping.getId());
			
											getWebExtensionManager().insertEmployeeRequisitionForm(empRequisitionForm);
			
										}
			
								} catch (Exception e) {
			

								}
						    }


							workFlowExtraDao.updateWorkFlowFormStatusForform(workFlowFormStatusOld);
						} else {
							WorkFlowFormStatusHistory workFlowFormStatusHistory = new WorkFlowFormStatusHistory();
							workFlowFormStatusHistory.setApprovedBy(workFlowFormStatusOld.getApprovedBy());
							String auditId = formIdAuditMap.get("" + workFlowFormStatusOld.getFormId());
							if (!Api.isEmptyString(auditId)) {
								workFlowFormStatusOld.setAuditId(Long.parseLong(auditId));
							}
							workFlowFormStatus.setCreatedTime(workFlowFormStatusOld.getCreatedTime());
							workFlowFormStatus.setSubmittedTime(workFlowFormStatusOld.getSubmittedTime());
							workFlowFormStatus.setFormIdentifier(workFlowFormStatusOld.getFormIdentifier());
							workFlowFormStatus.setEmpgroupId(workFlowFormStatusOld.getEmpgroupId());
							workFlowFormStatus.setManagerRank(workFlowFormStatusOld.getManagerRank());
							workFlowFormStatus.setId(workFlowFormStatusOld.getId());

							WebUser webUser = new WebUser();
							webUser.setCompanyId(employee.getCompanyId());
							webUser.setEmpId(employee.getEmpId());
				
							WorkFlowRealData workFlowRealData = workFlowManager.getWorkFlowRealData(workFlowFormStatus.getFormId());
							boolean isService = false;
							Long workFlowStatusHistoryId = workFlowManager.insertOrUpdateWorkFlowFormStatusHistory(employee,webUser, workFlowFormStatus, webUser.getEmpId(),isService);
							
							FormSpec formSpec = webManager.getFormSpec(workFlowFormStatus.getFormSpecId()+"");
							formSpec = webManager.getLatestFormSpec(formSpec.getUniqueId());
							if (formSpec.isDeleted()) {
								workFlowFormStatus.setStatus(WorkFlowFormStatus.STATUS_TYPE_REJECTED);
								workFlowFormStatus.setStatusMessage(constants.getRejectedBySytemDueToFormModify());
								workFlowManager.updateWorkFlowFormStatusReject(workFlowFormStatus);

							}
							else if (workFlowStatusHistoryId != null)
							{
								workFlowFormStatus.setStatusMessage(workFlowManager.getWorkFlowStatusMessage(workFlowFormStatus,webUser.getCompanyId(),employee.getRank(),ipAddress,null));
								workFlowManager.insertOrUpdateWorkFlowFormStatus(workFlowRealData,webUser,employee, workFlowFormStatus,workFlowFormStatusOldOriginal,false,isService);
								Long currentTimeTYPE_WORK_FLOW = Api.getCurrentTimeInUTCLong();
								if(workFlowFormStatus.isTriggerJms()) {
										
									   /*webManager.sendJmsMessage(JmsMessage.TYPE_WORK_FLOW, workFlowStatusHistoryId,
											webUser.getCompanyId(), webUser.getEmpId(), -1, workFlowFormStatus.getId(),
											null, true, jmsMessages);*/
										boolean ignoreFirebasePushNotification = false;
										if(workFlowFormStatus.getStatus() != null && (workFlowFormStatus.getStatus().shortValue()  == WorkFlowFormStatus.STATUS_TYPE_APPROVED ||
												workFlowFormStatus.getStatus().shortValue()  == WorkFlowFormStatus.STATUS_TYPE_REJECTED)) {
											ignoreFirebasePushNotification = true;
											bulkActionWorkFlowFormStatusList.add(workFlowFormStatus);
										}
										JmsMessage jmsMessage = Api.getJmsMessage(JmsMessage.TYPE_WORK_FLOW, workFlowStatusHistoryId,
												employee.getCompanyId(), employee.getEmpId(), -1, workFlowFormStatus.getId(), null,
												true,employee.getEmpId()+"",ignoreFirebasePushNotification);
										jmsMessages.add(jmsMessage);
										
										CustomerRequisitionJmsMessageStatus customerRequisitionJmsMessageStatus = new CustomerRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowStatusHistoryId, webUser.getCompanyId(), webUser.getEmpId(), -1,
												workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
										CustomEntityRequisitionJmsMessageStatus customEntityRequisitionJmsMessageStatus = new CustomEntityRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowStatusHistoryId, webUser.getCompanyId(), webUser.getEmpId(), -1,
												workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
										WorkRequisitionJmsMessageStatus workRequisitionJmsMessageStatus = new WorkRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW, workFlowStatusHistoryId, webUser.getCompanyId(), webUser.getEmpId(), -1,
												workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
										EmployeeRequisitionJmsMessageStatus employeeRequisitionJmsMessageStatus = new EmployeeRequisitionJmsMessageStatus(JmsMessage.TYPE_WORK_FLOW,workFlowStatusHistoryId, webUser.getCompanyId(), webUser.getEmpId(), -1,
												workFlowFormStatus.getId(),true,workFlowFormStatus.getFormId());
										getWebExtensionManager().getAllReqConfiurations(workFlowFormStatus.getFormSpecId(),customerRequisitionJmsMessageStatus,customEntityRequisitionJmsMessageStatus,workRequisitionJmsMessageStatus,employeeRequisitionJmsMessageStatus);
										
								}
								if(webUser.getCompanyId().longValue() == reportConstants.getCdsgCompanyId()) {
									  getExtraSupportAdditionalDao().insertWorkFlowForCdsgStockUpdate(workFlowFormStatus.getId(), webUser.getCompanyId(), JmsMessage.TYPE_WORK_FLOW, -1, 0);
								  }
								if (formSpec.getPurpose() == -1) {
									CustomEntitySpecStockConfiguration customEntitySpecStockConfiguration = getExtraSupportAdditionalSupportDao()
											.getCustomEntitySpecStockConfigurationByFormSpecUniqueId(
													formSpec.getUniqueId(), 1);

									if (!Api.isEmptyObj(customEntitySpecStockConfiguration)) {
										getExtraSupportAdditionalSupportDao()
												.insertIntoCustomEntityCreationOnStockUpdation(formId,
														customEntitySpecStockConfiguration);
									}
									
								}
								if (formSpec.getPurpose() == 12) {
									try {
										int processingStatus = CustomerOnDemandMapping.UNPROCESSED_STATUS;
										int triggerEvent = CustomerOnDemandMapping.TRIGGER_EVENT_TYPE_APPROVED_FORM;

										List<EmployeeOnDemandMapping> employeeCreationOnDemandList = getWebExtensionManager()
												.getEmployeesOnDemandMappingByUniqueId(formSpec.getUniqueId(),
														formSpec.getCompanyId(), triggerEvent);

										if (employeeCreationOnDemandList != null
												&& employeeCreationOnDemandList.size() > 0) {
											EmployeeOnDemandMapping employeeOnDemandMapping = employeeCreationOnDemandList
													.get(0);
											EmployeeRequisitionForm empRequisitionForm = new EmployeeRequisitionForm(
													Integer.parseInt(webUser.getCompanyId() + ""),
													workFlowFormStatus.getId(), workFlowFormStatus.getFormId(),
													triggerEvent, processingStatus,
													employeeOnDemandMapping.getId());

											getWebExtensionManager()
													.insertEmployeeRequisitionForm(empRequisitionForm);

										}

									} catch (Exception e) {
										Log.info(getClass(), "saveWorkFlowFormStatus() // companyId = "
												+ webUser.getCompanyId() + " webuserEmpId = " + webUser.getEmpId());
									}
								}


								if (constants.isPrintLogs())
									Log.info(this.getClass(),
											"JmsMessage for TYPE_WORK_FLOW End. Time taken to process for empId "
													+ empId + " : "
													+ (Api.getCurrentTimeInUTCLong() - currentTimeTYPE_WORK_FLOW));
							}
						}
					} else {
						if (workFlowFormStatusOld != null && workFlowFormStatusOld.getId() != null) {
							extraSupportDao.updateWorkFlowFormStatusModifiedTime(workFlowFormStatusOld.getId());
						}
					}
					// WorkflowStage workflowStage=
					// workflowIdAndInitialStagesMap.get(workFlowFormStatus.getWorkFlowId());

					if (constants.isPrintLogs())
						Log.info(this.getClass(),
								"getFormContainer-workFlowFormStatusOldUpdateRecordProcessingCurrentTime End. Time taken to process for empId "
										+ empId + " : " + (Api.getCurrentTimeInUTCLong()
												- workFlowFormStatusOldUpdateRecordProcessingCurrentTime));
				}
			}
			processBulkActionProcess(employee, bulkActionWorkFlowFormStatusList, jmsMessages);
		}

	}

	
	if (constants.isPrintLogs())
		Log.info(this.getClass(),
				"getFormContainer-workflowContainerCurrentTime End. Time taken to process for empId " + empId
						+ " : " + (Api.getCurrentTimeInUTCLong() - workflowContainerCurrentTime));


	
	
	return addedModifiedFormIds;
}
	
	public void processBulkActionProcess(Employee employee,List<WorkFlowFormStatus> bulkActionWorkFlowFormStatusList,
			List<JmsMessage> jmsMessages) {
		
		String logText = "bulkActionWorkFlowFormStatusList() // empId = "+employee.getEmpId();
		Log.info(getClass(), logText+" bulkActionWorkFlowFormStatusList size = "+bulkActionWorkFlowFormStatusList.size());
		if(bulkActionWorkFlowFormStatusList.size() == 0) {
			return;
		}
		List<Long> approvedWorkFlowStatusIds = null;
		List<Long> rejectedWorkFlowStatusIds = null;
		List<Long> approvedFormIds = null;
		List<Long> rejectedFormIds = null;
		Map<Long,List<WorkFlowFormStatus>> formSpecIdAndWorkFlowFormStatusListMap = (Map)Api.getGroupedMapFromList(bulkActionWorkFlowFormStatusList, "formSpecId",null);
		long formSpecId = 0;
		List<WorkFlowFormStatus> workFlowFormStatusList = null;
		for (Entry<Long, List<WorkFlowFormStatus>> entry : formSpecIdAndWorkFlowFormStatusListMap.entrySet()) {
		
			 formSpecId = entry.getKey();
			 workFlowFormStatusList = entry.getValue();
			 approvedWorkFlowStatusIds = new ArrayList<Long>();
			 rejectedWorkFlowStatusIds = new ArrayList<Long>();
			 approvedFormIds = new ArrayList<Long>();
			 rejectedFormIds = new ArrayList<Long>();
			for (WorkFlowFormStatus workFlowFormStatus : workFlowFormStatusList) {
				if(workFlowFormStatus.getStatus().shortValue()  == WorkFlowFormStatus.STATUS_TYPE_APPROVED) {
					approvedWorkFlowStatusIds.add(workFlowFormStatus.getId());
					approvedFormIds.add(workFlowFormStatus.getFormId());
				}else if(workFlowFormStatus.getStatus().shortValue()  == WorkFlowFormStatus.STATUS_TYPE_REJECTED) {
					rejectedWorkFlowStatusIds.add(workFlowFormStatus.getId());
					rejectedFormIds.add(workFlowFormStatus.getFormId());
				}
			}
			if(approvedWorkFlowStatusIds.size() > 0) {
				JmsMessage jmsMessage = Api.getJmsMessageForBulkApproval(JmsMessage.TYPE_WORK_FLOW_BULK_ACTION, approvedWorkFlowStatusIds.get(0),
						employee.getCompanyId(), employee.getEmpId(), JmsMessage.A_SUBMISSION_IS_APPROVED, approvedWorkFlowStatusIds.get(0), null,
						true,employee.getEmpId()+"",false,true);
				jmsMessage.setWorkFlowFormStatusIds(Api.toCSV(approvedWorkFlowStatusIds));
				jmsMessage.setFormIds(Api.toCSV(approvedFormIds));
				jmsMessages.add(jmsMessage);
			}
			if(rejectedWorkFlowStatusIds.size() > 0) {
				JmsMessage jmsMessage = Api.getJmsMessageForBulkApproval(JmsMessage.TYPE_WORK_FLOW_BULK_ACTION, rejectedWorkFlowStatusIds.get(0),
						employee.getCompanyId(), employee.getEmpId(), JmsMessage.A_SUBMISSION_IS_REJECTED, rejectedWorkFlowStatusIds.get(0), null,
						true,employee.getEmpId()+"",false,true);
				jmsMessage.setWorkFlowFormStatusIds(Api.toCSV(rejectedWorkFlowStatusIds));
				jmsMessage.setFormIds(Api.toCSV(rejectedFormIds));
				jmsMessages.add(jmsMessage);
			}
			Log.info(getClass(), logText+" formSpecId = "+formSpecId+" approvedFormIds = "+approvedFormIds);
			Log.info(getClass(), logText+" formSpecId = "+formSpecId+" rejectedFormIds = "+rejectedFormIds);
		}
		
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
	public void computeFieldsForSync(String hasAutoComputeCapability,List<Form> added,List<Form> modified,
			   List<FormField> fields,List<FormSectionField> sectionFields,Map<Long, FormFieldSpec> formFieldSpecMap,Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap,
			   Employee employee,List<Long> hardDeletedFormIdsInReq) {
			if ("0".equalsIgnoreCase(hasAutoComputeCapability)) {
				List<Form> allForms = new ArrayList<Form>();
				allForms.addAll(added);
				allForms.addAll(modified);
				mainManager.computeFieldsForSync(fields, sectionFields, formFieldSpecMap, formSectionFieldSpecMap, allForms, employee, hardDeletedFormIdsInReq);

			}
	   }
}
