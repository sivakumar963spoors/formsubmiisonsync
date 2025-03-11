package com.effort.entity;


public class EmployeeFilteringCritiria {
	
    private long    formSpecId;
    private long	fieldSpecId;
    private long	fieldOptionId ; 
    private String employeeFieldSpecUniqueId;
    private String	referenceFieldExpressionId;
    private String	value;
    private int condition;
    private int type;
    private Integer filterType;
    
    public final static int TERRITORY_FILTER_TYPE = 1;
    public final static int LIST_BASED_FILTER_TYPE = 2;
    
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
	
	public String getReferenceFieldExpressionId() {
		return referenceFieldExpressionId;
	}
	public void setReferenceFieldExpressionId(String referenceFieldExpressionId) {
		this.referenceFieldExpressionId = referenceFieldExpressionId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getFieldOptionId() {
		return fieldOptionId;
	}
	public void setFieldOptionId(long fieldOptionId) {
		this.fieldOptionId = fieldOptionId;
	}
	public Integer getFilterType() {
		return filterType;
	}
	public void setFilterType(Integer filterType) {
		this.filterType = filterType;
	}
	public String getEmployeeFieldSpecUniqueId() {
		return employeeFieldSpecUniqueId;
	}
	public void setEmployeeFieldSpecUniqueId(String employeeFieldSpecUniqueId) {
		this.employeeFieldSpecUniqueId = employeeFieldSpecUniqueId;
	}
	
	
}
