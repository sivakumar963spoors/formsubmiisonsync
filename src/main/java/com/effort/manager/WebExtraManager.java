package com.effort.manager;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.core.appender.routing.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.effort.context.AppContext;
import com.effort.dao.ActivityLocationDao;
import com.effort.dao.ConfiguratorDao;
import com.effort.dao.EmployeeDao;
import com.effort.dao.ExtendedDao;
import com.effort.dao.ExtraDao;
import com.effort.dao.ExtraSupportAdditionalDao;
import com.effort.dao.ExtraSupportAdditionalSupportDao;
import com.effort.dao.ExtraSupportDao;
import com.effort.dao.SettingsDao;
import com.effort.dao.SyncDao;
import com.effort.dao.TrackDao;
import com.effort.dao.WorkFlowExtraDao;
import com.effort.entity.ActivityStream;
import com.effort.entity.Company;
import com.effort.entity.CompanyLabel;
import com.effort.entity.Customer;
import com.effort.entity.CustomerField;
import com.effort.entity.CustomerStaticField;
import com.effort.entity.EffortAccessSettings;
import com.effort.entity.Employee;
import com.effort.entity.EmployeeAccessSettings;
import com.effort.entity.EmployeeEntityMap;
import com.effort.entity.EmployeeStaticField;
import com.effort.entity.Entity;
import com.effort.entity.EntityFieldSpec;
import com.effort.entity.Form;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.JmsMessage;
import com.effort.entity.JobCreateOrModifiNotify;
import com.effort.entity.PermissionSet;
import com.effort.entity.WebUser;
import com.effort.entity.WorkFlowFormStatus;
import com.effort.entity.WorkFlowFormStatusHistory;
import com.effort.entity.WorkSpec;
import com.effort.entity.Workflow;
import com.effort.entity.WorkflowStage;
import com.effort.exception.EffortError;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.settings.ReportConstants;
import com.effort.util.Api;
import com.effort.util.ContextUtils;
import com.effort.util.Log;
import com.effort.validators.FormValidator;
@Service
public class WebExtraManager {

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
	
	private WebExtensionManager getWebExtensionManager(){
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
		return webExtensionManager;
	}
	
	
	public List<FormFieldSpec> getCustomerUserDefinedFieldsForComputation(WebUser webUser) 
	{
		try
		{
			FormSpec customerFormSpec = getCustomerFormSpec(webUser);
			if(customerFormSpec != null)
			{
				return webManager.getFormFieldSpecs(customerFormSpec.getFormSpecId());
			}
		}
		catch(Exception e)
		{
			StackTraceElement[] stackTrace = e.getStackTrace();
			getWebExtensionManager().sendExceptionDetailsMail("Exception Occured in getCustomerUserDefinedFieldsForComputation method",e.toString(),stackTrace,webUser.getCompanyId());
		}
		
		return new ArrayList<FormFieldSpec>();
	}
	public FormSpec getCustomerFormSpec(WebUser webUser)
	{
		Long formSpecIdForCustomerForm = webUser.getServiceCallConstants()
				.getGlobal_company_customer_form_spec_id();
		
		if(formSpecIdForCustomerForm == null) {
			Long skeletonSpecId = constants.getGlobal_company_customer_form_spec_id();
			formSpecIdForCustomerForm = extraDao.getFormSpecIdFromSkeleton(skeletonSpecId,webUser.getCompanyId());
		}
		FormSpec formSpec = webManager
				.getFormSpec(formSpecIdForCustomerForm + "");
		if(formSpec!=null)
		{
			return webManager.getLatestFormSpec(formSpec.getUniqueId());
		}
		return null;
	}
	
