package com.effort.entity;


import java.io.Serializable;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class LabelValue implements Serializable {

	private static final long serialVersionUID = -4050564145570376755L;
	
	private long id;
	private long companyId;
	private long labelId;
	private String labelValue;
	private String labelValueName;
	private String msg;
	private int order;
	private boolean enableDefineLogic;

	private Long appId;
	private boolean visibility;
	// @Transient
		private transient CommonsMultipartFile file = null;
		private long mediaId=0;
	
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
	public long getLabelId() {
		return labelId;
	}
	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}
	public String getLabelValue() {
		return labelValue;
	}
	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}
	public String getLabelValueName() {
		return labelValueName;
	}
	public void setLabelValueName(String labelValueName) {
		this.labelValueName = labelValueName;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	// added newly for apps on 24 Jul 2015
	
	public boolean isVisibility() {
		return visibility;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public Long getMediaId() {
		return mediaId;
	}
	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}
	@Override
	public String toString() {
		return "LabelValue [id=" + id + ", companyId=" + companyId
				+ ", labelId=" + labelId + ", labelValue=" + labelValue
				+ ", labelValueName=" + labelValueName + ", msg=" + msg
				+ ", order=" + order + ", appId=" + appId + ", visibility="
				+ visibility + ", mediaId=" + mediaId + "]";
	}
	public boolean isEnableDefineLogic() {
		return enableDefineLogic;
	}
	public void setEnableDefineLogic(boolean enableDefineLogic) {
		this.enableDefineLogic = enableDefineLogic;
	}
	
	
	
}
