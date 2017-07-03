package com.chinawiserv.wsmp.controller.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chinawiserv.wsmp.pojo.MonitoringStation;
import com.chinawiserv.wsmp.pojo.Signature;

@Controller
@RequestMapping("/signal")
public class SignalViewController {

    @RequestMapping(path = {"/", ""})
    public String home() {
        return "signal/signal_home";
    }

    @GetMapping(path = "/sigaldetail")
    public String signal_detail(Model model) {
        model.addAttribute("sigal", new Signature(1, "xxxx", 5.02, 90.0, 30));
        return "signal/signal_detail";
    }

    @GetMapping(path = {"/stationlist"})
	public ModelAndView stationList(ModelAndView modelAndView) {
		modelAndView.setViewName("signal/monitoring_station_list");
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
}
