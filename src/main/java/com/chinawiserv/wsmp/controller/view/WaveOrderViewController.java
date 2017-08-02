package com.chinawiserv.wsmp.controller.view;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tempuri.ArrayOfString;
import org.tempuri.RadioSignalClassifiedQueryRequest;
import org.tempuri.RadioSignalClassifiedQueryResponse;
import org.tempuri.RadioSignalWebService;

import com.alibaba.fastjson.JSON;
import com.chinawiserv.wsmp.pojo.RedioStatusCount;


@Controller
@RequestMapping("/waveorder")
public class WaveOrderViewController {

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

	@PostMapping("/redioType")
	public String redioType(Model model, @RequestBody Map<String, Object> map) {
		//根据监测站查询信号类型统计
		//System.out.println("================================map:"+map);
		RadioSignalWebService service = new RadioSignalWebService();
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
		return "waveorder/Redio_type_list";
	}
}
