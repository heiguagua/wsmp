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
import com.sefon.ws.service.impl.FreqServicePortType;

import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
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

	private static IImportFreqRangeManageService serviceImportFreqRangeManage;

	private static FreqWarningWebServiceSoap serviceFreqWarningSoap;

	private static FreqServicePortType serviceFreqPortType;

	private static RadioSignalWebServiceSoap serviceRadioSignalSoap;

	private final static BigDecimal multiplicand = new BigDecimal(Math.pow(10, 6));

	@PostConstruct
	public void init() throws MalformedURLException {
		URL url1 = new URL(urlFreq);
		FreqService serviceFreq = new FreqService(url1);
		Logger.info("初始化FreqService================================");
		serviceFreqPortType = serviceFreq.getFreqServiceHttpSoap12Endpoint();

		URL url2 = new URL(urlRadioSignal);
		RadioSignalWebService serviceRadioSignal = new RadioSignalWebService(url2);
		Logger.info("初始化RadioSignalWebService======================");
		serviceRadioSignalSoap = serviceRadioSignal.getRadioSignalWebServiceSoap();

		URL url3 = new URL(urlFreqWarning);
		FreqWarningWebService serviceFreqWarning = new FreqWarningWebService(url3);
		Logger.info("初始化FreqWarningWebService======================");
		serviceFreqWarningSoap = serviceFreqWarning.getFreqWarningWebServiceSoap();

		URL url4 = new URL(urlImportFreqRange);
		serviceImportFreqRangeManage = new ImportFreqRangeManageService(url4).getBasicHttpBindingIImportFreqRangeManageService();
		Logger.info("初始化serviceImportFreqRangeManage======================");

	}

	@PostMapping("/rediostatus")
	public Map<String, Object> getRedioStatus(@RequestBody Map<String, Object> param) {
		// 根据用户ID查询自定义频段
		FrequencyRangeQuerySpec request = new FrequencyRangeQuerySpec();
		request.setUserId(param.get("userID").toString());
		List<FrequencyRangeInfo> response = serviceFreqPortType.queryFrequencyRange(request);
		final List<String> freqNames = Lists.newArrayList();
		final List<FrequencyBand> freqList = response.stream().map(t -> {
			// 名字放入List中
			freqNames.add(t.getName());
//			// 并设置service2入参:频段
			FrequencyBand freq = new FrequencyBand();
			BigInteger freqMin = new BigDecimal(t.getBeginFreq()).multiply(multiplicand).toBigInteger();
			freq.setFreqMin(freqMin);
			BigInteger freqMax = new BigDecimal(t.getEndFreq()).multiply(multiplicand).toBigInteger();
			freq.setFreqMax(freqMax);
			return freq;
		}).collect(Collectors.toList());
		Logger.info("查询自定义频段,{},返回:{}",urlFreq,JSON.toJSONString(response));
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
		request2.setStationsOnBand(stationArray);
		RadioSignalClassifiedQueryResponse response2 = serviceRadioSignalSoap.queryRadioSignalClassified(request2);
		Logger.info("查询信号类型统计,{},返回:{}",urlRadioSignal,JSON.toJSONString(response2));



//		//查询合法子类型(违规),并且是无效的
//		RadioSignalSubClassifiedQueryRequest request4 = new RadioSignalSubClassifiedQueryRequest();
//		request4.setFreqBandList(array);
//		request4.setStationsOnBand(stationArray);
//		request4.setType(1);
//		request4.setIsInValid(true);
//		RadioSignalSubClassifiedQueryResponse response4 = serviceRadioSignalSoap.queryRadioSignalSubClassified(request4);
//		Logger.info("查询无效的合法信号(违规)类型统计,{},返回:{}",urlRadioSignal,JSON.toJSONString(response4));
//		final List<Integer> legalSubTypeInvalidCountList = response4.getLstOnFreqBand().getSignalSubStaticsOnFreqBand().stream()
//				.map(m -> m.getCount())
//				.collect(Collectors.toList());

		//查询该频段是否有重点监测数据
		String important = serviceImportFreqRangeManage.findAllFreqRange();
		final Type type = new TypeReference<List<MeasureTaskParamDto>>() {}.getType();
		@SuppressWarnings("unchecked")
		List<MeasureTaskParamDto> resultList = (List<MeasureTaskParamDto>) JSON.parseObject(important,type);

		List<RedioStatusCount> rsCountRows = Lists.newArrayList();
		AtomicInteger index = new AtomicInteger();
		response2.getLstOnFreqBand().getSignalStaticsOnFreqBand().stream().forEach(t -> {
			RedioStatusCount rsCount = new RedioStatusCount();
			rsCount.setRedioName(freqNames.get(index.get()));

			//查询合法子类型(违规)
			RadioSignalQueryRequest request3 = new RadioSignalQueryRequest();
			request3.setStationIDs(stationArray);
			request3.setBeginFreq(t.getBand().getFreqMin());
			request3.setEndFreq(t.getBand().getFreqMax());
			ArrayOfSignalTypeDTO arrayOfSignalTypeDTO=new ArrayOfSignalTypeDTO();
			SignalTypeDTO signalTypeDTO=new SignalTypeDTO();
			ArrayOfSignalTypeDTO arrayOfSignalTypeDTO1=new ArrayOfSignalTypeDTO();
			SignalTypeDTO signalTypeDTO2=new SignalTypeDTO();
			signalTypeDTO2.setSignalType(11);
			arrayOfSignalTypeDTO1.getSignalTypeDTO().add(signalTypeDTO2);
			signalTypeDTO.setAbnormalTypes(arrayOfSignalTypeDTO1);
			signalTypeDTO.setSignalType(1);
			arrayOfSignalTypeDTO.getSignalTypeDTO().add(signalTypeDTO);
			request3.setTypeCodes(arrayOfSignalTypeDTO);
			RadioSignalQueryResponse response3 = serviceRadioSignalSoap.queryRadioSignal(request3);
			Logger.info("查询有效的合法信号(违规)子类型统计,{},返回:{}",urlRadioSignal,JSON.toJSONString(response3));
			ArrayOfRadioSignalDTO arrayOfRadioSignalDTO=response3.getRadioSignals();
			int tempNumber=0;
			if(arrayOfRadioSignalDTO!=null){
				List<RadioSignalDTO> radioSignalDTOList=arrayOfRadioSignalDTO.getRadioSignalDTO();
				if(radioSignalDTOList!=null&&radioSignalDTOList.size()>0){
					for(RadioSignalDTO radioSignalDTO:radioSignalDTOList){
						ArrayOfRadioSignalAbnormalHistoryDTO arrayOfRadioSignalAbnormalHistoryDTO=radioSignalDTO.getAbnormalHistory();
						if(arrayOfRadioSignalAbnormalHistoryDTO!=null){
							List<RadioSignalAbnormalHistoryDTO> radioSignalAbnormalHistoryDTOList=arrayOfRadioSignalAbnormalHistoryDTO.getRadioSignalAbnormalHistoryDTO();
							if(radioSignalAbnormalHistoryDTOList!=null){
								tempNumber+=radioSignalAbnormalHistoryDTOList.size();
							}
						}
					}
				}
			}
			final int legalUnNormalStationNumber=tempNumber;
			//设置合法子类型（违规）
			rsCount.setLegalUnNormalStationNumber(legalUnNormalStationNumber);
			rsCount.setBeginFreq(t.getBand().getFreqMin());
			rsCount.setEndFreq(t.getBand().getFreqMax());
			//是否有重点监测信息
			BigDecimal beginFreqCalculate = new BigDecimal(t.getBand().getFreqMin());
			BigDecimal endFreqCalculate = new BigDecimal(t.getBand().getFreqMax());
			Double beginFreq = Double.valueOf(beginFreqCalculate.divide(multiplicand).toString());
			Double endFreq = Double.valueOf(endFreqCalculate.divide(multiplicand).toString());
			Boolean importantMonitor = resultList.stream().filter(f -> f.getBeginFreq() <= beginFreq && endFreq <= f.getEndFreq())
					.findAny().isPresent();
			rsCount.setImportantMonitor(importantMonitor);
			t.getSignalStaticsLst().getSignalStatics().forEach(t1 -> {
				int signalType = t1.getSignalType();
				int count = t1.getCount();
				switch (signalType) {
					case 1:
						rsCount.setLegalNormalStationNumber(count - legalUnNormalStationNumber);
						break;
					case 2:
						rsCount.setKonwStationNumber(count);
						break;
					case 3:
						rsCount.setIllegalSignal(count);
						break;
					case 4:
						rsCount.setUnKonw(count);
						break;
					default:;
				}
			});
			index.getAndIncrement();
			rsCountRows.add(rsCount);
		});
		//根据起始及终止频率获取告警数量
		FreqWarningStandardQueryRequest warningRequest = new FreqWarningStandardQueryRequest ();
		FreqWarningQueryResponse warningResponse = null;
		for(RedioStatusCount countRow : rsCountRows) {
			warningRequest.setBeginFreq(countRow.getBeginFreq());
			warningRequest.setEndFreq(countRow.getEndFreq());
			warningRequest.setStationIDs(stationArray);
			warningResponse = serviceFreqWarningSoap.queryStandard(warningRequest);
			Long warningTotalCount = warningResponse.getTotalCount();
			countRow.setAlarmingNumber(null == warningTotalCount?0 : warningTotalCount);
		}
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", rsCountRows.size());
		result.put("data", rsCountRows);
		return result;
	}

	@PostMapping("/statisticsForSingnalsAndWarnings")
	public Map<String, Object> getStatisticsForSingnalsAndWarnings(@RequestBody Map<String, Object> param) {
		// 根据未确认和监测站查询告警
		FreqWarningStandardQueryRequest request = new FreqWarningStandardQueryRequest();
//		request.setAreaCode(Integer.valueOf(param.get("areaCode").toString()));
		// 设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		List<String> stationString = (List<String>) param.get("monitorsID");
		stationArray.setString(stationString);
		request.setStationIDs(stationArray);
		FreqWarningQueryResponse response = serviceFreqWarningSoap.queryStandard(request);
		//Logger.info("查询告警未确认,{},返回:{}",urlFreqWarning,JSON.toJSONString(response));
		List<FreqWarningDTO> alarmRows = response.getWarningInfos().getFreqWarningDTO();
		int alarmTotalCount = alarmRows.size();
		//未处理
		int alarmUnconfiredCount = 0;
		//已处理
		int alarmConfirmedCount = 0;
		for(FreqWarningDTO warning : alarmRows) {
			if(0 == warning.getStatus()||1 == warning.getStatus()) {
				alarmUnconfiredCount++;
			} else if(2 == warning.getStatus()) {
				alarmConfirmedCount++;
			}
		}
		RadioSignalQueryRequest signalRequest = new RadioSignalQueryRequest();
//		String areaCode = (String) param.get("areaCode");
//		ArrayOfInt areaIntCodes = new ArrayOfInt();
//		areaIntCodes.getInt().add(Integer.valueOf(areaCode));
//		signalRequest.setAreaCodes(areaIntCodes);
		signalRequest.setStationIDs(stationArray);
		RadioSignalQueryResponse signalResponse = serviceRadioSignalSoap.queryRadioSignal(signalRequest);
		List<RadioSignalDTO> signals = signalResponse.getRadioSignals().getRadioSignalDTO();
		int signalCount = signals.size();
		int signalAutoCount = 0;
		for(RadioSignalDTO signal : signals) {
			if(!signal.isIsManualInsert()) {
				signalAutoCount++;
			}
		}
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("alarmTotalCount", alarmTotalCount);
		result.put("alarmUnconfiredCount", alarmUnconfiredCount);
		result.put("alarmConfirmedCount", alarmConfirmedCount);
		result.put("signalCount", signalCount);
		result.put("signalAutoCount", signalAutoCount);
		return result;
	}

	@PostMapping("/alarmundealed")
	public Map<String, Object> getAlarmUnDealed(@RequestBody Map<String, Object> param) {
		// 根据未确认和监测站查询告警
		FreqWarningStandardQueryRequest request = new FreqWarningStandardQueryRequest();
		//未确认
		request.setStatus(0);
		// 设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> stationString = (List<String>) param.get("monitorsID");
		stationArray.setString(stationString);
		request.setStationIDs(stationArray);
		// 入参:频段范围
		if(null != param.get("beginFreq")) {
			request.setBeginFreq(new BigInteger(param.get("beginFreq").toString()));
		}
		if(null != param.get("endFreq")) {
			request.setEndFreq(new BigInteger(param.get("endFreq").toString()));
		}
		FreqWarningQueryResponse response = serviceFreqWarningSoap.queryStandard(request);
		//Logger.info("查询告警未确认,{},返回:{}",urlFreqWarning,JSON.toJSONString(response));
		List<Alarm> alarmRows = response.getWarningInfos().getFreqWarningDTO().stream()
				.sorted((a, b) -> b.getLastTimeDate().toString().compareTo(a.getLastTimeDate().toString()))
				.map(m -> {
					Alarm alarm = new Alarm();
					BigDecimal certerFreq = new BigDecimal(m.getCenterFreq());
					BigDecimal divisor = new BigDecimal(1000000);
					alarm.setRadio(certerFreq.divide(divisor).toString());
					alarm.setFirstTime(m.getSaveDate().toString().replace('T', ' '));
					alarm.setLastingTime(m.getLastTimeDate().toString().replace('T', ' '));
					alarm.setMark(m.getDescription());
					alarm.setId(m.getID());
					alarm.setKeyword(m.getKeyword());
					List<String> stationID = Lists.newArrayList();
					m.getStatList().getFreqWarningStatDTO().stream().forEach(t1 -> {
						stationID.add(t1.getStationGUID());
					});
					alarm.setStationID(stationID);
					return alarm;
				}).collect(Collectors.toList());
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", alarmRows.size());
		result.put("data", alarmRows);
		return result;
	}

	@PostMapping("/alarmdealed")
	public Map<String, Object> getAlarmDealed(@RequestBody Map<String, Object> param) {
		// 根据未确认和监测站查询告警
		FreqWarningStandardQueryRequest request = new FreqWarningStandardQueryRequest();
		//确认
		request.setStatus(1);
		// 设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> stationString = (List<String>) param.get("monitorsID");
		stationArray.setString(stationString);
		request.setStationIDs(stationArray);
		// 入参:频段范围
		if(null != param.get("beginFreq")) {
			request.setBeginFreq(new BigInteger(param.get("beginFreq").toString()));
		}
		if(null != param.get("endFreq")) {
			request.setEndFreq(new BigInteger(param.get("endFreq").toString()));
		}
		FreqWarningQueryResponse response = serviceFreqWarningSoap.queryStandard(request);
		//Logger.info("查询告警已确认,{},返回:{}",urlFreqWarning,JSON.toJSONString(response));
		List<Alarm> alarmRows = response.getWarningInfos().getFreqWarningDTO().stream()
				.sorted((a,b) -> b.getLastTimeDate().toString().compareTo(a.getLastTimeDate().toString()))
				.map(m -> {
					Alarm alarm = new Alarm();
					BigDecimal certerFreq = new BigDecimal(m.getCenterFreq());
					BigDecimal divisor = new BigDecimal(1000000);
					alarm.setRadio(certerFreq.divide(divisor).toString());
					alarm.setFirstTime(m.getSaveDate().toString().replace('T',' '));
					alarm.setLastingTime(m.getLastTimeDate().toString().replace('T',' '));
					alarm.setMark(m.getDescription());
					alarm.setKeyword(m.getKeyword());
					List<String> stationID = Lists.newArrayList();
					m.getStatList().getFreqWarningStatDTO().stream().forEach(t1 -> {
						stationID.add(t1.getStationGUID());
					});
					alarm.setStationID(stationID);
					return alarm;
				}).collect(Collectors.toList());
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", alarmRows.size());
		result.put("data", alarmRows);
		return result;
	}

	@PostMapping("/radioAutoConfirm")
	public Map<String, Object> getRadioAutoConfirm(@RequestBody Map<String, Object> param) {
		RadioSignalQueryRequest request = new RadioSignalQueryRequest();
		request.setIsManualInsert(false);
		ArrayOfString value = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> stationString = (List<String>) param.get("monitorsID");
		value.setString(stationString);
		request.setStationIDs(value );
		// 入参:频段范围
		if(null != param.get("beginFreq")) {
			request.setBeginFreq(new BigInteger(param.get("beginFreq").toString()));
		}
		if(null != param.get("endFreq")) {
			request.setEndFreq(new BigInteger(param.get("endFreq").toString()));
		}
		ArrayOfSignalTypeDTO typeCodes = new ArrayOfSignalTypeDTO();
		List<SignalTypeDTO> signalTypeDTO = Lists.newArrayList();
		SignalTypeDTO e = new SignalTypeDTO();
		e.setSignalType(1);
		SignalTypeDTO e2 = new SignalTypeDTO();
		e2.setSignalType(2);
		SignalTypeDTO e3 = new SignalTypeDTO();
		e3.setSignalType(3);
		SignalTypeDTO e4 = new SignalTypeDTO();
		e4.setSignalType(4);
		signalTypeDTO.add(e);
		signalTypeDTO.add(e2);
		signalTypeDTO.add(e3);
		signalTypeDTO.add(e4);
		typeCodes.setSignalTypeDTO(signalTypeDTO );
		request.setTypeCodes(typeCodes);
		List<RedioDetail> redioRows = Lists.newArrayList();
		RadioSignalQueryResponse response = serviceRadioSignalSoap.queryRadioSignal(request);
		//Logger.info("查询信号自动确认,{},返回:{}",urlRadioSignal,JSON.toJSONString(response));
		response.getRadioSignals().getRadioSignalDTO().stream().forEach(t -> {
			RedioDetail redio = new RedioDetail();
			BigDecimal cFreq = new BigDecimal(t.getCenterFreq());
			BigDecimal divisor = new BigDecimal(1000000);
			redio.setCentor(Double.valueOf((cFreq.divide(divisor).toString())));
			redio.setBand(t.getBandWidth()/1000);
			redio.setId(t.getID());
			// 设置监测站
			List<String> monitorID = Lists.newArrayList();
			t.getStationDTOs().getRadioSignalStationDTO().stream().forEach(t1 -> {
				// System.out.println("=====监测站ID:"+t1.getStationNumber());
				monitorID.add(t1.getStationNumber());
			});
			redio.setMonitorID(monitorID);
			// 设置台站
			redio.setStation(t.getName());
//			String stationName = Optional.ofNullable(t.getRadioStation())
//					.map(m -> m.getStation())
//					.map(m -> m.getName())
//					.orElse("-");
//			redio.setStation(stationName);
			redioRows.add(redio);
		});
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", redioRows.size());
		result.put("data", redioRows);
		return result;
	}

	@PostMapping("/radioDetail")
	public Map<String, Object> getRedioDetail(@RequestBody Map<String, Object> param){

		RadioSignalQueryRequest request = new RadioSignalQueryRequest();
		// 设置监测站ID列表
		ArrayOfString stationArray = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> stationIDList = (List<String>) param.get("monitorsID");
		stationArray.setString(stationIDList );
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
		RadioSignalQueryResponse response = serviceRadioSignalSoap.queryRadioSignal(request);
		Logger.info("查询信号详情,{},返回:{}",urlRadioSignal,JSON.toJSONString(response));
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
					redio.setCentor(Double.valueOf((cFreq.divide(divisor).toString())));
					redio.setBand(t.getBandWidth()/1000);
					redio.setId(t.getID());
					// 设置监测站
					List<String> monitorID = Lists.newArrayList();
					t.getStationDTOs().getRadioSignalStationDTO().stream().forEach(t1 -> {
						monitorID.add(t1.getStationNumber());
					});
					redio.setMonitorID(monitorID);
					// 设置台站
					redio.setStation(t.getName());
//					String stationName = Optional.ofNullable(t.getRadioStation())
//							.map(m -> m.getStation())
//							.map(m -> m.getName())
//							.orElse("-");
//					redio.setStation(stationName);
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
					redio.setCentor(Double.valueOf((cFreq.divide(divisor).toString())));
					redio.setBand(t.getBandWidth()/1000);
					redio.setId(t.getID());
					// 设置监测站
					List<String> monitorID = Lists.newArrayList();
					t.getStationDTOs().getRadioSignalStationDTO().stream().forEach(t1 -> {
						// System.out.println("=====监测站ID:"+t1.getStationNumber());
						monitorID.add(t1.getStationNumber());
					});
					redio.setMonitorID(monitorID);
					// 设置台站
					redio.setStation(t.getName());
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
		RadioSignalQueryResponse response = serviceRadioSignalSoap.queryRadioSignal(request);
		Logger.info("查询监测站信号统计,{},返回:{}",urlRadioSignal,JSON.toJSONString(response));
		//重新封装结果集
		@SuppressWarnings("unchecked")
		final List<Map<String,Object>> monitors = (List<Map<String, Object>>) param.get("monitors");
		@SuppressWarnings("serial")
		List<HashMap<String, Object>> monitorsList = monitors.stream()
				.map(e -> new HashMap<String, Object>() {{
					put("monitorID", e.get("Num"));
					put("count", 0);
					put("monitorName", e.get("Name"));
					put("x", e.get("Longitude"));
					put("y", e.get("Latitude"));
				}}).collect(Collectors.toList());

		if(param.get("isSubType").toString().equals("true")) {
			//如果是子类型
			Map<String, List<RadioSignalStationDTO>> map1 = response.getRadioSignals().getRadioSignalDTO().stream()
					.filter(t -> t.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO().stream().findAny().isPresent() && t.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO().stream().findAny().get().isIsInvalid() == false)
					.flatMap(m -> m.getStationDTOs().getRadioSignalStationDTO().stream())
					.collect(Collectors.groupingBy(RadioSignalStationDTO::getStationNumber));
			//统计有信号的同一种监测站的信号数
			@SuppressWarnings("serial")
			List<HashMap<String, Object>> countList = map1.entrySet().stream()
					.map(e -> new HashMap<String, Object>() {{
						put("monitorID", e.getKey());
						put("count", e.getValue().size());
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
					.map(e -> new HashMap<String, Object>() {{
						put("monitorID", e.getKey());
						put("count", e.getValue().size());
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

	@PostMapping("/SignalCountOnMonitors")
	public List<HashMap<String,Object>> getSignalCountOnMonitors(@RequestBody Map<String,Object> param) {
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
		RadioSignalQueryResponse response = serviceRadioSignalSoap.queryRadioSignal(request);
		Logger.info("查询监测站上信号个数统计,{},返回:{}",urlRadioSignal,JSON.toJSONString(response));
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
					put("signalType",Integer.valueOf(param.get("signalType").toString()));
					put("isSubType",param.get("isSubType").toString());
				}}).collect(Collectors.toList());

		if(param.get("isSubType").toString().equals("true")) {
			//如果是子类型
			Map<String, List<RadioSignalStationDTO>> map1 = response.getRadioSignals().getRadioSignalDTO().stream()
					.filter(t -> t.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO().stream().findAny().isPresent() )
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

	@PostMapping("/SignalsOnMonitors")
	public Map<String, Object> getSignalsOnMonitors(@RequestBody Map<String,Object> param) {
		RadioSignalQueryRequest request = new RadioSignalQueryRequest();
		// 设置监测站ID
		ArrayOfString stationArray = new ArrayOfString();
		List<String> stationIDList = new ArrayList<String>();
		stationIDList.add(param.get("monitorID").toString());
		stationArray.setString(stationIDList );
		request.setStationIDs(stationArray);
		// 入参:信号类型
		ArrayOfSignalTypeDTO signaTypeArray = new ArrayOfSignalTypeDTO();
		List<SignalTypeDTO> signalTypeDTO = Lists.newArrayList();
		SignalTypeDTO signalType = new SignalTypeDTO();
		signalType.setSignalType(Integer.valueOf(param.get("signalType").toString()));
		signalTypeDTO.add(signalType);
		signaTypeArray.setSignalTypeDTO(signalTypeDTO);
		request.setTypeCodes(signaTypeArray);
		// 返回结果:
		RadioSignalQueryResponse response = serviceRadioSignalSoap.queryRadioSignal(request);
		Logger.info("查询监测站上信号详情,{},返回:{}",urlRadioSignal,JSON.toJSONString(response));
		List<RedioDetail> redioRows = Lists.newArrayList();
		if(param.get("isSubType").toString().equals("true")) {
			//如果是子类型
			response.getRadioSignals().getRadioSignalDTO().stream().forEach(t -> {
				// 判断是否存在任何同一频段下合法信号的子类型信号，如果存在，则添加到返回集里面
				if (t.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO().stream().findAny().isPresent() && t.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO().stream().findAny().get().isIsInvalid() == false) {
					Logger.debug("存在子类的大类型频段：{}", t.getCenterFreq());
					t.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO().stream().forEach(t2 -> {
						Logger.debug("子类型:{}", t2.getHistoryType());
					});
					RedioDetail redio = new RedioDetail();
					BigDecimal cFreq = new BigDecimal(t.getCenterFreq());
					BigDecimal divisor = new BigDecimal(1000000);
					redio.setCentor(Double.valueOf((cFreq.divide(divisor).toString())));
					redio.setBand(t.getBandWidth()/1000);
					redio.setId(t.getID());
					// 设置监测站
					List<String> monitorID = Lists.newArrayList();
					t.getStationDTOs().getRadioSignalStationDTO().stream().forEach(t1 -> {
						monitorID.add(t1.getStationNumber());
					});
					redio.setMonitorID(monitorID);
					// 设置台站
					redio.setStation(t.getName());
//					String stationName = Optional.ofNullable(t.getRadioStation())
//							.map(m -> m.getStation())
//							.map(m -> m.getName())
//							.orElse("-");
//					redio.setStation(stationName);
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
					redio.setCentor(Double.valueOf((cFreq.divide(divisor).toString())));
					redio.setBand(t.getBandWidth()/1000);
					redio.setId(t.getID());
					// 设置监测站
					List<String> monitorID = Lists.newArrayList();
					t.getStationDTOs().getRadioSignalStationDTO().stream().forEach(t1 -> {
						// System.out.println("=====监测站ID:"+t1.getStationNumber());
						monitorID.add(t1.getStationNumber());
					});
					redio.setMonitorID(monitorID);
					// 设置台站
					redio.setStation(t.getName());
					redioRows.add(redio);
				}
			});
		}
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", redioRows.size());
		result.put("data", redioRows);
		return result;
	}
}
