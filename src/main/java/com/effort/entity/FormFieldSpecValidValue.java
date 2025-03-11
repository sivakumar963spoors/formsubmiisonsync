package com.effort.entity;


public class FormFieldSpecValidValue {
	private long valueId;
	private long fieldSpecId;
	private String value;
	
	public long getValueId() {
		return valueId;
	}
	public void setValueId(long valueId) {
		this.valueId = valueId;
	}
	public long getFieldSpecId() {
		return fieldSpecId;
	}
	public void setFieldSpecId(long fieldSpecId) {
		this.fieldSpecId = fieldSpecId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
