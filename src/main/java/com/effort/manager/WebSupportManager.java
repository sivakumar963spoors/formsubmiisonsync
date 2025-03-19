package com.effort.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.effort.context.AppContext;
import com.effort.dao.ExtraSupportAdditionalDao;
import com.effort.dao.ExtraSupportAdditionalSupportDao;
import com.effort.dao.ExtraSupportDao;
import com.effort.dao.MediaDao;
import com.effort.entity.AutoGenereteSequenceSpecConfiguarationField;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeGroup;
import com.effort.entity.FieldSpecRestrictionGroup;
import com.effort.entity.FieldValidationCritiria;
import com.effort.entity.Form;
import com.effort.entity.FormAndField;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSpec;
import com.effort.entity.Media;
import com.effort.entity.WebUser;
import com.effort.entity.Workflow;
import com.effort.settings.Constants;
import com.effort.util.Api;
import com.effort.util.Api.CsvOptions;
import com.effort.util.Log;
@Service
public class WebSupportManager {
	
	@Autowired
	private WebManager webManager;
	@Autowired
	private ExtraSupportAdditionalDao extraSupportAdditionalDao;
	@Autowired
	private ExtraSupportDao extraSupportDao;

	@Autowired
	private Constants constants;
	
	@Autowired
	private MediaDao mediaDao;
	
	private WebAdditionalSupportExtraManager getWebAdditionalSupportExtraManager(){
		WebAdditionalSupportExtraManager webAdditionalSupportExtraManager = AppContext.getApplicationContext().getBean("webAdditionalSupportExtraManager",WebAdditionalSupportExtraManager.class);
		return webAdditionalSupportExtraManager;
	}
	
	private WebExtensionManager getWebExtensionManager(){
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
		return webExtensionManager;
	}
	
	private ExtraSupportAdditionalSupportDao getExtraSupportAdditionalSupportDao(){
		ExtraSupportAdditionalSupportDao extraSupportAdditionalSupportDao = AppContext.getApplicationContext().getBean("extraSupportAdditionalSupportDao",ExtraSupportAdditionalSupportDao.class);
		return extraSupportAdditionalSupportDao;
	}
	
