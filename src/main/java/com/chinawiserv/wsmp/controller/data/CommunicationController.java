package com.chinawiserv.wsmp.controller.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinawiserv.apps.logger.Logger;
import com.chinawiserv.wsmp.pojo.CommunicationTableTop;
import com.chinawiserv.wsmp.pojo.CountResult;
import com.chinawiserv.wsmp.service.StationCountService;
import com.google.common.collect.Maps;
import com.sefon.ws.model.freq.xsd.FreqSelfInfo;
import com.sefon.ws.service.impl.QueryToolsService;
import com.sefon.ws.service.impl.QueryToolsServicePortType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

@RestControllerAdvice
@RequestMapping("data/communication")
public class CommunicationController {

    @Autowired
    private StationCountService service;

    @Value("${sefon.webservice.queryToolservice}")
	private String urlQueryTool;
    
    @Value("${sefon.httpservice.getFreqBandList}")
    private String urlFreqBandList;
    
    private QueryToolsServicePortType queryToolsService;
    
    private Map<String,String> techCodingTable;
	
    private RestTemplate restTemplate;
	@PostConstruct
	public void init() throws MalformedURLException {
		//初始化服务
		URL url1 = new URL(urlQueryTool);
		QueryToolsService service = new QueryToolsService(url1);
		queryToolsService = service.getQueryToolsServiceHttpSoap11Endpoint();
		//技术制式编码表
		techCodingTable = Maps.newHashMap();
		techCodingTable.put("LY0101", "GSM/GPRS系统");
		techCodingTable.put("LY0102", "CDMA系统");
		techCodingTable.put("LY0103", "WCDMA系统");
		techCodingTable.put("LY0104", "TD-SCDMA系统");
		//初始化restTemplate
		restTemplate = new RestTemplate();
	}
    @PostMapping("/topTable")
    public Map<String, Object> getTopTable(@RequestBody Map<String,Object> param) {
    	System.out.println("===param:"+param);
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> request = Maps.newHashMap();
		request.put("queryStartTime", param.get("startTime"));
		request.put("queryEndTime", param.get("endTime"));
		request.put("queryScope", param.get("areaCode"));
    	List<FreqSelfInfo> response = queryToolsService.querySelfFreqInfoByPID("1");
    	long loopStartTime = System.currentTimeMillis();
		List<CommunicationTableTop> communicationRows = response.parallelStream().map(m -> {
			
			System.out.println(JSON.toJSONString(m));
			CommunicationTableTop communication = new CommunicationTableTop();
			communication.setGeneration(m.getServiceName());
			communication.setOperator("电信");
			communication.setFreqRange(m.getFreqMin().toString() + '-' + m.getFreqMax());
			communication.setTechName(techCodingTable.get(m.getSt()));
			communication.setInfoChannel(m.getFreqMax().subtract(m.getFreqMin()).multiply(new BigDecimal("1000")).divide(new BigDecimal(m.getChannelBandwidth())).toString());
			//
			request.put("freqMin", m.getFreqMin());
			request.put("freqMax", m.getFreqMax());
			HttpEntity<String> entity = new HttpEntity<String>(JSON.toJSONString(request), headers);
			long startTime = System.currentTimeMillis();
			JSONObject result = restTemplate.postForObject(urlFreqBandList, entity, JSONObject.class);
			long end = System.currentTimeMillis();
			System.out.println("result:"+result.toJSONString());
			String stationCoverageRate = result.getJSONObject("data").getJSONArray("result").getJSONObject(0).getString("stationCoverageRate");
			String freqBandOccupyAngle = result.getJSONObject("data").getJSONArray("result").getJSONObject(0).getString("freqBandOccupyAngle");
			System.out.println("ss:"+stationCoverageRate);
			communication.setMonitorCoverage(null);
			communication.setStationCoverage(stationCoverageRate);
			communication.setOccupancy(freqBandOccupyAngle);
			System.out.println("request end :"+(end-startTime)/1000);
			return communication;
		}).collect(Collectors.toList());
		long loopEndTime = System.currentTimeMillis();
		System.out.println("loop end : "+(loopEndTime -loopStartTime)/1000);
		Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("total", communicationRows.size());
		result.put("data", communicationRows);
		return result;
    }

    @RequestMapping("/bottomTable")
    public Object bottomtable() {
        List<CountResult> current = service.getCurrentYearCount();
        List<CountResult> last = service.getLastYearCount();

        Map<String, CountResult> currentMap = current.stream().collect(toMap(k->k.getNetTs()+"_"+k.getOrgSystemCode(),v->v));
        Map<String, CountResult> lastMap = last.stream().collect(toMap(k->k.getNetTs()+"_"+k.getOrgSystemCode(),v->v));

        return null;
    }
}
