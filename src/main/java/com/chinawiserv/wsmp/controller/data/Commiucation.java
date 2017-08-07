package com.chinawiserv.wsmp.controller.data;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("data/commiucation")
public class Commiucation {

	@RequestMapping("/toptable")
	public void getTopTable() {

	}

}
