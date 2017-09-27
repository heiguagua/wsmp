package com.chinawiserv.wsmp.controller.view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chinawiserv.apps.util.logger.Logger;
import com.chinawiserv.wsmp.client.WebServiceSoapFactory;
import com.chinawiserv.wsmp.hbase.HbaseClient;
import com.chinawiserv.wsmp.pojo.MeasureTaskParamDto;
import com.chinawiserv.wsmp.pojo.MonitoringStation;
import com.chinawiserv.wsmp.pojo.RedioDetail;
import com.chinawiserv.wsmp.pojo.Singal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.tempuri.*;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/signal")
public class SignalViewController {

	@Autowired
	WebServiceSoapFactory service;

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	HbaseClient hbaseClient;

	@Value("${mapservice.wdsl}")
	private String mapUrl;
	
	@Value("${importFreqRangeManageService.wsdl}")
	private String urlImportFreqRange;

	@Value("${asio.formatter:yyyyMMddHHmmss}")
	DateTimeFormatter formatter;

	private IImportFreqRangeManageService serviceImportFreqRangeManage;
	@PostConstruct
	public void init() throws MalformedURLException {
		URL url3 = new URL(urlImportFreqRange);
		serviceImportFreqRangeManage = new ImportFreqRangeManageService(url3).getBasicHttpBindingIImportFreqRangeManageService();

	}

	@RequestMapping(path = { "/", ""
	})
	public String home(Model model) {

		model.addAttribute("mapUrl",mapUrl);
		return "signal/signal_home";
	}

	@RequestMapping(path = { "/", ""
	},params = {"id=sefon"})
	public String homeForSefan(Model model,@RequestParam String cenFreg,@RequestParam String signalId) {

		model.addAttribute("mapUrl",mapUrl);
		model.addAttribute("FromSingal",cenFreg);
		model.addAttribute("singalId",signalId);
		return "signal/signal_home";
	}


	@PostMapping(path = "/sigaldetail")
	public String signal_detail(Model model, @RequestParam String centorfreq, @RequestParam String beginTime, @RequestParam String endTime,
			@RequestParam String areaCode, @RequestParam String stationCode, @RequestParam String id) {
		try {
			// long frequency = Long.parseLong(centorfreq);
			System.out.println( LocalDateTime.now().format(formatter));
			Map<String, Object> map = hbaseClient.queryFeaturePara(stationCode, LocalDateTime.now().format(formatter), Long.parseLong(centorfreq));

			Map<String, Object> requestPara = Maps.newLinkedHashMap();

			requestPara.put("id", id);

			RedioDetail redioDetail = new RedioDetail();
			final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall("queryRadioSignal",
					mapper.writeValueAsString(requestPara), RadioSignalQueryRequest.class);

			RadioSignalDTO radio = responce.getRadioSignals().getRadioSignalDTO().stream().findFirst().orElseGet(() -> {

				RadioSignalDTO dto = new RadioSignalDTO();
				return dto;
			});

			radio = radio == null ? new RadioSignalDTO() : radio;

			double center = 0;

			if (radio.getCenterFreq() != null) {
				center = radio.getCenterFreq().doubleValue();
			}

			long bandWidth = radio.getBandWidth();

			redioDetail.setBand(bandWidth / 1000);
			redioDetail.setCentor(center / 1000000);
			redioDetail.setType(radio.getTypeCode() + "");
			redioDetail.setrMax(Optional.ofNullable(map.get("rmax")).orElse("-"));
			redioDetail.setSpecT(Optional.ofNullable(map.get("specT")).orElse("-"));
			redioDetail.setSymRate(Optional.ofNullable(map.get("symRate")).orElse("-"));
			redioDetail.setFlatDegree(Optional.ofNullable(map.get("flatDegree")).orElse("-"));
			redioDetail.setFreqPeakNumFSK(Optional.ofNullable(map.get("freqPeakNumFSK")).orElse("-"));

			model.addAttribute("redioDetail", redioDetail);
			model.addAttribute("hBaseMap", map);

			Logger.info("方法 特征值查询 操作时间{} 请求成功 中心频率{} 开始时间{} 监测站id{} 信号id{} 返回值{} ", "signal_detail", LocalDateTime.now().toString(),centorfreq,beginTime,stationCode,id,JSON.toJSONString(map));
		}
		catch (Exception e) {
			Logger.error("方法 特征值查询 操作时间{} 请求异常  原因{}", "signal_detail", LocalDateTime.now().toString(), e);
		}

		return "signal/signal_detail";
	}

	@PostMapping(path = "/singallist")
	public Object singalList(@RequestParam Map<String, String> para, ModelAndView modelAndView)  {

		final String param = para.get("param").toString();
		final String jsonStr = param.replace("\\","");

		try {
			final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall("queryRadioSignal",
                    jsonStr, RadioSignalQueryRequest.class);

			final List<Singal> redio = responce.getRadioSignals().getRadioSignalDTO().stream().map(t -> {

                final Singal singal = new Singal();
                final String id = t.getID();

                singal.setId(id);
                singal.setWarnimgId(t.getWarningFreqID());
                singal.setCentorFreq(t.getCenterFreq().toString());
                singal.setBeginTime(t.getSaveDate().toString().replaceAll(":", "").replaceAll("T", "").replaceAll("-", ""));
                singal.setEndTime(t.getInvalidDate().toString().replaceAll(":", "").replaceAll("T", "").replaceAll("-", ""));
                singal.setContext(t.getSaveDate().toString().replaceAll("T", " "));
				singal.setDes(t.getDes());
				singal.setType(t.getTypeCode());
				singal.setStationKey(t.getStationKey());
				//singal.setStation(t.getStationDTOs().getRadioSignalStationDTO().stream().findFirst().orElseGet(()->new RadioSignalStationDTO()).getStationNumber());
                return singal;
            }).collect(toList());

			modelAndView.addObject("stations", redio);
		} catch (Exception e) {
			Logger.error("请求信号列表异常{}",e);
		}

		modelAndView.setViewName("signal/signal_list");
		return modelAndView;
	}

	@PostMapping(path = { "/stationlist"
	})
	public ModelAndView stationList(ModelAndView modelAndView, @RequestParam Map<String, Object> param) {
		modelAndView.setViewName("signal/monitoring_station_list");
		List<MonitoringStation> stations = new ArrayList<>();
		modelAndView.addObject("stations", stations);
		return modelAndView;
	}
	
	@PostMapping("/importantMonitor")
	public String importantMonitor(Model model, @RequestBody Map<String, Object> map){
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
			dto.setIQCount(0);
			dto.setTotalIQCount(0);
			dto.setFeatureCount(0);
			dto.setTotalFeatureCount(0);
			dto.setITUCount(0);
			dto.setTotalITUCount(0);
			dto.setSpecCount(0);
			dto.setTotalSpecCount(0);
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