	public List<FormField> getAutoGenSeqFields(List<FormField> fields,String tzo,Map<Long, List<FormField>> oldFormFieldsMap, 
			Collection<Long> allformIdColletctions, boolean isMasterForm, List<Long> hardDeletedFormIdsInReq,
			List<FormSectionField> sectionFields){

		List<FormField> autoGenSeqFields = new ArrayList<FormField>();
		try{
			//For data form fields , i.e., formfields(non-empty array) comes from mobile app for forms 
			FormField autoGenSeqField = null;
			Form form = null;
			Map<Long,List<FormField>> formIdAndFormFieldsMap = (Map) Api.getGroupedMapFromList(fields, "formId", null);
			Long formId = null;
			List<FormField> formFields = null;
			Set<Long> dataFormIdsSet = formIdAndFormFieldsMap.keySet();
			List<Long> dataFormIdsList = new ArrayList<Long>();
			dataFormIdsList.addAll(dataFormIdsSet);
			
			//For empty form fields,i.e., no formfield(empty array) comes from mobile app for forms
			List<Long> allFormIds = new ArrayList<Long>(allformIdColletctions);
			List<Long> emptyDataFormIds = allFormIds;
			emptyDataFormIds.removeAll(dataFormIdsList);
			//Get unique values from emptyDataFormIds
			Set<Long> hs = new HashSet<Long>();
			hs.addAll(emptyDataFormIds);
			emptyDataFormIds.clear();
			emptyDataFormIds.addAll(hs);
			
			List<FormField> emptyFormFieldArray = new ArrayList<FormField>();
			for(Long emptyDataFormId : emptyDataFormIds){
				formIdAndFormFieldsMap.put(emptyDataFormId,emptyFormFieldArray);
			}
			
			//Haritha
			Map<Long,List<FormSectionField>> formIdAndFormSectionFieldsMap = (Map) Api.getGroupedMapFromList(sectionFields, "formId", null);
			List<FormSectionField> formSectionFields = null;
			if(formIdAndFormSectionFieldsMap != null) {
				Set<Long> dataSectionFormIdsSet = formIdAndFormSectionFieldsMap.keySet();
				List<Long> dataSectionFormIdsList = new ArrayList<Long>();
				dataSectionFormIdsList.addAll(dataSectionFormIdsSet);
				
				List<Long> emptySectionDataFormIds = allFormIds;
				emptySectionDataFormIds.removeAll(dataSectionFormIdsList);
				Set<Long> shs = new HashSet<Long>();
				shs.addAll(emptySectionDataFormIds);
				emptySectionDataFormIds.clear();
				emptySectionDataFormIds.addAll(shs);
				
				List<FormSectionField> emptyFormSectionFieldArray = new ArrayList<FormSectionField>();
				if(emptySectionDataFormIds != null && emptySectionDataFormIds.size() > 0) {
					for(Long emptySectionDataFormId : emptySectionDataFormIds){
						formIdAndFormSectionFieldsMap.put(emptySectionDataFormId,emptyFormSectionFieldArray);
					}
				}
			}
			
			for (Map.Entry<Long, List<FormField>> entry : formIdAndFormFieldsMap.entrySet()){
				
				formId = entry.getKey();
				formFields = entry.getValue();
				if(hardDeletedFormIdsInReq.contains(formId)) {
					continue;
				}
				if(isMasterForm){
					form = extraSupportAdditionalDao.getMasterForm(formId);
				}else{
					form = webManager.getForm(formId+"");
				}
				if(formIdAndFormSectionFieldsMap != null && form != null) {
					formSectionFields = formIdAndFormSectionFieldsMap.get(form.getFormId());
					autoGenSeqField = getAutoGenFormField(form, formFields, null,tzo,isMasterForm,formSectionFields);
				}else {
					autoGenSeqField = getAutoGenFormField(form, formFields, null,tzo,isMasterForm,null);
				}
				if(autoGenSeqField != null)
					autoGenSeqFields.add(autoGenSeqField);
			}
			return autoGenSeqFields;
		}catch(Exception e){
			e.printStackTrace();
			Log.info(getClass(), "Error in getAutoGenSeqFields ",e);
			return autoGenSeqFields;
		}
	}
	public FormField getAutoGenFormField(Form form,List<FormField> fields,FormSpec formSpec,
			String tzo,boolean isMasterForm, List<FormSectionField> sectionFields){
		
	   try{
		   
		if(fields == null || fields.isEmpty()) {
			if(isMasterForm){
				fields = extraSupportAdditionalDao.getMasterFormFieldsByFormId(form.getFormId());
			}else{
				fields = webManager.getFormFields(form.getFormId());
			}
		}
		
		if(formSpec == null) {
			if(form != null) {
				formSpec = webManager.getFormSpec(form.getFormSpecId()+"");
			}
		}
		
		FormFieldSpec autoGenFormFieldSpec = getAutoGeneratedFormFieldSpec(formSpec.getFormSpecId());
		if(autoGenFormFieldSpec == null){
			return null;
		}
		String exists = isAutoGeneratedFormFieldDataExists(form, fields,autoGenFormFieldSpec,isMasterForm);
		if(exists.contains("same")){
			return null;
		}
		
		FormField formField = getAutoGeneratedFormField(form, formSpec, autoGenFormFieldSpec,tzo,fields,sectionFields,exists);
		return formField;
	   }catch(Exception e){
		   StackTraceElement[] stackTrace = e.getStackTrace();
		   getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getAutoGenFormField method",e.toString(),stackTrace,0);
		   e.printStackTrace();
		   Log.info(getClass(), "Error in getAutoGenFormField ",e);
		   return null;
	   }
	}
	

	
	public String isAutoGeneratedFormFieldDataExists(Form form,List<FormField> fields,
			FormFieldSpec autoGenFormFieldSpec,boolean isMasterForm){
		
		try{
			
			if(fields == null){
				if(isMasterForm){
					fields = extraSupportAdditionalDao.getMasterFormFieldsByFormId(form.getFormId());
				}else{
					fields = webManager.getFormFields(form.getFormId());
				}
			}
			Map<String,List<FormField>> uniqueIdAndFormField = (Map) Api.getGroupedMapFromList(fields, "fieldSpecId",null);
			List<FormField> formFieldList = uniqueIdAndFormField.get(autoGenFormFieldSpec.getFieldSpecId());
			
			if(formFieldList != null && !formFieldList.isEmpty()){
				FormField formField = 	formFieldList.get(0);
				Log.info(getClass(), "Auto Generated FormField already exists for this form "+form.getFormId()+" empId = "+form.getModifiedBy()+" formField.getFieldValue() = "+formField.getFieldValue());
				
				List<AutoGenereteSequenceSpecConfiguarationField> autoGenereteSequenceSpecConfiguarationFields = getExtraSupportAdditionalSupportDao().
						getAutoGenereteSequenceSpecConfiguarationByFormSpecId(form.getUniqueId());
				if(autoGenereteSequenceSpecConfiguarationFields != null && autoGenereteSequenceSpecConfiguarationFields.size() > 0) {
					return "exists";
				}else {
					return "same";
				}
			}
		}catch(Exception e){
			StackTraceElement[] stackTrace = e.getStackTrace();
			getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in isAutoGeneratedFormFieldDataExists method",e.toString(),stackTrace,0);
			e.printStackTrace();
			return "same";
		}
		return "notexists";
	}
	
public FormFieldSpec getAutoGeneratedFormFieldSpec(long formSpecId){
		
		try{
			int autoGeneratedFormFieldType = Constants.FORM_FIELD_TYPE_AUTOGENERATED;
			List<FormFieldSpec> formFieldSpecs = extraSupportDao.getFormFieldSpecByFormSpecIdAndFieldType(formSpecId+"", autoGeneratedFormFieldType+"");
			if(formFieldSpecs == null || formFieldSpecs.isEmpty()){
				return null;
		    }
			FormFieldSpec autoGenereatedFormFieldSpec = formFieldSpecs.get(formFieldSpecs.size()-1);
			return autoGenereatedFormFieldSpec;
		}catch(Exception e){
			StackTraceElement[] stackTrace = e.getStackTrace();
			getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getAutoGeneratedFormFieldSpec method",e.toString(),stackTrace,0);
			e.printStackTrace();
			Log.info(getClass(), "Error in getAutoGeneratedFormFieldSpecExists ",e);
			return null;
		}
	}
	public List<Employee> getManagersAboveMe(String empId) {
		List<Employee> managers = new ArrayList<Employee>();
		Employee employee = getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId(empId);
		while (employee != null && employee.getManagerId() != null) {
			employee =getWebAdditionalSupportExtraManager().getEmployeeBasicDetailsByEmpId("" + employee.getManagerId());
			managers.add(employee);
		}
		return managers;
	}
	
