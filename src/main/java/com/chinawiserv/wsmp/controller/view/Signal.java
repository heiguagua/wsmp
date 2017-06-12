package com.chinawiserv.wsmp.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("signal")
public class Signal {

	@RequestMapping(path = { "/", "" })
	public String home() {
		return "signal/home";
	}
}
