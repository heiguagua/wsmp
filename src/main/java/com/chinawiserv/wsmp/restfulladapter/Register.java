package com.chinawiserv.wsmp.restfulladapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;

import com.chinawiserv.apps.util.logger.Logger;

//@Component
public class Register implements ApplicationListener<WSDLServiceEvent> {

	@Autowired
	@Qualifier(WSDLServiceAdapterFactory.type)
	private WSDLServiceAdapterFactory factory;

	@Override
	public void onApplicationEvent(WSDLServiceEvent event) {
		Logger.info("================服务注册开始====================");
		if (event.getClass().isAssignableFrom(WSDLServiceRegistByListEvents.class)) {
			final WSDLServiceRegistByListEvents webEvent = (WSDLServiceRegistByListEvents) event;
			List<RegistInfo> registInfo = webEvent.getRegistInfo();

			registInfo.forEach(r -> {
				factory.registerService(r.getServiceID(), r.getServiceUrl());
			});

		} else {
			final WSDLServiceRegistOneEvent webEvent = (WSDLServiceRegistOneEvent) event;
			final RegistInfo registInfo = webEvent.getRegistInfo();
			factory.registerService(registInfo.getServiceID(), registInfo.getServiceUrl());
		}
		Logger.info("================服务注册完毕====================");
	}

}
