package com.effort.entity;


import java.util.List;

import com.effort.util.Api;


public class ExtraProperties implements Cloneable{
	
	public static String WORK_FORM_TYPE_NEXT_ACTION="1";
	public static String WORK_FORM_TYPE_ATTACHMENT="2";
	
	//these two fields are used for creating  or updating work status
	private String workId;
	private String workActionSpecId;
	//these two fields are used for creating new Work From  Existing Work
	private String parentWorkId;
	private String parentWorkActionSpecId;
	//these field is used for to know work action type
	private String workActionType;
	
	private Integer frequency;
	private Boolean checkRecurring=false;
	private Integer eventCount;
	private String recurringFromDate;
	
	private Long recurringParentId;
	private Boolean isTemplate=false;
	
	private Integer applyEventType;
	
	private Boolean isWebLiteSignIn=false;
	private Boolean isWebLiteSignOut=false;
	private boolean isAutoSignOut=false;
	private boolean autoSignInInWeblite = false;
	private long autoSignInFormSpecUniqueId;
	private boolean isAutoSignIn=false;
	
	private Long checkInId;
	private boolean forceActivity;
	
	private List<JmsMessage> jmsMessages;
	private boolean addJmsMessages;

	
	//below 3 fields are used for hcl work creation through api service order duplication 
	private String externalId; // serviceOrderNumber
	private String apiUserId;
	private String archiveExternalId;
	private boolean isTelenorWork;
	private List<Employee> underEmployees;
	private int recurringEndsNever;
	private String recurringEndDate;
	
	private String clientSideId;
	private String clientCode;
	
	private String workFieldsUniqueKey;
	
	private Long workSpecId;
	private boolean addToLTFApplicationWorkDetails;
	private String externalWorkSpecId;
	
	private String recurrenceDays;
	private int bulkActionPerform;
	private boolean fromMigrationFields;
	
	private boolean formInstance;
	
	private String customEntityFieldsUniqueKey;
	
