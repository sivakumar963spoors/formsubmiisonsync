package com.effort.entity;



public class RichTextFormSectionField {
	
	private long richTextSectionFieldId;
	private long formSpecId;
	private long sectionSpecId;
	private long sectionFieldSpecId;
	private long formId;
	private String value;
	private Integer instanceId;
	
	public RichTextFormSectionField() {}
	public RichTextFormSectionField(long formSpecId,long sectionSpecId,long sectionFieldSpecId,long formId,String value,Integer instanceId) {
		this.formSpecId = formSpecId;
		this.sectionSpecId = sectionSpecId;
		this.sectionFieldSpecId = sectionFieldSpecId;
		this.formId = formId;
		this.value = value;
		this.setInstanceId(instanceId);
	}
	
	public long getRichTextSectionFieldId() {
		return richTextSectionFieldId;
	}
	public void setRichTextSectionFieldId(long richTextSectionFieldId) {
		this.richTextSectionFieldId = richTextSectionFieldId;
	}
	public long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public long getSectionSpecId() {
		return sectionSpecId;
	}
	public void setSectionSpecId(long sectionSpecId) {
		this.sectionSpecId = sectionSpecId;
	}
	public long getSectionFieldSpecId() {
		return sectionFieldSpecId;
	}
	public void setSectionFieldSpecId(long sectionFieldSpecId) {
		this.sectionFieldSpecId = sectionFieldSpecId;
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
	public Integer getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}
	
	
	

}
