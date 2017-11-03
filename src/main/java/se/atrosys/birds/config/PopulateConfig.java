package se.atrosys.birds.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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


	// cache manager is merely used so population occurs after the cache manager is set up
	@Autowired
	public PopulateConfig(PopulateService populateService,
	                      CacheManager cacheManager) {
		this.populateService = populateService;
	}

	@PostConstruct
	public void populate() {
		populateService.populate();
	}
}
