package com.chinawiserv.wsmp.controller.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.ArrayOfFrequencyBand;
import org.tempuri.ArrayOfInt;
import org.tempuri.ArrayOfSignalTypeDTO;
import org.tempuri.FreqWarningQueryRequest;
import org.tempuri.FreqWarningQueryResponse;
import org.tempuri.FreqWarningWebService;
import org.tempuri.FrequencyBand;
import org.tempuri.RadioSignalClassifiedQueryRequest;
import org.tempuri.RadioSignalClassifiedQueryResponse;
import org.tempuri.RadioSignalQueryRequest;
import org.tempuri.RadioSignalQueryResponse;
import org.tempuri.RadioSignalWebService;
import org.tempuri.SignalTypeDTO;

import com.alibaba.fastjson.JSON;
import com.chinawiserv.wsmp.pojo.Alarm;
import com.chinawiserv.wsmp.pojo.RedioDetail;
import com.chinawiserv.wsmp.pojo.RedioStatusCount;
import com.chinawiserv.wsmp.pojo.RsbtStation;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sefon.ws.model.freq.xsd.FrequencyRangeInfo;
import com.sefon.ws.model.freq.xsd.FrequencyRangeQuerySpec;
import com.sefon.ws.model.freq.xsd.ObjectFactory;
import com.sefon.ws.service.impl.FreqService;

@RestController
@RequestMapping("/data/waveorder")
public class WaveOrderDataController {

	@GetMapping("/rediostatus")
	public Map<String, Object> getRedioStatus(@RequestParam Map<String, Object> param) {
		//System.out.println("======================"+param);
		//根据用户ID查询自定义频段
		FreqService service = new FreqService();
		ObjectFactory obj = new ObjectFactory();
		FrequencyRangeQuerySpec model = new FrequencyRangeQuerySpec();
		model.setUserId(param.get("userID").toString());
		List<FrequencyRangeInfo> response = service.getFreqServiceHttpSoap12Endpoint().queryFrequencyRange(model);
		//System.out.println("=======================response:"+JSON.toJSONString(response));
		final List<String> freqNames = Lists.newArrayList();
		List<FrequencyBand> freqList = Lists.newArrayList();
		response.stream().forEach(t -> {
			//名字放入List中
			freqNames.add(t.getName());
			//并设置service2入参:频段
			FrequencyBand freq = new FrequencyBand();
			BigDecimal multiplicand = new BigDecimal(Math.pow(10, 6));
			BigInteger freqMin = new BigDecimal(t.getBeginFreq()).multiply(multiplicand).toBigInteger();
			freq.setFreqMin(freqMin);
			BigInteger freqMax = new BigDecimal(t.getEndFreq()).multiply(multiplicand).toBigInteger();
			freq.setFreqMax(freqMax);
			freqList.add(freq);
		});
		//根据自定义频段和区域码查询信号类型
		RadioSignalWebService service2 = new RadioSignalWebService();
		RadioSignalClassifiedQueryRequest request2 = new RadioSignalClassifiedQueryRequest();
		ArrayOfFrequencyBand array = new ArrayOfFrequencyBand();
		array.setFrequencyBand(freqList);
		request2.setFreqBandList(array);
		ArrayOfInt intArray = new ArrayOfInt();
		List<Integer> _int = Lists.newArrayList();
		_int.add(Integer.valueOf(param.get("areaCode").toString()));
		intArray.set_int(_int );
		request2.setAreaCodes(intArray);
		RadioSignalClassifiedQueryResponse response2 = service2.getRadioSignalWebServiceSoap().queryRadioSignalClassified(request2);
		//System.out.println("==========================response2"+JSON.toJSONString(response2));
		List<RedioStatusCount> rsCountRows = Lists.newArrayList();
		AtomicInteger index = new AtomicInteger();
		response2.getLstOnFreqBand().getSignalStaticsOnFreqBand().stream().forEach(t -> {
			RedioStatusCount rsCount = new RedioStatusCount();
			rsCount.setRedioName(freqNames.get(index.getAndIncrement()));
			rsCount.setBeginFreq(t.getBand().getFreqMin());
			rsCount.setEndFreq(t.getBand().getFreqMax());
			//System.out.println("第"+index.get()+"次：");
			t.getSignalStaticsLst().getSignalStatics().forEach(t1 -> {
				int type = t1.getSignalType();
				int count = t1.getCount();
				//System.out.println("===type:"+type);
				//System.out.println("===count:"+count);
				switch(type) {
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
				default:
					;
				}
			});
			rsCountRows.add(rsCount);
		});
		Map<String,Object> result = Maps.newLinkedHashMap();
		result.put("total", rsCountRows.size());
		result.put("data", rsCountRows);
		return result;
	}

