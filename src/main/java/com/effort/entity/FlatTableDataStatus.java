package com.effort.entity;


import java.io.Serializable;

public class FlatTableDataStatus implements Serializable{

    private static final long serialVersionUID = 1L;
    public static final int STATUS_PROCESSED = 1;
    public static final int STATUS_UN_PROCESSED = 0;
    public static final int STATUS_IN_PROGRESS = 5;
    public static final int STATUS_UN_PROCESSED_FOR_BULKUPLOAD_WORKS = -2;
    public static final int STATUS_UN_PROCESSED_FOR_FULL_PUSH = -3;
    
    public static final int TYPE_UPDATE = 1;
    public static final int TYPE_CREATE = 0;
    
    public static final int TYPE_FORM = 1;
    public static final int TYPE_LOCATION = 4;
    public static final int TYPE_EMPLOYEES = 2;
    public static final int TYPE_ENTITIES = 3;
    public static final int TYPE_WORK = 5;
    public static final int TYPE_SIGN_IN_SIGN_OUT = 6;
    
    private long flatTableDataEntityId;
    private long companyId;
    private int flatDataEntityType;
    private long flatDataTableId;
    private boolean status;
    private boolean updateType;
    private String createdTime;
    private String modifiedTime;
    private int statusInt;
    
    public FlatTableDataStatus(long flatTableDataEntityId,int companyId,int flatDataEntityType,
    		boolean status,boolean updateType,String createdTime,String modifiedTime) {
    	this.flatTableDataEntityId = flatTableDataEntityId;
    	this.companyId = companyId;
    	this.flatDataEntityType = flatDataEntityType;
    	this.status = status;
    	this.updateType = updateType;
    	this.createdTime = createdTime;
    	this.modifiedTime = modifiedTime;
    }
    public FlatTableDataStatus() {
    	
    }
    
    public long getFlatTableDataEntityId() {
        return flatTableDataEntityId;
    }
    public void setFlatTableDataEntityId(long flatTableDataEntityId) {
        this.flatTableDataEntityId = flatTableDataEntityId;
    }
    public long getCompanyId() {
        return companyId;
    }
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
   
    public int getFlatDataEntityType() {
		return flatDataEntityType;
	}
	public void setFlatDataEntityType(int flatDataEntityType) {
		this.flatDataEntityType = flatDataEntityType;
	}
	public long getFlatDataTableId() {
        return flatDataTableId;
    }
    public void setFlatDataTableId(long flatDataTableId) {
        this.flatDataTableId = flatDataTableId;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean isUpdateType() {
        return updateType;
    }
    public void setUpdateType(boolean updateType) {
        this.updateType = updateType;
    }
    public String getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
    public String getModifiedTime() {
        return modifiedTime;
    }
    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
    
    
    public int getStatusInt() {
		return statusInt;
	}
	public void setStatusInt(int statusInt) {
		this.statusInt = statusInt;
	}
	@Override
    public String toString() {
        return "FlatTableDataStatus [flatTableDataEntityId=" + flatTableDataEntityId + ", companyId=" + companyId
                + ", flatDataEntityType=" + flatDataEntityType + ", flatDataTableId=" + flatDataTableId + ", status="
                + status + ", updateType=" + updateType + ", createdTime=" + createdTime + ", modifiedTime="
                + modifiedTime + "]";
    }
    
}
