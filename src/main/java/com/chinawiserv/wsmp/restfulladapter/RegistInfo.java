package com.chinawiserv.wsmp.restfulladapter;


public class RegistInfo {

	private String serviceID;

	private String serviceUrl;

	public RegistInfo(String serviceID, String serviceUrl) {
		super();
		this.serviceID = serviceID;
		this.serviceUrl = serviceUrl;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	

}