	@GetMapping("/alarmundealed")
	public Map<String, Object> getAlarmUnDealed(@RequestParam Map<String, Object> param) {
		//System.out.println("=============================param:"+param);
		//根据未确认和区域码查询告警
		FreqWarningWebService freqWarningWS = new FreqWarningWebService();
		FreqWarningQueryRequest request = new FreqWarningQueryRequest();
		request.setAreaCode(Integer.valueOf(param.get("areaCode").toString()));
		request.setIsInvalid(false);
		FreqWarningQueryResponse response = freqWarningWS.getFreqWarningWebServiceSoap().query(request);
		//System.out.println("=============================response:"+JSON.toJSONString(response));
		List<Alarm> alarmRows = Lists.newArrayList();
		response.getWarningInfos().getFreqWarningDTO().stream().forEach(t -> {
			Alarm alarm = new Alarm();
			alarm.setRadio(t.getCenterFreq().toString());
			alarm.setFirstTime(t.getSaveDate().toString());
			alarm.setLastingTime(t.getInvalidDate().toString());
			alarm.setMark(t.getDescription());
			List<String> stationID = Lists.newArrayList();
			t.getStatList().getFreqWarningStatDTO().stream().forEach(t1 -> {
				stationID.add(t1.getStationGUID());
			});
			alarm.setStationID(stationID);
			alarmRows.add(alarm);
		});
		Map<String,Object> result = Maps.newLinkedHashMap();
		result.put("total",alarmRows.size());
		result.put("data", alarmRows);
		return result;
	}

	@GetMapping("/alarmdealed")
	public Map<String, Object> getAlarmDealed(@RequestParam Map<String, Object> param) {
		//System.out.println("=============================param:"+param);
		//根据未确认和区域码查询告警
		FreqWarningWebService freqWarningWS = new FreqWarningWebService();
		FreqWarningQueryRequest request = new FreqWarningQueryRequest();
		request.setAreaCode(Integer.valueOf(param.get("areaCode").toString()));
		request.setIsInvalid(true);
		FreqWarningQueryResponse response = freqWarningWS.getFreqWarningWebServiceSoap().query(request);
		//System.out.println("=============================response:"+JSON.toJSONString(response));
		List<Alarm> alarmRows = Lists.newArrayList();
		response.getWarningInfos().getFreqWarningDTO().stream().forEach(t -> {
			Alarm alarm = new Alarm();
			alarm.setRadio(t.getCenterFreq().toString());
			alarm.setFirstTime(t.getSaveDate().toString());
			alarm.setLastingTime(t.getInvalidDate().toString());
			alarm.setMark(t.getDescription());
			List<String> stationID = Lists.newArrayList();
			t.getStatList().getFreqWarningStatDTO().stream().forEach(t1 -> {
				stationID.add(t1.getStationGUID());
			});
			alarm.setStationID(stationID);
			alarmRows.add(alarm);
		});
		Map<String,Object> result = Maps.newLinkedHashMap();
		result.put("total",alarmRows.size());
		result.put("data", alarmRows);
		return result;
	}
	
	@GetMapping("/radioDetail")
	public Map<String,Object> getRedioDetail(@RequestParam Map<String,Object> param) {
		//System.out.println("==================param:"+param);
		RadioSignalWebService service = new RadioSignalWebService();
		RadioSignalQueryRequest request = new RadioSignalQueryRequest();
		//入参：区域码
		ArrayOfInt value = new ArrayOfInt();
		List<Integer> _int = Lists.newArrayList();
		_int.add(Integer.valueOf(param.get("areaCode").toString()));
		value.set_int(_int );
		request.setAreaCodes(value);
		//入参:信号类型
		ArrayOfSignalTypeDTO signaTypeArray = new ArrayOfSignalTypeDTO();
		List<SignalTypeDTO> signalTypeDTO = Lists.newArrayList();
		SignalTypeDTO signalType = new SignalTypeDTO();
		signalType.setSignalType(Integer.valueOf(param.get("radioType").toString()));
		signalTypeDTO.add(signalType);
		signaTypeArray.setSignalTypeDTO(signalTypeDTO);
		request.setTypeCodes(signaTypeArray);
		//入参:频段范围
		request.setBeginFreq(new BigInteger(param.get("beginFreq").toString()));
		request.setEndFreq(new BigInteger(param.get("endFreq").toString()));
		//返回结果:
		RadioSignalQueryResponse response = service.getRadioSignalWebServiceSoap().queryRadioSignal(request );
		//System.out.println("==============================================response:"+JSON.toJSONString(response));
		//System.out.println("==============================================total:"+response.getTotalCount());
		List<RedioDetail> redioRows = Lists.newArrayList();
		response.getRadioSignals().getRadioSignalDTO().stream().forEach(t -> {
			//System.out.println("====信号频率："+t.getCenterFreq());
			RedioDetail redio = new RedioDetail();
			BigDecimal cFreq = new BigDecimal(t.getCenterFreq());
			BigDecimal divisor = new BigDecimal(100000);
			redio.setCentor(Float.valueOf((cFreq.divide(divisor).toString())));
			redio.setBand(t.getBandWidth());
			//设置监测站
			List<String> monitorID = Lists.newArrayList();
			t.getStationDTOs().getRadioSignalStationDTO().stream().forEach(t1 -> {
//				System.out.println("=====监测站ID:"+t1.getStationNumber());
				monitorID.add(t1.getStationNumber());
			});
			redio.setMonitorID(monitorID);
			//设置台站
			//redio.setStation(t.getRadioStation().getStation().getName());
			redioRows.add(redio);
		});
		
		Map<String,Object> result = Maps.newLinkedHashMap();
		result.put("total", redioRows.size());
		result.put("data", redioRows);
		return result;
	}

	public Object get(@RequestParam RsbtStation station) {
		/*EntityWrapper<RsbtStation> ew = new EntityWrapper<>(station);
		ew.where("STAT_Type = {0}", station.getSTATType());
		List<RsbtStation> stations = impl.selectList(ew);*/
		return null;
	}

}
