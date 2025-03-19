package com.effort.entity;

import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
/**
 * @author sateesh chundru
 * 
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormFieldSpec extends FormFieldSpecMaster {
	public final static int COMPUTED_FIELD_TYPE_COMPUTED = 1;
	public final static int COMPUTED_FIELD_TYPE_DEFAULT = 2;
	public final static int COMPUTED_FIELD_TYPE_NONE = 0;
	public final static int COMPUTED_FIELD_TYPE_FUNCTION = 3;
	public final static int COMPUTED_FIELD_TYPE_DEPENDENT = 4;

	private long fieldSpecId;
	private String uniqueId;
	private long formSpecId;
	private String fieldLabel;
	private int fieldType;
	private String fieldTypeExtra;
	private boolean computedField;
	private boolean defaultField;
	private boolean barcodeField;
	private String formula;
	private boolean isRequired;
	private boolean isVisible = true;
	private int displayOrder;
	private String expression;
	private boolean identifier;
	private Integer pageId;
	private int computedFieldType;
	private boolean mandatory;
	private int type;
	private String value;
	private String validEncodedValue;
	private boolean visibleForCutomerForm;

	private int index;
	private String fieldLabelError;
	private String fieldTypeError;
	private String computedFieldError;
	private String barcodeFieldError;
	private String isRequiredError;
	private String isVisibleError;
	private String functionFieldError;
	// extra properties
	@JsonProperty(access = Access.WRITE_ONLY)
	private String fieldVisbleRestrictedEmpGrps;
	@JsonProperty(access = Access.WRITE_ONLY)
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
	private List<ListFilteringCritiria> listFilteringCritirias;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String visibilityDependencyCriteria;// this is json for
												// ListFilteringCritiria
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<VisibilityDependencyCriteria> visibilityDependencyCriterias;
	@JsonProperty(access = Access.WRITE_ONLY)
	private int visbleOnVisibilityCondition = 1;
	private int formFieldIdentifier = 0;
	
	private Long initialFormFieldSpecId;
	private Long skeletonFormFieldSpecId;
	private boolean isUnique;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long actionSpecId;
	
	private int isRemoteField = 0;
	private String isRemoteFieldError;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String customerFilteringCritiria;// this json for CustomerFilteringCritiria
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<CustomerFilteringCritiria> customerFilteringCritirias;
	
	private String backgroundColorDependencyCritiria;
	
	private String fieldTypeExtraForm;

	private String externalLabel;

	private boolean isWorkInviationField;
	
	private Integer mediaPickCondition;
	
	private boolean conditionalMandatory = false;
	
	private Integer locationPickCondition = 0;
	
	private Integer visibilityOnCheckin = 0;
	
	private Integer workFieldVisibility = 0;
	
	private Integer radioButtonCondition = 0;
	private String radioButtonDefaultValue;
	
	private String formSpecUniqueId;
	private String validationExpr;
	private String validationErrorMsg;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String fieldValidationCritiria;// this json for FieldValidationCritiria
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<FieldValidationCritiria> fieldValidationCritirias;
	/*Date: 2016-06-20
	*Change Purpose:Saving imagesize and enable imageSize values also(Image max size restriction )
	*Resource: Deva*/
	private Boolean imageSizeEnabled = false;//we made this Boolean(wrapper) to handle null also
	private Integer imageSize;
	private Integer customerFieldVisibility = 0;

	private Integer prefix;
	private Integer seperator;
	private Integer include;
	private Integer sequenceLength;
	
	private boolean visibleForCreation = true;
	private Long formFieldGroupSpecId;
	private Integer isGroupFieldSpec=0;
	private String backgroundColor;
	
	private boolean searchableField = true;
	
	private Integer empGroupVisibleType = 1; //default invisible
	private Integer empGroupEditableType = 2; //default un-editable
	private Integer groupIndex;
	private String groupExpression;
	private Integer enableSpinnerCondition;
	
	private Integer decimalValueLimit;
	
	private String empRoleFilterValues;
	
	private String modifiedTime;
	
	private String aliasExpression;
	
	private Integer textFieldMultiLineValue;
	
	private String formFilteringCritiria;//
	private List<FormFilteringCritiria> formFilteringCritirias;
	
	private boolean uniqueCheck;
	private Integer otpExpiryTimeInSeconds;
	private Integer noOfOtpDigits;
	
	private Integer reminderConfigEnabled;
	private String remainderRemarksFields;
	
	private Integer readOnlyCondition = 0;
	private Integer updateFormAsProcessed =0;
	private Integer restrictToCaps = 0;
	private Integer pickDiffCustomer = 0;
	
	private Integer guidField = 0;
	
	private String fieldTypeExtraCustomEntity;
	
	private String pickEmployeesFromGroupIds;
	
	private boolean simpleSearch;
	
	private boolean canViewSubTaskProcess;
	private boolean canAddSubTaskProcess;
	private boolean performMandateSubTask;
	
	private boolean dependentComputedField;
	private boolean dependentDefaultField;
	
	private String employeeFilteringCritiria;// this json for EmployeeFilteringCritiria
	private List<EmployeeFilteringCritiria> employeeFilteringCritirias;
	
	private String employeeListBasedFilteringCritiria;// this json for EmployeeFilteringCritiria
	private List<EmployeeFilteringCritiria> employeeListBasedFilteringCritirias;
	
	private Long fieldLabelFontId;
	private Long formFieldEntityId;
	private Long fieldValueFontId;
	private boolean created = false;
	private String displayLable;
	
    private String entityFilterFunctionFieldsMapping;// this json for EmployeeFilteringCritiria
