package com.chinawiserv.wsmp.client;

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
import com.fasterxml.jackson.databind.ObjectMapper;

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
			System.out.println(parameterTypes);
			Object object = parameterTypes.newInstance();
			object = parash(object, param);
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writeValueAsString(object));
			return method.invoke(radioSignalService, object);

		}
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			Logger.error("=============没有对应的方法==================", e);
		}
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			Logger.error("=============请勿调用私有的方法==================", e);
		}
		catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Object radioStationServiceCall(String methodName, String param, Class<?> parameterTypes) {
		try {
			Method method = radioStationService.getClass().getMethod(methodName, parameterTypes);
			Object object = parameterTypes.newInstance();
			object = parash(object, param);
			return method.invoke(freqWarnService, object);

		}
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			Logger.error("=============没有对应的方法==================", e);
		}
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			Logger.error("=============请勿调用私有的方法==================", e);
		}
		catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Object radioStationServiceCall(String methodName, Class<?> parameterTypes) {
		try {
			Method method = radioStationService.getClass().getMethod(methodName, parameterTypes);
			Object object = parameterTypes.newInstance();
			return method.invoke(radioStationService, object);

		}
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			Logger.error("=============没有对应的方法==================", e);
		}
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			Logger.error("=============请勿调用私有的方法==================", e);
		}
		catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Object fregHistoryServiceCall(String methodName, String param, Class<?> parameterTypes) {
		try {
			Method method = fregHistoryService.getClass().getMethod(methodName, parameterTypes);
			Object object = parameterTypes.newInstance();
			object = parash(object, param);
			return method.invoke(freqWarnService, object);

		}
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			Logger.error("=============没有对应的方法==================", e);
		}
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			Logger.error("=============请勿调用私有的方法==================", e);
		}
		catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Object freqWarnServiceCall(String methodName, String param, Class<?> parameterTypes) {
		try {
			Method method = freqWarnService.getClass().getMethod(methodName, parameterTypes);
			Object object = parameterTypes.newInstance();
			object = parash(object, param);
			// freqWarnService.query((FreqWarningQueryRequest) object);
			return method.invoke(freqWarnService, object);

		}
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			Logger.error("=============没有对应的方法==================", e);
		}
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			Logger.error("=============请勿调用私有的方法==================", e);
		}
		catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	// public static void main(String[] args) throws JsonProcessingException {
	// Map<String, Object> testMap = Maps.newHashMap();
	// testMap.put("centerFreq", 100000);
	// ObjectMapper mapper = new ObjectMapper();
	// String param = mapper.writeValueAsString(testMap);
	// // final Type type = new TypeReference<FreqWarningQueryRequest>()
	// // {}.getType();
	// // FreqWarningQueryRequest freqWarningQueryRequest =
	// // JSON.parseObject(param, type);
	// FreqWarningQueryRequest f = new FreqWarningQueryRequest();
	// //
	// System.out.println(mapper.writeValueAsString(freqWarningQueryRequest));
	// }

}
