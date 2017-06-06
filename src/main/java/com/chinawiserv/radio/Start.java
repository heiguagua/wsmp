package com.chinawiserv.radio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.chinawiserv.radio.business.mapper.UserMapper;

@SpringBootApplication
public class Start {

	public static void main(String[] args) {

		ConfigurableApplicationContext con = SpringApplication.run(Start.class);
		final UserMapper userMapper = con.getBean(UserMapper.class);
		System.out.println(userMapper);
		System.out.println(userMapper.test());
	}
}
