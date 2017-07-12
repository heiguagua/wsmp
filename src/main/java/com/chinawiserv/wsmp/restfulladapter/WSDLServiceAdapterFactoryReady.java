package com.chinawiserv.wsmp.restfulladapter;

import org.springframework.context.ApplicationEvent;


public class WSDLServiceAdapterFactoryReady extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WSDLServiceAdapterFactoryReady(Object source) {
		super(source);
	}

}
