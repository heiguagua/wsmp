package com.chinawiserv.wsmp.configuration;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableMap;

@Configuration
public class BaseConfiguration {

	@Bean
	public CustomEditorConfigurer dateTimeFormaterBean() {

		final CustomEditorConfigurer configurer = new CustomEditorConfigurer();
		configurer.setCustomEditors(ImmutableMap.of(DateTimeFormatter.class, DateTimeFormaterConver.class));
		return configurer;
	}
}
