package com.chinawiserv.wsmp.controller.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinawiserv.apps.logger.Logger;
import com.chinawiserv.wsmp.pojo.CommunicationTableButtom;
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
import org.tempuri.RadioSignalWebServiceSoap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
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
    
    @Value("${sefon.httpservice.getFreqBandListByOption}")
    private String urlFreqBandListByOption;
    
    @Value("${radioSignalWebService.wsdl}")
    private String urlRadioSignal;
    
    private static QueryToolsServicePortType queryToolsService;
    
    private static RadioSignalWebServiceSoap radioSignalServiceSoap;
    
    private static Map<String,String> techCodingTable;
    
    private static Map<String,String> operatorTable;
    
    private static ExecutorService eService;
	
    private RestTemplate restTemplate;
	@PostConstruct
	public void init() throws MalformedURLException {
		//初始化服务
		URL url1 = new URL(urlQueryTool);
		QueryToolsService service = new QueryToolsService(url1);
		queryToolsService = service.getQueryToolsServiceHttpSoap11Endpoint();
		URL url2 = new URL(urlRadioSignal);
		RadioSignalWebService radioSignalService = new RadioSignalWebService(url2 );
		radioSignalServiceSoap = radioSignalService.getRadioSignalWebServiceSoap();
		//技术制式编码表
		techCodingTable = Maps.newHashMap();
		techCodingTable.put("LY0101", "GSM/GPRS系统");//2g
		techCodingTable.put("LY0102", "CDMA系统");//3g
		techCodingTable.put("LY0103", "WCDMA系统");//3g
		techCodingTable.put("LY0104", "TD-SCDMA系统");//3g
		techCodingTable.put("LY0105", "TD-LTE系统");//4g
		techCodingTable.put("LY0106", "FDD-LTE系统");//4g
		//运营商编码表
		operatorTable = Maps.newHashMap();
		operatorTable.put("529", "电信");
		operatorTable.put("535", "移动");
		operatorTable.put("536", "联通");
		//初始化restTemplate
		restTemplate = new RestTemplate();
		//初始化线程池
		eService = Executors.newCachedThreadPool();
	}
	
