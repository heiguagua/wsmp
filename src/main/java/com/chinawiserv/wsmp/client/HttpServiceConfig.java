package com.chinawiserv.wsmp.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

@Configuration
public class HttpServiceConfig {

	// @Bean
	// public Object FreqBandList() {
	// final RestTemplate restTemplate = new RestTemplate();
	// final HttpHeaders headers = new HttpHeaders();
	// final MediaType mediaType = MediaType.parseMediaType("application/json;
	// charset=UTF-8");
	// headers.setContentType(mediaType);
	// headers.add("Accept", MediaType.APPLICATION_JSON.toString());
	// return null;
	// }
	
	public static String httpclient(double[][] dataIn,String url ) {
		 RestTemplate restTemplate = new RestTemplate();
         HttpHeaders headers = new HttpHeaders();
         MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
         headers.setContentType(type);
         headers.add("Accept", MediaType.APPLICATION_JSON.toString());
          
         JSONObject jsonObj = new JSONObject();
         jsonObj.put("data", dataIn);
          
         HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
 
         String result = restTemplate.postForObject(url, formEntity, String.class);
         return result;
	}
	
	
}
