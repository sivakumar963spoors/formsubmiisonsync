package com.effort.entity;


public class VisibilityDependencyCriteria {
	private long id;
	private long formSpecId;
	private long fieldSpecId;
	private int fieldType;
	private String targetFieldExpression;
	private int fieldDataType;
	private String valueIds;
	private String value;
	private int condition;
	private int visibilityType;
	private int conjunction=1;
	
	private String destinationFieldExpression;
	
	//This key is used for web
	private boolean ignoreHiddenFields;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public int getFieldType() {
		return fieldType;
	}
	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}
	public String getTargetFieldExpression() {
		return targetFieldExpression;
	}
	public void setTargetFieldExpression(String targetFieldExpression) {
		this.targetFieldExpression = targetFieldExpression;
	}
	public int getFieldDataType() {
		return fieldDataType;
	}
	public void setFieldDataType(int fieldDataType) {
		this.fieldDataType = fieldDataType;
	}
	public String getValueIds() {
		return valueIds;
	}
	public void setValueIds(String valueIds) {
		this.valueIds = valueIds;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}
	public int getVisibilityType() {
		return visibilityType;
	}
	public void setVisibilityType(int visibilityType) {
		this.visibilityType = visibilityType;
	}
	public int getConjunction() {
		return conjunction;
	}
	public void setConjunction(int conjunction) {
		this.conjunction = conjunction;
	}
	
	public boolean isIgnoreHiddenFields() {
		return ignoreHiddenFields;
	}
	public void setIgnoreHiddenFields(boolean ignoreHiddenFields) {
		this.ignoreHiddenFields = ignoreHiddenFields;
	}
	public String getDestinationFieldExpression() {
		return destinationFieldExpression;
	}
	public void setDestinationFieldExpression(String destinationFieldExpression) {
		this.destinationFieldExpression = destinationFieldExpression;
	}
	
	
	
	
}
