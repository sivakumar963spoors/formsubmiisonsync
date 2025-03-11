package com.effort.entity;

public class AuditFormSectionFields
{

	
	private long auditParent;
	private long sectionFieldId;
	private long formId;
	private long formSpecId;
	private long sectionSpecId;
	private long sectionFieldSpecId;
	private long instanceId;
	private String fieldValue;
	private String createdTime;
	private String modifiedTime;
	private long auditBy;
	private String auditAt;
	
	public long getAuditParent() {
		return auditParent;
	}
	public void setAuditParent(long auditParent) {
		this.auditParent = auditParent;
	}
	public long getSectionFieldId() {
		return sectionFieldId;
	}
	public void setSectionFieldId(long sectionFieldId) {
		this.sectionFieldId = sectionFieldId;
	}
	public long getFormId() {
		return formId;
	}
	public void setFormId(long formId) {
		this.formId = formId;
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
	public long getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(long instanceId) {
		this.instanceId = instanceId;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public long getAuditBy() {
		return auditBy;
	}
	public void setAuditBy(long auditBy) {
		this.auditBy = auditBy;
	}
	public String getAuditAt() {
		return auditAt;
	}
	public void setAuditAt(String auditAt) {
		this.auditAt = auditAt;
	}
	


}
