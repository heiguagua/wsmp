package com.chinawiserv.wsmp.controller.view;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/getSignalListForSifon")
public class WaveOrderSignalListForSifonController {
	
	@GetMapping(path = {"/", ""})
	public String getSignalList(Model model, 
			@RequestParam(required=false,name="areaCode",defaultValue="5101") String areaCode ,
			@RequestParam(required=false,name="signalType",defaultValue="1") Integer signalType , 
			@RequestParam(required=false,name="isSubType",defaultValue="false") Boolean isSubType) {
		System.out.println(areaCode + "|" +signalType +"|"+ isSubType);
		
		model.addAttribute("areaCode",areaCode);
		model.addAttribute("signalType", signalType);
		model.addAttribute("isSubType", isSubType);
		return "waveorder/waveorder_signalList_to_sifon";
	}
}
