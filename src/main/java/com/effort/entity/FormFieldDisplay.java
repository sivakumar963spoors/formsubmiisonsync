package com.effort.entity;



import java.util.List;



public class FormFieldDisplay {
	private long fieldSpecId;
	private int fieldType;
	private String fieldLabel;
	private int displayOrder;
	private String fieldValue;
	private int visible = 1;
	private String fieldValueDispaly;
	private String locationAddress;
	private String locationLatLong;
	private String fieldSpecUniqueId;
	private Long skeletonFormFieldSpecId; 
	private String groupExpression;
	private String backgroundColor;

	private String fontColor;
	private String fontSize;
	private boolean bold;
	private boolean italic;
	private boolean underLine;
	private List<Media> multiDocMedias;
	private boolean identifier;
	private String uniqueId;
	private boolean staticField;
	private String pdfThumbNailMediaId;
	private String fieldValuePdfDisplay;
	private String documentMediaIcon;
	private String expression;

	public long getFieldSpecId() {
		return fieldSpecId;
	}

	public void setFieldSpecId(long fieldSpecId) {
		this.fieldSpecId = fieldSpecId;
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

	public String getFieldSpecUniqueId() {
		return fieldSpecUniqueId;
	}

	public void setFieldSpecUniqueId(String fieldSpecUniqueId) {
		this.fieldSpecUniqueId = fieldSpecUniqueId;
	}

	@Override
	public String toString() {
		return "FormFieldDisplay [fieldSpecId=" + fieldSpecId + ", fieldType="
				+ fieldType + ", fieldLabel=" + fieldLabel + ", displayOrder="
				+ displayOrder + ", fieldValue=" + fieldValue + ", visible="
				+ visible + ", fieldValueDispaly=" + fieldValueDispaly
				+ ", locationAddress=" + locationAddress + ", locationLatLong="
				+ locationLatLong + ", genericMultiSelects="
				+ "]";
	}

	public Long getSkeletonFormFieldSpecId() {
		return skeletonFormFieldSpecId;
	}

	public void setSkeletonFormFieldSpecId(Long skeletonFormFieldSpecId) {
		this.skeletonFormFieldSpecId = skeletonFormFieldSpecId;
	}

	public String getGroupExpression() {
		return groupExpression;
	}

	public void setGroupExpression(String groupExpression) {
		this.groupExpression = groupExpression;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
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
	public boolean isBold() {
		return bold;
	}
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	public boolean isItalic() {
		return italic;
	}
	public void setItalic(boolean italic) {
		this.italic = italic;
	}
	public boolean isUnderLine() {
		return underLine;
	}
	public void setUnderLine(boolean underLine) {
		this.underLine = underLine;
	}

	public List<Media> getMultiDocMedias() {
		return multiDocMedias;
	}

	public void setMultiDocMedias(List<Media> multiDocMedias) {
		this.multiDocMedias = multiDocMedias;
	}

	public boolean isIdentifier() {
		return identifier;
	}

	public void setIdentifier(boolean identifier) {
		this.identifier = identifier;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public boolean isStaticField() {
		return staticField;
	}

	public void setStaticField(boolean staticField) {
		this.staticField = staticField;
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

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}
