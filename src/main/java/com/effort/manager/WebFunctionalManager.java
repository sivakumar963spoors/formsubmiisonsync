package com.effort.manager;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import com.effort.context.AppContext;
import com.effort.dao.ExtraSupportAdditionalSupportDao;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.WorkOnDemandMapping;
@Service
public class WebFunctionalManager {
	
	private ExtraSupportAdditionalSupportDao getExtraSupportAdditionalSupportDao() {
		ExtraSupportAdditionalSupportDao extraSupportAdditionalSupportDao = AppContext.getApplicationContext()
				.getBean("extraSupportAdditionalSupportDao", ExtraSupportAdditionalSupportDao.class);
		return extraSupportAdditionalSupportDao;
	}
	
	public boolean validateCustomerFieldsForMissingMandatoryFields(
			List<FormField> masterCustomerFormFieldsToValidate,
			Map<Long, FormFieldSpec> formFieldSpecMap,
			List<ObjectError> errorsTemp, boolean isMasterForm)
	{
		
		boolean customerMandatoryFormFieldsMissing = false;
		Iterator<FormFieldSpec> formFieldSpecIterator = formFieldSpecMap.values().iterator();
		while (formFieldSpecIterator.hasNext()) {
			FormFieldSpec formFieldSpec = formFieldSpecIterator.next();
			if(formFieldSpec.isRequired() && (formFieldSpec.getVisible()==1 && formFieldSpec.getEditable()==1 && formFieldSpec.getIsVisible() &&
					formFieldSpec.getVisbleOnVisibilityCondition()!=0)){
				if(masterCustomerFormFieldsToValidate == null || !masterCustomerFormFieldsToValidate.contains(formFieldSpec)){
					if(formFieldSpec.getVisbleOnVisibilityCondition()!=0 && formFieldSpec.getIsVisible()){
						customerMandatoryFormFieldsMissing = true;
						break;
					}
				}
			}
		}
		
		return customerMandatoryFormFieldsMissing;
		
	}
	
	

	

public WorkOnDemandMapping getWorkOnDemandMappingForGivenFormSpec(String uniqueId, int companyId) {
	return getExtraSupportAdditionalSupportDao().getWorkOnDemandMappingForGivenFormSpec(uniqueId,companyId);
}
	
}
