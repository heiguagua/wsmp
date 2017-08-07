//package com.chinawiserv.wsmp.controller.data;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.chinawiserv.wsmp.hbase.HbaseClient;
//
//@RestController
//@RequestMapping("/asiq")
//public class ASIQDataController {
//
//	@Autowired
//	HbaseClient client;
//
//	/**
//	 * 查询音频数据
//	 *
//	 * @param id 基站Id
//	 * @param centerFreq 中心频率
//	 * @param timeStart 开始时间
//	 * @param timeStop 结束时间
//	 * @return
//	 */
//	@GetMapping("/audio/{id}/{centerFreq}/{timeStart}/{timeStop}")
//	public Object queryAudio(@PathVariable String id, @PathVariable long centerFreq, @PathVariable long timeStart, @PathVariable long timeStop) {
//		return this.client.queryAudio(id, centerFreq, timeStart, timeStop);
//	}
//
//	/**
//	 * 查询IQ数据
//	 *
//	 * @param id 基站Id
//	 * @param centerFreq 中心频率
//	 * @param timeStart 开始时间
//	 * @param timeStop 结束时间
//	 * @return
//	 */
//	@GetMapping("/iq/{id}/{centerFreq}/{timeStart}/{timeStop}")
//	public Object queryIQ(@PathVariable String id, @PathVariable long centerFreq, @PathVariable long timeStart, @PathVariable long timeStop) {
//		return this.client.queryIQ(id, centerFreq, timeStart, timeStop);
//	}
//
//	/**
//	 * 查询频谱数据
//	 *
//	 * @param id 基站Id
//	 * @param centerFreq 中心频率
//	 * @param timeStart 开始时间
//	 * @param timeStop 结束时间
//	 * @return
//	 */
//	@GetMapping("/spectrum/{id}/{centerFreq}/{timeStart}/{timeStop}")
//	public Object querySpectrum(@PathVariable String id, @PathVariable long centerFreq, @PathVariable long timeStart, @PathVariable long timeStop) {
//		return this.client.querySpectrum(id, centerFreq, timeStart, timeStop);
//	}
//
//}
