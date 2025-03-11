package com.effort.entity;



public class FormFilteringCritiria {

	private long    formSpecId;
    private long	fieldSpecId;
    private String	formFieldSpecUniqueId ; 
    private String	referenceFieldExpressionId;
    private String	value;
    private int condition;
    private int type;
    private Integer fieldType;
    private Integer formFieldType;
    
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
	public String getFormFieldSpecUniqueId() {
		return formFieldSpecUniqueId;
	}
	public void setFormFieldSpecUniqueId(String formFieldSpecUniqueId) {
		this.formFieldSpecUniqueId = formFieldSpecUniqueId;
	}
	public String getReferenceFieldExpressionId() {
		return referenceFieldExpressionId;
	}
	public void setReferenceFieldExpressionId(String referenceFieldExpressionId) {
		this.referenceFieldExpressionId = referenceFieldExpressionId;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getFieldType() {
		return fieldType;
	}
	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
	public Integer getFormFieldType() {
		return formFieldType;
	}
	public void setFormFieldType(Integer formFieldType) {
		this.formFieldType = formFieldType;
	}
	
}
