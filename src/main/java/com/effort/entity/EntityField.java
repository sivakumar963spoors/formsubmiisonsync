package com.effort.entity;

import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityField implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long fieldId;
	private long entityId;
	private long entitySpecId;
	private long entityFieldSpecId;
	private String fieldValue;
//	@JsonProperty(access = Access.WRITE_ONLY)
	private String fieldValueSubstitute;
//	@JsonProperty(access = Access.WRITE_ONLY)
	private String clientSideId;
//	private String clientFieldId;
	
	private String error;
	
	private Integer identifier;
	private String fieldLabel;
    private Integer fieldType;
    private Integer displayOrder;
    private String displayValue;
    
    private int rowId;
    private String externalLabel;
    
    private String fieldValueSignature;
    
    private long companyId;
	
	
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
		public long getFieldId() {
	return fieldId;
}
	public void setFieldId(long fieldId) {
	this.fieldId = fieldId;
	}
	public String getFieldValue() {
		return fieldValue;
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
	public long getEntityFieldSpecId() {
		return entityFieldSpecId;
	}
	public void setEntityFieldSpecId(long entityFieldSpecId) {
		this.entityFieldSpecId = entityFieldSpecId;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
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
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getFieldValueSubstitute() {
		return fieldValueSubstitute;
	}
	public void setFieldValueSubstitute(String fieldValueSubstitute) {
		this.fieldValueSubstitute = fieldValueSubstitute;
	}
	
	
	
//	public String getClientFieldId() {
//		return clientFieldId;
//	}
//	public void setClientFieldId(String clientFieldId) {
//		this.clientFieldId = clientFieldId;
//	}
	
	public String getClientSideId() {
		return clientSideId;
	}
	public void setClientSideId(String clientSideId) {
		this.clientSideId = clientSideId;
	}
	@JsonIgnore
	public String getError() {
		return error;
	}
	@JsonIgnore
	public void setError(String error) {
		this.error = error;
	}
	
	@JsonIgnore
	public int getRowId() {
		return rowId;
	}
	@JsonIgnore
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	
	public String toCSV() {
		return "[entityId=" + entityId + ", entitySpecId="
				+ entitySpecId + ", entityFieldSpecId=" + entityFieldSpecId
				+ ", fieldValue=" + fieldValue + ", fieldValueSubstitute="
				+ fieldValueSubstitute + ", clientSideId=" + clientSideId
				+ ", error=" + error + "]";
	}
	
	
	public String getExternalLabel() {
		return externalLabel;
	}
	public void setExternalLabel(String externalLabel) {
		this.externalLabel = externalLabel;
	}
	@Override
	public String toString() {
		return toCSV();
	}
	public String getFieldValueSignature() {
		return fieldValueSignature;
	}
	public void setFieldValueSignature(String fieldValueSignature) {
		this.fieldValueSignature = fieldValueSignature;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	
	
}
