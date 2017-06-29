package com.chinawiserv.wsmp.controller.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.chinawiserv.wsmp.pojo.IntensiveMonitoring;
import com.chinawiserv.wsmp.pojo.WarringConfirm;
import com.chinawiserv.wsmp.service.impl.IntensiveMonitoringServiceImpl;

@RestController
@RequestMapping("/data/alarm")
public class AlarmDataController {

	@Autowired
	IntensiveMonitoringServiceImpl iIntensiveMonitoringServicel;

	@GetMapping("/dayCharts")
	public Object dayCharts(@RequestParam Map<String, Object> param) {

		String[] xAxis = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24" };
		double[] series = new double[] { 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 51.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9, 55.2, 58.4,
				60.0, 58.1, 56.2, 58.9 };
		HashMap<String, Object> map = new HashMap<>();
		map.put("xAxis", xAxis);
		map.put("series", series);
		return map;
	}

	@GetMapping("/hourCharts")
	public Object hourCharts(@RequestParam Map<String, Object> param) {

		String[] xAxis = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24" };
		double[] series = new double[] { 55, 60.5, 60.0, 58.1, 56.2, 58.9, 58.2, 57.4, 58.0, 60.1, 59.1, 58.2, 58, 60.0, 58.1, 59.1, 57.9, 51.5, 55.2, 58.4,
				58.2, 58, 57.9, 55.2, 58.4 };
		HashMap<String, Object> map = new HashMap<>();
		map.put("xAxis", xAxis);
		map.put("series", series);
		return map;

	}

	@GetMapping(path = "/monthCharts")
	public Object monthCharts(@RequestParam Map<String, Object> param) {

		// RestRequest re = new RestRequest(request);
		String[] xAxis = new String[] { "1", "10", "20", "30", "40", "50", "60", "70", "80", "90" };
		double[] series = new double[] { 55, 62.5, 55.2, 58.4, 60.0, 58.1, 59.1, 58.2, 58, 57.9 };
		HashMap<String, Object> map = new HashMap<>();
		map.put("xAxis", xAxis);
		map.put("series", series);
		return map;
	}

	@PostMapping(path = "/intensivemonitoring")
	public void intensivemonitoring(@RequestBody IntensiveMonitoring in) {

		if (in.getStatus() == 0) {
			// 需要取消对应的
			EntityWrapper<IntensiveMonitoring> ew = new EntityWrapper<IntensiveMonitoring>(in);
			ew.where("SINGAL_FREQUENCY = {0}", in.getSingalFrequency());
			iIntensiveMonitoringServicel.delete(ew);
		} else {
			iIntensiveMonitoringServicel.insert(in);
		}
	}

	@PostMapping(path = "/warringconfirm")
	public void warning_confirm(@RequestBody WarringConfirm warringConfirm) {}
}
