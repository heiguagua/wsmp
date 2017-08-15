package com.chinawiserv.wsmp.controller.view;

import com.alibaba.fastjson.JSON;
import com.chinawiserv.apps.util.logger.Logger;
import com.chinawiserv.wsmp.client.WebServiceSoapFactory;
import com.chinawiserv.wsmp.pojo.RedioType;
import com.chinawiserv.wsmp.pojo.Singal;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.tempuri.FreqWarningQueryRequest;
import org.tempuri.FreqWarningQueryResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
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

	@Value("${mapservice.wdsl}")
	private String mapUrl;

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

		final String param = para.get("param").toString();

		final String jsonStr = param.replace("\\","");
		final FreqWarningQueryResponse response ;

		List<Singal> reslutList = Collections.emptyList();
		try {
			response = (FreqWarningQueryResponse) service.freqWarnServiceCall("query", jsonStr, FreqWarningQueryRequest.class);

			reslutList = response.getWarningInfos().getFreqWarningDTO().stream().map(t -> {

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
			Logger.info("告警列表获取成功  操作时间{}  入参{} 返回值{}", LocalDateTime.now().toString(), JSON.toJSONString(para),JSON.toJSONString(reslutList));
		} catch (Exception e) {
			Logger.error("告警列表获取异常  操作时间{}  入参{} 异常{}", LocalDateTime.now().toString(), JSON.toJSONString(para),e);
		}

		model.addAttribute("stations", reslutList);

		return "signal/signal_list";
	}

	@RequestMapping(path = { ""
	})
	public ModelAndView test(ModelAndView modelAndView) throws IOException {
		modelAndView.setViewName("alarmmanage/alarmmanage_home");
		modelAndView.addObject("redioType", redioType);
		modelAndView.addObject("mapUrl",mapUrl);
		return modelAndView;
	}
}
