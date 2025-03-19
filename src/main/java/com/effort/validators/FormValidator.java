package com.effort.validators;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.routines.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.effort.context.AppContext;
import com.effort.dao.MediaDao;
import com.effort.entity.CustomEntityFilteringCritiria;
import com.effort.entity.Customer;
import com.effort.entity.CustomerAutoFilteringCritiria;
import com.effort.entity.FieldValidationCritiria;
import com.effort.entity.Form;
import com.effort.entity.FormAndField;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormFieldSpecValidValue;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSectionFieldSpecValidValue;
import com.effort.entity.FormSectionSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.ListFilteringCritiria;
import com.effort.entity.Media;
import com.effort.entity.MobileNumberVerification;
import com.effort.entity.VisibilityDependencyCriteria;
import com.effort.manager.WebAdditionalSupportExtraManager;
import com.effort.manager.WebExtensionManager;
import com.effort.manager.WebManager;
import com.effort.manager.WebSupportManager;
import com.effort.settings.Constants;
import com.effort.util.Api;
import com.effort.util.Log;
import com.effort.util.SecurityUtils;

@Component
public class FormValidator extends CommonValidator implements Validator{
	
	@Autowired
	private Constants constants;
	
	@Autowired
	private MediaDao mediaDao;
	
	private WebManager getWebManager(){
		WebManager webManager = AppContext.getApplicationContext().getBean("webManager",WebManager.class);
			return webManager;
	}

	public void validateForm(Form form, long companyId, List<ObjectError> errors) {
		FormSpec formSpec = getWebManager().getFormSpec(form.getFormSpecId()+"");
		if(formSpec == null){
			errors.add(rejectValue("formSpecId","invalid formSpecId"));
		} else if(formSpec.getCompanyId() != companyId){
			errors.add(rejectValue("formSpecId","invalid formSpecId"));
		}
	}
	
	private WebExtensionManager getWebExtensionManager(){
		WebExtensionManager webExtensionManager = AppContext.getApplicationContext().getBean("webExtensionManager",WebExtensionManager.class);
		return webExtensionManager;
	}
	
