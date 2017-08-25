package com.chinawiserv.wsmp.controller.data;

import com.alibaba.fastjson.JSON;
import com.chinawiserv.apps.util.logger.Logger;
import com.chinawiserv.wsmp.client.WebServiceSoapFactory;
import com.chinawiserv.wsmp.hbase.HbaseClient;
import com.chinawiserv.wsmp.hbase.query.OccAndMax;
import com.chinawiserv.wsmp.pojo.Singal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.tempuri.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/data/signal")
public class SiganlDataController {

	@Autowired
	private WebServiceSoapFactory service;

	private ObjectMapper mapper = new ObjectMapper();

	@Value("${asio.formatter:yyyyMMddHHmmss}")
	DateTimeFormatter formatter;

	@Autowired
    private HbaseClient hbaseClient;

	@Value("${upperBound.value:5000000}")
    private long upperBound;

	@Value("${upperBound.value:5000000}")
    private long lowerBound;

	@PostMapping("/insterConfig")
	public void insterConfig(@RequestBody Map<String, String> param) {
		System.out.println(param);
	}

	@GetMapping("/modulationRatio")
	public Object getFMRate(@RequestParam String id, @RequestParam String timeStart, @RequestParam String frequency) throws Exception {

		long frequencyLong = Long.parseLong(frequency);
		Map<String, Object> map = Maps.newHashMap();
		//Map<String, Object> map = hbaseClient.queryFeaturePara(id, timeStart, frequencyLong);
		map.put("noise ",4);
		map.put("normal",7);
		return map;
	}

	@GetMapping("/FmRate")
	public Object getFMRate(@RequestParam String id, @RequestParam String timeStart, @RequestParam String timeStop, @RequestParam String frequency) {

		// long frequencyLong = Long.parseLong(frequency);

		Map<String, Object> resluteMap = Maps.newHashMap();

		//Map<String, Object> map = hbaseClient.queryModulationMode(id, timeStart, timeStop, Long.parseLong(frequency));

		try {
			Map<String, Object>  map = hbaseClient.queryModulationMode(id, timeStart, timeStop, Long.parseLong(frequency));
			List<String> lists = Lists.newLinkedList();
//			int sum =  map.values().stream().mapToInt(m->Integer.parseInt(m.toString())).reduce(0,(a, b) ->  a +  b);

			List<Object> reslut = map.entrySet().stream().map(m -> {
				HashMap<String, Object> mapReslut = Maps.newHashMap();
//				double d = (double) ((Integer) m.getValue() / sum);
				mapReslut.put("name", m.getKey());
				mapReslut.put("value", (Integer) m.getValue());
				lists.add(m.getKey());
				return mapReslut;
			}).collect(toList());
			System.out.println(JSON.toJSONString(reslut));
			resluteMap.put("name", lists);
			resluteMap.put("value", reslut);
			Logger.info("调制方式调用正常 操作时间：{} 入参：监测站id{}，开始时间{}，结束时间{}，中心频率{} 返回值：{}", LocalDateTime.now().toString(),id,timeStart,timeStop,frequency,JSON.toJSONString(map));
			return resluteMap;
		} catch (Exception e) {
			Logger.error("调制方式调用异常 操作时间：{} 入参：监测站id{}，开始时间{}，结束时间{}，中心频率{} 异常：{}", LocalDateTime.now().toString(),id,timeStart,timeStop,frequency,e);
		}
		return  resluteMap;
	}

	@GetMapping("/station")
	public Object getStationPiont(@RequestParam Map<String, Object> para) {
		System.out.println(para);
		Map<String, Object> map = new HashMap<>();
		map.put("x", "104.06");
		map.put("y", "30.67");
		map.put("stationId", "xxxxd");
		map.put("count", "45");
		return map;
	}

	@GetMapping("/provisionaldegree")
	public void provisionalDegree(@RequestParam Map<String, Object> para) {
		System.out.println("provisionaldegree" + para);
		System.out.println("waiting...");
	}

	@PutMapping("/signal")
	public String insetSignal(@RequestParam Map<String, Object> para) {
		System.out.println(para);
		return null;
	}

	@GetMapping(path = "/singallist")
	public Object singalList(@RequestParam Map<String, String> param) throws JsonProcessingException {

		try {
			final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall("queryRadioSignal",
                    mapper.writeValueAsString(param), RadioSignalQueryRequest.class);

			return responce.getRadioSignals().getRadioSignalDTO().stream().map(t -> {

                final Singal singal = new Singal();
                final String id = t.getID();

                singal.setId(id);
                singal.setText(t.getCenterFreq().toString().concat("   ").concat(t.getSaveDate().toString()));

				Logger.info("获取信号列表时间成功 操作时间：{} 入参：{} 返回值：{}",LocalDateTime.now().toString(), JSON.toJSONString(param));

                return singal;
            }).collect(toList());
		} catch (JsonProcessingException e) {
			Logger.error("获取信号列表时间异常 操作时间：{} 入参：{} 异常：{}",LocalDateTime.now().toString(), JSON.toJSONString(param),e);
		}

		return  Collections.emptyMap();
	}

