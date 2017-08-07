package com.chinawiserv.wsmp.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinawiserv.wsmp.service.StationCountService;

@Controller
@RequestMapping("/communication")
public class CommunicationViewController {

    @Autowired
    StationCountService station;

    @RequestMapping(path = { "/", "" })
    public String communication() {
	station.getCount();
	return "communication/communication_home";
    }
}
