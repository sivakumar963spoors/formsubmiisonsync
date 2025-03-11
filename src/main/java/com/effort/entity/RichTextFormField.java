package com.effort.entity;


public class RichTextFormField {

	private long richTextValueId;
	private long formSpecId;
	private long fieldSpecId;
	private long formId;
	private String value;
	
	public RichTextFormField() {}
	public RichTextFormField(long formSpecId,long fieldSpecId,long formId,String value) {
		this.formSpecId = formSpecId;
		this.fieldSpecId = fieldSpecId;
		this.formId = formId;
		this.value = value;
	}
	
	public long getRichTextValueId() {
		return richTextValueId;
	}
	public void setRichTextValueId(long richTextValueId) {
		this.richTextValueId = richTextValueId;
	}
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
	public long getFormId() {
		return formId;
	}
	public void setFormId(long formId) {
		this.formId = formId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
