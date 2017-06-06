package com.chinawiserv.radio.config.druid;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

//@Configuration
public class DruidConfig {

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String userPassWord;

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.driver-class-name}")
	private String driverName;

	@Bean
	public DataSource getDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUsername(this.userName);
		dataSource.setPassword(this.userPassWord);
		dataSource.setDriverClassName(this.driverName);
		dataSource.setUrl(this.url);
		return dataSource;

	}

}
