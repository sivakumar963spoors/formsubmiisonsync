package com.effort.entity;

public class Settings {

	private Long id;
	private Long companyId;
	private Long empId;
	private String key;
	private String value;
	private String createdTime;
	private String modifiedTime;
	private String syncKey;
	private String settingLabel;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	@Override
	public String toString() {
		return "Settings [id=" + id + ", companyId=" + companyId + ", empId="
				+ empId + ", key=" + key + ", value=" + value
				+ ", createdTime=" + createdTime + ", modifiedTime="
				+ modifiedTime + "]";
	}
	public String getSyncKey() {
		return syncKey;
	}
	public void setSyncKey(String syncKey) {
		this.syncKey = syncKey;
	}
	public String getSettingLabel() {
		return settingLabel;
	}
	public void setSettingLabel(String settingLabel) {
		this.settingLabel = settingLabel;
	}
	
	
}
