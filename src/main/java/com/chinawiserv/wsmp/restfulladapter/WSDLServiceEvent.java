package com.chinawiserv.wsmp.restfulladapter;

import org.springframework.context.ApplicationEvent;

public abstract class WSDLServiceEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3179471801533900316L;

	public WSDLServiceEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
