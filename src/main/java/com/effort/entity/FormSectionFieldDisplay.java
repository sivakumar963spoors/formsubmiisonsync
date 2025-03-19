package com.effort.entity;


import java.util.List;


public class FormSectionFieldDisplay {
	private long sectionFieldSpecId;
	private int instanceId;
	private int fieldType;
	private String fieldLabel;
	private int displayOrder;
	private String fieldValue;
	private String fieldValueDispaly;
	private int visible=1;
	private int visibleOnVisibilityDependencyCritiria=1;
	private int sectionFieldVisibleOnVisibilityDependencyCritiria=1;
	
	private String sectionFieldUniqueId;
	
	private String locationAddress;
	private String locationLatLong;
	private String backgroundColor;
	
	private String fontColor;
	private String fontSize;
	private String bold;
	private String italic;
	private String underLine;
	private List<Media> multiDocMedias;
	private String pdfThumbNailMediaId;
	private String fieldValuePdfDisplay;
	private String documentMediaIcon;
	private String expression;

	
	public int getVisibleOnVisibilityDependencyCritiria() {
		return visibleOnVisibilityDependencyCritiria;
	}
	public void setVisibleOnVisibilityDependencyCritiria(int visibleOnVisibilityDependencyCritiria) {
		this.visibleOnVisibilityDependencyCritiria = visibleOnVisibilityDependencyCritiria;
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
	public int getFieldType() {
		return fieldType;
	}
	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getFieldValueDispaly() {
		return fieldValueDispaly;
	}
	public void setFieldValueDispaly(String fieldValueDispaly) {
		this.fieldValueDispaly = fieldValueDispaly;
	}
	public int getVisible() {
		return visible;
	}
	public void setVisible(int visible) {
		this.visible = visible;
	}
	
	public String getLocationAddress() {
		return locationAddress;
	}
	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}
	public String getLocationLatLong() {
		return locationLatLong;
	}
	public void setLocationLatLong(String locationLatLong) {
		this.locationLatLong = locationLatLong;
	}
	@Override
	public String toString() {
		return "FormSectionFieldDisplay [sectionFieldSpecId="
				+ sectionFieldSpecId + ", instanceId=" + instanceId
				+ ", fieldType=" + fieldType + ", fieldLabel=" + fieldLabel
				+ ", displayOrder=" + displayOrder + ", fieldValue="
				+ fieldValue + ", fieldValueDispaly=" + fieldValueDispaly
				+ ", genericMultiSelects=" +  ", visible="
				+ visible + ", visibleOnVisibilityDependencyCritiria="
				+ visibleOnVisibilityDependencyCritiria + ", locationAddress="
				+ locationAddress + ", locationLatLong=" + locationLatLong
				+ "]";
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getSectionFieldUniqueId() {
		return sectionFieldUniqueId;
	}
	public void setSectionFieldUniqueId(String sectionFieldUniqueId) {
		this.sectionFieldUniqueId = sectionFieldUniqueId;
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
	public List<Media> getMultiDocMedias() {
		return multiDocMedias;
	}
	public void setMultiDocMedias(List<Media> multiDocMedias) {
		this.multiDocMedias = multiDocMedias;
	}

	public String getPdfThumbNailMediaId() {
		return pdfThumbNailMediaId;
	}
	public void setPdfThumbNailMediaId(String pdfThumbNailMediaId) {
		this.pdfThumbNailMediaId = pdfThumbNailMediaId;
	}
	public String getFieldValuePdfDisplay() {
		return fieldValuePdfDisplay;
	}
	public void setFieldValuePdfDisplay(String fieldValuePdfDisplay) {
		this.fieldValuePdfDisplay = fieldValuePdfDisplay;
	}
	public String getDocumentMediaIcon() {
		return documentMediaIcon;
	}
	public void setDocumentMediaIcon(String documentMediaIcon) {
		this.documentMediaIcon = documentMediaIcon;
	}
	public int getSectionFieldVisibleOnVisibilityDependencyCritiria() {
		return sectionFieldVisibleOnVisibilityDependencyCritiria;
	}
	public void setSectionFieldVisibleOnVisibilityDependencyCritiria(
			int sectionFieldVisibleOnVisibilityDependencyCritiria) {
		this.sectionFieldVisibleOnVisibilityDependencyCritiria = sectionFieldVisibleOnVisibilityDependencyCritiria;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	

	
}