	public Integer getApplyEventType() {
		return applyEventType;
	}
	public void setApplyEventType(Integer applyEventType) {
		this.applyEventType = applyEventType;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getWorkActionSpecId() {
		return workActionSpecId;
	}
	public void setWorkActionSpecId(String workActionSpecId) {
		this.workActionSpecId = workActionSpecId;
	}
	public String getParentWorkId() {
		return parentWorkId;
	}
	public void setParentWorkId(String parentWorkId) {
		this.parentWorkId = parentWorkId;
	}
	public String getParentWorkActionSpecId() {
		return parentWorkActionSpecId;
	}
	public void setParentWorkActionSpecId(String parentWorkActionSpecId) {
		this.parentWorkActionSpecId = parentWorkActionSpecId;
	}
	public String getWorkActionType() {
		return workActionType;
	}
	public void setWorkActionType(String workActionType) {
		this.workActionType = workActionType;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public Boolean getCheckRecurring() {
		return checkRecurring;
	}
	public void setCheckRecurring(Boolean checkRecurring) {
		this.checkRecurring = checkRecurring;
	}
	public Integer getEventCount() {
		return eventCount;
	}
	public void setEventCount(Integer eventCount) {
		this.eventCount = eventCount;
	}
	public String getRecurringFromDate() {
		return recurringFromDate;
	}
	public void setRecurringFromDate(String recurringFromDate) {
		this.recurringFromDate = recurringFromDate;
	}
	public Long getRecurringParentId() {
		return recurringParentId;
	}
	public void setRecurringParentId(Long recurringParentId) {
		this.recurringParentId = recurringParentId;
	}
	public Boolean getIsTemplate() {
		return isTemplate;
	}
	public void setIsTemplate(Boolean isTemplate) {
		this.isTemplate = isTemplate;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getApiUserId() {
		return apiUserId;
	}
	public void setApiUserId(String apiUserId) {
		this.apiUserId = apiUserId;
	}
	public String getArchiveExternalId() {
		return archiveExternalId;
	}
	public void setArchiveExternalId(String archiveExternalId) {
		this.archiveExternalId = archiveExternalId;
	}
	public boolean isTelenorWork() {
		return isTelenorWork;
	}
	public void setTelenorWork(boolean isTelenorWork) {
		this.isTelenorWork = isTelenorWork;
	}
	public List<Employee> getUnderEmployees() {
		return underEmployees;
	}
	public void setUnderEmployees(List<Employee> underEmployees) {
		this.underEmployees = underEmployees;
	}
	public int getRecurringEndsNever() {
		return recurringEndsNever;
	}
	public void setRecurringEndsNever(int recurringEndsNever) {
		this.recurringEndsNever = recurringEndsNever;
	}
	public String getClientSideId() {
		return clientSideId;
	}
	public void setClientSideId(String clientSideId) {
		this.clientSideId = clientSideId;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getWorkFieldsUniqueKey() {
		return workFieldsUniqueKey;
	}
	public void setWorkFieldsUniqueKey(String workFieldsUniqueKey) {
		this.workFieldsUniqueKey = workFieldsUniqueKey;
	}
	public Long getWorkSpecId() {
		return workSpecId;
	}
	public void setWorkSpecId(Long workSpecId) {
		this.workSpecId = workSpecId;
	}
	public boolean isAddToLTFApplicationWorkDetails() {
		return addToLTFApplicationWorkDetails;
	}
	public void setAddToLTFApplicationWorkDetails(boolean addToLTFApplicationWorkDetails) {
		this.addToLTFApplicationWorkDetails = addToLTFApplicationWorkDetails;
	}
	public String getExternalWorkSpecId() {
		return externalWorkSpecId;
	}
	public void setExternalWorkSpecId(String externalWorkSpecId) {
		this.externalWorkSpecId = externalWorkSpecId;
	}
	public String getRecurrenceDays() {
		return recurrenceDays;
	}
	public void setRecurrenceDays(String recurrenceDays) {
		this.recurrenceDays = recurrenceDays;
	}
	public Boolean getIsWebLiteSignIn() {
		return isWebLiteSignIn;
	}
	public void setIsWebLiteSignIn(Boolean isWebLiteSignIn) {
		this.isWebLiteSignIn = isWebLiteSignIn;
	}
	public Boolean getIsWebLiteSignOut() {
		return isWebLiteSignOut;
	}
	public void setIsWebLiteSignOut(Boolean isWebLiteSignOut) {
		this.isWebLiteSignOut = isWebLiteSignOut;
	}
	public Long getCheckInId() {
		return checkInId;
	}
	public void setCheckInId(Long checkInId) {
		this.checkInId = checkInId;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	public int getBulkActionPerform() {
		return bulkActionPerform;
	}
	public void setBulkActionPerform(int bulkActionPerform) {
		this.bulkActionPerform = bulkActionPerform;
	}
	public boolean isForceActivity() {
		return forceActivity;
	}
	public void setForceActivity(boolean forceActivity) {
		this.forceActivity = forceActivity;
	}
	public boolean isFromMigrationFields() {
		return fromMigrationFields;
	}
	public void setFromMigrationFields(boolean fromMigrationFields) {
		this.fromMigrationFields = fromMigrationFields;
	}
	public String getRecurringEndDate() {
		if(Api.isEmptyString(recurringEndDate)) {
			recurringEndDate = null;
		}
		return recurringEndDate;
	}
	public void setRecurringEndDate(String recurringEndDate) {
		if(Api.isEmptyString(recurringEndDate)) {
			recurringEndDate = null;
		}
		this.recurringEndDate = recurringEndDate;
	}
	public boolean isFormInstance() {
		return formInstance;
	}
	public void setFormInstance(boolean formInstance) {
		this.formInstance = formInstance;
	}
	public String getCustomEntityFieldsUniqueKey() {
		return customEntityFieldsUniqueKey;
	}
	public void setCustomEntityFieldsUniqueKey(String customEntityFieldsUniqueKey) {
		this.customEntityFieldsUniqueKey = customEntityFieldsUniqueKey;
	}
	public boolean isAutoSignOut() {
		return isAutoSignOut;
	}
	public void setAutoSignOut(boolean isAutoSignOut) {
		this.isAutoSignOut = isAutoSignOut;
	}
	public boolean isAutoSignInInWeblite() {
		return autoSignInInWeblite;
	}
	public void setAutoSignInInWeblite(boolean autoSignInInWeblite) {
		this.autoSignInInWeblite = autoSignInInWeblite;
	}
	public boolean isAutoSignIn() {
		return isAutoSignIn;
	}
	public void setAutoSignIn(boolean isAutoSignIn) {
		this.isAutoSignIn = isAutoSignIn;
	}
	public long getAutoSignInFormSpecUniqueId() {
		return autoSignInFormSpecUniqueId;
	}
	public void setAutoSignInFormSpecUniqueId(long autoSignInFormSpecUniqueId) {
		this.autoSignInFormSpecUniqueId = autoSignInFormSpecUniqueId;
	}
	public List<JmsMessage> getJmsMessages() {
		return jmsMessages;
	}
	public void setJmsMessages(List<JmsMessage> jmsMessages) {
		this.jmsMessages = jmsMessages;
	}
	public boolean isAddJmsMessages() {
		return addJmsMessages;
	}
	public void setAddJmsMessages(boolean addJmsMessages) {
		this.addJmsMessages = addJmsMessages;
	}
	
	
	

}
