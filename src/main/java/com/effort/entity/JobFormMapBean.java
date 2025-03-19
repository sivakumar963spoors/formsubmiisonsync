package com.effort.entity;



public class JobFormMapBean {
	
	private long id;
	private boolean formFollowUp;
	private String formSpecUniqueId;
	private String dateFieldSpecUniqueId;
	private String jobType;
	private String jobTitle;
	private String jobDescription;
	private String customer;
	private Integer pickAnother = 1;
	private String webActionToken = "-1";
	
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}
	public String getJobType() {
		return jobType;
	}
	public String getDateFieldSpecUniqueId() {
		return dateFieldSpecUniqueId;
	}
	public void setDateFieldSpecUniqueId(String dateFieldSpecUniqueId) {
		this.dateFieldSpecUniqueId = dateFieldSpecUniqueId;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public boolean isFormFollowUp() {
		return formFollowUp;
	}
	public void setFormFollowUp(boolean formFollowUp) {
		this.formFollowUp = formFollowUp;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Integer getPickAnother() {
		return pickAnother;
	}
	public void setPickAnother(Integer pickAnother) {
		this.pickAnother = pickAnother;
	}
	public String getWebActionToken() {
		return webActionToken;
	}
	public void setWebActionToken(String webActionToken) {
		this.webActionToken = webActionToken;
	}
	
}