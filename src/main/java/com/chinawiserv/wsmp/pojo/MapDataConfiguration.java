package com.chinawiserv.wsmp.pojo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.LinkedHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

@Configuration
public class MapDataConfiguration {

	public final static String MAP_DATA = "mapData";

	// @PostConstruct
	// public void ss() {
	//
	// Resource resource = this.spring.getResource("classpath:geoJson/Tianjin_Great.json");
	// try {
	// File file = resource.getFile();
	// FileReader fis = new FileReader(file);
	// BufferedReader br = new BufferedReader(fis);
	// String next = "";
	// String line;
	// while ((line = br.readLine()) != null) {
	// next = next.concat(line);
	// }
	// LinkedHashMap<String, Object> map = JSON.parseObject(next, new TypeReference<LinkedHashMap<String, Object>>() {});
	// br.close();
	// this.data = map.get("geometries");
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// } finally {
	//
	// }
	//
	// }

	@Bean(name = MAP_DATA)
	public Object mapData() throws IOException {

		final File file = ResourceUtils.getFile("classpath:geoJson/Tianjin_Great.json");
		final Type type = new TypeReference<LinkedHashMap<String, Object>>() {}.getType();

		try (InputStream is = Files.newInputStream(file.toPath())) {

			final LinkedHashMap<String, Object> map = JSON.parseObject(is, type);
			return map.get("geometries");
		}
	}
}