	private WebAdditionalSupportExtraManager getWebAdditionalSupportExtraManager(){
		WebAdditionalSupportExtraManager webAdditionalSupportExtraManager = AppContext.getApplicationContext().getBean("webAdditionalSupportExtraManager",WebAdditionalSupportExtraManager.class);
		return webAdditionalSupportExtraManager;
	}
	
private WebSupportManager getWebSupportManager(){
		
		WebSupportManager webSupportManager = AppContext.getApplicationContext().getBean("webSupportManager",WebSupportManager.class);
		
		return webSupportManager;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void validateFields(List<FormField> formFields, boolean errorByLabelName, 
			long companyId, long empId, Map<Long, FormFieldSpec> formFieldSpecMap, 
			List<ObjectError> errors, boolean web, FormAndField formAndField, boolean forModify, 
			boolean fromSync, boolean isMasterForm) {
		Map<Long, Form> formMap = new HashMap<Long, Form>();
		Map<Long, FormFieldSpecValidValue> validValueMap = new HashMap<Long, FormFieldSpecValidValue>();
		
		String assignTo = formAndField == null ? null : formAndField.getAssignTo(); 
		
		if(web)
		{
			// Code for visibility condition by Tushar 
			//24-05-2016 
			String empgroupIds = getWebManager().getEmployeeGroupIdsForEmployee("" + empId);
		    List<FormSectionSpec> formSectionSpecs=	getWebManager().getFormSectionSpecs(formAndField.getFormSpecId());
		    List<FormFieldSpec> formFieldSpecs=new ArrayList<FormFieldSpec>(formFieldSpecMap.values());
		    Map<String, Integer> visibilityCondtionSectionFieldSpecMap = new HashMap<String, Integer>();
		    Map<String, Boolean> condtionMandatorySectionFieldSpecMap = new HashMap<String, Boolean>();
		    List<FormSectionField> formSectionFields=new ArrayList<FormSectionField>();
		    
		    if(formAndField.getFields()!=null){
		    	formFields=formAndField.getFields();
		    }
		    
		    if(formAndField.getSectionFields()!=null){
		    	formSectionFields=formAndField.getSectionFields();
		    }
		    
		    getWebManager().resolveVisibiltyDependecyForForForm(formAndField.getFormSpecId(), formFieldSpecs, formSectionSpecs, formFields, formSectionFields, visibilityCondtionSectionFieldSpecMap,true, condtionMandatorySectionFieldSpecMap, formAndField.getCustomer());
		    
		    List<FormSectionFieldSpec> formSectionFieldSpecs=new ArrayList<FormSectionFieldSpec>();
		     for (FormSectionSpec formSectionSpec : formSectionSpecs) {
		    	 formSectionFieldSpecs.addAll(formSectionSpec.getFormSectionFieldSpecs());
			 }
		     
		      getWebManager().resolveFormFieldsWithRestrictions(formFieldSpecs, ""+formAndField.getFormSpecId(), empgroupIds, new ArrayList<ListFilteringCritiria>(), new ArrayList<VisibilityDependencyCriteria>(),  new ArrayList<FieldValidationCritiria>(),new ArrayList<CustomEntityFilteringCritiria>(),false,new ArrayList<CustomerAutoFilteringCritiria>());
			    getWebManager().resolveFormSectionFieldsWithRestrictions(formSectionSpecs, ""+formAndField.getFormSpecId(), empgroupIds, new ArrayList<ListFilteringCritiria>(), new ArrayList<VisibilityDependencyCriteria>(), new ArrayList<FieldValidationCritiria>(),new ArrayList<CustomEntityFilteringCritiria>(),false);
			    formFieldSpecMap = (Map) Api.getMapFromList(formFieldSpecs, "fieldSpecId");
			// End of code for resolved visibility condition   
		}
		 
		Iterator<FormFieldSpec> formFieldSpecIterator = formFieldSpecMap.values().iterator();
		while (formFieldSpecIterator.hasNext()) {
			FormFieldSpec formFieldSpec = formFieldSpecIterator.next();
			if(formFieldSpec.isRequired() && (formFieldSpec.getVisible()==1 && formFieldSpec.getEditable()==1 && formFieldSpec.getIsVisible() &&
					formFieldSpec.getVisbleOnVisibilityCondition()!=0)){
				if(formFields == null || !formFields.contains(formFieldSpec)){
					String errMesgPrefix = formFieldSpec.getFieldSpecId() + "";
					if(errorByLabelName){
						errMesgPrefix = formFieldSpec.getFieldLabel();
					}
					
					if(web){
					       errors.add(rejectValue("fieldValue", errMesgPrefix + " field required"));
						if(formFieldSpec.getVisbleOnVisibilityCondition()!=0 && formFieldSpec.getIsVisible()){
							formAndField.setError(formFieldSpec.getFieldLabel() + " is required");
						}
					} else {
						return;
					}
				}
			}
		}
		
		
		if(formFields != null){
			
			for (FormField formField : formFields) {
				if(!fromSync && !formField.isTicketingField() && !formAndField.isTicketingForm())
				{
					SecurityUtils.stripInvalidCharactersFromField(formField);
				}
				
				if(forModify){
					Form form = formMap.get(formField.getFormId());
					
					if(form == null){
						if(isMasterForm) {
							form = getWebAdditionalSupportExtraManager().getMasterForm(formField.getFormId());
						}else {
							form = getWebManager().getForm(formField.getFormId()+"");
						}
						if(form != null){
							formMap.put(form.getFormId(), form);
						}
					}
					
					if(form == null){
						//invalid form
						errors.add(rejectValue("formSpecId", "Form: "+formField.getFormId()+" invalid formId"));
						if(web){
							formAndField.setError("Invalid formId");
						}
						return;
					} else if(form.getCompanyId() != companyId){
						//invalid form
						errors.add(rejectValue("formSpecId", "Form: "+formField.getFormId()+" invalid formId"));
						if(web){
							formAndField.setError("Access denied");
						}
						return;
					} else {
						formField.setFormSpecId(form.getFormSpecId());
						
						if(formField.getFormSpecId() != form.getFormSpecId()){
							//invalid formSpec
							errors.add(rejectValue("formSpecId","Form: "+formField.getFormId()+" invalid formSpecId"));
							if(web){
								formField.setError("Invalid form spec");
							}
							return;
						} else {
							//validateField(formField, errorByLabelName, assignTo, empId, formFieldSpecMap, validValueMap, errors, web,forModify, fromSync);
						}
					}
				} else {
					//validateField(formField, errorByLabelName, assignTo, empId, formFieldSpecMap, validValueMap, errors, web,false, fromSync);
				}
			}
		}
	}
	public void validateFieldsForSync(List<FormField> formFields, boolean errorByLabelName, long companyId, long empId, Map<Long, FormFieldSpec> formFieldSpecMap, List<ObjectError> errors, boolean fromSync, boolean isMasterForm) {
		validateFields(formFields, errorByLabelName, companyId, empId, formFieldSpecMap, errors, false, null, false, fromSync,isMasterForm);
	}
	

	public void validateSectionFieldsForSync(List<FormSectionField> formSectionFields, boolean errorByLabelName, long companyId, long empId, Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap, List<ObjectError> errors, boolean fromSync,boolean isMasterForm) {
		validateSectionFields(formSectionFields, errorByLabelName, companyId, empId, formSectionFieldSpecMap, errors, false, null, false,null, null, fromSync,isMasterForm);
	}

	public boolean supports(Class<?> clazz) {
		return Form.class.isAssignableFrom(clazz);
	}
	public void validateSectionFields(List<FormSectionField> formSectionFields, boolean errorByLabelName, long companyId, long empId, Map<Long, FormSectionFieldSpec> formSectionFieldSpecMap, List<ObjectError> errors, boolean web, FormAndField formAndField, boolean forModify, Map<String, Integer> visibilityCondtionSectionFieldSpecMap, Map<String, Boolean> condtionMandatorySectionFieldSpecMap, boolean fromSync,boolean isMasterForm) {
//		if(formSectionFields != null){
		Map<Long, Form> formMap = new HashMap<Long, Form>();
		Map<Long, FormSectionFieldSpecValidValue> validValueMap = new HashMap<Long, FormSectionFieldSpecValidValue>();
		
		String assignTo = formAndField == null ? null : formAndField.getAssignTo(); 
		
		
		if(formSectionFields != null){
			Iterator<FormSectionField> sectionFieldIterator = formSectionFields.iterator();
			while (sectionFieldIterator.hasNext()) {
				FormSectionField formSectionField = (FormSectionField) sectionFieldIterator.next();
				if(formSectionField.getFormSpecId() <= 0){
					sectionFieldIterator.remove();
				}
			}
		}
	

	}
	
	public long checkOtpVerifiedStatus(int verificationId,int otp,String mobileNo){
		long status = 0;
		MobileNumberVerification mobileDto = new MobileNumberVerification();
		mobileDto.setId(verificationId);
		mobileDto.setOtp(otp);
		mobileDto.setPhoneNo(Long.parseLong(mobileNo));
		
		status = getWebExtensionManager().verifyMobileNumberAndOtpForWeb(mobileDto);
		
		return status;
	}
	
	public void validateFormField(FormField formField,int fieldTye,String min1 ,String max1,List<ObjectError> errors,String fieldValue,String errMesgPrefix){
		
		if(fieldTye==1 || fieldTye==9){
			if (!Api.isEmptyString(min1) &&!Api.isEmptyString( max1)) {
				double min = Double.parseDouble(min1);
				double max = Double.parseDouble(max1);
				// double value=Double.parseDouble(fieldValue);
				if (fieldValue != null && (fieldValue.length() < min || fieldValue.length() > max)) {
					errors.add(rejectValue("fieldValue", errMesgPrefix + " length should be in  between " + min1 + " and " + max1));
					formField.setError("Field value length should be in between " + min1 + " and " + max1);
				}

			}else if(!Api.isEmptyString(min1)){
				double min = Double.parseDouble(min1);
				// double value=Double.parseDouble(fieldValue);
				if (fieldValue != null && (fieldValue.length() < min)) {
					errors.add(rejectValue("fieldValue", errMesgPrefix + " length should be  greater than" + min1));
					formField.setError("Field value length should not be lesser than " + min1 );
				}
				
			}else if(!Api.isEmptyString(max1)){
				double max = Double.parseDouble(max1);
				if (fieldValue != null && (fieldValue.length() > max)) {
					errors.add(rejectValue("fieldValue", errMesgPrefix + " length should not be Greter than " + max1));
					formField.setError("Field value length should not be Greter than "+ max1);
				}
			}
		}
	    else if(fieldTye==2||fieldTye==16){
		if (isDecimal(min1) && isDecimal(max1)) {
			double min = Double.parseDouble(min1);
			double max = Double.parseDouble(max1);
			double value = Double.parseDouble(fieldValue);
			if (value < min || value > max) {

				errors.add(rejectValue("fieldValue", "field value should be between " + min1 + " and " + max1));
				formField.setError("Field value should be between " + min1 + " and " + max1);
			}
		} else if (isDecimal(min1)) {
			double min = Double.parseDouble(min1);
			double value = Double.parseDouble(fieldValue);
			if (value < min) {

				errors.add(rejectValue("fieldValue", "field value should not be lesser than " + min1));
				formField.setError("Field value should be between " + min1);

			}
		} else if (isDecimal(max1)) {
			double max = Double.parseDouble(max1);
			double value = Double.parseDouble(fieldValue);
			if (value > max) {
				errors.add(rejectValue("fieldValue", "field value should not be greater than " + max1));
				formField.setError("Field value should  should not be greater than " + max1);

			}
		}
	  }
	}
	
	private String correctDecimalValue(String fieldValue) {
		try{
		fieldValue =fieldValue.replaceAll(",(\\d\\d)$", ".$1");
		}catch(Exception ex){
			Log.info(this.getClass(), "getting exception at while replacing , with .", ex);
		}
	    return fieldValue;
	}
	
	public static boolean dateValidator(String date) {
//		return Pattern.matches("((19|20|21|22)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", date);
		try {
			/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			format.parse(date);
//			format.setLenient(false);
			if(date.length()!=10){
			   return false; 
			}*/
			Date date1=DateValidator.getInstance().validate(date, "yyyy-MM-dd");
			if(date1 == null)
				return false;
						
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void validateField(FormField formField, boolean errorByLabelName, String assignTo, long empId, Map<Long, FormFieldSpec> formFieldSpecMap, Map<Long, FormFieldSpecValidValue> validValueMap, List<ObjectError> errors, boolean web, boolean forModify,boolean fromSync){
		String errMesgPrefix = "Form: "+formField.getFormId();
		String errMesgPrefixLong = "Form: "+formField.getFormId()+" "+formField.getFieldSpecId();
		//long formSpecId = formField.getFormSpecId();
		//FormSpec formSpec = getWebManager().getFormSpec(formSpecId+"");
		
		long otpStatus = 0; 
		String savedValue = "";
				
		FormFieldSpec fieldSpec = formFieldSpecMap.get(formField.getFieldSpecId());
		
		if(errorByLabelName){
			errMesgPrefix = fieldSpec.getFieldLabel();
			errMesgPrefixLong = fieldSpec.getFieldLabel();
		}
		
		if(fieldSpec == null){
			//invalid filedSpec
			errors.add(rejectValue("formSpecId", errMesgPrefix+" invalid filedSpecId"));
			if(web){
				formField.setError("Invalid filed spec");
			}
			return;
		} else {
			String fieldValue = formField.getFieldValue();
			
			int fieldType = fieldSpec.getFieldType();
			
			if(fieldType == 4 || fieldType == 5 || fieldType == 6 || fieldType == 7){
				fieldValue = Api.makeNullIfEmpty(fieldValue);
				formField.setFieldValue(fieldValue);
			}
			if(fieldType == 18){
				if(fieldValue!=null&&fieldValue.startsWith(",")){
					fieldValue=null;
				}
				fieldValue = Api.makeNullIfEmpty(fieldValue);
//				fieldValue = "17.5602465032949,78.310546875";
				
				formField.setFieldValue(fieldValue);
			}
			
			/**Written by Ameer for platform
			 * Requirement : Regular expression validation for text fields
			 * Note : If field value is empty or null then field value is not validated
			 */
			if(!fromSync && fieldType == 1 && !Api.isEmptyString(fieldValue) && !Api.isEmptyString(fieldSpec.getValidationExpr()) && fieldSpec.getVisible()==1)
			{
				if(!Api.isValueMatchForRegEx(fieldValue, fieldSpec.getValidationExpr()))
				{
					String errorMsg = "Default Error Message : Value not matched for specified Regular Expression";
					if(fieldSpec.getValidationErrorMsg() != null || !Api.isEmptyString(fieldSpec.getValidationErrorMsg().trim()))
					{
						errorMsg = fieldSpec.getValidationErrorMsg().trim();
					}
					errors.add(rejectValue("fieldValue", errorMsg));
					if (web) 
					{
						formField.setError(errorMsg);
					}
					else 
					{
						return;
					}
				}
			}
			
			if(!fromSync && fieldType == 1 && fieldSpec.getIsRemoteField() == 1 && 
					!Api.isEmptyString(fieldSpec.getValidationExpr()) && fieldSpec.getVisible()==1)
			{
				if(Api.isEmptyString(fieldValue)) {
					
					if (web) {
						String errorMsg = "Default Error Message : Value not matched for specified Regular Expression";
						if(fieldSpec.getValidationErrorMsg() != null || !Api.isEmptyString(fieldSpec.getValidationErrorMsg().trim())){
							errorMsg = fieldSpec.getValidationErrorMsg().trim();
						}
						formField.setError(errorMsg);
						errors.add(rejectValue("fieldValue", errorMsg));
						return;
					}
				}
				
				if(!Api.isValueMatchForRegEx(fieldValue, fieldSpec.getValidationExpr()))
				{
					String errorMsg = "Default Error Message : Value not matched for specified Regular Expression";
					if(fieldSpec.getValidationErrorMsg() != null || !Api.isEmptyString(fieldSpec.getValidationErrorMsg().trim()))
					{
						errorMsg = fieldSpec.getValidationErrorMsg().trim();
					}
					errors.add(rejectValue("fieldValue", errorMsg));
					if (web) 
					{
						formField.setError(errorMsg);
					}
					else 
					{
						return;
					}
				}
			}
					
			if(fieldSpec.isRequired()&&(fieldSpec.getVisible()==1 && fieldSpec.getEditable()==1 && fieldSpec.getIsVisible()) && (Api.isEmptyString(fieldValue) || Api.isEmptyString(fieldValue.trim()))){
				//field required
				
				if(web){
					if(fieldSpec.getVisbleOnVisibilityCondition()!=0 && fieldSpec.getVisible()!=0){
						errors.add(rejectValue("fieldValue", errMesgPrefixLong + " field required"));
					}
					
					if(fieldSpec.getVisbleOnVisibilityCondition()!=0 && fieldSpec.getVisible()!=0){
					formField.setError(fieldSpec.getFieldLabel() + " is required");
					}
					
					if(fieldSpec.getIsWorkInviationField()){
						formField.setError(fieldSpec.getFieldLabel() + " is required");
					}
					
					
					
					
				}else {
					return;
				}
			}
			else if(formField.getFormId() <= 0 &&(fieldSpec.getVisible()==1 &&  fieldSpec.getIsVisible()) && fieldValue != null && (!Api.isEmptyString(fieldValue) || !Api.isEmptyString(fieldValue.trim())) ){

				
				if(web){
					if(fieldSpec.getNoOfOtpDigits() != null && fieldSpec.getNoOfOtpDigits() > 0){

						otpStatus = checkOtpVerifiedStatus(formField.getVerificationId(),formField.getOtp(),formField.getFieldValue());
						formField.setOtpStatus(otpStatus);
						if(otpStatus != MobileNumberVerification.OTP_VERIFIED){
							errors.add(rejectValue("fieldValue", "Otp needs to be verified for "+formField.getFieldValue()));
							formField.setError("Otp needs to be verified for "+formField.getFieldValue());
						}

					}
				}

			} 
			
			else if(formField.getFormId() > 0 && (fieldSpec.getVisible()==1 &&  fieldSpec.getIsVisible()) && fieldValue != null &&  (!Api.isEmptyString(fieldValue) || !Api.isEmptyString(fieldValue.trim()))){
				if(fieldSpec.getNoOfOtpDigits() != null && fieldSpec.getNoOfOtpDigits() > 0){
					savedValue = getWebExtensionManager().getExistedValueAgainstFormSpecAndFieldSpec(formField.getFormId(),formField.getFormSpecId(),fieldSpec.getFieldSpecId());

					if(!formField.getFieldValue().equals(savedValue)){
						otpStatus = checkOtpVerifiedStatus(formField.getVerificationId(),formField.getOtp(),formField.getFieldValue());
						formField.setOtpStatus(otpStatus);
						if(otpStatus != MobileNumberVerification.OTP_VERIFIED){
							errors.add(rejectValue("fieldValue", "Field value Modified.Modified value otp needs to be verified for "+formField.getFieldValue()));
							formField.setError("Field value Modified.Modified value otp needs to be verified for "+formField.getFieldValue());
						}
					}
				}
			}
			
			//Decimal Points limit restriction based on setting for number and currency field.
			if(fieldType== 2 || fieldType== 16){
				if(!Api.isEmptyString(fieldValue) && 
						fieldSpec.getDecimalValueLimit() != null 
						&&  fieldSpec.getDecimalValueLimit() > 0){
					if(fieldValue.indexOf(".") > -1){
						int decimalPoint = fieldValue.indexOf(".");
						String decimalValue = fieldValue.substring(decimalPoint+1);
						String numericValue = fieldValue.substring(0, decimalPoint+1);
						if(decimalValue.length() >= fieldSpec.getDecimalValueLimit().intValue()){
							decimalValue = decimalValue.substring(0, fieldSpec.getDecimalValueLimit().intValue())+"";
							fieldValue = numericValue+""+decimalValue;
						}else{
							int remaingPlaces =  fieldSpec.getDecimalValueLimit().intValue() - decimalValue.length();
							for(int i=0; i<remaingPlaces; i++){
								decimalValue += "0";
							}
							fieldValue = numericValue+""+decimalValue;
						}
					}/*else{
						fieldValue +=".";
						for(int i=0; i<fieldSpec.getDecimalValueLimit().intValue(); i++){
							fieldValue += "0";
						}
					}*/
				}
			}
			
			
			if(!Api.isEmptyString(fieldValue)){
				switch (fieldType) {
				case 1:
	                  {
						if (web) {
							validateFormField(formField, fieldType, fieldSpec.getMin(), fieldSpec.getMax(), errors, fieldValue, errMesgPrefixLong);
						} else {
							return;
						}
	
					}
						break;
				case 2:
					{
						
						if(fieldValue!=null){
							fieldValue=correctDecimalValue(fieldValue);
							formField.setFieldValue(fieldValue);
						}
						
						if(!isDecimal(fieldValue)){
							//invalid number
							errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid number"));
							if(web){
								formField.setError("Please insert a valid value");
							} else {
								return;
							}
						}else{
						if (web) {
							validateFormField(formField, fieldType, fieldSpec.getMin(), fieldSpec.getMax(), errors, fieldValue, errMesgPrefixLong);

						} else {
								return;
							}	
							
							
					}
					}
					break;
				case 3:
					{
						if(!dateValidator(fieldValue)){
							//invalid date
							errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid date"));
							if(web){
								formField.setError("Please insert a valid value");
							} else {
								return;
							}
						}
						if (!Api.isEmptyString(fieldValue) && fieldSpec.isEnableAgeRestriction()) {
							if (Api.evaluateDateRestriction(fieldValue, fieldSpec.getMinAge(), fieldSpec.getMaxAge())) {
								errors.add(rejectValue("fieldValue",fieldSpec.getAgeRestrictionErrorMessage()));
								if (web) {
									formField.setError(fieldSpec.getAgeRestrictionErrorMessage());
								} else {
									return;
								}
							}

						}
						
					}
					break;
				case 4:
					{
						if(!isBoolean(fieldValue)){
							//invalid boolean
							errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid boolean"));
							if(web){
								formField.setError("Please insert a valid value");
							} else {
								return;
							}
						}
					}
					break;
				case 5:
					{
						FormFieldSpecValidValue fieldSpecValidValue = validValueMap.get(fieldValue);
						
						if(fieldSpecValidValue == null){
							fieldSpecValidValue = getWebManager().getFormFieldSpecValidValue(fieldValue);
//							fieldSpecValidValue = getWebManager().getFormFieldSpecValidValue(fieldValue, fieldSpecValidValue.getFieldSpecId()+"");
							if(fieldSpecValidValue != null){
								validValueMap.put(fieldSpecValidValue.getValueId(), fieldSpecValidValue);
							}
							
							if(fieldSpecValidValue == null){
								//invalid value
								errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid value"));
								if(web){
									formField.setError("Please insert a valid value");
								} else {
									return;
								}
							} else {
								if(fieldSpecValidValue.getFieldSpecId() != formField.getFieldSpecId()){
									//invalid value
									errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid value"));
									if(web){
										formField.setError("Please insert a valid value");
									} else {
										return;
									}
								}
							}
						}
					}
					break;
				case 6:
					{
						String[] values = fieldValue.split(",");
						for (String value : values) {
							FormFieldSpecValidValue fieldSpecValidValue = validValueMap.get(value);
							
							if(fieldSpecValidValue == null){
								fieldSpecValidValue = getWebManager().getFormFieldSpecValidValue(value);
								if(fieldSpecValidValue != null){
									validValueMap.put(fieldSpecValidValue.getValueId(), fieldSpecValidValue);
								}
								
								if(fieldSpecValidValue == null){
									//invalid value
									errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid value"));
									if(web){
										formField.setError("Please insert a valid value");
									} else {
										return;
									}
								} else {
									if(fieldSpecValidValue.getFieldSpecId() != formField.getFieldSpecId()){
										//invalid value
										errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid value"));
										if(web){
											formField.setError("Please insert a valid value");
										} else {
											return;
										}
									}
								}
							}
						}
					}
					break;
				case 7:
					{
						Customer customer = getWebManager().getCustomerOrNull(fieldValue, empId);
						if(customer == null){
							//invalid customer
							errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid customer"));
							if(web){
								formField.setError("Please insert a valid value");
							} else {
								return;
							}
						} else {
							if(!Api.isEmptyString(assignTo)){
								List<Long> mapedCustomerIds = getWebManager().getCustomerIdsForEmp(Long.parseLong(assignTo));
								if(mapedCustomerIds != null && mapedCustomerIds.size() > 0 && !mapedCustomerIds.contains(customer.getCustomerId())){
									errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid customer"));
									if(web){
										formField.setError("This customer is not mapped for selected employee");
									} else {
										return;
									}
								}
							}
						}
					}
					break;
				case 8:
					{
						if(!Api.isEmptyString(fieldValue) && !emailValidator(fieldValue)){
							//invalid email
							errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid email"));
							if(web){
								formField.setError("Enter a valid email address. (eg: yourname@domain.com)");
							} else {
								return;
							}
						}
					}
					break;
				case 9:
					{
						if (web) {
							validateFormField(formField, fieldType, fieldSpec.getMin(), fieldSpec.getMax(), errors, fieldValue, errMesgPrefixLong);
						} else {
							return;
						}
					}
					break;
				case 10:
					{
					}
					break;
				case 11:
					{
						if(!Api.isEmptyString(fieldValue) && !timeValidator(fieldValue)){
							//invalid time
							errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid time"));
							if(web){
								formField.setError("Please insert a valid time");
							} else {
								return;
							}
						}
					}
					break;
				case 12:
					{
						
						if(fieldSpec.isStaticField()) {
							
							if(Api.isEmptyString(formField.getFieldValue())) {
									formField.setError("Image can not be empty");
							}
							return;	
						}
						
						/*Date: 2016-06-22
						*Method Purpose: User Specific Image Size 
						*Resource: Deva*/
						if(fieldSpec.getImageSizeEnabled()){
							
							if(fieldSpec.getImageSize() == null){
								if(web){
									formField.setError("Image size can not be null");
									return;
								} else {
									return;
								}
							}
							
							String mediaId = formField.getFieldValue();
							long fileSize = getWebSupportManager().getFileSize(mediaId);
							boolean platFormBoundsSatisfied = true;
							long definedMaxSizeInBytes = fieldSpec.getImageSize() * 1024;
							
							
							if(fileSize > constants.getSystem_defined_max_imageSize_for_type_image()){  //First checking max,min sizes from constants
								platFormBoundsSatisfied = false;
								//long systemDefinedMaxSizeInMB = (constants.getSystem_defined_max_imageSize_for_type_image() / (1024 * 1024));
								errors.add(rejectValue("fieldValue",errMesgPrefix+" Image size exceeds max allowed size "+fieldSpec.getImageSize()+" KB"));
								if(web){
									formField.setError("Image size exceeds max allowed size "+fieldSpec.getImageSize()+" KB");
								} else {
									return;
								}
							}
							else if(fileSize < constants.getSystem_defined_min_imageSize_for_type_image()){
								platFormBoundsSatisfied = false;
								long systemDefinedMinSizeInKB = (constants.getSystem_defined_min_imageSize_for_type_image() / 1024);
								errors.add(rejectValue("fieldValue",errMesgPrefix+" Image size is less than min allowed size "+systemDefinedMinSizeInKB+" KB"));
								if(web){
									formField.setError("Image size is less than min allowed size "+systemDefinedMinSizeInKB+" KB");
								} else {
									return;
								}
							}
							
							if(platFormBoundsSatisfied && fileSize > definedMaxSizeInBytes){  //Next checking max size from defined for this specific field
								errors.add(rejectValue("fieldValue",errMesgPrefix+" Image size exceeds max allowed size "+fieldSpec.getImageSize()+" KB"));
								if(web){
									formField.setError("Image size exceeds max allowed size "+fieldSpec.getImageSize()+" KB");
								} else {
									return;
								}
							}
						}
						
					}
					break;
				case 13:
					{
					}
					break;
				case 14:
				{
				}
				break;
				case 15:
				{
				}
				break;
				case 16:
				{
					if(fieldValue!=null){
						fieldValue=correctDecimalValue(fieldValue);
						formField.setFieldValue(fieldValue);
					}
					
					if(!isDecimal(fieldValue)){
						//invalid number
						errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid number"));
						if(web){
							formField.setError("Please insert a valid value");
						} else {
							return;
						}
					}else{
						if (web) {
							validateFormField(formField, fieldType, fieldSpec.getMin(), fieldSpec.getMax(), errors, fieldValue, errMesgPrefixLong);
						} else {
						     return;
						}
						
					}
				}
				break;
				case 17:
				{
				}
				break;
				case 18:
				{
					if(!Api.isEmptyString(fieldValue)){
						String[] locations=fieldValue.split(",");
						if(locations.length>1){
							for (int i = 0; i < locations.length; i++) {
								if(!isDecimal(locations[i])){
									//invalid number
									errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid latitude/longitude"));
									if(web){
										formField.setError("Please insert a valid value");
									} else {
										return;
									}
								}
							}
						}else{
							formField.setFieldValue(null);
						}	
					}
				}
				break;
				case 19:
				{
					if(!Api.isEmptyString(fieldValue)){

						
						Date date=DateValidator.getInstance().validate(fieldValue, "yyyy-MM-dd HH:mm:ss");
						if(date==null){
							errors.add(rejectValue("fieldValue",errMesgPrefix+" invalid dateTime"));
							if(web){
								formField.setError("Please enter valid Datetime");
							} else {
								return;
							}
						}

					}
				}
				break;
				
				case 20:
				{
					if(!Api.isEmptyString(fieldValue)){
						
		                       String country = isValidCountry(fieldValue);
		                       if(country == null){
		                               errors.add(rejectValue("addressCountry", "Please enter valid country"));
		                               if(web){
		                            	   formField.setError("Please enter valid country"); 
		                               }else{
		                            	   return ;
		                               }
		                       } else {
		                    	   formField.setFieldValue(country);
		                       }
						

					} 
				}
				break;
				case 22:
				{
					
					if(fieldSpec.isStaticField()) {
						
						if(Api.isEmptyString(formField.getFieldValue())) {
								formField.setError("Audio can not be empty");
						}
						return;	
					}
					
					if(web){
						String contentType = "";
						if(formField.getFile() != null) {
							contentType = formField.getFile().getContentType();
							if(!Api.isEmptyString(contentType) && (contentType.startsWith("audio") || contentType.endsWith("octet-stream"))){
								
							}else{
									errors.add(rejectValue("Audio", "Please enter valid audio file"));
			                     	   formField.setError("Please enter valid audio file"); 
							}
						}
					}else{
						return;
					}
				}
				break;
				
				case 27:
				{
				
					if(fieldSpec.isStaticField()) {
						
						if(Api.isEmptyString(formField.getFieldValue())) {
								formField.setError("Video can not be empty");
						}
						return;	
					}
					
					if(web){
						String contentType = "";
						if(formField.getFile() != null) {
							contentType = formField.getFile().getContentType();
							if(!Api.isEmptyString(contentType) && ( contentType.startsWith("video") || contentType.endsWith("octet-stream"))){
								
							}else{
									errors.add(rejectValue("Video", "Please enter valid video file"));
			                     	   formField.setError("Please enter valid video file"); 
								
							}
						}
					}
					else{
						return;
					}
				}
				break;
				
				case 33:
				{
					
					if(fieldSpec.isStaticField()) {
						
						if(Api.isEmptyString(formField.getFieldValue())) {
								formField.setError("Document can not be empty");
						}
						return;	
					}
					
					if(fieldSpec.getImageSizeEnabled()){
						
						if(fieldSpec.getImageSize() == null){
							if(web){
								formField.setError("Media size can not be null");
								return;
							} else {
								return;
							}
						}
						String mediaId = formField.getFieldValue();
						long fileSize = getWebSupportManager().getFileSize(mediaId);
						boolean platFormBoundsSatisfied = true;
						long definedMaxSizeInBytes = fieldSpec.getImageSize() * 1024;
						
						
						if(fileSize > constants.getSystem_defined_max_imageSize_for_type_image()){  //First checking max,min sizes from constants
							platFormBoundsSatisfied = false;
							//long systemDefinedMaxSizeInMB = (constants.getSystem_defined_max_imageSize_for_type_image() / (1024 * 1024));
							errors.add(rejectValue("fieldValue",errMesgPrefix+" Media size exceeds max allowed size "+fieldSpec.getImageSize()+" KB"));
							if(web){
								formField.setError("Media size exceeds max allowed size "+fieldSpec.getImageSize()/1024+" MB");
							} else {
								return;
							}
						}
						else if(fileSize < constants.getSystem_defined_min_imageSize_for_type_image()){
							platFormBoundsSatisfied = false;
							long systemDefinedMinSizeInKB = (constants.getSystem_defined_min_imageSize_for_type_image() / 1024);
							errors.add(rejectValue("fieldValue",errMesgPrefix+" Media size is less than min allowed size "+systemDefinedMinSizeInKB+" KB"));
							if(web){
								formField.setError("Media size is less than min allowed size "+systemDefinedMinSizeInKB+" KB");
							} else {
								return;
							}
						}
						
						if(platFormBoundsSatisfied && fileSize > definedMaxSizeInBytes){  //Next checking max size from defined for this specific field
							errors.add(rejectValue("fieldValue",errMesgPrefix+" Media size exceeds max allowed size "+fieldSpec.getImageSize()+" KB"));
							if(web){
								formField.setError("Media size exceeds max allowed size "+fieldSpec.getImageSize()/1024+" MB");
							} else {
								return;
							}
						}
					}
					else
					{
						String mediaId = formField.getFieldValue();
						long fileSize = getWebSupportManager().getFileSize(mediaId);
						if(fileSize > constants.getSystem_defined_max_imageSize_for_type_image()){  //First checking max,min sizes from constants
							errors.add(rejectValue("fieldValue",errMesgPrefix+" Media size exceeds max allowed size 10 MB"));
							if(web){
								formField.setError("Media size exceeds max allowed size 10 MB");
							} else {
								return;
							}
						}
					}
					
					if(fieldSpec.getEnableMediaFormatRestriction() == 1){
						String mediaId = formField.getFieldValue();
						Media media = mediaDao.getMedia(mediaId);
						String mediaType = "";
						String fileExtension = media.getFileName().substring(media.getFileName().lastIndexOf("."));
						if (fileExtension.equalsIgnoreCase(".xls") || media.getFileName().contains(".vnd.ms-excel")) {
							mediaType = "xls";
						} 
						else if (fileExtension.equalsIgnoreCase(".pdf")) {
							mediaType = "pdf";
						}
						else if (fileExtension.equalsIgnoreCase(".pds")) {
							mediaType = "pds";
						}
						else if (fileExtension.equalsIgnoreCase(".doc") || fileExtension.equalsIgnoreCase(".msword")) {
							mediaType = "doc";
						} else if (fileExtension.equalsIgnoreCase(".docx")
								|| media.getFileName().contains("vnd.openxmlformats-officedocument.wordprocessingml.document")) {
							mediaType = "docx";
						} else if (fileExtension.equalsIgnoreCase(".xlsx")
								|| media.getFileName().contains(".vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
							mediaType = "xlsx";
						} else if (fileExtension.equalsIgnoreCase(".ppt") || media.getFileName().contains(".vnd.ms-powerpoint")) {
							mediaType = "ppt";
						} else if (fileExtension.equalsIgnoreCase(".pptx")
								|| media.getFileName().contains("vnd.openxmlformats-officedocument.presentationml.presentation")) {
							mediaType = "pptx";
						} else if (fileExtension.equalsIgnoreCase(".odp")
								|| media.getFileName().contains("vnd.oasis.opendocument.presentation")) {
							mediaType = "odp";
						} else if (fileExtension.equalsIgnoreCase(".ods")
								|| media.getFileName().contains("vnd.oasis.opendocument.spreadsheet")) {
							mediaType = "doc";
						} else if (fileExtension.equalsIgnoreCase(".msg")) {
							mediaType = "doc";
						} else if (fileExtension.equalsIgnoreCase(".zip") || media.getFileName().contains("x-zip-compressed")) {
							mediaType = "zip";
						} else if (fileExtension.equalsIgnoreCase(".csv")
								|| fileExtension.equalsIgnoreCase(".comma-separated-values")) {
							mediaType = "csv";
						} else if (fileExtension.equalsIgnoreCase(".ppa")) {
							mediaType = "ppa";
						} else if (fileExtension.equalsIgnoreCase(".pps")) {
							mediaType = "pps";
						} else if (media.getFileName().contains(".psd") || media.getFileName().contains("vnd.adobe.photoshop") ) {
							mediaType = "psd";
						} else if (fileExtension.equalsIgnoreCase(".eml")) {
							mediaType = "eml";
						} else if (fileExtension.equalsIgnoreCase(".sheet")) {
							mediaType = "sheet";
						} else if (fileExtension.equalsIgnoreCase(".ms-excel")) {
							mediaType = "ms-excel";
						}
						else if (fileExtension.equalsIgnoreCase(".txt")) {
							mediaType = "txt";
						}
						
					if(!Api.isEmptyString(mediaType) && fieldSpec.getAllowRequiredFormat().contains(mediaType))
					{
						return;
					}
					else {
						errors.add(rejectValue("fieldValue"," Media format is another than specified "+fieldSpec.getAllowRequiredFormat()));
							formField.setError("Media format is another than specified "+fieldSpec.getAllowRequiredFormat());
					}
					}
					
				}
				break;
				
				}
				}
			}
		}
	
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}
	
	
}

