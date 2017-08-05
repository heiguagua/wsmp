package com.chinawiserv.wsmp.controller.view;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinawiserv.wsmp.pojo.Area;
import com.chinawiserv.wsmp.pojo.RedioStatusCount;
import com.google.common.collect.Lists;


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
	public String home(Model model, @RequestParam Map<String, Object> map) {
		Area area = new Area();
		area.setAreaCode("510010");
		area.setName("成都");
		List<Area> areas = Lists.newLinkedList();
		areas.add(area);
		model.addAttribute("area", areas);
        return "waveorder/waveorder_home";
    }

	@PostMapping("/redioType")
	public String redioType(Model model, @RequestParam Map<String, Object> map) {
		System.out.println(map);
    	RedioStatusCount redio = new RedioStatusCount();
		redio.setIllegalSignal(10);
		redio.setKonwStationNumber(10);
		redio.setLegalNormalStationNumber(10);
		redio.setLegalUnNormalStationNumber(10);
		redio.setUnKonw(10);
		model.addAttribute("redio", redio);
		return "waveorder/Redio_type_list";
	}
}
