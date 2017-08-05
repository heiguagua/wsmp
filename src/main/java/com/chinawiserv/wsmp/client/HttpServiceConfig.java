package com.chinawiserv.wsmp.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpServiceConfig {

    @Bean
    public Object FreqBandList() {
	final RestTemplate restTemplate = new RestTemplate();
	final HttpHeaders headers = new HttpHeaders();
	final MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
	headers.setContentType(mediaType);
	headers.add("Accept", MediaType.APPLICATION_JSON.toString());
	return null;
    }

}
