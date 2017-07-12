package com.chinawiserv.wsmp.restfulladapter;

import static com.chinawiserv.apps.util.logger.Logger.optionalError;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.chinawiserv.apps.util.logger.Logger;
import com.google.common.collect.Maps;

@Component(WSDLServiceAdapterFactory.type)
public class WSDLServiceAdapterFactory implements ApplicationEventPublisherAware {

	@Value("${service.home}")
	private String service_home;

	private Map<String, Map<String, Object>> serverInfos = Maps.newConcurrentMap();

	// private Map<String, String> idUrlMap = Maps.newConcurrentMap();

	private Map<String, WSDLService> services = Maps.newConcurrentMap();

	private String serverInfo = "http://";

	public final static String type = "wsdl";

	private ApplicationEventPublisher publisher;

	Map<String, String> serviceTypes = Maps.newConcurrentMap();

	@Async
	@EventListener(value = { ApplicationReadyEvent.class })
	public void loadWSDLservice() throws IOException {

		try {
			Path file = Paths.get(service_home);

			if (!Files.exists(file)) {
				Logger.info("没有发现sevice缓存目录");
				Path paths = Files.createDirectories(file);
				Logger.info("创建service缓存目录".concat(paths.toAbsolutePath().toString()));
			}

			Files.list(Paths.get(service_home)).parallel().filter(p -> !Files.isDirectory(p)).forEach(t -> {

				final String serviceID = t.getFileName().toString();
				final Path path = t;

				try {
					Files.readAllLines(path).stream().forEach(p -> {
						Assert.hasLength(p, "============注册地址为空===========");
						this.registerService(serviceID, p.trim());
					});
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

		}
		catch (Throwable e) {
			// TODO: handle exception
			Logger.error("==============读取服务地址异常==============", e);
		}
		;
		Logger.info("=========WSDLSevicer初始化完成===========");
		publisher.publishEvent(new WSDLServiceAdapterFactoryReady(this));
	}

	public void registerService(String serviceID, String serviceUrl) {

		System.out.println("注册的服务为  :   " + serviceID.concat(" : ").concat(serviceUrl));

		try {
			conection(serviceUrl);
		}
		catch (Throwable e) {
			// TODO Auto-generated catch block
			Logger.error("服务连接出错....", e);
		}

		WSDLService wsdlService = new WSDLService(serviceUrl);
		wsdlService.init();

		if (services.containsKey(serviceID)) {

			services.remove(serviceID);
			this.services.put(serviceID, wsdlService);

		} else if (serverInfos.containsKey(serviceID)) {

			serverInfos.remove(serviceID);
			this.serverInfos.put(serviceID, wsdlService.getMethodInfos(this.serverInfo.concat(serviceID).concat("/")));

		} else if (serviceTypes.containsKey(serviceID)) {

			this.serviceTypes.remove(serviceID);
			this.serviceTypes.put(serviceID, type);

		}

		this.services.put(serviceID, wsdlService);
		this.serverInfos.put(serviceID, wsdlService.getMethodInfos(this.serverInfo.concat(serviceID).concat("/")));
		this.serviceTypes.put(serviceID, type);
	}

	private void conection(String serviceUrl) throws MalformedURLException, IOException {

		URL url = new URL(serviceUrl);
		URLConnection connection = url.openConnection();

		connection.getContent();
	}

	public ServiceMethod getServiceMethod(String serviceId, String methodName) {

		final WSDLService service = Optional.ofNullable(this.services.get(serviceId)).orElseGet(() -> optionalError("没有注册的服务 {}", serviceId));
		return service.getMethod(methodName);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

		this.publisher = applicationEventPublisher;
	}

}
