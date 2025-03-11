package com.effort.entity;



public class FieldValidationCritiria {

	private long    formSpecId;
    private long	fieldSpecId;
    private String	referenceFieldExpressionId;
    private String	value;
    private int condition;
    private int validationType;
    private String validationMessage;
    private boolean isSectionField;
    private int conjunction=1;
    
    private String value2;
    private String referenceFieldExpressionId2;
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
	public int getValidationType() {
		return validationType;
	}
	public void setValidationType(int validationType) {
		this.validationType = validationType;
	}
	public String getValidationMessage() {
		return validationMessage;
	}
	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}
	public boolean isSectionField() {
		return isSectionField;
	}
	public void setIsSectionField(boolean isSectionField) {
		this.isSectionField = isSectionField;
	}
	public int getConjunction() {
		return conjunction;
	}
	public void setConjunction(int conjunction) {
		this.conjunction = conjunction;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getReferenceFieldExpressionId2() {
		return referenceFieldExpressionId2;
	}
	public void setReferenceFieldExpressionId2(String referenceFieldExpressionId2) {
		this.referenceFieldExpressionId2 = referenceFieldExpressionId2;
	}
    
}
