package com.effort.entity;

public class CompanyRestApis {

	public static final int TYPE_FORM = 1;
	public static final int TYPE_WORK = 2;
	public static final int TYPE_PUNCH_IN = 3;
	public static final int TYPE_PUNCH_OUT = 4;
	public static final int TYPE_LEAVE_BALANCE = 5;
	public static final int TYPE_LEAVE_APPLIED = 6;
	public static final int TYPE_CUSTOMER_UPLOAD = 7;
	public static final int TYPE_CHECKIN_CHECKOUT = 8;
	public static final int TYPE_LEAVE_IMPORT = 10;
	
	
	public static final int POST_JSON = 1;
	public static final int POST_XML = 2;
	
	public static final int DETAILED_FORM_DATA_PUSH_TYPE = 1;
	public static final int SIMPLIFIED_FORM_DATA_PUSH_TYPE = 2;
	
	public static final int ACTIVITY_BASED_EMPLOYEE_TRACKING_LOCATION = 9;
	
	private long id;
	private int companyId;
	private String submissionUrl;
	private String modificationUrl;
	private String triggerList;
	private int type;
	private boolean ignoreSSL;
	private int postType;
	private String createdTime;
	private String modifiedTime;
	
	private Integer formDataPushType;
	
	private boolean pushFormDataOnApproval;
	
	private String escalationMailIds;
	private String escalationMailSubjectTemplate;
	private String escalationMailBodyTemplate;
	
	public static final int PUSH_EVENT_TYPE_REGULAR = 1;//REGULAR(FIRST TIME)
	public static final int PUSH_EVENT_TYPE_RETRY = 2;//RETRY(FROM SECOND TIME ONWARDS)
	public String successPattern;
	
	private boolean enable;
	private long formSpecId;
	
	private boolean enabledDataPushFailedAlert;
	private int alertStoppingDurationInHours;
	private String alertDisabledTime;
	
	private String userName;
	private String password;
	private Integer authenticationType;

	private int retryCount;
	private int retryMode;
	private int retryInterval;
	private String successResponse;
	private String emailIds;
	
	private Integer includeBase64EncodedStringForMediaFields;
	
	private String empNoForHeaderValue;
	private boolean intronLifeSciencesCompany;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getSubmissionUrl() {
		return submissionUrl;
	}

	public void setSubmissionUrl(String submissionUrl) {
		this.submissionUrl = submissionUrl;
	}

	public String getModificationUrl() {
		return modificationUrl;
	}

	public void setModificationUrl(String modificationUrl) {
		this.modificationUrl = modificationUrl;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTriggerList() {
		return triggerList;
	}

	public void setTriggerList(String triggerList) {
		this.triggerList = triggerList;
	}

	public boolean isIgnoreSSL() {
		return ignoreSSL;
	}

	public void setIgnoreSSL(boolean ignoreSSL) {
		this.ignoreSSL = ignoreSSL;
	}

	public int getPostType() {
		return postType;
	}

	public void setPostType(int postType) {
		this.postType = postType;
	}

	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getFormDataPushType() {
		return formDataPushType;
	}

	public void setFormDataPushType(Integer formDataPushType) {
		this.formDataPushType = formDataPushType;
	}

	public boolean isPushFormDataOnApproval() {
		return pushFormDataOnApproval;
	}

	public void setPushFormDataOnApproval(boolean pushFormDataOnApproval) {
		this.pushFormDataOnApproval = pushFormDataOnApproval;
	}

	public String getEscalationMailIds() {
		return escalationMailIds;
	}

	public void setEscalationMailIds(String escalationMailIds) {
		this.escalationMailIds = escalationMailIds;
	}

	public String getEscalationMailSubjectTemplate() {
		return escalationMailSubjectTemplate;
	}

	public void setEscalationMailSubjectTemplate(String escalationMailSubjectTemplate) {
		this.escalationMailSubjectTemplate = escalationMailSubjectTemplate;
	}

	public String getEscalationMailBodyTemplate() {
		return escalationMailBodyTemplate;
	}

	public void setEscalationMailBodyTemplate(String escalationMailBodyTemplate) {
		this.escalationMailBodyTemplate = escalationMailBodyTemplate;
	}

	public String getSuccessPattern() {
		return successPattern;
	}

	public void setSuccessPattern(String successPattern) {
		this.successPattern = successPattern;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public long getFormSpecId() {
		return formSpecId;
	}

	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}

	public boolean isEnabledDataPushFailedAlert() {
		return enabledDataPushFailedAlert;
	}

	public void setEnabledDataPushFailedAlert(boolean enabledDataPushFailedAlert) {
		this.enabledDataPushFailedAlert = enabledDataPushFailedAlert;
	}

	public int getAlertStoppingDurationInHours() {
		return alertStoppingDurationInHours;
	}

	public void setAlertStoppingDurationInHours(int alertStoppingDurationInHours) {
		this.alertStoppingDurationInHours = alertStoppingDurationInHours;
	}

	public String getAlertDisabledTime() {
		return alertDisabledTime;
	}

	public void setAlertDisabledTime(String alertDisabledTime) {
		this.alertDisabledTime = alertDisabledTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(Integer authenticationType) {
		this.authenticationType = authenticationType;
	}
	public int getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	public int getRetryMode() {
		return retryMode;
	}
	public void setRetryMode(int retryMode) {
		this.retryMode = retryMode;
	}
	public int getRetryInterval() {
		return retryInterval;
	}
	public void setRetryInterval(int retryInterval) {
		this.retryInterval = retryInterval;
	}
	public String getSuccessResponse() {
		return successResponse;
	}
	public void setSuccessResponse(String successResponse) {
		this.successResponse = successResponse;
	}
	public String getEmailIds() {
		return emailIds;
	}
	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}

	public Integer getIncludeBase64EncodedStringForMediaFields() {
		return includeBase64EncodedStringForMediaFields;
	}

	public void setIncludeBase64EncodedStringForMediaFields(Integer includeBase64EncodedStringForMediaFields) {
		this.includeBase64EncodedStringForMediaFields = includeBase64EncodedStringForMediaFields;
	}

	public String getEmpNoForHeaderValue() {
		return empNoForHeaderValue;
	}

	public void setEmpNoForHeaderValue(String empNoForHeaderValue) {
		this.empNoForHeaderValue = empNoForHeaderValue;
	}

	public boolean isIntronLifeSciencesCompany() {
		return intronLifeSciencesCompany;
	}

	public void setIntronLifeSciencesCompany(boolean intronLifeSciencesCompany) {
		this.intronLifeSciencesCompany = intronLifeSciencesCompany;
	}
	
	

}
