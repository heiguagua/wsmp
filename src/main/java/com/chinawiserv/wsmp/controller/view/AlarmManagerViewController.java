package com.chinawiserv.wsmp.controller.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinawiserv.wsmp.pojo.MonitoringStation;
import com.chinawiserv.wsmp.pojo.RedioType;
import com.chinawiserv.wsmp.pojo.Singal;
import com.google.common.collect.Lists;

@Controller
@RequestMapping("/alarmmanage")
public class AlarmManagerViewController {

	@Autowired
	private RedioType redioType;

	@GetMapping(path = "/dayCharts")
	public String dayCharts(@RequestParam Map<String, Object> params) {
		return "alarmmanage/day_chart";
	}

	@GetMapping(path = "/hourCharts")
	public String hourCharts(@RequestParam Map<String, Object> params) {
		return "alarmmanage/hour_chart";
	}

	@GetMapping(path = "/monthCharts")
	public String monthCharts(@RequestParam Map<String, Object> params) {

		return "alarmmanage/month_chart";
	}

	@PostMapping(path = { "/stationlist" })
	public ModelAndView stationList(ModelAndView modelAndView, @RequestParam Map<String, Object> param) {
		modelAndView.setViewName("alarmmanage/monitoring_station_list");
		List<MonitoringStation> stations = new ArrayList<MonitoringStation>();
		for (int i = 0; i < 4; i++) {
			MonitoringStation station = new MonitoringStation();
			station.setStationCode("dddd");
			station.setStationName("dddd站2");
			stations.add(station);
		}
		modelAndView.addObject("stations", stations);
		return modelAndView;
	}

	@PostMapping(path = { "/singal" })
	public String singalList(Model model, @RequestParam Map<String, Object> para) {
		Singal singal = new Singal();
		singal.setId("00155");
		singal.setContext("信号一 2017.7.6");
		List<Singal> reslutList = Lists.newLinkedList();
		reslutList.add(singal);
		model.addAttribute("singal", reslutList);
		return "signal/signal_list";
	}

	@RequestMapping(path = { "" })
	public ModelAndView test(ModelAndView modelAndView) throws IOException {
		modelAndView.setViewName("alarmmanage/alarmmanage_home");
		modelAndView.addObject("redioType", redioType);
		return modelAndView;
	}
}
