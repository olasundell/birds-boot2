package se.atrosys.birds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

/**
 * TODO write documentation
 */
@Configuration
public class CorsConfig {
	@Bean
	public CorsConfiguration corsConfiguration() {
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		return corsConfiguration.applyPermitDefaultValues();
	}
}
