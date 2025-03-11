package com.effort.entity;



public class FieldSpecFilter {

	public static final int EMPLOYEE_ROLE_FILTER = 1;
	
	public static final int FIELD_IS_FORMFIELD=1;
	public static final int FIELD_IS_SECTIONFIELD=2;
	
	long id;
	long formSpecId;
	long formSectionFieldSpecId;
	long formFieldSpecId;
	int type;//this will tell type of filter like employee role filter etc
	long  valueId;
	int formFieldType;//it will tell whether field is a section field or form field
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public long getFormSectionFieldSpecId() {
		return formSectionFieldSpecId;
	}
	public void setFormSectionFieldSpecId(long formSectionFieldSpecId) {
		this.formSectionFieldSpecId = formSectionFieldSpecId;
	}
	public long getFormFieldSpecId() {
		return formFieldSpecId;
	}
	public void setFormFieldSpecId(long formFieldSpecId) {
		this.formFieldSpecId = formFieldSpecId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getValueId() {
		return valueId;
	}
	public void setValueId(long valueId) {
		this.valueId = valueId;
	}
	public int getFormFieldType() {
		return formFieldType;
	}
	public void setFormFieldType(int formFieldType) {
		this.formFieldType = formFieldType;
	}
	
}
