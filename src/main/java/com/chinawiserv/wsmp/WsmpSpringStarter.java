package com.chinawiserv.wsmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(
	exclude = {
			JmxAutoConfiguration.class,
			MongoAutoConfiguration.class,
	}	
)
@EnableAsync
public class WsmpSpringStarter {

	public static void main(String[] args) {
		SpringApplication.run(WsmpSpringStarter.class, args);
	}
}
