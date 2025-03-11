package com.effort.entity;


public class FormSpecConfigSaveOnOtpVerify {

	private String formSpecUniqueId;
	private String phoneNumFieldUniqueIdToVerifyOTP;
	private String customMessageForOTP;
	private String fieldsToVisibleInTinyLink;
	
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}
	
	public String getCustomMessageForOTP() {
		return customMessageForOTP;
	}
	public void setCustomMessageForOTP(String customMessageForOTP) {
		this.customMessageForOTP = customMessageForOTP;
	}
	
	public String getFieldsToVisibleInTinyLink() {
		return fieldsToVisibleInTinyLink;
	}
	public void setFieldsToVisibleInTinyLink(String fieldsToVisibleInTinyLink) {
		this.fieldsToVisibleInTinyLink = fieldsToVisibleInTinyLink;
	}
	public String getPhoneNumFieldUniqueIdToVerifyOTP() {
		return phoneNumFieldUniqueIdToVerifyOTP;
	}
	public void setPhoneNumFieldUniqueIdToVerifyOTP(String phoneNumFieldUniqueIdToVerifyOTP) {
		this.phoneNumFieldUniqueIdToVerifyOTP = phoneNumFieldUniqueIdToVerifyOTP;
	}
	
}
