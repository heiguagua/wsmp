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

import com.chinawiserv.wsmp.pojo.AlarmDealed;
import com.chinawiserv.wsmp.pojo.AlarmUnDealed;
import com.chinawiserv.wsmp.pojo.BandStatusTable;

@Component
public class TableColumnFactory {

	// 频段状态表头
	@Cacheable(value = "BandStatus", sync = true)
	public BandStatusTable getBandStatusTable() throws IOException {

		DefaultResourceLoader loader = new DefaultResourceLoader();
		final Resource resource = loader.getResource("table_column/band_status.properties");
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		String radioName = p.getProperty("radio_name", "频段名称");
		String legalStation = p.getProperty("legal_station", "合法正常台站");
		String illegalStation = p.getProperty("illegal_station", "合法违规台站");
		String legalSignal = p.getProperty("legal_signal", "已知信号");
		String illegalSignal = p.getProperty("illegal_signal", "不明信号");
		String unknownSignal = p.getProperty("unknown_signal", "非法信号");
		return new BandStatusTable(radioName, legalStation, illegalStation, legalSignal, unknownSignal, illegalSignal);

	}

	// 实时警告处理表头
	@Cacheable(value = "AlarmDealed", sync = true)
	public AlarmDealed getAlarmDealed() throws IOException {
		DefaultResourceLoader loader = new DefaultResourceLoader();
		final Resource resource = loader.getResource("table_column/alarm_dealed.properties");
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		String radio = p.getProperty("radio", "频率");
		String firstTime = p.getProperty("first_appear_time", "首次出现时间");
		String lastingTime = p.getProperty("lasting_time", "持续时间");
		String radioType = p.getProperty("radio_type", "类型");
		String station = p.getProperty("station", "监测站");
		String radioStatus = p.getProperty("radio_status", "状态");
		String mark = p.getProperty("mark", "备注");
		return new AlarmDealed(radio, firstTime, lastingTime, radioType, station, radioStatus, mark);

	}

	// 实时警告未处理表头
	@Cacheable(value = "AlarmUnDealed", sync = true)
	public AlarmUnDealed getAlarmUnDealed() throws IOException {
		DefaultResourceLoader loader = new DefaultResourceLoader();
		final Resource resource = loader.getResource("table_column/alarm_undealed.properties");
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		String radio = p.getProperty("radio", "频率");
		String firstTime = p.getProperty("first_appear_time", "首次出现时间");
		String lastingTime = p.getProperty("lasting_time", "持续时间");
		String radioType = p.getProperty("radio_type", "类型");
		String station = p.getProperty("station", "监测站");
		String radioStatus = p.getProperty("radio_status", "状态");
		String mark = p.getProperty("mark", "备注");
		return new AlarmUnDealed(radio, firstTime, lastingTime, radioType, station, radioStatus, mark);

	}

}
