package com.effort.entity;


import java.util.List;

public class CustomEntitySpecStockConfiguration {

	public static final String mobileTableName = "custom_entity_spec_stock_configuration";

	private Long customEntitySpecStockConfigurationId;
	private boolean enableStockManagement = true;
	private String stockOverRideFormUniqueId;
	private String stockIncrementFormUniqueId;
	private String stockDecrementFormUniqueId;
	private Long companyId;
	private String createdBy;
	private String modifiedBy;
	private String createdTime;
	private String modifiedTime;
	private boolean managerApproval;
	private String customEntitySpecId;
	private String sectionSpecUniqueId;

	
	public Long getCustomEntitySpecStockConfigurationId() {
		return customEntitySpecStockConfigurationId;
	}

	public void setCustomEntitySpecStockConfigurationId(Long customEntitySpecStockConfigurationId) {
		this.customEntitySpecStockConfigurationId = customEntitySpecStockConfigurationId;
	}

	public boolean isEnableStockManagement() {
		return enableStockManagement;
	}

	public void setEnableStockManagement(boolean enableStockManagement) {
		this.enableStockManagement = enableStockManagement;
	}

	public String getStockOverRideFormUniqueId() {
		return stockOverRideFormUniqueId;
	}

	public void setStockOverRideFormUniqueId(String stockOverRideFormUniqueId) {
		this.stockOverRideFormUniqueId = stockOverRideFormUniqueId;
	}

	public String getStockIncrementFormUniqueId() {
		return stockIncrementFormUniqueId;
	}

	public void setStockIncrementFormUniqueId(String stockIncrementFormUniqueId) {
		this.stockIncrementFormUniqueId = stockIncrementFormUniqueId;
	}

	public String getStockDecrementFormUniqueId() {
		return stockDecrementFormUniqueId;
	}

	public void setStockDecrementFormUniqueId(String stockDecrementFormUniqueId) {
		this.stockDecrementFormUniqueId = stockDecrementFormUniqueId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}


	public boolean isManagerApproval() {
		return managerApproval;
	}

	public void setManagerApproval(boolean managerApproval) {
		this.managerApproval = managerApproval;
	}

	public String getCustomEntitySpecId() {
		return customEntitySpecId;
	}

	public void setCustomEntitySpecId(String customEntitySpecId) {
		this.customEntitySpecId = customEntitySpecId;
	}

	public String getSectionSpecUniqueId() {
		return sectionSpecUniqueId;
	}

	public void setSectionSpecUniqueId(String sectionSpecUniqueId) {
		this.sectionSpecUniqueId = sectionSpecUniqueId;
	}
	
	

	
}