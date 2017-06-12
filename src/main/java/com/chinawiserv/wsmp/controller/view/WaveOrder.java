package com.chinawiserv.wsmp.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("waveorder")
public class WaveOrder {
	
	@RequestMapping(path = {"/",""})
	public String home() {
		return "waveorder/waveorder_home";
	}
}
