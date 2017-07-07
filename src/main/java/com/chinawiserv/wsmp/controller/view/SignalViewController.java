package com.chinawiserv.wsmp.controller.view;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinawiserv.wsmp.pojo.RedioDetail;
import com.chinawiserv.wsmp.pojo.Signature;
import com.chinawiserv.wsmp.pojo.Singal;
import com.google.common.collect.Lists;

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

	@PostMapping(path = "/singallist")
	public ModelAndView stationList(ModelAndView modelAndView, @RequestParam Map<String, Object> param) {
		modelAndView.setViewName("signal/signal_list");
		List<Singal> stations = Lists.newArrayList();
		for (int i = 0; i < 4; i++) {
			Singal station = new Singal();
			station.setContext("信号一 2017.6.7");
			station.setId("dasww");
			stations.add(station);
		}
		modelAndView.addObject("stations", stations);
		return modelAndView;
    }
}
