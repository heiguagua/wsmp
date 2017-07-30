package com.chinawiserv.wsmp.controller.view;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.tempuri.RadioSignalDTO;
import org.tempuri.RadioSignalQueryRequest;
import org.tempuri.RadioSignalQueryResponse;

import com.chinawiserv.wsmp.client.WebServiceSoapFactory;
import com.chinawiserv.wsmp.hbase.HbaseClient;
import com.chinawiserv.wsmp.pojo.MonitoringStation;
import com.chinawiserv.wsmp.pojo.RedioDetail;
import com.chinawiserv.wsmp.pojo.Signature;
import com.chinawiserv.wsmp.pojo.Singal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/signal")
public class SignalViewController {

	@Autowired
	WebServiceSoapFactory service;

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	HbaseClient hbaseClient;

	@RequestMapping(path = { "/", "" })
	public String home() {
		return "signal/signal_home";
	}

	@PostMapping(path = "/sigaldetail")
	public String signal_detail(Model model, @RequestParam String centorfreq, @RequestParam String beginTime, @RequestParam String endTime,
			@RequestParam String areaCode, @RequestParam String stationcode) throws Exception {

		long frequency = Long.parseLong(centorfreq);
		Map<String, Object> map = hbaseClient.queryFeaturePara(stationcode, beginTime, endTime, frequency);

		model.addAttribute("sigal", new Signature(1, "xxxx", 5.02, 90.0, 30));

		Map<String, Object> requestPara = Maps.newLinkedHashMap();

		requestPara.put("beginFreq", centorfreq);
		requestPara.put("endFreq", centorfreq);

		LinkedList<Integer> areaCodes = Lists.newLinkedList();
		areaCodes.add(Integer.parseInt(areaCode));

		Map<String, LinkedList<Integer>> arry = Maps.newHashMap();

		arry.put("_int", areaCodes);

		requestPara.put("areaCodes", arry);
		RedioDetail redioDetail = new RedioDetail();
		final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall("queryRadioSignal",
				mapper.writeValueAsString(requestPara), RadioSignalQueryRequest.class);

		System.out.println(mapper.writeValueAsString(responce));

		RadioSignalDTO radio = responce.getRadioSignals().getRadioSignalDTO().get(0);

		int center = radio.getCenterFreq().intValue();
		Long bandWidth = radio.getBandWidth();

		redioDetail.setBand(center / 1000000);
		redioDetail.setCentor(bandWidth / 1000000);
		redioDetail.setType(radio.getTypeCode() + "");
		redioDetail.setrMax(map.get("rmax"));
		redioDetail.setSpecT(map.get("specT"));
		redioDetail.setSymRate(map.get("symRate"));
		redioDetail.setFlatDegree(map.get("flatDegree"));
		redioDetail.setFreqPeakNumFSK(map.get("freqPeakNumFSK"));

		model.addAttribute("redioDetail", redioDetail);

		return "signal/signal_detail";
	}

	@PostMapping(path = "/singallist")
	public Object singalList(@RequestParam Map<String, String> param, ModelAndView modelAndView) throws JsonProcessingException {

		final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall("queryRadioSignal",
				mapper.writeValueAsString(param), RadioSignalQueryRequest.class);

		final List<Singal> redio = responce.getRadioSignals().getRadioSignalDTO().stream().map(t -> {

			final Singal singal = new Singal();
			final String id = t.getID();

			singal.setId(id);
			singal.setCentorFreq(t.getCenterFreq().toString());
			singal.setBeginTime(t.getSaveDate().toString().replaceAll(":", "").replaceAll("T", "").replaceAll("-", ""));
			singal.setEndTime(t.getInvalidDate().toString().replaceAll(":", "").replaceAll("T", "").replaceAll("-", ""));
			singal.setContext(t.getCenterFreq().toString().concat("   ").concat(t.getSaveDate().toString()));

			return singal;
		}).collect(toList());
		modelAndView.setViewName("signal/signal_list");
		modelAndView.addObject("stations", redio);
		return modelAndView;
	}

	@PostMapping(path = { "/stationlist" })
	public ModelAndView stationList(ModelAndView modelAndView, @RequestParam Map<String, Object> param) {
		modelAndView.setViewName("signal/monitoring_station_list");
		List<MonitoringStation> stations = new ArrayList<MonitoringStation>();
		modelAndView.addObject("stations", stations);
		return modelAndView;
	}
}
