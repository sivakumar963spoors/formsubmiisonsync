package com.effort.entity;


import java.io.Serializable;

import com.effort.util.Api;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;




public class JobCreateOrModifiNotify  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public static final int JOB_ADDED=1;
	public static final int JOB_MODIFIED=2;
	public static final int JOB_COMPLETED=3;
	public static final int ERROR_WHEN_COMPLETING_JOB=4;
	
	public JobCreateOrModifiNotify() {
	}
	
	public JobCreateOrModifiNotify(String empPhone,String msg,int type) {
		this.empPhone=empPhone;
		this.msg=msg;
		this.type=type;
	}
	
	
	private long empVisitId;
	private long empId;
	private String empVisitTitle;
	private String empVisitDesc;
	private String visitStartTime;
	private String visitEndTime;
	private boolean completed;
	private String completeTime;
	private boolean approved;
	private String modifiedTime;
	private String createTime;
	private String addressStreet;
	private String addressArea;
	private String addressCity;
	private String addressPincode;
	private String addressLandMark;
	private String addressState;
	private String addressCountry;
	private String addressLat;
	private String addressLng;
	private String empFirstName;
	private String empLastName;
	private String jobLabel="Job";
	private String msg;
	private String empPhone;
	private int type;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getEmpPhone() {
		return empPhone;
	}
	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getEmpVisitId() {
		return empVisitId;
	}
	public void setEmpVisitId(long empVisitId) {
		this.empVisitId = empVisitId;
	}
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	
	public String getEmpVisitTitle() {
		return empVisitTitle;
	}
	public void setEmpVisitTitle(String empVisitTitle) {
		this.empVisitTitle = empVisitTitle;
	}
	public String getEmpVisitDesc() {
		return empVisitDesc;
	}
	public void setEmpVisitDesc(String empVisitDesc) {
		this.empVisitDesc = empVisitDesc;
	}
	
	public String getVisitStartTime() {
		return visitStartTime;
	}
	public void setVisitStartTime(String visitStartTime) {
		this.visitStartTime = visitStartTime;
	}
	public String getVisitEndTime() {
		return visitEndTime;
	}
	public void setVisitEndTime(String visitEndTime) {
		this.visitEndTime = visitEndTime;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}
	public String getAddressArea() {
		return addressArea;
	}
	public void setAddressArea(String addressArea) {
		this.addressArea = addressArea;
	}
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	public String getAddressPincode() {
		return addressPincode;
	}
	public void setAddressPincode(String addressPincode) {
		this.addressPincode = addressPincode;
	}
	public String getAddressLandMark() {
		return addressLandMark;
	}
	public void setAddressLandMark(String addressLandMark) {
		this.addressLandMark = addressLandMark;
	}
	public String getAddressState() {
		return addressState;
	}
	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}
	public String getAddressCountry() {
		return addressCountry;
	}
	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}
	public String getAddressLat() {
		return addressLat;
	}
	public void setAddressLat(String addressLat) {
		this.addressLat = addressLat;
	}
	public String getAddressLng() {
		return addressLng;
	}
	public void setAddressLng(String addressLng) {
		this.addressLng = addressLng;
	}
	
	public String getEmpFirstName() {
		return empFirstName;
	}
	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}
	public String getEmpLastName() {
		return empLastName;
	}
	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}
	public String getJobLabel() {
		return jobLabel;
	}
	public void setJobLabel(String jobLabel) {
		this.jobLabel = jobLabel;
	}
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getEmpName(){
		String empName = "";
		
		if(!Api.isEmptyString(getEmpFirstName())){
			empName += getEmpFirstName();
		}
		
		if(!Api.isEmptyString(getEmpLastName())){
			if(!Api.isEmptyString(empName)){
				empName += (" " + getEmpLastName());
			} else {
				empName += getEmpLastName();
			}
		}
		
		return empName;
	}
	
	
	
	@Override
	public String toString() {
		return "JobCreateOrModifiNotify [empVisitId=" + empVisitId + ", empId=" + empId + ", empVisitTitle=" + empVisitTitle + ", empVisitDesc=" + empVisitDesc + ", visitStartTime=" + visitStartTime + ", visitEndTime=" + visitEndTime + ", completed=" + completed + ", completeTime=" + completeTime
				+ ", approved=" + approved + ", modifiedTime=" + modifiedTime + ", createTime=" + createTime + ", addressStreet=" + addressStreet + ", addressArea=" + addressArea + ", addressCity=" + addressCity + ", addressPincode=" + addressPincode + ", addressLandMark=" + addressLandMark
				+ ", addressState=" + addressState + ", addressCountry=" + addressCountry + ", addressLat=" + addressLat + ", addressLng=" + addressLng + ", empFirstName=" + empFirstName + ", empLastName=" + empLastName + ", jobLabel=" + jobLabel + ", msg=" + msg + ", empPhone=" + empPhone
				+ ", type=" + type + "]";
	}

	public String getAddress(){
		String address = "";
		
		if(!Api.isEmptyString(addressStreet)){
			address += addressStreet;
		}
		
		if(!Api.isEmptyString(addressArea)){
			if(address.length() > 0){
				address += ", ";
			}
			address += addressArea;
		}
		
		if(!Api.isEmptyString(addressCity)){
			if(address.length() > 0){
				address += ", ";
			}
			address += addressCity;
		}
		
		if(!Api.isEmptyString(addressPincode)){
			if(address.length() > 0){
				address += ", ";
			}
			address += addressPincode;
		}
		return address;
	}
}
