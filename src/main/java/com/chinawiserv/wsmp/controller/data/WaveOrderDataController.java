package com.chinawiserv.wsmp.controller.data;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.chinawiserv.wsmp.pojo.AlarmUnDealed;
import com.chinawiserv.wsmp.pojo.RedioStatusCount;
import com.chinawiserv.wsmp.pojo.RsbtStation;
import com.chinawiserv.wsmp.service.impl.RsbtStationServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RestController
@RequestMapping("/data/waveorder")
public class WaveOrderDataController {

	@Autowired
	private RsbtStationServiceImpl impl;

	@GetMapping("/rediostatus")
	public Map<String, Object> getRedioStatus(@RequestParam Map<String, Object> param) {
		// offset 第几页
		// limit=10, 个数
		// areaCode=xxxx 区域代码
		final Map<String, Object> reslut = Maps.newHashMap();
		final RedioStatusCount count = new RedioStatusCount();
		count.setRedioName("广播");
		count.setIllegalSignal(9);
		count.setKonwStationNumber(8);
		count.setLegalNormalStationNumber(9);
		count.setLegalUnNormalStationNumber(111);
		count.setUnKonw(111);
		final List<RedioStatusCount> redioStatus = Lists.newArrayListWithCapacity(1);
		for (int i = 0; i < 10; i++) {
			redioStatus.add(count);
		}
		reslut.put("total", 30);
		reslut.put("rows", redioStatus);
		return reslut;
	}

	@GetMapping("/alarmundealed")
	public Map<String, Object> getAlarmUnDealed(@RequestParam Map<String, Object> param) {
		System.out.println(param);
		// offset 第几页
		// limit=10, 个数
		// areaCode=xxxx 区域代码
		final Map<String, Object> reslut = Maps.newHashMap();
		List<AlarmUnDealed> list = Lists.newLinkedList();
		AlarmUnDealed alarmUnDealed = new AlarmUnDealed();
		alarmUnDealed.setFirstTime("2017-06-13");
		alarmUnDealed.setLastingTime("2017-06-27");
		alarmUnDealed.setMark("猜猜看");
		alarmUnDealed.setRadio("黑广播");
		alarmUnDealed.setRadioStatus("3");
		alarmUnDealed.setRadioType("黑广播");
		alarmUnDealed.setStation("....");
		for (int i = 0; i < 10; i++) {
			list.add(alarmUnDealed);
		}
		reslut.put("total", 30);
		reslut.put("rows", list);
		return reslut;
	}

	@GetMapping("/alarmdealed")
	public Map<String, Object> getAlarmDealed(@RequestParam Map<String, Object> param) {
		System.out.println(param);
		// offset 第几页
		// limit=10, 个数
		// areaCode=xxxx 区域代码
		final Map<String, Object> reslut = Maps.newHashMap();
		List<AlarmUnDealed> list = Lists.newLinkedList();
		AlarmUnDealed alarmUnDealed = new AlarmUnDealed();
		alarmUnDealed.setFirstTime("2017-06-13");
		alarmUnDealed.setLastingTime("2017-06-27");
		alarmUnDealed.setMark("猜猜看11");
		alarmUnDealed.setRadio("黑广播");
		alarmUnDealed.setRadioStatus("4");
		alarmUnDealed.setRadioType("黑广播");
		alarmUnDealed.setStation("....");
		for (int i = 0; i < 10; i++) {
			list.add(alarmUnDealed);
		}
		reslut.put("total", 30);
		reslut.put("rows", list);
		return reslut;
	}

	public Object get(@RequestParam RsbtStation station) {
		EntityWrapper<RsbtStation> ew = new EntityWrapper<RsbtStation>(station);
		ew.where("STAT_Type = {0}", station.getSTATType());
		List<RsbtStation> stations = impl.selectList(ew);
		return stations;
	}
}
