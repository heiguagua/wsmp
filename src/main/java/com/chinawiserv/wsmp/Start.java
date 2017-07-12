package com.chinawiserv.wsmp;

import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//@SpringBootApplication
//@MapperScan("com.chinawiserv.wsmp.mapper")
//@EnableAsync
public class Start extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Start.class);
	}

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(Start.class, args);
	}
}
