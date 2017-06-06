package com.chinawiserv.radio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.chinawiserv.radio.business.mapper.UserMapper;

@SpringBootApplication
public class Start {

	public static void main(String[] args) {

		SpringApplication.run(Start.class);
	}
}
