package com.effort.entity;

import java.util.List;
import java.util.Map;

public class FormDisplay {
	private long id;
	private int type;
	private int displayOrder;
	private int pageId;
	private String label;
	private String groupExpression;
	private FormFieldDisplay fieldDisplay;
	private Map<Integer, List<FormSectionFieldDisplay>> sectionDisplay;
	private Map<String, List<FormDisplay>> groupDisplay;
	private String workActionSpecId = "";
	private int fieldDataType;
	private boolean required;
	private long sectionSpecId;
	private String formFieldUniqueId;
	private String fontColor;
	private String fontSize;
	private String bold;
	private String italic;
	private String underLine;

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public int getPageId() {
		return pageId;
	}
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public FormFieldDisplay getFieldDisplay() {
		return fieldDisplay;
	}
	public void setFieldDisplay(FormFieldDisplay fieldDisplay) {
		this.fieldDisplay = fieldDisplay;
	}
	public Map<Integer, List<FormSectionFieldDisplay>> getSectionDisplay() {
		return sectionDisplay;
	}
	public void setSectionDisplay(Map<Integer, List<FormSectionFieldDisplay>> sectionDisplay) {
		this.sectionDisplay = sectionDisplay;
	}
	public Map<String, List<FormDisplay>> getGroupDisplay() {
		return groupDisplay;
	}
	public void setGroupDisplay(Map<String, List<FormDisplay>> groupDisplay) {
		this.groupDisplay = groupDisplay;
	}
	
	
	
	@Override
	public String toString() {
		return "FormDisplay [id=" + id + ", type=" + type + ", displayOrder=" + displayOrder + ", pageId=" + pageId
				+ ", label=" + label + ", groupExpression=" + groupExpression + ", fieldDisplay=" + fieldDisplay
				+ ", sectionDisplay=" + sectionDisplay + ", groupDisplay=" + groupDisplay + ", workActionSpecId="
				+ workActionSpecId + ", fieldDataType=" + fieldDataType + ", required=" + required + ", sectionSpecId="
				+ sectionSpecId + ", formFieldUniqueId=" + formFieldUniqueId + ", fontColor=" + fontColor
				+ ", fontSize=" + fontSize + ", bold=" + bold + ", italic=" + italic + ", underLine=" + underLine + "]";
	}


	public String getGroupExpression() {
		return groupExpression;
	}
	public void setGroupExpression(String groupExpression) {
		this.groupExpression = groupExpression;
	}
	public String getWorkActionSpecId() {
		return workActionSpecId;
	}
	public void setWorkActionSpecId(String workActionSpecId) {
		this.workActionSpecId = workActionSpecId;
	}
	public int getFieldDataType() {
		return fieldDataType;
	}
	public void setFieldDataType(int fieldDataType) {
		this.fieldDataType = fieldDataType;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public long getSectionSpecId() {
		return sectionSpecId;
	}
	public void setSectionSpecId(long sectionSpecId) {
		this.sectionSpecId = sectionSpecId;
	}

	public String getFormFieldUniqueId() {
		return formFieldUniqueId;
	}
	public void setFormFieldUniqueId(String formFieldUniqueId) {
		this.formFieldUniqueId = formFieldUniqueId;
	}
	
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getBold() {
		return bold;
	}
	public void setBold(String bold) {
		this.bold = bold;
	}
	public String getItalic() {
		return italic;
	}
	public void setItalic(String italic) {
		this.italic = italic;
	}
	public String getUnderLine() {
		return underLine;
	}
	public void setUnderLine(String underLine) {
		this.underLine = underLine;
	}
		

	
}
