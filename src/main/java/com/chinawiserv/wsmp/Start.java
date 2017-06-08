package com.chinawiserv.wsmp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "${mapper}")
public class Start {

	public static void main(String[] args) {
		SpringApplication.run(Start.class);
	}
}
