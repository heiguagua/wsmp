package com.chinawiserv.wsmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(
	exclude = {
			JmxAutoConfiguration.class,
			MongoAutoConfiguration.class,
	}	
)

public class WsmpSpringStarter {

	public static void main(String[] args) {
		SpringApplication.run(WsmpSpringStarter.class, args);
	}
}
