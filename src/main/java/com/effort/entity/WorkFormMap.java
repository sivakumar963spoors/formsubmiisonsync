package com.effort.entity;


public class WorkFormMap {
	public static final int GIVEN_IDS_TYPE_WORK=1;
	public static final int GIVEN_IDS_TYPE_WORKFORMMAP=2;
	
	private long workFormMapId;
	private  String clientWorkFormMapId;
	private  Long workId;
	private  Long formId;
	private String clientWorkId;
	private String clientFormId;
	private boolean followUpWork = false;
	
	
	public Long getWorkId() {
		return workId;
	}
	public void setWorkId(Long workId) {
		this.workId = workId;
	}
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public String getClientWorkId() {
		return clientWorkId;
	}
	public void setClientWorkId(String clientWorkId) {
		this.clientWorkId = clientWorkId;
	}
	public String getClientFormId() {
		return clientFormId;
	}
	public void setClientFormId(String clientFormId) {
		this.clientFormId = clientFormId;
	}
	public long getWorkFormMapId() {
		return workFormMapId;
	}
	public void setWorkFormMapId(long workFormMapId) {
		this.workFormMapId = workFormMapId;
	}
	public String getClientWorkFormMapId() {
		return clientWorkFormMapId;
	}
	public void setClientWorkFormMapId(String clientWorkFormMapId) {
		this.clientWorkFormMapId = clientWorkFormMapId;
	}
	
	
	public boolean isFollowUpWork() {
		return followUpWork;
	}
	public void setFollowUpWork(boolean followUpWork) {
		this.followUpWork = followUpWork;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof WorkFormMap){
			WorkFormMap workFormMap=(WorkFormMap)obj;
			if(getFormId()!=null&&workFormMap.getFormId()==getFormId().longValue() && getWorkId()!=null&&workFormMap.getWorkId()==getWorkId().longValue()){
				return true;
			}
		}
		return false;
	}
	

}
