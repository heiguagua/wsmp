package com.chinawiserv.wsmp.controller.data;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinawiserv.wsmp.cache.CacheConfig;

@RestController
@RequestMapping(path = "/data/map")
public class MapDataController {

	@Autowired
	ApplicationContext spring;

	@Resource(name = CacheConfig.MAP_DATA)
	private Object data;


	public Object getData(Object data) {
		return data;
	}
}
