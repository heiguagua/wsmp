package com.chinawiserv.wsmp.controller.data;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.RadioSignalDTO;
import org.tempuri.RadioSignalQueryRequest;
import org.tempuri.RadioSignalQueryResponse;

import com.chinawiserv.wsmp.client.WebServiceSoapFactory;
import com.chinawiserv.wsmp.pojo.Singal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RestController
@RequestMapping("/data/signal")
public class SiganlDataController {

	@Autowired
	WebServiceSoapFactory service;

	private ObjectMapper mapper = new ObjectMapper();

	@GetMapping("/FmRate")
	public Object getFMRate() {

		final List<Map<String, Object>> reslut = Lists.newArrayListWithExpectedSize(2);

		final Map<String, Object> map1 = Maps.newLinkedHashMapWithExpectedSize(2);
		final Map<String, Object> map2 = Maps.newLinkedHashMapWithExpectedSize(2);

		map1.put("name", "AM");
		map1.put("value", 20);

		map2.put("name", "FM");
		map2.put("value", 80);

		reslut.add(map1);
		reslut.add(map2);

		return reslut;
	}

	@GetMapping("/station")
	public Object getStationPiont(@RequestParam Map<String, Object> para) {
		System.out.println(para);
		Map<String, Object> map = new HashMap<String, Object>();
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

		final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall("queryRadioSignal",
				mapper.writeValueAsString(param), RadioSignalQueryRequest.class);

		final List<Singal> redio = responce.getRadioSignals().getRadioSignalDTO().stream().map(t -> {

			final Singal singal = new Singal();
			final String id = t.getID();

			singal.setId(id);

			singal.setContext(t.getCenterFreq().toString().concat("   ").concat(t.getSaveDate().toString()));

			return singal;
		}).collect(toList());

		return redio;
	}

	@GetMapping(path = "/stationList", params = "id")
	public Object stationList(@RequestParam Map<String, String> param) throws JsonProcessingException {

		final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall("queryRadioSignal",
				mapper.writeValueAsString(param), RadioSignalQueryRequest.class);

		List<RadioSignalDTO> radioSignals = responce.getRadioSignals().getRadioSignalDTO();
		radioSignals = radioSignals.size() != 0 ? radioSignals : Collections.emptyList();

		return radioSignals;
	}

	@GetMapping(path = "/one", params = "id")
	public Object oneSingal(@RequestParam Map<String, String> param) throws JsonProcessingException {

		final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall("queryRadioSignal",
				mapper.writeValueAsString(param), RadioSignalQueryRequest.class);

		final List<RadioSignalDTO> radioSignals = responce.getRadioSignals().getRadioSignalDTO();
		RadioSignalDTO dto = radioSignals.size() > 0 ? radioSignals.get(0) : new RadioSignalDTO();

		return dto;
	}

	@PutMapping(path = "one/update")
	public Object oneUpdate(@RequestBody Map<String, String> param) {
		return param;

	}

}
