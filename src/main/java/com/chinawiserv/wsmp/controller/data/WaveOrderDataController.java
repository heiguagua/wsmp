package com.chinawiserv.wsmp.controller.data;

import com.chinawiserv.wsmp.pojo.Alarm;
import com.chinawiserv.wsmp.pojo.RedioDetail;
import com.chinawiserv.wsmp.pojo.RedioStatusCount;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sefon.ws.model.freq.xsd.FrequencyRangeInfo;
import com.sefon.ws.model.freq.xsd.FrequencyRangeQuerySpec;
import com.sefon.ws.service.impl.FreqService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data/waveorder")
public class WaveOrderDataController {
	
	@Value("${freqWarningWebService.wsdl}")
	private String urlFreqWarning;

	@Value("${radioSignalWebService.wsdl}")
	private String urlRadioSignal;
	
	@Value("${sefon.webservice.freqservice}")
	private String urlFreq;
	
	@Value("${importFreqRangeManageService.wsdl}")
	private String urlImportFreqRange;

	@PostMapping("/rediostatus")
	public Map<String, Object> getRedioStatus(@RequestBody Map<String, Object> param) throws MalformedURLException {
//		 System.out.println("======================"+param);
		// 根据用户ID查询自定义频段
		URL url1 = new URL(urlFreq);
		FreqService service = new FreqService(url1);
		FrequencyRangeQuerySpec model = new FrequencyRangeQuerySpec();
		model.setUserId(param.get("userID").toString());
		List<FrequencyRangeInfo> response = service.getFreqServiceHttpSoap12Endpoint().queryFrequencyRange(model);
		// System.out.println("=======================response:"+JSON.toJSONString(response));
		final List<String> freqNames = Lists.newArrayList();
		List<FrequencyBand> freqList = Lists.newArrayList();
		response.stream().forEach(t -> {
			// 名字放入List中
			freqNames.add(t.getName());
			// 并设置service2入参:频段
			FrequencyBand freq = new FrequencyBand();
			BigDecimal multiplicand = new BigDecimal(Math.pow(10, 6));
			BigInteger freqMin = new BigDecimal(t.getBeginFreq()).multiply(multiplicand).toBigInteger();
			freq.setFreqMin(freqMin);
			BigInteger freqMax = new BigDecimal(t.getEndFreq()).multiply(multiplicand).toBigInteger();
			freq.setFreqMax(freqMax);
			freqList.add(freq);
		});
		// 根据自定义频段和监测站查询信号类型
		URL url2 = new URL(urlRadioSignal);
		RadioSignalWebService service2 = new RadioSignalWebService(url2);
		RadioSignalClassifiedQueryRequest request2 = new RadioSignalClassifiedQueryRequest();
		//设置自定义频段
		ArrayOfFrequencyBand array = new ArrayOfFrequencyBand();
		array.setFrequencyBands(freqList);
		request2.setFreqBandList(array);
		//设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> stationString = (List<String>) param.get("monitorsID");
		stationArray.setStrings(stationString );
		request2.setStationNumber(stationArray);
		RadioSignalClassifiedQueryResponse response2 = service2.getRadioSignalWebServiceSoap().queryRadioSignalClassified(request2);
		// System.out.println("==========================response2"+JSON.toJSONString(response2));
		List<RedioStatusCount> rsCountRows = Lists.newArrayList();
		AtomicInteger index = new AtomicInteger();
		response2.getLstOnFreqBand().getSignalStaticsOnFreqBands().stream().forEach(t -> {
			RedioStatusCount rsCount = new RedioStatusCount();
			rsCount.setRedioName(freqNames.get(index.getAndIncrement()));
			rsCount.setBeginFreq(t.getBand().getFreqMin());
			rsCount.setEndFreq(t.getBand().getFreqMax());
			// System.out.println("第"+index.get()+"次：");
			t.getSignalStaticsLst().getSignalStatics().forEach(t1 -> {
				int type = t1.getSignalType();
				int count = t1.getCount();
				// System.out.println("===type:"+type);
				// System.out.println("===count:"+count);
				switch (type) {
				case 0:
					rsCount.setLegalNormalStationNumber(count);
					break;
				case 1:
					rsCount.setLegalUnNormalStationNumber(count);
					break;
				case 2:
					rsCount.setKonwStationNumber(count);
					break;
				case 3:
					rsCount.setUnKonw(count);
					break;
				case 4:
					rsCount.setIllegalSignal(count);
					break;
				default:;
				}
			});
			rsCountRows.add(rsCount);
		});
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", rsCountRows.size());
		result.put("data", rsCountRows);
		return result;
	}

