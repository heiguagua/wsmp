package com.chinawiserv.wsmp.pojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

@Component
public class MapData {

	@Autowired
	public ApplicationContext spring;

	public Object data;

	@PostConstruct
	public void ss() {

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
			this.data = map.get("geometries");
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {

		}

	}

	public Object getData() {
		return data;
	}

}
