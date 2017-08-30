package com.chinawiserv.wsmp.controller.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chinawiserv.apps.logger.Logger;
import com.chinawiserv.wsmp.pojo.Alarm;
import com.chinawiserv.wsmp.pojo.MeasureTaskParamDto;
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

import java.lang.reflect.Type;
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

import javax.annotation.PostConstruct;

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
	
	private IImportFreqRangeManageService serviceImportFreqRangeManage;
	
	private FreqService serviceFreq;
	
	private RadioSignalWebService serviceRadioSignal;
	
	private FreqWarningWebService serviceFreqWarning;
	
	@PostConstruct
	public void init() throws MalformedURLException {
		URL url1 = new URL(urlFreq);
	    serviceFreq = new FreqService(url1);
	    URL url2 = new URL(urlRadioSignal);
	    serviceRadioSignal = new RadioSignalWebService(url2);
		URL url3 = new URL(urlFreqWarning);
		serviceFreqWarning = new FreqWarningWebService(url3);
		URL url4 = new URL(urlImportFreqRange);
		serviceImportFreqRangeManage = new ImportFreqRangeManageService(url4).getBasicHttpBindingIImportFreqRangeManageService();

	}
	
	@PostMapping("/rediostatus")
	public Map<String, Object> getRedioStatus(@RequestBody Map<String, Object> param) {
		// 根据用户ID查询自定义频段
		FrequencyRangeQuerySpec request = new FrequencyRangeQuerySpec();
		request.setUserId(param.get("userID").toString());
		List<FrequencyRangeInfo> response = serviceFreq.getFreqServiceHttpSoap12Endpoint().queryFrequencyRange(request);
		final List<String> freqNames = Lists.newArrayList();
		final List<FrequencyBand> freqList = response.stream().map(t -> {
			// 名字放入List中
			freqNames.add(t.getName());
//			// 并设置service2入参:频段
			FrequencyBand freq = new FrequencyBand();
			BigDecimal multiplicand = new BigDecimal(Math.pow(10, 6));
			BigInteger freqMin = new BigDecimal(t.getBeginFreq()).multiply(multiplicand).toBigInteger();
			freq.setFreqMin(freqMin);
			BigInteger freqMax = new BigDecimal(t.getEndFreq()).multiply(multiplicand).toBigInteger();
			freq.setFreqMax(freqMax);
			return freq;
		}).collect(Collectors.toList());
		// 根据自定义频段和监测站查询信号类型
		RadioSignalClassifiedQueryRequest request2 = new RadioSignalClassifiedQueryRequest();
		//设置自定义频段
		ArrayOfFrequencyBand array = new ArrayOfFrequencyBand();
		array.setFrequencyBand(freqList);
		request2.setFreqBandList(array);
		//设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> stationString = (List<String>) param.get("monitorsID");
		stationArray.setString(stationString );
		request2.setStationNumber(stationArray);
		RadioSignalClassifiedQueryResponse response2 = serviceRadioSignal.getRadioSignalWebServiceSoap().queryRadioSignalClassified(request2);

		//查询合法子类型(违规)
		RadioSignalSubClassifiedQueryRequest request3 = new RadioSignalSubClassifiedQueryRequest();
		request3.setFreqBandList(array);
		request3.setStationNumber(stationArray);
		request3.setType(1);
		RadioSignalSubClassifiedQueryResponse response3 = serviceRadioSignal.getRadioSignalWebServiceSoap().queryRadioSignalSubClassified(request3);
		final List<Integer> legalSubTypeCountList = response3.getLstOnFreqBand().getSignalSubStaticsOnFreqBand().stream()
				.map(m -> m.getCount())
				.collect(Collectors.toList());
		
		//查询该频段是否有重点监测数据
//		String important = serviceImportFreqRangeManage.findAllFreqRange();
//		final Type type = new TypeReference<List<MeasureTaskParamDto>>() {}.getType();
//		@SuppressWarnings("unchecked")
//		List<MeasureTaskParamDto> resultList = (List<MeasureTaskParamDto>) JSON.parseObject(important,type);
//		System.out.println("===result:"+resultList);
		
		List<RedioStatusCount> rsCountRows = Lists.newArrayList();
		AtomicInteger index = new AtomicInteger();
		response2.getLstOnFreqBand().getSignalStaticsOnFreqBand().stream().forEach(t -> {
			RedioStatusCount rsCount = new RedioStatusCount();
			rsCount.setRedioName(freqNames.get(index.get()));
			//设置合法子类型（违规）
			rsCount.setLegalUnNormalStationNumber(legalSubTypeCountList.get(index.get()));
			rsCount.setBeginFreq(t.getBand().getFreqMin());
			rsCount.setEndFreq(t.getBand().getFreqMax());
//			System.out.println(t.getBand().getFreqMax() + " - " + t.getBand().getFreqMin());
			//是否有重点监测信息
//			Boolean imporantMonitor = resultList.stream().filter(f -> f.getBeginFreq().equals(t.getBand().getFreqMin()) && f.getEndFreq().equals(t.getBand().getFreqMax()))
//				.findAny().isPresent();
//			System.out.println(imporantMonitor);
//			rsCount.setImportantMonitor(imporantMonitor);
			t.getSignalStaticsLst().getSignalStatics().forEach(t1 -> {
				int signalType = t1.getSignalType();
				int count = t1.getCount();
				switch (signalType) {
				case 1:
					rsCount.setLegalNormalStationNumber(count - legalSubTypeCountList.get(index.get()));
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
			index.getAndIncrement();
			rsCountRows.add(rsCount);
		});
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", rsCountRows.size());
		result.put("data", rsCountRows);
		return result;
	}

	@PostMapping("/alarmundealed")
	public Map<String, Object> getAlarmUnDealed(@RequestBody Map<String, Object> param) {
		// 根据未确认和监测站查询告警
		FreqWarningQueryRequest request = new FreqWarningQueryRequest();
		//未确认
		request.setStatus(0);
		// 设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> stationString = (List<String>) param.get("monitorsID");
		stationArray.setString(stationString);
		request.setStationIDs(stationArray);
		FreqWarningQueryResponse response = serviceFreqWarning.getFreqWarningWebServiceSoap().query(request);
		List<Alarm> alarmRows = Lists.newArrayList();
		response.getWarningInfos().getFreqWarningDTO().stream().forEach(t -> {
			Alarm alarm = new Alarm();
			BigDecimal certerFreq = new BigDecimal(t.getCenterFreq());
			BigDecimal divisor = new BigDecimal(1000000);
			alarm.setRadio(certerFreq.divide(divisor).toString());
			alarm.setFirstTime(t.getSaveDate().toString().replace('T',' '));
			alarm.setLastingTime(t.getLastTimeDate().toString().replace('T',' '));
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
	public Map<String, Object> getAlarmDealed(@RequestBody Map<String, Object> param) {
		// 根据未确认和监测站查询告警
		FreqWarningQueryRequest request = new FreqWarningQueryRequest();
		//确认
		request.setStatus(1);
		// 设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> stationString = (List<String>) param.get("monitorsID");
		stationArray.setString(stationString);
		request.setStationIDs(stationArray);
		FreqWarningQueryResponse response = serviceFreqWarning.getFreqWarningWebServiceSoap().query(request);
		List<Alarm> alarmRows = Lists.newArrayList();
		response.getWarningInfos().getFreqWarningDTO().stream().forEach(t -> {
			Alarm alarm = new Alarm();
			BigDecimal certerFreq = new BigDecimal(t.getCenterFreq());
			BigDecimal divisor = new BigDecimal(1000000);
			alarm.setRadio(certerFreq.divide(divisor).toString());
			alarm.setFirstTime(t.getSaveDate().toString().replace('T',' '));
			alarm.setLastingTime(t.getLastTimeDate().toString().replace('T',' '));
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
	public Map<String, Object> getRedioDetail(@RequestBody Map<String, Object> param){
		
		RadioSignalQueryRequest request = new RadioSignalQueryRequest();
		// 设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		String stations = (String) param.get("monitorsID");
		String[] stationString = stations.split(",");
		stationArray.setString(Arrays.asList(stationString));
		request.setStationIDs(stationArray);
		// 入参:信号类型
		ArrayOfSignalTypeDTO signaTypeArray = new ArrayOfSignalTypeDTO();
		List<SignalTypeDTO> signalTypeDTO = Lists.newArrayList();
		SignalTypeDTO signalType = new SignalTypeDTO();
		signalType.setSignalType(Integer.valueOf(param.get("radioType").toString()));
		signalTypeDTO.add(signalType);
		signaTypeArray.setSignalTypeDTO(signalTypeDTO);
		request.setTypeCodes(signaTypeArray);
		// 入参:频段范围
		request.setBeginFreq(new BigInteger(param.get("beginFreq").toString()));
		request.setEndFreq(new BigInteger(param.get("endFreq").toString()));
		// 返回结果:
		RadioSignalQueryResponse response = serviceRadioSignal.getRadioSignalWebServiceSoap().queryRadioSignal(request);
		List<RedioDetail> redioRows = Lists.newArrayList();
		if((Boolean)(param.get("isSubType"))) {
			//如果是子类型
			response.getRadioSignals().getRadioSignalDTO().stream().forEach(t -> {
				// 判断是否存在任何同一频段下合法信号的子类型信号，如果存在，则添加到返回集里面
				if (t.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO().stream().findAny().isPresent()) {
					Logger.debug("存在子类的大类型频段：{}", t.getCenterFreq());
					t.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO().stream().forEach(t2 -> {
						Logger.debug("子类型:{}", t2.getHistoryType());
					});
					RedioDetail redio = new RedioDetail();
					BigDecimal cFreq = new BigDecimal(t.getCenterFreq());
					BigDecimal divisor = new BigDecimal(1000000);
					redio.setCentor(Float.valueOf((cFreq.divide(divisor).toString())));
					redio.setBand(t.getBandWidth());
					// 设置监测站
					List<String> monitorID = Lists.newArrayList();
					t.getStationDTOs().getRadioSignalStationDTO().stream().forEach(t1 -> {
						monitorID.add(t1.getStationNumber());
					});
					redio.setMonitorID(monitorID);
					// 设置台站
					// redio.setStation(t.getRadioStation().getStation().getName());
					redioRows.add(redio);
				}
				
			});
		}else {
			//如果是大类型
			response.getRadioSignals().getRadioSignalDTO().stream().forEach(t -> {
				// System.out.println("====信号频率："+t.getCenterFreq());
				// 每个大类信号，都要先判断一下是否有子类型信号，如果出来有结果，则减去子类型信号的总数即为大类型的个数。
				// 判断是否存在任何同一频段下合法信号的子类型信号，如果存在，则不添加到返回集里面
					if(t.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO().stream().findAny().isPresent()) {
						Logger.debug("存在子类的大类型频段：{}", t.getCenterFreq());
					}else {
						//如果不存在子类型，则添加到大类型中
						RedioDetail redio = new RedioDetail();
						BigDecimal cFreq = new BigDecimal(t.getCenterFreq());
						BigDecimal divisor = new BigDecimal(1000000);
						redio.setCentor(Float.valueOf((cFreq.divide(divisor).toString())));
						redio.setBand(t.getBandWidth());
						// 设置监测站
						List<String> monitorID = Lists.newArrayList();
						t.getStationDTOs().getRadioSignalStationDTO().stream().forEach(t1 -> {
							// System.out.println("=====监测站ID:"+t1.getStationNumber());
							monitorID.add(t1.getStationNumber());
						});
						redio.setMonitorID(monitorID);
						// 设置台站
						// redio.setStation(t.getRadioStation().getStation().getName());
						redioRows.add(redio);
					}
			});
		}
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", redioRows.size());
		result.put("data", redioRows);
		return result;
	}

	@PostMapping("/monitorsPoint")
	public List<HashMap<String, Object>> getMonitorsPoint(@RequestBody Map<String,Object> param) {
		// 根据信号类型，监测站列表（id or name）查询能够监测到该信号的监测站ID和个数，和每个监测站的该信号个数。
		RadioSignalQueryRequest request = new RadioSignalQueryRequest();
		// 设置信号类型
		ArrayOfSignalTypeDTO value = new ArrayOfSignalTypeDTO();
		List<SignalTypeDTO> signalTypeDTO = Lists.newArrayList();
		SignalTypeDTO dto = new SignalTypeDTO();
		dto.setSignalType(Integer.valueOf(param.get("signalType").toString()));
		signalTypeDTO.add(dto);
		value.setSignalTypeDTO(signalTypeDTO);
		request.setTypeCodes(value);
		// 设置监测站，过滤有信号的监测站ID
		ArrayOfString value1 = new ArrayOfString();
		final List<?> monitorsID = (List<?>) param.get("monitorsNum");
		List<String> string = monitorsID.stream().map(o -> o.toString()).collect(Collectors.toList());
		value1.setString(string);
		request.setStationIDs(value1);
		RadioSignalQueryResponse response = serviceRadioSignal.getRadioSignalWebServiceSoap().queryRadioSignal(request);
		
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
		
		if(param.get("isSubType").toString().equals("true")) {
			//如果是子类型
			Map<String, List<RadioSignalStationDTO>> map1 = response.getRadioSignals().getRadioSignalDTO().stream()
				.filter(t -> t.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO().stream().findAny().isPresent())
				.flatMap(m ->m.getStationDTOs().getRadioSignalStationDTO().stream())
				.collect(Collectors.groupingBy(RadioSignalStationDTO::getStationNumber));
			//统计有信号的同一种监测站的信号数
			@SuppressWarnings("serial")
			List<HashMap<String, Object>> countList = map1.entrySet().stream()
			.map(e -> new HashMap<String,Object>(){{
				put("monitorID",e.getKey());
				put("count",e.getValue().size());
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
		}else {
			//如果是大类型
			Map<String, List<RadioSignalStationDTO>> map1 = response.getRadioSignals().getRadioSignalDTO().stream()
					.collect(Collectors.partitioningBy(t -> t.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO()
							.stream().findAny().isPresent()))
					.get(false).stream()
					.flatMap(m -> m.getStationDTOs().getRadioSignalStationDTO().stream())
					.collect(Collectors.groupingBy(RadioSignalStationDTO::getStationNumber));
			
			//统计有信号的同一种监测站的信号数
			@SuppressWarnings("serial")
			List<HashMap<String, Object>> countList = map1.entrySet().stream()
			.map(e -> new HashMap<String,Object>(){{
				put("monitorID",e.getKey());
				put("count",e.getValue().size());
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
	
}