	@PostMapping("/alarmundealed")
	public Map<String, Object> getAlarmUnDealed(@RequestBody Map<String, Object> param) throws MalformedURLException {
//		 System.out.println("=============================param:"+param);
		// 根据未确认和监测站查询告警
		URL url = new URL(urlFreqWarning);
		FreqWarningWebService freqWarningWS = new FreqWarningWebService(url);
		FreqWarningQueryRequest request = new FreqWarningQueryRequest();
		//未确认
		request.setStatus(0);
		// 设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> stationString = (List<String>) param.get("monitorsID");
		stationArray.setStrings(stationString);
		request.setStationIDs(stationArray);
		FreqWarningQueryResponse response = freqWarningWS.getFreqWarningWebServiceSoap().query(request);
		// System.out.println("=============================response:"+JSON.toJSONString(response));
		List<Alarm> alarmRows = Lists.newArrayList();
		response.getWarningInfos().getFreqWarningDTO().stream().forEach(t -> {
			Alarm alarm = new Alarm();
//			Logger.info("===================================:{}", t.getID());
			BigDecimal certerFreq = new BigDecimal(t.getCenterFreq());
			BigDecimal divisor = new BigDecimal(1000000);
			alarm.setRadio(certerFreq.divide(divisor).toString());
			alarm.setFirstTime(t.getSaveDate().toString().replace('T',' '));
			alarm.setLastingTime(t.getInvalidDate().toString().replace('T',' '));
			alarm.setMark(t.getDescription());
			List<String> stationID = Lists.newArrayList();
			t.getStatList().getFreqWarningStatDTO().stream().forEach(t1 -> {
				stationID.add(t1.getStationGUID());
			});
			alarm.setStationID(stationID);
			alarmRows.add(alarm);
		});
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", alarmRows.size());
		result.put("data", alarmRows);
		return result;
	}

	@PostMapping("/alarmdealed")
	public Map<String, Object> getAlarmDealed(@RequestBody Map<String, Object> param) throws MalformedURLException {
//		 System.out.println("=============================param:"+param);
		// 根据未确认和监测站查询告警
		URL url = new URL(urlFreqWarning);
		FreqWarningWebService freqWarningWS = new FreqWarningWebService(url);
		FreqWarningQueryRequest request = new FreqWarningQueryRequest();
		//确认
		request.setStatus(1);
		// 设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> stationString = (List<String>) param.get("monitorsID");
		stationArray.setStrings(stationString);
		request.setStationIDs(stationArray);
		FreqWarningQueryResponse response = freqWarningWS.getFreqWarningWebServiceSoap().query(request);
		// System.out.println("=============================response:"+JSON.toJSONString(response));
		List<Alarm> alarmRows = Lists.newArrayList();
		response.getWarningInfos().getFreqWarningDTO().stream().forEach(t -> {
			Alarm alarm = new Alarm();
			BigDecimal certerFreq = new BigDecimal(t.getCenterFreq());
			BigDecimal divisor = new BigDecimal(1000000);
			alarm.setRadio(certerFreq.divide(divisor).toString());
			alarm.setFirstTime(t.getSaveDate().toString().replace('T',' '));
			alarm.setLastingTime(t.getInvalidDate().toString().replace('T',' '));
			alarm.setMark(t.getDescription());
			List<String> stationID = Lists.newArrayList();
			t.getStatList().getFreqWarningStatDTO().stream().forEach(t1 -> {
				stationID.add(t1.getStationGUID());
			});
			alarm.setStationID(stationID);
			alarmRows.add(alarm);
		});
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", alarmRows.size());
		result.put("data", alarmRows);
		return result;
	}

