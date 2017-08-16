package com.chinawiserv.wsmp.controller.data;

import com.chinawiserv.apps.util.logger.Logger;
import com.chinawiserv.wsmp.hbase.HbaseClient;
import com.chinawiserv.wsmp.hbase.query.Audio;
import com.chinawiserv.wsmp.hbase.query.AudioData;
import com.chinawiserv.wsmp.hbase.query.IQ;
import com.chinawiserv.wsmp.hbase.query.Spectrum;
import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

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

		try {
			final long timeStartLong = LocalDateTime.parse(timeStart,
                    this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			final long timeStopLong = LocalDateTime.parse(timeStart,
                    this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

			List<Audio> audios = client.queryAudio(id, centerFreq, timeStartLong, timeStopLong);

			Logger.info("查询音频数据正常 操作时间{},入参：基站id :{},中心频率:{},开始时间:{},结束时间{}",LocalDateTime.now().toString(),id,centerFreq,timeStart,timeStop);

			return audios;
		} catch (Exception e) {
			Logger.error("查询音频数据异常 操作时间{},入参：基站id :{},中心频率:{},开始时间:{},结束时间{} 异常为{}",LocalDateTime.now().toString(),id,centerFreq,timeStart,timeStop,e);
		}
		List<Audio> empty = Collections.emptyList();
		return  empty;
	}

//	/**
//	 * 查询音频数据
//	 *
//	 * @param id 基站Id
//	 * @param centerFreq 中心频率
//	 * @param timeStart 开始时间
//	 * @param timeStop 结束时间间
//	 * @return
//	 * @throws IOException
//	 */
	@GetMapping("/audio/{id}/{rowKey}")
	public Object queryAudio(@PathVariable String id, @PathVariable String rowKey) throws IOException {

		try {
			final AudioData aduioData = this.client.queryAudioData(id, rowKey);
			final byte[] bytes = aduioData.getAudioData();

			Logger.info("查询音频数据成功 操作时间{} 入参为 基站id{},rowKey{}",LocalDateTime.now().toString(),id,rowKey);
			return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=\"%s\"", "test.wav"))
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(bytes);

		} catch (Exception e) {
			Logger.error("查询音频数据异常 操作时间{} 入参为 基站id{},rowKey{} 异常{}",LocalDateTime.now().toString(),id,rowKey,e);
		}
		return  new Object();
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
	public Object queryIQ(@PathVariable String id, @PathVariable long centerFreq,
	        @PathVariable String timeStart, @PathVariable String timeStop) {

		try {
			final long timeStartLong = LocalDateTime.parse(timeStart,
                    this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			final long timeStopLong = LocalDateTime.parse(timeStart,
                    this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

			List<IQ>  iq = this.client.queryIQ(id, centerFreq, timeStartLong, timeStopLong);
			Logger.info("iq 数据查询正常 基站id{} 中心频率为{} 开始时间{} 返回值 {}",id,centerFreq,timeStart);

			return  iq;
		} catch (Exception e) {
			Logger.error("iq 数据查询异常 基站id{} 中心频率为{} 开始时间{} 异常",id,centerFreq,timeStart,e);
		}
		List<IQ> iq = Collections.emptyList();
		return  iq;
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

		List<Spectrum>  spectrums = Collections.emptyList();

		try {
			final long timeStartLong = LocalDateTime.parse(timeStart,
                    this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			final long timeStopLong = LocalDateTime.parse(timeStart,
                    this.formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

			spectrums = client.querySpectrum(id, centerFreq, timeStartLong,
					timeStopLong);

			Logger.info("音频数据查询正常 入参id：{},中心频率：{}，开始时间：{}，返回值 {}",id,centerFreq,timeStart,spectrums);
			return this.client.querySpectrum(id, centerFreq, timeStartLong,
                    timeStopLong);
		} catch (Exception e) {

			Logger.error("音频数据查询异常 入参id：{},中心频率：{}，开始时间：{}，异常 {}",id,centerFreq,timeStart,e);
		}
		return  spectrums;
	}
}
