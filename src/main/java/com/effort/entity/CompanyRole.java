package com.effort.entity;



public class CompanyRole {

    private Long id;
    private String roleName;
    private Long rank;
    private Long companyId;
    private boolean deleted;
    private Long createdBy;
    private Long modifiedBy;
    private String createdTime;
    private String errorRankMessage;
    private String errorRoleMessage;
    public String getModifiedTime() {
        return modifiedTime;
    }
    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
    private String modifiedTime;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public Long getRank() {
        return rank;
    }
    public void setRank(Long rank) {
        this.rank = rank;
    }
    public Long getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    public Long getModifiedBy() {
        return modifiedBy;
    }
    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    public String getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
    public String getErrorRankMessage() {
        return errorRankMessage;
    }
    public void setErrorRankMessage(String errorRankMessage) {
        this.errorRankMessage = errorRankMessage;
    }
    public String getErrorRoleMessage() {
        return errorRoleMessage;
    }
    public void setErrorRoleMessage(String errorRoleMessage) {
        this.errorRoleMessage = errorRoleMessage;
    }
    
    
    
}