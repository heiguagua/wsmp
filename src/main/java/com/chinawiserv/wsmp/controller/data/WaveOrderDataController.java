package com.chinawiserv.wsmp.controller.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.chinawiserv.wsmp.pojo.RsbtStation;
import com.chinawiserv.wsmp.service.impl.RsbtStationServiceImpl;

@RestController
@RequestMapping("/data/waveorder")
public class WaveOrderDataController {

	@Autowired
	RsbtStationServiceImpl impl;

	public Object get(@RequestParam RsbtStation station) {
		EntityWrapper<RsbtStation> ew = new EntityWrapper<RsbtStation>(station);
		ew.where("STAT_Type = {0}", station.getSTATType());
		List<RsbtStation> stations = impl.selectList(ew);
		return stations;
	}

}
