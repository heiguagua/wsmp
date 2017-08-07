
package com.chinawiserv.wsmp.client;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sefon.ws.service.impl.QueryToolsService;
import com.sefon.ws.service.impl.QueryToolsServicePortType;
import com.sefon.ws.service.impl.StationService;

@Configuration
public class WebServiceSeFonConfig {

	@Value("${sefon.webservice.stationservice}")
	private String url;

	@Value("${sefon.webservice.queryToolservice}")
	private String queryToolsUrl;

	@Bean
	public StationService initStationService() throws MalformedURLException {
		URL wsdlLoction = new URL(url);
		StationService stationService = new StationService(wsdlLoction);
		return stationService;

	}

	// @Bean
	public QueryToolsServicePortType initQueryToolsServicePortType() throws MalformedURLException {
		URL wsdlLocation = new URL(queryToolsUrl);
		QueryToolsService service = new QueryToolsService(wsdlLocation);
		QueryToolsServicePortType quertToolservice = service.getQueryToolsServiceHttpSoap11Endpoint();
		return quertToolservice;

	}

}

