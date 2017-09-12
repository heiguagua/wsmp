package com.chinawiserv.wsmp.controller.data;

import com.chinawiserv.wsmp.cache.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Created by jh on 2017/9/12.
 */


@RestControllerAdvice
@RequestMapping("/cache/data")
public class CacheDataController {

    @Autowired
    @Qualifier(CacheConfig.MAP_DATA)
    List<Object> regions;

    @RequestMapping(path ={"/mapdata"},method = RequestMethod.GET)
    public  Object getData(){
        return  regions;
    }

}
