package com.effort.entity;


import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.effort.util.Api;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormSectionField implements Serializable {
	private static final long serialVersionUID = 1L;

	// private long sectionFieldId;
	private long formId;
	private long formSpecId;
	private long sectionSpecId;
	private long sectionFieldSpecId;
	private int instanceId;
	private String fieldValue;
	private String fieldDisplayValue;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String fieldValueSubstitute;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String clientFormId;
	private int canIgnoreUpdate;
	private String externalId;
	private String fieldLabel;
	// private String clientFieldId;

	private String error;
	private String uniqueId;

	private Integer fieldType;
	private Integer displayOrder;
	private Integer identifier;
	private String displayValue;

	private Long initialFormSectionFieldSpecId;
	private Long skeletonFormSectionFieldSpecId; 
	
	private String backgroundColor;
	
	private String fieldName;
	private String sectionSpecName;
	private long tempSectionFieldId;
	private String fieldValueSignature;
	private int verificationId;
	private int otp;
	private long otpStatus;
	
	private boolean additionalFieldsInfo;
	private String sectionSpecUniqueId;
	
	private boolean ignoreValidation;
	
	private List<Map<String,String>> media;

	

	// public long getSectionFieldId() {
	// return sectionFieldId;
	// }
	// public void setSectionFieldId(long sectionFieldId) {
	// this.sectionFieldId = sectionFieldId;
	// }

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

	public long getFormSpecId() {
		return formSpecId;
	}

	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}

	public long getSectionSpecId() {
		return sectionSpecId;
	}

	public void setSectionSpecId(long sectionSpecId) {
		this.sectionSpecId = sectionSpecId;
	}

	public long getSectionFieldSpecId() {
		return sectionFieldSpecId;
	}

	public void setSectionFieldSpecId(long sectionFieldSpecId) {
		this.sectionFieldSpecId = sectionFieldSpecId;
	}

	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;

	}
	public int getFieldType() {
		return fieldType;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public String getCorrectedFieldValue() {

		if (!Api.isEmptyString(fieldValue)) {
			// String result = this.fieldValue.replaceAll("[\\n]", "");
			String result = fieldValue.replace("\n", " \\n").replace("\r",
					" \\n");
			return result;
		}
		return fieldValue;
	}

	
	public String getFieldValueSubstitute() {
		return fieldValueSubstitute;
	}

	public void setFieldValueSubstitute(String fieldValueSubstitute) {
		this.fieldValueSubstitute = fieldValueSubstitute;
	}

	
	public String getClientFormId() {
		return clientFormId;
	}

	public void setClientFormId(String clientFormId) {
		this.clientFormId = clientFormId;
	}

	// public String getClientFieldId() {
	// return clientFieldId;
	// }
	// public void setClientFieldId(String clientFieldId) {
	// this.clientFieldId = clientFieldId;
	// }

	public String getFieldDisplayValue() {
		return fieldDisplayValue;
	}

	public void setFieldDisplayValue(String fieldDisplayValue) {
		this.fieldDisplayValue = fieldDisplayValue;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	@JsonIgnore
	public String getError() {
		return error;
	}

	@JsonIgnore
	public void setError(String error) {
		this.error = error;
	}



	public int getCanIgnoreUpdate() {
		return canIgnoreUpdate;
	}

	public void setCanIgnoreUpdate(int canIgnoreUpdate) {
		this.canIgnoreUpdate = canIgnoreUpdate;
	}

	public String toCSV() {
		return "[formId=" + formId + ", formSpecId=" + formSpecId
				+ ", sectionFieldSpecId=" + sectionFieldSpecId
				+ ", fieldValue=" + fieldValue + ", fieldValueSubstitute="
				+ fieldValueSubstitute + ", clientFormId=" + clientFormId
				+ ", error=" + error + "]";
	}

	/*@Override
	public String toString() {
		return toCSV();
	}
*/
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public Long getInitialFormSectionFieldSpecId() {
		return initialFormSectionFieldSpecId;
	}

	public void setInitialFormSectionFieldSpecId(Long initialFormSectionFieldSpecId) {
		this.initialFormSectionFieldSpecId = initialFormSectionFieldSpecId;
	}

	public Long getSkeletonFormSectionFieldSpecId() {
		return skeletonFormSectionFieldSpecId;
	}

	public void setSkeletonFormSectionFieldSpecId(
			Long skeletonFormSectionFieldSpecId) {
		this.skeletonFormSectionFieldSpecId = skeletonFormSectionFieldSpecId;
	}

	@Override
	public String toString() {
		return "FormSectionField [formId=" + formId + ", formSpecId="
				+ formSpecId + ", sectionSpecId=" + sectionSpecId
				+ ", sectionFieldSpecId=" + sectionFieldSpecId
				+ ", instanceId=" + instanceId + ", fieldValue=" + fieldValue
				+ ", fieldDisplayValue=" + fieldDisplayValue
				+ ", fieldValueSubstitute=" + fieldValueSubstitute
				+ ", clientFormId=" + clientFormId + ", canIgnoreUpdate="
				+ canIgnoreUpdate + ", externalId=" + externalId
				+ ", fieldLabel=" + fieldLabel + ", error=" + error
				+ ", uniqueId=" + uniqueId + ", fieldType=" + fieldType
				+ ", displayOrder=" + displayOrder + ", identifier="
				+ identifier + ", displayValue=" + displayValue
				+ ", initialFormSectionFieldSpecId="
				+ initialFormSectionFieldSpecId
				+ ", skeletonFormSectionFieldSpecId="
				+ skeletonFormSectionFieldSpecId + "]";
	}
	@JsonIgnore
	public String getBackgroundColor() {
		return backgroundColor;
	}
	@JsonIgnore
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	@JsonIgnore
	public String getFieldName() {
		return fieldName;
	}

	@JsonIgnore
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@JsonIgnore
	public String getSectionSpecName() {
		return sectionSpecName;
	}

	@JsonIgnore
	public void setSectionSpecName(String sectionSpecName) {
		this.sectionSpecName = sectionSpecName;
	}
	public long getTempSectionFieldId() {
		return tempSectionFieldId;
	}

	public void setTempSectionFieldId(long tempSectionFieldId) {
		this.tempSectionFieldId = tempSectionFieldId;
	}

	public String getFieldValueSignature() {
		return fieldValueSignature;
	}

	public void setFieldValueSignature(String fieldValueSignature) {
		this.fieldValueSignature = fieldValueSignature;
	}
	public int getVerificationId() {
		return verificationId;
	}

	public void setVerificationId(int verificationId) {
		this.verificationId = verificationId;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}
	
	public long getOtpStatus() {
		return otpStatus;
	}

	public void setOtpStatus(long otpStatus) {
		this.otpStatus = otpStatus;
	}
	
	public boolean isAdditionalFieldsInfo() {
		return additionalFieldsInfo;
	}

	public void setAdditionalFieldsInfo(boolean additionalFieldsInfo) {
		this.additionalFieldsInfo = additionalFieldsInfo;
	}

	public String getSectionSpecUniqueId() {
		return sectionSpecUniqueId;
	}

	public void setSectionSpecUniqueId(String sectionSpecUniqueId) {
		this.sectionSpecUniqueId = sectionSpecUniqueId;
	}
	
	/*public static class FormSectionFieldSort implements Comparator<FormSectionField> {
		 
	    // Method of this class
	    // @Override
	    public int compare(FormSectionField formSectionField1, FormSectionField formSectionField2)
	    {
	    	
	        // Returning the value after comparing the objects
	        // this will sort the data in Ascending order
	    	long diff = formSectionField1.getSectionSpecId().getWorkStatusHistoryId()-o2.getWorkStatusHistoryId();
			return Integer.parseInt(diff+"");
	        return (formSectionField1.getSectionSpecId()) > (formSectionField2.getSectionSpecId());
	    }

		@Override
		public int compare(UserHistory arg0, UserHistory arg1) {
			// TODO Auto-generated method stub
			return 0;
		}
	}*/
	
	
	public void sortFormSectionFieldBySectionSpecId(List<FormSectionField> formSectionFields) {
		
		if(formSectionFields!=null && !formSectionFields.isEmpty()){
			Collections.sort(formSectionFields, new Comparator<FormSectionField>() {
				@Override
				public int compare(FormSectionField o1, FormSectionField o2) {
					long diff = o1.getSectionSpecId()-o2.getSectionSpecId();
					return Integer.parseInt(diff+"");
				}
			});
		}
	}

	public boolean isIgnoreValidation() {
		return ignoreValidation;
	}

	public void setIgnoreValidation(boolean ignoreValidation) {
		this.ignoreValidation = ignoreValidation;
	}

	public List<Map<String, String>> getMedia() {
		return media;
	}

	public void setMedia(List<Map<String, String>> media) {
		this.media = media;
	}

	
	
	
}
