package com.effort.entity;



import java.io.Serializable;
import java.text.ParseException;

import com.effort.beans.http.request.location.Location;
import com.effort.util.Api;
import com.effort.util.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entity  implements Serializable {
	
	private static final long serialVersionUID = -7844387335904825139L;
	
	private long entityId;
	private int companyId;
	private long entitySpecId;
	private int entityStatus;
	private Long filledBy;
	private Long modifiedBy;
	private String createdTime;
	private String modifiedTime;
	private boolean deleted;
	private Location location;
	@JsonProperty(access = Access.READ_ONLY)
	private String filledByName;
	@JsonProperty(access = Access.READ_ONLY)
	private String modifiedByName;
	private String clientSideId;
	private int type;
	private String tzo;
	@JsonProperty(access = Access.READ_ONLY)
	private String identifier;
	private String locationAddress;
	
	private String externalId;
	private String apiUserId;
	
	private boolean forJob;
	private String entityIds;
	private String checksum;
	
	private boolean processed;
	
	private String entityTitle;
	
	private String clientCode;
	private int expired;
	private int allowExpiredSelection;
	
	public long getEntityId() {
		return entityId;
	}
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	public long getEntitySpecId() {
		return entitySpecId;
	}
	public void setEntitySpecId(long entitySpecId) {
		this.entitySpecId = entitySpecId;
	}
	public int getEntityStatus() {
		return entityStatus;
	}
	public void setEntityStatus(int entityStatus) {
		this.entityStatus = entityStatus;
	}
	public Long getFilledBy() {
		return filledBy;
	}
	public void setFilledBy(Long filledBy) {
		this.filledBy = filledBy;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public String getClientSideId() {
		return clientSideId;
	}
	public void setClientSideId(String clientSideId) {
		this.clientSideId = clientSideId;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	@JsonProperty("createdTime")
	public String getCreatedTimeXsd(){
		if(!Api.isEmptyString(createdTime)){
			try {
				return Api.toUtcXsd(createdTime);
			} catch (ParseException e) {
				Log.info(this.getClass(), e.toString(), e);
				return createdTime;
			}
		} else {
			return createdTime;
		}
	}
	
	@JsonProperty("modifiedTime")
	public String getModifiedTimeXsd(){
		if(!Api.isEmptyString(modifiedTime)){
			try {
				return Api.toUtcXsd(modifiedTime);
			} catch (ParseException e) {
				Log.info(this.getClass(), e.toString(), e);
				return modifiedTime;
			}
		} else {
			return modifiedTime;
		}
	}
	
	public String getFilledByName() {
		return filledByName;
	}
	public void setFilledByName(String filledByName) {
		this.filledByName = filledByName;
	}
	public String getModifiedByName() {
		return modifiedByName;
	}
	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getApiUserId() {
		return apiUserId;
	}
	public void setApiUserId(String apiUserId) {
		this.apiUserId = apiUserId;
	}
	@JsonIgnore
	public String getCreatedTime() {
		return createdTime;
	}
	@JsonIgnore
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	@JsonIgnore
	public String getModifiedTime() {
		return modifiedTime;
	}
	@JsonIgnore
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	@JsonIgnore
	public int getCompanyId() {
		return companyId;
	}
	@JsonIgnore
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	@JsonIgnore
	public int getType() {
		return type;
	}
	@JsonIgnore
	public void setType(int type) {
		this.type = type;
	}
	@JsonIgnore
	public String getTzo() {
		return tzo;
	}
	@JsonIgnore
	public void setTzo(String tzo) {
		this.tzo = tzo;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	@JsonIgnore
	public String getLocationAddress() {
		return locationAddress;
	}
	@JsonIgnore
	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}
	@JsonIgnore
	public boolean isForJob() {
		return forJob;
	}
	@JsonIgnore
	public void setForJob(boolean forJob) {
		this.forJob = forJob;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getCreatedTimeLTZ(){
		if(!Api.isEmptyString(createdTime) && !Api.isEmptyString(tzo)){
			try {
				return Api.getDateTimeInTz(Api.getCalendar(Api.getDateTimeInUTC(createdTime)), tzo, 1);
			} catch (ParseException e) {
				Log.info(this.getClass(), e.toString(), e);
				return createdTime;
			}
		} else {
			return createdTime;
		}
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getModifiedTimeLTZ() {
		if(!Api.isEmptyString(modifiedTime) && !Api.isEmptyString(tzo)){
			try {
				return Api.getDateTimeInTz(Api.getCalendar(Api.getDateTimeInUTC(modifiedTime)), tzo, 1);
			} catch (ParseException e) {
				Log.info(this.getClass(), e.toString(), e);
				return modifiedTime;
			}
		} else {
			return modifiedTime;
		}
	}
	
	
	public String toCSV() {
		return "[entityId=" + entityId + ", companyId=" + companyId
				+ ", entitySpecId=" + entitySpecId + ", entityStatus="
				+ entityStatus + ", filledBy=" + filledBy + ", modifiedBy="
				+ modifiedBy + ", createdTime=" + createdTime
				+ ", modifiedTime=" + modifiedTime + ", deleted=" + deleted
				+ ", location=" + location + ", filledByName=" + filledByName
				+ ", modifiedByName=" + modifiedByName + ", clientSideId="
				+ clientSideId + ", type=" + type + ", tzo=" + tzo
				+ ", identifier=" + identifier + ", locationAddress="
				+ locationAddress + ", externalId=" + externalId
				+ ", apiUserId=" + apiUserId + ", forJob=" + forJob + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Entity){
			return getEntityId() == ((Entity)obj).getEntityId();
		} 
		else {
			return super.equals(obj);
		}
	}
	
	@JsonIgnore
	public String getEntityIds() {
		return entityIds;
	}
	@JsonIgnore
	public void setEntityIds(String entityIds) {
		this.entityIds = entityIds;
	}
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	@Override
	public String toString() {
		return "Entity [entityId=" + entityId + ", companyId=" + companyId
				+ ", entitySpecId=" + entitySpecId + ", entityStatus="
				+ entityStatus + ", filledBy=" + filledBy + ", modifiedBy="
				+ modifiedBy + ", createdTime=" + createdTime
				+ ", modifiedTime=" + modifiedTime + ", deleted=" + deleted
				+ ", location=" + location + ", filledByName=" + filledByName
				+ ", modifiedByName=" + modifiedByName + ", clientSideId="
				+ clientSideId + ", type=" + type + ", tzo=" + tzo
				+ ", identifier=" + identifier + ", locationAddress="
				+ locationAddress + ", externalId=" + externalId
				+ ", apiUserId=" + apiUserId + ", forJob=" + forJob
				+ ", entityIds=" + entityIds + ", checksum=" + checksum + "]";
	}
	
	

	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	public String getEntityTitle() {
		return entityTitle;
	}
	public void setEntityTitle(String entityTitle) {
		this.entityTitle = entityTitle;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public int getExpired() {
		return expired;
	}
	public void setExpired(int expired) {
		this.expired = expired;
	}
	public int getAllowExpiredSelection() {
		return allowExpiredSelection;
	}
	public void setAllowExpiredSelection(int allowExpiredSelection) {
		this.allowExpiredSelection = allowExpiredSelection;
	}
	
	
	

}
