package com.chinawiserv.wsmp.restfulladapter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.chinawiserv.apps.util.logger.Logger;

@Component
public class ConfigListener implements ApplicationEventPublisherAware {

	@Value("${service.home}")
	private String service_home;

	private ApplicationEventPublisher EventPublisher;

	@Autowired
	@Qualifier(WSDLServiceAdapterFactory.type)
	public WSDLServiceAdapterFactory factory;

	@Async
	@EventListener(classes = WSDLServiceAdapterFactoryReady.class)
	public void beginWork() {

		try {
			Logger.info("=========开始监听Servicer配置文件夹内容变化=========");
			Path file = Paths.get(service_home);

			WatchService service = FileSystems.getDefault().newWatchService();
			file.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

			while (true) {

				WatchKey watchKey = service.take();

				if (watchKey.isValid()) {

					List<WatchEvent<?>> events = watchKey.pollEvents();

					events.stream().filter(t -> t.count() == 1 && t.context().toString().endsWith(".txt")).forEach(t -> {

						Logger.info("=====service文件夹下发现新的配置信息========");
						try {
							Path p = Paths.get(service_home, t.context().toString());

							Files.readAllLines(p).forEach(z -> {
								factory.registerService(p.getFileName().toString(), z);
							});
							Logger.info("=====新的服务已经注册完毕========");
						}
						catch (IOException e) {
							// TODO Auto-generated catch block
							Logger.error("文件读取错误", e);
							return;
						}
					});
					watchKey.reset();
				}
			}
		}
		catch (IOException e) {
			Logger.error("========监听service注册信息失败=========", e);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		// TODO Auto-generated method stub
		this.EventPublisher = applicationEventPublisher;
	}

}
