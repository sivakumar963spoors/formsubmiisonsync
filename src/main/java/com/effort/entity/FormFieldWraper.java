package com.effort.entity;



public class FormFieldWraper {
	public static final int TYPE_FIELD = 1;
	public static final int TYPE_SECTION_FIELD = 2;
	
	private long formSpecId;
	private long fieldSpecId;
	private String fieldValue;
	private int instanceId;
	private String entityId;
	private String fieldDataType;
	
	private int type;
	private boolean computedField;
	private boolean defaultField;
	private int index;
	private int sectionIndex;
	
	private String expression;
	
	private String formula;
	private String uniqueId;
	
	private boolean enableAgeRestriction;
	private Integer minAge;
	private Integer maxAge;
	private String ageRestrictionErrorMessage;
	private String pickEmployeesFromGroupIds;
	
	public long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public long getFieldSpecId() {
		return fieldSpecId;
	}
	public void setFieldSpecId(long fieldSpecId) {
		this.fieldSpecId = fieldSpecId;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public int getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
//	public String getExpression() {
//		return expression;
//	}
//	public void setExpression(String expression) {
//		this.expression = expression;
//	}
	public boolean isComputedField() {
		return computedField;
	}
	public void setComputedField(boolean computedField) {
		this.computedField = computedField;
	}
	public boolean isDefaultField() {
		return defaultField;
	}
	public void setDefaultField(boolean defaultField) {
		this.defaultField = defaultField;
	}
	  public String getEntityId() {
	    return entityId;
	 }
	 public void setEntityId(String entityId) {
	    this.entityId = entityId;
	 }
	public String getFieldDataType() {
		return fieldDataType;
	}
	public void setFieldDataType(String fieldDataType) {
		this.fieldDataType = fieldDataType;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getSectionIndex() {
		return sectionIndex;
	}
	public void setSectionIndex(int sectionIndex) {
		this.sectionIndex = sectionIndex;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public boolean isEnableAgeRestriction() {
		return enableAgeRestriction;
	}
	public void setEnableAgeRestriction(boolean enableAgeRestriction) {
		this.enableAgeRestriction = enableAgeRestriction;
	}
	public Integer getMinAge() {
		return minAge;
	}
	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}
	public Integer getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}
	public String getAgeRestrictionErrorMessage() {
		return ageRestrictionErrorMessage;
	}
	public void setAgeRestrictionErrorMessage(String ageRestrictionErrorMessage) {
		this.ageRestrictionErrorMessage = ageRestrictionErrorMessage;
	}
	public String getPickEmployeesFromGroupIds() {
		return pickEmployeesFromGroupIds;
	}
	public void setPickEmployeesFromGroupIds(String pickEmployeesFromGroupIds) {
		this.pickEmployeesFromGroupIds = pickEmployeesFromGroupIds;
	}
	
}
