package com.effort.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;


public class CustomerField {
	public static final int SYSTEM_DEFINED_CUSTOMER_TYPE_ID = 23;
	public static final String SYSTEM_DEFINED_CUSTOMER_TYPE_EXPRESSION = "SDCF23";
	private Integer id;
	private Integer customerFieldId;
	private String fieldLabel;
	private Integer enable;
	private Integer visibilityCheck;
	private Integer mandatoryCheck;
	private Integer uniqueCheck;
	private Integer order;
	private Integer disableMandatoryCheck;
	private Integer disableVisibilityCheck;
	private Integer disableUniqueCheck;
	private Long companyId;
	private String modifiedTime;
	private Long modifiedBy;
	private Integer visibilityOnCheckin;
	private String objectFieldName;
	private String columnName;
	private Integer templateOrder;
	private String fieldSelector;
	//Deva,2017-12-19
	private Integer fieldType;
	private Long fieldTypeExtra;
	private Integer customerFieldType;
	private String fieldId;
	private boolean searchField;
	private boolean skeletonField;
	private String value;
	
	public static final int STATIC_FIELD=1;
	public static final int USER_DEFINED_FIELD=2;
	
	public static final int CUSTOMER_FIELD_TYPE_CUSTOMER_NO = 2;
	public static final int MANDATORY = 1;
	
	public Integer getCustomerFieldId() {
		return customerFieldId;
	}
	public void setCustomerFieldId(Integer customerFieldId) {
		this.customerFieldId = customerFieldId;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	@JsonIgnore
	public Integer getEnable() {
		return enable;
	}
	@JsonIgnore
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Integer getVisibilityCheck() {
		return visibilityCheck;
	}
	public void setVisibilityCheck(Integer visibilityCheck) {
		this.visibilityCheck = visibilityCheck;
	}
	public Integer getMandatoryCheck() {
		return mandatoryCheck;
	}
	public void setMandatoryCheck(Integer mandatoryCheck) {
		this.mandatoryCheck = mandatoryCheck;
	}
	public Integer getUniqueCheck() {
		return uniqueCheck;
	}
	public void setUniqueCheck(Integer uniqueCheck) {
		this.uniqueCheck = uniqueCheck;
	}
	@JsonIgnore
	public Integer getOrder() {
		return order;
	}
	@JsonIgnore
	public void setOrder(Integer order) {
		this.order = order;
	}
	@JsonIgnore
	public Integer getDisableMandatoryCheck() {
		return disableMandatoryCheck;
	}
	@JsonIgnore
	public void setDisableMandatoryCheck(Integer disableMandatoryCheck) {
		this.disableMandatoryCheck = disableMandatoryCheck;
	}
	@JsonIgnore
	public Integer getDisableVisibilityCheck() {
		return disableVisibilityCheck;
	}
	@JsonIgnore
	public void setDisableVisibilityCheck(Integer disableVisibilityCheck) {
		this.disableVisibilityCheck = disableVisibilityCheck;
	}
	@JsonIgnore
	public Integer getDisableUniqueCheck() {
		return disableUniqueCheck;
	}
	@JsonIgnore
	public void setDisableUniqueCheck(Integer disableUniqueCheck) {
		this.disableUniqueCheck = disableUniqueCheck;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getVisibilityOnCheckin() {
		return visibilityOnCheckin;
	}
	public void setVisibilityOnCheckin(Integer visibilityOnCheckin) {
		this.visibilityOnCheckin = visibilityOnCheckin;
	}
	public String getObjectFieldName() {
		return objectFieldName;
	}
	public void setObjectFieldName(String objectFieldName) {
		this.objectFieldName = objectFieldName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Integer getTemplateOrder() {
		return templateOrder;
	}
	public void setTemplateOrder(Integer templateOrder) {
		this.templateOrder = templateOrder;
	}
	public String getFieldSelector() {
		return fieldSelector;
	}
	public void setFieldSelector(String fieldSelector) {
		this.fieldSelector = fieldSelector;
	}
	
	public Integer getFieldType() {
		return fieldType;
	}
	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}
	@JsonIgnore
	public Long getFieldTypeExtra() {
		return fieldTypeExtra;
	}
	@JsonIgnore
	public void setFieldTypeExtra(Long fieldTypeExtra) {
		this.fieldTypeExtra = fieldTypeExtra;
	}
	@JsonIgnore
	public Integer getCustomerFieldType() {
		return customerFieldType;
	}
	@JsonIgnore
	public void setCustomerFieldType(Integer customerFieldType) {
		this.customerFieldType = customerFieldType;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public boolean isSearchField() {
		return searchField;
	}
	public void setSearchField(boolean searchField) {
		this.searchField = searchField;
	}
	public boolean isSkeletonField() {
		return skeletonField;
	}
	public void setSkeletonField(boolean skeletonField) {
		this.skeletonField = skeletonField;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
