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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.tempuri.IImportFreqRangeManageService;
import org.tempuri.ImportFreqRangeManageService;
import org.tempuri.RadioSignalDTO;
import org.tempuri.RadioSignalQueryRequest;
import org.tempuri.RadioSignalQueryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

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
	
	@Value("${freqWarningWebService.wsdl}")
	private String urlFreqWarning;

	@Value("${radioSignalWebService.wsdl}")
	private String urlRadioSignal;
	
	@Value("${sefon.webservice.freqservice}")
	private String urlFreq;
	
	@Value("${importFreqRangeManageService.wsdl}")
	private String urlImportFreqRange;

	@RequestMapping(path = { "/", ""
	})
	public String home(Model model) {

		model.addAttribute("mapUrl",mapUrl);
		return "signal/signal_home";
	}

	@PostMapping(path = "/sigaldetail")
	public String signal_detail(Model model, @RequestParam String centorfreq, @RequestParam String beginTime, @RequestParam String endTime,
			@RequestParam String areaCode, @RequestParam String stationCode, @RequestParam String id) {

		String Id = "52010126";
		long frequency = 0L;
		String timeStart = "20170812125344";

		try {
			// long frequency = Long.parseLong(centorfreq);
			Map<String, Object> map = hbaseClient.queryFeaturePara(Id, timeStart, frequency);

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

			int center = 0;

			if (radio.getCenterFreq() != null) {
				center = radio.getCenterFreq().intValue();
			}

			long bandWidth = radio.getBandWidth();

			redioDetail.setBand(center / 1000000);
			redioDetail.setCentor(bandWidth / 1000000);
			redioDetail.setType(radio.getTypeCode() + "");
			redioDetail.setrMax(map.get("rmax"));
			redioDetail.setSpecT(map.get("specT"));
			redioDetail.setSymRate(map.get("symRate"));
			redioDetail.setFlatDegree(map.get("flatDegree"));
			redioDetail.setFreqPeakNumFSK(map.get("freqPeakNumFSK"));

			model.addAttribute("redioDetail", redioDetail);
		}
		catch (Exception e) {
			Logger.error("方法 {} 请求异常  原因{}", "signal_detail", e);
		}

		return "signal/signal_detail";
	}

	@PostMapping(path = "/singallist")
	public Object singalList(@RequestParam Map<String, String> para, ModelAndView modelAndView) throws JsonProcessingException {

		final String param = para.get("param").toString();
		final String jsonStr = param.replace("\\","");

		final RadioSignalQueryResponse responce = (RadioSignalQueryResponse) service.radioSignalServiceCall("queryRadioSignal",
				jsonStr, RadioSignalQueryRequest.class);

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

	@PostMapping(path = { "/stationlist"
	})
	public ModelAndView stationList(ModelAndView modelAndView, @RequestParam Map<String, Object> param) {
		modelAndView.setViewName("signal/monitoring_station_list");
		List<MonitoringStation> stations = new ArrayList<>();
		modelAndView.addObject("stations", stations);
		return modelAndView;
	}
	
	 @PostMapping("/importantMonitor")
	    public String importantMonitor(Model model,@RequestBody Map<String,Object> map) throws MalformedURLException {
	    	//根据频段查询重点监测，返回页面和对象
	    	System.out.println("=================================map:"+map);
	    	BigDecimal beginFreq = new BigDecimal(map.get("beginFreq").toString());
			BigDecimal endFreq = new BigDecimal(map.get("endFreq").toString());
			BigDecimal divisor = new BigDecimal(1000000);
			URL url = new URL(urlImportFreqRange);
	    	ImportFreqRangeManageService service = new ImportFreqRangeManageService(url);
			IImportFreqRangeManageService service2 = service.getBasicHttpBindingIImportFreqRangeManageService();
			String result = service2.findAllFreqRange();
			//System.out.println("=================================result:"+result);
			final Type type = new TypeReference<List<MeasureTaskParamDto>>() {}.getType();
			@SuppressWarnings("unchecked")
			List<MeasureTaskParamDto> resultList = (List<MeasureTaskParamDto>) JSON.parseObject(result,type);
			System.out.println("====================================resultList:"+JSON.toJSONString(resultList));
			//过滤传过来的频段
			Optional<MeasureTaskParamDto> optional = resultList.stream().filter(dto -> Double.valueOf(beginFreq.divide(divisor).toString()) >= dto.getBeginFreq() &&
					Double.valueOf(endFreq.divide(divisor).toString()) <= dto.getEndFreq()).findFirst();
			if(optional.isPresent()) {
				System.out.println("==============================================查询结果"+JSON.toJSONString(optional.get()));
				model.addAttribute("dto",optional.get());
				return "waveorder/important_monitor";
			}
			//如果没有查询到数据，设置默认的频段范围，是否频段，nullID
			MeasureTaskParamDto dto = new MeasureTaskParamDto();
			dto.setBeginFreq(Double.valueOf(beginFreq.divide(divisor).toString()));
			dto.setEndFreq(Double.valueOf(endFreq.divide(divisor).toString()));
			dto.setFreqRange(true);
			System.out.println("===================================================没有数据传入model:"+JSON.toJSONString(dto));
			model.addAttribute("dto",dto);
			return "waveorder/important_monitor_insert";
	    }
	    
	    @PostMapping("/importantMonitorCreateOrUpdate")
	    public String importantMonitorCreateOrUpdate(MeasureTaskParamDto dto,Model model) throws MalformedURLException {
	    	//或者直接用模型接受参数MeasureTaskParamDto.java
	    	System.out.println("===================更新或添加=======================前端传参dto:"+JSON.toJSONString(dto));
	    	if(dto.getID().equals("")) {
	    		dto.setID(null);
	    	}
	    	//System.out.println("==========================================前端传参operation:"+operation);
	    	URL url = new URL(urlImportFreqRange);
	    	ImportFreqRangeManageService service = new ImportFreqRangeManageService(url);
			IImportFreqRangeManageService service2 = service.getBasicHttpBindingIImportFreqRangeManageService();
	    		//更新或添加重点监测，进行更新或添加操作，只管操作成功与否.
	    		String json = JSON.toJSONString(dto);
	    		String resultDTOJson = service2.createOrUpdate(json);
	    		final Type type = new TypeReference<MeasureTaskParamDto>() {}.getType();
	    		MeasureTaskParamDto resultDTO = (MeasureTaskParamDto) JSON.parseObject(resultDTOJson,type);
	    		if(resultDTOJson != null) {
	    			System.out.println("====================================更新或添加成功");
	    			System.out.println("====================================更新或添加model:"+JSON.toJSONString(resultDTO));
	    			model.addAttribute("dto",resultDTO);
	    			return "waveorder/important_monitor";
	    		}else{
	    			System.out.println("====================================更新或添加失败");
	    			return "false";
	    		}
	    }
	    
	    
	    @PostMapping("/importantMonitorDelete")
	    public String importantMonitorDelete(MeasureTaskParamDto dto,Model model) throws MalformedURLException {
	    	//或者直接用模型接受参数MeasureTaskParamDto.java
	    	System.out.println("==================删除========================前端传参dto:"+JSON.toJSONString(dto));
	    	URL url = new URL(urlImportFreqRange);
	    	ImportFreqRangeManageService service = new ImportFreqRangeManageService(url);
			IImportFreqRangeManageService service2 = service.getBasicHttpBindingIImportFreqRangeManageService();

			Boolean resultDTOJson = service2.removeById(dto.getID());
			if(resultDTOJson) {
				System.out.println("==========================================删除成功!");
				MeasureTaskParamDto modelDTO = new MeasureTaskParamDto();
				modelDTO.setBeginFreq(dto.getBeginFreq());
				modelDTO.setEndFreq(dto.getEndFreq());
				modelDTO.setFreqRange(true);
				System.out.println("==========================================删除成功传入model:"+JSON.toJSONString(modelDTO));
				model.addAttribute("dto",modelDTO);
				return "waveorder/important_monitor_insert";
				//成功返回空白页面
			}else {
				System.out.println("==========================================删除失败!");
				return "false";
				//不成功返回失败信息
			}
	    }
}
