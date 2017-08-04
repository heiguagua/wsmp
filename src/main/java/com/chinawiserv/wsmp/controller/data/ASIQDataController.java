package com.chinawiserv.wsmp.controller.data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinawiserv.wsmp.hbase.HbaseClient;

@RestController
@RequestMapping("/data/asiq")
public class ASIQDataController {

	@Value("${asio.formatter:yyyyMMddHHmmss}")
	DateTimeFormatter formatter;

	@Autowired
	HbaseClient client;

	/**
	 * 查询音频数据
	 *
	 * @param id 基站Id
	 * @param centerFreq 中心频率
	 * @param timeStart 开始时间
	 * @param timeStop 结束时间
	 * @return
	 */
	@GetMapping("/audio/{id}/{centerFreq}/{timeStart}/{timeStop}")
	public Object queryAudio(@PathVariable String id, @PathVariable long centerFreq, @PathVariable String timeStart,
	        @PathVariable String timeStop) {

		final long timeStartLong = LocalDateTime.parse(timeStart, this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		final long timeStopLong = LocalDateTime.parse(timeStart, this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		return this.client.queryAudio(id, centerFreq, timeStartLong, timeStopLong);
	}

	/**
	 * 查询IQ数据
	 *
	 * @param id 基站Id
	 * @param centerFreq 中心频率
	 * @param timeStart 开始时间
	 * @param timeStop 结束时间
	 * @return
	 */
	@GetMapping("/iq/{id}/{centerFreq}/{timeStart}/{timeStop}")
	public Object queryIQ(@PathVariable String id, @PathVariable long centerFreq, @PathVariable String timeStart, @PathVariable String timeStop) {

		final long timeStartLong = LocalDateTime.parse(timeStart, this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		final long timeStopLong = LocalDateTime.parse(timeStart, this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		return this.client.queryIQ(id, centerFreq, timeStartLong, timeStopLong);
	}

	/**
	 * 查询频谱数据
	 *
	 * @param id 基站Id
	 * @param centerFreq 中心频率
	 * @param timeStart 开始时间
	 * @param timeStop 结束时间
	 * @return
	 */
	@GetMapping("/spectrum/{id}/{centerFreq}/{timeStart}/{timeStop}")
	public Object querySpectrum(@PathVariable String id, @PathVariable long centerFreq, @PathVariable String timeStart,
	        @PathVariable String timeStop) {

		final long timeStartLong = LocalDateTime.parse(timeStart, this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		final long timeStopLong = LocalDateTime.parse(timeStart, this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		return this.client.querySpectrum(id, centerFreq, timeStartLong, timeStopLong);
	}

}
