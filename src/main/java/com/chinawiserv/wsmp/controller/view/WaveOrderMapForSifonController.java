package com.chinawiserv.wsmp.controller.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinawiserv.apps.logger.Logger;

@Controller
@RequestMapping("/getMapForSifon")
public class WaveOrderMapForSifonController {

	@Value("${mapservice.wdsl}")
	private String mapUrl;
	
	@GetMapping(path = {"/", ""})
	public String getWaveOrderMap(Model model, @RequestParam Map<String, Object> map) {
		Logger.info("============================================ {}",map);
		String areaCode = map.get("areaCode").toString();
		model.addAttribute("areaCode",areaCode);
		model.addAttribute("mapUrl", mapUrl);
		return "waveorder/waveorder_map_to_sifon";
	}
}
