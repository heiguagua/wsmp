package com.chinawiserv.wsmp.client;

import com.alibaba.fastjson.JSON;
import com.chinawiserv.apps.util.logger.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tempuri.*;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.chinawiserv.apps.logger.Logger.errorThrow;

@Component
public class WebServiceSoapFactory {

	@Value("${freqWarningWebService.wsdl}")
	private String wsdlFreWarning;

	@Value("${fregHistoryWebService.wsdl}")
	private String wsdlFregHistory;

	@Value("${radioStationWebService.wsdl}")
	private String wsdlradioStation;

	@Value("${radioSignalWebService.wsdl}")
	private String radioSignal;

	@Value("${importFreqRangeManageService.wsdl}")
	private String iImportFreqRangeManageServiceUrl;

	private FreqWarningWebServiceSoap freqWarnService;

	private FreqHistoryWebServiceSoap fregHistoryService;

	private RadioStationWebServiceSoap radioStationService;

	private RadioSignalWebServiceSoap radioSignalService;

	//private  IImportFreqRangeManageService iImportFreqRangeManageService;

	public Object fregHistoryServiceCall(String methodName, String param, Class<?> parameterTypes) {

		try {
			Method method = this.fregHistoryService.getClass().getMethod(methodName, parameterTypes);

			Object object = JSON.parseObject(param, parameterTypes);

			return method.invoke(this.freqWarnService, object);

		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			errorThrow("{}方法{}调用错误   参数{}", methodName, param, parameterTypes);
		}

		return null;
	}

	public Object freqWarnServiceCall(String methodName, String param, Class<?> parameterTypes) {

		try {
			Method method = this.freqWarnService.getClass().getMethod(methodName, parameterTypes);
			final JSON json =  (JSON)JSON.parse(param);
			Object object = JSON.toJavaObject(json, parameterTypes);

			return method.invoke(this.freqWarnService, object);

		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			Logger.error("=============没有对应的方法==================", e);
		}

		return null;
	}

	@PostConstruct
	public void init() {

		final JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

		factoryBean.setAddress(this.wsdlFreWarning);
		this.freqWarnService = factoryBean.create(FreqWarningWebServiceSoap.class);

		factoryBean.setAddress(this.wsdlFregHistory);
		this.fregHistoryService = factoryBean.create(FreqHistoryWebServiceSoap.class);

		factoryBean.setAddress(this.wsdlradioStation);
		this.radioStationService = factoryBean.create(RadioStationWebServiceSoap.class);

		factoryBean.setAddress(this.radioSignal);
		this.radioSignalService = factoryBean.create(RadioSignalWebServiceSoap.class);

//		factoryBean.setAddress(this.iImportFreqRangeManageServiceUrl);
//		this.iImportFreqRangeManageService = factoryBean.create(IImportFreqRangeManageService.class);
	}

	@SuppressWarnings("unchecked")
	public <T> T parash(T t, String param) {
		// final Type type = new TypeReference<T>() {}.getType();
		// t = JSON.parseObject(param, type);
		t = (T) JSON.parseObject(param, t.getClass());
		return t;

	}

	public Object radioSignalServiceCall(String methodName, String param, Class<?> parameterTypes) throws JsonProcessingException {

		try {
			Method method = this.radioSignalService.getClass().getMethod(methodName, parameterTypes);
			final JSON json =  (JSON)JSON.parse(param);
			Object object = JSON.toJavaObject(json, parameterTypes);

			return method.invoke(this.radioSignalService, object);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw errorThrow("{} 没有对应的方法 {}", e, "radioSignalService", methodName);
		}
	}

	public Object radioStationServiceCall(String methodName, String param, Class<?> parameterTypes) throws JsonProcessingException {

		try {

			Method method = this.radioStationService.getClass().getMethod(methodName, parameterTypes);

			Object object = JSON.parseObject(param, parameterTypes);

			return method.invoke(this.radioStationService, object);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			errorThrow("{}方法{}调用错误   参数{}", methodName, param, parameterTypes);
		}
		return null;
	}

//	public Object IImportFreqRangeManageServiceCall( String methodName, String param){
//		String method = Reflect.on(iImportFreqRangeManageService).call(methodName,param).toString();
//			return  null;
//	}

	public FreqWarningWebServiceSoap getFreqWarnService() {
		return freqWarnService;
	}
}
