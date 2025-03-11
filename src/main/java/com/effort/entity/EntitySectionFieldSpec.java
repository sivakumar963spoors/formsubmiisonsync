package com.effort.entity;



import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class EntitySectionFieldSpec implements Serializable {

	private static final long serialVersionUID = 1L;
	private long sectionFieldSpecId;
	private long entitySpecId;
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
	private Long initialEntitySectionFieldSpecId;
	private Long skeletonEntitySectionFieldSpecId;
	private String createdTime;
	private String modifiedTime;
	private int isRemoteField = 0;
	private String fieldTypeExtraForm;
	private String externalLabel;
	private Integer mediaPickCondition;
	private Integer locationPickCondition = 0;
	private String validationExpr;
	private String validationErrorMsg;
	private int computedFieldType;
	private String value;
	private String min;
	private String max;
	private int visible = 1;
	private int editable = 1;
	
	private String fieldLabelError;
	private String fieldTypeError;
	private String isRequiredError;
	
	private String fieldTypeExtraCustomEntity;
	
	public long getSectionFieldSpecId() {
		return sectionFieldSpecId;
	}
	public void setSectionFieldSpecId(long sectionFieldSpecId) {
		this.sectionFieldSpecId = sectionFieldSpecId;
	}
	public long getEntitySpecId() {
		return entitySpecId;
	}
	public void setEntitySpecId(long entitySpecId) {
		this.entitySpecId = entitySpecId;
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
	public boolean getIsRequired(){
		return isRequired;
	}
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public void setIsRequired(boolean isRequired) {
		this.isRequired = isRequired;
		setRequired(isRequired);
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
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
	public Long getInitialEntitySectionFieldSpecId() {
		return initialEntitySectionFieldSpecId;
	}
	public void setInitialEntitySectionFieldSpecId(Long initialEntitySectionFieldSpecId) {
		this.initialEntitySectionFieldSpecId = initialEntitySectionFieldSpecId;
	}
	public Long getSkeletonEntitySectionFieldSpecId() {
		return skeletonEntitySectionFieldSpecId;
	}
	public void setSkeletonEntitySectionFieldSpecId(Long skeletonEntitySectionFieldSpecId) {
		this.skeletonEntitySectionFieldSpecId = skeletonEntitySectionFieldSpecId;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public int getIsRemoteField() {
		return isRemoteField;
	}
	public void setIsRemoteField(int isRemoteField) {
		this.isRemoteField = isRemoteField;
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
	public int getComputedFieldType() {
		return computedFieldType;
	}
	public void setComputedFieldType(int computedFieldType) {
		this.computedFieldType = computedFieldType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFieldLabelError() {
		return fieldLabelError;
	}
	public void setFieldLabelError(String fieldLabelError) {
		this.fieldLabelError = fieldLabelError;
	}
	public String getFieldTypeError() {
		return fieldTypeError;
	}
	public void setFieldTypeError(String fieldTypeError) {
		this.fieldTypeError = fieldTypeError;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public String getEncodedValue() {
		return value == null ? "" : value.replace("\r", "").replace("\n", "\\n");
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
	
	public String getIsRequiredError() {
		return isRequiredError;
	}
	public void setIsRequiredError(String isRequiredError) {
		this.isRequiredError = isRequiredError;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof EntitySectionField){
			return getSectionFieldSpecId() == ((EntitySectionField) obj).getSectionFieldSpecId();
		} else {
			return super.equals(obj);
		}
	}
	
	@Override
	public String toString() {
		return "EntitySectionFieldSpec [sectionFieldSpecId=" + sectionFieldSpecId + ", entitySpecId=" + entitySpecId
				+ ", sectionSpecId=" + sectionSpecId + ", fieldLabel=" + fieldLabel + ", fieldType=" + fieldType
				+ ", fieldTypeExtra=" + fieldTypeExtra + ", computedField=" + computedField + ", defaultField="
				+ defaultField + ", barcodeField=" + barcodeField + ", formula=" + formula + ", isRequired="
				+ isRequired + ", displayOrder=" + displayOrder + ", expression=" + expression + ", identifier="
				+ identifier + ", initialEntitySectionFieldSpecId=" + initialEntitySectionFieldSpecId
				+ ", skeletonEntitySectionFieldSpecId=" + skeletonEntitySectionFieldSpecId + ", createdTime="
				+ createdTime + ", modifiedTime=" + modifiedTime + ", isRemoteField=" + isRemoteField
				+ ", fieldTypeExtraForm=" + fieldTypeExtraForm + ", externalLabel=" + externalLabel
				+ ", mediaPickCondition=" + mediaPickCondition + ", locationPickCondition=" + locationPickCondition
				+ ", validationExpr=" + validationExpr + ", validationErrorMsg=" + validationErrorMsg
				+ ", computedFieldType=" + computedFieldType + ", value=" + value + ", fieldLabelError="
				+ fieldLabelError + ", fieldTypeError=" + fieldTypeError + "]";
	}
	public String getFieldTypeExtraCustomEntity() {
		return fieldTypeExtraCustomEntity;
	}
	public void setFieldTypeExtraCustomEntity(String fieldTypeExtraCustomEntity) {
		this.fieldTypeExtraCustomEntity = fieldTypeExtraCustomEntity;
	}
	
	
	
}
