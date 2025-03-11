package com.effort.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.effort.context.AppContext;
import com.effort.entity.Form;
import com.effort.entity.FormAndField;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormFieldSpecValidValue;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSectionSpec;
import com.effort.entity.FormSpec;
import com.effort.manager.WebManager;
import com.effort.util.Api;
import com.effort.util.SecurityUtils;

@Component
public class FormValidator extends CommonValidator implements Validator{
	
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
							validateField(formField, errorByLabelName, assignTo, empId, formFieldSpecMap, validValueMap, errors, web,forModify, fromSync);
						}
					}
				} else {
					validateField(formField, errorByLabelName, assignTo, empId, formFieldSpecMap, validValueMap, errors, web,false, fromSync);
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

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
