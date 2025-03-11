package com.effort.entity;

public class CustomerFilteringCritiria {
	
    private long    formSpecId;
    private long	fieldSpecId;
    private long	fieldOptionId ; 
    private String	referenceFieldExpressionId;
    private String	value;
    private int condition;
    private int type;
    
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
	public long getFieldOptionId() {
		return fieldOptionId;
	}
	public void setFieldOptionId(long fieldOptionId) {
		this.fieldOptionId = fieldOptionId;
	}
}
