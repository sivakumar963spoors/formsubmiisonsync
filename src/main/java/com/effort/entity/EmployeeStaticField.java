package com.effort.entity;

public class EmployeeStaticField {

	public static final String EMPLOYEE_FIRST_NAME_EXPR = "E1";
	public static final String EMPLOYEE_LAST_NAME_EXPR = "E2";
	public static final String EMPLOYEE_ID_EXPR = "E3";
	public static final String EMPLOYEE_PHOTO_EXPR = "E4";
	public static final String EMPLOYEE_TYPE_EXPR = "E5";
	public static final String EMPLOYEE_REPORTING_MANAGER_EXPR = "E6";
	public static final String EMPLOYEE_EMAIL_EXPR = "E7";
	public static final String EMPLOYEE_MOBILE_EXPR = "E8";
	public static final String EMPLOYEE_EFFORT_ID_EXPR = "E9";
	public static final String EMPLOYEE_DESIGNATION_EXPR = "E10";
	public static final String EMPLOYEE_ADDRESS_STREET_EXPR = "E11";
	public static final String EMPLOYEE_ADDRESS_AREA_EXPR = "E12";
	public static final String EMPLOYEE_ADDRESS_CITY_EXPR = "E13";
	public static final String EMPLOYEE_ADDRESS_DISTRICT_EXPR = "E14";
	public static final String EMPLOYEE_ADDREESS_PINCODE_EXPR = "E15";
	public static final String EMPLOYEE_ADDRESS_LANDMARK_EXPR = "E16";
	public static final String EMPLOYEE_ADDRESS_COUNTRY_EXPR = "E17";
	public static final String EMPLOYEE_ADDRESS_STATE_EXPR = "E18";
	public static final String EMPLOYEE_HOME_LOCATION_EXPR = "E19";
	public static final String EMPLOYEE_WORK_LOCATION_EXPR = "E20";
	public static final String EMPLOYEE_GROUP_EXPR = "E21";
	public static final String EMPLOYEE_TERRITORY_EXPR = "E22";
	public static final String EMPLOYEE_ROLE_EXPR = "E23";
	public static final String EMPLOYEE_EMPLOYEE_BRANCH_EXPR = "E24";
	public static final String EMPLOYEE_FORM_FILLED_EXPR = "E25";
	public static final String EMPLOYEE_JOINING_DATE_EXPR = "E26";

	
	
	public static final String[] EMPLOYEE_FIRST_NAME_PROP = {"First Name", "1", EMPLOYEE_FIRST_NAME_EXPR};
	public static final String[] EMPLOYEE_LAST_NAME_PROP = {"Last Name", "1", EMPLOYEE_LAST_NAME_EXPR};
	public static final String[] EMPLOYEE_ID_PROP = {"ID", "1", EMPLOYEE_ID_EXPR};
	public static final String[] EMPLOYEE_PHOTO_PROP = {"Photo", "12", EMPLOYEE_PHOTO_EXPR};
	public static final String[] EMPLOYEE_TYPE_PROP = {"Type", "1", EMPLOYEE_TYPE_EXPR};
	public static final String[] EMPLOYEE_REPORTING_MANAGER_PROP = {"Reporting Manager", "15", EMPLOYEE_REPORTING_MANAGER_EXPR};
	public static final String[] EMPLOYEE_EMAIL_PROP = {"Email", "8", EMPLOYEE_EMAIL_EXPR};
	public static final String[] EMPLOYEE_MOBILE_PROP = {"Mobile", "9", EMPLOYEE_MOBILE_EXPR};
	public static final String[] EMPLOYEE_EFFORT_ID_PROP = {"EFFORT ID", "1", EMPLOYEE_EFFORT_ID_EXPR};
	public static final String[] EMPLOYEE_DESIGNATION_PROP = {"Designation", "1", EMPLOYEE_DESIGNATION_EXPR};
	public static final String[] EMPLOYEE_ADDRESS_STREET_PROP = {"Address Street", "1", EMPLOYEE_ADDRESS_STREET_EXPR};
	public static final String[] EMPLOYEE_ADDRESS_AREA_PROP = {"Address Area", "1", EMPLOYEE_ADDRESS_AREA_EXPR};
	public static final String[] EMPLOYEE_ADDRESS_CITY_PROP = {"Address City", "1", EMPLOYEE_ADDRESS_CITY_EXPR};
	public static final String[] EMPLOYEE_ADDRESS_DISTRICT_PROP = {"Address District", "1", EMPLOYEE_ADDRESS_DISTRICT_EXPR};
	public static final String[] EMPLOYEE_ADDREESS_PINCODE_PROP = {"Address Pincode", "2", EMPLOYEE_ADDREESS_PINCODE_EXPR};
	public static final String[] EMPLOYEE_ADDRESS_LANDMARK_PROP = {"Address Landmark", "1", EMPLOYEE_ADDRESS_LANDMARK_EXPR};
	public static final String[] EMPLOYEE_ADDRESS_COUNTRY_PROP = {"Address Country", "20", EMPLOYEE_ADDRESS_COUNTRY_EXPR};
	public static final String[] EMPLOYEE_ADDRESS_STATE_PROP = {"Address State", "1",  EMPLOYEE_ADDRESS_STATE_EXPR};
	public static final String[] EMPLOYEE_HOME_LOCATION_PROP = {"Home Location", "18", EMPLOYEE_HOME_LOCATION_EXPR};
	public static final String[] EMPLOYEE_WORK_LOCATION_PROP = {"Work Location", "18", EMPLOYEE_WORK_LOCATION_EXPR};
	public static final String[] EMPLOYEE_GROUP_PROP = {"Employee Group", "1", EMPLOYEE_GROUP_EXPR};
	public static final String[] EMPLOYEE_TERRITORY_PROP = {"Employee Territory", "31", EMPLOYEE_TERRITORY_EXPR};
	public static final String[] EMPLOYEE_ROLE_PROP = {"Role", "1", EMPLOYEE_ROLE_EXPR};
	public static final String[] EMPLOYEE_BRANCH_PROP = {"Employee Branch", "1", EMPLOYEE_EMPLOYEE_BRANCH_EXPR};
    public static final String[] EMPLOYEE_FORM_FILLED = {"Filled By", "15", EMPLOYEE_FORM_FILLED_EXPR};
    public static final String[] EMPLOYEE_JOINING_DATE = {"Joining Date", "3", EMPLOYEE_JOINING_DATE_EXPR};

	
	
	private String fieldLabel;
	private Integer fieldType;
	private String expression;
	private int mandatory;

	public EmployeeStaticField(String[] properties) {
		super();
		this.fieldLabel = properties[0];
		this.fieldType = Integer.parseInt(properties[1]);
		this.expression = properties[2];
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

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public int getMandatory() {
		return mandatory;
	}

	public void setMandatory(int mandatory) {
		this.mandatory = mandatory;
	}

}
