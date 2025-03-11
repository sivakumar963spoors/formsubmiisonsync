package com.effort.entity;



public class EmployeeRequisitionForm {

	private long id;
	private int companyId;
	private Long configurationId;
	private Long workFlowFormStatusId;
	private long formId;
	private Long empId;
	private int processingStatus;
	private int processingResult;
	
	private int validationStatus;
	private long processingTime;
	private String remarks;
	private Integer triggerEvent; 
	private String createdTime;
	private long filledBy;
	private String filledByName;
	
	
	public static final int PROCESSING_STATUS = 5;
	public static final int PROCESSED_STATUS = 1;
	public static final int UNPROCESSED_STATUS = 0;
	
	public static final int SUCCESS_RESULT = 1;
	public static final int FAILED_RESULT = -1;
	public static final int OLD_RECORD_EXCLUDED = -2;
	
	public static final int VALIDATION_SUCCESS_RESULT = 1;
	public static final int VALIDATION_FAILED_RESULT = -1;
	public static final int VALIDATION_NOTDONE_RESULT = 0;
	
	public static final int TRIGGER_EVENT_TYPE_APPROVED_FORM = 1;
	public static final int TRIGGER_EVENT_TYPE_SUBMITTED_FORM = 2;
	
	public EmployeeRequisitionForm(){
		
	}
	
	public EmployeeRequisitionForm(Integer companyId,Long workFlowFormStatusId,Long formId,
			Integer triggerEvent,int processingStatus,Long configurationId){
		this.companyId=companyId;
		this.workFlowFormStatusId=workFlowFormStatusId;
		this.formId=formId;
		this.triggerEvent = triggerEvent;
		this.processingStatus=processingStatus;
		this.configurationId=configurationId;
	}
	
	public EmployeeRequisitionForm(Integer triggerEvent,Long empId,int processingStatus,int processingResult,
			long processingTime,String remarks,Long formId,Integer companyId,Integer validationStatus){
		if(triggerEvent != null)
			this.triggerEvent = triggerEvent;
		if(empId != null)
			this.empId=empId;
		this.processingStatus=processingStatus;
		this.processingResult=processingResult;
		this.processingTime=processingTime;
		this.remarks=remarks;
		if(formId != null)
			this.formId=formId;
		if(companyId != null)
			this.companyId=companyId;
		if(validationStatus != null)
			this.validationStatus=validationStatus;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFormId() {
		return formId;
	}
	public void setFormId(long formId) {
		this.formId = formId;
	}
	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	
	public int getProcessingStatus() {
		return processingStatus;
	}
	public void setProcessingStatus(int processingStatus) {
		this.processingStatus = processingStatus;
	}
	public int getProcessingResult() {
		return processingResult;
	}
	public void setProcessingResult(int processingResult) {
		this.processingResult = processingResult;
	}
	public long getProcessingTime() {
		return processingTime;
	}
	public void setProcessingTime(long processingTime) {
		this.processingTime = processingTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	public Long getWorkFlowFormStatusId() {
		return workFlowFormStatusId;
	}

	public void setWorkFlowFormStatusId(Long workFlowFormStatusId) {
		this.workFlowFormStatusId = workFlowFormStatusId;
	}

	public Integer getTriggerEvent() {
		return triggerEvent;
	}
	public void setTriggerEvent(Integer triggerEvent) {
		this.triggerEvent = triggerEvent;
	}
	
	public int getValidationStatus() {
		return validationStatus;
	}

	public void setValidationStatus(int validationStatus) {
		this.validationStatus = validationStatus;
	}

	public Long getConfigurationId() {
		return configurationId;
	}

	public void setConfigurationId(Long configurationId) {
		this.configurationId = configurationId;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public long getFilledBy() {
		return filledBy;
	}

	public void setFilledBy(long filledBy) {
		this.filledBy = filledBy;
	}

	public String getFilledByName() {
		return filledByName;
	}

	public void setFilledByName(String filledByName) {
		this.filledByName = filledByName;
	}
	
}