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
	
	@Value("${freqWarningWebService.wsdl}")
	private String urlFreqWarning;

	@Value("${radioSignalWebService.wsdl}")
	private String urlRadioSignal;
	
	@Value("${sefon.webservice.freqservice}")
	private String urlFreq;
	
	@Value("${importFreqRangeManageService.wsdl}")
	private String urlImportFreqRange;

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

			reslutList = response.getWarningInfos().getFreqWarningDTOs().stream().map(t -> {

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

                t.getStatList().getFreqWarningStatDTOs().stream().map(m -> {
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
				return "alarmmanage/important_monitor";
			}else {
				//如果没有查询到数据，设置默认的频段范围，是否频段，nullID
				MeasureTaskParamDto dto = new MeasureTaskParamDto();
				dto.setBeginFreq(Double.valueOf(centorFreq.divide(divisor).toString()));
				dto.setEndFreq(Double.valueOf(centorFreq.divide(divisor).toString()));
				dto.setFreqRange(false);
				dto.setWarnID(map.get("warningID").toString());
				System.out.println("===================================================没有数据传入model:"+JSON.toJSONString(dto));
				model.addAttribute("dto",dto);
				return "alarmmanage/important_monitor_insert";
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
	    			return "alarmmanage/important_monitor";
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
				return "alarmmanage/important_monitor_insert";
				//成功返回空白页面
			}else {
				System.out.println("==========================================删除失败!");
				return "false";
				//不成功返回失败信息
			}
	    }
}
