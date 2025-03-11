package com.effort.entity;


public class FormCleanUpRule {
	private Long id;
	private String formSpecUniqueId;
	private Integer cleanUpType;
	private String targetFieldExpression;
	private String values;
	private String value;
	private Integer targetFieldDataType;
	private Integer condition;
	private Integer conjunction;
	private String createdTime;
	private String modifiedTime;
	public Long getId() {
		return id;
	}
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}
	public Integer getCleanUpType() {
		return cleanUpType;
	}
	public String getTargetFieldExpression() {
		return targetFieldExpression;
	}
	public String getValues() {
		return values;
	}
	public String getValue() {
		return value;
	}
	public Integer getTargetFieldDataType() {
		return targetFieldDataType;
	}
	public Integer getCondition() {
		return condition;
	}
	public Integer getConjunction() {
		return conjunction;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}
	public void setCleanUpType(Integer cleanUpType) {
		this.cleanUpType = cleanUpType;
	}
	public void setTargetFieldExpression(String targetFieldExpression) {
		this.targetFieldExpression = targetFieldExpression;
	}
	public void setValues(String values) {
		this.values = values;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setTargetFieldDataType(Integer targetFieldDataType) {
		this.targetFieldDataType = targetFieldDataType;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}
	public void setConjunction(Integer conjunction) {
		this.conjunction = conjunction;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	

}
