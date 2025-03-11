package com.effort.entity;



public class WorkflowEntityVisibilityCondition {

	private long id;
	private long workflowEntityMapId;
	private boolean sectionField;
	private String targetFieldExpression;
	private String valueIds;
	private String value;
	private int fieldDataType;
	private int condition;
	private int visibilityType;
	private int conjunction;
	private String formSpecUniqueId;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getWorkflowEntityMapId() {
		return workflowEntityMapId;
	}
	public void setWorkflowEntityMapId(long workflowEntityMapId) {
		this.workflowEntityMapId = workflowEntityMapId;
	}
	public boolean isSectionField() {
		return sectionField;
	}
	public void setSectionField(boolean sectionField) {
		this.sectionField = sectionField;
	}
	public String getTargetFieldExpression() {
		return targetFieldExpression;
	}
	public void setTargetFieldExpression(String targetFieldExpression) {
		this.targetFieldExpression = targetFieldExpression;
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
	public int getFieldDataType() {
		return fieldDataType;
	}
	public void setFieldDataType(int fieldDataType) {
		this.fieldDataType = fieldDataType;
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
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}
	
	
}
