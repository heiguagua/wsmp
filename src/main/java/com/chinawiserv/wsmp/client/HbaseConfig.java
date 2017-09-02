
package com.chinawiserv.wsmp.client;

import com.chinawiserv.wsmp.hbase.HbaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

@Configuration
public class HbaseConfig  implements ApplicationEventPublisherAware {

	@Autowired
	private ApplicationContext applicationContext;

	@Value("${config.home:classpath:}")
	private String configHome;

	@Bean(name = "hbaseClient")
	public HbaseClient initHbaseClient() throws IOException {
		final EncodedResource res = new EncodedResource(this.applicationContext.getResource(configHome.concat("app.properties")), Charset.forName("utf-8"));
		final Properties propertice = PropertiesLoaderUtils.loadProperties(res);
		HbaseClient hbaseClient = HbaseClient.apply(propertice);
		return hbaseClient;
	}

//	@Bean
//	public LevelLocate initLevelLocate() {
//		LevelLocate levelLocate = new LevelLocate("192.168.21.104", 8444);
//		return levelLocate;
//	}


	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

	}
}
