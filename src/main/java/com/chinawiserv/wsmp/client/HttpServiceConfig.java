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
	
	public static String httpclient(double[][] dataIn,String url, Boolean isAdut) {
		 RestTemplate restTemplate = new RestTemplate();
         HttpHeaders headers = new HttpHeaders();
         MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
         headers.setContentType(type);
         headers.add("Accept", MediaType.APPLICATION_JSON.toString());
          
         JSONObject jsonObj = new JSONObject();
         jsonObj.put("data", dataIn);
         jsonObj.put("stepx", 0.0138);
         jsonObj.put("stepy", 0.0305);
         jsonObj.put("scale", 0.25);
         jsonObj.put("radis", 195);
         if(isAdut){
             jsonObj.put("stepx", 0.03);
             jsonObj.put("stepy", 0.03);
             jsonObj.put("scale", 0.35);
             jsonObj.put("radis", 120);
         }

         HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
 
         String result = restTemplate.postForObject(url, formEntity, String.class);
         return result;
	}
	
	
}
