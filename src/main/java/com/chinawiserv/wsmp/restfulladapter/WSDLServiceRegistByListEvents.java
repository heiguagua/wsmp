package com.chinawiserv.wsmp.restfulladapter;

import java.util.List;

public class WSDLServiceRegistByListEvents extends WSDLServiceEvent {


	/**
	 * 
	 */



	private static final long serialVersionUID = 44L;

	public WSDLServiceRegistByListEvents(List<RegistInfo> infos) {

		super(infos);
	}

	@SuppressWarnings("unchecked")
	public List<RegistInfo> getRegistInfo() {
		return (List<RegistInfo>) this.source;
	};
}
