package com.effort.entity;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class VisitState {
	private long visitStateId;
	private String visitStateValue;
	private long companyId;
	private Boolean startState;
	private Boolean endState;
	private Boolean revisitable;
	private Boolean mandatoryToComplete;
	private Long formSpecId;
	private Boolean formMandatory;
	private int minSubmissions;
	private int maxSubmissions;
	private boolean makeDefault;
	private String externalId;
	private String apiUserId;
	private boolean completed;
	private boolean addForm;
	private  Long skeletonVisitStateId;
	private int isSystemDefined;

	
	//for  form auto fill
	private Long id;
	private String formSpecUniqueId;
	private boolean enable;
	private long stateId;

	private boolean deleted;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public long getStateId() {
		return stateId;
	}
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

	
	public Long getSkeletonVisitStateId() {
		return skeletonVisitStateId;
	}
	public void setSkeletonVisitStateId(Long skeletonVisitStateId) {
		this.skeletonVisitStateId = skeletonVisitStateId;
	}
	public int getIsSystemDefined() {
		return isSystemDefined;
	}
	public void setIsSystemDefined(int isSystemDefined) {
		this.isSystemDefined = isSystemDefined;
	}
	public long getVisitStateId() {
		return visitStateId;
	}
	public void setVisitStateId(long visitStateId) {
		this.visitStateId = visitStateId;
	}
	public String getVisitStateValue() {
		return visitStateValue;
	}
	public void setVisitStateValue(String visitStateValue) {
		this.visitStateValue = visitStateValue;
	}
	public boolean isStartState() {
		if(startState == null){
			return false;
		}
		return startState;
	}
	public void setStartState(boolean startState) {
		this.startState = startState;
	}
	public boolean isEndState() {
		if(endState == null){
			return false;
		}
		return endState;
	}
	public void setEndState(boolean endState) {
		this.endState = endState;
	}
	public boolean isRevisitable() {
		if(revisitable == null){
			return false;
		}
		return revisitable;
	}
	public void setRevisitable(boolean revisitable) {
		this.revisitable = revisitable;
	}
	public boolean isMandatoryToComplete() {
		if(mandatoryToComplete == null){
			return false;
		}
		return mandatoryToComplete;
	}
	public void setMandatoryToComplete(boolean mandatoryToComplete) {
		this.mandatoryToComplete = mandatoryToComplete;
	}
	public Long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(Long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public boolean isFormMandatory() {
		if(formMandatory == null){
			return false;
		}
		return formMandatory;
	}
	public void setFormMandatory(boolean formMandatory) {
		this.formMandatory = formMandatory;
	}
	public int getMinSubmissions() {
		return minSubmissions;
	}
	public void setMinSubmissions(int minSubmissions) {
		this.minSubmissions = minSubmissions;
	}
	public int getMaxSubmissions() {
		return maxSubmissions;
	}
	public void setMaxSubmissions(int maxSubmissions) {
		this.maxSubmissions = maxSubmissions;
	}
	public boolean isMakeDefault() {
		return makeDefault;
	}
	public void setMakeDefault(boolean makeDefault) {
		this.makeDefault = makeDefault;
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
	
	
	
	
	@JsonIgnore
	public long getCompanyId() {
		return companyId;
	}
	@JsonIgnore
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	@JsonIgnore
	public boolean isCompleted() {
		return completed;
	}
	@JsonIgnore
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	@JsonIgnore
	public boolean isAddForm() {
		return addForm;
	}
	@JsonIgnore
	public void setAddForm(boolean addForm) {
		this.addForm = addForm;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof VisitState){
			VisitState visitState = (VisitState) obj;
			return visitState.getVisitStateId() == this.visitStateId;
		} else {
			return super.equals(obj);
		}
	}
	
	public String toCSV() {
		return "[visitStateId=" + visitStateId
				+ ", visitStateValue=" + visitStateValue + ", companyId="
				+ companyId + ", endState=" + endState + ", formSpecId="
				+ formSpecId + ", formMandatory=" + formMandatory
				+ ", externalId=" + externalId + ", apiUserId=" + apiUserId
				+ "]";
	}
	
	

	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
}
