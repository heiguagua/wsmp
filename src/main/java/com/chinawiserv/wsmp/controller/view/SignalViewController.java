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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.tempuri.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	@RequestMapping(path = { "/", ""
	},params = {"id=sefon"})
	public String homeForSefan(Model model,@RequestParam String cenFreg) {

		model.addAttribute("mapUrl",mapUrl);
		model.addAttribute("FromSingal",cenFreg);
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
			Map<String, Object> map = hbaseClient.queryFeaturePara(stationCode, beginTime, Long.parseLong(centorfreq));

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

			redioDetail.setBand(center / 1000000);
			redioDetail.setCentor(bandWidth / 1000000);
			redioDetail.setType(radio.getTypeCode() + "");
			redioDetail.setrMax(map.get("rmax"));
			redioDetail.setSpecT(map.get("specT"));
			redioDetail.setSymRate(map.get("symRate"));
			redioDetail.setFlatDegree(map.get("flatDegree"));
			redioDetail.setFreqPeakNumFSK(map.get("freqPeakNumFSK"));

			model.addAttribute("redioDetail", redioDetail);

			Logger.info("方法 {} 操作时间{} 请求成功 中心频率{} 开始时间{} 监测站id{} 信号id{} 返回值{} ", "signal_detail", LocalDateTime.now().toString(),centorfreq,beginTime,stationCode,id,JSON.toJSONString(redioDetail));
		}
		catch (Exception e) {
			Logger.error("方法 {} 操作时间{} 请求异常  原因{}", "signal_detail", LocalDateTime.now().toString(), e);
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
	    public String importantMonitor(Model model,@RequestBody Map<String,Object> map) throws MalformedURLException {
	    	//根据频段查询重点监测，返回页面和对象
	    	System.out.println("=================================map:"+map);
//	    	BigDecimal beginFreq = new BigDecimal(map.get("beginFreq").toString());
			BigDecimal centorFreq = new BigDecimal(map.get("centorFreq").toString());
			BigDecimal divisor = new BigDecimal(1000000);
			URL url = new URL(urlImportFreqRange);
	    	ImportFreqRangeManageService service = new ImportFreqRangeManageService(url);
			IImportFreqRangeManageService service2 = service.getBasicHttpBindingIImportFreqRangeManageService();
			String result = service2.findFreqByWarn(map.get("warningID").toString());
			System.out.println("=================================result:"+result);
			final Type type = new TypeReference<MeasureTaskParamDto>() {}.getType();
			@SuppressWarnings("unchecked")
			MeasureTaskParamDto resultDTO = (MeasureTaskParamDto) JSON.parseObject(result,type);
			System.out.println("====================================resultDTO:"+JSON.toJSONString(resultDTO));
			//查询到重点监测
			if(resultDTO != null) {
				model.addAttribute("dto", resultDTO);
				return "signal/important_monitor";
			}else {
				//如果没有查询到数据，设置默认的频段范围，是否频段，nullID
				MeasureTaskParamDto dto = new MeasureTaskParamDto();
				dto.setBeginFreq(Double.valueOf(centorFreq.divide(divisor).toString()));
				dto.setEndFreq(Double.valueOf(centorFreq.divide(divisor).toString()));
				dto.setFreqRange(false);
				dto.setWarnID(map.get("warningID").toString());
				System.out.println("===================================================没有数据传入model:"+JSON.toJSONString(dto));
				model.addAttribute("dto",dto);
				return "signal/important_monitor_insert";
			}
			
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
	    			return "signal/important_monitor";
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
				return "signal/important_monitor_insert";
				//成功返回空白页面
			}else {
				System.out.println("==========================================删除失败!");
				return "false";
				//不成功返回失败信息
			}
	    }
}
