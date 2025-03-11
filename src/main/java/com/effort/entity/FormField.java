package com.effort.entity;



import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.effort.util.Api;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormField implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;

	private long fieldId;
	private long formId;
	private long formSpecId;
	
	private String fieldValue;
	private long fieldSpecId;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String fieldValueSubstitute;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String clientFormId;
	private int canIgnoreUpdate;
	private String fieldDisplayValue;
	private String externalId;
	private String fieldLabel;
	private String uniqueId;
	private int identifier;
	private int verificationId;
	private int otp;
	private long otpStatus;
	private Double longitude;
	private Double lattitude;

	private Integer formFieldType;
	private String columnName;
	private String fieldSelector;
	private Long fieldTypeExtra;
	private Integer customerFieldType;
	private Long fieldTypeExtraCustomEntity;
	public static final int USER_DEFINED_FIELD=1;
	// private String clientFieldId;

	private String error;

	private String displayValue;

	private Integer fieldType;
	private Integer displayOrder;

	private Long initialFormFieldSpecId;
	private Long skeletonFormFieldSpecId;
	private Integer isVisible;
	
	private String fieldName;
	private String groupExpression;
	
	private String backgroundColor;
	
	private String expression;
	private String externalLabel;
	
	private String fieldValueSignature;

	private String workActionSpecId;
	private Integer mandatoryCheck;
	
	private boolean additionalFieldsInfo;
	private long empId;
	
	private String totalValue;
	private String currencyCountValue;
	private String stateTypeFieldValue;
	private String zoneFieldValue;
	private boolean ticketingField;


	
	private boolean computedField;
	private boolean locationUpdate;
	private boolean fromMigrationFields;
	private Long migrationFilledBy;
	private Long migrationModifiedBy;
	private String migrationCreatedTime;
	private String migrationModifiedTime;
	private boolean skipDateTimeFields;
	
	private List<Map<String,String>> media;

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public long getFieldId() {
		return fieldId;
	}

	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
	}
	
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

	public long getFieldSpecId() {
		return fieldSpecId;
	}

	public void setFieldSpecId(long fieldSpecId) {
		this.fieldSpecId = fieldSpecId;
	}

	public String getFieldValue() {

		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public String getCorrectedFieldValue() {
		if (!Api.isEmptyString(fieldValue)) {
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

	@JsonIgnore
	public String getError() {
		return error;
	}

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
	public void setError(String error) {
		this.error = error;
	}


	/*public String toCSV() {
		return "[formId=" + formId + ", formSpecId=" + formSpecId
				+ ", fieldSpecId=" + fieldSpecId + ", fieldValue=" + fieldValue
				+ ", clientFormId=" + clientFormId + "]";
	}*/
	
	

	public String toCSV() {
		return "FormField [formId=" + formId + ", formSpecId=" + formSpecId
				+ ", fieldSpecId=" + fieldSpecId + ", fieldValue=" + fieldValue
				+ ", fieldValueSubstitute=" + fieldValueSubstitute
				+ ", clientFormId=" + clientFormId + ", canIgnoreUpdate="
				+ canIgnoreUpdate + ", fieldDisplayValue=" + fieldDisplayValue
				+ ", externalId=" + externalId + ", fieldLabel=" + fieldLabel
				+ ", uniqueId=" + uniqueId + ", identifier=" + identifier
				+ ", error=" + error + ", displayValue=" + displayValue
				+ ", fieldType=" + fieldType + ", displayOrder=" + displayOrder
				+ ", initialFormFieldSpecId=" + initialFormFieldSpecId
				+ ", skeletonFormFieldSpecId=" + skeletonFormFieldSpecId
				+ ", isVisible=" + isVisible + "]";
	}

	public int getCanIgnoreUpdate() {
		return canIgnoreUpdate;
	}

	public void setCanIgnoreUpdate(int canIgnoreUpdate) {
		this.canIgnoreUpdate = canIgnoreUpdate;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}

	public Long getInitialFormFieldSpecId() {
		return initialFormFieldSpecId;
	}

	public void setInitialFormFieldSpecId(Long initialFormFieldSpecId) {
		this.initialFormFieldSpecId = initialFormFieldSpecId;
	}

	public Long getSkeletonFormFieldSpecId() {
		return skeletonFormFieldSpecId;
	}

	public void setSkeletonFormFieldSpecId(Long skeletonFormFieldSpecId) {
		this.skeletonFormFieldSpecId = skeletonFormFieldSpecId;
	}
	
	public Integer getIsVisible() {
		return isVisible;
	}
	
	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}
	
	
	

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FormField) {
			return (getFormId() == ((FormField) obj).getFormId() && getFieldSpecId() == ((FormField) obj)
					.getFieldSpecId());
		}else {
			return super.equals(obj);
		}
	}

	
	public String getGroupExpression() {
		return groupExpression;
	}

	public void setGroupExpression(String groupExpression) {
		this.groupExpression = groupExpression;
	}

	@Override
	public String toString() {
		return "FormField [formId=" + formId + ", formSpecId=" + formSpecId
				+ ", fieldSpecId=" + fieldSpecId + ", fieldValue=" + fieldValue
				+ ", fieldValueSubstitute=" + fieldValueSubstitute
				+ ", clientFormId=" + clientFormId + ", canIgnoreUpdate="
				+ canIgnoreUpdate + ", fieldDisplayValue=" + fieldDisplayValue
				+ ", externalId=" + externalId + ", fieldLabel=" + fieldLabel
				+ ", uniqueId=" + uniqueId + ", identifier=" + identifier
				+ ", error=" + error + ", displayValue=" + displayValue
				+ ", fieldType=" + fieldType + ", displayOrder=" + displayOrder
				+ ", initialFormFieldSpecId=" + initialFormFieldSpecId
				+ ", skeletonFormFieldSpecId=" + skeletonFormFieldSpecId
				+ ", isVisible=" + isVisible + ", fieldName=" + fieldName + "]";
	}
	@JsonIgnore
	public String getBackgroundColor() {
		return backgroundColor;
	}
	@JsonIgnore
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getExternalLabel() {
		return externalLabel;
	}

	public void setExternalLabel(String externalLabel) {
		this.externalLabel = externalLabel;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
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

	public Double getLattitude() {
		return lattitude;
	}

	public void setLattitude(Double lattitude) {
		this.lattitude = lattitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getWorkActionSpecId() {
		return workActionSpecId;
	}

	public void setWorkActionSpecId(String workActionSpecId) {
		this.workActionSpecId = workActionSpecId;
	}
	
	public Integer getFormFieldType() {
		return formFieldType;
	}

	public void setFormFieldType(Integer formFieldType) {
		this.formFieldType = formFieldType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getFieldSelector() {
		return fieldSelector;
	}
	public void setFieldSelector(String fieldSelector) {
		this.fieldSelector = fieldSelector;
	}
	
	@JsonIgnore
	public Long getFieldTypeExtra() {
		return fieldTypeExtra;
	}
	@JsonIgnore
	public void setFieldTypeExtra(Long fieldTypeExtra) {
		this.fieldTypeExtra = fieldTypeExtra;
	}
	@JsonIgnore
	public Integer getCustomerFieldType() {
		return customerFieldType;
	}
	@JsonIgnore
	public void setCustomerFieldType(Integer customerFieldType) {
		this.customerFieldType = customerFieldType;
	}
	public Integer getMandatoryCheck() {
		return mandatoryCheck;
	}
	public void setMandatoryCheck(Integer mandatoryCheck) {
		this.mandatoryCheck = mandatoryCheck;
	}


	public boolean isAdditionalFieldsInfo() {
		return additionalFieldsInfo;
	}

	public void setAdditionalFieldsInfo(boolean additionalFieldsInfo) {
		this.additionalFieldsInfo = additionalFieldsInfo;
	}

	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	public String getCurrencyCountValue() {
		return currencyCountValue;
	}

	public void setCurrencyCountValue(String currencyCountValue) {
		this.currencyCountValue = currencyCountValue;
	}

	public String getStateTypeFieldValue() {
		return stateTypeFieldValue;
	}

	public void setStateTypeFieldValue(String stateTypeFieldValue) {
		this.stateTypeFieldValue = stateTypeFieldValue;
	}

	public String getZoneFieldValue() {
		return zoneFieldValue;
	}

	public void setZoneFieldValue(String zoneFieldValue) {
		this.zoneFieldValue = zoneFieldValue;
	}

	public boolean isComputedField() {
		return computedField;
	}

	public void setComputedField(boolean computedField) {
		this.computedField = computedField;
	}

	public boolean isLocationUpdate() {
		return locationUpdate;
	}

	public void setLocationUpdate(boolean locationUpdate) {
		this.locationUpdate = locationUpdate;
	}

	public boolean isTicketingField() {
		return ticketingField;
	}

	public void setTicketingField(boolean ticketingField) {
		this.ticketingField = ticketingField;
	}
	
	
	public boolean isFromMigrationFields() {
		return fromMigrationFields;
	}
	public void setFromMigrationFields(boolean fromMigrationFields) {
		this.fromMigrationFields = fromMigrationFields;
	}
	public Long getMigrationFilledBy() {
		return migrationFilledBy;
	}

	public void setMigrationFilledBy(Long migrationFilledBy) {
		this.migrationFilledBy = migrationFilledBy;
	}

	public Long getMigrationModifiedBy() {
		return migrationModifiedBy;
	}

	public void setMigrationModifiedBy(Long migrationModifiedBy) {
		this.migrationModifiedBy = migrationModifiedBy;
	}

	public String getMigrationCreatedTime() {
		return migrationCreatedTime;
	}

	public void setMigrationCreatedTime(String migrationCreatedTime) {
		this.migrationCreatedTime = migrationCreatedTime;
	}

	public String getMigrationModifiedTime() {
		return migrationModifiedTime;
	}

	public void setMigrationModifiedTime(String migrationModifiedTime) {
		this.migrationModifiedTime = migrationModifiedTime;
	}

	public Long getFieldTypeExtraCustomEntity() {
		return fieldTypeExtraCustomEntity;
	}

	public void setFieldTypeExtraCustomEntity(Long fieldTypeExtraCustomEntity) {
		this.fieldTypeExtraCustomEntity = fieldTypeExtraCustomEntity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static int getUserDefinedField() {
		return USER_DEFINED_FIELD;
	}

	public boolean isSkipDateTimeFields() {
		return skipDateTimeFields;
	}

	public void setSkipDateTimeFields(boolean skipDateTimeFields) {
		this.skipDateTimeFields = skipDateTimeFields;
	}

	public List<Map<String, String>> getMedia() {
		return media;
	}

	public void setMedia(List<Map<String, String>> media) {
		this.media = media;
	}

	public long getFieldSpecId() {
		return fieldSpecId;
	}

	public void setFieldSpecId(long fieldSpecId) {
		this.fieldSpecId = fieldSpecId;
	}
	
	
	
	
	
}
