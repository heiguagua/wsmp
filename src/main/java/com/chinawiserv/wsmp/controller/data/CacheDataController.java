package com.chinawiserv.wsmp.controller.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chinawiserv.apps.logger.Logger;
import com.chinawiserv.wsmp.cache.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jh on 2017/9/12.
 */


@RestControllerAdvice
@RequestMapping("/cache/data")
public class CacheDataController {

    @Autowired
    @Qualifier(CacheConfig.MAP_DATA)
    List<Object> regions;
    
//    @Value("${config.home:classpath:}")
//	private String configHome;
//
//	@Autowired
//	private ApplicationContext def;

    @RequestMapping(path ={"/mapdata"},method = RequestMethod.GET)
    public  Object getData(@RequestParam(required=false,name="areaCode") String areaCode) throws IOException{
    	//根据区域码取得区域边界
//    	Logger.info("areaCode={}", areaCode);
//    	String province = areaCode.substring(0,2).concat("00");
//    	final Resource resource = this.def.getResource(configHome.concat("boundary/"+province+".json"));
//		final File file = resource.getFile();
//		final Type type = new TypeReference<LinkedHashMap<String, Object>>() {}.getType();
//
//		try (InputStream is = Files.newInputStream(file.toPath())) {
//
//			final LinkedHashMap<String, Object> map = JSON.parseObject(is, type);
//
//			Map<String,Object> boundary = (Map<String, Object>) map.get(areaCode);
//			List<Object> regions = (List<Object>) boundary.get("boundary");
//
//			return regions;
//		}
        return  regions;
    }

}
