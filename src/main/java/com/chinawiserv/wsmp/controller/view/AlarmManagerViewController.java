package com.chinawiserv.wsmp.controller.view;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/alarmmanage")
public class AlarmManagerViewController {

	@RequestMapping(path = { "" })
	public String test() {
		return "alarmmanage/alarmmanage_home";
	}

	@RequestMapping(path = "/monthCharts", method = RequestMethod.GET)
	public String monthCharts(@RequestParam Map<String, Object> params) {

		return "alarmmanage/month_chart";
	}

	@GetMapping(path = "/hourCharts")
	public String hourCharts(@RequestParam Map<String, Object> params) {
		return "alarmmanage/hour_chart";
	}

	@GetMapping(path = "/dayCharts")
	public String dayCharts(@RequestParam Map<String, Object> params) {
		return "alarmmanage/day_chart";
	}
}