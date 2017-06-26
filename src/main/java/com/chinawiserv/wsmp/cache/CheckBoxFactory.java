package com.chinawiserv.wsmp.cache;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import com.chinawiserv.wsmp.pojo.RedioType;

@Component
public class CheckBoxFactory {

	@Cacheable(value = "RedioType", sync = true)
	public RedioType getRedioType() throws IOException {
		final DefaultResourceLoader loader = new DefaultResourceLoader();
		final Resource resource = loader.getResource("checkbox/RedioType.properties");
		final EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		final Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		final String legalNormalStation = p.getProperty("legalNormalStation", "合法台站正常");
		final String illegalNormalStation = p.getProperty("legalUnNormalStation", "合法台站违规");
		final String llegalStation = p.getProperty("legalStation", "合法台站");
		final String illegalSignal = p.getProperty("illegalSignal", "非法信号");
		final String unKonw = p.getProperty("unKonw", "不明信号");
		return new RedioType(legalNormalStation, illegalNormalStation, llegalStation, illegalSignal, unKonw);

	}

}
