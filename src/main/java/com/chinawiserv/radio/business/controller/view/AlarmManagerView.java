package com.chinawiserv.radio.business.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alarmmanage")
public class AlarmManagerView {
	
	@RequestMapping(path={"/",""})
	public String test() {
		return "alarmmanage/home";

	}
}
