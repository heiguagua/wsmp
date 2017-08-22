package com.chinawiserv.wsmp.configuration;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class BaseConfiguration {

	@Bean
	public CustomEditorConfigurer dateTimeFormaterBean() {

		final CustomEditorConfigurer configurer = new CustomEditorConfigurer();
		configurer.setCustomEditors(ImmutableMap.of(DateTimeFormatter.class, DateTimeFormaterConver.class));
		return configurer;
	}
}
