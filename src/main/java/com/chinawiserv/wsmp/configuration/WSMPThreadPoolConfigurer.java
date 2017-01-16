package com.chinawiserv.wsmp.configuration;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableAsync
public class WSMPThreadPoolConfigurer extends AsyncConfigurerSupport {

	@Override
	public Executor getAsyncExecutor() {

		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setBeanName("defalut-ThreadPool");
		executor.setAllowCoreThreadTimeOut(true);
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(20);
        executor.setKeepAliveSeconds( 360 );
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setThreadPriority(Thread.MAX_PRIORITY);
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            ex.printStackTrace();
        };
	}
}
