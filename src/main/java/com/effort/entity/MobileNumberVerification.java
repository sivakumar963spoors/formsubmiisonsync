package com.effort.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

public class MobileNumberVerification {
	
	public static final int MOBILE_NUMBER_INVALID = -4;
	public static final int INVALID_OTP = -3;
	public static final int OTP_EXPIRED = -2;
	public static final int OTP_VERIFIED = 1;
	public static final int OTP_SENT = 0;
	public static final int OTP_NOT_SENT = -5;

	private int id;
	private Long formSpecId;
	private Long fieldSpecId;
	private Long sectionFieldSpecId;
	private String clientSideId;
	private int type;
	private String clientCode;
	private Long empId;
	private Long companyId;
	private Long phoneNo;
	private int otp;
	private int expiryTimeInSeconds;
	private String createdTime;
	private String modifiedTime;
	private int status;
	private String verifiedTime;
	private String uniqueId;
	private FormAndFieldData formData;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(Long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public Long getFieldSpecId() {
		return fieldSpecId;
	}
	public void setFieldSpecId(Long fieldSpecId) {
		this.fieldSpecId = fieldSpecId;
	}
	public Long getSectionFieldSpecId() {
		return sectionFieldSpecId;
	}
	public void setSectionFieldSpecId(Long sectionFieldSpecId) {
		this.sectionFieldSpecId = sectionFieldSpecId;
	}
	public String getClientSideId() {
		return clientSideId;
	}
	public void setClientSideId(String clientSideId) {
		this.clientSideId = clientSideId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	public int getExpiryTimeInSeconds() {
		return expiryTimeInSeconds;
	}
	public void setExpiryTimeInSeconds(int expiryTimeInSeconds) {
		this.expiryTimeInSeconds = expiryTimeInSeconds;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getVerifiedTime() {
		return verifiedTime;
	}
	public void setVerifiedTime(String verifiedTime) {
		this.verifiedTime = verifiedTime;
	}
	public FormAndFieldData getFormData() {
		return formData;
	}
	public void setFormData(FormAndFieldData formData) {
		this.formData = formData;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
		
}
