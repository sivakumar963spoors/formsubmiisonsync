package com.effort.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.effort.context.AppContext;
import com.effort.dao.ExtraDao;
import com.effort.entity.CustomEntitySpec;
import com.effort.entity.Employee;
import com.effort.entity.Entity;
import com.effort.entity.EntityField;
import com.effort.entity.EntityFieldSpec;
import com.effort.entity.Form;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormFieldWraper;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSectionSpec;
import com.effort.entity.WebUser;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.util.Api;
import com.effort.util.ContextUtils;
import com.effort.util.Log;
import com.fasterxml.jackson.core.type.TypeReference;

public class ComputeFieldsManager {
	@Autowired
	private Constants constants;
	@Autowired
	private ExtraDao extraDao;
	@Autowired
	private ConstantsExtra constantsExtra;
	
	private WebManager getWebManager(){
		WebManager webManager = AppContext.getApplicationContext().getBean("webManager",WebManager.class);
		return webManager;
	}
	
	private ContextUtils getContextUtils() {
		ContextUtils contextUtils = AppContext.getApplicationContext().getBean(
				"contextUtils", ContextUtils.class);
		return contextUtils;
	}
	
	private WebSupportManager getWebSupportManager(){
		WebSupportManager webSupportManager = AppContext.getApplicationContext().getBean("webSupportManager",WebSupportManager.class);
		return webSupportManager;
	}
	