	@GetMapping(path = "/stationList", params = "id")
	public Object stationList(@RequestParam Map<String, String> param) throws JsonProcessingException {

		final List<String> reslutList = Lists.newLinkedList();

		try {
			final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall("queryRadioSignal",
                    mapper.writeValueAsString(param), RadioSignalQueryRequest.class);

			responce.getRadioSignals().getRadioSignalDTO().forEach(z -> z.getStationDTOs().getRadioSignalStationDTO().forEach(f -> reslutList.add(f.getStationNumber())));
			Logger.info("获取监测站列表时间成功 操作时间：{} 入参：{} 返回值：{}",LocalDateTime.now().toString(), JSON.toJSONString(param));
		} catch (JsonProcessingException e) {
			Logger.error("获取监测站表时间异常 操作时间：{} 入参：{} 异常：{}",LocalDateTime.now().toString(), JSON.toJSONString(param),e);
		}

		return reslutList.size() != 0 ? reslutList : Collections.emptyList();
	}

	@GetMapping(path = "/one", params = "id")
	public Object oneSingal(@RequestParam Map<String, String> param) throws JsonProcessingException {

		List<RadioSignalDTO> radioSignals =Collections.emptyList();
		try {
			final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall("queryRadioSignal",
                    mapper.writeValueAsString(param), RadioSignalQueryRequest.class);

			radioSignals  = responce.getRadioSignals().getRadioSignalDTO();

			Logger.info("查询一条信号记录成功 入参：{} 返回值{}",JSON.toJSONString(param),JSON.toJSONString(radioSignals));

			return radioSignals !=null ? radioSignals.get(0) : new RadioSignalDTO();
		} catch (JsonProcessingException e) {
			Logger.error("查询一条信号记录异常 操作时间:{} 入参：{} 返回值{} 异常{}",LocalDateTime.now().toLocalDate(),param,e);
		}

		return  radioSignals;
	}

	@PutMapping(path = "/one/update")
	public Object oneUpdate(@RequestBody String param) throws JsonProcessingException {

		try {
			RadioSignalOperationReponse reponce = (RadioSignalOperationReponse) service.radioSignalServiceCall("updateRadioSignalForRequest", param,
					RadioSignalUpdateRequest.class);
			Logger.info("更新一条信号记录成功 入参：{} 返回值{}",JSON.toJSONString(param),JSON.toJSONString(reponce));
		} catch (JsonProcessingException e) {
			Logger.error("查询一条信号记录异常 操作时间:{} 入参：{} 返回值{} 异常{}",LocalDateTime.now().toString(),param,e);
		}

		return null;

	}

	@GetMapping("/maxlevel")
	public Object getMaxlevel(@RequestParam String beginTime, @RequestParam long centorFreq, @RequestParam String stationCode) {

		HashMap<String, Object> restlutHashMap = Maps.newHashMap();
		try {

			Map<Object, Object> reponceReslut = hbaseClient.queryMaxLevels(stationCode, centorFreq, upperBound, lowerBound, beginTime);

			LinkedList<Object> xAxis = Lists.newLinkedList();
			LinkedList<Object> series = Lists.newLinkedList();


			final double pow = Math.pow(10, 6);

			reponceReslut.forEach((k, v) -> {

				final double key = Double.parseDouble(k.toString()) / pow;

				xAxis.add(key);
				series.add(v);
			});



			restlutHashMap.put("xAxis", xAxis);
			restlutHashMap.put("series", series);

			return restlutHashMap;

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			Logger.error("峰值查询异常", e);

			restlutHashMap.put("xAxis", Collections.emptyList());
			restlutHashMap.put("series", Collections.emptyList());
			return restlutHashMap;
		}

	}

	@GetMapping(path = "/monthCharts")
	public Object monthCharts(@RequestParam String beginTime, @RequestParam long centorFreq, @RequestParam String stationCode) {
		HashMap<String, Object> resoluteHashMap = Maps.newHashMap();
		try {

			centorFreq = (long) (88.8 * 1000000);
			OccAndMax reslutResponce = hbaseClient.queryOccDay("52010062", beginTime, 90, centorFreq);
			Map<String, Object> occMap = reslutResponce.getOcc();
			if (occMap.size() == 0) {
				HashMap<String, Object> restlutHashMap = Maps.newHashMap();
				String[] xAxis = new String[] {};
				double[] series = new double[] {};
				restlutHashMap.put("xAxis", xAxis);
				restlutHashMap.put("series", series);
				return restlutHashMap;
			}
			else {
				LinkedList<Object> xAxis = Lists.newLinkedList();
				LinkedList<Object> series = Lists.newLinkedList();

				final double pow = Math.pow(10, 6);

				occMap.forEach((k, v) -> {

					final double key = Double.parseDouble(k) / pow;

					xAxis.add(key);
					series.add(v);
				});



				resoluteHashMap.put("xAxis", xAxis);
				resoluteHashMap.put("series", series);
			}

			return resoluteHashMap;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HashMap<String, Object> restlutHashMap = Maps.newHashMap();
			String[] xAxis = new String[] {};
			double[] series = new double[] {};
			restlutHashMap.put("xAxis", xAxis);
			restlutHashMap.put("series", series);
			return restlutHashMap;
		}

	}

