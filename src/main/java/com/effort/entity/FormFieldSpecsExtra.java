package com.effort.entity;



public class FormFieldSpecsExtra 
{

	private Long formFieldSpecExtraId;
	private Long formSpecId;
	private Long fieldSpecId;
	private String showHelpText;
	private Integer enableCommaSeperator = 0;
	private String currencyFormat;
	private boolean staticField;
	private String staticFieldMediaId;
	private String staticFieldThumbnailMediaId;
	
	private Integer enableUserInputsForTextFieldFormula=0;
	
	private Integer enablePickerAndDropdown;
	
	private String numberToWordCurrencyType;
	private Integer sendActionAssignmentsOfEmpIdsOfGroupRestrictions = 0;
	private Integer restrictLocationPickCondition = 0;
	private Integer enableFrontCameraInMobile=0;

	private Integer maxNumberOfFilesAllowed = 0;
	private Long formFieldEntityId;
	private String staticFieldValue;
	
	private int showOnlyMappedItemsWhileSelection = 0;
	private boolean findDistanceByGoogleApi;
	private Integer metricUnitsOfDistance;
	
	private boolean enableAgeRestriction;
	private Integer minAge;
	private Integer maxAge;
	private String ageRestrictionErrorMessage;
	private Integer enableMediaFormatRestriction = 0;
	private String allowRequiredFormat;
	private Integer enableMappedTerritoriesRestriction = 0;
	private Integer radioButtonOrientation;
	private Integer showRemainderBefore;
	private Integer includeEndDate;

	
	public Long getFormFieldSpecExtraId() {
		return formFieldSpecExtraId;
	}
	public void setFormFieldSpecExtraId(Long formFieldSpecExtraId) {
		this.formFieldSpecExtraId = formFieldSpecExtraId;
	}
	public Long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(Long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public Long getFieldSpecId() {
		return fieldSpecId;
	}
	public void setFieldSpecId(Long fieldSpecId) {
		this.fieldSpecId = fieldSpecId;
	}
	public String getShowHelpText() {
		return showHelpText;
	}
	public void setShowHelpText(String showHelpText) {
		this.showHelpText = showHelpText;
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
	public boolean isStaticField() {
		return staticField;
	}

	public void setStaticField(Boolean staticField) {
		if(staticField == null) {
			this.staticField = false;
		} else {
			this.staticField = staticField;
		}
	}
	public String getStaticFieldMediaId() {
		return staticFieldMediaId;
	}

	public void setStaticFieldMediaId(String staticFieldMediaId) {
		this.staticFieldMediaId = staticFieldMediaId;
	}
	public String getStaticFieldThumbnailMediaId() {
		return staticFieldThumbnailMediaId;
	}
	public void setStaticFieldThumbnailMediaId(String staticFieldThumbnailMediaId) {
		this.staticFieldThumbnailMediaId = staticFieldThumbnailMediaId;
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
	
	public Integer getRestrictLocationPickCondition() {
		return restrictLocationPickCondition;
	}
	public void setRestrictLocationPickCondition(
			Integer restrictLocationPickCondition) {
		this.restrictLocationPickCondition = restrictLocationPickCondition;
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
	public String getStaticFieldValue() {
		return staticFieldValue;
	}
	public void setStaticFieldValue(String staticFieldValue) {
		this.staticFieldValue = staticFieldValue;
	}
	
	public int getShowOnlyMappedItemsWhileSelection() {
		return showOnlyMappedItemsWhileSelection;
	}
	public void setShowOnlyMappedItemsWhileSelection(int showOnlyMappedItemsWhileSelection) {
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
	public Integer getShowRemainderBefore() {
		return showRemainderBefore;
	}
	public void setShowRemainderBefore(Integer showRemainderBefore) {
		this.showRemainderBefore = showRemainderBefore;
	}
	public Integer getIncludeEndDate() {
		return includeEndDate;
	}
	public void setIncludeEndDate(Integer includeEndDate) {
		this.includeEndDate = includeEndDate;
	}
	
	
}
