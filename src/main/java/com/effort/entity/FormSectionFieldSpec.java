package com.effort.entity;



import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormSectionFieldSpec {
	public final static int COMPUTED_FIELD_TYPE_COMPUTED=1;
	public final static int COMPUTED_FIELD_TYPE_DEFAULT=2;
	public final static int COMPUTED_FIELD_TYPE_NONE=0;
	
	private long sectionFieldSpecId;
	private String uniqueId;
	private long formSpecId;
	private long sectionSpecId;
	private String fieldLabel;
	private int fieldType;
	private String fieldTypeExtra;
	private boolean computedField;
	private boolean defaultField;
	private boolean barcodeField;
	private String formula;
	private boolean isRequired;
	private int displayOrder;
	private String expression;
	private boolean identifier;
	private int computedFieldType;
	
	private int type;
	private String value;
	
	private int index;
	private String fieldLabelError;
	private String fieldTypeError;
	private String computedFieldError;
	private String barcodeFieldError;
	private String isRequiredError;
	private String functionFieldError;
	
	// extra properties
	private String fieldVisbleRestrictedEmpGrps;
	private String fieldEditableRestrictedEmpGrps;
	private String min;
	private String max;
	private String minErrorValue;
	private String maxErrorValue;
	private int visible = 1;
	private int editable = 1;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String listFilteringCritiria;// this json for ListFilteringCritiria
	

	@JsonProperty(access = Access.WRITE_ONLY)
	private String visibilityDependencyCriteria;// this is json for
												// ListFilteringCritiria
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private int visibleOnVisibilityCondition = 1;
	private Long initialFormSectionFieldSpecId;
	private Long skeletonFormSectionFieldSpecId;
	
	private int isRemoteField = 0;
	private String isRemoteFieldError;
	private String fieldTypeExtraForm;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String customerFilteringCritiria;// this json for CustomerFilteringCritiria
	
	
	
	private String backgroundColorDependencyCritiria;
	
	private String externalLabel;
	private String signatureFieldTermsAndConditions;
	
	private Integer mediaPickCondition;
	
	private Integer locationPickCondition = 0;
	
	private Integer radioButtonCondition = 0;
	private String radioButtonDefaultValue;
	
	private String validationExpr;
	private String validationErrorMsg;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String fieldValidationCritiria;// this json for FieldValidationCritiria

	//private boolean imageSizeEnabled = false;
		/*Date: 2016-06-20
		*Change Purpose:Saving imagesize and enable imageSize values also(Image max size restriction )
		*Resource: Deva*/
	private Boolean imageSizeEnabled = false;  //we made this Boolean(wrapper) to handle null also  
	private Integer imageSize;
	
	
	private Integer empGroupVisibleType = 1; //default invisible
	private Integer empGroupEditableType = 2; //default un-editable
	
	
	private Integer enableSpinnerCondition;
	
	private Integer decimalValueLimit;
	
	private String empRoleFilterValues;
	
	private String modifiedTime;
	
	private Integer textFieldMultiLineValue;
	
	private String aliasExpression;
	
	private String formFilteringCritiria;

	
	private int valuesFilter = 0; 
	
	private Integer otpExpiryTimeInSeconds;
	private Integer noOfOtpDigits;
	private Integer updateFormAsProcessed =0;
	private Integer restrictToCaps = 0;
	private Integer pickDiffCustomer = 0;
	private boolean dependentComputedField;
	private boolean dependentDefaultField;
	
	private String fieldTypeExtraCustomEntity;
	
	private Integer guidField = 0;
	
	private String employeeFilteringCritiria;// this json for EmployeeFilteringCritiria

	
	private Long fieldLabelFontId;
	private Long fieldValueFontId;
	private Long formFieldEntityId;
	private String pickEmployeesFromGroupIds;
	
	private String entityFilterFunctionFieldsMapping;// this json for EmployeeFilteringCritiria

	
	private boolean functionField;
	private String functionName;
	private Integer readDataFrom;
	
	private boolean restrictDataFromMobile;
	private Integer timeStepValue;	
	
	private String showHelpText;
	private int formFieldVisibility;
	private String customEntityFilteringCritiria;
	
	private Integer enableCommaSeperator = 0;
	private String currencyFormat;
	private Integer enablePickerAndDropdown;
	private String numberToWordCurrencyType;
	private Integer enableMediaDelete;
	private Integer sendActionAssignmentsOfEmpIdsOfGroupRestrictions = 0;

	private int verificationId;
	private int otp;
	private int fieldValue;
	
	private String fontColor;
	private String fontSize;
	private boolean bold;
	private boolean italic;
	private boolean underLine;
	private Integer enableUserInputsForTextFieldFormula=0;
	
	private String customFieldLabel;
	
	private Integer maxNumberOfFilesAllowed = 0;
	
	private String employeeListBasedFilteringCritiria;// this json for EmployeeFilteringCritiria

	
	private Integer restrictLocationPickCondition = 0;
	//private boolean visible = true;
	private boolean visibleForCreation = true;
	private boolean searchableField = true;
	private Integer enableFrontCameraInMobile=0;
	
	private boolean findDistanceByGoogleApi;
	private Integer metricUnitsOfDistance = 2;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String childCustomerFilteringCritiria;
	
	
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String customerAutoFilteringCritiria;// this json for CustomerAutoFilteringCritiria
	
	
	private Integer showOnlyMappedItemsWhileSelection = 0;
	private Integer enableLaunchChatAppForMobile;
	
	private boolean enableAgeRestriction;
	private Integer minAge;
	private Integer maxAge;
	private String ageRestrictionErrorMessage;
	
	private Integer enableMediaFormatRestriction = 0;
	private String allowRequiredFormat;
	private Integer enableMappedTerritoriesRestriction = 0;
	private Integer radioButtonOrientation;
	private Integer includeEndDate;
	private Integer reminderConfigEnabled;
	private String remainderRemarksFields;
	private Integer showRemainderBefore;


	public boolean isRestrictDataFromMobile() {
		return restrictDataFromMobile;
	}
	public void setRestrictDataFromMobile(boolean restrictDataFromMobile) {
		this.restrictDataFromMobile = restrictDataFromMobile;
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
	public long getSectionFieldSpecId() {
		return sectionFieldSpecId;
	}
	public void setSectionFieldSpecId(long sectionFieldSpecId) {
		this.sectionFieldSpecId = sectionFieldSpecId;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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
	public String getFieldLabel() {
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public int getFieldType() {
		return fieldType;
	}
	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldTypeExtra() {
		return fieldTypeExtra;
	}
	public void setFieldTypeExtra(String fieldTypeExtra) {
		this.fieldTypeExtra = fieldTypeExtra;
	}
	public boolean isComputedField() {
		return computedField;
	}
	public void setComputedField(boolean computedField) {
		this.computedField = computedField;
	}
	public boolean isDefaultField() {
		return defaultField;
	}
	public void setDefaultField(boolean defaultField) {
		this.defaultField = defaultField;
	}
	public int getComputedFieldType() {
		return computedFieldType;
	}
	public void setComputedFieldType(int computedFieldType) {
		this.computedFieldType = computedFieldType;
	}
	public boolean isBarcodeField() {
		return barcodeField;
	}
	public void setBarcodeField(boolean barcodeField) {
		this.barcodeField = barcodeField;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public boolean isRequired() {
		return isRequired;
	}
	public void setIsRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	@JsonProperty("fieldSelector")
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public boolean isIdentifier() {
		return identifier;
	}
	public void setIdentifier(boolean identifier) {
		this.identifier = identifier;
	}

	public String getFieldVisbleRestrictedEmpGrps() {
		return fieldVisbleRestrictedEmpGrps;
	}

	public void setFieldVisbleRestrictedEmpGrps(
			String fieldVisbleRestrictedEmpGrps) {
		this.fieldVisbleRestrictedEmpGrps = fieldVisbleRestrictedEmpGrps;
	}

	public String getFieldEditableRestrictedEmpGrps() {
		return fieldEditableRestrictedEmpGrps;
	}

	public void setFieldEditableRestrictedEmpGrps(
			String fieldEditableRestrictedEmpGrps) {
		this.fieldEditableRestrictedEmpGrps = fieldEditableRestrictedEmpGrps;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	
	public String getListFilteringCritiria() {
		return listFilteringCritiria;
	}

	public void setListFilteringCritiria(String listFilteringCritiria) {
		this.listFilteringCritiria = listFilteringCritiria;
	}

	

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	public int getEditable() {
		return editable;
	}

	public void setEditable(int editable) {
		this.editable = editable;
	}

	
	public int getVisibleOnVisibilityCondition() {
		return visibleOnVisibilityCondition;
	}

	public void setVisibleOnVisibilityCondition(int visibleOnVisibilityCondition) {
		this.visibleOnVisibilityCondition = visibleOnVisibilityCondition;
	}

	
	public String getVisibilityDependencyCriteria() {
		return visibilityDependencyCriteria;
	}

	public void setVisibilityDependencyCriteria(
			String visibilityDependencyCriteria) {
		this.visibilityDependencyCriteria = visibilityDependencyCriteria;
	}

	
	

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@JsonIgnore
	public String getValue() {
		return value;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getEncodedValue() {
		return value == null ? "" : value.replace("\r", "").replace("\n", "\\n");
	}
	@JsonIgnore
	public void setValue(String value) {
		this.value = value;
	}
	@JsonIgnore
	public int getIndex() {
		return index;
	}
	@JsonIgnore
	public void setIndex(int index) {
		this.index = index;
	}
	@JsonIgnore
	public String getFieldLabelError() {
		return fieldLabelError;
	}
	@JsonIgnore
	public void setFieldLabelError(String fieldLabelError) {
		this.fieldLabelError = fieldLabelError;
	}
	@JsonIgnore
	public String getFieldTypeError() {
		return fieldTypeError;
	}
	@JsonIgnore
	public void setFieldTypeError(String fieldTypeError) {
		this.fieldTypeError = fieldTypeError;
	}
	@JsonIgnore
	public String getComputedFieldError() {
		return computedFieldError;
	}
	@JsonIgnore
	public void setComputedFieldError(String computedFieldError) {
		this.computedFieldError = computedFieldError;
	}
	@JsonIgnore
	public String getBarcodeFieldError() {
		return barcodeFieldError;
	}
	@JsonIgnore
	public void setBarcodeFieldError(String barcodeFieldError) {
		this.barcodeFieldError = barcodeFieldError;
	}
	@JsonIgnore
	public String getIsRequiredError() {
		return isRequiredError;
	}
	@JsonIgnore
	public void setIsRequiredError(String isRequiredError) {
		this.isRequiredError = isRequiredError;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FormSectionField){
			return getSectionFieldSpecId() == ((FormSectionField) obj).getSectionFieldSpecId();
		} else {
			return super.equals(obj);
		}
	}
	
	public String toCSV() {
		return "[sectionFieldSpecId=" + sectionFieldSpecId + ", uniqueId=" + uniqueId + ", formSpecId=" + formSpecId + ", sectionSpecId=" + sectionSpecId + ", fieldLabel=" + fieldLabel + ", fieldType=" + fieldType + ", fieldTypeExtra=" + fieldTypeExtra + ", computedField="
				+ computedField + ", formula=" + formula + ", isRequired=" + isRequired + ", displayOrder=" + displayOrder + ", expression=" + expression + ", identifier=" + identifier + ", type=" + type + ", value=" + value + ", index=" + index + ", fieldLabelError=" + fieldLabelError
				+ ", fieldTypeError=" + fieldTypeError + ", computedFieldError=" + computedFieldError +
				", isRequiredError=" + isRequiredError + ", fieldTypeExtraForm="+ fieldTypeExtraForm +
				", customerFilteringCritiria = "+customerFilteringCritiria+
				", employeeFilteringCritiria = "+employeeFilteringCritiria+"]";
	}
	public int getIsRemoteField() {
		return isRemoteField;
	}
	public void setIsRemoteField(int isRemoteField) {
		this.isRemoteField = isRemoteField;
	}
	public String getIsRemoteFieldError() {
		return isRemoteFieldError;
	}
	public void setIsRemoteFieldError(String isRemoteFieldError) {
		this.isRemoteFieldError = isRemoteFieldError;
	}

	
	public String getCustomerFilteringCritiria() {
		return customerFilteringCritiria;
	}

	public void setCustomerFilteringCritiria(String customerFilteringCritiria) {
		this.customerFilteringCritiria = customerFilteringCritiria;
	}


	public String getFieldTypeExtraForm() {
		return fieldTypeExtraForm;
	}
	public void setFieldTypeExtraForm(String fieldTypeExtraForm) {
		this.fieldTypeExtraForm = fieldTypeExtraForm;
	}
	public String getExternalLabel() {
		return externalLabel;
	}
	public void setExternalLabel(String externalLabel) {
		this.externalLabel = externalLabel;
	}
	public String getSignatureFieldTermsAndConditions() {
		return signatureFieldTermsAndConditions;
	}
	public void setSignatureFieldTermsAndConditions(
			String signatureFieldTermsAndConditions) {
		this.signatureFieldTermsAndConditions = signatureFieldTermsAndConditions;
	}
	public Integer getMediaPickCondition() {
		return mediaPickCondition;
	}
	public void setMediaPickCondition(Integer mediaPickCondition) {
		this.mediaPickCondition = mediaPickCondition;
	}
	public Integer getLocationPickCondition() {
		return locationPickCondition;
	}
	public void setLocationPickCondition(Integer locationPickCondition) {
		this.locationPickCondition = locationPickCondition;
	}
	public String getValidationExpr() {
		return validationExpr;
	}
	public void setValidationExpr(String validationExpr) {
		this.validationExpr = validationExpr;
	}
	public String getValidationErrorMsg() {
		return validationErrorMsg;
	}
	public void setValidationErrorMsg(String validationErrorMsg) {
		this.validationErrorMsg = validationErrorMsg;
	}
	
	
	public String getFieldValidationCritiria() {
		return fieldValidationCritiria;
	}

	public void setFieldValidationCritiria(String fieldValidationCritiria) {
		this.fieldValidationCritiria = fieldValidationCritiria;
	}

	
	
	public void setImageSizeEnabled(Boolean imageSizeEnabled) {
		if(imageSizeEnabled == null)
			imageSizeEnabled = false;
		this.imageSizeEnabled = imageSizeEnabled;
	}
	public Boolean getImageSizeEnabled() {
		if(imageSizeEnabled == null)
			return false;
		return imageSizeEnabled;
	}
	public Integer getImageSize() {
		return imageSize;
	}
	public void setImageSize(Integer imageSize) {
		this.imageSize = imageSize;
	}
	public Integer getEmpGroupVisibleType() {
		return empGroupVisibleType;
	}
	public void setEmpGroupVisibleType(Integer empGroupVisibleType) {
		this.empGroupVisibleType = empGroupVisibleType;
	}
	public Integer getEmpGroupEditableType() {
		return empGroupEditableType;
	}
	public void setEmpGroupEditableType(Integer empGroupEditableType) {
		this.empGroupEditableType = empGroupEditableType;
	}
	public Integer getRadioButtonCondition() {
		return radioButtonCondition;
	}
	public void setRadioButtonCondition(Integer radioButtonCondition) {
		this.radioButtonCondition = radioButtonCondition;
	}
	public String getRadioButtonDefaultValue() {
		return radioButtonDefaultValue;
	}
	public void setRadioButtonDefaultValue(String radioButtonDefaultValue) {
		this.radioButtonDefaultValue = radioButtonDefaultValue;
	}
	public String getMinErrorValue() {
		return minErrorValue;
	}
	public void setMinErrorValue(String minErrorValue) {
		this.minErrorValue = minErrorValue;
	}
	public String getMaxErrorValue() {
		return maxErrorValue;
	}
	public void setMaxErrorValue(String maxErrorValue) {
		this.maxErrorValue = maxErrorValue;
	}
	public Integer getEnableSpinnerCondition() {
		return enableSpinnerCondition;
	}

	public void setEnableSpinnerCondition(Integer enableSpinnerCondition) {
		this.enableSpinnerCondition = enableSpinnerCondition;
	}
	public String getEmpRoleFilterValues() {
		return empRoleFilterValues;
	}
	public void setEmpRoleFilterValues(String empRoleFilterValues) {
		this.empRoleFilterValues = empRoleFilterValues;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Integer getDecimalValueLimit() {
		return decimalValueLimit;
	}
	public void setDecimalValueLimit(Integer decimalValueLimit) {
		this.decimalValueLimit = decimalValueLimit;
	}
	public Integer getTextFieldMultiLineValue() {
		return textFieldMultiLineValue;
	}
	public void setTextFieldMultiLineValue(Integer textFieldMultiLineValue) {
		this.textFieldMultiLineValue = textFieldMultiLineValue;
	}
	
	public String getAliasExpression(){
		return aliasExpression;
	}
	public void setAliasExpression(String aliasExpression) {
		this.aliasExpression = aliasExpression;
	}
	public String getBackgroundColorDependencyCritiria() {
		return backgroundColorDependencyCritiria;
	}
	public void setBackgroundColorDependencyCritiria(
			String backgroundColorDependencyCritiria) {
		this.backgroundColorDependencyCritiria = backgroundColorDependencyCritiria;
	}
	public String getFormFilteringCritiria() {
		return formFilteringCritiria;
	}
	public void setFormFilteringCritiria(String formFilteringCritiria) {
		this.formFilteringCritiria = formFilteringCritiria;
	}
	
	public int getValuesFilter() {
		return valuesFilter;
	}
	public void setValuesFilter(int valuesFilter) {
		this.valuesFilter = valuesFilter;
	}
	public Integer getOtpExpiryTimeInSeconds() {
		return otpExpiryTimeInSeconds;
	}
	public void setOtpExpiryTimeInSeconds(Integer otpExpiryTimeInSeconds) {
		this.otpExpiryTimeInSeconds = otpExpiryTimeInSeconds;
	}
	
	public Integer getNoOfOtpDigits() {
		return noOfOtpDigits;
	}

	public void setNoOfOtpDigits(Integer noOfOtpDigits) {
		this.noOfOtpDigits = noOfOtpDigits;
	}
	public Integer getUpdateFormAsProcessed() {
		return updateFormAsProcessed;
	}

	public void setUpdateFormAsProcessed(Integer updateFormAsProcessed) {
		this.updateFormAsProcessed = updateFormAsProcessed;
	}
	public Integer getRestrictToCaps() {
		return restrictToCaps;
	}

	public void setRestrictToCaps(Integer restrictToCaps) {
		this.restrictToCaps = restrictToCaps;
	}
	public Integer getPickDiffCustomer() {
		return pickDiffCustomer;
	}

	public void setPickDiffCustomer(Integer pickDiffCustomer) {
		this.pickDiffCustomer = pickDiffCustomer;
	}
	public String getFieldTypeExtraCustomEntity() {
		return fieldTypeExtraCustomEntity;
	}
	public void setFieldTypeExtraCustomEntity(String fieldTypeExtraCustomEntity) {
		this.fieldTypeExtraCustomEntity = fieldTypeExtraCustomEntity;
	}
	public boolean isDependentComputedField() {
		return dependentComputedField;
	}

	public void setDependentComputedField(boolean dependentComputedField) {
		this.dependentComputedField = dependentComputedField;
	}
	public boolean isDependentDefaultField() {
		return dependentDefaultField;
	}
	public void setDependentDefaultField(boolean dependentDefaultField) {
		this.dependentDefaultField = dependentDefaultField;
	}
	public String getEmployeeFilteringCritiria() {
		return employeeFilteringCritiria;
	}
	public void setEmployeeFilteringCritiria(String employeeFilteringCritiria) {
		this.employeeFilteringCritiria = employeeFilteringCritiria;
	}
	
	public Long getFieldLabelFontId() {
		return fieldLabelFontId;
	}
	public void setFieldLabelFontId(Long fieldLabelFontId) {
		this.fieldLabelFontId = fieldLabelFontId;
	}
	public Long getFieldValueFontId() {
		return fieldValueFontId;
	}
	public void setFieldValueFontId(Long fieldValueFontId) {
		this.fieldValueFontId = fieldValueFontId;
	}
	public String getPickEmployeesFromGroupIds() {
		return pickEmployeesFromGroupIds;
	}
	public void setPickEmployeesFromGroupIds(String pickEmployeesFromGroupIds) {
		this.pickEmployeesFromGroupIds = pickEmployeesFromGroupIds;
	}
	public Integer getGuidField() {
		return guidField;
	}
	public void setGuidField(Integer guidField) {
		this.guidField = guidField;
	}
	public boolean isFunctionField() {
		return functionField;
	}
	public void setFunctionField(boolean functionField) {
		this.functionField = functionField;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getEntityFilterFunctionFieldsMapping() {
		return entityFilterFunctionFieldsMapping;
	}
	public void setEntityFilterFunctionFieldsMapping(String entityFilterFunctionFieldsMapping) {
		this.entityFilterFunctionFieldsMapping = entityFilterFunctionFieldsMapping;
	}

	public String getFunctionFieldError() {
		return functionFieldError;
	}
	public void setFunctionFieldError(String functionFieldError) {
		this.functionFieldError = functionFieldError;
	}
	
	public Integer getReadDataFrom() {
		if(readDataFrom == null) {
			return 0;
		}
		return readDataFrom;
	}

	public void setReadDataFrom(Integer readDataFrom) {
		if(readDataFrom == null) {
			readDataFrom = 0;
		}
		this.readDataFrom = readDataFrom;
	}
	public Integer getTimeStepValue() {
		return timeStepValue;
	}
	public void setTimeStepValue(Integer timeStepValue) {
		this.timeStepValue = timeStepValue;
	}
	
	public String getShowHelpText() {
		return showHelpText;
	}
	public void setShowHelpText(String showHelpText) {
		this.showHelpText = showHelpText;
	}
	public int getFormFieldVisibility() {
		return formFieldVisibility;
	}
	public void setFormFieldVisibility(int formFieldVisibility) {
		this.formFieldVisibility = formFieldVisibility;
	}
	@JsonIgnore
	public String getCustomEntityFilteringCritiria() {
		return customEntityFilteringCritiria;
	}
	@JsonIgnore
	public void setCustomEntityFilteringCritiria(String customEntityFilteringCritiria) {
		this.customEntityFilteringCritiria = customEntityFilteringCritiria;
	}
	
	public Integer getEnableCommaSeperator() {
		return enableCommaSeperator;
	}

	public void setEnableCommaSeperator(Integer enableCommaSeperator) {
		this.enableCommaSeperator = enableCommaSeperator;
	}

	public String getCurrencyFormat() {
		return currencyFormat;
	}

	public void setCurrencyFormat(String currencyFormat) {
		this.currencyFormat = currencyFormat;
	}
	public Integer getEnablePickerAndDropdown() {
		return enablePickerAndDropdown;
	}
	public void setEnablePickerAndDropdown(Integer enablePickerAndDropdown) {
		this.enablePickerAndDropdown = enablePickerAndDropdown;
	}

	public String getNumberToWordCurrencyType() {
		return numberToWordCurrencyType;
	}
	public void setNumberToWordCurrencyType(String numberToWordCurrencyType) {
		this.numberToWordCurrencyType = numberToWordCurrencyType;
	}
	public Integer getEnableMediaDelete() {
		return enableMediaDelete;
	}
	public void setEnableMediaDelete(Integer enableMediaDelete) {
		this.enableMediaDelete = enableMediaDelete;
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
	public int getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(int fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getCustomFieldLabel() {
		return customFieldLabel;
	}
	public void setCustomFieldLabel(String customFieldLabel) {
		this.customFieldLabel = customFieldLabel;
	}
	
	
	public Integer getEnableUserInputsForTextFieldFormula() {
		return enableUserInputsForTextFieldFormula;
	}
	public void setEnableUserInputsForTextFieldFormula(Integer enableUserInputsForTextFieldFormula) {
		this.enableUserInputsForTextFieldFormula = enableUserInputsForTextFieldFormula;
	}
	
	public Integer getSendActionAssignmentsOfEmpIdsOfGroupRestrictions() {
		return sendActionAssignmentsOfEmpIdsOfGroupRestrictions;
	}

	public void setSendActionAssignmentsOfEmpIdsOfGroupRestrictions(
			Integer sendActionAssignmentsOfEmpIdsOfGroupRestrictions) {
		this.sendActionAssignmentsOfEmpIdsOfGroupRestrictions = sendActionAssignmentsOfEmpIdsOfGroupRestrictions;
	}

	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public boolean isBold() {
		return bold;
	}
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	public boolean isItalic() {
		return italic;
	}
	public void setItalic(boolean italic) {
		this.italic = italic;
	}
	public boolean isUnderLine() {
		return underLine;
	}
	public void setUnderLine(boolean underLine) {
		this.underLine = underLine;
	}
	public Integer getRestrictLocationPickCondition() {
		return restrictLocationPickCondition;
	}
	public void setRestrictLocationPickCondition(
			Integer restrictLocationPickCondition) {
		this.restrictLocationPickCondition = restrictLocationPickCondition;
	}

	public String getEmployeeListBasedFilteringCritiria() {
		return employeeListBasedFilteringCritiria;
	}

	public void setEmployeeListBasedFilteringCritiria(String employeeListBasedFilteringCritiria) {
		this.employeeListBasedFilteringCritiria = employeeListBasedFilteringCritiria;
	}


	public boolean isVisibleForCreation() {
		return visibleForCreation;
	}
	public void setVisibleForCreation(boolean visibleForCreation) {
		this.visibleForCreation = visibleForCreation;
	}
	public boolean isSearchableField() {
		return searchableField;
	}
	public void setSearchableField(boolean searchableField) {
		this.searchableField = searchableField;
	}
	public Integer getEnableFrontCameraInMobile() {
		return enableFrontCameraInMobile;
	}
	public void setEnableFrontCameraInMobile(Integer enableFrontCameraInMobile) {
		this.enableFrontCameraInMobile = enableFrontCameraInMobile;
	}
	
	
	public Integer getMaxNumberOfFilesAllowed() {
		return maxNumberOfFilesAllowed;
	}
	public void setMaxNumberOfFilesAllowed(Integer maxNumberOfFilesAllowed) {
		this.maxNumberOfFilesAllowed = maxNumberOfFilesAllowed;
	}
	public Long getFormFieldEntityId() {
		return formFieldEntityId;
	}

	public void setFormFieldEntityId(Long formFieldEntityId) {
		this.formFieldEntityId = formFieldEntityId;
	}
	public String getChildCustomerFilteringCritiria() {
		return childCustomerFilteringCritiria;
	}
	public void setChildCustomerFilteringCritiria(String childCustomerFilteringCritiria) {
		this.childCustomerFilteringCritiria = childCustomerFilteringCritiria;
	}

	public String getCustomerAutoFilteringCritiria() {
		return customerAutoFilteringCritiria;
	}
	public void setCustomerAutoFilteringCritiria(String customerAutoFilteringCritiria) {
		this.customerAutoFilteringCritiria = customerAutoFilteringCritiria;
	}

	public Integer getShowOnlyMappedItemsWhileSelection() {
		return showOnlyMappedItemsWhileSelection;
	}
	public void setShowOnlyMappedItemsWhileSelection(Integer showOnlyMappedItemsWhileSelection) {
		this.showOnlyMappedItemsWhileSelection = showOnlyMappedItemsWhileSelection;
	}
	
	public boolean isFindDistanceByGoogleApi() {
		return findDistanceByGoogleApi;
	}
	public void setFindDistanceByGoogleApi(Boolean findDistanceByGoogleApi) {
		if(findDistanceByGoogleApi != null)
			this.findDistanceByGoogleApi = findDistanceByGoogleApi.booleanValue();
		else 
			this.findDistanceByGoogleApi = false;	
	}
	public Integer getMetricUnitsOfDistance() {
		return metricUnitsOfDistance;
	}
	public void setMetricUnitsOfDistance(Integer metricUnitsOfDistance) {
		this.metricUnitsOfDistance = metricUnitsOfDistance;
	}
	public Integer getEnableLaunchChatAppForMobile() {
		return enableLaunchChatAppForMobile;
	}
	public void setEnableLaunchChatAppForMobile(Integer enableLaunchChatAppForMobile) {
		this.enableLaunchChatAppForMobile = enableLaunchChatAppForMobile;
	}
	public boolean isEnableAgeRestriction() {
		return enableAgeRestriction;
	}
	public void setEnableAgeRestriction(boolean enableAgeRestriction) {
		this.enableAgeRestriction = enableAgeRestriction;
	}
	public Integer getMinAge() {
		return minAge;
	}
	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}
	public Integer getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}
	public String getAgeRestrictionErrorMessage() {
		return ageRestrictionErrorMessage;
	}
	public void setAgeRestrictionErrorMessage(String ageRestrictionErrorMessage) {
		this.ageRestrictionErrorMessage = ageRestrictionErrorMessage;
	}
	public Integer getEnableMediaFormatRestriction() {
		return enableMediaFormatRestriction;
	}
	public void setEnableMediaFormatRestriction(Integer enableMediaFormatRestriction) {
		this.enableMediaFormatRestriction = enableMediaFormatRestriction;
	}
	public String getAllowRequiredFormat() {
		return allowRequiredFormat;
	}
	public void setAllowRequiredFormat(String allowRequiredFormat) {
		this.allowRequiredFormat = allowRequiredFormat;
	}
	public Integer getEnableMappedTerritoriesRestriction() {
		return enableMappedTerritoriesRestriction;
	}

	public void setEnableMappedTerritoriesRestriction(Integer enableMappedTerritoriesRestriction) {
		this.enableMappedTerritoriesRestriction = enableMappedTerritoriesRestriction;
	}
	public Integer getRadioButtonOrientation() {
		return radioButtonOrientation;
	}
	public void setRadioButtonOrientation(Integer radioButtonOrientation) {
		this.radioButtonOrientation = radioButtonOrientation;
	}
	public Integer getIncludeEndDate() {
		return includeEndDate;
	}
	public void setIncludeEndDate(Integer includeEndDate) {
		this.includeEndDate = includeEndDate;
	}
	public Integer getReminderConfigEnabled() {
		return reminderConfigEnabled;
	}
	public void setReminderConfigEnabled(Integer reminderConfigEnabled) {
		this.reminderConfigEnabled = reminderConfigEnabled;
	}
	public String getRemainderRemarksFields() {
		return remainderRemarksFields;
	}
	public void setRemainderRemarksFields(String remainderRemarksFields) {
		this.remainderRemarksFields = remainderRemarksFields;
	}
	public Integer getShowRemainderBefore() {
		return showRemainderBefore;
	}
	public void setShowRemainderBefore(Integer showRemainderBefore) {
		this.showRemainderBefore = showRemainderBefore;
	}
	
}
