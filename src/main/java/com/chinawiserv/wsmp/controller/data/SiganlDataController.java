package com.chinawiserv.wsmp.controller.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data/signal")
public class SiganlDataController {

	@GetMapping("/FmRate")
	public Object getFMRate() {
		List<LinkedHashMap<String, Object>> reslut = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> map1 = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> map2 = new LinkedHashMap<String, Object>();
		map1.put("value", 20);
		map1.put("name", "AM");
		map2.put("value", 80);
		map2.put("name", "FM");
		reslut.add(map1);
		reslut.add(map2);
		return reslut;
	}
}
