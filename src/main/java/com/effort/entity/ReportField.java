package com.effort.entity;


public class ReportField {

	private String fieldLabel;
	private Integer fieldType;
	private Integer displayOrder;
	private Integer identifier;
	private String displayValue;
	private String uniqueId;
	private Long initialFieldSpecId;
	private Long skeletonFieldSpecId;
	private Integer isVisible;

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public Integer getFieldType() {
		return fieldType;
	}

	public void setFieldType(Integer fieldType) {
		this.fieldType = fieldType;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Long getInitialFieldSpecId() {
		return initialFieldSpecId;
	}

	public void setInitialFieldSpecId(Long initialFieldSpecId) {
		this.initialFieldSpecId = initialFieldSpecId;
	}

	public Long getSkeletonFieldSpecId() {
		return skeletonFieldSpecId;
	}

	public void setSkeletonFieldSpecId(Long skeletonFieldSpecId) {
		this.skeletonFieldSpecId = skeletonFieldSpecId;
	}

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	@Override
	public String toString() {
		return "ReportFields [fieldLabel=" + fieldLabel + ", fieldType="
				+ fieldType + ", displayOrder=" + displayOrder
				+ ", identifier=" + identifier + ", displayValue="
				+ displayValue + ", uniqueId=" + uniqueId + "]";
	}

}
