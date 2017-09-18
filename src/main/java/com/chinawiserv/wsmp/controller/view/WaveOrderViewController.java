
package com.chinawiserv.wsmp.controller.view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chinawiserv.apps.logger.Logger;
import com.chinawiserv.wsmp.pojo.MeasureTaskParamDto;
import com.chinawiserv.wsmp.pojo.RedioStatusCount;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tempuri.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

 
@Controller
@RequestMapping("/waveorder")
public class WaveOrderViewController {
	
	@Value("${radioSignalWebService.wsdl}")
	private String urlRadioSignal;
	
	@Value("${importFreqRangeManageService.wsdl}")
	private String urlImportFreqRange;
	
	@Value("${mapservice.wdsl}")
	private String mapUrl;
	
	private static RadioSignalWebServiceSoap serviceRadioSignalSoap;
	
	private static IImportFreqRangeManageService serviceImportFreqRangeManage;
	
	
	@PostConstruct
	public void init() throws MalformedURLException {
	    URL url2 = new URL(urlRadioSignal);
	    RadioSignalWebService serviceRadioSignal = new RadioSignalWebService(url2);
	    serviceRadioSignalSoap = serviceRadioSignal.getRadioSignalWebServiceSoap();
		URL url3 = new URL(urlImportFreqRange);
		serviceImportFreqRangeManage = new ImportFreqRangeManageService(url3).getBasicHttpBindingIImportFreqRangeManageService();

	}

    @GetMapping(path = {"/", ""})
	public String home(Model model, @RequestParam(required=false,name="areaCode") String areaCode) {
    	Optional.ofNullable(areaCode).ifPresent(a -> {
    		model.addAttribute("areaCode",a);
    	});
    	//缺少对于四方接口的参数校验
		model.addAttribute("mapUrl", mapUrl);
        return "waveorder/waveorder_home";
    }
    
    @PostMapping("/importantMonitor")
    public String importantMonitor(Model model,@RequestBody Map<String,Object> map) {
    	//根据频段查询重点监测，返回页面和对象
    	Logger.info("map:{}",map);
    	//BigInteger convert to Double
    	BigDecimal beginFreqCalculate = new BigDecimal(map.get("beginFreq").toString());
		BigDecimal endFreqCalculate = new BigDecimal(map.get("endFreq").toString());
		BigDecimal divisor = new BigDecimal(1000000);
		Double beginFreq = Double.valueOf(beginFreqCalculate.divide(divisor).toString());
		Double endFreq = Double.valueOf(endFreqCalculate.divide(divisor).toString());
		String result = serviceImportFreqRangeManage.findAllFreqRange();
		if (result == null) {
			//如果没有查询到数据，设置默认的频段范围，是否频段，nullID
			MeasureTaskParamDto dto = new MeasureTaskParamDto();
			dto.setBeginFreq(beginFreq);
			dto.setEndFreq(endFreq);
			dto.setFreqRange(true);
			Logger.info("没有数据传入model:{}",JSON.toJSONString(dto));
			model.addAttribute("dto",dto);
			return "waveorder/important_monitor_insert";
		}
		final Type type = new TypeReference<List<MeasureTaskParamDto>>() {}.getType();
		@SuppressWarnings("unchecked")
		List<MeasureTaskParamDto> resultList = (List<MeasureTaskParamDto>) JSON.parseObject(result,type);
		Logger.info("resultList:{}",JSON.toJSONString(resultList));
		//过滤传过来的频段
		Optional<MeasureTaskParamDto> optional = resultList.stream().filter(dto -> beginFreq >= dto.getBeginFreq() &&
				endFreq <= dto.getEndFreq())
				.findFirst();
		
		if(optional.isPresent()) {
			Logger.info("查询结果:{}",JSON.toJSONString(optional.get()));
			model.addAttribute("dto",optional.get());
			return "waveorder/important_monitor";
		}
		//如果没有查询到数据，设置默认的频段范围，是否频段，nullID
		MeasureTaskParamDto dto = new MeasureTaskParamDto();
		dto.setBeginFreq(beginFreq);
		dto.setEndFreq(endFreq);
		dto.setFreqRange(true);
		Logger.info("没有数据传入model:{}",JSON.toJSONString(dto));
		model.addAttribute("dto",dto);
		return "waveorder/important_monitor_insert";
    }
    
    @ResponseBody
    @PostMapping("/importantMonitorCreateOrUpdate")
    public String importantMonitorCreateOrUpdate(MeasureTaskParamDto dto) {
    	//或者直接用模型接受参数MeasureTaskParamDto.java
    	Logger.info("更新或添加-前端传参dto:{}",JSON.toJSONString(dto));
    	if(dto.getID().equals("")) {
    		dto.setID(null);
    	}
    	//System.out.println("==========================================前端传参operation:"+operation);
    		//更新或添加重点监测，进行更新或添加操作，只管操作成功与否.
    		String json = JSON.toJSONString(dto);
    		String resultDTOJson = serviceImportFreqRangeManage.createOrUpdate(json);
    		if(resultDTOJson != null) {
    			Logger.info("更新或添加成功！");
    			return null;
    		}else {
    			return "false";
    		}
    }
    
