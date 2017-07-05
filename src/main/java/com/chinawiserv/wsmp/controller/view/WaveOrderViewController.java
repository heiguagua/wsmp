package com.chinawiserv.wsmp.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String home(Model model) {
		RedioStatusCount redio = new RedioStatusCount();
		redio.setIllegalSignal(10);
		redio.setKonwStationNumber(10);
		redio.setLegalNormalStationNumber(10);
		redio.setLegalUnNormalStationNumber(10);
		redio.setUnKonw(10);
		model.addAttribute("redio", redio);
        return "waveorder/waveorder_home";
    }
}
