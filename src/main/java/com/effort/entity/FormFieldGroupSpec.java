package com.effort.entity;



public class FormFieldGroupSpec {

	private Long formFieldGroupSpecId;
	private Long formSpecId;
	private Integer companyId;
	private String groupTitle;
	private String printTemplate;
	private Integer displayOrder;
	private Integer pageId;
	private String createdTime;
	private String modifiedTime;
	private Integer groupIndex;
	private String expression;
	private String fieldLabelError;
	
	public Long getFormFieldGroupSpecId() {
		return formFieldGroupSpecId;
	}
	public void setFormFieldGroupSpecId(Long formFieldGroupSpecId) {
		this.formFieldGroupSpecId = formFieldGroupSpecId;
	}
	public Long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(Long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getGroupTitle() {
		return groupTitle;
	}
	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}
	public String getPrintTemplate() {
		return printTemplate;
	}
	public void setPrintTemplate(String printTemplate) {
		this.printTemplate = printTemplate;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public Integer getPageId() {
		return pageId;
	}
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
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
	public Integer getGroupIndex() {
		return groupIndex;
	}
	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getFieldLabelError() {
		return fieldLabelError;
	}
	public void setFieldLabelError(String fieldLabelError) {
		this.fieldLabelError = fieldLabelError;
	}
	
	
	
}