	public List<Long> getEmpWorkReassignmentEmpGroups(String empId) {
		return extraSupportDao.getEmpWorkReassignmentEmpGroups(empId);
	}
	public void resolveWorkReassignmentRights(List<Employee> employees,
			WebUser webUser) {
		
	     //Long rootEmpId = 	webManager.getRootEmployeeId(webUser.getCompanyId());
	     Long rootEmpId = null;
		 if(webUser.getRootEmpId() != null) {
			 rootEmpId = webUser.getRootEmpId();
			 Log.info(getClass(), "resolveWorkReassignmentRights() // from webUser rootEmpId = "+rootEmpId);
		 }else {
			 rootEmpId = webManager.getRootEmployeeId(webUser.getCompanyId());
			 Log.info(getClass(), "resolveWorkReassignmentRights() // rootEmpId = "+rootEmpId);
		 }
		 if(rootEmpId.longValue() != webUser.getEmpId().longValue()){
			List<Long> empIds = Api.csvToLongList(Api.toCSVListOfEmployeeIds(employees));
			List<Long> seletedEmpGroups =  getEmpWorkReassignmentEmpGroups(Api.toCSV(empIds));
			
			if(seletedEmpGroups != null && seletedEmpGroups.size() > 0){
				String empGrpIds = Api.toCSV(seletedEmpGroups,CsvOptions.FILTER_NULL_OR_EMPTY);
				List<EmployeeGroup> groupsWithEmpIds = extraSupportDao.getEmployeesOfEmployeeGroups(empGrpIds);
				
				String empIdsInGroup = Api.toCSVFromList(groupsWithEmpIds, "empIds");
				List<Employee> employeesInGroups = webManager.getEmployeesIn(empIdsInGroup);
				
				Iterator<Employee> empItr = employeesInGroups.iterator();
				
				while(empItr.hasNext()){
					Employee employee = empItr.next();
					if(empIds.contains(employee.getEmpId()))
						empItr.remove();
				}
				employees.addAll(employeesInGroups);
			}
		 }	
	}
	
/*public FormField getAutoGeneratedFormField(Form form,FormSpec formSpec,FormFieldSpec formFieldSpec,String tzo, List<FormField> formFields, List<FormSectionField> sectionFields, String exists){
		
		FormField newFormField = null;
		try{
			//1. Checking autoGen config record exists in DB             ==========>
			List<AutoGenereteSequenceSpecConfiguaration> autoGenereteSequenceSpecConfiguarations = extraSupportDao.getAutoGenSeqConfig(formSpec.getFormSpecId()+"", formFieldSpec.getFieldSpecId()+"");
			if(autoGenereteSequenceSpecConfiguarations == null || autoGenereteSequenceSpecConfiguarations.isEmpty()){
				return null;
			}
			
			AutoGenereteSequenceSpecConfiguaration autoGenSeqSpecConfig = autoGenereteSequenceSpecConfiguarations.get(autoGenereteSequenceSpecConfiguarations.size()-1);
			
			//Haritha
			String value = null;
			String prefixFormFieldValue = null;
			String sufixFormFieldValue = null;
			if(formSpec == null) {
				if(form != null) {
					formSpec=extraDao.getFormSpec(form.getFormSpecId()+"");
				}
			}else if(formSpec.getUniqueId() == null){
				formSpec=extraDao.getFormSpec(form.getFormSpecId()+"");
			}
			String sufixFormFieldExp = null;
			String sufixFormFieldExpression= null;
			if(formSpec != null) {
				List<AutoGenereteSequenceSpecConfiguarationField> autoGenereteSequenceSpecConfiguarationFields = getExtraSupportAdditionalSupportDao().getAutoGenereteSequenceSpecConfiguarationByFormSpecId(formSpec.getUniqueId());
				if(autoGenereteSequenceSpecConfiguarationFields != null && autoGenereteSequenceSpecConfiguarationFields.size() > 0) {
					AutoGenereteSequenceSpecConfiguarationField autoGenereteSequenceSpecConfiguarationField = autoGenereteSequenceSpecConfiguarationFields.get(autoGenereteSequenceSpecConfiguarationFields.size()-1);
					String inputFormFieldExpression = null;
					String prefixFormFieldExpression = null;
					if(autoGenereteSequenceSpecConfiguarationField != null) {
						inputFormFieldExpression = autoGenereteSequenceSpecConfiguarationField.getInputFormFieldExpression();
						value = getWebFunctionalManager().getFieldAndSectionFieldSpecIdsForAutoGenExpression(formSpec.getFormSpecId(),inputFormFieldExpression,formFields,sectionFields);
						if(!autoGenSeqSpecConfig.isPrefixExpression()) {
							prefixFormFieldExpression = autoGenereteSequenceSpecConfiguarationField.getPrefixFormFieldExpression();
							if(prefixFormFieldExpression != null) {
								prefixFormFieldValue = getWebFunctionalManager().getFieldAndSectionFieldSpecIdsForAutoGenExpression(formSpec.getFormSpecId(),prefixFormFieldExpression,formFields,sectionFields);
							}
						}
						if(!autoGenSeqSpecConfig.isSufixExpression()) {
							sufixFormFieldExpression = autoGenereteSequenceSpecConfiguarationField.getSufixFormFieldExpression();
							if(sufixFormFieldExpression != null) {
								sufixFormFieldValue = getWebFunctionalManager().getFieldAndSectionFieldSpecIdsForAutoGenExpression(formSpec.getFormSpecId(),sufixFormFieldExpression,formFields,sectionFields);
							}
						}
						if(!autoGenSeqSpecConfig.isSufixExpression()) {
							sufixFormFieldExp = autoGenereteSequenceSpecConfiguarationField.getSufixFormFieldExpression();
						}else {
							sufixFormFieldExp = autoGenSeqSpecConfig.getCustomisedSufix();
						}
					}
				}
			}
			
			
			
			int size = autoGenSeqSpecConfig.getSequenceLength();
			Long maxValue = Api.maxValue(size);
			
			//2. Frame table name and try to get maxId from that             ==========>
			String tableName =  "`"+formSpec.getUniqueId()+"_Sequential"+"`";
			Long currentMaxId = extraSupportDao.selectMaxId(tableName);
			if(currentMaxId != null && currentMaxId >= maxValue ){
				Log.info(getClass(), "Inside generateReceiptId currentMaxId = "+currentMaxId+" for formId = "+form.getFormId()+" exceeds max limit.So not generating receiptId for this form");
				return null;
			}	
			
			//3. If get maxId query failed on table name then create table        ==========>
			if(currentMaxId == null){
				if(sufixFormFieldExp != null || (autoGenSeqSpecConfig.getSufix() != null && autoGenSeqSpecConfig.getSufix() != -1)) {
				String tableSql = "CREATE TABLE "+tableName+
						 		  "(id BIGINT(18)  NOT NULL AUTO_INCREMENT," +
						 		  " `timestamp` varchar(100) DEFAULT NULL," +
						 		  " `formId` BIGINT DEFAULT NULL," +
						 		  " PRIMARY KEY ( id )) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8";
				extraSupportDao.createTable(tableSql);
				}else {
					String tableSql = "CREATE TABLE "+tableName+
					 		  "(id BIGINT(18)  NOT NULL AUTO_INCREMENT," +
					 		  " `timestamp` varchar(100) DEFAULT NULL," +
					 		 " `formId` BIGINT DEFAULT NULL," +
					 		  " PRIMARY KEY ( id )) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8";
					extraSupportDao.createTable(tableSql);
				}
				
			}
			//4. insert next auto Gen           ==========>
			String autoGenSeqIdString = "";
			
			Integer separator = autoGenSeqSpecConfig.getSeperator();
			String separatorString = getSeperatorString(separator);
			
			long autoGenSeqId = 0;
			if(exists.equalsIgnoreCase("exists")) {
				for(FormField formField : formFields) {
					if(Constants.FORM_FIELD_TYPE_AUTOGENERATED == formField.getFieldType()) {
						if(!Api.isEmptyString(formField.getFieldValue())) {
							if(sufixFormFieldExp != null || (autoGenSeqSpecConfig.getSufix() != null && autoGenSeqSpecConfig.getSufix() != -1)) {
								autoGenSeqId = getExtendedDao().selectMaxIdWithFormId(tableName,form.getFormId());
								autoGenSeqIdString = autoGenSeqId + "";
								if (autoGenSeqSpecConfig.getInclude() == 1) {
									String format = "%0" + size + "d";
									autoGenSeqIdString = String.format(format, Long.parseLong(autoGenSeqId + "")); // autoGenSeqIdString
																													// =
									String.format("%06d", Long.parseLong(autoGenSeqId + ""));
								}
								}else {
							String array[] = (formField.getFieldValue()).split(separatorString);
							if(autoGenSeqSpecConfig.getInclude() == 1){
								if(!Api.isEmptyString(separatorString)) {
									if(array != null && array.length > 0) {
										autoGenSeqIdString = array[array.length-1];
									}
								}else {
									if(array != null && array.length > 0) {
										if(array.length-size > -1) {
											autoGenSeqIdString = formField.getFieldValue().substring(array.length-size);
										}else {
											autoGenSeqIdString = formField.getFieldValue();
										}
									}
								}
							}else {
								if(!Api.isEmptyString(separatorString)) {
									if(array != null && array.length > 0) {
										autoGenSeqIdString = array[array.length-1];
										autoGenSeqIdString = Api.removeZero(autoGenSeqIdString);
									}
								}else {
									if(array != null && array.length > 0) {
										if(array.length-size > -1) {
											autoGenSeqIdString = formField.getFieldValue().substring(array.length-size);
										}else {
											autoGenSeqIdString = formField.getFieldValue();
										}
										autoGenSeqIdString = Api.removeZero(autoGenSeqIdString);
									}
								}
							}
						  }
						}
						Log.info(getClass(), "getAutoGeneratedFormField() itsme = "+formField.getFieldValue());
						Log.info(getClass(), "getAutoGeneratedFormField() itsme2 = "+autoGenSeqIdString);
					}
				}
				Log.info(getClass(), "getAutoGeneratedFormField() exists = "+autoGenSeqSpecConfig.getSequenceLength());
			}else {
				if(sufixFormFieldExp != null || (autoGenSeqSpecConfig.getSufix() != null && autoGenSeqSpecConfig.getSufix() != -1)) {
				autoGenSeqId = getExtendedDao().insertSequentialWithFormId(tableName,form.getFormId());
				}else {
					autoGenSeqId = extraSupportDao.insertSequential(tableName);
				}
				//5. Prepare formFieldValue 'autoGenSeqIdString' with autoGenSeqId using prefix,java level zero fill ==========>
				//java level Left padding zero fill
				autoGenSeqIdString = autoGenSeqId+"";
				if(autoGenSeqSpecConfig.getInclude() == 1){
					String format = "%0"+size+"d";
					autoGenSeqIdString = String.format(format, Long.parseLong(autoGenSeqId+""));
					//autoGenSeqIdString = String.format("%06d", Long.parseLong(autoGenSeqId+""));
				}
				
			}
			
			
			
			
			  //Prefix & separator logic
			Integer prefix = autoGenSeqSpecConfig.getPrefix();
			Integer sufix = autoGenSeqSpecConfig.getSufix();
			String prefixString = "";
			String sufixString = "";
			String prefixValue = "";
			String sufixValue = "";
			String sufixYear = "";
			
			if(!autoGenSeqSpecConfig.isPrefixExpression() && prefixFormFieldValue != null) {
				prefixValue = prefixFormFieldValue;
			}else if(!Api.isEmptyString(autoGenSeqSpecConfig.getCustomisedPrefix())) {
				prefixValue = autoGenSeqSpecConfig.getCustomisedPrefix();
			}
			if(!Api.isEmptyString(prefixValue)) 
			    prefixString += prefixValue+
			            getSeperatorString(autoGenSeqSpecConfig.getSeperator() != null  && prefix != null && prefix != -1 ? autoGenSeqSpecConfig.getSeperator() : null);
			
			if(!autoGenSeqSpecConfig.isSufixExpression() && sufixFormFieldValue != null) {
				sufixValue = sufixFormFieldValue;
			}else if(!Api.isEmptyString(autoGenSeqSpecConfig.getCustomisedSufix())) {
				sufixValue = autoGenSeqSpecConfig.getCustomisedSufix();
			}
			
			prefixString += getAutoGenPrefixString(prefix,tzo);
			
			sufixString += getAutoGenPrefixString(sufix,tzo);
			
			if(!Api.isEmptyString(sufixValue)) 
				sufixString +=
			            getSeperatorString(autoGenSeqSpecConfig.getSeperator() != null  && sufix != null && sufix != -1 ? autoGenSeqSpecConfig.getSeperator() : null)+sufixValue;
			
			if(Api.isEmptyString(sufixYear)) {
				sufixYear = "";
			}else {
				sufixYear = separatorString+sufixValue;
			}
			
			if(Api.isEmptyString(sufixString)) {
				sufixValue = "";
			}else {
				sufixValue = separatorString+sufixString;
			}
			if(!Api.isEmptyString(value)) {
				autoGenSeqIdString = prefixString+separatorString+value+separatorString+autoGenSeqIdString+sufixYear+sufixValue;
			}else {
				autoGenSeqIdString = prefixString+separatorString+autoGenSeqIdString+sufixYear+sufixValue;
			}
			Log.info(getClass(), "autoGenSeqIdString  = "+autoGenSeqIdString);
			// 6. Framing form Field with formSpec,formFieldSpec   ==========>
			newFormField = new FormField();
			//values from formSpec
			newFormField.setFormSpecId(formSpec.getFormSpecId());
			//values from formFieldSpec
			newFormField.setFieldType(formFieldSpec.getFieldType());
			newFormField.setFieldSpecId(formFieldSpec.getFieldSpecId());
			newFormField.setUniqueId(formFieldSpec.getUniqueId());
			newFormField.setFieldLabel(formFieldSpec.getFieldLabel());
			newFormField.setDisplayOrder(formFieldSpec.getDisplayOrder());
			
			// values from formId and calculated values
			newFormField.setFormId(form.getFormId());
			newFormField.setFieldValue(autoGenSeqIdString);
			newFormField.setDisplayValue(autoGenSeqIdString);
			return newFormField;
		}catch(Exception e){
			StackTraceElement[] stackTrace = e.getStackTrace();
			getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getAutoGeneratedFormField method",e.toString(),stackTrace,0);
			e.printStackTrace();
			Log.info(getClass(), "Error in getAutoGeneratedFormField ",e);
			return newFormField;
		}
	}
	*/
	
	
	public long getFileSize(String mediaId){
	    long fileSize = 0l;
		try{
			Media media = mediaDao.getMedia(mediaId);
			File file = new File(constants.getMediaStoragePath()+File.separator+media.getLocalPath());
			if(file.exists()){
				fileSize = file.length();
			}
		}catch(Exception e){
			StackTraceElement[] stackTrace = e.getStackTrace();
			getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getFileSize method",e.toString(),stackTrace,0);
			e.printStackTrace();
			return fileSize;
		}
		return fileSize;
		
	}
	
	
	public void replaceWorkFieldValueForGivenSkeleton(FormAndField formAndField,
			long skeletonFieldSpecId,  String fieldValueNew) {
		Map<Long, FormField> fielSpecIdAndFieldMap = new HashMap<Long, FormField>();
		Map<Long, FormFieldSpec> skeletonFieldSpecIdAndFormFieldSpecMap = new HashMap<Long, FormFieldSpec>();

		List<FormField> formFields = formAndField.getFields();

		// FormSpec formSpec=getFormSpec(""+formAndField.getFormSpecId());
		List<FormFieldSpec> formFieldSpecs = webManager.getFormFieldSpecs(formAndField
				.getFormSpecId());

		fielSpecIdAndFieldMap = (Map) Api.getMapFromList(formFields,
				"fieldSpecId");
		skeletonFieldSpecIdAndFormFieldSpecMap = (Map) Api.getMapFromList(
				formFieldSpecs, "skeletonFormFieldSpecId");
		FormFieldSpec formFieldSpec = skeletonFieldSpecIdAndFormFieldSpecMap
				.get(skeletonFieldSpecId);

		FormField formFied = fielSpecIdAndFieldMap.get(formFieldSpec
				.getFieldSpecId());
		if (formFied != null) {
			formFied.setFieldValue(fieldValueNew);
		}

	}
	
