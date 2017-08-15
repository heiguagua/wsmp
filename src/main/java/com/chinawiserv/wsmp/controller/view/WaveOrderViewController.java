
package com.chinawiserv.wsmp.controller.view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
import java.util.concurrent.atomic.AtomicInteger;

 
@Controller
@RequestMapping("/waveorder")
public class WaveOrderViewController {
	
	@Value("${freqWarningWebService.wsdl}")
	private String urlFreqWarning;

	@Value("${radioSignalWebService.wsdl}")
	private String urlRadioSignal;
	
	@Value("${sefon.webservice.freqservice}")
	private String urlFreq;
	
	@Value("${importFreqRangeManageService.wsdl}")
	private String urlImportFreqRange;

    @GetMapping("/ssss")
    public String alarmDealed() {
        return null;
    }

    @GetMapping("/frequencyrange")
    public String frequencyRange() {
        return "waveorder/table_radio";
    }

    @GetMapping(path = {"/", ""})
	public String home(Model model, @RequestParam Map<String, Object> map) {
		
        return "waveorder/waveorder_home";
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

	@PostMapping("/redioType")
	public String redioType(Model model, @RequestBody Map<String, Object> map) throws MalformedURLException {
		//根据监测站查询信号类型统计
//		System.out.println("================================map:"+map);
		URL url = new URL(urlRadioSignal);
		RadioSignalWebService service = new RadioSignalWebService(url);
		RadioSignalClassifiedQueryRequest request = new RadioSignalClassifiedQueryRequest();
		ArrayOfString value = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> monitorsNum = (List<String>) map.get("monitorsNum");
		value.setString(monitorsNum);
		request.setStationNumber(value);
		RadioSignalClassifiedQueryResponse response = service.getRadioSignalWebServiceSoap().queryRadioSignalClassified(request);
		//System.out.println("===============================response:"+JSON.toJSONString(response));
		
		RedioStatusCount rsCountTotal = new RedioStatusCount();
		AtomicInteger index0 = new AtomicInteger();
		AtomicInteger index1 = new AtomicInteger();
		AtomicInteger index2 = new AtomicInteger();
		AtomicInteger index3 = new AtomicInteger();
		AtomicInteger index4 = new AtomicInteger();
		response.getLstOnStation().getSignalStaticsOnStation().stream().forEach(t -> {
			RedioStatusCount rsCount = new RedioStatusCount();
			t.getSignalStaticsLst().getSignalStatics().forEach(t1 -> {
				int type = t1.getSignalType();
				int count = t1.getCount();
				switch(type) {
				case 0:
					rsCount.setLegalNormalStationNumber(count);
					break;
				case 1:
					rsCount.setLegalUnNormalStationNumber(count);
					break;
				case 2:
					rsCount.setKonwStationNumber(count);
					break;
				case 3:
					rsCount.setUnKonw(count);
					break;
				case 4:
					rsCount.setIllegalSignal(count);
					break;
				default:
					;
				}
			});
			index0.getAndAdd(rsCount.getLegalNormalStationNumber());
			index1.getAndAdd(rsCount.getLegalUnNormalStationNumber());
			index2.getAndAdd(rsCount.getKonwStationNumber());
			index3.getAndAdd(rsCount.getUnKonw());
			index4.getAndAdd(rsCount.getIllegalSignal());
		});
		rsCountTotal.setLegalNormalStationNumber(index0.get());
		rsCountTotal.setLegalUnNormalStationNumber(index1.get());
		rsCountTotal.setKonwStationNumber(index2.get());
		rsCountTotal.setUnKonw(index3.get());
		rsCountTotal.setIllegalSignal(index4.get());
		model.addAttribute("redio", rsCountTotal);
		return "waveorder/redio_type_list";
	}
	@PostMapping("/redioTypeForSiFon")
	public String redioTypeForSiFon(Model model, @RequestBody Map<String, Object> map) throws MalformedURLException {
		//根据监测站查询信号类型统计
//		System.out.println("================================map:"+map);
		URL url = new URL(urlRadioSignal);
		RadioSignalWebService service = new RadioSignalWebService(url);
		RadioSignalClassifiedQueryRequest request = new RadioSignalClassifiedQueryRequest();
		ArrayOfString value = new ArrayOfString();
		@SuppressWarnings("unchecked")
		List<String> monitorsNum = (List<String>) map.get("monitorsNum");
		value.setString(monitorsNum);
		request.setStationNumber(value);
		RadioSignalClassifiedQueryResponse response = service.getRadioSignalWebServiceSoap().queryRadioSignalClassified(request);
		//System.out.println("===============================response:"+JSON.toJSONString(response));
		
		RedioStatusCount rsCountTotal = new RedioStatusCount();
		AtomicInteger index0 = new AtomicInteger();
		AtomicInteger index1 = new AtomicInteger();
		AtomicInteger index2 = new AtomicInteger();
		AtomicInteger index3 = new AtomicInteger();
		AtomicInteger index4 = new AtomicInteger();
		response.getLstOnStation().getSignalStaticsOnStation().stream().forEach(t -> {
			RedioStatusCount rsCount = new RedioStatusCount();
			t.getSignalStaticsLst().getSignalStatics().forEach(t1 -> {
				int type = t1.getSignalType();
				int count = t1.getCount();
				switch(type) {
				case 0:
					rsCount.setLegalNormalStationNumber(count);
					break;
				case 1:
					rsCount.setLegalUnNormalStationNumber(count);
					break;
				case 2:
					rsCount.setKonwStationNumber(count);
					break;
				case 3:
					rsCount.setUnKonw(count);
					break;
				case 4:
					rsCount.setIllegalSignal(count);
					break;
				default:
					;
				}
			});
			index0.getAndAdd(rsCount.getLegalNormalStationNumber());
			index1.getAndAdd(rsCount.getLegalUnNormalStationNumber());
			index2.getAndAdd(rsCount.getKonwStationNumber());
			index3.getAndAdd(rsCount.getUnKonw());
			index4.getAndAdd(rsCount.getIllegalSignal());
		});
		rsCountTotal.setLegalNormalStationNumber(index0.get());
		rsCountTotal.setLegalUnNormalStationNumber(index1.get());
		rsCountTotal.setKonwStationNumber(index2.get());
		rsCountTotal.setUnKonw(index3.get());
		rsCountTotal.setIllegalSignal(index4.get());
		model.addAttribute("redio", rsCountTotal);
		return "waveorder/redio_type_list_to_sifon";
	}

}

