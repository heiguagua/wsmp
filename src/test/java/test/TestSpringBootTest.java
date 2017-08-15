package test;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.tempuri.ArrayOfFrequencyBand;
import org.tempuri.ArrayOfInt;
import org.tempuri.ArrayOfString;
import org.tempuri.FrequencyBand;
import org.tempuri.IImportFreqRangeManageService;
import org.tempuri.ImportFreqRangeManageService;
import org.tempuri.RadioSignalClassifiedQueryRequest;
import org.tempuri.RadioSignalClassifiedQueryResponse;
import org.tempuri.RadioSignalWebService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chinawiserv.apps.util.logger.Logger;
import com.chinawiserv.wsmp.pojo.MeasureTaskParamDto;
import com.chinawiserv.wsmp.pojo.RedioStatusCount;
import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@SpringBootTest
@SpringBootConfiguration
public class TestSpringBootTest {
	@Value("${freqWarningWebService.wsdl}")
	private String urlFreqWarning;

	@Value("${radioSignalWebService.wsdl}")
	private String urlRadioSignal;

	@Value("${sefon.webservice.freqservice}")
	private String urlFreq;

	@Value("${importFreqRangeManageService.wsdl}")
	private String urlImportFreqRange;

	@Test
	public void testWebService() throws MalformedURLException {
		URL wsdlLocation = new URL(urlImportFreqRange);
		ImportFreqRangeManageService service = new ImportFreqRangeManageService(wsdlLocation );
		//查询频点
		String response = service.getBasicHttpBindingIImportFreqRangeManageService().findFreqByWarn("048512a1-7a88-4536-8bea-473367492db4");
		System.out.println("========================================:"+response);
		final Type type = new TypeReference<MeasureTaskParamDto>() {}.getType();
		@SuppressWarnings("unchecked")
		MeasureTaskParamDto resultList = (MeasureTaskParamDto) JSON.parseObject(response,type);
		Logger.info("=================={}", JSON.toJSONString(resultList));	
		
		
		//添加
		//增加重点监测或更新(更新需要ID)
		MeasureTaskParamDto dto = new MeasureTaskParamDto();
//		dto.setID("f49925a5-9a94-41c5-b118-c5e6a2e0dd96");
//		dto.setWarnID("0eadb104-dad6-40e9-814f-249c6b095833");
		dto.setWarnID("048512a1-7a88-4536-8bea-473367492db4");
		dto.setBeginFreq(0.1);
		dto.setEndFreq(0.1);
		dto.setBeginTime("2017-08-07T19:13:30");
		dto.setEndTime("2017-08-07T19:13:30");
		dto.setCycleStep(30);
		dto.setDuration(30);
		dto.setIQCount(5);
		dto.setSpecCount(5);
		dto.setFeatureCount(5);
		dto.setITUCount(5);
		dto.setAudioTimespan(29);//执行时长无效，应大于声音采集时长
		dto.setTotalAudioTimespan(6);
		dto.setTotalFeatureCount(6);
		dto.setTotalIQCount(6);
		dto.setTotalITUCount(6);
		dto.setTotalSpecCount(6);
		dto.setFreqRange(false);
		String jsonTaskParam = JSON.toJSONString(dto);
		String result = service.getBasicHttpBindingIImportFreqRangeManageService().createOrUpdate(jsonTaskParam);
		System.out.println("==============================================================result:"+result);		
	}
}
