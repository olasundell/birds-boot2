package se.atrosys.birds.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * TODO write documentation
 */
@Configuration
public class RestTemplateConfig {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RestTemplate snakeCaseRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().forEach(conv -> {
			if (conv.getSupportedMediaTypes().contains(MediaType.APPLICATION_JSON)) {
				MappingJackson2HttpMessageConverter jacksonConv = (MappingJackson2HttpMessageConverter) conv;
				final ObjectMapper mapper = jacksonConv.getObjectMapper()
					.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//				mapper.setConfig(mapper.getDeserializationConfig().with(DeserializationFeature.READ_ENUMS_USING_TO_STRING));
				jacksonConv.setObjectMapper(mapper);
			}
		});

		return restTemplate;
	}
}
