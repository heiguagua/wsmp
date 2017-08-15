package com.chinawiserv.wsmp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.onlinehome.geomath.jk3d.Jk2d;

@Configuration
public class KryingConfiguration {

	@Bean
	public Jk2d jk2d() {

		final Jk2d jk2d = new Jk2d();
		return jk2d;
	}
}
