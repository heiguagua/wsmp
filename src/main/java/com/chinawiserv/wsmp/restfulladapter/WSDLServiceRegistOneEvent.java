package com.chinawiserv.wsmp.restfulladapter;

public class WSDLServiceRegistOneEvent extends WSDLServiceEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8657099834390275988L;


	public WSDLServiceRegistOneEvent(RegistInfo infos) {
		super(infos);
	}

	public RegistInfo getRegistInfo() {
		return (RegistInfo) this.source;
	}

}