	@GetMapping("/AbnormalHistory")
	public  Object getAbnormalHistory(@RequestParam Map<Object,Object> param) throws JsonProcessingException {

		String paramStr = JSON.toJSONString(param);

		Map<Object,Object> reslute = Maps.newHashMap();

		AbnormalHistoryQueryResponse response = (AbnormalHistoryQueryResponse) service.radioSignalServiceCall("queryAbnormalHistory",paramStr,AbnormalHistoryRequest.class);
		List<RadioSignalAbnormalHistoryDTO> dto = response.getHistorys().getRadioSignalAbnormalHistoryDTO();
		RadioSignalAbnormalHistoryDTO historyDTO = dto.stream().findFirst().orElseGet(()-> new RadioSignalAbnormalHistoryDTO());

		String id = historyDTO.getID();
		if (StringUtils.isEmpty(id)){
			reslute.put("id","");
		}else{
			reslute.put("id",id);
			reslute.put("saveDate",historyDTO.getSaveDate().toString().replace("T"," "));
			reslute.put("historyType",historyDTO.getHistoryType());
			reslute.put("invalidDate",historyDTO.getInvalidDate().toString().replace("T"," "));
			reslute.put("des",historyDTO.getDes());
		}
		
		return  reslute;
	}

	@PostMapping( value =  "/AbnormalHistory",headers = {"contentType=application/json"})
	public  @ResponseBody String  insterAbnormalHistory(@RequestBody Map<String,Object> param) throws JsonProcessingException, DatatypeConfigurationException {

		String  startTime = (String) param.get("startTime");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		final long timeStartLong = LocalDateTime.parse(startTime,
				dateTimeFormatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

		GregorianCalendar beginGregorianCalendar =new GregorianCalendar();
		beginGregorianCalendar.setTime(new Date(timeStartLong));
		XMLGregorianCalendar beginCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(beginGregorianCalendar);

		param.replace("startTime",beginCalendar);
		param.replace("isInvalid",true);
		service.radioSignalServiceCall("insertAbnormalHistory",JSON.toJSONString(param),RadioSignalAbnormalHistoryDTO.class);

		return  "sussed";
	}

	@PutMapping(path = {"/AbnormalHistory"},params = {"isInvalid=0"},headers = {"contentType=application/json"})
	public  @ResponseBody String  updateAbnormalHistory(@RequestBody Map<String,Object> param) throws JsonProcessingException, DatatypeConfigurationException {
		final String  startTime = (String) param.get("startTime");

		final long timeStartLong = LocalDateTime.parse(startTime,
				formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();


		GregorianCalendar beginGregorianCalendar =new GregorianCalendar();
		beginGregorianCalendar.setTime(new Date(timeStartLong));
		XMLGregorianCalendar beginCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(beginGregorianCalendar);

		param.replace("startTime",beginCalendar);
		param.replace("isInvalid",true);

		service.radioSignalServiceCall("updateAbnormalHistory",JSON.toJSONString(param),RadioSignalAbnormalHistoryDTO.class);
		return  "sussed";
	}

	@PutMapping(path={"/AbnormalHistory",""},params = {"isInvalid=1"},headers = {"contentType=application/json"})
	public  @ResponseBody  String recoveryAbnormalHistory(@RequestBody Map<String,Object> param) throws JsonProcessingException, DatatypeConfigurationException {

		final String  startTime = (String) param.get("startTime");
		final String  endTime = (String) param.get("invalidDate");

		final long timeStartLong = LocalDateTime.parse(startTime,
				formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

		final long timeEndLong = LocalDateTime.parse(endTime,
				formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

		GregorianCalendar beginGregorianCalendar =new GregorianCalendar();
		GregorianCalendar endGregorianCalendar =new GregorianCalendar();

		beginGregorianCalendar.setTime(new Date(timeStartLong));
		endGregorianCalendar.setTime(new Date(timeEndLong));

		XMLGregorianCalendar beginCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(beginGregorianCalendar);
		XMLGregorianCalendar endCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(endGregorianCalendar);

		param.replace("startTime",beginCalendar);
		param.replace("invalidDate",endCalendar);
		param.replace("isInvalid",false);

		service.radioSignalServiceCall("updateAbnormalHistory",JSON.toJSONString(param),RadioSignalAbnormalHistoryDTO.class);
		return "sussed";
	}
}
