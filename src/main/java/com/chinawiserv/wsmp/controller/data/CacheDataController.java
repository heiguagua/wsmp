package com.chinawiserv.wsmp.controller.data;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chinawiserv.wsmp.cache.CacheConfig;

/**
 * Created by jh on 2017/9/12.
 */


@RestControllerAdvice
@RequestMapping("/cache/data")
public class CacheDataController {

    @Autowired
    @Qualifier(CacheConfig.MAP_DATA)
    List<Object> regions;


    @Autowired
    @Qualifier(CacheConfig.COLOR_LIST)
    List<int[]> colorsList;
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

    @RequestMapping(path ={"/getColor"},method = RequestMethod.GET)
    public  Object getColor() throws IOException{
        return  colorsList;
    }

}
