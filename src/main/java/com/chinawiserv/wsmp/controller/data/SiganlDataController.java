
package com.chinawiserv.wsmp.controller.data;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.RadioSignalDTO;
import org.tempuri.RadioSignalOperationReponse;
import org.tempuri.RadioSignalQueryRequest;
import org.tempuri.RadioSignalQueryResponse;

import com.chinawiserv.apps.util.logger.Logger;
import com.chinawiserv.wsmp.client.WebServiceSoapFactory;
import com.chinawiserv.wsmp.hbase.HbaseClient;
import com.chinawiserv.wsmp.hbase.query.OccAndMax;
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

    @Autowired
    HbaseClient hbaseClient;

    @Value("${upperBound.value:5000000}")
    long upperBound;

    @Value("${upperBound.value:5000000}")
    long lowerBound;

    @PostMapping("/insterConfig")
    public void insterConfig(@RequestBody Map<String, String> param) {
	System.out.println(param);
    }

    @GetMapping("/modulationRatio")
    public Object getFMRate(@RequestParam String id, @RequestParam String timeStart, @RequestParam String frequency)
	    throws Exception {

	long frequencyLong = Long.parseLong(frequency);

	Map<String, Object> map = hbaseClient.queryFeaturePara(id, timeStart, frequencyLong);

	return map;
    }

    @GetMapping("/FmRate")
    public Object getFMRate(@RequestParam String id, @RequestParam String timeStart, @RequestParam String timeStop,
	    @RequestParam String frequency) throws Exception {

	long frequencyLong = Long.parseLong(frequency);

	Map<String, Object> map = hbaseClient.queryFeaturePara(id, timeStart, frequencyLong);

	return map;
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

	final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall(
		"queryRadioSignal", mapper.writeValueAsString(param), RadioSignalQueryRequest.class);
	System.out.println(mapper.writeValueAsString(responce));
	final List<Singal> redio = responce.getRadioSignals().getRadioSignalDTO().stream().map(t -> {

	    final Singal singal = new Singal();
	    final String id = t.getID();

	    singal.setId(id);

	    singal.setText(t.getCenterFreq().toString().concat("   ").concat(t.getSaveDate().toString()));

	    return singal;
	}).collect(toList());

	return redio;
    }

    @GetMapping(path = "/stationList", params = "id")
    public Object stationList(@RequestParam Map<String, String> param) throws JsonProcessingException {

	final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall(
		"queryRadioSignal", mapper.writeValueAsString(param), RadioSignalQueryRequest.class);

	final List<String> reslutList = Lists.newLinkedList();
	List<String> list = Lists.newLinkedList();

	responce.getRadioSignals().getRadioSignalDTO().forEach(z -> {
	    z.getStationDTOs().getRadioSignalStationDTO().forEach(f -> {
		reslutList.add(f.getStationNumber());
	    });
	});

	list = reslutList.size() != 0 ? reslutList : Collections.emptyList();

	return list;
    }

    @GetMapping(path = "/one", params = "id")
    public Object oneSingal(@RequestParam Map<String, String> param) throws JsonProcessingException {

	final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall(
		"queryRadioSignal", mapper.writeValueAsString(param), RadioSignalQueryRequest.class);

	final List<RadioSignalDTO> radioSignals = responce.getRadioSignals().getRadioSignalDTO();
	RadioSignalDTO dto = radioSignals.size() > 0 ? radioSignals.get(0) : new RadioSignalDTO();

	return dto;
    }

    @PutMapping(path = "/one/update")
    public Object oneUpdate(@RequestBody String param) throws JsonProcessingException {

	RadioSignalOperationReponse reponce = (RadioSignalOperationReponse) service
		.radioSignalServiceCall("updateRadioSignalForRequest", param, RadioSignalDTO.class);
	System.out.println(mapper.writeValueAsString(reponce));
	return param;

    }

    @GetMapping("/maxlevel")
    public Object getMaxlevel(@RequestParam String beginTime, @RequestParam long centorFreq,
	    @RequestParam String stationCode) {
	try {

	    Map<Object, Object> reponceReslut = hbaseClient.queryMaxLevels(stationCode, centorFreq, upperBound,
		    lowerBound, beginTime);

	    LinkedList<Object> xAxis = Lists.newLinkedList();
	    LinkedList<Object> series = Lists.newLinkedList();

	    final double pow = Math.pow(10, 6);

	    reponceReslut.forEach((k, v) -> {

		final double key = Double.parseDouble(k.toString()) / pow;

		xAxis.add(key);
		series.add(v);
	    });

	    HashMap<String, Object> restlutHashMap = Maps.newHashMap();

	    restlutHashMap.put("xAxis", xAxis);
	    restlutHashMap.put("series", series);

	    return restlutHashMap;

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    Logger.error("峰值查询异常", e);

	    HashMap<String, Object> restlutHashMap = Maps.newHashMap();
	    restlutHashMap.put("xAxis", Collections.emptyList());
	    restlutHashMap.put("series", Collections.emptyList());
	    return restlutHashMap;
	}

    }

    @GetMapping(path = "/monthCharts")
    public Object monthCharts(@RequestParam String beginTime, @RequestParam long centorFreq,
	    @RequestParam String stationCode) {
	HashMap<String, Object> map = new HashMap<>();
	try {

	    centorFreq = (long) (88.8 * 1000000);
	    OccAndMax reslutResponce = hbaseClient.queryOccDay("52010062", "20170717000000", 90, centorFreq);
	    Map<String, Object> occMap = reslutResponce.getOcc();
	    if (occMap.size() == 0) {
		HashMap<String, Object> restlutHashMap = Maps.newHashMap();
		String[] xAxis = new String[] {};
		double[] series = new double[] {};
		restlutHashMap.put("xAxis", xAxis);
		restlutHashMap.put("series", series);
		return restlutHashMap;
	    } else {
		LinkedList<Object> xAxis = Lists.newLinkedList();
		LinkedList<Object> series = Lists.newLinkedList();

		final double pow = Math.pow(10, 6);

		occMap.forEach((k, v) -> {

		    final double key = Double.parseDouble(k.toString()) / pow;

		    xAxis.add(key);
		    series.add(v);
		});

		HashMap<String, Object> restlutHashMap = Maps.newHashMap();

		restlutHashMap.put("xAxis", xAxis);
		restlutHashMap.put("series", series);
	    }

	    return map;
	} catch (Exception e) {
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

}
