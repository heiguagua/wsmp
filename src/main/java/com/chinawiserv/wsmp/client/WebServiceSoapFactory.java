package com.chinawiserv.wsmp.client;

import static com.chinawiserv.apps.logger.Logger.errorThrow;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tempuri.FreqHistoryWebServiceSoap;
import org.tempuri.FreqWarningWebServiceSoap;
import org.tempuri.RadioSignalWebServiceSoap;
import org.tempuri.RadioStationWebServiceSoap;

import com.alibaba.fastjson.JSON;
import com.chinawiserv.apps.util.logger.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;

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

	private FreqWarningWebServiceSoap freqWarnService;

	private FreqHistoryWebServiceSoap fregHistoryService;

	private RadioStationWebServiceSoap radioStationService;

	private RadioSignalWebServiceSoap radioSignalService;

	@PostConstruct
	public void init() {

		final JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();

		factoryBean.setAddress(wsdlFreWarning);
		this.freqWarnService = factoryBean.create(FreqWarningWebServiceSoap.class);

		factoryBean.setAddress(wsdlFregHistory);
		this.fregHistoryService = factoryBean.create(FreqHistoryWebServiceSoap.class);

		factoryBean.setAddress(wsdlradioStation);
		this.radioStationService = factoryBean.create(RadioStationWebServiceSoap.class);

		factoryBean.setAddress(radioSignal);
		this.radioSignalService = factoryBean.create(RadioSignalWebServiceSoap.class);
	}

	public Object radioSignalServiceCall(String methodName, String param, Class<?> parameterTypes) throws JsonProcessingException {

		try {
			Method method = radioSignalService.getClass().getMethod(methodName, parameterTypes);

			Object object = JSON.parseObject(param, parameterTypes);

			return method.invoke(radioSignalService, object);
		}
		catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw errorThrow("{} 没有对应的方法 {}", e, "radioSignalService", methodName);
		}
	}

	public Object radioStationServiceCall(String methodName, String param, Class<?> parameterTypes) throws JsonProcessingException {

		try {

			Method method = radioStationService.getClass().getMethod(methodName, parameterTypes);

			Object object = JSON.parseObject(param, parameterTypes);

			return method.invoke(radioStationService, object);
		}
		catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			errorThrow("{}方法{}调用错误   参数{}", methodName, param, parameterTypes);
		}
		return null;
	}

	public Object fregHistoryServiceCall(String methodName, String param, Class<?> parameterTypes) {

		try {
			Method method = fregHistoryService.getClass().getMethod(methodName, parameterTypes);

			Object object = JSON.parseObject(param, parameterTypes);

			return method.invoke(freqWarnService, object);

		}
		catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			errorThrow("{}方法{}调用错误   参数{}", methodName, param, parameterTypes);
		}

		return null;
	}

	public Object freqWarnServiceCall(String methodName, String param, Class<?> parameterTypes) {

		try {
			Method method = freqWarnService.getClass().getMethod(methodName, parameterTypes);

			Object object = JSON.parseObject(param, parameterTypes);

			return method.invoke(freqWarnService, object);

		}
		catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			Logger.error("=============没有对应的方法==================", e);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T parash(T t, String param) {
		// final Type type = new TypeReference<T>() {}.getType();
		// t = JSON.parseObject(param, type);
		t = (T) JSON.parseObject(param, t.getClass());
		return t;

	}

}