	 public Object getValueForFormula(String formula,int instanceId,
			   Map<String, Object> values,int fieldType,int type)
	   {
		   Object valueObj = null;
		   try{
			   
				if (formula.contains("[")) {
					String fieldExpression = formula;
					fieldExpression = fieldExpression.replace("+", ":").replace("-", ":").replace("*", ":")
							.replace("/", ":").replace("SUM", "").replace("(", "").replace(")", "");
					String[] expressionValues = fieldExpression.split(":");
					Map<String, Object> valuesTemp = new HashMap<String, Object>();
					valuesTemp.put("i", instanceId);
					for (String expression : expressionValues) {
						String formulatemp = expression;
						String arthExp = expression.substring(expression.indexOf("[") + 1, expression.indexOf("]"));

						expression = arthExp.replace("#", "+");

						Object objValue = webManager.evaluateFormula(expression, 1, valuesTemp);
						if (objValue.getClass() == Integer.class) {
							String processedFormula = formulatemp.replace(arthExp, objValue + "");
							formula = formula.replace(formulatemp, processedFormula);
						} else {
							Double resD = (Double) objValue;

							Long resu = Math.round(resD);
							String processedFormula = formulatemp.replace(arthExp, resu + "");
							formula = formula.replace(formulatemp, processedFormula);
						}

					}
				}
			   
				String computedValue = "";
				 String separator = " ";
				 
				 /*if(fieldType == Constants.FORM_FIELD_TYPE_TEXT){
					 
					 //To get section fields out side of section
					 if(formula.indexOf("S") > -1){
						 if (type == FormFieldWraper.TYPE_FIELD) {
							 instanceId = 1;
						 }
					 }
				 }*/
				 //String separator support
				if(Api.isStringOperationFormula(formula)){
					
					List<String> operatorsList = Api.getOperatorsList(formula);
					formula = formula.replaceAll("<comma>", ",");
		        	formula = formula.replaceAll("<space>", ",");
					Log.info(getClass(), "Inside isStringOperationFormula formula = "+formula);
					List<String> operands = Api.csvToList(formula);
					 
					 int operandCount = 0;
					for (String operand : operands) {
								
					        	formula = operand;
					        	valueObj = webManager.evaluateFormula(formula, instanceId, values);
					        	separator = Api.getSeparator(operatorsList, operandCount);
					        	if(!Api.isEmptyObj(valueObj))
					        		computedValue = computedValue + separator + valueObj;
					        	operandCount++;
					        	
					 }
					valueObj = computedValue.trim();
				}else{
					//Non-string separators,i.e, operators like +,- etc 
					/*if(formula.equalsIgnoreCase("S1F0[1]"))
						formula = "S1F0";*/
					//values.put("S1F0","10.0");
					valueObj = webManager.evaluateFormula(formula, instanceId, values);
					
					if(valueObj == null){
						if(Api.isAutoComputedEnabledNumericDataType(fieldType)){
							valueObj = "0.0";
						}else{
							valueObj = "";
						}
					}
				}
		   }//try
		   catch(Exception e){
			   e.printStackTrace();
			   Log.info(getClass(), "Error in getValueForFormula "+e.getMessage());
		   }
		   return valueObj;
	   }
	 
	 public String getValueOfTimeField(String value) 
		{
			if(value != null && !Api.isEmptyString(value) && !"0".equalsIgnoreCase(value) && value.contains(":") && value.length() == 5)
			{
				value =Api.timeToSeconds(value)+"";
			
			}
			return value;
		}
	 
