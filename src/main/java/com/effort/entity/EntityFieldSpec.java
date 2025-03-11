package com.effort.entity;



import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class EntityFieldSpec extends FormFieldSpecMaster implements Serializable{
	private long entityFieldSpecId;
	private long entitySpecId;
	private String fieldLabel;
	private int fieldType;
	private String fieldTypeExtra;
	private boolean computedField;
	private String formula;
	private boolean isRequired;
	private boolean requiredValue;
	private int displayOrder;
	private String expression;
	private boolean identifier;
	
	private int type;
	private String value;
	
	private int index;
	private String fieldLabelError;
	private String fieldTypeError;
	private String isRequiredError;
	private Long skeletonEntityFieldSpecId;
	
	private String externalLabel;
	private int entityFieldIdentifier;
	private boolean visibleInEntitySelectionPicker;
	
	private String fieldTypeExtraCustomEntity;
	private boolean simpleSearchable;
	private boolean advancedSearchable;
	
	public long getEntityFieldSpecId() {
		return entityFieldSpecId;
	}
	public void setEntityFieldSpecId(long entityFieldSpecId) {
		this.entityFieldSpecId = entityFieldSpecId;
	}
	public long getEntitySpecId() {
		return entitySpecId;
	}
	public void setEntitySpecId(long entitySpecId) {
		this.entitySpecId = entitySpecId;
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
	public boolean isRequiredValue() {
		return isRequired;
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
	
	@JsonIgnore
	public int getType() {
		return type;
	}
	@JsonIgnore
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
	public String getIsRequiredError() {
		return isRequiredError;
	}
	@JsonIgnore
	public void setIsRequiredError(String isRequiredError) {
		this.isRequiredError = isRequiredError;
	}

	public String toCSV() {
		               return "[entityFieldSpecId=" + entityFieldSpecId
		                               + ", entitySpecId=" + entitySpecId + ", fieldLabel="
		                               + fieldLabel + ", fieldType=" + fieldType + ", fieldTypeExtra="
		                               + fieldTypeExtra + ", isRequired=" + isRequired
		                               + ", displayOrder=" + displayOrder + ", expression="
		                               + expression + ", identifier=" + identifier + ", type=" + type
		                               + ", value=" + value + ", index=" + index
		                               + ", fieldLabelError=" + fieldLabelError + ", fieldTypeError="
		                               + fieldTypeError + ", isRequiredError=" + isRequiredError+ ", externalLabel=" + externalLabel + "+"
		                                               + ", entityFieldIdentifier=" + entityFieldIdentifier + "]";
    }

	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof EntityField){
			return getEntityFieldSpecId() == ((EntityField) obj).getEntityFieldSpecId();
		} else {
			return super.equals(obj);
		}
	}
	
	public Long getSkeletonEntityFieldSpecId() {
		return skeletonEntityFieldSpecId;
	}
	public void setSkeletonEntityFieldSpecId(Long skeletonEntityFieldSpecId) {
		this.skeletonEntityFieldSpecId = skeletonEntityFieldSpecId;
	}
	
	public String getExternalLabel() {
		return externalLabel;
	}
	public void setExternalLabel(String externalLabel) {
		this.externalLabel = externalLabel;
	}
	public int getEntityFieldIdentifier() {
		return entityFieldIdentifier;
	}
	public void setEntityFieldIdentifier(int entityFieldIdentifier) {
		this.entityFieldIdentifier = entityFieldIdentifier;
	}
	public boolean isVisibleInEntitySelectionPicker() {
		return visibleInEntitySelectionPicker;
	}
	public void setVisibleInEntitySelectionPicker(boolean visibleInEntitySelectionPicker) {
		this.visibleInEntitySelectionPicker = visibleInEntitySelectionPicker;
	}
	public String getFieldTypeExtraCustomEntity() {
		return fieldTypeExtraCustomEntity;
	}
	public void setFieldTypeExtraCustomEntity(String fieldTypeExtraCustomEntity) {
		this.fieldTypeExtraCustomEntity = fieldTypeExtraCustomEntity;
	}
	public boolean isSimpleSearchable() {
		return simpleSearchable;
	}
	public void setSimpleSearchable(boolean simpleSearchable) {
		this.simpleSearchable = simpleSearchable;
	}
	public boolean isAdvancedSearchable() {
		return advancedSearchable;
	}
	public void setAdvancedSearchable(boolean advancedSearchable) {
		this.advancedSearchable = advancedSearchable;
	}
	
	
	
}
