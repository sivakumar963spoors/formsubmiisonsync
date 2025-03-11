package com.effort.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EntitySectionField implements Serializable {

	private static final long serialVersionUID = 1L;

	private long sectionFieldId;
	private long entityId;
	private long entitySpecId;
	private long sectionSpecId;
	private long sectionFieldSpecId;
	private int instanceId;
	private String fieldValue;
	private Long initialEntitySectionFieldSpecId;
	private Long skeletonEntitySectionFieldSpecId;
	private String createdTime;
	private String modifiedTime;
	private String fieldLabel;
	private Integer fieldType;
	private Integer displayOrder;
	private Integer identifier;
	private String displayValue;
	
	private int rowId;
	private String clientSideId;
	private String fieldValueSubstitute;

	public String getClientSideId() {
		return clientSideId;
	}

	public void setClientSideId(String clientSideId) {
		this.clientSideId = clientSideId;
	}

	public String getFieldValueSubstitute() {
		return fieldValueSubstitute;
	}

	public void setFieldValueSubstitute(String fieldValueSubstitute) {
		this.fieldValueSubstitute = fieldValueSubstitute;
	}

	private String error;
	public long getSectionFieldId() {
		return sectionFieldId;
	}

	public void setSectionFieldId(long sectionFieldId) {
		this.sectionFieldId = sectionFieldId;
	}

	public long getEntityId() {
		return entityId;
	}

	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	public long getEntitySpecId() {
		return entitySpecId;
	}

	public void setEntitySpecId(long entitySpecId) {
		this.entitySpecId = entitySpecId;
	}

	public long getSectionSpecId() {
		return sectionSpecId;
	}

	public void setSectionSpecId(long sectionSpecId) {
		this.sectionSpecId = sectionSpecId;
	}

	public long getSectionFieldSpecId() {
		return sectionFieldSpecId;
	}

	public void setSectionFieldSpecId(long sectionFieldSpecId) {
		this.sectionFieldSpecId = sectionFieldSpecId;
	}

	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}



	public Long getInitialEntitySectionFieldSpecId() {
		return initialEntitySectionFieldSpecId;
	}

	public void setInitialEntitySectionFieldSpecId(Long initialEntitySectionFieldSpecId) {
		this.initialEntitySectionFieldSpecId = initialEntitySectionFieldSpecId;
	}

	public Long getSkeletonEntitySectionFieldSpecId() {
		return skeletonEntitySectionFieldSpecId;
	}

	public void setSkeletonEntitySectionFieldSpecId(Long skeletonEntitySectionFieldSpecId) {
		this.skeletonEntitySectionFieldSpecId = skeletonEntitySectionFieldSpecId;
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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return toCsv();
	}

	public String toCsv(){
		return "EntitySectionField [sectionFieldId=" + sectionFieldId + ", entityId=" + entityId + ", entitySpecId="
				+ entitySpecId + ", sectionSpecId=" + sectionSpecId + ", sectionFieldSpecId=" + sectionFieldSpecId
				+ ", instanceId=" + instanceId + ", fieldValue=" + fieldValue + ", initialEntitySectionFieldSpecId="
				+ initialEntitySectionFieldSpecId + ", skeletonEntitySectionFieldSpecId="
				+ skeletonEntitySectionFieldSpecId + ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime
				+ ", fieldLabel=" + fieldLabel + ", fieldType=" + fieldType + ", displayOrder=" + displayOrder
				+ ", identifier=" + identifier + ", displayValue=" + displayValue + ", error=" + error + "]";
	}

	@JsonIgnore
	public int getRowId() {
		return rowId;
	}

	@JsonIgnore
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	
	
	

}
