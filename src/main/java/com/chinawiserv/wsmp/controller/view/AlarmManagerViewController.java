package com.chinawiserv.wsmp.controller.view;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinawiserv.wsmp.cache.CheckBoxFactory;
import com.chinawiserv.wsmp.pojo.RedioType;

@Controller
@RequestMapping("/alarmmanage")
public class AlarmManagerViewController {

	@Autowired
	CheckBoxFactory checkBoxFactory;

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

	@RequestMapping(path = { "" })
	public ModelAndView test(ModelAndView modelAndView) throws IOException {
		RedioType redioType = checkBoxFactory.getRedioType();
		modelAndView.setViewName("alarmmanage/alarmmanage_home");
		modelAndView.addObject("redioType", redioType);
		return modelAndView;
	}
}
