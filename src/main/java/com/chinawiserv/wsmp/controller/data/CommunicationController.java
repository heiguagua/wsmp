package com.chinawiserv.wsmp.controller.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinawiserv.apps.logger.Logger;
import com.chinawiserv.wsmp.pojo.CommunicationTableTop;
import com.chinawiserv.wsmp.pojo.CountResult;
import com.chinawiserv.wsmp.service.StationCountService;
import com.google.common.collect.Lists;
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
import org.tempuri.ArrayOfString;
import org.tempuri.RadioSignalQueryRequest;
import org.tempuri.RadioSignalQueryResponse;
import org.tempuri.RadioSignalStationDTO;
import org.tempuri.RadioSignalWebService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    
    @Value("${radioSignalWebService.wsdl}")
    private String urlRadioSignal;
    
    private QueryToolsServicePortType queryToolsService;
    
    private RadioSignalWebService radioSignalService;
    
    private Map<String,String> techCodingTable;
	
    private RestTemplate restTemplate;
	@PostConstruct
	public void init() throws MalformedURLException {
		//初始化服务
		URL url1 = new URL(urlQueryTool);
		QueryToolsService service = new QueryToolsService(url1);
		queryToolsService = service.getQueryToolsServiceHttpSoap11Endpoint();
		URL url2 = new URL(urlRadioSignal);
		radioSignalService = new RadioSignalWebService(url2 );
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
    	@SuppressWarnings("unchecked")
		List<String> monitorsID = (List<String>) param.get("monitorsID");
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> request = Maps.newHashMap();
		request.put("queryStartTime", param.get("startTime"));
		request.put("queryEndTime", param.get("endTime"));
		request.put("queryScope", param.get("areaCode"));
		request.put("userId", param.get("userID"));
    	List<FreqSelfInfo> response = queryToolsService.querySelfFreqInfoByPID("1");
//    	long loopStartTime = System.currentTimeMillis();
		List<CommunicationTableTop> communicationRows = response.stream().map(m -> {
			CommunicationTableTop communication = new CommunicationTableTop();
			communication.setGeneration(m.getServiceName());
			communication.setOperator(m.getFreqDesc());
			communication.setFreqRange(m.getFreqMin().toString() + '-' + m.getFreqMax().toString());
			communication.setTechName(techCodingTable.get(m.getSt()));
			communication.setInfoChannel(m.getFreqMax().subtract(m.getFreqMin()).multiply(new BigDecimal("1000")).divide(new BigDecimal(m.getChannelBandwidth())).toString());
			//查询并设置频段占用度和台站覆盖率
			request.put("freqMin", m.getFreqMin());
			request.put("freqMax", m.getFreqMax());
			System.out.println("request:" + JSON.toJSONString(request));
			HttpEntity<String> entity = new HttpEntity<String>(JSON.toJSONString(request), headers);
			JSONObject result = restTemplate.postForObject(urlFreqBandList, entity, JSONObject.class);
			System.out.println("result:"+result.toJSONString());
			String stationCoverageRate = result.getJSONObject("data").getJSONArray("result").getJSONObject(0).getString("stationCoverageRate");
			System.out.println(stationCoverageRate);
			String freqBandOccupyAngle = result.getJSONObject("data").getJSONArray("result").getJSONObject(0).getString("freqBandOccupyAngle");
			freqBandOccupyAngle = freqBandOccupyAngle == null ? "0.0%" : freqBandOccupyAngle + '%';
			stationCoverageRate = stationCoverageRate.equals("--") ? "0.0%" : stationCoverageRate + '%';
			communication.setStationCoverage(stationCoverageRate);
			communication.setOccupancy(freqBandOccupyAngle);
			//查询并设置监测站
			RadioSignalQueryRequest request2 = new RadioSignalQueryRequest();
			request2.setBeginFreq(m.getFreqMin().multiply(new BigDecimal("1000000")).toBigInteger());
			request2.setEndFreq(m.getFreqMax().multiply(new BigDecimal("1000000")).toBigInteger());
			ArrayOfString value = new ArrayOfString();
			value.setString(monitorsID);
			request2.setStationIDs(value );
			RadioSignalQueryResponse response2 = radioSignalService.getRadioSignalWebServiceSoap().queryRadioSignal(request2 );
			Map<String, List<RadioSignalStationDTO>> map = response2.getRadioSignals().getRadioSignalDTO().stream().flatMap(m2 -> m2.getStationDTOs().getRadioSignalStationDTO().stream())
				.collect(Collectors.groupingBy(RadioSignalStationDTO :: getStationNumber));
			Double monitorCoverage = (double) (map.entrySet().size() / monitorsID.size() * 100);
			communication.setMonitorCoverage(monitorCoverage.toString() + "%");
			return communication;
		}).collect(Collectors.toList());
//		long loopEndTime = System.currentTimeMillis();
//		System.out.println("loop end : "+(loopEndTime -loopStartTime)/1000);
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
