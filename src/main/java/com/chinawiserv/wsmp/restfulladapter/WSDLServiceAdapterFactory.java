package com.chinawiserv.wsmp.restfulladapter;

import static com.chinawiserv.apps.util.logger.Logger.optionalError;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;

import com.chinawiserv.apps.util.logger.Logger;
import com.google.common.collect.Maps;

//@Component(WSDLServiceAdapterFactory.type)
public class WSDLServiceAdapterFactory implements ApplicationEventPublisherAware {

	@Value("${service.home}")
	private String service_home;

	private Map<String, Map<String, Object>> serverInfos = Maps.newConcurrentMap();

	// private Map<String, String> idUrlMap = Maps.newConcurrentMap();

	private Map<String, WSDLService> services = Maps.newConcurrentMap();

	private String serverInfo = "http://";

	public final static String type = "wsdl";

	private ApplicationEventPublisher EventPublisher;

	Map<String, String> serviceTypes = Maps.newConcurrentMap();

	@EventListener(value = { ApplicationReadyEvent.class })
	public void loadWSDLservice() throws IOException {

		Path file = Paths.get(service_home);

		if (Files.exists(file)) {
			List<RegistInfo> registInfos = Files.list(Paths.get(service_home)).parallel().filter(p -> !Files.isDirectory(p)).map(f -> {
				final String serviceID = f.getFileName().toString();
				final Path path = f;
				String url = "";
				try {
					url = Files.readAllLines(path).get(0);
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new RegistInfo(serviceID, url);
			}).collect(toList());

			Logger.info("=========准备初始注册WSDLService===========");
			EventPublisher.publishEvent(new WSDLServiceRegistByListEvents(registInfos));
		} else {
			Logger.info("没有发现sevice缓存目录");
			Path paths = Files.createDirectories(file);
			Logger.info("创建service缓存目录".concat(paths.toAbsolutePath().toString()));
		}

		EventPublisher.publishEvent(new WSDLServiceAdapterFactoryReady(this));
	}

	public void registerService(String serviceID, String serviceUrl) {
		int index = serviceID.indexOf(".txt");
		serviceID = serviceID.substring(0, index);
		Logger.info("注册的服务为  :   " + serviceID.concat(" : ").concat(serviceUrl));

		try {
			conection(serviceUrl);
		}
		catch (Throwable e) {
			// TODO Auto-generated catch block
			Logger.error("服务连接出错....", e);
		}

		final WSDLService wsdlService = new WSDLService(serviceUrl);
		wsdlService.init();

		if (services.containsKey(serviceID)) {

			services.remove(serviceID);
			this.services.put(serviceID, wsdlService);
			return;

		} else if (serverInfos.containsKey(serviceID)) {

			serverInfos.remove(serviceID);
			this.serverInfos.put(serviceID, wsdlService.getMethodInfos(this.serverInfo.concat(serviceID).concat("/")));
			return;

		} else if (serviceTypes.containsKey(serviceID)) {

			this.serviceTypes.remove(serviceID);
			this.serviceTypes.put(serviceID, type);
			return;
		}

		this.services.put(serviceID, wsdlService);
		this.serverInfos.put(serviceID, wsdlService.getMethodInfos(this.serverInfo.concat(serviceID).concat("/")));
		this.serviceTypes.put(serviceID, type);
		Logger.info("=========" + serviceID + " :  服务的详情" + "=========");
		Logger.info(serverInfos.get(serviceID).toString());
		Logger.info("==============================================");
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

		this.EventPublisher = applicationEventPublisher;
	}
}
