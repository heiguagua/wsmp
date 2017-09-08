package com.chinawiserv.wsmp.configuration;

import de.onlinehome.geomath.jk3d.Jk3d;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KryingConfiguration {

	@Bean
	public Jk3d jk2d() {

		final Jk3d jk2d = new Jk3d();
		return jk2d;
	}
}
