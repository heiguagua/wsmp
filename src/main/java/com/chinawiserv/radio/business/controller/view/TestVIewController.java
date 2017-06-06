package com.chinawiserv.radio.business.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/testview")
public class TestVIewController {
	
	@RequestMapping(path ="/test")
	public String test() {
		return "test";

	}
}
