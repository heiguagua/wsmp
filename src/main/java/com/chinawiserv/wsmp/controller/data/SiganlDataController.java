package com.chinawiserv.wsmp.controller.data;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RestController
@RequestMapping("/data/signal")
public class SiganlDataController {

	@GetMapping("/FmRate")
	public Object getFMRate() {

		final List<Map<String, Object>> reslut = Lists.newArrayListWithExpectedSize(2);

		final Map<String, Object> map1 = Maps.newLinkedHashMapWithExpectedSize(2);
		final Map<String, Object> map2 = Maps.newLinkedHashMapWithExpectedSize(2);

		map1.put("name", "AM");
		map1.put("value", 20);

		map2.put("name", "FM");
		map2.put("value", 80);

		reslut.add(map1);
		reslut.add(map2);

		return reslut;
	}
}
