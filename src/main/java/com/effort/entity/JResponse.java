package com.effort.entity;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "abcdefg")
public class JResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String api = null;
	private String externalId = null;
	private String id = "-1";
	private String responseCode = null;
	private String responseMesssage = null;
	private String spoorsWorkId = null;
	private String workStaus = null;

	@XmlAttribute(name = "api")
	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getExternalId() {
		return externalId;
	}

	@XmlElement
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getId() {
		return id;
	}

	@XmlElement
	public void setId(String id) {
		this.id = id;
	}

	public String getResponseCode() {
		return responseCode;
	}

	@XmlElement
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMesssage() {
		return responseMesssage;
	}

	@XmlElement
	public void setResponseMesssage(String responseMesssage) {
		this.responseMesssage = responseMesssage;
	}

	public JResponse(String api) {
		this();
		this.api = api;
	}

	public JResponse() {
	}

	public String getSpoorsWorkId() {
		return spoorsWorkId;
	}

	public String getWorkStaus() {
		return workStaus;
	}

	public void setSpoorsWorkId(String spoorsWorkId) {
		this.spoorsWorkId = spoorsWorkId;
	}

	public void setWorkStaus(String workStaus) {
		this.workStaus = workStaus;
	}
	
	

}