//	private List<EntityFilterFunctionFieldsMapping> entityFilterFunctionFieldsMappingList;
	
	private boolean functionField;
	private String functionName;
	
	private String customisedPrefix;
	private Integer readDataFrom;
	private boolean restrictDataFromMobile;
	private boolean groupIdentifier;
	
	private boolean identifierForPrintTemplate;
	
	private boolean captureForWorkAudit;
	private Integer timeStepValue;
	
	private String fontColor;
	private String fontSize;
	private boolean bold;
	private boolean italic;
	private boolean underLine;
	private String showHelpText;
	private int visibile;
	private int formFieldVisibility;
	private String customEntityFilteringCritiria;
	private List<CustomEntityFilteringCritiria> customEntityFilteringCritirias;
	private Integer enableCommaSeperator = 0;
	private String currencyFormat = "0";
	
	private Integer enablePickerAndDropdown = 1;

	private String numberToWordCurrencyType;
	
	private String inputFormFieldExpression;
	
	private Integer enableMediaDelete;
	
	private boolean staticField;

	private Integer status;

//	private transient CommonsMultipartFile staticFieldFile = null;
	
	private String staticFieldMediaId;
	
	private String staticFieldMediaFileError;
	
	private String staticFieldCheckboxError;
	
//	private transient CommonsMultipartFile staticFieldThumbnailFile = null;
	
	private String staticFieldThumbnailMediaId;
	
	private String staticFieldThumbnailMediaFileError;
	private Integer sendActionAssignmentsOfEmpIdsOfGroupRestrictions = 0;

	
	private Integer enableUserInputsForTextFieldFormula=0;
	
	private String customFieldLabel; //used in feature form fields localization 
	
	private Integer onDemandLocationRequest= 0;
	private String onDemandLocationDefaultPhnNo;
	
	private Integer restrictLocationPickCondition = 0;
	private Integer enableFrontCameraInMobile=0;

	
	private Integer maxNumberOfFilesAllowed = 0;
	
	private String fieldId;
	private boolean section;
	private String mappedFieldLabel;
	
	private String staticFieldValue;
	private String staticFieldValueError;
	
	private Long customEntitySpecId;
	
	private boolean findDistanceByGoogleApi;
	private Integer metricUnitsOfDistance = 2;
	
	private boolean enableAgeRestriction;
	private Integer minAge;
	private Integer maxAge;
	private String ageRestrictionErrorMessage;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String childCustomerFilteringCritiria;
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<CustomerFilteringCritiria> childCustomerFilteringCritirias;
	
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String customerAutoFilteringCritiria;// this json for CustomerAutoFilteringCritiria
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<CustomerAutoFilteringCritiria> customerAutoFilteringCritirias;
	
	private Integer showOnlyMappedItemsWhileSelection = 0;
	private boolean ocrField;
	private Integer ocrTypeId;
	
	private boolean dependentField;
	private String dependentFieldExpression;
	private String dependentFieldLabel;
	
	private String dependentFieldExpressionError;
	private String dependentFieldLabelError;
	
	private Integer enableLaunchChatAppForMobile;
	
	private Integer enableMediaFormatRestriction = 0;
	private String allowRequiredFormat;
	private Integer enableMappedTerritoriesRestriction = 0;
	private Integer radioButtonOrientation;
	private Integer showRemainderBefore;
	
	private String remainderRemarksFieldsCsv;
	private Integer includeEndDate;
	private String customisedSufix;
	private Integer sufix;
	private boolean prefixExpression;
	private boolean sufixExpression;
	private String prefixFormFieldExpression;
	private String sufixFormFieldExpression;

	
	public boolean isRestrictDataFromMobile() {
		return restrictDataFromMobile;
	}

	public void setRestrictDataFromMobile(boolean restrictDataFromMobile) {
		this.restrictDataFromMobile = restrictDataFromMobile;
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

	public long getFieldSpecId() {
		return fieldSpecId;
	}

	public void setFieldSpecId(long fieldSpecId) {
		this.fieldSpecId = fieldSpecId;
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
	public boolean getIsRequired() {
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

	public Integer getPageId() {
		return pageId == null ? 0 : pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
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

	
	public int getVisbleOnVisibilityCondition() {
		return visbleOnVisibilityCondition;
	}

	public void setVisbleOnVisibilityCondition(int visbleOnVisibilityCondition) {
		this.visbleOnVisibilityCondition = visbleOnVisibilityCondition;
	}

	
	public String getVisibilityDependencyCriteria() {
		return visibilityDependencyCriteria;
	}

	public void setVisibilityDependencyCriteria(
			String visibilityDependencyCriteria) {
		this.visibilityDependencyCriteria = visibilityDependencyCriteria;
	}

	
	public List<VisibilityDependencyCriteria> getVisibilityDependencyCriterias() {
		return visibilityDependencyCriterias;
	}

	public void setVisibilityDependencyCriterias(
			List<VisibilityDependencyCriteria> visibilityDependencyCriterias) {
		this.visibilityDependencyCriterias = visibilityDependencyCriterias;
	}

	
	public String getListFilteringCritiria() {
		return listFilteringCritiria;
	}

	public void setListFilteringCritiria(String listFilteringCritiria) {
		this.listFilteringCritiria = listFilteringCritiria;
	}

	
	public List<ListFilteringCritiria> getListFilteringCritirias() {
		return listFilteringCritirias;
	}

	public void setListFilteringCritirias(
			List<ListFilteringCritiria> listFilteringCritirias) {
		this.listFilteringCritirias = listFilteringCritirias;
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
		return value == null ? "" : value.replace("\r", "")
				.replace("\n", "\\n");
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
	public String toString() {
		return "FormFieldSpec [fieldSpecId=" + fieldSpecId + ", uniqueId=" + uniqueId + ", formSpecId=" + formSpecId
				+ ", fieldLabel=" + fieldLabel + ", fieldType=" + fieldType + ", fieldTypeExtra=" + fieldTypeExtra
				+ ", computedField=" + computedField + ", defaultField=" + defaultField + ", barcodeField="
				+ barcodeField + ", formula=" + formula + ", isRequired=" + isRequired + ", isVisible=" + isVisible
				+ ", displayOrder=" + displayOrder + ", expression=" + expression + ", identifier=" + identifier
				+ ", pageId=" + pageId + ", computedFieldType=" + computedFieldType + ", mandatory=" + mandatory
				+ ", type=" + type + ", value=" + value + ", validEncodedValue=" + validEncodedValue
				+ ", visibleForCutomerForm=" + visibleForCutomerForm + ", index=" + index + ", fieldLabelError="
				+ fieldLabelError + ", fieldTypeError=" + fieldTypeError + ", computedFieldError=" + computedFieldError
				+ ", barcodeFieldError=" + barcodeFieldError + ", isRequiredError=" + isRequiredError
				+ ", isVisibleError=" + isVisibleError + ", functionFieldError=" + functionFieldError
				+ ", fieldVisbleRestrictedEmpGrps=" + fieldVisbleRestrictedEmpGrps + ", fieldEditableRestrictedEmpGrps="
				+ fieldEditableRestrictedEmpGrps + ", min=" + min + ", max=" + max + ", minErrorValue=" + minErrorValue
				+ ", maxErrorValue=" + maxErrorValue + ", visible=" + visible + ", editable=" + editable
				+ ", listFilteringCritiria=" + listFilteringCritiria + ", listFilteringCritirias="
				+ listFilteringCritirias + ", visibilityDependencyCriteria=" + visibilityDependencyCriteria
				+ ", visibilityDependencyCriterias=" + visibilityDependencyCriterias + ", visbleOnVisibilityCondition="
				+ visbleOnVisibilityCondition + ", formFieldIdentifier=" + formFieldIdentifier
				+ ", initialFormFieldSpecId=" + initialFormFieldSpecId + ", skeletonFormFieldSpecId="
				+ skeletonFormFieldSpecId + ", isUnique=" + isUnique + ", actionSpecId=" + actionSpecId
				+ ", isRemoteField=" + isRemoteField + ", isRemoteFieldError=" + isRemoteFieldError
				+ ", customerFilteringCritiria=" + customerFilteringCritiria + ", customerFilteringCritirias="
				+ customerFilteringCritirias + ", backgroundColorDependencyCritiria="
				+ backgroundColorDependencyCritiria + ", fieldTypeExtraForm=" + fieldTypeExtraForm + ", externalLabel="
				+ externalLabel + ", isWorkInviationField=" + isWorkInviationField + ", mediaPickCondition="
				+ mediaPickCondition + ", conditionalMandatory=" + conditionalMandatory + ", locationPickCondition="
				+ locationPickCondition + ", visibilityOnCheckin=" + visibilityOnCheckin + ", workFieldVisibility="
				+ workFieldVisibility + ", radioButtonCondition=" + radioButtonCondition + ", radioButtonDefaultValue="
				+ radioButtonDefaultValue + ", formSpecUniqueId=" + formSpecUniqueId + ", validationExpr="
				+ validationExpr + ", validationErrorMsg=" + validationErrorMsg + ", fieldValidationCritiria="
				+ fieldValidationCritiria + ", fieldValidationCritirias=" + fieldValidationCritirias
				+ ", imageSizeEnabled=" + imageSizeEnabled + ", imageSize=" + imageSize + ", customerFieldVisibility="
				+ customerFieldVisibility + ", prefix=" + prefix + ", seperator=" + seperator + ", include=" + include
				+ ", sequenceLength=" + sequenceLength + ", visibleForCreation=" + visibleForCreation
				+ ", formFieldGroupSpecId=" + formFieldGroupSpecId + ", isGroupFieldSpec=" + isGroupFieldSpec
				+ ", backgroundColor=" + backgroundColor + ", searchableField=" + searchableField
				+ ", empGroupVisibleType=" + empGroupVisibleType + ", empGroupEditableType=" + empGroupEditableType
				+ ", groupIndex=" + groupIndex + ", groupExpression=" + groupExpression + ", enableSpinnerCondition="
				+ enableSpinnerCondition + ", decimalValueLimit=" + decimalValueLimit + ", empRoleFilterValues="
				+ empRoleFilterValues + ", modifiedTime=" + modifiedTime + ", aliasExpression=" + aliasExpression
				+ ", textFieldMultiLineValue=" + textFieldMultiLineValue + ", formFilteringCritiria="
				+ formFilteringCritiria + ", formFilteringCritirias=" + formFilteringCritirias + ", uniqueCheck="
				+ uniqueCheck + ", otpExpiryTimeInSeconds=" + otpExpiryTimeInSeconds + ", noOfOtpDigits="
				+ noOfOtpDigits + ", reminderConfigEnabled=" + reminderConfigEnabled + ", remainderRemarksFields="
				+ remainderRemarksFields + ", readOnlyCondition=" + readOnlyCondition + ", updateFormAsProcessed="
				+ updateFormAsProcessed + ", restrictToCaps=" + restrictToCaps + ", pickDiffCustomer="
				+ pickDiffCustomer + ", guidField=" + guidField + ", fieldTypeExtraCustomEntity="
				+ fieldTypeExtraCustomEntity + ", pickEmployeesFromGroupIds=" + pickEmployeesFromGroupIds
				+ ", simpleSearch=" + simpleSearch + ", canViewSubTaskProcess=" + canViewSubTaskProcess
				+ ", canAddSubTaskProcess=" + canAddSubTaskProcess + ", performMandateSubTask=" + performMandateSubTask
				+ ", dependentComputedField=" + dependentComputedField + ", dependentDefaultField="
				+ dependentDefaultField + ", employeeFilteringCritiria=" + employeeFilteringCritiria
				+ ", employeeFilteringCritirias=" + employeeFilteringCritirias + ", fieldLabelFontId="
				+ fieldLabelFontId + ", fieldValueFontId=" + fieldValueFontId + ", created=" + created
				+ ", displayLable=" + displayLable + ", entityFilterFunctionFieldsMapping="
				+ entityFilterFunctionFieldsMapping + ", entityFilterFunctionFieldsMappingList="
				+  ", functionField=" + functionField + ", functionName="
				+ functionName + ", customisedPrefix=" + customisedPrefix + ", readDataFrom=" + readDataFrom
				+ ", restrictDataFromMobile=" + restrictDataFromMobile + ", groupIdentifier=" + groupIdentifier
				+ ", identifierForPrintTemplate=" + identifierForPrintTemplate + ", captureForWorkAudit="

				+ captureForWorkAudit + ", timeStepValue=" + timeStepValue + ", enableCommaSeperator=" 
				+ enableCommaSeperator + ", currencyFormat=" + currencyFormat +", enableMediaDelete="
				+ enableMediaDelete + ", inputFormFieldExpression=" + inputFormFieldExpression + ","
				+ " staticField="+ staticField +", staticFieldMediaId="+staticFieldMediaId+","
				+ "sendActionAssignmentsOfEmpIdsOfGroupRestrictions="+sendActionAssignmentsOfEmpIdsOfGroupRestrictions+""
				+ ",enableUserInputsForTextFieldFormula="+enableUserInputsForTextFieldFormula+ ""
				+ ",restrictLocationPickCondition="+restrictLocationPickCondition+ ",maxNumberOfFilesAllowed="+maxNumberOfFilesAllowed+" ,"
				+ "enableFrontCameraInMobile="+enableFrontCameraInMobile+",formFieldEntityId="+formFieldEntityId+","
				+ "radioButtonOrientation="+radioButtonOrientation+","
				+ "staticFieldValue="+staticFieldValue+",includeEndDate="+includeEndDate+",customisedSufix="+customisedSufix+","
				+ "sufix="+sufix+",prefixFormFieldExpression="+prefixFormFieldExpression+","
				+ "sufixFormFieldExpression="+sufixFormFieldExpression+",prefixExpression="+prefixExpression+",sufixExpression="+sufixExpression+"]";

	}

    @Override
	public boolean equals(Object obj) {
		if (obj instanceof FormField) {
			return getFieldSpecId() == ((FormField) obj).getFieldSpecId();
		} else {
			return super.equals(obj);
		}
	}

	public String toCSV() {
		return "[fieldSpecId=" + fieldSpecId + ", uniqueId=" + uniqueId
				+ ", formSpecId=" + formSpecId + ", fieldLabel=" + fieldLabel
				+ ", fieldType=" + fieldType + ", fieldTypeExtra="
				+ fieldTypeExtra + ", computedField=" + computedField
				+ ", formula=" + formula + ", isRequired=" + isRequired
				+ ", displayOrder=" + displayOrder + ", expression="
				+ expression + ", identifier=" + identifier + ", pageId="
				+ pageId + ", type=" + type + ", value=" + value + ", index="
				+ index + ", fieldLabelError=" + fieldLabelError
				+ ", fieldTypeError=" + fieldTypeError
				+ ", computedFieldError=" + computedFieldError
				+ ", computedFieldType=" + computedFieldType
				+ ", isRequiredError=" + isRequiredError + ",fieldTypeExtraForm=" + fieldTypeExtraForm + ",decimalValueLimit=" + decimalValueLimit +"]";
	}

	public boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getIsVisibleError() {
		return isVisibleError;
	}

	public void setIsVisibleError(String isVisibleError) {
		this.isVisibleError = isVisibleError;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	
	public Long getActionSpecId() {
		return actionSpecId;
	}

	public void setActionSpecId(Long actionSpecId) {
		this.actionSpecId = actionSpecId;
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

	public int getFormFieldIdentifier() {
		return formFieldIdentifier;
	}

	public void setFormFieldIdentifier(int formFieldIdentifier) {
		this.formFieldIdentifier = formFieldIdentifier;
	}
	
	

	
	public String getCustomerFilteringCritiria() {
		return customerFilteringCritiria;
	}

	public void setCustomerFilteringCritiria(String customerFilteringCritiria) {
		this.customerFilteringCritiria = customerFilteringCritiria;
	}

	
	public List<CustomerFilteringCritiria> getCustomerFilteringCritirias() {
		return customerFilteringCritirias;
	}

	public void setCustomerFilteringCritirias(
			List<CustomerFilteringCritiria> customerFilteringCritirias) {
		this.customerFilteringCritirias = customerFilteringCritirias;
	}

	public String getFieldTypeExtraForm() {
		return fieldTypeExtraForm;
	}

	public void setFieldTypeExtraForm(String fieldTypeExtraForm) {
		this.fieldTypeExtraForm = fieldTypeExtraForm;
	}

	public boolean getIsUnique() {
		return isUnique;
	}

	public void setIsUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	public String getExternalLabel() {
		return externalLabel;
	}

	public void setExternalLabel(String externalLabel) {
		this.externalLabel = externalLabel;
	}

	
	public boolean isVisibleForCutomerForm() {
		return visibleForCutomerForm;
	}

	public void setVisibleForCutomerForm(boolean visibleForCutomerForm) {
		this.visibleForCutomerForm = visibleForCutomerForm;
	}


	public boolean getIsWorkInviationField() {
		return isWorkInviationField;
	}

	public void setIsWorkInviationField(boolean isWorkInviationField) {
		this.isWorkInviationField = isWorkInviationField;
	}

	public Integer getMediaPickCondition() {
		return mediaPickCondition;
	}

	public void setMediaPickCondition(Integer mediaPickCondition) {
		this.mediaPickCondition = mediaPickCondition;
	}

	public boolean isConditionalMandatory() {
		return conditionalMandatory;
	}

	public void setConditionalMandatory(boolean conditionalMandatory) {
		this.conditionalMandatory = conditionalMandatory;
	}

	public Integer getLocationPickCondition() {
		return locationPickCondition;
	}

	public void setLocationPickCondition(Integer locationPickCondition) {
		this.locationPickCondition = locationPickCondition;
	}

	@JsonIgnore
	public Integer getVisibilityOnCheckin() {
		return visibilityOnCheckin;
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


	@JsonIgnore
	public void setVisibilityOnCheckin(Integer visibilityOnCheckin) {
		this.visibilityOnCheckin = visibilityOnCheckin;
	}

	@JsonIgnore
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}

	@JsonIgnore
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}

	@JsonIgnore
	public Integer getWorkFieldVisibility() {
		return workFieldVisibility;
	}

	@JsonIgnore
	public void setWorkFieldVisibility(Integer workFieldVisibility) {
		this.workFieldVisibility = workFieldVisibility;
	}

	
	public String getFieldValidationCritiria() {
		return fieldValidationCritiria;
	}

	public void setFieldValidationCritiria(String fieldValidationCritiria) {
		this.fieldValidationCritiria = fieldValidationCritiria;
	}

	
	public List<FieldValidationCritiria> getFieldValidationCritirias() {
		return fieldValidationCritirias;
	}

	public void setFieldValidationCritirias(List<FieldValidationCritiria> fieldValidationCritirias) {
		this.fieldValidationCritirias = fieldValidationCritirias;
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

	public Integer getCustomerFieldVisibility() {
		return customerFieldVisibility;
	}

	public void setCustomerFieldVisibility(Integer customerFieldVisibility) {
		this.customerFieldVisibility = customerFieldVisibility;
	}

	public Integer getPrefix() {
		return prefix;
	}

	public void setPrefix(Integer prefix) {
		this.prefix = prefix;
	}

	public Integer getSeperator() {
		return seperator;
	}

	public void setSeperator(Integer seperator) {
		this.seperator = seperator;
	}

	public Integer getInclude() {
		return include;
	}

	public void setInclude(Integer include) {
		this.include = include;
	}

	public Integer getSequenceLength() {
		return sequenceLength;
	}

	public void setSequenceLength(Integer sequenceLength) {
		this.sequenceLength = sequenceLength;
	}

	public boolean isVisibleForCreation() {
		return visibleForCreation;
	}

	public void setVisibleForCreation(boolean visibleForCreation) {
		this.visibleForCreation = visibleForCreation;
	}

	//Deva,2016-12-30
		/*Feature :Work escalation based on priority  
		Need to get workfields data(especailly single select list) from FormFieldSpecValidValues in json
		so instead of uncommented @JsonIgnore for Value,getEncodedValue, we are creating new variable validEncodedValue*/
	public String getValidEncodedValue() {
		return validEncodedValue;
	}

	public void setValidEncodedValue(String validEncodedValue) {
		this.validEncodedValue = validEncodedValue;
	}

	public boolean isSearchableField() {
		return searchableField;
	}

	public void setSearchableField(boolean searchableField) {
		this.searchableField = searchableField;
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
	
	public Long getFormFieldGroupSpecId() {
		return formFieldGroupSpecId;
	}

	public void setFormFieldGroupSpecId(Long formFieldGroupSpecId) {
		this.formFieldGroupSpecId = formFieldGroupSpecId;
	}

	public Integer getIsGroupFieldSpec() {
		return isGroupFieldSpec;
	}

	public void setIsGroupFieldSpec(Integer isGroupFieldSpec) {
		this.isGroupFieldSpec = isGroupFieldSpec;
	}

	@JsonIgnore
	public Integer getGroupIndex() {
		return groupIndex;
	}
	@JsonIgnore
	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}

	public String getGroupExpression() {
		return groupExpression;
	}

	public void setGroupExpression(String groupExpression) {
		this.groupExpression = groupExpression;
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

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getFormFilteringCritiria() {
		return formFilteringCritiria;
	}

	public void setFormFilteringCritiria(String formFilteringCritiria) {
		this.formFilteringCritiria = formFilteringCritiria;
	}

	public List<FormFilteringCritiria> getFormFilteringCritirias() {
		return formFilteringCritirias;
	}

	public void setFormFilteringCritirias(List<FormFilteringCritiria> formFilteringCritirias) {
		this.formFilteringCritirias = formFilteringCritirias;
	}

	public boolean isUniqueCheck() {
		return uniqueCheck;
	}

	public void setUniqueCheck(boolean uniqueCheck) {
		this.uniqueCheck = uniqueCheck;
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

	public Integer getReadOnlyCondition() {
		return readOnlyCondition;
	}

	public void setReadOnlyCondition(Integer readOnlyCondition) {
		this.readOnlyCondition = readOnlyCondition;
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

	public String getPickEmployeesFromGroupIds() {
		return pickEmployeesFromGroupIds;
	}

	public void setPickEmployeesFromGroupIds(String pickEmployeesFromGroupIds) {
		this.pickEmployeesFromGroupIds = pickEmployeesFromGroupIds;
	}

	public boolean isSimpleSearch() {
		return simpleSearch;
	}

	public void setSimpleSearch(boolean simpleSearch) {
		this.simpleSearch = simpleSearch;
	}
	@JsonIgnore
	public boolean isCanViewSubTaskProcess() {
		return canViewSubTaskProcess;
	}
	@JsonIgnore
	public void setCanViewSubTaskProcess(boolean canViewSubTaskProcess) {
		this.canViewSubTaskProcess = canViewSubTaskProcess;
	}
	@JsonIgnore
	public boolean isCanAddSubTaskProcess() {
		return canAddSubTaskProcess;
	}
	@JsonIgnore
	public void setCanAddSubTaskProcess(boolean canAddSubTaskProcess) {
		this.canAddSubTaskProcess = canAddSubTaskProcess;
	}
	@JsonIgnore
	public boolean isPerformMandateSubTask() {
		return performMandateSubTask;
	}
	@JsonIgnore
	public void setPerformMandateSubTask(boolean performMandateSubTask) {
		this.performMandateSubTask = performMandateSubTask;
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

	public List<EmployeeFilteringCritiria> getEmployeeFilteringCritirias() {
		return employeeFilteringCritirias;
	}

	public void setEmployeeFilteringCritirias(List<EmployeeFilteringCritiria> employeeFilteringCritirias) {
		this.employeeFilteringCritirias = employeeFilteringCritirias;
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

	public Integer getGuidField() {
		return guidField;
	}

	public void setGuidField(Integer guidField) {
		this.guidField = guidField;
	}

	

	public String getEntityFilterFunctionFieldsMapping() {
		return entityFilterFunctionFieldsMapping;
	}

	public void setEntityFilterFunctionFieldsMapping(String entityFilterFunctionFieldsMapping) {
		this.entityFilterFunctionFieldsMapping = entityFilterFunctionFieldsMapping;
	}

	/*public List<EntityFilterFunctionFieldsMapping> getEntityFilterFunctionFieldsMappingList() {
		return entityFilterFunctionFieldsMappingList;
	}

	public void setEntityFilterFunctionFieldsMappingList(
			List<EntityFilterFunctionFieldsMapping> entityFilterFunctionFieldsMappingList) {
		this.entityFilterFunctionFieldsMappingList = entityFilterFunctionFieldsMappingList;
	}*/
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
	public String getFunctionFieldError() {
		return functionFieldError;
	}
	public void setFunctionFieldError(String functionFieldError) {
		this.functionFieldError = functionFieldError;
	}
	@JsonIgnore
    public boolean isCreated() {
        return created;
    }
    @JsonIgnore
    public void setCreated(boolean created) {
        this.created = created;
    }
    @JsonIgnore
    public String getDisplayLable() {
        return displayLable;
    }
    @JsonIgnore
    public void setDisplayLable(String displayLable) {
        this.displayLable = displayLable;
    }
    
    public String getCustomisedPrefix() {
        return customisedPrefix;
    }

    public void setCustomisedPrefix(String customisedPrefix) {
        this.customisedPrefix = customisedPrefix;
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
	public boolean isGroupIdentifier() {
		return groupIdentifier;
	}

	public void setGroupIdentifier(boolean groupIdentifier) {
		this.groupIdentifier = groupIdentifier;
	}

	@JsonIgnore
	public boolean isIdentifierForPrintTemplate() {
		return identifierForPrintTemplate;
	}

	@JsonIgnore
	public void setIdentifierForPrintTemplate(boolean identifierForPrintTemplate) {
		this.identifierForPrintTemplate = identifierForPrintTemplate;
	}

	public boolean isCaptureForWorkAudit() {
		return captureForWorkAudit;
	}

	public void setCaptureForWorkAudit(boolean captureForWorkAudit) {
		this.captureForWorkAudit = captureForWorkAudit;
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

	public int getVisibile() {
		return visibile;
	}

	public void setVisibile(int visibile) {
		this.visibile = visibile;
	}

	public int getFormFieldVisibility() {
		return formFieldVisibility;
	}

	public void setFormFieldVisibility(int formFieldVisibility) {
		this.formFieldVisibility = formFieldVisibility;
	}
	public Integer getEnableCommaSeperator() {
		return enableCommaSeperator;
	}

	public void setEnableCommaSeperator(Integer enableCommaSeperator) {
		this.enableCommaSeperator = enableCommaSeperator;
	}

	public String getCustomEntityFilteringCritiria() {
		return customEntityFilteringCritiria;
	}

	public void setCustomEntityFilteringCritiria(String customEntityFilteringCritiria) {
		this.customEntityFilteringCritiria = customEntityFilteringCritiria;
	}

	public List<CustomEntityFilteringCritiria> getCustomEntityFilteringCritirias() {
		return customEntityFilteringCritirias;
	}

	public void setCustomEntityFilteringCritirias(List<CustomEntityFilteringCritiria> customEntityFilteringCritirias) {
		this.customEntityFilteringCritirias = customEntityFilteringCritirias;
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

	public String getInputFormFieldExpression() {
		return inputFormFieldExpression;
	}

	public void setInputFormFieldExpression(String inputFormFieldExpression) {
		this.inputFormFieldExpression = inputFormFieldExpression;
	}
	
	public Integer getEnableMediaDelete() {
		return enableMediaDelete;
	}

	public void setEnableMediaDelete(Integer enableMediaDelete) {
		this.enableMediaDelete = enableMediaDelete;
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
/*	@JsonIgnore
	public CommonsMultipartFile getStaticFieldFile() {
		return staticFieldFile;
	}
	@JsonIgnore
	public void setStaticFieldFile(CommonsMultipartFile staticFieldFile) {
		this.staticFieldFile = staticFieldFile;
	}*/

	public String getStaticFieldMediaId() {
		return staticFieldMediaId;
	}

	public void setStaticFieldMediaId(String staticFieldMediaId) {
		this.staticFieldMediaId = staticFieldMediaId;
	}

	public String getStaticFieldMediaFileError() {
		return staticFieldMediaFileError;
	}

	public void setStaticFieldMediaFileError(String staticFieldMediaFileError) {
		this.staticFieldMediaFileError = staticFieldMediaFileError;
	}

	public String getStaticFieldCheckboxError() {
		return staticFieldCheckboxError;
	}

	public void setStaticFieldCheckboxError(String staticFieldCheckboxError) {
		this.staticFieldCheckboxError = staticFieldCheckboxError;
	}

/*	public CommonsMultipartFile getStaticFieldThumbnailFile() {
		return staticFieldThumbnailFile;
	}

	public void setStaticFieldThumbnailFile(CommonsMultipartFile staticFieldThumbnailFile) {
		this.staticFieldThumbnailFile = staticFieldThumbnailFile;
	}*/

	public String getStaticFieldThumbnailMediaId() {
		return staticFieldThumbnailMediaId;
	}

	public void setStaticFieldThumbnailMediaId(String staticFieldThumbnailMediaId) {
		this.staticFieldThumbnailMediaId = staticFieldThumbnailMediaId;
	}

	public String getStaticFieldThumbnailMediaFileError() {
		return staticFieldThumbnailMediaFileError;
	}

	public void setStaticFieldThumbnailMediaFileError(String staticFieldThumbnailMediaFileError) {
		this.staticFieldThumbnailMediaFileError = staticFieldThumbnailMediaFileError;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getEnableUserInputsForTextFieldFormula() {
		return enableUserInputsForTextFieldFormula;
	}

	public void setEnableUserInputsForTextFieldFormula(Integer enableUserInputsForTextFieldFormula) {
		this.enableUserInputsForTextFieldFormula = enableUserInputsForTextFieldFormula;
	}
	
	public String getCustomFieldLabel() {
		return customFieldLabel;
	}

	public void setCustomFieldLabel(String customFieldLabel) {
		this.customFieldLabel = customFieldLabel;
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
	public String getEmployeeListBasedFilteringCritiria() {
		return employeeListBasedFilteringCritiria;
	}

	public void setEmployeeListBasedFilteringCritiria(String employeeListBasedFilteringCritiria) {
		this.employeeListBasedFilteringCritiria = employeeListBasedFilteringCritiria;
	}

	public List<EmployeeFilteringCritiria> getEmployeeListBasedFilteringCritirias() {
		return employeeListBasedFilteringCritirias;
	}

	public void setEmployeeListBasedFilteringCritirias(
			List<EmployeeFilteringCritiria> employeeListBasedFilteringCritirias) {
		this.employeeListBasedFilteringCritirias = employeeListBasedFilteringCritirias;
	}
	public Integer getRestrictLocationPickCondition() {
		return restrictLocationPickCondition;
	}
	public void setRestrictLocationPickCondition(
			Integer restrictLocationPickCondition) {
		this.restrictLocationPickCondition = restrictLocationPickCondition;
	}

	public Integer getOnDemandLocationRequest() {
		return onDemandLocationRequest;
	}

	public void setOnDemandLocationRequest(Integer onDemandLocationRequest) {
		this.onDemandLocationRequest = onDemandLocationRequest;
	}

	public String getOnDemandLocationDefaultPhnNo() {
		return onDemandLocationDefaultPhnNo;
	}
	
	public void setOnDemandLocationDefaultPhnNo(String onDemandLocationDefaultPhnNo) {
		this.onDemandLocationDefaultPhnNo = onDemandLocationDefaultPhnNo;
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

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public Long getFormFieldEntityId() {
		return formFieldEntityId;
	}

	public void setFormFieldEntityId(Long formFieldEntityId) {
		this.formFieldEntityId = formFieldEntityId;
	}

	public boolean isSection() {
		return section;
	}

	public void setSection(boolean section) {
		this.section = section;
	}

	public String getStaticFieldValue() {
		return staticFieldValue;
	}

	public void setStaticFieldValue(String staticFieldValue) {
		this.staticFieldValue = staticFieldValue;
	}

	public String getStaticFieldValueError() {
		return staticFieldValueError;
	}

	public void setStaticFieldValueError(String staticFieldValueError) {
		this.staticFieldValueError = staticFieldValueError;
	}
	
	public String getMappedFieldLabel() {
		return mappedFieldLabel;
	}

	public void setMappedFieldLabel(String mappedFieldLabel) {
		this.mappedFieldLabel = mappedFieldLabel;
	}

	public String getChildCustomerFilteringCritiria() {
		return childCustomerFilteringCritiria;
	}

	public void setChildCustomerFilteringCritiria(String childCustomerFilteringCritiria) {
		this.childCustomerFilteringCritiria = childCustomerFilteringCritiria;
	}

	public List<CustomerFilteringCritiria> getChildCustomerFilteringCritirias() {
		return childCustomerFilteringCritirias;
	}

	public void setChildCustomerFilteringCritirias(List<CustomerFilteringCritiria> childCustomerFilteringCritirias) {
		this.childCustomerFilteringCritirias = childCustomerFilteringCritirias;
	}

	public String getCustomerAutoFilteringCritiria() {
		return customerAutoFilteringCritiria;
	}

	public void setCustomerAutoFilteringCritiria(String customerAutoFilteringCritiria) {
		this.customerAutoFilteringCritiria = customerAutoFilteringCritiria;
	}

	public List<CustomerAutoFilteringCritiria> getCustomerAutoFilteringCritirias() {
		return customerAutoFilteringCritirias;
	}

	public void setCustomerAutoFilteringCritirias(List<CustomerAutoFilteringCritiria> customerAutoFilteringCritirias) {
		this.customerAutoFilteringCritirias = customerAutoFilteringCritirias;
	}

	public Integer getShowOnlyMappedItemsWhileSelection() {
		return showOnlyMappedItemsWhileSelection;
	}

	public void setShowOnlyMappedItemsWhileSelection(Integer showOnlyMappedItemsWhileSelection) {
		this.showOnlyMappedItemsWhileSelection = showOnlyMappedItemsWhileSelection;
	}

	public Long getCustomEntitySpecId() {
		return customEntitySpecId;
	}

	public void setCustomEntitySpecId(Long customEntitySpecId) {
		this.customEntitySpecId = customEntitySpecId;
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

	public boolean isDependentField() {
		return dependentField;
	}

	public void setDependentField(boolean dependentField) {
		this.dependentField = dependentField;
	}

	public String getDependentFieldExpression() {
		return dependentFieldExpression;
	}

	public void setDependentFieldExpression(String dependentFieldExpression) {
		this.dependentFieldExpression = dependentFieldExpression;
	}

	public String getDependentFieldLabel() {
		return dependentFieldLabel;
	}

	public void setDependentFieldLabel(String dependentFieldLabel) {
		this.dependentFieldLabel = dependentFieldLabel;
	}

	public boolean isOcrField() {
		return ocrField;
	}

	public void setOcrField(boolean ocrField) {
		this.ocrField = ocrField;
	}

	public Integer getOcrTypeId() {
		return ocrTypeId;
	}

	public void setOcrTypeId(Integer ocrTypeId) {
		this.ocrTypeId = ocrTypeId;
	}

	public String getDependentFieldExpressionError() {
		return dependentFieldExpressionError;
	}

	public void setDependentFieldExpressionError(String dependentFieldExpressionError) {
		this.dependentFieldExpressionError = dependentFieldExpressionError;
	}

	public String getDependentFieldLabelError() {
		return dependentFieldLabelError;
	}

	public void setDependentFieldLabelError(String dependentFieldLabelError) {
		this.dependentFieldLabelError = dependentFieldLabelError;
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

	public Integer getShowRemainderBefore() {
		return showRemainderBefore;
	}

	public void setShowRemainderBefore(Integer showRemainderBefore) {
		this.showRemainderBefore = showRemainderBefore;
	}

	public String getRemainderRemarksFieldsCsv() {
		return remainderRemarksFieldsCsv;
	}

	public void setRemainderRemarksFieldsCsv(String remainderRemarksFieldsCsv) {
		this.remainderRemarksFieldsCsv = remainderRemarksFieldsCsv;
	}

	public Integer getIncludeEndDate() {
		return includeEndDate;
	}

	public void setIncludeEndDate(Integer includeEndDate) {
		this.includeEndDate = includeEndDate;
	}

	public String getCustomisedSufix() {
		return customisedSufix;
	}

	public void setCustomisedSufix(String customisedSufix) {
		this.customisedSufix = customisedSufix;
	}

	public Integer getSufix() {
		return sufix;
	}

	public void setSufix(Integer sufix) {
		this.sufix = sufix;
	}

	public boolean isPrefixExpression() {
		return prefixExpression;
	}

	public void setPrefixExpression(boolean prefixExpression) {
		this.prefixExpression = prefixExpression;
	}

	public boolean isSufixExpression() {
		return sufixExpression;
	}

	public void setSufixExpression(boolean sufixExpression) {
		this.sufixExpression = sufixExpression;
	}

	public String getPrefixFormFieldExpression() {
		return prefixFormFieldExpression;
	}

	public void setPrefixFormFieldExpression(String prefixFormFieldExpression) {
		this.prefixFormFieldExpression = prefixFormFieldExpression;
	}

	public String getSufixFormFieldExpression() {
		return sufixFormFieldExpression;
	}

	public void setSufixFormFieldExpression(String sufixFormFieldExpression) {
		this.sufixFormFieldExpression = sufixFormFieldExpression;
	}
	
	
	
}
