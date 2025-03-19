package com.effort.manager;

import org.springframework.stereotype.Service;

import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;

@Service
public class SchedularManager {
public FormField getFormField(long formId,FormFieldSpec formFieldSpec,String fieldValue,String displayValue) {
		
		FormField formField = new FormField();
		formField.setFormId(formId);
		formField.setFormSpecId(formFieldSpec.getFormSpecId());
		formField.setFieldSpecId(formFieldSpec.getFieldSpecId());
		formField.setFieldValue(fieldValue);
		formField.setDisplayValue(displayValue);
		formField.setFieldType(formFieldSpec.getFieldType());
		formField.setFieldLabel(formFieldSpec.getFieldLabel());
		formField.setExpression(formFieldSpec.getExpression());
		formField.setDisplayOrder(formFieldSpec.getDisplayOrder());
		
		formField.setInitialFormFieldSpecId(formFieldSpec.getInitialFormFieldSpecId());
		if(formFieldSpec.getInitialFormFieldSpecId() == null)
			formField.setInitialFormFieldSpecId(-1l);
		
		
		formField.setSkeletonFormFieldSpecId(formFieldSpec.getSkeletonFormFieldSpecId());
		if(formFieldSpec.getSkeletonFormFieldSpecId() == null)
			formField.setSkeletonFormFieldSpecId(-1l);
		
		formField.setIsVisible(1);
		if (!formFieldSpec.getIsVisible())
			formField.setIsVisible(0);
		
		if(formFieldSpec.isIdentifier())
		{
			formField.setIdentifier(1);
		}else
		{
			formField.setIdentifier(0);
		}
		formField.setUniqueId(formFieldSpec.getUniqueId());
		return formField;
	}

public FormSectionField getFormSectionField(long formId,FormSectionFieldSpec formSectionFieldSpec,
		String fieldValue,String displayValue,int instanceId) {
	
	FormSectionField sectionField = new FormSectionField();
	sectionField.setFormSpecId(formSectionFieldSpec.getFormSpecId());
	sectionField.setSectionSpecId(formSectionFieldSpec.getSectionSpecId());
	sectionField.setSectionFieldSpecId(formSectionFieldSpec.getSectionFieldSpecId());
	sectionField.setFieldType(formSectionFieldSpec.getFieldType());
	sectionField.setUniqueId(formSectionFieldSpec.getUniqueId());
	sectionField.setInstanceId(instanceId);
	sectionField.setFieldValue(fieldValue);
	sectionField.setDisplayValue(displayValue);
	return sectionField;
}
	
	
	
}