	 public List<EmployeeStaticField> getEmployeeStaticFieldsForComputation() 
		{
			List<EmployeeStaticField> employeeStaticFields = new ArrayList<EmployeeStaticField>();
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_FIRST_NAME_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_LAST_NAME_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_ID_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_REPORTING_MANAGER_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_EMAIL_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_MOBILE_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_EFFORT_ID_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_DESIGNATION_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_ADDRESS_STREET_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_ADDRESS_AREA_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_ADDRESS_CITY_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_ADDRESS_DISTRICT_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_ADDREESS_PINCODE_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_ADDRESS_LANDMARK_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_ADDRESS_COUNTRY_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_ADDRESS_STATE_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_HOME_LOCATION_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_WORK_LOCATION_PROP));
			employeeStaticFields.add(new EmployeeStaticField(EmployeeStaticField.EMPLOYEE_JOINING_DATE));
			return employeeStaticFields;
		}
	 
	 public void populateEmployeeFields(Map<String, Object> values,
				String id, String value, Integer instanceId,
				List<String> accessibleEmployeeIds, WebUser webUser,
				List<FormFieldSpec> employeeUserDefinedFields, List<Employee> allEmployees) 
		{

			values.put(id, value);
			
			Employee employee = null;
			if(!Api.isEmptyString(value))
			{
				employee = employeeDao.getEmployeeBasicDetailsByEmpId(value);
			}
			
			if(employee != null)
			{
				List<EmployeeStaticField> employeeStaticFields = getEmployeeStaticFieldsForComputation();
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
					}else if(employeeStaticField.getExpression().equalsIgnoreCase(EmployeeStaticField.EMPLOYEE_JOINING_DATE_EXPR))
					{
						if(!Api.isEmptyString(employee.getEmpJoiningDate()))
						{
							staticValue = employee.getEmpJoiningDate();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
					}
					else if(employeeStaticField.getExpression().equals(EmployeeStaticField.EMPLOYEE_FORM_FILLED_EXPR))
					{
					  staticValue = employee.getEmployeeCreatedBy()+""; 
					  if(!Api.isEmptyString(staticValue)) {
						  addData = true; 
					  }
					 
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
						Employee webUserEmp = employeeDao.getEmployeeBasicDetailsAndTimeZoneByEmpId(webUser.getEmpId()+"");
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
									if(!isAccessibleEntity(formFieldSpec.getFieldTypeExtra(), formField.getFieldValue(), webUser))
									{
										addData = false;
									}
								}
								else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER &&
										formField.getFieldValue() != null && Api.isNumber(formField.getFieldValue()))
								{
									if(!isAccessibleCustomer(formField.getFieldValue(), webUser))
									{
										addData = false;
									}
								}
								else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_LIST &&
										!Api.isEmptyString(formField.getFieldValue()))
								{
									String entityIds = getAccessibleEntititesFromEntityIds(formFieldSpec.getFieldTypeExtra(), formField.getFieldValue(), webUser);
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
				}
				
				
			}
		
			
		}

	 public String getAccessibleEntititesFromEntityIds(String entitySpecId,
				String entityIds, WebUser webUser) 
		{
			List<EmployeeEntityMap> employeeEntityMaps=webManager.getMappedEntityIds(webUser.getEmpId(), entitySpecId);
		    if(employeeEntityMaps!=null && !employeeEntityMaps.isEmpty())
		    {
		    	List<String> mappedEntityIds=Api.listToList(employeeEntityMaps,"entityId");
		    	List<String> entityIdsList = Api.csvToList(entityIds);
		    	List<String> accessibleEntiyIds = new ArrayList<String>();
		    	for(String entityId : entityIdsList)
		    	{
		    		if(mappedEntityIds.contains(entityId))
			    	{
		    			accessibleEntiyIds.add(entityId);
			    	}
		    	}
		    	
		    	return Api.toCSV(accessibleEntiyIds);
		    	
		    }
		    else
		    {
		    	return entityIds;
		    }
		}
	 public void populateCustomerFields(Map<String, Object> values,
				String id, String value, String instanceId,
				List<String> accessibleEmployeeIds, WebUser webUser, List<FormFieldSpec> customerUserDefinedFields) 
		{

			values.put(id, value);
			
			Customer customer = null;
			try
			{
				if(!Api.isEmptyString(value))
					customer = webManager.getCustomer(value);
			}
			catch(Exception e)
			{
				
			}
			if(customer != null)
			{
				List<CustomerStaticField> customerStaticFields = getCustomerStaticFieldsForComputation(webUser);
				boolean addData;
				for(CustomerStaticField customerStaticField : customerStaticFields)
				{
					String staticValue = "";
					addData = false;
					if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_NAME_EXPR))
					{
						staticValue = customer.getCustomerName();
						if(!Api.isEmptyString(staticValue))
							addData = true;
						
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_ID_EXPR))
					{
						staticValue = customer.getCustomerNo();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_TYPE_EXPR))
					{
						staticValue = customer.getCustomerTypeId()+"";
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_PHONE_EXPR))
					{
						staticValue = customer.getCustomerPhone();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_LOCATION_EXPR))
					{
						if(!Api.isEmptyString(customer.getCustomerLat())
								&& !Api.isEmptyString(customer.getCustomerLong()))
						{
							staticValue = customer.getCustomerLat()+","+customer.getCustomerLong();
							if(!Api.isEmptyString(staticValue))
								addData = true;
						}
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_PARENT_EXPR))
					{
						staticValue = customer.getParentId()+"";
						if(!Api.isEmptyString(staticValue))
						{
							if(isAccessibleCustomer(staticValue, webUser))
							{
								addData = true;
							}
						}
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_ADDRESS_STREET_EXPR))
					{
						staticValue = customer.getCustomerAddressStreet();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_ADDRESS_LANDMARK_EXPR))
					{
						staticValue = customer.getCustomerAddressLandMark();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_ADDRESS_AREA_EXPR))
					{
						staticValue = customer.getCustomerAddressArea();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_ADDRESS_CITY_EXPR))
					{
						staticValue = customer.getCustomerAddressCity();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_ADDRESS_DISTRICT_EXPR))
					{
						staticValue = customer.getCustomerAddressDistrict();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_ADDREESS_PINCODE_EXPR))
					{
						staticValue = customer.getCustomerAddressPincode();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_ADDRESS_STATE_EXPR))
					{
						staticValue = customer.getCustomerAddressState();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_ADDRESS_COUNTRY_EXPR))
					{
						staticValue = customer.getCustomerAddressCountry();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_PRIMARY_CONTACT_FIRST_NAME_EXPR))
					{
						staticValue = customer.getPrimaryContactFirstName();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_PRIMARY_CONTACT_LAST_NAME_EXPR))
					{
						staticValue = customer.getPrimaryContactLastName();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_PRIMARY_CONTACT_TITLE_EXPR))
					{
						staticValue = customer.getPrimaryContactTitle();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_PRIMARY_CONTACT_PHONE_EXPR))
					{
						staticValue = customer.getPrimaryContactPhone();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_PRIMARY_CONTACT_EMAIL_EXPR))
					{
						staticValue = customer.getPrimaryContactEmail();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_SECONDARY_CONTACT_FIRST_NAME_EXPR))
					{
						staticValue = customer.getSecondaryContactFirstName();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_SECONDARY_CONTACT_LAST_NAME_EXPR))
					{
						staticValue = customer.getSecondaryContactLastName();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_SECONDARY_CONTACT_TITLE_EXPR))
					{
						staticValue = customer.getSecondaryContactTitle();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_SECONDARY_CONTACT_PHONE_EXPR))
					{
						staticValue = customer.getSecondaryContactPhone();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_SECONDARY_CONTACT_EMAIL_EXPR))
					{
						staticValue = customer.getSecondaryContactEmail();
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}else if(customerStaticField.getExpression().equalsIgnoreCase(CustomerStaticField.CUSTOMER_TERRITORY_EXPR))
					{
						staticValue = customer.getCustomerTerritoryId()+"";
						if(!Api.isEmptyString(staticValue))
							addData = true;
					}
					
					if (addData) 
					{
						String key = id + "_"
								+ customerStaticField.getExpression();
						String id1 = id;
						if (instanceId != null) 
						{
							key = key + "[" + instanceId + "]";
							id1 = id1 + "[" + instanceId + "]";
						}

						if (Api.isAutoComputedEnabledNumericDataType(customerStaticField
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
				
				if(customerUserDefinedFields != null && customerUserDefinedFields.size() > 0 &&
						customer.getCustomerFormId() != null &&customer.getCustomerFormId() != 0 && customer.getCustomerFormId() != -1)
				{
					//List<FormField> formFields = webManager.getFormFields(customer.getCustomerFormId());
					List<FormField> formFields = getWebAdditionalSupportExtraManager().getMasterFormFields(customer.getCustomerFormId());
					if(formFields != null && formFields.size() > 0)
					{
						Employee webUserEmp = employeeDao.getEmployeeBasicDetailsAndTimeZoneByEmpId(webUser.getEmpId()+"");
						Map<String, FormFieldSpec> uniqueIdAndFormFieldSpecMap = (Map)Api.getMapFromList(customerUserDefinedFields, "uniqueId");
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
									if(!isAccessibleEntity(formFieldSpec.getFieldTypeExtra(), formField.getFieldValue(), webUser))
									{
										addData = false;
									}
								}
								else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_CUSTOMER &&
										formField.getFieldValue() != null && Api.isNumber(formField.getFieldValue()))
								{
									if(!isAccessibleCustomer(formField.getFieldValue(), webUser))
									{
										addData = false;
									}
								}
								else if(formFieldSpec.getFieldType() == Constants.FORM_FIELD_TYPE_MULTIPICK_LIST &&
										!Api.isEmptyString(formField.getFieldValue()))
								{
									String entityIds = getAccessibleEntititesFromEntityIds(formFieldSpec.getFieldTypeExtra(), formField.getFieldValue(), webUser);
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
				}
				
			}
			
		}
		
	 public boolean isAccessibleCustomer(String customerId, WebUser webUser) 
		{
			List<Long> customerIds = new ArrayList<Long>();
			
			customerIds = webManager.getCustomerIdsForEmpWithCustomerFilter1(
					webUser.getEmpId(),
					Constants.FORM_CUSTOMER_FILTER_TYPE_CUSTOMERS_IN_MY_HIERARCHY,
					webUser.getCompanyId(),customerIds);
			boolean allCustomers=false;
			if ("102".equalsIgnoreCase(constants.getMapTo(webUser.getEmpId()))) 
			{
				return true;
			}else if (customerIds.size() > 0) {
				if(customerIds.contains(Long.parseLong(customerId)))
				{
					return true;
				}
			} 
			return false;
		}
	 
	 public WorkSpec getWorkSpecsForFromSpecUniqueIdAndCompany(String uniqueId,
				Integer companyId) {
			
			return extraDao.getWorkSpecsForFromSpecUniqueIdAndCompany(uniqueId,
					companyId);
		}
	 
	 public boolean isAccessibleEntity(String entitySpecId,
				String entityId, WebUser webUser) 
		{
			List<EmployeeEntityMap> employeeEntityMaps=webManager.getMappedEntityIds(webUser.getEmpId(), entitySpecId);
		    if(employeeEntityMaps!=null && !employeeEntityMaps.isEmpty())
		    {
		    	List<String> entityIds=Api.listToList(employeeEntityMaps,"entityId");
		    	if(entityIds.contains(entityId))
		    	{
		    		return true;
		    	}
		    }
		    else
		    {
		    	return true;
		    }
		    return false;
		}
		
	 public List<CustomerStaticField> getCustomerStaticFieldsForComputation(WebUser webUser) 
		{
			List<CustomerStaticField> customerStaticFields = new ArrayList<CustomerStaticField>();
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_NAME_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_ID_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_TYPE_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_PHONE_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_LOCATION_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_PARENT_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_ADDRESS_STREET_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_ADDRESS_LANDMARK_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_ADDRESS_AREA_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_ADDRESS_CITY_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_ADDRESS_DISTRICT_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_ADDREESS_PINCODE_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_ADDRESS_STATE_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_ADDRESS_COUNTRY_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_PRIMARY_CONTACT_FIRST_NAME_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_PRIMARY_CONTACT_LAST_NAME_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_PRIMARY_CONTACT_TITLE_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_PRIMARY_CONTACT_PHONE_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_PRIMARY_CONTACT_EMAIL_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_SECONDARY_CONTACT_FIRST_NAME_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_SECONDARY_CONTACT_LAST_NAME_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_SECONDARY_CONTACT_TITLE_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_SECONDARY_CONTACT_PHONE_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_SECONDARY_CONTACT_EMAIL_PROP));
			customerStaticFields.add(new CustomerStaticField(CustomerStaticField.CUSTOMER_TERRITORY_PROP));
			
			List<CustomerField> customerFields=new ArrayList<CustomerField>();
			Map<Integer,Integer> customerStaticIdAndVisibilityMap = new HashMap<Integer, Integer>();
			
			customerFields= getCustomerFieldsForCompany(webUser.getCompanyId());

			if(customerFields == null || customerFields.isEmpty())
					customerFields= getCustomerFields();
			
			customerStaticIdAndVisibilityMap = (Map) Api.getMapFromList(customerFields, "customerFieldId", "visibilityCheck");
			
			customerStaticIdAndVisibilityMap.put(25, 1); // For Parent Visibility in static customer fields.
			
			Iterator<CustomerStaticField> customerStaticFieldIterator = customerStaticFields
					.iterator();
			while (customerStaticFieldIterator.hasNext())
			{
				CustomerStaticField customerStaticField = (CustomerStaticField) customerStaticFieldIterator
						.next();
				
					Integer visibilityCheck = customerStaticIdAndVisibilityMap.get(customerStaticField.getStaticId());
					if(visibilityCheck == null || visibilityCheck != 1)
					{
						customerStaticFieldIterator.remove();
					}
			}

			return customerStaticFields;
		}
	 public List<CustomerField> getCustomerFieldsForCompany(Integer companyId) {
			Long currentTime = System.currentTimeMillis();
				List<CustomerField> customerFields=new ArrayList<CustomerField>();
				customerFields=extraDao.getCustomerFieldsForCompany(companyId);
			Log.info(this.getClass(), "Time Taken for executing the medhod getCustomerFieldsForCompany : "+(System.currentTimeMillis() - currentTime));
			return customerFields;
		}
	 
	 public List<CustomerField> getCustomerFields() {
			
			return extraDao.getCustomerFields();
		}

}
