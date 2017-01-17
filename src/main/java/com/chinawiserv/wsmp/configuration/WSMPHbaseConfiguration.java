package com.chinawiserv.wsmp.configuration;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

@org.springframework.context.annotation.Configuration
public class WSMPHbaseConfiguration implements InitializingBean {

	@Bean
	public org.apache.hadoop.conf.Configuration createHbaseConfig(
			@Value("${hbase.zookeeper.quorum}")
			String hbaseIp,
			@Value("${hbase.zookeeper.property.clientPort}")
			String hbasePort,
			@Value("${hbase.zookeeper.znode.parent}")
			String znodeParent) throws FileNotFoundException, URISyntaxException {

		System.setProperty("hadoop.home.dir", Paths.get(ResourceUtils.getURL("Hadoop").toURI()).toString());
		final Configuration configuration = HBaseConfiguration.create();
		configuration.set("hbase.zookeeper.quorum", hbaseIp);
		configuration.set("hbase.zookeeper.property.clientPort", hbasePort);
		configuration.set("zookeeper.znode.parent", znodeParent);
		
		return configuration;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		final String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("windows") >= 0) {
			final String hadoop_Home = Paths.get(ResourceUtils.getURL("Hadoop").toURI()).toString();
			System.setProperty("hadoop.home.dir", hadoop_Home);
		}
	}
}
