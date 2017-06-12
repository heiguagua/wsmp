package com.chinawiserv.wsmp.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("communication")
public class Communication {

	@RequestMapping(path = { "/", "" })
	public String communication() {
		return "communication/communication_home";
	}
}
