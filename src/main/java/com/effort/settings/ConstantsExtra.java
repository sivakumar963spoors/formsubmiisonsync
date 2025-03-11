package com.effort.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.effort.dao.SettingsDao;

@Component
public class ConstantsExtra {
	

	@Autowired
	private SettingsDao settingsDao;
	private boolean asynchronousProcess;
	private boolean ltfsLogin;
	private boolean asynchronousCustomEntityProcess;
	public static final int FORM_FIELD_TYPE_METER_READING = 37;
	public static final int FORM_FIELD_TYPE_REGISTRATION_NUMBER_READER = 38;
	public static final int FORM_FIELD_TYPE_SIGNATURE = 13;
	public static final int FORM_FIELD_TYPE_MULTI_DOCUMENT = 41;
	public static final int FORM_FIELD_TYPE_MULTI_IMAGE = 42;
	public static final int FORM_FIELD_TYPE_DURATION = 44;
	public static final int FORM_FIELD_TYPE_LOCATION_DIFF = 45;
	public static final int FORM_FIELD_TYPE_LOCATION_ADDRESS = 47;
	private String intronLifeScienceCompanyId;
	private String backUpSqliteFile;
	private String imeiBasedProvisioningKey;
	private boolean imeiBasedProvisioning;
	private String enableActivityLocationToResolveAddressKey;
	
	
	public boolean isAsynchronousCustomEntityProcess() {
		return asynchronousCustomEntityProcess;
	}
	public void setAsynchronousCustomEntityProcess(boolean asynchronousCustomEntityProcess) {
		this.asynchronousCustomEntityProcess = asynchronousCustomEntityProcess;
	}
	public boolean isAsynchronousProcess() {
		return asynchronousProcess;
	}
	
	public boolean getImeiBasedProvisioning(long companyId) {
		try {
			return Boolean.parseBoolean(settingsDao.getCompanySetting(
					companyId, getImeiBasedProvisioningKey()));
		} catch (Exception e) {
			return imeiBasedProvisioning;
		}
	}
	public String getImeiBasedProvisioningKey() {
		return imeiBasedProvisioningKey;
	}

	public boolean isLtfsLogin() {
		return ltfsLogin;
	}

	public void setAsynchronousProcess(boolean asynchronousProcess) {
		this.asynchronousProcess = asynchronousProcess;
	}
	public String getIntronLifeScienceCompanyId() {
		return intronLifeScienceCompanyId;
	}
	public void setIntronLifeScienceCompanyId(String intronLifeScienceCompanyId) {
		this.intronLifeScienceCompanyId = intronLifeScienceCompanyId;
	}
	public boolean isBackUpSqliteFile(long companyId) {
		try {
			return Boolean.parseBoolean(settingsDao.getCompanySetting(companyId, getBackUpSqliteFile()));
		} catch (Exception e) {
			return false;
		}
	}
	public String getBackUpSqliteFile() {
		return backUpSqliteFile;
	}
	public String getEnableActivityLocationToResolveAddressKey() {
		return enableActivityLocationToResolveAddressKey;
	}
	
}
