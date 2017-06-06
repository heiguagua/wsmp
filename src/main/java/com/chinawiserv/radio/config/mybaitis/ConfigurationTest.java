//package com.chinawiserv.radio.config.mybaitis;
//
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.ibatis.builder.xml.XMLMapperBuilder;
//import org.apache.ibatis.executor.ErrorContext;
//import org.apache.ibatis.parsing.XNode;
//import org.apache.ibatis.session.Configuration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.NestedIOException;
//
//public class ConfigurationTest extends Configuration {
//    private final static Logger logger = LoggerFactory.getLogger(ConfigurationTest.class);
//	 public void reLoadSource(String resource, Path path) throws NestedIOException {
//			logger.info("reload mybatis mapper.xml[" + path + "]");
//			if (isResourceLoaded(resource)) {
//			    this.loadedResources.remove(resource);
//			}
//			try {
//			    final InputStream is = Files.newInputStream(path);
//			    final Map<String, XNode> newSqlFragments = new HashMap<>();
//			    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(is, this, resource, newSqlFragments);
//			    xmlMapperBuilder.parse();
//
//			    if (!newSqlFragments.isEmpty()) {
//				for (Entry<String, XNode> entry : newSqlFragments.entrySet()) {
//				    final String key = entry.getKey();
//				    if (sqlFragments.containsKey(key)) {
//					sqlFragments.remove(key);
//				    }
//				    sqlFragments.put(key, entry.getValue());
//				}
//			    }
//
//			} catch (Exception e) {
//			    logger.error("Failed to parse mapping resource: '" + resource + "'", e);
//			} finally {
//			    ErrorContext.instance().reset();
//			}
//		    }
//}
