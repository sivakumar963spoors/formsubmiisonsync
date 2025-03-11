package com.effort.entity;

public class Plugin {
	public  static final  int WORKFKLOW_PLUGIN_NUMBER=2;
	private long pluginId;
	private long companyId;
	private int pluginNo;
	private String pluginName;
	private String pluginClass;
	
	public long getPluginId() {
		return pluginId;
	}
	public void setPluginId(long pluginId) {
		this.pluginId = pluginId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public int getPluginNo() {
		return pluginNo;
	}
	public void setPluginNo(int pluginNo) {
		this.pluginNo = pluginNo;
	}
	public String getPluginName() {
		return pluginName;
	}
	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}
	public String getPluginClass() {
		return pluginClass;
	}
	public void setPluginClass(String pluginClass) {
		this.pluginClass = pluginClass;
	}
}
