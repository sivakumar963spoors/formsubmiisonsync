package com.effort.entity;


import java.io.Serializable;
import java.util.List;

public class FlatDataTableConfiguration implements Serializable{

    private static final long serialVersionUID = 1L;

    public static final int QUERY_CREATE = 1;
    public static final int QUERY_ALTER = 2;
    
    public static final int FORM_SPEC_TYPE =1;
    public static final int EMPLOYEE_TYPE =2;
    public static final int ENTITY_TYPE =3;
    public static final int LOCATION_TYPE =4;
    public static final int WORK_TYPE =5;
    public static final int SIGN_IN_SIGN_OUT_TYPE =6;
    
    public static final int ENTITY_UNIQUE_ID =1;
    public static final int PRIMARY_KEY =2;
    
    public static final int customer = 7;
    public static final int list = 14;
    public static final int emplyoee = 15;
    public static final int multiPickList = 17;
    public static final int form = 25;
    
    
    private long flatDataTableId;
    private int flatDataEntityType;
    private String dataEntityUniqueId;
    private String userTableName;
    private String systemTableName;
    private boolean updateDataOnModify;
    private long companyId;
    private boolean deleted;
    private long createdBy;
    private String createdTime;
    private long modifiedBy;
    private String modifiedTime;
    private String query;
    private boolean forWorkAction;
    private String fromdate;
    private Long activeFullPushId;
    
    public boolean isForWorkAction() {
		return forWorkAction;
	}
	public void setForWorkAction(boolean forWorkAction) {
		this.forWorkAction = forWorkAction;
	}
	private boolean containsSectionField = false;

    
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
  
    public long getFlatDataTableId() {
        return flatDataTableId;
    }
    public void setFlatDataTableId(long flatDataTableId) {
        this.flatDataTableId = flatDataTableId;
    }
    public int getFlatDataEntityType() {
        return flatDataEntityType;
    }
    public void setFlatDataEntityType(int flatDataEntityType) {
        this.flatDataEntityType = flatDataEntityType;
    }
    public String getDataEntityUniqueId() {
        return dataEntityUniqueId;
    }
    public void setDataEntityUniqueId(String dataEntityUniqueId) {
        this.dataEntityUniqueId = dataEntityUniqueId;
    }
    public String getUserTableName() {
        return userTableName;
    }
    public void setUserTableName(String userTableName) {
        this.userTableName = userTableName;
    }
    public String getSystemTableName() {
        return systemTableName;
    }
    public void setSystemTableName(String systemTableName) {
        this.systemTableName = systemTableName;
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
    public String getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
    public long getModifiedBy() {
        return modifiedBy;
    }
    public void setModifiedBy(long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    public String getModifiedTime() {
        return modifiedTime;
    }
    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
    public boolean isUpdateDataOnModify() {
        return updateDataOnModify;
    }
    public void setUpdateDataOnModify(boolean updateDataOnModify) {
        this.updateDataOnModify = updateDataOnModify;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
	public boolean containsSectionField() {
		return containsSectionField;
	}
	public void setContainsSectionField(boolean containsSectionField) {
		this.containsSectionField = containsSectionField;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	
	public Long getActiveFullPushId() {
		return activeFullPushId;
	}
	public void setActiveFullPushId(Long activeFullPushId) {
		this.activeFullPushId = activeFullPushId;
	}
	@Override
	public String toString() {
		return "FlatDataTableConfiguration [flatDataTableId=" + flatDataTableId + ", flatDataEntityType="
				+ flatDataEntityType + ", dataEntityUniqueId=" + dataEntityUniqueId + ", userTableName=" + userTableName
				+ ", systemTableName=" + systemTableName + ", updateDataOnModify=" + updateDataOnModify + ", companyId="
				+ companyId + ", deleted=" + deleted + ", createdBy=" + createdBy + ", createdTime=" + createdTime
				+ ", modifiedBy=" + modifiedBy + ", modifiedTime=" + modifiedTime + ", query=" + query
				+ ", forWorkAction=" + forWorkAction + ", containsSectionField=" + containsSectionField
				+ ", flatDataTableColumnConfiguration=" + flatDataTableColumnConfiguration + "]";
	}

}
