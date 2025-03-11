package com.effort.entity;

public class CustomerAutoFilteringCritiria {

	private long formSpecId;
	private long fieldSpecId;
	private String customerFieldSpecUniqueId;
	private String referenceFieldExpressionId;
	private String value;
	private int condition;
	private int type;
	private int staticField;
	private String errorMsg;
	private boolean onlineSearch;
	private int conjunction;

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

	public String getCustomerFieldSpecUniqueId() {
		return customerFieldSpecUniqueId;
	}

	public void setCustomerFieldSpecUniqueId(String customerFieldSpecUniqueId) {
		this.customerFieldSpecUniqueId = customerFieldSpecUniqueId;
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

	public int getStaticField() {
		return staticField;
	}

	public void setStaticField(int staticField) {
		this.staticField = staticField;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isOnlineSearch() {
		return onlineSearch;
	}

	public void setOnlineSearch(boolean onlineSearch) {
		this.onlineSearch = onlineSearch;
	}

	public int getConjunction() {
		return conjunction;
	}

	public void setConjunction(int conjunction) {
		this.conjunction = conjunction;
	}
	
	

}