	private WebAdditionalSupportManager getWebAdditionalSupportManager(){
		WebAdditionalSupportManager webAdditionalSupportManager = AppContext.getApplicationContext().getBean("webAdditionalSupportManager",WebAdditionalSupportManager.class);
		return webAdditionalSupportManager;
	}
	private WebExtensionManager getWebExtensionManager(){
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
		return webExtensionManager;
	}
	private WebExtraManager getWebExtraManager(){
		WebExtraManager webExtraManager = AppContext.getApplicationContext().getBean("webExtraManager",WebExtraManager.class);
		return webExtraManager;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void computeFields(Form form, List<FormField> fields,
			List<FormSectionField> sectionFields,
			Map<Long, FormFieldSpec> formFieldSpecMap,
			Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap,
			List<FormFieldSpec> computedFormFieldSepc,
			List<FormSectionFieldSpec> computedFormSectionFieldSpec,
			Map<Long, List<Entity>> entitiesMaps,
			List<FormField> allFieldsContainer,
			List<FormSectionField> allSectionFieldsContainer, List<String> accessibleEmployeeIds,WebUser webUser,
			List<Employee> allEmployees) {

		try {

			Map<String, Object> values = new HashMap<String, Object>();
			Map<Long, List<Integer>> formSectionFieldSpecIdAndInstanceIdMap = new HashMap<Long, List<Integer>>();
			List<Long> entitySpecsIds = getWebManager().getEntitySpecIdsForFormSpec(""
					+ form.getFormSpecId());
			Map<Long, EntityFieldSpec> entityFieldSpecMap = getWebManager().getEntityFieldSpecMap(Api
					.toCSV(entitySpecsIds));
			Map<Long, List<EntityField>> entityAndFieldsMap = new HashMap<Long, List<EntityField>>();
			
			
			List<FormFieldSpec> customerUserDefinedFields = getWebExtraManager().getCustomerUserDefinedFieldsForComputation(webUser);
			List<FormFieldSpec> employeeUserDefinedFields = getWebAdditionalSupportManager().getEmployeeUserDefinedFieldsForComputation(webUser);
			
			List<CustomEntitySpec> customEntitySpecs = getWebAdditionalSupportManager().getCustomEntitySpecs(webUser.getCompanyId());
			Map<Long,List<FormFieldSpec>> customEntitySpecIdAndFormFieldSpesMap = 
					getWebAdditionalSupportManager().getCustomEntitySpecIdAndFormFieldSpesMap(customEntitySpecs);
			
			List<Employee> allEligibleEmps = new ArrayList<Employee>(allEmployees);
			List<Long> entityIds = new ArrayList<Long>();
			if (fields != null) {
				for (FormField formField : fields) {
					FormFieldSpec formFieldSpec = formFieldSpecMap
							.get(formField.getFieldSpecId());
					String fieldSpecId = "F"+formFieldSpec.getFieldSpecId();
					
					allEligibleEmps = getWebAdditionalSupportManager().resolveEmployeesForComputedFields(allEligibleEmps, webUser,form.getFormSpecId()+"",fieldSpecId,"false");
					if (formFieldSpec.getFieldType() == 14) {
						entityIds
								.add(Long.parseLong(formField.getFieldValue()));

					}
				}
			}

			if (sectionFields != null) {
				for (FormSectionField formSectionField : sectionFields) {
					FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
							.get(formSectionField.getSectionFieldSpecId());
					String fieldSpecId = "SF"+formSectionFieldSpec.getSectionFieldSpecId();
					allEligibleEmps = getWebAdditionalSupportManager().resolveEmployeesForComputedFields(allEligibleEmps, webUser,form.getFormSpecId()+"",fieldSpecId,"true");
					if (formSectionFieldSpec.getFieldType() == 14) {
						entityIds.add(Long.parseLong(formSectionField
								.getFieldValue()));
					}
				}
			}
			List<EntityField> entityFields = extraDao
					.getEntityFieldByEntityIn(Api.toCSV(entityIds));
			getWebManager().populateEntityAndFieldsMap(entityAndFieldsMap, entityFields);

			if (fields != null) {

				for (FormField formField : fields) {
					FormFieldSpec formFieldSpec = formFieldSpecMap
							.get(formField.getFieldSpecId());
					if(Api.isAutoComputedEnabledDataType(formFieldSpec.getFieldType())){
						
						String id = formFieldSpec.getExpression();
						String value = formField.getFieldValue();
		
						if(!Api.isNumber(value) && value != null) {
							if(formFieldSpec.getFieldType() == 16  && formFieldSpec.getCurrencyFormat() != null && Integer.parseInt(formFieldSpec.getCurrencyFormat()) != 0 && formFieldSpec.getEnableCommaSeperator() != null && formFieldSpec.getEnableCommaSeperator() == 1) {
								value = value.replaceAll("[^.0-9%]", "");
							}
						}
						
						if (formFieldSpec.getFieldType() == 11) {
							value = getWebExtraManager().getValueOfTimeField(value);
						}
						else if (formFieldSpec.getFieldType() == 14) {
							getWebManager().populateValuesWithEntityValues(values,
									entityAndFieldsMap, entityFieldSpecMap,
									id, value, null, accessibleEmployeeIds, webUser);
							continue;
						}
						else if (formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE) {
							getWebExtraManager().populateEmployeeFields(values,id, value, null, accessibleEmployeeIds, webUser,employeeUserDefinedFields,allEmployees);
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
				
				for (FormSectionField formSectionField : sectionFields) {

					List<Integer> instanceIdList = formSectionFieldSpecIdAndInstanceIdMap
							.get(formSectionField.getSectionSpecId());
					if (instanceIdList == null) {
						instanceIdList = new ArrayList<Integer>();
						formSectionFieldSpecIdAndInstanceIdMap.put(
								formSectionField.getSectionSpecId(),
								instanceIdList);
					}

					if (!instanceIdList.contains(formSectionField
							.getInstanceId())) {
						instanceIdList.add(formSectionField.getInstanceId());
					}

					FormSectionFieldSpec formSectionFieldSpec = formSectionFieldSpecMap
							.get(formSectionField.getSectionFieldSpecId());
					 if(Api.isAutoComputedEnabledDataType(formSectionFieldSpec.getFieldType())){
							
							String id = formSectionFieldSpec.getExpression();
							String value = formSectionField.getFieldValue();
			
							if(!Api.isNumber(value) && value != null) {
								if(formSectionFieldSpec.getFieldType() == 16  && formSectionFieldSpec.getCurrencyFormat() != null && Integer.parseInt(formSectionFieldSpec.getCurrencyFormat()) != 0 && formSectionFieldSpec.getEnableCommaSeperator() != null && formSectionFieldSpec.getEnableCommaSeperator() == 1) {
									value = value.replaceAll("[^.0-9%]", "");
								}
							}
							
							if (formSectionFieldSpec.getFieldType() == 11) {
								value = getWebExtraManager().getValueOfTimeField(value);
							}
							
							else if (formSectionFieldSpec.getFieldType() == 14) {
								getWebManager().populateValuesWithEntityValues(values,
										entityAndFieldsMap, entityFieldSpecMap,
										id, value,
										formSectionField.getInstanceId(), accessibleEmployeeIds, webUser);
								continue;
							}
							else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_EMPLOYEE) {
								getWebExtraManager().populateEmployeeFields(values,id, value, formSectionField.getInstanceId(),
										accessibleEmployeeIds, webUser, employeeUserDefinedFields,allEligibleEmps);
								continue;
							}
							else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER) {
								getWebExtraManager().populateCustomerFields(values,id, value, formSectionField.getInstanceId()+"", accessibleEmployeeIds, webUser, customerUserDefinedFields);
								continue;
							}
							else if (formSectionFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOM_ENTITY) {
								getWebAdditionalSupportManager().populateCustomEntityFields(values,id, value, formSectionField.getInstanceId()+"", accessibleEmployeeIds, webUser, customEntitySpecIdAndFormFieldSpesMap);
								continue;
							}
			
							id = id + "[" + formSectionField.getInstanceId()
									+ "]";
							if(Api.isAutoComputedEnabledNumericDataType(formSectionFieldSpec.getFieldType())){
								 
								getWebManager().populateValueInMap(id, value,values);
							  }else{
								  getWebManager().populateValueInObjectMap(id, value,values);
							  }
						}
					}
				}

			if (computedFormFieldSepc != null) {
				Map<Long, FormField> fieldSpecIdAndFieldMap = new HashMap<Long, FormField>();
				if (fields != null) {
					fieldSpecIdAndFieldMap = (Map) Api.getMapFromList(fields,
							"fieldSpecId");
				}
				for (FormFieldSpec formFieldSpec : computedFormFieldSepc) {

					FormField formField = fieldSpecIdAndFieldMap
							.get(formFieldSpec.getFieldSpecId());
					if (formField == null) {
						formField = new FormField();
						formField.setFormId(form.getFormId());
						formField
								.setFieldSpecId(formFieldSpec.getFieldSpecId());
						formField.setClientFormId(form.getClientFormId());
						formField.setFormSpecId(formFieldSpec.getFormSpecId());
						allFieldsContainer.add(formField);
					}
					String fieldValue = null;
					Double value = null;
					String formula = formFieldSpec.getFormula();
					int instanceId = 1;
					int fieldType = formFieldSpec.getFieldType();
					String expression =  formFieldSpec.getExpression();
					
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
					
					Object valueObj = getWebExtraManager().
							getValueForFormula(formula, instanceId, values,fieldType,FormFieldWraper.TYPE_FIELD);
	
					if(Api.isNumber(valueObj)){
						
					   if(Api.isAutoComputedEnabledNumericDataType(formFieldSpec.getFieldType())){ 	
					    	value =  Double.parseDouble(valueObj.toString());
					    	if(formFieldSpec.getDecimalValueLimit() != null && formFieldSpec.getDecimalValueLimit().intValue() > 0){
					    		fieldValue = Api.roundToString(value.doubleValue(), formFieldSpec.getDecimalValueLimit());
							}else{
								fieldValue = Api.roundToString(value.doubleValue(), 2);
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
						   fieldValue = getWebExtensionManager().secondsToDurationForDurationDataType(valueObj.toString()+"");
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

						Log.info(getClass(), "computeFields() // expression = "+expression+" fieldValue = "+fieldValue);
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
									formField.setFieldValue(Api.roundToString(value.doubleValue(), formFieldSpec.getDecimalValueLimit()));
								}else{
									formField.setFieldValue(String.format("%.2f",(value.doubleValue())));
								}
								
							
						} else{
							if(formFieldSpec.getDecimalValueLimit() != null && formFieldSpec.getDecimalValueLimit().intValue() > 0){
								formField.setFieldValue(Api.roundToString(value.doubleValue(), formFieldSpec.getDecimalValueLimit()));
							}else{
								formField.setFieldValue(Api.roundToString(
										value.doubleValue(), 2));
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

			if (computedFormSectionFieldSpec != null) {
				Map<String, FormSectionField> sectionFieldSpecIdAndSectionFieldMap = new HashMap<String, FormSectionField>();
				if (sectionFields != null) {

					for (FormSectionField sectionField : sectionFields) {
						String key = sectionField.getSectionFieldSpecId() + "_"
								+ sectionField.getInstanceId();
						sectionFieldSpecIdAndSectionFieldMap.put(key,
								sectionField);
					}
				}

				for (FormSectionFieldSpec formSectionFieldSpec : computedFormSectionFieldSpec) {

					List<Integer> instanceIdList = formSectionFieldSpecIdAndInstanceIdMap
							.get(formSectionFieldSpec.getSectionSpecId());
					if (instanceIdList != null) {
						for (Integer instaceId : instanceIdList) {
							String key = formSectionFieldSpec
									.getSectionFieldSpecId() + "_" + instaceId;

							FormSectionField formSectionField = sectionFieldSpecIdAndSectionFieldMap
									.get(key);
							if (formSectionField == null) {
								formSectionField = new FormSectionField();
								formSectionField.setFormId(form.getFormId());
								formSectionField
										.setSectionFieldSpecId(formSectionFieldSpec
												.getSectionFieldSpecId());
								formSectionField
										.setFormSpecId(formSectionFieldSpec
												.getFormSpecId());
								formSectionField.setClientFormId(form
										.getClientFormId());
								formSectionField
										.setSectionSpecId(formSectionFieldSpec
												.getSectionSpecId());
								formSectionField.setInstanceId(instaceId);
								allSectionFieldsContainer.add(formSectionField);
							}
							
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
							
							Object valueObj = getWebExtraManager().
									getValueForFormula(formula, instanceId, values,fieldType,FormFieldWraper.TYPE_SECTION_FIELD);
							
							if(Api.isNumber(valueObj)){
								
								   if(Api.isAutoComputedEnabledNumericDataType(formSectionFieldSpec.getFieldType())){ 	
								    	value =  Double.parseDouble(valueObj.toString());
								    	if(formSectionFieldSpec.getDecimalValueLimit() != null && formSectionFieldSpec.getDecimalValueLimit().intValue() > 0){
								    		fieldValue =  Api.roundToString(value.doubleValue(), formSectionFieldSpec.getDecimalValueLimit());
										}else{
											fieldValue = Api.roundToString(value.doubleValue(), 2);
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
                                      fieldValue = getWebExtensionManager().secondsToDurationForDurationDataType(valueObj.toString()+"");
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
											formSectionField.setFieldValue(Api.roundToString(value.doubleValue(), formSectionFieldSpec.getDecimalValueLimit()));
										}else{
											formSectionField.setFieldValue(String.format("%.2f",(value.doubleValue())));
										}
										
								} else{
									if(formSectionFieldSpec.getDecimalValueLimit() != null && formSectionFieldSpec.getDecimalValueLimit().intValue() > 0){
										formSectionField.setFieldValue(Api.roundToString(value.doubleValue(), formSectionFieldSpec.getDecimalValueLimit()));
									}else{
										formSectionField.setFieldValue(Api.roundToString(
												value.doubleValue(), 2));
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

			// if (fields != null) {
			// for (FormField formField : fields) {
			// FormFieldSpec formFieldSpec =
			// formFieldSpecMap.get(formField.getFieldSpecId());
			// if (formFieldSpec.isComputedField()) {
			// Double value = evaluateFormula(formFieldSpec.getFormula(), 0,
			// values);
			// if (value != null) {
			// formField.setFieldValue(value.doubleValue() + "");
			// }
			// }
			// }
			// }
			//
			// if (sectionFields != null) {
			// for (FormSectionField formSectionField : sectionFields) {
			// FormSectionFieldSpec formSectionFieldSpec =
			// formSectionFieldSpecMap.get(formSectionField.getSectionFieldSpecId());
			// if (formSectionFieldSpec.isComputedField()) {
			// Double value =
			// evaluateFormula(formSectionFieldSpec.getFormula(),formSectionField.getInstanceId(),
			// values);
			// if (value != null) {
			// formSectionField.setFieldValue(value.doubleValue()+ "");
			// }
			// }
			// }
			// }
		} catch (NumberFormatException e) {
			Log.info(this.getClass(), e.toString(), e);
		}
	}
	
	
	
}