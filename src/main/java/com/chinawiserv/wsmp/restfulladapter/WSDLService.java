package com.chinawiserv.wsmp.restfulladapter;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.springframework.core.annotation.AnnotationUtils.getValue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chinawiserv.apps.util.logger.Logger;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class WSDLService {

	final String serviceUrl;

	private Client client;

	private LinkedHashMap<String, WSDLServiceMethod> methods;

	public WSDLService(String serviceUrl) {
		super();
		this.serviceUrl = serviceUrl;
	}

	public void destroy() {

		if (ObjectUtils.isEmpty(this.client)) return;

		this.client.destroy();
		this.client = null;
		this.methods.clear();
	}

	public ServiceMethod getMethod(String methodName) {
		return Optional.ofNullable(this.methods.get(methodName)).orElseGet(() -> Logger.optionalError("{} 没有发现 {} 服务", this.serviceUrl, methodName));
	}

	public String getType() {
		return WSDLServiceAdapterFactory.type;
	}

	@Async
	public void init() {

		this.destroy();

		this.client = JaxWsDynamicClientFactory.newInstance().createClient(this.serviceUrl);

		final Optional<ServiceInfo> opService = this.client.getEndpoint().getService().getServiceInfos().stream().findFirst();
		final ServiceInfo serviceInfo = opService.orElseGet(() -> Logger.optionalError("没有发现服务信息", this.serviceUrl));

		final Optional<BindingInfo> bdOptional = serviceInfo.getBindings().stream().findFirst();
		final BindingInfo bindingInfo = bdOptional.orElseGet(() -> Logger.optionalError("没有发现绑定的服务", this.serviceUrl));

		final Collection<BindingOperationInfo> options = bindingInfo.getOperations();
		final List<BindingOperationInfo> optionInfos = options.stream().map(op -> op.isUnwrapped() ? op.getWrappedOperation() : op.getUnwrappedOperation())
				.collect(toList());

		this.methods = optionInfos.stream().map(op -> {

			final List<MessagePartInfo> inputInfos = op.getInput().getMessageParts();

			final Map<String, Class<?>> inputClasses = inputInfos.stream().collect(toMap(mp -> mp.getName().getLocalPart(), mp -> mp.getTypeClass(), (k, v) -> {
				return null;
			}, LinkedHashMap::new));

			final Map<String, Object> inputDescriptor = inputClasses.entrySet().stream()
					.collect(toMap(e -> e.getKey(), e -> this.getClassDescriptor(e.getValue()), (k, u) -> null, LinkedHashMap::new));

			final Optional<MessagePartInfo> resultOptional = op.getOutput().getMessageParts().stream().findFirst();
			final Object outputDescriptor = resultOptional.map(mp -> this.getClassDescriptor(mp.getTypeClass())).orElseGet(() -> "void");
			return new WSDLServiceMethod(op.getName(), inputClasses, inputDescriptor, outputDescriptor);

		}).collect(toMap(m -> m.operationName.getLocalPart(), m -> m, (k, v) -> null, LinkedHashMap::new));
	}

	Map<String, Object> getMethodInfos(String serverInfo) {

		return this.methods.entrySet().stream().collect(toMap(e -> e.getKey(), e -> {

			final WSDLServiceMethod method = e.getValue();
			final String name = e.getKey();
			final String vistUrl = serverInfo.concat(name);
			return ImmutableMap.of("服务名称", name, "注册地址", this.serviceUrl, "访问地址", vistUrl, "参数", method.inputDescriptor, "返回值", method.outputDescriptor);
		}));
	}

	private Object getClassDescriptor(Class<?> clazz) {

		if (clazz.equals(String.class) || Number.class.isAssignableFrom(clazz) || clazz.isPrimitive()) return clazz.getSimpleName();

		if (clazz.isArray()) return Lists.newArrayList(this.getClassDescriptor(clazz.getComponentType()));

		if (clazz.equals(XMLGregorianCalendar.class)) return "YYYY-MM-DD hh:ss:mm";

		return stream(clazz.getDeclaredFields()).filter(f -> !Modifier.isStatic(f.getModifiers()))
				.collect(toMap(this::getFieldName, this::getFieldDescriptor, (k, v) -> null, LinkedHashMap::new));
	}

	private Object getFieldDescriptor(Field field) {

		final Class<?> clazz = field.getType();

		if (Object.class.equals(clazz)) return "AnyType";

		if (Collection.class.isAssignableFrom(clazz)) {

			final Class<?> genericClass = this.getGenericClasses(field.getGenericType()).get(0);
			return Lists.newArrayList(this.getClassDescriptor(genericClass));
		}

		if (Map.class.isAssignableFrom(clazz)) {

			final List<Class<?>> genericClasses = this.getGenericClasses(field.getGenericType());
			return ImmutableMap.of(this.getClassDescriptor(genericClasses.get(0)), this.getClassDescriptor(genericClasses.get(1)));
		}

		if (clazz.isArray()) {

			final Class<?> genericClass = clazz.getComponentType();
			return Lists.newArrayList(this.getClassDescriptor(genericClass));
		}

		return this.getClassDescriptor(clazz);
	}

	private String getFieldName(Field f) {

		final Stream<Annotation> stream = stream(f.getAnnotations());
		final Optional<Object> optional = stream.map(a -> getValue(a, "name")).filter(name -> !"##default".equals(name)).findFirst();
		return String.valueOf(optional.orElseGet(() -> f.getName()));
	}

	private List<Class<?>> getGenericClasses(Type gType) {

		if (ParameterizedType.class.isAssignableFrom(gType.getClass())) {

			final ParameterizedType parameterizedType = (ParameterizedType) gType;
			final Type[] types = parameterizedType.getActualTypeArguments();
			return Arrays.stream(types).map(type -> (Class<?>) type).collect(toList());
		}
		return Collections.emptyList();
	}

	public class WSDLServiceMethod implements ServiceMethod {

		private final QName operationName;

		private final Map<String, Class<?>> inputClasses;

		private final Map<String, Object> inputDescriptor;

		private final Object outputDescriptor;

		private final TypeReference<LinkedHashMap<String, Object>> typeReference = new TypeReference<LinkedHashMap<String, Object>>() {};

		public WSDLServiceMethod(QName operationName, Map<String, Class<?>> inputClasses, Map<String, Object> inputDescriptor, Object outputDescriptor) {

			this.operationName = operationName;
			this.inputClasses = inputClasses;
			this.inputDescriptor = inputDescriptor;
			this.outputDescriptor = outputDescriptor;
		}

		@Override
		public Object doMethod(String paramString) throws Exception {

			final Map<String, Object> paramMap = JSON.parseObject(paramString, this.typeReference);

			final Object[] paramObjects = this.inputClasses.entrySet().stream().map(e -> {

				final String key = e.getKey();
				final Object obj = Optional.ofNullable(paramMap.get(key)).orElseGet(() -> Logger.optionalError("参数 [{}] 必须传递", key));

				Logger.debug("参数 key {} 传递的值 {}", key, obj);

				final Class<?> clazz = e.getValue();
				return (String.class.isAssignableFrom(clazz)) ? obj : JSON.toJavaObject(JSON.class.cast(obj), clazz);

			}).collect(toList()).toArray();

			final Object[] result = WSDLService.this.client.invoke(this.operationName, paramObjects);
			return Stream.of(result).findFirst().orElseGet(() -> Collections.emptyMap());
		}
	}
}
