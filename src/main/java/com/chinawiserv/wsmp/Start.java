package com.chinawiserv.wsmp;

import java.io.FileNotFoundException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.chinawiserv.wsmp.mapper")
@EnableAsync
public class Start extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Start.class);
	}

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(Start.class, args);

	}
}
