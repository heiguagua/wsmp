package com.chinawiserv.wsmp.controller.data;

import com.alibaba.fastjson.JSON;
import com.chinawiserv.wsmp.pojo.CommunicationTableTop;
import com.chinawiserv.wsmp.pojo.CountResult;
import com.chinawiserv.wsmp.service.StationCountService;
import com.google.common.collect.Maps;
import com.sefon.ws.model.freq.xsd.FreqSelfInfo;
import com.sefon.ws.service.impl.QueryToolsService;
import com.sefon.ws.service.impl.QueryToolsServicePortType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    
    private QueryToolsServicePortType queryToolsService;
	
	@PostConstruct
	public void init() throws MalformedURLException {
		//初始化服务
		URL url1 = new URL(urlQueryTool);
		QueryToolsService service = new QueryToolsService(url1);
		queryToolsService = service.getQueryToolsServiceHttpSoap11Endpoint();
		
	}
    @PostMapping("/topTable")
    public Map<String, Object> getTopTable(@RequestBody Map<String,Object> param) {
    	System.out.println("===param:"+param);
    	List<FreqSelfInfo> response = queryToolsService.querySelfFreqInfoByPID("1");
		List<CommunicationTableTop> communicationRows = response.stream().map(m -> {
			System.out.println(JSON.toJSONString(m));
			CommunicationTableTop communication = new CommunicationTableTop();
			communication.setGeneration(m.getServiceName());
			communication.setOperator("电信");
			communication.setFreqRange(m.getFreqMin().toString() + '-' + m.getFreqMax());
			communication.setTechName(m.getSt());
			communication.setInfoChannel(Double.valueOf(m.getFreqMax().subtract(m.getFreqMin()).multiply(new BigDecimal("1000")).divide(new BigDecimal(m.getChannelBandwidth())).toString()));
			communication.setMonitorCoverage(null);
			communication.setStationCoverage(null);
			communication.setOccupancy(null);
			return communication;
		}).collect(Collectors.toList());
		
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
