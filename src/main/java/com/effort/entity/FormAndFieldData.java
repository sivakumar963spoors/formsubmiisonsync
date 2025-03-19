package com.effort.entity;



import java.util.List;

import com.effort.beans.http.request.location.Location;




public class FormAndFieldData {
	private Form form;
	private List<FormField> fields;
	private List<FormSectionField> sectionFields;
	private Long jobId;
	
	List<Employee> employees;
	List<Customer> customers ;
	List<Entity> entities ;
	private String formSpecUniqueId;
	
	private Location filledLocation;
	private Location modifiedLocation;
	
	//private List<PrintLog> printLogs;
	
	public Form getForm() {
		return form;
	}
	public void setForm(Form form) {
		this.form = form;
	}
	public List<FormField> getFields() {
		return fields;
	}
	public void setFields(List<FormField> fields) {
		this.fields = fields;
	}
	public List<FormSectionField> getSectionFields() {
		return sectionFields;
	}
	public void setSectionFields(List<FormSectionField> sectionFields) {
		this.sectionFields = sectionFields;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	public List<Entity> getEntities() {
		return entities;
	}
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}
	public Location getFilledLocation() {
		return filledLocation;
	}
	public void setFilledLocation(Location filledLocation) {
		this.filledLocation = filledLocation;
	}
	public Location getModifiedLocation() {
		return modifiedLocation;
	}
	public void setModifiedLocation(Location modifiedLocation) {
		this.modifiedLocation = modifiedLocation;
	}
	/*public List<PrintLog> getPrintLogs() {
		return printLogs;
	}
	public void setPrintLogs(List<PrintLog> printLogs) {
		this.printLogs = printLogs;
	}*/

	
	
}
