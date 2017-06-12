package com.chinawiserv.wsmp.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alarmmanage")
public class AlarmManagerViewController {

	@RequestMapping(path = { "/", "" })
	public String test() {
		return "alarmmanage/alarmmanage_home";
	}
}
