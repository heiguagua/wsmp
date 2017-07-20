package com.chinawiserv.wsmp.controller.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinawiserv.wsmp.cache.CacheConfig;

@RestController
@RequestMapping("/csdata")
public class CsdataController {

	@Autowired
	@Qualifier(value = CacheConfig.USR_DATA)
	private Object userInfo;

	@Autowired
	@Qualifier(value = CacheConfig.DATA_LIST)
	private Object dataList;

	@GetMapping("/userInfo")
	public Object getUserInfo() {
		return userInfo;
	}

	@GetMapping("/stationList")
	public Object name() {
		return dataList;
	}
}
