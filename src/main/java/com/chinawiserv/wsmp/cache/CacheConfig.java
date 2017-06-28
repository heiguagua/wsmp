package com.chinawiserv.wsmp.cache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chinawiserv.wsmp.pojo.AlarmDealed;
import com.chinawiserv.wsmp.pojo.AlarmUnDealed;
import com.chinawiserv.wsmp.pojo.BandStatusTable;
import com.chinawiserv.wsmp.pojo.RedioType;

@Configuration
public class CacheConfig {

	public final static String MAP_DATA = "mapData";

	@Autowired
	private DefaultResourceLoader def;

	// 实时警告处理表头
	@Bean
	public AlarmDealed getAlarmDealed() throws IOException {

		final Resource resource = this.def.getResource("classpath:table_column/alarm_dealed.properties");
		final EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		final Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		final String radio = p.getProperty("radio", "频率");
		final String firstTime = p.getProperty("first_appear_time", "首次出现时间");
		final String lastingTime = p.getProperty("lasting_time", "持续时间");
		final String radioType = p.getProperty("radio_type", "类型");
		final String station = p.getProperty("station", "监测站");
		final String radioStatus = p.getProperty("radio_status", "状态");
		final String mark = p.getProperty("mark", "备注");
		return new AlarmDealed(radio, firstTime, lastingTime, radioType, station, radioStatus, mark);
	}

	// 实时警告未处理表头
	@Bean
	public AlarmUnDealed getAlarmUnDealed() throws IOException {

		final Resource resource = this.def.getResource("classpath:table_column/alarm_undealed.properties");
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		final Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		final String radio = p.getProperty("radio", "频率");
		final String firstTime = p.getProperty("first_appear_time", "首次出现时间");
		final String lastingTime = p.getProperty("lasting_time", "持续时间");
		final String radioType = p.getProperty("radio_type", "类型");
		final String station = p.getProperty("station", "监测站");
		final String radioStatus = p.getProperty("radio_status", "状态");
		final String mark = p.getProperty("mark", "备注");
		return new AlarmUnDealed(radio, firstTime, lastingTime, radioType, station, radioStatus, mark);
	}

	@Bean
	public BandStatusTable getBandStatusTable() throws IOException {

		// DefaultResourceLoader loader = new DefaultResourceLoader();
		final Resource resource = this.def.getResource("classpath:table_column/band_status.properties");
		final EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		final Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		final String radioName = p.getProperty("radio_name", "频段名称");
		final String legalStation = p.getProperty("legal_station", "合法正常台站");
		final String illegalStation = p.getProperty("illegal_station", "合法违规台站");
		final String legalSignal = p.getProperty("legal_signal", "已知信号");
		final String illegalSignal = p.getProperty("illegal_signal", "不明信号");
		final String unknownSignal = p.getProperty("unknown_signal", "非法信号");
		return new BandStatusTable(radioName, legalStation, illegalStation, legalSignal, unknownSignal, illegalSignal);
	}

	@Bean
	public RedioType getRedioType() throws IOException {

		final Resource resource = this.def.getResource("classpath:checkbox/RedioType.properties");
		final EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		final Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		final String legalNormalStation = p.getProperty("legalNormalStation", "合法台站正常");
		final String illegalNormalStation = p.getProperty("legalUnNormalStation", "合法台站违规");
		final String llegalStation = p.getProperty("legalStation", "合法台站");
		final String illegalSignal = p.getProperty("illegalSignal", "非法信号");
		final String unKonw = p.getProperty("unKonw", "不明信号");
		return new RedioType(legalNormalStation, illegalNormalStation, llegalStation, illegalSignal, unKonw);
	}

	@Bean(name = MAP_DATA)
	public Object mapData() throws IOException {

		final File file = ResourceUtils.getFile("classpath:geoJson/Tianjin_Great.json");
		final Type type = new TypeReference<LinkedHashMap<String, Object>>() {}.getType();

		try (InputStream is = Files.newInputStream(file.toPath())) {

			final LinkedHashMap<String, Object> map = JSON.parseObject(is, type);
			return map.get("geometries");
		}
	}
}
