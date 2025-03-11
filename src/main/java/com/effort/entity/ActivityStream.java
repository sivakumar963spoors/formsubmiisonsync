package com.effort.entity;


public class ActivityStream {
	public static final int ACTIVITY_STREAM_JOB_CREATED_AND_ASSIGNED = 1;
    public static final int ACTIVITY_STREAM_JOB_CREATED_BUT_NOT_ASSIGNED = 2;
    public static final int ACTIVITY_STREAM_JOB_ASSIGNED = 3;
    public static final int ACTIVITY_STREAM_JOB_MODIFIED = 4;
    public static final int ACTIVITY_STREAM_JOB_COMPLETED = 5;
    public static final int ACTIVITY_STREAM_JOB_COMMENT_ADDED = 6;
    public static final int ACTIVITY_STREAM_JOB_COMMENT_MODIFIED = 7;
    public static final int ACTIVITY_STREAM_JOB_FILE_ADDED = 8;
    public static final int ACTIVITY_STREAM_JOB_STAGE_COMPLETED = 9;
    public static final int ACTIVITY_STREAM_FORM_ADDED = 10;
    public static final int ACTIVITY_STREAM_FORM_MODIFIED = 11;
    
    public static final int ACTIVITY_STREAM_WORK_ADDED = 12;
    public static final int ACTIVITY_STREAM_WORK_ADDED_AND_ASSIGNED = 13;
    public static final int ACTIVITY_STREAM_WORK_ASSIGNED = 14;
    public static final int ACTIVITY_STREAM_WORK_ACTION_COMPLETED = 15;
    public static final int ACTIVITY_STREAM_WORK_COMPLETED = 16;
    public static final int ACTIVITY_STREAM_WORK_SHARED = 17;
    
    
    public static final int ACTIVITY_STREAM_ROUTE_ADDED = 18;
    public static final int ACTIVITY_STREAM_ROUTE_ASSIGNED = 19;
    public static final int ACTIVITY_STREAM_ROUTE_COMPLETED = 20;
    public static final int ACTIVITY_STREAM_ROUTE_ACTIVITY_PERFORMED_FOR_CUSTOMER = 21;
    public static final int ACTIVITY_STREAM_MODIFIED_ROUTE_ASSIGNED = 22;
    public static final int ACTIVITY_STREAM_WORK_SCHEDULING_ASSIGNMENT_FAILED = 23;
    
	
    public static final int STERILITE_DASHBOARD_TARGET_TYPE = 4;
    
	private long id;
	private long companyId;
	private long createdBy;
	private String message;
	private long targetId;
	private int targetType;
	private String createdTime;
	private boolean deleted;
	
	private String elapseTime;
	private long activityStreamId;
	private long empId;
	private int status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTargetId() {
		return targetId;
	}
	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
	public int getTargetType() {
		return targetType;
	}
	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getElapseTime() {
		return elapseTime;
	}
	public void setElapseTime(String elapseTime) {
		this.elapseTime = elapseTime;
	}
	public long getActivityStreamId() {
		return activityStreamId;
	}
	public void setActivityStreamId(long activityStreamId) {
		this.activityStreamId = activityStreamId;
	}
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
