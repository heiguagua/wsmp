package com.chinawiserv.wsmp.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class WSMPThreadPoolConfigurer extends AsyncConfigurerSupport {

	final private static Logger logger  = LoggerFactory.getLogger(WSMPThreadPoolConfigurer.class);

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
			logger.error(ex.getMessage());
        };
	}
}
