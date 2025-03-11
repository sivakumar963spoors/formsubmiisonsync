package com.effort.entity;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CompanyLabel implements Serializable {
	
	private static final long serialVersionUID = 2741284144725394073L;
	public static final String CUSTOMER_LABEL = "1";	
	public static final String CUSTOMERS_LABEL = "2";
	public static final String EMPLOYEE_LABEL = "3";
	public static final String EMPLOYEES_LABEL = "4";
	public static final String NAMED_LOCATION_LABEL = "5";
	public static final String NAMED_LOCATIONS_LABEL = "6";
	public static final String KNOWLEDGE_BASE = "7";
	public static final String KNOWLEDGE_BASES = "8";
	public static final String FORM_LABEL = "9";
	public static final String FORMS_LABEL = "10";
	public static final String JOB_LABEL = "11";
	public static final String JOBS_LABEL = "12";
	public static final String LIST_LABEL = "13";
	public static final String LISTS_LABEL = "14";
	public static final String AGENDA_LABEL = "15";
	public static final String AGENDA_LABEL_PLURAL = "16";
	public static final String WORK_LABEL = "33";
	public static final String WORK_LABEL_PLURAL = "34";
	
	public static final String PARENT_LABEL = "53";
	public static final String PARENT_LABEL_PLURAL = "54";
	


	private Map<String, String> labelValuesmap;

	private String appName;
	private long appId;
	private String theme;
	
	

	public Map<String, String> getLabelValuesmap() {
		if(labelValuesmap==null){
			if(labelValues!=null){
				labelValuesmap=new HashMap<String, String>();
				for (LabelValue labelValue : labelValues) {
					labelValuesmap.put(""+labelValue.getLabelId(), labelValue.getLabelValue());
					
				}
			}
			
		}
		return  labelValuesmap;
	}
	

	public String getCustomersLabel(){
		return getLabelValuesmap() .get(CUSTOMER_LABEL);
	}
	
	public String getCustomersLabelPlural(){
		return getLabelValuesmap() .get(CUSTOMERS_LABEL);
	}
	
	public String getEmployeesLabel(){
		return getLabelValuesmap() .get(EMPLOYEE_LABEL);
	}
	
	public String getEmployeesLabelPlural(){
		return getLabelValuesmap() .get(EMPLOYEES_LABEL);
	}
	
	public String getNamedLocationsLabel(){
		return getLabelValuesmap() .get(NAMED_LOCATION_LABEL);
	}
	public String getNamedLocationsLabelPlural(){
		return getLabelValuesmap() .get(NAMED_LOCATIONS_LABEL);
	}
	
	public String getKnowledgeBaseLabel(){
		return getLabelValuesmap() .get(KNOWLEDGE_BASE);
	}
	public String getKnowledgeBaseLabelPlural(){
		return getLabelValuesmap() .get(KNOWLEDGE_BASES);
	}
	
	public String getFormsLabel(){
		return getLabelValuesmap() .get(FORM_LABEL);
	}
	public String getFormsLabelPlural(){
		return getLabelValuesmap() .get(FORMS_LABEL);
	}
	
	public String getJobsLabelPlural(){
		return getLabelValuesmap() .get(JOBS_LABEL);
	}
	public String getJobsLabel(){
		return getLabelValuesmap() .get(JOB_LABEL);
	}
	public String getListsLabel(){
		return getLabelValuesmap() .get(LIST_LABEL);
	}
	
	public String getListsLabelPlural(){
		return getLabelValuesmap() .get(LISTS_LABEL);
	}
	
	public String getAgendaLabel(){
		return getLabelValuesmap() .get(AGENDA_LABEL);
	}
	
	public String getAgendaLabelPlural(){
		return getLabelValuesmap() .get(AGENDA_LABEL_PLURAL);
	}

	public String getWorkLabel(){
		return getLabelValuesmap() .get(WORK_LABEL);
	}
	
	public String getWorkLabelPlural(){
		return getLabelValuesmap() .get(WORK_LABEL_PLURAL);
	}
	
	
	public String getParentLabel(){
		return getLabelValuesmap() .get(PARENT_LABEL);
	}
	
	public String getParentLabelPlural(){
		return getLabelValuesmap() .get(PARENT_LABEL_PLURAL);
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "CompanyLabel [labelValues=" + labelValues + ", labelValuesmap="
				+ labelValuesmap + ", deviceHomeItems=" + deviceHomeItems
				+ ", appName=" + appName + ", appId=" + appId + ", theme="
				+ theme + "]";
	}
	


}
