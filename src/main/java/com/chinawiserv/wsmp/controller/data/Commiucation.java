package com.chinawiserv.wsmp.controller.data;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chinawiserv.wsmp.pojo.CommiucationTableTop;

@RestControllerAdvice
@RequestMapping("data/commiucation")
public class Commiucation {

    @RequestMapping("/toptable")
    public CommiucationTableTop getTopTable() {
	return null;

    }

}
