package se.atrosys.birds.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import se.atrosys.birds.service.PopulateService;

import javax.annotation.PostConstruct;

/**
 * TODO write documentation
 */
@Configuration
@Profile("populate")
public class PopulateConfig {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final PopulateService populateService;

	@Autowired
	public PopulateConfig(PopulateService populateService) {
		this.populateService = populateService;
	}

	@PostConstruct
	public void populate() {
		populateService.populate();
	}
}
