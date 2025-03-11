package com.effort.entity;

public class AuditFormFields {


	private long auditParent;
	private long fieldId;
	private long formId;
	private long formSpecId;
	private long fieldSpecId;
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
	public long getFieldId() {
		return fieldId;
	}
	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
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
	public long getFieldSpecId() {
		return fieldSpecId;
	}
	public void setFieldSpecId(long fieldSpecId) {
		this.fieldSpecId = fieldSpecId;
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

