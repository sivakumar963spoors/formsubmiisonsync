package com.effort.entity;


public class FormFieldsColorDependencyCriterias {
	
	public static final int DEPENDENT_ON_OWN_FIELD = 1;
	public static final int DEPENDENT_ON_OTHER_FIELD = 2;
	
	private Long id;
	private Long formSpecId;
	private Long fieldSpecId;
	private Integer fieldType;
	private Integer dependencyType;
	private String targetFieldExpression;
	private String value;
	private String otherValue;
	private Integer fieldDataType;
	private Integer condition;
	private String backgroundColor;
	private String fieldExpression1;
	private String fieldExpression2;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Integer getFieldType() {
		return fieldType;
	}
	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
	public Integer getDependencyType() {
		return dependencyType;
	}
	public void setDependencyType(Integer dependencyType) {
		this.dependencyType = dependencyType;
	}
	public String getTargetFieldExpression() {
		return targetFieldExpression;
	}
	public void setTargetFieldExpression(String targetFieldExpression) {
		this.targetFieldExpression = targetFieldExpression;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOtherValue() {
		return otherValue;
	}
	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}
	public Integer getFieldDataType() {
		return fieldDataType;
	}
	public void setFieldDataType(Integer fieldDataType) {
		this.fieldDataType = fieldDataType;
	}
	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getFieldExpression1() {
		return fieldExpression1;
	}
	public void setFieldExpression1(String fieldExpression1) {
		this.fieldExpression1 = fieldExpression1;
	}
	public String getFieldExpression2() {
		return fieldExpression2;
	}
	public void setFieldExpression2(String fieldExpression2) {
		this.fieldExpression2 = fieldExpression2;
	}
	
	
	
}