//    @PostMapping("/topTable")
//    public Map<String, Object> getTopTable(@RequestBody Map<String,Object> param) {
//    	System.out.println("===param:"+param);
//    	@SuppressWarnings("unchecked")
//		List<String> monitorsID = (List<String>) param.get("monitorsID");
//    	HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		Map<String, Object> request = Maps.newHashMap();
//		request.put("queryStartTime", param.get("startTime"));
//		request.put("queryEndTime", param.get("endTime"));
//		request.put("queryScope", param.get("areaCode"));
//		request.put("userId", param.get("userID"));
//    	List<FreqSelfInfo> response = queryToolsService.querySelfFreqInfoByPID("1");
//    	long loopStartTime = System.currentTimeMillis();
//		List<CommunicationTableTop> communicationRows = response.parallelStream().map(m -> {
//			long taskBegin = System.currentTimeMillis();
//			CommunicationTableTop communication = new CommunicationTableTop();
//			communication.setGeneration(m.getServiceName());
//			communication.setOperator(m.getFreqDesc());
//			communication.setFreqRange(m.getFreqMin().toString() + '-' + m.getFreqMax().toString());
//			communication.setTechName(techCodingTable.get(m.getSt()));
//			communication.setInfoChannel(m.getFreqMax().subtract(m.getFreqMin()).multiply(new BigDecimal("1000")).divide(new BigDecimal(m.getChannelBandwidth())).toString());
//			//查询并设置频段占用度和台站覆盖率
//			request.put("freqMin", m.getFreqMin());
//			request.put("freqMax", m.getFreqMax());
//			HttpEntity<String> entity = new HttpEntity<String>(JSON.toJSONString(request), headers);
//			JSONObject result = restTemplate.postForObject(urlFreqBandList, entity, JSONObject.class);
//			String stationCoverageRate = result.getJSONObject("data").getJSONArray("result").getJSONObject(0).getString("stationCoverageRate");
//			String freqBandOccupyAngle = result.getJSONObject("data").getJSONArray("result").getJSONObject(0).getString("freqBandOccupyAngle");
//			freqBandOccupyAngle = freqBandOccupyAngle == null ? "0" : freqBandOccupyAngle;
//			stationCoverageRate = stationCoverageRate.equals("--") || stationCoverageRate == null || stationCoverageRate.equals("无") ? "0" : stationCoverageRate;
//			communication.setStationCoverage(stationCoverageRate);
//			communication.setOccupancy(freqBandOccupyAngle);
//			//查询并设置监测站
//			RadioSignalQueryRequest request2 = new RadioSignalQueryRequest();
//			request2.setBeginFreq(m.getFreqMin().multiply(new BigDecimal("1000000")).toBigInteger());
//			request2.setEndFreq(m.getFreqMax().multiply(new BigDecimal("1000000")).toBigInteger());
//			ArrayOfString value = new ArrayOfString();
//			value.setString(monitorsID);
//			request2.setStationIDs(value );
//			RadioSignalQueryResponse response2 = radioSignalServiceSoap.queryRadioSignal(request2 );
//			Map<String, List<RadioSignalStationDTO>> map = response2.getRadioSignals().getRadioSignalDTO().stream().flatMap(m2 -> m2.getStationDTOs().getRadioSignalStationDTO().stream())
//				.collect(Collectors.groupingBy(RadioSignalStationDTO :: getStationNumber));
//			Double monitorCoverage = (double) (map.entrySet().size() / monitorsID.size() * 100);
//			communication.setMonitorCoverage(monitorCoverage.toString() + "%");
//			long taskEnd = System.currentTimeMillis();
//			System.out.println("task end : " + (taskEnd - taskBegin));
//			return communication;
//		}).collect(Collectors.toList());
//		long loopEndTime = System.currentTimeMillis();
//		System.out.println("loop end : "+(loopEndTime -loopStartTime)/1000);
//		Map<String, Object> result = Maps.newLinkedHashMap();
//		result.put("total", communicationRows.size());
//		result.put("data", communicationRows);
//		return result;
//    }
    
    
    @PostMapping("/topTable")
    public Map<String, Object> getTopTable(@RequestBody Map<String,Object> param) {
    	System.out.println("===param:"+param);
    	@SuppressWarnings("unchecked")
    	List<String> monitorsID = (List<String>) param.get("monitorsID");
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	Map<String, Object> request = Maps.newConcurrentMap();
    	request.put("queryStartTime", param.get("startTime"));
    	request.put("queryEndTime", param.get("endTime"));
    	request.put("queryScope", param.get("areaCode"));
    	request.put("userId", param.get("userID"));
    	List<FreqSelfInfo> response = queryToolsService.querySelfFreqInfoByPID("1");
    	List<CommunicationTableTop> communicationRows = Lists.newArrayList();
    	
    	List<Future<CommunicationTableTop>> futureList = Lists.newArrayList();
    	
    	long loopStartTime = System.currentTimeMillis();
    	response.forEach(m -> {
    		Callable<CommunicationTableTop> task = new Callable<CommunicationTableTop>() {
				public CommunicationTableTop call() throws Exception {
					long taskBegin = System.currentTimeMillis();
					Logger.info("任务{}开始", Thread.currentThread().getId());
		    		CommunicationTableTop communication = new CommunicationTableTop();
		    		communication.setGeneration(m.getServiceName());
		    		communication.setOperator(Optional.ofNullable(m.getFreqDesc()).orElse(""));
		    		communication.setFreqRange(m.getFreqMin().toString() + '-' + m.getFreqMax().toString());
		    		communication.setTechName(techCodingTable.get(m.getSt()));
		    		communication.setInfoChannel(m.getFreqMax().subtract(m.getFreqMin()).multiply(new BigDecimal("1000000")).divide(new BigDecimal(m.getChannelBandwidth())).toString());
		    		//查询并设置频段占用度和台站覆盖率
		    		request.put("freqMin", m.getFreqMin());
		    		request.put("freqMax", m.getFreqMax());
		    		HttpEntity<String> entity = new HttpEntity<String>(JSON.toJSONString(request), headers);
		    		JSONObject result = restTemplate.postForObject(urlFreqBandList, entity, JSONObject.class);
		    		String stationCoverageRate = result.getJSONObject("data").getJSONArray("result").getJSONObject(0).getString("stationCoverageRate");
		    		String freqBandOccupyAngle = result.getJSONObject("data").getJSONArray("result").getJSONObject(0).getString("freqBandOccupyAngle");
		    		freqBandOccupyAngle = freqBandOccupyAngle.equals("无") || freqBandOccupyAngle == null ? "0" : freqBandOccupyAngle;
		    		stationCoverageRate = stationCoverageRate.equals("--") || stationCoverageRate == null || stationCoverageRate.equals("无") ? "0" : stationCoverageRate;
		    		communication.setStationCoverage(stationCoverageRate);
		    		communication.setOccupancy(freqBandOccupyAngle);
		    		//查询并设置监测站
		    		RadioSignalQueryRequest request2 = new RadioSignalQueryRequest();
		    		request2.setBeginFreq(m.getFreqMin().multiply(new BigDecimal("1000000")).toBigInteger());
		    		request2.setEndFreq(m.getFreqMax().multiply(new BigDecimal("1000000")).toBigInteger());
		    		ArrayOfString value = new ArrayOfString();
		    		value.setString(monitorsID);
		    		request2.setStationIDs(value );
		    		RadioSignalQueryResponse response2 = radioSignalServiceSoap.queryRadioSignal(request2 );
		    		Map<String, List<RadioSignalStationDTO>> map = response2.getRadioSignals().getRadioSignalDTO().stream().flatMap(m2 -> m2.getStationDTOs().getRadioSignalStationDTO().stream())
		    				.collect(Collectors.groupingBy(RadioSignalStationDTO :: getStationNumber));
		    		Double monitorCoverage = (double) (map.entrySet().size() / monitorsID.size() * 100);
		    		communication.setMonitorCoverage(monitorCoverage.toString() + "%");
		    		long taskEnd = System.currentTimeMillis();
		    		Logger.info("任务{}结束,执行时间：{}",Thread.currentThread().getId(), (taskEnd - taskBegin));
					return communication;
				}
			};
			Future<CommunicationTableTop> future = eService.submit(task);
			futureList.add(future);
    	});
    	futureList.forEach(t -> {
    		try {
    			CommunicationTableTop top = t.get();
				communicationRows.add(top);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    	long loopEndTime = System.currentTimeMillis();
    	System.out.println("loop end : "+(loopEndTime -loopStartTime));
    	Map<String, Object> result = Maps.newLinkedHashMap();
    	result.put("total", communicationRows.size());
    	result.put("data", communicationRows);
    	return result;
    }
    
//    @PostMapping("/topTable1")
//    public Map<String, Object> getTopTable1(@RequestBody Map<String,Object> param) {
//    	System.out.println("===param:"+param);
//    	//得到频段并设置一部分返回值
//    	List<FreqSelfInfo> response = queryToolsService.querySelfFreqInfoByPID("1");
//    	List<Map<String,BigDecimal>> freqBandList = Lists.newArrayList();
//    	List<CommunicationTableTop> communicationRows = response.stream().map(m -> {
//    		CommunicationTableTop communication = new CommunicationTableTop();
//			communication.setGeneration(m.getServiceName());
//			communication.setOperator(m.getFreqDesc());
//			communication.setFreqRange(m.getFreqMin().toString() + '-' + m.getFreqMax().toString());
//			communication.setTechName(techCodingTable.get(m.getSt()));
//			communication.setInfoChannel(m.getFreqMax().subtract(m.getFreqMin()).multiply(new BigDecimal("1000")).divide(new BigDecimal(m.getChannelBandwidth())).toString());
//			Map<String,BigDecimal> freqBand = Maps.newHashMap();
//			freqBand.put("freqMin", m.getFreqMin());
//			freqBand.put("freqMax", m.getFreqMax());
//			freqBandList.add(freqBand);
//			return communication;
//    	}).collect(Collectors.toList());
//    	
//    	//根据频段查询台站覆盖率和频段覆盖率
//    	@SuppressWarnings("unchecked")
//    	List<String> monitorsID = (List<String>) param.get("monitorsID");
//    	HttpHeaders headers = new HttpHeaders();
//    	headers.setContentType(MediaType.APPLICATION_JSON);
//    	Map<String, Object> request = Maps.newHashMap();
//    	request.put("queryStartTime", param.get("startTime"));
//    	request.put("queryEndTime", param.get("endTime"));
//    	request.put("queryScope", param.get("areaCode"));
//    	request.put("userId", param.get("userID"));
//		request.put("condition", freqBandList);
//    	System.out.println("request:" + JSON.toJSONString(request));
//		HttpEntity<String> entity = new HttpEntity<String>(JSON.toJSONString(request), headers);
//		long loopStartTime = System.currentTimeMillis();
//		JSONObject result = restTemplate.postForObject(urlFreqBandListByOption, entity, JSONObject.class);
//		long loopEndTime = System.currentTimeMillis();
//		System.out.println("loop end : "+(loopEndTime -loopStartTime)/1000);
//		System.out.println(result);
//    	return null;
//    }

    @RequestMapping("/bottomTable")
    public Map<String, Object> bottomtable(){
        //List<CountResult> current = service.getCurrentYearCount();//2017年没有数据，暂时用去年的
        List<CountResult> last = service.getLastYearCount();
        
        List<CommunicationTableButtom> resultList = last.stream().collect(Collectors.groupingBy(CountResult :: getOrgSystemCode)).entrySet().stream().map(m -> {
        	CommunicationTableButtom row = new CommunicationTableButtom();
        	row.setStation_type(operatorTable.get(m.getKey()));
        	row.setG2(m.getValue().stream().filter(f -> f.getNetTs().equals("LY0101")).map(m1 -> m1.getNum()).reduce((a,b) -> a + b).orElse("0"));
        	row.setG3(m.getValue().stream().filter(f -> f.getNetTs().equals("LY0102")||f.getNetTs().equals("LY0103")||f.getNetTs().equals("LY0104")).map(m1 -> m1.getNum()).reduce((a,b) -> a + b).orElse("0"));
        	row.setG4(m.getValue().stream().filter(f -> f.getNetTs().equals("LY0105")||f.getNetTs().equals("LY0106")).map(m1 -> m1.getNum()).reduce((a,b) -> a + b).orElse("0"));
        	Integer total = Integer.valueOf(row.getG2())+Integer.valueOf(row.getG3())+Integer.valueOf(row.getG4());
        	row.setStation_total(total.toString());
        	return row;
        }).collect(Collectors.toList());

        Map<String, Object> result = Maps.newLinkedHashMap();
		result.put("data", resultList);
		return result;
    }
}
