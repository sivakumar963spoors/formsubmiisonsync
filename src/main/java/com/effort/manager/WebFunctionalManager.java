package com.effort.manager;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import com.effort.context.AppContext;
import com.effort.dao.ExtraSupportAdditionalSupportDao;
import com.effort.entity.CustomEntityFilteringCritiria;
import com.effort.entity.Form;
import com.effort.entity.FormField;
import com.effort.entity.FormFieldSpec;
import com.effort.entity.FormSectionField;
import com.effort.entity.FormSectionFieldSpec;
import com.effort.entity.FormSectionSpec;
import com.effort.entity.FormSpec;
import com.effort.entity.RichTextFormField;
import com.effort.entity.RichTextFormSectionField;
import com.effort.entity.WorkOnDemandMapping;
import com.effort.settings.Constants;
import com.effort.util.Api;
import com.effort.util.Log;

import io.micrometer.common.util.StringUtils;
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
	public void resolveRichTextFormSectionField(Form form, long formSpecId, FormSectionField formSectionField) {

		String logText = "resolveRichTextFormField() // formId = " + form.getFormId();
		try {
			RichTextFormSectionField richTextFormSectionField = new RichTextFormSectionField(formSpecId,
					formSectionField.getSectionSpecId(), formSectionField.getSectionFieldSpecId(), form.getFormId(),
					formSectionField.getFieldValue(), formSectionField.getInstanceId());

			long richTextFormSectionFieldId = getExtraSupportAdditionalSupportDao()
					.insertRichTextFormSectionField(richTextFormSectionField);
			Log.info(getClass(), logText + " inserted richTextValueId = " + richTextFormSectionFieldId);

			formSectionField.setFieldValue(richTextFormSectionFieldId + "");
			formSectionField.setDisplayValue(richTextFormSectionFieldId + "");
		} catch (Exception e) {
			e.printStackTrace();
			Log.info(getClass(), logText + " exception occured ", e);
		}
	}
	public void resolveRichTextFormSectionFields(Form form, List<FormSectionField> sectionFields, FormSpec formSpec) {

		try {

			for (FormSectionField formSectionField : sectionFields) {

				if (formSectionField.getFieldType() != null
						&& (Constants.FORM_FIELD_TYPE_RICH_TEXT == formSectionField.getFieldType())) {
					resolveRichTextFormSectionField(form, formSpec.getFormSpecId(), formSectionField);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.info(getClass(), "Error in resolveRichTextFormSectionFields ", e);
		}
	}
	
	public static String getCommaSeperatedAndCurencyTypeString(String number, Integer currencyFormat) {
		if (!Api.isEmptyString(number)) {
			NumberFormat n = null;
			String s = "";
			BigDecimal payment = new BigDecimal(number);
			double doublePayment = payment.doubleValue();
			if (currencyFormat == 1 /* || currencyFormat == 4 */ ) {
				
				n = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
				String rs = n.format(payment);
				//s = rs.replaceFirst("[.]", "");
				s = rs;
			} else if (currencyFormat == 2) {
				n = NumberFormat.getCurrencyInstance(new Locale("en", "AU"));
				s = n.format(payment);
			} else if (currencyFormat == 3) {
				n = NumberFormat.getCurrencyInstance(Locale.US);
				s = n.format(payment);
			} else if (currencyFormat == 5) {
				n = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
				String cn = n.format(payment);
				s = formatIndonesiaCurrency(cn);
			} else if(currencyFormat == 4)
			{
				n = NumberFormat.getCurrencyInstance(new Locale("ta", "LK"));
				s = n.format(payment);
				//s = s.replace("රු.", "Rs");
			}
			return s;
		} else {
			return number;
		}

	}

	private static String formatIndonesiaCurrency(String value) {
		if (StringUtils.isNotBlank(value)) {
			value = value.replaceAll("\\.", "\\#");
			value = value.replaceAll("\\,", "\\^");
			return value.replaceAll("\\#", "\\,").replaceAll("\\^", "\\.");
		}
		return null;

	}

	public void CommaSeperatorValueFormSectionFieldsForUI(List<FormSectionField> formSectionFields,
			List<FormSectionSpec> formSectionSpecs) {

		for (FormSectionField formSectionField : formSectionFields) {

			if (formSectionField.getFieldType() == Constants.FORM_FIELD_TYPE_CURRENCY) {

				if (!Api.isNumber(formSectionField.getFieldValue())) {
					continue;
				}

				for (FormSectionSpec formSectionSpec : formSectionSpecs) {
					List<FormSectionFieldSpec> formSectionFieldSpecs = formSectionSpec.getFormSectionFieldSpecs();
					for (FormSectionFieldSpec formSectionFieldSpec : formSectionFieldSpecs) {
						if (formSectionFieldSpec.getSectionFieldSpecId() == formSectionField.getSectionFieldSpecId()) {

							String commaSeperatedAndCurencyTypeString = "";

							if (formSectionFieldSpec.getEnableCommaSeperator() != null && formSectionFieldSpec.getEnableCommaSeperator() == 1 && formSectionFieldSpec.getCurrencyFormat() != null
									&& Integer.parseInt(formSectionFieldSpec.getCurrencyFormat()) != 0) {

								commaSeperatedAndCurencyTypeString = getCommaSeperatedAndCurencyTypeString(
										formSectionField.getFieldValue(),
										Integer.parseInt(formSectionFieldSpec.getCurrencyFormat()));
								formSectionField.setFieldValue(commaSeperatedAndCurencyTypeString);
								formSectionField.setDisplayValue(commaSeperatedAndCurencyTypeString);
							} else if (formSectionFieldSpec.getEnableCommaSeperator() != null && formSectionFieldSpec.getEnableCommaSeperator() == 1
									&& formSectionFieldSpec.getCurrencyFormat() != null && Integer.parseInt(formSectionFieldSpec.getCurrencyFormat()) == 0) {

								commaSeperatedAndCurencyTypeString = Api
										.getCommaSeperatedStringWithOutFormat(formSectionField.getFieldValue());
								formSectionField.setFieldValue(commaSeperatedAndCurencyTypeString);
								formSectionField.setDisplayValue(commaSeperatedAndCurencyTypeString);
							} else {
								formSectionField.setFieldValue(formSectionField.getFieldValue());
								formSectionField.setDisplayValue(formSectionField.getFieldValue());
							}

						}
					}
				}
			}
		}
	}
	public void CommaSeperatedValueFormFieldsForUI(List<FormField> formFields, List<FormFieldSpec> formFieldSpecs) {

		for (FormField formField : formFields) {

			if (formField.getFieldType() == Constants.FORM_FIELD_TYPE_CURRENCY) {

				if (!Api.isNumber(formField.getFieldValue())) {
					continue;
				}

				for (FormFieldSpec formFieldSpec : formFieldSpecs) {
					if (formFieldSpec.getFieldSpecId() == formField.getFieldSpecId()) {

						String commaSeperatedAndCurencyTypeString = "";
						if (formFieldSpec.getEnableCommaSeperator() != null && formFieldSpec.getEnableCommaSeperator() == 1 &&
								formFieldSpec.getCurrencyFormat() != null && Integer.parseInt(formFieldSpec.getCurrencyFormat()) != 0) {

							commaSeperatedAndCurencyTypeString = getCommaSeperatedAndCurencyTypeString(
									formField.getFieldValue(), Integer.parseInt(formFieldSpec.getCurrencyFormat()));
							formField.setFieldValue(commaSeperatedAndCurencyTypeString);
							formField.setDisplayValue(commaSeperatedAndCurencyTypeString);
						} else if (formFieldSpec.getEnableCommaSeperator() != null && formFieldSpec.getEnableCommaSeperator() == 1 &&
								formFieldSpec.getCurrencyFormat() != null && Integer.parseInt(formFieldSpec.getCurrencyFormat()) == 0) {
							commaSeperatedAndCurencyTypeString = Api.getCommaSeperatedStringWithOutFormat(formField.getFieldValue());
							formField.setFieldValue(commaSeperatedAndCurencyTypeString);
							formField.setDisplayValue(commaSeperatedAndCurencyTypeString);

						} else {
							formField.setFieldValue(formField.getFieldValue());
							formField.setDisplayValue(formField.getFieldValue());
						}
					}
				}
			}
		}
	}
	
	
			commaSeperatedAndCurencyTypeString = getCommaSeperatedAndCurencyTypeString(
									formField.getFieldValue(), Integer.parseInt(formFieldSpec.getCurrencyFormat()));
							formField.setFieldValue(commaSeperatedAndCurencyTypeString);
							formField.setDisplayValue(commaSeperatedAndCurencyTypeString);
						} else if (formFieldSpec.getEnableCommaSeperator() != null && formFieldSpec.getEnableCommaSeperator() == 1 &&
								formFieldSpec.getCurrencyFormat() != null && Integer.parseInt(formFieldSpec.getCurrencyFormat()) == 0) {
							commaSeperatedAndCurencyTypeString = Api.getCommaSeperatedStringWithOutFormat(formField.getFieldValue());
							formField.setFieldValue(commaSeperatedAndCurencyTypeString);
							formField.setDisplayValue(commaSeperatedAndCurencyTypeString);

						} else {
							formField.setFieldValue(formField.getFieldValue());
							formField.setDisplayValue(formField.getFieldValue());
						}
					}
				}
			}
		}
	}

	public void resolveRichTextFormField(Form form,long formSpecId,long fieldSpecId,FormField formField){
  		
    	String logText = "resolveRichTextFormField() // formId = "+form.getFormId();
  		try{
  			//1. Checking autoGen config record exists in DB             ==========>
  			RichTextFormField richTextFormField = new RichTextFormField(formSpecId,
  					fieldSpecId,form.getFormId(),formField.getFieldValue());
  			
  			long richTextValueId = getExtraSupportAdditionalSupportDao().insertRichTextFormField(richTextFormField);
  			Log.info(getClass(), logText+" inserted richTextValueId = "+richTextValueId);
  			
  			formField.setFieldValue(richTextValueId+"");
  			formField.setDisplayValue(richTextValueId+"");
  		}catch(Exception e){
  			e.printStackTrace();
  			Log.info(getClass(), logText+" exception occured ",e);
  		}
  	}
	public void resolveRichTextFormFields(Form form,List<FormField> fields,FormSpec formSpec){
  		
	   	   try{
	   		   
	   		  for (FormField formField : fields) {
				
	   			  if(formField.getFieldType() != null && (Constants.FORM_FIELD_TYPE_RICH_TEXT == formField.getFieldType())) {
	   				resolveRichTextFormField(form, formSpec.getFormSpecId(), formField.getFieldSpecId(), formField);
				 }
			  }
	   	   }catch(Exception e){
	   		   e.printStackTrace();
	   		   Log.info(getClass(), "Error in getAutoGenFormField ",e);
	   	   }
	   	}
	

	public Map<Long,List<CustomEntityFilteringCritiria>> getCustomEntityCritiriaMap(
			List<CustomEntityFilteringCritiria> customEntityFilteringCritiria, int type){
		Map<Long, List<CustomEntityFilteringCritiria>> customEntityFilteringCritiriaMap = new HashMap<Long, List<CustomEntityFilteringCritiria>>();
		if(customEntityFilteringCritiria!=null) {
			for(CustomEntityFilteringCritiria customEntityFilteringCritiria2 : customEntityFilteringCritiria){
				if(customEntityFilteringCritiria2.getType() == type) {
					List<CustomEntityFilteringCritiria> customEntityFilteringCritirias = customEntityFilteringCritiriaMap.get(customEntityFilteringCritiria2.getFieldSpecId());
					if(customEntityFilteringCritirias == null) {
						customEntityFilteringCritirias = new ArrayList<CustomEntityFilteringCritiria>();
						customEntityFilteringCritiriaMap.put(customEntityFilteringCritiria2.getFieldSpecId(),customEntityFilteringCritirias);
					}
					customEntityFilteringCritirias.add(customEntityFilteringCritiria2);
				}
			}
		}
		
		return customEntityFilteringCritiriaMap;
	}
	

public WorkOnDemandMapping getWorkOnDemandMappingForGivenFormSpec(String uniqueId, int companyId) {
	return getExtraSupportAdditionalSupportDao().getWorkOnDemandMappingForGivenFormSpec(uniqueId,companyId);
}
	
}
