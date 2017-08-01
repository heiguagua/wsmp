package com.chinawiserv.wsmp.client;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.chinawiserv.wsmp.hbase.HbaseClient;

@Configuration
public class HbaseConfig {

	@Autowired
	private ApplicationContext applicationContext;

	@Bean(name = "hbaseClient")
	public HbaseClient initHbaseClient() throws IOException {

		final EncodedResource res = new EncodedResource(applicationContext.getResource("classpath:app.properties"), Charset.forName("utf-8"));
		final Properties propertice = PropertiesLoaderUtils.loadProperties(res);
		HbaseClient hbaseClient = HbaseClient.apply(propertice);
		return hbaseClient;

	}

}