	public Map<Long, List<FieldValidationCritiria>> getFieldValidationCritiriaMap(
			List<FieldValidationCritiria> fieldValidationCritiria, int type) {
		Map<Long, List<FieldValidationCritiria>> fieldValidationCritiriaMap = new HashMap<Long, List<FieldValidationCritiria>>();
		if (fieldValidationCritiria != null) {
			for (FieldValidationCritiria fieldValidationCritiria2 : fieldValidationCritiria) {
				if ((fieldValidationCritiria2.isSectionField() ? FieldSpecRestrictionGroup.FIELD_IS_SECTIONFIELD : FieldSpecRestrictionGroup.FIELD_IS_FORMFIELD)
						== type) {
					List<FieldValidationCritiria> fieldValidationCritirias = fieldValidationCritiriaMap
							.get(fieldValidationCritiria2.getFieldSpecId());
					if (fieldValidationCritirias == null) {
						fieldValidationCritirias = new ArrayList<FieldValidationCritiria>();
						fieldValidationCritiriaMap.put(
								fieldValidationCritiria2.getFieldSpecId(),
								fieldValidationCritirias);
					}
					fieldValidationCritirias.add(fieldValidationCritiria2);
				}
			}
		}
		return fieldValidationCritiriaMap;
	}
	
	public Workflow getWorkFlowMappedFormEntity(int companyId, String entityId,
			short entityType) {
		return extraSupportDao.getWorkFlowMappedFormEntity(companyId, entityId, entityType);
	}
}
