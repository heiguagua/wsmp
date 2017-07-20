package com.chinawiserv.wsmp.controller.data;

import static java.util.stream.Collectors.toList;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.FreqWarningDTO;
import org.tempuri.FreqWarningOperationResponse;
import org.tempuri.FreqWarningQueryRequest;
import org.tempuri.FreqWarningQueryResponse;
import org.tempuri.RStatQuerySignalsRequest;
import org.tempuri.RStatQuerySignalsResponse2;
import org.tempuri.RadioFreqDTO;
import org.tempuri.RadioSignalDTO;
import org.tempuri.RadioSignalOperationReponse;
import org.tempuri.RadioStationDTO;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.chinawiserv.apps.util.logger.Logger;
import com.chinawiserv.wsmp.client.WebServiceSoapFactory;
import com.chinawiserv.wsmp.pojo.IntensiveMonitoring;
import com.chinawiserv.wsmp.pojo.Station;
import com.chinawiserv.wsmp.service.impl.IntensiveMonitoringServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/data/alarm")
public class AlarmDataController {

	@Autowired
	IntensiveMonitoringServiceImpl iIntensiveMonitoringServicel;

	@Autowired
	WebServiceSoapFactory service;

	private ObjectMapper mapper = new ObjectMapper();

	public Object getStationInfo() {
		return null;

	}

	@GetMapping("/dayCharts")
	public Object dayCharts(@RequestParam Map<String, Object> param) {

		String[] xAxis = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24" };
		double[] series = new double[] { 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 51.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 55.2, 58.4,
				60.0, 58.1, 56.2, 58.9 };
		HashMap<String, Object> map = new HashMap<>();
		map.put("xAxis", xAxis);
		map.put("series", series);
		return map;
	}

	@GetMapping("/hourCharts")
	public Object hourCharts(@RequestParam Map<String, Object> param) {

		String[] xAxis = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24" };
		double[] series = new double[] { 55, 60.5, 60.0, 58.1, 56.2, 58.9, 58.2, 57.4, 58.0, 60.1, 59.1, 58.2, 58, 60.0, 58.1, 59.1, 57.9, 51.5, 55.2, 58.4,
				58.2, 58, 57.9, 55.2, 58.4 };
		HashMap<String, Object> map = new HashMap<>();
		map.put("xAxis", xAxis);
		map.put("series", series);
		return map;

	}

	@GetMapping(path = "/monthCharts")
	public Object monthCharts(@RequestParam Map<String, Object> param) {

		// RestRequest re = new RestRequest(request);
		String[] xAxis = new String[] { "1", "10", "20", "30", "40", "50", "60", "70", "80", "90" };
		double[] series = new double[] { 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9 };
		HashMap<String, Object> map = new HashMap<>();
		map.put("xAxis", xAxis);
		map.put("series", series);
		return map;
	}

	@PostMapping(path = "/intensivemonitoring")
	public void intensivemonitoring(@RequestBody IntensiveMonitoring in) {

		if (in.getStatus() == 0) {
			// 需要取消对应的
			EntityWrapper<IntensiveMonitoring> ew = new EntityWrapper<IntensiveMonitoring>(in);
			ew.where("SINGAL_FREQUENCY = {0}", in.getSingalFrequency());
			iIntensiveMonitoringServicel.delete(ew);
		} else {
			iIntensiveMonitoringServicel.insert(in);
		}
	}

	@PostMapping(path = "/warringconfirm")
	public void warning_confirm(@RequestBody String param) throws JsonProcessingException {

		FreqWarningOperationResponse res = (FreqWarningOperationResponse) service.freqWarnServiceCall("update", param, FreqWarningDTO.class);
		ObjectMapper mapper = new ObjectMapper();

		Logger.info(mapper.writeValueAsString(res));
	}

	@GetMapping(path = "/getStation")
	public @ResponseBody Map<String, Object> getStationPiont(@RequestParam Map<String, Object> param) {
		System.out.println(param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("x", "104.06");
		map.put("y", "30.67");
		map.put("count", "45");
		map.put("stationId", "oopsoo");
		return map;
	}

	@PostMapping("/instersingal")
	public String insterSingal(@RequestBody Map<String, Map<String, Object>> param) throws JsonProcessingException {

		final Map<String, Object> signal = param.get("sigal");

		final Map<String, Object> station = param.get("station");
		final FreqWarningQueryResponse response = (FreqWarningQueryResponse) service.freqWarnServiceCall("query",
				mapper.writeValueAsString(signal.get("warmingId")), FreqWarningQueryRequest.class);

		final FreqWarningDTO t = response.getWarningInfos().getFreqWarningDTO().size() > 0 ? response.getWarningInfos().getFreqWarningDTO().get(0)
				: new FreqWarningDTO();

		final BigInteger bandWidth = t.getBandWidth();
		final BigInteger centerFreq = t.getCenterFreq();

		station.put("centerFreq", centerFreq);
		station.put("bandWidth", bandWidth);

		String stationId = (String) signal.get("stationId");

		String typeCode = (String) signal.get("typeCode");

		String areaCode = stationId.substring(0, 4);

		station.put("stationKey", stationId);

		station.put("typeCode", typeCode);

		station.put("areaCode", areaCode);

		final RadioSignalOperationReponse res = (RadioSignalOperationReponse) service.radioSignalServiceCall("insertRadioSignal",
				mapper.writeValueAsString(station), RadioSignalDTO.class);

		Logger.info(mapper.writeValueAsString(res));
		return null;
	}

	@GetMapping(path = { "/StationInfo" }, params = "type")
	public Object stationList(@RequestParam Map<String, Object> map) throws JsonProcessingException {

		final RStatQuerySignalsResponse2 response = (RStatQuerySignalsResponse2) service.radioStationServiceCall("rStatQuerySignals",
				RStatQuerySignalsRequest.class);

		List<Station> reslutDtos = response.getRStatSignalList().getRadioStationSignalDTO().stream().map(t -> {

			final RadioStationDTO radioStationDTO = t.getStation();

			final RadioFreqDTO radioFreqDTO = t.getFreq();

			int centerFre = radioFreqDTO.getCenterFreq().intValue() / 1000000;
			int tapeWidth = radioFreqDTO.getBandWidth().intValue() / 1000000;
			String centerFreString = centerFre + "";
			String tapeWidthString = tapeWidth + "";

			final Station station = new Station(radioStationDTO.getID(), radioStationDTO.getName(), centerFreString, tapeWidthString);

			return station;
		}).collect(toList());

		return reslutDtos;
	}
}
