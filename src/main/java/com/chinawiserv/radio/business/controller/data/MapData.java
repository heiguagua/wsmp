package com.chinawiserv.radio.business.controller.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/data/map")
public class MapData {

	@Autowired
	ApplicationContext spring;

	@RequestMapping(path = "/getGeoJson", method = RequestMethod.GET)
	public Object getGeoJson() {
		Resource resource = this.spring.getResource("classpath:geoJson/Tianjin_Great.json");
		try {
			File file = resource.getFile();
			FileReader fis = new FileReader(file);
			BufferedReader br = new BufferedReader(fis);
			String next = "";
			String line;
			while ((line = br.readLine()) != null) {
				next = next.concat(line);
			}
			LinkedHashMap<String, Object> map = JSON.parseObject(next, new TypeReference<LinkedHashMap<String, Object>>() {});
			br.close();
			Object object = map.get("geometries");
			return object;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {

		}

		return Collections.emptyList();
	}
}