    @ResponseBody
    @PostMapping("/importantMonitorDelete")
    public String importantMonitorDelete(MeasureTaskParamDto dto) {
    	//或者直接用模型接受参数MeasureTaskParamDto.java
    	Logger.info("删除-前端传参dto:{}",JSON.toJSONString(dto));
		Boolean resultDTOJson = serviceImportFreqRangeManage.removeById(dto.getID());
		if(resultDTOJson) {
			Logger.info("删除成功！");
			return null;
		}else {
			return "false";
		}
    }

	@PostMapping("/redioType")
	public String redioType(Model model, @RequestBody Map<String, Object> map) {
		//根据监测站查询信号类型统计
//		System.out.println("================================map:"+map);
		//设置大类型
		RadioSignalClassifiedQueryRequest request = new RadioSignalClassifiedQueryRequest();
		ArrayOfString value = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> monitorsNum = (List<String>) map.get("monitorsNum");
		value.setString(monitorsNum);
		request.setStationNumber(value);
		RadioSignalClassifiedQueryResponse response = serviceRadioSignalSoap.queryRadioSignalClassified(request);
		//System.out.println("===============================response:"+JSON.toJSONString(response));
		RedioStatusCount rsCount = new RedioStatusCount();
		//设置合法子类型(违规)
		RadioSignalSubClassifiedQueryRequest request2 = new RadioSignalSubClassifiedQueryRequest();
		request2.setStationNumber(value);
		request2.setType(1);
		RadioSignalSubClassifiedQueryResponse response2 = serviceRadioSignalSoap.queryRadioSignalSubClassified(request2);
		
		Integer legalSubTypeCount = response2.getLstOnStation().getSignalSubStaticsOnStation().stream().mapToInt(m -> m.getCount()).reduce(0,(a,b) -> a + b);
		rsCount.setLegalUnNormalStationNumber(legalSubTypeCount);
		
		response.getLstOnStation().getSignalStaticsOnStation().stream()
			.flatMap(t -> t.getSignalStaticsLst().getSignalStatics().stream())
			.collect(Collectors.groupingBy(SignalStatics :: getSignalType))
			.entrySet().stream()
			.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream().mapToInt(m -> m.getCount()).reduce(0,(a,b) -> a + b)))
			.entrySet().stream()
			.forEach(f -> {
				switch(f.getKey()) {
				case 1:
					rsCount.setLegalNormalStationNumber(f.getValue() - legalSubTypeCount);
					break;
				case 2:
					rsCount.setKonwStationNumber(f.getValue());
					break;
				case 3:
					rsCount.setIllegalSignal(f.getValue());
					break;
				case 4:
					rsCount.setUnKonw(f.getValue());
					break;
				default:
					;
				}
			});
		
		model.addAttribute("redio", rsCount);
		return "waveorder/redio_type_list";
	}
	@PostMapping("/redioTypeForSiFon")
	public String redioTypeForSiFon(Model model, @RequestBody Map<String, Object> map) {
		//根据监测站查询信号类型统计
//		System.out.println("================================map:"+map);
		RadioSignalClassifiedQueryRequest request = new RadioSignalClassifiedQueryRequest();
		ArrayOfString value = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> monitorsNum = (List<String>) map.get("monitorsNum");
		value.setString(monitorsNum);
		request.setStationNumber(value);
		RadioSignalClassifiedQueryResponse response = serviceRadioSignalSoap.queryRadioSignalClassified(request);
		//System.out.println("===============================response:"+JSON.toJSONString(response));
		
		RedioStatusCount rsCount = new RedioStatusCount();
		//设置合法子类型(违规)
		RadioSignalSubClassifiedQueryRequest request2 = new RadioSignalSubClassifiedQueryRequest();
		request2.setStationNumber(value);
		request2.setType(1);
		RadioSignalSubClassifiedQueryResponse response2 = serviceRadioSignalSoap.queryRadioSignalSubClassified(request2);
		Integer legalSubTypeCount = response2.getLstOnStation().getSignalSubStaticsOnStation().stream().mapToInt(m -> m.getCount()).reduce(0,(a,b) -> a + b);
		rsCount.setLegalUnNormalStationNumber(legalSubTypeCount);
		
		//设置大类型
		response.getLstOnStation().getSignalStaticsOnStation().stream()
			.flatMap(t -> t.getSignalStaticsLst().getSignalStatics().stream())
			.collect(Collectors.groupingBy(SignalStatics :: getSignalType))
			.entrySet().stream()
			.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream().mapToInt(m -> m.getCount()).reduce(0,(a,b) -> a + b)))
			.entrySet().stream()
			.forEach(f -> {
				switch(f.getKey()) {
				case 1:
					rsCount.setLegalNormalStationNumber(f.getValue() - legalSubTypeCount);
					break;
				case 2:
					rsCount.setKonwStationNumber(f.getValue());
					break;
				case 3:
					rsCount.setIllegalSignal(f.getValue());
					break;
				case 4:
					rsCount.setUnKonw(f.getValue());
					break;
				default:
					;
				}
			});
		model.addAttribute("redio", rsCount);
		return "waveorder/redio_type_list_to_sifon";
	}
	
	

}

