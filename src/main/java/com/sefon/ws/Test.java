package com.sefon.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sefon.ws.model.freq.xsd.FreqSelfInfo;
import com.sefon.ws.service.impl.QueryToolsService;

public class Test {
	public static void main(String[] args) throws JsonProcessingException {
		QueryToolsService service = new QueryToolsService();
		FreqSelfInfo reslut = service.getQueryToolsServiceHttpSoap11Endpoint().querySelfFreqInfoByID("D25");
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(reslut));
	}
}
