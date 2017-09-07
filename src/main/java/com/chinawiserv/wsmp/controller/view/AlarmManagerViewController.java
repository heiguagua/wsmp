package com.chinawiserv.wsmp.controller.view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chinawiserv.apps.util.logger.Logger;
import com.chinawiserv.wsmp.client.WebServiceSoapFactory;
import com.chinawiserv.wsmp.pojo.MeasureTaskParamDto;
import com.chinawiserv.wsmp.pojo.RedioType;
import com.chinawiserv.wsmp.pojo.Singal;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.tempuri.FreqWarningQueryRequest;
import org.tempuri.FreqWarningQueryResponse;
import org.tempuri.IImportFreqRangeManageService;
import org.tempuri.ImportFreqRangeManageService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

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
	
	@Value("${importFreqRangeManageService.wsdl}")
	private String urlImportFreqRange;
	
	private IImportFreqRangeManageService serviceImportFreqRangeManage;
	@PostConstruct
	public void init() throws MalformedURLException {
		URL url3 = new URL(urlImportFreqRange);
		serviceImportFreqRangeManage = new ImportFreqRangeManageService(url3).getBasicHttpBindingIImportFreqRangeManageService();

	}

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

                singal.setContext(t.getSaveDate().toString().replaceAll("T", " "));
                singal.setInteger(t.getCenterFreq());
                singal.setId(t.getID());
				String stationKey = t.getStationKey();
                int status = t.getStatus();
                singal.setStatus(status);
				singal.setStationKey(stationKey);
                singal.setCentorFreq(t.getCenterFreq().toString());
                String beginTime = t.getSaveDate().toString().replaceAll(":", "").replaceAll("T", "").replaceAll("-", "");
                singal.setBeginTime(beginTime);
				singal.setDes(t.getDescription());
                t.getStatList().getFreqWarningStatDTO().stream().map(m ->
                     m.getStationGUID()
                ).forEach(z -> {
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

	@RequestMapping(path = { "/", ""
	},params = {"id=QZ"})
	public String homeForQz(Model model,@RequestParam String cenFreg) {

		model.addAttribute("mapUrl",mapUrl);
		model.addAttribute("FromQz",cenFreg);
		model.addAttribute("redioType",redioType);
		return "alarmmanage/alarmmanage_home";
	}

	@RequestMapping(path = { ""
	})
	public ModelAndView test(ModelAndView modelAndView) throws IOException {
		modelAndView.setViewName("alarmmanage/alarmmanage_home");
		modelAndView.addObject("redioType", redioType);
		modelAndView.addObject("mapUrl",mapUrl);
		return modelAndView;
	}
	
	@PostMapping("/importantMonitor")
	public String importantMonitor(Model model, @RequestBody Map<String, Object> map) {
		// 根据频段查询重点监测，返回页面和对象
		Logger.info("=================================map:" + map);
		BigDecimal centorFreq = new BigDecimal(map.get("centorFreq").toString());
		BigDecimal divisor = new BigDecimal(1000000);
		Double centorFre = Double.valueOf(centorFreq.divide(divisor).toString());
		String result = serviceImportFreqRangeManage.findByFreq(map.get("warningID").toString(), centorFre);
		Logger.info("=================================result:" + result);
		final Type type = new TypeReference<MeasureTaskParamDto>() {
		}.getType();
		MeasureTaskParamDto resultDTO = (MeasureTaskParamDto) JSON.parseObject(result, type);
		Logger.info("====================================resultDTO:" + JSON.toJSONString(resultDTO));
		// 查询到重点监测
		if (resultDTO != null) {
			// 判断是频点重点监测还是频段重点监测
			if (resultDTO.isFreqRange()) {
				// 如果是频段
				// 传入告警ID和中心频率
				resultDTO.setWarnID(map.get("warningID").toString());
				resultDTO.setBeginFreq(Double.valueOf(centorFreq.divide(divisor).toString()));
				resultDTO.setEndFreq(Double.valueOf(centorFreq.divide(divisor).toString()));
				model.addAttribute("dto", resultDTO);
			} else {
				// 如果是频点
				model.addAttribute("dto", resultDTO);
			}
			return "waveorder/important_monitor";
		} else {
			// 如果没有查询到数据，设置默认的频段范围，是否频段，nullID
			MeasureTaskParamDto dto = new MeasureTaskParamDto();
			dto.setBeginFreq(Double.valueOf(centorFreq.divide(divisor).toString()));
			dto.setEndFreq(Double.valueOf(centorFreq.divide(divisor).toString()));
			dto.setFreqRange(false);
			dto.setWarnID(map.get("warningID").toString());
			Logger.info(
					"===================================================没有数据传入model:" + JSON.toJSONString(dto));
			model.addAttribute("dto", dto);
			return "waveorder/important_monitor_insert";
		}

	}

	@ResponseBody
	@PostMapping("/importantMonitorCreateOrUpdate")
	public String importantMonitorCreateOrUpdate(MeasureTaskParamDto dto, Model model){
		// 或者直接用模型接受参数MeasureTaskParamDto.java
		Logger.info("===================更新或添加=======================前端传参dto:" + JSON.toJSONString(dto));
		if (dto.getID().equals("")) {
			dto.setID(null);
		}
		// 更新信号管理中的频段的重点监测(相当于添加频点的重点监测)
		if (dto.isFreqRange()) {
			// 如果为频段重点监测,
			dto.setID(null);
			dto.setFreqRange(false);
		}
		// 更新或添加重点监测，进行更新或添加操作，只管操作成功与否.
		String json = JSON.toJSONString(dto);
		String resultDTOJson = serviceImportFreqRangeManage.createOrUpdate(json);
		if (resultDTOJson != null) {
			return null;
		} else {
			return "false";
		}
	}

	@ResponseBody
	@PostMapping("/importantMonitorDelete")
	public String importantMonitorDelete(MeasureTaskParamDto dto, Model model){
		// 或者直接用模型接受参数MeasureTaskParamDto.java
		Logger.info("==================删除========================前端传参dto:" + JSON.toJSONString(dto));
		Boolean resultDTOJson = serviceImportFreqRangeManage.removeById(dto.getID());
		if (resultDTOJson) {
			return null;
		} else {
			return "false";
		}
	}
}
