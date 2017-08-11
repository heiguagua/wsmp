package com.chinawiserv.wsmp.controller.view;

import com.chinawiserv.wsmp.client.WebServiceSoapFactory;
import com.chinawiserv.wsmp.pojo.RedioType;
import com.chinawiserv.wsmp.pojo.Singal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.tempuri.FreqWarningQueryRequest;
import org.tempuri.FreqWarningQueryResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/alarmmanage")
public class AlarmManagerViewController {

	@Autowired
	private RedioType redioType;

	@Autowired
	WebServiceSoapFactory service;

	@GetMapping(path = "/dayCharts")
	public String dayCharts(@RequestParam Map<String, Object> params) {
		return "alarmmanage/day_chart";
	}

	@GetMapping(path = "/hourCharts")
	public String hourCharts(@RequestParam Map<String, Object> params) {
		return "alarmmanage/hour_chart";
	}

	@GetMapping(path = "/monthCharts")
	public String monthCharts(@RequestParam Map<String, Object> params) {

		return "alarmmanage/month_chart";
	}

	@PostMapping(path = { "/singal"
	})
	public String singalList(Model model, @RequestParam Map<String, Object> para) throws Exception {

		final ObjectMapper mapper = new ObjectMapper();
		final String param = para.get("param").toString();

		final FreqWarningQueryResponse response = (FreqWarningQueryResponse) service.freqWarnServiceCall("query", param, FreqWarningQueryRequest.class);
		System.out.println(mapper.writeValueAsString(response));
		final List<Singal> reslutList = response.getWarningInfos().getFreqWarningDTO().stream().map(t -> {

			final Singal singal = new Singal();

			List<String> listId = Lists.newArrayList();

			singal.setContext(t.getCenterFreq().toString().concat("  ").concat(t.getSaveDate().toString()));
			singal.setInteger(t.getCenterFreq());
			System.out.println(t.getID());
			singal.setId(t.getID());

			int id = t.getStatus();
			singal.setStatus(id);

			singal.setCentorFreq(t.getCenterFreq().toString());

			String beginTime = t.getSaveDate().toString().replaceAll(":", "").replaceAll("T", "").replaceAll("-", "");
			singal.setBeginTime(beginTime);

			t.getStatList().getFreqWarningStatDTO().stream().map(m -> {
				return m.getStationGUID();
			}).forEach(z -> {
				listId.add(z);
			});

			final int size = listId.size();

			String listIdString = "";

			for (int i = 0; i < size; i++) {
				listIdString = listIdString.concat(listId.get(i)).concat(",");
			}

			int index = listIdString.lastIndexOf(",");

			listIdString = index != -1 ? listIdString.substring(0, index) : "";

			singal.setListString(listIdString);
			return singal;
		}).collect(toList());

		model.addAttribute("stations", reslutList);

		return "signal/signal_list";
	}

	@RequestMapping(path = { ""
	})
	public ModelAndView test(ModelAndView modelAndView) throws IOException {
		modelAndView.setViewName("alarmmanage/alarmmanage_home");
		modelAndView.addObject("redioType", redioType);
		return modelAndView;
	}
}
