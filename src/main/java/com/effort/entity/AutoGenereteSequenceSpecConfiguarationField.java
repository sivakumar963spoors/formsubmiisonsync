package com.effort.entity;


public class AutoGenereteSequenceSpecConfiguarationField {
	
	private long id;
	private Long autoGenereteSequenceSpecConfigId;
	private String formSpecUniqueId;
	private String fieldSpecUniqueId;
	private Integer fieldDataType;
	private String inputFormFieldExpression	;
	private String prefixFormFieldExpression;
	private String sufixFormFieldExpression;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getAutoGenereteSequenceSpecConfigId() {
		return autoGenereteSequenceSpecConfigId;
	}
	public void setAutoGenereteSequenceSpecConfigId(Long autoGenereteSequenceSpecConfigId) {
		this.autoGenereteSequenceSpecConfigId = autoGenereteSequenceSpecConfigId;
	}
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}
	public String getFieldSpecUniqueId() {
		return fieldSpecUniqueId;
	}
	public void setFieldSpecUniqueId(String fieldSpecUniqueId) {
		this.fieldSpecUniqueId = fieldSpecUniqueId;
	}
	public Integer getFieldDataType() {
		return fieldDataType;
	}
	public void setFieldDataType(Integer fieldDataType) {
		this.fieldDataType = fieldDataType;
	}
	public String getInputFormFieldExpression() {
		return inputFormFieldExpression;
	}
	public void setInputFormFieldExpression(String inputFormFieldExpression) {
		this.inputFormFieldExpression = inputFormFieldExpression;
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
