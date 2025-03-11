package com.effort.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockFormConfiguration {

	private Long id;
	private String formSpecUniqueId;
	private String stockFieldSpecUniqueId;
	private Long sourceStockFieldSpecId;
	private int isStockFieldInSection = 0;
	private int condition;
	private String targetFieldUniqueId;
	private Integer companyId;
	private String createdTime;
	private String modifiedTime;
	private Integer stockFormEnabled = 0;
	
	private String stockFieldSpecUniqueIdError;
	private String sourceStockFieldSpecIdError;
	private String conditionError;
	private String targetFieldUniqueIdError;
	
	private boolean error;
	
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
	public String getStockFieldSpecUniqueId() {
		return stockFieldSpecUniqueId;
	}
	public void setStockFieldSpecUniqueId(String stockFieldSpecUniqueId) {
		this.stockFieldSpecUniqueId = stockFieldSpecUniqueId;
	}
	public int getIsStockFieldInSection() {
		return isStockFieldInSection;
	}
	public void setIsStockFieldInSection(int isStockFieldInSection) {
		this.isStockFieldInSection = isStockFieldInSection;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}
	public String getTargetFieldUniqueId() {
		return targetFieldUniqueId;
	}
	public void setTargetFieldUniqueId(String targetFieldUniqueId) {
		this.targetFieldUniqueId = targetFieldUniqueId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	public Integer getStockFormEnabled() {
		return stockFormEnabled;
	}
	public void setStockFormEnabled(Integer stockFormEnabled) {
		this.stockFormEnabled = stockFormEnabled;
	}
	public Long getSourceStockFieldSpecId() {
		return sourceStockFieldSpecId;
	}
	public void setSourceStockFieldSpecId(Long sourceStockFieldSpecId) {
		this.sourceStockFieldSpecId = sourceStockFieldSpecId;
	}
	public String getStockFieldSpecUniqueIdError() {
		return stockFieldSpecUniqueIdError;
	}
	public void setStockFieldSpecUniqueIdError(String stockFieldSpecUniqueIdError) {
		this.stockFieldSpecUniqueIdError = stockFieldSpecUniqueIdError;
	}
	public String getSourceStockFieldSpecIdError() {
		return sourceStockFieldSpecIdError;
	}
	public void setSourceStockFieldSpecIdError(String sourceStockFieldSpecIdError) {
		this.sourceStockFieldSpecIdError = sourceStockFieldSpecIdError;
	}
	public String getConditionError() {
		return conditionError;
	}
	public void setConditionError(String conditionError) {
		this.conditionError = conditionError;
	}
	public String getTargetFieldUniqueIdError() {
		return targetFieldUniqueIdError;
	}
	public void setTargetFieldUniqueIdError(String targetFieldUniqueIdError) {
		this.targetFieldUniqueIdError = targetFieldUniqueIdError;
	}
	public boolean hasError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
}