	@PostMapping("/radioDetail")
	public Map<String, Object> getRedioDetail(@RequestBody Map<String, Object> param) throws MalformedURLException {
		
//		 System.out.println("==================param:"+param);
		URL url = new URL(urlRadioSignal);
		RadioSignalWebService service = new RadioSignalWebService(url);
		RadioSignalQueryRequest request = new RadioSignalQueryRequest();
		// 设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		String stations = (String) param.get("monitorsID");
		String[] stationString = stations.split(",");
		stationArray.setStrings(Arrays.asList(stationString));
		request.setStationIDs(stationArray);
		// 入参:信号类型
		ArrayOfSignalTypeDTO signaTypeArray = new ArrayOfSignalTypeDTO();
		List<SignalTypeDTO> signalTypeDTO = Lists.newArrayList();
		SignalTypeDTO signalType = new SignalTypeDTO();
		signalType.setSignalType(Integer.valueOf(param.get("radioType").toString()));
		signalTypeDTO.add(signalType);
		signaTypeArray.setSignalTypeDTOs(signalTypeDTO);
		request.setTypeCodes(signaTypeArray);
		// 入参:频段范围
		request.setBeginFreq(new BigInteger(param.get("beginFreq").toString()));
		request.setEndFreq(new BigInteger(param.get("endFreq").toString()));
		// 返回结果:
		RadioSignalQueryResponse response = service.getRadioSignalWebServiceSoap().queryRadioSignal(request);
		// System.out.println("==============================================response:"+JSON.toJSONString(response));
		// System.out.println("==============================================total:"+response.getTotalCount());
		List<RedioDetail> redioRows = Lists.newArrayList();
		response.getRadioSignals().getRadioSignalDTOs().stream().forEach(t -> {
			// System.out.println("====信号频率："+t.getCenterFreq());
			RedioDetail redio = new RedioDetail();
			BigDecimal cFreq = new BigDecimal(t.getCenterFreq());
			BigDecimal divisor = new BigDecimal(1000000);
			redio.setCentor(Float.valueOf((cFreq.divide(divisor).toString())));
			redio.setBand(t.getBandWidth());
			// 设置监测站
			List<String> monitorID = Lists.newArrayList();
			t.getStationDTOs().getRadioSignalStationDTOs().stream().forEach(t1 -> {
				// System.out.println("=====监测站ID:"+t1.getStationNumber());
				monitorID.add(t1.getStationNumber());
			});
			redio.setMonitorID(monitorID);
			// 设置台站
			// redio.setStation(t.getRadioStation().getStation().getName());
			redioRows.add(redio);
		});

		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", redioRows.size());
		result.put("data", redioRows);
		return result;
	}

	@PostMapping("/monitorsPoint")
	public List<HashMap<String, Object>> getMonitorsPoint(@RequestBody Map<String,Object> param) throws MalformedURLException {
		// 根据信号类型，监测站列表（id or name）查询能够监测到该信号的监测站ID和个数，和每个监测站的该信号个数。
		URL url = new URL(urlRadioSignal);
		RadioSignalWebService service = new RadioSignalWebService(url);
		RadioSignalQueryRequest request = new RadioSignalQueryRequest();
		// 设置信号类型
		ArrayOfSignalTypeDTO value = new ArrayOfSignalTypeDTO();
		List<SignalTypeDTO> signalTypeDTO = Lists.newArrayList();
		SignalTypeDTO dto = new SignalTypeDTO();
		dto.setSignalType(Integer.valueOf(param.get("signalType").toString()));
		signalTypeDTO.add(dto);
		value.setSignalTypeDTOs(signalTypeDTO);
		request.setTypeCodes(value);
		// 设置监测站，过滤有信号的监测站ID
		ArrayOfString value1 = new ArrayOfString();
		final List<?> monitorsID = (List<?>) param.get("monitorsNum");
		// System.out.println("=========================================monitorsList:"+monitorsID);
		List<String> string = monitorsID.stream().map(o -> o.toString()).collect(Collectors.toList());
		value1.setStrings(string);
		request.setStationIDs(value1);
		RadioSignalQueryResponse response = service.getRadioSignalWebServiceSoap().queryRadioSignal(request);
		// System.out.println("====================:"+JSON.toJSONString(response));
		Map<String, List<RadioSignalStationDTO>> map1 = response.getRadioSignals().getRadioSignalDTOs().stream()
				.flatMap(t -> t.getStationDTOs().getRadioSignalStationDTOs().stream())
				.collect(Collectors.groupingBy(RadioSignalStationDTO::getStationNumber));
		
		//统计有信号的同一种监测站的信号数
		@SuppressWarnings("serial")
		List<HashMap<String, Object>> countList = map1.entrySet().stream()
			.map(e -> new HashMap<String,Object>(){{
				put("monitorID",e.getKey());
				put("count",e.getValue().size());
			}}).collect(Collectors.toList());
		
		
		//重新封装结果集
		@SuppressWarnings("unchecked")
		final List<Map<String,Object>> monitors = (List<Map<String, Object>>) param.get("monitors");
		@SuppressWarnings("serial")
		List<HashMap<String, Object>> monitorsList = monitors.stream()
			.map(e -> new HashMap<String,Object>(){{
				put("monitorID",e.get("Num"));
				put("count",0);
				put("monitorName",e.get("Name"));
				put("x",e.get("Longitude"));
				put("y",e.get("Latitude"));
			}}).collect(Collectors.toList());
		
		//循环遍历两个List,得到resultList
		List<HashMap<String,Object>> resultList = monitorsList.stream().peek(t -> {
			countList.stream().forEach(t1 -> {
				if(t.get("monitorID").equals(t1.get("monitorID"))) {
					t.put("count", t1.get("count"));
				}
			});
		}).collect(Collectors.toList());
		
		return resultList;
	}
	
}
