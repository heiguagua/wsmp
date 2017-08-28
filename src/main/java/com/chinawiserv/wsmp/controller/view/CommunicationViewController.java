package com.chinawiserv.wsmp.controller.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinawiserv.wsmp.service.StationCountService;

@Controller
@RequestMapping("/communication")
public class CommunicationViewController {

	@Autowired
	StationCountService station;
	
	@Value("${sefon.webservice.queryToolservice}")
	String urlQueryTool;
	
	@PostConstruct
	public void init() {
		
	}

	@RequestMapping(path = { "/", ""
	})
	public String communication() {

//		station.getCurrentYearCount();
//
//		station.getLastYearCount();

		return "communication/communication_home";
	}
	@RequestMapping("/openLayTest")
	public String openLayTest(){
		return "openLayerTest";
	}
}
