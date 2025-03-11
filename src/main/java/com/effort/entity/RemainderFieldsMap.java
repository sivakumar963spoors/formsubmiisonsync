package com.effort.entity;



public class RemainderFieldsMap {

	 private Long id;
	 private Long formSpecId;
	 private Long fieldSpecId;
	 private Long referenceFieldSpecId ; 
	 private String	referenceFieldExpressionId;
	 
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
	public Long getReferenceFieldSpecId() {
		return referenceFieldSpecId;
	}
	public void setReferenceFieldSpecId(Long referenceFieldSpecId) {
		this.referenceFieldSpecId = referenceFieldSpecId;
	}
	public String getReferenceFieldExpressionId() {
		return referenceFieldExpressionId;
	}
	public void setReferenceFieldExpressionId(String referenceFieldExpressionId) {
		this.referenceFieldExpressionId = referenceFieldExpressionId;
	}
	 
	 
	    
}
