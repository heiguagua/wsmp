//package com.chinawiserv.radio.config;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ClassUtils;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.chinawiserv.radio.config.file.FileListener;
//
//
//@Service
//public class AfterLoadOverListener implements ApplicationListener<ContextRefreshedEvent> {
//	
//	@Autowired
//	FileListener fileListener;
//	@Value(value = "${mybaits.mapper}")
//	private  String mapperPath;
//	@Autowired
//	WebApplicationContext context;
//	
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event) {
//		// TODO Auto-generated method stub
//		 if(event.getApplicationContext().getParent() == null){
//			//开始执行容器加载完毕后的初始化代码
//			//遍历需要异步进行调用的对象
//			  for (Object object :  context.getBeansWithAnnotation(NeedAsyn.class).values()) {
//				 final Class<?> objClass = object.getClass();
//				 final NeedAsyn needAsyn = AnnotationUtils.findAnnotation(objClass, NeedAsyn.class);
//				 final String methodName = needAsyn.aysn_method();
//				 System.out.println(methodName);
//				 final Method method = ClassUtils.getMethod(objClass, methodName,String.class);
//				 try {
//					 System.out.println(mapperPath);
//					method.invoke(object,mapperPath);
//				} catch (IllegalAccessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IllegalArgumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			  }
//			 	
//	        }
//	}
//
//}
