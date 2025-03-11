package com.effort.entity;


public class ListFilteringCritiria {
	
    private long    formSpecId;
    private long	fieldSpecId;
    private long	listFieldSpecId ; 
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
	public long getListFieldSpecId() {
		return listFieldSpecId;
	}
	public void setListFieldSpecId(long listFieldSpecId) {
		this.listFieldSpecId = listFieldSpecId;
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
}
