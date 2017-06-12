package com.chinawiserv.wsmp.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("signal")
public class SignalViewController {

	@RequestMapping(path = { "/", "" })
	public String home() {
		return "signal/signal_home";
	}
}
