package com.effort.settings;

import java.io.Serializable;

import org.springframework.stereotype.Component;
@Component
public class ReportConstants implements Serializable {

	private Long cdsgCompanyId;
	
	public Long getCdsgCompanyId() {
		return cdsgCompanyId;
	}
	public void setCdsgCompanyId(Long cdsgCompanyId) {
		this.cdsgCompanyId = cdsgCompanyId;
	}
}
