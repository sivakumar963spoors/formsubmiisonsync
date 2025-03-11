package com.effort.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tag {
	private Long tagId;
	private Integer companyId;
	private String tagName;
	private Boolean isSpecialTag=false;
	private Integer specialTagId;
	private String createdTime;
	
	private Long entityId;
	private Long entitySpecId;
	private Boolean isListItem=false;
	
	private Long customerId;
	private String clientCustomerId;
	
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	@JsonIgnore
	public Boolean getIsSpecialTag() {
		return isSpecialTag;
	}
	@JsonIgnore
	public void setIsSpecialTag(Boolean isSpecialTag) {
		this.isSpecialTag = isSpecialTag;
	}
	@JsonIgnore
	public Integer getSpecialTagId() {
		return specialTagId;
	}
	@JsonIgnore
	public void setSpecialTagId(Integer specialTagId) {
		this.specialTagId = specialTagId;
	}
	
	@JsonIgnore
	public String getCreatedTime() {
		return createdTime;
	}
	
	@JsonIgnore
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public Long getEntitySpecId() {
		return entitySpecId;
	}
	public void setEntitySpecId(Long entitySpecId) {
		this.entitySpecId = entitySpecId;
	}
	public Boolean getIsListItem() {
		return isListItem;
	}
	public void setIsListItem(Boolean isListItem) {
		this.isListItem = isListItem;
	}

	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getClientCustomerId() {
		return clientCustomerId;
	}
	public void setClientCustomerId(String clientCustomerId) {
		this.clientCustomerId = clientCustomerId;
	}
	

}
