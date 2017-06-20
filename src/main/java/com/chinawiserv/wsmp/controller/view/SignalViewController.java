package com.chinawiserv.wsmp.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinawiserv.wsmp.pojo.Signature;

@Controller
@RequestMapping("signal")
public class SignalViewController {

	@RequestMapping(path = { "/", "" })
	public String home() {
		return "signal/signal_home";
	}

	@GetMapping(path = { "/stationlist" })
	public String stationList() {
		return "signal/monitoring_station_list";

	}

	@GetMapping(path = "/sigaldetail")
	public String signal_detail(Model model) {
		model.addAttribute("sigal", new Signature(1, "xxxx", 5.02, 90.0, 30));
		return "signal/signal_detail";
	}
}
