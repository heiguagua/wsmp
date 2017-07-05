package com.chinawiserv.wsmp.controller.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinawiserv.wsmp.pojo.MonitoringStation;
import com.chinawiserv.wsmp.pojo.RedioDetail;
import com.chinawiserv.wsmp.pojo.Signature;

@Controller
@RequestMapping("/signal")
public class SignalViewController {

    @RequestMapping(path = {"/", ""})
    public String home() {
        return "signal/signal_home";
    }

	@PostMapping(path = "/sigaldetail")
	public String signal_detail(Model model, @RequestParam Map<String, Object> param) {

		System.out.println(param);
        model.addAttribute("sigal", new Signature(1, "xxxx", 5.02, 90.0, 30));

        RedioDetail redioDetail = new RedioDetail();
		redioDetail.setBand(123.4);
		redioDetail.setCentor(100);
		redioDetail.setType("1");
		model.addAttribute("redioDetail", redioDetail);
        return "signal/signal_detail";
    }

    @GetMapping(path = {"/stationlist"})
	public ModelAndView stationList(ModelAndView modelAndView) {

		modelAndView.setViewName("signal/signal_list");
		List<MonitoringStation> stations = new ArrayList<MonitoringStation>();
		for (int i = 0; i < 4; i++) {
			MonitoringStation station = new MonitoringStation();
			station.setStationCode("dddd");
			station.setStationName("信号1");
			stations.add(station);
		}
		modelAndView.addObject("stations", stations);
		return modelAndView;
    }
}
