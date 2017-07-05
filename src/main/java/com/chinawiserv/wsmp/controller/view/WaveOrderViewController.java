package com.chinawiserv.wsmp.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinawiserv.wsmp.pojo.RedioStatusCount;


@Controller
@RequestMapping("/waveorder")
public class WaveOrderViewController {

    @GetMapping("/ssss")
    public String alarmDealed() {
        return null;
    }

    @GetMapping("/frequencyrange")
    public String frequencyRange() {
        return "waveorder/table_radio";
    }

    @GetMapping(path = {"/", ""})
    public String home() {
		RedioStatusCount redio = new RedioStatusCount();
        return "waveorder/waveorder_home";
    }
}
