package com.chinawiserv.wsmp.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import javax.annotation.PostConstruct;

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

    private static double stepx;

    private static double stepy;

    private static double scale;

    private static double radis;
//    @PostConstruct
//    public void init(){
//        stepx = getStepx();
//        stepy=getStepy();
//        scale=getScale();
//        radis=getRadis();
//    }
	public static String httpclient(double[][] dataIn,String url, Boolean isAdut) {
		 RestTemplate restTemplate = new RestTemplate();
         HttpHeaders headers = new HttpHeaders();
         MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
         headers.setContentType(type);
         headers.add("Accept", MediaType.APPLICATION_JSON.toString());
          
         JSONObject jsonObj = new JSONObject();
         jsonObj.put("data", dataIn);
         jsonObj.put("stepx",stepx );
         jsonObj.put("stepy",stepy);
         jsonObj.put("scale",scale);
         jsonObj.put("radis", radis);
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

//    public static String httpclient(double[][] dataIn,String url, Boolean isAdut,int radis) {

//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//
//        JSONObject jsonObj = new JSONObject();
//        jsonObj.put("data", dataIn);
//        jsonObj.put("stepx",stepx );
//        jsonObj.put("stepy",stepy);
//        jsonObj.put("radis", radis);
//        jsonObj.put("scale",scale);
//
//
//        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
//
//        String result = restTemplate.postForObject(url, formEntity, String.class);
//        return result;
//    }

    public  double getStepx() {
        return stepx;
    }
    @Value("${kring.stepx}")
    public  void setStepx(double stepx) {
        HttpServiceConfig.stepx = stepx;
    }

    public  double getStepy() {
        return stepy;
    }
    @Value("${kring.stepy}")
    public  void setStepy(double stepy) {
        HttpServiceConfig.stepy = stepy;
    }

    public  double getScale() {
        return scale;
    }
    @Value("${kring.scale}")
    public  void setScale(double scale) {
        HttpServiceConfig.scale = scale;
    }

    public  double getRadis() {
        return radis;
    }
    @Value("${kring.radis}")
    public  void setRadis(double radis) {
        HttpServiceConfig.radis = radis;
    }
}
